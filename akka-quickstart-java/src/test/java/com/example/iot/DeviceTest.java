package com.example.iot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DeviceTest {
    static ActorSystem system;
    private TestKit testProbe;
    private ActorRef deviceActor;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @Before
    public void setUp() {
        testProbe = new TestKit(system);
        deviceActor = system.actorOf(Device.props("group", "device"));
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void replyWithEmptyReaingWhenTemperatureIsNotKnown() {
        //When
        deviceActor.tell(new Device.ReadTemperature(42L), testProbe.getRef());

        //Then
        Device.RespondTemperature respondTemperature = testProbe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(42L, respondTemperature.requestId);
        assertEquals(Optional.empty(), respondTemperature.value);
    }

    @Test
    public void testReplyWithLatestTemperatureReading() {
        //When
        deviceActor.tell(new Device.RecordTemperature(1L, 24.0), testProbe.getRef());
        assertEquals(1L, testProbe.expectMsgClass(Device.TemperatureRecorded.class).requestId);

        //Then
        deviceActor.tell(new Device.ReadTemperature(2L), testProbe.getRef());
        Device.RespondTemperature respondTemperature = testProbe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(2L, respondTemperature.requestId);
        assertEquals(Optional.of(24.0), respondTemperature.value);
    }

    @Test
    public void testReplyWithLatestTemperatureReading2() {
        //When
        deviceActor.tell(new Device.RecordTemperature(3L, 55.0), testProbe.getRef());
        assertEquals(3L, testProbe.expectMsgClass(Device.TemperatureRecorded.class).requestId);

        //Then
        deviceActor.tell(new Device.ReadTemperature(4L), testProbe.getRef());
        Device.RespondTemperature respondTemperature = testProbe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(4L, respondTemperature.requestId);
        assertEquals(Optional.of(55.0), respondTemperature.value);
    }

    @Test
    public void testReplyToRegistrationRequests() {
        //When
        deviceActor.tell(new RequestTrackDevice("group", "device"), testProbe.getRef());
        testProbe.expectMsgClass(DeviceRegistered.class);

        //Then
        assertEquals(deviceActor, testProbe.getLastSender());
    }

    @Test
    public void testIgnoreWrongRegistrationRequestsForInvalidGroupId() {
        //When
        deviceActor.tell(new RequestTrackDevice("wrongGroup", "device"), testProbe.getRef());

        //Then
        testProbe.expectNoMessage(Duration.ofMillis(200));
    }

    @Test
    public void testIgnoreWrongRegistrationRequestsForInvalidDeviceId() {
        //When
        deviceActor.tell(new RequestTrackDevice("group", "wrongDevice"), testProbe.getRef());

        //Then
        testProbe.expectNoMessage(Duration.ofMillis(200));
    }
}