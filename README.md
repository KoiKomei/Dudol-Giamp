# DUDOL GIAMP

### 1. Description:

Dudol Giamp è un gioco per cellulare a scorrimento verticale che simula un'infinita ascesa del proprio personaggio. 
L'obiettivo principale è quello di non cadere ed accumulare più punti possibili salendo sempre di più utilizzando delle piccole piattaforme per saltare.
Non richiede sapere niente riguardo il gioco per poter iniziare a giocare e va bene per qualsiasi età, però è propensa di più per adolescenti che devono coprire qualche buco di noia nella giornata, una partita può durare anche solo 30 secondi.

### 2. Context-Aware:

Sia il multiplayer che il singleplayer possono utilizzare il gps per cambiare parti del gioco come lo sfondo, ad esempio, in caso piova nel posto in cui ci si trova, lo sfondo cambia da quello standard ad uno che simula un ambiente piovoso. 
In base alla posizione gps verrà proposto di battere il record di punti nella zona circostante se maggiore al proprio. Inoltre, sempre grazie alla posizione gps, verrà valutato il punteggio finale del giocatore dopo una partita singleplayer, confrontandolo con quello di altri giocatori e indicando se sia il migliore del mondo, del proprio paese, della propria regione e cosi via. Grazie alla mappa accessibile dal menù di gioco,  l’utente può vedere i punteggi record degli altri giocatori e la posizione dove questi sono stati raggiunti.

### 3. Features:

#### 3a. Account: 

La persona che inizia a giocare può creare un account per salvare il proprio punteggio record e la posizione in cui lo ha raggiunto su un database in cloud oppure giocare senza un proprio account e salvare solo i record in locale. 
É possibile effettuare la registrazione, il login, il logout, visionare le proprie informazioni e modificare la propria password.

#### 3b. Single Player:

L'utente può giocare una partita in giocatore singolo cliccando sull’apposito tasto nel menù.
Il movimento del personaggio in orizzontale è dato dall'inclinazione data al proprio cellulare (controllata dall’accelerometro), il movimento verticale invece è dato dalla gravità e quindi il proprio personaggio sarà sempre in caduta, eccetto per quando rimbalzerà su una piattaforma.
La partita finisce in caso il personaggio dovesse cadere non prendendo nessuna piattaforma oppure in caso si avvicini troppo ad un nemico. 
Cliccando sullo schermo, il proprio personaggio sparerà dei proiettili che saranno utili per eliminare i nemici.
Un metodo alternativo per eliminare i nemici è quello di saltargli sopra: facendo così si avrà anche una spinta verso l’alto, quindi possono essere usati come piattaforme rimbalzanti monouso.
Si possono inoltre utilizzare dei trampolini o dei jetpack per salire più velocemente.
Al termine della partita, il punteggio verrà trasformato, con un rateo 10:1, in valuta virtuale per il negozio del gioco.

#### 3c. Multiplayer: 

Prende tutte le basi di gioco del giocatore singolo ma dà la possibilità di giocare online insieme ad una seconda persona.
Appena entrato in partita si dovrà aspettare che un secondo giocatore si connetta per poter effettivamente competere.
Le mappe sono generate casualmente per entrambi i giocatori, quindi il secondo giocatore sullo schermo è solo una rappresentazione di quest’ultimo nella propria partita.
La partita finisce non appena uno dei due dovesse perdere cadendo o finendo contro un nemico.
Personalizzazione: 
E’ possibile personalizzare l’aspetto del proprio personaggio cambiando il costume indossato attraverso il negozio dove è possibile anche acquistarne di nuovi.
Attraverso le opzioni, invece, è possibile abilitare/disabilitare i suoni, la musica ed lo sfondo dinamico basato sul meteo della propria zona.
#### 3d. Classifica:
Lo scoreboard, ovvero, la classifica, mostra i 5 migliori punteggi raggiunti sul dispositivo indicando anche il nome del giocatore che lo ha raggiunto.
Il nome di solito equivale a quello dato all’account alla creazione, ma alla fine di una partita è possibile salvare un punteggio anche con un nome differente in caso non si abbia effettuato l’accesso oppure in caso qualcun’altro giochi col tuo dispositivo.
#### 3e. Mappa:
La mappa consente di visualizzare la propria posizione attuale con il proprio record e la posizione ed il punteggio di tutti gli altri utenti registrati. 
In caso ci sia un record più alto del tuo nei tuoi dintorni, l’applicazione farà apparire una notifica per invitare l’utente a giocare e cercare di battere il record della zona.

### 4. Implementation

Gestione di un account: comprende la registrazione e creazione di un account, il login ed il logout e la possibilità di visualizzare le proprie informazioni e di eventualmente cambiare la password.

Gestione dei punteggi: permette di salvare nel dispositivo, tramite l’uso dei pref, i 5 punteggi più alti effettuati nel dispositivo, offrendo la possibilità di salvare anche il proprio nome con o senza account.

Single player: permette di effettuare una partita in solitaria cercando di accumulare punti per battere i propri record o quelli degli altri utenti.

Multiplayer: consente di effettuare una partita contro un altro giocatore attraverso l’uso di un sistema client-server.

Negozio: l’utente può usare I soldi guadagnati giocando per comprare cosmetici per il personaggio da usare poi in partita.

Scoreboard: visualizza i 5 punteggio migliori sul dispositivo

Mappa: vengono visualizzati i punteggi e la posizione dei record degli utenti e dell’utente attualmente sul dispositivo.

Opzioni: permettono di modificare le impostazioni del gioco.

Musica di sottofondo.

Sound di gioco.

Modifica dello sfondo in base al meteo.

### 5. Related work

Il progetto nasce dal voler realizzare un’applicazione ispirata al noto gioco Doodle Jump.
La nostra app infatti ripropone lo stesso sistema di gioco, aggiungendo diverse funzionalità, come ad esempio l’utilizzo del sistema di localizzazione GPS per gestire una mappa in cui la posizione ed i record dei giocatori sono salvati e per valutare il proprio punteggio in ogni zona, oppure la creazione di un multiplayer dove due giocatori possono scontrarsi o perfino un negozio di cosmetici per il proprio personaggio.

### 6. Future works:

La creazione di tornei temporanei nei quali si danno dei premi in base al posizionamento nella classifica del torneo.
La creazione di classifiche che possono variare dal singolo giorno alla classifica annuale e dare dei premi in base alla posizione.
La realizzazione di livelli sfruttando un algoritmo procedurale.
Adattamento dell’applicazione su ogni tipo di schermo cellulare e tablet.

### 7. Members

Antonio Arci, Creator, Lead Project Design and Network Specialist

Alex Parisella, Graphic Designer and Database Specialist

Luca Quarta, Game Mechanics and Social Media Specialist
