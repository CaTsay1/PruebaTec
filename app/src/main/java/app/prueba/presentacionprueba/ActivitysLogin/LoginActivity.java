package app.prueba.presentacionprueba.ActivitysLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;

import app.prueba.presentacionprueba.Controladores.LoginController;
import app.prueba.presentacionprueba.R;
import app.prueba.presentacionprueba.utils.EmailUtils;

public class LoginActivity extends AppCompatActivity {

    private Button btnRegistrar, btnEntrar;
    private TextInputEditText txtCorreo, txtContraseña;
    private TextView btnRecuperarContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializar();
        setlisener();
        onclick();
    }

    private void setlisener(){

        txtCorreo.addTextChangedListener(textWatcher);
        txtContraseña.addTextChangedListener(textWatcher);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginController.iniciarSesion(getCorreo(),getContraseña(),
                        new WeakReference<>(LoginActivity.this),
                        new WeakReference<>(txtContraseña));

            }
        });

        txtContraseña.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if (puedoIniciarSesion()){
                        LoginController.iniciarSesion(getCorreo(),getContraseña(),
                                new WeakReference<>(LoginActivity.this),
                                new WeakReference<>(txtContraseña));
                    }
                }

                return false;
            }
        });
    }

    public String getCorreo() {
        return txtCorreo.getText().toString();
    }

    public String getContraseña() {
        return txtContraseña.getText().toString();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            puedoIniciarSesion();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean puedoIniciarSesion(){
        String correo = getCorreo().trim();
        String contraseña = getContraseña().trim();

        if (EmailUtils.esCorreoValido(correo) && contraseña.length()>5){
            btnEntrar.setEnabled(true);
            return true;

        }else {
            btnEntrar.setEnabled(false);
            return false;
        }

    }

    private void onclick() {

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(LoginActivity.this, RegsitroActivity.class));


            }
        });

        btnRecuperarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(LoginActivity.this,RecuperarContraActivity.class));

            }
        });
    }

    private void inicializar() {
        this.btnRegistrar = findViewById(R.id.btnRegistro);
        this.btnEntrar = findViewById(R.id.btnEntrarLogin);
        this.txtCorreo = findViewById(R.id.Txt_Correo_Login);
        this.txtContraseña = findViewById(R.id.Txt_Contraseña_Login);
        this.btnRecuperarContraseña= findViewById(R.id.btnRecuperarContraseña);
    }
}