package salva.e_news.modelos;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.peticionesBD.Tags;

public class Noticia {
    String pk;
    String contenido_noticia;
    String categoria_noticia;
    String fecha_noticia;

    public Noticia(JSONObject jsonObject){
        try {
            setPk(jsonObject.getString(Tags.PK));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setContenido_noticia(jsonObject.getString(Tags.CONTENIDO_NOTICIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setCategoria_noticia(jsonObject.getString(Tags.CATEGORIA_NOTICIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setFecha_noticia(jsonObject.getString(Tags.FECHA_NOTICIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getContenido_noticia() {
        return contenido_noticia;
    }

    public void setContenido_noticia(String contenido_noticia) {
        this.contenido_noticia = contenido_noticia;
    }

    public String getCategoria_noticia() {
        return categoria_noticia;
    }

    public void setCategoria_noticia(String categoria_noticia) {
        this.categoria_noticia = categoria_noticia;
    }

    public String getFecha_noticia() {
        return fecha_noticia;
    }

    public void setFecha_noticia(String fecha_noticia) {
        this.fecha_noticia = fecha_noticia;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
}
