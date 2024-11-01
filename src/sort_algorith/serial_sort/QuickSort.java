package sort_algorith.serial_sort;

import sort_algorith.SortAlgo;

public class QuickSort implements SortAlgo{

    // Método principal que ordena o array
    public int[] sort(int[] array) {
        quickSort(array, 0, array.length - 1);
        return array;
    }

    // Método recursivo para aplicar o QuickSort
    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Particiona o array e obtém o índice do pivô
            int pivotIndex = partition(array, low, high);

            // Ordena as duas metades
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    // Método para particionar o array
    private int partition(int[] array, int low, int high) {
        // Escolhe o último elemento como pivô
        int pivot = array[high];
        int i = (low - 1); // Índice do menor elemento

        for (int j = low; j < high; j++) {
            // Se o elemento atual for menor ou igual ao pivô
            if (array[j] <= pivot) {
                i++;

                // Troca array[i] com array[j]
                swap(array, i, j);
            }
        }

        // Troca array[i + 1] com array[high] (pivô)
        swap(array, i + 1, high);
        return i + 1; // Retorna o índice do pivô
    }

    // Método para trocar dois elementos no array
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Método principal para testar a implementação
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] array = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Array original:");
        printArray(array);

        quickSort.sort(array); // Ordena o array

        System.out.println("Array ordenado:");
        printArray(array);
    }

    // Método para imprimir o array
    private static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
