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
    <div ID="_faMenu" class="faMenu" style="display:none;">
      <p class="btn_faManage"><a href="javascript:;">즐겨찾기 관리</a></p>
      <p id= "_bkmkTxt" class="txt">즐겨찾기한 메뉴가 없습니다.<br /><br />‘즐겨찾기 관리’ 버튼을 클릭하시면<br />즐겨찾기 메뉴와 고정메뉴를<br />설정할 수 있습니다.</p>
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

  <%-- 트리 생성--%>
  function makeTree(div, data, initMenu) {
    
    var tree = new wijmo.nav.TreeView(div, {
      displayMemberPath: 'nm',
      childItemsPath: 'items',
      autoCollapse: true,
      expandOnClick: false
    });
    
    <%-- 트리의 아이템이 load 완료 되었을 때 이벤트 --%>
    tree.loadedItems.addHandler(function(s, e) {
        <%-- 아이콘 Class 추가 --%>
        for (var node = s.getFirstNode(); node; node = node.nextSibling()) {
          if(!isEmpty(node)){
            wijmo.addClass(node.element, node.dataItem.icon);
          }
        }
        s.collapseToLevel(0);
        
        <%-- 초기 메뉴(현재 메뉴) 설정--%>
        if(initMenu) {
          for (var node = s.getFirstNode(); node; node = node.next()) {
            if(isEmpty(node.nodes)) {
              if(!isEmpty(node.dataItem) && node.dataItem.cd == initMenu) {
                s.selectedItem = node.dataItem;
              }
            }
          }
        }
    });

    <%-- 선택된 메뉴가 변경 되었을 때 이벤트 --%>
    tree.selectedItemChanged.addHandler(function(s, e) {
      <%-- 이전 메뉴의 클래스 제거--%>
      if(pNode) {
        for (var node = pNode; node; node = node.parentNode) {
          wijmo.removeClass(node.element, "on");
        }
      }
      <%-- 선택된 메뉴에 클래스 추가--%>
      for (var node = s.selectedNode; node; node = node.parentNode) {
        wijmo.addClass(node.element, "on");
      }
      pNode = s.selectedNode;
    });
    
    <%-- 아이템 클릭 시 이벤트 --%>
    tree.itemClicked.addHandler(function(s, e) {
      <%-- URL 이 있을 경우 페이지 이동 --%>
      if(!isEmpty(s.selectedNode.dataItem.url)) {
        location.href = s.selectedNode.dataItem.url;
      }
      <%-- 같은 메뉴를 다시 선택 했을 때 메뉴 닫기 기능 --%>
      if( pNode == s.selectedNode) {
        s.selectedNode.isCollapsed = !s.selectedNode.isCollapsed;
      }
      else {
        s.selectedNode.isCollapsed = false;
      }
    });

    <%-- Tree 생성자에서 데이터를 넣는 경우에는 이벤트 핸들러를 생성자에 넣을 수 있다.
    데이터를 생성자에서 넣으면서 이벤트를 나중에 선언하면 생성 시 이벤트 처리 안됨
    아래 처럼 이벤트를 다 선언한 후에 데이터를 넣어야 한다.
    --%>
    tree.itemsSource = data;

    return tree;
  }

  <%-- 데이터 파싱 --%>
  var menuStr = '${menuData}';
  var menus = isEmpty(menuStr) ? '' : JSON.parse(menuStr);
  var bkmkStr = '${bkmkData}';
  var bkmks = isEmpty(bkmkStr) ? '' : JSON.parse(bkmkStr);
  var pNode;
  
  <%-- 트리 생성 --%>
  var allMenu = makeTree('#_theTreeAll', menus, "${cMenu.getResrceCd()}");
  var bkmkMenu = makeTree('#_theTreeBkmk', bkmks);
  
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
    $("#_bkmkTxt").show();
    $("#_theTreeAll").hide();
    if(isEmpty(bkmks)) {
      $("#_faMenu").show();
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
