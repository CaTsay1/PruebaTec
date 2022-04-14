package app.prueba.presentacionprueba.Activitys.FragmentNav;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.prueba.presentacionprueba.Adapters.FavoritosAdapter;
import app.prueba.presentacionprueba.Adapters.PersonajesAdapter;
import app.prueba.presentacionprueba.Models.Favoritos;
import app.prueba.presentacionprueba.R;
import app.prueba.presentacionprueba.channel.AdminSQLiteOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {

    View mView;

    private ArrayList<Favoritos>Listfavoritos;
    private FavoritosAdapter mFavoritosAdapter;

    private RecyclerView mRecyclerView;
    private TextView btn_Actualizar;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favoritos, container, false);
        inicializar();
        ObtenerDatos();
        onClick();
        return  mView;
    }

    private void onClick() {
        btn_Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listfavoritos.clear();
                ObtenerDatos();
            }
        });
    }

    private void inicializar() {

        btn_Actualizar = mView.findViewById(R.id.btn_Actualizar);

        mRecyclerView = mView.findViewById(R.id.Recycler_Favoritos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Listfavoritos = new ArrayList<Favoritos>();

    }

    private void ObtenerDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "FAVORITOS", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        Favoritos favoritos = null;

        String posicion;
        String id;
        String Resutaldos;

        Cursor fila = BaseDatos.rawQuery("select id, contenido, pos from favoritos", null);

        if (fila.moveToFirst()){

            do{
                favoritos = new Favoritos();

                favoritos.setId(fila.getString(0));
                favoritos.setResultados(fila.getString(1));
                favoritos.setPosicion(fila.getString(2));

                Listfavoritos.add(favoritos);
            }while (fila.moveToNext());
        }

        BaseDatos.close();
        getAllFavorites();

    }

    private void getAllFavorites() {
        mFavoritosAdapter = new FavoritosAdapter(getContext(), Listfavoritos);
        mFavoritosAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mFavoritosAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        Listfavoritos.clear();
        ObtenerDatos();
    }
}