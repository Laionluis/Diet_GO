package com.example.laion.myapplication;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.skoumal.fragmentback.BackFragment;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Lista_para_assistir extends Fragment implements BackFragment {

    ArrayList<Estrutura_Dieta> Estrutura_Dieta;
    ListView listView;
    AdapterListViewDietasSemana adapter;
    Dietas_DB dbDietas;
    int aux;
    recy_movies fragment_filmes;

    public Lista_para_assistir() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_para_assistir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbDietas = new Dietas_DB(getContext());
        listView = view.findViewById(R.id.list_view_dietasSemana);
        Estrutura_Dieta = new ArrayList<>();

        int total_results = dbDietas.numberOfRows();
        if(total_results == 0){
            dbDietas.insertDieta("1+2+3", "Domingo");
            dbDietas.insertDieta("1+2+3", "Segunda");
        }

        if(savedInstanceState == null){
            Estrutura_Dieta = dbDietas.getAllDIETAS();
        }
        else{
            Estrutura_Dieta = dbDietas.getAllDIETAS();
        }

        adapter = new AdapterListViewDietasSemana(Estrutura_Dieta, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Estrutura_Dieta dataModel= Estrutura_Dieta.get(position);  //pega posicao do item na lista
                /*if (aux == 1){
                    dataModel.isItemSelected = !dataModel.isItemSelected;
                    adapter.notifyDataSetChanged();
                }else{
                    dataModel.checked = !dataModel.checked;                                      //se estiver marcado desmarca e viceversa
                    adapter.notifyDataSetChanged();                                              //avisa adaptador das mudanças
                }
                dbFilmes.updateFilme(position+1,dataModel.nome, dataModel.data, dataModel.checked ? 1 : 0, dataModel.isItemSelected ? 1 : 0);
                */
            }
        });

        trataDia();

    }

    private void trataDia() {
        Calendar cal = new GregorianCalendar();
        String diaAtual = diaSemana(cal);
        for (Estrutura_Dieta dia: Estrutura_Dieta) {
            if(dia.diaSemana.toLowerCase().equals(diaAtual)){
                dia.diaAtual = true;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public String diaSemana(Calendar cal) {
        return new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ok", 1);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getBackPriority() {
        return LOW_BACK_PRIORITY;
    }

    interface Communicator{
        public void respond(Boolean carregar);
    }

}
