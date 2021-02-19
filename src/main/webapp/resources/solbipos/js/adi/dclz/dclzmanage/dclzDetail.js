/****************************************************************
 *
 * 파일명 : dclzDetail.js
 * 설  명 : 근태관리 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  근태관리 상세 팝업 생성
 */
app.controller('dclzDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dclzDetailCtrl', $scope, $http, true));

    $scope.$on("dclzDetailCtrl", function (event, data) {

        if(!isEmptyObject(data)){
            // 기존 정보 불러오기
            $scope.getInfo(data);
        }
    });

    // 기존 정보 조회
    $scope.getInfo = function(data){

        var params = {};
        params.storeCd = data.storeCd;
        params.empNo = data.empNo;
        params.empInDate = data.empInDate;
        params.inFg = data.inFg;

        $scope._postJSONQuery.withOutPopUp( "/adi/dclz/dclzmanage/dclzmanage/detail.sb", params, function(response){

            var result = response.data.data;

            $("#lblSaleDate").text(getFormatDate(result.saleDate)); // 영업일자
            $("#lblEmpNm").text(result.empNm); // 사원명

            // 출근일시
            var vEmpInDt = getFormatDate((result.empInDt).substr(0, 8)) + " " +
                            (result.empInDt).substr(8, 2) + ":" +
                            (result.empInDt).substr(10, 2) + ":" +
                            (result.empInDt).substr(12, 2);
            $("#lblEmpInDt").text(vEmpInDt);

            // 퇴근일시
            var vEmpOutDt = getFormatDate((result.empOutDt).substr(0, 8)) + " " +
                (result.empOutDt).substr(8, 2) + ":" +
                (result.empOutDt).substr(10, 2) + ":" +
                (result.empOutDt).substr(12, 2);
            $("#lblEmpOutDt").text(vEmpOutDt);

            $("#lblRemark").text(result.remark); // 비고

            // 필요한 키값 hidden에 가지고 있기(수정, 삭제시 사용)
            $("#hdStoreCd").val(result.storeCd);
            $("#hdEmpNo").val(result.empNo);
            $("#hdEmpInDate").val(result.empInDate);
            $("#hdInFg").val(result.inFg);

        });
    };

    // 수정
    $scope.modify = function(){
        var params    = {};
        params.storeCd = $("#hdStoreCd").val();
        params.empNo = $("#hdEmpNo").val();
        params.empInDate = $("#hdEmpInDate").val();
        params.inFg = $("#hdInFg").val();
        $scope.wjDclzRegistLayer.show(true);
        $scope._broadcast('dclzRegistCtrl', params);
    }

    // 닫기
    $scope.close = function () {
        $scope.wjDclzDetailLayer.hide();
    }

}]);