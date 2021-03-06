package filehandling;

import enums.Move;

import java.io.BufferedWriter;
import java.io.IOException;

public class OutputFile {

    private StringBuilder movesList = new StringBuilder();
    private BufferedWriter outputFile;

    public OutputFile(BufferedWriter file) {
        this.outputFile = file;
    }

    public void updateMovesMap(Move move) {
        movesList.append(move.getValue());
        movesList.append('\n');
    }

    public void setEndGame(char c) {
        movesList.append(c);
    }

    public void exportToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(outputFile)) {
            writer.write(movesList.toString());
        } catch (IOException e){
            throw new IOException(e);
        }
    }

    public void printAllMoves() {
        System.out.print(movesList.toString());
    }
}
