/**
 * Created by thoma on 12-Apr-17.
 */
public interface RWLock {
    void acquireReadLock();

    void acquireWriteLock();

    void releaseReadLock();

    void releaseWriteLock();
}
