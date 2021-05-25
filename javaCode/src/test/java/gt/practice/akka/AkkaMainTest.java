package gt.practice.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.TestProbe;
import akka.util.FiniteDuration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AkkaMainTest {
    ActorSystem application = ActorSystem.create();
    private TestProbe testProbe = new TestProbe(application);

    @Test
    void name() {
        ActorRef ref = testProbe.ref();
        ref.tell("Do Something", testProbe.ref());
        List<Long> times = new ArrayList<>();
        int N = 200;

        List<Long> expectedTimes = IntStream.range(0, N)
                .mapToObj(() -> {
                    var start = System.currentTimeMillis();
                    testProbe.expectMsg(new FiniteDuration(100, TimeUnit.MILLISECONDS),
                            "Test Should Have finished",
                            Response(string));
                    var stop = System.currentTimeMillis();
                    return stop - start;
                })
                .sorted(Comparator.naturalOrder().reversed())
                .skip(N * 0.5)
                .collect(Collectors.toList());

        Long ninetyFifthPercentile = expectedTimes.get(expectedTimes.size() - 1);
        assertThat(ninetyFifthPercentile).isLessThan(1);
    }
}
