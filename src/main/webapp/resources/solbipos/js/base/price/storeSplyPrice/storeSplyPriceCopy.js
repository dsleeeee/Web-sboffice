/****************************************************************
 *
 * 파일명 : storeSplyPriceCopy.js
 * 설  명 : 기초관리 > 가격관리 > 매장공급가관리 > 매장공급가복사 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeSplyPriceCopyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSplyPriceCopyCtrl', $scope, $http, false));

    $scope.$on("storeSplyPriceCopyCtrl", function (event, data) {
        event.preventDefault();
    });

    // 매장공급가 복사
    $scope.copySplyPrice = function () {

        if( isEmptyObject($("#originalStoreCd").val())) {
            $scope._popMsg("기준매장을 선택해주세요.");
            return false;
        }

        if (isEmptyObject($("#targetStoreCd").val())) {
            $scope._popMsg("적용대상매장을 선택해주세요.");
            return false;
        }

        if ($("#originalStoreCd").val() == $("#targetStoreCd").val()) {
            $scope._popMsg("기준매장과 적용대상매장이 같을 수 없습니다.");
            return false;
        }

        var msg = "(" + $("#originalStoreNm").val() + ")의 매장공급가를 (" + $("#targetStoreNm").val() + ")에 적용하시겠습니까?";
        $scope._popConfirm(msg, function () {

            var params = {};
            params.originalStoreCd = $("#originalStoreCd").val();
            params.targetStoreCd = $("#targetStoreCd").val();

            $scope._postJSONSave.withOutPopUp("/base/price/storeSplyPrice/copyStoreSplyPrice.sb", params, function (response) {

                if (response.data.status === 'OK') {
                    $scope._popMsg(messages["cmm.copySucc"]); // 복사 되었습니다.
                } else {
                    $scope._popMsg(messages["cmm.copyFail"]); //복사 중 에러가 발생 하였습니다.
                }
            });
        });
    };

    // <-- //검색 호출 -->
    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.originalStoreShow = function () {
        $scope._pageView('originalStoreCtrl', 1);
    };

    $scope.targetStoreShow = function () {
        $scope._pageView('targetStoreCtrl', 1);
    };
}]);