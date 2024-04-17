//view more
// const div_content = document.querySelector('#purpose');

// const btn_detail = document.querySelector('#buttonDetail');
// btn_detail.addEventListener("click", (e)=>{
//     if(e.target.InnerText=="더보기"){
//         div_content.classList.remove("short");
//         e.target.InnerText = "접기";
        
//     }else{
//         div_content.classList.add("short");
//         e.target.InnerText = "더보기"
//     }
// });




// scroll content
const carousel = document.querySelectorAll('.carousel');

for (let target of carousel) {
    let isDragStart = false, prevPageX, prevScrollLeft;
    let hasMoved = false; // 움직임 여부를 추적하는 플래그 추가

    let dragStart = (e) => {
        // 마우스 다운 이벤트에서 전역 변수 값 업데이트
        isDragStart = true;
        prevPageX = e.pageX;
        prevScrollLeft = target.scrollLeft;
    }

    const dragging = (e) => {
        // 마우스 포인터에 따라 이미지/캐러셀을 왼쪽으로 스크롤
        if (!isDragStart) return;
        e.preventDefault();
        let positionDiff = e.pageX - prevPageX;
        target.scrollLeft = prevScrollLeft - positionDiff;
        hasMoved = true; // 움직임이 있었음을 나타내는 플래그 업데이트
    }

    const dragStop = () => {
        isDragStart = false;
        setTimeout(() => hasMoved = false, 50); // 짧은 지연 후 플래그 재설정
    }

    const preventClick = (e) => {
        if (hasMoved) {
            e.preventDefault(); // 움직임이 있었을 경우 클릭 동작 방지
        }
    }

    target.addEventListener("mousedown", dragStart);
    target.addEventListener("mousemove", dragging);
    target.addEventListener("mouseup", dragStop);
    target.addEventListener("click", preventClick, true); // 캡처 단계에서 이벤트를 빨리 잡기 위해 사용
}

// scroll content END


