package sort_algorith.serial_sort;

import java.util.Arrays;

import sort_algorith.SortAlgo;

public class CountingSort implements SortAlgo{
        // Função que realiza o Counting Sort

    
    
    private static void countingSort(int[] array) {
        // Encontrar o valor máximo no array
        int max = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);
        int min = Arrays.stream(array).min().orElse(Integer.MIN_VALUE);

        // Definindo o tamanho do array de contagem
        int range = max - min + 1; // Intervalo total de valores
        int[] count = new int[range]; // Array de contagem

        // Preencher o array de contagem
        for (int num : array) {
            count[num - min]++;
        }

        // Construindo o array ordenado
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                array[index++] = i + min; // Ajusta o valor para a posição correta
                count[i]--;
            }
        }
    }

    @Override
    public int[] sort(int[] array) {
        countingSort(array);
        return array;
    }
}