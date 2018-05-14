import javax.management.monitor.Monitor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Artur on 23.05.2017.
 */
public class Lab910 {
    private static RouteMonitor AY = new RouteMonitor(100,50,"AY");
    private static RouteMonitor YB = new RouteMonitor(50,10,"YB");
    private static RouteMonitor AX = new RouteMonitor(50,10,"AX");
    private static RouteMonitor XY = new RouteMonitor(50,30,"XY");
    private static RouteMonitor XB = new RouteMonitor(100,50,"XB");
    private static Statistics stats = new Statistics();
    private static int index=0;
    private static ArrayList<Thread> threadList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        runThreads(500);
        runThreads(1500);
        runThreads(500);
        runThreads(2000);
        runThreads(500);
        runThreads(1000);
        for(Thread t:threadList)
            t.join();
        System.out.println(stats.getAvgTime());
    }

    public static RouteMonitor selectRoute(RouteMonitor prev)
    {

        if(prev==null)
        {
            long avg = AX.getAvgTime();
            if(avg<AY.getAvgTime())
                return AX;
            else
                return AY;
        }
        else
        {
            switch(prev.getName())
            {
                case "AX":
                    long avg = XY.getAvgTime();
                    if(avg<XB.getAvgTime())
                        return XY;
                    else
                        return XB;
                case "XY":return YB;
                default: return YB; //AY
            }
        }

    }

    public static void runThreads(int n)
    {
        for(int i=0;i<n;i++)
        {
            RouteThread routeThread = new RouteThread(index,stats);
            routeThread.addToTrace(selectRoute(null));
            routeThread.addToTrace(selectRoute(routeThread.getTrace().get(0)));
            if(routeThread.getTrace().get(0).getName().equals("XY"))
                routeThread.addToTrace(selectRoute(routeThread.getTrace().get(1)));
            Thread t = new Thread(routeThread);
            t.start();
            threadList.add(t);
            index++;
        }
    }

}
