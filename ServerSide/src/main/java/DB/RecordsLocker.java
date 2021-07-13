package DB;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RecordsLocker {
    // Thread-safe map
    private final Map<DatabaseKey, Lock> lockMap = new ConcurrentHashMap<>();

    public void lock(DatabaseKey recordKey) {
        if (!lockMap.containsKey(recordKey))
            lockMap.put(recordKey, new ReentrantLock());
        lockMap.get(recordKey).lock();
    }

    public void unlock(DatabaseKey recordKey) {
        lockMap.get(recordKey).unlock();
    }

    public void deleteLock(DatabaseKey recordKey) {
        lockMap.remove(recordKey);
    }
}
