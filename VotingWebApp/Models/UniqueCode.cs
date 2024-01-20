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
        public Guid? IDKandydataSejmu { get; set; }
        public virtual Kandydat? KandydatSejm { get; set; }
        [ForeignKey("Kandydat")]
        public Guid? IDKandydataSenatu { get; set; }
        public virtual Komitet? KomitetSenat { get; set; }
    }
}
