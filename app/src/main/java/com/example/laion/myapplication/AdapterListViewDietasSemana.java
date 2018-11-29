package com.example.laion.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListViewDietasSemana extends ArrayAdapter {

    private ArrayList dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtDiaSemana;
        View bolinha;
    }

    public AdapterListViewDietasSemana(ArrayList data, Context context) {
        super(context, R.layout.item_dieta, data);
        this.dataSet = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Estrutura_Dieta getItem(int position) {
        return (Estrutura_Dieta) dataSet.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        AdapterListViewDietasSemana.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new AdapterListViewDietasSemana.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dieta, parent, false);
            viewHolder.txtDiaSemana = convertView.findViewById(R.id.DiaSemana);
            viewHolder.bolinha = convertView.findViewById(R.id.bolinha);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (AdapterListViewDietasSemana.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Estrutura_Dieta item = getItem(position);
        viewHolder.txtDiaSemana.setText(item.diaSemana);

        if(item.diaAtual){
            result.setBackgroundColor(Color.LTGRAY);
            viewHolder.bolinha.setVisibility(View.VISIBLE);
        } else{
            result.setBackgroundColor(Color.TRANSPARENT);
        }

        return result;
    }
}
