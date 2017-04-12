import java.io.*;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Writer implements Runnable {
    private static int writers;
    private final int id;
    private File file;
    private RWLock lock;
    private BufferedWriter bufferedWriter;

    public Writer(BufferedWriter bufferedWriter, RWLock lock) throws IOException {
        this.bufferedWriter = bufferedWriter;
        this.lock = lock;
        id = writers;
        writers++;
    }

    @Override

    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        while (true) {
            SleepUtilities.nap();
            System.out.println(MessageFormat.format("Writer {0} wants to write.", id));
            lock.acquireWriteLock();

            System.out.println(MessageFormat.format("Writer {0} is writing.", id));
            try {
                String date = dateFormat.format(new Date());
                bufferedWriter.write(date);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println(MessageFormat.format("Writer {0} is wrote: {1}", id, date));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(MessageFormat.format("Writer {0} is done writing.", id));
            lock.releaseWriteLock();
        }
    }
}
