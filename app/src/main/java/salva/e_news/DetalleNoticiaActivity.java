package salva.e_news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Noticia;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;
import salva.e_news.util.DescargarImagen;

public class DetalleNoticiaActivity extends AppCompatActivity {
    private Noticia noticia;
    TextView tv_nombre_noticia,tv_titular_noticia,tv_contenido_noticia, tv_fecha_noticia, textViewComentarios;
    ImageView img_noticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);
        cargarBotones();
        recogerDatosNoticia();
        cargarNoticias();
    }

    private boolean recogerDatosNoticia() {
        try {
            noticia = getIntent().getExtras().getParcelable("noticia");
            Log.v("noticia",noticia.getNombre_noticia());
            return true;
        } catch (Exception e) {
            Log.v("Exception", e.toString());
            return false;
        }
    }
    public void cargarBotones(){
       tv_nombre_noticia = findViewById(R.id.tv_nombre_noticia);
       tv_titular_noticia = findViewById(R.id.tv_titular_noticia);
       tv_contenido_noticia = findViewById(R.id.tv_contenido_noticia);
       tv_fecha_noticia = findViewById(R.id.tv_fecha_noticia);
       img_noticia= findViewById(R.id.img_noticia);
       textViewComentarios = findViewById(R.id.textViewComentarios);
       textViewComentarios.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Intent i = new Intent(DetalleNoticiaActivity.this,ComentariosNoticiaActivity.class);
                startActivity(i);
           }
       });

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
                if(noticia != null) {
                    tv_nombre_noticia.setText(noticia.getNombre_noticia());
                    tv_titular_noticia.setText(noticia.getTitular_noticia());
                    tv_contenido_noticia.setText(noticia.getContenido_noticia());
                    tv_fecha_noticia.setText(noticia.getFecha_noticia());
                    if(!noticia.getRutaImagen().equals("")){
                        Bitmap bitmap = DescargarImagen.descargarImagen(Tags.SERVIDOR +"static/media/"+noticia.getRutaImagen());
                        img_noticia.setImageBitmap(bitmap);
                    }
                }
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
