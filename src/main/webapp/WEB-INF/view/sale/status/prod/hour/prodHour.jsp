<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/sale/status/prod/hour/prodHour/"/>

<div id="prodHourView" name="dayView" class="subCon3" style="display: none;" ng-controller="prodHourCtrl"> <%-- 수정 사항 || 클래스 변경 :: class="subCon" >> class="subCon3"--%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="day.time"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodHourCtrlSrch')">
            <s:message code="cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w13"/>
            <col class="w37"/>
            <col class="w13"/>
            <col class="w37"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchHourStartDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchHourEndDate" class="w120px"></span>
                    <span class="chk ml10">
										<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
		              	<label for="chkDt">
	                		<s:message code="cmm.all.day" />
	              		</label>
            	</span>
                </div>
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
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
              <%-- 매장코드 --%>
              <th><s:message code="day.time.store"/></th>
              <td>
                      <%-- 매장선택 모듈 싱글 선택 사용시 include
                           param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                        displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                        modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                        closeFunc - 팝업 닫기시 호출할 함수
                      --%>
                  <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                      <jsp:param name="targetId" value="dayTimeSelectStore"/>
                  </jsp:include>
                      <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
              </td>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dayTimeSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
            <th><s:message code="day.time.time"/></th>
            <td>
                <div class="sb-select fl w200px">
                    <wj-combo-box
                            id="saleTime"
                            ng-model="saleTime"
                            items-source="_getComboData('srchSaleTimeCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div style="clear: both;"></div>

	<div class="mt20 oh sb-select dkbr"> <%-- 수정 사항 || 클래스변경 :: class="mt40" >> class="mt20" --%>
		<%-- 페이지 스케일  --%>
	    <wj-combo-box
	      class="w100px fl"
	      id="prodHourlistScaleBox"
	      ng-model="prodHourlistScale"
	      control="listScaleCombo"
	      items-source="_getComboData('prodHourlistScaleBox')"
	      display-member-path="name"
	      selected-value-path="value"
	      initialized="_initComboBox(s)"
	      is-editable="true"
          text-changed="_checkValidation(s)">
	    </wj-combo-box>
	    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
			<input type="text" id="dayTimeSelectStoreStoreNum" ng-model="storeNum">
		</c:if>
	    <%-- 엑셀 다운로드 //TODO --%>
	    <button class="btn_skyblue fr" ng-click="excelDownloadHour()"><s:message code="cmm.excel.down" />
	    </button>
	</div>

    <div id="wjWrapType2" class="w100 mt10"> <%-- 수정 사항 || head line 2 아이디 추가 :: id="wjWrapType2" --%>
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap"> <%-- 수정 사항 || 그리드 높이값 스타일 제거 :: style="height: 000px;" --%>
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    frozen-columns="5"
                     id="wjGridList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          		<wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.prodCd"/>" 			binding="prodCd" 		width="130" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.prodNm"/>" 			binding="prodNm"		width="200" align="center" is-read-only="true"></wj-flex-grid-column>

                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="24" step="1">
                    <c:set var="time"><fmt:formatNumber value="${i}" pattern="0"/></c:set>
                    <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty${time}" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt${time}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대 '전체' 선택 시 보이는 컬럼 --%>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty1" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt1" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty2" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt2" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty3" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt3" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty4" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt4" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty5" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt5" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty6" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt6" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty7" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt7" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

   				<wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty8" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt8" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty9" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt9" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty10" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt10" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty11" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt11" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty12" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt12" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty13" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt13" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty14" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt14" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty15" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt15" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty16" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt16" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty17" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt17" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty18" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt18" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty19" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt19" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty20" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt20" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty21" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt21" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty22" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt22" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty23" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt23" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="prodhour.totSaleQty"/>" binding="totSaleQty24" width="100" align="center" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodhour.totSaleAmt"/>" binding="totSaleAmt24" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="prodHourCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>

      <%-- 페이지 리스트 --%>
	  <div class="pageNum mt20">
	    <%-- id --%>
	    <ul id="prodHourCtrlPager" data-size="10">
	    </ul>
	  </div>
	  <%--//페이지 리스트--%>
    </div>
</div>

<script type="text/javascript">

</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/prod/hour/prodHour.js?ver=20190622" charset="utf-8"></script>
