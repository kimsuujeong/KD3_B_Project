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

// pagination
let link = document.querySelectorAll('.link');
let currentValue = 1;

function activeLink(){
    for(let l of link){
        l.classList.remove("active");
    }
    event.target.classList.add("active");
    currentValue = event.target.value;
};

function backBtn(){
    if(currentValue > 1){
        for(let l of link){
            l.classList.remove("active");
        }
        currentValue--;
        link[currentValue - 1].classList.add("active");
    }
};

function nextBtn(){
    if(currentValue < 6){
        for(let l of link){
            l.classList.remove("active");
        }
        currentValue++;
        link[currentValue - 1].classList.add("active");
    }
}
// after 5 is Error