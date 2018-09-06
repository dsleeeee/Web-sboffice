<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/bill/kind/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="javascript:void(0);" class="open">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb10 oh">
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('printCtrl')"><s:message code="cmm.search" /></button>
  </div>

  <div class="wj-TblWrap">
    <div class="w50 fl" style="width: 40%">
      <%--위즈모 테이블--%>
      <div id="gridPrint" class="wj-TblWrapBr mr10 pd20" style="height: 490px;" ng-controller="printCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='kind.gridNm' /></span>
          <button class="btn_skyblue" id="btnAddPrint" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelPrint" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSavePrint" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:405px">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="kind.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kind.prtClassCd"/>" binding="prtClassCd" width="70"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kind.prtClassNm"/>" binding="prtClassNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kind.generalYn"/>" binding="generalYn" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kind.foodYn"/>" binding="foodYn" format="checkBoxText" width="40"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="printCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <div class="w50 fl" style="width: 60%">
      <%--위즈모 테이블--%>
      <div id="gridMapng" class="wj-TblWrapBr ml10 pd20" style="height: 490px;" ng-controller="mapngCtrl">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='kind.gridMapngNm' /></span>
          <button class="btn_up" id="btnUpMapng" style="display: none;" ng-click="rowMoveUp()">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownMapng" style="display: none;" ng-click="rowMoveDown()">
            <s:message code="cmm.down" />
          </button>
          <button class="btn_skyblue" id="btnAddMapng" style="display: none;" ng-click="addRow()">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelMapng" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSaveMapng" style="display: none;" ng-click="save()">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:405px;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="kind.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kind.prtCd"/>" binding="prtCd" width="*"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <input type="hidden" id="prtClassCd" />
  </div>
  <!-- -->
  <script type="text/javascript" src="/resource/solbipos/js/sys/bill/kind/kind.js?ver=2018090301" charset="utf-8"></script>

  <%-- 레이어 팝업 --%>
  <c:import url="/WEB-INF/view/sys/bill/kind/popUpKind.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>

</div>

