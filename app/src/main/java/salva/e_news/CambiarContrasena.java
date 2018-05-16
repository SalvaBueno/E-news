package salva.e_news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class CambiarContrasena extends AppCompatActivity {
    EditText pass_actual,pass_nueva, pass_confirmar;
    Button cambiarpass;
    String passAntigua, passNueva,mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);
        cargarbotones();

    }

    public void cargarbotones(){
        pass_actual = findViewById(R.id.edit_contrasena_actual);
        pass_nueva = findViewById(R.id.edit_nueva_contrasena);
        pass_confirmar = findViewById(R.id.edit_confirmar_contrasena);
        cambiarpass = findViewById(R.id.bttn_cambio_pass);
        cambiarpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(pass_actual.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(pass_nueva.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(pass_confirmar.getWindowToken(), 0);
                if(!pass_nueva.getText().toString().equals(pass_confirmar.getText().toString())){
                    mensaje=getResources().getString(R.string.pass_dont_match);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else{
                    if(pass_nueva.getText().toString().length()<4){
                        mensaje=getResources().getString(R.string.pass_min_cuatro);
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    else if (cambiarPass()) {
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Intent intentmain = getIntent();
                        setResult(Activity.RESULT_OK, intentmain);
                        finish();
                    }else{
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        mensaje="";
                    }

                }
            }
        });

    }

    public boolean cambiarPass() {

        passAntigua = pass_actual.getText().toString();
        passNueva = pass_nueva.getText().toString();

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject jsonConsulta = new JSONObject();
        try{
            jsonConsulta.put(Tags.USUARIO_ID, Usuario.getID(CambiarContrasena.this));
            jsonConsulta.put(Tags.TOKEN, Usuario.getToken(CambiarContrasena.this));
            jsonConsulta.put(Tags.PASSWORD_ANTIGUA, passAntigua);
            jsonConsulta.put(Tags.PASSWORD_NUEVA, passNueva);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        //Hacemos petición de lista de centros al servidor
        jsonConsulta = JSONUtil.hacerPeticionServidor("usuarios/java/cambiar_pass/",jsonConsulta);

        try{
            String p = jsonConsulta.getString(Tags.RESULTADO);

            //Comprobamos la conexión con el servidor
            if (p.contains(Tags.ERRORCONEXION)){
                mensaje= getResources().getString(R.string.error_conexion);
                return false;
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)){
                setResult(Tags.LOGIN_OK);
                finish();
                mensaje=getResources().getString(R.string.cambio_contrasena_correcto);
                return true;
            }

            //resultado falla por otro error
            else if (p.contains(Tags.ERROR)){
                String msg = jsonConsulta.getString(Tags.MENSAJE);
                mensaje=msg;
                return false;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}

