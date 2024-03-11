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
            $("#popStoreTitle").text(messages["kioskKeymap.envConfgStore"]);
        }else{
            $("#popStoreTitle").text(messages["kioskKeymap.envConfgPack"]);
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

        // 키맵그룹이 없습니다.
        if ($scope.envStoreTuClsTypeCombo.selectedValue === "" || $scope.envStoreTuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        // 단독매장에서 키맵적용시
        if (orgnFg === "STORE" && hqOfficeCd === "00000") {
            
            // '01' 키맵그룹을 매장포스환경에 적용하시겠습니까?
            $scope._popConfirm("'" + $scope.envStoreTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreKioskPosEnv.msg"], function() {

                // 단독매장은 본사 키맵 매장적용 사용불가
                $scope.save("N");
            });
        }

        // 프랜차이즈 매장에서 키맵적용시
        if (orgnFg === "STORE" && hqOfficeCd !== "00000") {

            // '01' 키맵그룹을 매장포스환경에 적용하시겠습니까?
            $scope._popConfirm("'" + $scope.envStoreTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreKioskPosEnv.msg"], function() {

                // 본사의 키맵그룹 정보도 수신하시겠습니까?
                var id = s_alert.randomString(5);
                var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);

                pop.find("p").html(messages["kioskKeyMap.keyMapStoreKioskPosEnv2.msg"] + "<br/>"
                                              + "[확인] 클릭시, 키맵설정 적용 & 본사 키맵그룹 '" + $scope.envStoreTuClsTypeCombo.text + "' 매장 적용<br/>"
                                              + "[취소] 클릭시, 키맵설정만 적용");

                // 팝업 띄우기
                $("#_alertTent").show();
                pop.show();

                // 확인 버튼 클릭시, 매장사용키맵 환경설정값 & 본사 키맵 정보 매장적용 UPDATE
                pop.find("a.btn_blue.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();

                    setTimeout(function () {
                        $scope.save("Y");
                    }, 50);

                    return false;
                });

                // 취소 버튼 클릭시, 매장사용키맵 환경설정값만 UPDATE
                pop.find("a.btn_gray.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();

                    setTimeout(function () {
                        $scope.save("N");
                    }, 50);

                    return false;
                });
            });
        }
        
    };

    // 키맵적용 저장
    $scope.save = function(applyYn){

        // 파라미터 설정
        var params = new Array();
        var obj = {};
        obj.storeCd = storeCd;
        obj.posNo = $("#hdStorePosNo").val();
        obj.envstCd = $("#hdStoreEnvstCd").val();
        obj.envstVal = $scope.envStoreTuClsTypeCombo.selectedValue;
        obj.tuClsType = $scope.envStoreTuClsTypeCombo.selectedValue;

        // 키맵매장적용 여부
        obj.chkTuClsTypeStore = applyYn;

        params.push(obj);

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/saveHqStoreKioskPosEnv.sb", params, function () {
            
            $scope.kioskKeyMapEnvStoreLayer.hide(true);
            // 키오스크키맵 재조회
            document.getElementById('btnSearchCls').click();
        });
    };

    // 닫기
    $scope.close = function () {

        $scope.envStoreTuClsTypeCombo.selectedIndex = 0;
    };

}]);