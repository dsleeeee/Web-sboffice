<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="posDayPeriodView" class="subCon" style="display: none;" ng-controller="posDayPeriodCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="barcd.barcd"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnPosDayPeriodSearch" ng-click="_broadcast('posDayPeriodMainCtrlSrch')">
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
	            <span class="txtIn"><input id="srchPosDayPeriodStartDate" class="w120px"></span>
	                <span class="rg">~</span>
	            <span class="txtIn"><input id="srchPosDayPeriodEndDate" class="w120px"></span>
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
	        <th><s:message code="todayBillSaleDtl.store"/></th>
	        <td colspan="3">
	            <%-- 매장선택 모듈 싱글 선택 사용시 include
	               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
	                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
	                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
	                            closeFunc - 팝업 닫기시 호출할 함수
	            --%>
	<%--             <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true"> --%>
	<%--                 <jsp:param name="targetId" value="posDayPeriodSelectStore"/> --%>
	<%--             </jsp:include> --%>
	            <%-- //매장선택 모듈 싱글 선택 사용시 include --%>
	            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
	            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
	                <jsp:param name="targetId" value="posDayPeriodSelectStore"/>
	            </jsp:include>
	            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
	        </td>
        </tr>
      </c:if>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="posDayPeriodSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>


      </tbody>
    </table>
    <div style="clear: both;"></div>
            <%-- left --%>
            <div class="w50 fl" ng-controller="posDayPeriodMainCtrl" style="width: 49%;">
            <div class="mt20 oh sb-select dkbr mb10">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="posDayPeriodListScaleBox"
                    ng-model="posDayPeriodListScale"
                    items-source="_getComboData('posDayPeriodListScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    initialized="initComboBox(s)"
                    control="listScaleCombo"
                    is-editable="true"
                    text-changed="_checkValidation(s)">
            </wj-combo-box>
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriod()"><s:message code="cmm.excel.down" />
            </button>
            </div>
            <%--위즈모 테이블--%>
		    <div class="w100 mt10" id="wjWrapType1">
		      <div class="wj-gridWrap">
                   <wj-flex-grid
                      id="posDayPeriodGrid"
                      loaded-rows="loadedRows(s,e)"
                      autoGenerateColumns="false"
                      selection-mode="Row"
                      items-source="data"
                      control="flex"
                      initialized="initGrid(s,e)"
                      is-read-only="true"
                      item-formatter="_itemFormatter">
                      <!-- define columns -->
                      <wj-flex-grid-column header="<s:message code="pos.pos"/>"                 binding="storeNmPosNo"          width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleDtl.saleQty"/>"     binding="saleCnt"        width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                      <wj-flex-grid-column header="<s:message code="prodSaleDtl.realSaleAmt"/>" binding="realSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                      <jsp:param name="pickerTarget" value="posDayPeriodMainCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <ul id="posDayPeriodMainCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>

        <%-- right --%>
        <div class="w50 fr" ng-controller="posDayPeriodDtlCtrl">
        <div class="mt20 oh sb-select dkbr mb10">
           <%-- 페이지 스케일  --%>
           <wj-combo-box
                   class="w100px fl"
                   id="posDayPeriodDtlListScaleBox"
                   ng-model="posDayPeriodDtlListScale"
                   items-source="_getComboData('posDayPeriodDtlListScaleBox')"
                   display-member-path="name"
                   selected-value-path="value"
                   initialized="initComboBox(s)"
                   control="listScaleCombo"
                   is-editable="true"
                   text-changed="_checkValidation(s)">
           </wj-combo-box>
           <%-- 엑셀 다운로드 //TODO --%>
           <button class="btn_skyblue fr" ng-click="excelDownloadDayPeriodDtl()"><s:message code="cmm.excel.down" />
           </button>
           </div>
                <%--위즈모 테이블--%>
			    <div class="w100 mt10" id="wjWrapType1">
			      <div class="wj-gridWrap">
                       <wj-flex-grid
                          id="posDayPeriodDtlGrid"
                          autoGenerateColumns="false"
                          selection-mode="Row"
                          items-source="data"
                          control="flex"
                          initialized="initGrid(s,e)"
                          is-read-only="true"
                          item-formatter="_itemFormatter">
                          <!-- define columns -->
                          <wj-flex-grid-column header="<s:message code="prodrank.prodClassLNm"/>" 	binding="lv1Nm" 		width="150" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          				  <wj-flex-grid-column header="<s:message code="prodrank.prodClassMNm"/>" 	binding="lv2Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          				  <wj-flex-grid-column header="<s:message code="prodrank.prodClassSNm"/>" 	binding="lv3Nm" 		width="200" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="corner.prodCd"/>"           binding="prodCd"        width="100" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="corner.prodNm"/>"           binding="prodNm"        width="*"   align="center"   is-read-only="true"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="pos.saleQty"/>"             binding="totSaleQty"    width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="pos.realSaleAmt"/>"         binding="realSaleAmt"   width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        </wj-flex-grid>
                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                          <jsp:param name="pickerTarget" value="posDayPeriodDtlCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </div>
                </div>
              <%-- 페이지 리스트 --%>
              <div class="pageNum mt20">
                <ul id="posDayPeriodDtlCtrlPager" data-size="10">
                </ul>
              </div>
              <%--//페이지 리스트--%>
        </div>
    </div>
    <%-- //wj grid end --%>
<!-- //contents end -->

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/pos/dayPeriod/dayPeriod.js" charset="utf-8"></script>

