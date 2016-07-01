package com.dconsultores.cobranzamalaga.io;

import com.dconsultores.cobranzamalaga.ClientsActivity;
import com.dconsultores.cobranzamalaga.io.model.ClientResponse;
import com.dconsultores.cobranzamalaga.io.model.ClientsResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ldelcampo on 25/06/16.
 */
public interface ClientsAPIService {
    @GET(APIConstants.CLIENTS)
    void getClients(@Path("salesman_id") int salesman_id, Callback<ClientsResponse> serverResponse);

    @GET(APIConstants.CLIENT)
    void getClient(@Path("client_id") int client_id, Callback<ClientResponse> serverResponse);

    @POST(APIConstants.PAYMENT)
    void setPayment(@Path("id") int i, @Body double payment, Callback<ClientResponse> serverResponse);

    @GET(APIConstants.CLIENTSSEARCH)
    void getClientsSearch(@Path("sales_id") int salesman_id, @Path("search") String search, Callback<ClientsResponse> serverResponse);
}