<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="prodImgStoreRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="prodImgStoreRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodImg.storeReg" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="prodImg.storeCd" /></th>
                    <td>
                        <input type="text" id="srchStoreCd"/>
                    </td>
                    <th><s:message code="prodImg.storeNm" /></th>
                    <td>
                        <input type="text" id="srchStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="prodImg.sysStatFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchSysStatFg"
                                    items-source="_getComboData('srchSysStatFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
                </tbody>
            </table>
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <button class="btn_blue" id="btnSearchStore" ng-click="btnSearchStore()"><s:message code="cmm.search" /></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImg.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImg.storeNm"/>" binding="storeNm" width="300" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImg.sysStatFg"/>" binding="sysStatFg" width="100"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
                <!-- 적용할 이미지타입 선택 -->
                <div style="padding-top:20px;">
                    <table class="tblType01">
                        <colgroup>
                            <col width="20%" />
                            <col width="80%" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="prodImg.imgFg" /></th>
                            <td colspan="3">
                                <div style="float: left;"><input type="checkbox" id="chkProd" value="001"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImg.prodImg" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkKiosk" value="002"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImg.kioskImg" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkDid" value="003"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImg.didImg" /></label></div>
                                <div style="float: left;"><input type="checkbox" id="chkHash" value="004"/></div>
                                <div style="float: left; padding: 3px 10px 0 5px;"><label><s:message code="prodImg.didHash" /></label></div>
                                <button class="btn_blue ml10 fl" id="btnRegImgStore" ng-click="btnRegImgStore()"><s:message code="cmm.apply"/></button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImg/prodImgStoreRegist.js?ver=20210617.02" charset="utf-8"></script>