package hernando.com.customlog;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomLoggerMultiThreadedTest {

    private CustomLogger customLogger;
    private static final int NUM_THREADS = 5;
    private static final int NUM_LOGS_PER_THREAD = 1000;

    @BeforeEach
    void setUp() {
        customLogger = CustomLogger.getInstance("MultiThreadTestLogger");
    }

    @Test
    @DisplayName("Test logging in a multi-threaded environment")
    void testMultiThreadedLogging() throws InterruptedException {
        // Create a latch to synchronize the start of all threads
        CountDownLatch startLatch = new CountDownLatch(1);

        // Create an array of worker threads
        Thread[] threads = new Thread[NUM_THREADS];
        LoggingWorker[] workers = new LoggingWorker[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            workers[i] = new LoggingWorker(i, startLatch);
            threads[i] = new Thread(workers[i]);
            threads[i].start();
        }

        // Release the latch to start all threads simultaneously
        startLatch.countDown();

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Check if any errors occurred in worker threads
        for (LoggingWorker worker : workers) {
            assertNull(worker.getError(), "Error occurred in a worker thread: " + worker.getError());
        }
    }

    private class LoggingWorker implements Runnable {
        private final int workerId;
        private final CountDownLatch startLatch;
        private Throwable error;

        LoggingWorker(int workerId, CountDownLatch startLatch) {
            this.workerId = workerId;
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                startLatch.await(); // Wait for the start signal

                for (int i = 0; i < NUM_LOGS_PER_THREAD; i++) {
                    customLogger.info("Thread " + workerId + " log message " + i);
                    customLogger.debug("Thread " + workerId + " debug message " + i);
                    customLogger.warn("Thread " + workerId + " warning message " + i);
                }
            } catch (Throwable e) {
                error = e;
            }
        }

        Throwable getError() {
            return error;
        }
    }
}
