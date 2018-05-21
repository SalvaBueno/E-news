package salva.e_news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Noticia;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;

public class DetalleNoticiaActivity extends AppCompatActivity {
    private Noticia noticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);
        recogerDatosNoticia();
        cargarNoticias();
    }

    private boolean recogerDatosNoticia() {
        try {
            noticia = getIntent().getExtras().getParcelable("noticia");
            return true;
        } catch (Exception e) {
            Log.v("Exception", e.toString());
            return false;
        }
    }

    public void cargarNoticias() {

        String token = Preferencias.getToken(DetalleNoticiaActivity.this);
        String usuario_id = Preferencias.getID(DetalleNoticiaActivity.this);
        String pk_noticia = noticia.getPk();

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USUARIO_ID, usuario_id);
            json.put(Tags.NOTICIA_PK, pk_noticia);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("JSON","este es el json--->"+json);
        //Se hace petición de login al servidor.
        json = JSONUtil.hacerPeticionServidor("enews/get_noticia/", json);

        try {
            String p = json.getString(Tags.RESULTADO);

            // Se comprueba la conexión al servidor.
            if (p.contains(Tags.ERRORCONEXION)) {
                // mensaje = "Error de conexión";
            }
            //En caso de que conecte y no haya animales para dicha busqueda.
            else if (p.contains(Tags.OK)) {
                Noticia noticia = new Noticia(json);
                //LLEGA HASTA AQUI ASI QUE WORTH!!!
            }
            // Resultado falla por otro error.
            else if (p.contains(Tags.ERROR)) {
                String msg = json.getString(Tags.MENSAJE);
                //mensaje = msg;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
