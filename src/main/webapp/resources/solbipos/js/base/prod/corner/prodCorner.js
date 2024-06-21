/****************************************************************
 *
 * 파일명 : prodCorner.js
 * 설  명 : 상품별 코너변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.27     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 상품정보관리 그리드 생성
 */
app.controller('prodCornerCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodCornerCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);
    $scope._setComboData('srchCornrCd', cornerList);
    $scope._setComboData('cornrCd', cornerList2);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        if(cornerList2.length <= 0){
            $scope._popMsg(messages["prodCorner.notExists.cornr"]); // 이동할 코너가 없습니다.

        }

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.header === messages["prodCorner.prodClassLNm"]) {
                    e.cell.innerHTML = $scope.getClass(e.cell.innerText, 1);
                }else if (col.header === messages["prodCorner.prodClassMNm"]) {
                    e.cell.innerHTML = $scope.getClass(e.cell.innerText, 2);
                }else if (col.header === messages["prodCorner.prodClassSNm"]) {
                    e.cell.innerHTML = $scope.getClass(e.cell.innerText, 3);
                }
            }
        });

    };

    // 상품 분류 나누기
    $scope.getClass = function(str, depth){

        var strCnt = (str.match(/>/g) || []).length;

        if(strCnt + 1 >= depth){
            return str.split('>')[depth - 1];
        }else{
            return "";
        }
    }

    // 상품정보관리 그리드 조회
    $scope.$on("prodCornerCtrl", function(event, data) {

        $scope.searchProdList();
        event.preventDefault();
    });

    // 상품 목록 조회
    $scope.searchProdList = function(){

        // 파라미터
        var params = {};
        params.srchCornrCd = $scope.srchCornrCd;
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/prod/corner/prodCorner/list.sb", params, function(){
        });
    };

    // 코너 이동
    $scope.changeProdCorner = function(){

        if(cornerList2.length <= 0){
            $scope._popMsg(messages["prodCorner.notExists.cornr"]); // 이동할 코너가 없습니다.
            return false;
        }

        if($scope.srchCornrCd === $scope.cornrCd) {
            /*조회한 코너가 이동할 코너가 같습니다. 이동할 코너를 다시 선택하세요.*/
            $scope._popMsg(messages["prodCorner.change.chkValid"]);
            return;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].cornrCd = $scope.cornrCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/corner/prodCorner/changeProdCorner.sb", params, function(){ $scope.searchProdList() });

    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 엑셀 다운로드
    $scope.excelDownloadTotal = function () {
        $scope._popConfirm(messages["statusStore.totalExceDownload"], function() {
            $scope._broadcast('totalExcelCtrl');
        });
    };

}]);

app.controller('totalExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('totalExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) { };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("totalExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchExcelList = function (data) {
        // 파라미터
        var params       = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/corner/prodCorner/excelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }
            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "상품별 코너변경" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
}]);