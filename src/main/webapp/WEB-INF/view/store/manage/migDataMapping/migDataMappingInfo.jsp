<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<wj-popup control="wjMigDataMappingInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1050px;height:730px;" fade-in="false" fade-out="false">

    <div ng-controller="migDataMappingInfoCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="migDataMappingInfo.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('migDataMappingInfoCtrl', 1)">
                        <s:message code="cmm.search" />
                    </button>
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
                        <%-- 아이디 --%>
                        <th>
                            <s:message code="migDataMappingInfo.srchUserId"/><em class="imp">*</em>
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                        </td>
                        <%-- 비밀번호 --%>
                        <th>
                            <s:message code="migDataMappingInfo.srchUserPwd"/><em class="imp">*</em>
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchUserPwd" ng-model="userPwd" />
                        </td>
                    </tr>
                    <tr>
                        <%-- OKPOS-KCP 매장코드 --%>
                        <th>
                            <s:message code="migDataMappingInfo.srchOkposStoreCd" />
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchOkposStoreCd" ng-model="okposStoreCd" />
                        </td>
                        <%-- OKPOS-KCP 매장명 --%>
                        <th>
                            <s:message code="migDataMappingInfo.srchOkposStoreNm" />
                        </th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchOkposStoreNm" ng-model="okposStoreNm" />
                        </td>
                    </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <p class="tl s14 mt5 mb10 lh15">- 조회 전 OKPOS-KCP에서 사용하는 아이디와 비밀번호를 반드시 입력한 후 조회하셔야 합니다.</p>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:200px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.bizNo"/>" binding="bizNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.telNo"/>" binding="telNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.vanTermNo"/>" binding="vanTermNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.address"/>" binding="address" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.lastSaleDate"/>" binding="lastSaleDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeNm"/>" binding="hqOfficeNm" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.ownerNm"/>" binding="ownerNm" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.postNo"/>" binding="postNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.addr"/>" binding="addr" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.addrDtl"/>" binding="addrDtl" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.areaCd"/>" binding="areaCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.clsFg"/>" binding="clsFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.sysStatFg"/>" binding="sysStatFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.sysOpenDate"/>" binding="sysOpenDate" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.sysRemark"/>" binding="sysRemark" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.installPosCnt"/>" binding="installPosCnt" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.posNo"/>" binding="posNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.newSoibiStoreCd"/>" binding="newSoibiStoreCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <div class="mt10 oh sb-select dkbr">
                <p class="tl s14 mt5 mb10 lh15">- 조회된 OKPOS-KCP 매장 체크 후, 솔비포스 매장환경 복사 선택 -> 이관 등록 클릭</p>
                <p class="tl s14 mt5 mb10 lh15">- 환경복사 : 솔비포스 시스템에 등록된 매장환경을 복사 ( 체크한 전체 매장에 적용됨 )</p>
            </div>

            <%-- 솔비포스 복사매장 선택 --%>
            <h3 class="h3_tbl"><s:message code="migDataMappingInfo.additionalSetting" /></h3>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 솔비포스 매장환경 복사 --%>
                        <th>
                            <s:message code="migDataMappingInfo.solbiCopyStoreSetting"/><em class="imp">*</em>
                        </th>
                        <td colspan="3" class="oh">
                            <div class="mr5 w50">
                                <%-- 본사 선택 --%>
                                <div class="sb-select">
                                    <wj-combo-box
                                        id="envHqOfficeCd"
                                        ng-model="copyHqOfficeCd"
                                        control="envHqOfficeCdCombo"
                                        items-source="_getComboData('envHqOfficeCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index-changed="setEnvHqOfficeCdVal(s,e)">
                                    </wj-combo-box>
                                </div>
                                <%-- 매장선택 --%>
                                <div class="sb-select">
                                    <wj-combo-box
                                        id="envStoreCd"
                                        ng-model="copyStoreCd"
                                        control="envStoreCdCombo"
                                        items-source="_getComboData('envStoreCd')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index-changed="setEnvStoreCdVal(s,e)">
                                    </wj-combo-box>
                                </div>
                            </div>
                            <div class="mr5 mt10">
                                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="storeEnvChk" value="storeEnv"/><%-- 매장환경 --%>
                                    <label for="storeEnvChk" ><s:message code="storeManage.storeEnv" /></label>
                                </span>
                                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="posEnvChk" value="posEnv"/>  <%-- 포스환경 --%>
                                    <label for="posEnvChk" ><s:message code="storeManage.posEnv" /></label>
                                </span>
                                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="foodEnvChk" value="foodEnv"/> <%-- 외식환경 --%>
                                    <label for="foodEnvChk" ><s:message code="storeManage.foodEnv" /></label>
                                </span>
                                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="kitchenPrintChk" value="kitchenPrint"/> <%-- 주방프린터 --%>
                                    <label for="kitchenPrintChk" ><s:message code="storeManage.kitchenPrint" /></label>
                                </span>
                                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="posFnkeyChk" value="posFnkey"/><%-- 포스기능키 --%>
                                    <label for="posFnkeyChk" ><s:message code="storeManage.posFnkey" /></label>
                                </span>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="migDataMappingInfo.save" />
                </button>
            </div>

        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script>
    var hqList = ${ccu.getHqOfficeList()};
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/migDataMappingInfo.js?ver=20211019.01" charset="utf-8"></script>