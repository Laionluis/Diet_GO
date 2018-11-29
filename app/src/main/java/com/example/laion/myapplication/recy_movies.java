package com.example.laion.myapplication;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;

import net.skoumal.fragmentback.BackFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class recy_movies extends Fragment implements BackFragment, MainActivity.OnAboutDataReceivedListener, MainActivity.OnChangeCategory {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    ListView listView;
    MainActivity mActivity;
    CustomAdapter adapter;
    Lista_para_assistir.Communicator comm;
    ArrayList<Estrutura_Receita> Estrutura_Receita;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comm = (Lista_para_assistir.Communicator)context;
    }

    public recy_movies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mActivity.setAboutDataListener(this);
        mActivity.setChangeCategoryListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recy_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_view_dietaDiaria);
        Estrutura_Receita = new ArrayList<>();

        Receitas_DB dbReceitas = new Receitas_DB(getContext());
        int total_results = dbReceitas.numberOfRows();
        if(total_results == 0){
            dbReceitas.insertReceita("Café da manhã", "Segunda", 1,"Kg", "06:00", "Um pão francês com queijo minas e um copo de suco de laranja", "Pao", "Queijo minas", "Laranja");
            dbReceitas.insertReceita("Lanche da manhã", "Segunda", 1,"Kg", "09:00", "Sorvete light, frozen de iogurte desnatado e picolé de frutas", "Sorvete light", "Frozen de iogurte", "Picolé de Frutas");
            dbReceitas.insertReceita("Almoço", "Segunda", 1,"Kg", "12:00", "Peito de frango sem pele assada no azeite, berinjela, tomate, quinoa e abobrinha", "Peito de frango", "Berinjela", "Salada");
        }

        Estrutura_Receita = dbReceitas.getAllReceitas();
        adapter = new CustomAdapter(Estrutura_Receita, getContext());
        listView.setAdapter(adapter);

        trataHora();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Estrutura_Receita dataModel= Estrutura_Receita.get(position);  //pega posicao do item na lista

                Intent intent = new Intent(getContext(), Adicionar.class);
                intent.putExtra("Receita", dataModel );
                startActivity(intent);
            }
        });
    }

    private void trataHora() {
        Context context = getContext();
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);

        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.HOUR_OF_DAY, date1.getTime().getHours() );
        date1.set(Calendar.MINUTE, date1.getTime().getMinutes() );
        date1.set(Calendar.SECOND, 0);


        for (Estrutura_Receita hora: Estrutura_Receita) {
            String[] parts = hora.horario.split(":");
            Calendar date2 = Calendar.getInstance();
            date2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
            date2.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
            date2.set(Calendar.SECOND, 0);

            if (date1.after(date2)) {
                hora.horaAtual = true;
            }

            boolean alarmUp = (PendingIntent.getBroadcast(context, hora.Id,
                    new Intent(context, AlarmReceiver.class),
                    PendingIntent.FLAG_NO_CREATE) != null);
            if (alarmUp)
            {
                Log.d("myTag", "Alarm is already active");
            }else{
                NotificationScheduler.setReminder(context, AlarmReceiver.class, Integer.parseInt(parts[0]) - 1, 50, hora.Id);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /*public void TrataSearchView(String newQuery){
        ApiInterface api = AppClient.getRetrofit().create(ApiInterface.class);
        retrofit2.Call<Movie> call = api.getMovieByName(newQuery);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(retrofit2.Call<Movie> call, Response<Movie> response) {
                movie_search = response.body();
                if(movie_search != null && movie_search.getTotalResults() > 0){
                    adapterMovie = new MovieApadpter(results);
                    adapterMovie.setData(movie_search.getResults());
                    mView.setAdapter(adapterMovie);
                    trataClick(movie_search);
                }else
                    movieLoad();
            }

            @Override
            public void onFailure(retrofit2.Call<Movie> call, Throwable t) {

            }
        });
    }*/

    public void AbrirInformacoes(Result filme) {

        Intent intent = new Intent(getContext(), Adicionar.class);
        intent.putExtra("Result", filme);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == MainActivity.RESULT_OK) {
                // pega a string do intent
                String titulo = data.getStringExtra("titulo");
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
                String date = mdformat.format(Calendar.getInstance().getTime());

                Filmes dbFilmes = new Filmes(getContext());
                dbFilmes.insertFilme(titulo, date, 0,0);

                comm.respond(true);
            }
        }
    }*/

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getBackPriority() {
        return 0;
    }

    @Override
    public void onDataReceived(String query) {
        //TrataSearchView(query);
    }

    @Override
    public void OnChangeCat(int cat) {

    }
}
