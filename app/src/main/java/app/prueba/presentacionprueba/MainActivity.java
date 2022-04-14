package app.prueba.presentacionprueba;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

import app.prueba.presentacionprueba.ActivitysLogin.LoginActivity;
import app.prueba.presentacionprueba.Adapters.PersonajesAdapter;
import app.prueba.presentacionprueba.Api.API;
import app.prueba.presentacionprueba.Api.ApiService.RandMService;
import app.prueba.presentacionprueba.Controladores.LoginController;
import app.prueba.presentacionprueba.Controladores.PagerController;
import app.prueba.presentacionprueba.Models.RandM;
import app.prueba.presentacionprueba.Models.Resultados;
import app.prueba.presentacionprueba.Provider.FavoritosProvider;
import app.prueba.presentacionprueba.channel.AdminSQLiteOpenHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tab1, tab2;

    PagerController mPagerController;

    LoginController mLoginController;
    FavoritosProvider mFavoritosProvider;

    int size;
    String idUser;

    String idPerson;
    String posi;
    String resul;

    android.app.AlertDialog mDialog;
    AlertDialog.Builder mBuilderSelector;
    CharSequence optionsBuilder [];

    ListenerRegistration mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        idUser = mLoginController.getUid();
        toolbar();
        tabLayoutMet();
        opcionDialog();
    }

    private void opcionDialog() {
        mDialog =  new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Sincronizando Datos")
                .setCancelable(false).build();
    }

    public void toolbar(){
        mToolbar = findViewById(R.id.toolbarActivity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Rick and Morty");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected( MenuItem item) {
        if (item.getItemId() == R.id.itemLogout){
            logout();
        }
        else if (item.getItemId() == R.id.itemEmpleado){
            sincronizarDatos();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mLoginController.Logout();
        borrarDatos();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void borrarDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "FAVORITOS", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        BaseDatos.delete("favoritos","",null);
    }

    private void sincronizarDatos() {
      mListener = mFavoritosProvider.getFav(mLoginController.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                size = value.size();
                if (size > 0){
                    int con = 0;
                    int  bus = 1;
                    mDialog.show();
                    sincronizar(con, bus);
                }
                else {
                    Toast.makeText(MainActivity.this, "No Tienes Datos En La Nube", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sincronizar(int con, int bus) {
        String buscar = Integer.toString(bus);
        if (con < size){

            int a = con + 1;
            int b = bus + 1;

            mFavoritosProvider.getData(idUser + buscar).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()){
                        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity.this, "FAVORITOS", null, 1);
                        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

                        Cursor fila = BaseDatos.rawQuery("select contenido from favoritos where id =" + buscar, null);
                        if (fila.moveToFirst()){
                            BaseDatos.close();
                            sincronizar(a, b);
                        }else{
                            if (documentSnapshot.contains("id")){
                                idPerson = documentSnapshot.getString("id");
                            }
                            if (documentSnapshot.contains("posicion")){
                                posi = documentSnapshot.getString("posicion");
                            }
                            if (documentSnapshot.contains("resultados")){
                                resul = documentSnapshot.getString("resultados");
                            }

                            ContentValues registro = new ContentValues();

                            registro.put("id", idPerson);
                            registro.put("contenido", resul);
                            registro.put("pos", posi);

                            BaseDatos.insert("favoritos", null, registro);
                            BaseDatos.close();

                            sincronizar(a, b);
                        }

                    }else {
                        sincronizar(a, b);
                    }
                }
            });
        }else {
            mListener.remove();
            mDialog.dismiss();
            Toast.makeText(MainActivity.this, "Click En Actualizar Para Ver Los Datos Descargados", Toast.LENGTH_SHORT).show();
        }

    }

    private void tabLayoutMet() {
        viewPager.setAdapter(mPagerController);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    mPagerController.notifyDataSetChanged();
                }if (tab.getPosition() == 1){
                    mPagerController.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void inicializar(){

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        tab1 = findViewById(R.id.tabInicio);
        tab2 = findViewById(R.id.tabFavoritos);

        mLoginController = new LoginController();
        mFavoritosProvider = new FavoritosProvider();
        mPagerController = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null){
            mListener.remove();
        }
    }
}