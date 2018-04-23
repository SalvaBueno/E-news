package salva.e_news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSqlLite extends SQLiteOpenHelper {
    public AdminSqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Se crea la base de datos interna a traves de sqlite.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (id_usuario primary key AUTOINCREMENT ,nombre_usuario varchar(50),email varchar(30), pass_usuario varchar(20), confirmar_pass_usuario varchar(20))");
        db.execSQL("CREATE TABLE noticia (id_noticia primary key AUTOINCREMENT, contenido_noticia VARCHAR(300), fecha_noticia datetime default current_timestamp, id_categoria integer(2),FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria))");
        db.execSQL("CREATE TABLE categoria (id_categoria primary key AUTOINCREMENT, nombre_categoria varchar(50), descripcion_categoria varchar(50))");
        db.execSQL("CREATE TABLE comentario (id_comentario primary key AUTOINCREMENT, id_noticia integer(2) ,id_usuario integer(2),fecha_comentario datetime default current_timestamp, contenido_comentario varchar(100), " +
                "FOREIGN KEY (id_noticia) REFERENCES noticia(id_noticia), " +
                "FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS noticia");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS comentario");
        onCreate(db);
    }
}
