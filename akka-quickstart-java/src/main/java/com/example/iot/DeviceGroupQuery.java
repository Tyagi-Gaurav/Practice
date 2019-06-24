package com.example.iot;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeviceGroupQuery extends AbstractActor {
    public static final class CollectionTimeout {}

    public final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    final Map<ActorRef, String> actorToDeviceId;
    final long requestId;
    final ActorRef requestor;

    Cancellable queryTimeoutTimer;

    public DeviceGroupQuery(Map<ActorRef, String> actorToDeviceId,
                            long requestId,
                            ActorRef actorRef,
                            FiniteDuration timeout) {
        this.actorToDeviceId = actorToDeviceId;
        this.requestId = requestId;
        this.requestor = actorRef;
        this.queryTimeoutTimer =
                getContext().
                getSystem().scheduler()
                .scheduleOnce(timeout, getSelf(), new CollectionTimeout(),
                        getContext().getDispatcher(), getSelf());
    }

    public static Props props(Map<ActorRef, String> actorToDeviceId,
                              long requestId,
                              ActorRef requestor,
                              FiniteDuration timeout
                              ) {
        return Props.create(DeviceGroupQuery.class,
                () -> new DeviceGroupQuery(actorToDeviceId, requestId, requestor, timeout));
    }

    @Override
    public void preStart() {
        for (ActorRef actorRef : actorToDeviceId.keySet()) {
            getContext().watch(actorRef);
            actorRef.tell(new Device.ReadTemperature(0L), getSelf());
        }
    }

    @Override
    public void postStop() {
        queryTimeoutTimer.cancel();
    }

    /*
    You can imagine that before starting, your actor automatically calls
    context.become(receive), i.e. installing the Receive function that is
    returned from receive.

    This is another important observation: it is not
    receive that handles the messages, it returns a Receive function that
    will actually handle the messages.
     */
    @Override
    public Receive createReceive() {
        return waitingForReplies(new HashMap<>(), actorToDeviceId.keySet());
    }

    private Receive waitingForReplies(Map<String, Types.TemperatureReading> repliesSoFar, Set<ActorRef> stillWaiting) {
        return receiveBuilder()
                .match(Device.RespondTemperature.class, r -> {
                    ActorRef sender = getSender();
                    Types.TemperatureReading temperatureReading = r.value.map(v -> (Types.TemperatureReading) new Types.Temperature(v))
                            .orElse(Types.TemperatureNotAvailable.INSTANCE);
                    receivedResponse(sender, temperatureReading, stillWaiting, repliesSoFar);
                })
                .match(Terminated.class, t-> {
                    receivedResponse(t.getActor(), Types.DeviceNotAvailable.INSTANCE, stillWaiting, repliesSoFar);
                })
                .match(CollectionTimeout.class, t -> {
                    Map<String, Types.TemperatureReading> replies = new HashMap<>(repliesSoFar);
                    for (ActorRef actorRef : stillWaiting) {
                        String deviceId = actorToDeviceId.get(actorRef);
                        replies.put(deviceId, Types.DeviceTimedOut.INSTANCE);
                    }
                    requestor.tell(new Types.RespondAllTemperatures(requestId, replies), getSelf());
                    getContext().stop(self());
                })
                .build();
    }

    private void receivedResponse(ActorRef sender,
                                  Types.TemperatureReading temperatureReading,
                                  Set<ActorRef> stillWaiting,
                                  Map<String, Types.TemperatureReading> repliesSoFar) {
        getContext().unwatch(sender);
        String deviceId = actorToDeviceId.get(sender);

        Set<ActorRef> newStillWaiting = new HashSet<>(stillWaiting);
        newStillWaiting.remove(sender);

        Map<String, Types.TemperatureReading> newRepliesSoFar = new HashMap<>(repliesSoFar);
        newRepliesSoFar.put(deviceId, temperatureReading);

        if (newStillWaiting.isEmpty()) {
            requestor.tell(new Types.RespondAllTemperatures(requestId, newRepliesSoFar), getSelf());
            getContext().stop(getSelf());
        } else {
            getContext().become(waitingForReplies(newRepliesSoFar, newStillWaiting));
        }
    }
}
