package it.unibo.ingsoft.fortuna.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.unibo.ingsoft.fortuna.model.richiesta.Ordine;
import it.unibo.ingsoft.fortuna.model.richiesta.OrdineDomicilio;

@SpringBootTest
public class OrdineTest {
    private ArrayList<Prodotto> listaProdotti;
    private ArrayList<Sconto> listaSconti;
    private Ordine ordine;

    @BeforeEach
    public void ordineSetUp()
    {
        listaProdotti = new ArrayList<Prodotto>();
        listaProdotti.add(new Prodotto("Involtini", 102, 2.50));
        listaProdotti.add(new Prodotto("Ravioli", 104, 3.50));
        listaProdotti.add(new Prodotto("Riso", 150, 4));

        listaSconti = new ArrayList<Sconto>();
        listaSconti.add(Sconto.of(LocalDateTime.of(2021, 06, 05, 0, 0), LocalDateTime.of(2021, 06, 30, 23, 59), 0, 0.1, 0));
        listaSconti.add(Sconto.of(LocalDateTime.of(2021, 05, 05, 0, 0), LocalDateTime.of(2021, 06, 10, 23, 59), 1, 0, 4.5));
        listaSconti.add(Sconto.ofProdotti(LocalDateTime.of(2021, 06, 11, 0, 0), LocalDateTime.of(2021, 06, 12, 23, 59), 0, 0.6, 6, listaProdotti.get(0)));

        ordine = new OrdineDomicilio();
        ordine.nominativo("Pinotto")
            .dataOra(LocalDateTime.of(2021, 06, 15, 12, 30, 0))
            .note("no insalata")
            .prodotti(listaProdotti)
            .sconti(listaSconti);
    }

    @Test
    public void testGetters()
    {
        assertEquals("Pinotto", ordine.getNominativo());
        assertEquals(LocalDateTime.of(2021, 06, 15, 12, 30, 0), ordine.getDataOra());
        assertEquals("no insalata", ordine.getNote());
        assertEquals(LocalDateTime.of(2021, 06, 05, 0, 0), ordine.getSconti().get(0).getInizio());
        assertEquals("Involtini", ordine.getProdotti().get(0).getNome());
    }

    @Test
    public void testSetters()
    {
        ordine.setNominativo("Smough");
        ordine.setDataOra(LocalDateTime.of(2021, 06, 20, 19, 30, 00));
        ordine.setNote("no formaggio");
        ordine.setSconti(new ArrayList<Sconto>());
        ArrayList<Prodotto> testProd = new ArrayList<Prodotto>();
        testProd.add(new Prodotto("Anima", 200, 15));
        ordine.setProdotti(testProd);

        assertEquals("Smough",ordine.getNominativo());
        assertEquals(LocalDateTime.of(2021, 06, 20, 19, 30, 00), ordine.getDataOra());
        assertEquals("no formaggio", ordine.getNote());
        assertTrue(ordine.getSconti().isEmpty());
        assertEquals("Anima", ordine.getProdotti().get(0).getNome());
    }

    @Test
    public void testCostoTotale()
    {
        assertEquals(10.0, ordine.calcolaCostoTotale());
    }

    @Test
    public void testScontoBase()
    {
        assertEquals(9.0, ordine.calcolaCostoScontato());
    }

    @Test
    public void testScontoPrezzoMinimo()
    {
        ordine.setDataOra(LocalDateTime.of(2021, 06, 06, 12, 30, 00)); //entra nel periodo del primo+secondo sconto
        assertEquals(8.0, ordine.calcolaCostoScontato());
        ArrayList<Prodotto> soloInvoltini = new ArrayList<Prodotto>();
        soloInvoltini.add(listaProdotti.get(0));
        ordine.setProdotti(soloInvoltini);
        assertEquals(2.50 - 2.50 * 0.1, ordine.calcolaCostoScontato()); //solo primo sconto 10%, no sconto con minimo
    }

    @Test
    public void testScontoProdotto()
    {
        ordine.setDataOra(LocalDateTime.of(2021, 06, 11, 12, 30, 00)); //entra nel periodo del primo+terzo sconto
        assertEquals(7.5, ordine.calcolaCostoScontato());
        ArrayList<Prodotto> sopra6NoInvoltini = new ArrayList<Prodotto>();
        sopra6NoInvoltini.add(listaProdotti.get(1));
        sopra6NoInvoltini.add(listaProdotti.get(2));
        ordine.setProdotti(sopra6NoInvoltini);
        assertEquals(6.75, ordine.calcolaCostoScontato()); //solo sconto fisso 10%, no sconto con minimo
    }}
