package cuong.run.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorUtils {
    public static Executor executor = Executors.newCachedThreadPool();
}
