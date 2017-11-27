
/*

Array adapter preenche o conteudo tanto do list view quando do spinner
portanto é essencial utilizar o array adapter.
 */

package com.codemaicon.agendacontato;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.codemaicon.agendacontato.app.MessageBox;
import com.codemaicon.agendacontato.database.DataBase;
import com.codemaicon.agendacontato.domain.RepositorioContato;
import com.codemaicon.agendacontato.entity.Contato;
import com.codemaicon.agendacontato.util.DateUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

//implementar seta voltar no action bar
public class ActCadContatos extends AppCompatActivity {
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtTelefone;
    private EditText edtEndereco;
    private EditText edtDatasEspeciais;
    private EditText edtGrupos;
    private Spinner spnTipoEmail;
    private Spinner spnTipoTelefone;

    private Spinner spnTipoEndereco;
    private Spinner spnTipoDatasEspeciais;
    private ArrayAdapter<String> adpTipoEmail;
    private ArrayAdapter<String> adpTipoTelefone;
    private ArrayAdapter<String> adpTipoEndereco;
    private ArrayAdapter<String> adpTipoDatasEspeciais;

    String arquivo;
    Uri outputFileUri;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cad_contatos);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        edtDatasEspeciais = (EditText) findViewById(R.id.edtDatasEspeciais);
        edtGrupos = (EditText) findViewById(R.id.edtGrupos);

        spnTipoEmail = (Spinner) findViewById(R.id.spnTipoEmail);
        spnTipoTelefone = (Spinner) findViewById(R.id.spnTipoTelefone);
        spnTipoEndereco = (Spinner) findViewById(R.id.spnTipoEndereco);
        spnTipoDatasEspeciais = (Spinner) findViewById(R.id.spnDatasEspeciais);
        //cria instancia
        adpTipoEmail = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTipoEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpTipoTelefone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTipoTelefone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpTipoEndereco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTipoEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpTipoDatasEspeciais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTipoDatasEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //associa cada objt tipo spinner ao obj array adpater.
        spnTipoEmail.setAdapter(adpTipoEmail);
        spnTipoTelefone.setAdapter(adpTipoTelefone);
        spnTipoEndereco.setAdapter(adpTipoEndereco);
        spnTipoDatasEspeciais.setAdapter(adpTipoDatasEspeciais);

        //adicionar os itens
        adpTipoEmail.add("pessoal");
        adpTipoEmail.add("trabalho");
        adpTipoEmail.add("outros");

        adpTipoTelefone.add("celular");
        adpTipoTelefone.add("trabalho");
        adpTipoTelefone.add("casa");
        adpTipoTelefone.add("principal");
        adpTipoTelefone.add("fax");
        adpTipoTelefone.add("outros");

        adpTipoEndereco.add("casa");
        adpTipoEndereco.add("trabalho");
        adpTipoEndereco.add("outros");

        adpTipoDatasEspeciais.add("aniversario");
        adpTipoDatasEspeciais.add("data comemorativa");
        adpTipoDatasEspeciais.add("outros");

        //referenciando o dataListener
        ExibeDataListener listener = new ExibeDataListener();

        edtDatasEspeciais.setOnClickListener(listener);
        edtDatasEspeciais.setOnFocusChangeListener(listener);
        //impede que digite no campo data
        edtDatasEspeciais.setOnKeyListener(null);
        //recupera obj // retorna bundle
        //se exta excluindo ou alterando o contato
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null ) && (bundle.containsKey(ActContato.PARAM_CONTATO))) {
            contato = (Contato) bundle.getSerializable(ActContato.PARAM_CONTATO);
            preencheDados();

        } else {
            contato = new Contato();
        }


        contato = new Contato();


        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioContato = new RepositorioContato(conn);

        } catch (SQLException ex) {
            MessageBox.show(this, "Erro", "|Erro ao criar o banco" + ex.getMessage());
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(conn != null){
            conn.close();
        }

    }

    //exibir menu
    //ainda nao exclui descobrir pq alterando tbm
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_contatos, menu);

        if (contato.getId() != 0)
            //habilita o excluir
            menu.getItem(1).setVisible(true);

        return super.onCreateOptionsMenu(menu);


    }

    //trata o item selecionado
