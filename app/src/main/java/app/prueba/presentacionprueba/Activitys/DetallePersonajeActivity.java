package app.prueba.presentacionprueba.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.prueba.presentacionprueba.Controladores.LoginController;
import app.prueba.presentacionprueba.Models.FavoFire;
import app.prueba.presentacionprueba.Models.Like;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.Provider.FavoritosProvider;
import app.prueba.presentacionprueba.Provider.LikeProvider;
import app.prueba.presentacionprueba.R;
import app.prueba.presentacionprueba.channel.AdminSQLiteOpenHelper;

public class DetallePersonajeActivity extends AppCompatActivity {

    String resul;
    int pos;
    int band, banLike;
    String imagen;
    String idPerson;
    String posi;

    TextView txtNombre, txtEstado, txtSpecie, txtFavorito, txtFechaCreacion, txtGenero, btn_Episodios, txtMegusta, txtFav;
    ImageView btn_Regresar, FotoPersonaje, favorite, megusta;
    LinearLayout btn_Favoritos;


    ArrayList<Resultados>resultados;
    ArrayList<String>episodios;
    LikeProvider mLikeProvider;
    LoginController mLoginController;
    FavoritosProvider mFavoritosProvider;

    ListenerRegistration mListener, mmListener, mmmListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_personaje);

        pos = getIntent().getExtras().getInt("position");
        resul = getIntent().getStringExtra("List");

        posi = Integer.toString(pos);

        inicializar();
        converter();
        getDatos();
        onClick();
    }

    private void onClick() {
        btn_Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Episodios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String Epi = gson.toJson(episodios);

                Intent intent = new Intent(DetallePersonajeActivity.this, EpisodiosActivity.class);
                intent.putExtra("Episodios", Epi);
                intent.putExtra("Imagen", imagen);
                startActivity(intent);
            }
        });

        btn_Favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (band == 0){
                    AgregarFav();
                }else if (band == 1){
                    EliminarFav();
                }

            }
        });

        megusta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (banLike == 1){
                    eliminarLike();
                }else if (banLike == 0){
                    registrarLike();
                }
            }
        });
    }

    private void eliminarLike() {
        String id = mLoginController + idPerson;
        mLikeProvider.borrarInformacion(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetallePersonajeActivity.this, "Ya No Te Gusta", Toast.LENGTH_SHORT).show();
                    megusta.setImageResource(R.drawable.ic_baseline_like_white);
                }else {
                    Toast.makeText(DetallePersonajeActivity.this, "No Se Pudo Eliminar EL Me Gusta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarLike() {
        String id = mLoginController + idPerson;
        Like like = new Like();
        like.setIdPerson(idPerson);
        like.setIdUser(mLoginController.getUid());
        mLikeProvider.create(like, id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetallePersonajeActivity.this, "Te Gusta", Toast.LENGTH_SHORT).show();
                    megusta.setImageResource(R.drawable.ic_baseline_like_blue);
                }else {
                    Toast.makeText(DetallePersonajeActivity.this, "No Se Pudo Registrar EL Me Gusta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EliminarFav() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FAVORITOS", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        int size = BaseDatos.delete("favoritos", "id =" + idPerson, null);
        BaseDatos.close();
        if (size >0){
            Toast.makeText(DetallePersonajeActivity.this, "Se Elimino De Tu Lista De Favoritos", Toast.LENGTH_SHORT).show();
            eliminarFavFire();
        }else {
            Toast.makeText(DetallePersonajeActivity.this, "Personaje No Encontrado", Toast.LENGTH_SHORT).show();
        }
        VerificarFav();
    }

    private void eliminarFavFire() {
        mFavoritosProvider.borrarInformacion(mLoginController.getUid()+idPerson).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DetallePersonajeActivity.this, "Se Elimino De La Base De Datos", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetallePersonajeActivity.this, "No se Pudo Eliminar De La Base De Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AgregarFav() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FAVORITOS", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id", idPerson);
        registro.put("contenido", resul);
        registro.put("pos", posi);

        BaseDatos.insert("favoritos", null, registro);
        BaseDatos.close();

        agreFavFire();

        Toast.makeText(DetallePersonajeActivity.this, "Se Agrego A La Lista De Favoritos", Toast.LENGTH_SHORT).show();

        VerificarFav();
    }

    private void agreFavFire() {

        String idFavorito = mLoginController.getUid() + idPerson;

        mFavoritosProvider.getData(idFavorito).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Toast.makeText(DetallePersonajeActivity.this, "Ya Esta En La Nube Sincroniza Los Datos", Toast.LENGTH_SHORT).show();
                }else {
                    FavoFire fav = new FavoFire();
                    fav.setId(idPerson);
                    fav.setPosicion(posi);
                    fav.setResultados(resul);
                    fav.setIdFav(idFavorito);
                    fav.setIdUser(mLoginController.getUid());

                    mFavoritosProvider.create(fav, idFavorito).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(DetallePersonajeActivity.this, "Se Agrego A La Base De Datos", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DetallePersonajeActivity.this, "No Se Pudo Agregar A La Base De Datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void converter() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Resultados>>(){}.getType();
        resultados = gson.fromJson(resul,type);
    }

    private void inicializar() {
        txtNombre = findViewById(R.id.txt_Nombre_Detalle);
        txtFechaCreacion = findViewById(R.id.txt_Creado_Detalle);
        txtEstado = findViewById(R.id.txt_Estado_Detalle);
        txtSpecie = findViewById(R.id.txt_Especie_Detalle);
        txtGenero = findViewById(R.id.txt_Genero_Detalle);
        txtFavorito = findViewById(R.id.txt_addFavoritos_Detalle);
        txtMegusta = findViewById(R.id.txt_MeGusta_Detalle);
        txtFav = findViewById(R.id.txt_Fav_Detalle);
        FotoPersonaje = findViewById(R.id.ImagPerson_Detalle);
        megusta = findViewById(R.id.megusta_Detalle);
        favorite = findViewById(R.id.ImgFavorite_Detalle);
        btn_Regresar = findViewById(R.id.Regresar_Detalle);
        btn_Episodios = findViewById(R.id.btn_Episodios_Detalle);
        btn_Favoritos = findViewById(R.id.Add_ListaFavoritos_Detalle);

        mLikeProvider = new LikeProvider();
        mLoginController = new LoginController();
        mFavoritosProvider = new FavoritosProvider();
    }

    private void getDatos(){
        Resultados model = resultados.get(pos);
        String name = model.getName();
        txtNombre.setText(name);

        String estado = model.getStatus();
        txtEstado.setText(estado);

        String especie = model.getSpecies();
        txtSpecie.setText(especie);

        String genero = model.getGender();
        txtGenero.setText(genero);

        String creacion = model.getCreated();
        txtFechaCreacion.setText(creacion);

        int idPer = model.getId();
        idPerson = Integer.toString(idPer);



        imagen = model.getImage();
        if (imagen != null){
            if (!imagen.isEmpty()){
                Picasso.with(DetallePersonajeActivity.this).load(imagen).into(FotoPersonaje);
            }
        }

        episodios = (ArrayList<String>) model.getEpisodio();

        VerificarFav ();
        datosLike();
        datosFavoritos();
    }

    private void datosFavoritos() {
      mmmListener = mFavoritosProvider.NumFav(idPerson).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int size = value.size();
                String num = Integer.toString(size);
                txtFav.setText(num);
            }
        });
    }

    private void datosLike() {
      mListener = mLikeProvider.NumLike(idPerson).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int size = value.size();
                String num = Integer.toString(size);
                txtMegusta.setText(num);
            }
        });

     mmListener = mLikeProvider.getLike(mLoginController.getUid(), idPerson).addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
              int size = value.size();
              if (size >0){
                  megusta.setImageResource(R.drawable.ic_baseline_like_blue);
                  banLike = 1;
              }else {
                  banLike = 0;
              }
          }
      });
    }

    private void VerificarFav() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FAVORITOS", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        Cursor fila = BaseDatos.rawQuery("select contenido from favoritos where id =" + idPerson, null);

        if (fila.moveToFirst()){
            favorite.setImageResource(R.drawable.ic_baseline_favorite_pink);
            txtFavorito.setText("EN LISTA DE FAVORITOS");
            BaseDatos.close();
            band = 1;
        }else {
            favorite.setImageResource(R.drawable.ic_baseline_favorite_gray);
            txtFavorito.setText("AÑADIR A LISTA DE FAVORITOS");
            BaseDatos.close();
            band = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null){
            mListener.remove();
        }
        if (mmListener != null){
            mmListener.remove();
        }
        if (mmmListener != null){
            mmmListener.remove();
        }
    }
}