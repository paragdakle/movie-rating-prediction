import java.util.ArrayList;
import java.util.List;

/**
 * Created by dakle on 21/3/17.
 */
public class User {

    public byte[] ratings;

    public short[] movieIds;

    public List<Byte> ratingsList;

    public List<Short> movieIdsList;

    public float mean_vote;

    public User() {
        ratingsList = new ArrayList<>();
        movieIdsList = new ArrayList<>();
        mean_vote = 0;
    }

    public void generateArrays() {
        ratings = new byte[ratingsList.size()];
        for(int i = 0; i < ratingsList.size(); i++) {
            ratings[i] = ratingsList.get(i);
            mean_vote += ratings[i];
        }
        mean_vote = mean_vote / ratingsList.size();
        movieIds = new short[movieIdsList.size()];
        for(int i = 0; i < movieIdsList.size(); i++) movieIds[i] = movieIdsList.get(i);
    }
}
