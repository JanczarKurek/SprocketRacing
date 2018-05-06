package Factory;

import java.util.*;

import static MapServer.EffectsSet.getEffect;

public class ReadListOfFieldsFromStdIn {
    public static LinkedList<SimplifiedBoardFiled> readListOfFieldsFromStdIn(){
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
                sbf.addEffect(getEffect(scanner.nextInt()));

            System.out.println("Set number of OnPassEffects");
            int numOfOPE = scanner.nextInt();
            if (numOfOPE < 0) {
                System.out.println("Number of OnPassEffects can not be negative!");
                return new java.util.LinkedList<>();
            }

            System.out.println("Set OnPassEffects IDs: ");
            for(int j=0; j<numOfOPE; j++)
                sbf.addEffect(getEffect(scanner.nextInt()));

            list.add(sbf);
        }

        for (SimplifiedBoardFiled sbf : list)
            for (Integer i : sbf.getNextFields())
                if (!idSet.contains(i)) {
                    System.out.println("ID of one of succesors is wrong!");
                    return new java.util.LinkedList<>();
                }

        int endCounter = 0;
        for (SimplifiedBoardFiled sbf : list)
            if (sbf.getNextFields().isEmpty())
                endCounter++;
        if (endCounter != 1) {
            System.out.println("Invalid linking!");
            return new java.util.LinkedList<>();
        }

        TreeSet<Integer> startFieldsSet = new java.util.TreeSet<>();
        for (Integer i : idSet)
            startFieldsSet.add(i);
        for (SimplifiedBoardFiled sbf : list)
            for (Integer i : sbf.getNextFields())
                if (startFieldsSet.contains(i))
                    startFieldsSet.remove(i);
        if(startFieldsSet.size() != 1) {
            System.out.println("Invalid linking!");
            return new java.util.LinkedList<>();
        }


        return list;
    }
}
