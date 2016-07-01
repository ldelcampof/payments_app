package com.dconsultores.cobranzamalaga;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dconsultores.cobranzamalaga.io.APIAdapter;
import com.dconsultores.cobranzamalaga.io.model.ClientsResponse;
import com.dconsultores.cobranzamalaga.ui.ItemOffsetDecoration;
import com.dconsultores.cobranzamalaga.ui.adapter.ClientsAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ClientsActivity extends AppCompatActivity implements Callback<ClientsResponse> {
    public final static String EXTRA_salesman_id = "com.dconsultores.cobranzamalaga.SALESMAN";
    private RecyclerView mRecyclerView;
    private ClientsAdapter mAdapter;
    private int salesman_id;
    private String search = null;
    private String salesman_id_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        salesman_id_txt = intent.getStringExtra(MainActivity.EXTRA_salesman_id);

        setContentView(R.layout.activity_clients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                intent.putExtra(EXTRA_salesman_id, salesman_id_txt);
                startActivity(intent);
            }
        });

        if(salesman_id_txt == null){
            salesman_id_txt = intent.getStringExtra(SearchActivity.EXTRA_salesman_id);
        }
        String search_txt = intent.getStringExtra(SearchActivity.EXTRA_search);
        if(search_txt != null){
            search = search_txt;
            fab.setVisibility(View.GONE);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.client_list);

        mAdapter = new ClientsAdapter(this);
        salesman_id = Integer.parseInt(salesman_id_txt);
        setupClientList();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(search != null) {
            Log.e("SEARCH", search);
            APIAdapter.getAPIService()
                    .getClientsSearch(salesman_id, search, this);
        }else{
            APIAdapter.getAPIService()
                    .getClients(salesman_id, this);
        }
    }

    private void setupClientList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.integer.offset));
    }

    @Override
    public void success(ClientsResponse clientsResponse, Response response) {
        mAdapter.addAll(clientsResponse.getClients());
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
    }
}
