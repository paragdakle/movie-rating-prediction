import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by dakle on 21/3/17.
 */
public class CollaborativeFiltering {

    public static void main(String[] args) {
        DataManager manager = new DataManager(args[0]);
        manager.generateData();
        System.out.println("Data loaded successfully!");
        calculateWeights(manager);
        System.out.println("Offline process complete!");
        System.out.println("Starting online process!");
        testCF(manager, args[1], Integer.parseInt(args[2]));
    }

    public static void calculateWeights(DataManager manager) {
        float au_mean_vote, ou_mean_vote;
        short auMovieId[], ouMovieId[];
        byte auRating[], ouRating[];
        float au_error, ou_error;
        short weights[];
        double numerator, denominator1, denominator2, denominator;
        Double weight;
        StorageManager manager1 = new StorageManager("output.txt");
        manager1.openFile();
        User activeUser, user;
        for (Integer activeUserId: manager.userIdSet) {
            activeUser = manager.userList.get(manager.userMap.get(activeUserId));
            au_mean_vote = activeUser.mean_vote;
            auMovieId = activeUser.movieIds;
            auRating = activeUser.ratings;
            weights = new short[manager.userIdSet.size()];
            for(Integer userId: manager.userIdSet) {
                if(activeUserId < userId) {
                    user = manager.userList.get(manager.userMap.get(userId));
                    ou_mean_vote = user.mean_vote;
                    ouMovieId = user.movieIds;
                    ouRating = user.ratings;
                    numerator = denominator1 = denominator2 = denominator = 0;
                    if (auMovieId.length > ouMovieId.length) {
                        for (int i = 0, j = 0; i < ouMovieId.length; i++) {
                            while (j < auMovieId.length && auMovieId[j] < ouMovieId[i]) {
                                j++;
                            }
                            if(j >= auMovieId.length) break;
                            if (ouMovieId[i] == auMovieId[j]) {
                                au_error = auRating[j] - au_mean_vote;
                                ou_error = ouRating[i] - ou_mean_vote;
                                numerator += (au_error * ou_error);
                                denominator1 += au_error * au_error;
                                denominator2 += ou_error * ou_error;
                                j++;
                            }
                        }
                    } else {
                        for (int i = 0, j = 0; i < auMovieId.length; i++) {
                            while (j < ouMovieId.length && ouMovieId[j] < auMovieId[i]) {
                                j++;
                            }
                            if(j >= auMovieId.length) break;
                            if (ouMovieId[j] == auMovieId[i]) {
                                au_error = auRating[i] - au_mean_vote;
                                ou_error = ouRating[j] - ou_mean_vote;
                                numerator += (au_error * ou_error);
                                denominator1 += (au_error * au_error);
                                denominator2 += (ou_error * ou_error);
                                j++;
                            }
                        }
                    }
                    denominator = Math.sqrt(denominator1 * denominator2);
                    if(numerator != 0 && denominator != 0) {
                        weight = (numerator / denominator) * 1000;
                        weights[manager.userMap.get(userId)] = weight.shortValue();
                    }
                }
            }
            manager1.writeLine(weights);
        }
        manager1.closeFile();
    }

    public static void testCF(DataManager manager, String testFilePath, int userCount) {
        DataManager manager1 = new DataManager(testFilePath);
        RetrievalManager manager2 = new RetrievalManager("output.txt");
        manager2.openFile();
        String[] splitLine;
        short[] userWeights = new short[manager.userIdSet.size()];
        int[] movieUsers;
        byte[] userRatings;
        int userId;
        short movieId;
        Double dRating, mae = 0.0, rms = 0.0;
        double kappa;
        byte rating;
        double predictedRating;
        float mean_vote;
        int counter = 0;
        if(manager1.openFile()) {
            String line;
            double diff;
            while ((line = manager1.getLine()) != null) {
                counter++;
                splitLine = line.split(",");
                movieId = Short.parseShort(splitLine[0]);
                userId = Integer.parseInt(splitLine[1]);
                dRating = Double.parseDouble(splitLine[2]);
                rating = dRating.byteValue();
                predictedRating = 0;
                kappa = 0;
                movieUsers = manager.movieList.get(manager.movieMap.get(movieId)).userIds;
                userRatings = manager.movieList.get(manager.movieMap.get(movieId)).ratings;
                for(int i = 0; i < movieUsers.length; i++) {
                    if(userId > movieUsers[i]) {
                        userWeights = byteToShort(manager2.readBytes(2, (manager.userIdSet.headSet(movieUsers[i]).size() * userWeights.length * 2) + (manager.userMap.get(userId) + 2)));
                    }
                    else {
                        userWeights = byteToShort(manager2.readBytes(2, (manager.userIdSet.headSet(userId).size() * userWeights.length * 2) + (manager.userMap.get(movieUsers[i]) + 2)));
                    }
                    kappa += userWeights[0];
                    mean_vote = manager.userList.get(manager.userMap.get(movieUsers[i])).mean_vote;
                    predictedRating += ((userWeights[0] / 1000) * (userRatings[i] - mean_vote));
                }
                predictedRating = manager.userList.get(manager.userMap.get(userId)).mean_vote + ((1 / kappa) * predictedRating);
                diff = predictedRating - rating;
                mae += Math.abs(diff);
                rms += (diff * diff);
                System.out.println(line);
                System.out.print("Absolute Error: " + Math.abs(diff));
                System.out.print(" Squared Error: " + (diff * diff) + ".\n");
                if(userCount > 0 && counter == userCount) {
                    break;
                }
            }
            manager1.closeFile();
            mae = mae / counter;
            rms = Math.sqrt(rms / counter);
            System.out.print("Final Mean Absolute Error: " + mae);
            System.out.print(" Final Root Mean Squared Error: " + rms + ".\n");
        }
        manager2.closeFile();
    }

    public static short[] byteToShort(byte[] arr) {
        short[] shorts = new short[arr.length/2];
        ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }
}
