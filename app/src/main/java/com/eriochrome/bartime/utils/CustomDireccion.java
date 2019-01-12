package com.eriochrome.bartime.utils;

public class CustomDireccion {
    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
    private String pais;

    public CustomDireccion(String calle, String numero, String ciudad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
    }

    public String construir() {
        StringBuilder devolver = new StringBuilder();
        if (calle != null)
            devolver.append(calle);
        if (numero != null)
            devolver.append(" ").append(numero);
        if (ciudad != null)
            devolver.append(", ").append(ciudad);
        if (provincia != null)
            devolver.append(", ").append(provincia);
        if (pais != null)
            devolver.append(", ").append(pais);

        return devolver.toString();
    }
}
