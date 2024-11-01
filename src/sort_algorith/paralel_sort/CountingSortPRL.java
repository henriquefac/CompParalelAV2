package sort_algorith.paralel_sort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import sort_algorith.SortAlgoPRL;

public class CountingSortPRL extends RecursiveAction implements SortAlgoPRL {
    ForkJoinPool pool;


    private int[] array;
    private int[] count;
    private int min;
    private int max;
    private int start;
    private int end;

    // Construtor principal
    public CountingSortPRL(int[] array, ForkJoinPool pool) {
        this.pool = pool != null ? pool : new ForkJoinPool();
    }
    public CountingSortPRL(int[] array, int[] count, int min, int max, int start, int end) {
        this.array = array;
        this.count = count;
        this.min = min;
        this.max = max;
        this.start = start;
        this.end = end;
    }
    @Override
    protected void compute() {
        if (end - start <= 1000) { // Limite para a divisão do trabalho
            for (int i = start; i < end; i++) {
                count[array[i] - min]++;
            }
        } else {
            int mid = (start + end) / 2;
            CountingSortPRL leftTask = new CountingSortPRL(array, count, min, max, start, mid);
            CountingSortPRL rightTask = new CountingSortPRL(array, count, min, max, mid, end);
            invokeAll(leftTask, rightTask);
        }
    }
    @Override
    public int[] sort(int[] array) {
        final int max = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);
        final int min = Arrays.stream(array).min().orElse(Integer.MIN_VALUE);
        final int range = max - min + 1;
        final int[] count = new int[range];

        // Executa a tarefa de contagem paralela
        pool = new ForkJoinPool(); // Inicia o pool caso não esteja configurado
        pool.invoke(new CountingSortPRL(array, count, min, max, 0, array.length));

        // Constrói o array ordenado a partir do array de contagem
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                array[index++] = i + min;
                count[i]--;
            }
        }
        return array;
    }
    @Override
    public void setPool(ForkJoinPool pool) {
        this.pool = pool;
    }


    
}