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

// Модальное окно
const form = document.getElementById('contactForm');
const modal = document.getElementById('myModal');
const span = document.getElementsByClassName('close')[0];

form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(form);

    fetch('/', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                modal.style.display = 'block';
                form.reset();
            } else {
                console.error('Error submitting form:', response.statusText);
            }
        });
});
span.onclick = function () {
    modal.style.display = 'none';
}
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}
