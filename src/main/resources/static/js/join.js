// 이메일 유효성 검사
function sendVerificationCode() {

  var email = document.getElementById("email").value;
  var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (!emailPattern.test(email)) {
    alert("유효한 이메일 주소를 입력하세요.");
    return false;
  }

  // AJAX 요청
  $.ajax({

    url: "/submitEmailToken",
    type: "POST",
    dataType: "json",
    data: { email: email },

    success: function (response) {

      console.log(response);
      console.log(email);

      var redirectUrl = response.redirectUrl;
      var message = response.message;

      alert(message); // 서버에서 보낸 메시지를 알림으로 표시

    },
    error: function () {

      alert("서버오류입니다.");

    }

  });

  // 기본 동작 방지
  return false;

}

// 토큰 검사
function checkVerificationCode() {

  var email_Check_key = document.getElementById("email_Check_key").value;
  var tokenPattern = /^(\(?\+?[0-9]*\)?)?[0-9_\- \(\)]*$/;

  console.log(email_Check_key);

  if (email_Check_key == "") {
    alert("토큰값을 입력해주세요.");
    return false;
  }

  if (!tokenPattern.test(email_Check_key)) {
    alert("유효한 토큰을 입력하세요.");
    return false;
  }

  // AJAX 요청
  $.ajax({

    url: "/checkEmailToken",
    type: "POST",
    dataType: "json",
    data: { email_Check_key: email_Check_key },

    success: function (response) {

      console.log(response);
      console.log(email_Check_key);

      var redirectUrl = response.redirectUrl;
      var message = response.message;

      alert(message); // 서버에서 보낸 메시지를 알림으로 표시

    },
    error: function () {

      alert("서버오류입니다.");

    }

  });

  // 기본 동작 방지
  return false;

}

// 아이디 유효성 검사
var $mid_id = $("#mid_id");
$mid_id.on("keyup", function () { // 키보드에서 손을 땠을 때 실행
  var regExp = /^[a-z]+[a-z0-9]{5,15}$/g;
  var $id = $("#id");

  if (!regExp.test($mid_id.val())) { // id 가 공백인 경우 체크
    idchk = false;
    $id.html("<span id='idcheck'>특수문자,공백 제외 영어로만 (5~15자 입력)</span>");
    $("#idcheck").css({
      "color": "#ff0000",
      "font-weight": "bold",
      "font-size": "10px"
    })
    return false;
  } else { // 공백아니면 중복체크
    $.ajax({
      type: "POST", // http 방식 
      url: "/idCheck", // ajax 통신 url
      data: { // ajax 내용 => 파라미터 : 값 이라고 생각해도 무방
        "type": "user",
        "id": $mid_id.val()
      },
      success: function (data) {

        if (data == 1) { // 1이면 중복

          idchk = false;
          $id.html("<span id='idcheck'>이미 존재하는 아이디입니다</span>")
          $("#idcheck").css({
            "color": "#ff0000",
            "font-weight": "bold",
            "font-size": "10px"

          })
          return false;

        } else { // 아니면 중복아님
          idchk = true;
          $id.html("<span id='idcheck'>사용가능한 아이디입니다</span>")

          $("#idcheck").css({
            "color": "#9c9c9c",
            "font-weight": "bold",
            "font-size": "10px"

          })
        }
      }
    })
  }
});

// 닉네임 유효성 검사
var $mid_nickname = $("#mid_nickname");
var nicknamechk = false;
$mid_nickname.on("keyup", function () {
  var regExp = /^[a-z]+[a-z0-9]{5,15}$/g;
  var $nickname = $("#nickname");

  if ($mid_nickname.val() === "") {
    nicknamechk = false; // 유효하지 않은 닉네임이 입력됐을 때 상태를 변경
    $nickname.html("<span id='nickname'>닉네임을 입력해주세요.</span>").css({ // 스타일 적용하는 부분에 직접적으로 css 메소드를 체이닝
      "color": "#ff0000",
      "font-weight": "bold",
      "font-size": "10px"
    });
    return false;
  } else {
    $.ajax({
      type: "POST",
      url: "/nicknameCheck",
      data: {
        "type": "user",
        "nickname": $mid_nickname.val()
      },
      success: function (data) {
        if (data == 1) {
          nicknamechk = false; // 중복된 닉네임이 있을 때 상태를 변경
          $nickname.html("<span id='nickname'>이미 존재하는 닉네임입니다.</span>").css({ // 스타일 적용하는 부분에 직접적으로 css 메소드를 체이닝
            "color": "#ff0000",
            "font-weight": "bold",
            "font-size": "10px"
          });
          return false;
        }
        else { // 아니면 중복아님
          nicknamechk = true;
          $nickname.html("<span id='nickname'>사용가능한 닉네임입니다</span>")

          $("#nickname").css({
            "color": "#9c9c9c",
            "font-weight": "bold",
            "font-size": "10px"

          })
        }
      }
    });
  }
});


// 비밀번호 유효성 검사
var $password_ck = $("#password_ck");
var $password = $("#password");
$password_ck.on("keyup", function () {

  if ($('#password').val() == $('#password_ck').val()) {
    
    $('#pwConfirm').text('비밀번호 일치').css({
      "color": "#9c9c9c",
      "font-weight": "bold",
      "font-size": "10px"
   
    });

  } else {

    $('#pwConfirm').text('비밀번호 불일치').css({
      "color": "#ff0000",
      "font-weight": "bold",
      "font-size": "10px"

    });

    return false;
    
  };;

});

// 데이터 insert 검사
function validateForm() {

  var email = document.getElementById("email").value;
  var email_Check_key = document.getElementById("email_Check_key").value;
  var mid_id = document.getElementById("mid_id").value;
  var mid_nickname = document.getElementById("mid_nickname").value;
  var password = document.getElementById("password").value;
  var password_ck = document.getElementById("password_ck").value;


  $.ajax({

    url: "/joinForm",
    type: "POST",
    dataType: "json",
    data: { email: email,
            email_Check_key: email_Check_key,
            mid_id: mid_id,
            mid_nickname: mid_nickname,
            password: password,
            password_ck: password_ck
          },

    success: function (response) {

      console.log(response);

      var redirectUrl = response.redirectUrl;
      var message = response.message;

      alert(message); // 서버에서 보낸 메시지를 알림으로 표시
      window.location.href = redirectUrl; // 리다이렉트

    },
    error: function () {

      alert("서버 오류가 발생했습니다.");

    }
  });

}

// 유효성 검사
document.addEventListener('DOMContentLoaded', () => {
  const ckTag = document.getElementById('id_a');
  const submitButton = document.querySelector('.submit');
  const requiredCheckboxes = document.querySelectorAll('input[name="agree"]');

  // 버튼 전체 동의 (필수, 선택) 부분 코드
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
