package camelride.multicast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class ParallelMultiCastExecutor {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);
}
