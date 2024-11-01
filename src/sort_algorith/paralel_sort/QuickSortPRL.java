package sort_algorith.paralel_sort;
import java.util.concurrent.RecursiveAction;

import sort_algorith.SortAlgoPRL;

import java.util.concurrent.ForkJoinPool;

public class QuickSortPRL implements SortAlgoPRL{
    
    // Tamanho mínimo para ordenar um array serialmente
    private static final int THRESHOLD = 1000;

    private ForkJoinPool pool;
    private int[] array;

    public QuickSortPRL(int[] array, ForkJoinPool pool){
        this.array = array;
        this.pool = pool;
    }
    public void setPool(ForkJoinPool pool){
        this.pool = pool;
    }
    // Método principal que inicia a ordenação
    public int[] sort(int[] array) {
        this.array = array;
        pool.invoke(new SortTask(array, 0, array.length - 1)); // Inicia a tarefa de ordenação
        return array;
    }

    // Classe interna para a tarefa de ordenação
    private static class SortTask extends RecursiveAction {
        private final int[] array;
        private final int low;
        private final int high;

        public SortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (high - low < THRESHOLD) {
                // Se o tamanho da subarray for menor que o limite, ordena serialmente
                quickSort(array, low, high);
            } else {
                // Caso contrário, divide a tarefa em duas
                int pivotIndex = partition(array, low, high);
                invokeAll(new SortTask(array, low, pivotIndex - 1), new SortTask(array, pivotIndex + 1, high));
            }
        }

        // Método para aplicar o QuickSort de forma serial
        private void quickSort(int[] array, int low, int high) {
            if (low < high) {
                int pivotIndex = partition(array, low, high);
                quickSort(array, low, pivotIndex - 1);
                quickSort(array, pivotIndex + 1, high);
            }
        }

        // Método para particionar o array
        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, i + 1, high);
            return i + 1;
        }

        // Método para trocar elementos
        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

}