/****************************************************************
 *
 * 파일명 : kioskKeyMapEnv.js
 * 설  명 : 키오스크 키맵그룹코드(매장/포장) 적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgMapEnvComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

app.controller('kioskKeyMapEnvCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapEnvCtrl', $scope, $http, false));

    $scope._setComboData("srchEnvSysStatFg", sysStatFg);
    $scope._setComboData("envTuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록
    $scope._setComboData("tuMClsFgMapEnv", tuMClsFgMapEnvComboData); // KIOSK중분류사용

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgMapEnvComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskKeyMapEnvCtrl", function(event, key) {

        // 매장적용(매장/포장) 키값 hidden에 갖고 있기
        $("#hdEnvstCd").val(key);

        // 제목셋팅
        if(key === "4068") {
            $("#popTitle").text(messages["kioskKeymap.envConfgStore"]);
        }else{
            $("#popTitle").text(messages["kioskKeymap.envConfgPack"]);
        }
        
        // 키맵매장적용 체크박스 기본 체크로 설정
        $("input:checkbox[id='chkTuClsTypeStore']").prop("checked", true);

        // 매장 키오스크 포스 조회
        $scope.searchPos();

        // 환경설정 값 조회
        $scope.getEnv();

        event.preventDefault();

    });

    // 매장 키오스크 포스 조회
    $scope.searchPos = function(){

        var params = {};
        params.storeCd = $("#srchEnvStoreCd").val();
        params.storeNm = $("#srchEnvStoreNm").val();
        params.sysStatFg = $scope.srchEnvSysStatFgCombo.selectedValue;
        params.tuMClsFg = $scope.tuMClsFgMapEnvCombo.selectedValue;

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreKioskPosList.sb", params, function () {

            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;
            var rows = grid.rows;

            // 환경설정 코드에 따라 보이는 컬럼이 다름
            if($("#hdEnvstCd").val() === "4068") {
                columns[6].visible = true;
                columns[7].visible = false;
            }else{
                columns[6].visible = false;
                columns[7].visible = true;
            }

            // 키오스크포스가 없는 매장은 선택 불가
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.posNo === null || item.posNo === undefined || item.posNo === ""){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }

        });
    };

    // 환경설정 값 조회
    $scope.getEnv = function(){

        var params = {};
        params.envstCd = $("#hdEnvstCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskEnv.sb", params,
            function(response){
                if(response.data.data !== null && response.data.data !== ""){

                    // 본사에서 사용중인 키맵으로 셋팅
                    $scope.envTuClsTypeCombo.selectedValue = response.data.data;

                }else{
                    $scope.envTuClsTypeCombo.selectedIndex = 0;
                }
            }
        );
    };

    // 조회
    $scope.btnSearchPos = function(){

        // 매장 키오스크 포스 조회
        $scope.searchPos();
    };

    // 적용
    $scope.btnInsertEnv = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        // 키맵그룹이 없습니다.
        if ($scope.envTuClsTypeCombo.selectedValue === "" || $scope.envTuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        // '01' 키맵그룹을 매장포스환경에 적용하시겠습니까?
        // <br/> (중분류 사용 키맵그룹은 중분류 사용 키오스크에만 적용됩니다.)
        $scope._popConfirm("'" + $scope.envTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreKioskPosEnv.msg"] + messages["kioskKeyMap.kioskTuMClsFg.msg"], function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true) {
                    if(item.posNo !== null && item.posNo !== "") {

                        if($("#hdTuMClsFgMapEnv").val() === item.tuMClsFg) {

                            var obj = {};
                            obj.storeCd = item.storeCd;
                            obj.posNo = item.posNo;
                            obj.envstCd = $("#hdEnvstCd").val();
                            obj.envstVal = $scope.envTuClsTypeCombo.selectedValue;
                            obj.tuClsType = $scope.envTuClsTypeCombo.selectedValue;
                            obj.tuMClsFg = $("#hdTuMClsFgStoreRegist").val(); // 매장 적용시에만 UPDATE / 매장 해당컬럼 사용안함 / 수정 기록 확인용 / 매장은 [1243 KIOSK중분류사용] 사용

                            // 키맵매장적용 여부
                            if($("#chkTuClsTypeStore").is(":checked")){
                                obj.chkTuClsTypeStore = "Y";
                            }else{
                                obj.chkTuClsTypeStore = "N";
                            }

                            params.push(obj);
                        }
                    }
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveHqStoreKioskPosEnv.sb", params, function () {

                $scope.kioskKeyMapEnvLayer.hide(true);

            });
        });

    };

    // 닫기
    $scope.close = function () {

        $("#srchEnvStoreCd").val("");
        $("#srchEnvStoreNm").val("");
        $scope.srchEnvSysStatFgCombo.selectedIndex = 0;
        $scope.envTuClsTypeCombo.selectedIndex = 0;
    };

    // 적용할 키맵그룹 선택 이벤트
    $scope.setEnvTuClsTypeCombo = function (s){
        var params = {};
        params.tuClsType = s.selectedValue;

        // 키맵그룹에 KIOSK중분류사용 조회
        $scope.kioskKeyMapGroupTuMClsFg(params);
    };

    // 키맵그룹에 KIOSK중분류사용 조회
    $scope.kioskKeyMapGroupTuMClsFg = function(params){
        $scope._postJSONQuery.withOutPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMapGroupTuMClsFg.sb", params, function(response) {
            var list = response.data.data.list;
            if(list.length > 0) {
                var tuMClsFgNm;
                if(list[0].tuMClsFg == 0) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 미사용)";
                } else if(list[0].tuMClsFg == 2) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 중분류사용)";
                }
                $("#lblTuMClsFgMapEnv").text(tuMClsFgNm);
                $("#hdTuMClsFgMapEnv").val(list[0].tuMClsFg);
            }
        }, false);
    };

}]);