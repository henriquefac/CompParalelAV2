package sort_algorith.paralel_sort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import sort_algorith.SortAlgoPRL;

public class MergeSortPRL extends RecursiveTask<int[]> implements SortAlgoPRL{
    // array que vai ser ordenado
    private int[] array;
    private ForkJoinPool pool;

    public MergeSortPRL(int[] array, ForkJoinPool pool){
        this.array = array;
        this.pool = pool;
    }

    public void setPool(ForkJoinPool pool){
        this.pool = pool;
    }
    
    // Implementação do método sort da interface SortAlgo
    @Override
    public int[] sort(int[] array) {
        this.array = array;
        return pool.invoke(new MergeSortPRL(array, pool));
    }
    // (lista, indice esquerdo, indice direito)
    @Override
    protected int[] compute() {
        if (array.length <= 1) {
            return array;
        }
        // indice do meio da lista
        int mid = array.length/ 2;
        
        MergeSortPRL tarefa1 = new MergeSortPRL(java.util.Arrays.copyOfRange(array, 0, mid), pool);
        MergeSortPRL tarefa2 = new MergeSortPRL(java.util.Arrays.copyOfRange(array, mid, array.length), pool);

        tarefa1.fork();
        tarefa2.fork();

        int[] resultadoEsquerdo = tarefa1.join();
        int[] resultadoDireito = tarefa2.join();

        return  merge(resultadoEsquerdo, resultadoDireito);

    }

    // função de merge entre dois array separados
    private int[] merge(int[] esquerda, int[] direita){
        int[] resul = new int[esquerda.length + direita.length];
        int r = 0; int i = 0; int j = 0;
        while (i < esquerda.length && j < direita.length) {
            if (esquerda[i] < direita[j]){
                resul[r] = esquerda[i];
                i++;
                r++;
            }else{
                resul[r] = direita[j];
                j++;
                r++;
            }
        }
        while (i < esquerda.length){
            resul[r] = esquerda[i];
            i++;
            r++;
        }
        while (j < direita.length){
            resul[r] = direita[j];
            j++;
            r++;
        }
        return resul;
    }

}