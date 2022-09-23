<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" style="display: none" ng-controller="cardCreditExcelUploadAddCtrl">

    <input type="file" class="form-control" id="cardCreditExcelUpFile"
           ng-model="cardCreditExcelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:50px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cardCredit.apprDate"/>" binding="apprDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cardCredit.apprNo"/>" binding="apprNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cardCredit.creditDate"/>" binding="creditDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cardCredit.creditAmt"/>" binding="creditAmt" width="70" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cardCredit.creditFee"/>" binding="creditFee" width="70" is-read-only="true" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cardCredit.creditBank"/>" binding="creditBank" data-map="creditBankDataMap" width="70" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/card/cardCredit/cardCreditExcelUploadAdd.js?ver=20220918.01" charset="utf-8"></script>