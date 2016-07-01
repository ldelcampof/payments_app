package com.dconsultores.cobranzamalaga.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dconsultores.cobranzamalaga.R;
import com.dconsultores.cobranzamalaga.io.APIAdapter;
import com.dconsultores.cobranzamalaga.io.model.ClientResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaymentFragment extends Fragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface ItemSelectedListener {
        public String getClientName();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        Button payment_button = (Button) root.findViewById(R.id.button_pay);

        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView client_id = (TextView) getActivity().findViewById(R.id.detail_client_id);
                EditText payment = (EditText) getActivity().findViewById(R.id.input_payment);
                Log.e("Activity", (String) client_id.getText());

                APIAdapter.getAPIService().setPayment(
                        Integer.parseInt(client_id.getText().toString()),
                        Double.parseDouble(payment.getText().toString()),
                        (Callback<ClientResponse>) getActivity());

                payment.getText().clear();
            }
        });

        return root;
    }

}
