// Initialisiere die Karte und setze den Mittelpunkt und den Zoomlevel
var center = [55.454, 4.504];
var map = L.map('map').setView(center, 5);

// Füge ein Tile-Layer hinzu (z.B. OpenStreetMap)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

var marker;
var firstClick = false; // Variable, um den ersten Klick zu überprüfen

// Funktion, um einen neuen Marker zu setzen
function onMapClick(e) {
    if (marker) {
        map.removeLayer(marker);
    }
    marker = L.marker(e.latlng).addTo(map);
    
    // Übertrage die Koordinaten in die Eingabefelder bei jedem Klick
    var latitudeInput = document.querySelector("[id$='latitude']");
    var longitudeInput = document.querySelector("[id$='longitude']");
    
    if (latitudeInput && longitudeInput) {
        latitudeInput.value = e.latlng.lat.toFixed(5);
        longitudeInput.value = e.latlng.lng.toFixed(5);
        latitudeInput.dispatchEvent(new Event('change'));
        longitudeInput.dispatchEvent(new Event('change'));
    }
    
    // Nur beim ersten Klick die firstClick-Variable setzen
    if (!firstClick) {
        firstClick = true; // Setze die Variable auf true nach dem ersten Klick
    }
}

// Eventlistener für Klicks auf die Karte
map.on('click', onMapClick);
