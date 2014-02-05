Game-Timer
==========
*Standalone Timer application for Warhammer tournaments*

Syfte
-------
En timmer som hj�lper till med h�lltider i Warhammer med en timmer och text som talar om vilken tid det �r och vilken tur och spelare det borde vara.

Indata:
-------
* Speltid: den tiden som hela matchen ska ta som mest
* Presentations tid: Den tid som b�da spelarna ska ha p� sig att presentera sin arm�er f�r varandra om man spelar med �ppna listor.
* Deployment tid: den tid man ska f� p� sig att s�tta ut sin arm� p� spelbordet.

Utdata:
-------
* Total tid kvar
* Tid kvar per runda
* Vilken spelare det borde vara

Faser:
-------
* Presentation
* Deployment
* Turn 1-6

Mattematik
-------
* Total tid = speltid
* tid per runda = (total tid - (presentations tid + deployment tid)) / 6
* tid per spelare = tid per runda / 2

Knappar
--------
* Start: Startar matchen
* Stop: Stopar matchen
* Reset: Nollst�ller timers
* Clear: Rensar inst�llningarna

UI
--------
* En ruta med tv� flikar. En f�r inst�llningar och en f�r klockan*
* Inst�llningar
** Presentations tid: Dropdown f�r minuter (om 0 hoppar man �ver den fasen)
** Deployment: Dropdown f�r minuter
** Total tid: Dropdown f�r timmar och minuter.
* Klocka
** Total tid kvar: Label
** Tur: Label
** Spelare: Label
** Tid kvar tipp n�sta spelare: Label

Fluff
-------
* Ljud
* Start
* Stop
* Halvtid
* Turbyte
* Spelarbyte
* Faktisk tid och spelare typ som en schack klocka
* Logg d�r man kan se tider och sammanst�llning f�r vad som tog l�ngst tid
* Knappar f�r att byta spelare och registrera tid f�r regelfr�gor