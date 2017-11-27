package com.codemaicon.agendacontato;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.codemaicon.agendacontato.app.MessageBox;
import com.codemaicon.agendacontato.database.DataBase;
import com.codemaicon.agendacontato.domain.RepositorioContato;
import com.codemaicon.agendacontato.entity.Contato;

import java.util.ArrayList;


public class ActContato extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton btnAdicionar;
    private EditText edtPesquisa;
    private ListView lstContatos;
    private ArrayAdapter<Contato> adpContatos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private FiltraDados filtraDados;

    public static final String PARAM_CONTATO = "CONTATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contato);
        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        lstContatos = (ListView) findViewById(R.id.lstContatos);


        btnAdicionar.setOnClickListener(this);
        lstContatos.setOnItemClickListener(this);
        try {


            dataBase = new DataBase(this);
            //cria e abre o bd
            //alteracao no bd
            conn = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(conn); // conexao ativa

            adpContatos = repositorioContato.buscaContatos(this);

            lstContatos.setAdapter(adpContatos);
                // a partir do obj faz o filtro
            filtraDados = new FiltraDados(adpContatos);
            edtPesquisa.addTextChangedListener(filtraDados);
      /*     AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("conexao criada com sucesso");
            dlg.setNeutralButton("OK", null);
            dlg.show();*/

        } catch (SQLException ex) {
            MessageBox.show(this,"Erro", "|Erro ao criar o banco"+ ex.getMessage() );
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(conn != null){
            conn.close();
        }

    }
    @Override
    public void onClick(View view) {
        Intent it = new Intent(this, ActCadContatos.class);
        //cria codigo para act
        startActivityForResult(it, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpContatos = repositorioContato.buscaContatos(this);
        filtraDados.setArrayAdapter(adpContatos);
        lstContatos.setAdapter(adpContatos);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Contato contato = adpContatos.getItem(position);
        Intent it = new Intent(this, ActCadContatos.class);
        it.putExtra(PARAM_CONTATO, contato);
        startActivityForResult(it, 0);
    }
        private class FiltraDados implements TextWatcher{

        private ArrayAdapter<Contato> arrayAdapter;
        private FiltraDados(ArrayAdapter<Contato> arrayAdapter){
            this.arrayAdapter = arrayAdapter;
        }
        public void setArrayAdapter(ArrayAdapter<Contato> arrayAdapter){
            this.arrayAdapter = arrayAdapter;
        }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //responsavel por capturar dados que usuario digitar
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }

}
