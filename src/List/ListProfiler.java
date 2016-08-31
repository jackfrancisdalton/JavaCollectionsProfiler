package List;

import Helper.TestResult;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListProfiler<T> {
    private Class<T> type;
    private List<T> listUnderInspection;

    private LinkedList<T> linkedList;
    private ArrayList<T> arrayList;
    private Vector<T> vector;
    private CopyOnWriteArrayList copyOnWriteArrayList;

    public ListProfiler(Class<T> type) {
        this.linkedList = new LinkedList<>();
        this.arrayList = new ArrayList<>();
        this.vector = new Vector<>();
        this.copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.type = type;
    }

    public void generateValues(int size) {
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

    public TestResult test(Class<? extends List> listType, int numberOfRuns, int sampleSize) {

        if(linkedList.getClass() == listType)
            listUnderInspection = linkedList;
        else if(arrayList.getClass() == listType)
            listUnderInspection = arrayList;
        else if(vector.getClass() == listType)
            listUnderInspection = vector;
        else if(copyOnWriteArrayList.getClass() == listType)
            listUnderInspection = copyOnWriteArrayList;

//        return iterationTest(listType, numberOfRuns, sampleSize);
//        return insertionTest(listType, numberOfRuns, sampleSize, true);
        return deleteTest(listType, numberOfRuns, sampleSize, true);
    }

    public TestResult iterationTest(Class<?> listType, int numberOfRuns, int sampleSize) {
        long[] results = new long[numberOfRuns];

        generateValues(sampleSize);

        if(listUnderInspection != null) {
            for (int i = 0; i < numberOfRuns; i++) {
                long start = System.nanoTime();
                for (T instance: listUnderInspection) {}
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, listUnderInspection.size());
    }

    public TestResult deleteTest(Class<? extends List> listType, int numberOfRuns, int size, boolean randomInsertionPosition) {
        generateValues(size);
        Random random = new Random();
        long[] results = new long[numberOfRuns];
        List<T> listUnderInspectionCopy = copyToInstanceOfListType(listType, listUnderInspection);

        if(listUnderInspectionCopy != null) {
            for (int i = 0; i < numberOfRuns; i++) {

                listUnderInspectionCopy = copyToInstanceOfListType(listType, listUnderInspection);
                long start = System.nanoTime();

                if(randomInsertionPosition)
                    for (int j = 0; j < size; j++)
                        listUnderInspectionCopy.remove(random.nextInt(listUnderInspectionCopy.size()));
                else
                    for (int j = 0; j < size; j++)
                        listUnderInspectionCopy.remove(0);

                int length = listUnderInspectionCopy.size();
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, size);
    }

    public TestResult insertionTest(Class<? extends List> listType, int numberOfRuns, int size, boolean randomInsertionPosition) {
        resetListStates();
        Random random = new Random();
        long[] results = new long[numberOfRuns];

        if(listUnderInspection != null) {
            for (int i = 0; i < numberOfRuns; i++) {
                long start = System.nanoTime();

                if(randomInsertionPosition) {
                    for (int j = 0; j < size; j++) {
                        listUnderInspection.add(random.nextInt(listUnderInspection.size() + 1), generateTypeInstance());
                    }
                } else {
                    for (int j = 0; j < size; j++)
                        listUnderInspection.add(generateTypeInstance());
                }

                listUnderInspection.clear();
                long duration = System.nanoTime() - start;
                results[i] = duration;
            }
        }

        return new TestResult(listType.getSimpleName(), results, size);
    }

    public void randomInsertionTest(Class<?> t) {

    }

    public void sortingTest() {

    }
}
