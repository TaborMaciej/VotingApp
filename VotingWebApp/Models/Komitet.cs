using System.ComponentModel.DataAnnotations;

namespace VotingWebApp.Models
{
    public class Komitet
    {
        [Key]
        public Guid? ID { get; set; }
        public string Nazwa { get; set; } = string.Empty;
        public string LogoNazwa { get; set; } = string.Empty;
        public int? NrListy { get; set; }
        public virtual List<Kandydat>? Kandydat { get; set; }
        public virtual List<UniqueCode>? UniqueCode { get; set; }
    }
}
