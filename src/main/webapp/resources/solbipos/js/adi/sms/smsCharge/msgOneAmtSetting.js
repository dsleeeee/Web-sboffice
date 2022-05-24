/****************************************************************
 *
 * 파일명 : msgOneAmtSetting.js
 * 설  명 : 전송요금설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 메세지타입
var msgTypeDataMapData = [
    {"name":"SMS","value":"SMS"},
    {"name":"LMS","value":"LMS"},
    {"name":"MMS","value":"MMS"},
    {"name":"ALK","value":"ALK"},
    {"name":"ALK_LMS","value":"ALK_LMS"}
];
// 소속구분
var orgnFgComboData = [
    {"name":"전체","value":""},
    {"name":"관리자","value":"M"},
    {"name":"총판/대리점","value":"A"},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];

// 전송요금설정 상세에 추가버튼, '재료코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  전송요금설정 조회 그리드 생성
 */
app.controller('msgOneAmtSettingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgOneAmtSettingCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("msgOneAmtSettingOrgnFgCombo", orgnFgComboData); // 소속구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orgnFgDataMap = new wijmo.grid.DataMap(orgnFgComboData, 'value', 'name'); // 소속구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 소속코드
                if (col.binding === "orgnCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 소속코드 클릭시 상세정보 조회
                if ( col.binding === "orgnCd") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    var storeScope = agrid.getScope('msgOneAmtSettingDetailCtrl');
                    storeScope._broadcast('msgOneAmtSettingDetailCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });

        // 건당금액(기본값)
        $scope.searchMsgOneAmtBase();
    };

    // <-- 검색 호출 -->
    $scope.$on("msgOneAmtSettingCtrl", function(event, data) {
        $scope.searchMsgOneAmtSetting();
        event.preventDefault();
    });

    $scope.searchMsgOneAmtSetting = function() {
        var params = {};

        $scope._inquiryMain("/adi/sms/smsCharge/msgOneAmtSetting/getMsgOneAmtSettingList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('msgOneAmtSettingDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('msgOneAmtSettingDetailCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 건당금액(기본값)
    $scope.searchMsgOneAmtBase = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/sms/smsCharge/msgOneAmtSetting/getMsgOneAmtBaseList.sb", params, function(response){
            var msgOneAmtSetting = response.data.data.result;
            $scope.msgOneAmtSetting = msgOneAmtSetting;

            $("#msgOneAmtSettingSmsOneAmt").val($scope.msgOneAmtSetting.smsOneAmt);
            $("#msgOneAmtSettingLmsOneAmt").val($scope.msgOneAmtSetting.lmsOneAmt);
            $("#msgOneAmtSettingMmsOneAmt").val($scope.msgOneAmtSetting.mmsOneAmt);
            $("#msgOneAmtSettingAlkOneAmt").val($scope.msgOneAmtSetting.alkOneAmt);
            $("#msgOneAmtSettingAlkLmsOneAmt").val($scope.msgOneAmtSetting.alkLmsOneAmt);
        });
    };
}]);


/**
 *  전송요금설정 상세 조회 그리드 생성
 */
app.controller('msgOneAmtSettingDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgOneAmtSettingDetailCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
    };

    // <-- 검색 호출 -->
    $scope.$on("msgOneAmtSettingDetailCtrl", function(event, data) {
        $scope.setSelectedMsgOneAmtSetting(data);

        if(!$.isEmptyObject($scope.selectedMsgOneAmtSetting) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblOrgnCd").text(" [ " + $scope.selectedMsgOneAmtSetting.orgnCd + " ]");
            $("#lblOrgnNm").text($scope.selectedMsgOneAmtSetting.orgnNm);
            $scope.searchMsgOneAmtSettingDetail();

        } else if(addSelected === "N") {
            $("#lblOrgnCd").text("");
            $("#lblOrgnNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchMsgOneAmtSettingDetail = function() {
        var params = {};
        params.orgnCd = $scope.selectedMsgOneAmtSetting.orgnCd;

        $scope._inquiryMain("/adi/sms/smsCharge/msgOneAmtSetting/getMsgOneAmtSettingDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedMsgOneAmtSetting;
    $scope.setSelectedMsgOneAmtSetting = function(store) {
        $scope.selectedMsgOneAmtSetting = store;
    };
    $scope.getSelectedMsgOneAmtSetting = function(){
        return $scope.selectedMsgOneAmtSetting;
    };

    // <-- 저장 -->
    $scope.save = function() {
        if(!$.isEmptyObject($scope.selectedMsgOneAmtSetting) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            // 저장
            $scope.saveReal();

        } else if(addSelected === "N") {
            $scope._popMsg(messages["msgOneAmtSetting.orgnCdBlank"]); // 소속코드를 선택해주세요.
            return false;
        }
    };

    $scope.saveReal = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].msgOneAmt === "" || $scope.flex.collectionView.itemsEdited[i].msgOneAmt === null) {
                    $scope._popMsg(messages["msgOneAmtSetting.msgOneAmtBlack"]); // 건당금액을 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if(numChkexp.test($scope.flex.collectionView.itemsEdited[i].msgOneAmt)) {
                        $scope._popMsg(messages["msgOneAmtSetting.msgOneAmtInChk"]); // 건당금액은 숫자만 입력해주세요.
                        return false;
                    } else {
                        if(parseInt(nvl($scope.flex.collectionView.itemsEdited[i].msgOneAmt, 0)) > 0 && parseInt(nvl($scope.flex.collectionView.itemsEdited[i].msgOneAmt, 0)) < 1000) {
                        } else {
                            $scope._popMsg(messages["msgOneAmtSetting.msgOneAmtIntChk"]); // 건당금액은 1~999원만 입력해주세요.
                            return false;
                        }
                    }
                }

                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsCharge/msgOneAmtSetting/getMsgOneAmtSettingDetailSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchMsgOneAmtSettingDetail();
    };
    // <-- //저장 -->
}]);