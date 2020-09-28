/****************************************************************
 *
 * 파일명 : simpleProd.js
 * 설  명 : 간편상품등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품목록 조회 그리드 생성
 */
app.controller('simpleProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('simpleProdCtrl', $scope, $http, true));

    // 상품 본사통제구분 (H : 본사, S: 매장)
    $scope.prodEnvstVal = prodEnvstVal;
    $scope.userOrgnFg = gvOrgnFg;

    // 상품코드 채번방식
    $scope.prodNoEnvFg = prodNoEnvFg;

    // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
    $scope.userStoreCd = gvStoreCd;
    $("#divSimpleProd").css("display", "none");
    $("#divSimpleProdAuth").css("display", "none");

    if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
        || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
        $("#divSimpleProd").css("display", "block");
        $("#divSimpleProdAuth").css("display", "none");
    } else {
        $("#divSimpleProd").css("display", "none");
        $("#divSimpleProdAuth").css("display", "block");
    }

    if($scope.prodEnvstVal === 'HQ') {
        $("#lblSimpleProdAuth").text("'본사통제'");
    } else if($scope.prodEnvstVal === 'STORE') {
        $("#lblSimpleProdAuth").text("'매장통제'");
    }

    // 상품명 중복체크
    $scope.isChecked = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 검증결과
                if (col.binding === "result") {
                     var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (item[("result")] !== '검증전' && item[("result")] !== '검증성공') {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 전체삭제
        $scope.delAll();

        // 그리드 셋팅
        $scope.searchSimpleProdDefault(25);
    };

    // <-- 검색 호출 -->
    $scope.$on("simpleProdCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 그리드 셋팅
    $scope.searchSimpleProdDefault = function(rowsCount) {
        // 파라미터 설정
        var params = {};
        params.result = "검증전";
        if($scope.prodNoEnvFg === "MANUAL") {
            params.prodCd = "";
        }
        params.prodNm = "";
        params.saleUprc = "";
        params.prodTypeFg = "1";
        params.poProdFg = "1";
        params.splyUprc = "0";
        params.costUprc = "0";
        params.vatFg = "1";
        params.barCd = "";

        for(var i = 0; i < rowsCount; i++) {
            // 추가기능 수행 : 파라미터
            $scope._addRow(params);
        }
    };

    // 전체삭제
    $scope.delAll = function() {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/simpleProd/simpleProd/getSimpleProdCheckDeleteAll.sb", params, function(){});
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

    // 초기화
    $scope.clear = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].result = "검증전";
            if($scope.prodNoEnvFg === "MANUAL") {
                $scope.flex.collectionView.items[i].prodCd = "";
            }
            $scope.flex.collectionView.items[i].prodNm = "";
            $scope.flex.collectionView.items[i].saleUprc = "";
            $scope.flex.collectionView.items[i].prodTypeFg = "1";
            $scope.flex.collectionView.items[i].poProdFg = "1";
            $scope.flex.collectionView.items[i].splyUprc = "0";
            $scope.flex.collectionView.items[i].costUprc = "0";
            $scope.flex.collectionView.items[i].vatFg = "1";
            $scope.flex.collectionView.items[i].barCd = "";
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.save = function() {
        // 전체삭제
        $scope.delAll();

        if (isNull($scope.prodClassCd)) {
            $scope._popMsg(messages["simpleProd.prodClassCdBlank"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            // 상품코드 채번방식
            var prodNoEnv = "";
            if($scope.prodNoEnvFg === "MANUAL") { prodNoEnv = "1"; } else { prodNoEnv = "0"; } // 0 : 자동채번, 1 : 수동채번
            $scope.flex.collectionView.items[i].prodNoEnv = prodNoEnv;

            // 상품명 중복체크
            $scope.flex.collectionView.items[i].chkProdNm = $scope.isChecked;

            // 상품분류코드
            $scope.flex.collectionView.items[i].prodClassCd = $scope.prodClassCd;


            // <-- 검증 -->
            var result = "";

            // 바코드
            if($scope.flex.collectionView.items[i].barCd === "" || $scope.flex.collectionView.items[i].barCd === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].barCd, '').getByteLengthForOracle() > 40) { result = messages["simpleProd.barCdLengthChk"]; } // 바코드 길이가 너무 깁니다.

                //  바코드 중복체크
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    if(i !== j) {
                        if($scope.flex.collectionView.items[j].barCd === $scope.flex.collectionView.items[i].barCd) { result = messages["simpleProd.barCdChk"]; } // 바코드가 중복됩니다.
                    }
                }
            }

            // 과세여부
            if($scope.flex.collectionView.items[i].vatFg === "" || $scope.flex.collectionView.items[i].vatFg === null) { result = messages["simpleProd.vatFgBlank"]; } // 과세여부를 선택하세요.

            // 원가단가
            if($scope.flex.collectionView.items[i].costUprc === "" || $scope.flex.collectionView.items[i].costUprc === null) {
                result = messages["simpleProd.costUprcBlank"]; // 원가단가를 입력하세요.
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].costUprc)) {
                    $scope.flex.collectionView.items[i].costUprc = "";
                    result = messages["simpleProd.costUprcInChk"]; // 원가단가 숫자만 입력해주세요.
                }
            }

            // 공급단가
            if($scope.flex.collectionView.items[i].splyUprc === "" || $scope.flex.collectionView.items[i].splyUprc === null) {
                result = messages["simpleProd.splyUprcBlank"]; // 공급단가를 입력하세요.
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].splyUprc)) {
                    $scope.flex.collectionView.items[i].splyUprc = "";
                    result = messages["simpleProd.splyUprcInChk"]; // 공급단가 숫자만 입력해주세요.
                }
            }

            // 발주상품구분
            if($scope.flex.collectionView.items[i].poProdFg === "" || $scope.flex.collectionView.items[i].poProdFg === null) { result = messages["simpleProd.poProdFgBlank"]; } // 발주상품구분을 선택하세요.

            // 상품유형
            if($scope.flex.collectionView.items[i].prodTypeFg === "" || $scope.flex.collectionView.items[i].prodTypeFg === null) { result = messages["simpleProd.prodTypeFgBlank"]; } // 상품유형을 입력하세요.

            // 판매단가
            if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                result = messages["simpleProd.saleUprcBlank"]; // 판매단가를 입력하세요.
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                    $scope.flex.collectionView.items[i].saleUprc = "";
                    result = messages["simpleProd.saleUprcInChk"]; // 판매단가 숫자만 입력해주세요.
                }
            }

            // 상품명
            if($scope.flex.collectionView.items[i].prodNm === "" || $scope.flex.collectionView.items[i].prodNm === null) {
                result = messages["simpleProd.prodNmBlank"]; // 상품명을 입력하세요.
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].prodNm, '').getByteLengthForOracle() > 100) { result = messages["simpleProd.prodNmLengthChk"]; } // 상품명 길이가 너무 깁니다.

                // 상품명 중복체크
                if($scope.isChecked === true) {
                    for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                        if(i !== j) {
                            if($scope.flex.collectionView.items[j].prodNm === $scope.flex.collectionView.items[i].prodNm) { result = messages["simpleProd.prodNmChk"]; } // 상품명이 중복됩니다.
                        }
                    }
                }
            }

            // 상품코드
            // 수동채번일때만 체크
            if(prodNoEnv === "1") {
                if ($scope.flex.collectionView.items[i].prodCd === "" || $scope.flex.collectionView.items[i].prodCd === null) {
                    result = messages["simpleProd.prodCdBlank"]; // 상품코드를 입력하세요.
                } else {
                    // 숫자/영문만 입력
                    var numChkexp = /[^A-Za-z0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].prodCd)) { result = messages["simpleProd.prodCdInChk"]; } // 숫자/영문만 입력해주세요.

                    // 최대길이 체크
                    if (nvl($scope.flex.collectionView.items[i].prodCd, '').getByteLengthForOracle() > 13) { result = messages["simpleProd.prodCdLengthChk"]; } // 상품코드 길이가 너무 깁니다.

                    // 상품코드 중복체크
                    for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                        if (i !== j) {
                            if ($scope.flex.collectionView.items[j].prodCd === $scope.flex.collectionView.items[i].prodCd) { result = messages["simpleProd.prodCdChk"]; } // 상품코드가 중복됩니다.
                        }
                    }
                }
            }

            $scope.flex.collectionView.items[i].result = result;
            // <-- //검증 -->


            params.push($scope.flex.collectionView.items[i]);
        }

        // 검증결과 저장
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/simpleProd/simpleProd/getSimpleProdCheckSave.sb", params, function(){
            // 검증결과 조회
            $scope.searchSimpleProdCheckList("select");
            $scope._popConfirm(messages["simpleProd.saveConfirm"], function() {
                // 상품등록 저장
                $scope.SimpleProdSave();
            });
        });
    };

    // 검증결과 조회
    $scope.searchSimpleProdCheckList = function(data) {
        var gubun = data;

        var params = {};

        $scope._inquirySub("/base/prod/simpleProd/simpleProd/getSimpleProdList.sb", params, function() {
            if(gubun === "save") {
                var rowsCount = 25 - $scope.flex.collectionView.items.length;
                // 그리드 셋팅
                $scope.searchSimpleProdDefault(rowsCount);
            }
        }, false);
    };

    // 상품등록 저장
    $scope.SimpleProdSave = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/base/prod/simpleProd/simpleProd/getSimpleProdSave.sb", params, function(){
            // 검증결과 조회
            $scope.searchSimpleProdCheckList("save");
        });
    };

}]);