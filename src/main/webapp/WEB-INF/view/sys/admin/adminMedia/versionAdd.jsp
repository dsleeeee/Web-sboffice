<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<%-- 버전추가 레이어 --%>
<wj-popup control="versionAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:1000px;height:680px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="adminMedia.version.registed" />
            <span id="versionDetailTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" >
            <%-- 탭 --%>
            <ul class="subTab">
                <%-- 버전정보 --%>
                <li><a id="storeInfo" href="#" onclick="changeTab2('I')"><s:message code="media.verInfo" /></a></li>
                <%-- 적용매장 --%>
                <li><a id="storeEnv" href="#" onclick="changeTab2('V');"><s:message code="media.store.registed" /></a></li>
                <%-- 적용버전 --%>
                <li><a id="verRegist" href="#" class="on"  ><s:message code="adminMedia.version.registed" /></a></li>
            </ul>

            <div  ng-controller="regVersionCtrl">
                <div class="oh">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 버전일렬번호 --%>
                            <th><s:message code="adminMedia.verSerNo" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminVerSerNo" />
                            </td>
                            <%-- 버전적용명 --%>
                            <th><s:message code="adminMedia.verSerNm" /></th>
                            <td>
                                <input type="text" class="sb-input w100" id="adminVerSerNm" />
                            </td>
                        </tr>
                        <tr>
                            <%-- 프로그램구분 --%>
                            <th><s:message code="adminMedia.progFg" /></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchProgFg"
                                            ng-model="progFg"
                                            items-source="_getComboData('progFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchProgFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 프로그램 상세구분 --%>
                            <th><s:message code="adminMedia.progDetailFg" /></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchProgDetailFg"
                                            ng-model="progDetailFg"
                                            items-source="_getComboData('progDetailFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchProgDetailFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="mt10 tr">
                        <%-- 조회 --%>
                        <button id="btnSearchStore" class="btn_skyblue" onclick="searchVersion()"><s:message code="cmm.search" /></button>
                    </div>
                </div>

                <%-- 등록매장 그리드 --%>
                <div class="oh mt10 w50 fl">
                    <div class="wj-TblWrap mr10" style="height:405px; overflow-y: hidden;">
                        <div class="oh mb10">
                            <span class="fl bk lh20 s14"><s:message code="adminMedia.version.registed"/></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
                        </div>
                        <div id="regProdGrid" style="height: 370px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.verSerNo"/>" binding="verSerNo" align="left" width="120" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.verSerNm"/>" binding="verSerNm" align="left" width="120" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.progFg"/>" binding="progFg" align="center" width="100" data-map="progFgDataMap" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.progDetailFg"/>" binding="progDetailFg" align="center" width="100" data-map="progDetailFgDataMap" is-read-only="true"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>

            <%--- 미등록매장 그리드 --%>
            <div class="oh mt10 w50 ">
                <div class=" ">
                    <div class="wj-TblWrap ml10" style="height:405px; overflow-y: hidden;" ng-controller="allVersionCtrl">
                        <div class="oh mb10">
                            <span class="fl bk lh20 s14"><s:message code="adminMedia.version.noRegisted" /></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="save()" ><s:message code="adminMedia.version.reg" /></a></span>
                        </div>
                        <div id="noRegProdGrid" style="height: 370px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.verSerNo"/>" binding="verSerNo" align="left" width="120" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.verSerNm"/>" binding="verSerNm" align="left" width="120" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.progFg"/>" binding="progFg" align="center" width="100" data-map="progFgDataMap" is-read-only="true" ></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="adminMedia.progDetailFg"/>" binding="progDetailFg" align="center" width="100" data-map="progDetailFgDataMap" is-read-only="true"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/adminMedia/versionAdd.js?ver=20250326.01" charset="utf-8"></script>

<script>
    var orgnFg = "${orgnFg}";
    var progFgAll       = ${ccu.getCommCode("059")};
    var progDetailFgAll    = ${ccu.getCommCode("405")};


</script>
