package app.prueba.presentacionprueba.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.prueba.presentacionprueba.Activitys.DetallePersonajeActivity;
import app.prueba.presentacionprueba.MainActivity;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.R;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Resultados> options;
    private String resultados;



    public PersonajesAdapter (Context context, ArrayList<Resultados> options, String resultados){
        this.context = context;
        this. options = options;
        this.resultados = resultados;
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
        Resultados model = options.get(position);

        String nombre = model.getName();
        holder.NombrePerson.setText(nombre);

        String estado = model.getStatus();
        holder.EstadoPerson.setText(estado);

        String especie = model.getSpecies();
        holder.EspeciePerson.setText(especie);



        String imagen = model.getImage();
        if (imagen != null){
            if (!imagen.isEmpty()){
                Picasso.with(context).load(imagen).into(holder.imagenPerson);
            }
        }




        holder.viewHolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, DetallePersonajeActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("List", resultados);
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
