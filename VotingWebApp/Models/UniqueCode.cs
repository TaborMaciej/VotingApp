using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VotingWebApp.Models
{
    public class UniqueCode
    {
        [Key]
        public Guid? ID { get; set; }
        public string Code { get; set; }
        public Boolean wasUsed { get; set; } = false;
        [ForeignKey("Kandydat")]
        public Guid? IDKandydata { get; set; }
        public virtual Kandydat? Kandydat { get; set; }
        [ForeignKey("Komitet")]
        public Guid? IDKomitetu { get; set; }
        public virtual Komitet? Komitet { get; set; }
    }
}
