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
$(".searchBar").click(function() {
    if($(".searchBar").children().attr("class") == "open") {
        $(".searchBar").children().attr("class", "close");
    }
    else {
        $(".searchBar").children().attr("class", "open");
    }
    $(".searchTbl").toggle();
});
