using System.ComponentModel.DataAnnotations;

namespace VotingWebApp.Models
{
    public class Glosujacy
    {
        [Key]
        public Guid? ID { get; set; }
        public string Imie { get; set; } = string.Empty;
        public string Nazwisko { get; set; } = string.Empty;
        public string Pesel { get; set; } = string.Empty;
        public string Miasto { get; set; } = string.Empty;
        public string Ulica { get; set; } = string.Empty;
        public int? NrDomu { get; set; }
        public Boolean Zaglosowal { get; set; } = false;

    }
}
