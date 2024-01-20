using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    public class CzlonekKomisjiController : Controller
    {
        private readonly VotingContext _context;

        public CzlonekKomisjiController(VotingContext context)
        {
            _context = context;
        }

        // GET: CzlonekKomisji
        public async Task<IActionResult> Index()
        {
            var votingContext = _context.CzlonkowieKomisji.Include(c => c.Obwod);
            return View(await votingContext.ToListAsync());
        }

        // GET: CzlonekKomisji/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.CzlonkowieKomisji == null)
            {
                return NotFound();
            }

            var czlonekKomisji = await _context.CzlonkowieKomisji
                .Include(c => c.Obwod)
                .FirstOrDefaultAsync(m => m.ID == id);
            if (czlonekKomisji == null)
            {
                return NotFound();
            }

            return View(czlonekKomisji);
        }

        // GET: CzlonekKomisji/Create
        public IActionResult Create()
        {
            ViewData["IDObwod"] = new SelectList(_context.Obwody, "ID", "ID");
            return View();
        }

        // POST: CzlonekKomisji/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,Imie,Nazwisko,Login,Haslo,IDObwod")] CzlonekKomisji czlonekKomisji)
        {
            if (ModelState.IsValid)
            {
                _context.Add(czlonekKomisji);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["IDObwod"] = new SelectList(_context.Obwody, "ID", "ID", czlonekKomisji.IDObwod);
            return View(czlonekKomisji);
        }

        // GET: CzlonekKomisji/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.CzlonkowieKomisji == null)
            {
                return NotFound();
            }

            var czlonekKomisji = await _context.CzlonkowieKomisji.FindAsync(id);
            if (czlonekKomisji == null)
            {
                return NotFound();
            }
            ViewData["IDObwod"] = new SelectList(_context.Obwody, "ID", "ID", czlonekKomisji.IDObwod);
            return View(czlonekKomisji);
        }

        // POST: CzlonekKomisji/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,Imie,Nazwisko,Login,Haslo,IDObwod")] CzlonekKomisji czlonekKomisji)
        {
            if (id != czlonekKomisji.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(czlonekKomisji);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CzlonekKomisjiExists(czlonekKomisji.ID))
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
            ViewData["IDObwod"] = new SelectList(_context.Obwody, "ID", "ID", czlonekKomisji.IDObwod);
            return View(czlonekKomisji);
        }

        // GET: CzlonekKomisji/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.CzlonkowieKomisji == null)
            {
                return NotFound();
            }

            var czlonekKomisji = await _context.CzlonkowieKomisji
                .Include(c => c.Obwod)
                .FirstOrDefaultAsync(m => m.ID == id);
            if (czlonekKomisji == null)
            {
                return NotFound();
            }

            return View(czlonekKomisji);
        }

        // POST: CzlonekKomisji/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.CzlonkowieKomisji == null)
            {
                return Problem("Entity set 'VotingContext.CzlonkowieKomisji'  is null.");
            }
            var czlonekKomisji = await _context.CzlonkowieKomisji.FindAsync(id);
            if (czlonekKomisji != null)
            {
                _context.CzlonkowieKomisji.Remove(czlonekKomisji);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CzlonekKomisjiExists(Guid? id)
        {
          return (_context.CzlonkowieKomisji?.Any(e => e.ID == id)).GetValueOrDefault();
        }
    }
}
