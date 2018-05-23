package salva.e_news;

import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import salva.e_news.modelos.Comentario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;

public class ComentariosNoticiaActivity extends AppCompatActivity {
    String noticiapk;
    ArrayList<Comentario> listaComentario;
    Comentario comentario;
    TextInputEditText anadircomentario;
    Button enviarcomentario;
    String mensaje;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_noticia);
        noticiapk = getIntent().getExtras().getString(Tags.NOTICIA_PK);

        cargarbotones();
        cargarComentarios();
        AdapterComentariosNoticia adaptadorComentarios = new AdapterComentariosNoticia(listaComentario);
        recyclerView.setAdapter(adaptadorComentarios);
    }

    public void cargarbotones(){
        anadircomentario = findViewById(R.id.anadircomentario);
        enviarcomentario = findViewById(R.id.enviarcomentario);
        enviarcomentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enviarcomentario.equals("") && enviarcomentario == null){
                    mensaje=getResources().getString(R.string.comentariovacio);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else{
                    if(registrarcomentario()){
                        mensaje=getResources().getString(R.string.comentarioregistrado);
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }else {
                        mensaje=getResources().getString(R.string.comentarionoregistrado);
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });

        listaComentario = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclercomentarionoticia);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
    }

    public void cargarComentarios() {
        String token = Preferencias.getToken(ComentariosNoticiaActivity.this);
        String usuario_id = Preferencias.getID(ComentariosNoticiaActivity.this);
        String pk_noticia = noticiapk;

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USUARIO_ID, usuario_id);
            json.put(Tags.NOTICIA_PK, pk_noticia);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Se hace petición de login al servidor.
        json = JSONUtil.hacerPeticionServidor("enews/get_comentarios_noticia/", json);

        try {
            String p = json.getString(Tags.RESULTADO);

            // Se comprueba la conexión al servidor.
            if (p.contains(Tags.ERRORCONEXION)) {
                // mensaje = "Error de conexión";
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)) {
                JSONArray array = json.getJSONArray(Tags.LISTA_COMENTARIOS);
                Log.v("ComentariooARRAY", array.toString());
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        comentario = new Comentario(array.getJSONObject(i));
                        Log.v("Comentarioo1", "ESTO SE SUPONE QUE ES EL NOMBRE DE LA NOTICIA-->"+comentario.getFecha_comentario());
                        Log.v("Comentarioo2", "ESTO SE SUPONE QUE ES EL NOMBRE DEL USUARIO-->"+comentario.getUsuario().getUsername());
                        Log.v("Comentarioo3", "ESTO SE SUPONE QUE ES EL CONTENIDO-->"+comentario.getContenido_comentario());
                        listaComentario.add(comentario);
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

    public boolean registrarcomentario(){
        return true;
    }
}
