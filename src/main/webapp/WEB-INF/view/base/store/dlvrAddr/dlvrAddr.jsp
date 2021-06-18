<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon">

    <div ng-controller="dlvrAddrCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('dlvrAddrCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col width="15%" />
                <col width="70%" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 배달권역명 --%>
                <th><s:message code="dlvrAddr.dlvrAddrNm"/></th>
                <td>
                    <input type="text" id="srchAddrNm" ng-model="addrNm" class="sb-input w60"/><br/>
                    <span>법정 시/구/동 명을 입력 후 조회 버튼을 눌러주세요</span>
                    <br/>
                    <br/>
                    <span>CID , 전화 주문 시 POS에서 조회할 주소 정보의 법정동 권역을 설정 합니다.<br/>입력되지 않은 법정동은 주소 조회가 되지 않습니다.</span>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w50 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30"><s:message code="dlvrAddr.dlvrAddr"/></span>
                    <button class="btn_skyblue" id="btnFoodAllergyDel" ng-click="del()"><s:message code='cmm.del' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
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
                                <wj-flex-grid-column header="<s:message code="dlvrAddr.sidoNm"/>" binding="sidoNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="dlvrAddr.sigunguNm"/>" binding="sigunguNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="dlvrAddr.dongNm"/>" binding="lawDongNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="dlvrAddr.lawDongCd"/>" binding="lawDongCd" width="*" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="dlvrAddr.myAreaCd"/>" binding="myAreaCd" width="*" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="dlvrAddrCodeCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="dlvrAddr.noDlvrAddr"/></span>
                <button class="btn_skyblue" id="btnFoodAllergyProdAdd" ng-click="add()"><s:message code='cmm.add' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"

                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAddr.sidoNm"/>" binding="sidoNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAddr.sigunguNm"/>" binding="sigunguNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAddr.dongNm"/>" binding="lawDongNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAddr.lawDongCd"/>" binding="lawDongCd" width="*" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/dlvrAddr/dlvrAddr.js?ver=20210327.03" charset="utf-8"></script>
