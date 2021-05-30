using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RistoranteWeb.Models
{
    public class ZonaConsegnaPunti : IZonaConsegna
    {
        public Boolean include(Pair<Double, Double> coordinata, double prezzo)
        {
            // TODO
            return true;
        }

        public Boolean include(string indirizzo, double prezzo)
        {
            // TODO
            return true;
        }
    }
}