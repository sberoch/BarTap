package com.eriochrome.bartime.modelos;

public class Filtro {

    private boolean abierto;
    private boolean happyHour;
    private String ordenamiento;
    private boolean pagoEfectivo;
    private boolean pagoCredito;
    private boolean pagoDebito;

    public void ordenarSegun(String ordenamiento) {
        this.ordenamiento = ordenamiento;
    }

    public String getOrdenamiento() {
        return ordenamiento;
    }

    public boolean filtroAbierto() {
        return abierto;
    }

    public boolean filtroHappyHour() {
        return happyHour;
    }

    public void aplicarAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public void aplicarHappyHour(boolean happyHour) {
        this.happyHour = happyHour;
    }

    public boolean filtroPagoEfectivo() {
        return pagoEfectivo;
    }

    public boolean filtroPagoCredito() {
        return pagoCredito;
    }

    public boolean filtroPagoDebito() {
        return pagoDebito;
    }

    public void aplicarMetodosDePago(boolean pagoEfectivo, boolean pagoCredito, boolean pagoDebito) {
        this.pagoEfectivo = pagoEfectivo;
        this.pagoCredito = pagoCredito;
        this.pagoDebito = pagoDebito;
    }
}
