package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String licensePlate = req.getParameter("licensePlate");
            long price = Long.parseLong(req.getParameter("price"));

            CarService.getInstance().addCar(brand, model, licensePlate, price);

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
