package app.prueba.presentacionprueba.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandM {

    @SerializedName("info")
    private Informacion informacion;
     @SerializedName("results")
     private List<Resultados> resultados;


    public RandM(Informacion informacion, List<Resultados> resultados) {
        this.informacion = informacion;
        this.resultados = resultados;
    }

    public Informacion getInformacion() {
        return informacion;
    }

    public void setInformacion(Informacion informacion) {
        this.informacion = informacion;
    }

  public List<Resultados> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultados> resultados) {
        this.resultados = resultados;
    }

    public static Informacion parseJsonIn(String respose){
        Gson gson = new GsonBuilder().create();
        Informacion inf = gson.fromJson(respose, Informacion.class);
        return inf;
    }

   public static Resultados parseJsonRes(String respose){
        Gson gson = new GsonBuilder().create();
        Resultados resul = gson.fromJson(respose, Resultados.class);
        return resul;
    }
}
