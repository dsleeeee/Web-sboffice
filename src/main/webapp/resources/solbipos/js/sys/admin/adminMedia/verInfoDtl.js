/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : (관리자) 듀얼모니터 영상관리 탭 -  상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var langFgDtl = [
    {"name":"","value":null},
    {"name":"국문","value":"0"},
    {"name":"영문","value":"1"},
    {"name":"중문","value":"2"},
    {"name":"일문","value":"3"}
];

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('verDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('verDetailCtrl', $scope, $http, true));

    // 콤보박스 데이터
    $scope._setComboData("useYnCombo", useYn);
    $scope._setComboData("fileTypeCombo", fileType);
    $scope._setComboData("langFgCombo", langFgDtl);

    // 버전정보
    $scope.version;

    // 조회 버튼 클릭
    $scope.$on("verDetailCtrl", function(event, data) {
        $scope.getVersionInfo();
        event.preventDefault();
    });

    // 버전 목록 조회
    $scope.getVersionInfo = function(){
        var params = {};

        var scope = agrid.getScope('adminMediaCtrl');
        params    = scope.getSelectVersion();

        $scope._postJSONQuery.withOutPopUp( "/sys/admin/adminMedia/verInfo/dtlInfo.sb", params, function(response){
            var data = response.data.data;
            $scope.version = data;

            // 파일사이즈 변환하여 표기
            $scope.version.fileSize = getfileSize($scope.version.fileSize);

        });
    };

    // 수정
    $scope.modify = function(){
        $scope.versionRegistLayer.show(true, function(){
            var scope = agrid.getScope('verRegistCtrl');
            console.log('scope.version', scope.version);
            scope.version = null;

            $scope.getVersionInfo();
        });
    };

    // 닫기
    $scope.close = function(){
        $scope.versionInfoDetailLayer.hide();
    };

    // 탭변경
    $scope.changeTab = function(tabFg){
        $scope.versionInfoDetailLayer.hide();

        if(tabFg === 'S') {
            $scope.storeAddLayer.show(true, function () {
                // 탭 닫을때 그리드 초기화
                var sScope = agrid.getScope("addStoreCtrl");
                sScope._gridDataInit();
                var nScope = agrid.getScope("allStoreCtrl");
                nScope._gridDataInit();

                $("#adminHqOfficeCd").val("");
                $("#adminHqOfficeNm").val("");
                $("#adminStoreCd").val("");
                $("#adminStoreNm").val("");
            });
        }else{
            $scope.versionAddLayer.show(true, function () {
                // 탭 닫을때 그리드 초기화
                var sScope = agrid.getScope("regVersionCtrl");
                sScope._gridDataInit();
                var nScope = agrid.getScope("allVersionCtrl");
                nScope._gridDataInit();

                $("#adminVerSerNo").val("");
                $("#adminVerSerNm").val("");
                sScope.srchProgFgCombo.selectedIndex = 0;
                sScope.srchProgDetailFgCombo.selectedIndex = 0;

            });
        }
    };

}]);

