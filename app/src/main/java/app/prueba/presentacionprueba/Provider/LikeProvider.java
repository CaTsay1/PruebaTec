package app.prueba.presentacionprueba.Provider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import app.prueba.presentacionprueba.Models.Like;

public class LikeProvider {

    CollectionReference mCollection;

    public LikeProvider() { mCollection = FirebaseFirestore.getInstance().collection("Likes");
    }

    public Task<Void> create(Like like, String id){ return mCollection.document(id).set(like); }

    public Query getLike(String idUser, String idPerson){
        return mCollection.whereEqualTo("idUser", idUser).whereEqualTo("idPerson", idPerson);
    }

    public Query NumLike(String idPerson){
        return mCollection.whereEqualTo("idPerson", idPerson);
    }

    public Task<Void> borrarInformacion(String id){
        return mCollection.document(id).delete();
    }

}
