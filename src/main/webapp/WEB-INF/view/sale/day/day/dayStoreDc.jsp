<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayStoreDc/"/>

<wj-popup control="wjDayStoreDcLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="dayStoreDcCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="day.dayStoreDc.dayStoreDc"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="day.dayStoreDc.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayStoreDc.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="day.dayStoreDc.totDcAmt"/>" binding="totDcAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <%-- 할인 컬럼 생성--%>
                        <c:forEach var="dcCol" items="${dcColList}">
                          <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        </c:forEach>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayStoreDc.js?ver=20191219" charset="utf-8"></script>