<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/pos/confg/func/func/" />

<div class="subCon">
  <div class="wj-TblWrap">
    <%-- 기능구분 --%>
    <div class="w25 fl" style="" ng-controller="funcFgCtrl">
      <div class="wj-TblWrapBr mr10 pd20" >
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='func.funFg' /></span>
          <button class="btn_skyblue" id="btnDefaultFunc" ng-click="defaultFunc()">
            <s:message code="func.defaultFunc" />
          </button>
          <button class="btn_skyblue" id="btnBatchStore" ng-click="batchStore()">
            <s:message code="func.batch" />
          </button>
        </div>

        <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
                  control="flex"
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  initialized="initGrid(s,e)"
                  items-source="data"
                  item-formatter="_itemFormatter"
                  ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="func.funFgCd"/>" binding="nmcodeCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="func.funFg"/>" binding="nmcodeNm" width="120" width="*" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

      </div>
    </div>

    <%-- 기능 --%>
    <div class="w75 fr" ng-controller="funcCtrl">
      <div class="wj-TblWrapBr ml10 pd20" style="overflow-y: hidden; overflow-x: hidden;">

        <%-- 등록 수정등은 시스템 계정에서만 가능 --%>
        <c:if test="${orgnFg == 'MASTER'}">

          <div class="updownSet oh mb10">
            <span class="fl bk lh30">기능 <span id="funcName"></span></span>
            <%--버튼 --%>
            <button class="btn_up" id="btnUp" style="display: none;" ng-click="up()">
              <s:message code="cmm.up" />
            </button>
            <button class="btn_down" id="btnDown" style="display: none;" ng-click="down()">
              <s:message code="cmm.down" />
            </button>
            <button class="btn_skyblue" id="btnAdd" style="display: none;" ng-click="add()">
              <s:message code="cmm.add" />
            </button>
            <button class="btn_skyblue" id="btnDel" style="display: none;" ng-click="del()">
              <s:message code="cmm.delete" />
            </button>
            <button class="btn_skyblue" id="btnSave" style="display: none;" ng-click="save()">
              <s:message code="cmm.save" />
            </button>
          </div>

        </c:if>

        <%-- 기능구분 상세 --%>
        <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
                  control="flex"
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  initialized="initGrid(s,e)"
                  items-source="data"
                  item-formatter="_itemFormatter">

          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
  var funcFgList  = ${fnkeyFgList};
  var storeKind   = ${ccu.getCommCodeExcpAll("057")};
  var posFg       = ${ccu.getCommCodeExcpAll("027")};
  var useYn       = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/func.js?ver=20200518.08" charset="utf-8"></script>


<%-- 매장선택 --%>
<c:import url="/WEB-INF/view/pos/confg/func/store.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="orgnFg" value="${orgnFg}"/>
  <c:param name="baseUrl" value="${baseUrl}"/>
</c:import>

<%-- 기본기능적용 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/defaultFunc.jsp">
</c:import>

<%-- 일괄기능적용 매장리스트 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/batchStore.jsp">
</c:import>

<%-- 일괄기능적용 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/batchFunc.jsp">
</c:import>


