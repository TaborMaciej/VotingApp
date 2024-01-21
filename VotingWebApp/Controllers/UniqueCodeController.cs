using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using System.Text.Json;
using VotingWebApp.Context;
using VotingWebApp.Models;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using NuGet.Protocol.Plugins;
using System.Security.Claims;
using VotingWebApp.VotingManager;

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
            if(!VotingVariable.IsVotingEnabled)
                return RedirectToAction("Index", "Home");
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
            if (!VotingVariable.IsVotingEnabled)
                return BadRequest("Glosowanie zakonczone");
            try
            {
                var code_ = Request.Form.Keys.ElementAt(0);

                if (string.IsNullOrEmpty(code_))
                    return BadRequest("Błędne dane");

                if (!CheckIfCodeExists(code_))
                    return BadRequest("Kod nie istnieje");

                var codeObj = _context.UniqueCodes.FirstOrDefault(entity => entity.Code == code_);
                if (codeObj != null)
                    return Ok(new { exists = true, used = codeObj.wasUsed });
                return Ok(new { exists = false, used = false });
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Internal Server Error");
            }
        }

        [HttpPost("postVote")]
        public async Task<IActionResult> PostVote([FromBody] JsonElement data)
        {
            var code_ = data.GetProperty("code").GetString();
            var idSejm = data.GetProperty("IDsejm").GetString();
            var idSenat = data.GetProperty("IDsenat").GetString();

            if (string.IsNullOrEmpty(code_))
                return BadRequest("Błędne dane");

            if (!CheckIfCodeExists(code_))
                return BadRequest("Kod nie istnieje");

            var codeObj = _context.UniqueCodes.FirstOrDefault(entity => entity.Code == code_);
            codeObj.IDKandydataSejmu = new Guid(idSejm);
            codeObj.IDKandydataSenatu = new Guid(idSenat);
            codeObj.wasUsed = true;
            var x = _context.Update(codeObj);
            await _context.SaveChangesAsync();
            return Ok();
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

        [HttpPost("loginUser")]
        public IActionResult tryLoginUser([FromBody] JsonElement data)
        {
            try
            {
                var login = data.GetProperty("login").GetString();
                var password = data.GetProperty("password").GetString();

                var adm = _context.CzlonkowieKomisji.FirstOrDefault(p => p.Login == login);

                if (adm != null)
                {
                    var haslo = _context.CzlonkowieKomisji.FirstOrDefault(p => p.Haslo == password);
                    if (haslo != null)

                        return Ok(new { correct = true });
                }
                return Ok(new { correct = false });
            }
            catch (Exception ex)
            {
                return StatusCode(500, "Internal Server Error");
            }
        }

        [HttpGet("stopVote")]
        public IActionResult StopTheVote()
        {
            VotingVariable.IsVotingEnabled = false;
            return Ok();
        }
    }
}