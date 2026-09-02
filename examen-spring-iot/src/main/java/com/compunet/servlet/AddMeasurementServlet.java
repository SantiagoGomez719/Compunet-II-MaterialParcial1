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

/**
 * Punto 1 de "Desarrollo" del examen: agregar una medición a un
 * dispositivo usando su id (assetId).
 *
 * Punto 2 de "Desarrollo": si se incumple una regla de negocio, se debe
 * mostrar una alerta en HTML. Aquí eso pasa capturando la
 * IllegalArgumentException que lanza el Service.
 */
@WebServlet("/measurements/add")
public class AddMeasurementServlet extends HttpServlet {

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
        // Formulario simple para probar el servlet desde el navegador.
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Agregar medición</h2>");
        out.println("<form action='" + request.getContextPath() + "/measurements/add' method='POST'>");
        out.println("Id medición: <input type='number' name='id' required/><br/>");
        out.println("Timestamp (ms): <input type='number' name='timestamp' required/><br/>");
        out.println("Valor: <input type='number' step='0.01' name='value' required/><br/>");
        out.println("Id del dispositivo (assetId): <input type='number' name='assetId' required/><br/>");
        out.println("<button type='submit'>Registrar</button>");
        out.println("</form>");
        out.println("<p><a href='" + request.getContextPath() + "/measurements'>Ver todas las mediciones</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            long timestamp = Long.parseLong(request.getParameter("timestamp"));
            double value = Double.parseDouble(request.getParameter("value"));
            Integer assetId = Integer.parseInt(request.getParameter("assetId"));

            Measurement measurement = new Measurement(id, timestamp, value, assetId);
            measurementService.registrarMeasurement(measurement);

            response.sendRedirect(request.getContextPath() + "/measurements");

        } catch (IllegalArgumentException e) {
            mostrarAlerta(response, e.getMessage());
        }
    }

    private void mostrarAlerta(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2 style='color:red;'>No se pudo registrar la medición</h2>");
        out.println("<p>" + mensaje + "</p>");
        out.println("<a href='javascript:history.back()'>Volver</a>");
        out.println("</body></html>");
    }
}
