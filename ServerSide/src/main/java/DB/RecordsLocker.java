package DB;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RecordsLocker {
    private final Map<DatabaseKey, Lock> lockMap = new HashMap<>();

    public void lock(DatabaseKey recordKey) {
        // No two threads should be able to generate new locks
        synchronized (lockMap) {
            if (!lockMap.containsKey(recordKey))
                lockMap.put(recordKey, new ReentrantLock());
        }
        lockMap.get(recordKey).lock();
    }

    public void unlock(DatabaseKey recordKey) {
        lockMap.get(recordKey).unlock();
    }
}
