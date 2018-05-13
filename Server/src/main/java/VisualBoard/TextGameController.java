package VisualBoard;

import ErrorsAndExceptions.NoSuchPlayer;
import ErrorsAndExceptions.WrongMove;
import MapServer.Board;
import MapServer.Path;
import MapServer.PawnController;
import SmallFunctionalFeaturesDamnYouJava.Functional;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TextGameController {
    Board game;
    TreeMap<Integer, PawnController> controllers;
    public TextGameController(Board game){
        this.game = game;
        controllers = new TreeMap<>();
        for(int player : Functional.range(game.getNumberOfPlayers())){
            try {
                controllers.put(player, game.getController(player));
            } catch (NoSuchPlayer noSuchPlayer) {
                System.err.println("No such player!");
                System.exit(-1);
            }
        }
    }

    public void parseCommand(String command){
        Scanner scanner = new Scanner(new StringReader(command));
        String cmd = scanner.next();
        if(cmd.equals("test"))
            return;
        if(cmd.equals("exit"))
            System.exit(0);
        Integer player = scanner.nextInt();
        LinkedList<Integer> path = new LinkedList<>();
        while(scanner.hasNext()){
            path.add(scanner.nextInt());
        }
        try {
            controllers.get(player).move(new Path(path));
        } catch (WrongMove wrongMove) {
            System.err.println("Wrong move!");
        }
    }

}
