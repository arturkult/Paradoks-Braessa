import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Artur on 25.05.2017.
 */
public class Statistics {
    private ArrayList<Long> times = new ArrayList(Collections.nCopies(6000,new Long(0)));

    public void addTime(long time, int index)
    {
        times.set(index,times.get(index)+time);
    }
    public long getAvgTime()
    {
        long sum = 0;
        for(long item: times)
            sum+=item;
        return sum/6000;

    }


}
