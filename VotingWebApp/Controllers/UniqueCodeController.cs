using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using System.Text.Json;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    [Route("api/[controller]")]
    public class UniqueCodeController : Controller
    {
        private readonly VotingContext _context;


        public UniqueCodeController(VotingContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {
            String randomString = "";
            do
            {
                randomString = GenerateRandomCode();
                bool x = CheckIfCodeExists(randomString);
                Console.WriteLine(x);
            } while (CheckIfCodeExists(randomString));

            UniqueCode newCode = new UniqueCode();
            newCode.Code = randomString;
            newCode.wasUsed = false;
            _context.Add(newCode);
            await _context.SaveChangesAsync();
            return View(newCode);

        }

        private String GenerateRandomCode()
        {
            String allowedSigns = "0123456789";
            Random random = new Random();

            String code = new String(Enumerable.Repeat(allowedSigns, 9)
                .Select(s => s[random.Next(s.Length)]).ToArray());

            return code;
        }

        private bool CheckIfCodeExists(String code)
        {
            return (_context.UniqueCodes?.Any(c => c.Code == code)).GetValueOrDefault();
        }

        [HttpPost("checkCode")]
        public async Task<IActionResult> CheckStringExists()
        {
            try
            {
                var code_ = Request.Form.Keys.ElementAt(0);

                if (string.IsNullOrEmpty(code_))
                    return BadRequest("Błędne dane");

                if (!CheckIfCodeExists(code_))
                    return Ok(new { exists = false });

                return Ok(new { exists = true });
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Internal Server Error");
            }
        }

        [HttpPost("usedCode")]
        public async Task<IActionResult> MarkCodeAsUsed(string code_)
        {
            if (string.IsNullOrEmpty(code_))
                return BadRequest("Błędne dane");

            if (!CheckIfCodeExists(code_))
                return Ok(new { exists = false, used = false });

            var codeObj = _context.UniqueCodes.FirstOrDefault(entity => entity.Code == code_);
            codeObj.wasUsed = true;
            var x = _context.Update(codeObj);
            await _context.SaveChangesAsync();
            return Ok(new { exists = true, used = true });
        }

        [HttpGet("getKandydaci")]
        public IActionResult GetKandydatJson()
        {
            var result = new
            {
                Komitet = _context.Komitety.Select(k => new
                    {
                        ID = k.ID,
                        Nazwa = k.Nazwa,
                        LogoNazwa = k.LogoNazwa,
                        NrListy = k.NrListy
                    }).ToList(),

                Kandydaci = _context.Kandydaci
                    .Include(p => p.Komitet)
                    .Include(p => p.Okreg)
                    .Select(k => new KandydatDto
                    {
                        ID = k.ID,
                        Imie = k.Imie,
                        Nazwisko = k.Nazwisko,
                        Zdjecie = k.Zdjecie,
                        Opis = k.Opis,
                        czySenat = k.czySenat,
                        NrListy = k.Komitet.NrListy,
                        IDKomitetu = k.Komitet.ID,
                        IDokreg = k.Okreg.ID,
                        NazwaOkregu = k.Okreg.Nazwa
                    })
                    .ToList()
            };

            var jsonOptions = new JsonSerializerOptions
            {
                ReferenceHandler = ReferenceHandler.Preserve,
                MaxDepth = 256 // Set the maximum depth as needed
            };

            var jsonData = Json(result, jsonOptions);

            return jsonData;
        }
    }
}
