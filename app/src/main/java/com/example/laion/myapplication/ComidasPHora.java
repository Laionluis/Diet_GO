package com.example.laion.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ComidasPHora implements Parcelable {

    public int Id;
    public String nome;
    public String data;
    public String horario;
    public String objetivo;
    public int quantidade;
    public String unidadeMedida;

    ComidasPHora(int Id, String name, String data, int quantidade, String horario, String unidadeMedida, String objetivo) {
        this.Id = Id;
        this.nome = name;
        this.data = data;
        this.quantidade = quantidade;
        this.horario = horario;
        this.unidadeMedida = unidadeMedida;
        this.objetivo = objetivo;
    }

    private ComidasPHora(Parcel in) {
        Id = in.readInt();
        nome = in.readString();
        data = in.readString();
        horario = in.readString();
        objetivo = in.readString();
        quantidade = in.readInt();
        unidadeMedida = in.readString();
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
    }

    public static final Parcelable.Creator<ComidasPHora> CREATOR = new Parcelable.Creator<ComidasPHora>() {
        public ComidasPHora createFromParcel(Parcel in) {
            return new ComidasPHora(in);
        }

        public ComidasPHora[] newArray(int size) {
            return new ComidasPHora[size];
        }
    };

    @Override
    public String toString() {
        return nome + quantidade + unidadeMedida;
    }

    public String toString2() {
        return horario;
    }
}
