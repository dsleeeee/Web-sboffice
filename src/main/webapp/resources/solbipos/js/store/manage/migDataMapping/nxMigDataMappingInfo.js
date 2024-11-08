/****************************************************************
 *
 * 파일명 : nxMigDataMappingInfo.js
 * 설  명 : NXPOS1 데이터 이관 신규등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  OKPOS-KCP 매장 그리드 생성
 */
app.controller('nxMigDataMappingInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nxMigDataMappingInfoCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 상태
    };

    // <-- 검색 호출 -->
    $scope.$on("nxMigDataMappingInfoCtrl", function(event, data) {
        if(data === "clear") {
            $scope.$apply(function() {
                var dtlScope = agrid.getScope('nxMigDataMappingInfoCtrl');
                dtlScope._gridDataInit();
                $scope.srchNxHqOfficeCd = "";
                $scope.srchNxHqOfficeNm = "";
                $scope.srchNxStoreCd    = "";
                $scope.srchNxStoreNm    = "";
                // grid paging 초기화(숨기기.. 아예 없애는거 모름..)
                var vCtrlPager = document.getElementById('nxMigDataMappingInfoCtrlPager');
                vCtrlPager.style.visibility='hidden'
            });
            return false;
        }

        $scope.searchMigDataMappingInfo();

        event.preventDefault();
    });


    // NXPOS1 매장 조회
    $scope.searchMigDataMappingInfo = function(){
        var params = {};

        params.hqOfficeCd   = $scope.srchNxHqOfficeCd;
        params.hqOfficeNm   = $scope.srchNxHqOfficeNm;
        params.storeCd      = $scope.srchNxStoreCd;
        params.storeNm      = $scope.srchNxStoreNm;

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMappingInfo/getNxMigDataMappingInfoList.sb", params, function() {

            var vCtrlPager = document.getElementById('nxMigDataMappingInfoCtrlPager');
            vCtrlPager.style.visibility='visible'

        }, false);
    };
    // <-- //검색 호출 -->

    // 매장 신규 등록
    $scope.nxMigDataMappingInfoSave = function(){

        $scope._popConfirm(messages["migDataMappingInfo.saveConfirm"], function() {

            var startDt = new Date();
            startDt.setDate(startDt.getDate() - 93);  // 93일 전 날짜로 설정

            // 날짜 포맷팅: 'yyyyMMdd' 형식으로 변환
            var beforeDt = startDt.toISOString().slice(0, 10).replace(/-/g, '');

            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                // 파라미터 설정
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].fSaleDate = beforeDt;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/migDataMapping/migDataMappingInfo/getNxMigDataMappingInfoSave.sb", params, function(){
                $scope.close();
            });
        });
    };


    // 팝업 닫기
    $scope.close = function(){
        $scope.nxMigDataMappingInfoLayer.hide();

        $scope.srchNxHqOfficeCd = "";
        $scope.srchNxHqOfficeNm = "";
        $scope.srchNxStoreCd    = "";
        $scope.srchNxStoreNm    = "";

        var storeScope = agrid.getScope('nxMigDataMappingInfoCtrl');
        storeScope._gridDataInit();

        // grid paging 초기화(숨기기.. 아예 없애는거 모름..)
        var vCtrlPager = document.getElementById('nxMigDataMappingInfoCtrlPager');
        vCtrlPager.style.visibility='hidden'

        // 저장기능 수행후 재조회
        $scope._broadcast('nxMigDataMappingCtrl');
    };
}]);