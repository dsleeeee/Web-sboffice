<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="regMenuRankStoreLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:920px;">
    <div class="wj-dialog wj-dialog-columns title">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="menuRank.regist.store" />
            <a href="#" class="wj-hide btn_close" onclick="closePop();"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                    <tr>
                        <th><s:message code="menuRank.displayContent"/></th>
                        <td colspan="3"><label id="nmcodeNm"></label></td>
                        <input type="hidden" id="nmcodeCd"/>
                    </tr>
                    <tr>
                        <th><s:message code="menuRank.storeCd"/></th>
                        <td><input type="text" id="storeCd" ng-model="storeCd" /></td>
                        <th><s:message code="menuRank.storeNm"/></th>
                        <td><input type="text" id="storeNm" ng-model="storeNm" /></td>
                    </tr>
                </tbody>
            </table>
            <%-- 조회 --%>
            <div class="mt10 tr">
                <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regMenuRankStoreCtrl', 1)" ><s:message code="cmm.search" /></button>
            </div>

            <%--- 적용매장 그리드 --%>
            <div class="oh mt20">
                <div class="w50 fl" ng-controller="regMenuRankStoreCtrl">
                    <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
                        <div class="oh">
                            <span class="fl bk lh20 s14"><s:message code="menuRank.regStore"/></span>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a>
                              </span>
                        </div>
                        <div id="regStoreGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="menuRank.storeCd"/>" binding="storeCd" width="80" is-read-only="true"  align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="menuRank.storeNm"/>" binding="storeNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                        </div>
                    </div>

                    <%--- 미적용매장 그리드 --%>
                    <div class="w50 fr" ng-controller="noRegMenuRankStoreCtrl">
                        <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden;" >
                            <div class="oh">
                                <span class="fl bk lh20 s14"><s:message code="menuRank.noRegStore"/></span>
                                <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="menuRank.regist"/></a></span>
                            </div>

                            <div id="noRegStoreGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
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
                                    <wj-flex-grid-column header="<s:message code="menuRank.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="menuRank.storeNm"/>" binding="storeNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/menuRank/menuRankStoreRegist.js?ver=20200811.16" charset="utf-8"></script>