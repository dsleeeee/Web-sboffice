<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>

<div class="wj-dialog-body" id="cardView" name="cardView" class="subCon" ng-controller="memberCardCtrl">
    <%-- body --%>
    <div style="height:520px; overflow-y: auto;">
        <f:form id="regCardForm" name="regCardForm">
<%--                                <h3 class="h3_tbl"><s:message code="storeManage.basicInfo"/></h3>--%>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #ccc;">
                    <%-- 발급구분 --%>
                    <th><s:message code="regist.card.fg"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="cstCardIssFg"
                                    ng-model="member.cstCardIssFg"
                                    control="rMembrcardIssFgCombo"
                                    items-source="_getComboData('cstCardIssFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 발급소속 --%>
                    <th><s:message code="regist.card.org"/><em class="imp">*</em></th>
                    <td>
                        <c:if test="${orgnFg eq 'HQ'}">
                            <div class="sb-select">
                                <wj-combo-box
                                        id="basicRegStoreCd"
                                        ng-model="member.issOrgnCd"
                                        control="basicRegStoreCdCombo"
                                        items-source="_getComboData('basicRegStoreCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </c:if>
                        <c:if test="${orgnFg ne 'HQ'}">
                            <input type="text" id="rMembrCardOrgn" class="sb-input w100" ng-model="member.issOrgnCd" readonly required />
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <%-- 신규카드번호 --%>
                    <th><s:message code="regist.card.new.no"/><em class="imp">*</em></th>
                    <td>
                        <input type="text" id="rMembrCardNo" name="membrCardNo" ng-model="member.membrCardNo" class="sb-input w100" maxlength="30" required>
                    </td>
                    <%-- 이전카드번호 --%>
                    <th><s:message code="regist.card.old.no"/></th>
                    <td>
                        <input type="text" id="rOldCstCardNo" name="oldCstCardNo" ng-model="member.oldCstCardNo" readonly class="sb-input w100" maxlength="30"/>
                    </td>
                </tr>
                <tr>
                    <%-- 발급일자 --%>
                    <th><s:message code="regist.card.iss.date"/><em class="imp">*</em></th>
                    <td>
                        <div class="sb-select">
                            <wj-input-date
                                    value="issDate"
                                    ng-model="member.issDate"
                                    control="issDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
<%--                        <input type="text" id="rIssDate" name="issDate" ng-model="member.issDate" class="sb-input w100" maxlength="30" required>--%>
                    </td>
                    <td>{{}}</td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="regist.remark"/></th>
                    <td colspan="3">
                        <input type="text" id="rIssRemark" name="issRemark" ng-model="member.issRemark" class="sb-input w100" maxlength="100"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </f:form>

        <%-- Card Histroy --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:230px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        control="flex"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
<%--                        frozen-columns="3"--%>
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
                    <wj-flex-grid-column header="<s:message code="regist.card.new.no"/>" binding="membrCardNo" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.old.no"/>" binding="oldCstCardNo" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.iss.fg"/>" binding="statFgNm" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.fg"/>" binding="issFgNm" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.org"/>" binding="membrOrgnCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.iss.date"/>" binding="issDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="regist.card.iss.remark"/>" binding="issRemark" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
    <div class="btnSet">
        <%-- 저장 --%>
        <span <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
            <a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save"/></a>
        </span>
    </div>
</div>

<script>
    <%--var cstCardIssFg = ${ccu.getCommCode("300")};--%>
    <%--var cstCardStatFg = ${ccu.getCommCode("301")};--%>
    var sessionInfo = {
        orgnCd: "${sessionScope.sessionInfo.orgnCd}",
        orgnNm: "${sessionScope.sessionInfo.orgnNm}"
    };
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberCard.js?ver=20201112.03" charset="utf-8"></script>