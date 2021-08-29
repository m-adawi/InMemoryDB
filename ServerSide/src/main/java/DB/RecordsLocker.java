package DB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RecordsLocker {
    // Thread-safe map
    private final Map<DatabaseKey, Lock> lockMap = new ConcurrentHashMap<>();

    public void lock(Record record) {
        lock(record.getKey());
    }

    public void lock(DatabaseKey recordKey) {
        if (!lockMap.containsKey(recordKey))
            lockMap.put(recordKey, new ReentrantLock());
        lockMap.get(recordKey).lock();
    }

    public void lock(List<DatabaseKey> keys) {
        for(DatabaseKey key : keys) {
            lock(key);
        }
    }

    public void unlock(Record record) {
        unlock(record.getKey());
    }

    public void unlock(DatabaseKey recordKey) {
        lockMap.get(recordKey).unlock();
    }

    public void unlock(List<DatabaseKey> keys) {
        for(DatabaseKey key : keys) {
            unlock(key);
        }
    }

    public void deleteLock(DatabaseKey recordKey) {
        lockMap.remove(recordKey);
    }
}
