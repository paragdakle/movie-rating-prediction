# movie-rating-prediction
Movie Rating Prediction using Collaborative Filtering

To run CollaborativeFiltering.java follow the steps below on terminal:
``` terminal
javac /path/to/file/CollaborativeFiltering.java
java /path/to/file/CollaborativeFiltering <training-file-path> <test-file-path> <number-of-samples-to-test>
```
 where-  
 training-file-path = The path to file containing Training Data.  
 test-file-path : The path to file containing Test Data.  
 number-of-samples-to-test : Since the test files contains > 100000 samples and each sample takes around 1 sec for prediction, you can pass number of samples to test. Pass -1 to run for all test samples.

## Sample Execution:

```
javac collaborative_filtering/CollaborativeFiltering.java
java collaborative_filtering/CollaborativeFiltering ../train.txt ../test.txt 10

Data loaded successfully!
Offline process complete!
Starting online process!
8,573364,1.0
Absolute Error: 2.4775274175217405 Squared Error: 6.138142104571945.
8,2149668,3.0
Absolute Error: 0.4383648417055439 Squared Error: 0.19216373444352658.
8,1089184,3.0
Absolute Error: 0.03522061885102801 Squared Error: 0.0012404919922493895.
8,2465894,3.0
Absolute Error: 0.2389521973166593 Squared Error: 0.05709815260245967.
8,534508,1.0
Absolute Error: 2.1085672126169057 Squared Error: 4.4460556901230275.
8,992921,4.0
Absolute Error: 0.7205293967135362 Squared Error: 0.5191626115283725.
8,595054,4.0
Absolute Error: 0.8348659249676005 Squared Error: 0.6970011126720072.
8,1298304,4.0
Absolute Error: 0.05413234845651882 Squared Error: 0.0029303111494179755.
8,1661600,4.0
Absolute Error: 0.289325940183609 Squared Error: 0.08370949966312931.
8,553787,2.0
Absolute Error: 1.3938748916219446 Squared Error: 1.9428872134940878.
Final Mean Absolute Error: 0.8591360789955086 Final Root Mean Squared Error: 1.1866082303035077.
```
