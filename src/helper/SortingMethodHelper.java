package helper;

import sortingMethods.SortStrategy;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SortingMethodHelper {

    private static SortingMethodHelper instance;
    private static ArrayList<String> sortingMethods = new ArrayList<>();

    private SortingMethodHelper() { }

    public static SortingMethodHelper getInstance() {

        if(instance == null) {
            instance = new SortingMethodHelper();
            populateClassList();
        }

        return instance;
    }

    public static SortStrategy convertStringToClass(String sortMethodClassName) throws Exception {
        Class<?> clazz = Class.forName("sortingMethods.common." + sortMethodClassName);
        Constructor<?> ctor = clazz.getConstructor();
        Object object = ctor.newInstance();
        try {
            return (SortStrategy) object;
        } catch (Exception e) {
            throw new Exception("Could not cast SortStrategy found in common or custom, insure all classes implement SortStrategy interface");
        }
    }

    private static void populateClassList() {
        try {
            sortingMethods.addAll(ClassFinder.getClassNamesFromPackage("sortingMethods.common"));
            sortingMethods.addAll(ClassFinder.getClassNamesFromPackage("sortingMethods.custom"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<String> getClassList() {
        return sortingMethods;
    }
}
