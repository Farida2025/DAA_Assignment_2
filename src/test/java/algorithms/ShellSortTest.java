package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive set of unit tests to verify the correctness of ShellSort
 * for all three required gap sequences (Shell, Knuth, Sedgewick).
 */
class ShellSortTest {

    private final ShellSort sorter = new ShellSort();
    // Tracker is primarily used here to ensure that the sort method runs without errors
    // and produces non-zero metrics, confirming the code execution path.
    private final PerformanceTracker tracker = new PerformanceTracker();

    /**
     * Helper method to assert that an array is sorted in ascending order.
     */
    private void assertIsSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1], "Array is not sorted correctly. Element at index " + i + " is larger than the next one.");
        }
    }

    /**
     * Helper method: runs the correctness test for all three gap sequences
     * on copies of the input array.
     */
    private void runSortTestForAllGaps(int[] arr, int[] expected) {
        // Test 1: Knuth Sequence
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        sorter.sort(arr1, tracker, GapSequence.KNUTH);
        assertArrayEquals(expected, arr1, "Knuth sequence failed to sort correctly.");

        // Test 2: Shell Sequence
        int[] arr2 = Arrays.copyOf(arr, arr.length);
        sorter.sort(arr2, tracker, GapSequence.SHELL);
        assertArrayEquals(expected, arr2, "Shell sequence failed to sort correctly.");

        // Test 3: Sedgewick Sequence
        int[] arr3 = Arrays.copyOf(arr, arr.length);
        sorter.sort(arr3, tracker, GapSequence.SEDGEWICK);
        assertArrayEquals(expected, arr3, "Sedgewick sequence failed to sort correctly.");
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        // Only one sequence test is sufficient for edge cases like this
        sorter.sort(arr, tracker, GapSequence.KNUTH);
        assertEquals(0, arr.length, "Empty array must remain empty.");
    }

    @Test
    void testSingleElementArray() {
        int[] arr = {5};
        int[] expected = {5};
        runSortTestForAllGaps(arr, expected);
    }

    @Test
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        runSortTestForAllGaps(arr, expected);
    }

    @Test
    void testReverseSortedArray() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        runSortTestForAllGaps(arr, expected);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arr = {4, 2, 5, 4, 1, 2, 5, 1, 3};
        int[] expected = {1, 1, 2, 2, 3, 4, 4, 5, 5};
        runSortTestForAllGaps(arr, expected);
    }

    @Test
    void testArrayWithZerosAndNegatives() {
        int[] arr = {-5, 0, -10, 3, 0, 7};
        int[] expected = {-10, -5, 0, 0, 3, 7};
        runSortTestForAllGaps(arr, expected);
    }

    @Test
    void testRandomLargeArray() {
        int size = 1000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }

        // Test one sequence for correctness on a large, random array
        sorter.sort(arr, tracker, GapSequence.SEDGEWICK);
        assertIsSorted(arr);

        // Ensure metrics were recorded, validating the tracker integration
        assertTrue(tracker.getComparisons() > 0, "Comparisons should have been performed.");
    }
}