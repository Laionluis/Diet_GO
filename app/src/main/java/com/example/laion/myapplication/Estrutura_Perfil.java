package com.example.laion.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Estrutura_Perfil  implements Parcelable {
    public int Id;
    public String nome;
    public int idade;
    public int peso;
    public int altura;
    public String objetivo;
    public int metaPeso;
    public String email;

    Estrutura_Perfil(int Id, String name, int idade, int altura, int peso, String objetivo, int metaPeso, String email) {
        this.Id = Id;
        this.nome = name;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.objetivo = objetivo;
        this.metaPeso = metaPeso;
        this.email = email;
    }

    private Estrutura_Perfil(Parcel in) {
        Id = in.readInt();
        nome = in.readString();
        idade = in.readInt();
        altura = in.readInt();
        peso = in.readInt();
        objetivo = in.readString();
        metaPeso = in.readInt();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(nome);
        dest.writeInt(idade);
        dest.writeInt(altura);
        dest.writeInt(peso);
        dest.writeString(objetivo);
        dest.writeInt(metaPeso);
        dest.writeString(email);
    }

    public static final Parcelable.Creator<Estrutura_Perfil> CREATOR = new Parcelable.Creator<Estrutura_Perfil>() {
        public Estrutura_Perfil createFromParcel(Parcel in) {
            return new Estrutura_Perfil(in);
        }

        public Estrutura_Perfil[] newArray(int size) {
            return new Estrutura_Perfil[size];
        }
    };

    @Override
    public String toString() {
        return Id + nome + idade + altura + peso + objetivo + metaPeso + email;
    }

}
