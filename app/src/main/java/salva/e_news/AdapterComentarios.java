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

/**
 * Created by jblandii on 28/04/18.
 */

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.ComentariosViewHolder> implements View.OnClickListener {

    ArrayList<Comentario> listaComentarios;
    Context context;
    private View.OnClickListener listener;

    public AdapterComentarios(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    @Override
    public ComentariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentarios, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new ComentariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComentariosViewHolder holder, int position) {
        holder.nomUsuario.setText(listaComentarios.get(position).getUsuario().getUsername());
        holder.nomNoticia.setText(listaComentarios.get(position).getNoticia().getNombre_noticia());
        holder.textComentario.setText(listaComentarios.get(position).getContenido_comentario());
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
        TextView nomUsuario, nomNoticia, textComentario;

        public ComentariosViewHolder(View itemView) {
            super(itemView);
            nomUsuario = itemView.findViewById(R.id.TextViewNomUsuario);
            nomNoticia = itemView.findViewById(R.id.textViewNoticia);
            textComentario = itemView.findViewById(R.id.textViewComentario);
        }
    }
}