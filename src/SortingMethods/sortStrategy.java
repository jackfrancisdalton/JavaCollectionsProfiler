package sortingMethods;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public interface SortStrategy<T extends Comparable> {
    void sortList(List<T> list);
    void sortQueue(Queue<T> queue);
    void sortSet(Set<T> set);
}
