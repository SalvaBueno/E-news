package salva.e_news;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class RegistrarActivity extends AppCompatActivity {
    Button registro_usu;
    TextInputEditText pass_usu,email_usu,nombre_usu,confirmar_pass_usu;
    String mensaje = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        pass_usu = (TextInputEditText)findViewById(R.id.pass);
        email_usu = (TextInputEditText)findViewById(R.id.email_usu);
        nombre_usu = (TextInputEditText)findViewById(R.id.nombre_usu);
        confirmar_pass_usu =(TextInputEditText)findViewById(R.id.confirmar_pass);

        registro_usu =(Button) findViewById(R.id.btn_registro);
        registro_usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass_usu.getText().toString().equals(confirmar_pass_usu.getText().toString())) {
                    mensaje= getResources().getString(R.string.pass_dont_match);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if(!nombre_usu.getText().toString().isEmpty() || !nombre_usu.getText().toString().equals("") ||
                        !email_usu.getText().toString().isEmpty() || !email_usu.getText().toString().equals("")){

                    String nombre_usuario = nombre_usu.getText().toString();
                    String email_usuario = email_usu.getText().toString();
                    String pass_usuario = pass_usu.getText().toString();

                    //Creamos el JSON que vamos a mandar al servidor
                    JSONObject json = new JSONObject();
                    try{
                        json.put(Tags.USUARIO, nombre_usuario);
                        json.put(Tags.EMAIL, email_usuario);
                        json.put(Tags.PASSWORD, pass_usuario);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                    //Hacemos petición de login al servidor
                    json = JSONUtil.hacerPeticionServidor("usuarios/java/registrar_usuario/",json); //En contacto activity, es sesiones/java/getcentros

                    try{
                        String p = json.getString(Tags.RESULTADO);
                        if (p.contains(Tags.ERRORCONEXION)){
                            mensaje= getResources().getString(R.string.error_conexion);
                            Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }else if (p.contains(Tags.OK)){
                            mensaje= getResources().getString(R.string.usuario_creado);
                            Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            finish();
                        }else if (p.contains(Tags.ERROR)){
                            String msg = json.getString(Tags.MENSAJE);
                            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                } else{
                    mensaje= getResources().getString(R.string.debe_rellenar_campos);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }

        });

    }
}
