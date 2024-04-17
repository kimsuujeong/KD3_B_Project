// id가 content인 쪽에 ck에디터를 넣어줌

ClassicEditor.create( document.querySelector( '#content' ), {
    language: "ko",
    ckfinder :{
      // 백단에서 이미지를 받아줄 링크
      uploadUrl: "/images/upload",
      withCredentials: true
    }
  } );