package salva.e_news.modelos;

import org.json.JSONException;
import org.json.JSONObject;
import salva.e_news.modelos.Usuario;
import salva.e_news.modelos.Categoria;
import salva.e_news.modelos.Noticia;
import salva.e_news.peticionesBD.Tags;

//Modelo comentario con sus set y get, para crear un nuevo objeto comentario utilizamos json.
public class Comentario {
    String contenido_comentario;
    String fecha_comentario;
    Noticia noticia;
    Usuario usuario;

    /**
     * En el constructor del Usuario se le da un valor a las distintas variables.
     * Estas se sacan del JSON que nos ha retornado el servidor.
     * @param jsonObject
     */
    public Comentario(JSONObject jsonObject) {
        try {
            setContenido_comentario(jsonObject.getString(Tags.CONTENIDO_COMENTARIO));
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Dentro de comentario tendra un objeto noticia.
        try {
            setNoticia(new Noticia(jsonObject.getJSONObject(Tags.NOTICIA)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            setFecha_comentario(jsonObject.getString(Tags.FECHA_COMENTARIO));
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Dentro de comentario tendra un objeto usuario.
        try {
            setUsuario(new Usuario(jsonObject.getJSONObject(Tags.USUARIO)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getContenido_comentario() {
        return contenido_comentario;
    }

    public void setContenido_comentario(String contenido_comentario) {
        this.contenido_comentario = contenido_comentario;
    }

    public String getFecha_comentario() {
        return fecha_comentario;
    }

    public void setFecha_comentario(String fecha_comentario) {
        this.fecha_comentario = fecha_comentario;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
