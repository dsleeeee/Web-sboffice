/****************************************************************
 *
 * 파일명 : body.js
 * 설  명 : body 용 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
// 검색 조건 닫고 열기
$(".flddUnfld").click(function() {
    if($(".flddUnfld").children().attr("class") == "open") {
        $(".flddUnfld").children().attr("class", "close");
    }
    else {
        $(".flddUnfld").children().attr("class", "open");
    }
    $(".searchTbl").toggle();
});
