package helper;

import chart.BarChart;
import collectionsImplementation.ListImplementation.ListProfiler;
import config.Function;
import config.Order;
import sortingMethods.SortStrategy;
import sortingMethods.common.BubbleSort;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack F. Dalton on 0031 31 08 2016.
 */
public class TestRunner {

    public TestRunner() {

    }

    public static void runTest(
            int runThroughCount,
            int minSampleSize,
            int maxSampleSize,
            int sampleSizeSteps,
            List<String> listToTest,
            Function function,
            Order order,
            SortStrategy sortStrategy,
            ChronoUnit timeUnit
    ) throws Exception {

        //TODO: change string to a generic when type choice is added
        ListProfiler<Integer> listProfiler = new ListProfiler<>(Integer.class);
        List<Class<? extends List>> listTypesToTest = new ArrayList<>();

        for (String implementationClassName: listToTest)
            if(implementationClassName.equals(LinkedList.class.getSimpleName()))
                listTypesToTest.add(LinkedList.class);
            else if(implementationClassName.equals(ArrayList.class.getSimpleName()))
                listTypesToTest.add(ArrayList.class);
            else if(implementationClassName.equals(Vector.class.getSimpleName()))
                listTypesToTest.add(Vector.class);
            else if(implementationClassName == CopyOnWriteArrayList.class.getSimpleName())
                listTypesToTest.add(CopyOnWriteArrayList.class);

        List<TestResult> testResults = new ArrayList<>();

        for (int sampleSize = minSampleSize; sampleSize < maxSampleSize; sampleSize += sampleSizeSteps)
            for (Class<? extends List> listImplementation: listTypesToTest) {
                TestResult linkedListTR = listProfiler.test(listImplementation, runThroughCount, sampleSize, function, order, sortStrategy, timeUnit);
                testResults.add(linkedListTR);
            }

        if(Function.SORT == function) {
            generateBarChart(
                    "Results",
                    sortStrategy.getClass().getSimpleName() + " Result",
                    "List Type",
                    testResults.get(0).getyAxisLabel(),
                    testResults
            );
        } else {
            generateBarChart(
                    "Results",
                    order.toString() + " " + function.toString() + " Result",
                    "List Type",
                    testResults.get(0).getyAxisLabel(),
                    testResults
            );
        }
    }


    private static void generateBarChart(String superTitle, String title, String xAxisLabel, String yAxisLabel, List<TestResult> testResults) {
        BarChart demo = new BarChart(superTitle, title, xAxisLabel, yAxisLabel, testResults);
        demo.pack();
        demo.setVisible(true);
    }
}
