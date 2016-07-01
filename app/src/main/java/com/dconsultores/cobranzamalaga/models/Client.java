package com.dconsultores.cobranzamalaga.models;

import java.util.ArrayList;

/**
 * Created by ldelcampo on 25/06/16.
 */
public class Client {

    String nombre;
    Double adeudo;
    String domicilio;
    String telefono;
    int id;

    public Client(String name, Double debt, int id) {
        this.nombre = name;
        this.adeudo = debt;
        this.id = id;
    }

    public String getName() { return nombre; }

    public void setName(String name) {
        this.nombre = name;
    }

    public Double getDebt() { return adeudo; }

    public void setDebt(Double debt) {
        this.adeudo = debt;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() { return id; }

    public String getDireccion() { return domicilio; }

    public void setDireccion(String direccion) {
        this.domicilio = direccion;
    }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private static int lastContactId = 0;

    public static ArrayList<Client> createClientList(int numContacts) {
        ArrayList<Client> clients = new ArrayList<Client>();

        for (int i = 1; i <= numContacts; i++) {
            clients.add(new Client("Person " + ++lastContactId, 1000.00, ++lastContactId));
        }

        return clients;
    }
}
