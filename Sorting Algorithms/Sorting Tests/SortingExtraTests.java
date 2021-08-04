import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This is some extra tests for the sorting algorithms, they aren't much but they should check more edge cases.
 * 3 more weeks guys, good luck!
 * @author Menelik Gebremariam
 * @version 1.1
 */
public class SortingExtraTests {
    private static final int TIMEOUT = 300;
    private Integer[] unsorted;
    private Integer[] sorted;
    private int comparisons;

    @Before
    public void setUp() {
        // Unsorted List: [-629, 817, -720, 513, 376, -576, -243, -714, 386, -147, 861, -5190, -790, -217, -895, 120]

        //Sorted List: [-5190, -895, -790, -720, -714, -629, -576, -243, -217, -147, 120, 376, 386, 513, 817, 861]

        unsorted = new Integer[]{-629, 817, -720, 513, 376, -576, -243, -714, 386, -147, 861, -5190,
                -790, -217, -895, 120};
        sorted = new Integer[]{-5190, -895, -790, -720, -714, -629, -576, -243,
                -217, -147, 120, 376, 386, 513, 817, 861};
        comparisons = 0;
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort1() {
        Sorting.insertionSort(unsorted, (o1, o2) -> o1 - o2);
        assertArrayEquals(unsorted, sorted);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort2() {
        Integer[] unsorted2 = new Integer[]{46};
        Sorting.insertionSort(unsorted2, (o1, o2) -> {
            comparisons++;
            return o1 - o2;
        });
        assertArrayEquals(unsorted2, new Integer[]{46});
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort3() {
        Integer[] sortedCopy = new Integer[]{-5190, -895, -790, -720, -714, -629, -576, -243,
                -217, -147, 120, 376, 386, 513, 817, 861};
        Sorting.insertionSort(sortedCopy, (o1, o2) -> {
            comparisons++;
            return o1 - o2;
        });
        assertArrayEquals(sortedCopy, sorted);
        assertTrue(comparisons <= 16);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort1() {
        Sorting.mergeSort(unsorted, (o1, o2) -> o1 - o2);
        assertArrayEquals(unsorted, sorted);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort2() {
        Integer[] unsorted2 = new Integer[]{11};
        Sorting.mergeSort(unsorted2, (o1, o2) -> o1 - o2);
        assertArrayEquals(unsorted2, new Integer[]{11});
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort3() {
        Integer[] sortedCopy = new Integer[]{-5190, -895, -790, -720, -714, -629, -576, -243,
                -217, -147, 120, 376, 386, 513, 817, 861};
        Sorting.mergeSort(sortedCopy, (o1, o2) -> o1 - o2);
        assertArrayEquals(sortedCopy, sorted);
    }

    @Test(timeout = TIMEOUT)
    public void testLSDSort1() {
        int[] unsorted2 = new int[]{-629, 817, -720, 513, 376, -576, -243, -714, 386, -147, 861, -5190,
                -790, -217, -895, 120};
        int[] sorted2 = new int[]{-5190, -895, -790, -720, -714, -629, -576, -243,
                -217, -147, 120, 376, 386, 513, 817, 861};
        Sorting.lsdRadixSort(unsorted2);
        assertArrayEquals(unsorted2, sorted2);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect1() {
        Integer kth = Sorting.kthSelect(8, unsorted, (o1, o2) -> o1 - o2, new Random());
        assertEquals(kth, new Integer(-243));
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect2() {
        Integer[] unsorted2 = new Integer[]{66};
        Integer kth = Sorting.kthSelect(1, unsorted2, (o1, o2) -> o1 - o2, new Random());
        assertEquals(kth, new Integer(66));
    }
}