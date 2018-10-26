/****************************************************************
 *
 * 파일명 : bkmk.js
 * 설  명 : 즐겨찾기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

  $(".btn_faManage").click(function (e) {
    makeMenu();
  });

  $(".bkmk_close").click(function (e) {
    $("#_brmkLayer, #_brmkFullDimmed").hide();
  });

  $(".bkmk_save").click(function (e) {
    var param = {};
    var sFavs = $(".ic_fav.on");
    for (var i = 0; i < sFavs.length; i++) {
      if (param.favorMenuCodes === undefined) {
        param.favorMenuCodes = $(sFavs[i]).attr("id").replace("fav_", "");
      } else {
        param.favorMenuCodes += "," + $(sFavs[i]).attr("id").replace("fav_", "");
      }
    }
    var sFixes = $(".ic_fix.on");
    for (var i = 0; i < sFixes.length; i++) {
      if (param.fixMenuCodes === undefined) {
        param.fixMenuCodes = $(sFixes[i]).attr("id").replace("fix_", "");
      } else {
        param.fixMenuCodes += "," + $(sFixes[i]).attr("id").replace("fix_", "");
      }
    }
    $("#_brmkLayer, #_brmkFullDimmed").hide();
    $.postJSONSave("/application/com/bkmk/save.sb", param, function (result) {
        var scope = agrid.getScope("bkmkCtrl");
        // 저장 후 팝업메시지
        scope.$apply(function(){
          scope._popMsg(messages["cmm.saveSucc"], function () {
            // 즐겨찾기메뉴 재조회
            scope._searchTree("/menu/bkmkList.sb", function() {
              var data = isEmpty(result.data[0]) ? '' : JSON.parse(result.data[0]);
              if (data.length < 1) {
                $("#_bkmkTxt").show();
              } else {
                $("#_bkmkTxt").hide();
              }

              var sFixes = $(".btn_close.favClose");
              for (var i = 0; i < sFixes.length; i++) {
                $(sFixes[i]).parents("li").remove();
              }
              var fixMenus = isEmpty(result.data[1]) ? '' : JSON.parse(result.data[1]);
              if (fixMenus) {
                var fixMenu = "";
                fixMenus.forEach(function (item) {
                  fixMenu = '<li id="' + item.resrceCd + '">';
                  fixMenu += '<a href="' + item.url + '" class="">' + item.resrceNm + '</a>';
                  fixMenu += '<a href="#" class="btn_close favClose" data-value="' + item.resrceCd + '" ></a>';
                  fixMenu += '</li>';
                  $("#_fixMenu").prepend(fixMenu);
                });
              }
            });
          });
        });
      },
      function (result) {
        var scope = agrid.getScope("bkmkCtrl");
        scope.$apply(function(){
          scope._popMsg(result.message);
        });
        return false;
      });
  });

  //트리 변환 메서드
  function convertTreeModel(arrayList, rootId) {
    var rootNodes = [];
    var traverse = function (nodes, item, index) {
      if (nodes instanceof Array) {
        return nodes.some(function (node) {
          if (node.resrceCd === item.pResrce) {
            node.children = node.children || [];
            return node.children.push(arrayList.splice(index, 1)[0]);
          }

          return traverse(node.children, item, index);
        });
      }
    };
    while (arrayList.length > 0) {
      arrayList.some(function (item, index) {
        if (item.pResrce === rootId) {
          return rootNodes.push(arrayList.splice(index, 1)[0]);
        }
        return traverse(rootNodes, item, index);
      });
    }

    return rootNodes;
  }
  // 즐겨찾기 관리 메뉴 생성
  function makeMenu() {
    var menuCnt = 0;
    var menuHtml = "";
    var param = {};
    $.postJSON("/application/com/bkmk/list.sb", param, function (result) {
      // 전체메뉴
      if ( result.data.menuData ) {
        var menus = JSON.stringify(convertTreeModel(result.data.menuData, "000000"), null, '');
        var menuDatas = JSON.parse(menus);

        if (menuDatas) {
          var first;
          for (var mf = 0; mf < menuDatas.length; mf++) {
            first = menuDatas[mf];
            if (first.pResrce === '000000') {
              if (menuCnt === 0) {
                menuHtml += '<div class="sort">';
              } else if (menuCnt % 4 === 0) {
                menuHtml += '</div>';
                menuHtml += '<div class="sort mt10">';
              }
              menuHtml += '<div><p class="depth1">';
              menuHtml += first.resrceNm;
              menuHtml += '</p>';
            }

            if (first.children && first.children.length > 0) {
              var second;
              for (var ms = 0; ms < first.children.length; ms++) {
                second = first.children[ms];
                if (second) {
                  if (second.url) {
                    menuHtml += '<p class="depth2">' + second.resrceNm + '</p>';
                  } else {
                    menuHtml += '<p class="depth2">' + second.resrceNm + '</p>';
                  }
                  if (second.children && second.children.length > 0) {
                    menuHtml += '<ul>';
                    var third;
                    for (var mt = 0; mt < second.children.length; mt++) {
                      third = second.children[mt];
                      menuHtml += '<li><span class="txt">' + third.resrceNm + '</span><span class="btn">';
                      menuHtml += '<a href="#" id="fav_' + third.resrceCd + '" class="ic_fav off"></a>';
                      menuHtml += '<a href="#" id="fix_' + third.resrceCd + '" class="ic_fix off"></a>';
                      menuHtml += '</span></li>';
                    }
                    menuHtml += '</ul>';
                  }
                }
              }
            }
            menuCnt++;
            menuHtml += "</div>";
            if (menuCnt === menuDatas.length) {
              menuHtml += "</div>";
            }
          }
          document.getElementById("bkmkMenu").innerHTML = menuHtml;
        }
      }
      // 즐겨찾기
      if ( result.data.bkmkData ) {
        var bkmkData = JSON.stringify(convertTreeModel(result.data.bkmkData, "000000"), null, '');
        var bkmkMenus = JSON.parse(bkmkData);

        if (bkmkMenus) {
          var first;
          for (var bf = 0; bf < bkmkMenus.length; bf++) {
            first = bkmkMenus[bf];
            if (first && first.children.length > 0) {
              var second;
              for (var bs = 0; bs < first.children.length; bs++) {
                second = first.children[bs];
                if (second && second.children.length > 0) {
                  var third;
                  for (var bt = 0; bt < first.children.length; bt++) {
                    third = second.children[bt];
                    if (third) {
                      $("#fav_" + third.resrceCd).removeClass("ic_fav off").addClass("ic_fav on");
                    }
                  }
                }
              }
            }
          }
        }
      }
      // 고정메뉴 
      if ( result.data.fixData ) {
        var fixMenus = result.data.fixData;
        if (fixMenus) {
          for (var f = 0; f < fixMenus.length; f++) {
            $("#fix_" + fixMenus[f].resrceCd).removeClass("ic_fix off").addClass("ic_fix on");
          }
        }
      }

      $(".ic_fav").click(function (event) {
        if ($(this).hasClass("ic_fav off")) {
          $(this).removeClass("ic_fav off").addClass("ic_fav on");
        } else {
          $(this).removeClass("ic_fav on").addClass("ic_fav off");
        }
      });
      $(".ic_fix").click(function (event) {
        if ($(this).hasClass("ic_fix off")) {
          var fixedCnt = $(".ic_fix.on").length;
          if (fixedCnt > 3) {
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
    function (result) {
      s_alert.pop(result.message);
    });
  }

});
