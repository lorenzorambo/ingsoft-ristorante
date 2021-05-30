package it.unibo.ingsoft.fortuna.model.richiesta;

public class OrdineAlTavolo extends Ordine {
    private String tavolo;


    public OrdineAlTavolo() {
    }


    public String getTavolo() {
        return this.tavolo;
    }

    public void setTavolo(String tavolo) {
        this.tavolo = tavolo;
    }


    public OrdineAlTavolo tavolo(String tavolo) {
        setTavolo(tavolo);
        return this;
    }
}
