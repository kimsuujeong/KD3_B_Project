/**
 *  회원가입 관련 처리
 */

function checkEmail() {
  var email = document.getElementById("email").value;
  // 간단히 이메일 형식을 체크합니다.
  if (!isValidEmail(email)) {
    document.getElementById("emailMessage").innerHTML = "<strong style='color:red;'>유효한 이메일 주소를 입력하세요.</strong>";
    return;
  }

//   // 간단히 이메일이 "admin@example.com"일 때만 중복된 것으로 처리합니다.
//   if (email === "admin@example.com") {
//     document.getElementById("emailMessage").innerHTML = "<strong style='color:red;'>이미 사용 중인 이메일 주소입니다.</strong>";
//   } else {
//     document.getElementById("emailMessage").innerHTML = "<strong style='color:green;'>사용 가능한 이메일 주소입니다.</strong>";
//   }
}

// 이메일 형식을 체크하는 함수
function isValidEmail(email) {
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// function checkUsername() {
//   var username = document.getElementById("username").value;
//   // 여기에서는 예시로 간단히 길이가 5 이상인 경우에만 유효한 것으로 가정합니다.
//   if (username.length < 5) {
//     document.getElementById("usernameMessage").innerHTML = "<strong style='color: red;'>아이디는 5자 이상이어야 합니다.</strong>";
//     return;
//   }

//   // 간단히 아이디가 "admin"인 경우에만 중복된 것으로 처리합니다.
//   if (username === "admin") {
//     document.getElementById("usernameMessage").innerHTML = "<strong style='color: red;'>이미 사용 중인 아이디입니다.</strong>";
//   } else {
//     document.getElementById("usernameMessage").innerHTML = "<strong style='color: green;'>사용 가능한 아이디입니다.</strong>";
//   }
// }

// function checkNickname() {
//   var nickname = document.getElementById("nickname").value;
//   // 여기에서는 예시로 간단히 길이가 3 이상인 경우에만 유효한 것으로 가정합니다.
//   if (nickname.length < 3) {
//     document.getElementById("nicknameMessage").innerHTML = "<strong style='color: red;'>닉네임은 3자 이상이어야 합니다.</strong>";
//     return;
//   }

//   // 간단히 닉네임이 "user"인 경우에만 중복된 것으로 처리합니다.
//   if (nickname === "user") {
//     document.getElementById("nicknameMessage").innerHTML = "<strong style='color: red;'>이미 사용 중인 닉네임입니다.</strong>";
//   } else {
//     document.getElementById("nicknameMessage").innerHTML = "<strong style='color: green;'>사용 가능한 닉네임입니다.</strong>";
//   }
// }

// function submitForm() {
//   window.location.href = "login.html";
// }

// 버튼 전체동의 (필수, 선택) 부분 코드
document.addEventListener('DOMContentLoaded', () => {
    const ckTag = document.getElementById('id_a');
    const submitButton = document.querySelector('.submit');
    const requiredCheckboxes = document.querySelectorAll('input[name="agree"]:required');

    ckTag.addEventListener('change', () => {
        const allChecked = ckTag.checked;

        requiredCheckboxes.forEach(checkbox => {
            checkbox.checked = allChecked;
        });

        submitButton.disabled = !allChecked;
    });

    requiredCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            const allAgreed = [...requiredCheckboxes].every(cb => cb.checked);
            submitButton.disabled = !allAgreed;

        });

    });

});