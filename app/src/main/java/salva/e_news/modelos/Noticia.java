package salva.e_news.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.modelos.Usuario;
import salva.e_news.modelos.Categoria;
import salva.e_news.modelos.Noticia;
import salva.e_news.peticionesBD.Tags;

public class Noticia implements Parcelable {

    String pk;
    String descripcion_noticia;
    String resumen_noticia;
    String categoria_noticia;
    String fecha_noticia;
    String nombre_noticia;
    String rutaImagen;

    public Noticia(JSONObject jsonObject){

        try {
            setPk(jsonObject.getString(Tags.PK));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setNombre_noticia(jsonObject.getString(Tags.NOMBRE_NOTICIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setContenido_noticia(jsonObject.getString(Tags.CONTENIDO_NOTICIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setResumen_noticia(jsonObject.getString(Tags.RESUMEN_NOTICIA));
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
        try {
            setRutaImagen(jsonObject.getString(Tags.IMAGEN_NOTICIA));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected Noticia(Parcel in) {
        pk = in.readString();
        descripcion_noticia = in.readString();
        resumen_noticia = in.readString();
        categoria_noticia = in.readString();
        fecha_noticia = in.readString();
        nombre_noticia = in.readString();
        rutaImagen = in.readString();
    }

    public static final Creator<Noticia> CREATOR = new Creator<Noticia>() {
        @Override
        public Noticia createFromParcel(Parcel in) {
            return new Noticia(in);
        }

        @Override
        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };

    public String getNombre_noticia() {
        return nombre_noticia;
    }

    public void setNombre_noticia(String nombre_noticia) {
        this.nombre_noticia = nombre_noticia;
    }

    public String getContenido_noticia() {
        return descripcion_noticia;
    }

    public void setContenido_noticia(String descripcion_noticia) {
        this.descripcion_noticia = descripcion_noticia;
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

    public String getRutaImagen() { return rutaImagen; }

    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen;  }


    public String getResumen_noticia() {  return resumen_noticia;  }

    public void setResumen_noticia(String resumen_noticia) { this.resumen_noticia = resumen_noticia;  }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(descripcion_noticia);
        dest.writeString(resumen_noticia);
        dest.writeString(categoria_noticia);
        dest.writeString(fecha_noticia);
        dest.writeString(nombre_noticia);
        dest.writeString(rutaImagen);
    }
}
