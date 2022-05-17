package maluevArtem;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        final CarDealership carDealership = new CarDealership();

        new Thread(carDealership::sellCar, "Покупатель1").start();
        new Thread(carDealership::sellCar, "Покупатель2").start();
        new Thread(carDealership::sellCar, "Покупатель3").start();

        new Thread(carDealership::receiveCar, "Поставщик").start();

    }
}
