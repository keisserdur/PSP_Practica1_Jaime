package com.example.admin.psp_practica1_jaime;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DescargarImg extends Activity {

    private ImageView iv;
    private String imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargar_img);

        Bundle b=getIntent().getExtras();
        imagen=b.getString("img");

        Tarea t=new Tarea();
        t.execute(new String[]{"" + imagen});
    }
    public class Tarea extends AsyncTask<String, Integer, Boolean> {
        private String ruta;
        private Uri uri;
        @Override
        protected Boolean doInBackground(String... params) {
            Log.v("SXC", "Antes del try");
            try{
                Log.v("SXC","Dentro del try, antes de la url, url: "+params[0]);
                URL url=new URL(params[0]);
                Log.v("SXC","Dentro del try, despues de la url");
                InputStream in=url.openStream();

                Log.v("SXC","Dentro del try, antes de la ruta, ruta: "+ruta);
                OutputStream out=new FileOutputStream(ruta);
                byte[] buffer=new byte[2048];
                int longitud;
                Log.v("SXC","Dentro del try, antes del while");
                while((longitud=in.read(buffer))!=-1){
                    out.write(buffer,0,longitud);
                }
                Log.v("SXC","Dentro del try, antes de los closed");
                in.close();
                out.close();
                return true;
            }catch(MalformedURLException e){
                Log.v("SXC","URLException");
                return false;
            }catch(IOException e){
                Log.v("SXC","Exception");
                return false;
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
        @Override
        protected void onPreExecute() {

            ruta= Environment.getExternalStorageDirectory() +"/"+"img"+".jpg";
            iv= (ImageView) findViewById(R.id.imageView);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if(result==true)
                uri=Uri.fromFile(new File(ruta));
                iv.setImageURI(uri);
        }
        @Override
        protected void onCancelled() {
        }
    }


}
