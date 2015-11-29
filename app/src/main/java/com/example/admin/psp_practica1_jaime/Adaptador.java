package com.example.admin.psp_practica1_jaime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 29/11/2015.
 */
public class Adaptador extends ArrayAdapter {
    private Context c;
    private int r;
    private TextView tv;
    private ArrayList<String> l;

    public Adaptador(Context context, int resource,ArrayList<String> l) {
        super(context, resource,l);
        this.c=context;
        this.r=resource;
        this.l= l;
    }

    @Override
    public View getView(int posicion, View vista, ViewGroup padre) {
        LayoutInflater i = (LayoutInflater) c.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        vista = i.inflate(r, null);
        TextView tv = (TextView) vista.findViewById(R.id.tv);
        tv.setText(l.get(posicion));
        return vista;
    }
}
