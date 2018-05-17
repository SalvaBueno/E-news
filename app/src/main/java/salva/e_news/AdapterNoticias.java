package salva.e_news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import salva.e_news.modelos.Comentario;
import salva.e_news.modelos.Noticia;

/**
 * Created by jblandii on 28/04/18.
 */

public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.NoticiasViewHolder> implements View.OnClickListener {

    ArrayList<Noticia> listaNoticias;
    Context context;
    private View.OnClickListener listener;

    public AdapterNoticias(ArrayList<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticiasViewHolder holder, int position) {
        holder.nombre_noticia.setText(listaNoticias.get(position).getNombre_noticia());
        holder.fecha_noticia.setText(listaNoticias.get(position).getFecha_noticia());
        holder.resumen_noticia.setText(listaNoticias.get(position).getResumen_noticia());
        holder.imagen_noticia.setImageResource(R.drawable.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
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

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_noticia, fecha_noticia,resumen_noticia;
        ImageView imagen_noticia;

        public NoticiasViewHolder(View itemView) {
            super(itemView);
            nombre_noticia = itemView.findViewById(R.id.nombre_noticia);
            fecha_noticia = itemView.findViewById(R.id.fecha_noticia);
            resumen_noticia = itemView.findViewById(R.id.resumen_noticia);
            imagen_noticia = itemView.findViewById(R.id.imagen_noticia);
        }
    }
}