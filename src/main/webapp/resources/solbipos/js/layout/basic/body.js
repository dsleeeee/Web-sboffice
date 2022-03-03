/****************************************************************
 *
 * 파일명 : body.js
 * 설  명 : body 용 JavaScript
 *
 *    수정일      수정자       Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03    노현수       1.0
 * 2018.10.22    노현수       1.1
 * 2021.04.02    이다솜       1.2           Div 닫고 열기 추가
 * **************************************************************/
// 검색 조건 닫고 열기
$(".flddUnfld").click(function(e) {
  if($(e.target).is('button')){
    e.preventDefault();
    return false;
  }
  $(this).children("a").toggleClass("open");
  $(this).children("a").toggleClass("close");

  $(".searchTbl").toggle();
});

// Div 닫고 열기
function divFldUnfld(Id){
  $("#" + Id).children("a").toggleClass("open");
  $("#" + Id).children("a").toggleClass("close");

  //$("#" + Id + "Div").slideToggle('fast');
  $("#" + Id + "Div").toggle();
}

// 열려있는 Div를 Close 상태로 셋팅
function divClose(Id){
  // 기존 Class 제거
  $("#" + Id).children("a").removeClass("open");
  $("#" + Id).children("a").removeClass("close");

  // Close 상태로 셋팅
  $("#" + Id).children("a").addClass("close");
  $("#" + Id + "Div").css("display", "none");
}