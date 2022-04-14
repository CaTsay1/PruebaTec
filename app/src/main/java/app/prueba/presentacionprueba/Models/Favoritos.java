package app.prueba.presentacionprueba.Models;

public class Favoritos {

    String id;
    String resultados;
    String posicion;

    public Favoritos() {
    }

    public Favoritos(String id, String resultados, String posicion) {
        this.id = id;
        this.resultados = resultados;
        this.posicion = posicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
}
