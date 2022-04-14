package app.prueba.presentacionprueba.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.prueba.presentacionprueba.Adapters.EpisodiosAdapter;
import app.prueba.presentacionprueba.MainActivity;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.R;

public class EpisodiosActivity extends AppCompatActivity {

    String Epi, Image;

    ImageView imgPersonaje;

    ArrayList<String>Episodios;

    private RecyclerView mRecyclerView;
    EpisodiosAdapter mEpisodiosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodios);
        Epi = getIntent().getStringExtra("Episodios");
        Image = getIntent().getStringExtra("Imagen");
        inicializar();
        datos();
        Listener();
    }

    private void Listener() {
        mEpisodiosAdapter = new EpisodiosAdapter(EpisodiosActivity.this, Episodios);
        mEpisodiosAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mEpisodiosAdapter);
    }

    private void datos() {
        if (Image != null){
            if (!Image.isEmpty()){
                Picasso.with(EpisodiosActivity.this).load(Image).into(imgPersonaje);
            }
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        Episodios = gson.fromJson(Epi,type);
    }

    private void inicializar() {
        imgPersonaje = findViewById(R.id.imagen_Personaje_Episodios);

        mRecyclerView = findViewById(R.id.Recycler_Episodios);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EpisodiosActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}