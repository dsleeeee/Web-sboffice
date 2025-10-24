/****************************************************************
 *
 * 파일명 : dlvrAgencyReg.js
 * 설  명 : 배달관리 - 배달정보 - 배달대행사 연동 - 배달대행사 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrAgencyRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAgencyRegCtrl', $scope, $http, false));

    $scope.$on("dlvrAgencyRegCtrl", function (event, data) {

        // 배달대행사 코드 조회
        $scope.getDlvrAgency();

    });

    // 배달대행사 코드 조회
    $scope.getDlvrAgency = function () {

        var params = {};
        params.linkType = "003";

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getDlvrAgency.sb", params, function (response) {
            var data = response.data.data.list;

            if(data.code === "0000") {

                var list = data.data;
                var comboArray = [];
                var comboData = {};

                // 콤보 기본값 '선택' 추가
                comboData.name  = messages["cmm.select"];
                comboData.value = "";
                comboArray.push(comboData);

                for (var i = 0; i < list.length; i++) {
                    comboData = {};
                    comboData.name = list[i].agencyName;
                    comboData.value = list[i].agencyCode;
                    comboArray.push(comboData);
                }

                $scope._setComboData("srchDlvrAgency", comboArray);

            } else {
                $scope._popMsg(data.message);
                return;
            }

        });

    };

    // 하위 배달대행사 코드 조회
    $scope.getSubAgency = function (s) {

        var comboArray = [];
        var comboData = {};

        // 콤보 기본값 '선택' 추가
        comboData.name  = messages["cmm.select"];
        comboData.value = "";
        comboArray.push(comboData);

        // 배달대행사 코드 선택값이 없는경우, 하위 배달대행사 콤보박스 disabled 처리
        if (s.selectedValue === "") {
            $scope._setComboData("srchSubAgency", comboArray);
            $scope.srchSubAgencyCombo.isDisabled = true;
            return;
        }

        var params = {};
        params.linkType = "003";

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getDlvrAgency.sb", params, function (response) {
            var data = response.data.data.list;

            if(data.code === "0000") {

                var list = data.data;

                for (var i = 0; i < list.length; i++) {

                    // 선택한 배달대행사 코드에 따른 하위 배달대행사 코드 셋팅
                    if (list[i].agencyCode === $scope.srchDlvrAgencyCombo.selectedValue) {

                        var list2 = list[i].subAgencies;

                        for (var j = 0; j < list2.length; j++) {

                            comboData = {};
                            comboData.name = list2[j].subAgencyName;
                            comboData.value = list2[j].subAgencyCode;
                            comboArray.push(comboData);

                        }
                    }
                }

                $scope._setComboData("srchSubAgency", comboArray);

                // 하위 배달대행사 유무에 따라 콤보박스 disabled 처리
                if (comboArray.length > 1) {
                    $scope.srchSubAgencyCombo.isDisabled = false;
                } else {
                    $scope.srchSubAgencyCombo.isDisabled = true;
                }

            } else {
                $scope._popMsg(data.message);
                $scope.srchSubAgencyCombo.isDisabled = true;
                return;
            }

        });
    };

    // 가맹점 조회
    $scope.seachStoreList = function () {

        var params = {};
        params.linkType = "004";
        params.posShopId = "omspos1";
        params.agencyCode = $scope.srchDlvrAgencyCombo.selectedValue;
        params.subAgencyCode = $scope.srchSubAgencyCombo.selectedValue;

        /*$scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/deleteAgencyLink.sb", params, function(response){

        });*/

    };

    // 선택
    $scope.btnSelect = function () {

        var params = {};
        params.linkType = "004";
        params.posShopId = "omspos1";
        params.agencyCode = $scope.srchDlvrAgencyCombo.selectedValue;
        params.subAgencyCode = $scope.srchSubAgencyCombo.selectedValue;


    };

    // 닫기
    $scope.btnClose = function () {
        $scope.wjDlvrAgencyRegLayer.hide();
    };

}]);