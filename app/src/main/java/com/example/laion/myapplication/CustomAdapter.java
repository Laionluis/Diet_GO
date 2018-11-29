package com.example.laion.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtNameRefeicao;
        TextView horario;
    }

    public CustomAdapter(ArrayList data, Context context) {
        super(context, R.layout.item_receita, data);
        this.dataSet = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Estrutura_Receita getItem(int position) {
        return (Estrutura_Receita) dataSet.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receita, parent, false);
            viewHolder.txtNameRefeicao = convertView.findViewById(R.id.txtNomeRefeicao);
            viewHolder.horario = convertView.findViewById(R.id.horario);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Estrutura_Receita item = getItem(position);
        viewHolder.txtNameRefeicao.setText(item.nome);
        viewHolder.horario.setText(item.horario);
        if(item.horaAtual){  //parte em que risca o texto
            viewHolder.txtNameRefeicao.setPaintFlags(viewHolder.txtNameRefeicao.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.txtNameRefeicao.setPaintFlags(0);
        }

        /*if(item.isItemSelected){
            result.setBackgroundColor(Color.LTGRAY);
        } else{
            result.setBackgroundColor(Color.TRANSPARENT);
        }*/

        return result;
    }
}