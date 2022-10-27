/****************************************************************
 *
 * 파일명 : cdKwn.js
 * 설  명 : 공통코드 (광운대 아이스링크) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.07     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회조건 DropBoxDataMap
var useYnFg = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"사용안함","value":"N"}
];

// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
    {id: "Y", name: "사용"},
    {id: "N", name: "사용안함"}
], 'id', 'name');

/**
 * 대표명칭 그리드 생성
 */
app.controller('cdKwnCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cdKwnCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchUseYnFg", useYnFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("cdKwnCtrl");

        // 그리드 내 콤보박스 설정
        $scope.useYnFgDataMap = useYnFgDataMap;

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "nmcodeCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink');
                        //wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        //wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 대표명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "nmcodeCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        // 대표명칭 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
                    //$scope._broadcast('cdKwnDetailCtrl', selectedRow.nmcodeCd);
                    $scope._broadcast('cdKwnDetailCtrl', selectedRow);
                }
            }
        });
    };

    // 대표명칭 그리드 조회
    $scope.$on("cdKwnCtrl", function(event, data) {
        // 파라미터
        var params = {};
        params.nmcodeGrpCd = "000";
        params.useYn = $scope.useYnFg;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/etc/cdKwn/cdKwn/getNmcodeCdKwnList.sb", params, function() {
            // 대표명칭 그리드 버튼 show
            //$("#btnAddRepresent").show();
            //$("#btnDelRepresent").show();
            //$("#btnSaveRepresent").show();
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 대표명칭 그리드 행 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.nmcodeGrpCd = "000";
        params.useYn = "Y";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 대표명칭 그리드 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            var item = $scope.flex.collectionView.itemsAdded[i];
            if(item.nmcodeCd === undefined || item.nmcodeCd.length === 0){
                $scope._popMsg(messages["cdKwn.represent.require.nmcodeCd"]); // 대표명칭의 코드를 입력해주세요
                return false;
            }

            if(item.nmcodeNm === undefined || item.nmcodeNm.length === 0){
                $scope._popMsg(messages["cdKwn.represent.require.nmcodeNm"]); // 대표명칭의 코드명을 입력해주세요
                return false;
            }

            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/etc/cdKwn/cdKwn/getNmcodeCdKwnSave.sb", params);
    };

    // 대표명칭 그리드 행 삭제
    $scope.deleteRow = function() {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                if(item.cnt > 0){
                    $scope._popMsg("세부명칭이 등록된 대표명칭은 삭제할 수 없습니다. ");
                    return false;
                }
                $scope.flex.collectionView.removeAt(i);
            }
        }
    }

}]);


/**
 * 세부명칭 그리드 생성
 */
