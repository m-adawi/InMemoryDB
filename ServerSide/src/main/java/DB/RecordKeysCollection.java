package DB;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RecordKeysCollection implements Iterable<DatabaseKey> {
    private final Set<DatabaseKey> keySet = new HashSet<>();

    public synchronized void addKey(DatabaseKey recordKey) {
        keySet.add(recordKey);
    }

    public synchronized void removeKey(DatabaseKey recordKey) {
        keySet.remove(recordKey);
    }

    public synchronized void removeAllKeys(){
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