package sortingMethods.common;

import sortingMethods.SortStrategy;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
public class InsertionSort<T extends Comparable> implements SortStrategy<T>{
    @Override
    public void sortList(List<T> list) {
        int j;
        T key;
        int i;

        for (j = 1; j < list.size(); j++) {
            key = list.get(j);
            for(i = j - 1; (i >= 0) && (list.get(i).compareTo(key) == -1); i--)
                list.set(i+1, list.get(i));

            list.set(i+1, key);
        }
    }

    @Override
    public void sortQueue(Queue<T> queue) {

    }

    @Override
    public void sortSet(Set<T> set) {

    }
}
