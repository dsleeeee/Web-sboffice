/****************************************************************
 *
 * 파일명 : mobileProdSoldOut.js
 * 설  명 : (모바일) 상품관리 > 품절관리(상품)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.03     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 품절여부
var soldOutYnAllData = [
    {"name": "전체", "value": ""},
    {"name": "품절", "value": "Y"},
    {"name": "정상", "value": "N"}
];
var soldOutYnData = [
    {"name": "품절", "value": "Y"},
    {"name": "정상", "value": "N"}
];

var poUnitFgComboData = [
    {"name": "낱개", "value": "1"},
    {"name": "박스", "value": "2"}
]

/**
 *  그리드 생성
 */
app.controller('mobileProdSoldOutCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileProdSoldOutCtrl', $scope, $http, false));

    // 입력구분 콤보박스
    $scope._setComboData("useYn", useYnAllComboData);
    $scope._setComboData("soldOutYn", soldOutYnAllData);
    $scope._setComboData("soldOutYnChg", soldOutYnData);

    // 등록 일자 전체기간 체크박스
    $scope.isChecked = true;

    // 일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope.isChkDt = function () {      // 등록 일자 전체기간 체크박스 클릭이벤트
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
        // 품절여부
        $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name');
        // 주문단위 콤보박스와 data-map
        $scope.poUnitFgComboDataMap = new wijmo.grid.DataMap(poUnitFgComboData, 'value', 'name');
        // 사용여부
        $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');

        // 품절여부 변경 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "soldOutYn") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileProdSoldOutCtrl", function(event, data) {
        $scope.searchMobileProdSoldOut(data);
        event.preventDefault();
    });

    $scope.searchMobileProdSoldOut = function(data){
        if($("#mobileProdSoldOutStoreCd").val() === null || $("#mobileProdSoldOutStoreCd").val() === "" || $("#mobileProdSoldOutStoreCd").val() === undefined){
            $scope._popMsg(messages["mobile.cmm.not.store"]);
            return false;
        }

        var params = {};
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

            // 시작일자가 종료일자보다 빠른지 확인
            if(params.startDate > params.endDate){
                $scope._popMsg(messages['mobile.cmm.dateChk.error']);
                return false;
            }
        }
        params.storeCd  = $("#mobileProdSoldOutStoreCd").val();
        params.prodCd       = $scope.prodCd;
        params.prodNm       = $scope.prodNm;
        params.prodClassCd  = $scope.prodClassCd;
        params.barCd        = $scope.barCd;
        params.useYn        = $scope.useYn;
        params.soldOutYn    = $scope.soldOutYn;
        params.hqBrandNm    = $scope.hqBrandNm;

        $scope._inquirySub("/mobile/prod/prodSoldOut/mobileProdSoldOut/getMobileProdSoldOutList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileProdSoldOut", $scope.flex, "N");
        }, true);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileProdSoldOutStoreShow = function () {
        $scope._broadcast('mobileProdSoldOutStoreCtrl');
    };

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["mobile.cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["mobile.cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].soldOutYn = $scope.soldOutYnChg;
            }
        }
        $scope.flex.refresh();
    };


    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "U";
                    $scope.flex.collectionView.items[i].storeCd = $("#mobileProdSoldOutStoreCd").val();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/mobile/prod/prodSoldOut/mobileProdSoldOut/getMobileProdSoldOutSave.sb", params, function(){
                $scope.searchMobileProdSoldOut();
            });
        });
    };

    // 다중매장 선택팝업
    $scope.mobileProdSoldOutStoreShow = function () {
        $scope._broadcast('mobileProdSoldOutStoreCtrl');
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

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };
}]);