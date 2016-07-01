package com.dconsultores.cobranzamalaga.io.model;

import com.dconsultores.cobranzamalaga.models.Client;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ldelcampo on 27/06/16.
 */
public class ClientResponse {
    @SerializedName(JSONkeys.CLIENT)
    Client client;

    @SerializedName(JSONkeys.TYPE)
    String type;

    @SerializedName(JSONkeys.PAYMENT)
    String payment;

    public Client getClient(){ return client; }
    public String getType(){ return type; }
    public String getPay(){ return payment; }
}
