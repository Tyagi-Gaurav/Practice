package com.example.iot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.testkit.javadsl.TestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeviceGroupTest {
    static ActorSystem system;
    private TestKit probe;

    @Before
    public void setUp() {
        system = ActorSystem.create();
        probe = new TestKit(system);
    }

    @After
    public void tearDown() {
        if (system != null) {
            system.terminate();
        }
    }

    @Test
    public void testRegisterDeviceActor() {
        //Given
        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));

        //When
        groupActor.tell(new RequestTrackDevice("group", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);
        ActorRef deviceActor1 = probe.getLastSender();

        groupActor.tell(new RequestTrackDevice("group", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);
        ActorRef deviceActor2 = probe.getLastSender();
        assertNotEquals(deviceActor1, deviceActor2);

        //Then
        deviceActor1.tell(new Device.RecordTemperature(0L, 1.0), probe.getRef());
        assertEquals(0L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);
        deviceActor2.tell(new Device.RecordTemperature(1L, 2.0), probe.getRef());
        assertEquals(1L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);
    }

    @Test
    public void testIgnoreRequestsForWrongGroupId() {
        TestKit probe = new TestKit(system);
        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));

        groupActor.tell(new RequestTrackDevice("wrongGroup", "device1"), probe.getRef());
        probe.expectNoMessage();
    }

    @Test
    public void testListActiveDevices() throws Exception {
        TestKit probe = new TestKit(system);
        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));

        groupActor.tell(new RequestTrackDevice("group", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);

        groupActor.tell(new RequestTrackDevice("group", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);

        groupActor.tell(new DeviceGroup.RequestDeviceList(0L), probe.getRef());
        DeviceGroup.ReplyDeviceList reply = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);
        assertEquals(0L, reply.requestId);
        assertEquals(Stream.of("device1", "device2").collect(Collectors.toSet()), reply.ids);
    }

    @Test
    public void testListActiveDevicesAfterOneShutsDown() {
        TestKit probe = new TestKit(system);
        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));

        groupActor.tell(new RequestTrackDevice("group", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);
        ActorRef toShutDown = probe.getLastSender();

        groupActor.tell(new RequestTrackDevice("group", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceRegistered.class);

        groupActor.tell(new DeviceGroup.RequestDeviceList(0L), probe.getRef());
        DeviceGroup.ReplyDeviceList reply = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);
        assertEquals(0L, reply.requestId);
        assertEquals(Stream.of("device1", "device2").collect(Collectors.toSet()), reply.ids);

        probe.watch(toShutDown);
        toShutDown.tell(PoisonPill.getInstance(), ActorRef.noSender());
        probe.expectTerminated(toShutDown);

        // using awaitAssert to retry because it might take longer for the groupActor
        // to see the Terminated, that order is undefined
        probe.awaitAssert(
                () -> {
                    groupActor.tell(new DeviceGroup.RequestDeviceList(1L), probe.getRef());
                    DeviceGroup.ReplyDeviceList r = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);
                    assertEquals(1L, r.requestId);
                    assertEquals(Stream.of("device2").collect(Collectors.toSet()), r.ids);
                    return null;
                });
    }
}