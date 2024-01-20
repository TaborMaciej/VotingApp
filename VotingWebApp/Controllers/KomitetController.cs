using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    [Authorize]
    public class KomitetController : Controller
    {
        private readonly VotingContext _context;

        public KomitetController(VotingContext context)
        {
            _context = context;
        }

        // GET: Komitet
        public async Task<IActionResult> Index()
        {
            return _context.Komitety != null ?
                        View(await _context.Komitety.ToListAsync()) :
                        Problem("Entity set 'VotingContext.Komitety'  is null.");
        }

        // GET: Komitet/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Komitety == null)
            {
                return NotFound();
            }

            var komitet = await _context.Komitety
                .FirstOrDefaultAsync(m => m.ID == id);
            if (komitet == null)
            {
                return NotFound();
            }

            return View(komitet);
        }

        // GET: Komitet/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Komitet/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,Nazwa,NrListy")] Komitet komitet)
        {
            if (ModelState.IsValid)
            {
                _context.Add(komitet);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(komitet);
        }

        // GET: Komitet/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Komitety == null)
            {
                return NotFound();
            }

            var komitet = await _context.Komitety.FindAsync(id);
            if (komitet == null)
            {
                return NotFound();
            }
            return View(komitet);
        }

        // POST: Komitet/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,Nazwa,LogoNazwa,NrListy")] Komitet komitet)
        {
            if (id != komitet.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(komitet);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!KomitetExists(komitet.ID))
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
            return View(komitet);
        }

        // GET: Komitet/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Komitety == null)
            {
                return NotFound();
            }

            var komitet = await _context.Komitety
                .FirstOrDefaultAsync(m => m.ID == id);
            if (komitet == null)
            {
                return NotFound();
            }

            return View(komitet);
        }

        // POST: Komitet/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.Komitety == null)
            {
                return Problem("Entity set 'VotingContext.Komitety'  is null.");
            }
            var komitet = await _context.Komitety.FindAsync(id);
            if (komitet != null)
            {
                _context.Komitety.Remove(komitet);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool KomitetExists(Guid? id)
        {
            return (_context.Komitety?.Any(e => e.ID == id)).GetValueOrDefault();
        }
    }
}
