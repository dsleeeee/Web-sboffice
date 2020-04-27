<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/disuse/disuse/disuseRegist/"/>

<wj-popup id="wjDisuseRegistLayer" control="wjDisuseRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="disuseRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="disuseRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="disuse.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p id="registSubTitle" class="s14 bk mb5 fl"></p>

      <form name="myForm" novalidate>
        <table class="tblType01" style="position: relative;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <%-- 폐기제목 --%>
            <th><s:message code="disuse.reg.disuseTitle"/><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="disuseTitle" name="disuseTitle" ng-model="disuseTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.disuseTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="disuse.reg.disuseTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 상품코드 --%>
            <th><s:message code="disuse.reg.prodCd"/></th>
            <td>
              <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="disuse.reg.prodNm"/></th>
            <td>
              <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
            </td>
          </tr>
          <tr>
            <%-- 바코드 --%>
            <th><s:message code="disuse.reg.barcd"/></th>
            <td>
              <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
            </td>
            <%-- 상품분류 --%>
            <th><s:message code="disuse.reg.prodClass"/></th>
            <td>
              <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                     placeholder="<s:message code="cmm.all" />" readonly/>
              <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
            </td>
          </tr>
          <tr>
          	 <c:if test="${sessionInfo.orgnFg == 'STORE'}">
	         <%-- 출고창고 --%>
	         <th><s:message code="hqMove.outStorage"/></th>
	         <td>
	            <%-- 창고선택 모듈 멀티 선택 사용시 include
	                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
	                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
	                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
	                              closeFunc - 팝업 닫기시 호출할 함수
	            --%>	            
	            <jsp:include page="/WEB-INF/view/stock/com/popup/cmmStorage/selectStorageS.jsp" flush="true">
	              <jsp:param name="targetId" value="registSelectStorage"/>
	            </jsp:include>
	            <%--// 창고선택 모듈 멀티 선택 사용시 include --%>
	        </td>
	        </c:if>
            <%-- 폐기구분 --%>
            <th><s:message code="disuse.reg.disuseFg"/></th>
            <td>
              <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchDisuseFg"
                  ng-model="disuseFg"
                  ng-disabled="readDisuseFg"
                  items-source="_getComboData('srchDisuseFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="disuse.reg.excelFormDownload"/></a>
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
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="disuse.reg.excelFormUpload"/></a>
<%--               <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="disuse.reg.textFormUpload"/></a> --%>
              <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
              <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="disuse.reg.excelFormUploadErrorInfo"/></a>
            </td>
          </tr>
          </tbody>
        </table>
      </form>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="fnSearch();">
          <s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="disuse.reg.txt1"/></li>
        <li class="red"><s:message code="disuse.reg.txt2"/></li>
        <li class="red"><s:message code="disuse.reg.txt3"/></li>
      </ul>

      <table class="tblType01 mt10 tc" style="position: relative;">
        <colgroup>
          <col class="w70"/>
          <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드/바코드 --%>
          <th class="tc">
            <s:message code="disuse.reg.prodCd"/>/<s:message code="disuse.reg.barcd"/></th>
          <%-- 추가수량 --%>
          <th class="tc"><s:message code="disuse.reg.addQty"/></th>
        </tr>
        <tr>
          <td>
            <input type="text" id="prodBarcdCd" name="prodBarcdCd" ng-model="prodBarcdCd" class="sb-input tc" maxlength="40" style="width: 250px;" ng-keydown="searchProdKeyEvt($event)"/>
            <%-- 찾기 --%>
            <a href="#" class="btn_grayS" ng-click="prodFindPop()"><s:message code="disuse.reg.prodFind"/></a>
            <span class="chk txtIn lh30 ml5" style="top: -2px;">
              <input type="checkbox" name="autoAddChk" id="autoAddChk" ng-model="autoAddChk"/>
              <label for="autoAddChk"><s:message code="disuse.reg.autoAdd"/></label>
            </span>
          </td>
          <td>
            <input type="text" id="addQty" name="addQty" ng-model="addQty" class="sb-input tc" maxlength="10" style="width: 100px;" ng-keydown="addQtyKeyEvt($event)"/>
            <%-- 추가 --%>
            <a href="#" class="btn_grayS" ng-click="fnAddQty()"><s:message code="disuse.reg.add"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt20 tr">
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

          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnRegSave" ng-click="saveDisuseRegist()">
            <s:message code="cmm.save"/></button>
        </div>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 500px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseQty"/>" binding="disuseQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseAmt"/>" binding="disuseAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="disuse.reg.disuseProdStatus"/>" binding="disuseProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="disuseRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/disuse/disuse/disuseRegist.js?ver=20181224.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>
