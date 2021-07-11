package StudentDatabaseTester;

import DB.DatabaseKey;
import DB.Record;
import DB.RecordsLocker;
import StudentDB.StudentID;
import StudentDB.StudentRecord;
import org.junit.Ignore;
import org.junit.Test;

public class RecordsLockerTest {
    private final static RecordsLocker locker = new RecordsLocker();

    @Test
    public void testLockingTwice() {
        Record record = new StudentRecord(new StudentID(1));
        locker.lock(record.getKey());
        locker.lock(record.getKey());
        System.out.println("finished");
    }

    @Ignore
    public void testMultipleThreadsLock() throws Exception {
        Record record = new StudentRecord(new StudentID(1));
        Runnable task1 = new LockerTester(locker, 100, record.getKey());
        Runnable task2 = new LockerTester(locker, 50, record.getKey());

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        Thread.sleep(10);
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Finished");
    }
}

// Threads that write

class LockerTester implements Runnable {
    private RecordsLocker locker;
    int timeToReleaseLock;
    DatabaseKey key;

    public LockerTester(RecordsLocker locker, int timeToReleaseLock, DatabaseKey key) {
        this.locker = locker;
        this.timeToReleaseLock = timeToReleaseLock;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            locker.lock(key);
            Thread.sleep(timeToReleaseLock);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            locker.unlock(key);
            System.out.println("Hello from different thread, timeTorRelease: " + timeToReleaseLock);
        }
    }
}