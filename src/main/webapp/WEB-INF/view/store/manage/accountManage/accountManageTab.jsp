<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="accountManageTabCtrl" ng-init="init()">
        <ul>
            <%-- 장기미사용 탭 --%>
            <li>
                <a id="longTermUnusedTab" href="#" class="on" ng-click="longTermUnusedShow()"><s:message code="accountManage.longTermUnused"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 매장상태구분
    var sysStatFg = ${ccu.getCommCode("005")};
    // 재직구분
    var serviceFgData = ${ccu.getCommCode("007")};
    // 사용여부
    var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/accountManage/accountManageTab.js?ver=20240214.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 장기미사용 레이어 --%>
<c:import url="/WEB-INF/view/store/manage/accountManage/longTermUnused.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>

