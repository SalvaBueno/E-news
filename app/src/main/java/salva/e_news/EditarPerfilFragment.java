package salva.e_news;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {
    Button cerrar_sesion, cambiardatos,cambiarcontrasena;
    Usuario user;
    TextView user_name,user_email;


    public EditarPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        cargarUsuario();

        user_name= view.findViewById(R.id.tv_nombre_editar_perfil);
        user_name.setText(user.getUsername());
        user_email= view.findViewById(R.id.tv_email_editar_perfil);
        user_email.setText(user.getCorreo());

        cerrar_sesion = view.findViewById(R.id.btn_cerrar_sesion);
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarsesion();
            }
        });
        cambiardatos = view.findViewById(R.id.editardatos);
        cambiardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CambiarDatosActivity.class);
                startActivity(i);
            }
        });
        cambiarcontrasena = view.findViewById(R.id.cambiarcontraseña);
        if(user.isLogingoogle()){
            cambiarcontrasena.setVisibility(View.GONE);
        }
        cambiarcontrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CambiarContrasena.class);
                startActivity(i);
            }
        });
        return view;
    }
    //Metodo que utlizamos para cerrar la sesion, este metodo llamara al metodo deleteAppData()
    // para borrar todos los datos internos de la aplicacion. Esto es necesario hacerlo por culpa
    // del logeo de usuarios de google, ya que si no se borran los datos la proxima vez que se
    // intente entrar con un usuario de google no te dejara seleccionar cuenta y solo te dejara
    // entrar con la misma anteriormente utilizada
    private void cerrarsesion() {
        JSONObject jsonEnviado = new JSONObject();
        try {
            jsonEnviado.put(Tags.USUARIO_ID, Usuario.getIDToken(Usuario.getToken(getActivity())));
            jsonEnviado.put(Tags.TOKEN, Usuario.getToken(getActivity()));

            JSONObject jsonRecibido = new JSONObject();
            jsonRecibido = JSONUtil.hacerPeticionServidor(
                    "usuarios/java/logout/", jsonEnviado);

            //Borramos el token del usuario al cerrar la sesion
            Usuario.borrarToken(getContext());
            ((MainActivity)getActivity()).lanzarLogin();

            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_LONG).show();
            deleteAppData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getContext(),"Problemas al cerrar sesion", Toast.LENGTH_LONG).show();
        }

    }

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Metodo que hacemos al iniciar la actividad para cargar los datos del usuario que esten en
    // el servidor
    public void cargarUsuario() {

        JSONObject jsonConsulta = new JSONObject();
        try{
            jsonConsulta.put(Tags.USUARIO_ID, Usuario.getID(getActivity()));
            jsonConsulta.put(Tags.TOKEN, Usuario.getToken(getActivity()));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        //Hacemos petición de los datos del usuario
        jsonConsulta = JSONUtil.hacerPeticionServidor("usuarios/java/get_perfil/",jsonConsulta);

        try{
            String p = jsonConsulta.getString(Tags.RESULTADO);

            //Comprobamos la conexión con el servidor
            if (p.contains(Tags.ERRORCONEXION)){
                //Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show(); //Si no conecta imprime un toast
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)){
                user = new Usuario(jsonConsulta);
            }

            //resultado falla por otro error
            else if (p.contains(Tags.ERROR)){
                String msg = jsonConsulta.getString(Tags.MENSAJE);
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


}
