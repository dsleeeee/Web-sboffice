<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <div id="selectMenuArea" class="wj-TblWrap mt20" ng-show="isMenuTab">
    <div class="w30 fl">
      <%--위즈모 테이블--%>
      <div id="gridPrint" class="wj-TblWrapBr mr10 pd20" style="height: 480px;" ng-controller="sideMenuSelectGroupCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
          <button class="btn_skyblue" id="btnAddGroup" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelGroup" style="display: none;" ng-click="delete()">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveGroup" style="display: none;" ng-click="save()">
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
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="100"></wj-flex-grid-column>
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
          <button class="btn_up" id="btnUpClass" style="display: none;" ng-click="rowMoveUp()">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownClass" style="display: none;" ng-click="rowMoveDown()">
            <s:message code="cmm.down" />
          </button>
          <button class="btn_skyblue" id="btnAddClass" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelClass" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveClass" style="display: none;" ng-click="save()">
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
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="*"></wj-flex-grid-column>

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
          <button class="btn_up" id="btnUpProd" style="display: none;" ng-click="rowMoveUp()">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownProd" style="display: none;" ng-click="rowMoveDown()">
            <s:message code="cmm.down" />
          </button>
          <button class="btn_skyblue" id="btnAddProd" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelProd" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveProd" style="display: none;" ng-click="save()">
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
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="100"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>

  <script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuSelectMenu.js?ver=20181114.01" charset="utf-8"></script>


