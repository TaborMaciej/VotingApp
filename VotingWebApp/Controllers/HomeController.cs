using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Security.Claims;
using VotingWebApp.Models;
using VotingWebApp.Context;

namespace VotingWebApp.Controllers
{
    public class HomeController : Controller
    {
            private readonly ILogger<HomeController> _logger;
            private readonly VotingContext _context;

            public HomeController(ILogger<HomeController> logger, VotingContext context)
            {
                _logger = logger;
                _context = context;
            }

            public IActionResult Index()
            {
                return View();
            }

            public IActionResult Privacy()
            {
                return View();
            }

            [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
            public IActionResult Error()
            {
                return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
            }

            [HttpPost]
            public async Task<IActionResult> Login(string login, string password)
            {
                var adm = _context.CzlonkowieKomisji.FirstOrDefault(p => p.Login == login);

                if (adm != null)
                {
                    var haslo = _context.CzlonkowieKomisji.FirstOrDefault(p => p.Haslo == password);
                    if (haslo != null)
                    {
                        var claims = new[]
                        {
                    new Claim(ClaimTypes.Role, "Admin")
                    };

                        var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
                        var principal = new ClaimsPrincipal(identity);

                        await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal);

                        return RedirectToAction("Index", "Home");
                    }
                }

                return View("Index");
            }
            [Authorize]
            [HttpPost]
            public async Task<IActionResult> Logout()
            {
                await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
                return RedirectToAction("Index", "Home");
            }
    }
}