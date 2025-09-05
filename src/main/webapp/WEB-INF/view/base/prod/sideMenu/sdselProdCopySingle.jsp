<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<wj-popup control="wjSdselProdCopySingleLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:760px;height:660px;" fade-in="false" fade-out="false">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <s:message code="sideMenu.sdselProdCopy.info"/>
        <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
        <div ng-controller="sdselProdCopySingleCtrl">
            <table class="tblType01">
                <colgroup>
                    <col class="w17"/>
                    <col class="w26"/>
                    <col class="w17"/>
                    <col class="w26"/>
                    <col class="w14"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 그룹명,그룹코드 --%>
                    <th>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchCopyTypeSelGroup"
                                    items-source="_getComboData('srchCopyTypeSelGroup')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchCopyTypeSelGroupCombo">
                            </wj-combo-box>
                        </div>
                    </th>
                    <td>
                        <input type="text" id="txtCopySelGroupProdSingle" class="fl sb-input w25 mr5" style="font-size: 12px;" onkeyup="fnNxBtnSearch(8);"/>
                    </td>
                    <%-- 상품명,상품코드 --%>
                    <th>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchCopyTypeSelProd"
                                    items-source="_getComboData('srchCopyTypeSelProd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchCopyTypeSelProdCombo">
                            </wj-combo-box>
                        </div>
                    </th>
                    <td>
                        <input type="text" id="txtCopySelProdProdSingle" class="fl sb-input w25 mr5" style="font-size: 12px;" onkeyup="fnNxBtnSearch(8);"/>
                    </td>
                    <td>
                        <%-- 조회 --%>
                        <button class="btn_blue fr" id="nxBtnSearch8" ng-click="searchSdselProdCopySingle()"><s:message code="cmm.search" /></button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="w100 fl mt10">
                <div class="mb10 oh sb-select dkbr">
                    <p class="tl s14 mt5 lh15 red">복사할 선택상품을 체크 하신 후, '선택상품복사' 버튼을 클릭하시면 '적용할 분류' 에 선택상품이 복사 됩니다.</p>
                </div>
            </div>
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w50"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 적용할 그룹 --%>
                    <th>
                        <s:message code="sideMenu.sdselProdCopy.copyGroup"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchApplyGroupProdSingle" ng-model="applyGroup" readonly />
                        <input type="text" class="sb-input w100" id="srchApplyGroupProdCdSingle" ng-model="applyGroupCd" readonly style="display: none" />
                    </td>
                </tr>
                <tr>
                    <%-- 적용할 분류 --%>
                    <th>
                        <s:message code="sideMenu.sdselProdCopy.copyClass"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchApplyClassProdSingle" ng-model="applyClass" readonly />
                        <input type="text" class="sb-input w100" id="srchApplyClassProdCdSingle" ng-model="applyClassCd" readonly style="display: none" />
                    </td>
                    <td>
                        <%-- 선택상품복사 --%>
                        <button class="btn_blue fr" id="btnSave" ng-click="prodCopySingleSave()"><s:message code="sideMenu.sdselProdCopy.copy" /></button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="w100 fl mt10">
            <%-- 좌측 --%>
            <div class="w40 fl">
                <%-- 좌측 상단 --%>
                <div>
                    <%--위즈모 테이블--%>
                    <div id="gridPrint" class="wj-TblWrapBr pd5" style="height: 220px;" ng-controller="sdselProdCopySingleGroupCtrl">
                        <div class="updownSet oh mb10" style="height:30px;">
                            <span class="fl bk lh30"><s:message code='sideMenu.sdselProdCopy.sdselGrp' /></span>
                        </div>
                        <%-- 개발시 높이 조절해서 사용--%>
                        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                        <div style="height:160px">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true"
                                    id="wjGridSelGroupCopyList">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselGrpNm"/>" binding="sdselGrpNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <c:if test="${hqOfficeCd == 'A0001' and orgnFg == 'HQ'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselQty"/>" binding="cnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" width="70" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <c:if test="${hqOfficeCd == 'A0001'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.progressStage"/>" binding="halfAndHalfYn" data-map="useYnDataMap" width="70" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                            </wj-flex-grid>
                        </div>
                    </div>
                    <%--//위즈모 테이블--%>
                </div>
                <%-- //좌측 상단 --%>
                <%-- 좌측 하단 --%>
                <div>
                    <%--위즈모 테이블--%>
                    <div id="gridMapng" class="wj-TblWrapBr pd5" style="height: 220px;" ng-controller="sdselProdCopySingleClassCtrl">
                        <div class="updownSet oh mb10" style="height:30px;">
                            <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.sdselProdCopy.sdselClass' /><span id="sideSelectGroupCopyTitleProdSingle"></span> </span>
                        </div>
                        <%-- 개발시 높이 조절해서 사용--%>
                        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                        <div style="height:160px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true"
                                    id="wjGridSelClassCopySingleListProd">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselClassNm"/>" binding="sdselClassNm" width="70" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselQty"/>" binding="sdselQty" width="50" max-length="3" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselQty"/>" binding="cnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.sdselQty"/>" binding="fixProdCnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" is-read-only="true" visible="false"></wj-flex-grid-column>
                                </c:if>
                                <c:if test="${(orgnFg == 'HQ' and hqOfficeCd == 'A0001') or (orgnFg == 'HQ' and hqOfficeCd == 'DS019') or (orgnFg == 'HQ' and hqOfficeCd == 'DS001')}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.topYn"/>" binding="topYn" data-map="topYnDataMap" width="85" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <c:if test="${(orgnFg == 'HQ' and hqOfficeCd == 'A0001') or (orgnFg == 'HQ' and hqOfficeCd == 'DS019') or (orgnFg == 'HQ' and hqOfficeCd == 'DS001')}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.expandYn"/>" binding="expandYn" data-map="expandYnDataMap" width="75" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.mappingYn"/>" binding="mappingYn" data-map="mappingYnDataMap" width="110" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <c:if test="${hqOfficeCd == 'H0614' or hqOfficeCd == 'H0616' or hqOfficeCd == 'DS008'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.popUpClassYn"/>" binding="popUpClassYn" data-map="popUpClassYnDataMap" width="100" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <c:if test="${hqOfficeCd == 'A0001'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.progressStage"/>" binding="popUpClassYn" data-map="popUpClassYnDataMap" width="100" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                            </wj-flex-grid>
                        </div>
                    </div>
                    <%--//위즈모 테이블--%>
                </div>
                <%-- //좌측 하단 --%>
            </div>
            <%-- //좌측 --%>
            <%-- 우측 --%>
            <div class="w60 fr">
                <div>
                    <%--위즈모 테이블--%>
                    <div id="gridMapng" class="wj-TblWrapBr ml10 pd5" style="height: 440px;" ng-controller="sdselProdCopySingleProdCtrl">
                        <div class="updownSet oh mb10" style="height:30px;">
                            <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.sdselProdCopy.sdselProd' /><span id="sideClassCopyTitleProdSingle"></span> </span>
                        </div>
                        <%-- 개발시 높이 조절해서 사용--%>
                        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                        <div style="height:380px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true"
                                    id="wjGridSelProdCopyListProd">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <c:if test="${brandUseFg == '1'}">
                                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                        <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.brand"/>" binding="hqBrandCd" data-map="brandDataMap" width="80" is-read-only="true"></wj-flex-grid-column>
                                    </c:if>
                                </c:if>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.addProdUprc"/>" binding="addProdUprc" width="50" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.addProdQty"/>" binding="addProdQty" width="50" max-length="3" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdCopy.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap" is-read-only="true"></wj-flex-grid-column>
                                <c:if test="${orgnFg == 'HQ' and hqOfficeCd == 'DS021'}">
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.oldRegStoreFg"/>" binding="oldRegStoreFg" data-map="oldRegStoreFgDataMap" width="85" visible="false" is-read-only="true"></wj-flex-grid-column>
                                </c:if>
                                <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.remark"/>" binding="remark" width="100" align="left" is-read-only="true" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                    <%--//위즈모 테이블--%>
                </div>
            </div>
            <%-- //우측 --%>
        </div>
    </div>
    <%-- //body --%>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sdselProdCopySingle.js?ver=20250905.01" charset="utf-8"></script>