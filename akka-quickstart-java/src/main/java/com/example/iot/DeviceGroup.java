package com.example.iot;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeviceGroup extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    final String groupId;
    final Map<String, ActorRef> deviceIdToActor = new HashMap<>();
    final Map<ActorRef, String> actorToDeviceId = new HashMap<>();

    public DeviceGroup(String groupId) {
        this.groupId = groupId;
    }

    public static Props props(String groupId) {
        return Props.create(DeviceGroup.class, () -> new DeviceGroup(groupId));
    }

    @Override
    public void preStart() throws Exception {
        log.info("DeviceGroup {} started", groupId);
    }

    @Override
    public void postStop() throws Exception {
        log.info("DeviceGroup {} stopped", groupId);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestTrackDevice.class, this::onTrackDevice)
                .match(Terminated.class, this::onTerminate)
                .match(RequestDeviceList.class, this::onDeviceList)
                .build();
    }

    private void onDeviceList(RequestDeviceList requestDeviceList) {
        getSender().tell(new ReplyDeviceList(requestDeviceList.requestId, deviceIdToActor.keySet()), getSelf());
    }

    private void onTerminate(Terminated terminated) {
        ActorRef deviceActor = terminated.getActor();
        String deviceId = actorToDeviceId.get(deviceActor);
        log.info("Device actor for {} has been terminated", deviceId);
        actorToDeviceId.remove(deviceActor);
        deviceIdToActor.remove(deviceId);
    }

    public static final class RequestDeviceList {
        final long requestId;

        public RequestDeviceList(long requestId) {
            this.requestId = requestId;
        }
    }

    public static final class ReplyDeviceList {
        final long requestId;
        final Set<String> ids;

        public ReplyDeviceList(long requestId, Set<String> ids) {
            this.requestId = requestId;
            this.ids = ids;
        }
    }

    private void onTrackDevice(RequestTrackDevice requestTrackDevice) {
        if (this.groupId.equals(requestTrackDevice.groupId)) {
            ActorRef actorRef = deviceIdToActor.get(requestTrackDevice.deviceId);
            if (actorRef != null) {
                actorRef.forward(requestTrackDevice, getContext());
            } else {
                ActorRef deviceActor =
                        getContext()
                                .actorOf(Device.props(requestTrackDevice.deviceId, groupId), "device-" + requestTrackDevice.deviceId);
                getContext().watch(deviceActor);
                deviceIdToActor.put(requestTrackDevice.deviceId, deviceActor);
                actorToDeviceId.put(deviceActor, requestTrackDevice.deviceId);
                deviceActor.forward(requestTrackDevice, getContext());
            }
        } else {
            log.warning(
                    "Ignoring TrackDevice request for {}. This actor is responsible for {}.",
                    groupId,
                    this.groupId);
        }
    }


}
