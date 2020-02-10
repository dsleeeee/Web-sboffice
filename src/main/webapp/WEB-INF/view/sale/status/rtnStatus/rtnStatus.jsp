<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="rtnStatusDayView" class="subCon"  ng-controller="rtnStatusDayCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="rtnStatus.rtnStatus"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnRtnStatusDaySearch" ng-click="_broadcast('rtnStatusDayCtrl')">
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
            <span class="txtIn"><input id="srchRtnStatusDayStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchRtnStatusDayEndDate" class="w120px"></span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>
    
    <!-- contents start -->
	<div class="subCon">
	    <%-- wj grid start --%>
	    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="rtnStatusDayListScaleBox"
                ng-model="rtnStatusDayListScale"
                items-source="_getComboData('rtnStatusDayListScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.down" />
        </button>
        </div>
	    <div class="wj-gridWrap mt10">
	        <div style="height:400px">
		        <wj-flex-grid
		          id="rtnStatusDayGrid"
		          loaded-rows="loadedRows(s,e)"
		          autoGenerateColumns="false"
		          selection-mode="Row"
		          items-source="data"
		          control="flex"
		          initialized="initGrid(s,e)"
		          is-read-only="true"
		          item-formatter="_itemFormatter">
		          <!-- define columns -->
		          <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"/>"    binding="storeNm"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntY"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtY"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		          <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntN"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtN"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cnt"            width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"/>"    binding="storeCd"        width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
		        </wj-flex-grid>
		        <%-- ColumnPicker 사용시 include --%>
		        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
		          <jsp:param name="pickerTarget" value="rtnStatusDayCtrl"/>
		        </jsp:include>
		        <%--// ColumnPicker 사용시 include --%>
            </div>
	    </div>
	    <%-- //wj grid end --%>
	    <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <ul id="rtnStatusDayCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
	</div>
	<!-- //contents end -->
    
    
    <!-- contents start -->
    <%-- wj grid start --%>
    <div class="subCon wj-TblWrap">
	        <%-- left --%>
	        <div class="w50 fl">
	        <div class="mt20 oh sb-select dkbr pd10">
            <%-- 페이지 스케일  --%>
<!--             <wj-combo-box -->
<!--                     class="w100px fl" -->
<!--                     id="rtnStatusDayDtlListScaleBox" -->
<!--                     ng-model="rtnStatusDayDtlListScale" -->
<!--                     items-source="_getComboData('rtnStatusDayDtlListScaleBox')" -->
<!--                     display-member-path="name" -->
<!--                     selected-value-path="value" -->
<!--                     is-editable="false" -->
<!--                     initialized="initComboBox(s)"> -->
<!--             </wj-combo-box> -->
            <span class="fl bk lh30" id="strNm"></span>
            <%-- 엑셀 다운로드 //TODO --%>
            <button class="btn_skyblue fr" ng-click="excelDownloadDayDtl()"><s:message code="cmm.excel.down" />
            </button>
            </div>
	            <div class="wj-TblWrapBr mr10 pd10">
	               <div ng-controller="rtnStatusDayDtlCtrl">
	                <div style="height:300px">
	                   <wj-flex-grid
		                  id="rtnStatusDayDtlGrid"
		                  loaded-rows="loadedRows2(s,e)"
		                  autoGenerateColumns="false"
		                  selection-mode="Row"
		                  items-source="data"
		                  control="flex"
		                  initialized="initGrid(s,e)"
		                  is-read-only="true"
		                  item-formatter="_itemFormatter">
		                  <!-- define columns -->
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.saleDate"/>"   binding="saleDate"       width="*" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntY"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtY"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cntN"           width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmtN"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="rtnStatus.totCnt"/>"     binding="cnt"            width="*" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                          <wj-flex-grid-column header="<s:message code="rtnStatus.totAmt"/>"     binding="realSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                </wj-flex-grid>
		                <%-- ColumnPicker 사용시 include --%>
		                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
		                  <jsp:param name="pickerTarget" value="rtnStatusDayDtlCtrl"/>
		                </jsp:include>
		                <%--// ColumnPicker 사용시 include --%>
	                </div>                  
	            </div>
	        </div>
	        <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <ul id="rtnStatusDayDtlCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
	        
        <%-- right --%>
        <div class="w50 fr">
        <div class="mt20 oh sb-select dkbr pd10">
           <%-- 페이지 스케일  --%>
<!--            <wj-combo-box -->
<!--                    class="w100px fl" -->
<!--                    id="rtnStatusPosDtlListScaleBox" -->
<!--                    ng-model="rtnStatusPosDtlListScale" -->
<!--                    items-source="_getComboData('rtnStatusPosDtlListScaleBox')" -->
<!--                    display-member-path="name" -->
<!--                    selected-value-path="value" -->
<!--                    is-editable="false" -->
<!--                    initialized="initComboBox(s)"> -->
<!--            </wj-combo-box> -->
           <span class="fl bk lh30" id="pNo"></span>
           <%-- 엑셀 다운로드 //TODO --%>
           <button class="btn_skyblue fr" ng-click="excelDownloadPosDtl()"><s:message code="cmm.excel.down" />
           </button>
           </div>
	            <div class="wj-TblWrapBr ml10 pd10">
	               <div ng-controller="rtnStatusPosDtlCtrl">
	                <div style="height:300px">
	                   <wj-flex-grid
	                      id="rtnStatusPosDtlGrid"
		                  autoGenerateColumns="false"
		                  selection-mode="Row"
		                  items-source="data"
		                  control="flex"
		                  initialized="initGrid(s,e)"
		                  is-read-only="true"
		                  item-formatter="_itemFormatter">
		                  <!-- define columns -->
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.posNo"/>"       binding="posNo"         width="*" align="center" is-read-only="true"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.bill.no"/>"     binding="billNo"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.saleFg"/>"      binding="saleYn"        width="*" align="center" is-read-only="true"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.totSaleAmt"/>"  binding="totSaleAmt"    width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.dcAmt"/>"       binding="totDcAmt"      width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                  <wj-flex-grid-column header="<s:message code="rtnStatus.realSaleAmt"/>" binding="realSaleAmt"   width="*" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
		                </wj-flex-grid>
		                <%-- ColumnPicker 사용시 include --%>
		                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
		                  <jsp:param name="pickerTarget" value="rtnStatusPosDtlCtrl"/>
		                </jsp:include>
		                <%--// ColumnPicker 사용시 include --%>
	                </div>              
	               </div>
	            </div>
	          <%-- 페이지 리스트 --%>
			  <div class="pageNum mt20">
			    <ul id="rtnStatusPosDtlCtrlPager" data-size="10">
			    </ul>
			  </div>
			  <%--//페이지 리스트--%>
        </div>  
    </div>
    <%-- //wj grid end --%> 
<!-- //contents end -->
  <%-- 페이지 리스트 --%>
<!--   <div class="pageNum mt20"> -->
<!--     <ul id="rtnStatusDayCtrlPager" data-size="10"> -->
<!--     </ul> -->
<!--   </div> -->
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/rtnStatus/rtnStatus.js" charset="utf-8"></script>

