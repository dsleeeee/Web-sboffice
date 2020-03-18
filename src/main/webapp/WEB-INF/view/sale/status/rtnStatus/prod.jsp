<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusProdView" class="subCon3" style="display: none;" ng-controller="rtnStatusProdCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="rtnStatus.rtnStatus"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnRtnStatusProdSearch" ng-click="_broadcast('rtnStatusProdCtrlSrch')">
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
        <td>
          <div class="sb-select">
          <span class="txtIn w110px">
              <wj-input-date
                      id="srchRtnStatusProdStartDate"
                      value="rtnStatusProdStartDate"
                      ng-model="startDate"
                      control="rtnStatusProdStartDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month"
                      format="y">
              </wj-input-date>
            </span>
            <span class="rg">~</span>
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchRtnStatusProdEndDate"
                      value="rtnStatusProdEndDate"
                      ng-model="endDate"
                      control="rtnStatusProdEndDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)"
                      selection-mode="Month"
                      format="y">
              </wj-input-date>
            </span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>

        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="rtnStatusProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="rtnStatusProdSelectStore"/>
            </jsp:include>
            <%-- //매장선택 모듈 싱글 선택 사용시 include --%>
        </td>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="rtnStatusProdSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <!-- contents start -->
    <div class="">
        <%-- wj grid start --%>
        <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="rtnStatusProdListScaleBox"
                ng-model="rtnStatusProdListScale"
                items-source="_getComboData('rtnStatusProdListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
        </button>
        </div>
        <div class="wj-gridWrap2 mt20">
               <wj-flex-grid
                 id="rtnStatusProdGrid"
                 autoGenerateColumns="false"
                 selection-mode="Row"
                 items-source="data"
                 control="flex"
                 initialized="initGrid(s,e)"
                 is-read-only="true"
                 item-formatter="_itemFormatter">
                 <!-- define columns -->
                 <wj-flex-grid-column header="<s:message code="rtnStatus.prodClassNm"/>"      binding="lv1Nm"           width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.prodClassNm1"/>"     binding="lv2Nm"           width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.prodClassNm2"/>"     binding="lv3Nm"           width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.prodCd"/>"           binding="prodCd"          width="*" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.prodNm"/>"           binding="prodNm"          width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.barcdCd"/>"          binding="barcdCd"         width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleQty"/>"       binding="cnt"             width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                 <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>"      binding="realSaleAmt"     width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
               </wj-flex-grid>
               <%-- ColumnPicker 사용시 include --%>
               <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                 <jsp:param name="pickerTarget" value="rtnStatusProdCtrl"/>
               </jsp:include>
               <%--// ColumnPicker 사용시 include --%>
        </div>
        <%-- //wj grid end --%>
    </div>
    <!-- //contents end -->

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="rtnStatusProdCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/prod.js" charset="utf-8"></script>

