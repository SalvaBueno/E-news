package salva.e_news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;


public class MainActivity extends AppCompatActivity {

    boolean inicio;
    private ProgressDialog dialog;
    static Usuario user;
    BottomNavigationView bnv;
    Boolean cerradoSesion = false;

    final Fragment comentariosFragment = new ComentariosFragment();
    final Fragment noticiasFragment = new NoticiasFragment();
    final Fragment editarPerfil = new EditarPerfilFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicio = true;

        bnv = findViewById(R.id.bnv);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando datos...");
        dialog.setCancelable(false);
        dialog.show();
        //Para hacer que el fragmento que aparezca por defecto el primero sea el fragmento de
        // las noticias que esta en el centro.
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, noticiasFragment).commit();
            bnv.setSelectedItemId(R.id.noticias);
        }

        //Definimos la navegacion con los botones y sus respectivos fragmentos.
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (item.getItemId() == R.id.comentarios) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, comentariosFragment).commit();
                } else if (item.getItemId() == R.id.noticias) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, noticiasFragment).commit();
                } else if (item.getItemId() == R.id.editar_usuario) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, editarPerfil).commit();
                }
                return true;
            }
        });

        dialog.cancel();
    }

    //Cuando se quieran cargar las noticias de una categoria especifica se llamara a este metodo
    // para pasarle a traves de Bundle el tags.NOMBRE_CATEGORIA para poder realizar la consulta al servidor.
    public void cargarfiltrado(String nombreCategoria){
        final Fragment noticiasFiltradasFragment = new NoticiasFiltradoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle arguments = new Bundle();
        arguments.putString(Tags.NOMBRE_CATEGORIA, nombreCategoria);
        noticiasFiltradasFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.fragmentContainer, noticiasFiltradasFragment).addToBackStack(null).commit();
    }

    //Lanzamos el login desde el MainActivity
    @Override
    protected void onResume() {
        super.onResume();
        lanzarLogin();
        if (cerradoSesion) {
            cerradoSesion = false;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, noticiasFragment).commit();
            bnv.setSelectedItemId(R.id.noticias);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
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
    //Lanzamos la activity lanzar login comprobando antes si hay internet, ya que la aplicacion no
    // tendria sentido utilizarse en el caso de que no hubiera ya que el uso principal es la opinion
    // y lectura de opiniones de los usuarios conectados simultaneamente.
    public void lanzarLogin() {
        if (hayInternet()) {
            String token = Usuario.getToken(getApplicationContext());
            if (token == null || token == "" || !JSONUtil.compruebaToken(token)) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, Tags.LOGIN);
            }
        } else {

        }
    }

    //Para comprobar si hay internet en la aplicacion
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Tags.LOGIN:
                    Log.v("sesion", "on activity result" + cerradoSesion.toString());
                    Log.v("pasando", "pasando");
                    cerradoSesion = true;
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
