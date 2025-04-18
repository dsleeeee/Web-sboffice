<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayPeriod/dayPeriodTime/"/>

<div id="dayPeriodTimeView" name="dayPeriodView" class="subCon" style="display: none;padding: 10px 20px 40px;">

     <div ng-controller="dayPeriodTimeCtrl">
          <%-- 조회조건 --%>
          <div class="searchBar">
               <a href="#" class="open fl"> <s:message code="dayPeriod.timeSale" /></a>
               <%-- 조회 --%>
               <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('dayPeriodTimeCtrl',1)">
                         <s:message code="cmm.search" />
                    </button>
               </div>
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
                         <th>
                              <s:message code="cmm.search.date" />
                         </th>
                         <td>
                              <div class="sb-select">
                                   <span class="txtIn"> <input id="startDateDayPeriodTime" name="startDate" class="w110px" /></span>
                                   <span class="rg">~</span>
                                   <span class="txtIn"> <input id="endDateDayPeriodTime" name="endDate" class="w110px" /></span>
                              </div>
                         </td>
                         <%-- 옵션 --%>
                         <th><s:message code="day.time.optionFg"/></th>
                         <td>
                              <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                              <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
                         </td>
                    </tr>
                    <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                         <%-- 매장선택 --%>
                         <th><s:message code="cmm.store.select"/></th>
                         <td colspan="3">
                              <%-- 매장선택 모듈 사용시 include --%>
                              <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                                   <jsp:param name="targetTypeFg" value="M"/>
                                   <jsp:param name="targetId" value="dayPeriodTimeStore"/>
                              </jsp:include>
                              <%--// 매장선택 모듈 사용시 include --%>
                         </td>
                    </tr>
                    <tr id="timeOption">
                         <th><s:message code="day.time.time"/></th>
                         <td colspan="3">
                              <div class="sb-select fl w200px">
                                   <div class="sb-slect fl" style="width:65px;">
                                        <wj-combo-box
                                                id="startTime"
                                                ng-model="startTime"
                                                items-source="_getComboData('startTimeCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="startTimeCombo"
                                                initialized="_initComboBox(s)">
                                        </wj-combo-box>
                                   </div>
                                   <div class="fl pd5" style="padding-right: 15px;">
                                        <label> ~ </label>
                                   </div>
                                   <div class="sb-select fl" style="width:65px;">
                                        <wj-combo-box
                                                id="endTime"
                                                ng-model="endTime"
                                                items-source="_getComboData('endTimeCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="endTimeCombo"
                                                initialized="_initComboBox(s)">
                                        </wj-combo-box>
                                   </div>
                              </div>
                         </td>
                    </tr>
                    <tr id="timeSlotOption" style="display: none">
                         <th><s:message code="day.time.time"/></th>
                         <td colspan="3">
                              <div class="sb-select fl w120px" >
                                   <wj-combo-box
                                           id="timeSlotCombo"
                                           ng-model="timeSlot"
                                           control="timeSlotCombo"
                                           items-source="_getComboData('timeSlotCombo')"
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

          <%--left--%>
          <div class="wj-TblWrap mt10 mb20 w45 fl">
               <div class="wj-TblWrapBr mr10 pd10" style="height:470px;">
                    <div class="updownSet oh mb10">
                         <span class="fl bk lh30"><s:message code="dayPeriod.time"/></span>
                         <%-- 시간대별 엑셀다운로드 --%>
                         <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleTime()"><s:message code="cmm.excel.down"/></button>
                    </div>
                    <div class="w100 mt10 mb20">
                         <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                              <wj-flex-grid
                                   autoGenerateColumns="false"
                                   control="flex"
                                   initialized="initGrid(s,e)"
                                   selection-mode="Row"
                                   items-source="data"
                                   item-formatter="_itemFormatter">

                                   <!-- define columns -->
                                   <wj-flex-grid-column header="<s:message code="dayPeriod.saleTime"/>" binding="saleTime" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="dayPeriod.billCnt"/>" binding="billCnt" width="75" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="dayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="dayPeriod.billUprc"/>" binding="billUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                              </wj-flex-grid>
                              <%-- ColumnPicker 사용시 include --%>
                              <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                                   <jsp:param name="pickerTarget" value="dayPeriodTimeCtrl"/>
                              </jsp:include>
                              <%--// ColumnPicker 사용시 include --%>
                         </div>
                    </div>
               </div>
          </div>
          <%--left--%>
     </div>

     <%--right--%>
     <div class="wj-TblWrap mt10 mb20 w55 fr" ng-controller="dayPeriodTimeDetailCtrl">
          <div class="wj-TblWrapBr pd10" style="height:470px; overflow-y: hidden;">
               <div class="updownSet oh mb10">
                    <span class="fl bk lh30"><s:message code="dayPeriod.saleDtl"/></span>
                    <%-- 매출상세 엑셀다운로드 --%>
                    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleDtl()"><s:message code="cmm.excel.down"/></button>
               </div>
               <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                         <wj-flex-grid
                              autoGenerateColumns="false"
                              control="flex"
                              initialized="initGrid(s,e)"
                              selection-mode="Row"
                              items-source="data"
                              item-formatter="_itemFormatter">

                              <!-- define columns -->
                              <wj-flex-grid-column header="<s:message code="dayPeriod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="dayPeriod.prodNm"/>" binding="prodNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="dayPeriod.saleQty"/>" binding="saleQty" width="75" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="dayPeriod.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                         </wj-flex-grid>
                         <%-- ColumnPicker 사용시 include --%>
                         <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                              <jsp:param name="pickerTarget" value="dayPeriodTimeDetailCtrl"/>
                         </jsp:include>
                         <%--// ColumnPicker 사용시 include --%>
                    </div>
               </div>
          </div>
     </div>
     <%--right--%>

</div>

<script type="text/javascript">
     var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayPeriod/dayPeriodTime.js?ver=20250415.01" charset="utf-8"></script>