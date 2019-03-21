package com.eriochrome.bartime.modelos.entidades;
import java.util.concurrent.ThreadLocalRandom;

public class ComprobanteDeCompra {

    private String nombreBar;
    private String descripcion;
    private int costo;
    private int nroComprobante;

    private static final int MIN = 100000;
    private static final int MAX = 1000000;

    public ComprobanteDeCompra() {

    }

    public ComprobanteDeCompra(ItemTienda itemTienda, String nombreBar) {
        this.descripcion = itemTienda.getDescripcion();
        this.costo = itemTienda.getCosto();
        this.nroComprobante = ThreadLocalRandom.current().nextInt(MIN, MAX);
        this.nombreBar = nombreBar;

    }

    public String getNombreBar() {
        return nombreBar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCosto() {
        return costo;
    }

    public int getNroComprobante() {
        return nroComprobante;
    }

}
