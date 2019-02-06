package com.eriochrome.bartime.modelos.entidades;

public class PreguntaTrivia {

    private String pregunta;
    private String opcionCorrecta;
    private String opcionA;
    private String opcionB;
    private String opcionC;

    public PreguntaTrivia() {
    }

    public PreguntaTrivia(String pregunta, String opcionA, String opcionB, String opcionC, String correcta) {
        this.pregunta = pregunta;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.opcionCorrecta = correcta;
    }

    public String getPregunta() {
        return pregunta;
    }
    public String getOpcionCorrecta() {
        return opcionCorrecta;
    }
    public String getOpcionA() {
        return opcionA;
    }
    public String getOpcionB() {
        return opcionB;
    }
    public String getOpcionC() {
        return opcionC;
    }

}
