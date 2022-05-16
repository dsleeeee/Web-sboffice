<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="printerGroupView" name="printerGroupView" class="subCon" style="display: none;">

    <div ng-controller="printerGroupCtrl">
        <%-- 제목 및 조회버튼  --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="printerGroup.printerGroup" /></a>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch2" ng-click="_pageView('printerGroupCtrl', 2)"><s:message code="cmm.search"/></button>
        </div>
        <%-- 조회조건 --%>
        <table class="searchTbl">
            <colgroup>
                <col class="w13" />
                <col class="w35" />
                <col class="w13" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 그룹명 --%>
                <th><s:message code="printerGroup.groupNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" ng-model="printerGroupNm" onkeyup="fnNxBtnSearch('2');"/>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- left (메뉴그룹관리-메뉴그룹등록 grid) --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl" style="width: 275px;">
            <div class="wj-TblWrapBr mr10 pd10" style="height:535px;">
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30"><s:message code='printerGroup.groupManage' /></span>
                    <button class="btn_skyblue" id="btnAddMenuGroup" ng-click="addMenuGroup()">
                        <s:message code="cmm.add" />
                    </button>
                    <button class="btn_skyblue" id="btnSaveMenuGroup" ng-click="saveMenuGroup()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:450px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="printerGroup.groupCd"/>"      binding="printerGroupCd"    width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="printerGroup.groupNm"/>"      binding="printerGroupNm"    width="70"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="printerGroup.addProd"/>"      binding="addProd"           width="40" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="printerGroup.addPrinter"/>"   binding="addPrinter"        width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <input type="hidden" id="groupCd" />
            </div>
        </div>
    </div>

    <%-- right - 상품추가 --%>
    <div id="addProd" class="wj-TblWrap fr" style="width:calc(100% - 275px);">

        <%-- 상품연결 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fl" ng-controller="prodMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:535px;">
                <div class="ml5">
                    <span class="bk"><s:message code='printerGroup.addProd' /></span>
                </div>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblMenuGroup"></span>
                    <button class="btn_skyblue" id="btnDelProdMapping" ng-click="delProdMapping()">
                        <s:message code="cmm.del" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:450px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 상품선택 grid --%>
        <div class="wj-TblWrap mt20 mb5 w50 fr" ng-controller="prodSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:535px;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w13" />
                        <col class="w35" />
                        <col class="w13" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="printerGroup.regDate" /></th><%--등록일자--%>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          id="srchGroupStartDate"
                                          value="groupStartDate"
                                          ng-model="groupStartDate"
                                          control="startDateCombo"
                                          min="2000-01-01"
                                          max="2099-12-31"
                                          initialized="_initDateBox(s)">
                                  </wj-input-date>
                                </span>
                                <span class="rg">~</span>
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          id="srchGroupEndDate"
                                          value="groupEndDate"
                                          ng-model="groupEndDate"
                                          control="endDateCombo"
                                          min="2000-01-01"
                                          max="2099-12-31"
                                          initialized="_initDateBox(s)">
                                  </wj-input-date>
                                </span>
                                <span class="chk ml10">
                                  <input type="checkbox" id="chkGroupDt" ng-model="isGroupChecked" ng-change="isChkDt()" />
                                  <label for="chkGroupDt">
                                    <s:message code="cmm.all.day" />
                                  </label>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="printerGroup.brand" /></th><%--브랜드--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                    id="srchBrand"
                                    items-source="_getComboData('srchBrand')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <th><s:message code="printerGroup.prodCd" /></th><%--상품코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdCd" ng-model="srchProdCd"/>
                        </td>
                        <th><s:message code="printerGroup.prodNm" /></th><%--상품명--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchProdNm" ng-model="srchProdNm"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="printerGroup.srchClass" /></th><%--분류조회--%>
                        <td>
                            <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                        </td>
                        <th><s:message code="printerGroup.barCd" /></th><%--바코드--%>
                        <td>
                            <input type="text" class="sb-input w100" id="srchBarCd"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="printerGroup.useYn" /></th><%--사용여부--%>
                        <td>
                            <div class="sb-select w100">
                                <wj-combo-box
                                        id="srchUseYn"
                                        items-source="_getComboData('srchUseYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchUseYnCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="printerGroup.prodTypeFg" /></th><%--상품유형--%>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdTypeFg"
                                        items-source="_getComboData('srchProdTypeFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdTypeFgCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="printerGroup.menuGroup" /></th><%--메뉴그룹--%>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchStoreGroup"
                                        items-source="_getComboData('srchStoreGroup')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchStoreGroupCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td class="fr">
                            <button class="btn_skyblue" id="btnSrchProd" ng-click="searchProd()">
                                <s:message code="cmm.search" />
                            </button>
                            <button class="btn_skyblue" id="btnRegProd" ng-click="regProd()">
                                <s:message code="printerGroup.reg" />
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="wj-gridWrap" style="height:230px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>

    <%-- right - 프린터추가 --%>
    <div id="addPrinter" class="wj-TblWrap fl" style="width:calc(100% - 275px); display: none;">

        <%-- 프린터연결 grid --%>
        <div class="wj-TblWrap mt20 w100 fl" ng-controller="printerMappingCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:265px;">
                <div class="ml5">
                    <span class="bk"><s:message code='printerGroup.addPrinter' /></span>
                </div>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblPrinterGroup1"></span>
                    <button class="btn_skyblue" id="btnDelPrinterMapping" ng-click="delPrinterMapping()">
                        <s:message code="cmm.del" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:190px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.storeNm"/>" binding="storeNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prterNo"/>" binding="prterNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prterNm"/>" binding="prterNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 프린터선택 grid --%>
        <div class="wj-TblWrap mt10 mb5 w100 fr" ng-controller="printerSelectCtrl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:255px;">
                <div class="ml5">
                </div>
                <div class="updownSet oh mb10 pd5">
                    <span class="fl bk lh30" id="lblPrinterGroup2"></span>
                    <button class="btn_skyblue" id="btnAddPrinterMapping" ng-click="addPrinterMapping()">
                        <s:message code="cmm.add" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:190px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.storeNm"/>" binding="storeNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prterNo"/>" binding="prterNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="printerGroup.prterNm"/>" binding="prterNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>

</div>

<script type="text/javascript">
    var useYn = ${ccu.getCommCodeExcpAll("067")};
    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodKitchenprintLink/printerGroup.js?ver=20220511.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>