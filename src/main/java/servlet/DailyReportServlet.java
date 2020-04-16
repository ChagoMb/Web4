package servlet;

import com.google.gson.Gson;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo().contains("all")) {
            Gson gson = new Gson();
            String json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            resp.getWriter().write(json);
            resp.setStatus(200);
        } else if (req.getPathInfo().contains("last")) {
            Gson gson = new Gson();
            String json = gson.toJson(DailyReportService.getInstance().getLastReport());
            resp.getWriter().write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DailyReportService.getInstance().deleteAllReports();

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
