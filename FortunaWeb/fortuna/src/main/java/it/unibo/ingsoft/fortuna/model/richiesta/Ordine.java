package it.unibo.ingsoft.fortuna.model.richiesta;

import java.time.LocalDateTime;
import java.util.List;

import it.unibo.ingsoft.fortuna.model.*;

public abstract class Ordine extends Richiesta {
    private String note;
    private List<Prodotto> prodotti;
    private List<Sconto> sconti;

    protected Ordine() {}

    protected Ordine(String nominativo, LocalDateTime dataOra, String note, List<Prodotto> prodotti, List<Sconto> sconti)
    {
        super(nominativo, dataOra);
        this.note = note;
        this.prodotti = prodotti;
        this.sconti = sconti;
    }

    public double calcolaCostoTotale()
    {
        return prodotti.stream()
            .map(prodotto -> prodotto.getPrezzo())
            .reduce(0., (a, b) -> a + b);
    }

    public double calcolaCostoScontato()
    {
        double totale = calcolaCostoTotale();
        double costo = totale;

        for (Sconto sconto : sconti) {
            if (sconto.isAttivo(this.getDataOra(), totale))
            {
                costo -= sconto.getQuantita();
                costo -= sconto.getQuantitaPct() * totale;
            }

            for (Prodotto prodotto : prodotti)
            {
                if (sconto.isAttivo(this.getDataOra(), prodotto, totale))
                {
                    costo -= sconto.getQuantita();
                    costo -= sconto.getQuantitaPct() * prodotto.getPrezzo();
                }
            }
        }

        return costo;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Prodotto> getProdotti() {
        return this.prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public List<Sconto> getSconti() {
        return this.sconti;
    }

    public void setSconti(List<Sconto> sconti) {
        this.sconti = sconti;
    }


    public Ordine dataOra(LocalDateTime dataOra) {
        this.setDataOra(dataOra);
        return this;
    }

    public Ordine nominativo(String nominativo) {
        this.setNominativo(nominativo);
        return this;
    }

    public Ordine note(String note) {
        setNote(note);
        return this;
    }

    public Ordine prodotti(List<Prodotto> prodotti) {
        setProdotti(prodotti);
        return this;
    }

    public Ordine sconti(List<Sconto> sconti) {
        setSconti(sconti);
        return this;
    }
}
