package gt.sorting_searching;

public class SelectionSort<T extends Comparable> {
    public T[] sort(T[] array) {
        int current = 0;
        for (int i = 0; i < array.length; i++) {
            current = i;
            int minIndex = findMin(current, array);

            if (current != minIndex) {
                T temp = array[current];
                array[current] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        return array;
    }

    private int findMin(int i, T[] array) {
        int index = i;
        if (i < array.length) {
            T min = array[i];

            for (int j = i; j < array.length; j++) {
                if (min.compareTo(array[j]) == 1) {
                    index = j;
                    min = array[j];
                }
            }

        }

        return index;
    }
}