// passa o obj do menu selecionado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_acao1:

                salvar();
                finish();
                break;
            case R.id.mni_acao2:
                excluir();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void preencheDados() {
        edtNome.setText(contato.getNome());
        edtTelefone.setText(contato.getTelefone());
        spnTipoTelefone.setSelection(Integer.parseInt(contato.getTipoTelefone()));
        edtEmail.setText(contato.getEmail());
        spnTipoEmail.setSelection(Integer.parseInt(contato.getTipoEmail()));
        edtEndereco.setText(contato.getEndereco());
        spnTipoEndereco.setSelection(Integer.parseInt(contato.getTipoEndereco()));

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(contato.getDatasEspeciais());
        edtDatasEspeciais.setText(dt);

        spnTipoDatasEspeciais.setSelection(Integer.parseInt(contato.getTipoDatasEspeciais()));
        edtGrupos.setText(contato.getGrupos());


    }

    private void excluir() {
        try {
            repositorioContato.excluir(contato.getId());
        } catch (SQLException ex) {

            MessageBox.show(this, "Erro", "Erro ao excluir os dados" + ex.getMessage());

        }


    }

    private void salvar() {

        try {


            contato.setNome(edtNome.getText().toString());
            contato.setTelefone(edtTelefone.getText().toString());
            contato.setEmail(edtEndereco.getText().toString());
            contato.setEndereco(edtEndereco.getText().toString());


            contato.setGrupos(edtGrupos.getText().toString());
            // resgata item selecionado no spinner
            contato.setTipoEndereco(String.valueOf(spnTipoEndereco.getSelectedItemPosition()));
            contato.setTipoTelefone(String.valueOf(spnTipoTelefone.getSelectedItemPosition()));
            contato.setTipoEmail(String.valueOf(spnTipoEmail.getSelectedItemPosition()));
            contato.setTipoDatasEspeciais(String.valueOf(spnTipoDatasEspeciais.getSelectedItemPosition()));

            if (contato.getId() == 0)
                repositorioContato.inserir(contato);
            else
                repositorioContato.alterar(contato);

        } catch (Exception ex) {
            MessageBox.show(this, "Erro", "|Erro ao salvar os dados no banco" + ex.getMessage());
        }

    }

    private void exibeData() {
        Calendar calendar = Calendar.getInstance();
        //recupera data atual
        int ano = calendar.get(calendar.YEAR);
        int mes = calendar.get(calendar.MONTH);
        int dia = calendar.get(calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener {

        @Override
        public void onClick(View view) {
            exibeData();
        }

        @Override
        public void onFocusChange(View view, boolean foco) {
            if (foco)
                exibeData();
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dt = DateUtils.dateToString(year, month, day);
            Date data = DateUtils.getDate(year, month, day);


            edtDatasEspeciais.setText(dt);
            contato.setDatasEspeciais(data);

        }
    }
    //Chamado quando acionado pelo usuário
    public void abrirCamera(View v){
        //Cria uma intenção para abrir a camera fotográfica
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Informa que a camera a ser aberta é a frontal
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        //Montagem do caminho onde o arquivo será salvo
        arquivo = Environment.getExternalStorageDirectory() + "/Pictures/fotoMyIMC.jpg";

        //Abre o caminho onde a foto será salva
        File file = new File(arquivo);
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        //Abre a camera
        startActivityForResult(intent, RESULT_FIRST_USER);
    }


    public void verImagem( View v ){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        arquivo = Environment.getExternalStorageDirectory() + "/Pictures/fotoMyIMC.jpg";
        intent.setDataAndType(Uri.parse("file://" + arquivo), "image/*");
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        //TODO Auto-generated method stub
        super.onResume();
        carregaImagem();
    }

    public void carregaImagem(){
        ImageView imageView = (ImageView) findViewById(R.id.foto);
        arquivo = Environment.getExternalStorageDirectory() + "/Pictures/fotoMyIMC.jpg";
        imageView.setImageURI( Uri.parse(arquivo) );
    }

}
