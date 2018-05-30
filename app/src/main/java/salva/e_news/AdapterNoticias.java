package salva.e_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import salva.e_news.modelos.Noticia;
import salva.e_news.modelos.Tarea;
import salva.e_news.peticionesBD.Tags;
import salva.e_news.util.DescargarImagen;


public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.NoticiasViewHolder> implements View.OnClickListener {

    ArrayList<Noticia> listaNoticias;
    Context context;
    private View.OnClickListener listener;
    private Handler puente;
    ArrayList<Tarea> arrayTareas = new ArrayList<>();
    ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    /**
     * Lista de noticias que se añade en el recycler de noticias filtradas por la categoria que se
     * selecciones en la pantalla principal
     * @param listaNoticias
     */
    public AdapterNoticias(ArrayList<Noticia> listaNoticias, Context context) {
        this.listaNoticias = listaNoticias;
        this.context = context;
        //El puente es el nuevo Thread que añadira las imagenes en el siguiente ciclo de la
        // aplicacion cuando se hayan descargado
        puente = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    Tarea tarea = ((Tarea) msg.obj);
                    ImageView imageView = (ImageView) tarea.getDato2();
                    imageView.setTag("descargado");
                    Bitmap bitmap = (Bitmap) tarea.getDatoExtra();
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
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
    public void onBindViewHolder(NoticiasViewHolder holder, final int position) {
        holder.nombre_noticia.setText(listaNoticias.get(position).getNombre_noticia());
        holder.fecha_noticia.setText(listaNoticias.get(position).getFecha_noticia());
        holder.resumen_noticia.setText(listaNoticias.get(position).getResumen_noticia());
        holder.imagen_noticia.setImageResource(R.drawable.logo_final);
        //se añaden los valores en el recycler, se comprueba el tag de la imagen si el valor es logo,
        // se añade una tarea nueva para ejecutar con el nuevo Thread para descargar la imagen,
        // de esta manera quitamos los tiempos de carga de las activity que tengan imagenes.
        if (holder.imagen_noticia.getTag().equals("logo")) {
            holder.imagen_noticia.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.imagen_noticia.setImageResource(R.drawable.logo_final);
            arrayTareas.add(new Tarea("descargarFoto", listaNoticias.get(position), holder.imagen_noticia));
            Log.v("estaesssss", listaNoticias.get(position).getPk() + "");
            hacerTarea(position);
        }
        //Aqui enviamos los datos con el onclick en la noticia utilizando el parcelable que hemos
        // implementado en el modelo de Noticia.
        holder.cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetallesAnimal = new Intent(v.getContext(), DetalleNoticiaActivity.class);
                Log.v("noticiaspase", listaNoticias.get(position).toString());
                intentDetallesAnimal.putExtra("noticia", (Parcelable) listaNoticias.get(position));
                v.getContext().startActivity(intentDetallesAnimal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }
    //Metodo que llama al nuevo Thread llamado puente con el array de Tareas que se han añadido antes,
    // de nuevo recordar que asi ahorramos el tiempo de carga en las consultas y descarga de imagenes.
    private void hacerTarea(final int position) {
        pool.execute(new Thread() {
            @Override
            public void run() {
                while (arrayTareas.size() > 0) {
                    if (arrayTareas.get(0).getFuncion().contains("descargarFoto")) {
                        Bitmap bmp;
                        bmp = descargarFoto((Noticia) arrayTareas.get(0).getDato(), -1000);
                        String pk = ((Noticia) arrayTareas.get(0).getDato()).getPk();
                        arrayTareas.get(0).setDatoExtra(bmp);
                        Message msg = new Message();
                        msg.what = 100;
                        msg.arg1 = Integer.parseInt(pk +"");
                        msg.obj = arrayTareas.get(0);
                        puente.sendMessage(msg);
                    }
                    arrayTareas.remove(0);
                }
            }
        });
    }
    //Metodo que utilizamos para llamar al metodo DescargarImagen que descargara la imagen del servidor.
    private Bitmap descargarFoto(Object objeto, int pk) {
        Bitmap bitmap;
        int longitud = ((Noticia) objeto).getRutaImagen().length();
        String url = ((Noticia) objeto).getRutaImagen();
        if (longitud > 0) {
            bitmap = DescargarImagen.descargarImagen(Tags.SERVIDOR +"static/media/" + url);
            if (pk != -1000) {
                DescargarImagen.guardaImagen(bitmap, "noticias/", "noticia" + pk);
            }
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_final);
        }
        return bitmap;
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
        CardView cardview_id;

        public NoticiasViewHolder(View itemView) {
            super(itemView);
            nombre_noticia = itemView.findViewById(R.id.nombre_noticia);
            fecha_noticia = itemView.findViewById(R.id.tv_fecha_noticia);
            resumen_noticia = itemView.findViewById(R.id.resumen_noticia);
            imagen_noticia = itemView.findViewById(R.id.imagen_noticia);
            cardview_id = itemView.findViewById(R.id.cardview_id);
        }
    }
}