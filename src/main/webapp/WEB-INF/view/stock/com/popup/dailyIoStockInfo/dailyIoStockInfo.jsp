<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/status/dailyIoStockDtl/"/>

<wj-popup id="dailyIoStockInfoLayer" control="dailyIoStockInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="dailyIoStockInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      {{ioOccrNm}}
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
     <%-- 날짜, 체크박스 표 --%>
	<table class="searchTbl" style="text-align: center;">
		<colgroup>
			<col class="w50"/>
			<col class="w50"/>
		</colgroup>
		<tbody>
			<tr style="border-top: 1px solid #ccc;">
				<th style="text-align: center;"><s:message code="dailyIostock.ioOccrDt"/></th>
				<th></th>
			</tr>
			<tr>
                <td style="border-left: 1px solid #ccc;">{{ioOccrDt}}</td>
                <td>
                    <span class="chk ml10">
                        <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                            <label for="chkDt">
                            <s:message code="currUnity.isChk" />
                        </label>
                    </span>
                </td>
            </tr>
		</tbody>
	</table>

	<div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일 --%>
        <%-- <wj-combo-box
            class="w100px fl"
            id="dailyIoStockListScaleBox"
            ng-model="listScale"
            control="listScaleCombo"
            items-source="_getComboData('dailyIoStockListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
        </wj-combo-box> --%>

        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" />
        </button>
    </div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px;">
          <wj-flex-grid
            id="storeDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dailyIostock.slipNo"/>"        binding="col1"         width="100"     align="center" is-read-only="true"></wj-flex-grid-column>
            
          </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="dailyIoStockInfoCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/com/popup/dailyIoStockInfo/dailyIoStockInfo.js?ver=20190207.01" charset="utf-8"></script>
