<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="gvOrgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="prodEnvstVal" value="${prodEnvstVal}" />
<c:set var="baseUrl" value="/sale/anals/abc/" />

<div id="abcView" class="subCon3" ng-controller="abcCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="abc.abc" /></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnAbcSearch" ng-click="_broadcast('abcCtrlSrch')">
            <s:message code="cmm.search" />
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date" /></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchAbcStartDate" class="w120px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchAbcEndDate" class="w120px"></span>
                        <span class="chk ml10">
                            <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                            <label for="chkDt"><s:message code="cmm.all.day" /></label>
                        </span>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <input type="hidden" id="abcSelectStoreCd" value=""/>
                    <%-- 매장코드 --%>
                    <th><s:message code="todayDtl.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="abcSelectStore"/>
                            <jsp:param name="targetTableId" value="abcSelectTable"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <input type="hidden" id="abcSelectStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
            </tr>
            <tr>
                <%-- 누적판매비율 --%>
                <th><s:message code="abc.totSaleRate" /></th>
                <td>
                    <span class="txtIn" style="width:32%;">A등급<input id="abcGradeA" class="sb-input" value="70" style="width:40%;">%</span>
                    <span class="txtIn" style="width:32%;">B등급<input id="abcGradeB" class="sb-input" value="90" style="width:40%;">%</span>
                    <span class="txtIn" style="width:32%;">C등급<input id="abcGradeC" class="sb-input" value="100" style="width:45%;">%</span>
                </td>
	            <%-- 조회옵션 --%>
				<th><s:message code="periodIostock.srchOption" /></th>
				<td>
		          	<span class="chk ml10">
						<input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
		              	<label for="chkDt">
	                		<s:message code="periodIostock.prodClassDisplay" />
	              		</label>
	            	</span>
				</td>
            </tr>
            <tr>
                <%-- 정렬순서 --%>
                <th><s:message code="abc.order" /></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn">
                            <wj-combo-box id="srchAbcDisplay" ng-model="sortFg"
                                items-source="_getComboData('srchAbcDisplay')"
                                display-member-path="name" selected-value-path="value"
                                is-editable="false" initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">

            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadAbc()"><s:message code="cmm.excel.down" />
            </button>
        </div>

        <%--위즈모 테이블--%>
        <div class="w100 mt10">
          <div class="wj-gridWrap1">
            <wj-flex-grid
              id="abcGrid"
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="abc.grade"/>"              binding="grade"       width="50" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.lv1Nm"/>"              binding="lv1Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.lv2Nm"/>"              binding="lv2Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.lv3Nm"/>"              binding="lv3Nm"       width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.prodCd"/>"             binding="prodCd"        width="*" align="center" is-read-only="true" word-wrap="true" multi-line="true" format="d"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.prodNm"/>"             binding="prodNm"   width="165" align="center" is-read-only="true" word-wrap="true" multi-line="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.realSaleAmt"/>"        binding="realSaleAmt"   width="*" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.totSaleQty"/>"         binding="totSaleQty"   width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.rat"/>"                binding="rat"        width="*" align="center" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="abc.acc"/>"                binding="accRat"        width="*" align="center" is-read-only="true" word-wrap="true" multi-line="true"></wj-flex-grid-column>
            </wj-flex-grid>

            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="abcCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
        </div>
        <%--//위즈모 테이블--%>

    </div>

<script>
  var prodEnvstVal = "${prodEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/abc/abc/abc.js?ver=20190125.02" charset="utf-8"></script>

<%-- 레이어 팝업 : 상품상세정보 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodDetailView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="prodNoEnvFg" value="${prodNoEnvFg}"/>
</c:import>
