import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Artur on 24.05.2017.
 */
public class RouteMonitor {
    private int counter=0;
    private int capacity;
    private int sleepTime;
    private long avgTime;
    private String name;
    private CircleQueue<Long> times = new CircleQueue<>(100);

    private Runnable computingAvgInBackground = new Runnable() {
        @Override
        public void run() {
            if(times.getSize()!=0)
            while(true) {
                long sum = 0;
                for (Long time : times.getData())
                    sum += time;
                setAvgTime(sum / times.getSize());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public RouteMonitor(int capacity, int time, String name) {
        this.capacity = capacity;
        this.sleepTime = time;
        this.avgTime = time;
        this.name = name;
        (new Thread(computingAvgInBackground)).start();
    }

    public synchronized void inc()
    {
        while(counter>=capacity)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        counter++;
    }

    public synchronized void dec()
    {
        counter--;
        notify();
    }


    public int getSleepTime() {
        return sleepTime;
    }

    public String getName() {
        return name;
    }

    public synchronized long getAvgTime() {
        return avgTime;
    }

    public synchronized void setAvgTime(long avgTime) {
        times.add(avgTime);
        this.avgTime = avgTime;
    }
}
