/****************************************************************
 *
 * 파일명 : storeVerDel.js
 * 설  명 : 매장별 POS 버전 삭제 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.10.12    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeVerDelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeVerDelCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("progFg", progFg); // 포스버전구분

    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap = new wijmo.grid.DataMap(progFg, 'value', 'name'); // 포스버전구분
    };

    $scope.$on("storeVerDelCtrl", function(event, data) {
        // 매장별 포스 버전 정보 조회
        $scope.searchStoreVer();
        event.preventDefault();
    });

    // 매장별 포스 버전 정보 조회
    $scope.searchStoreVer = function () {

        var params = {};
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.progFg = $scope.srchProgFgCombo.selectedValue;
        params.verSerNo = $scope.verSerNo;
        params.listScale = 500;

        $scope._inquiryMain("/pos/confg/storeVerDel/getStoreVerList.sb", params, function() {}, false);
    };

    // 매장별 포스 버전 삭제
    $scope.del = function(){

        $scope._popConfirm(messages["cmm.choo.delete"], function() {

            var params = [];

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                $scope._popMsg(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/pos/confg/storeVerDel/deleteStoreVer.sb", params, function(){
                $scope._pageView('storeVerDelCtrl', 1);
            });

        });
    };

    // 매장별 포스 버전 정보 조회 엑셀다운로드 버튼 클릭
    $scope.excelDownload = function () {

       if ($scope.flex.rows.length <= 0) {
           $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
           return false;
       }

       var params = {};
       params.hqOfficeCd = $scope.hqOfficeCd;
       params.hqOfficeNm = $scope.hqOfficeNm;
       params.storeCd = $scope.storeCd;
       params.storeNm = $scope.storeNm;
       params.progFg = $scope.srchProgFgCombo.selectedValue;
       params.verSerNo = $scope.verSerNo;

        $scope._broadcast('storeVerDelExcelCtrl', params);
    };

}]);

// 엑셀다운로드 그리드
app.controller('storeVerDelExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeVerDelExcelCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap = new wijmo.grid.DataMap(progFg, 'value', 'name'); // 포스버전구분
    };

    $scope.$on("storeVerDelExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 매장별 포스 버전 정보 조회 엑셀다운로드
    $scope.searchExcelList = function (data) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/pos/confg/storeVerDel/getStoreVerExcelList.sb", data, function() {
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
            }, messages["storeVerDel.storeVerDel"] + "_"  + getCurDateTime()+'.xlsx', function () {
            $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
            }, 10);
        });
    };

}]);