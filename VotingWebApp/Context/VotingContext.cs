using Microsoft.EntityFrameworkCore;
using VotingWebApp.Models;

namespace VotingWebApp.Context
{
    public class VotingContext : DbContext
    {
        public VotingContext(DbContextOptions<VotingContext> options) : base(options) { }
        public DbSet<Obwod> Obwody { get; set; }
        public DbSet<CzlonekKomisji> CzlonkowieKomisji { get; set; }
        public DbSet<Glosujacy> OsobyGlosujace { get; set; }
        public DbSet<Kandydat> Kandydaci { get; set; }
        public DbSet<Komitet> Komitety { get; set; }
        public DbSet<Okreg> Okregi { get; set; }
        public DbSet<UniqueCode> UniqueCodes { get; set; }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<UniqueCode>().HasIndex(c => c.Code).IsUnique();
        }

    }
}
