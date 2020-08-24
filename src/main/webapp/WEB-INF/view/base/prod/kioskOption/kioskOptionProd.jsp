<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjKioskOptionProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:750px;height:610px;" fade-in="false" fade-out="false">

    <div ng-controller="kioskOptionProdCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="kioskOptionProd.info"/>
            <label id="lblProdCd" style="display: none;"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
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
                    <%-- 상품코드 --%>
                    <th>
                        <s:message code="kioskOptionProd.optionProdCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchKioskOptionProdOptionProdCd" ng-model="optionProdCd" />
                    </td>
                    <%-- 상품명 --%>
                    <th>
                        <s:message code="kioskOptionProd.optionProdNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchKioskOptionProdOptionProdNm" ng-model="optionProdNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 분류조회 --%>
                    <th>
                        <s:message code="kioskOptionProd.srchClass" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchKioskOptionProdProdClassCd" ng-model="prodClassCdNm" ng-click="popUpKioskOptionProdProdClass()" style="float: left;"
                               placeholder="<s:message code="kioskOptionProd.srchClass" /> 선택" readonly/>
                        <input type="hidden" id="_kioskOptionProdProdClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    </td>
                    <td>
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelKioskOptionProdProdClassCd" style="margin-left: 5px;" ng-click="delKioskOptionProdProdClass()"><s:message code="cmm.selectCancel"/></button>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 바코드 --%>
                    <th>
                        <s:message code="kioskOptionProd.barCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchKioskOptionProdBarCd" ng-model="barCd" />
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 페이지 스케일  --%>
                    <wj-combo-box
                        class="w100px fl"
                        id="listScaleBox"
                        ng-model="listScale"
                        control="listScaleCombo"
                        items-source="_getComboData('listScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                    </wj-combo-box>
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_pageView('kioskOptionProdCtrl',1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="kioskOptionProd.prodClassNm"/>" binding="prodClassNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOptionProd.optionProdCd"/>" binding="optionProdCd" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskOptionProd.optionProdNm"/>" binding="optionProdNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>

        <%-- 페이지 리스트 --%>
        <div class="pageNum2">
            <%-- id --%>
            <ul id="kioskOptionProdCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

        <div class="btnSet tc mt20 mb10">
            <%-- 기본 --%>
            <span id="saveButton"><a href="#" class="btn_blue pd20" ng-click="save(0)"><s:message code="kioskOptionProd.optionFgData0" /></a></span>
            <%-- 무료 --%>
            <span id="saveButton"><a href="#" class="btn_blue pd20" ng-click="save(1)"><s:message code="kioskOptionProd.optionFgData1" /></a></span>
            <%-- 유료 --%>
            <span id="saveButton"><a href="#" class="btn_blue pd20" ng-click="save(2)"><s:message code="kioskOptionProd.optionFgData2" /></a></span>
            <%-- 닫기 --%>
            <span><a href="#" class="btn wj-hide btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>

    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskOption/kioskOptionProd.js?ver=20200824.01" charset="utf-8"></script>