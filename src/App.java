import java.util.Random;
import sort_algorith.paralel_sort.MergeSortPRL;
import sort_algorith.paralel_sort.QuickSortPRL;
import sort_algorith.serial_sort.MergeSort;
import sort_algorith.serial_sort.QuickSort;
import TestAlgorithm.Tester;

public class App {
    public static void main(String[] args) throws Exception {
        // Tamanhos dos arrays a serem testados
        System.out.println("iniciou!");
        int[] tamanhos = {1000, 10000, 100000, 1000000, 10000000}; // Ajuste conforme necessário

        QuickSort sortSerial = new QuickSort(); // Instância do algoritmo de ordenação serial
        QuickSortPRL sortPRL = new QuickSortPRL(null, null); // Instância do algoritmo paralelo
        System.out.println("Teste");
        // Criando o tester para executar os testes de desempenho
        Tester tester = new Tester(sortPRL, sortSerial, tamanhos);
        System.out.println("Fim teste");
        // Exibindo os resultados médios de tempo de execução
        for (int i = 0; i < tamanhos.length; i++) {
            System.out.println("Tamanho: " + tamanhos[i]);
            for (int j = 0; j < 5; j++) {
                System.out.printf("Tempo médio Paralelo (nivel %d): %.2f ms%n", j, tester.getListaTempoMedioPRL()[i][j]);
            }
            System.out.printf("Tempo Médio Serial: %.2f ms%n", tester.getListaTempoMedioSerial()[i]);
            System.out.println(); // Linha em branco para separação
        }
    }

    // Função para gerar um array aleatório de inteiros
    public static int[] gerarArrayAleatorio(int tamanho) {
        Random rand = new Random();
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = rand.nextInt(100000); // Números aleatórios de 0 a 100000
        }
        return array;
    }
}
