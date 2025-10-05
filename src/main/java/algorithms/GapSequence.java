package algorithms;

/**
 * Defines the available gap sequences for the Shell Sort algorithm,
 * as required by the assignment.
 */
public enum GapSequence {
    SHELL,     // Original Shell sequence (N/2, N/4, ...)
    KNUTH,     // Knuth's sequence (h = (3^k - 1) / 2)
    SEDGEWICK  // Sedgewick's sequence (h = 4^k + 3 * 2^(k-1) + 1)
}