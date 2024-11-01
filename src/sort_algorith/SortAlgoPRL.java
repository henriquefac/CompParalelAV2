package sort_algorith;

import java.util.concurrent.ForkJoinPool;

public interface SortAlgoPRL{
    int[] sort(int[] array);
    void setPool(ForkJoinPool pool);
    
}