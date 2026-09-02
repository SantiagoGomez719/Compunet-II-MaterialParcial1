package com.compunet.servlet;

import com.compunet.model.Measurement;
import com.compunet.service.MeasurementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/measurements")
public class ListMeasurementsServlet extends HttpServlet {

    private MeasurementService measurementService;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        this.measurementService = (MeasurementService) context.getBean("measurementServiceBean");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Measurement> mediciones = measurementService.listarTodas();

        out.println("<html><body>");
        out.println("<h2>Mediciones registradas</h2>");
        out.println("<p><a href='" + request.getContextPath() + "/measurements/add'>Agregar medición</a></p>");
        out.println("<ul>");
        for (Measurement m : mediciones) {
            out.println("<li>#" + m.getId()
                    + " - timestamp: " + m.getTimestamp()
                    + " - valor: " + m.getValue()
                    + " - dispositivo (assetId): " + m.getAssetId()
                    + "</li>");
        }
        out.println("</ul></body></html>");
    }
}
