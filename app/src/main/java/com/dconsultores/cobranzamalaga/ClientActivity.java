package com.dconsultores.cobranzamalaga;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dconsultores.cobranzamalaga.io.APIAdapter;
import com.dconsultores.cobranzamalaga.io.model.ClientResponse;
import com.dconsultores.cobranzamalaga.models.Client;
import com.dconsultores.cobranzamalaga.ui.PaymentFragment;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.zip.Inflater;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ClientActivity extends AppCompatActivity implements Callback<ClientResponse> {
    private Bundle params;
    private Client client;
    private String type;

    private TextView name;
    private TextView phone;
    private TextView address;
    private TextView debt;
    private TextView client_id;


    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mmDevice;
    private BluetoothSocket mmSocket;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    private Thread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        params = getIntent().getExtras();

        int client_id = params.getInt("client_id");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.payment_fragment, new PaymentFragment())
                .commit();

        APIAdapter.getAPIService().getClient(client_id, this);
    }

    @Override
    public void success(ClientResponse clientResponse, Response response) {
        client = clientResponse.getClient();
        type = clientResponse.getType();
        String payment = clientResponse.getPay();

        setClientInfo((TextView) findViewById(R.id.detail_client_id),
                (TextView) findViewById(R.id.detail_client_name),
                (TextView) findViewById(R.id.detail_client_phone),
                (TextView) findViewById(R.id.detail_client_address),
                (TextView) findViewById(R.id.detail_client_debt));

        setTextClientInfo(client);


        String mensaje = "------- MUEBLES MALAGA ------- \n";
        mensaje += "Se ha recibido del (la) Sr(a).: " + name.getText().toString();
        mensaje += " abono por: $" + payment + "\n";
        mensaje += "Restando un saldo por: $"+ debt.getText().toString() + "\n";
        mensaje += "Dudas o aclaraciones favor de comunicarse al 812 6250\n\n\n\n";
        if(type.equals("Payment")){
            try {
                findBT();
                openBT();
                SendData(mensaje);
                closeBT();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }

    public void setClientInfo(TextView id, TextView name, TextView phone, TextView address, TextView debt){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.debt = debt;
        client_id = id;
    }


    public void setTextClientInfo(Client client){
        Log.e("ID CLIENTE", String.valueOf(client.getId()));

        client_id.setText(String.valueOf(client.getId()));
        this.name.setText(client.getName());
        this.address.setText(client.getDireccion());
        this.debt.setText("$ " + client.getDebt().toString());
        this.phone.setText(client.getTelefono());
    }



    void findBT(){
        try{
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null){
                // Snackbar.make(findViewById(), "Impresora Bluetooth no disponible", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Log.e("ErrorBluetooth", "No disponible");
            }

            if(!mBluetoothAdapter.isEnabled()){
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairDevices = mBluetoothAdapter.getBondedDevices();

            if(pairDevices.size() > 0){
                for (BluetoothDevice device : pairDevices){
                    if(device.getName().equals("btprint")){
                        mmDevice = device;
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void openBT() throws IOException {
        try{
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void beginListenForData(){
        try{
            final Handler handler = new Handler();

            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            stopWorker = true;
                        }
                    }
                }
            });
            workerThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void SendData(String mensaje) throws IOException{
        try{
            mensaje += "\n";

            mmOutputStream.write(mensaje.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void closeBT() throws IOException{
        try{
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
