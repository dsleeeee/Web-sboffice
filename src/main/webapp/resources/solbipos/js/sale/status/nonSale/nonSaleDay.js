/****************************************************************
 *
 * 파일명 : nonSaleDay.js
 * 설  명 : 보증금현황 >일자별 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.16     권지현        1.0
 *
 * **************************************************************/
/**
 * get application
 */

var nonSaleFgData = [
    {"name":"전체","value":""},
    {"name":"매출","value":"0"},
    {"name":"반환","value":"1"}
];

var app = agrid.getApp();

/** 일자별 상세현황 controller */
app.controller('nonSaleDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nonSaleDayCtrl', $scope, $http, true));

    // 조회일자 세팅
    $scope.startDateDay = wcombo.genDateVal("#startDateNonSaleDay", gvStartDate);
    $scope.endDateDay = wcombo.genDateVal("#endDateNonSaleDay", gvEndDate);
  
    // 콤보박스 데이터 Set
    $scope._setComboData('nonSaleDaylistScaleBox', gvListScaleBoxData);
    // 메인화면매출표시
    $scope._setComboData("srchNonSaleFg", nonSaleFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("nonSaleDayCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("nonSaleDayCtrl", function (event, data) {
        $scope.searchNonSaleDayList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchNonSaleDayList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.startDateDay.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDateDay.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31 일) 제한
        if (diffDay > 30) {
            s_alert.pop(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params       = {};
        params.storeCd   = $("#nonSaleDaySelectStoreCd").val();
        params.startDate = wijmo.Globalize.format($scope.startDateDay.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.endDateDay.value, 'yyyyMMdd'); //조회기간
        params.listScale = $scope.listScaleCombo.text; //-페이지 스케일 갯수

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/nonSale/nonSale/getNonSaleDayList.sb", params, function() {});
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.nonSaleDaySelectStoreShow = function () {
        $scope._broadcast('nonSaleDaySelectStoreCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpNonSaleDay = function() {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
          // 선택 버튼 눌렀을때만
          if (s.dialogResult === "wj-hide-apply") {
            var scope = agrid.getScope('prodClassPopUpCtrl');
            var nonSaleDayCd = scope.getSelectedClass();
            var params = {};
            params.prodClassCd = nonSaleDayCd;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
              function(response){
                $scope.nonSaleDayCdModel = nonSaleDayCd;
                $scope.nonSaleDayCdNm = response.data.data;
              }
            );
          }
        });
    };

    // 상품분류정보 선택취소
    $scope.delNonSaleDay = function(){
        $scope.nonSaleDayCdModel = "";
        $scope.nonSaleDayCdNm = "";
    };

    // 엑셀 다운로드
    $scope.excelDownloadDay = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.startDateDay.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDateDay.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            s_alert.pop(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31 일) 제한
        if (diffDay > 30) {
            s_alert.pop(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params       = {};
        params.storeCd   = $("#nonSaleDaySelectStoreCd").val();
        params.startDate = wijmo.Globalize.format($scope.startDateDay.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.endDateDay.value, 'yyyyMMdd'); //조회기간

        $scope._broadcast('nonSaleDayExcelCtrl', params);
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

}]);


/** 일자별상품 엑셀다운로드 controller */
app.controller('nonSaleDayExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('nonSaleDayExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("nonSaleDayExcelCtrl", function (event, data) {
        $scope.searchNonSaleDayExcelList(data);
        // 기능수행 종료 : 반드시 추가
        //    event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchNonSaleDayExcelList = function (data) {
        // 파라미터
        var params       = {};
        params.storeCd   = data.storeCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.nonSaleFg = data.nonSaleFg;

        $scope.isChkProdClassDisplay();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/nonSale/nonSale/getNonSaleDayExcelList.sb", params, function() {
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
                  }, '매출현황2_보증금현황_일자별_'+getToday()+'.xlsx', function () {
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