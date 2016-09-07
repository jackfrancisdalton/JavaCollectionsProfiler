package helper;

import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 * Created by Jack F. Dalton on 0031 31 08 2016.
 */
public class TestResult {
    private String label;
    private int sampleSize;
    private long[] rawResults;
    private double[] adjustedResults;
    private double median;
    private double mean;
    private double range;
    private double min;

    //TODO: switch this to a parent test results class
    private ChronoUnit timeUnit;
    private String yAxisLabel;

    public TestResult(String label, long[] rawResults, int sampleSize, ChronoUnit timeUnit) {
        this.label = label;
        this.sampleSize = sampleSize;
        this.rawResults = rawResults;
        this.adjustedResults = new double[rawResults.length];
        this.timeUnit = timeUnit;
        this.calculateStatistics();
    }


    private void convertResults() {

        //Assumes millis are given
        double divisor = 1000;

        switch (timeUnit) {
            case MILLIS:
                yAxisLabel = "Milliseconds";
                break;
            case SECONDS:
                yAxisLabel = "Seconds";
                divisor = divisor * 100;
                break;
            case MINUTES:
                yAxisLabel = "Minutes";
                divisor = divisor * 600;
                break;
            default: throw new UnsupportedTemporalTypeException("TestResults time unit is not supported");
        }

        for (int i = 0; i < rawResults.length; i++) {
            adjustedResults[i] = (double) rawResults[i] / divisor;
        }
    }

    private void calculateStatistics() {
        convertResults();
        this.median = calculateMedian();
        this.mean = calculateMean();
        this.range = calculateRange();
        this.min = calculateMinResult();
    }

    private double calculateMedian() {
        int middle = adjustedResults.length/2;
        if (adjustedResults.length%2 == 1) {
            return adjustedResults[middle];
        } else {
            return (adjustedResults[middle-1] + adjustedResults[middle]) / 2.0;
        }
    }

    private double calculateMean() {
        float sum = 0;
        for (int i = 0; i < adjustedResults.length; i++) {
            sum += adjustedResults[i];
        }
        return sum / adjustedResults.length;
    }

    private double calculateRange() {
        if (adjustedResults.length == 0) {
            return 0;
        } else {
            double max = adjustedResults[0];
            double min = adjustedResults[0];
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

    private double calculateMinResult() {
        double min = adjustedResults[0];
        for (int i = 1; i < adjustedResults.length; i++) {
            if (adjustedResults[i] < min) {
                min = adjustedResults[i];
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
        double diffMin = this.min - testResult.min;
        double diffMean = this.mean - testResult.mean;
        double diffMedian = this.median - testResult.median;
        double diffRange = this.range - testResult.range;

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

    public double getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public double getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public ChronoUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(ChronoUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    public void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }
}
