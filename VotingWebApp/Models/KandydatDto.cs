namespace VotingWebApp.Models
{
    public class KandydatDto
    {
        public Guid? ID { get; set; }
        public string Imie { get; set; }
        public string Nazwisko { get; set; }
        public string Zdjecie { get; set; }
        public string Opis { get; set; }
        public int? NrListy { get; set; }
        public string NazwaOkregu { get; set; }
        public Boolean czySenat { get; set; }
    }

}
