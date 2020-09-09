<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup control="memberVendorMappingLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1100px;height:570px;" fade-in="false" fade-out="false">
    <div ng-controller="memberVendorMappingCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.memberVendorMapping" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"> <s:message code="regist.memberVendorMapping" /></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_pageView('memberVendorMappingCtrl',1)">
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
                        <%-- 회원번호 --%>
                        <th>
                            <s:message code="regist.membr.no" />
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="vendorMemberNo" ng-model="memberNo" />
                        </td>
                        <%-- 회원명 --%>
                        <th>
                            <s:message code="regist.membr.nm" />
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="vendorMemberNm" ng-model="memberNm" />
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:330px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="regist.cdPartner"/>" binding="cdPartner" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.lnPartner"/>" binding="lnPartner" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.membr.no"/>" binding="membrNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.membr.nm"/>" binding="membrNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.class.cd"/>" binding="membrClassCd" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.class.nm"/>" binding="membrClassNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.tel"/>" binding="telNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.membr.stortNo"/>" binding="shortNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreCd" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.membr.regStore"/>" binding="regStoreNm" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.email.recv"/>" binding="emailRecvYn" data-map="emailRecvDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.sms.recv"/>" binding="smsRecvYn" data-map="smsRecvDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="regist.useYn"/>" binding="useYn" data-map="useYnDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="regist.membr.store"/>" binding="postpaidStore" is-read-only="true" align="center" ></wj-flex-grid-column>
                        </c:if>

                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="memberVendorMappingCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>

    </div>
</wj-popup>

<script>
    var recvDataMap = ${ccu.getCommCode("072")}; <%--수신, 미수신--%>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")}; <%--사용, 미사용--%>
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberVendorMapping.js?ver=20200909.01" charset="utf-8"></script>

<%-- 매장 등록/수정 --%>
<%--<c:import url="/WEB-INF/view/membr/info/view/memberRegist.jsp">--%>
<%--    <c:param name="menuCd" value="${menuCd}"/>--%>
<%--    <c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>