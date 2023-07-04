package es.ufv.dis.final2022.back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
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
