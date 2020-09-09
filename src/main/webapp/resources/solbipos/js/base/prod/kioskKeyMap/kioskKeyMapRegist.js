/****************************************************************
 *
 * 파일명 : kioskKeyMapRegist.js
 * 설  명 : 키오스크키맵등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var regYn = [
    {"name": "전체", "value": ""},
    {"name": "등록", "value": "Y"},
    {"name": "미등록", "value": "N"}
];

var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 키오스크 카테고리(분류)
app.controller('kioskKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapRegistCtrl', $scope, $http, true));

    // 키오스크용 포스 목록
    $scope._setComboData("posNoCombo", kioskPosList);

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'tuClsCd') {
                    if(item.tuClsCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 카테고리 코드 클릭 시, 키/상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "tuClsCd") {
                    if(selectedRow.tuClsCd !== '자동채번') {
                        $scope._broadcast('kioskKeyCtrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("kioskKeyMapRegistCtrl", function(event, data) {

    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){

        // POS 번호 hidden에 갖고있기( 조회 시 사용)
        $("#hdPosNo").val($scope.posNo);

        var params = {};
        params.posNo = $scope.posNo;

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb", params, function() {}, false);
    }

    // 카테고리(분류) 상위 순서 이동
    $scope.rowMoveUpCls = function () {
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
    }

    // 카테고리(분류) 하위 순서 이동
    $scope.rowMoveDownCls = function () {
        var movedRows = $scope.flex.itemsSource.itemCount - 1;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (i < ($scope.flex.itemsSource.itemCount - 1) && item.gChk) {
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
    }

    // 카테고리(분류) 빈칸 추가
    $scope.blankRowCls = function () {

        var params = {};

        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = 'KIOSK_BLANK_CLS_NM_X';

        // 행추가
        $scope._addRow(params, 2);
    }

    // 카테고리(분류) 추가
    $scope.addRowCls = function () {

        var params = {};

        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = '';
        
        // 행추가
        $scope._addRow(params, 2);
    }

    // 카테고리(분류) 삭제
    $scope.delRowCls = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    }

    // 카테고리(분류) 저장
    $scope.saveCls = function () {

        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            $scope.flex.collectionView.itemsRemoved[d].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsRemoved[d].status = 'D';
            params.push($scope.flex.collectionView.itemsRemoved[d]);
        }

        // indexNo 재설정
        var editItems = [];
        for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                editItems.push($scope.flex.collectionView.items[s]);
            }
        }

        for (var s = 0; s < editItems.length; s++) {
            editItems[s].indexNo = (s + 1);
            $scope.flex.collectionView.editItem(editItems[s]);
            editItems[s].status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 생성, 수정 Validation Check
        for (var m = 0; m < params.length; m++) {
            if(params[m].status !== 'D') {
                if(  params[m].tuClsNm === null  || params[m].tuClsNm === '') {
                    $scope._popMsg(messages['kioskKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return false;
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategory.sb', params, function() {

            // 재조회
            $scope.btnSearchCls();
        });
    }

}]);

// 키오스크 키
app.controller('kioskKeyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("kioskKeyCtrl", function(event, data) {

        if(data !== undefined && !isEmptyObject(data)) {

            // 카테고리 관련 셋팅
            $("#spanTuKeyCls").text("[" + data.tuClsCd + "] " + data.tuClsNm)
            $("#hdTuClsCd").val(data.tuClsCd);
        }
        
        // 키 조회
        $scope.searchKey();

    });

    // 키 조회
    $scope.searchKey = function () {

        var params = {};
        params.posNo = $("#hdPosNo").val();
        params.tuClsCd = $("#hdTuClsCd").val();

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKey.sb", params, function() {

            // 미등록상품 조회
            var kioskProdGrid = agrid.getScope("kioskProdCtrl");
            kioskProdGrid._pageView('kioskProdCtrl', 1);
            
        }, false);
    }

    // 키 상위 순서 이동
    $scope.rowMoveUpKey = function () {

    }

    // 키 하위 순서 이동
    $scope.rowMoveDownKey = function () {

    }

    // 키 삭제
    $scope.delRowKey = function () {

    }

    // 키 저장
    $scope.saveKey = function () {

    }

}]);

// 키오스크 미등록상품
app.controller('kioskProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskProdCtrl', $scope, $http, true));

    // ComboBox 셋팅
    $scope._setComboData('useYnAllComboData', useYnAllComboData);
    $scope._setComboData('prodTypeFg', prodTypeFg);
    $scope._setComboData('regYn', regYn);
    
    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;

        $scope.regYnDataMap = new wijmo.grid.DataMap(regYn, 'value', 'name');
    };

    $scope.$on("kioskProdCtrl", function(event, data) {
        $scope.searchProd();
        event.preventDefault();
    });

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;
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
    }
    
    // 미등록상품 조회
    $scope.searchProd = function () {

        var params = {};
        params.posNo = $("#hdPosNo").val();
        params.tuClsCd = $("#hdTuClsCd").val();
        params.chkDt = $scope.isChecked;
        params.startDate = $scope.startDate;
        params.endDate = $scope.endDate;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;
        params.prodTypeFg = $scope.prodTypeFg;
        params.regYn = $scope.regYn;


        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getKioskProdList.sb", params, function() {}, false);
    }
    
    // 키 등록
    $scope.regProd = function () {

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].posNo = $("#hdPosNo").val();
                $scope.flex.collectionView.items[i].tuClsCd = $("#hdTuClsCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKey.sb", params, function(){

            // 미등록 상품 재조회
            $scope.searchProd()

            // 키 재조회
            var kioskKeyGrid = agrid.getScope("kioskKeyCtrl");
            kioskKeyGrid._pageView('kioskKeyCtrl', 1);
            
        });
    }
}]);