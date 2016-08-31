package Helper;

import Chart.BarChart;
import CollectionsImplementation.ListImplementation.ListProfiler;
import Config.Function;
import Config.Order;

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
            Order order
    ) throws Exception {

        ListProfiler<String> listProfiler = new ListProfiler<>(String.class);
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

        for (int sampleSize = minSampleSize; sampleSize < maxSampleSize; sampleSize += sampleSizeSteps) {
            for (Class<? extends List> listImplementation: listTypesToTest) {
                TestResult linkedListTR = listProfiler.test(listImplementation, runThroughCount, sampleSize, function, order);
                testResults.add(linkedListTR);
            }
        }

        generateBarChart(
                "Results",
                order.toString() + " " + function.toString() + " Result",
                "List Type",
                "Milliseconds",
                testResults
        );
    }


    private static void generateBarChart(String superTitle, String title, String xAxisLabel, String yAxisLabel, List<TestResult> testResults) {
        BarChart demo = new BarChart(superTitle, title, xAxisLabel, yAxisLabel, testResults);
        demo.pack();
        demo.setVisible(true);
    }
}
