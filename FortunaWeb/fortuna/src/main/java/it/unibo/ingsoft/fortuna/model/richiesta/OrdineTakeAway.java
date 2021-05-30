package it.unibo.ingsoft.fortuna.model.richiesta;

public class OrdineTakeAway extends Ordine {
    private String telefono;


    public OrdineTakeAway() {
    }


    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    public OrdineTakeAway telefono(String telefono) {
        setTelefono(telefono);
        return this;
    }

}
