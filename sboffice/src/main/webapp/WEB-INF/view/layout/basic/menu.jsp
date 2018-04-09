<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 선택된 메뉴와 부모 메뉴 --%>
<c:set var="cMenu" value="${sessionScope.sessionInfo.currentMenu}" />

<%-- 접힌 메뉴용 --%>
<c:set var="menuData" value="${sessionScope.sessionInfo.menuData}" />
<c:set var="bkmkData" value="${sessionScope.sessionInfo.bkmkData}" />

<%-- wijmo 메뉴 용 --%>
<c:set var="madeMenuData" value="${sessionScope.sessionInfo.madeMenuData}" />
<c:set var="madeBkmkData" value="${sessionScope.sessionInfo.madeBkmkData}" />

<%-- 로고영역 --%>
<h1><a href="/main.sb"><span><img src="/resource/solbipos/css/img/logo_main.png" alt="" /></span></a></h1>

<%-- 전체,즐겨찾기 --%>
<div class="menuTab">
    <p class="all"><a href="#" id="_all" class="on"><span>전체</span></a></p>
    <p class="favorite"><a href="#" id="_favorite"><span>즐겨찾기</span></a></p>
</div>

<div class="menuTree">
    <!--open : 즐겨찾기 메뉴 -->
    <div class="faMenu" id="faMenu" style="display:none;">
        <p class="btn_faManage"><a href="#">즐겨찾기 관리</a></p>
        <p class="txt">즐겨찾기한 메뉴가 없습니다.<br /><br />‘즐겨찾기 관리’ 버튼을 클릭하시면<br />즐겨찾기 메뉴와 고정메뉴를<br />설정할 수 있습니다.</p>
    </div>
    <!--open : 즐겨찾기 메뉴-->

    <!--위즈모 메뉴-->
    <!-- <div> -->  <!-- 해당 div 태그 없애면 왼쪽 메뉴 스크롤 가능 -->
        <div id="theTreeAll" style="display:block;"></div>
        <div id="theTreeBkmk" style="display:none;"></div>
    <!-- </div> -->
    <!--//위즈모 메뉴-->
    
    <!--접혔을때 : 클릭시 열린메뉴로 변경-->
    <div class="smallMenu" id="smallMenu">
        <ul>
          <c:forEach var="item" items="${menuData}">
            <li class="${item.iconNm}"><a href="#"></a></li>
          </c:forEach>
        </ul>    
    </div>
    <!--//접혔을때-->
</div>    
  
<script type="text/javascript">

