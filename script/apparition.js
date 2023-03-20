window.addEventListener('load', function() {
    var searchButton = document.querySelector('.search-box button[type="submit"]');
    var reveal = document.querySelector('.reveal');

    searchButton.addEventListener('click', function(event) {
        event.preventDefault();
        reveal.classList.add('active');
    });

   window.addEventListener('scroll', revealOnScroll);

   function revealOnScroll(){
        var reveals = document.querySelectorAll('.reveal');
        for (var i = 0; i < reveals.length; i++) {
            var windowHeight = window.innerHeight;
            var revealTop = reveals[i].getBoundingClientRect().top;
            var revealPoint = 150;

            if(revealTop < windowHeight - revealPoint){
                reveals[i].classList.add('active');
            }else{
                reveals[i].classList.remove('active');
            }
        }
    }
});
