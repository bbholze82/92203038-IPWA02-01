function highlightRowAndScroll(id) {
    // Entfernen aller früheren Hervorhebungen
    document.querySelectorAll('tr.highlight').forEach(function(row) {
        row.classList.remove('highlight');
    });

    // Neue Zeile hervorheben
    var targetRow = document.getElementById(id);
    if (targetRow) {
        targetRow.classList.add('highlight');
        // Scrollen zum hervorgehobenen Element
        targetRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}