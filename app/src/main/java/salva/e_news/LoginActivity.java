package salva.e_news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class LoginActivity extends AppCompatActivity {
TextInputEditText email_usu;
TextInputEditText pass_usu;
TextInputLayout textInputLayout_email;
TextInputLayout textInputLayout_pass;
Button entrar, registrar;
String mensaje="";
TextView recordar_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_usu = findViewById(R.id.email_usu);
        pass_usu = findViewById(R.id.pass_usu);
        textInputLayout_email= findViewById(R.id.textInputLayout_email);
        textInputLayout_pass = findViewById(R.id.textInputLayout_pass);
        cargarBotones();
    }

    private void cargarBotones() {
        entrar = findViewById(R.id.button_entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(email_usu.getText().toString().isEmpty() || email_usu.getText().toString().equals("") ||
                        pass_usu.getText().toString().isEmpty() || pass_usu.getText().toString().equals("")) {
                    mensaje=getResources().getString(R.string.debe_rellenar_campos);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else if (login()) {
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Intent intentmain = getIntent();
                        setResult(Activity.RESULT_OK, intentmain);
                        finish();
                }else{
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    mensaje="";
                }
            }
        });

        registrar = findViewById(R.id.button_registrar);
        registrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegistrarActivity.class);
                startActivity(intent);
            }

        });
        recordar_pass = findViewById(R.id.text_recordar_pass);
        recordar_pass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RecordarPass.class);
                startActivity(intent);
            }

        });

    }
    @Override
    public void onBackPressed() {
        /*
         * Con las siguientes lineas conseguimos que al salir de la aplicacion,
         * siga funcionando en segundo plano
         */
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        moveTaskToBack(true);
        startActivity(intent);
    }

    /**
     * Logea en el servidor. Crea un JSON y confirma si la pass es correcta.
     */
    protected boolean login() {
        String usuario = email_usu.getText().toString();
        String pass = pass_usu.getText().toString();

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.USUARIO, usuario);
            json.put(Tags.PASSWORD, pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Hacemos petición de login al servidor
        json = JSONUtil.hacerPeticionServidor("usuarios/java/login/", json); //En contacto activity, es sesiones/java/getcentros

        try {
            String p = json.getString(Tags.RESULTADO);

            //Comprobamos la conexión con el servidor
            if (p.contains(Tags.ERRORCONEXION)) {
                mensaje= getResources().getString(R.string.error_conexion);
               // Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)) {
                Usuario.guardarEnPref(this, usuario, json.getString(Tags.TOKEN)); //Guarda en las preferencias la token
                mensaje=getResources().getString(R.string.logeo_correcto);
                setResult(Tags.LOGIN_OK);
                finish();
                return true;
            }
            //resultado falla por otro error
            else if (p.contains(Tags.ERROR)) {
                String msg = json.getString(Tags.MENSAJE);
                mensaje=msg;
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}
