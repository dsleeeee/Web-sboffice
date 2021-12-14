<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="kioskRecmdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:800px;">
    <div>
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"><s:message code="kioskKeyMap.tuRecmd"/></a>
                <%-- 조회 --%>
                <button class="btn_blue fr mt5 mr10" id="btnSearchCls" ng-click="_pageView('kioskRecmdCtrl', 1)"><s:message code="cmm.search"/></button>
            </div>

            <%-- 위 그리드 영역 --%>
            <div class="wj-TblWrapBr pd10" style="height:200px; overflow-y: hidden;" ng-controller="kioskRecmdCtrl">
                <%-- 버튼영역 --%>
                <div class="tr mb5">
                    <button class="btn_skyblue" id="btnAdd" ng-click="addRow()" ><s:message code="cmm.add" /></button>
                    <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
                </div>
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 140px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.recmdCd"/>" binding="recmdCd" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.recmdType"/>" binding="recmdType" width="200" data-map="recmdTypeDataMap" align="center" ch></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.recmdProdCd"/>" binding="recmdProdCd" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.dispType"/>" binding="dispType" width="100" data-map="dispTypeDataMap" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.useYn"/>" binding="useYn" width="100" data-map="useYnDataMap" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 아래 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <!-- 추천메뉴로 등록된 상품 -->
                <div class="w40 fl">
                    <div class="wj-TblWrapBr pd10" style="height:490px; overflow-y: hidden;"  ng-controller="recmdProdCtrl">
                        <span class="fl bk lh30" id="spanRecmd"></span>
                        <div class="updownSet oh mb10 pd5" id="divBtnRecmdProd" style="visibility: hidden;">
                            <button class="btn_up" id="btnUpKeyMap" ng-click="rowMoveUpKeyMap()" >
                                <s:message code="cmm.up" />
                            </button>
                            <button class="btn_down" id="btnDownKeyMap" ng-click="rowMoveDownKeyMap()">
                                <s:message code="cmm.down" />
                            </button>
                            <button class="btn_skyblue" id="btnDelKeyMap" ng-click="delRowKeyMap()">
                                <s:message code="cmm.delete" />
                            </button>
                            <button class="btn_skyblue" id="btnSaveKeyMap" ng-click="saveRecmdProd()">
                                <s:message code="cmm.save" />
                            </button>
                        </div>
                        <div class="w100 mt10 mb20">
                            <div class="wj-gridWrap" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        id="wjGridKeyMap">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodCd"/>" binding="recmdProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                        <input type="hidden" id="recmdCd" />
                    </div>
                </div>
                <!-- 상품조회(추천메뉴로 등록되지 않은 상품) -->
                <div class="w60 fl" >
                    <div class="wj-TblWrapBr ml10 pd10" style="height:490px; overflow-y: hidden;" ng-controller="recmdProdListCtrl">

                        <table class="tblType01">
                            <colgroup>
                                <col class="w13" />
                                <col class="w35" />
                                <col class="w13" />
                                <col class="w35" />
                            </colgroup>
                            <tbody>
                            <tr>
                                <th><s:message code="kioskKeyMap.regDate" /></th><%--등록일자--%>
                                <td colspan="3">
                                    <div class="sb-select">
                                        <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                                        <span class="rg">~</span>
                                        <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                                        <%--전체기간--%>
                                        <span class="chk ml10">
                                  <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                                  <label for="chkDt">
                                    <s:message code="cmm.all.day" />
                                  </label>
                                </span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><s:message code="kioskKeyMap.prodCd" /></th><%--상품코드--%>
                                <td>
                                    <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                                </td>
                                <th><s:message code="kioskKeyMap.prodNm" /></th><%--상품명--%>
                                <td>
                                    <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                                </td>
                            </tr>
                            <tr>
                                <th><s:message code="kioskKeyMap.srchClass" /></th><%--분류조회--%>
                                <td>
                                    <input type="text" class="sb-input w" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left; width:69%;" placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.cancel"/></button>
                                </td>
                                <th><s:message code="kioskKeyMap.barCd" /></th><%--바코드--%>
                                <td>
                                    <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
                                </td>
                            </tr>
