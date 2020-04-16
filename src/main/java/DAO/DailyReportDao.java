package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastDay() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("from DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports.get(dailyReports.size() - 1);
    }

    public boolean getNewDay() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("from Car").list();
        if (cars.isEmpty()) {
            session.save(new DailyReport(0L, 0L));
        }
        session.createQuery("delete from Car").executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }

    public void deleteAllReports() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}
