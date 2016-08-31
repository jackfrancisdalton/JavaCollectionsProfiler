import Chart.BarChart;
import Helper.TestResult;
import List.ListProfiler;
import List.Stratagies.ProfilingStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        ListProfiler<String> stringTester = new ListProfiler<String>(String.class);

        int runs = 10;
        int minSampleSize = 1000;
        int maxSampleSize = 10000;
        int sampleSizeSteps = 1000;

        List<TestResult> testResults = new ArrayList<>();

        for (int i = minSampleSize; i < maxSampleSize; i+= sampleSizeSteps) {
            TestResult linkedListTR = stringTester.test(LinkedList.class, runs, i);
            TestResult arrayListTR = stringTester.test(ArrayList.class, runs, i);
            TestResult vectorListTR = stringTester.test(Vector.class, runs, i);
//            TestResult copyOnWriteArrayListTR = stringTester.test(CopyOnWriteArrayList.class, runs, i);

            testResults.add(linkedListTR);
            testResults.add(arrayListTR);
            testResults.add(vectorListTR);
//            testResults.add(copyOnWriteArrayListTR);
        }

        generateBarChart("Deletion Result", "Delition Rate", "List Type", "Milliseconds", testResults);
    }

    private static void generateBarChart(String superTitle, String title, String xAxisLabel, String yAxisLabel, List<TestResult> testResults) {
        BarChart demo = new BarChart(superTitle, title, xAxisLabel, yAxisLabel, testResults);
        demo.pack();
        demo.setVisible(true);
    }
}
