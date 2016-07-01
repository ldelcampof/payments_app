package com.dconsultores.cobranzamalaga.io;

import retrofit.RestAdapter;

/**
 * Created by ldelcampo on 25/06/16.
 */
public class APIAdapter {
    private static ClientsAPIService APIService;

    public static ClientsAPIService getAPIService(){
        // Se utiliza un singleton para invocar al adaptador
        // En caso de ser igual a null el servicio es inicializado
        if (APIService == null){
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(APIConstants.URL_BASE)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            APIService = adapter.create(ClientsAPIService.class);
        }

        // Se retorna el servicio
        return APIService;
    }
}
