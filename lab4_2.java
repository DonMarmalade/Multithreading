package lab4_expe;

import java.util.*;
import java.util.concurrent.*;

public class lab4_2 {
    static List<Object> arr = Collections.synchronizedList(new ArrayList<>());
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(6); // totul lucreaza pe un thread
    public static void main(String[] args) {

        ScheduledFuture<?> future1 = scheduler.scheduleWithFixedDelay(Fish1(), 0, 5, TimeUnit.SECONDS); // Pentru Runnable
        ScheduledFuture<?> future2 = scheduler.scheduleWithFixedDelay(Fish2(), 0, 5, TimeUnit.SECONDS); // Pentru Thread
        ScheduledFuture<?> future3 = scheduler.scheduleWithFixedDelay(() -> Fish3(), 0, 5, TimeUnit.SECONDS); // Pentru Future Callable
        ScheduledFuture<?> future4 = scheduler.scheduleWithFixedDelay(Fish4(), 0, 5, TimeUnit.SECONDS); // Pentru FutureClass Callable

        scheduler.schedule(new Runnable() { // A opri programa cu tot
            public void run() {
                future1.cancel(true);
                future2.cancel(true);
                // future3.cancel(true);
                future4.cancel(true);
                try {
                    scheduler.awaitTermination(5000L, TimeUnit.MILLISECONDS);
                    scheduler.shutdown();
                    System.out.println(arr.size());
                } catch(InterruptedException e) {}
            }
        }, 20, TimeUnit.SECONDS);

    }

    private static Runnable Fish1() {
        return new Runnable() {
            public void run() {
                synchronized (arr) {
                    arr.add(new Fish());
                    System.out.println("List nr." + arr.size() + ": Runnable Fish, " + Thread.currentThread().getName());
                }
            }
        };
    }

    private static Thread Fish2() {
        return new Thread() {
            public void run() {
                synchronized (arr) {
                    arr.add(new Fish());
                    System.out.println("List nr." + arr.size() + ": Thread Fish, " + Thread.currentThread().getName());
                }
            }
        };
    }

    private static void Fish3() {
        Future future = scheduler.submit( 
            new Callable<String>() {
                public String call() throws Exception {
                    synchronized (arr) {
                        arr.add(new Fish());
                        System.out.println("List nr." + arr.size() + ": Future Callable Fish, " + Thread.currentThread().getName());
                    }
                String msg = "List nr." + arr.size() + ": Future Callable Fish, " + Thread.currentThread().getName();
                return msg;
            }
        });

        try {
            future.get();
        } catch( InterruptedException | ExecutionException e) {};
        future.cancel(true);
    }

    private static FutureTask<String> Fish4() {
        FutureTask<String> future = new FutureTask<>(
            new Callable<String>() {
                public String call() throws Exception {
                    synchronized (arr) {
                        arr.add(new Fish());
                        System.out.println("List nr." + arr.size() + ": FutureTask Callable Fish, " + Thread.currentThread().getName());
                    }
                String msg = "List nr." + arr.size() + ": FutureTask Callable Fish, " + Thread.currentThread().getName();
                return msg;
            }
        });

        scheduler.submit(future);

        try {
            future.get();
        } catch( InterruptedException | ExecutionException e) {};
        return future;
    }
}
