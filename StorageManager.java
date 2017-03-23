import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by dakle on 22/3/17.
 */
public class StorageManager {

    private String filePath = "";

    private DataOutputStream stream = null;


    public StorageManager(String filePath) {
        this.filePath = filePath;
    }

    public boolean openFile() {
        try {
            stream = new DataOutputStream(new FileOutputStream(filePath));
            if(stream != null) {
                return true;
            }
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void writeLine(short[] output) {
        try {
            stream.write(shortToBytes(output));
            stream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] shortToBytes(short[] arr) {
        byte[] bytes2 = new byte[arr.length * 2];
        ByteBuffer.wrap(bytes2).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(arr);
        return bytes2;
    }

    public boolean closeFile() {
        try {
            if(stream != null) {
                stream.close();
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
