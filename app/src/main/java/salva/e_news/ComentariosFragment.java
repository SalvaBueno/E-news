package salva.e_news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import salva.e_news.modelos.Comentario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Comentario> listaComentario;
    RecyclerView recyclerView;
    Comentario comentario;

    public ComentariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comentarios, container, false);

        listaComentario = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerViewcomentarios);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        cargarComentarios();

        AdapterComentarios adaptadorComentarios = new AdapterComentarios(listaComentario);
        recyclerView.setAdapter(adaptadorComentarios);

        return view;
    }

    //METODO para obtener los datos que rellenaran los item del AdapterComentarios.
   public void cargarComentarios() {
        String token = Preferencias.getToken(getActivity());
        String usuario_id = Preferencias.getID(getActivity());
        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USUARIO_ID, usuario_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Se hace petición para obtener los comentarios al servidor
        json = JSONUtil.hacerPeticionServidor("enews/get_comentarios/", json);

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
                        Log.v("Comentarioo1", "ESTO SE SUPONE QUE ES EL NOMBRE DE LA NOTICIA-->"+comentario.getNoticia().getNombre_noticia());
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


    @Override
    public void onResume() {
        super.onResume();
    }

}
