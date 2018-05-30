package salva.e_news.modelos;

import org.json.JSONException;
import org.json.JSONObject;

import salva.e_news.peticionesBD.Tags;

//Modelo categoria con sus set and get. Para crear el objeto Noticia utilizamos un json con tags
public class Categoria {
    private String pk;
    private String nombre_categoria;
    private String descripcion_categoria;

    /**
     * En el constructor del Usuario se le da un valor a las distintas variables.
     * Estas se sacan del JSON que nos ha retornado el servidor.
     * @param json
     */
    public Categoria(JSONObject json) {
        try {
            setPk(json.getString(Tags.PK));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setNombre_categoria(json.getString(Tags.NOMBRE_CATEGORIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            setDescripcion_categoria(json.getString(Tags.DESCRIPCION_CATEGORIA));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

}
