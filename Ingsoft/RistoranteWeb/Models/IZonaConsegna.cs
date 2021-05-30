using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RistoranteWeb.Models
{
    interface IZonaConsegna
    {
        public Boolean include(Pair<Double, Double> coordinata, double prezzo);
        public Boolean include(string indirizzo, double prezzo);
    }
}
