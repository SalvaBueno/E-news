package salva.e_news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {
    Button cerrar_sesion;


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
        cerrar_sesion = view.findViewById(R.id.btn_cerrar_sesion);
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarsesion();
            }
        });
        return view;
    }

    private void cerrarsesion() {
        JSONObject jsonEnviado = new JSONObject();
        try {
            jsonEnviado.put(Tags.USUARIO_ID, Usuario.getIDToken(Usuario.getToken(getActivity())));
            jsonEnviado.put(Tags.TOKEN, Usuario.getToken(getActivity()));

            JSONObject jsonRecibido = new JSONObject();
            jsonRecibido = JSONUtil.hacerPeticionServidor(
                    "usuarios/java/logout/", jsonEnviado);

            Usuario.borrarToken(getContext());

            ((MainActivity)getActivity()).lanzarLogin();

            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getContext(),"Problemas al cerrar sesion", Toast.LENGTH_LONG).show();
        }

    }


}
