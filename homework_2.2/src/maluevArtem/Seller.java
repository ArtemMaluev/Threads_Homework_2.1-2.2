package maluevArtem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {

    private final int DELAY_TIME = 1000;
    private final int DELIVERY_TIME = 3000;

    private final CarDealership carDealership;
    private final ReentrantLock locker;
    private final Condition condition;

    public Seller(CarDealership carDealership) {
        this.carDealership = carDealership;
        locker = new ReentrantLock(true);
        condition = locker.newCondition();
    }

    public void sellCar() {
        try {
            locker.lock();
            Thread.sleep(DELAY_TIME);
            while (carDealership.getListCar().isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
                System.out.println("Продавец: извините, машин нет");
                condition.await();
                if (carDealership.getFlag() >= CarDealership.SIZE_LIST) {
                    return;
                }
            }
            Thread.sleep(DELAY_TIME);
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            System.out.println(Thread.currentThread().getName() + " купил машину " +
                    carDealership.getListCar().get(0).getName());
            carDealership.getListCar().remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void receiveCar() {
        try {
            locker.lock();
           if (carDealership.getFlag() == CarDealership.SIZE_LIST) {
                condition.signalAll();
                return;
            }
            Thread.sleep(DELAY_TIME);
            System.out.println("Пришла новая машина");
            Thread.sleep(DELAY_TIME);
            carDealership.getListCar().add(new Car("Toyota"));
            System.out.println("Машина готова к продаже");
            Thread.sleep(DELAY_TIME);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
        try {
            Thread.sleep(DELIVERY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
