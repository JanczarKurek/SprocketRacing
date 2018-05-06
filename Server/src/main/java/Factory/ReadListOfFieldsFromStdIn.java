package Factory;

import java.util.*;

public class ReadListOfFieldsFromStdIn {
    public static Collection<SimplifiedBoardFiled> readListOfFieldsFromStdIn(){
        Scanner scanner = new Scanner(System.in);
        TreeSet<Integer> idSet = new TreeSet<>();

        System.out.println("Set number of fields: ");
        int numOfFields = scanner.nextInt();
        if (numOfFields < 0) {
            System.out.println("Number of fields must be positive!");
            return new LinkedList<>();
        }

        LinkedList<SimplifiedBoardFiled> list = new LinkedList<>();
        for (int i=0; i<numOfFields; i++) {
            SimplifiedBoardFiled sbf = new SimplifiedBoardFiled();

            System.out.println("Set ID of this field: ");
            int id = scanner.nextInt();
            if(idSet.contains(id)) {
                System.out.println("ID must be uniqe!");
                return new java.util.LinkedList<>();
            }
            idSet.add(id);
            sbf.setId(id);

            System.out.println("Set number of succersors");
            int numOfSucc = scanner.nextInt();
            if (numOfSucc<0) {
                System.out.println("Illegal argument!");
                return new java.util.LinkedList<>();
            }

            System.out.println("Set succesors IDs: ");
            for(int j=0; j<numOfSucc; j++)
                sbf.add(scanner.nextInt());

            System.out.println("Set number of OnStayEffects");
            int numOfOSE = scanner.nextInt();
            if (numOfOSE < 0) {
                System.out.println("Number of OnStayEffects can not be negative!");
                return new java.util.LinkedList<>();
            }

            System.out.println("Set OnStayEffects IDs: ");
            for(int j=0; j<numOfOSE; j++)
                sbf.addEffect()
        }

    }
}
