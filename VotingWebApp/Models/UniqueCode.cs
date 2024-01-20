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
        [ForeignKey("KandydatSejmu")]
        public Guid? IDKandydataSejmu { get; set; }
        public virtual Kandydat? KandydatSejmu { get; set; }
        [ForeignKey("KandydatSenatu")]
        public Guid? IDKandydataSenatu { get; set; }
        public virtual Kandydat? KandydatSenatu { get; set; }
    }
}
