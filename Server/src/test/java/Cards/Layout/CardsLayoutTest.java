package Cards.Layout;

import static org.junit.Assert.*;
import java.util.*;
import Cards.VehicleCardData;
public class CardsLayoutTest {

    @org.junit.Test
    public void test1() {
        VehicleCardData[][] vehicleCardDataArray = new VehicleCardData[10][10];

        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                vehicleCardDataArray[x][y] = new Cards.VehicleCardData();

        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                vehicleCardDataArray[x][y].setJoints(true, true, false, false);

        CardsLayout cardsLayout = new CardsLayout();
        cardsLayout.setCockpit(vehicleCardDataArray[0][0], 0, 0);
        for (int y = 1; y < 10; y++)
            cardsLayout.add(vehicleCardDataArray[0][y], 0, y);
        for (int x = 1; x < 10; x++)
            for (int y = 0; y < 10; y++)
                cardsLayout.add(vehicleCardDataArray[x][y], x, y);

        assertFalse(cardsLayout.checkCorrectness());

        for (int y = 0; y < 10; y++)
            vehicleCardDataArray[0][y].setJoints(true, true, true, true);
        assertTrue(cardsLayout.checkCorrectness());

        vehicleCardDataArray[0][5].setJoints(false, true, false, false);
        assertFalse(cardsLayout.checkCorrectness());

        vehicleCardDataArray[5][5].setJoints(true, true, true, true);
        vehicleCardDataArray[5][6].setJoints(true, true, true, true);
        vehicleCardDataArray[5][4].setJoints(true, true, true, true);

        assertTrue(cardsLayout.checkCorrectness());
    }

}