/****************************************************************
 *
 * 파일명 : lsmKioskStore.js
 * 설  명 : 맘스터치 > 매장관리 > LSM사용매장조회 > 키오스크 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.03.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


app.controller('lsmKioskStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('lsmKioskStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("lsmKioskStoreCtrl", function (event, data) {
        $scope.searchLsmKioskStoreList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 리스트 조회
    $scope.searchLsmKioskStoreList = function () {
        // 파라미터
        var params       = {};
        params.listScale = 500;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/storeMoms/lsmStore/lsmStore/getLsmKioskStoreList.sb", params, function (){
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function (){
        // 파라미터
        var params = {};
        $scope._broadcast('lsmKioskStoreExcelCtrl',params);
    }
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('lsmKioskStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('lsmKioskStoreExcelCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("lsmKioskStoreExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
    });

    // 리스트 조회
    $scope.searchExcelList = function () {
        // 파라미터
        var params       = {};
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/storeMoms/lsmStore/lsmStore/getLsmKioskStoreExcelList.sb", params, function() {

            var flex = $scope.excelFlex;

            if (flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["lsmStore.lsmStore"] + '(' + messages["lsmStore.kiosk"]  +')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);