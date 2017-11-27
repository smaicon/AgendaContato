package com.codemaicon.agendacontato.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.widget.ArrayAdapter;

import com.codemaicon.agendacontato.ContatoArrayAdapter;
import com.codemaicon.agendacontato.R;
import com.codemaicon.agendacontato.entity.Contato;

import java.util.Date;

/**
 * Created by smaicon on 03/11/2017.
 */

public class RepositorioContato {

    private SQLiteDatabase conn;

    public RepositorioContato(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Contato contato) {

        ContentValues values = new ContentValues();

        values.put("NOME", contato.getNome());
        values.put("TELEFONE", contato.getTelefone());
        values.put("TIPOTELEFONE", contato.getTipoTelefone());
        values.put("EMAIL", contato.getEmail());
        values.put("TIPOEMAIL", contato.getTipoEmail());
        values.put("ENDERECO", contato.getEndereco());
        values.put("TIPOENDERECO", contato.getTipoEndereco());
        values.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        values.put("TIPODATASESPECIAIS", contato.getTipoDatasEspeciais());
        values.put("GRUPOS", contato.getGrupos());


        return values;
    }

    public void excluir(long id) {
        conn.delete(Contato.TABELA, " _id = ?", new String[]{String.valueOf(id)});
    }

    //ALTERAR CRIA NOVO
    public void alterar(Contato contato) {

        ContentValues values = preencheContentValues(contato);

        //atualiza os dados se mais parametros apos ? inserir and e demais campos   //converte para array de string
        conn.update(Contato.TABELA, values, "_id = ?", new String[]{String.valueOf(contato.getId())});

    }


    public void inserir(Contato contato) {

        ContentValues values = preencheContentValues(contato);
        //salva dados
        conn.insertOrThrow(Contato.TABELA, null, values);
    }

    public ContatoArrayAdapter buscaContatos(Context context)
    {

        ContatoArrayAdapter adpContatos = new ContatoArrayAdapter(context, R.layout.item_contato );

        Cursor cursor  =  conn.query(Contato.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0 )
        {

            cursor.moveToFirst();

            do {

                Contato contato = new Contato();
                contato.setId( cursor.getLong( cursor.getColumnIndex(Contato.ID) ) );
                contato.setNome( cursor.getString( cursor.getColumnIndex(Contato.NOME ) ) );
                contato.setTelefone( cursor.getString( cursor.getColumnIndex(Contato.TELEFONE ) ) );
                contato.setTipoTelefone( cursor.getString( cursor.getColumnIndex(Contato.TIPOTELEFONE ) ) );
                contato.setEmail(cursor.getString( cursor.getColumnIndex(Contato.EMAIL ) ));
                contato.setTipoEmail(cursor.getString( cursor.getColumnIndex(Contato.TIPOEMAIL ) ));
                contato.setEndereco(cursor.getString( cursor.getColumnIndex(Contato.ENDERECO) ));
                contato.setTipoEndereco(cursor.getString( cursor.getColumnIndex(Contato.TIPOENDERECO ) ));
                contato.setDatasEspeciais( new Date(cursor.getLong( cursor.getColumnIndex(Contato.DATASESPECIAIS ) )) );
                contato.setTipoDatasEspeciais(cursor.getString( cursor.getColumnIndex(Contato.TIPODATASESPECIAIS ) ));
                contato.setGrupos(cursor.getString( cursor.getColumnIndex(Contato.GRUPOS ) ));


                adpContatos.add(contato);

            }while (cursor.moveToNext());

        }

        return adpContatos;

    }
    //substituir pelo metodo abaixo
    /*
    public ContatoArrayAdapter buscaContatos(Context context) {

        ContatoArrayAdapter adpContatos = new ContatoArrayAdapter(context, R.layout.item_contato);

        Cursor cursor = conn.query(Contato.TABELA, null, null, null, null, null, null);



        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Contato contato = new Contato();



                contato.setId(cursor.getLong(0));
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                contato.setTipoTelefone(cursor.getString(3));
                contato.setEmail(cursor.getString(4));
                contato.setTipoEmail(cursor.getString(5));
                contato.setEndereco(cursor.getString(6));
                contato.setTipoEndereco(cursor.getString(7));
                contato.setDatasEspeciais(new Date(cursor.getLong(8)));
                contato.setTipoDatasEspeciais(cursor.getString(9));
                contato.setGrupos(cursor.getString(10));


                adpContatos.add(contato);

            } while (cursor.moveToNext());

        }
        return adpContatos;
    }*/

}