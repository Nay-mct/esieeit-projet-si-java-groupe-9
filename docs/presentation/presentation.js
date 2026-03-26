const slides = Array.from(document.querySelectorAll('.slide'));
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
const counter = document.getElementById('counter');
const progressBar = document.getElementById('progressBar');
let index = 0;

function render(nextIndex) {
  index = Math.max(0, Math.min(nextIndex, slides.length - 1));
  slides.forEach((slide, i) => slide.classList.toggle('active', i === index));
  counter.textContent = `${index + 1} / ${slides.length}`;
  progressBar.style.width = `${((index + 1) / slides.length) * 100}%`;
}

prevBtn.addEventListener('click', () => render(index - 1));
nextBtn.addEventListener('click', () => render(index + 1));

document.addEventListener('keydown', (event) => {
  if (['ArrowRight', 'PageDown', ' '].includes(event.key)) {
    event.preventDefault();
    render(index + 1);
  }
  if (['ArrowLeft', 'PageUp'].includes(event.key)) {
    event.preventDefault();
    render(index - 1);
  }
  if (event.key === 'Home') {
    event.preventDefault();
    render(0);
  }
  if (event.key === 'End') {
    event.preventDefault();
    render(slides.length - 1);
  }
});

render(0);
