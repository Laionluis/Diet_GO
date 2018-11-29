package com.example.laion.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Estrutura_Estatisticas implements Parcelable {

    public int Id;
    public String Data;
    public int Peso;

    Estrutura_Estatisticas(int Id, String Data, int Peso) {
        this.Id = Id;
        this.Data = Data;
        this.Peso = Peso;
    }

    private Estrutura_Estatisticas(Parcel in) {
        Id = in.readInt();
        Data = in.readString();
        Peso = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Data);
        dest.writeInt(Peso);
    }

    public static final Parcelable.Creator<Estrutura_Estatisticas> CREATOR = new Parcelable.Creator<Estrutura_Estatisticas>() {
        public Estrutura_Estatisticas createFromParcel(Parcel in) {
            return new Estrutura_Estatisticas(in);
        }

        public Estrutura_Estatisticas[] newArray(int size) {
            return new Estrutura_Estatisticas[size];
        }
    };

    @Override
    public String toString() {
        return Id + Data + Peso;
    }
}
