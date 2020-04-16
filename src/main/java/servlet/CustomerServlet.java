package servlet;

import com.google.gson.Gson;
import model.Car;
import org.hibernate.SessionFactory;
import service.CarService;
import util.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String licensePlate = req.getParameter("licensePlate");

            CarService.getInstance().sellCar(brand, model, licensePlate);

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
