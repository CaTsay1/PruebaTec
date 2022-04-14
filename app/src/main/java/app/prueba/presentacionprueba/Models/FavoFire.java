package app.prueba.presentacionprueba.Models;

public class FavoFire {

    String id;
    String resultados;
    String posicion;
    String idFav;
    String idUser;

    public FavoFire() {
    }

    public FavoFire(String id, String resultados, String posicion, String idFav, String idUser) {
        this.id = id;
        this.resultados = resultados;
        this.posicion = posicion;
        this.idFav = idFav;
        this.idUser = idUser;
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

    public String getIdFav() {
        return idFav;
    }

    public void setIdFav(String idFav) {
        this.idFav = idFav;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