app.controller('cdKwnDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cdKwnDetailCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("cdKwnDetailCtrl");

        // 그리드 내 콤보박스 설정
        $scope.useYnFgDataMap = useYnFgDataMap;

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "nmcodeCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
                var itemChk = s.rows[e.row].dataItem;
                if (itemChk.nmcodeGrpCd == "093" && (itemChk.nmcodeCd == "1" || itemChk.nmcodeCd == "2"))
                {
                    if (col.binding === "gChk" || col.binding === "nmcodeNm" || col.binding === "nmcodeItem1" || col.binding === "nmcodeItem2")
                    {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 대표명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "nmcodeCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
            var dataItemChk = s.rows[elements.row].dataItem;
            if (dataItemChk.nmcodeGrpCd == "093" && (dataItemChk.nmcodeCd == "1" || dataItemChk.nmcodeCd == "2"))
            {
                if (col.binding === "gChk" || col.binding === "nmcodeNm" || col.binding === "nmcodeItem1" || col.binding === "nmcodeItem2") {
                    elements.cancel = true;
                }
            }
        });
    };

    // 세부명칭 그리드 초기화
    $scope.$on("init", function() {
        $scope._gridDataInit();
    });

    // 세부명칭 그리드 조회
    $scope.$on("cdKwnDetailCtrl", function(event, data) {
        // 파라미터
        var params = {};
        params.nmcodeGrpCd = data.nmcodeCd;
        params.nmcodeItem1 = data.nmcodeItem1;
        params.nmcodeItem2 = data.nmcodeItem2;
        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope._inquirySub("/adi/etc/cdKwn/cdKwn/getNmcodeCdKwnList.sb", params, function() {
            // 세부명칭 그리드 버튼 show
            if( (gvOrgnFg == "H" && (params.nmcodeItem1 == "C" || params.nmcodeItem1 == "H"    )   )
                ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "S" )                                   )
                ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "C" && gvHqOfficeCd == "00000"      )   )
            )
            {
                $("#btnAddDetail").show();
                $("#btnDelDetail").show();
                $("#btnSaveDetail").show();
            }
            else
            {
                $("#btnAddDetail").hide();
                $("#btnDelDetail").hide();
                $("#btnSaveDetail").hide();
            }
        });

        $("#s_nmcodeCd").val(data.nmcodeCd);
        $("#s_nmcodeItem1").val(data.nmcodeItem1);
        $("#s_nmcodeItem2").val(data.nmcodeItem2);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.searchDetailCtrl = function(){
        var params = {};
        params.nmcodeGrpCd = $("#s_nmcodeCd").val(data.nmcodeCd);
        params.nmcodeItem1 = $("#s_nmcodeCd").val(data.nmcodeItem1);
        params.nmcodeItem2 = $("#s_nmcodeCd").val(data.nmcodeItem2);

        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope._inquirySub("/adi/etc/cdKwn/cdKwn/getNmcodeCdKwnList.sb", params, function() {
            // 세부명칭 그리드 버튼 show
            if( (gvOrgnFg == "H" && (params.nmcodeItem1 == "C" || params.nmcodeItem1 == "H"    )   )
                ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "S" )                                   )
                ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "C" && gvHqOfficeCd == "00000"      )   )
            )
            {
                $("#btnAddDetail").show();
                $("#btnDelDetail").show();
                $("#btnSaveDetail").show();
            }
            else
            {
                $("#btnAddDetail").hide();
                $("#btnDelDetail").hide();
                $("#btnSaveDetail").hide();
            }
        });

        $("#s_nmcodeCd").val(data.nmcodeCd);
        $("#s_nmcodeItem1").val(data.nmcodeItem1);
        $("#s_nmcodeItem2").val(data.nmcodeItem2);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    };

    // 세부명칭 그리드 행 추가
    $scope.addRow = function() {
        var gridRepresent = agrid.getScope('cdKwnCtrl');
        var selectedRow = gridRepresent.flex.selectedRows[0]._data;

        var params = {};
        params.nmcodeGrpCd = selectedRow.nmcodeCd;
        params.useYn = "Y";
        params.gChk = false;

        $scope._addRow(params, 1);
    };

    // 세부명칭 그리드 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            var item = $scope.flex.collectionView.itemsAdded[i];
            if(item.nmcodeCd === undefined || item.nmcodeCd.length === 0){
                $scope._popMsg(messages["cdKwn.detail.require.nmcodeCd"]); // 세부명칭의 코드를 입력해주세요
                return false;
            }

            if(item.nmcodeNm === undefined || item.nmcodeNm.length === 0){
                $scope._popMsg(messages["cdKwn.detail.require.nmcodeNm"]); // 세부명칭의 코드명을 입력해주세요
                return false;
            }

            if(item.nmcodeCd.length != $("#s_nmcodeItem2").val()){
                $scope._popMsg(messages["cdKwn.detail.require.nmcodeCdLengthChk"]+' ('+$("#s_nmcodeItem2").val()+')'); // 세부명칭의 코드자릿수를 확인하여 주십시오.
                return false;
            }

            var check_nmcodeCd_cnt = 0;
            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                if($scope.flex.collectionView.items[j].nmcodeCd == item.nmcodeCd) {
                    check_nmcodeCd_cnt++;
                }
            }
            if(check_nmcodeCd_cnt > 1){
                $scope._popMsg(messages["cdKwn.detail.require.nmcodeCdChk"]+' ('+item.nmcodeCd+')'); // 코드중복 확인
                return false;
            }

            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/etc/cdKwn/cdKwn/getNmcodeCdKwnSave.sb", params, function(){ $scope.allSearch() });

        // 재조회
        $scope.allSearch = function () {
            $scope.searchDetailCtrl();
        };
    };

    // 세부명칭 그리드 행 삭제
    $scope.deleteRow = function() {
        $scope._popConfirm(messages["cdKwn.detail.require.delConfirm"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk) {
                    if (item.nmcodeGrpCd == "093" && (item.nmcodeCd == "1" || item.nmcodeCd == "2"))
                    {
                        $scope._popMsg(messages["cdKwn.detail.require.chk.093.1"]); // 093 발주단위 기본값 0 낱개 1 박스 는 삭제할 수 없습니다.
                        return false;
                    }
                    $scope.flex.collectionView.removeAt(i);
                }
            }
            $scope.save();
        });
    }

}]);