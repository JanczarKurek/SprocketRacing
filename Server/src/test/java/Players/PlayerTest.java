package Players;

import Cards.*;
import Cards.OnCardEffects.HealEffect;
import Cards.OnCardEffects.MoveEffect;
import Cards.OnCardEffects.SmoothMoveEffect;
import Cards.OnCardEffects.VentEffect;
import ErrorsAndExceptions.WrongMove;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlots;
import InGameResources.Dice.DiceSlotsImpl;
import MapServer.*;
import Table.Phase;
import misc.Cost;
import misc.Effect;
import org.junit.Before;
import org.junit.Test;
import Table.Table;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Table table;
    Player player0, player1;

    @Before
    public void prep(){
        BoardStructure boardStructure = new BoardStructure();

        BoardField boardField1 = new BoardField(new SimpleField(0));
        BoardField boardField2 = new BoardField(new SimpleField(1));
        BoardField boardField3 = new BoardField(new SimpleField(2));
        BoardField boardField4 = new BoardField(new SimpleField(3));
        BoardField boardField5 = new BoardField(new SimpleField(4));
        BoardField boardField6 = new BoardField(new SimpleField(5));
        BoardField boardField7 = new BoardField(new SimpleField(6));

        boardField1.addNext(boardField2);
        boardField2.addNext(boardField3);
        boardField3.addNext(boardField4);
        boardField4.addNext(boardField5);
        boardField5.addNext(boardField6);
        boardField6.addNext(boardField7);

        boardField7.addPrev(boardField6);
        boardField6.addPrev(boardField5);
        boardField5.addPrev(boardField4);
        boardField4.addPrev(boardField3);
        boardField3.addPrev(boardField2);
        boardField2.addPrev(boardField1);

        boardStructure.setField(boardField1);
        boardStructure.setField(boardField2);
        boardStructure.setField(boardField3);
        boardStructure.setField(boardField4);
        boardStructure.setField(boardField5);
        boardStructure.setField(boardField6);
        boardStructure.setField(boardField7);

        boardStructure.setStart(boardField1);
        boardStructure.setEnd(boardField7);

        LinkedList<Integer> PlayersList = new LinkedList<>(Arrays.asList(0, 1));

        Board board = new Board(boardStructure, PlayersList, 0);

        CardUsageCost c = new CardUsagePipCost(4);
        MoveEffect moveOnce = new MoveEffect(1);
        MoveEffect moveTwice= new MoveEffect(2);
        SmoothMoveEffect moveSmooth = new SmoothMoveEffect(1);
        VentEffect ventTwice = new VentEffect(2);
        DamageEffect damage = new DamageEffect(3);
        HealEffect heal = new HealEffect(4);
        DiceSlots slots = new DiceSlotsImpl(4, Dice.Color.RED);
        Effect[][] kek = {{heal, ventTwice}, {moveSmooth, damage}};
        CardEffect e = new CardEffect(kek);
        VehicleCardEngine engine = new VehicleCardEngine(c, e, slots);
        Cost cost = new Cost(0, 0, 2, 3);

        Deck deck0 = new Deck(0, "Ex deck no 0");

        VehicleCardData card = new VehicleCardData(cost, 0, "Ex card 0", engine, new Joints(false, false, true, true));
        deck0.put(card);

        cost = new Cost(0, 2, 2, 3);
        c = new CardUsageDiceCost();
        Effect[][] kek2 = {{moveTwice, damage}, {moveOnce}};
        e = new CardEffect(kek2);
        slots = new DiceSlotsImpl(2, Dice.Color.BLUE);
        engine = new VehicleCardEngine(c, e, slots);
        card = new VehicleCardData(cost, 1, "Ex card 1", engine, new Joints(true, true, true, true));
        deck0.put(card);

        Deck deck1 = new Deck(1, "Ex deck no 1");

        cost = new Cost(1, 1, 1, 1);
        c = new CardUsagePipCost(5);
        slots = new DiceSlotsImpl(3, Dice.Color.YELLOW);
        Effect[][] kek3 = {{heal}};
        e = new CardEffect(kek3);
        engine = new VehicleCardEngine(c, e, slots);
        card = new VehicleCardData(cost, 2, "Ex card 2", engine, new Joints(true, false, true, true));
        deck1.put(card);

        cost = new Cost(1, 1, 0, 1);
        c = new CardUsagePipCost(2);
        slots = new DiceSlotsImpl(1, Dice.Color.YELLOW);
        Effect[][] kek4 = {{moveOnce}, {moveOnce, moveOnce}};
        e = new CardEffect(kek4);
        engine = new VehicleCardEngine(c, e, slots);
        card = new VehicleCardData(cost, 3, "Ex card 3", engine, new Joints(true, false, true, false));
        deck1.put(card);

        table = new Table(board, Arrays.asList(deck0, deck1));

        cost = new Cost(1, 1, 0, 1);
        c = new CardUsageDiceCost();
        slots = new DiceSlotsImpl(1, Dice.Color.YELLOW);
        Effect[][] kek5 = {{moveOnce}, {moveOnce, moveOnce}};
        e = new CardEffect(kek5);
        engine = new VehicleCardEngine(c, e, slots);
        card = new VehicleCardData(cost, 4, "cockpit0", engine, new Joints(true, false, true, false));
        player0 = new Player(table, 0, card);

        cost = new Cost(1, 1, 0, 1);
        c = new CardUsageDiceCost();
        slots = new DiceSlotsImpl(1, Dice.Color.RED);
        Effect[][] kek6 = {{moveOnce}, {moveOnce, moveOnce}};
        e = new CardEffect(kek6);
        engine = new VehicleCardEngine(c, e, slots);
        card = new VehicleCardData(cost, 5, "cockpit1", engine, new Joints(true, false, true, false));
        player1 = new Player(table, 1, card);
        
    }

    @Test
    public void test1() throws WrongMove {
        player0.aquireHand();
        player1.aquireHand();
        player0.chooseCard(0);
        player1.chooseCard(0);
        player0.sellCard();
        player1.takeCard();
        player1.putCard(0, 0, 1); // I hope felix uses this kind of coordinates.
        player1.acceptVehicleLayout();
        player0.aquireHand();
        player1.aquireHand();
        player0.chooseCard(0);
        player1.chooseCard(0);
        player0.sellCard(Dice.Color.YELLOW);
        player1.sellCard(Dice.Color.RED);
        assertEquals(1, player0.getMyWallet().getDices().size());
        assertEquals(1, player1.getMyWallet().getDices().size());
        player0.vote();
        player1.vote();
        assertEquals(Phase.VENT, table.getCurrentPhase());
        player0.vote();
        player1.vote();
        assertEquals(Phase.RACE, table.getCurrentPhase());
        player1.roll();
        player0.roll();
        assertEquals(1, player0.getMyVehicle().getLayout().size());
        assertEquals(2, player1.getMyVehicle().getLayout().size());
        player1.useCard(0, 0, Arrays.asList(0));
        player1.acceptProposition();
        player1.runEffects(1);
        player1.runAtomicEffect();
        assertEquals(Player.Task.MAKEMOVE, player1.taskManager.getCurrentTask().type);
        player1.makeMove(new Path(Arrays.asList(0, 1)));
        assertEquals(Player.Task.RUNEFFECTS, player1.taskManager.getCurrentTask().type);
        player1.runAtomicEffect();
        player1.makeMove(new Path(Arrays.asList(1, 2)));
        System.err.println(table.getBoard().getPlayersPositions());
        assertEquals(Integer.valueOf(2), table.getBoard().getPlayersPositions().get(player1.getId()));
        assertEquals(Player.Task.IDLERACE, player1.taskManager.getCurrentTask().type);
    }
}