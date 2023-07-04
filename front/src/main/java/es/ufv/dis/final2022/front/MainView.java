package es.ufv.dis.final2022.front;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@PageTitle("Examen Final DIS ")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     */
    VerticalLayout option1Cont;
    VerticalLayout option2Cont;
    /**
     * Clase empleada para la creación de la vista principal de la aplicación.
     */
    public MainView() throws URISyntaxException, IOException, InterruptedException {

        Label Title = new Label("Examen DIS Manuel Benítez Gutiérrez");
        Title.addClassName("title");
        Title.addClassName("centered-content");
        add(Title);

        setId("login-view");
        VerticalLayout verticalLayout = new VerticalLayout();
        API api = new API();
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        TextField categoria = new TextField("Categoria");
        categoria.setRequired(true);
        TextField precio = new TextField("Nombre de usuario");
        precio.setRequired(true);
        TextField EAN13 = new TextField("EAN13");
        EAN13.setRequired(true);
        verticalLayout.add(
                new H1("Registrar medicamento"),
                nombre,
                categoria,
                precio,
                EAN13,
                new Button("Añadir medicamento", e->{
                    try {
                        api.guardarmedicamento(nombre.getValue(), categoria.getValue(), precio.getValue(), EAN13.getValue());

                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        this.add(verticalLayout);

        List<Medicamento> medicamentos = api.cogermedicamento();
        VerticalLayout vl = new VerticalLayout();
        vl.setAlignItems(Alignment.CENTER);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        H1 f = new H1("Listado de medicinas:");
        Grid<Medicamento> grid = new Grid<>(Medicamento.class, false);
        grid.addColumn(Medicamento::getNombre).setHeader("Nombre");
        grid.addColumn(Medicamento::getCategoria).setHeader("Categoria");
        grid.addColumn(Medicamento::getPrecio).setHeader("Precio");
        grid.addColumn(Medicamento::getEAN13).setHeader("EAN13");
        grid.setItems(medicamentos);
        verticalLayout.add(f, grid);
        this.add(vl);


    }
}