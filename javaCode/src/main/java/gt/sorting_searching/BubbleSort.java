package gt.sorting_searching;

public class BubbleSort<T extends Comparable> {
    public T[] sort(T[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[j].compareTo(array[i]) == -1) {
                    T temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

        return array;
    }
}
