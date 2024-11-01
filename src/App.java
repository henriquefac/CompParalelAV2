import CSVHandler.CSVWriter;
import sort_algorith.*;
import sort_algorith.paralel_sort.*;
import sort_algorith.serial_sort.*;
import TestAlgorithm.Tester;

public class App {
    public static void main(String[] args) {
        try {
            // Tamanhos dos arrays a serem testados
            System.out.println("Iniciando os testes!");
            int[] tamanhos = {1000, 10000, 100000, 1000000, 10000000}; // Ajuste conforme necessário

            // Criando instâncias dos algoritmos de ordenação
            SortAlgo sortSerial = new QuickSort(); // Instância do algoritmo de ordenação serial
            SortAlgoPRL sortPRL = new QuickSortPRL(null, null); // Instância do algoritmo paralelo

            // Criando o tester para executar os testes de desempenho
            Tester tester = new Tester(sortPRL, sortSerial, tamanhos);
            
            // Construindo o nome do arquivo CSV baseado nos algoritmos
            String nomeAlgoritmoSerial = sortSerial.getClass().getSimpleName();
            String nomeAlgoritmoParalelo = sortPRL.getClass().getSimpleName();
            String csvFile = nomeAlgoritmoSerial + "_vs_" + nomeAlgoritmoParalelo + ".csv"; // Nome do arquivo CSV

            // Usando a classe CSVWriter para gravar os resultados
            CSVWriter csvWriter = new CSVWriter(csvFile);
            String[] header = {"Tamanho", 
                               "Tempo Médio Paralelo (nível 0)", 
                               "Tempo Médio Paralelo (nível 1)", 
                               "Tempo Médio Paralelo (nível 2)", 
                               "Tempo Médio Paralelo (nível 3)",
                               "Tempo Médio Paralelo (nível 4)", 
                               "Tempo Médio Serial"};
            csvWriter.writeHeader(header); // Escrevendo cabeçalho

            // Exibindo os resultados médios de tempo de execução
            for (int i = 0; i < tamanhos.length; i++) {
                System.out.println("Tamanho: " + tamanhos[i]);
                
                double[] temposMediosParalelos = tester.getListaTempoMedioPRL()[i];
                
                for (int j = 0; j < temposMediosParalelos.length; j++) {
                    System.out.printf("Tempo médio Paralelo (nível %d): %.2f ms%n", j, temposMediosParalelos[j]);
                }
                
                double tempoMedioSerial = tester.getListaTempoMedioSerial()[i];
                System.out.printf("Tempo Médio Serial: %.2f ms%n", tempoMedioSerial);
                
                // Criando a linha de dados
                String[] dataRow = new String[header.length];
                dataRow[0] = String.valueOf(tamanhos[i]);
                for (int m = 0; m < 5; m++) {
                    dataRow[m + 1] = String.valueOf(temposMediosParalelos[m]);
                }
                dataRow[6] = String.valueOf(tempoMedioSerial);

                // Escrevendo os resultados no arquivo CSV
                csvWriter.writeRow(dataRow);
                System.out.println(); // Linha em branco para separação
            }
            csvWriter.close(); // Fechando o CSVWriter
            System.out.println("Resultados gravados em " + csvFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
