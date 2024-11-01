package sort_algorith.serial_sort;

import sort_algorith.SortAlgo;

public class MergeSort implements SortAlgo {
    
    public int[] sort(int[] numeros) {
        mergeSort(numeros, 0, numeros.length - 1);
        return numeros;
    }

    private void mergeSort(int[] numeros, int esquerda, int direita){
        if (esquerda < direita){
            int meio = (esquerda + direita)/2;


            mergeSort(numeros, esquerda, meio);
            mergeSort(numeros, meio + 1, direita);

            merge(numeros, esquerda, meio, direita);
        }
    }
    

    // fazer o merge

    private void merge(int[] numeros,int esquerda,int meio, int direita){
        // comparar o ultimo elemento da lista esuqerrda com o primeiro elemento da lista da direita
        if (numeros[meio] <= numeros[meio + 1]) {
            return;  // Já está ordenado, não precisa fazer nada
        }
        
        // deve organizar do menor para o maior
        
        // tamanho do  vetor resultante
        int tamanho = direita - esquerda + 1;

        int[] tempArray = new int[tamanho];
        
        // navegação
        int i = esquerda;
        int j = meio + 1;

        // array temporario
        int k = 0;

        while (i <= meio && j<= direita ) {
            if (numeros[i] < numeros[j]){
                tempArray[k] = numeros[i];
                i ++;
            }else{
                tempArray[k] = numeros[j];
                j++;
            }
            k++;
        }

        while (i <= meio){
            tempArray[k] = numeros[i];
            i++;
            k ++;
        }

        
        while (j <= direita){
            tempArray[k] = numeros[j];
            j++;
            k++;
        }

        System.arraycopy(tempArray, 0, numeros, esquerda, tamanho);
    }
}