onload = function() {
  <%-- 선택된 메뉴--%>
  var cResrce = "${cMenu.getResrceCd()}";
  var pResrce = "${cMenu.getPResrce()}";

  // 테스트용
  //var cResrce = "000173";
  //var pResrce = "000030";

  <%-- 현재 선택된 메뉴 seq 저장--%>
  var sel1Depth = 0;
  var sel2Depth = 0;
  var sel3Depth = 0;
  
  <%-- 현재 열려있는 메뉴 저장--%>
  var openMenu = "";
  var openBMenu = "";
  
  <%-- 전체 메뉴 생성 --%>
  var tree = new wijmo.nav.TreeView('#theTreeAll', {
    itemsSource: getAllMenu(),
    displayMemberPath: 'header',
    childItemsPath: 'items',
    loadedItems: function(s, e) {
      s.collapseToLevel(0);
    },

    itemClicked: function(s, e) {
      if(wijmo.format('{items}', s.selectedItem)) {
        <%-- 1depth 초기화 후 class on --%>
        if(wijmo.format('{level1Seq}', s.selectedItem)){
          if((wijmo.format('{level1Seq}', s.selectedItem) == sel1Depth) && (wijmo.format('{level1Seq}', s.selectedItem) == openMenu)){
            tree.collapseToLevel(2);
            tree.collapseToLevel(1);
            tree.collapseToLevel(0);
            openMenu = "";
          }else {
            openMenu = wijmo.format('{level1Seq}', s.selectedItem);
          }
          sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
          $("#theTreeAll div[wj-part=root] > .wj-node").each(function(i,e){ $(this).removeClass("on");  });
          $("#theTreeAll div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
        }
        <%-- 2depth 초기화 후 class on  --%>
        if(wijmo.format('{level2Seq}', s.selectedItem)){
          sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
          $("#theTreeAll div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){ $(this).removeClass("on");  });
          $("#theTreeAll div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on");
        }
      }
      <%-- 3depth 초기화 후 class on  --%>
      if(wijmo.format('{url}', s.selectedItem) != "") {
        sel3Depth = wijmo.format('{level3Seq}', s.selectedItem);
        $("#theTreeAll div[wj-part=root] > .wj-nodelist .wj-nodelist").children('.wj-node').each(function(i, e){  $(this).removeClass("wj-state-selected"); });
        $("#theTreeAll div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
        location.href = wijmo.format('{url}', s.selectedItem);
      }
    }
  });
  
  <%-- 즐겨찾기 메뉴 생성 --%>
  var bkmkMenuTree = new wijmo.nav.TreeView('#theTreeBkmk', {
    itemsSource: getbmkMenu(),
    displayMemberPath: 'header',
    childItemsPath: 'items',
    loadedItems: function(s, e) {
      s.collapseToLevel(0);
    },
    itemClicked: function(s, e){
      if(wijmo.format('{items}', s.selectedItem)) { 
        if(wijmo.format('{level1Seq}', s.selectedItem)){
          if((wijmo.format('{level1Seq}', s.selectedItem) == sel1Depth) && (wijmo.format('{level1Seq}', s.selectedItem) == openBMenu)){
            bkmkMenuTree.collapseToLevel(2);
            bkmkMenuTree.collapseToLevel(1);
            bkmkMenuTree.collapseToLevel(0);
            openBMenu = "";
          }else {
            openBMenu = wijmo.format('{level1Seq}', s.selectedItem);
          }
          sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
          $("#theTreeBkmk div[wj-part=root] > .wj-node").each(function(i,e){ $(this).removeClass("on");  });
          $("#theTreeBkmk div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
        }
        if(wijmo.format('{level2Seq}', s.selectedItem)){
          sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
          $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){ $(this).removeClass("on"); });
          $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on")
        }
      }
      if(wijmo.format('{url}', s.selectedItem) != "") {
        sel3Depth = wijmo.format('{level3Seq}', s.selectedItem);
        $("#theTreeBkmk div[wj-part=root] > .wj-nodelist .wj-nodelist").children('.wj-node').each(function(i, e){ $(this).removeClass("wj-state-selected"); });
        $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
        location.href = wijmo.format('{url}', s.selectedItem);
      }
    } 
  });

  <%-- 메뉴 데이터 조회 --%>
  function getAllMenu(){
    var data = ${madeMenuData};
    return data;
  }
  
  <%-- 즐겨찾기 문구--%>
  function getbmkMenu(){
    var data = ${madeBkmkData};
    return data;
  }

  <%-- 즐겨찾기  --%>
  if(getbmkMenu().length > 0) {
    $("#faMenu .txt").hide();
  }else{
    $("#faMenu .txt").show();
  }

  <%-- 메뉴 생성 후, 메뉴 아이콘 셋팅--%>
  setMenuIcon(getAllMenu(), "theTreeAll");
  setMenuIcon(getbmkMenu(), "theTreeBkmk");

  function setMenuIcon(data, tree){
    var iconArr = new Array(Object.keys(data).length);
    $.each(data, function(key, entry){
      if(data[key].iconNm != "" && data[key].iconNm != "null") {
        iconArr[key] = data[key].iconNm;
      }
    });
    $("#"+ tree +" div[wj-part=root] > .wj-node").each(function(i){
      $(this).addClass(iconArr[i]);
    });
  }
  
  <%-- 현재 선택된 메뉴 표시--%>
  initMenu("theTreeAll");
  initMenu("theTreeBkmk");
  
  function initMenu(tree){
    if(pResrce != "" && cResrce !=""){
      var stat = false;
      var items = (tree == "theTreeAll"? getAllMenu():getbmkMenu());
      
      var pCnt = 0; <%-- 1depth의 index 알아내기 위한 변수--%>
      for(var i=0; i<items.length; i++){
        var item = items[i];
        pCnt = item.level1Seq;
        if(item.items){
          for(var j=0; j<item.items.length; j++){
            var item2 = item.items[j];
            if(item2.resrceCd == cResrce){ <%-- 2depth가 마지막 depth일때--%>
              sel1Depth = i;
              sel2Depth = j;
              sel3Depth = j;
              stat = true;
            }
            if(item2.resrceCd == pResrce){ <%-- 3depth가 마지막 depth일때--%>
              sel1Depth = i;
              sel2Depth = j;
              for(var k=0; k<item2.items.length; k++){
                var item3 = item2.items[k];
                if(item3.resrceCd == cResrce){
                  sel3Depth = k;
                  stat = true;
                }
              }
            }
          }
        }
      }
      if(stat){
        $("#"+ tree +" div[wj-part=root] > .wj-node").eq(sel1Depth).click();
        $("#"+ tree +" div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).click();
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on");
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
      }
    }
  }
  
  <%-- 전체메뉴 클릭--%>
  $(".menuTab .all").click(function() {
    $("#_all").addClass("on");
    $("#_favorite").removeClass();
    $("#faMenu").hide();
    $("#theTreeAll").show();
    $("#theTreeBkmk").hide();
  });
  
  <%-- 즐겨찾기 메뉴 클릭--%>
  $(".menuTab .favorite").click(function() {
    $("#_all").removeClass();
    $("#_favorite").addClass("on");
    $("#faMenu").show();
    $("#theTreeAll").hide();
    $("#theTreeBkmk").show();
  });

  <%-- 접힌 메뉴 클릭 시 열린 메뉴 오픈--%>
  $(document).on("click", ".smallMenu li", function(){
    $(".menuControl").trigger("click");
    var findClass = $(this).attr("class");
    if(findClass != null) {
      $("#theTreeAll div.wj-node." + findClass).trigger("click");
      $.each(tree.nodes, function(idx){
        var item = tree.nodes[idx].dataItem;
        if(findClass.indexOf(item.iconNm) >= 0) {
          tree.selectedItem = item;
        }
      });
    }
  });
  
  <%-- 접힌 메뉴(즐겨찾기) 클릭 시 열린 메뉴 오픈--%>
  $(document).on("click", "#_favorite", function(){
    if($("#_nav").attr("class")=="menuClose"){
      $(".menuControl").trigger("click");
      $("#_favorite").trigger("click");
    }
  });
}
</script>
