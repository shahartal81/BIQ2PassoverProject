package additionalclasses;

import enums.Move;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputFile {

    private StringBuilder movesList = new StringBuilder();
    private File outPutFile;

    public OutputFile(File file) {
        this.outPutFile = file;
    }

    public void updateMovesMap(Move move) {
        movesList.append(move.getValue());
        movesList.append('\n');
    }

    public void setEndGame(char c) {
        movesList.append(c);
    }

    public void exportToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPutFile))) {
            writer.write(movesList.toString());
        } catch (IOException e){
            throw new IOException(e);
        }

    }

    public void printAllMoves() {
        System.out.print(movesList.toString());
    }
}
