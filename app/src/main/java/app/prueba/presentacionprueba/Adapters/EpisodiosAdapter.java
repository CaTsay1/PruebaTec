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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.prueba.presentacionprueba.Activitys.DetallePersonajeActivity;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.R;

public class EpisodiosAdapter extends RecyclerView.Adapter<EpisodiosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> options;

    RequestQueue requestQueue;


    public EpisodiosAdapter(Context context, ArrayList<String> options){
        this.context = context;
        this. options = options;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_episodios,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int pos = position;

        String url = options.get(pos);

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nombre = response.getString("name");
                            holder.NombreEpisodio.setText(nombre);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String numEpi = response.getString("episode");
                            holder.NumEpisodio.setText(numEpi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String lanzamiento = response.getString("air_date");
                            holder.LanzamientoEpi.setText(lanzamiento);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public  TextView NombreEpisodio;
        public TextView NumEpisodio;
        public  TextView LanzamientoEpi;
        View viewHolders;

        public ViewHolder(@NonNull View view) {
            super(view);

            NombreEpisodio = view.findViewById(R.id.Nombre_Episodio_Card);
            NumEpisodio = view.findViewById(R.id.Num_Episodio_Card);
            LanzamientoEpi = view.findViewById(R.id.Lanzamiento_Episodio_Card);
            viewHolders = view;
        }
    }
}
