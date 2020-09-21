<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/status/dayiostock/pord/"/>

<!-- contents start -->
<div class="subCon3" ng-controller="dayIostockCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>

      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dayIostockMainCtrlSrch')">
        <s:message code="cmm.search"/>
      </button>
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
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="srchDayIostockStartDate" class="w120px"></span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="dayIostock.prodCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd"/></td>

        <%-- 상품명 --%>
        <th><s:message code="dayIostock.prodNm"/></th>
        <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm"/></td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="dayIostock.barcdCd"/></th>
        <td><input type="text" class="sb-input w100" id="srchBarcdCd" ng-model="srchBarcdCd"/></td>

        <%-- 조회옵션 --%>
        <th><s:message code="dayIostock.srchOption"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
                <wj-combo-box
                  id="srchOptionDisplay"
                  ng-model="srchOption"
                  items-source="_getComboData('srchOptionDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
            <span class="chk ml10">
                <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()"/>
                <label for="chkDt">
                    <s:message code="dayIostock.prodClassDisplay" />
                </label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상품분류 --%>
		<th><s:message code="dayIostock.prodClass"/></th>
		<td>
		  <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
		         placeholder="<s:message code="cmm.all" />" readonly/>
		  <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCdModel" disabled/>
		  <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
		</td>

		<%-- 단위구분 --%>
        <th><s:message code="dayIostock.unitFg"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn">
                <wj-combo-box
                  id="srchUnitFgDisplay"
                  ng-model="unitFgModel"
                  items-source="_getComboData('srchUnitFgDisplay')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
            </span>
          </div>
        </td>
      </tr>
      <tr style="display: none">
        <%-- 거래처 --%>
        <th style="display: none"><s:message code="dayIostock.vendr"/></th>
        <td style="display: none">
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="dayIostockSelectVendr"/>
          </jsp:include>
          <input type="hidden" id="dayIostockSelectVendrCd" value=""/>
        </td>
      </tr>
      </tbody>
    </table>

    <input type="hidden" id="hqOfficeCd" value="${sessionInfo.hqOfficeCd}"/>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
	    <input type="hidden" id="storeCd" value="${sessionInfo.storeCd}"/>
	    <input type="hidden" id="storeNm" value="${sessionInfo.storeNm}"/>
    </c:if>

    <div ng-controller="dayIostockMainCtrl">
	    <div class="mt20 oh sb-select dkbr">
	      <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="dayIostockMainlistScaleBox"
            ng-model="listScale"
            items-source="_getComboData('dayIostockMainlistScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="_initComboBox(s)"
            control="listScaleCombo"
            is-editable="true"
            text-changed="_checkValidation(s)">
          </wj-combo-box>
	        <%-- 엑셀 다운로드 //TODO --%>
		    <button class="btn_skyblue fr" ng-click="excelDownloadDayIostock()"><s:message code="cmm.excel.down" />
		    </button>
	    </div>

	    <%-- 위즈모 테이블 gird 1 --%>
	    <div class="w100 mt10" id="wjWrapType2">
		    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
		        <wj-flex-grid
		          id="dayIostockMainGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="flex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="5"
	              >

	              <!-- define columns -->
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassLNm"/>"                binding="lv1Nm" width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassMNm"/>"                binding="lv2Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassSNm"/>"                binding="lv3Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="dayIostock.prodCd"/>"                   binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodNm"/>"                   binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.poUnitQty"/>"                binding="poUnitQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.poUnitFg"/>"                 binding="poUnitFgNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.barcdCd"/>"                  binding="barcdCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>

	              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrInQty"/>"           binding="ioOccrQty01" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrInAmt"/>"           binding="ioOccrTot01" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrOutQty"/>"          binding="ioOccrQty16" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrOutAmt"/>"          binding="ioOccrTot16" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqOutQty"/>"                binding="ioOccrQty13" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqOutAmt"/>"                binding="ioOccrTot13" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqInQty"/>"                 binding="ioOccrQty02" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqInAmt"/>"                 binding="ioOccrTot02" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>

	              <c:if test="${sessionInfo.orgnFg == 'STORE'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInQty"/>"               binding="ioOccrQty03" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInAmt"/>"               binding="ioOccrTot03" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutQty"/>"              binding="ioOccrQty12" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutAmt"/>"              binding="ioOccrTot12" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInQty"/>"              binding="ioOccrQty06" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInAmt"/>"              binding="ioOccrTot06" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutQty"/>"             binding="ioOccrQty18" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutAmt"/>"             binding="ioOccrTot18" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleQty"/>"             binding="ioOccrQty11" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleAmt"/>"             binding="ioOccrTot11" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>

	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInQty"/>"                binding="ioOccrQty04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInAmt"/>"                binding="ioOccrTot04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutQty"/>"               binding="ioOccrQty14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutAmt"/>"               binding="ioOccrTot14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accDisuseQty"/>"                     binding="ioOccrQty17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accDisuseAmt"/>"                     binding="ioOccrTot17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accAdjQty"/>"                        binding="ioOccrQty21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accAdjAmt"/>"                        binding="ioOccrTot21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accSetInQty"/>"                      binding="ioOccrQty22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accSetInAmt"/>"                      binding="ioOccrTot22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrOutQty"/>"           binding="ioOccrQty19" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrOutAmt"/>"           binding="ioOccrTot19" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrInQty"/>"            binding="ioOccrQty33" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrInAmt"/>"            binding="ioOccrTot33" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>
	            </wj-flex-grid>
	            <%-- ColumnPicker 사용시 include --%>
	            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	              <jsp:param name="pickerTarget" value="dayIostockMainCtrl"/>
	            </jsp:include>
	            <%--// ColumnPicker 사용시 include --%>
		    </div>
		    <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <%-- id --%>
              <ul id="dayIostockMainCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>
            
            <%-- 엑셀 리스트 --%>
			<div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="dayIostockMainExcelCtrl">
      			<div class="wj-gridWrap">
      			<wj-flex-grid
		          id="dayIostockMainExcelGrid"
	              loaded-rows="loadedRows(s,e)"
	              autoGenerateColumns="false"
	              selection-mode="Row"
	              items-source="data"
	              control="excelFlex"
	              initialized="initGrid(s,e)"
	              is-read-only="true"
	              item-formatter="_itemFormatter"
	              frozen-columns="5"
	              >

	              <!-- define columns -->
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassLNm"/>"                binding="lv1Nm" width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassMNm"/>"                binding="lv2Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodClassSNm"/>"                binding="lv3Nm" width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

	              <wj-flex-grid-column header="<s:message code="dayIostock.prodCd"/>"                   binding="prodCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.prodNm"/>"                   binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.poUnitQty"/>"                binding="poUnitQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.poUnitFg"/>"                 binding="poUnitFgNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.barcdCd"/>"                  binding="barcdCd" width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>

	              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrInQty"/>"           binding="ioOccrQty01" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrInAmt"/>"           binding="ioOccrTot01" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrOutQty"/>"          binding="ioOccrQty16" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accVendrOutAmt"/>"          binding="ioOccrTot16" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqOutQty"/>"                binding="ioOccrQty13" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqOutAmt"/>"                binding="ioOccrTot13" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqInQty"/>"                 binding="ioOccrQty02" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.hqInAmt"/>"                 binding="ioOccrTot02" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>

	              <c:if test="${sessionInfo.orgnFg == 'STORE'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInQty"/>"               binding="ioOccrQty03" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreInAmt"/>"               binding="ioOccrTot03" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutQty"/>"              binding="ioOccrQty12" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreOutAmt"/>"              binding="ioOccrTot12" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInQty"/>"              binding="ioOccrQty06" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsInAmt"/>"              binding="ioOccrTot06" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutQty"/>"             binding="ioOccrQty18" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accPurchsOutAmt"/>"             binding="ioOccrTot18" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleQty"/>"             binding="ioOccrQty11" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreSaleAmt"/>"             binding="ioOccrTot11" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>

	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInQty"/>"                binding="ioOccrQty04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveInAmt"/>"                binding="ioOccrTot04" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutQty"/>"               binding="ioOccrQty14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accStoreMoveOutAmt"/>"               binding="ioOccrTot14" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accDisuseQty"/>"                     binding="ioOccrQty17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accDisuseAmt"/>"                     binding="ioOccrTot17" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accAdjQty"/>"                        binding="ioOccrQty21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accAdjAmt"/>"                        binding="ioOccrTot21" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accSetInQty"/>"                      binding="ioOccrQty22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              <wj-flex-grid-column header="<s:message code="dayIostock.accSetInAmt"/>"                      binding="ioOccrTot22" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

	              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrOutQty"/>"           binding="ioOccrQty19" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrOutAmt"/>"           binding="ioOccrTot19" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrInQty"/>"            binding="ioOccrQty33" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		              <wj-flex-grid-column header="<s:message code="dayIostock.accSaleVendrInAmt"/>"            binding="ioOccrTot33" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
	              </c:if>
	            </wj-flex-grid>
            	</div>
			</div>
			<%--//엑셀 리스트--%>
	    </div>
    </div>
</div>
<!-- //contents end -->

<script type="text/javascript" src="/resource/solbipos/js/stock/status/dayiostock/prod.js" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품코드 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodCodeDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 수량 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/status/periodiostock/prodQtyDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
