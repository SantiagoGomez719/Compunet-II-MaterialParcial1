package com.compunet.servlet;

import com.compunet.model.Item;
import com.compunet.service.ItemService;

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

/**
 * PATRÓN REAL usado en EstudianteServlet:
 * 1) @WebServlet mapea la ruta (no se toca web.xml para esto)
 * 2) init() obtiene el WebApplicationContext que dejó el
 *    ContextLoaderListener, y saca el bean UNA VEZ (no en cada request)
 * 3) doGet/doPost usan ese bean ya guardado en un atributo de instancia
 */
@WebServlet("/items")
public class ItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());

        // Por nombre (como en el ejemplo del profesor):
        this.itemService = (ItemService) context.getBean("itemServiceBean");

        // Alternativa por tipo, más segura si cambias el id del bean:
        // this.itemService = context.getBean(ItemService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Item> items = itemService.listarItems();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Items</title></head><body>");
        out.println("<h1>Gestión de Items - Spring Core + Servlets</h1>");

        out.println("<h2>Registrar nuevo item</h2>");
        out.println("<form action='" + request.getContextPath() + "/items' method='POST'>");
        out.println("<label>Id: </label><input type='number' name='id' required/><br/>");
        out.println("<label>Nombre: </label><input type='text' name='nombre' required/><br/>");
        out.println("<label>Valor: </label><input type='number' step='0.01' name='valor' required/><br/>");
        out.println("<button type='submit'>Guardar</button>");
        out.println("</form>");

        out.println("<hr/><h2>Lista de items</h2><ul>");
        for (Item item : items) {
            out.println("<li>#" + item.getId() + " - " + item.getNombre()
                    + " (" + item.getValor() + ")</li>");
        }
        out.println("</ul></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            double valor = Double.parseDouble(request.getParameter("valor"));

            itemService.registrarItem(new Item(id, nombre, valor));
            response.sendRedirect(request.getContextPath() + "/items");

        } catch (IllegalArgumentException e) {
            // Regla de negocio violada -> alerta HTML, no un stacktrace
            mostrarAlerta(response, e.getMessage());
        }
    }

    private void mostrarAlerta(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2 style='color:red;'>No se pudo registrar el item</h2>");
        out.println("<p>" + mensaje + "</p>");
        out.println("<a href='javascript:history.back()'>Volver</a>");
        out.println("</body></html>");
    }
}
