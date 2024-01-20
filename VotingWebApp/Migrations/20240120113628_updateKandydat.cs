using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace VotingWebApp.Migrations
{
    /// <inheritdoc />
    public partial class updateKandydat : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "nrListy",
                table: "Kandydaci",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "nrListy",
                table: "Kandydaci");
        }
    }
}
