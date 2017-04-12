import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Reader implements Runnable {
    private static int readers;
    private final int id;
    private File file;
    private RWLock lock;
    private BufferedReader bufferedReader;

    public Reader(BufferedReader bufferedReader, RWLock lock) throws FileNotFoundException {
        this.bufferedReader = bufferedReader;
        this.lock = lock;
        id = readers;
        readers++;
    }

    @Override

    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        while (true) {
            SleepUtilities.nap();
            System.out.println(MessageFormat.format("Reader {0} wants to read.", id));
            lock.acquireReadLock();

            System.out.println(MessageFormat.format("reader {0} is reading. {1}", id, lock));
            try {
                String line = bufferedReader.readLine();
                System.out.println(MessageFormat.format("reader {0} read: {1}", id, line));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(MessageFormat.format("reader {0} is done reading. {1}", id, lock));
            lock.releaseReadLock();
        }
    }
}
