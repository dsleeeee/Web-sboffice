/****************************************************************
 *
 * 파일명 : kioskKeyMapEnvStore.js
 * 설  명 : 키오스크 키맵그룹코드(매장/포장) 적용 팝업 (매장용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('kioskKeyMapEnvStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapEnvStoreCtrl', $scope, $http, false));

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskKeyMapEnvStoreCtrl", function(event, data) {

        // 매장적용(매장/포장) 키값 hidden에 갖고 있기
        $("#hdStorePosNo").val(data.posNo);
        $("#hdStoreEnvstCd").val(data.envstCd);
        $("#lblPosNo").text(data.posNo);

        // 제목셋팅
        if(data.envstCd === "4068") {
            $("#popStoreTitle").text(messages["kioskKeymap.envConfgStore"] + "_[4068]");
        }else{
            $("#popStoreTitle").text(messages["kioskKeymap.envConfgPack"]+ "_[4069]");
        }

        // 환경설정 값 조회
        $scope.getEnv();

        event.preventDefault();

    });

    // 환경설정 값 조회
    $scope.getEnv = function(){

        var params = {};
        params.posNo = $("#hdStorePosNo").val();
        params.envstCd = $("#hdStoreEnvstCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskEnv.sb", params,
            function(response){
                if(response.data.data !== null && response.data.data !== ""){

                    // 본사에서 사용중인 키맵으로 셋팅
                    $scope.envStoreTuClsTypeCombo.selectedValue = response.data.data;

                }else{
                    $scope.envStoreTuClsTypeCombo.selectedIndex = 0;
                }
            }
        );
    };

    // 적용
    $scope.btnInsertEnvStore = function () {

        // 파라미터 설정
        var params = new Array();

        // 키맵그룹이 없습니다.
        if ($scope.envStoreTuClsTypeCombo.selectedValue === "" || $scope.envStoreTuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        // '01' 키맵그룹을 매장포스환경에 적용하시겠습니까?
        $scope._popConfirm("'" + $scope.envStoreTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreKioskPosEnv.msg"], function() {

            var obj = {};
            obj.storeCd = storeCd;
            obj.posNo = $("#hdStorePosNo").val();
            obj.envstCd = $("#hdStoreEnvstCd").val();
            obj.envstVal = $scope.envStoreTuClsTypeCombo.selectedValue;
            obj.tuClsType = $scope.envStoreTuClsTypeCombo.selectedValue;

            // 키맵매장적용 여부 - 매장은 사용안함
            obj.chkTuClsTypeStore = "N";

            params.push(obj);

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/saveHqStoreKioskPosEnv.sb", params, function () {

                $scope.kioskKeyMapEnvStoreLayer.hide(true);

            });
        });

    };

    // 닫기
    $scope.close = function () {

        $scope.envStoreTuClsTypeCombo.selectedIndex = 0;
    };

}]);