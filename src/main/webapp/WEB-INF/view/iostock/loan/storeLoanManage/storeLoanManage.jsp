<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanManage/storeLoanManage/"/>

<div class="subCon" ng-controller="storeLoanManageCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('storeLoanManageCtrlSrch')"><s:message code="cmm.search"/></button>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="loan.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="12" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="loan.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="15" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    </tbody>
  </table>

  <ul class="txtSty2 mt10 pdb20 bb">
    <li class="red">
      <s:message code="loan.info1"/><br>
      <p>
        <span><s:message code="loan.info1.txt1"/></span>
        <s:message code="loan.info1.txt1.1"/><br>
        <span><s:message code="loan.info1.txt2"/></span>
        <s:message code="loan.info1.txt2.1"/><br>
        <span><s:message code="loan.info1.txt3"/></span>
        <s:message code="loan.info1.txt3.1"/>
      </p>
    </li>
    <li class="red mt10">
      <s:message code="loan.info2"/><br>
      <p>
        <span><s:message code="loan.info2.txt1"/></span>
        <s:message code="loan.info2.txt1.1"/><br>
        <span><s:message code="loan.info2.txt2"/></span>
        <s:message code="loan.info2.txt2.1"/>
      </p>
    </li>
  </ul>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
      class="w100px fl"
      id="listScaleBox"
      ng-model="listScale"
      items-source="_getComboData('listScaleBox')"
      display-member-path="name"
      selected-value-path="value"
      initialized="_initComboBox(s)"
      control="conListScale"
	  is-editable="true"
	  text-changed="_checkValidation(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>
    <%-- 엑셀 다운로드 --%>
    <%--<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>--%>
    <%-- 삭제 --%>
    <button id="btnDel" class="btn_skyblue fr mr5" ng-click="fnDel()"><s:message code="cmm.delete"/></button>    
    <%-- 저장 --%>
    <button id="btnSave" class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save"/></button>

    
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 300px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                frozen-columns="2"
                sorted-column="toggleFreeze(false)"
                ime-enabled="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" 			binding="gChk" 		width="30" 	align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.limitLoanAmt"/>" binding="limitLoanAmt" width="90" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.useLoanAmt"/>" binding="useLoanAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.currLoanAmt"/>" binding="currLoanAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.maxOrderAmt"/>" binding="maxOrderAmt" width="90" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.maxOrderAmtYn"/>" binding="maxOrderAmtYn" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.orderFg"/>" binding="orderFg" width="90" align="center" is-read-only="false" data-map="orderFg"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.availableOrderAmt"/>" binding="availableOrderAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.noOutstockAmtFg"/>" binding="noOutstockAmtFg" width="90" align="center" is-read-only="false" data-map="noOutstockAmtFg"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.orderCloseYn"/>" binding="orderCloseYn" width="50" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.remark"/>" binding="remark" width="100" align="left" is-read-only="false"></wj-flex-grid-column>
      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="storeLoanManageCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeLoanManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoanManage/storeLoanManage.js?ver=20200824.02" charset="utf-8"></script>

<%-- 매장여신관리 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/loan/storeLoanManage/storeLoanManageDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
