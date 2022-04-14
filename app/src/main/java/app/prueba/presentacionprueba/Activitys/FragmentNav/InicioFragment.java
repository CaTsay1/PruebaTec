package app.prueba.presentacionprueba.Activitys.FragmentNav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import app.prueba.presentacionprueba.Adapters.PersonajesAdapter;
import app.prueba.presentacionprueba.Api.API;
import app.prueba.presentacionprueba.Api.ApiService.RandMService;
import app.prueba.presentacionprueba.MainActivity;
import app.prueba.presentacionprueba.Models.RandM;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    View mView;

    API mApi;
    private static final String TAG = "InicioFragment";

    private RecyclerView mRecyclerView;
    private Resultados resultados;
    private PersonajesAdapter mPersonajesAdapter;
    private ArrayList<PersonajesAdapter> lista;
    private ArrayList<Resultados> result;

    TextView btn_add, btn_subtract;
    TextInputEditText txt_pagina;

    String NewCant;
    int can, getCant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        mView = inflater.inflate(R.layout.fragment_inicio, container, false);
        inicializar();
        Listener("1");
        setListener();
        onClick();
        return mView;
    }

    private void onClick() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarCantidad();
            }
        });

        btn_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restarCantidad();
            }
        });
    }

    private void sumarCantidad() {
        String gC = txt_pagina.getText().toString().trim();
        getCant = Integer.valueOf(gC);
        if (getCant >= 50){
            Toast.makeText(getContext(), "Numero Demasiado Grande", Toast.LENGTH_SHORT).show();
        }
        else {
            int newCan = getCant+1;
            can = newCan;
            NewCant = Integer.toString(newCan);
            txt_pagina.setText(NewCant);
            Listener(NewCant);
            if (can <= 1){
                btn_subtract.setVisibility(View.GONE);
            }else {
                btn_subtract.setVisibility(View.VISIBLE);
            }
        }
    }

    private void restarCantidad() {
        String gC = txt_pagina.getText().toString().trim();
        getCant = Integer.valueOf(gC);
        if (getCant >= 50){
            Toast.makeText(getContext(), "Numero Demasiado Grande", Toast.LENGTH_SHORT).show();
        }
        else if (getCant == 1){
            btn_subtract.setVisibility(View.GONE);
        }
        else if (getCant > 1){
            int newCan = getCant - 1;
            can = newCan;
            NewCant = Integer.toString(newCan);
            txt_pagina.setText(NewCant);
            Listener(NewCant);
            if (can <= 1){
                btn_subtract.setVisibility(View.GONE);
            }
        }
    }

    private void setListener() {
        txt_pagina.addTextChangedListener(textWatcher);
    }

    public void Listener(String pag) {
        RandMService service = API.getApi().create(RandMService.class);

        // Call<RandM> randMCall = service.getInfo("1");
        Call<RandM> randMCall = service.getInfo(pag);

        randMCall.enqueue(new Callback<RandM>() {
            @Override
            public void onResponse(Call<RandM> call, Response<RandM> response) {
                RandM randM = response.body();
                result = (ArrayList<Resultados>) randM.getResultados();

                Gson gson = new Gson();
                String Rs = gson.toJson(result);

                mPersonajesAdapter = new PersonajesAdapter(getContext(), result, Rs);
                mPersonajesAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mPersonajesAdapter);

            }

            @Override
            public void onFailure(Call<RandM> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t, Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error:" + t);
            }
        });

    }

    private void inicializar() {

        txt_pagina = mView.findViewById(R.id.Pagina_Personajes);
        btn_add = mView.findViewById(R.id.add_Personajes);
        btn_subtract = mView.findViewById(R.id.Subtract_Personajes);

        mRecyclerView = mView.findViewById(R.id.Recycler_Personajes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //cada vez q el usuario escriba en la caja de texto revisamos que cumpla con los requisitos llamando al siguiente metodo
            String textW = txt_pagina.getText().toString().trim();
            if (textW.equals("")){
                Toast.makeText(getContext(), "Escriba Una Pagina", Toast.LENGTH_SHORT).show();
            }else {
                can = Integer.valueOf(textW);
                if (can <= 1){
                    btn_subtract.setVisibility(View.GONE);
                    Listener(textW);
                }else if (can > 1 && can < 50 ){
                    btn_subtract.setVisibility(View.VISIBLE);
                    Listener(textW);
                }else if (can >= 50){
                    Toast.makeText(getContext(), "Numero Demasiado Grande", Toast.LENGTH_SHORT).show();
                }

            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}