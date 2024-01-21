using System;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    public class WynikiGlosowaniaController : Controller
    {
        private readonly VotingContext _context;

        public WynikiGlosowaniaController(VotingContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {
            var votingContext = _context.UniqueCodes;
            return View();
        }


        public async Task<IActionResult> Wyniki(Kandydat kandydat)
        {
            var votingContext = _context.UniqueCodes;

            var sejmVotes = await votingContext
                .Where(g => g.IDKandydataSejmu != null)
                .GroupBy(g => g.IDKandydataSejmu)
                .Select(g => new { IDKomitetu = g.Key, VotesCount = g.Count() })
                .OrderByDescending(g => g.VotesCount)
                .FirstOrDefaultAsync();

            var senatVotes = await votingContext
                .Where(g => g.IDKandydataSenatu != null)
                .GroupBy(g => g.IDKandydataSenatu)
                .Select(g => new { IDKomitetu = g.Key, VotesCount = g.Count() })
                .OrderByDescending(g => g.VotesCount)
                .FirstOrDefaultAsync();


            var partiesSejm = await _context.Komitety
                .Where(k => k.Kandydat.Any(c => !c.czySenat))
                .ToListAsync();

            var partyVotesSejm = await votingContext
                .Where(g => g.IDKandydataSejmu != null)
                .GroupBy(g => g.KandydatSejmu.IDKomitetu)
                .Select(g => new { IDKomitetu = g.Key, VotesCount = g.Count() })
                .ToListAsync();

            var partiesSenat = await _context.Komitety
                .Where(k => k.Kandydat.Any(c => c.czySenat))
                .ToListAsync();

            var partyVotesSenat = await votingContext
              .Where(g => g.IDKandydataSenatu != null)
               .GroupBy(g => g.KandydatSenatu.IDKomitetu)
             .Select(g => new { IDKomitetu = g.Key, VotesCount = g.Count() })
            .ToListAsync();


            var partySenatResults = new List<PartyResultViewModel>();
            var partySejmResults = new List<PartyResultViewModel>();

            foreach (var sejmResult in partyVotesSejm)
            {
                var partyResult = new PartyResultViewModel
                {
                    Party = partiesSejm.FirstOrDefault(p => p.ID == sejmResult.IDKomitetu),
                    Count = sejmResult.VotesCount
                };
                partySejmResults.Add(partyResult);
            }

            foreach (var senatResult in partyVotesSenat)
            {
                var partyResult = new PartyResultViewModel
                {
                    Party = partiesSenat.FirstOrDefault(p => p.ID == senatResult.IDKomitetu),
                    Count = senatResult.VotesCount
                };
                partySenatResults.Add(partyResult);
            }

            var wynikiViewModel = new WynikiViewModel
            {

                partySejmResults = partySejmResults,
                partySenatResults = partySenatResults
            };

            return View(wynikiViewModel);
        }



    }

}

