package salva.e_news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class MainActivity extends AppCompatActivity {
    boolean inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicio = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (inicio) {
            lanzarLogin();
        }
    }

    @Override
    public void onBackPressed() {
        /*
         * Con las siguientes lineas conseguimos que al salir de la aplicacion,
         * siga funcionando en segundo plano
         */
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        moveTaskToBack(true);
        startActivity(intent);
    }

    public void lanzarLogin() {
        if (hayInternet()) {
            String token = Usuario.getToken(getApplicationContext());
            if (token == null || token == "" || !JSONUtil.compruebaToken(token)) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, Tags.LOGIN);
            }
        } else {
            //AQUI EL LOGIN CON SQLITE
        }
    }


    public boolean hayInternet() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
