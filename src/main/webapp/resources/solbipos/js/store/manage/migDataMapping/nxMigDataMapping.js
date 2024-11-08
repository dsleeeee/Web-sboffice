/****************************************************************
 *
 * 파일명 : nxMigDataMapping.js
 * 설  명 : MXPOS1 매출정보 데이터 이관 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 이관여부
var migYnData = [
    {"name":"전체","value":""},
    {"name":"처리완료","value":"Y"},
    {"name":"미처리","value":"N"},
    {"name":"에러","value":"E"}
];

/**
 *  NXPOS1 데이터 이관 그리드 생성
 */
app.controller('nxMigDataMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nxMigDataMappingCtrl', $scope, $http, true));


    var startDt = new Date();
    startDt.setMonth(startDt.getMonth() - 1);

    // 날짜 포맷팅: 'yyyyMMdd' 형식으로 변환
    var oneMonth = startDt.toISOString().slice(0, 10).replace(/-/g, '');

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#nxStartDate", oneMonth);
    var endDate = wcombo.genDateVal("#nxEndDate", gvEndDate);
    $scope._setComboData("migYnCombo", migYnData); // 팀별

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.migYnDataMap = new wijmo.grid.DataMap(migYnData, 'value', 'name'); //이관여부

        // 조회
        // $scope.searchNxMigDataMapping();
    };

    // <-- 검색 호출 -->
    $scope.$on("nxMigDataMappingCtrl", function(event, data) {
        $scope.searchNxMigDataMapping();
        event.preventDefault();
    });

    $scope.searchNxMigDataMapping = function() {
        var params = {};
        params.startDate    = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate      = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCd      = $scope.nxStoreCd;
        params.storeNm      = $scope.nxStoreNm;
        params.procYn       = $scope.srchMigYnCombo.selectedValue;

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMapping/getNxMigDataMappingList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 신규이관요청
    $scope.addNxInfo = function() {
        $scope.nxMigDataMappingInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // OKPOS-KCP 데이터 이관 신규등록 팝업 핸들러 추가
        $scope.nxMigDataMappingInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('nxMigDataMappingInfoCtrl', "clear");
            }, 50)
        });
    });

}]);