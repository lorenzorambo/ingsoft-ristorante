package it.unibo.ingsoft.fortuna.model.attivazione;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.unibo.ingsoft.fortuna.model.Prodotto;

@SpringBootTest
public class InsiemePeriodiDisattivazioneTest {
    private InsiemePeriodiDisattivazione insiemePeriodiDisattivazione;
    private HashMap<String, PeriodoDisattivazione> mappaPeriodi;
    private ArrayList<Prodotto> listaProdotti;

    @BeforeEach
    public void setup() {
        insiemePeriodiDisattivazione = new InsiemePeriodiDisattivazione();

        listaProdotti = new ArrayList<>();
        mappaPeriodi = new HashMap<>();

        listaProdotti.add(new Prodotto("Involtini", 102, 2.50));
        listaProdotti.add(new Prodotto("Ravioli", 104, 3.50));
        listaProdotti.add(new Prodotto("Riso", 150, 4));

        mappaPeriodi.put("prenot_maggio_giugno", new PeriodoDisattivazione(LocalDateTime.of(2021, 05, 15, 0, 0), LocalDateTime.of(2021, 06, 25, 12, 00), TipoDisattivazione.PRENOTAZIONE));
        mappaPeriodi.put("prenot_giugno", new PeriodoDisattivazione(LocalDateTime.of(2021, 06, 20, 0, 0), LocalDateTime.of(2021, 06, 30, 23, 59), TipoDisattivazione.PRENOTAZIONE));
        mappaPeriodi.put("ordinaz_maggio_giugno", new PeriodoDisattivazione(LocalDateTime.of(2021, 05, 1, 0, 0), LocalDateTime.of(2021, 06, 30, 23, 59), TipoDisattivazione.ORDINAZ_ASPORTO));
        mappaPeriodi.put("involtini_maggio_giugno", new PeriodoDisattivazione(LocalDateTime.of(2021, 05, 7, 0, 0), LocalDateTime.of(2021, 06, 10, 12, 00), listaProdotti.get(0)));
        mappaPeriodi.put("ravioli_aprile_giugno", new PeriodoDisattivazione(LocalDateTime.of(2021, 04, 7, 0, 0), LocalDateTime.of(2021, 06, 5, 12, 00), listaProdotti.get(1)));
    }

    @Test
    public void testBasics() {
        assertTrue(insiemePeriodiDisattivazione.getPeriodi().isEmpty());

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        assertTrue(insiemePeriodiDisattivazione.getPeriodi().size() == 1);
        assertEquals(TipoDisattivazione.PRENOTAZIONE, insiemePeriodiDisattivazione.get(0).getTipo());

        insiemePeriodiDisattivazione.remove(mappaPeriodi.get("prenot_maggio_giugno"));
        assertTrue(insiemePeriodiDisattivazione.getPeriodi().isEmpty());
    }

