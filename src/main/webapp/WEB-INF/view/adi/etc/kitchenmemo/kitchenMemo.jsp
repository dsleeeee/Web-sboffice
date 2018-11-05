<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/adi/etc/kitchenMemo/kitchenMemo/" />

<div class="subCon" ng-controller="kitchenMemoCtrl">
  <div class="updownSet oh">
    <span class="fl bk lh30">${menuNm}</span>
    <div class="txtIn">
      <button class="btn_skyblue" id="addBtn" style="display: none;" ng-click="addRow()">
        <s:message code="cmm.add" />
      </button>
      <button class="btn_skyblue" id="deleteBtn" style="display: none;" ng-click="delete()">
        <s:message code="cmm.delete" />
      </button>
      <button class="btn_skyblue" id="saveBtn" style="display: none;" ng-click="save()">
        <s:message code="cmm.save" />
      </button>
    </div>
  </div>

  <div id="grid" class="w100">
    <div class="wj-gridWrap mt10" style="height:315px; overflow-y: hidden;">
      <div class="row">
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
          <wj-flex-grid-column header="<s:message code="kitchenMemo.kitchnMemoCd"/>" binding="kitchnMemoCd" maxLength="3" width="90"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="kitchenMemo.kitchnMemoNm"/>" binding="kitchnMemoNm" maxLength="30" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="kitchenMemo.memoFg"/>" binding="memoFg" data-map="memoFgDataMap" width="60"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="kitchenMemo.useYn"/>" binding="useYn" data-map="useYnDataMap" width="60" ></wj-flex-grid-column>
          <c:if test="${orgnFg == 'STORE'}">
            <wj-flex-grid-column header="<s:message code="kitchenMemo.regFg"/>" binding="regFg" data-map="regFgDataMap" width="80"></wj-flex-grid-column>
          </c:if>

        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%--<div class="wj-TblWrapBr mt10" style="height: 400px;">--%>
    <%--<div class="wj-gridWrap" style="height:310px">--%>
      <%--<div id="theGrid" class="mt10">--%>
        <%----%>
      <%--</div>--%>
    <%--</div>--%>
  <%--</div>--%>
</div>

<script type="text/javascript">
  var orgnFg      = "${orgnFg}";
  var baseUrl     = "${baseUrl}";
  var envstVal    = "${envstVal}";

  var useYn       = ${ccu.getCommCodeExcpAll("067")};
  var memoFg      = ${ccu.getCommCodeExcpAll("070")};
  var regFg       = ${ccu.getCommCodeExcpAll("071")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/adi/etc/kitchenMemo/kitchenMemo.js?ver=20180911.01" charset="utf-8"></script>

