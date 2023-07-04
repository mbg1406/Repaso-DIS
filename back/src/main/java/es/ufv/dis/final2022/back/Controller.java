package es.ufv.dis.final2022.back;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.flow.component.html.Paragraph;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller
{
    private List<Medicamento> medicamento = new ArrayList<Medicamento>();

    public void generarPDF(List<Medicamento> medicamentos){
        try {
            Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(System.getProperty("user.dir") + "/productos/backup.pdf"));//"../../../../../../../../../productos/backup.pdf"));
            doc.open();
            // Convertimos la lista en un String con formato JSON
            String text = new Gson().toJson(medicamentos);
            Paragraph p = new Paragraph(text);
            doc.add((Element) p);
            doc.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("./back/src/main/resources/medicamento.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.medicamento = gson.fromJson(reader, new TypeToken<List<Medicamento>>() {}.getType());
    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("./back/src/main/resources/medicamento.json");
        writer.print(gson.toJson(this.medicamento));
        writer.close();
    }

    @GetMapping("/guardarmedicamento")
    public void guardarmedicamento(@RequestParam String nombre, @RequestParam String categoria, @RequestParam String precio, @RequestParam String EAN13) throws FileNotFoundException {
        this.loadjson();
        medicamento.add(new Medicamento(nombre, categoria, precio, EAN13));
        System.out.println(medicamento);
        this.savejson();
    }

    @GetMapping("/ObtenerMedicamento")
    public List<Medicamento> cogermedicamento(){
        this.loadjson();
        return medicamento;
    }
}
