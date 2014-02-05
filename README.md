Game-Timer
==========
*Standalone Timer application for Warhammer tournaments*

Syfte
-------
En timmer som hjälper till med hålltider i Warhammer med en timmer och text som talar om vilken tid det är och vilken tur och spelare det borde vara.

Indata:
-------
* Speltid: den tiden som hela matchen ska ta som mest
* Presentations tid: Den tid som båda spelarna ska ha på sig att presentera sin arméer för varandra om man spelar med öppna listor.
* Deployment tid: den tid man ska få på sig att sätta ut sin armé på spelbordet.

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
* Reset: Nollställer timers
* Clear: Rensar inställningarna

UI
--------
* En ruta med två flikar. En för inställningar och en för klockan*
* Inställningar
** Presentations tid: Dropdown för minuter (om 0 hoppar man över den fasen)
** Deployment: Dropdown för minuter
** Total tid: Dropdown för timmar och minuter.
* Klocka
** Total tid kvar: Label
** Tur: Label
** Spelare: Label
** Tid kvar tipp nästa spelare: Label

Fluff
-------
* Ljud
* Start
* Stop
* Halvtid
* Turbyte
* Spelarbyte
* Faktisk tid och spelare typ som en schack klocka
* Logg där man kan se tider och sammanställning för vad som tog längst tid
* Knappar för att byta spelare och registrera tid för regelfrågor