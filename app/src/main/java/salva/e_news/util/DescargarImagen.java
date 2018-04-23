package salva.e_news.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DescargarImagen {

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
}
