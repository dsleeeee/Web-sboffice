/****************************************************************
 *
 * 파일명 : kioskKeyMapCopyStore.js
 * 설  명 : 키오스크 키맵그 그룹복제 팝업 (매장용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.15     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('kioskKeyMapCopyStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapCopyStoreCtrl', $scope, $http, false));

    $scope._setComboData("orgStorePosNo", kioskPosList); // 키오스크용 포스 목록
    $scope._setComboData("storePosNo", kioskPosList); // 키오스크용 포스 목록

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskKeyMapCopyStoreCtrl", function(event, data) {
        $scope.setOrgTuClsType();
        event.preventDefault();
    });

    // copy : POS번호 선택 시, 키맵그룹 dropdown 조회
    $scope.setOrgTuClsType = function (s) {
        // 키맵그룹 dropdown 재조회
        $scope.setTuClsDropdownList();
    }

    // 키맵그룹 dropdownLIst 재조회
    $scope.setTuClsDropdownList = function (type){

        var url = '/base/prod/kioskKeyMap/kioskKeyMap/getKioskTuClsTypeList.sb';
        var params = {};
        params.posNo = $scope.orgStorePosNoCombo.selectedValue;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    for (var i = 0; i < list.length; i++) {
                        comboData = {};
                        comboData.name  = list[i].name;
                        comboData.value = list[i].value;
                        comboArray.push(comboData);
                    }

                    $scope._setComboData("orgStoreTuClsType", comboArray);

                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        });
    };

    // 적용
    $scope.btnInsertEnvStore = function () {

        // 파라미터 설정
        var params = new Array();

        // 키맵그룹이 없습니다.
        if ($scope.orgStorePosNoCombo.selectedValue === "" || $scope.orgStorePosNoCombo.selectedValue === null || $scope.storePosNoCombo.selectedValue === "" || $scope.storePosNoCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        // 복사 할 POS번호와 붙여 넣을 POS번호가 동일(새로운 그룹)
        if($scope.orgStorePosNoCombo.selectedValue === $scope.storePosNoCombo.selectedValue){
            // '01' 그룹을 복사하여 새 그룹을 생성하시겠습니까?
            $scope._popConfirm( "'" + $scope.orgStoreTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.tuClsTypeCopy.msg"], function() {

                // 파라미터 설정
                var params = {};
                params.posNo = $scope.orgStorePosNoCombo.selectedValue;
                params.tuClsType = $scope.orgStoreTuClsTypeCombo.selectedValue;

                $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/copyKioskTuClsType.sb", params, function (response) {

                    var scope = agrid.getScope('kioskKeyMapRegistCtrl');
                    scope.setTuClsType();

                    $scope.kioskKeyMapCopyStoreLayer.hide(true);

                });
            });
        } else {
            // 01번 포스 01번 그룹을 02번 포스 01번 그룹으로 복사하시겠습니까?
            $scope._popConfirm( $scope.orgStorePosNoCombo.selectedValue + messages["kioskKeyMap.copy.msg1"] + " " +
                                    $scope.orgStoreTuClsTypeCombo.selectedValue + messages["kioskKeyMap.copy.msg2"] + " " +
                                    $scope.storePosNoCombo.selectedValue + messages["kioskKeyMap.copy.msg3"], function() {

                var params = {};
                params.storeCd = storeCd;
                params.orgPosNo = $scope.orgStorePosNoCombo.selectedValue;
                params.orgTuClsType = $scope.orgStoreTuClsTypeCombo.selectedValue;
                params.posNo = $scope.storePosNoCombo.selectedValue;

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/copyStoreKioskTuClsType.sb", params, function () {

                    var scope = agrid.getScope('kioskKeyMapRegistCtrl');
                    scope.setTuClsType();

                    $scope.kioskKeyMapCopyStoreLayer.hide(true);

                });
            });
        }

    };

    // 닫기
    $scope.close = function () {
        $scope.orgStorePosNoCombo.selectedIndex = 0;
        $scope.orgStoreTuClsTypeCombo.selectedIndex = 0;
        $scope.storePosNoCombo.selectedIndex = 0;
    };

}]);