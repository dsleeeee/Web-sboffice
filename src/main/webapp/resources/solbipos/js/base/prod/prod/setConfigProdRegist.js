/****************************************************************
 *
 * 파일명 : setConfigProdRegist.js
 * 설  명 : 세트구성상품 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 세트구성상품 팝업 닫기
function closePop(){
    var scope = agrid.getScope("setConfigProdCtrl");
    scope.closePop();
}

/**
 * 구성내역 그리드 생성
 */
app.controller('setConfigProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('setConfigProdCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 단위

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("setConfigProdCtrl", function(event, data) {

        // 상품정보 Set
        $("#hdSetConfigProdCd").val(data.prodCd);
        $("#hdSetConfigSetProdFg").val(data.setProdFg);

        // 조회화면에서 들어오면, 구성상품내역도 조회만 가능하도록 setting
        if(data.viewType === "modify"){
            $("#divSetConfigProdBtn").css("display", "");
            $("#tblSrchProd").css("display", "");
        }else{
            $("#divSetConfigProdBtn").css("display", "none");
            $("#tblSrchProd").css("display", "none");
        }

        // 구성내역 리스트 조회
        $scope.getSetConfigProdList();
    });

    // 구성내역 리스트 조회
    $scope.getSetConfigProdList = function () {

        var params = {};
        params.prodCd = $("#hdSetConfigProdCd").val();

        $scope._inquirySub("/base/prod/prod/prod/getSetConfigProdList.sb", params, function() {

            // 상품 리스트 조회
            /*var scope = agrid.getScope('srchSetConfigProdCtrl');
            scope._pageView*/
            $scope._pageView('srchSetConfigProdCtrl', 1);

        }, false);
    };

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };
    
    // 구성내역 상품 삭제
    $scope.delProd = function () {

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].prodCd = $("#hdSetConfigProdCd").val();
                $scope.flex.collectionView.items[i].status = 'D';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/saveSetConfigProd.sb", params, function(){

            // 구성내역 리스트 재조회
            $scope.getSetConfigProdList();

            // 상품 리스트 재조회
            var scope = agrid.getScope('srchSetConfigProdCtrl');
            scope.getSrchSetConfigProdList();

        });

    };

    // 구성내역 상품 저장
    $scope.saveProd = function () {

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            // dispSeq 재설정
            var editItems = [];
            for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
                if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                    editItems.push($scope.flex.collectionView.items[s]);
                }
            }

            for (var s = 0; s < editItems.length; s++) {
                editItems[s].dispSeq = (s + 1);
                $scope.flex.collectionView.editItem(editItems[s]);
                editItems[s].status = "U";
                $scope.flex.collectionView.commitEdit();
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].prodCd = $("#hdSetConfigProdCd").val();
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            for (var m = 0; m < params.length; m++) {
                if(params[m].status !== 'D') {
                    if(params[m].unitProdQty === null || params[m].unitProdQty === '') {
                        $scope._popMsg("상품 수량을 한 개 이상 입력해주세요.");
                        return false;
                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/prod/prod/saveSetConfigProd.sb', params, function() {

                // 구성내역 리스트 재조회
                $scope.getSetConfigProdList();

                // 상품 리스트 재조회
                var scope = agrid.getScope('srchSetConfigProdCtrl');
                scope.getSrchSetConfigProdList();

            });
        });
    };

    // 세트구성상품 팝업 닫기
    $scope.closePop = function () {

        // 세트구성상품 팝업 닫은 후, 호출 (뒤에 있는 상품정보등록 화면을 자동으로 닫히게 하기 위해)
        var scope = agrid.getScope("prodModifyCtrl");
        scope.closeView();
    };

}]);

/**
 * 상품 그리드 생성
 */
app.controller('srchSetConfigProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('srchSetConfigProdCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 단위
    };

    $scope.$on("srchSetConfigProdCtrl", function(event, data) {

        // 상품 리스트 조회
        $scope.getSrchSetConfigProdList();
    });

    // 상품 리스트 조회
    $scope.getSrchSetConfigProdList = function () {

        var params = {};
        params.prodCd = $("#hdSetConfigProdCd").val();
        params.prodNm = $scope.prodNm;
        params.storeProdUseFg = storeProdUseFg; // 매장상품제한구분 여부 (0: 미사용 / 1: 사용)

        $scope._inquirySub("/base/prod/prod/prod/getSrchSetConfigProdList.sb", params, function() {}, false);
    };
    
    // 상품 등록
    $scope.regProd = function () {

        $scope.flex.collectionView.commitEdit();

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].prodCd = $("#hdSetConfigProdCd").val();
                $scope.flex.collectionView.items[i].setProdFg = $("#hdSetConfigSetProdFg").val();
                $scope.flex.collectionView.items[i].unitProdQty = "1";
                $scope.flex.collectionView.items[i].status = 'I';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/saveSetConfigProd.sb", params, function(){

            // 상품리스트 재조회
            $scope.getSrchSetConfigProdList();

            // 구성내역 리스트 재조회
            var scope = agrid.getScope('setConfigProdCtrl');
            scope.getSetConfigProdList();

        });
    }


}]);