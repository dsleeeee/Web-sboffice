/****************************************************************
 *
 * 파일명 : mainVerRegist.js
 * 설  명 : 메인버전 등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.29     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 구분
var verGubunComboData = [
    {"name": "전체", "value": ""},
    {"name": "NXPOS", "value": "1"},
    {"name": "LYNKPOS", "value": "2"}
];

// 시스템타입
var systemTypeFgData = [
    {"name": "공통", "value": "0"},
    {"name": "32bit", "value": "1"},
    {"name": "64bit", "value": "2"}
];

/**
 *  메인버전 등록 팝업 조회 그리드 생성
 */
app.controller('mainVerRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mainVerRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("verGubunCombo", verGubunComboData); // 구분
    $scope._setComboData("progDetailFgCombo", progDetailFgData); // 프로그램상세구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.progFgDataMap = new wijmo.grid.DataMap(progFgData, 'value', 'name'); // 프로그램구분
        $scope.progDetailFgDataMap = new wijmo.grid.DataMap(progDetailFgData, 'value', 'name'); // 프로그램상세구분
        $scope.systemTypeFgDataMap = new wijmo.grid.DataMap(systemTypeFgData, 'value', 'name'); // 시스템타입
        $scope.pgmYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // PGM 포함 여부
        $scope.dbYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // DB 포함 여부
        $scope.imgYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // IMG 포함 여부
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("mainVerRegistCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedMainVerRegist(data);
            $("#lblHqOfficeCd").text(" [" + data.hqOfficeCd + " " + data.hqOfficeNm + "]");
        }
        $scope.searchMainVerRegist();
        event.preventDefault();
    });

    $scope.searchMainVerRegist = function(){
        var params = {};
        params.verGubun = $scope.verGubun;
        params.verSerNo = $scope.verSerNo;
        params.verSerNm = $scope.verSerNm;
        params.progDetailFg = $scope.progDetailFg;
        params.fileDesc = $scope.fileDesc;

        $scope._inquiryMain("/pos/confg/mainVerManage/mainVerRegist/getMainVerRegistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedMainVerRegist;
    $scope.setSelectedMainVerRegist = function(store) {
        $scope.selectedMainVerRegist = store;
    };
    $scope.getSelectedMainVerRegist = function(){
        return $scope.selectedMainVerRegist;
    };

    // 저장
    $("#funcRegist").click(function(e){
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].hqOfficeCd = $scope.selectedMainVerRegist.hqOfficeCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        if(params.length > 1) {
            s_alert.pop(messages["mainVerRegist.registChkAlert"]); // 1개만 선택해주세요.
            return;
        }

        // 등록하시겠습니까?
        var msg = $("#lblHqOfficeCd").text() + " " + messages["mainVerRegist.registConfirm"];
        $scope._popConfirm(msg, function() {

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/pos/confg/mainVerManage/mainVerRegist/getMainVerRegistSave.sb", params, function(){
                // 팝업 닫기
                $scope.close();
            });

        });
    });

    // 팝업 닫기
    $scope.close = function() {
        var scope = agrid.getScope("mainVerManageCtrl");
        scope.searchMainVerManage();

        $scope.setSelectedMainVerRegist(null);

        $("#lblHqOfficeCd").text("");
        $scope.srchVerGubunCombo.selectedIndex = 0;
        $scope.verSerNo = "";
        $scope.verSerNm = "";
        $scope.srchProgDetailFgCombo.selectedIndex = 0;
        $scope.fileDesc = "";

        $scope.wjMainVerRegistLayer.hide();
        event.preventDefault();
    };

}]);