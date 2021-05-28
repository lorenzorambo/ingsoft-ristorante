using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models.Richiesta
{
    public abstract class Ordine : Richiesta
    {
        private string _note;
        private IList<Prodotto> _prodotti;
        private ISet<Sconto> _sconti;

        protected Ordine(string nominativo, DateTime dataOra, string note, IList<Prodotto> prodotti, ISet<Sconto> sconti) : base(nominativo, dataOra)
        {
            _note = note;
            _prodotti = prodotti;
            _sconti = sconti;
        }

        public string Note { get => _note; set => _note = value; }
        public IList<Prodotto> Prodotti { get => _prodotti; set => _prodotti = value; }
        public ISet<Sconto> Sconti { get => _sconti; set => _sconti = value; }

        public double CalcolaCostoTotale()
        {
            return Prodotti.Sum(prodotto => prodotto.Prezzo);
        }

        public double CalcolaCostoScontato()
        {
            double totale = CalcolaCostoTotale();
            double costo = totale;

            foreach (Sconto sconto in Sconti) {
                if (sconto.IsAttivo(this.DataOra, totale))
                {
                    costo -= sconto.Quantita;
                    costo -= sconto.QuantitaPct * totale;
                }

                foreach (Prodotto prodotto in Prodotti)
                {
                    if (sconto.IsAttivo(this.DataOra, prodotto, totale))
                    {
                        costo -= sconto.Quantita;
                        costo -= sconto.QuantitaPct * prodotto.Prezzo;
                    }
                }
            }

            return costo;
        }
    }

    public class OrdineDomicilio : Ordine
    {
        private string _telefono;
        private string _indirizzo;
        private string _tokenPagamento;
        private string _email; // TBD
        private bool _inConsegna; // TBD

        public OrdineDomicilio(string nominativo, DateTime dataOra, string note, IList<Prodotto> prodotti, ISet<Sconto> sconti, string telefono, string indirizzo, string tokenPagamento, string email, bool inConsegna)
            : base(nominativo, dataOra, note, prodotti, sconti)
        {
            _telefono = telefono;
            _indirizzo = indirizzo;
            _tokenPagamento = tokenPagamento;
            _email = email;
            _inConsegna = inConsegna;
        }

        public string Telefono { get => _telefono; set => _telefono = value; }
        public string Indirizzo { get => _indirizzo; set => _indirizzo = value; }
        public string TokenPagamento { get => _tokenPagamento; set => _tokenPagamento = value; }
        public string Email { get => _email; set => _email = value; }
        public bool InConsegna { get => _inConsegna; set => _inConsegna = value; }
    }

    public class OrdineTakeAway : Ordine
    {
        private string _telefono;

        public OrdineTakeAway(string nominativo, DateTime dataOra, string note, IList<Prodotto> prodotti, ISet<Sconto> sconti, string telefono)
            : base(nominativo, dataOra, note, prodotti, sconti)
        {
            _telefono = telefono;
        }

        public string Telefono { get => _telefono; set => _telefono = value; }
    }

    public class OrdineAlTavolo : Ordine
    {
        private string _tavolo;

        public OrdineAlTavolo(string nominativo, DateTime dataOra, string note, IList<Prodotto> prodotti, ISet<Sconto> sconti, string tavolo)
            : base(nominativo, dataOra, note, prodotti, sconti)
        {
            Tavolo = tavolo;
        }

        public string Tavolo { get => _tavolo; set => _tavolo = value; }
    }
}