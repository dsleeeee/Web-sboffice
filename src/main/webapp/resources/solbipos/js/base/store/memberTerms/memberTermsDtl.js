/****************************************************************
 *
 * 파일명 : memberTermsDtl.js
 * 설  명 : 회원약관관리 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.07.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 파일타입
var dtlFileTypeComboData = [
    {"name":"회원약관","value":"008"}
];

/**
 *  회원약관관리 상세 조회 그리드 생성
 */
app.controller('memberTermsDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberTermsDtlCtrl', $scope, $http, false));

    // 콤보박스 데이터
    $scope._setComboData("useYnCombo", useYnComboData);
    $scope._setComboData("fileTypeCombo", dtlFileTypeComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("memberTermsDtlCtrl", function(event, data) {
        $scope.searchMemberTermsDtl();
        event.preventDefault();
    });

    $scope.searchMemberTermsDtl = function() {
        var params = {};

        var scope = agrid.getScope('memberTermsCtrl');
        params = scope.getSelectVersion();

        $scope._postJSONQuery.withOutPopUp( "/base/store/media/verInfo/dtlInfo.sb", params, function(response){
            var data = response.data.data;
            $scope.version = data;

            if(orgnFg === "STORE" && $scope.version.verSerNo < 8000){
                $("#btnMod").hide();
            } else {
                $("#btnMod").show();
            }
        });
    };
    // <-- //검색 호출 -->

    // 탭변경
    $scope.changeTab = function(){
        $scope.wjMemberTermsDtlLayer.hide();

        $scope.wjMemberTermsStoreAddLayer.show(true, function(){
            // 탭 닫을때 그리드 초기화
            var sScope = agrid.getScope("addStoreCtrl");
            sScope._gridDataInit();
            var nScope = agrid.getScope("allStoreCtrl");
            nScope._gridDataInit();

            $("#srchHqOfficeCd").val("");
            $("#srchHqOfficeNm").val("");
            $("#srchStoreCd").val("");
            $("#srchStoreNm").val("");
        });
    };

    // 닫기
    $scope.close = function(){
        $scope.wjMemberTermsDtlLayer.hide();
    };
}]);