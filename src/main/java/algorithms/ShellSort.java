package algorithms;

import metrics.PerformanceTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShellSort {

    /**
     * Generates the list of gap values (h) for the given array size and sequence type.
     * The list is generated in ascending order and then reversed to be used from largest to smallest.
     * * @param n The size of the array.
     * @param sequence The type of gap sequence to use (Shell, Knuth, or Sedgewick).
     * @return A list of gap values in descending order.
     */
    private List<Integer> getGaps(int n, GapSequence sequence) {
        List<Integer> gaps = new ArrayList<>();

        // 1. Generate gaps based on the chosen sequence type
        switch (sequence) {
            case KNUTH:
                int hKnuth = 1;
                while (hKnuth < n) {
                    gaps.add(hKnuth);
                    hKnuth = 3 * hKnuth + 1; // Knuth's formula: 3h + 1
                }
                break;

            case SEDGEWICK:
                int k = 1;
                int hSedgewick;
                do {
                    // Sedgewick's formula: 4^k + 3 * 2^(k-1) + 1
                    hSedgewick = (int) (Math.pow(4, k) + 3 * Math.pow(2, k - 1) + 1);
                    if (hSedgewick < n) {
                        gaps.add(hSedgewick);
                    }
                    k++;
                } while (hSedgewick < n);
                break;

            case SHELL:
            default:
                int hShell = n / 2;
                while (hShell > 0) {
                    gaps.add(hShell);
                    hShell /= 2;
                }
                break;
        }

        // 2. Prepare the gaps for sorting
        // Gaps must be sorted in descending order for the outer sorting loop.
        Collections.reverse(gaps);

        // Ensure the smallest gap is 1, a requirement for any proper sorting algorithm.
        if (gaps.isEmpty() || gaps.get(gaps.size() - 1) != 1) {
            gaps.add(1);
        }

        return gaps;
    }

    /**
     * Sorts the array using the Shell Sort algorithm with the specified gap sequence.
     * * @param arr The array to be sorted.
     * @param tracker The object used to collect performance metrics (comparisons, accesses).
     * @param sequence The gap sequence to use (Shell, Knuth, or Sedgewick).
     */
    public void sort(int[] arr, PerformanceTracker tracker, GapSequence sequence) {
        if (arr == null || arr.length < 2) return;

        tracker.start();
        int n = arr.length;
        List<Integer> gaps = getGaps(n, sequence);

        // Iterate through all gaps, from largest to smallest
        for (int h : gaps) {

            // h-Sort: Perform insertion sort with a step of 'h'
            for (int i = h; i < n; i++) {
                int temp = arr[i];
                tracker.incrementArrayAccesses(1); // Read arr[i] into temp
                int j = i;

                // Inner loop: Insertion sort pass to shift elements
                while (j >= h) {
                    tracker.incrementComparisons(1);

                    if (arr[j - h] > temp) {
                        // Shift operation: move larger element forward
                        arr[j] = arr[j - h];
                        j -= h;
                        tracker.incrementArrayAccesses(1); // Write (shift)
                    } else {
                        // Element is in correct position (relative to h-sort)
                        break;
                    }
                }
                arr[j] = temp;
                tracker.incrementArrayAccesses(1); // Write (place temp into final gap-position)
            }
        }
        tracker.stop();
    }
}