using System.ComponentModel.DataAnnotations;

namespace VotingWebApp.Models
{
    public class Obwod
    {
        [Key]
        public Guid? ID { get; set; }
        public string NazwaObwodu { get; set; } = string.Empty;
        public string Miasto { get; set; } = string.Empty;

        public virtual List<CzlonekKomisji>? CzlonekKomisji { get; set; }
    }
}
