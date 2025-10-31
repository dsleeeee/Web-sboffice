<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="couponRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:850px;height:600px;">
    <div class="wj-dialog wj-dialog-columns title">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="couponInfo.couponInfoDtl" />
            <span id="couponDtlTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="couponRegistCtrl">
            <div>
                <div style="height:450px; overflow-y: auto;">
                    <f:form id="regForm" name="regForm" >
                        <%-- 쿠폰정보 --%>
                        <div>
                            <h3 class="h3_tbl lh25">
                                <s:message code="couponInfo.couponInfoDtl" />
                                <a href="#" class="btn_grayS fr" id="coupnIssue" ng-model="coupn.coupnIssue" ng-click="couponIssue()" ><s:message code="couponInfo.couponIssue" /></a>
                            </h3>
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
                                    <%-- 쿠폰코드 --%>
                                    <th>
                                        <s:message code="couponInfo.coupnCd" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="coupnCd" ng-model="coupn.coupnCd" readonly placeholder="자동채번">
                                    </td>
                                    <%-- 상태 --%>
                                    <th>
                                        <s:message code="couponInfo.status" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="coupnStatus" ng-model="coupn.coupnStatus" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 쿠폰명 --%>
                                    <th>
                                        <s:message code="couponInfo.coupnNm" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="coupnNm" ng-model="coupn.coupnNm">
                                    </td>
                                    <%-- 사용매장 --%>
                                    <th>
                                        <s:message code="couponInfo.useStore" />
                                    </th>
                                    <td>
                                        <%-- 매장선택 모듈 사용시 include --%>
                                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                                            <jsp:param name="targetTypeFg" value="S"/>
                                            <jsp:param name="targetId" value="couponRegistStore"/>
                                        </jsp:include>
                                        <%--// 매장선택 모듈 사용시 include --%>
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 사용부서 --%>
                                    <th>
                                        <s:message code="couponInfo.usePart" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input fl mr5" style="cursor:pointer; width:200px;" id="selectPart" ng-click="selectPartShow()" value="선택" readonly/>
                                        <input type="hidden" id="selectPartCd"/>
                                        <button type="button" class="btn_skyblue fl" id="selectPartCancel" ng-click="selectPartCancel()">
                                            <s:message code="cmm.selectCancel"/>
                                        </button>
                                        <%--// 매장선택 모듈 사용시 include --%>
                                    </td>
                                    <%-- 상품선택 --%>
                                    <th>
                                        <s:message code="cmm.prod.select" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input fl mr5" style="cursor:pointer; width:200px;" id="selectProd" ng-click="selectProdShow()" value="선택" readonly/>
                                        <input type="hidden" id="selectProdCd"/>
                                        <button type="button" class="btn_skyblue fl" id="selectProdCancel" ng-click="selectProdCancel()">
                                            <s:message code="cmm.selectCancel"/>
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 사용기간 --%>
                                    <th>
                                        <s:message code="couponInfo.usePeriod" />
                                    </th>
                                    <td>
                                        <div class="sb-select">
                                            <span class="txtIn"><input id="dtlStartDate" name="dtlStartDate" class="w110px" /></span>
                                            <span class="rg">~</span>
                                            <span class="txtIn"><input id="dtlEndDate" name="dtlEndDate" class="w110px" /></span>
                                        </div>
                                    </td>
                                    <%-- 발행수량 --%>
                                    <th>
                                        <s:message code="couponInfo.issueQty" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="coupnCount" ng-model="coupn.coupnCount">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <%-- 쿠폰출력정보 --%>
                        <h3 class="h3_tbl"><s:message code="couponInfo.couponPrintDtl" /></h3>
                        <table class="searchTbl">
                            <colgroup>
                                <col class="w15" />
                                <col class="w35" />
                                <col class="w15" />
                                <col class="w35" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <%-- 제목 --%>
                                    <th>
                                        <s:message code="couponInfo.title" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="title" ng-model="coupn.title">
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 발행처 --%>
                                    <th>
                                        <s:message code="couponInfo.issuer" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="issuer" ng-model="coupn.issuer">
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 사용매장 --%>
                                    <th>
                                        <s:message code="couponInfo.useStore" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="useStore" ng-model="coupn.useStore">
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 인쇄문구 --%>
                                    <th>
                                        <s:message code="couponInfo.printMsg" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="printMsg" ng-model="coupn.printMsg">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <%-- 쿠폰회수정보 --%>
                        <h3 class="h3_tbl lh25">
                            <s:message code="couponInfo.couponSaleDtl" />
                            <a href="#" class="btn_grayS fr" id="selectSaleCoupon" ng-model="coupn.selectSaleCoupon" ng-click="selectSaleCoupon()" ><s:message code="couponInfo.srchSaleCoupon" /></a>
                        </h3>
                        <table class="searchTbl">
                            <colgroup>
                                <col class="w15" />
                                <col class="w35" />
                                <col class="w15" />
                                <col class="w35" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <%-- 회수수량 --%>
                                    <th>
                                        <s:message code="couponInfo.saleCnt" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="srchSaleCnt" ng-model="coupn.saleCnt" readonly>
                                    </td>
                                    <%-- 미회수량 --%>
                                    <th>
                                        <s:message code="couponInfo.notSaleCnt" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="srchNotSaleCnt" ng-model="coupn.notSaleCnt" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 회수금액 --%>
                                    <th>
                                        <s:message code="couponInfo.saleAmt" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="srchSaleAmt" ng-model="coupn.saleAmt" readonly>
                                    </td>
                                    <%-- 미회수금액 --%>
                                    <th>
                                        <s:message code="couponInfo.notSaleAmt" />
                                    </th>
                                    <td>
                                        <input type="text" class="sb-input w100" id="srchNotSaleAmt" ng-model="coupn.notSaleAmt" readonly>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </f:form>
                </div>
                <div class="btnSet2">
                    <%-- 수정 --%>
                    <span><a href="#" class="btn_blue pd20" id="couponInfoSave" ng-click="couponInfoSave()"><s:message code="cmm.save" /></a></span>
                    <%-- 닫기 --%>
                    <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>
            </div>
        </div>
    </div>

    <%-- 엑셀업로드  --%>
    <div style="display: none;" ng-controller="couponRegistExcelCtrl">

        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="회원번호"      binding="membrNo"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="수신자명"      binding="membrNm"      width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="수신자연락처"   binding="membrTelNo"   width="90"  align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>

        <input type="file" class="form-control" id="excelUpFile"
               ng-model="excelUpFile"
               onchange="angular.element(this).scope().excelFileChanged()"
               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
    </div>
</wj-popup>
<script>
    var hqOfficeCd = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/coupon/couponInfo/couponRegist.js?ver=20251022.01" charset="utf-8"></script>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>