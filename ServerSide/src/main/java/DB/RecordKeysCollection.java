package DB;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class RecordKeysCollection implements Iterable<DatabaseKey> {
    // Thread-safe set
    private final Set<DatabaseKey> keySet = ConcurrentHashMap.newKeySet();

    public void addKey(DatabaseKey recordKey) {
        keySet.add(recordKey);
    }

    public void removeKey(DatabaseKey recordKey) {
        keySet.remove(recordKey);
    }

    public void removeAllKeys(){
        keySet.clear();
    }

    public boolean containsKey(DatabaseKey recordKey) {
        return keySet.contains(recordKey);
    }

    public boolean isEmpty() {
        return keySet.isEmpty();
    }

    @Override
    public Iterator<DatabaseKey> iterator() {
        return keySet.iterator();
    }
}