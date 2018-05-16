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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentarios, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticiasViewHolder holder, int position) {
        //holder.tv_raza_animal.setText(listaComentarios.get(position).getRaza());
        //holder.tv_nombre_animal.setText(listaComentarios.get(position).getNombre());
       //holder.iv_animal.setImageResource(R.drawable.nueva_pancarta);
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
        TextView tv_raza_animal, tv_nombre_animal;
        ImageView iv_animal;

        public NoticiasViewHolder(View itemView) {
            super(itemView);
            //tv_raza_animal = itemView.findViewById(R.id.tv_raza_animal);
            //tv_nombre_animal = itemView.findViewById(R.id.tv_nombre_animal);
            //iv_animal = itemView.findViewById(R.id.iv_animal);
        }
    }
}