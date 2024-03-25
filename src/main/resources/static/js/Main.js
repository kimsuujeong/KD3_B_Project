// img slider 
var counter = 1;
setInterval(function(){
    document.getElementById('radio' + counter).checked = true;
    counter++;
    if(counter > 5){
        counter = 1;
    }
}, 5000);
// img slider END


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

// carousel.addEventListener("mousedown", dragStart);
// carousel.addEventListener("mousemove", dragging);
// carousel.addEventListener("mouseup", dragStop);
// scroll content END