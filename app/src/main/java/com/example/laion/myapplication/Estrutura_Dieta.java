package com.example.laion.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Estrutura_Dieta implements Parcelable {

    public int Id;
    public String IdsReceita;
    public String diaSemana;
    public boolean diaAtual;

    Estrutura_Dieta(int Id, String IdsReceita, String diaSemana) {
        this.Id = Id;
        this.IdsReceita = IdsReceita;
        this.diaSemana = diaSemana;
    }

    private Estrutura_Dieta(Parcel in) {
        Id = in.readInt();
        IdsReceita = in.readString();
        diaSemana = in.readString();
        diaAtual = in.readInt() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(IdsReceita);
        dest.writeString(diaSemana);
        dest.writeInt(diaAtual ? 1 : 0);
    }

    public static final Parcelable.Creator<Estrutura_Dieta> CREATOR = new Parcelable.Creator<Estrutura_Dieta>() {
        public Estrutura_Dieta createFromParcel(Parcel in) {
            return new Estrutura_Dieta(in);
        }

        public Estrutura_Dieta[] newArray(int size) {
            return new Estrutura_Dieta[size];
        }
    };

    @Override
    public String toString() {
        return Id + IdsReceita + diaSemana;
    }

}
