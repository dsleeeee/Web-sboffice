/****************************************************************
 *
 * 파일명 : sideProdClass.js
 * 설  명 : 상품별 >분류별상품 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.21     권지현        1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 분류별상품 상세현황 controller */
app.controller('sideProdClassCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideProdClassCtrl', $scope, $http, true));

    // 조회일자 세팅
    var startDate = wcombo.genDateVal("#startDateProdClass", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateProdClass", gvEndDate);


    // 콤보박스 데이터 Set
    $scope._setComboData('prodClasslistScaleBox', gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("sideProdClassCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("sideProdClassCtrl", function (event, data) {
        $scope.searchProdClassList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchProdClassList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }
        // 파라미터
        var params       = {};
        params.storeCd   = $("#pordClassSelectStoreCd").val();
        params.prodCd    = $("#srchProdCd").val();
        params.prodNm    = $("#srchProdNm").val();
        params.prodClassCd = $("#prodClassSelectClassCd").val();
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/side/side/sideProdClass.sb", params, function() {});
    };

    // 엑셀 다운로드
    $scope.excelDownloadClass = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        var params       = {};
        params.storeCd   = $("#pordClassSelectStoreCd").val();
        params.prodCd    = $("#srchProdCd").val();
        params.prodNm    = $("#srchProdNm").val();
        params.prodClassCd = $("#prodClassSelectClassCd").val();
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._broadcast('prodClassExcelCtrl',params);
    };

    // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.flex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'pathNm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {

        $scope._broadcast('prodClassCheckPersistPopUpCtrl', {
            selectCancelFgCheckPersist: $("#_selectCancelFgCheckPersist").val()
        });
        var popUp = $scope.prodClassCheckPersistPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassCheckPersistPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd[0];
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/treePopup/getProdClassCdNmCheck.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = (isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : "");
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
        $("#_selectCancelFgCheckPersist").val("Y");
    };

}]);


app.controller('prodClassExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodClassExcelCtrl", function (event, data) {
        $scope.searchProdClassExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchProdClassExcelList = function (data) {
        // 파라미터
        var params       = {};
        params.storeCd = data.storeCd;
        params.prodCd = data.prodCd;
        params.prodNm = data.prodNm;
        params.prodClassCd = data.prodClassCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope.isChkProdClassDisplay();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/side/side/sideProdClassExcel.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["side.prodClass"] + '_'+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

    // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.excelFlex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'pathNm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

}]);