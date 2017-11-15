package gt.practice.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("TestSystem");
        ActorRef actorRef = actorSystem.actorOf(Props.create(RestServiceActor.class));
        actorRef.tell("hello", actorSystem.lookupRoot());
        actorRef.tell("restart", actorSystem.lookupRoot());

    }
}
