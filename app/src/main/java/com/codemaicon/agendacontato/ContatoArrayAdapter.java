package com.codemaicon.agendacontato;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codemaicon.agendacontato.entity.Contato;
/*
 * //pra cada linha preenche com valor definido
 */

public class ContatoArrayAdapter extends ArrayAdapter<Contato> {
    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;


    public ContatoArrayAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(resource, parent, false);
            //  chama obj do layout
            viewHolder.txtCor = (TextView) view.findViewById(R.id.txtCor);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            viewHolder.txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);

            //associa obj viewHolder ao obj view
            view.setTag(viewHolder);
            convertView = view;
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;


        }

        //recupera item do arrayAdater com getItem.

        Contato contato = getItem(position);
        //testa se a primeira letra a para mudar de cor
        if (contato.getNome().toUpperCase().startsWith("A"))
        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.azul));
       else
           if (contato.getNome().toUpperCase().startsWith("B"))
        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.vermelho));
            else
        viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.laranja));


        viewHolder.txtNome.setText(contato.getNome());
        viewHolder.txtTelefone.setText(contato.getTelefone());


        return view;
    }

    static class ViewHolder {
        TextView txtCor;
        TextView txtNome;
        TextView txtTelefone;
    }
}
