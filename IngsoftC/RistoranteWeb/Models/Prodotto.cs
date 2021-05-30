using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models
{
    public class Prodotto
    {
        private double _prezzo;

        public double Prezzo { get => _prezzo; set => _prezzo = value; }
    }
}