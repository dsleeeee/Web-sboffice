<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjLibraryDetailLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:300px;" fade-in="false" fade-out="false">
    <div ng-controller="libraryDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="libraryDetail.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 370px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 자료명 --%>
                        <th>
                            <s:message code="libraryDetail.title"/>
                        </th>
                        <td colspan="3">
                            {{libraryDetail.title}}
                        </td>
                    </tr>
                </tbody>
                <%-- 첨부파일 --%>
                <tbody id="fileContent">
                </tbody>
            </table>

            <div class="btnSet tc mt20 mb10">
                <%-- 삭제 --%>
                <span id="delButton"><a href="#" class="btn_blue pd20" ng-click="del()"><s:message code="boardDetail.del" /></a></span>
                <%-- 수정 --%>
                <span id="modifyButton"><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="boardDetail.modify" /></a></span>
                <%-- 닫기 --%>
                <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/library/libraryDetail.js?ver=20200317.01" charset="utf-8"></script>

<%-- 자료실 신규등록,수정 팝업 --%>
<%--<c:import url="/WEB-INF/view/adi/board/library/libraryInfo.jsp">--%>
    <%--<c:param name="menuCd" value="${menuCd}"/>--%>
    <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>