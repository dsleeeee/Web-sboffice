<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="wjRecpOriginRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="recpOriginRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="recpOrigin.recpOrigin" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 재료명 --%>
                    <th><s:message code="recpOrigin.recipesNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="recipesNm" />
                    </td>
                    <%-- 원산지명 --%>
                    <th><s:message code="recpOrigin.orgplceNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="orgplceNm" />
                    </td>
                </tr>
                </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <%-- 조회 --%>
                <button class="btn_skyblue" id="btnSearch" ng-click="search()"><s:message code="cmm.search" /></button>
                <%-- 추가 --%>
                <button class="btn_skyblue" id="btnAddRow" ng-click="addRow()"><s:message code="cmm.add" /></button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code='cmm.save' /></button>
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnDel" ng-click="del()"><s:message code='cmm.del' /></button>

            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.recipesCd"/>" binding="recipesCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.recipesNm"/>" binding="recipesNm" width="160"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOrigin.orgplceNm"/>" binding="orgplceNm" width="110"></wj-flex-grid-column>
                        <c:if test="${hqOfficeCd eq 'A0001' and orgnFg eq 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="recpOrigin.hqBrandNm"/>" binding="hqBrandCd" data-map="hqBrandFgMap" width="100" align="center" ></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/recpOriginReg.js?ver=20230110.02" charset="utf-8"></script>