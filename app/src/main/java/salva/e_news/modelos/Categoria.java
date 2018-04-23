package salva.e_news.modelos;

public class Categoria {
    String nombre_categoria;
    String descripcion_categoria;

    public Categoria(String nombre_categoria, String descripcion_categoria) {
        this.setNombre_categoria(nombre_categoria);
        this.setDescripcion_categoria(descripcion_categoria);

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


}
