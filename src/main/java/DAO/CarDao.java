package DAO;

import model.Car;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public void addCar(Car car) throws Exception {
        Transaction transaction = session.beginTransaction();

        List<Car> cars = session.createQuery("from Car").list();
        if (cars.isEmpty()) {
            session.save(new DailyReport(0L, 0L));
        }

        List<Long> list = session.createQuery("select count (brand) from Car where brand = :car_brand").setParameter("car_brand", car.getBrand()).list();
        if (list.get(0) < 10) {
            session.save(car);
        } else {
            throw new Exception("Impossible to hold more cars of this brand!");
        }

        transaction.commit();
        session.close();
    }

    public void sellCar(String brand, String model, String licensePlate) {
        try {
            Transaction transaction = session.beginTransaction();
            long price = findCurrentPriceOfCar(brand, model, licensePlate);

            List<Long> reports = session.createQuery("select (id) from DailyReport").list();
            long id = reports.get(reports.size() - 1);

            List<Long> earningsList = session.createQuery("select (earnings) from DailyReport").list();
            long earnings = 0L;
            if (!earningsList.isEmpty()) {
                earnings = earningsList.get(earningsList.size() - 1);
            }

            List<Long> soldCarsList = session.createQuery("select (soldCars) from DailyReport").list();
            long soldCars = 0L;
            if (!soldCarsList.isEmpty()) {
                soldCars = soldCarsList.get(soldCarsList.size() - 1);
            }

            DailyReport dailyReport = new DailyReport(earnings + price, ++soldCars);

            Query q = session.createQuery("update DailyReport set earnings = :earnings , soldCars = :soldCars where id = :id");
            q.setParameter("earnings", dailyReport.getEarnings());
            q.setParameter("soldCars", dailyReport.getSoldCars());
            q.setParameter("id", id);
            q.executeUpdate();

            deleteCar(brand, model, licensePlate);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(String brand, String model, String licensePlate) {
        Query deleteCarQuery = session.createQuery("delete from Car where brand =:car_brand and model =:car_model and licensePlate =:car_licensePlate");
        deleteCarQuery.setParameter("car_brand", brand);
        deleteCarQuery.setParameter("car_model", model);
        deleteCarQuery.setParameter("car_licensePlate", licensePlate);
        deleteCarQuery.executeUpdate();
    }

    public long findCurrentPriceOfCar(String brand, String model, String licensePlate) {
        Query selectCarQuery = session.createQuery("select (price) from Car where brand =:car_brand and model =:car_model and licensePlate =:car_licensePlate");
        selectCarQuery.setParameter("car_brand", brand);
        selectCarQuery.setParameter("car_model", model);
        selectCarQuery.setParameter("car_licensePlate", licensePlate);
        return (long) selectCarQuery.list().get(0);
    }
}
