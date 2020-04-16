package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public void addCar(String brand, String model, String licensePlate, Long price) throws Exception {
        new CarDao(sessionFactory.openSession()).addCar(new Car(brand, model, licensePlate, price));
    }

    public void sellCar(String brand, String model, String licensePlate) {
        new CarDao(sessionFactory.openSession()).sellCar(brand, model, licensePlate);
    }
}
