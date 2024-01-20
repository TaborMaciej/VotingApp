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
    public class ObwodController : Controller
    {
        private readonly VotingContext _context;

        public ObwodController(VotingContext context)
        {
            _context = context;
        }

        // GET: Obwods
        public async Task<IActionResult> Index()
        {
              return _context.Obwody != null ? 
                          View(await _context.Obwody.ToListAsync()) :
                          Problem("Entity set 'VotingContext.Obwody'  is null.");
        }

        // GET: Obwods/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.Obwody == null)
            {
                return NotFound();
            }

            var obwod = await _context.Obwody
                .FirstOrDefaultAsync(m => m.ID == id);
            if (obwod == null)
            {
                return NotFound();
            }

            return View(obwod);
        }

        // GET: Obwods/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Obwods/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ID,NazwaObwodu,Miasto")] Obwod obwod)
        {
            if (ModelState.IsValid)
            {
                _context.Add(obwod);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(obwod);
        }

        // GET: Obwods/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.Obwody == null)
            {
                return NotFound();
            }

            var obwod = await _context.Obwody.FindAsync(id);
            if (obwod == null)
            {
                return NotFound();
            }
            return View(obwod);
        }

        // POST: Obwods/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid? id, [Bind("ID,NazwaObwodu,Miasto")] Obwod obwod)
        {
            if (id != obwod.ID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(obwod);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ObwodExists(obwod.ID))
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
            return View(obwod);
        }

        // GET: Obwods/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.Obwody == null)
            {
                return NotFound();
            }

            var obwod = await _context.Obwody
                .FirstOrDefaultAsync(m => m.ID == id);
            if (obwod == null)
            {
                return NotFound();
            }

            return View(obwod);
        }

        // POST: Obwods/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid? id)
        {
            if (_context.Obwody == null)
            {
                return Problem("Entity set 'VotingContext.Obwody'  is null.");
            }
            var obwod = await _context.Obwody.FindAsync(id);
            if (obwod != null)
            {
                _context.Obwody.Remove(obwod);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ObwodExists(Guid? id)
        {
          return (_context.Obwody?.Any(e => e.ID == id)).GetValueOrDefault();
        }
    }
}
