package com.example.admin.psp_practica1_jaime;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private EditText et;
    private Adaptador adt;
    private ListView lv;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et= (EditText) findViewById(R.id.editText);
        et.setText("https://www.flickr.com/explore");
        i = new Intent(this,DescargarImg.class);
    }
    public void descargar(View v){
        Log.v("SXC", "Lanzando hilo");
        Hilo as=new Hilo();
        as.execute(et.getText().toString());
    }
    public class Hilo extends AsyncTask<String,Integer,ArrayList<String>> {
        private ArrayList<String> l;
        //Hilo, obligatorio
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            Log.v("SXC","doIn");
            String pag=params[0];
            URL url = null;
            try {
                url = new URL(pag);
            } catch (MalformedURLException ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }

            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException ex) {
                //Logger.getLogger(VerWeb.class.getName()).log(Level.SEVERE, null, ex);
            }


            String linea, out="";
            try {
                int pos=0;
                int fin=0;
                String cad="";
                while ((linea=in.readLine())!=null){
                    int i=0;
                    pos=linea.indexOf("img");
                    if(pos!=-1){// && fin > pos){
                        cad=linea.substring(pos);

                        //desde src=
                        //hast " || '
                        pos=cad.indexOf("src=");
                        if(pos!=-1){
                            cad=cad.substring(pos);
                            fin=cad.indexOf(">");
                            if(fin!=-1) {
                                cad = cad.substring(0, fin-1);
                                pos=cad.indexOf("\"")+1;
                                if(pos!=-1) {
                                    cad = cad.substring(pos);
                                    fin=cad.indexOf("\"")-1;
                                    if(fin!=-1) {
                                        cad = cad.substring(0, fin);
                                        l.add(cad);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                //Logger.getLogger(VerWeb.class.getName()).log(Level.SEVERE, null, ex);
            }catch (NullPointerException ex){
                Log.v("SZCV","esta vacio");
            }

            try {
                in.close();
            } catch (IOException ex) {
                //Logger.getLogger(VerWeb.class.getName()).log(Level.SEVERE, null, ex);
            }


            return l;

        }

        //Lo primero que se ejecuta, opcional
        @Override
        protected void onPreExecute() {
            Log.v("SXC","onPre");
            lv= (ListView) findViewById(R.id.listView);
            l=new ArrayList<String>();
        }

        //Cuando finaliza el hilo, obligatorio

        @Override
        protected void onPostExecute(ArrayList<String> result ) {
            Log.v("SXC","onPos");
            Log.v("SXC","Lista imganes: "+result.toString());
            adt=new Adaptador(MainActivity.this,R.layout.item,result);
            lv.setAdapter(adt);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                    String t = (String) lv.getItemAtPosition(pos);
                    Bundle b = new Bundle();
                    b.putString("img", t);
                    i.putExtras(b);
                    startActivity(i);

                }
            });

        }


    }
}
