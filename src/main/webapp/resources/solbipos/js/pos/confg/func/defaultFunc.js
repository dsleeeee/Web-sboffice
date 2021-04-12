/****************************************************************
 *
 * 파일명 : defaultFunc.js
 * 설  명 : 기본기능적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.01     이다솜      1.0
 *
 * **************************************************************/

app.controller('defaultFuncCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('defaultFuncCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("dfHqOfficeCd", hqList);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if( col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if( col.binding === "storeCd") {

                    $("#divInfo").css("display", "");

                    // 선택한 매장의 정보 셋팅
                    $("#lblStoreInfo").text("적용대상매장 : [" + selectedRow.storeCd + "] " + selectedRow.storeNm);
                    $("#hdStoreCd").val( selectedRow.storeCd);
                    $("#hdStoreNm").val( selectedRow.storeNm);
                    
                    // 초기화 버튼 보이게
                    $("#btnReset").css("display", "");
                }
            }
        });
    };

    // 팝업 오픈시 테이블 그룹정보 조회
    $scope.$on("defaultFuncCtrl", function(event, data) {
        $scope.selectStoreList();
        event.preventDefault();
    });
    
    // 매장코드 조회
    $scope.selectStoreList = function () {

        /*if($scope.hqOfficeCd == ''){
            s_alert.pop( messages["func.require.select.hq"] );
            return;
        }*/

        // 파라미터
        var params = {};
        if(orgnFg != 'HQ') {
            params.hqOfficeCd = $scope.hqOfficeCd;
        } else if(orgnFg == 'HQ') {
            params.hqOfficeCd = hqOfficeCd;
        }
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.orgnFg = orgnFg;
        params.agencyCd = orgnCd;

        $scope._inquirySub("/pos/confg/func/func/selectStoreList.sb", params, function () {
            $("#divInfo").css("display", "");
            $("#lblStoreInfo").text("");
            $("#hdStoreCd").val("");
            $("#hdStoreNm").val("");
            $("#btnReset").css("display", "none");
        });
    };

    // 기능키 초기화 버튼 클릭
    $scope.defaultFuncReset = function(){

        var msg = "["+ $("#hdStoreCd").val() + "] " + $("#hdStoreNm").val() + " 매장의 기능 정보를 기본값으로 설정하시겠습니까?\r(기존 기능 정보는 모두 삭제됩니다.)" ;
        s_alert.popConf(msg, function(){

            var param = {};
            param.storeCd = $("#hdStoreCd").val();

            $scope._postJSONSave.withPopUp( "/pos/confg/func/func/saveDefaultFunc.sb", param, function(response){
                //$scope.taxBillDepositLayer.hide();
            });
        });
    };


    // 닫기버튼 클릭
    $scope.close = function(){

        // 입력 값 초기화
        $scope.hqOfficeCd = "";
        $scope.storeCd = "";
        $scope.storeNm = "";
        $("#divInfo").css("display", "none");
        $("#lblStoreInfo").text("");
        $("#hdStoreCd").val("");
        $("#hdStoreNm").val("");
        $("#btnReset").css("display", "none");

        $scope.defaultFuncLayer.hide();
    }

}]);