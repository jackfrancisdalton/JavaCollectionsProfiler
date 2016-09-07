package sortingMethods.common;

import sortingMethods.SortStrategy;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
public class SelectionSort<T extends Comparable> implements SortStrategy<T> {
    @Override
    public void sortList(List<T> list) {
        // just return if the array list is null
        if (list == null)
            return;

        // just return if the array list is empty or only has a single element
        if (list.size() == 0 || list.size() == 1)
            return;

        // declare an int variable to hold value of index at which the element
        // has the smallest value
        int smallestIndex;

        // declare an int variable to hold the smallest value for each iteration
        // of the outer loop
        T smallest;

        // for each index in the array list
        for (int curIndex = 0; curIndex < list.size(); curIndex++) {

			/* find the index at which the element has smallest value */
            // initialize variables
            smallest = list.get(curIndex);
            smallestIndex = curIndex;

            for (int i = curIndex + 1; i < list.size(); i++) {
                if (list.get(i).compareTo(smallest) == -1) {
                    // update smallest
                    smallest = list.get(i);
                    smallestIndex = i;
                }
            }

			/* swap the value */
            // do nothing if the curIndex has the smallest value
            if (smallestIndex == curIndex)
                ;
                // swap values otherwise
            else {
                T temp = list.get(curIndex);
                list.set(curIndex, list.get(smallestIndex));
                list.set(smallestIndex, temp);
            }

        }
    }

    @Override
    public void sortQueue(Queue queue) {

    }

    @Override
    public void sortSet(Set set) {

    }
}
