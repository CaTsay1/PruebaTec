package app.prueba.presentacionprueba.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.prueba.presentacionprueba.Activitys.DetallePersonajeActivity;
import app.prueba.presentacionprueba.Models.Favoritos;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.R;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Favoritos> options;
    private ArrayList<Resultados>resultados;


    public FavoritosAdapter(Context context, ArrayList<Favoritos> options){
        this.context = context;
        this. options = options;
        resultados = new ArrayList<Resultados>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_personajes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int pos = position;
        Favoritos model = options.get(pos);

        String resul = model.getResultados();
        String posici = model.getPosicion();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Resultados>>(){}.getType();
        resultados = gson.fromJson(resul,type);

        int p = Integer.valueOf(posici);

        Resultados mode = resultados.get(p);

        String nombre = mode.getName();
        holder.NombrePerson.setText(nombre);

        String estado = mode.getStatus();
        holder.EstadoPerson.setText(estado);

        String especie = mode.getSpecies();
        holder.EspeciePerson.setText(especie);


        String imagen = mode.getImage();
        if (imagen != null){
            if (!imagen.isEmpty()){
                Picasso.with(context).load(imagen).into(holder.imagenPerson);
            }
        }




        holder.viewHolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, DetallePersonajeActivity.class);
                intent.putExtra("position", p);
                intent.putExtra("List", resul);
              //  intent.putIntegerArrayListExtra("List", (ArrayList)options);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagenPerson;
        public  TextView NombrePerson;
        public TextView EstadoPerson;
        public  TextView EspeciePerson;
        View viewHolders;

        public ViewHolder(@NonNull View view) {
            super(view);

            imagenPerson = view.findViewById(R.id.imagen_PersonajeCard);
            NombrePerson = view.findViewById(R.id.Nombre_PersonajeCard);
            EstadoPerson = view.findViewById(R.id.Estado_PersonajeCard);
            EspeciePerson = view.findViewById(R.id.Especie_PersonajeCard);
            viewHolders = view;
        }
    }
}
