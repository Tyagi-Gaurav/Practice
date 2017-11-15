package gt.practice.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorLogging;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Optional;

public class RestServiceActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(
                String.class, s -> {
                    log.info("Received String message: {} ", s);
                    if (s.equals("restart"))
                        throw new IllegalArgumentException();
                    getSender().tell("Hello World", getSelf());
                }
        )
        .matchAny(o -> log.info("received Unknown message"))
        .build();
    }

    @Override
    public void preStart() throws Exception {
        log.info("Prestart RestService Actor");
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop RestService Actor");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("postRestart");
        super.postRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        log.info("preRestart");
        super.preRestart(reason, message);
    }
}
