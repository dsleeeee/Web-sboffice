<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyDlvrProdNmLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="copyDlvrProdNmCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeView.copy.store" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <%-- 매장검색영역 --%>
            <div class="searchBar_s">
                <a href="#" class="open">환경복사 매장 선택</a><!--하단 검색테이블 열기 .open, 하단 검색테이블 닫기 .close-->
                <p class="noFolding" style="display:none;">환경복사 매장 선택</p><!-- 접기기능 사용하지 않을경우 -->
            </div>
            <div class="tblBr mb40" >
                <table class="tblType01">
                    <colgroup>
                        <col class="w30" />
                        <col class="w70" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="storeView.original.storeCd" /></th>
                        <td>
                            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                            <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                                <jsp:param name="targetId" value="originalStore"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="storeView.target.storeCd" /></th>
                        <td>
                            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                            <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                                <jsp:param name="targetId" value="targetStore"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <p class="s12 bk mt10 lh20">
                                * [기준매장]의 환경설정값을 복사하여, [적용대상매장]에 적용합니다.<br />
                            </p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--위즈모 테이블--%>
            <div class="theGrid" style="height: 250px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeCd" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>

            <div class="btnSet">
                <%-- 복사 --%>
                <span><a href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
                <%-- 닫기 --%>
                <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/dlvrProd/copyDlvrProdNm.js?ver=20210106.01" charset="utf-8"></script>
