package salva.e_news;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class CambiarDatosActivity extends AppCompatActivity {
    TextInputEditText nombre_usu, email_usu;
    Button enviar_datos;
    Usuario user;
    String nombre, correo, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_datos);
        cargarUsuario();
        cargarbotones();
        nombre_usu= findViewById(R.id.txtnombre_edit_campo);
        nombre_usu.setText(user.getUsername());
        email_usu= findViewById(R.id.txtemail_edit_campo);
        email_usu.setText(user.getCorreo());
    }
    public void cargarbotones(){
        nombre_usu= findViewById(R.id.txtnombre_edit_campo);
        email_usu = findViewById(R.id.txtemail_edit_campo);
        enviar_datos= findViewById(R.id.bttn_cambiar_datos);
        enviar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(nombre_usu.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(email_usu.getWindowToken(), 0);
                if(user.getUsername().equals(nombre_usu.getText().toString()) && user.getCorreo().equals(email_usu.getText().toString())){
                    mensaje=getResources().getString(R.string.need_cambio_datos);
                } else{
                    editarUsuario();
                }

                Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();


            }
        });
    }

    public void cargarUsuario() {
        //Creamos el JSON que vamos a mandar al servidor
        JSONObject jsonConsulta = new JSONObject();
        try{
            jsonConsulta.put(Tags.USUARIO_ID, Usuario.getID(CambiarDatosActivity.this));
            jsonConsulta.put(Tags.TOKEN, Usuario.getToken(CambiarDatosActivity.this));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        //Hacemos petición de lista de centros al servidor
        jsonConsulta = JSONUtil.hacerPeticionServidor("usuarios/java/get_perfil/",jsonConsulta);

        try{
            String p = jsonConsulta.getString(Tags.RESULTADO);

            //Comprobamos la conexión con el servidor
            if (p.contains(Tags.ERRORCONEXION)){
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show(); //Si no conecta imprime un toast
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)){
                user = new Usuario(jsonConsulta);
            }

            //resultado falla por otro error
            else if (p.contains(Tags.ERROR)){
                String msg = jsonConsulta.getString(Tags.MENSAJE);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }



    public void editarUsuario() {
        nombre = nombre_usu.getText().toString();
        correo = email_usu.getText().toString();

        if(user.getNombre().equals(nombre) &&  user.getCorreo().equals(correo)){
            mensaje=getResources().getString(R.string.cambios_para_editar);
        }
        else {
            //Sobreescribimos el usuario con los valores del formulario
            user.setNombre(nombre_usu.getText().toString());
            user.setCorreo(email_usu.getText().toString());

            //Creamos el JSON que vamos a mandar al servidor
            JSONObject jsonConsulta = new JSONObject();
            try{
                jsonConsulta.put(Tags.USUARIO_ID, Usuario.getID(CambiarDatosActivity.this));
                jsonConsulta.put(Tags.TOKEN, Usuario.getToken(CambiarDatosActivity.this));
                jsonConsulta.put(Tags.NOMBRE, user.getNombre());
                jsonConsulta.put(Tags.APELLIDOS, user.getApellidos());
                jsonConsulta.put(Tags.EMAIL, user.getCorreo());
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            //Hacemos petición de lista de centros al servidor
            jsonConsulta = JSONUtil.hacerPeticionServidor("usuarios/java/cambiar_datos/",jsonConsulta);

            try{
                String p = jsonConsulta.getString(Tags.RESULTADO);

                //Comprobamos la conexión con el servidor
                if (p.contains(Tags.ERRORCONEXION)){
                    mensaje=getResources().getString(R.string.error_conexion);
                }
                //En caso de que conecte
                else if (p.contains(Tags.OK)){
                    mensaje=getResources().getString(R.string.cambio_datos_ok);
                    finish();
                }

                //resultado falla por otro error
                else if (p.contains(Tags.ERROR)){
                    String msg = jsonConsulta.getString(Tags.MENSAJE);
                    mensaje=msg;
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
