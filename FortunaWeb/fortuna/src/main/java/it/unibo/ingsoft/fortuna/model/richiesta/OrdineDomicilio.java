package it.unibo.ingsoft.fortuna.model.richiesta;

public class OrdineDomicilio extends Ordine {
    private String telefono;
    private String indirizzo;
    private String tokenPagamento;
    private String email; // TBD
    private boolean inConsegna; // TBD


    public OrdineDomicilio() {}


    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTokenPagamento() {
        return this.tokenPagamento;
    }

    public void setTokenPagamento(String tokenPagamento) {
        this.tokenPagamento = tokenPagamento;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInConsegna() {
        return this.inConsegna;
    }

    public boolean getInConsegna() {
        return this.inConsegna;
    }

    public void setInConsegna(boolean inConsegna) {
        this.inConsegna = inConsegna;
    }


    public OrdineDomicilio telefono(String telefono) {
        setTelefono(telefono);
        return this;
    }

    public OrdineDomicilio indirizzo(String indirizzo) {
        setIndirizzo(indirizzo);
        return this;
    }

    public OrdineDomicilio tokenPagamento(String tokenPagamento) {
        setTokenPagamento(tokenPagamento);
        return this;
    }

    public OrdineDomicilio email(String email) {
        setEmail(email);
        return this;
    }

    public OrdineDomicilio inConsegna(boolean inConsegna) {
        setInConsegna(inConsegna);
        return this;
    }
}
