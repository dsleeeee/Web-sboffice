/****************************************************************
 *
 * 파일명 : empCardInfo.js
 * 설  명 : 사원카드정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('empCardInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empCardInfoCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("empCardInfoCtrl", function(event, data) {
        
        // 조회
        $scope.searchEmpCardInfo();
        event.preventDefault();
    });
    
    // 조회
    $scope.searchEmpCardInfo = function () {

        // 파라미터
        var params = {};
        params.listScale = $scope.listScale;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/store/emp/cardInfo/getEmpCardInfo.sb", params, function(){});
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var vScope = agrid.getScope('excelUploadEmpCardInfoCtrl');
        var msg = messages["empCardInfo.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        s_alert.popConf(msg, function () {

            /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            vScope.parentCtrl = 'empCardInfoCtrl';

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');

        });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('empCardInfoCtrl', 1);
    };

    // 양식 다운로드
    $scope.excelDownload = function () {
        var vScope = agrid.getScope('excelUploadEmpCardInfoCtrl');
        vScope.excelFormDownload();
    };

}]);