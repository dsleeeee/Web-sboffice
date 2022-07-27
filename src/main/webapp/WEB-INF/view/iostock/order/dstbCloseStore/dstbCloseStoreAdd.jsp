<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseStore/dstbCloseStoreAdd/"/>

<wj-popup id="wjDstbCloseStoreAddLayer" control="wjDstbCloseStoreAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="dstbCloseStoreAddLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseStoreAddCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dstbCloseStore.add.title"/> -
      <s:message code="dstbCloseStore.add.addProdSubTitle"/>
      <label id="addProdSubTitle"></label>
      <label id="orderFgSubTitle"></label>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 매장코드 --%>
          <th><s:message code="dstbCloseStore.add.store"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="dstbCloseStoreAddSelectStore"/>
              <jsp:param name="closeFunc" value="fnGridClear"/>
            </jsp:include>
            <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="dstbCloseStore.add.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품코드 --%>
          <th><s:message code="dstbCloseStore.add.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="dstbCloseStore.add.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 분류 --%>
          <th><s:message code="dstbCloseStore.add.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        </tbody>
      </table>
      <table class="tblType01" id="tblSearchAddShow" style="display: none;">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 옵션1 --%>
          <th><s:message code="dstbCloseStore.add.option1"/></th>
          <td>
            <span class="txtIn w200px sb-select fl mr5">
              <wj-combo-box
                id="option1"
                ng-model="option1"
                items-source="_getComboData('option1')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
          </td>
          <%-- 거래처 --%>
          <th <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>><s:message code="dstbCloseProd.dtl.vender"/></th>
          <td <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>>
            <div class="sb-select fl w150px">
              <wj-combo-box
                id="dtlVendrCd"
                ng-model="vendrCd"
                control="dtlVendrCdCombo"
                items-source="_getComboData('dtlVendrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 옵션2 --%>
          <th><s:message code="dstbCloseStore.add.option2"/></th>
          <td colspan="3">
            <span class="txtIn w120px sb-select fl mr5">
              <wj-combo-box
                id="option2"
                ng-model="option2"
                items-source="_getComboData('option2')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s, e)"
              >
              </wj-combo-box>
            </span>
            <p id="option2OrdLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.reqDate"/></p>
            <p id="option2OutLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.outDate"/></p>
            <p id="option2SaleLayer" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.saleDate"/></p>
            <div id="option2DateLayer" class="sb-select fl ml10" style="display: none;">
              <span class="txtIn"><input id="srchRegStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchRegEndDate" class="w120px"></span>
            </div>
            <p id="option2OrdLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.txtOption2Ord"/></p>
            <p id="option2OutLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.txtOption2Out"/></p>
            <p id="option2SaleLayer2" class="s14 bk lh30 fl ml10" style="display: none;">
              <s:message code="dstbCloseStore.add.txtOption2Sale"/></p>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="dstbCloseStore.add.excelFormDownload"/></a>
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="dstbCloseStore.add.excelFormUpload"/></a>
<%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="dstbCloseStore.add.textFormUpload"/></a> --%>
            <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="dstbCloseStore.add.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="search();">
          <s:message code="cmm.search"/></button>
          <%-- 확장조회 --%>
          <button class="btn_blue mr5 fr" id="btnSearchAddShow" ng-click="searchAddShowChange()">
              <s:message code="cmm.search.addShow" />
          </button>
      </div>

      <%--<ul class="txtSty3 mt10">--%>
        <%--<li class="red"><s:message code="dstbCloseStore.add.txt1"/></li>--%>
      <%--</ul>--%>

      <div class="mt20 tr">
        <div class="tr">
          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnSave" ng-click="saveDstbCloseStoreAdd()">
            <s:message code="cmm.save"/></button>
            <div class="tooltipBtn fl">설명
                 <span class="tooltiptext tooltip-right">
                   * <s:message code="dstbCloseStore.add.txt1"/><br/>
                 </span>
             </div>
        </div>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                             binding="gChk"                width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prodClassNm"/>" binding="prodClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrSplyUprc"/>" binding="mgrSplyUprc" width="60" align="right" max-length=10 is-read-only="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prevMgrUnitQty"/>" binding="prevMgrUnitQty" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prevMgrEtcQty"/>" binding="prevMgrEtcQty" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.prevMgrTotQty"/>" binding="prevMgrTotQty" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrUnitQty"/>" binding="mgrUnitQty" width="60" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrEtcQty"/>" binding="mgrEtcQty" width="60" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrTotQty"/>" binding="mgrTotQty" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrAmt"/>" binding="mgrAmt" width="60" align="right" is-read-only="true" visible="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrVat"/>" binding="mgrVat" width="60" align="right" is-read-only="true" visible="false" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.mgrTot"/>" binding="mgrTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.saleUprc"/>" binding="saleUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.splyUprc"/>" binding="splyUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.hqSafeStock"/>" binding="hqSafeStockUnitQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.hqSafeStock"/>" binding="hqSafeStockEtcQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.hqCurrQty"/>" binding="hqCurrUnitQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.hqCurrQty"/>" binding="hqCurrEtcQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.storeCurrQty"/>" binding="storeCurrUnitQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.storeCurrQty"/>" binding="storeCurrEtcQty" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.remark"/>" binding="remark" width="150" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.poMinQty"/>" binding="poMinQty" width="90" align="right" is-read-only="true" visible="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.vatFg"/>" binding="vatFg01" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseStore.add.envst0011"/>" binding="envst0011" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dstbCloseStoreAddCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbCloseStore/dstbCloseStoreAdd.js?ver=20220726.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 공통팝업 수불/재고 엑셀업로드 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.jsp">
</c:import>