import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by dakle on 22/3/17.
 */
public class RetrievalManager {

    private String filePath = "";

    private RandomAccessFile handler = null;


    public RetrievalManager(String filePath) {
        this.filePath = filePath;
    }

    public boolean openFile() {
        try {
            handler = new RandomAccessFile(filePath, "r");
            if(handler != null) {
                return true;
            }
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] readBytes(int bytesToRead, long offset) {
        try {
            byte[] buffer = new byte[bytesToRead];
            handler.seek(offset);
            handler.readFully(buffer);
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean closeFile() {
        try {
            if(handler != null) {
                handler.close();
                return true;
            }
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
