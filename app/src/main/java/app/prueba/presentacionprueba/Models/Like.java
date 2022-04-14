package app.prueba.presentacionprueba.Models;

public class Like {

    String idUser;
    String idPerson;

    public Like() {
    }

    public Like(String idUser, String idPerson) {
        this.idUser = idUser;
        this.idPerson = idPerson;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }
}
