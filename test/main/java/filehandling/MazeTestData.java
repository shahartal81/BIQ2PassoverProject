package filehandling;

import java.util.List;

public class MazeTestData {
    public static void init(List<String> result, MazeParser testSubject){
        result.add("My maze");
        result.add("MaxSteps = 10");
        result.add("Rows = 4");
        result.add("Cols = 12");

        testSubject.setMazeDefinition(result);
        ErrorsSingleton.instance().clean();
    }

    public static void initEmptyBody(List<String> result, MazeParser testSubject){
        result.add("My maze");
        result.add("MaxSteps = 10");
        result.add("Rows = 3");
        result.add("Cols = 4");

        testSubject.setMazeDefinition(result);
        ErrorsSingleton.instance().clean();
    }
}
