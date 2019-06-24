package com.example.iot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class IOTMain {
    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("iot-system");

        try {
            ActorRef actorRef = actorSystem.actorOf(IOTSupervisor.props(), "iot-supervisor");

            System.out.println("Please ENTER to exit the system");
            System.in.read();
        } finally {
            actorSystem.terminate();
        }
    }
}
