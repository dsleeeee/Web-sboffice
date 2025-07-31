<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="dcDetailMrpizzaTabCtrl" ng-init="init()">
        <ul>
            <%-- 전체점포 탭 --%>
            <li>
                <a id="dcDetailMrpizzaAllStoreTab" href="#" class="on" ng-click="dcDetailMrpizzaAllStoreShow()"><s:message code="dcDetailMrpizza.allStore"/></a>
            </li>
            <%-- 선택점포 탭 --%>
            <%--<li>
                <a id="dcDetailMrpizzaSelectStoreTab" href="#" ng-click="dcDetailMrpizzaSelectStoreShow()"><s:message code="dcDetailMrpizza.selectStore"/></a>
            </li>--%>
            <%-- 할인구분 탭 --%>
            <%--<li>
                <a id="dcDetailMrpizzaDcTypeTab" href="#" ng-click="dcDetailMrpizzaDcTypeShow()"><s:message code="dcDetailMrpizza.dcType"/></a>
            </li>--%>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaTab.js?ver=20250730.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 전체점포 레이어 --%>
<c:import url="/WEB-INF/view/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaAllStore.jsp">
</c:import>

<%-- 선택점포 레이어 --%>
<c:import url="/WEB-INF/view/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaSelectStore.jsp">
</c:import>

<%-- 할인구분 레이어 --%>
<c:import url="/WEB-INF/view/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaDcType.jsp">
</c:import>
<%-- //탭페이지 레이어 --%>