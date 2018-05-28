package VisualCards;

import Cards.VehicleCardData;
import Cards.VehicleCardEngine;
import InGameResources.Dice.Dice;
import InGameResources.Dice.DiceSlotsImpl;
import misc.Cost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadCard {
    public VisualCard readCard(File file, CardMap.StaticMap map){
        try{
            FileInputStream stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
            VehicleCardData vehicle = new VehicleCardData();
            vehicle.setName(fileReader.nextLine());
            vehicle.setId(fileReader.nextInt());
            vehicle.setJoints(fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean());
            Cost cost = new Cost(fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt());
            vehicle.setCost(cost);
            int size = fileReader.nextInt();
            int color = fileReader.nextInt(); // 0-red, 1-yellow, 2-blue
            VehicleCardEngine engine;
            if(color==0)
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.RED));
            else if(color==1)
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.YELLOW));
            else
                engine = new VehicleCardEngine(null, null, new DiceSlotsImpl(size, Dice.Color.BLUE));
            vehicle.setEngine(engine);
            VisualCard visualCard = new VisualCard(vehicle);
            if(color==0)
                visualCard.setColorSlot(Dice.Color.RED);
            else if(color==1)
                visualCard.setColorSlot(Dice.Color.YELLOW);
            else
                visualCard.setColorSlot(Dice.Color.BLUE);
            visualCard.setNumberSlots(size);
            visualCard.setUsagePipCost(fileReader.nextInt());
            int effectsNumber = fileReader.nextInt();
            for(int i=0; i<effectsNumber; i++){
                int temp = fileReader.nextInt();
                visualCard.addEffect(temp);
            }
            map.put(vehicle.getID(), visualCard);
            return visualCard;
        }catch (FileNotFoundException e) {
            System.err.println("Input file not found " +file.getPath());
        }catch (NoSuchElementException e2){
            System.err.println("No such element");
        }catch (Exception e){
            System.err.println("Other exception");
        }
        return null;
    }
}
