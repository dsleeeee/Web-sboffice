<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 선택된 메뉴와 부모 메뉴 --%>
<c:set var="cMenu" value="${sessionScope.sessionInfo.currentMenu}" />

<%-- 접힌 메뉴용 --%>
<c:set var="menuData" value="${sessionScope.sessionInfo.menuData}" />
<c:set var="bkmkData" value="${sessionScope.sessionInfo.bkmkData}" />


  <%--로고영역--%>
  <h1><a href="/main.sb" class="on">
  <span><img src="/resource/solbipos/css/img/logo_main.png" alt="" /></span></a></h1><%-- 활성화 : class="on" --%>
  <%--//로고영역--%>

  <%--전체,즐겨찾기--%>
  <div class="menuTab">
    <p class="all"><a id="_all" href="javascript:;" class="on"><span>전체</span></a></p><%-- 활성화 : class="on" --%>
    <p class="favorite"><a id="_favorite" href="javascript:;"><span>즐겨찾기</span></a></p>
  </div>
  <%--//전체,즐겨찾기--%>

  <div class="menuTree">

    <%--open : 즐겨찾기 메뉴--%>
    <div id="_faMenu" class="faMenu" style="display:none;">
      <p class="btn_faManage"><a href="javascript:void(0);">즐겨찾기 관리</a></p>
      <p id= "_bkmkTxt" class="txt" style="display:none;">즐겨찾기한 메뉴가 없습니다.<br /><br />‘즐겨찾기 관리’ 버튼을 클릭하시면<br />즐겨찾기 메뉴와 고정메뉴를<br />설정할 수 있습니다.</p>
    </div>
    <%--open : 즐겨찾기 메뉴--%>

    <%--위즈모 메뉴--%>
    <div id="_theTreeAll" class="theTreeAll"></div>
    <div id="_theTreeBkmk" class="theTreeAll" style="display:none;"></div>
    <%--//위즈모 메뉴--%>

    <%--접혔을때 : 클릭시 열린메뉴로 변경--%>
    <div class="smallMenu">
      <ul id="_smallMenuUl"></ul>
    </div>
    <%--//접혔을때--%>
  </div>

<script>
$(document).ready(function() {

  <%-- 데이터 파싱 --%>
  var menuStr = '${menuData}';
  var menus = isEmpty(menuStr) ? '' : JSON.parse(menuStr);
  var bkmkStr = '${bkmkData}';
  var bkmks = isEmpty(bkmkStr) ? '' : JSON.parse(bkmkStr);

  <%-- 트리 생성 --%>
  allMenu = makeTree('#_theTreeAll', menus, "${cMenu.getResrceCd()}");
  bkmkMenu = makeTree('#_theTreeBkmk', bkmks);

  <%-- 단축 메뉴 생성--%>
  $(menus).each(function(index) {
    $("#_smallMenuUl").append(wijmo.format('<li class="{icon}"><a href="javascript:;"></a></li>', this));
  });

  <%-- 전체메뉴 클릭--%>
  $(".menuTab .all").click(function() {
    $("#_all").addClass("on");
    $("#_favorite").removeClass("on");
    $("#_bkmkTxt, #_faMenu").hide();
    $("#_theTreeAll").show();
    $("#_theTreeBkmk").hide();
  });

  <%-- 즐겨찾기 메뉴 클릭--%>
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

  <%-- 접힌 메뉴 클릭 시 열린 메뉴 오픈--%>
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

  <%-- 접힌 메뉴(즐겨찾기) 클릭 시 열린 메뉴 오픈--%>
  $(document).on("click", "#_favorite", function(){
    if($("#_nav").attr("class")=="menuClose"){
      $(".menuControl").trigger("click");
      $(".menuTab .favorite").trigger("click");
    }
  });

});
</script>
