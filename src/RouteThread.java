import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Artur on 24.05.2017.
 */
public class RouteThread implements Runnable {

    //private RouteMonitor monitor;
    private Statistics stats;
    private ArrayList<RouteMonitor> trace;
    private int index;

    public RouteThread(int index, Statistics stats) {
        this.index = index;
        trace = new ArrayList<>();
        this.stats = stats;
    }

    public void addToTrace(RouteMonitor monitor)
    {
        trace.add(monitor);
    }

    @Override
    public void run() {
        for (RouteMonitor monitor: trace ) {
            long start = System.currentTimeMillis();
            //System.out.println(index+" jedzie droga "+monitor.getName());
            monitor.inc();
            try {
                Thread.sleep(monitor.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.dec();
            //System.out.println("\t"+index+" zakonczyl jazde droga "+monitor.getName()+" "+(double)(System.currentTimeMillis()-start)/1000);
            monitor.setAvgTime(System.currentTimeMillis()-start);
            stats.addTime(System.currentTimeMillis()-start,index);
        }
    }

    public ArrayList<RouteMonitor> getTrace() {
        return trace;
    }
}
