package helper;

import config.Function;
import config.Order;

import java.util.List;

/**
 * Created by Jack F. Dalton on 0031 31 08 2016.
 */
public class TestRequest {
    private int runThroughCount;
    private int minSampleSize;
    private int maxSampleSize;
    private int sampleSizeSteps;
    private List<Class<? extends List>> listsToTest;
    private Order order;
    private Function function;

    public TestRequest(int runThroughCount, int minSampleSize, int maxSampleSize, int sampleSizeSteps, List<Class<? extends List>> listsToTest, Order order, Function function) {
        this.runThroughCount = runThroughCount;
        this.minSampleSize = minSampleSize;
        this.maxSampleSize = maxSampleSize;
        this.sampleSizeSteps = sampleSizeSteps;
        this.listsToTest = listsToTest;
        this.order = order;
        this.function = function;
    }
}
