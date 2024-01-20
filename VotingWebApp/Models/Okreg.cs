using System.ComponentModel.DataAnnotations;

namespace VotingWebApp.Models
{
    public class Okreg
    {
        [Key]
        public Guid? ID { get; set; }
        public string Nazwa { get; set; } = string.Empty;
        public int? NrOkregu { get; set; }
        public virtual List<Kandydat>? Kandydat { get; set; }
    }
}
