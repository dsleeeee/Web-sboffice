/****************************************************************
 *
 * 파일명 : copyStoreTouchKey.js
 * 설  명 : 매장 판매터치키복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.29     이다솜      1.0
 *
 * **************************************************************/

app.controller('copyStoreTouchKeyCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyStoreTouchKeyCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 기준매장 매장코드 클릭
                if ( col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var scope = agrid.getScope('copyStoreTouchKey2Ctrl');

                    // 선택한 기준매장 정보 및 터치키 그룹 셋팅
                    scope.setOrgStoreInfo(selectedRow);

                    // 적용대상매장 조회
                    scope.searchTargetStoreList();

                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("copyStoreTouchKeyCtrl", function (event, data) {

        // 기준매장 조회
        $scope.searchOrgStoreList();
        event.preventDefault();
    });
    
    // 기준매장 조회
    $scope.searchOrgStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd1").val();
        params.storeNm = $("#srchStoreNm1").val();
        params.originalStoreCd = "";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/view/view/getStoreList.sb", params);
    };

    // 닫기
    $scope.closePop = function () {

        // 초기화
        $("#srchStoreCd1").val("");
        $("#srchStoreNm1").val("");
        $("#srchStoreCd2").val("");
        $("#srchStoreNm2").val("");
        $("#selStore").text("");
        $("#hdOrgStorecd").val("");
        $("#selTouchKeyGrp").css("display", "none");

        var scope = agrid.getScope('copyStoreTouchKey2Ctrl');
        scope._gridDataInit();

    };

}]);

app.controller('copyStoreTouchKey2Ctrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyStoreTouchKey2Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("copyStoreTouchKey2Ctrl", function (event, data) {
        // 적용대상매장 조회
        $scope.searchTargetStoreList();
        event.preventDefault();
    });

    // 적용대상매장 조회
    $scope.searchTargetStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd2").val();
        params.storeNm = $("#srchStoreNm2").val();
        params.originalStoreCd = $("#hdOrgStorecd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/view/view/getStoreList.sb", params);
    };

    // 선택한 기준매장 정보 및 터치키 그룹 셋팅
    $scope.setOrgStoreInfo = function(data){

        // 선택한 기준매장 셋팅
        $("#selStore").text("[" + data.storeCd + "] " + data.storeNm);
        $("#hdOrgStorecd").val(data.storeCd);

        // 선택한 기준매장의 터치키그룹 셋팅
        var comboParams = {};
        comboParams.storeCd = data.storeCd;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : "/base/store/view/view/getStoreTouchKeyGrpCombo.sb", /* 통신할 URL */
            params : comboParams, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    if(list.length > 0) {
                        comboData.name = messages["cmm.all"];
                        comboData.value = "";
                        comboArray.push(comboData);

                        // 계정 콤보박스 셋팅
                        for (var i = 0; i < list.length; i++) {
                            comboData = {};
                            comboData.name = list[i].name;
                            comboData.value = list[i].value;
                            comboArray.push(comboData);
                        }

                    }else{
                        comboData.name = "-";
                        comboData.value = "-";
                        comboArray.push(comboData);
                    }

                    $scope._setComboData("tuKeyGrpCombo", comboArray);

                    // 터치키 그룹 콤보박스 보여주기
                    $("#selTouchKeyGrp").css("display", "");
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };

    // 복사
    $scope.saveCopyStoreTouchKey = function () {
        
        if($("#hdOrgStorecd").val() === ""){
            $scope._popMsg(messages["storeView.original.storeCd"] + messages["cmm.require.select"]); // 기준매장을 선택해주세요.
            return false;
        }

        if($scope.tuKeyGrpCombo.selectedValue === "-"){
            $scope._popMsg(messages["storeView.no.touchKey.grp.msg"] ); // 기준매장에 등록된 터치키그룹이 없어, 복사가 불가합니다.
            return false;
        }

        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["storeView.target.storeCd"] + messages["cmm.require.select"]); // 적용대상매장을(를) 선택하세요.
            return false;
        }

        // 선택한 터치키그룹을 복사하시겠습니까?(적용대상매장의 기존 터치키그룹 정보는 모두 삭제됩니다.)
        $scope._popConfirm(messages["storeView.copy.touchKey.grp.msg1"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {

                    $scope.flex.collectionView.items[i].originalStoreCd = $("#hdOrgStorecd").val();
                    $scope.flex.collectionView.items[i].targetStoreCd = $scope.flex.collectionView.items[i].storeCd;
                    $scope.flex.collectionView.items[i].tukeyGrpCd = $scope.tuKeyGrpCombo.selectedValue;

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/view/view/copyStoreTouchKeyGrp.sb', params, function() {

                // 초기화
                $("#srchStoreCd1").val("");
                $("#srchStoreNm1").val("");
                $("#srchStoreCd2").val("");
                $("#srchStoreNm2").val("");
                $("#selStore").text("");
                $("#hdOrgStorecd").val("");
                $("#selTouchKeyGrp").css("display", "none");

                $scope._gridDataInit();

                $scope.copyStoreTouchKeyLayer.hide();
            });
        });

    };

}]);