/****************************************************************
 *
 * 파일명 : instlDtl.js
 * 설  명 : 설치현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.23     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 설치구분 DropBoxDataMap
var instFgData = [
    {"name": "설치의뢰", "value": "0"},
    {"name": "신규설치", "value": "1"},
    {"name": "재설치", "value": "2"}
];

/**
 *  팝업 그리드 생성
 */
app.controller('instlDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name'); //설치구분
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("instlDtlCtrl", function(event, data) {
        $scope.instlDtlLayer.show(true);
        $scope.searchInstlDtlList(data);

        $("#lblStoreNmInstlDtl").text(data.storeNm);
        $("#lblStoreCdInstlDtl").text("[" + data.storeCd + "]");
        $("#lblPosNoInstlDtl").text( "( " + messages["instl.posInfo"] + " : " + data.posNo + " )");

        event.preventDefault();
    });

    // 설치현황 상세조회
    $scope.searchInstlDtlList = function(data){
        var params = {};
        params.storeCd = data.storeCd;
        params.posNo = data.posNo;

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getInstlDtlList.sb", params, function() {}, false);
    };

}]);