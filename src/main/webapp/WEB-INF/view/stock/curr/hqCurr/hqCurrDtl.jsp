<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/curr/hqCurr/"/>

<wj-popup id="hqCurrDtlLayer" control="hqCurrDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqCurrDtlCtrl">
    <div id="spanPopDtlTitle" class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqCurr.hqCurrDtl"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
      <table class="tblType01 mt10 tc">
        <colgroup>
          <col class="w15"/>
          <col class="w20"/>
          <col class="w20"/>
          <col class="w20"/>
          <col class="w25"/>
        </colgroup>
        <tbody>
        	<tr>
          		<th class="tc"><s:message code="periodIostock.prodCd"/></th>
          		<th class="tc"><s:message code="periodIostock.prodNm"/></th>
        	</tr>
        	<tr>
          		<td id="prodCd" style="border-left: 1px solid #ccc;">{{prodCd}}</td>
          		<td id="prodNm" style="border-left: 1px solid #ccc;">{{prodNm}}</td>
	        </tr>
        </tbody>
      </table>
      
      <input type="hidden" id="hqOfficeCode" value="${sessionInfo.hqOfficeCd}"/>

      <div class="tr mt20 fr">
        <div id="volmErrBtnLayer" style="display: none;">
          <span class="chk txtIn fl lh30" style="top: -2px;" ng-if="volmErrConfirmFg">
            <input type="checkbox" name="volmErrConfirmFg" id="volmErrConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="volmErrConfirmFg"><s:message code="volmErr.dtl.confirm"/></label>
          </span>
          <%-- 출고일자 --%>
        <%--<div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">--%>
       		<div id="divDtlOutDate2" class="sb-select ml10 fl">
            <p class="s12 fl mr5 lh30"><s:message code="volmErr.dtl.outDate"/> : </p>
            <span class="txtIn"><input id="dtlOutDate2" class="w120px"></span>
          </div>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave">
            <s:message code="cmm.save"/></button>
        </div>
        
        <%-- 엑셀 다운로드 --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 200px; overflow-y: hidden; overflow-x: hidden" >
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="hqCurr.prodCd"/>"			binding="prodCd"		width="130"		align="center"	is-read-only="true"></wj-flex-grid-column>
            <c:if test="${storageEnvstVal == '1'}">
              <wj-flex-grid-column header="<s:message code="hqCurr.storageCd"/>"		binding="storageCd"		width="130"		align="center"	is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="hqCurr.storageNm"/>"		binding="storageNm"		width="*"		align="center"	is-read-only="true"></wj-flex-grid-column>
            </c:if>
            <wj-flex-grid-column header="<s:message code="hqCurr.currQty"/>"			binding="currQty"		width="120"		align="center"	is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="hqCurrDtlCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  // [1241 창고사용여부] 환경설정값
  var storageEnvstVal = "${storageEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/stock/curr/hqCurr/hqCurrDtl.js?ver=20200413.01" charset="utf-8"></script>
