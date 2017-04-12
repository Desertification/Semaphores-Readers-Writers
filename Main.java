import java.io.*;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Main {
    public static void main(String[] args) {
        RWLock lock = new RWLockImpl();

        File file = new File("example resource.txt");

        try {
            file.delete();
            file.createNewFile();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));


            Thread[] writers = new Thread[2];
            Thread[] readers = new Thread[5];

            for (int i = 0; i < writers.length; i++) {
                Thread thread = new Thread(new Writer(bufferedWriter, lock));
                writers[i] = thread;
                thread.start();
            }

            for (int i = 0; i < readers.length; i++) {
                Thread thread = new Thread(new Reader(bufferedReader, lock));
                readers[i] = thread;
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}