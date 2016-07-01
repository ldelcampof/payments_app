package com.dconsultores.cobranzamalaga.io.model;

import android.util.Log;

import com.dconsultores.cobranzamalaga.models.Client;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ldelcampo on 25/06/16.
 */
public class ClientsResponse {
    @SerializedName(JSONkeys.CLIENT_INFO)
    ArrayList<Client> clients;

    public ArrayList<Client> getClients() {
        return clients;
    }
}
