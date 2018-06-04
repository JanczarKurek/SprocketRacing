package Cards;

public class LoadedCard {
    Cards.VehicleCardData vehicleCardData;
    String pathToImage;

    public LoadedCard(Cards.VehicleCardData vehicleCardData, String pathtoImage) {
        this.vehicleCardData = vehicleCardData;
        this.pathToImage = pathtoImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public void setVehicleCardData(Cards.VehicleCardData vehicleCardData) {
        this.vehicleCardData = vehicleCardData;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public Cards.VehicleCardData getVehicleCardData() {
        return vehicleCardData;
    }
}
