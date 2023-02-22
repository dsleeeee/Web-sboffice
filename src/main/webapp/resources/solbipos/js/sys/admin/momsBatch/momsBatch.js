/****************************************************************
 *
 * 파일명 : momsBatch.js
 * 설  명 : 맘스터치일괄처리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 맘스터치일괄처리 그리드 생성
 */
app.controller('momsBatchCtrl',  ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('momsBatchCtrl', $scope, $http, true));

    // 처리정보 콤보박스 셋팅
    $scope._setComboData('procInfo', momsBatchType);

    // 일괄처리
    $scope.$on("momsBatchCtrl", function(event, data) {

        // 본사코드를 선택하세요.
        if($("#hqOfficeCd").val() === ""){
            $scope._popMsg(messages["momsBatch.chk.hqOfficeCd"]);
            return false;
        }

        // 매장코드를 선택하세요.
        if($("#momsBatchStoreCd").val() === ""){
            $scope._popMsg(messages["momsBatch.chk.storeCds"]);
            return false;
        }

        // 추가정보를 입력하세요.
        if($("#datas").val() === ""){
            $scope._popMsg(messages["momsBatch.chk.datas"]);
            return false;
        }

        var params = {};
        params.hqOfficeCd = $("#hqOfficeCd").val();
        params.storeCds = $("#momsBatchStoreCd").val();
        params.nmcodeCd = $scope.procInfoCombo.selectedValue;
        params.datas = $("#datas").val();

        $scope._postJSONSave.withPopUp("/sys/admin/momsBatch/momsBatch/batchProc.sb", params, function (response) {
            console.log(response.data.data);
        });
    });

    // 본사코드 조회 팝업
    $scope.searchHq = function(){
        var popup = $scope.hqLayer;
        // 팝업 닫을때
        popup.show(true, function (s) {
            var hqScope = agrid.getScope('searchHqCtrl');
            hqScope.$apply(function(){
                hqScope._gridDataInit();
                if( !$.isEmptyObject(hqScope.getHq())  ) {
                    // 본사정보 셋팅
                    $("#hqOfficeNm").val(hqScope.getHq().hqOfficeNm);
                    $("#hqOfficeCd").val(hqScope.getHq().hqOfficeCd);
                }
            });

          // 본사 정보 초기화(이전데이터 남아있는 현상 발생)
          hqScope.setHq("");
        });
        event.preventDefault();
    };

    // 본사코드 선택취소
    $scope.delHq = function(){
        $("#hqOfficeNm").val("");
        $("#hqOfficeCd").val("");
    };

    // 추가정보 [⊥] 버튼
    $scope.btnAdd1 = function () {
        $("#datas").val($("#datas").val() + '⊥');
    };

    // 추가정보 [♪] 버튼
    $scope.btnAdd2 = function () {
        $("#datas").val($("#datas").val() + '♪');
    };

    //매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.momsBatchStoreShow = function () {
      $scope._broadcast('momsBatchStoreCtrl');
    };

}]);