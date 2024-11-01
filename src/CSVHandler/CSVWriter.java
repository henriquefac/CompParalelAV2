package CSVHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private BufferedWriter writer;

    // Construtor que aceita o nome do arquivo
    public CSVWriter(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName));
    }

    // Método para escrever o cabeçalho no CSV
    public void writeHeader(String[] header) throws IOException {
        writer.write(String.join(",", header));
        writer.newLine();
    }

    // Método para escrever uma linha de dados no CSV
    public void writeRow(String[] data) throws IOException {
        writer.write(String.join(",", data));
        writer.newLine();
    }

    // Método para fechar o arquivo
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}