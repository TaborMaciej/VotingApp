using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace VotingWebApp.Migrations
{
    /// <inheritdoc />
    public partial class czySenat : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "czySenat",
                table: "Kandydaci",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "czySenat",
                table: "Kandydaci");
        }
    }
}