<%--                            <tr>--%>
<%--                                <th><s:message code="kioskKeyMap.useYn" /></th>&lt;%&ndash;사용여부&ndash;%&gt;--%>
<%--                                <td>--%>
<%--                                    <div class="sb-select">--%>
<%--                                        <wj-combo-box--%>
<%--                                                id="srchUseYn"--%>
<%--                                                ng-model="useYn"--%>
<%--                                                control="useYnAllCombo"--%>
<%--                                                items-source="_getComboData('useYnAllComboData')"--%>
<%--                                                display-member-path="name"--%>
<%--                                                selected-value-path="value"--%>
<%--                                                is-editable="false"--%>
<%--                                                initialized="_initComboBox(s)">--%>
<%--                                        </wj-combo-box>--%>
<%--                                    </div>--%>
<%--                                </td>--%>
<%--                                <th><s:message code="kioskKeymap.prodTypeFg" /></th>&lt;%&ndash;상품유형&ndash;%&gt;--%>
<%--                                <td>--%>
<%--                                    <div class="sb-select">--%>
<%--                                        <wj-combo-box--%>
<%--                                                id="srchProdTypeFg"--%>
<%--                                                ng-model="prodTypeFg"--%>
<%--                                                control="prodTypeFgAllCombo"--%>
<%--                                                items-source="_getComboData('prodTypeFg')"--%>
<%--                                                display-member-path="name"--%>
<%--                                                selected-value-path="value"--%>
<%--                                                is-editable="false"--%>
<%--                                                initialized="_initComboBox(s)">--%>
<%--                                        </wj-combo-box>--%>
<%--                                    </div>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
                            <tr>
<%--                                <th><s:message code="kioskKeyMap.regYn" /></th>&lt;%&ndash;등록여부&ndash;%&gt;--%>
<%--                                <td>--%>
<%--                                    <div class="sb-select">--%>
<%--                                        <wj-combo-box--%>
<%--                                                id="srchRegYn"--%>
<%--                                                ng-model="regYn"--%>
<%--                                                control="regYnAllCombo"--%>
<%--                                                items-source="_getComboData('regYn')"--%>
<%--                                                display-member-path="name"--%>
<%--                                                selected-value-path="value"--%>
<%--                                                is-editable="false"--%>
<%--                                                initialized="_initComboBox(s)">--%>
<%--                                        </wj-combo-box>--%>
<%--                                    </div>--%>
<%--                                </td>--%>
                                <td colspan="4" align="right">
                                    <div id="divBtnProdSearch" style="visibility: hidden;">
                                        <button class="btn_skyblue" id="btnSearchProd" ng-click="_pageView('recmdProdListCtrl', 1)">
                                            <s:message code="cmm.search" />
                                        </button>
                                        <button class="btn_skyblue" id="btnRegRecmdProd" ng-click="regRecmdProd()">
                                            <s:message code="kioskKeyMap.prodReg" />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="w100 mt10 mb20">
                            <div class="wj-gridWrap" style="height:250px; overflow-x: hidden; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        id="wjGridProd">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodCd"/>" binding="recmdProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                        <%-- 페이지 리스트 --%>
                        <div class="pageNum2 mt10">
                            <%-- id --%>
                            <ul id="recmdProdListCtrlPager" data-size="10">
                            </ul>
                        </div>
                        <%--//페이지 리스트--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    var useYn = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskRecmd.js?ver=20211208.01" charset="utf-8"></script>


<%-- 상품선택 모듈 멀티 선택 사용시 include --%>
<jsp:include page="/WEB-INF/view/application/layer/searchProductS.jsp" flush="true">
    <jsp:param name="targetId" value="recmdProdPopup"/>
</jsp:include>
<%--// 상품선택 모듈 멀티 선택 사용시 include --%>
