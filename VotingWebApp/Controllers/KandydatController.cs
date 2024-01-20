using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    [Authorize]
    public class KandydatController : Controller
    {
        private readonly VotingContext _context;

        public KandydatController(VotingContext context)
        {
            _context = context;
        }

        // GET: Kandydats
        public async Task<IActionResult> Index()
        {
            var votingContext = _context.Kandydaci.Include(k => k.Komitet).Include(k => k.Okreg);
            return View(await votingContext.ToListAsync());
        }

        // GET: Kandydats/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Kandydaci == null)
            {
                return NotFound();
            }

            var kandydat = await _context.Kandydaci
                .Include(k => k.Komitet)
                .Include(k => k.Okreg)
                .FirstOrDefaultAsync(m => m.ID == id);
            if (kandydat == null)
            {
                return NotFound();
            }

            return View(kandydat);
        }

        // GET: Kandydats/Create
        public IActionResult Create()
        {
            ViewData["IDKomitetu"] = new SelectList(_context.Komitety, "ID", "ID");
            ViewData["IDOkregu"] = new SelectList(_context.Okregi, "ID", "ID");
            return View();
        }

        // POST: Kandydats/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,Imie,Nazwisko,Zdjecie,Opis,czySenat,IDKomitetu,IDOkregu")] Kandydat kandydat)
        {
            if (ModelState.IsValid)
            {
                _context.Add(kandydat);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["IDKomitetu"] = new SelectList(_context.Komitety, "ID", "ID", kandydat.IDKomitetu);
            ViewData["IDOkregu"] = new SelectList(_context.Okregi, "ID", "ID", kandydat.IDOkregu);
            return View(kandydat);
        }

        // GET: Kandydats/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Kandydaci == null)
            {
                return NotFound();
            }

            var kandydat = await _context.Kandydaci.FindAsync(id);
            if (kandydat == null)
            {
                return NotFound();
            }
            ViewData["IDKomitetu"] = new SelectList(_context.Komitety, "ID", "ID", kandydat.IDKomitetu);
            ViewData["IDOkregu"] = new SelectList(_context.Okregi, "ID", "ID", kandydat.IDOkregu);
            return View(kandydat);
        }

        // POST: Kandydats/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,Imie,Nazwisko,Zdjecie,Opis,czySenat,IDKomitetu,IDOkregu")] Kandydat kandydat)
        {
            if (id != kandydat.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(kandydat);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!KandydatExists(kandydat.ID))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["IDKomitetu"] = new SelectList(_context.Komitety, "ID", "ID", kandydat.IDKomitetu);
            ViewData["IDOkregu"] = new SelectList(_context.Okregi, "ID", "ID", kandydat.IDOkregu);
            return View(kandydat);
        }

        // GET: Kandydats/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Kandydaci == null)
            {
                return NotFound();
            }

            var kandydat = await _context.Kandydaci
                .Include(k => k.Komitet)
                .Include(k => k.Okreg)
                .FirstOrDefaultAsync(m => m.ID == id);
            if (kandydat == null)
            {
                return NotFound();
            }

            return View(kandydat);
        }

        // POST: Kandydats/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.Kandydaci == null)
            {
                return Problem("Entity set 'VotingContext.Kandydaci'  is null.");
            }
            var kandydat = await _context.Kandydaci.FindAsync(id);
            if (kandydat != null)
            {
                _context.Kandydaci.Remove(kandydat);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool KandydatExists(Guid? id)
        {
          return (_context.Kandydaci?.Any(e => e.ID == id)).GetValueOrDefault();
        }

    }
}
