package sortingMethods.common;

import sortingMethods.SortStrategy;

import java.util.*;

/**
 * Created by Jack F. Dalton on 0007 07 09 2016.
 */
public class QuickSort<T extends Comparable> implements SortStrategy<T> {
    @Override
    public void sortList(List<T> list) {
        quickSort(list);
    }

    private List<T> quickSort(List<T> input){

        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        T pivot = input.get(middle);

        List<T> less = new ArrayList<>();
        List<T> greater = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).compareTo(pivot) <= 0 ){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }

        return concatenate(quickSort(less), pivot, quickSort(greater));
    }

    private List<T> concatenate(List<T> less, T pivot, List<T> greater){

        List<T> list = new ArrayList<>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }

    @Override
    public void sortQueue(Queue<T> queue) {

    }

    @Override
    public void sortSet(Set<T> set) {

    }
}
