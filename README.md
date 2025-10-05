# DAA Assignment 2: Shell Sort Implementation and Benchmarking

## Project Overview

This repository contains the implementation of the Shell Sort algorithm, complete with performance tracking and a comprehensive benchmarking suite, as required for the Design and Analysis of Algorithms (DAA) assignment.

The primary goal was to implement the Shell Sort algorithm using three different gap sequences and collect empirical data to analyze its time complexity and efficiency under various input conditions.

***

## Key Features Implemented

1.  **Shell Sort Algorithm:** Fully functional sorting algorithm.
2.  **Gap Sequences:** Support for the three specified gap sequences:
    * **SHELL:** Original sequence ($N/2, N/4, \dots$).
    * **KNUTH:** Knuth's $3h+1$ sequence.
    * **SEDGEWICK:** Sedgewick's sequence ($4^k + 3 \cdot 2^{k-1} + 1$).
3.  **Performance Tracking:** Integration of `PerformanceTracker.java` to measure execution time, comparisons, swaps, and array accesses.
4.  **Benchmarking Runner:** Command Line Interface (CLI) runner to perform large-scale testing across all required input sizes and distributions.

***

## Project Structure

The key files and directories are organized as follows:

| Path | Description |
| :--- | :--- |
| `src/main/java/algorithms/ShellSort.java` | Main Shell Sort implementation and gap sequence generation logic. |
| `src/main/java/algorithms/GapSequence.java` | Enumeration defining the three gap sequence types. |
| `src/main/java/cli/BenchmarkRunner.java` | The main executable class that runs all performance tests. |
| `src/main/java/metrics/PerformanceTracker.java` | Class for measuring and accumulating runtime metrics. |
| `src/test/java/algorithms/ShellSortTest.java` | Unit tests to verify the correctness of the sorting logic for all sequences. |
| **`docs/shell_sort_benchmark_results.csv`** | **The final empirical data collected from the benchmark runs.** |
| `pom.xml` | Maven configuration file managing project dependencies (JUnit 5). |

***

## How to Run the Benchmarks

This project is configured to run using IntelliJ IDEA and Maven.

1.  **Load the Project:** Open the project directory in IntelliJ IDEA.
2.  **Run Tests (Verification):** Right-click on `ShellSortTest.java` and select **Run 'ShellSortTest'** to confirm the algorithm's correctness.
3.  **Run Benchmark (Data Collection):**
    * Open `src/main/java/cli/BenchmarkRunner.java`.
    * Right-click within the file and select **Run 'BenchmarkRunner.main()'**.
    * The runner will execute $\mathbf{4 \times 4 \times 3 \times 5}$ tests and save the results to the CSV file.

Upon completion, the file `docs/shell_sort_benchmark_results.csv` will contain the full dataset required for the analytical report.
