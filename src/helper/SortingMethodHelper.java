package helper;

import sortingMethods.SortStrategy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
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

    private static void  populateClassList() {
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
