package com.dconsultores.cobranzamalaga.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dconsultores.cobranzamalaga.ClientActivity;
import com.dconsultores.cobranzamalaga.R;
import com.dconsultores.cobranzamalaga.models.Client;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by ldelcampo on 25/06/16.
 */
public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder> {
    Context context;
    ArrayList<Client> clients;

    public ClientsAdapter(Context context){
        this.context = context;
        this.clients = new ArrayList<>();
    }

    @Override
    public ClientsAdapter.ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_client_card, parent, false);

        return new ClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        Client currentClient = clients.get(position);

        holder.setClient(currentClient.getName(), currentClient.getDebt(), currentClient.getId());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    public void addAll(@NonNull ArrayList<Client> clients){
        if(clients == null)
            throw new NullPointerException("The array cannot be null");

        this.clients.clear();
        this.clients.addAll(clients);
        notifyDataSetChanged();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView client_name;
        TextView client_debt;
        TextView client_id;

        public ClientViewHolder(final View itemView) {
            super(itemView);

            client_name = (TextView) itemView.findViewById(R.id.txt_client_name);
            client_debt = (TextView) itemView.findViewById(R.id.txt_client_debt);
            client_id = (TextView) itemView.findViewById(R.id.txt_client_id);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                Log.d("AClick", String.valueOf(client_id.getText().toString()));
                Intent intent = new Intent(itemView.getContext(), ClientActivity.class);
                Bundle params = new Bundle();
                params.putInt("client_id", Integer.parseInt(client_id.getText().toString()));
                intent.putExtras(params);

                v.getContext().startActivity(intent);
                }
            });

        }

        public void setClient (String name, Double debt, int id){
            NumberFormat nm = NumberFormat.getNumberInstance();

            Log.e("Deuda", nm.format(debt));
            client_name.setText(name);
            client_debt.setText('$' + debt.toString());
            client_id.setText(Integer.toString(id));
        }
    }
}
