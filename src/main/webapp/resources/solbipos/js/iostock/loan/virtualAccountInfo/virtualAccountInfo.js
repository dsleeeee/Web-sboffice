/****************************************************************
 *
 * 파일명 : virtualAccountInfo.js
 * 설  명 : 가상계좌-기초정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  가상계좌-기초정보등록 그리드 생성
 */
app.controller('virtualAccountInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualAccountInfoCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        if(orgnFg == "HQ") {
            $scope.searchVirtualAccountInfo();
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("virtualAccountInfoCtrl", function(event, data) {
        $scope.searchVirtualAccountInfo();
        event.preventDefault();
    });

    $scope.searchVirtualAccountInfo = function(){
        // 파라미터
        var params = {};
        if(orgnFg == "HQ") {
            params.hqOfficeCd = hqOfficeCd;
        }
        params.gubun = "Y";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/iostock/loan/virtualAccountInfo/virtualAccountInfo/getVirtualAccountInfoList.sb", params, function (){});
    };
    // <-- //검색 호출 -->

    // 본사코드 조회 팝업
    $scope.searchHq = function() {
        var popup = $scope.hqLayer;
        // 팝업 닫을때
        popup.show(true, function (s) {
            var hqScope = agrid.getScope('searchHqCtrl');
            hqScope.$apply(function(){
                hqScope._gridDataInit();
                if( !$.isEmptyObject(hqScope.getHq())  ) {
                    // 본사정보 셋팅
                    $("#virtualAccountInfoHqOfficeNm").val(hqScope.getHq().hqOfficeNm);
                    $("#virtualAccountInfoHqOfficeCd").val(hqScope.getHq().hqOfficeCd);
                }
            });

            // 본사 정보 초기화(이전데이터 남아있는 현상 발생)
            hqScope.setHq("");
        });
        event.preventDefault();
    };

    // 본사코드 선택취소
    $scope.delHq = function() {
        $("#virtualAccountInfoHqOfficeNm").val("");
        $("#virtualAccountInfoHqOfficeCd").val("");
    };

    // 추가
    $scope.add = function() {
        // 파라미터
        var params = {};
        params.hqOfficeCd = $("#virtualAccountInfoHqOfficeCd").val();

        if(params.hqOfficeCd == "" || params.hqOfficeCd == null) {
            $scope._popMsg(messages["cmm.require.selectHq"]); // 본사를 선택해 주세요.
            return;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/iostock/loan/virtualAccountInfo/virtualAccountInfo/getVirtualAccountInfoList.sb", params, function (){});
    };

    // 저장
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        if(orgnFg == "MASTER") {
            if ($scope.flex.rows.length > 1) {
                $scope._popMsg(messages["virtualAccountInfo.rowCountAlert"]); // 추가 버튼 클릭 후 저장해주세요.
                return false;
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].siteCd == "" || $scope.flex.collectionView.items[i].siteCd == null) {
                $scope._popMsg(messages["virtualAccountInfo.siteCdBlankAlert"]); // NHN KCP 발급 사이트코드를 입력해주세요.
                return false;
            }
            if($scope.flex.collectionView.items[i].kcpCertInfo == "" || $scope.flex.collectionView.items[i].kcpCertInfo == null) {
                $scope._popMsg(messages["virtualAccountInfo.kcpCertInfoBlankAlert"]); // KCP PG-API 인증서정보를 입력해주세요.
                return false;
            }
            params.push($scope.flex.collectionView.items[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/iostock/loan/virtualAccountInfo/virtualAccountInfo/getVirtualAccountInfoSave.sb", params, function(){});
    };

}]);