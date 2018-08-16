/****************************************************************
 *
 * 파일명 : menu.js
 * 설  명 : 메뉴생성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function() {

    // 트리 생성
    allMenu = makeTree('#_theTreeAll', menus, cMenuResrceCd);
    bkmkMenu = makeTree('#_theTreeBkmk', bkmks);

    // 단축 메뉴 생성--%>
    $(menus).each(function(index) {
        $("#_smallMenuUl").append(wijmo.format('<li class="{icon}"><a href="javascript:void(0);"></a></li>', this));
    });

    // 전체메뉴 클릭
    $(".menuTab .all").click(function() {
        $("#_all").addClass("on");
        $("#_favorite").removeClass("on");
        $("#_bkmkTxt, #_faMenu").hide();
        $("#_theTreeAll").show();
        $("#_theTreeBkmk").hide();
    });

    // 즐겨찾기 메뉴 클릭
    $(".menuTab .favorite").click(function() {
        $("#_all").removeClass();
        $("#_favorite").addClass("on");
        $("#_faMenu").show();
        $("#_theTreeAll").hide();
        if(isEmpty(bkmks)) {
            $("#_bkmkTxt").show();
        }
        $("#_theTreeBkmk").show();
    });

    // 접힌 메뉴 클릭 시 열린 메뉴 오픈
    $(document).on("click", ".smallMenu li", function(){
        $(".menuControl").trigger("click");
        var findClass = $(this).attr("class");
        if(findClass != null) {
            for (var node = allMenu.getFirstNode(); node; node = node.nextSibling()) {
                if(node.dataItem.icon == findClass) {
                    allMenu.selectedItem = node.dataItem;
                    node.isCollapsed = false;
                }
            }
        }
    });

    // 접힌 메뉴(즐겨찾기) 클릭 시 열린 메뉴 오픈
    $(document).on("click", "#_favorite", function(){
        if($("#_nav").attr("class")=="menuClose"){
            $(".menuControl").trigger("click");
            $(".menuTab .favorite").trigger("click");
        }
    });

});
