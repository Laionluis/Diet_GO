package com.example.laion.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class CadastroPerfil extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private EditText nome;
    private EditText idade;
    private EditText altura;
    private EditText peso;
    private RadioGroup objetivo;
    private UsuarioPerfil_BD perfil_bd;
    private EditText metaPeso;
    private EditText email;
    private int IdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_perfil);
        nome = (EditText) findViewById(R.id.editNome);
        idade = (EditText) findViewById(R.id.editIdade);
        altura = (EditText) findViewById(R.id.editAltura);
        peso = (EditText) findViewById(R.id.editPeso);
        objetivo = (RadioGroup) findViewById(R.id.radioGroupObjetivo);
        metaPeso = (EditText) findViewById(R.id.editMetaPeso);
        email = findViewById(R.id.editEmail);
        IdUsuario = -1;

        if( getIntent().getExtras() != null)
        {
            Intent intent = getIntent();
            Estrutura_Perfil aux = (Estrutura_Perfil) intent.getParcelableExtra("Perfil");
            IdUsuario = aux.Id;
            nome.setText(aux.nome);
            idade.setText(Integer.toString(aux.idade));
            altura.setText(Integer.toString(aux.altura));
            peso.setText(Integer.toString(aux.peso));
            email.setText(aux.email);
        }
    }


    public void salvar(View view) {
        perfil_bd = new UsuarioPerfil_BD(this);
        String nome_ = nome.getText().toString();
        int idade_ = Integer.parseInt(idade.getText().toString());
        int altura_ = Integer.parseInt(altura.getText().toString());
        int peso_ = Integer.parseInt(peso.getText().toString());
        RadioButton radioButton = findViewById(objetivo.getCheckedRadioButtonId());
        String objetivo_ = radioButton.getText().toString();
        int metapeso_ = Integer.parseInt(metaPeso.getText().toString());
        String emaill = email.getText().toString();
        int Id = -1;
        if(IdUsuario == -1)
            Id = perfil_bd.insertPerfilUsuario(nome_,idade_,altura_,peso_,objetivo_, metapeso_, emaill);
        else {
            Id = IdUsuario;
            perfil_bd.updatePerfilUsuario(Id, nome_,idade_,altura_,peso_,objetivo_, metapeso_, emaill);
        }

        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("IdPerfil", Id );
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }
}
