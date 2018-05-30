package salva.e_news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import salva.e_news.modelos.Comentario;

public class AdapterComentariosNoticia extends RecyclerView.Adapter<AdapterComentariosNoticia.ComentariosViewHolder> implements View.OnClickListener {

    ArrayList<Comentario> listaComentarios;
    Context context;
    private View.OnClickListener listener;

    /**
     * Lista de comentarios que se añadira con el adaptador para el recycler de comentarios para
     * las noticias especificas
     * @param listaComentarios
     */
    public AdapterComentariosNoticia(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    @Override
    public ComentariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentarios_noticias, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new ComentariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComentariosViewHolder holder, int position) {
        holder.tv_nombre_usuario_comentario.setText(listaComentarios.get(position).getUsuario().getUsername());
        holder.tv_fecha_noticia_comentario.setText(listaComentarios.get(position).getFecha_comentario());
        holder.tv_comentario_contenido.setText(listaComentarios.get(position).getContenido_comentario());
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ComentariosViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nombre_usuario_comentario, tv_fecha_noticia_comentario, tv_comentario_contenido;

        public ComentariosViewHolder(View itemView) {
            super(itemView);
            tv_nombre_usuario_comentario = itemView.findViewById(R.id.tv_nombre_usuario_comentario);
            tv_fecha_noticia_comentario = itemView.findViewById(R.id.tv_fecha_noticia_comentario);
            tv_comentario_contenido = itemView.findViewById(R.id.tv_comentario_contenido);
        }
    }
}