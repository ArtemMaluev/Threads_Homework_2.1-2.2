package maluevArtem;

public class Seller {

    private final int DELAY_TIME = 1000;
    private final int WAIT_TIME = 5000;

    private final CarDealership carDealership;

    public Seller(CarDealership carDealership) {
        this.carDealership = carDealership;
    }

    public synchronized void sellCar() {
        try {
            Thread.sleep(DELAY_TIME);
            while (carDealership.getListCar().isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
                System.out.println("Продавец: извините, машин нет");
                wait();
                if (carDealership.getFlag() >= CarDealership.SIZE_LIST) {
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            System.out.println(Thread.currentThread().getName() + " купил машину " +
                    carDealership.getListCar().get(0).getName());
            carDealership.getListCar().remove(0);
            wait(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void receiveCar() {
        try {
            if (carDealership.getFlag() == CarDealership.SIZE_LIST) {
                notifyAll();
                return;
            }
            Thread.sleep(DELAY_TIME);
            System.out.println("Пришла новая машина");
            Thread.sleep(DELAY_TIME);
            carDealership.getListCar().add(new Car("Toyota"));
            System.out.println("Машина готова к продаже");
            Thread.sleep(DELAY_TIME);
            notifyAll();
            wait(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
