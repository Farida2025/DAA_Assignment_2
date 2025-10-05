package cli;

import algorithms.ShellSort;
import algorithms.GapSequence;
import metrics.PerformanceTracker;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Command Line Interface (CLI) runner for conducting large-scale performance benchmarks
 * and exporting the results to a CSV file for empirical validation.
 */
public class BenchmarkRunner {

    // Input sizes required by the assignment (10^2 to 10^5)
    private static final int[] INPUT_SIZES = {100, 1000, 10000, 100000};
    private static final int REPETITIONS = 5; // Number of times to repeat each test for averaging

    public static void main(String[] args) {
        System.out.println("Starting Shell Sort Benchmarks for N=100 to N=100,000...");

        // Output file path, required to be in the docs/ folder
        String filename = "docs/shell_sort_benchmark_results.csv";

        try (FileWriter writer = new FileWriter(filename)) {
            // CSV Header: Time is recorded in nanoseconds (ns)
            writer.append("Size,Distribution,Sequence,Run,Time_ns,Comparisons,Swaps,ArrayAccesses\n");

            ShellSort sorter = new ShellSort();
            PerformanceTracker tracker = new PerformanceTracker();

            // Run benchmarks for all required input sizes
            for (int size : INPUT_SIZES) {
                testSize(size, sorter, tracker, writer);
            }

            System.out.println("Benchmarks completed successfully. Results saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to CSV file. Check if the 'docs' folder exists: " + e.getMessage());
        }
    }

    private static void testSize(int size, ShellSort sorter, PerformanceTracker tracker, FileWriter writer) throws IOException {
        System.out.println("  Testing N = " + size);

        // Generate template arrays for all required distributions
        int[] randomTemplate = generateRandomArray(size);
        int[] sortedTemplate = generateSortedArray(size);
        int[] reverseTemplate = generateReverseSortedArray(size);
        int[] nearlySortedTemplate = generateNearlySortedArray(size);

        // Run tests for all distributions against all sequences
        runTestSet(size, "Random", randomTemplate, sorter, tracker, writer);
        runTestSet(size, "Sorted", sortedTemplate, sorter, tracker, writer);
        runTestSet(size, "Reverse", reverseTemplate, sorter, tracker, writer);
        runTestSet(size, "NearlySorted", nearlySortedTemplate, sorter, tracker, writer);
    }

    private static void runTestSet(int size, String distribution, int[] template,
                                   ShellSort sorter, PerformanceTracker tracker,
                                   FileWriter writer) throws IOException {

        // Iterate through all gap sequences (SHELL, KNUTH, SEDGEWICK)
        for (GapSequence sequence : GapSequence.values()) {
            for (int run = 1; run <= REPETITIONS; run++) {
                // Copy the array to ensure the sort always starts from the same initial state
                int[] arr = Arrays.copyOf(template, template.length);

                // Execute sorting
                sorter.sort(arr, tracker, sequence);

                // Export metrics to CSV
                writer.append(String.format("%d,%s,%s,%d,%d,%d,%d,%d\n",
                        size, distribution, sequence.toString(), run,
                        tracker.getExecutionTimeNanos(),
                        tracker.getComparisons(),
                        tracker.getSwaps(),
                        tracker.getArrayAccesses()));
            }
        }
    }

    // --- Array Generation Methods (Input Distribution Tests) ---

    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(n * 10);
        }
        return arr;
    }

    private static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    private static int[] generateReverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    /**
     * Generates a nearly sorted array: starts sorted and performs 5% random swaps.
     */
    private static int[] generateNearlySortedArray(int n) {
        int[] arr = generateSortedArray(n);
        Random rand = new Random();
        int swapsCount = (int) (n * 0.05); // 5% random perturbations

        for (int i = 0; i < swapsCount; i++) {
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            // Swap operation
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }
}