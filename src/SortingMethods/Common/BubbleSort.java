package sortingMethods.common;

import sortingMethods.SortStrategy;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
public class BubbleSort<T extends Comparable> implements SortStrategy<T>{

    //TODO: ideally make this generic so any abstract collection can be added in
//    @Override
//    public void sort(C collection) {
//
//        if(collection.getClass() == Set.class) {
//            setSort((Set)collection);
//        } else if (collection.getClass() == Queue.class) {
//            queueSort((Queue)collection);
//        } else if (collection.getClass() == List.class) {
//            listSort((List)collection);
//        } else {
//            throw new UnsupportedOperationException("Sorry the collection supplied is not supported");
//        }
//    }

    public void sortSet(Set<T> set) {
        throw new UnsupportedOperationException("Bubble Set sorting not supported");
    }

    public void sortQueue(Queue<T> Queue) {
        throw new UnsupportedOperationException("Bubble Queue sorting not supported");

    }

    public void sortList(List<T> list) {
        int i;
        boolean firstPassFlag = true;
        T holdingValue;

        while ( firstPassFlag )
        {
            firstPassFlag = false;
            for(i=0; i < list.size()-1; i++)
            {
                if(list.get(i).compareTo(list.get(i+1)) == 1)
                {
                    holdingValue = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1, holdingValue);
                    firstPassFlag = true;
                }
            }
        }
    }
}
