package salva.e_news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
TextInputEditText email_usu;
TextInputEditText pass_usu;
TextInputLayout textInputLayout_email;
TextInputLayout textInputLayout_pass;
Button entrar, registrar;
String mensaje="";
TextView recordar_pass;
SignInButton signInButton;
GoogleApiClient mGoogleApiClient;
String nombre_usu2, email_usu2;
private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_usu = findViewById(R.id.email_usu);
        pass_usu = findViewById(R.id.pass_usu);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        textInputLayout_email= findViewById(R.id.textInputLayout_email);
        textInputLayout_pass = findViewById(R.id.textInputLayout_pass);
        cargarBotones();

    }

    private void cargarBotones() {
        entrar = findViewById(R.id.button_entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(email_usu.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(pass_usu.getWindowToken(), 0);
                if(email_usu.getText().toString().isEmpty() || email_usu.getText().toString().equals("") ||
                        pass_usu.getText().toString().isEmpty() || pass_usu.getText().toString().equals("")) {
                    mensaje=getResources().getString(R.string.debe_rellenar_campos);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else if (login()) {
                        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Intent intentmain = getIntent();
                    setResult(Activity.RESULT_OK, intentmain);
                    finish();
                }else{
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    mensaje="";
                }
            }
        });

        registrar = findViewById(R.id.button_registrar);
        registrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegistrarActivity.class);
                startActivity(intent);
            }

        });
        recordar_pass = findViewById(R.id.text_recordar_pass);
        recordar_pass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RecordarPass.class);
                startActivity(intent);
            }

        });
        signInButton = (SignInButton) findViewById(R.id.bt_google);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

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

    public void signIn() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    /**
     * Logea en el servidor. Crea un JSON y confirma si la pass es correcta.
     */
    protected boolean login() {
        String usuario = email_usu.getText().toString();
        String pass = pass_usu.getText().toString();

        //Creamos el JSON que vamos a mandar al servidor
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.USUARIO, usuario);
            json.put(Tags.PASSWORD, pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Hacemos petición de login al servidor
        json = JSONUtil.hacerPeticionServidor("usuarios/java/login/", json); //En contacto activity, es sesiones/java/getcentros

        try {
            String p = json.getString(Tags.RESULTADO);

            //Comprobamos la conexión con el servidor
            if (p.contains(Tags.ERRORCONEXION)) {
                mensaje= getResources().getString(R.string.error_conexion);
               // Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            //En caso de que conecte
            else if (p.contains(Tags.OK)) {
                Usuario.guardarEnPref(this, usuario, json.getString(Tags.TOKEN)); //Guarda en las preferencias la token
                mensaje=getResources().getString(R.string.logeo_correcto);
                return true;
            }
            //resultado falla por otro error
            else if (p.contains(Tags.ERROR)) {
                String msg = json.getString(Tags.MENSAJE);
                mensaje=msg;
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //SI REQUEST CODE ES IGUAL A RC_SIGN_IN INTENTA INICIAR CON GOOGLE
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    //METODO DONDE SE EJECUTA EL INICIO CUANDO ES GOOGLE
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            email_usu2 = acct.getEmail();
            nombre_usu2 = email_usu2.substring(0, email_usu2.indexOf("@"));
            JSONObject json = new JSONObject();
            Log.d("ERROR", "NOMBRE USUARIO" + nombre_usu2 + "EMAIL USU" + email_usu2);
            try {
                json.put(Tags.USUARIO, nombre_usu2);
                json.put(Tags.EMAIL, email_usu2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Hacemos petición de login al servidor
            json = JSONUtil.hacerPeticionServidor("usuarios/java/registro_google/", json);

            try {
                String p = json.getString(Tags.RESULTADO);
                //Comprobamos la conexión con el servidor
                if (p.contains(Tags.ERRORCONEXION)) {
                    Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_LONG).show();
                }
                //En caso de que conecte
                else if (p.contains(Tags.OK)) {
                    Usuario.guardarEnPref(this, nombre_usu2, json.getString(Tags.TOKEN));
                    setResult(Tags.LOGIN);
                    finish();
                }
                //resultado falla por otro error
                else if (p.contains(Tags.ERROR)) {
                    String msg = json.getString(Tags.MENSAJE);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
