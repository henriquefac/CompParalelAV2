package TestAlgorithm;

import sort_algorith.SortAlgo;
import sort_algorith.SortAlgoPRL;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Tester {
    // Lista para armazenar o tempo médio de execução para cada tamanho (paralelo) para cada quantidade de threads
    private double[][] listaTempoMedioPRL;
    // Lista para armazenar o tempo médio de execução para cada tamanho (serial)
    private double[] listaTempoMedioSerial;
    // Tamanhos de arrays a serem testados
    private int[] tamanhosLista;
    // Número máximo de threads
    private final int maxThreads = 8; // Pode ser ajustado conforme necessário

    // Construtor da classe Tester
    public Tester(SortAlgoPRL sortPRL, SortAlgo sortSerial, int[] tamanhosLista) {
        this.tamanhosLista = tamanhosLista;
        this.listaTempoMedioPRL = new double[tamanhosLista.length][5]; // 5 frações
        this.listaTempoMedioSerial = new double[tamanhosLista.length];

        realizarTestes(sortPRL, sortSerial);
    }

    // Método para realizar os testes
    private void realizarTestes(SortAlgoPRL sortPRL, SortAlgo sortSerial) {
        int numeroDeExecucoes = 10; // Número de execuções para média

        // Frações de threads a serem testadas
        double[] fracoesThreads = {0.2, 0.4, 0.6, 0.8, 1.0};

        for (int i = 0; i < tamanhosLista.length; i++) {
            int tamanho = tamanhosLista[i];

            // Teste da versão serial
            listaTempoMedioSerial[i] = testarSortSerial(sortSerial, tamanho, numeroDeExecucoes);

            // Teste das versões paralelas com diferentes frações de threads
            for (int j = 0; j < fracoesThreads.length; j++) {
                int threads = (int) (maxThreads * fracoesThreads[j]);
                listaTempoMedioPRL[i][j] = testarSortParalelo(sortPRL, tamanho, threads, numeroDeExecucoes);
            }
        }
    }

    // Método para testar a versão serial
    private double testarSortSerial(SortAlgo sortSerial, int tamanho, int numeroDeExecucoes) {
        long totalTempo = 0;

        for (int i = 0; i < numeroDeExecucoes; i++) {
            int[] array = gerarArrayAleatorio(tamanho);
            long inicio = System.nanoTime();
            sortSerial.sort(array); // Chama o método de ordenação da versão serial
            long fim = System.nanoTime();
            totalTempo += (fim - inicio);
        }

        return totalTempo / (double) numeroDeExecucoes / 1_000_000; // Tempo em milissegundos
    }

    // Método para testar a versão paralela
    private double testarSortParalelo(SortAlgoPRL sortPRL, int tamanho, int threads, int numeroDeExecucoes) {
        long totalTempo = 0;
        sortPRL.setPool(new ForkJoinPool(threads));
        for (int i = 0; i < numeroDeExecucoes; i++) {
            int[] array = gerarArrayAleatorio(tamanho);
            long inicio = System.nanoTime();
            sortPRL.sort(array); // Chama o método de ordenação da versão paralela
            long fim = System.nanoTime();
            totalTempo += (fim - inicio);
        }

        return totalTempo / (double) numeroDeExecucoes / 1_000_000; // Tempo em milissegundos
    }

    // Método para gerar um array aleatório
    private int[] gerarArrayAleatorio(int tamanho) {
        Random rand = new Random();
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = rand.nextInt(100000); // Gera números aleatórios entre 0 e 99.999
        }
        return array;
    }

    // Métodos para obter os resultados (opcional)
    public double[][] getListaTempoMedioPRL() {
        return listaTempoMedioPRL;
    }

    public double[] getListaTempoMedioSerial() {
        return listaTempoMedioSerial;
    }
}
