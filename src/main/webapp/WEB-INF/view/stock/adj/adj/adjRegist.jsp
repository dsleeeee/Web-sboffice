<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/adj/adj/adjRegist/"/>

<wj-popup id="wjAdjRegistLayer" control="wjAdjRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="adjRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="adjRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="adj.reg.registTitle"/>&nbsp;&nbsp;<span id="registSubTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">

      <form name="myForm" novalidate>
        <table class="tblType01" style="position: relative;">
          <colgroup>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
            <col class="w10"/>
            <col class="w20"/>
          </colgroup>
          <tbody>
          <tr>
            <%-- 조정제목 --%>
            <th><s:message code="adj.reg.adjTitle"/><em class="imp">*</em></th>
            <td colspan="5">
              <input type="text" id="adjTitle" name="adjTitle" ng-model="adjTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.adjTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="adj.reg.adjTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 상품코드 --%>
            <th><s:message code="adj.reg.prodCd"/></th>
            <td>
              <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="adj.reg.prodNm"/></th>
            <td>
              <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="adj.reg.barcd"/></th>
            <td>
              <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
            </td>
          </tr>
          <tr>
            <%-- 상품분류 --%>
            <th><s:message code="adj.reg.prodClass"/></th>
            <td>
              <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                     placeholder="<s:message code="cmm.all" />" readonly/>
              <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
            </td>
            <%-- 조정구분 --%>
            <th><s:message code="adj.reg.adjFg"/></th>
            <td>
              <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchAdjFg"
                  ng-model="adjFg"
                  ng-disabled="readAdjFg"
                  items-source="_getComboData('srchAdjFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
              </div>
            </td>
            <%-- 출고창고 --%>
            <th <c:if test="${storageEnvstVal == '0'}">style="display: none;"</c:if> >
              <s:message code="hqMove.outStorage"/>
            </th>
            <td <c:if test="${storageEnvstVal == '0'}">style="display: none;"</c:if> >
              <span class="txtIn w150px sb-select fl mr5">
                <wj-combo-box
                        id="acinsRegAdjStorageCd"
                        ng-model="acins.reg.adjStorageCd"
                        items-source="_getComboData('acinsRegAdjStorageCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        selected-index-changed="selectedIndexChangedReg(s)">
                  </wj-combo-box>
              </span>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="adj.reg.excelFormDownload"/></a>
              <span class="txtIn w120px" style="border:1px solid #e8e8e8;">
                <wj-combo-box
                  id="addQtyFg"
                  ng-model="addQtyFg"
                  items-source="_getComboData('addQtyFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="adj.reg.excelFormUpload"/></a>
<%--               <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="adj.reg.textFormUpload"/></a> --%>
              <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
              <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="adj.reg.excelFormUploadErrorInfo"/></a>
            </td>
          </tr>
          </tbody>
        </table>
      </form>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr ml5" id="btnSearch" ng-click="fnSearch();">
          <s:message code="cmm.search"/></button>
          <%-- 상품찾기 --%>
          <button type="button" class="btn_blue fr ml5" id="btnProdFind" ng-click="prodFind();">
            <s:message code="adj.reg.prodFind"/></button>
      </div>

      <table id="prodFind" class="tblType01 mt10 tc" style="position: relative;display:none;">
        <colgroup>
          <col class="w70"/>
          <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드/바코드 --%>
          <th class="tc">
            <s:message code="adj.reg.prodCd"/>/<s:message code="adj.reg.barcd"/></th>
          <%-- 추가수량 --%>
          <th class="tc"><s:message code="adj.reg.addQty"/></th>
        </tr>
        <tr>
          <td>
            <input type="text" id="prodBarcdCd" name="prodBarcdCd" ng-model="prodBarcdCd" class="sb-input tc" maxlength="40" style="width: 250px;" ng-keydown="searchProdKeyEvt($event)"/>
            <%-- 찾기 --%>
            <a href="#" class="btn_grayS" ng-click="prodFindPop()"><s:message code="adj.reg.prodFind"/></a>
            <span class="chk txtIn lh30 ml5" style="top: -2px;">
              <input type="checkbox" name="autoAddChk" id="autoAddChk" ng-model="autoAddChk"/>
              <label for="autoAddChk"><s:message code="adj.reg.autoAdd"/></label>
            </span>
          </td>
          <td>
            <input type="text" id="addQty" name="addQty" ng-model="addQty" class="sb-input tc" maxlength="10" style="width: 100px;" ng-keydown="addQtyKeyEvt($event)"/>
            <%-- 추가 --%>
            <a href="#" class="btn_grayS" ng-click="fnAddQty()"><s:message code="adj.reg.add"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 tr">
        <div class="oh sb-select">
          <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="regListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('regListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
          <%--// 페이지 스케일  --%>

            <ul class="txtSty3">
              <li class="red fl"><s:message code="adj.reg.txt3"/></li>
            </ul>

          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnRegSave" ng-click="saveAdjRegist()">
            <s:message code="cmm.save"/></button>
        </div>
      </div>

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
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjQty"/>" binding="adjQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjProdStatus"/>" binding="adjProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="adjRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  // [1241 창고사용여부] 환경설정값
  var storageEnvstVal = "${storageEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/stock/adj/adj/adjRegist.js?ver=20220714.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 공통팝업 실사/조정/폐기 엑셀업로드 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadStore/excelUploadStore.jsp">
</c:import>