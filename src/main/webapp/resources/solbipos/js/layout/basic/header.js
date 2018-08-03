/****************************************************************
 *
 * 파일명 : header.js
 * 설  명 : header 용 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
$("#pwchg").bind("click", function() {
    $("#fullDimmedPw").show();
    $("#layerpw").show();
});

$(".userId").click(function() {
    $(".userLayer").toggle();
});

$(".favClose").click(function() {
    callPostJson("/menu/delFixMenu.sb", $(this).data("value"));
});

$(".histClose").click(function() {
    callPostJson("/menu/delHistMenu.sb", $(this).data("value"));
});

function callPostJson(url, menuId) {
    var param = {};
    param.menuId = menuId;
    $.postJSON(url, param, function(result) {
            $("#" + menuId).remove();
        },
        function(result){
            s_alert.pop(result.data.msg);
        }).fail(function() {
        s_alert.pop("Ajax Fail");
    });
}

// 고정메뉴영역 오른쪽 스크롤
$(".moveBtn .mR").click(function() {
    var basePos = $(".moveBtn").position().left;
    var width = 0;
    $("#_fixMenu li").filter(":visible").each(function(index){
        width += $(this).width();
    });
    if(width > basePos) {
        $("#_fixMenu li:visible").first().hide();
    }
});
// 고정메뉴영역 왼쪽 스크롤
$(".moveBtn .mL").click(function() {
    $("#_fixMenu li:hidden").last().show();
});

//  메뉴 열고 닫기
$(".menuControl").click(function(){
    if($("#_nav").attr("class") == "menuOpen"){
        $("#_faMenu").hide();
        $("#theTreeAll").hide();
        $("#smallMenu").show();
        $("#_favorite").removeClass("on");
        $("#_nav").removeClass("menuOpen").addClass("menuClose");
        $("#_arrow").removeClass("arrowOpen").addClass("arrowClose");
    }
    else{
        $(".menuTab .all").trigger("click");
        $("#theTreeAll").show();
        $("#smallMenu").hide();
        $("#_nav").removeClass("menuClose").addClass("menuOpen");
        $("#_arrow").removeClass("arrowClose").addClass("arrowOpen");
    }
});
