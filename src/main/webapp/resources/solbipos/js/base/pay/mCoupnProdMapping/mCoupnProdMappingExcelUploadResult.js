/****************************************************************
 *
 * 파일명 : mCoupnProdMappingExcelUploadResult.js
 * 설  명 : 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2028.08.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 결과 구분
var resultGubunData = [
    {"name": "삭제", "value": "D"},
    {"name": "추가", "value": "I"},
    {"name": "유지", "value": "U"},
    {"name": "중복", "value": "X"}
];

/**
 *  모바일쿠폰상품매핑 엑셀업로드 결과 팝업 조회 그리드 생성
 */
app.controller('mCoupnProdMappingExcelUploadResultCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnProdMappingExcelUploadResultCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.resultGubunDataMap = new wijmo.grid.DataMap(resultGubunData, 'value', 'name'); // 결과 구분

        // 저장
        $("#funcSave").hide();

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "resultGubun") {
                    if(item.resultGubun === "D") {
                        wijmo.addClass(e.cell, 'red');
                    } else if(item.resultGubun === "I") {
                        wijmo.addClass(e.cell, 'blue');
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mCoupnProdMappingExcelUploadResultCtrl", function(event, data) {
        $scope.searchMCoupnProdMappingExcelUploadOverlap();
        event.preventDefault();
    });

    $scope.searchMCoupnProdMappingExcelUploadOverlap = function(){
        var params = {};
        // 조회구분 (1:중복, 2:삭제,추가,유지)
        params.searchGubun = "1";

        $scope._inquirySub("/base/pay/mCoupnProdMapping/mCoupnProdMappingExcelUploadResult/getMCoupnProdMappingExcelUploadResultList.sb", params, function() {
            if($scope.flex.collectionView.items.length == 0) {
                // 조회
                $scope.searchMCoupnProdMappingExcelUploadResult();

                // 저장
                $("#funcSave").show();
            } else {
                // 저장
                $("#funcSave").hide();
            }
        }, false);
    };

    $scope.searchMCoupnProdMappingExcelUploadResult = function(){
        var params = {};
        // 조회구분 (1:중복, 2:삭제,추가,유지)
        params.searchGubun = "2";

        $scope._inquiryMain("/base/pay/mCoupnProdMapping/mCoupnProdMappingExcelUploadResult/getMCoupnProdMappingExcelUploadResultList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $("#funcSave").click(function(e){
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ( $scope.flex.collectionView.items[i].prodNm !== null && $scope.flex.collectionView.items[i].prodNm !== ''
                && $scope.flex.collectionView.items[i].mcoupnCd !== null && $scope.flex.collectionView.items[i].mcoupnCd !== '' ) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            // 저장할 데이터가 없습니다.
            s_alert.pop(messages["mCoupnProdMappingExcelUploadResult.saveAlert"]);
            return;
        }

        // 저장하시겠습니까?
        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/base/pay/mCoupnProdMapping/mCoupnProdMappingExcelUploadResult/getMCoupnProdMappingExcelUploadResultSave.sb", params, function(){
                // 팝업 닫기
                $scope.close();

                // 재조회
                var scope = agrid.getScope("mCoupnProdMappingCtrl");
                scope.searchMCoupnProdMappingCnt("B");
            });

        });
    });

    // 닫기
    $("#funcClose").click(function(e){
        // 팝업 닫기
        $scope.close();
    });

    // 팝업 닫기
    $scope.close = function() {
        $scope.wjMCoupnProdMappingExcelUploadResultLayer.hide();
        event.preventDefault();
    };

}]);