package collectionsImplementation.ListImplementation;

import config.Function;
import config.Order;
import helper.TestResult;
import sortingMethods.SortStrategy;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListProfiler<T extends Comparable> {
    private Class<T> type;
    private List<T> listUnderInspection;

    private LinkedList<T> linkedList;
    private ArrayList<T> arrayList;
    private Vector<T> vector;
    private CopyOnWriteArrayList copyOnWriteArrayList;

    private final ChronoUnit chronoUnit = ChronoUnit.MINUTES;

    public ListProfiler(Class<T> type) {
        this.linkedList = new LinkedList<>();
        this.arrayList = new ArrayList<>();
        this.vector = new Vector<>();
        this.copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.type = type;
    }

    public void generateValues(int size, Order order) {
        linkedList.clear();
        arrayList.clear();
        vector.clear();
        copyOnWriteArrayList.clear();

        for (int i = 0; i < size; i++) {
            T instance = generateTypeInstance();
            linkedList.add(instance);
            arrayList.add(instance);
            vector.add(instance);
            copyOnWriteArrayList.add(instance);
        }

        //TODO: implement a way of generating lists of specific ordering (this will also need to be added to the ui
    }

    private T generateTypeInstance() {
        if(type.isAssignableFrom(String.class)) {
            return (T) UUID.randomUUID().toString();
        } else if(type.isAssignableFrom(Integer.class)) {
            return (T) (Integer) new Random().nextInt(10000 - 0 + 1);
        }
        return null;
    }

    private List<T> copyToInstanceOfListType(Class<? extends List> listType, List<T> list) {
        List<T> convertedList = null;

        if(LinkedList.class == listType)
            convertedList = new LinkedList<>(list);
        else if(ArrayList.class == listType)
            convertedList = new ArrayList<>(list);
        else if(Vector.class == listType)
            convertedList = new Vector<>(list);
        else if(CopyOnWriteArrayList.class == listType)
            convertedList = new CopyOnWriteArrayList<>(list);

        return  convertedList;
    }

    private void resetListStates() {
        this.linkedList = new LinkedList<>();
        this.arrayList = new ArrayList<>();
        this.vector = new Vector<>();
        this.copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.type = type;
    }

    public TestResult test(Class<? extends List> listType, int numberOfRuns, int sampleSize, Function function, Order order, SortStrategy sortingStrategy, ChronoUnit timeUnit) throws Exception {

        //Load in the specified list to test
        if(linkedList.getClass() == listType)
            listUnderInspection = linkedList;
        else if(arrayList.getClass() == listType)
            listUnderInspection = arrayList;
        else if(vector.getClass() == listType)
            listUnderInspection = vector;
        else if(copyOnWriteArrayList.getClass() == listType)
            listUnderInspection = copyOnWriteArrayList;

        if(function == Function.INSERT)
            return addElementTest(listType, numberOfRuns, sampleSize, order, timeUnit);
        else if(function == Function.ITERATE)
            return iterationTest(listType, numberOfRuns, sampleSize, timeUnit);
        else if (function == Function.REMOVE)
            return removeElementTest(listType, numberOfRuns, sampleSize, order, timeUnit);
        else if (function == Function.SORT)
            return sortingTest(listType, numberOfRuns, sampleSize, sortingStrategy, timeUnit);
        else
            throw new Exception("invalid test function");
    }

    public TestResult sortingTest(Class<?> listType, int numberOfRuns, int sampleSize, SortStrategy<T> sortStrategy, ChronoUnit timeUnit) {
        long[] results = new long[numberOfRuns];
        generateValues(sampleSize, Order.RANDOM);

        if(listUnderInspection != null) {
            for (int i = 0; i < numberOfRuns; i++) {
                long start = System.nanoTime();
                sortStrategy.sortList(listUnderInspection);
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, listUnderInspection.size(), timeUnit);
    }

    public TestResult iterationTest(Class<?> listType, int numberOfRuns, int sampleSize, ChronoUnit timeUnit) {
        long[] results = new long[numberOfRuns];

        generateValues(sampleSize, Order.RANDOM);

        if(listUnderInspection != null) {
            for (int i = 0; i < numberOfRuns; i++) {
                long start = System.nanoTime();
                for (T instance: listUnderInspection) {}
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, listUnderInspection.size(), timeUnit);
    }

    public TestResult removeElementTest(Class<? extends List> listType, int numberOfRuns, int size, Order order, ChronoUnit timeUnit) {
        generateValues(size, Order.RANDOM);
        Random random = new Random();
        long[] results = new long[numberOfRuns];
        List<T> listUnderInspectionCopy = copyToInstanceOfListType(listType, listUnderInspection);

        if(listUnderInspectionCopy != null) {
            for (int i = 0; i < numberOfRuns; i++) {

                listUnderInspectionCopy = copyToInstanceOfListType(listType, listUnderInspection);
                long start = System.nanoTime();

                if(order == Order.RANDOM)
                    for (int j = 0; j < size; j++)
                        listUnderInspectionCopy.remove(random.nextInt(listUnderInspectionCopy.size()));
                else if (order == Order.REVERSE)
                    for (int j = 0; j < size; j++)
                        listUnderInspectionCopy.remove(listUnderInspectionCopy.size() - 1);
                else if (order == order.STANDARD)
                    for (int j = 0; j < size; j++)
                        listUnderInspectionCopy.remove(0);

                int length = listUnderInspectionCopy.size();
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, size, timeUnit);
    }

    public TestResult addElementTest(Class<? extends List> listType, int numberOfRuns, int size, Order order, ChronoUnit timeUnit) {
        resetListStates();
        Random random = new Random();
        long[] results = new long[numberOfRuns];

        if(listUnderInspection != null) {
            for (int i = 0; i < numberOfRuns; i++) {
                long start = System.nanoTime();

                if(order == Order.RANDOM)
                    for (int j = 0; j < size; j++)
                        listUnderInspection.add(random.nextInt(listUnderInspection.size() + 1), generateTypeInstance());
                else if (order == Order.STANDARD)
                    for (int j = 0; j < size; j++)
                        listUnderInspection.add(generateTypeInstance());
                else if (order == Order.REVERSE) {
                    for (int j = 0; j < size; j++) {
                        listUnderInspection.add(listUnderInspection.size(), generateTypeInstance());
                    }
                }

                listUnderInspection.clear();
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, size, timeUnit);
    }

    public void randomInsertionTest(Class<?> t) {

    }

    public void sortingTest() {

    }
}
