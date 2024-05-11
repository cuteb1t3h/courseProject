// Прокрутка страницы при клике на якорную ссылку
$('a[href^="#"]').on('click', function (event) {
    const target = $(this.getAttribute('href'));

    if (target.length) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: target.offset().top
        }, 1000);
    }
});


const scrollBtn = document.getElementById('scroll');

scrollBtn.addEventListener('click', () => {
    const scrollHeight = window.innerHeight;
    window.scrollBy({
        top: scrollHeight,
        behavior: 'smooth'
    });
});