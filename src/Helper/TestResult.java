package Helper;

import javafx.util.Pair;

import java.util.Collections;

/**
 * Created by Jack F. Dalton on 0031 31 08 2016.
 */
public class TestResult {
    private String label;
    private int sampleSize;
    private long[] rawResults;
    private double median;
    private double mean;
    private float range;
    private float min;
    //standard deviation
    //mode

    public TestResult(String label, long[] rawResults, int sampleSize) {
        this.label = label;
        this.sampleSize = sampleSize;
        this.rawResults = rawResults;
        this.calculateStatistics();
    }

    private void calculateStatistics() {
        this.median = calculateMedian();
        this.mean = calculateMean();
        this.range = calculateRange();
        this.min = calculateMinResult();
    }

    private double calculateMedian() {
        int middle = rawResults.length/2;
        if (rawResults.length%2 == 1) {
            return rawResults[middle];
        } else {
            return (rawResults[middle-1] + rawResults[middle]) / 2.0;
        }
    }

    private double calculateMean() {
        float sum = 0;
        for (int i = 0; i < rawResults.length; i++) {
            sum += rawResults[i];
        }
        return sum / rawResults.length;
    }

    private float calculateRange() {
        if (rawResults.length == 0) {
            return 0;
        } else {
            float max = rawResults[0];
            float min = rawResults[0];
            for (final float i : rawResults) {
                if (i > max) {
                    max = i;
                } else if (i < min) {
                    min = i;
                }
            }
            return (max - min) + 1;
        }
    }

    private float calculateMinResult() {
        float min = rawResults[0];
        for (int i = 1; i < rawResults.length; i++) {
            if (rawResults[i] < min) {
                min = rawResults[i];
            }
        }
        return min;
    }

    public void printRawResults() {
        System.out.println("=== " + label + " ===");
        for (int i = 0; i < rawResults.length; i++) {
            System.out.println(rawResults[i]);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("=== " + label + " Results at " + sampleSize + " samples ===\n");
        stringBuilder.append("Median: " + median + "\n");
        stringBuilder.append("Mean: " + mean + "\n");
        stringBuilder.append("Range: " + range + "\n");
//        stringBuilder.append("Standard Deviation: " + standardDeviation + "\n");
        stringBuilder.append("Min: " + min + "\n");
//        stringBuilder.append("Max: " + max + "\n");
        return stringBuilder.toString();
    }

    public String generateComparisonResults(TestResult testResult) {
        float diffMin = this.min - testResult.min;
        double diffMean = this.mean - testResult.mean;
        double diffMedian = this.median - testResult.median;
        float diffRange = this.range - testResult.range;

        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("=== " + label + "@" + this.sampleSize + " VS " + testResult.label + "@" + this.sampleSize + " Results ===\n");
        stringBuilder.append("Median: " + diffMedian + "\n");
        stringBuilder.append("Mean: " + diffMean + "\n");
        stringBuilder.append("Range: " + diffRange + "\n");
        stringBuilder.append("Min: " + diffMin + "\n");
        return stringBuilder.toString();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public long[] getRawResults() {
        return rawResults;
    }

    public void setRawResults(long[] rawResults) {
        this.rawResults = rawResults;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }
}
