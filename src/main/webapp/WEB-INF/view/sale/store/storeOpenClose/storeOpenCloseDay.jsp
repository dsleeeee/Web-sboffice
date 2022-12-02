<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="storeOpenCloseDayView" class="subCon">
    <div ng-controller="storeOpenCloseDayTimeCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeOpenClose.day"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeOpenCloseDayTimeCtrl')"><s:message code="cmm.search"/></button>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </c:if>
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="dayStartDate" name="dayStartDate" class="w110px" /></span>
                </div>
            </td>
            <%-- 옵션 --%>
            <th><s:message code="storeOpenClose.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장브랜드 --%>
                <th><s:message code="dayProd.storeHqBrand"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchStoreHqBrandCdCombo"
                                ng-model="storeHqBrandCd"
                                items-source="_getComboData('storeHqBrandCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 매장코드 --%>
                <th><s:message code="cmm.store"/></th>
                <td>
                        <%-- 매장선택 모듈 싱글 선택 사용시 include
                             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수
                        --%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                        <jsp:param name="targetId" value="storeOpenCloseDayStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dayMomsStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                    <%-- 팀별 --%>
                <th><s:message code="dayProd.momsTeam"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsTeamCombo"
                                ng-model="momsTeam"
                                items-source="_getComboData('momsTeamCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsTeamCombo">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- AC점포별 --%>
                <th><s:message code="dayProd.momsAcShop"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAcShopCombo"
                                ng-model="momsAcShop"
                                items-source="_getComboData('momsAcShopCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAcShopCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                    <%-- 지역구분 --%>
                <th><s:message code="dayProd.momsAreaFg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAreaFgCombo"
                                ng-model="momsAreaFg"
                                items-source="_getComboData('momsAreaFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAreaFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 상권 --%>
                <th><s:message code="dayProd.momsCommercial"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsCommercialCombo"
                                ng-model="momsCommercial"
                                items-source="_getComboData('momsCommercialCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsCommercialCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                    <%-- 점포유형 --%>
                <th><s:message code="dayProd.momsShopType"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsShopTypeCombo"
                                ng-model="momsShopType"
                                items-source="_getComboData('momsShopTypeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsShopTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 매장관리타입 --%>
                <th><s:message code="dayProd.momsStoreManageType"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreManageTypeCombo"
                                ng-model="momsStoreManageType"
                                items-source="_getComboData('momsStoreManageTypeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreManageTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                        <%-- 지사 --%>
                    <th><s:message code="dayProd.branchCd"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchBranchCdComboo"
                                    ng-model="branchCd"
                                    items-source="_getComboData('branchCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchBranchCdComboo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </c:if>
    </div>

    <div class="w40 fl" ng-controller="storeOpenCloseDayCtrl">
        <div class="wj-TblWrapBr mr10 pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.dayTime' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.downCondition"/></button>
            </div>
            <div class="wj-TblWrapBr">
                <%--위즈모 테이블--%>
                <div style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter"
                            allowMerging="Cells">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="saleDate" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="min" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="max" width="80" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.time"/>" binding="time" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openCnt"/>" binding="openCnt" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeCnt"/>" binding="closeCnt" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.open"/>" binding="open" width="50" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.close"/>" binding="close" width="50" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.noOpen"/>" binding="none" width="60" align="center"></wj-flex-grid-column>

                        <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="storeOpenCloseDayCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div class="w60 fl" ng-controller="storeOpenCloseDayDtlCtrl">
        <div class="wj-TblWrapBr pd20">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='storeOpenClose.storeDtlList' /></span>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDay()"><s:message code="cmm.excel.downCondition"/></button>
            </div>
            <div class="wj-TblWrapBr">
                <%--위즈모 테이블--%>
                <div style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.openTime"/>" binding="openTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.closeTime"/>" binding="closeTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.runTime"/>" binding="runTime" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOpenClose.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="posExcclc.closeFg"/>"        binding="closeFgNm"          width="80"  align="center" is-read-only="true" visible="false" ng-click="ViewItemDtl($item)"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.regDate"/>"        binding="regDt"          	width="200" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.totSaleAmt"/>"     binding="totSaleAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.totDcAmt"/>"       binding="totDcAmt"           width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.realSaleAmt"/>"    binding="realSaleAmt"        width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashSaleAmt"/>"    binding="cashExactAmt"       width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashBillSaleAmt"/>" binding="cashBillSaleAmt"   width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.posFundAmt"/>"     binding="fundAmt"            width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.inAmt"/>"          binding="accntInAmt"         width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.outAmt"/>"         binding="accntOutAmt"        width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashTicketAmt"/>"  binding="cashTicketAmt"      width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="posExcclc.cashLostAmt"/>"    binding="lostAmt"      	    width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                    <%-- ColumnPicker 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                            <jsp:param name="pickerTarget" value="storeOpenCloseDayCtrl"/>
                        </jsp:include>
                        <%--// ColumnPicker 사용시 include --%>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
   #green {background-color:transparent; border:1px solid #1ab394; color:#1ab394; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #green:hover {background-color:#1ab394; color:#FFFFFF;}
   #yellow {background-color:transparent; border:1px solid #f8ac59; color:#f8ac59; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #yellow:hover {background-color:#f8ac59; color:#FFFFFF;}
   #red {background-color:transparent; border:1px solid #ed5565; color:#ed5565; border-radius:4px; line-height:16px; padding:0 10px; font-size:0.75em; transition:all 0.2s;}
   #red:hover {background-color:#ed5565; color:#FFFFFF;}
</style>

<script type="text/javascript" src="/resource/solbipos/js/sale/store/storeOpenClose/storeOpenCloseDay.js?ver=20221129.01" charset="utf-8"></script>
