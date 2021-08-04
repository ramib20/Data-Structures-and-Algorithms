import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @userid rbouhafs3
 * @GTID 903591700
 *
 * Collaborators: General help/tutoring of sorting algorithms by an upperclassman not in the class, Zach Minoh
 *
 * Resources: none
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The given array and/or comparator is null");
        } else {
            for (int i = 1; i < arr.length; i++) {
                for (int j = i; j > 0; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) >= 0) {
                        break;
                    }
                    T dummy = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = dummy;
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The given array and/or comparator is null");
        } else {
            if (arr.length <= 1) {
                return;
            } else {
                T[] leftArr = (T[]) new Object[arr.length / 2];
                T[] rightArr = (T[]) new Object[arr.length - leftArr.length];
                int copy = 0;
                for (int i = 0; i < leftArr.length; i++, copy++) {
                    leftArr[i] = arr[copy];
                }
                for (int i = 0; i < rightArr.length; i++, copy++) {
                    rightArr[i] = arr[copy];
                }
                mergeSort(leftArr, comparator);
                mergeSort(rightArr, comparator);
                int i = 0;
                int j = 0;
                while (i < leftArr.length && j < rightArr.length) {
                    if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                        arr[i + j] = leftArr[i];
                        i++;
                    } else {
                        arr[i + j] = rightArr[j];
                        j++;
                    }
                }
                while (i < leftArr.length) {
                    arr[i + j] = leftArr[i];
                    i++;
                }
                while (j < rightArr.length) {
                    arr[i + j] = rightArr[j];
                    j++;
                }
            }
        }
    }

    /**
    * Implement LSD (least significant digit) radix sort.
    *
    * Make sure you code the algorithm as you have been taught it in class.
    * There are several versions of this algorithm and you may not get full
    * credit if you do not implement the one we have taught you!
    *
    * Remember you CANNOT convert the ints to strings at any point in your
    * code! Doing so may result in a 0 for the implementation.
    *
    * It should be:
    * out-of-place
    * stable
    * not adaptive
    *
    * Have a worst case running time of:
    * O(kn)
    *
    * And a best case running time of:
    * O(kn)
    *
    * You are allowed to make an initial O(n) passthrough of the array to
    * determine the number of iterations you need.
    *
    * At no point should you find yourself needing a way to exponentiate a
    * number; any such method would be non-O(1). Think about how how you can
    * get each power of BASE naturally and efficiently as the algorithm
    * progresses through each digit.
    *
    * Refer to the PDF for more information on LSD Radix Sort.
    *
    * You may use ArrayList or LinkedList if you wish, but it may only be
    * used inside radix sort and any radix sort helpers. Do NOT use these
    * classes with other sorts. However, be sure the List implementation you
    * choose allows for stability while being as efficient as possible.
    *
    * Do NOT use anything from the Math class except Math.abs().
    *
    * @param arr the array to be sorted
    * @throws java.lang.IllegalArgumentException if the array is null
    */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The given array was null");
        } else {
            if (arr.length <= 1) {
                return;
            } else {
                Queue<Integer>[] copyQueue =  new Queue[19];
                for (int i = 0; i < 19; i++) {
                    copyQueue[i] = new LinkedList<>();
                }
                int firstI = arr[0];
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == Integer.MIN_VALUE) {
                        firstI = arr[i];
                        break;
                    }
                    int elem = Math.abs(arr[i]);
                    if (elem > firstI) {
                        firstI = elem;
                    }
                }
                int iterNum = 0;
                while (firstI != 0) {
                    firstI /= 10;
                    iterNum++;
                }
                int divisor = 1;
                for (int iter = 0; iter < iterNum; iter++) {
                    for (int i = 0; i < arr.length; i++) {
                        int num = ((arr[i] / (divisor) % 10));
                        copyQueue[num + 9].add(arr[i]);
                    }
                    int idx = 0;
                    for (Queue<Integer> each : copyQueue) {
                        while (!each.isEmpty()) { //checks for null
                            arr[idx++] = each.remove();
                        }
                    }
                    divisor *= 10;
                }
            }
        }

    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivot = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivot = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, Random rand) {
        if ((k < 1) || (k > arr.length)) {
            throw new IllegalArgumentException("The given int k was out of the range of (1, arr.length)");
        }
        if (arr == null) {
            throw new IllegalArgumentException("The given array was null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The given comparator was null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("The given Random object was null");
        } else {
            T smallestElement = kthHelper(k, arr, comparator, rand, 0, (arr.length - 1));
            return smallestElement;
        }
    }

    /**
     * private recursive helper method for kthSelect method above
     * @param <T> generic data type
     * @param k k
     * @param arr the modified array
     * @param comparator comparator
     * @param rand rand
     * @param start start
     * @param end end
     * @return return
     *
     */
    private static <T> T kthHelper(int k, T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (start < end) {
            int pivot = rand.nextInt(end - start + 1) + start;
            T dummy = arr[start]; //swapping with dummy
            arr[start] = arr[pivot];
            arr[pivot] = dummy;
            int aqui = (start + 1);
            int aver = end;
            while (aqui <= aver) {
                while (aqui <= aver && comparator.compare(arr[aqui], arr[start]) < 0) {
                    aqui++;
                }
                while (aver >= aqui && comparator.compare(arr[aver], arr[start]) > 0) {
                    aver--;
                }

                if (aqui <= aver) {
                    dummy = arr[aqui];
                    arr[aqui] = arr[aver];
                    arr[aver] = dummy;
                    aqui++;
                    aver--;
                }
            }
            dummy = arr[start]; //swapping again
            arr[start] = arr[aver];
            arr[aver] = dummy;

            int target = (k - 1);
            if (aver > target) {
                return kthHelper(k, arr, comparator, rand, start, aver - 1);
            } else if (aver < target) {
                return kthHelper(k, arr, comparator, rand, aver + 1, end);
            } else {
                dummy = arr[target];
                return dummy;
            }
        } else { //if (start > end)
            return arr[k - 1];
        }
    }
}