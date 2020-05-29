<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="userLayer" ng-controller="userCtrl">

    <%-- 탭 --%>
    <div class="tabType1">
        <ul>
            <%-- 사용자 --%>
            <li><a id="userDetail" href="#" class="on"><s:message code="userBase.user" /></a></li>
            <%-- 사용메뉴 --%>
            <li><a id="useMenu" href="#" ng-click="useMenuTab();"><s:message code="userBase.useMenu" /></a></li>
        </ul>
    </div>

    <%-- 그리드 --%>
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="userBase.user.useDate"/>" binding="useDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.user.level1Nm"/>" binding="level1Nm" width="*" is-read-only="true" align="center" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.user.level2Nm"/>" binding="level2Nm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.user.level3Nm"/>" binding="level3Nm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.user.userId"/>" binding="userId" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.user.userNm"/>" binding="userNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userBase/user.js?ver=20200528.08" charset="utf-8"></script>