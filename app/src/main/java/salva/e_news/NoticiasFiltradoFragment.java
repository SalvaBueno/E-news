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

import java.util.ArrayList;

import salva.e_news.modelos.Noticia;
/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFiltradoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Noticia> listaNoticia;
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


        listaNoticia = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewNoticiasFiltro);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        //cargarNoticias();

        AdapterNoticias adaptadorAnimales = new AdapterNoticias(listaNoticia);
        recyclerView.setAdapter(adaptadorAnimales);

        return view;

    }

    //METODO PARA RELLENAR LOS ITEMS

   /* public void cargarNoticias() {
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

        //Se hace petición de login al servidor.
        json = JSONUtil.hacerPeticionServidor("protectora/cargar_animales/", json);

        try {
            String p = json.getString(Tags.RESULTADO);

            // Se comprueba la conexión al servidor.
            if (p.contains(Tags.ERRORCONEXION)) {
            // mensaje = "Error de conexión";
            }
            //En caso de que conecte y no haya animales para dicha busqueda.
            else if (p.contains(Tags.OK_SIN_ANIMALES)) {
                Toast.makeText(getContext(), ucFirst(json.getString(Tags.MENSAJE)), Toast.LENGTH_LONG).show();
            } else if (p.contains(Tags.OK)) {
                String res = json.getString(Tags.RESULTADO);
                JSONArray array = json.getJSONArray(Tags.LISTA_ANIMALES);
                Log.v("animalesjson", array.toString());
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        Comentario comentario = new Comentario(array.getJSONObject(i));
                        Log.v("Comentario BUCLEEEe", comentario.toString());
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
    }*/


    @Override
    public void onResume() {
        super.onResume();
    }


}
