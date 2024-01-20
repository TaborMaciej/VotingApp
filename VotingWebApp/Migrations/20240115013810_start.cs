using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace VotingWebApp.Migrations
{
    /// <inheritdoc />
    public partial class start : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Komitety",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Nazwa = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    LogoNazwa = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    NrListy = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Komitety", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Obwody",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    NazwaObwodu = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Miasto = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Obwody", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Okregi",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Nazwa = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    NrOkregu = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Okregi", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "OsobyGlosujace",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Imie = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Nazwisko = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Pesel = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Miasto = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Ulica = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    NrDomu = table.Column<int>(type: "int", nullable: true),
                    Zaglosowal = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OsobyGlosujace", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "CzlonkowieKomisji",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Imie = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Nazwisko = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Login = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Haslo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IDObwod = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CzlonkowieKomisji", x => x.ID);
                    table.ForeignKey(
                        name: "FK_CzlonkowieKomisji_Obwody_IDObwod",
                        column: x => x.IDObwod,
                        principalTable: "Obwody",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Kandydaci",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Imie = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Nazwisko = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Zdjecie = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Opis = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IDKomitetu = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    IDOkregu = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Kandydaci", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Kandydaci_Komitety_IDKomitetu",
                        column: x => x.IDKomitetu,
                        principalTable: "Komitety",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Kandydaci_Okregi_IDOkregu",
                        column: x => x.IDOkregu,
                        principalTable: "Okregi",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UniqueCodes",
                columns: table => new
                {
                    ID = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Code = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    wasUsed = table.Column<bool>(type: "bit", nullable: false),
                    IDKandydata = table.Column<Guid>(type: "uniqueidentifier", nullable: true),
                    IDKomitetu = table.Column<Guid>(type: "uniqueidentifier", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UniqueCodes", x => x.ID);
                    table.ForeignKey(
                        name: "FK_UniqueCodes_Kandydaci_IDKandydata",
                        column: x => x.IDKandydata,
                        principalTable: "Kandydaci",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK_UniqueCodes_Komitety_IDKomitetu",
                        column: x => x.IDKomitetu,
                        principalTable: "Komitety",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateIndex(
                name: "IX_CzlonkowieKomisji_IDObwod",
                table: "CzlonkowieKomisji",
                column: "IDObwod");

            migrationBuilder.CreateIndex(
                name: "IX_Kandydaci_IDKomitetu",
                table: "Kandydaci",
                column: "IDKomitetu");

            migrationBuilder.CreateIndex(
                name: "IX_Kandydaci_IDOkregu",
                table: "Kandydaci",
                column: "IDOkregu");

            migrationBuilder.CreateIndex(
                name: "IX_UniqueCodes_Code",
                table: "UniqueCodes",
                column: "Code",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_UniqueCodes_IDKandydata",
                table: "UniqueCodes",
                column: "IDKandydata");

            migrationBuilder.CreateIndex(
                name: "IX_UniqueCodes_IDKomitetu",
                table: "UniqueCodes",
                column: "IDKomitetu");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CzlonkowieKomisji");

            migrationBuilder.DropTable(
                name: "OsobyGlosujace");

            migrationBuilder.DropTable(
                name: "UniqueCodes");

            migrationBuilder.DropTable(
                name: "Obwody");

            migrationBuilder.DropTable(
                name: "Kandydaci");

            migrationBuilder.DropTable(
                name: "Komitety");

            migrationBuilder.DropTable(
                name: "Okregi");
        }
    }
}
