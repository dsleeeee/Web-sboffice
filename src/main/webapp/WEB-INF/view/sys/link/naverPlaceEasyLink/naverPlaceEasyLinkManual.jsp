<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 네이버플레이스 간편연동 수기등록 팝업 --%>
<wj-popup control="wjNaverPlaceEasyLinkManualLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;height:640px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="naverPlaceEasyLinkManualCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="naverPlaceEasyLink.manualReg"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <p class="s13 bk lh20">1. 네이버 플레이스ID를 입력하세요.</p>
            <p class="s13 bk lh20">2. 등록할 매장을 검색(사업자번호, 주소, 매장코드, 매장명 중 1개 이상 입력)하여 체크하세요. (1개만 선택 가능)</p>
            <p class="s13 bk lh20">3. '저장'을 클릭하면 선택한 매장에 연동됩니다. (기등록 매장은 등록할 수 없습니다.)</p>

            <%-- 등록정보 --%>
            <table class="tblType01 mt10">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 네이버 플레이스ID --%>
                    <th><s:message code="naverPlaceEasyLink.naverPlaceId"/> <span class="red">*</span></th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualPlaceId" ng-model="manual.placeId" maxlength="30"/>
                    </td>
                    <%-- 업체명 --%>
                    <th><s:message code="naverPlaceEasyLink.businessName"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualBusinessName" ng-model="manual.businessName" maxlength="100" placeholder="미입력 시 매장명으로 저장"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 매장 검색조건 --%>
            <table class="tblType01 mt10">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 사업자번호 --%>
                    <th><s:message code="naverPlaceEasyLink.businessNumber"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualBizNo" ng-model="manual.bizNo" ng-keyup="$event.keyCode == 13 && search()"/>
                    </td>
                    <%-- 주소 --%>
                    <th>주소</th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualAddr" ng-model="manual.addr" ng-keyup="$event.keyCode == 13 && search()"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th><s:message code="naverPlaceEasyLink.storeCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualStoreCd" ng-model="manual.storeCd" ng-keyup="$event.keyCode == 13 && search()"/>
                    </td>
                    <%-- 매장명 --%>
                    <th><s:message code="naverPlaceEasyLink.storeNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="manualStoreNm" ng-model="manual.storeNm" ng-keyup="$event.keyCode == 13 && search()"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <%-- 조회 --%>
                <button class="btn_skyblue" ng-click="search()"><s:message code="cmm.search"/></button>
            </div>

            <%-- 매장 검색결과 --%>
            <div class="w100 mt10">
                <div class="wj-gridWrap" style="height:230px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="manualItemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeNm"/>" binding="hqOfficeNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.addr"/>" binding="addr" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeNm"/>" binding="storeNm" width="130" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessNumber"/>" binding="bizNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.regYn"/>" binding="regYn" data-map="regYnDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.lastSaleDate"/>" binding="lastSaleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <div class="wj-dialog-footer">
                <%-- 저장 --%>
                <button class="btn_blue" ng-click="save()"><s:message code="cmm.save"/></button>
                <%-- 닫기 --%>
                <button class="btn_gray wj-hide"><s:message code="cmm.close"/></button>
            </div>

        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/naverPlaceEasyLink/naverPlaceEasyLinkManual.js?ver=20261028.03" charset="utf-8"></script>
