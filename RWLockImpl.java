import java.util.concurrent.Semaphore;

/**
 * Created by thoma on 12-Apr-17.
 */
public class RWLockImpl implements RWLock {
    private Semaphore mutex; // for this class own internal critical section
    private Semaphore resource;

    private int readers;

    public RWLockImpl() {
        this.mutex = new Semaphore(1);
        this.resource = new Semaphore(1);
    }

    @Override
    public void acquireReadLock() {
        try {
            mutex.acquire();
            readers++;
            // if I am the first reader tell all others
            // that the resource is being read
            if (readers == 1) {
                resource.acquire();
            }
            mutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void acquireWriteLock() {
        try {
            resource.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseReadLock() {
        try {
            mutex.acquire();
            readers--;
            // if I am the last reader tell all others
            // that the resource is no longer being read
            if (readers == 0) {
                resource.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseWriteLock() {
        resource.release();
    }

    @Override
    public String toString() {
        return "RWLockImpl{" + "readers=" + readers + '}';
    }
}
