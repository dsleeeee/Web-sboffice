<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup control="wjAddresseeAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:610px;height:550px;" fade-in="false" fade-out="false">
    <div ng-controller="addresseeAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="addresseeAdd.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 사원명 --%>
                    <th>
                        <s:message code="addresseeAdd.empNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchEmpNm" ng-model="empNm" />
                    </td>
                    <%-- 사용자ID --%>
                    <th>
                        <s:message code="addresseeAdd.userId"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                    </td>
                </tr>
                <tr>
                    <%-- 휴대폰번호 --%>
                    <th>
                        <s:message code="addresseeAdd.mpNo"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMpNo" ng-model="mpNo" />
                    </td>
                    <%-- SMS수신 --%>
                    <th>
                        <s:message code="addresseeAdd.smsRecvYn"/>
                    </th>
                    <td>
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w100 fl"
                                    id="srchSmsRecvYnAddresseeAddCombo"
                                    ng-model="smsRecvYnAddresseeAddCombo"
                                    items-source="_getComboData('smsRecvYnAddresseeAddCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 재직구분 --%>
                    <th>
                        <s:message code="addresseeAdd.serviceFg"/>
                    </th>
                    <td>
                        <div class="sb-select dkbr ml5 fl">
                            <wj-combo-box
                                    class="w100 fl"
                                    id="srchServiceFgAddresseeAddCombo"
                                    ng-model="serviceFgAddresseeAddCombo"
                                    items-source="_getComboData('serviceFgAddresseeAddCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <c:if test="${orgnFg eq 'HQ' or orgnFg eq 'STORE' or orgnFg eq 'AGENCY'}">
                        <td></td>
                        <td></td>
                    </c:if>
                    <c:if test="${orgnFg eq 'MASTER'}">
                        <td colspan="2">
                            <div class="sb-input">
                                <%-- 전체 --%>
                                <input type="checkbox" id="totYn" name="totYnChk" ng-model="totYn" ng-change="totYnChk()">
                                <label for="totYn"><s:message code='addresseeAdd.totYn' /></label>
                                <%-- 시스템 --%>
                                <input type="checkbox" id="systemYn" name="systemYnChk" ng-model="systemYn" ng-change="systemYnChk()">
                                <label for="systemYn"><s:message code='addresseeAdd.systemYn' /></label>
                                <%-- 대리점 --%>
                                <input type="checkbox" id="agencyYn" name="agencyYnChk" ng-model="agencyYn" ng-change="agencyYnChk()">
                                <label for="agencyYn"><s:message code='addresseeAdd.agencyYn' /></label>
                                <%-- 본사 --%>
                                <input type="checkbox" id="hqOfficeYn" name="hqOfficeYnChk" ng-model="hqOfficeYn" ng-change="hqOfficeYnChk()">
                                <label for="hqOfficeYn"><s:message code='addresseeAdd.hqOfficeYn' /></label>
                                <%-- 매장 --%>
                                <input type="checkbox" id="storeYn" name="storeYnChk" ng-model="storeYn" ng-change="storeYnChk()">
                                <label for="storeYn"><s:message code='addresseeAdd.storeYn' /></label>
                            </div>
                        </td>
                    </c:if>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('addresseeAddCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:270px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.empNm"/>" binding="empNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.mpNo"/>" binding="mpNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.smsRecvYn"/>" binding="smsRecvYn" data-map="smsRecvYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.orgnFg"/>" binding="orgnFg" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="addresseeAdd.orgnCd"/>" binding="orgnCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 적용 버튼 --%>
            <div class="tc mt20">
                <button id="funcApply" class="btn_blue">
                    <s:message code="cmm.apply" />
                </button>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- SMS수신구분 --%>
    var smsRecvYnData = ${ccu.getCommCodeExcpAll("072")};
    <%-- 재직구분 --%>
    var serviceFgData = ${ccu.getCommCodeExcpAll("007")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/addresseeAdd.js?ver=20210625.01" charset="utf-8"></script>