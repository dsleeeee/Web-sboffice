<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />

<wj-popup control="copyDlvrProdNmLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="copyDlvrProdNmCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dlvrProd.nmCopy" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">

            <%-- 매장검색영역 --%>
            <div class="searchBar_s">
                <a href="#" class="open"><s:message code="dlvrProd.selectStore" /></a><!--하단 검색테이블 열기 .open, 하단 검색테이블 닫기 .close-->
                <p class="noFolding" style="display:none;"><s:message code="dlvrProd.selectStore" /></p><!-- 접기기능 사용하지 않을경우 -->
            </div>
            <div class="tblBr mb40" >
                <table class="tblType01">
                    <colgroup>
                        <col class="w30" />
                        <col class="w70" />
                    </colgroup>
                    <tbody>
                    <!-- 기준매장 -->
                    <tr>
                        <th><s:message code="dlvrProd.original.storeCd" /></th>
                        <td>
                            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                            <jsp:include page="/WEB-INF/view/application/layer/searchHqStore.jsp" flush="true">
                                <jsp:param name="targetId" value="originalStore"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <!-- 적용대상매장 -->
                    <tr>
                        <th><s:message code="dlvrProd.target.storeCd" /></th>
                        <td>
                            <!-- 본사권한은 전매장에 복사가능 -->
                            <c:if test="${orgnFg == 'HQ'}">
                                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                                <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                                    <jsp:param name="targetId" value="targetStore"/>
                                </jsp:include>
                            </c:if>
                            <!-- 매장권한은 본인매장에만 복사가능 -->
                            <c:if test="${orgnFg == 'STORE'}">
                                <input type="hidden" id="targetStoreCd" value="<c:out value="${storeCd}"></c:out>"/>
                                <input type="text" id="targetStoreNm" class="sb-input fl mr5" style="width:200px;"
                                       value="[<c:out value="${storeCd}"></c:out>] <c:out value="${storeNm}"></c:out>" readonly/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <p class="s12 bk mt10 lh20">
                                <s:message code="dlvrProd.selectMsg" /><br />
                                <s:message code="dlvrProd.selectMsg2" /><br />
                            </p>
                        </td>
                    </tr>
                    </tbody>
                </table>
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

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
    var storeNm = "${storeNm}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/dlvrProd/copyDlvrProdNm.js?ver=20210106.06" charset="utf-8"></script>
