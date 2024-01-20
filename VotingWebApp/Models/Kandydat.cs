using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VotingWebApp.Models
{
    public class Kandydat
    {
        [Key]
        public Guid? ID { get; set; }
        public string Imie { get; set; } = string.Empty;
        public string Nazwisko { get; set; } = string.Empty;
        public string Zdjecie { get; set; } = string.Empty;
        public string Opis { get; set; } = string.Empty;
        public Boolean czySenat { get; set; } = false;

        [ForeignKey("Komitet")]
        public Guid IDKomitetu { get; set; }
        public virtual Komitet? Komitet { get; set; }

        [ForeignKey("Okreg")]
        public Guid IDOkregu { get; set; }
        public virtual Okreg? Okreg { get; set; }
        public virtual List<UniqueCode>? UniqueCode { get; set; }
    }
}
