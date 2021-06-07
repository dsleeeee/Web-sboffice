/****************************************************************
 *
 * 파일명 : body.js
 * 설  명 : body 용 JavaScript
 *
 *    수정일      수정자       Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03    노현수       1.0
 * 2018.10.22    노현수       1.1
 * 2021.04.02    이다솜       1.2           Grid 닫고 열기 추가
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

// Grid 닫고 열기
function girdFldUnfld(Id){
  $("#" + Id).children("a").toggleClass("open");
  $("#" + Id).children("a").toggleClass("close");
  
  //$("#" + Id + "Grid").slideToggle('fast');
  $("#" + Id + "Grid").toggle();
}

// Grid 조회 시, 닫혀있는 Grid Open
function gridOpen(Id){
  // 기존 Class 제거
  $("#" + Id).children("a").removeClass("open");
  $("#" + Id).children("a").removeClass("close");

  // Open 상태로 셋팅
  $("#" + Id).children("a").addClass("open");
  $("#" + Id + "Grid").css("display", "block");
}

// Grid 조회 후, 데이터 유무에 따른 Msg 띄우기
function gridShowMsg(Id, type){
  if(type === "Y") {
    $("#" + Id + "Msg").css("display", "block");
  }else {
    $("#" + Id + "Msg").css("display", "none");
  }
}

// Grid 조회 후, 데이터 유무에 따른 Msg 띄우기
function gridShowMsgNoData(Id, gird, rowSumVisible){
    // rowSumVisible 합계 Row 존재하면 Y

    if(gird.rows.length <= 0) {
        $("#" + Id + "Msg").css("display", "block");

        // 합계 Row 존재하면
        if(rowSumVisible === "Y") {
            gird.columnFooters.rows.removeAt();
        }
    } else {
        $("#" + Id + "Msg").css("display", "none");

        // 합계 Row 존재하면
        if(rowSumVisible === "Y") {
            // 합계
            gird.columnFooters.rows.push(new wijmo.grid.GroupRow());
            gird.bottomLeftCells.setCellData(0, 0, '합계');
        }
    }
}

