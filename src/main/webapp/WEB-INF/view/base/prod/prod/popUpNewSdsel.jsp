<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<wj-popup control="wjNewSdselLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:100%;height:100%;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.newSdsel"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            <div class="wj-TblWrap w50 fl">
                <%-- 선택그룹 그리드 --%>
                <div class="wj-TblWrapBr w100 mb5 pd10" ng-controller="newSdselGrpCtrl">
                    <div class="updownSet oh mb5">
                        <span class="fl bk" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselGrp' /><span id="sideSelectGroupTitle"></span></span>
                        <button class="btn_skyblue" id="btnAddSelGroup" ng-click="addRow()" >
                            <s:message code="cmm.add" />
                        </button>
                        <button class="btn_skyblue" id="btnDelSelGroup" ng-click="deleteRow()" >
                            <s:message code="cmm.delete" />
                        </button>
                        <button class="btn_skyblue" id="btnSaveSelGroup" ng-click="save()" >
                            <s:message code="cmm.save" />
                        </button>
                    </div>
                    <div class="wj-gridWrap" style="height:100px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">
                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselGrpCd"/>" binding="sdselGrpCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselGrpNm"/>" binding="sdselGrpNm" width="210" align="left"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.sdselTypeFg"/>" binding="sdselTypeFg" is-read-only="true" data-map="sdselTypeFgDataMap" width="80" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" is-read-only="true" data-map="fixProdFgDataMap" width="50" align="center"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>

                <%-- 선택분류 그리드 --%>
                <div class="wj-TblWrapBr w100 mt10 mb5 pd10" ng-controller="newSdselClassCtrl">
                    <div class="updownSet oh mb5">
                        <span class="fl bk" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="sdselGrpTitle"></span></span>
                        <button class="btn_up" id="btnUpSelClass" ng-click="rowMoveUp()" >
                            <s:message code="cmm.up" />
                        </button>
                        <button class="btn_down" id="btnDownSelClass" ng-click="rowMoveDown()" >
                            <s:message code="cmm.down" />
                        </button>
                        <button class="btn_skyblue" id="btnAddSelClass" ng-click="addRow()" >
                            <s:message code="cmm.add" />
                        </button>
                        <button class="btn_skyblue" id="btnDelSelClass" ng-click="deleteRow()" >
                            <s:message code="cmm.delete" />
                        </button>
                        <button class="btn_skyblue" id="btnSaveSelClass" ng-click="saveClass()" >
                            <s:message code="cmm.save" />
                        </button>
                    </div>
                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">
                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.sdselClassCd"/>" binding="sdselClassCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.sdselClassNm"/>" binding="sdselClassNm" width="210" align="left"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.sdselQty"/>" binding="sdselQty" width="60" align="right" max-length="3" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="fixProdCnt" width="*" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>

                <%-- 선택메뉴 그리드 --%>
                <div class="wj-TblWrapBr w100 mt10 mb5 pd10" ng-controller="newSdselProdCtrl">
                    <div class="updownSet oh mb5">
                        <span class="fl bk" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="sdselClassTitle"></span></span>
                        <button class="btn_up" id="btnUpSelProd" ng-click="rowMoveUp()" >
                            <s:message code="cmm.up" />
                        </button>
                        <button class="btn_down" id="btnDownSelProd" ng-click="rowMoveDown()" >
                            <s:message code="cmm.down" />
                        </button>
                        <button class="btn_skyblue" id="btnDelSelProd" ng-click="deleteRow()" >
                            <s:message code="cmm.delete" />
                        </button>
                        <button class="btn_skyblue" id="btnSaveSelProd" ng-click="saveProd()" >
                            <s:message code="cmm.save" />
                        </button>
                    </div>
                    <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">
                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.addProdUprc"/>" binding="addProdUprc" width="80" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.sdselQty"/>" binding="addProdQty" width="80" align="right" max-length="3"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.printYn"/>" binding="printYn" data-map="printYnDataMap" width="70" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodClassNm"/>" binding="prodClassNm" width="200" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prod.brand"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center" data-map="brandDataMap" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="" binding="dispSeq" width="*" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
            <div class="wj-TblWrap w50">
                <%-- 상품선택 grid --%>
                <div class="wj-TblWrapBr pd10" ng-controller="prodSelectCtrl">
                    <div class="wj-TblWrapBr" style="height:690px;">
                        <table class="tblType01">
                            <colgroup>
                                <col class="w15" />
                                <col class="w35" />
                                <col class="w15" />
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
                                <td></td>
                                <td></td>
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
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodClassNm"/>" binding="prodClassNm" width="200" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="prod.brand"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    // 상품유형구분
    var prodTypeFg = ${ccu.getCommCode("008")};
    // 사용여부
    var useYn = ${ccu.getCommCode("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/popUpNewSdsel.js?ver=20230727.02" charset="utf-8"></script>