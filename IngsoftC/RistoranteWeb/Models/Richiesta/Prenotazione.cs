using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models.Richiesta
{
    public class Prenotazione : Richiesta
    {
        private string _telefono;
        private int _numeroPersone;

        public Prenotazione(string nominativo, DateTime dataOra, string telefono, int numeroPersone) : base(nominativo, dataOra)
        {
            Telefono = telefono;
            NumeroPersone = numeroPersone;
        }

        public string Telefono { get => _telefono; set => _telefono = value; }
        public int NumeroPersone { get => _numeroPersone; set => _numeroPersone = value; }
    }
}