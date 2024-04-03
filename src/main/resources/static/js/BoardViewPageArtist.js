// scroll content
const carousel = document.querySelectorAll('.carousel');

for(let target of carousel){

    let isDragStart = false, prevPageX, prevScrollLeft;

    let dragStart = (e) => {
        // updating global variables valueon mouse down event
        isDragStart = true;
        prevPageX = e.pageX;
        prevScrollLeft = target.scrollLeft;
    }

    const dragging = (e) => {
        // scrolling images/carousel to left according to mouse pointer
        if(!isDragStart) return;
        e.preventDefault();
        let positionDiff = e.pageX - prevPageX;
        target.scrollLeft = prevScrollLeft - positionDiff;
    }

    const dragStop = () => {
        isDragStart = false;
    }


    target.addEventListener("mousedown", dragStart);
    target.addEventListener("mousemove", dragging);
    target.addEventListener("mouseup", dragStop);
}

// scroll content END


// trailer img
const slider = document.querySelector('.trailer-img-slider');
const trailerCarousel = document.querySelector('.trailer-img-carousel');

const prev = document.querySelector('.prev');
const next = document.querySelector('.next');


var direction;

prev.addEventListener('click', function(){
    if(direction === -1){
        direction = 1;
        slider.appendChild(slider.firstElementChild);
    }
    trailerCarousel.style.justifyContent = 'flex-end'; 
    slider.style.transform = 'translate(20%)';
});

next.addEventListener('click', function(){
    direction = -1;
    trailerCarousel.style.justifyContent = 'flex-start'; 
    slider.style.transform = 'translate(-20%)';
});

slider.addEventListener('transitioned', function(){
    if(direction === -1){
        slider.appendChild(slider.firstElementChild);
    } else if(direction === 1){
        slider.prepend(slider.lastElementChild);
    }
    

    slider.style.transition = 'none';
    slider.style.transform = 'translate(0)';
    setTimeout(function(){
        slider.style.transition = 'all 0.5s';
    })
    
});