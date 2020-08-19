<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="useMenuLayer" ng-controller="useMenuCtrl">

    <%-- 탭 --%>
    <div class="tabType1">
        <ul>
            <%-- 사용자 --%>
            <li><a id="user" href="#" ng-click="userTab();"><s:message code="userBase.user" /></a></li>
            <%-- 사용메뉴 --%>
            <li><a id="useMenu" href="#" class="on"><s:message code="userBase.useMenu" /></a></li>
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
            <wj-flex-grid-column header="<s:message code="userBase.useMenu.level1Nm"/>" binding="level1Nm" width="*" is-read-only="true" align="center" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.useMenu.level2Nm"/>" binding="level2Nm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.useMenu.level3Nm"/>" binding="level3Nm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="userBase.useMenu.useCnt"/>" binding="useCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userBase/useMenu.js?ver=20200529.08" charset="utf-8"></script>