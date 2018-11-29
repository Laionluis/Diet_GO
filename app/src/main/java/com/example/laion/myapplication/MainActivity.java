package com.example.laion.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;

import net.skoumal.fragmentback.BackFragmentAppCompatActivity;
import net.skoumal.fragmentback.BackFragmentHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BackFragmentAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Lista_para_assistir.Communicator {
    FloatingSearchView searchView;
    DrawerLayout mDrawerLayout;
    BottomNavigationView bottomNavigationView;
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    Estrutura_Perfil perfil;
    NavigationView navigationView;
    UsuarioPerfil_BD perfil_bd = new UsuarioPerfil_BD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int Id = intent.getIntExtra("IdPerfil", 1);
        perfil = perfil_bd.getData(Id);

        mSlideViewPager = findViewById(R.id.slider_viewPager);
        sliderAdapter = new SliderAdapter(getSupportFragmentManager());
        mSlideViewPager.setAdapter(sliderAdapter);

        searchView = findViewById(R.id.search_view_floating);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TrataSearchView();
        TrataNavHeader(perfil);

        bottomNavigationView = findViewById(R.id.bottom_app_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.daSemana:
                        mSlideViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.doDia:
                        mSlideViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.estatisticas:
                        mSlideViewPager.setCurrentItem(2, true);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        mSlideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0)
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                if(i == 1)
                    bottomNavigationView.getMenu().getItem(1).setChecked(false);
                if(i == 2)
                    bottomNavigationView.getMenu().getItem(2).setChecked(false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void TrataNavHeader(Estrutura_Perfil perfil) {
        View hView =  navigationView.getHeaderView(0);

        TextView nomePerfil = hView.findViewById(R.id.NomeUser);
        TextView objetivoAtual = hView.findViewById(R.id.ObjetivoAtual);

        nomePerfil.setText(perfil.nome);
        objetivoAtual.setText(perfil.objetivo);
    }

    public void TrataSearchView(){
        searchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.setSearchHint("Procurar pelo app...");
            }
            @Override
            public void onFocusCleared(){
                searchView.setSearchHint("Procurar pelo app");
            }
        });


        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                mAboutDataListener.onDataReceived(newQuery);


                /*List<Filme_suggestion> filme_suggestionList = new ArrayList<>();
                if(movie != null && movie.getTotalResults() > 0){
                    int tamanho;
                    if (movie.getTotalResults() < 20)
                        tamanho = movie.getTotalResults();
                    else tamanho = 20;
                    for(int i = 0; i < tamanho ; i ++){
                        filme_suggestionList.add(new Filme_suggestion(movie.getResults().get(i).getTitle(), movie.getResults().get(i).getId()));
                    }
                    searchView.swapSuggestions(filme_suggestionList);
                    searchView.hideProgress();
                }*/
            }
        });
    }

    /*public void callSearch(String query) {
        ArrayList<Estrutura_Lista> registrosFilmes = dbFilmes.ObterFilmesPorNome(query);
        Estrutura_Lista = registrosFilmes;
        adapter = new CustomAdapter(Estrutura_Lista, getApplicationContext());
        listView.setAdapter(adapter);
        VerificaSeTemRegistros();
    }*/



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ok", 1);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if(!BackFragmentHelper.fireOnBackPressedEvent(this)) {
            // lets do the default back action if fragments don't consume it
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.Config:
                //mChangeCategoryListener.OnChangeCat(2);
                intent = new Intent(getApplicationContext(), CadastroPerfil.class);
                intent.putExtra("Perfil", perfil);
                startActivity(intent);
                break;
            case R.id.LogOut:
                // Set LoggedIn status to false
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                return true;
        }
        return true;
    }

    @Override
    public void respond(Boolean carregar) {
        Lista_para_assistir lista_para_assistir = (Lista_para_assistir) sliderAdapter.getRegisteredFragment(1);
    }

    private OnAboutDataReceivedListener mAboutDataListener;

    public interface OnAboutDataReceivedListener {
        void onDataReceived(String query);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }

    private OnChangeCategory mChangeCategoryListener;

    public interface OnChangeCategory{
        void OnChangeCat(int cat);
    }

    public void setChangeCategoryListener(OnChangeCategory listener) {
        this.mChangeCategoryListener = listener;
    }
}

