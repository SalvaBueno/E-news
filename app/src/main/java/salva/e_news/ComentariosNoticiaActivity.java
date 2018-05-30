package salva.e_news;

import android.app.Activity;
import android.content.Intent;
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
import salva.e_news.modelos.Noticia;
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
    Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_noticia);
        if(getIntent().getExtras().getString(Tags.NOTICIA_PK)!=null){
            noticiapk = getIntent().getExtras().getString(Tags.NOTICIA_PK);
        }

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

    //METODO para obtener los datos que rellenaran los item del AdapterComentariosNoticia
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
            //En caso de que conecte y devuelva Tags.OK. Creamos un JsonArray con el tags que
            // pasamos del servidor que en este caso seria Tags.LISTA_COMENTARIOS (comentarios),
            // con el bucle for recorremos el array para crear por cada campo del array un valor de
            // tipo comentario que se añadira a un Arraylist de tipo comentario.
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
                //si no tiene comentarios creo una noticia para añadirle la pk
            }else if(p.contains(Tags.NO_COMENTARIOS)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Tags.PK, noticiapk);
                noticia = new Noticia(jsonObject);

                // Resultado falla por otro error.
            }else if (p.contains(Tags.ERROR)) {
                String msg = json.getString(Tags.MENSAJE);
                //mensaje = msg;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Metodo para hacer la peticion al servidor que registre el comentario del usuario.
    // Con las preferencias obtenemos el token y el id de usuario, y el pk de la noticia
    // lo obtenemos de los datos que obtenemos de los comentarios que ya tenga la noticia en el
    // caso de que no existan comentarios lo obtendremos de la noticia que creamos en
    // cargarcomentarios si no se devuelve ninguna pk del servidor
    public boolean registrarcomentario(){
        String noticiapk;
        String token = Preferencias.getToken(ComentariosNoticiaActivity.this);
        String usuario_id = Preferencias.getID(ComentariosNoticiaActivity.this);
        if(comentario == null){
            noticiapk = noticia.getPk();
        }else {
            noticiapk = comentario.getNoticia().getPk();
        }
        String contenido_comentario = anadircomentario.getText().toString();

        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN,token);
            json.put(Tags.USUARIO_ID,usuario_id);
            json.put(Tags.NOTICIA_PK, noticiapk);
            json.put(Tags.CONTENIDO_COMENTARIO,contenido_comentario);

        }catch (JSONException e){
            e.printStackTrace();
        }
        json = JSONUtil.hacerPeticionServidor("enews/registrar_comentarios/",json);

        try{
            String p = json.getString(Tags.RESULTADO);
            if (p.contains(Tags.ERRORCONEXION)) {
                return false;
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)) {
                onRestart();
                return true;
            }else if (p.contains(Tags.ERROR)) {
                String msg = json.getString(Tags.MENSAJE);
                return false;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return true;
    }
    //Cuando el comentario se envie correctamente recargamos la actividad para que aparezca
    // automaticamente.
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(ComentariosNoticiaActivity.this,ComentariosNoticiaActivity.class);
        try{
            i.putExtra(Tags.NOTICIA_PK, comentario.getNoticia().getPk());
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            i.putExtra(Tags.NOTICIA_PK,noticia.getPk());
        }catch (Exception e){
            e.printStackTrace();
        }

        startActivity(i);
        finish();

    }
}
