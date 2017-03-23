import java.util.ArrayList;
import java.util.List;

/**
 * Created by dakle on 21/3/17.
 */
public class Movie {

    public int[] userIds;

    public List<Integer> userIdList;

    public byte[] ratings;

    public List<Byte> ratingsList;

    public Movie() {
        ratingsList = new ArrayList<>();
        userIdList = new ArrayList<>();
    }

    public void generateArrays() {
        userIds = new int[userIdList.size()];
        for(int i = 0; i < userIdList.size(); i++) userIds[i] = userIdList.get(i);
        ratings = new byte[ratingsList.size()];
        for(int i = 0; i < ratingsList.size(); i++) ratings[i] = ratingsList.get(i);
    }
}
