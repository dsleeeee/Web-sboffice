/****************************************************************
 *
 * 파일명 : body.js
 * 설  명 : body 용 JavaScript
 *
 *    수정일      수정자       Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03    노현수       1.0
 * 2018.10.22    노현수       1.1
 *
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
