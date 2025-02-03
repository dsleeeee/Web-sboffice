/****************************************************************
 *
 * 파일명 : recpOriginInfo.js
 * 설  명 : 원산지관리 - 정보입력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.20     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  관리코드 그리드 생성
 */
app.controller('recpOriginInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recpOriginInfoCtrl', $scope, $http, true));

    $scope._setComboData("srchRoProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 관리코드
                if (col.binding === "originCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                // 관리코드 클릭시 상세정보 조회
                if ( col.binding === "originCd" && selectedRow.status !== 'I') {
                    var params      = {};
                    params.originCd = selectedRow.originCd;
                    params.originNm = selectedRow.originNm;
                    $("#recpOriginInfoDetail").text('');
                    $("#recpOriginInfoDetail").val('');
                    $("#originCdNm").text('원산지내용([' + params.originCd + '] ' + params.originNm + ')');
                    $("#originCdNm").val(params.originCd);

                    // 정보 TEXT 조회
                    var storeScope = agrid.getScope('recpOriginInfoDetailCtrl');
                    storeScope._broadcast('recpOriginInfoDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "originNm") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

        // 조회
        $scope.searchRecpOriginInfo();
    };

    // <-- 검색 호출 -->
    $scope.$on("recpOriginInfoCtrl", function(event, data) {
        $scope.searchRecpOriginInfo();
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 조회
    $scope.searchRecpOriginInfo = function(){
        var params = {};

        $scope._inquiryMain("/base/prod/recpOrigin/recpOrigin/getRecpOriginInfoList.sb", params, function() {
            $scope.detailReset();

        }, false);
    };

    // 관리명 수정 시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };


    // <-- 그리드 행 추가 -->
    $scope.addRowInfo = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.originCd="자동채번";
        params.originNm = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.delInfo = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];

                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 저장
            $scope.saveInfo();
        });
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.saveInfo = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk === true) {
                if ($scope.flex.collectionView.items[i].originNm === "") {
                    $scope._popMsg(messages["recpOriginInfo.originNmBlank"]);
                    return false;
                }
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk === true) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].gChk === true) {
                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            if($scope.flex.collectionView.itemsRemoved[i].gChk === true) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/recpOrigin/getRecpOriginInfoSave.sb", params, function(){

            // 관리코드 리스트 재조회
            $scope.searchRecpOriginInfo();

            // 정보 TEXT 초기화
            $scope.detailReset();

        });
    };

    // 매장적용
    $scope.regStore = function(){
        $scope.wjOriginInfoRegStoreLayer.show();
        $scope._broadcast('originInfoRegStoreCtrl');
    };

    // 정보 TEXT 초기화
    $scope.detailReset = function(){
        $("#originCdNm").text('');
        $("#originCdNm").val('');
        $("#recpOriginInfoDetail").text('');
        $("#recpOriginInfoDetail").val('');
    };

}]);


/**
 *  정보 TEXT 그리드 생성
 */
app.controller('recpOriginInfoDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recpOriginInfoDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("recpOriginInfoDetailCtrl", function(event, data) {

        $scope.searchRecpOriginInfoDetail(data);
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    $scope.searchRecpOriginInfoDetail = function(data){
        var params = {};
        params.originCd = data.originCd;

        $scope._postJSONQuery.withPopUp("/base/prod/recpOrigin/recpOrigin/getRecpOriginInfoDetailList.sb", params, function(response) {
            var result = response.data.data.list;

            var msg = '';
            msg = nvl(result[0].originTxt1,'') + nvl(result[0].originTxt2,'') + nvl(result[0].originTxt3,'')
                    + nvl(result[0].originTxt4,'') + nvl(result[0].originTxt5,'')

            $("#recpOriginInfoDetail").val(msg);

        }, false);
    };



    // <-- 정보입력 - 저장 -->
    $scope.saveText = function() {

        if($("#originCdNm").val() === null || $("#originCdNm").val() === ''){
            var msg = messages["recpOriginInfo.originCd"] + messages["cmm.require.select"];
            $scope._popMsg(msg);
            return false;
        }

        var params = {};
        var orgInfoText = $("#recpOriginInfoDetail").val();
        var strLeng     = 0;
        var orgTxt1     = '';
        var orgTxt2     = '';
        var orgTxt3     = '';
        var orgTxt4     = '';
        var orgTxt5     = '';

        // Text별로 3200byte까지 입력 가능
        for(var i=0; i<orgInfoText.length; i++){
            var charLength = getByteLengthForOracleOrg(orgInfoText.charAt(i));
            strLeng += charLength;

            if (strLeng <= 3100) {
                orgTxt1 += orgInfoText.charAt(i);
            } else if (strLeng <= 6200) {
                orgTxt2 += orgInfoText.charAt(i);
            } else if (strLeng <= 9300) {
                orgTxt3 += orgInfoText.charAt(i);
            } else if (strLeng <= 12400) {
                orgTxt4 += orgInfoText.charAt(i);
            } else if (strLeng <= 15500) {
                orgTxt5 += orgInfoText.charAt(i);
            }
        }

        if(strLeng > 15500){
            var msg = messages["recpOriginInfo.regInfo"] + messages["cmm.overLength"] + " 15500 " +
                ", 현재 : " + strLeng + messages["cmm.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        params.originCd     = $("#originCdNm").val();
        params.originTxt1   = orgTxt1;
        params.originTxt2   = orgTxt2;
        params.originTxt3   = orgTxt3;
        params.originTxt4   = orgTxt4;
        params.originTxt5   = orgTxt5;

        $scope._postJSONSave.withPopUp("/base/prod/recpOrigin/recpOrigin/getRecpOriginInfoDetailSave.sb", params, function(){

        });

    };

    function getByteLengthForOracleOrg(char) {
        // 엔터는 2바이트
        if (char === '\n') {
            return 2;
        }
        // 그 외는 기존 getByteLengthForOracle 참고
        else {
            var i, c;
            for(var size=i=0;c=char.charCodeAt(i++);size+=c>>11?3:c>>7?2:1);
            return size;
        }
    }


}]);