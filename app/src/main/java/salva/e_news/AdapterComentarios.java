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

    public AdapterComentarios(ArrayList<Comentario> listaAnimales) {
        this.listaComentarios = listaAnimales;
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
        //holder.tv_raza_animal.setText(listaComentarios.get(position).getRaza());
        //holder.tv_nombre_animal.setText(listaComentarios.get(position).getNombre());
       //holder.iv_animal.setImageResource(R.drawable.nueva_pancarta);
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
        TextView tv_raza_animal, tv_nombre_animal;
        ImageView iv_animal;

        public ComentariosViewHolder(View itemView) {
            super(itemView);
            //tv_raza_animal = itemView.findViewById(R.id.tv_raza_animal);
            //tv_nombre_animal = itemView.findViewById(R.id.tv_nombre_animal);
            //iv_animal = itemView.findViewById(R.id.iv_animal);
        }
    }
}