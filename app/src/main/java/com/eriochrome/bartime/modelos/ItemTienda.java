package com.eriochrome.bartime.modelos;

public class ItemTienda {

    private String descripcion;
    private int costo;
    private String id;

    public ItemTienda() {
    }

    public ItemTienda(String descripcion, int costo) {
        this.descripcion = descripcion;
        this.costo = costo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getCosto() {
        return costo;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }
}
