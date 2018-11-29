package com.example.laion.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.skoumal.fragmentback.BackFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Estatistica extends Fragment implements BackFragment {

    public Estatistica() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estatistica, container, false);
    }

    private TextView pesoTextView;
    private TextView diaAtualTextView;
    private TextView metaPesoTextView;
    String data;
    String peso;
    Estatisticas_BD estatisticas_bd;
    UsuarioPerfil_BD userBD;
    Estrutura_Estatisticas item;
    int metaPeso;
    CalendarView simpleCalendarView;
    EditText novoPesoEditText;
    Button salvarNovoPeso;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        simpleCalendarView = view.findViewById(R.id.calendarView);
        pesoTextView = view.findViewById(R.id.ultimoPeso);
        diaAtualTextView = view.findViewById(R.id.dataAtual);
        metaPesoTextView = view.findViewById(R.id.metaPesoResult);
        novoPesoEditText = view.findViewById(R.id.novoPeso);
        salvarNovoPeso = view.findViewById(R.id.buttonSalvarPeso);
        data = diaAtualTextView.getText().toString();
        peso = pesoTextView.getText().toString();

        estatisticas_bd = new Estatisticas_BD(getContext());
        item = estatisticas_bd.getLastItem();

        if (TextUtils.isEmpty(data) || item == null){
            diaAtualTextView.setText("Atualize seu peso!");
            pesoTextView.setText("");
        }else{
            diaAtualTextView.setText(item.Data);
            pesoTextView.setText(Integer.toString(item.Peso) +"Kg");
        }
        int Id = SaveSharedPreference.getId(getContext());
        userBD = new UsuarioPerfil_BD(getContext());
        Estrutura_Perfil perfil = userBD.getData(Id);
        if(perfil != null){
            metaPeso = perfil.metaPeso;
            metaPesoTextView.setText(Integer.toString(metaPeso) + "Kg");
        }

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(simpleCalendarView.getDate()));

                item = estatisticas_bd.ObterEstatisticasPorData(selectedDate);
                if(item != null){
                    diaAtualTextView.setText(selectedDate);
                    pesoTextView.setText(Integer.toString(item.Peso) + "Kg");
                }else{
                    diaAtualTextView.setText("Atualize seu peso desse dia!");
                    pesoTextView.setText("");
                }
            }
        });

        salvarNovoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(simpleCalendarView.getDate()));

                String aux = novoPesoEditText.getText().toString();

                if (TextUtils.isEmpty(aux)) {
                    novoPesoEditText.setError(getString(R.string.error_field_required));
                    return;
                }else {
                    int pesoNovo = Integer.parseInt(aux);
                    estatisticas_bd.insertEstatistica(selectedDate, pesoNovo);
                    Toast.makeText(getContext(),"Peso inserido com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
