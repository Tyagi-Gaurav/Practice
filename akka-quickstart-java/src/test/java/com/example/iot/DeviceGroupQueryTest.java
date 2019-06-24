package com.example.iot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.testkit.javadsl.TestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DeviceGroupQueryTest {
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
    public void testReturnTemperatureValueForWorkingDevices() {
        //Given
        TestKit requester = new TestKit(system);

        TestKit device1 = new TestKit(system);
        TestKit device2 = new TestKit(system);

        //When
        Map<ActorRef, String> actorToDeviceId = new HashMap<>();
        actorToDeviceId.put(device1.getRef(), "device1");
        actorToDeviceId.put(device2.getRef(), "device2");

        ActorRef queryActor = system.actorOf(DeviceGroupQuery
                .props(actorToDeviceId,
                        1L,
                        requester.getRef(),
                        new FiniteDuration(3, TimeUnit.MINUTES)));

        assertEquals(0L, device1.expectMsgClass(Device.ReadTemperature.class).requestId);
        assertEquals(0L, device2.expectMsgClass(Device.ReadTemperature.class).requestId);

        queryActor.tell(new Device.RespondTemperature(0L, Optional.empty()), device1.getRef());
        queryActor.tell(new Device.RespondTemperature(0L, Optional.of(2.0)), device2.getRef());

        //Then
        Types.RespondAllTemperatures response = requester.expectMsgClass(Types.RespondAllTemperatures.class);
        assertEquals(1L, response.requestId);

        Map<String, Types.TemperatureReading> expectedTemperatures = new HashMap<>();
        expectedTemperatures.put("device1", Types.TemperatureNotAvailable.INSTANCE);
        expectedTemperatures.put("device2", new Types.Temperature(2.0));

        assertEquals(expectedTemperatures, response.temperatures);
    }

    @Test
    public void testReturnDeviceNotAvailableIfDeviceStopsBeforeAnswering() throws Exception {
        //Given
        TestKit requester = new TestKit(system);

        TestKit device1 = new TestKit(system);
        TestKit device2 = new TestKit(system);

        Map<ActorRef, String> actorToDeviceId = new HashMap<>();
        actorToDeviceId.put(device1.getRef(), "device1");
        actorToDeviceId.put(device2.getRef(), "device2");

        //When
        ActorRef queryActor = system.actorOf(DeviceGroupQuery
                .props(actorToDeviceId,
                        1L,
                        requester.getRef(),
                        new FiniteDuration(3, TimeUnit.MINUTES)));
        assertEquals(0L, device1.expectMsgClass(Device.ReadTemperature.class).requestId);
        assertEquals(0L, device2.expectMsgClass(Device.ReadTemperature.class).requestId);

        queryActor.tell(new Device.RespondTemperature( 0L, Optional.of(1.0)), device1.getRef());
        device2.getRef().tell(PoisonPill.getInstance(), ActorRef.noSender());

        //Then
        Types.RespondAllTemperatures response =
                requester.expectMsgClass(Types.RespondAllTemperatures.class);
        assertEquals(1L, response.requestId);

        Map<String, Types.TemperatureReading> expectedTemperatures = new HashMap<>();
        expectedTemperatures.put("device1", new Types.Temperature(1.0));
        expectedTemperatures.put("device2", Types.DeviceNotAvailable.INSTANCE);

        assertEquals(expectedTemperatures, response.temperatures);
    }

    @Test
    public void testReturnTemperatureReadingEvenIfDeviceStopsAfterAnswering() throws Exception {
        //Given
        TestKit requester = new TestKit(system);

        TestKit device1 = new TestKit(system);
        TestKit device2 = new TestKit(system);

        Map<ActorRef, String> actorToDeviceId = new HashMap<>();
        actorToDeviceId.put(device1.getRef(), "device1");
        actorToDeviceId.put(device2.getRef(), "device2");

        ActorRef queryActor = system.actorOf(DeviceGroupQuery
                .props(actorToDeviceId,
                        1L,
                        requester.getRef(),
                        new FiniteDuration(3, TimeUnit.SECONDS)));
        assertEquals(0L, device1.expectMsgClass(Device.ReadTemperature.class).requestId);
        assertEquals(0L, device2.expectMsgClass(Device.ReadTemperature.class).requestId);

        //When
        queryActor.tell(new Device.RespondTemperature(0L, Optional.of(1.0)), device1.getRef());
        device2.getRef().tell(PoisonPill.getInstance(), ActorRef.noSender());

        Types.RespondAllTemperatures response =
                requester.expectMsgClass(Types.RespondAllTemperatures.class);
        assertEquals(1L, response.requestId);

        //Then
        Map<String, Types.TemperatureReading> expectedTemperatures = new HashMap<>();
        expectedTemperatures.put("device1", new Types.Temperature(1.0));
        expectedTemperatures.put("device2", Types.DeviceNotAvailable.INSTANCE);

        assertEquals(expectedTemperatures, response.temperatures);
    }
}