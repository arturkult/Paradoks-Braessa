import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Artur on 26.05.2017.
 */
public class CircleQueue<T> implements Iterable{
    private int capacity;
    private int firstIndex;
    private int size;
    private ArrayList<T> data;

    public CircleQueue(int capacity) {

        this.capacity = capacity;
        data = new ArrayList<T>(capacity);
        firstIndex = 0;
        size=0;
    }

    public T get()
    {
        T ret = data.get(firstIndex);
        if(firstIndex+1 == capacity)
            firstIndex = 0;
        else
        {
            firstIndex++;
        }
        return  ret;
    }
    public void add(T item)
    {
        if(size<capacity)
        {
            data.add(size,item);
            size++;
        }
        else
        {
            data.add(firstIndex,item);
            if(firstIndex+1 == capacity)
                firstIndex = 0;
            else
            {
                firstIndex++;
            }
        }

    }
    public int getSize()
    {
        return size;
    }


    @Override
    public Iterator iterator() {
        return data.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        data.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return data.spliterator();
    }

    public ArrayList<T> getData() {
        return data;
    }
}
