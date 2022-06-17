<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="deliveryTelNoCtrl">
	<div class="searchBar flddUnfld">
		<a href="#" class="open fl">${menuNm}</a>
    	<%-- 조회 --%>
    	<button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('deliveryTelNoCtrl')">
    		<s:message code="cmm.search"/>
    	</button>
	</div>
    <%-- 조회조건 --%>
    <table class="searchTbl">
		<colgroup>
			<col class="w15"/>
			<col class="w35"/>
			<col class="w15"/>
			<col class="w35"/>
      	</colgroup>
      	<tbody>
       	<%-- 조회일자 --%>
		<tr>
	    	<th><s:message code="cmm.search.date" /></th>
        	<td colspan="3">
				<div class="sb-select fl" >
					<span class="txtIn"><input id="srchStartDate" class="w110px"></span>
					<span class="rg">~</span>
					<span class="txtIn"><input id="srchEndDate" class="w110px"></span>
					<span class="chk ml10">
						<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
						<label for="chkDt">
							<s:message code="cmm.all.day" />
						</label>
					</span>
				</div>
        	</td>
        </tr>
		<tr>
			<th>
				<s:message code="deliveryTelNo.telNo" />
			</th>
			<td>
				<input type="text" class="sb-input w100" id="srchCidTelNo" ng-model="cidTelNo" onkeyup="fnNxBtnSearch();"/>
			</td>
			<th>
				<s:message code="deliveryTelNo.dlvrAddr" />
			</th>
			<td>
				<input type="text" class="sb-input w100" id="srchDlvrAddr" ng-model="dlvrAddr" onkeyup="fnNxBtnSearch();"/>
			</td>
		</tr>
		<tr>
			<th>
				<s:message code="deliveryTelNo.dlvrMemo" />
			</th>
			<td>
				<input type="text" class="sb-input w100" id="srchDlvrMemo" ng-model="dlvrMemo" onkeyup="fnNxBtnSearch();"/>
			</td>
			<th></th>
			<td></td>
		</tr>
		</tbody>
	</table>
	<div class="mt20 oh sb-select dkbr">
	    <%-- 페이지 스케일  --%>
		<wj-combo-box
				class="w100px fl"
				id="listScaleBox"
				ng-model="listScale"
				items-source="_getComboData('listScaleBox')"
				display-member-path="name"
				selected-value-path="value"
				is-editable="false"
				initialized="initComboBox(s)">
		</wj-combo-box>

		<%-- 저장 --%>
		<button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.save" /></button>
		<%-- 선택삭제 --%>
		<button class="btn_skyblue ml5 fr" ng-click="del()"><s:message code="cmm.chk" /><s:message code="cmm.del" /></button>
		<%-- 전체삭제 --%>
		<button class="btn_skyblue ml5 fr" ng-click="delAll()"><s:message code="cmm.all" /><s:message code="cmm.del" /></button>
	</div>

	<%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
      <div class="wj-gridWrap">
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
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.saleDate"/>" 		binding="saleDate"		width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.cidCallSeq"/>"	binding="cidCallSeq"	width="60"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.cidCallDt"/>"		binding="cidCallDt"		width="120"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.cidLineNo"/>"		binding="cidLineNo" 	width="90" align="center"  is-read-only="true" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.cidTelNo"/>" 		binding="cidTelNo" 		width="100" align="left"></wj-flex-grid-column>
		  <wj-flex-grid-column header="<s:message code="deliveryTelNo.dlvrAddr"/>" 		binding="dlvrAddr"		width="200"  align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.dlvrAddrDtl"/>"	binding="dlvrAddrDtl"	width="85"  align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.orderNo"/>"		binding="orderNo"		width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.dlvrFg"/>"		binding="dlvrFg" 		data-map="dlvrFgDataMap" width="80" align="center"  is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryTelNo.dlvrMemo"/>" 		binding="dlvrMemo" 		width="100" align="left" ></wj-flex-grid-column>
        </wj-flex-grid>

        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="deliveryTelNoCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
    </div>
    <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="deliveryTelNoCtrlPager" data-size="10">
    </ul>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/deliveryTelNo/deliveryTelNo.js?ver=20220616.01" charset="utf-8"></script>

