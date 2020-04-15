package gt.performance;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AccountService {
    public boolean transfer(final AccountWithSimpleLock from,
                            final AccountWithSimpleLock to, final int amount) throws InterruptedException {
        AccountWithSimpleLock[] accountWithSimpleLocks = {from, to};
        Arrays.sort(accountWithSimpleLocks);

        try {
            if (accountWithSimpleLocks[0].monitor.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    if (accountWithSimpleLocks[1].monitor.tryLock(1, TimeUnit.SECONDS)) {
                        if (from.withdraw(amount)) {
                            to.deposit(amount);
                            return true;
                        }

                        return false;
                    }
                } finally {
                    accountWithSimpleLocks[1].monitor.unlock();
                }
            }
        } finally {
            accountWithSimpleLocks[0].monitor.unlock();
        }

        return false;
    }
}
