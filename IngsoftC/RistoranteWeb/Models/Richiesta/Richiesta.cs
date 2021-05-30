using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models.Richiesta
{
    public abstract class Richiesta
    {
        private string _nominativo;
        private string _IDRichiesta;
        private DateTime _dataOra;

        public string Nominativo { get => _nominativo; set => _nominativo = value; }
        public string IDRichiesta { get => _IDRichiesta; set => _IDRichiesta = value; }
        public DateTime DataOra { get => _dataOra; set => _dataOra = value; }

        protected Richiesta(string nominativo, DateTime dataOra)
        {
            _nominativo = nominativo;
            _dataOra = dataOra;
        }
    }
}