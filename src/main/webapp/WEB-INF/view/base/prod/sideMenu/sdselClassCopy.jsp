<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup control="wjSdselClassCopyLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:780px;height:630px;" fade-in="false" fade-out="false">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
        <s:message code="sideMenu.sdselClassCopy.info"/>
        <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
        <div class="w100 fl" ng-controller="sdselClassCopyCtrl">
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
                        <s:message code="sideMenu.sdselClassCopy.copyGroup"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchApplyGroup" ng-model="applyGroup" readonly />
                        <input type="text" class="sb-input w100" id="srchApplyGroupCd" ng-model="applyGroupCd" readonly style="display: none" />
                    </td>
                    <td>
                        <%-- 선택분류복사 --%>
                        <button class="btn_blue fr" id="btnSave" ng-click="classCopySave()"><s:message code="sideMenu.sdselClassCopy.copy" /></button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="w100 fl">
            <div class="mt10 mb10 oh sb-select dkbr">
                <p class="tl s14 mt5 lh15 red">복사할 선택분류를 체크 하신 후, 저장 버튼을 클릭하시면 '적용할 그룹' 에 선택분류가 복사 됩니다.</p>
            </div>
        </div>
        <%-- 좌측 --%>
        <div class="w40 fl">
            <%-- 좌측 상단 --%>
            <div>
                <%--위즈모 테이블--%>
                <div id="gridPrint" class="wj-TblWrapBr pd5" style="height: 230px;" ng-controller="sdselClassCopyGroupCtrl">
                    <div class="updownSet oh mb10" style="height:30px;">
                        <span class="fl bk lh30"><s:message code='sideMenu.sdselClassCopy.sdselGrp' /></span>
                    </div>
                    <%-- 개발시 높이 조절해서 사용--%>
                    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                    <div style="height:170px">
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
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselGrpNm"/>" binding="sdselGrpNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            <c:if test="${hqOfficeCd == 'A0001' and orgnFg == 'HQ'}">
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                            </c:if>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselQty"/>" binding="cnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" width="70" is-read-only="true" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <%-- //좌측 상단 --%>
            <%-- 좌측 하단 --%>
            <div>
                <%--위즈모 테이블--%>
                <div id="gridMapng" class="wj-TblWrapBr pd5" style="height: 230px;" ng-controller="sdselClassCopyClassCtrl">
                    <div class="updownSet oh mb10" style="height:30px;">
                        <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.sdselClassCopy.sdselClass' /><span id="sideSelectGroupCopyTitle"></span> </span>
                    </div>
                    <%-- 개발시 높이 조절해서 사용--%>
                    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                    <div style="height:130px;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true"
                                id="wjGridSelClassCopyList">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselClassNm"/>" binding="sdselClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselQty"/>" binding="sdselQty" width="50" max-length="3" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselQty"/>" binding="cnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.sdselQty"/>" binding="fixProdCnt" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
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
                <div id="gridMapng" class="wj-TblWrapBr ml10 pd5" style="height: 460px;" ng-controller="sdselClassCopyProdCtrl">
                    <div class="updownSet oh mb10" style="height:30px;">
                        <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.sdselClassCopy.sdselProd' /><span id="sideClassCopyTitle"></span> </span>
                    </div>
                    <%-- 개발시 높이 조절해서 사용--%>
                    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
                    <div style="height:370px;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true"
                                id="wjGridSelProdCopyList">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.addProdUprc"/>" binding="addProdUprc" width="50" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.addProdQty"/>" binding="addProdQty" width="50" max-length="3" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassCopy.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
        <%-- //우측 --%>
    </div>
    <%-- //body --%>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sdselClassCopy.js?ver=20230404.02" charset="utf-8"></script>