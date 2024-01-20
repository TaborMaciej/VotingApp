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
    public class OkregController : Controller
    {
        private readonly VotingContext _context;

        public OkregController(VotingContext context)
        {
            _context = context;
        }

        // GET: Okreg
        public async Task<IActionResult> Index()
        {
              return _context.Okregi != null ? 
                          View(await _context.Okregi.ToListAsync()) :
                          Problem("Entity set 'VotingContext.Okregi'  is null.");
        }

        // GET: Okreg/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Okregi == null)
            {
                return NotFound();
            }

            var okreg = await _context.Okregi
                .FirstOrDefaultAsync(m => m.ID == id);
            if (okreg == null)
            {
                return NotFound();
            }

            return View(okreg);
        }

        // GET: Okreg/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Okreg/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,Nazwa,NrOkregu")] Okreg okreg)
        {
            if (ModelState.IsValid)
            {
                _context.Add(okreg);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(okreg);
        }

        // GET: Okreg/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Okregi == null)
            {
                return NotFound();
            }

            var okreg = await _context.Okregi.FindAsync(id);
            if (okreg == null)
            {
                return NotFound();
            }
            return View(okreg);
        }

        // POST: Okreg/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,Nazwa,NrOkregu")] Okreg okreg)
        {
            if (id != okreg.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(okreg);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!OkregExists(okreg.ID))
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
            return View(okreg);
        }

        // GET: Okreg/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Okregi == null)
            {
                return NotFound();
            }

            var okreg = await _context.Okregi
                .FirstOrDefaultAsync(m => m.ID == id);
            if (okreg == null)
            {
                return NotFound();
            }

            return View(okreg);
        }

        // POST: Okreg/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.Okregi == null)
            {
                return Problem("Entity set 'VotingContext.Okregi'  is null.");
            }
            var okreg = await _context.Okregi.FindAsync(id);
            if (okreg != null)
            {
                _context.Okregi.Remove(okreg);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool OkregExists(Guid? id)
        {
          return (_context.Okregi?.Any(e => e.ID == id)).GetValueOrDefault();
        }
    }
}