    @Test
    public void controllaTipo() {
        LocalDateTime fineMaggio = LocalDateTime.of(2021, 05, 30, 0, 0);
        LocalDateTime fineGiugno = LocalDateTime.of(2021, 06, 30, 0, 0);
        LocalDateTime dataOverlap = LocalDateTime.of(2021, 06, 21, 0, 0);
        LocalDateTime gennaio = LocalDateTime.of(2021, 01, 15, 0, 0);

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(dataOverlap, TipoDisattivazione.PRENOTAZIONE));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineGiugno, TipoDisattivazione.PRENOTAZIONE));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.ORDINAZ_ASPORTO));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_giugno"));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineGiugno, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(dataOverlap, TipoDisattivazione.PRENOTAZIONE));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.ORDINAZ_DOMICILIO));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));
        assertTrue(insiemePeriodiDisattivazione.controlla(gennaio, TipoDisattivazione.PRENOTAZIONE));

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ordinaz_maggio_giugno"));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineGiugno, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(dataOverlap, TipoDisattivazione.PRENOTAZIONE));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.ORDINAZ_ASPORTO));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.ORDINAZ_DOMICILIO));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));
        assertTrue(insiemePeriodiDisattivazione.controlla(gennaio, TipoDisattivazione.PRENOTAZIONE));
    }

    @Test
    public void controllaProdotto() {
        LocalDateTime fineMaggio = LocalDateTime.of(2021, 05, 30, 0, 0);
        LocalDateTime fineAprile = LocalDateTime.of(2021, 04, 30, 0, 0);
        LocalDateTime gennaio = LocalDateTime.of(2021, 01, 15, 0, 0);

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("involtini_maggio_giugno"));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(1)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, TipoDisattivazione.PRENOTAZIONE));
        assertTrue(insiemePeriodiDisattivazione.controlla(gennaio, listaProdotti.get(0)));

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ravioli_aprile_giugno"));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(1)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(2)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineAprile, listaProdotti.get(0)));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineAprile, listaProdotti.get(1)));
        
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno")); //controlla che non venga alterato
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(0)));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(1)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineMaggio, listaProdotti.get(2)));
        assertTrue(insiemePeriodiDisattivazione.controlla(fineAprile, listaProdotti.get(0)));
        assertFalse(insiemePeriodiDisattivazione.controlla(fineAprile, listaProdotti.get(1)));
    }

    @Test
    void getTipiDisattivati() {
        LocalDateTime fineMaggio = LocalDateTime.of(2021, 05, 30, 0, 0);
        LocalDateTime fineAprile = LocalDateTime.of(2021, 04, 30, 0, 0);
        LocalDateTime gennaio = LocalDateTime.of(2021, 01, 15, 0, 0);

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(gennaio).isEmpty());
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).contains(TipoDisattivazione.PRENOTAZIONE));
        assertEquals(1, insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).size());
        
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ordinaz_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("involtini_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ravioli_aprile_giugno"));

        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(gennaio).isEmpty());

        assertEquals(3, insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).size());
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).contains(TipoDisattivazione.PRENOTAZIONE));
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).contains(TipoDisattivazione.ORDINAZ_ASPORTO));
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(fineMaggio).contains(TipoDisattivazione.PRODOTTO)); //vuol dire che sono disattivati _dei_ prodotti

        assertEquals(1, insiemePeriodiDisattivazione.getTipiDisattivati(fineAprile).size());
        assertTrue(insiemePeriodiDisattivazione.getTipiDisattivati(fineAprile).contains(TipoDisattivazione.PRODOTTO));
    }

    @Test
    void getProdottiDisattivati() {
        LocalDateTime fineMaggio = LocalDateTime.of(2021, 05, 30, 0, 0);
        LocalDateTime fineAprile = LocalDateTime.of(2021, 04, 30, 0, 0);
        LocalDateTime gennaio = LocalDateTime.of(2021, 01, 15, 0, 0);

        insiemePeriodiDisattivazione.add(mappaPeriodi.get("involtini_maggio_giugno"));
        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(gennaio).isEmpty());
        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(fineMaggio).contains(listaProdotti.get(0)));
        assertEquals(1, insiemePeriodiDisattivazione.getProdottiDisattivati(fineMaggio).size());
        
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("prenot_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ordinaz_maggio_giugno"));
        insiemePeriodiDisattivazione.add(mappaPeriodi.get("ravioli_aprile_giugno"));

        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(gennaio).isEmpty());

        assertEquals(2, insiemePeriodiDisattivazione.getProdottiDisattivati(fineMaggio).size());
        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(fineMaggio).contains(listaProdotti.get(0)));
        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(fineMaggio).contains(listaProdotti.get(1)));

        assertEquals(1, insiemePeriodiDisattivazione.getProdottiDisattivati(fineAprile).size());
        assertTrue(insiemePeriodiDisattivazione.getProdottiDisattivati(fineAprile).contains(listaProdotti.get(1)));
    }

}
