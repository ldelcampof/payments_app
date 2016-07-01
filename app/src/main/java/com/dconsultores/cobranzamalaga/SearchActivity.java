package com.dconsultores.cobranzamalaga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    public final static String EXTRA_search = "com.dconsultores.cobranzamalaga.SEARCH";
    public final static String EXTRA_salesman_id = "com.dconsultores.cobranzamalaga.MESSAGE";

    Intent intent;
    String salesman_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        salesman_id = intent.getStringExtra(ClientsActivity.EXTRA_salesman_id);
        Log.e("ES", salesman_id);

        setContentView(R.layout.activity_search);
    }

    public void openClientList(View view){
        EditText search = (EditText) findViewById(R.id.search_box);

        Intent intent = new Intent(this, ClientsActivity.class);
        intent.putExtra(EXTRA_salesman_id, salesman_id);
        intent.putExtra(EXTRA_search, search.getText().toString());

        startActivity(intent);
    }
}
