/****************************************************************
 *
 * 파일명 : statusApprInfo.js
 * 설  명 : 기초관리 > 매장정보관리 > 매장현황 > 관리매장 승인내역 > 매출 상세내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.24     이다솜      1.0
 *
 * **************************************************************/

app.controller('statusApprInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusApprInfoCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    }

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("statusApprInfoCtrl", function (event, data) {

        $scope.statusApprInfoLayer.show(true);

        // 조회
        $scope.saleDtlInfo(data);

        $scope._broadcast('statusCardPayInfoCtrl', data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 매출 상세내역 조회
    $scope.saleDtlInfo = function (data) {

        // 파라미터
        var params      = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;
        params.payCol = data.payCol;

        $scope._postJSONQuery.withOutPopUp( "/store/manage/status/storeAppr/getSaleDtlInfo.sb", params, function(response){
            var data = response.data.data;
            $scope.storeStatus = data;

            //$scope.hqEmp.empInfo = ' [' + data.empNo + ']' + data.empNm;
        });

    };

}]);

app.controller('statusCardPayInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusCardPayInfoCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.apprFgDataMap = new wijmo.grid.DataMap(vApprFg, 'value', 'name');
        $scope.apprProcFgDataMap = new wijmo.grid.DataMap(vApprProcFg, 'value', 'name');


        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

    }

    $scope.$on("statusCardPayInfoCtrl", function (event, data) {

        // 조회
        $scope.cardPayInfo(data);

        // Set Title & Column Control
        var grid = wijmo.Control.getControl("#wjApprCardList");
        var columns = grid.columns;

        if(data.payApprType === "CARD"){
            $("#spanPayTitle").text(messages["storeStatus.cardPayInfo"]);
            columns[3].visible = true;
            columns[5].visible = true;
            columns[9].visible = true;
            columns[10].visible = true;
            columns[11].visible = true;
        }
        else if(data.payApprType === "CASH"){
            $("#spanPayTitle").text(messages["storeStatus.cashPayInfo"]);
            columns[3].visible = false;
            columns[5].visible = false;
            columns[9].visible = false;
            columns[10].visible = false;
            columns[11].visible = false;
        }

        $scope._broadcast('statusProductInfoCtrl', data);

        // 기능수행 종료 : 반드시 추가
        //event.preventDefault();
    });

    $scope.cardPayInfo = function (data) {

        // 파라미터
        var params      = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;
        params.payApprType = data.payApprType;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/store/manage/status/storeAppr/getCardPayInfo.sb", params);
    };

}]);

app.controller('statusProductInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusProductInfoCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

    }

    $scope.$on("statusProductInfoCtrl", function (event, data) {

        // 조회
        $scope.saleProductInfo(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

    $scope.saleProductInfo = function (data) {

        // 파라미터
        var params      = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/store/manage/status/storeAppr/getSaleProductInfo.sb", params);
    };

    // 닫기버튼 클릭
    $scope.close = function(){
        $scope.statusApprInfoLayer.hide();
    };
}]);