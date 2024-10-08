<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" id="storeSplyPriceCopyView" ng-controller="storeSplyPriceCopyCtrl" style="display:none;padding: 10px 20px 40px;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeSplyPrice.storeSplyPriceCopy"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="copySplyPrice()">
                <s:message code="cmm.copy"/>
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
            <%-- 기준매장선택 --%>
            <th><s:message code="storeSplyPrice.original.store"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="originalStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 적용대상매장선택 --%>
            <th><s:message code="storeSplyPrice.target.store"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="targetStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <p class="s12 bk mt10 lh20">
                    ※ 가격통제 관계 없이 모두 복사됩니다.<br/>
                </p>
            </td>
        </tr>
        </tbody>
    </table>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeSplyPrice/storeSplyPriceCopy.js?ver=20240530.01" charset="utf-8"></script>