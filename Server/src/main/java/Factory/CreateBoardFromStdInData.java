package Factory;

import MapServer.*;
import java.util.*;
import java.io.*;

public class CreateBoardFromStdInData {
    public static BoardStructure createBoardFromStdInData() {
        System.out.println("Put name of file, in wich you will save your board shape: ");
        String fileName;
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.next();

        try (FileWriter fileWriter = new FileWriter(fileName)) {



        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
