package gt.performance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EnergySource {
    private final long MAXLEVEL = 100;
    private long level = MAXLEVEL;
    private long usage = 0;

    private final ReadWriteLock monitor = new ReentrantReadWriteLock();

    private static final ScheduledExecutorService replenishTimer = Executors.newScheduledThreadPool(10);
    private ScheduledFuture<?> replenishTask;

    private EnergySource() {}

    private void init() {
        replenishTask = replenishTimer.scheduleAtFixedRate(() -> replenish(), 0, 1, TimeUnit.SECONDS);
    }

    public static EnergySource create() {
        EnergySource energySource = new EnergySource();
        energySource.init();
        return energySource;
    }

    public long getUnitsAvailable() {
        monitor.readLock().lock();
        try {
            return usage;
        } finally {
            monitor.readLock().unlock();
        }
    }

    public boolean useEnergy(final long units) {
        monitor.writeLock().lock();
        try {
            if (units > 0 && level >= units) {
                level -= units;
                usage++;
                return true;
            } else {
                return false;
            }
        } finally {
            monitor.writeLock().unlock();
        }
    }

    public synchronized void stopEnergySource() {
        replenishTask.cancel(false);
    }

    private void replenish() {
        monitor.writeLock().lock();
        try {
            if (level < MAXLEVEL) level++;
        } finally {
            monitor.writeLock().unlock();
        }
    }
}
