<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>--%>
        <%--<input type="text"--%>
        <%--id="<c:out value="${param.targetId}Nm"/>"--%>
        <%--class="sb-input fl mr5"--%>
        <%--style="cursor:pointer; width:150px;"--%>
        <%--value="미선택"--%>
        <%--&lt;%&ndash;ng-click="<c:out value="${param.targetId}"/>Show()"&ndash;%&gt;--%>
        <%--readonly/>--%>
<button class="btn_skyblue" ng-click="selectUploadStoreShow()"><s:message code="selectStore.upload" /></button>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            > <s:message code="cmm.uploadStore.select"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
            <div class="w100">

                <%-- 버튼영역 --%>
                <div class="mt10 tr">
                    <%-- 양식다운로드 --%>
                    <button class="btn_skyblue" ng-click="sampleDownload()"><s:message code="cmm.excel.sampleDown" /></button>
                    <%-- 엑셀업로드 --%>
                    <button class="btn_skyblue" ng-click="excelUpload()"><s:message code="cmm.excel.excelUpload" /></button>
                    <%-- 조회 --%>
                    <button class="btn_skyblue" ng-click="searchStoreUpload()"><s:message code="cmm.search" /></button>
                    <%-- 삭제 --%>
                    <button class="btn_skyblue" ng-click="uploadDeleteSave()"><s:message code="cmm.delete" /></button>
                </div>

                <%--위즈모 테이블--%>
                <div class="theGrid mt10" style="height: 400px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="selectUploadStoreFlex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="selectStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>


<%-- 공용 업로드매장 팝업 - 엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/common/popup/selectUploadStoreExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>


<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 매장선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

        var targetId = '${param.targetId}';

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, false));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
        };

        $scope.searchFg = "N";

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on(targetId + 'Ctrl', function (event) {
            // 매장선택 팝업 오픈
            eval('$scope.wj' + targetId + 'Layer.show(true)');

            // 팝업 닫힐시 이벤트
            eval('$scope.wj' + targetId + 'Layer').hidden.addHandler(function () {
                if ('${param.closeFunc}' !== '') {
                    if('${param.closeFunc}'.indexOf(',')>-1){
                        var closeFunc = ('${param.closeFunc}').split(",");
                        for(var i=0; i<closeFunc.length; i++){
                            eval('$scope.'+closeFunc[i]+'()');
                        }
                    }else{
                        eval('$scope.${param.closeFunc}()');
                    }
                }
            });

            if ($scope.searchFg == "N") {
                // 조회
                $scope.searchStoreUpload();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 조회
        $scope.searchStoreUpload = function () {
            // 파라미터
            var params = {};

            $scope._inquirySub("/common/popup/selectStore/getSelectUploadStoreList.sb", params, function () {
                // $scope.searchFg = "Y";

                // 저장기능 수행후 재조회
                var preTargetId = targetId.substring(0, targetId.indexOf('SelectUpload'));
                var scope = agrid.getScope(preTargetId + 'Ctrl');
                scope.selectUploadStoreText();

                // 선택
                // $scope.storeSelected();
            });
        };

        // 선택
        // $scope.storeSelected = function () {
        //     var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
        //     var arrStoreCd = [];
        //     var strStoreCd = "";
        //     var strStoreNm = "";
        //     var cnt        = 0;
        //
        //     for (var i = 0; i < flex.length; i++) {
        //         if (cnt == 0) {
        //             strStoreCd = flex[i].storeCd;
        //             strStoreNm = flex[i].storeNm;
        //         }
        //         arrStoreCd.push(flex[i].storeCd);
        //         cnt++;
        //     }
        //
        //     $("#" + targetId + "Cd").val(arrStoreCd.join());
        //
        //     if (cnt == 0) {
        //         $("#" + targetId + "Nm").val("미선택");
        //     }
        //     else if (cnt == 1) {
        //         $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
        //     }
        //     else if (cnt > 1) {
        //         $("#" + targetId + "Nm").val(strStoreCd + " "+messages["selectStore.except"]+" " + (cnt - 1) + messages["selectStore.cntStore"]);
        //     }
        //
        //     // eval('$scope.wj' + targetId + 'Layer.hide(true)');
        // };

        // 양식다운로드
        $scope.sampleDownload = function () {
            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.selectUploadStoreFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, '업로드매장_'+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        };

        // <-- 엑셀업로드 -->
        $scope.excelUpload = function () {
            // 엑셀업로드 팝업
            $("#selectUploadStoreExcelUploadFile").val('');
            $("#selectUploadStoreExcelUploadFile").trigger('click');
            $("#selectUploadStoreExcelUploadTargetId").text(targetId);
        };
        // <-- //엑셀업로드 -->

        // 삭제
        $scope.uploadDeleteSave = function () {
            var params = {};

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/common/popup/selectStore/getSelectUploadStoreExcelUploadDeleteAll.sb", params, function(){
                // 조회
                $scope.searchStoreUpload();
            });
        };

    }]);
</script>