<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--<f:form modelAttribute="sessionInfo" method="post" action="/application/pos/posLogin.sb">--%>
    <%--매장코드 : <input type="text" id="storeCd" name="storeCd" value="D000001"/>--%>
    <%--&lt;%&ndash;<input type="text" id="userId" name="userId" value="111"/>&ndash;%&gt;--%>
    <%--하드웨어인증키 : <input type="text" id="hwAuthKey" name="hwAuthKey" value="1234"/>--%>
    <%--요청url : <input type="text" id="url" name="url" value="simpleMemberJoin"/>--%>

    <%--<button type="submit" class="btn_skyblue">gogogogo</button>--%>
<%--</f:form>--%>

<div id="theGrid" ng-app="app" ng-controller="appTCCtrl">
    <%-- 조회 --%>
    <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch" ng-click="search()"><s:message code="cmm.search"/></button>
    </div>
    <wj-flex-grid
        class="gridStyle"
        items-source="data"
        control="flex">

        <!-- define columns -->
        <wj-flex-grid-column header="NmcodeCd" binding="nmcodeCd" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="NmcodeNm" binding="nmcodeNm" width="*"></wj-flex-grid-column>

        <!-- enable column filtering-->
        <wj-flex-grid-filter></wj-flex-grid-filter>
    </wj-flex-grid>
</div>

<script type="text/javascript">

    var app = agrid.genGrid('app', 'appTCCtrl');

    $(document).ready(function () {
        var theGrid = agrid.getGrid("theGrid");

        var param = {};
        param.nmcodeGrpCd = "000";
        theGrid.search = function () {
          $.postJSON("/sys/cd/systemCd/systemCd/list.sb", param,
            function (result) {
              var list = result.data.list;
              if (list.length === undefined || list.length === 0) {
                s_alert.pop(result.message);
                return;
              }
              var data = new wijmo.collections.CollectionView(list);
              //track the changes
              data.trackChanges = true;
              // initialize the scope data.
              theGrid.data = data;

            },
            function (result) {
              s_alert.pop(result.message);
              return;
            }
          );
        }

        theGrid.getGrid.selectionChanged.addHandler(function (s, e) {
          console.log("click");
        });

    });

</script>
