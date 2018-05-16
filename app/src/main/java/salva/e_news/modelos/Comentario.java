package salva.e_news.modelos;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.peticionesBD.Tags;

public class Comentario {
    String contenido_comentario;
    String fecha_comentario;
    Noticia noticia;

    public Comentario(JSONObject jsonObject) {
        try {
            setContenido_comentario(jsonObject.getString(Tags.CONTENIDO_COMENTARIO));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setContenido_comentario(jsonObject.getString(Tags.FECHA_COMENTARIO));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setNoticia(new Noticia(jsonObject));
        }catch (Exception e){
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
}
