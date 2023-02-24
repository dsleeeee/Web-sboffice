<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<%--<c:set var="prodEnvstVal" value="${prodEnvstVal}" />--%>
<c:set var="priceEnvstVal" value="${priceEnvstVal}" />

<div class="subCon" id="storeSaleCopyArea" ng-controller="storeSaleCopyCtrl" style="display:none;">
  <%--searchTbl--%>
  <%--<div class="searchBar flddUnfld">--%>
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="salePrice.storeSaleCopy" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('storeSaleCopyCtrl', 1)">
        <s:message code="cmm.save" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <%-- 기준매장 --%>
    <tr>
      <th><s:message code="saleCopy.original.storeCd" /></th>
      <td  colspan="3">
        <%-- 매장선택 모듈 사용시 include --%>
        <c:if test="${momsEnvstVal == '0'}">
          <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="originalStore"/>
          </jsp:include>
        </c:if>
        <c:if test="${momsEnvstVal == '1'}">
          <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreSMoms.jsp" flush="true">
            <jsp:param name="targetId" value="originalStore"/>
          </jsp:include>
        </c:if>
        <%--// 매장선택 모듈 사용시 include --%>
      </td>
    </tr>
    <%-- 적용대상매장 --%>
    <tr>
      <th><s:message code="saleCopy.target.storeCd" /></th>
      <td  colspan="3">
        <%-- 매장선택 모듈 사용시 include --%>
        <c:if test="${momsEnvstVal == '0'}">
          <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="targetStore"/>
          </jsp:include>
        </c:if>
        <c:if test="${momsEnvstVal == '1'}">
          <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreSMoms.jsp" flush="true">
            <jsp:param name="targetId" value="targetStore"/>
          </jsp:include>
        </c:if>
        <%--// 매장선택 모듈 사용시 include --%>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <p class="s12 bk mt10 lh20">
          ※ 가격통제 관계 없이 모두 복사됩니다.<br />
        </p>
      </td>
    </tr>
   </tbody>
  </table>
  <%--//searchTbl--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/price/salePrice/storeSaleCopy.js?ver=20220124.02" charset="utf-8"></script>
