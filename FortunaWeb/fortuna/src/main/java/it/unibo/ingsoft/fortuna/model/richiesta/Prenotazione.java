package it.unibo.ingsoft.fortuna.model.richiesta;

import java.time.LocalDateTime;

public class Prenotazione extends Richiesta {
    private String telefono;
    private int numeroPersone;


    public Prenotazione() {}

    public Prenotazione(String nominativo, LocalDateTime dataOra, String telefono, int numeroPersone)
    {
        super(nominativo, dataOra);
        this.telefono = telefono;
        this.numeroPersone = numeroPersone;
    }


    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getNumeroPersone() {
        return this.numeroPersone;
    }

    public void setNumeroPersone(int numeroPersone) {
        this.numeroPersone = numeroPersone;
    }


    public Prenotazione dataOra(LocalDateTime dataOra) {
        setDataOra(dataOra);
        return this;
    }

    public Prenotazione nominativo(String nominativo) {
        setNominativo(nominativo);
        return this;
    }

    public Prenotazione telefono(String telefono) {
        setTelefono(telefono);
        return this;
    }

    public Prenotazione numeroPersone(int numeroPersone) {
        setNumeroPersone(numeroPersone);
        return this;
    }
}
