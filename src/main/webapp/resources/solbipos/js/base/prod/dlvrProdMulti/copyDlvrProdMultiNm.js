/****************************************************************
 *
 * 파일명 : copyDlvrProdMultiNm.js
 * 설  명 : 상품명칭 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.30     김유승      1.0
 *
 * **************************************************************/

app.controller('copyDlvrProdMultiNmCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyDlvrProdMultiNmCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    $scope.$on("copyDlvrProdMultiNmCtrl", function(event, data) {
        event.preventDefault();
    });

    // 복사
    $scope.copy = function(){

        // 기준매장을 선택해주세요.
        if( isEmptyObject($("#originalStoreFg").val()) || isEmptyObject($("#originalStoreCd").val()) ) {
            $scope._popMsg(messages['dlvrProdMulti.require.originalStoreCd']);
            return false;
        }

        // 적용대상매장을 선택해주세요.
        if (isEmptyObject($("#targetStoreCd").val())) {
            $scope._popMsg(messages['dlvrProdMulti.require.targetStoreCd']);
            return false;
        }

        // 기준매장과 적용대상매장이 같을 수 없습니다.
        if ($("#originalStoreCd").val() === $("#targetStoreCd").val()) {
            $scope._popMsg(messages['dlvrProdMulti.require.notEqualStoreCd']);
            return false;
        }

        var params = {};
        params.originalStoreFg = $("#originalStoreFg").val();
        params.originalStoreCd = $("#originalStoreCd").val();
        params.targetStoreCd   = $("#targetStoreCd").val();

        var msg = "(" + $("#originalStoreNm").val() + ")의 상품명칭을 ("  + $("#targetStoreNm").val() + ")에 적용하시겠습니까?";
        s_alert.popConf(msg, function() {

            $scope._postJSONSave.withPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/copyDlvrProdMultiNm.sb", params, function (response) {

                var result = response.data.data;

                if(result < 1){
                    $scope._popMsg(messages["cmm.registFail"]);

                }else{
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    $scope.close();

                    // 매장에서 등록하는 경우, 상품 명칭 매핑리스트 재조회
                    if(orgnFg === "STORE"){
                        $scope._pageView('dlvrProdMultiNmMappingCtrl', 1);
                    }
                }
            });

        });
    };

    // 팝업 닫기
    $scope.close = function(){
        // 초기화
        $("#originalStoreFg").val("");
        $("#originalStoreCd").val("");
        $("#originalStoreNm").val("선택");

        if(orgnFg === "HQ"){
            $("#targetStoreCd").val("");
            $("#targetStoreNm").val("선택");
        }else{
            $("#targetStoreCd").val(storeCd);
            $("#targetStoreNm").val("[" + storeCd + "] " + storeNm);
        }

        $scope.copyDlvrProdMultiNmLayer.hide();
    };

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