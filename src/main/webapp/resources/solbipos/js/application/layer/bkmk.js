/****************************************************************
 *
 * 파일명 : bkmk.js
 * 설  명 : 북마크 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function() {

    $(".btn_faManage").click(function(e){
        makeMenu();
    });

    $(".bkmk_close").click(function(e){
        $("#_brmkLayer, #_brmkFullDimmed").hide();
    });

    $(".bkmk_save").click(function(e){
        var param = {};
        var sFavs = $(".ic_fav.on");
        for ( var i = 0; i < sFavs.length; i++ ) {
            if ( param.favorMenuCodes == undefined ) {
                param.favorMenuCodes = $(sFavs[i]).attr("id").replace("fav_", "");
            } else {
                param.favorMenuCodes += "," + $(sFavs[i]).attr("id").replace("fav_", "");
            }
        }
        var sFixes = $(".ic_fix.on");
        for ( var i = 0; i < sFixes.length; i++ ) {
            if ( param.fixMenuCodes == undefined ) {
                param.fixMenuCodes = $(sFixes[i]).attr("id").replace("fix_", "");
            } else {
                param.fixMenuCodes += "," + $(sFixes[i]).attr("id").replace("fix_", "");
            }
        }
        $("#_brmkLayer, #_brmkFullDimmed").hide();
        $.postJSONSave("/application/com/bkmk/save.sb", param, function(result) {
                var data = isEmpty(result.data[0]) ? '' : JSON.parse(result.data[0]);
                if ( data == '' ) {
                    $("#_bkmkTxt").show();
                } else {
                    $("#_bkmkTxt").hide();
                }
                bkmkMenu.itemsSource = data;
                var sFixes = $(".btn_close.favClose");
                for ( var i = 0; i < sFixes.length; i++ ) {
                    $(sFixes[i]).parents("li").remove();
                }
                var fixMenus = isEmpty(result.data[1]) ? '' : JSON.parse(result.data[1]);
                if (fixMenus) {
                    var fixMenu = "";
                    fixMenus.forEach(function(item) {
                        fixMenu = '<li id="' + item.resrceCd + '">';
                        fixMenu += '<a href="' + item.url + '" class="">'+ item.resrceNm +'</a>';
                        fixMenu += '<a href="javascript:void(0);" class="btn_close favClose" data-value="' + item.resrceCd + '" ></a>';
                        fixMenu += '</li>';
                        $("#_fixMenu").prepend(fixMenu);
                    });
                }
            },
            function(result){
                s_alert.pop(result.message);
            }).fail(function(){
            s_alert.pop("Ajax Fail");
        });
    });

    function makeMenu() {
        var menuCnt = 0;
        var menuHtml = "";
        var param = {};
        $.postJSON("/application/com/bkmk/list.sb", param, function(result) {
                var menuDatas = isEmpty(result.data[0]) ? '' : JSON.parse(result.data[0]);
                if ( menuDatas ) {
                    menuDatas.forEach(function(first) {
                        if ( first.pcd == '000000' ) {
                            if ( menuCnt == 0 ) {
                                menuHtml += '<div class="sort">';
                            } else if ( menuCnt % 4 == 0 ) {
                                menuHtml += '</div>';
                                menuHtml += '<div class="sort mt10">';
                            }
                            menuHtml += '<div><p class="depth1">';
                            menuHtml += first.nm;
                            menuHtml += '</p>';
                        }
                        if ( first.items.length > 0 ) {
                            first.items.forEach(function(second) {
                                if ( second ) {
                                    menuHtml += '<p class="depth2">' + second.nm + '</p><ul>';
                                    if ( second.items.length > 0 ) {
                                        second.items.forEach(function(third) {
                                            menuHtml += '<li><span class="txt">' + third.nm + '</span><span class="btn">';
                                            menuHtml += '<a href="javascript:void(0);" id="fav_' + third.cd + '" class="ic_fav off"></a>';
                                            menuHtml += '<a href="javascript:void(0);" id="fix_' + third.cd + '" class="ic_fix off"></a>';
                                            menuHtml += '</span></li>';
                                        })
                                        menuHtml += '</ul>';
                                    }
                                }
                            });
                        }
                        menuCnt++;
                        menuHtml += "</div>";
                        if ( menuCnt == menuDatas.length ) {
                            menuHtml += "</div>";
                        }
                    });
                    document.getElementById("bkmkMenu").innerHTML = menuHtml;
                }
                var bkmkMenus = isEmpty(result.data[1]) ? '' : JSON.parse(result.data[1]);
                if (bkmkMenus) {
                    bkmkMenus.forEach(function(first) {
                        if ( first && first.items.length > 0 ) {
                            first.items.forEach(function(second) {
                                if ( second && second.items.length > 0 ) {
                                    second.items.forEach(function(third) {
                                        if ( third ) {
                                            $("#fav_" + third.cd).removeClass("ic_fav off").addClass("ic_fav on");
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                var fixMenus = isEmpty(result.data[2]) ? '' : JSON.parse(result.data[2]);
                if (fixMenus) {
                    fixMenus.forEach(function(item) {
                        $("#fix_" + item.resrceCd).removeClass("ic_fix off").addClass("ic_fix on");
                    });
                }
                $(".ic_fav").click(function(event) {
                    if ( $(this).hasClass("ic_fav off") ) {
                        $(this).removeClass("ic_fav off").addClass("ic_fav on");
                    } else {
                        $(this).removeClass("ic_fav on").addClass("ic_fav off");
                    }
                });
                $(".ic_fix").click(function(event) {
                    if ( $(this).hasClass("ic_fix off") ) {
                        var fixedCnt = $(".ic_fix.on").length;
                        if ( fixedCnt >= 3 ) {
                            s_alert.pop("고정메뉴는 최대 3개까지 선택 가능합니다.");
                        } else {
                            $(this).removeClass("ic_fix off").addClass("ic_fix on");
                        }
                    } else {
                        $(this).removeClass("ic_fix on").addClass("ic_fix off");
                    }
                });
                $("#_brmkLayer, #_brmkFullDimmed").show();
            },
            function(result){
                s_alert.pop(result.message);
            }).fail(function(){
            s_alert.pop("Ajax Fail");
        });
    };

});
