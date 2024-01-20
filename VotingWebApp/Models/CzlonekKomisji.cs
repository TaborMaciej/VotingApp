using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VotingWebApp.Models
{
    public class CzlonekKomisji
    {
        [Key]
        public Guid? ID { get; set; }
        public string Imie { get; set; } = string.Empty;
        public string Nazwisko { get; set; } = string.Empty;
        public string Login { get; set; } = string.Empty;
        public string Haslo { get; set; } = string.Empty;

        [ForeignKey("Obwod")]
        public Guid IDObwod { get; set; }
        public virtual Obwod? Obwod { get; set; }

    }
}
