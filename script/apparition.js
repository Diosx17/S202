window.addEventListener('load', function () {
    var searchButton = document.querySelector('.search-box button[type="submit"]');
    var searchBox = document.querySelector('.search-box');
    var logo = document.querySelector('#logo');
    var reveal = document.querySelector('.reveal');
    var randomarticle = document.querySelector('.random-article');
    var blochistorique = document.querySelector('.bloc-historique');
    var nav = document.querySelector('.navigation');
    var finditems = document.querySelector('.find-items');
    var ns = document.querySelector('#notreselect');

    searchButton.addEventListener('click', function (event) {
        event.preventDefault();
        searchBox.classList.add('active');
        logo.classList.add('active');
        reveal.classList.add('active');
        randomarticle.classList.add('suppr');
        blochistorique.classList.add('suppr');
        nav.classList.add('active');
        finditems.classList.add('active');
        ns.classList.add('suppr');
    });

    //  Andy ------------------------------------------------------------
    const checkbox = document.querySelector('#infobulle2');
    var acc = "#2d2c2c"
    checkbox.addEventListener('click', function() {
        document.body.style.background = acc;
        if (acc=="#2d2c2c"){
            acc = "white"
        }
        else{
            acc = "#2d2c2c"
        }
        
    });
    // fin Andy -----------------------------------------------------------
    
    //à continuer c'est pour la barre de recherche qui se met en haut quand on scroll
    /*const searchBar = document.querySelector('.search-box');

    window.addEventListener('scroll', () => {
    if (window.scrollY > searchBar.offsetTop) {
        searchBar.classList.add('fixed');
    } else {
        searchBar.classList.remove('fixed');
    }
    });*/


    window.addEventListener('scroll', revealOnScroll);

    function revealOnScroll() {
        var reveals = document.querySelectorAll('.reveal');
        for (var i = 0; i < reveals.length; i++) {
            var windowHeight = window.innerHeight;
            var revealTop = reveals[i].getBoundingClientRect().top;
            var revealPoint = 150;

            if (revealTop < windowHeight - revealPoint) {
                reveals[i].classList.add('active');
            }/* else {
                reveals[i].classList.remove('active');
            }*/
        }
    }
});

/*pour faire deux css différents : l'un pour le mode sombre et l'autre pour le mode clair

// Récupérez la balise <link> de la feuille de style
var styleSheet = document.getElementById('css/style.css');

// Fonction pour changer la feuille de style
function swapStyleSheet(sheet){
    styleSheet.setAttribute('href', sheet);
}

// Événement pour activer le mode nuit
document.getElementById('night-mode').addEventListener('click', function(){
    swapStyleSheet('night.css');
});

// Événement pour désactiver le mode nuit
document.getElementById('day-mode').addEventListener('click', function(){
    swapStyleSheet('day.css');
});
*/




