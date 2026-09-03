<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="wjNaverMenuSettingLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1400px;height:800px;" fade-in="false" fade-out="false">
    <div ng-controller="naverMenuSetting1Ctrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="naverMenuLink.menuSetting"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- left --%>
        <div class="wj-TblWrap mt10 mb20 w28 fl" style="margin-left: 26px;">
            <div class="wj-TblWrapBr mr10 pd10" style="height:655px;">
                <span class="bk lh30" id="spLevel1"><s:message code='naverMenuLink.naverMenu' /></span>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:550px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                ime-enabled="true"
                                id="wjGridNaverMenuSetting1">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.posShopId"/>" binding="posShopId" width="150" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.optionId"/>" binding="optionId" width="145" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.optionNm"/>" binding="name" width="180" align="left"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.subOptionCategoryId"/>" binding="subOptionCategoryId" width="150" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.subOptionItemSeq"/>" binding="subOptionItemSeq" width="150" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.prodCd"/>" binding="prodCd" width="145" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="naverMenuLink.prodNm"/>" binding="prodNm" width="180" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
        <%-- left --%>
    </div>

    <%-- mid --%>
    <div class="wj-TblWrap mt10 mb20 w28 fl" ng-controller="naverMenuSetting2Ctrl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:655px;">
            <span class="bk lh30" id="spLevel2"><s:message code='naverMenuLink.lynkPosMneu' /></span>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:550px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridNaverMenuSetting2">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.posShopId"/>" binding="posShopId" width="150" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.optionId"/>" binding="optionId" width="145" align="center"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.optionNm"/>" binding="name" width="180" align="left" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.subOptionCategoryId"/>" binding="subOptionCategoryId" width="150" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.subOptionItemSeq"/>" binding="subOptionItemSeq" width="150" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.prodCd"/>" binding="prodCd" width="130"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.prodNm"/>" binding="prodNm" width="160"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
            <div class="updownSet" id="divBtn">
                <%-- up 버튼 --%>
                <button class="btn_up" id="btnUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                <%-- down 버튼 --%>
                <button class="btn_down" id="btnDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                <%-- 삭제버튼 --%>
                <button class="btn_skyblue" id="btnDel" ng-click="rowDel()"><s:message code="cmm.del" /></button>
            </div>
        </div>
    </div>
    <%-- mid --%>

    <%-- right --%>
    <div class="wj-TblWrap mt10 mb20 w40 fl" ng-controller="naverMenuSetting3Ctrl">
        <div class="wj-TblWrapBr pd10" style="height:650px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w13" />
                    <col class="w35" />
                    <col class="w13" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="cmm.regDt" /></th><%--등록일시--%>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                            <%--전체기간--%>
                            <span class="chk ml10">
                                <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                                <label for="chkDt"><s:message code="cmm.all.day" /></label>
                            </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="naverMenuLink.prodCd" /></th><%--상품코드--%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
                    </td>
                    <th><s:message code="naverMenuLink.prodNm" /></th><%--상품명--%>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="right">
                        <button class="btn_skyblue" id="btnSearchProd" ng-click="_pageView('naverMenuSetting3Ctrl', 1)">
                            <s:message code="cmm.search" />
                        </button>
                        <button class="btn_skyblue" id="btnRegProd" ng-click="regProd()">
                            <s:message code="cmm.regist" />
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:470px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true"
                            id="wjGridNaverMenuSetting3">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="prodCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="prodNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="sdselGrpCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="sdselClassCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="sideProdcd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="sideProdNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="rowType" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.prodClassNm"/>" binding="prodClassNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.prodCd"/>" binding="dispProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="naverMenuLink.prodNm"/>" binding="dispProdNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="naverMenuSetting3CtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

        </div>
    </div>
    <%-- right --%>

    <div class="wj-dialog-footer" ng-controller="naverMenuSetting1Ctrl" style="clear:both; width:100%; text-align:center;">
        <div class="btnSet" style="display:inline-block; float:none; margin:0;">
            <%-- 저장 --%>
            <span><a href="#" class="btn_blue" ng-click="save()"><s:message code="cmm.save" /></a></span>
            <%-- 취소 --%>
            <span><a href="#" class="btn_gray" ng-click="cancel()"><s:message code="cmm.cancel" /></a></span>
        </div>
    </div>

</wj-popup>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverMenuLink/naverMenuSetting.js?ver=20260831.01" charset="utf-8"></script>