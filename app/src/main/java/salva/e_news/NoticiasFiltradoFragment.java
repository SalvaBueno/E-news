package salva.e_news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import salva.e_news.modelos.Noticia;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Preferencias;
import salva.e_news.peticionesBD.Tags;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFiltradoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Noticia> listaNoticias;
    RecyclerView recyclerView;

    public NoticiasFiltradoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias_filtrado, container, false);


        listaNoticias = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewNoticiasFiltro);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        //cargarNoticias();

        AdapterNoticias adapterNoticias = new AdapterNoticias(listaNoticias);
        recyclerView.setAdapter(adapterNoticias);
        return view;

    }

    //METODO PARA RELLENAR LOS ITEMS

   public void cargarNoticias() {
        String token = Preferencias.getToken(getActivity());
        String usuario_id = Preferencias.getID(getActivity());
        //STRING AÑADIR CATEGORIA !!!!!!!!!!!!!!!!!;

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USUARIO_ID, usuario_id);
            //json.put(Tags.CATEGORIA_NOTICIA, categoria_noticia);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Se hace petición de login al servidor.
        json = JSONUtil.hacerPeticionServidor("enews/get_noticias/", json);

        try {
            String p = json.getString(Tags.RESULTADO);

            // Se comprueba la conexión al servidor.
            if (p.contains(Tags.ERRORCONEXION)) {
            // mensaje = "Error de conexión";
            }
            //En caso de que conecte y no haya animales para dicha busqueda.
             else if (p.contains(Tags.OK)) {
                String res = json.getString(Tags.RESULTADO);
                JSONArray array = json.getJSONArray(Tags.LISTA_NOTICIAS);
                Log.v("noticiasjson", array.toString());
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        Noticia noticia = new Noticia(array.getJSONObject(i));
                        Log.v("Noticias BUCLE", noticia.toString());
                        listaNoticias.add(noticia);
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
