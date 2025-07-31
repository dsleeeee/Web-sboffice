<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="dcDetailMrpizzaSelectStoreView" name="dcDetailMrpizzaSelectStoreView" class="subCon" style="display: none;">

     <div ng-controller="dcDetailMrpizzaSelectStoreCtrl">
         <%-- 조회조건 --%>
         <div class="searchBar">
             <a href="#" class="open fl"> <s:message code="dcDetailMrpizza.selectStore"/></a>
             <%-- 조회 --%>
             <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                 <button class="btn_blue fr" ng-click="_broadcast('dcDetailMrpizzaSelectStoreCtrl',1)">
                     <s:message code="cmm.search"/>
                 </button>
             </div>
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
                         <span class="txtIn"> <input id="startDateDcDetailMrpizzaSelectStore" name="startDate" class="w110px"/></span>
                         <span class="rg">~</span>
                         <span class="txtIn"> <input id="endDateDcDetailMrpizzaSelectStore" name="endDate" class="w110px"/></span>
                     </div>
                 </td>
             </tr>
             <tr>
                 <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                     <tr>
                             <%-- 매장선택 --%>
                         <th style="border-left:1px solid #ccc"><s:message code="cmm.store.select"/></th>
                         <td colspan="3">
                                 <%-- 매장선택 모듈 사용시 include --%>
                             <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                                 <jsp:param name="targetTypeFg" value="M"/>
                                 <jsp:param name="targetId" value="dcDetailMrpizzaSelectStore"/>
                             </jsp:include>
                                 <%--// 매장선택 모듈 사용시 include --%>
                         </td>
                     </tr>
                 </c:if>
                 <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                     <input type="hidden" id="dcDetailMrpizzaSelectStoreCd" value="${sessionInfo.storeCd}"/>
                 </c:if>
             </tr>
         </table>
     </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaSelectStore.js?ver=20250730.01" charset="utf-8"></script>
