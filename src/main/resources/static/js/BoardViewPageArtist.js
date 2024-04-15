// scroll content
const carousel = document.querySelectorAll('.carousel');

document.addEventListener('DOMContentLoaded', function() {
    var startDate = document.getElementById('eventStart').textContent;
    var endDate = document.getElementById('eventEnd').textContent;

    var start = new Date(startDate);
    var end = new Date(endDate);
    
    console.log(start);
    console.log(end);

    var diffTime = Math.abs(end - start);
    var diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 

    document.querySelector('.remaintime').textContent = diffDays + '일 남음';
});

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


