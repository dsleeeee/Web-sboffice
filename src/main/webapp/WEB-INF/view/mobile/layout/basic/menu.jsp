<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<c:set var="sessionId" value="${param.sid}" />

  <%--로고영역--%>
  <%--메인페이지 좌측 메뉴리스트 상단 SOLBIPOS 로고--%>
  <%--가상로그인 후 로고 클릭시 세션없어짐 2021.02.16 김설아--%>
  <h1><a href="/mobile/main.sb?sid=${sessionId}" class="on">
  <span><img src="/resource/solbipos/css/img/logo_main.png" alt="" /></span></a></h1><%-- 활성화 : class="on" --%>
  <%--//로고영역--%>

  <%--전체,즐겨찾기--%>
  <%--<div class="menuTab">
    <p class="all"><a id="_all" href="#" class="on"><span>전체</span></a></p>&lt;%&ndash; 활성화 : class="on" &ndash;%&gt;
    <p class="favorite"><a id="_favorite" href="#"><span>즐겨찾기</span></a></p>
  </div>--%>
  <%--//전체,즐겨찾기--%>

  <div class="menuTree">

    <%--open : 즐겨찾기 메뉴--%>
    <%--<div id="_faMenu" class="faMenu" style="display:none;">
      <p class="btn_faManage"><a href="#">즐겨찾기 관리</a></p>
      <p id= "_bkmkTxt" class="txt" style="display:none;">즐겨찾기한 메뉴가 없습니다.<br /><br />‘즐겨찾기 관리’ 버튼을 클릭하시면<br />즐겨찾기 메뉴와 고정메뉴를<br />설정할 수 있습니다.</p>
    </div>--%>
    <%--open : 즐겨찾기 메뉴--%>

    <%--위즈모 메뉴--%>
    <div id="_theTreeAll" ng-controller="menuCtrl" style="height: 100%; overflow-y: auto; overflow-x: hidden;">
      <wj-tree-view control="flex"
        class="theTreeAll"
        items-source="items"
        display-member-path="'resrceNm'"
        child-items-path="'children'"
        is-animated="false"
        auto-collapse="true"
        expand-on-click="false"
        initialized="initTreeView(s,e)"
        selection-mode="ListBox"
        selected-item-changed="selectedItemChanged(s,e)"
        item-clicked="itemClicked(s,e)"
        loaded-items="loadedItems(s,e)">
      </wj-tree-view>
    </div>

    <%--<div id="_theTreeBkmk" style="display:none;" ng-controller="bkmkCtrl" style="height: 100%; overflow-y: auto; overflow-x: hidden;">
      <wj-tree-view control="flex"
        class="theTreeAll"
        items-source="items"
        display-member-path="'resrceNm'"
        child-items-path="'children'"
        is-animated="false"
        auto-collapse="true"
        expand-on-click="false"
        initialized="initTreeView(s,e)"
        selection-mode="ListBox"
        selected-item-changed="selectedItemChanged(s,e)"
        item-clicked="itemClicked(s,e)"
        loaded-items="loadedItems(s,e)">
      </wj-tree-view>
    </div>--%>
    <%--//위즈모 메뉴--%>

    <%--접혔을때 : 클릭시 열린메뉴로 변경--%>
    <%--<div class="smallMenu">
      <ul id="_smallMenuUl"></ul>
    </div>--%>
    <%--//접혔을때--%>
  </div>

<script type="text/javascript" src="/resource/solbipos/js/mobile/layout/basic/menu.js?ver=2020080701" charset="utf-8"></script>
