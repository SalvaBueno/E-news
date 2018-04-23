package salva.e_news.modelos;

public class Comentario {
    String contenido_comentario;
    String fecha_comentario;

    public Comentario(String contenido_comentario, String fecha_comentario) {
        this.setContenido_comentario(contenido_comentario);
        this.setFecha_comentario(fecha_comentario);

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
}
