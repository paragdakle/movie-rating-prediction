import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by dakle on 21/3/17.
 */
public class DataManager {

    private String filePath = "";

    private BufferedReader reader = null;

    public Map<Short, Integer> movieMap;

    public Map<Integer, Integer> userMap;

    public List<User> userList;

    public List<Movie> movieList;

    public TreeSet<Integer> userIdSet;

    public TreeSet<Short> movieIdSet;

    public DataManager(String filePath) {
        this.filePath = filePath;
        this.movieMap = new HashMap<>();
        this.userMap = new HashMap<>();
        this.userList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.userIdSet = new TreeSet<>();
        this.movieIdSet = new TreeSet<>();
    }

    public boolean openFile() {
        try {
            File f = new File(this.filePath);
            reader = new BufferedReader(new FileReader(f));
            if(reader != null) {
                return true;
            }
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLine() {
        try {
            if(reader != null) {
                return reader.readLine();
            }
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean closeFile() {
        try {
            if(reader != null) {
                reader.close();
                return true;
            }
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generateData() {
        String line;
        String[] splitLine;
        int userCounter = 0, movieCounter = 0;
        int userId;
        short movieId;
        Double dRating;
        byte rating;
        if(reader == null) {
            if(this.openFile()) {
                while ((line = getLine()) != null) {
                    splitLine = line.split(",");
                    movieId = Short.parseShort(splitLine[0]);
                    userId = Integer.parseInt(splitLine[1]);
                    dRating = Double.parseDouble(splitLine[2]);
                    rating = dRating.byteValue();
                    if(!userMap.containsKey(userId)) {
                        userIdSet.add(userId);
                        userMap.put(userId, userCounter);
                        userList.add(new User());
                        userCounter++;
                    }
                    userList.get(userMap.get(userId)).movieIdsList.add(movieId);
                    userList.get(userMap.get(userId)).ratingsList.add(rating);
                    if(!movieMap.containsKey(movieId)) {
                        movieIdSet.add(movieId);
                        movieMap.put(movieId, movieCounter);
                        movieList.add(new Movie());
                        movieCounter++;
                    }
                    movieList.get(movieMap.get(movieId)).userIdList.add(userId);
                    movieList.get(movieMap.get(movieId)).ratingsList.add(rating);
                }
                for (Integer id :
                        userIdSet) {
                    userList.get(userMap.get(id)).generateArrays();
                }
                for (Short id :
                        movieIdSet) {
                    movieList.get(movieMap.get(id)).generateArrays();
                }
                this.closeFile();
                return true;
            }
            return false;
        }
        return false;
    }
}
