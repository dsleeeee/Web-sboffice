/****************************************************************
 *
 * 파일명 : logSend.js
 * 설  명 : POS 시스템 로그 송신 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var dbSendYn = [
    {"name": messages["logSend.logSendExpected"], "value": "Y"},
    {"name": messages["logSend.release"], "value": "N"}
]

// 포함/미포함 구분
var incComboData = [
    {"name": "포함", "value": "Y"},
    {"name": "미포함", "value": "N"}
];

app.controller('logSendCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('logSendCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("sysStatFg", sysStatFg);
    $scope._setComboData("incSmlogYn", incComboData);
    $scope._setComboData("incDbYn", incComboData);

    $scope.initGrid = function (s, e) {

        $scope.dbSendYnDataMap = new wijmo.grid.DataMap(dbSendYn, 'value', 'name');
        $scope.incDataMap = new wijmo.grid.DataMap(incComboData, 'value', 'name');
    };

    // 조회 버튼 클릭
    $scope.$on("logSendCtrl", function(event, data) {
        $scope.getPosList();
        event.preventDefault();
    })

    $scope.getPosList = function () {

        var params = {};
        /*params.hqOfficeCd = "";
        params.hqOfficeCd = "";
        params.hqOfficeCd = "";
        params.hqOfficeCd = "";*/
        params.sysStatFg  = $scope.sysStatFg;

        $scope._inquiryMain("/sys/admin/logSend/logSend/getPosList.sb", params, function() {}, false);
    }
    
    $scope.logSend = function () {

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].dbSendYn !== 'Y') {
                    $scope.flex.collectionView.items[i].incDb = $scope.incDbCombo.selectedValue;
                    $scope.flex.collectionView.items[i].incSmlog = $scope.incSmlogCombo.selectedValue;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        if(params.length == 0){
            $scope._popMsg("변경사항이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin/logSend/logSend/updateLogSend.sb", params, function(){
            $scope.getPosList();
        });
    }

}]);