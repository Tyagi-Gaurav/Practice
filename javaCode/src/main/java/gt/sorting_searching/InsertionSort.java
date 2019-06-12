package gt.sorting_searching;

public class InsertionSort<T extends Comparable> {
    public T[] sort(T[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            int smallest = i;

            for (int j = i+1; j < array.length; j++) {
                if (array[smallest].compareTo(array[j]) == 1) {
                    smallest = j;
                }
            }

            if (i != smallest) {
                T temp = array[i];
                array[i] = array[smallest];
                array[smallest] = temp;
            }
        }

        return array;
    }
}
