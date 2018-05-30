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
import android.widget.ImageView;

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
    String nombre_categoria;
    ImageView imgBack;

    public NoticiasFiltradoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nombre_categoria  = bundle.getString(Tags.NOMBRE_CATEGORIA, "null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias_filtrado, container, false);


        listaNoticias = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewNoticiasFiltro);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        //Boton para volver atras de fragmento a fragmento con un boton en la esquina, ya que el
        // boton back de android esta preparado para que minimize la aplicacion sin cerrar sesion para que el usuario pueda mantener la sesion iniciada.
        imgBack= view.findViewById(R.id.ImgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    NoticiasFiltradoFragment.super.getActivity().onBackPressed();
                    getFragmentManager().popBackStack();
                } else {
                    getFragmentManager().popBackStack();
                }
            }
        });

        cargarNoticias();

        AdapterNoticias adapterNoticias = new AdapterNoticias(listaNoticias,getContext());
        recyclerView.setAdapter(adapterNoticias);
        return view;

    }


    //Metodo que carga la noticia con la pk obtenida que recibimos en el onCreate con Bundle,
    // utilizamos el nombre de la categoria para filtrar las noticias.
   public void cargarNoticias() {

        String token = Preferencias.getToken(getActivity());
        String usuario_id = Preferencias.getID(getActivity());
        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USUARIO_ID, usuario_id);
            json.put(Tags.CATEGORIA_NOTICIA, nombre_categoria);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("JSON","este es el json--->"+json);
        //Se hace petición de login al servidor.
        json = JSONUtil.hacerPeticionServidor("enews/get_noticias/", json);

        try {
            String p = json.getString(Tags.RESULTADO);

            // Se comprueba la conexión al servidor.
            if (p.contains(Tags.ERRORCONEXION)) {
            // mensaje = "Error de conexión";
            }
            //En caso de que conecte y devuelva el tags.OK Se crea la listaNoticias, que se enviara
            // para el AdapterNoticias para que rellene los campos necesarios.
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
