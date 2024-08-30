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

// 매장상태구분 DropBoxDataMap
var sysStatFgComboData = [
    {"name": "전체", "value": ""},
    {"name": "오픈", "value": "1"},
    {"name": "폐점", "value": "2"},
    {"name": "중지", "value": "3"},
    {"name": "데모", "value": "9"}
];

app.controller('lsmKioskStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('lsmKioskStoreCtrl', $scope, $http, true));

    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 매장상태구분
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
        params.storeCds         = $("#lsmKioskCd").val();
        params.storeHqBrandCd   = $scope.storeHqBrandCd;
        params.tuClsType        = $scope.srchTuClsType;
        params.tuClsTypeNm      = $scope.srchTuClsTypeNm;

        params.listScale = 500;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/storeMoms/lsmStore/lsmStore/getLsmKioskStoreList.sb", params, function (){
        });
    };

    // 양식다운로드
    $scope.sampleDownload = function () {
        // 파라미터
        var params = {};
        params.downFg = 'S';
        $scope._broadcast('lsmKioskStoreExcelCtrl',params);
    };

    // 엑셀다운로드
    $scope.excelDownload = function (){
        // 파라미터
        var params = {};
        params.storeCds         = $("#lsmKioskCd").val();
        params.storeHqBrandCd   = $scope.storeHqBrandCd;
        params.tuClsType        = $scope.srchTuClsType;
        params.tuClsTypeNm      = $scope.srchTuClsTypeNm;
        params.downFg           = 'A'
        $scope._broadcast('lsmKioskStoreExcelCtrl',params);
    }

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["lsmStore.excelUpload.confmMsg"];  // 매장수정허용카테고리(LSM)인 키오스크 카테고리코드만 수정됩니다.

        $scope._popConfirm(msg, function() {

            $("#lsmKioskStoreExcelUpFile").val('');
            $("#lsmKioskStoreExcelUpFile").trigger('click');

        });
    };

    $scope.uploadCallBack = function () {
        $scope._pageView('lsmKioskStoreCtrl', 1);
    };

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
    $scope.searchExcelList = function (data) {
        // 파라미터
        var params       = data;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/storeMoms/lsmStore/lsmStore/getLsmKioskStoreExcelList.sb", params, function() {

            var flex = $scope.excelFlex;

            if (flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            var msg = '';

            if(params.downFg === 'S'){
                msg = messages["lsmStore.lsmStore"] + '(' + messages["lsmStore.kiosk"] +')_' + messages["cmm.excel.sampleDown"];
            }else{
                msg = messages["lsmStore.lsmStore"] + '(' + messages["lsmStore.kiosk"] +')_';
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        if(params.downFg === 'S') {
                            if(column.binding == 'saleUprc' || column.binding == 'sysStatFg'){
                            }else {
                                return column.visible;
                            }
                        }else{
                            return column.visible;
                        }
                    }
                }, msg + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);