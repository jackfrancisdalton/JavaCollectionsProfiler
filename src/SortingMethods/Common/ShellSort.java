package sortingMethods.common;

import sortingMethods.SortStrategy;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
public class ShellSort<T extends Comparable> implements SortStrategy<T> {
    @Override
    public void sortList(List<T> list) {

        for( int gap = list.size() / 2; gap > 0; gap = gap == 2 ? 1 : (int) ( gap / 2.2 ) )
            for( int i = gap; i < list.size(); i++ ) {
                T tmp = list.get(i);
                int j = i;

                for( ; j >= gap && tmp.compareTo(list.get(j-gap)) < 0; j -= gap ) {
                    list.set(j, list.get(j - gap));
                    list.set(j, tmp);
                }

            }
    }

    @Override
    public void sortQueue(Queue queue) {
        throw new UnsupportedTemporalTypeException("unsupported");
    }

    @Override
    public void sortSet(Set set) {
        throw new UnsupportedTemporalTypeException("unsupported");
    }
}
