package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Messages;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ActorTest {
    private ActorSystem actorSystem;
    private Class<?> actorClass;
    private Object[] args;
    private BiConsumer<ActorRef, ActorRef> actorRefConsumer;
    private Object expected;

    public ActorTest with(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        return this;
    }

    public TestKit build() {
        return new TestKit(actorSystem) {{
            final Props props = Props.create(actorClass, args);
            final ActorRef subject = actorSystem.actorOf(props);

            actorRefConsumer.accept(subject, getRef());
        }};
    }

    public ActorTest execute(BiConsumer<ActorRef, ActorRef> actorRefConsumer) {
        this.actorRefConsumer = actorRefConsumer;
        return this;
    }

    public ActorTest forActor(Class<?> messageActorClass) {
        this.actorClass = messageActorClass;
        return this;
    }

    public ActorTest withArguments(Object... args) {
        this.args = args;
        return null;
    }

    public ActorTest expect(Object object) {
        expected = object;
        return null;
    }
}
