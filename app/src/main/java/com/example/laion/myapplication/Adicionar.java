package com.example.laion.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Callback;
import retrofit2.Response;

public class Adicionar extends AppCompatActivity {
    Estrutura_Receita receita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comidas_phora);
        this.setFinishOnTouchOutside(true);

        Intent intent = getIntent();
        receita = (Estrutura_Receita) intent.getParcelableExtra("Receita");

        TextView horaTitulo = findViewById(R.id.HoraTitulo);
        TextView titulo = findViewById(R.id.titulo);
        TextView comida1 = findViewById(R.id.Comida1);
        TextView comida2 = findViewById(R.id.Comida2);
        TextView comida3 = findViewById(R.id.Comida3);
        horaTitulo.setText(receita.data);
        titulo.setText(receita.nome);
        comida1.setText(receita.Comida1);
        comida2.setText(receita.Comida2);
        comida3.setText(receita.Comida3);
    }
}
