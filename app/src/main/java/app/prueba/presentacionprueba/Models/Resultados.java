package app.prueba.presentacionprueba.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Resultados  {

    private int id;
    private  String name;
    private  String status;
    private  String species;
    private  String type;
    private  String gender;
   @SerializedName("origin")
    private  Origen origen;
   @SerializedName("location")
    private Ubicacion ubicacion;
    private  String image;
    @SerializedName("episode")
    private List<String> episodio;
    private  String url;
    private String created;

    public Resultados(int id, String name, String status, String species, String type, String gender, Origen origen, Ubicacion ubicacion, String image, List<String> episodio, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origen = origen;
        this.ubicacion = ubicacion;
        this.image = image;
        this.episodio = episodio;
        this.url = url;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

   public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getEpisodio() {
        return episodio;
    }

    public void setEpisodio(List<String> episodio) {
        this.episodio = episodio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public static Origen parseJsonOr(String respose){
        Gson gson = new GsonBuilder().create();
        Origen ori = gson.fromJson(respose, Origen.class);
        return ori;
    }

    public static Ubicacion parseJsonUbi(String respose){
        Gson gson = new GsonBuilder().create();
        Ubicacion ubi = gson.fromJson(respose, Ubicacion.class);
        return ubi;
    }

}
