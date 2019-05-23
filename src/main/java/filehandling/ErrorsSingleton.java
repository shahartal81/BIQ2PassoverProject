package filehandling;

import java.util.ArrayList;
import java.util.List;

public class ErrorsSingleton {
    static ErrorsSingleton instance = new ErrorsSingleton();
    private List<String> errorList = new ArrayList<>();

    public static ErrorsSingleton instance() {
        return instance;
    }

    public void addToErrorList(String error){
        errorList.add(error);
    }

    public List<String> getErrorsList(){
        return errorList;
    }

    public void printErrors(){
        if (!errorList.isEmpty()) {
            for (String error : errorList){
                System.out.println(error);
            }
        }
    }


    void clean(){
        errorList.clear();
    }
}
