package gt.performance;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountWithSimpleLock implements Comparable<AccountWithSimpleLock> {
    private int balance;
    public final Lock monitor = new ReentrantLock();

    public AccountWithSimpleLock(int balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(AccountWithSimpleLock other) {
        return Integer.compare(hashCode(), other.hashCode());
    }

    public void deposit(final int amount) {
        try {
            monitor.lock();
            if (amount > 0) balance += amount;
        } finally {
            monitor.unlock();
        }
    }

    public boolean withdraw(final int amount) {
        try {
            monitor.lock();
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }

            return false;
        } finally {
            monitor.unlock();
        }
    }
}
