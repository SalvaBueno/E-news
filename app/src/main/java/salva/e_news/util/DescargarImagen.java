package salva.e_news.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DescargarImagen {
	/**
	 * Clase que se utilizara para la descarga de imagenes en el servidor.
	 * @param url
	 */
	public static Bitmap descargarImagen(String url){
		Descarga d=new Descarga();
		d.execute(url);
		Bitmap b=null;
		try {
			b=d.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}
	

	private static class Descarga extends AsyncTask<String, Void, Bitmap> {
		URL newurl;
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap mIcon_val = null;
			try {
				newurl = new URL(params[0]);
				mIcon_val = BitmapFactory.decodeStream(newurl.openConnection()
						.getInputStream());

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mIcon_val;
		}

	}

	/**
	 * Este metodo se utiliza para guardar imagenes en el movil cuando se quiere ahorrar
	 * la descarga continua de archivos del servidor
	 * @param b,ruta,picName
	 */
	public static void guardaImagen(Bitmap b, String ruta, String picName) {
		if (b != null) {
			crearCarpeta(ruta);
			FileOutputStream fos;
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/Enews/" + ruta + picName);
			try {
				fos = new FileOutputStream(f);
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				// Log.d("IMAGEN NO ENCONTRADA", "file not found");
				e.printStackTrace();
			} catch (IOException e) {
				// Log.d("IO", "io exception");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo que crea la carpeta en el movil para almacenar las imagenes que se quieren guardar
	 * @param ruta
	 */
	public static void crearCarpeta(String ruta) {
		String folder_main = "Enews/";
		File f = new File(Environment.getExternalStorageDirectory() + "/"
				+ folder_main, ruta);
		if (!f.exists()) {
			f.mkdirs();
		}
	}
}
