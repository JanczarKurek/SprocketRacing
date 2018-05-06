package Factory;

import MapServer.*;
import java.util.*;
import java.io.*;

import static Factory.PutListOfFields.putListOfFields;
import static Factory.ReadBoardStructure.readBoardStructure;
import static Factory.ReadListOfFieldsFromStdIn.readListOfFieldsFromStdIn;
import org.json.simple.*;

public class CreateBoardFromStdInData {
    public static boolean readBoardFromStdInData(BoardStructure boardStructure) {
        System.out.println("Set name of file, in which you will save your board shape: ");
        String fileName;
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.next();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            LinkedList<SimplifiedBoardFiled> list = readListOfFieldsFromStdIn();
            if (list.size() < 1)
                return false;
            JSONObject jsonObject = new JSONObject();

            putListOfFields(jsonObject, list);

            boardStructure = readBoardStructure(fileName);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
