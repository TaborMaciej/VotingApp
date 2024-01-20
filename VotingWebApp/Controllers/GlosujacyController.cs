using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using VotingWebApp.Context;
using VotingWebApp.Models;

namespace VotingWebApp.Controllers
{
    public class GlosujacyController : Controller
    {
        private readonly VotingContext _context;

        public GlosujacyController(VotingContext context)
        {
            _context = context;
        }

        // GET: Glosujacy
        public async Task<IActionResult> Index()
        {
            return _context.OsobyGlosujace != null ?
                        View(await _context.OsobyGlosujace.ToListAsync()) :
                        Problem("Entity set 'VotingContext.OsobyGlosujace'  is null.");
        }

        // GET: Glosujacy/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.OsobyGlosujace == null)
            {
                return NotFound();
            }

            var glosujacy = await _context.OsobyGlosujace
                .FirstOrDefaultAsync(m => m.ID == id);
            if (glosujacy == null)
            {
                return NotFound();
            }

            return View(glosujacy);
        }

        // GET: Glosujacy/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Glosujacy/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,Imie,Nazwisko,Pesel,Miasto,Ulica,NrDomu,Zaglosowal")] Glosujacy glosujacy)
        {
            if (ModelState.IsValid)
            {
                _context.Add(glosujacy);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(glosujacy);
        }

        // GET: Glosujacy/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.OsobyGlosujace == null)
            {
                return NotFound();
            }

            var glosujacy = await _context.OsobyGlosujace.FindAsync(id);
            if (glosujacy == null)
            {
                return NotFound();
            }
            return View(glosujacy);
        }

        // POST: Glosujacy/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,Imie,Nazwisko,Pesel,Miasto,Ulica,NrDomu,Zaglosowal")] Glosujacy glosujacy)
        {
            if (id != glosujacy.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(glosujacy);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!GlosujacyExists(glosujacy.ID))
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
            return View(glosujacy);
        }

        // GET: Glosujacy/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.OsobyGlosujace == null)
            {
                return NotFound();
            }

            var glosujacy = await _context.OsobyGlosujace
                .FirstOrDefaultAsync(m => m.ID == id);
            if (glosujacy == null)
            {
                return NotFound();
            }

            return View(glosujacy);
        }

        // POST: Glosujacy/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.OsobyGlosujace == null)
            {
                return Problem("Entity set 'VotingContext.OsobyGlosujace'  is null.");
            }
            var glosujacy = await _context.OsobyGlosujace.FindAsync(id);
            if (glosujacy != null)
            {
                _context.OsobyGlosujace.Remove(glosujacy);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool GlosujacyExists(Guid? id)
        {
            return (_context.OsobyGlosujace?.Any(e => e.ID == id)).GetValueOrDefault();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Zaglosowal(Guid? id, [Bind("ID,Imie,Nazwisko,Pesel,Miasto,Ulica,NrDomu,Zaglosowal")] Glosujacy glosujacy_)
        {
            if (id == null || _context.OsobyGlosujace == null)
            {
                return NotFound();
            }

            var glosujacy = await _context.OsobyGlosujace.FindAsync(id);
            if (glosujacy == null)
            {
                return NotFound();
            }

            glosujacy.Zaglosowal = true;

            _context.Update(glosujacy);
            await _context.SaveChangesAsync();

            return RedirectToAction(nameof(Index));
        }

    }

}
