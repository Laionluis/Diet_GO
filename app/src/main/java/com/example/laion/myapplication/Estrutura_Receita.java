package com.example.laion.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Estrutura_Receita implements Parcelable {
    public int Id;
    public String nome;
    public String data;
    public String horario;
    public String objetivo;
    public int quantidade;
    public String unidadeMedida;
    public boolean horaAtual;
    public String Comida1;
    public String Comida2;
    public String Comida3;

    Estrutura_Receita(int Id, String name, String data, int quantidade, String horario, String unidadeMedida, String objetivo, String Comida1, String Comida2, String Comida3) {
        this.Id = Id;
        this.nome = name;
        this.data = data;
        this.quantidade = quantidade;
        this.horario = horario;
        this.unidadeMedida = unidadeMedida;
        this.objetivo = objetivo;
        this.Comida1 = Comida1;
        this.Comida2 = Comida2;
        this.Comida3 = Comida3;
    }

    private Estrutura_Receita(Parcel in) {
        Id = in.readInt();
        nome = in.readString();
        data = in.readString();
        horario = in.readString();
        objetivo = in.readString();
        quantidade = in.readInt();
        unidadeMedida = in.readString();
        horaAtual = in.readInt() != 0;
        Comida1 = in.readString();
        Comida2 = in.readString();
        Comida3 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(nome);
        dest.writeString(data);
        dest.writeString(horario);
        dest.writeString(objetivo);
        dest.writeInt(quantidade);
        dest.writeString(unidadeMedida);
        dest.writeInt(horaAtual ? 1 : 0);
        dest.writeString(Comida1);
        dest.writeString(Comida2);
        dest.writeString(Comida3);
    }

    public static final Parcelable.Creator<Estrutura_Receita> CREATOR = new Parcelable.Creator<Estrutura_Receita>() {
        public Estrutura_Receita createFromParcel(Parcel in) {
            return new Estrutura_Receita(in);
        }

        public Estrutura_Receita[] newArray(int size) {
            return new Estrutura_Receita[size];
        }
    };

    @Override
    public String toString() {
        return Id + nome + data + horario + quantidade + unidadeMedida  + objetivo + Comida1 + Comida2 + Comida3;
    }

}


