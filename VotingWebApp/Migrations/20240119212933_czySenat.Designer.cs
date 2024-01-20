﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using VotingWebApp.Context;

#nullable disable

namespace VotingWebApp.Migrations
{
    [DbContext(typeof(VotingContext))]
    [Migration("20240119212933_czySenat")]
    partial class czySenat
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.13")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder);

            modelBuilder.Entity("VotingWebApp.Models.CzlonekKomisji", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Haslo")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<Guid>("IDObwod")
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Imie")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Login")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Nazwisko")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("ID");

                    b.HasIndex("IDObwod");

                    b.ToTable("CzlonkowieKomisji");
                });

            modelBuilder.Entity("VotingWebApp.Models.Glosujacy", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Imie")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Miasto")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Nazwisko")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<int?>("NrDomu")
                        .HasColumnType("int");

                    b.Property<string>("Pesel")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Ulica")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<bool>("Zaglosowal")
                        .HasColumnType("bit");

                    b.HasKey("ID");

                    b.ToTable("OsobyGlosujace");
                });

            modelBuilder.Entity("VotingWebApp.Models.Kandydat", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("IDKomitetu")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("IDOkregu")
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Imie")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Nazwisko")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Opis")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Zdjecie")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<bool>("czySenat")
                        .HasColumnType("bit");

                    b.HasKey("ID");

                    b.HasIndex("IDKomitetu");

                    b.HasIndex("IDOkregu");

                    b.ToTable("Kandydaci");
                });

            modelBuilder.Entity("VotingWebApp.Models.Komitet", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("LogoNazwa")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Nazwa")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<int?>("NrListy")
                        .HasColumnType("int");

                    b.HasKey("ID");

                    b.ToTable("Komitety");
                });

            modelBuilder.Entity("VotingWebApp.Models.Obwod", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Miasto")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("NazwaObwodu")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("ID");

                    b.ToTable("Obwody");
                });

            modelBuilder.Entity("VotingWebApp.Models.Okreg", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Nazwa")
                        .IsRequired()
                        .HasColumnType("nvarchar(max)");

                    b.Property<int?>("NrOkregu")
                        .HasColumnType("int");

                    b.HasKey("ID");

                    b.ToTable("Okregi");
                });

            modelBuilder.Entity("VotingWebApp.Models.UniqueCode", b =>
                {
                    b.Property<Guid?>("ID")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("Code")
                        .IsRequired()
                        .HasColumnType("nvarchar(450)");

                    b.Property<Guid?>("IDKandydata")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid?>("IDKomitetu")
                        .HasColumnType("uniqueidentifier");

                    b.Property<bool>("wasUsed")
                        .HasColumnType("bit");

                    b.HasKey("ID");

                    b.HasIndex("Code")
                        .IsUnique();

                    b.HasIndex("IDKandydata");

                    b.HasIndex("IDKomitetu");

                    b.ToTable("UniqueCodes");
                });

            modelBuilder.Entity("VotingWebApp.Models.CzlonekKomisji", b =>
                {
                    b.HasOne("VotingWebApp.Models.Obwod", "Obwod")
                        .WithMany("CzlonekKomisji")
                        .HasForeignKey("IDObwod")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Obwod");
                });

            modelBuilder.Entity("VotingWebApp.Models.Kandydat", b =>
                {
                    b.HasOne("VotingWebApp.Models.Komitet", "Komitet")
                        .WithMany("Kandydat")
                        .HasForeignKey("IDKomitetu")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("VotingWebApp.Models.Okreg", "Okreg")
                        .WithMany("Kandydat")
                        .HasForeignKey("IDOkregu")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Komitet");

                    b.Navigation("Okreg");
                });

            modelBuilder.Entity("VotingWebApp.Models.UniqueCode", b =>
                {
                    b.HasOne("VotingWebApp.Models.Kandydat", "Kandydat")
                        .WithMany("UniqueCode")
                        .HasForeignKey("IDKandydata");

                    b.HasOne("VotingWebApp.Models.Komitet", "Komitet")
                        .WithMany("UniqueCode")
                        .HasForeignKey("IDKomitetu");

                    b.Navigation("Kandydat");

                    b.Navigation("Komitet");
                });

            modelBuilder.Entity("VotingWebApp.Models.Kandydat", b =>
                {
                    b.Navigation("UniqueCode");
                });

            modelBuilder.Entity("VotingWebApp.Models.Komitet", b =>
                {
                    b.Navigation("Kandydat");

                    b.Navigation("UniqueCode");
                });

            modelBuilder.Entity("VotingWebApp.Models.Obwod", b =>
                {
                    b.Navigation("CzlonekKomisji");
                });

            modelBuilder.Entity("VotingWebApp.Models.Okreg", b =>
                {
                    b.Navigation("Kandydat");
                });
#pragma warning restore 612, 618
        }
    }
}
