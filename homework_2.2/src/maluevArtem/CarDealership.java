package maluevArtem;

import java.util.ArrayList;
import java.util.List;

public class CarDealership {

    final static int SIZE_LIST = 5;
    private int flag;

    private final Seller seller = new Seller(this);
    private final List<Car> listCar = new ArrayList<>(SIZE_LIST);

    public List<Car> getListCar() {
        return listCar;
    }

    public int getFlag() {
        return flag;
    }

    public void sellCar() {
        while (flag < SIZE_LIST) {
            seller.sellCar();
        }
    }

    public void receiveCar() {
        while (flag <= SIZE_LIST) {
            seller.receiveCar();
            flag++;
        }
    }
}
