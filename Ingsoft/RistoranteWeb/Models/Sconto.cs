using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models
{
    public class Sconto
    {
        private DateTime _inizio;
        private DateTime _fine;
        private double _quantita;
        private double _quantitaPct;
        private double _costoMinimo;

        private ISet<Prodotto> _perProdotti;

        //inserire costruttori e roba lol

        public DateTime Inizio { get => _inizio; set => _inizio = value; }
        public DateTime Fine { get => _fine; set => _fine = value; }
        public double Quantita { get => _quantita; set => _quantita = value; }
        public double QuantitaPct { get => _quantitaPct; set => _quantitaPct = value; }
        public double CostoMinimo { get => _costoMinimo; set => _costoMinimo = value; }
        public ISet<Prodotto> PerProdotti { get => _perProdotti; set => _perProdotti = value; }

        public bool IsAttivo(DateTime tempo, Prodotto prodotto, double costoTotale)
        {
            return isDateCostGood(tempo, costoTotale) && PerProdotti.Contains(prodotto);
        }

        public bool IsAttivo(DateTime tempo, double costoTotale)
        {
            return isDateCostGood(tempo, costoTotale) && PerProdotti.Count == 0;
        }

        private bool isDateCostGood(DateTime tempo, double costoTotale)
        {
            return tempo >= Inizio && tempo < Fine && costoTotale >= CostoMinimo;
        }
    }
}