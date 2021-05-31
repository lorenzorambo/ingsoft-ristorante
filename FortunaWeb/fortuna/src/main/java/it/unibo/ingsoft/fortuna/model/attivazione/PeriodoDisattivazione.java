package it.unibo.ingsoft.fortuna.model.attivazione;

import java.time.LocalDateTime;

import it.unibo.ingsoft.fortuna.model.Prodotto;

public class PeriodoDisattivazione {
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private TipoDisattivazione tipo;
    private Prodotto prodotto;


    public PeriodoDisattivazione() {
    }

    public PeriodoDisattivazione(LocalDateTime inizio, LocalDateTime fine, TipoDisattivazione tipo, Prodotto prodotto) {
        this.inizio = inizio;
        this.fine = fine;
        this.tipo = tipo;
        this.prodotto = prodotto;
    }

    public PeriodoDisattivazione(LocalDateTime inizio, LocalDateTime fine, Prodotto prodotto) {
        this(inizio, fine, TipoDisattivazione.PRODOTTO, prodotto);
    }

    public PeriodoDisattivazione(LocalDateTime inizio, LocalDateTime fine, TipoDisattivazione tipo) {
        this(inizio, fine, tipo, null);
    }


    public LocalDateTime getInizio() {
        return this.inizio;
    }

    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    public LocalDateTime getFine() {
        return this.fine;
    }

    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    public TipoDisattivazione getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDisattivazione tipo) {
        this.tipo = tipo;
    }

    public Prodotto getProdotto() {
        return this.prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    

    public PeriodoDisattivazione inizio(LocalDateTime inizio) {
        setInizio(inizio);
        return this;
    }

    public PeriodoDisattivazione fine(LocalDateTime fine) {
        setFine(fine);
        return this;
    }

    public PeriodoDisattivazione tipo(TipoDisattivazione tipo) {
        setTipo(tipo);
        return this;
    }

    public PeriodoDisattivazione prodotto(Prodotto prodotto) {
        setProdotto(prodotto);
        return this;
    }
}