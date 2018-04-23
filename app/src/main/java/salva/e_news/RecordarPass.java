package salva.e_news;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.peticionesBD.JSONUtil;
import salva.e_news.peticionesBD.Tags;

public class RecordarPass extends AppCompatActivity {
    TextInputEditText textInputLayout_email;
    Button recordar_pass;
    String mensaje ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar_pass);
        textInputLayout_email = findViewById(R.id.text_email_recordar);
        cargarBotones();
    }

    private void cargarBotones() {
        recordar_pass = findViewById(R.id.button_recordar);
        recordar_pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if (textInputLayout_email.getText().toString().isEmpty() || textInputLayout_email.getText().toString().equals("")) {
                    mensaje=getResources().getString(R.string.debe_indicar_nombre_usuario_registrado);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
//                    Para cerrar el teclado antes de mostrar el AlertDialog
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textInputLayout_email.getWindowToken(), 0);
                    AlertDialog alertDialog = new AlertDialog.Builder(RecordarPass.this).create();
                    alertDialog.setTitle(getResources().getString(R.string.recordarpasstitulo));
                    alertDialog.setMessage(getResources().getString(R.string.mensaje_recordar_pass));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            JSONObject json = new JSONObject();
                            try {
                                json.put(Tags.USUARIO, textInputLayout_email.getText().toString());
                                JSONUtil.hacerPeticionServidor("usuarios/java/recuperar_contrasena/", json);


                                String p = json.getString(Tags.RESULTADO);
                                //Comprobamos la conexión con el servidor
                                if (p.contains(Tags.ERRORCONEXION)) {
                                    //Si no conecta imprime un snackbar
                                    mensaje=getResources().getString(R.string.error_conexion);
                                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();

//                                return false;
                                }

                                else if (p.contains(Tags.OK)) {
                                    mensaje= getResources().getString(R.string.mensaje_enviado);
                                    Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    finish();
                                }
                                //resultado falla por otro error
                                else if (p.contains(Tags.ERROR)) {
                                    String msg = json.getString(Tags.MENSAJE);
                                    Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();

//                                return false;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }

        });
    }
}
