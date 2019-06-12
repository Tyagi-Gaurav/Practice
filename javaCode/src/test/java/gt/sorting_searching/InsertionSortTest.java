package gt.sorting_searching;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class InsertionSortTest {
    private InsertionSort<Integer> insertionSort = new InsertionSort<>();

    @Theory
    public void shouldSort(Integer[] intArray) {
        assertThat(insertionSort.sort(intArray)).isSorted();
    }

    @DataPoints
    public static Integer[][] points = {
      new Integer[] {8, 4, 1, 4, 6 ,7},
      new Integer[] {9, 8, 7, 6, 5, 4, 3, 2, 1},
      new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    };
}