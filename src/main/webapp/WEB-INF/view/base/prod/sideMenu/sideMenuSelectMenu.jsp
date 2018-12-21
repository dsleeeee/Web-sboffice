<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <div id="selectMenuArea" class="wj-TblWrap mt20 ng-cloak" ng-hide="isMenuTab">
    <div class="w30 fl">
      <%--위즈모 테이블--%>
      <div id="gridPrint" class="wj-TblWrapBr mr10 pd20" style="height: 480px;" ng-controller="sideMenuSelectGroupCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
          <button class="btn_skyblue" id="btnAddSelGroup" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelSelGroup" style="display: none;" ng-click="deleteRow()">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveSelGroup" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:400px">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="*"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <div class="w30 fl">
      <%--위즈모 테이블--%>
      <div id="gridMapng" class="wj-TblWrapBr ml10 pd20" style="height: 480px;" ng-controller="sideMenuSelectClassCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselClass' /></span>
          <button class="btn_up" id="btnUpSelClass" style="display: none;" ng-click="rowMoveUp()">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownSelClass" style="display: none;" ng-click="rowMoveDown()">
            <s:message code="cmm.down" />
          </button>
          <button class="btn_skyblue" id="btnAddSelClass" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelSelClass" style="display: none;" ng-click="deleteRow()">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveSelClass" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="sdselQty" width="50"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <div class="w40 fl">
      <%--위즈모 테이블--%>
      <div id="gridMapng" class="wj-TblWrapBr ml10 pd20" style="height: 480px;" ng-controller="sideMenuSelectProdCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselProd' /></span>
          <button class="btn_up" id="btnUpSelProd" style="display: none;" ng-click="rowMoveUp()">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownSelProd" style="display: none;" ng-click="rowMoveDown()">
            <s:message code="cmm.down" />
          </button>
          <button class="btn_skyblue" id="btnAddSelProd" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelSelProd" style="display: none;" ng-click="deleteRow()">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveSelProd" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="50"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdQty"/>" binding="addProdQty" width="50"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuSelectMenu.js?ver=20181221.02" charset="utf-8"></script>

  <%-- 레이어 팝업 : 상품선택 --%>
  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuProdView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>
