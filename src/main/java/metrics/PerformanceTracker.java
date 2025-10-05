package metrics;

/**
 * Class for collecting and storing performance metrics for sorting algorithms.
 * Tracks comparisons, swaps, array accesses, and execution time.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long startTimeNanos = 0;
    private long endTimeNanos = 0;

    /**
     * Starts the timer and resets all counters before a new run.
     */
    public void start() {
        this.startTimeNanos = System.nanoTime();
        this.comparisons = 0;
        this.swaps = 0;
        this.arrayAccesses = 0;
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        this.endTimeNanos = System.nanoTime();
    }

    // --- Increment Methods ---

    public void incrementComparisons(int count) {
        this.comparisons += count;
    }

    /**
     * Increments the swap counter.
     * Each logical swap operation is counted as 4 array accesses (2 reads + 2 writes).
     */
    public void incrementSwaps(int count) {
        this.swaps += count;
        this.arrayAccesses += count * 4;
    }

    /**
     * Increments the counter for single array read/write operations (not full swaps).
     */
    public void incrementArrayAccesses(int count) {
        this.arrayAccesses += count;
    }

    // --- Getter Methods ---

    /**
     * @return The total number of comparisons performed.
     */
    public long getComparisons() {
        return comparisons;
    }

    /**
     * @return The total number of swap operations performed.
     */
    public long getSwaps() {
        return swaps;
    }

    /**
     * @return The total number of times an array element was read or written.
     */
    public long getArrayAccesses() {
        return arrayAccesses;
    }

    /**
     * @return The execution time in nanoseconds.
     */
    public long getExecutionTimeNanos() {
        if (endTimeNanos > startTimeNanos) {
            return endTimeNanos - startTimeNanos;
        }
        return 0;
    }

    /**
     * Returns a formatted string with all collected metrics.
     */
    @Override
    public String toString() {
        return String.format(
                "Time: %.4f ms, Comparisons: %d, Swaps: %d, Array Accesses: %d",
                getExecutionTimeNanos() / 1_000_000.0, comparisons, swaps, arrayAccesses
        );
    }
}