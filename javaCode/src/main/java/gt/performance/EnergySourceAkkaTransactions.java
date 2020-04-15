package gt.performance;

import akka.stm.Atomic;
import akka.stm.Ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Instructions to run the program.
 *
 * JAR1=/Users/gauravt/.m2/repository/org/scala-lang/scala-library/2.9.1/scala-library-2.9.1.jar
 * JAR2=/Users/gauravt/.m2/repository/se/scalablesolutions/akka/akka-stm/1.3.1/akka-stm-1.3.1.jar
 * JAR3=/Users/gauravt/.m2/repository/org/multiverse/multiverse-alpha/0.6.2/multiverse-alpha-0.6.2.jar
 * JAR4=/Users/gauravt/.m2/repository/se/scalablesolutions/akka/akka-actor/1.3/akka-actor-1.3.jar
 *
 * export JARS=$JAR1:$JAR2:$JAR3:$JAR4:.
 *
 * javac -classpath $JARS -d . gt/performance/EnergySourceAkkaTransactions.java
 *
 * java -classpath $JARS gt/performance/EnergySourceAkkaTransactions
 */

public class EnergySourceAkkaTransactions {
    private final long MAXLEVEL = 100;
    private Ref<Long> level = new Ref<>(MAXLEVEL);
    private Ref<Long> usageCount = new Ref<>(0L);
    private Ref<Boolean> keepRunning = new Ref<>(true);

    private static final ScheduledExecutorService replenishTimer = Executors.newScheduledThreadPool(10);

    private EnergySourceAkkaTransactions() {
    }

    private void init() {
        replenishTimer.schedule(new Runnable() {
            @Override
            public void run() {
                replenish();
                if (keepRunning.get()) replenishTimer.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 1, TimeUnit.SECONDS);
    }

    public static EnergySourceAkkaTransactions create() {
        EnergySourceAkkaTransactions energySource = new EnergySourceAkkaTransactions();
        energySource.init();
        return energySource;
    }

    public long getUnitsAvailable() {
        return level.get();
    }

    public boolean useEnergy(final long units) {
        return new Atomic<Boolean>() {

            @Override
            public Boolean atomically() {
                Long curremtLevel = level.get();
                if (units > 0 && curremtLevel >= units) {
                    level.swap(curremtLevel - units);
                    usageCount.swap(usageCount.get() + 1);
                    return true;
                } else {
                    return false;
                }
            }
        }.execute();
    }

    public synchronized void stopEnergySource() {
        keepRunning.swap(false);
    }

    public long getUsageCount() {
        return usageCount.get();
    }

    private void replenish() {
        new Atomic() {
            @Override
            public Object atomically() {
                Long currentLevel = level.get();
                if (currentLevel < MAXLEVEL)
                    level.swap(currentLevel + 1);
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) throws InterruptedException {
        EnergySourceAkkaTransactions energySourceAkkaTransactions = EnergySourceAkkaTransactions.create();
        System.out.println("Energy level at start : " + energySourceAkkaTransactions.getUnitsAvailable());

        List<Callable<Object>> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tasks.add(() -> {
                for (int j = 0; j < 7; j++) {
                    energySourceAkkaTransactions.useEnergy(1);
                }
                return null;
            });
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.invokeAll(tasks);

        System.out.println("Energy level at End : " + energySourceAkkaTransactions.getUnitsAvailable());
        System.out.println("Usage: " + energySourceAkkaTransactions.getUsageCount());

        energySourceAkkaTransactions.stopEnergySource();
        executorService.shutdown();
    }
}
