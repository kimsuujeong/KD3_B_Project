// banner img slider

let btn1 = document.querySelector('.btn1');
let btn2 = document.querySelector('.btn2');
let btn3 = document.querySelector('.btn3');

btn1.addEventListener('click', function(){
    document.querySelector('.banner-img-slider-container').style.transform = 'translate(0vw)';
});

btn2.addEventListener('click', function(){
    document.querySelector('.banner-img-slider-container').style.transform = 'translate(-100vw)';
});

btn3.addEventListener('click', function(){
    document.querySelector('.banner-img-slider-container').style.transform = 'translate(-200vw)';
});

// slider = banner-img-slider
// list = banner-img-slider-container
// item = inner

// let list = document.querySelector('.banner-img-slider-container');
// let items = document.querySelectorAll('.inner');
// let dots = document.querySelectorAll('.dots li');
// let prev = document.getElementById('prev');
// let next = document.getElementById('next');

// let active = 0;

// console.log(list);

// next.addEventListener('click', function(){
//     active += 1;
// });




// scroll content
const carousel = document.querySelector('.carousel');

let isDragStart = false, prevPageX, prevScrollLeft;

let dragStart = (e) => {
    // updating global variables valueon mouse down event
    isDragStart = true;
    prevPageX = e.pageX;
    prevScrollLeft = carousel.scrollLeft;
}

const dragging = (e) => {
    // scrolling images/carousel to left according to mouse pointer
    if(!isDragStart) return;
    e.preventDefault();
    let positionDiff = e.pageX - prevPageX;
    carousel.scrollLeft = prevScrollLeft - positionDiff;
}

const dragStop = () => {
    isDragStart = false;
}

carousel.addEventListener("mousedown", dragStart);
carousel.addEventListener("mousemove", dragging);
carousel.addEventListener("mouseup", dragStop);
// scroll content END
