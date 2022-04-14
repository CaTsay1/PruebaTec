package app.prueba.presentacionprueba.Provider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import app.prueba.presentacionprueba.Models.FavoFire;
import app.prueba.presentacionprueba.Models.Like;

public class FavoritosProvider {

    CollectionReference mCollection;

    public FavoritosProvider() { mCollection = FirebaseFirestore.getInstance().collection("Favoritos");
    }

    public Task<DocumentSnapshot> getData (String id){ return mCollection.document(id).get(); }

    public Task<Void> create(FavoFire fav, String id){ return mCollection.document(id).set(fav); }

    public Query getFav(String idUser){
        return mCollection.whereEqualTo("idUser", idUser);
    }

    public Query NumFav(String idPerson){
        return mCollection.whereEqualTo("id", idPerson);
    }

    public Task<Void> borrarInformacion(String id){
        return mCollection.document(id).delete();
    }

}
