package salva.e_news.modelos;

public class Noticia {
    String contenido_noticia;
    String categoria_noticia;
    String fecha_noticia;

    public Noticia(String contenido_noticia, String categoria_noticia, String fecha_noticia) {
        this.setContenido_noticia(contenido_noticia);
        this.setCategoria_noticia(categoria_noticia);
        this.setFecha_noticia(fecha_noticia);

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
}
