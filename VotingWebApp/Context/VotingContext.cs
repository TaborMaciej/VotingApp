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

            modelBuilder.Entity<Kandydat>()
                .HasMany(k => k.UniqueCodeSejm)
                .WithOne(u => u.KandydatSejmu)
                .HasForeignKey(u => u.IDKandydataSejmu)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<Kandydat>()
                .HasMany(k => k.UniqueCodeSenat)
                .WithOne(u => u.KandydatSenatu)
                .HasForeignKey(u => u.IDKandydataSenatu)
                .OnDelete(DeleteBehavior.Restrict); 
        }

    }
}
