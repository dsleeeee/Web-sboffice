/****************************************************************
 *
 * 파일명 : envConfg.js
 * 설  명 : 환경설정관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var allEnvstGrp = new Array();
var cmmEnvstGrp = new Array();
var foodEnvstGrp = new Array();
var posEnvstGrp = new Array();

// dataMap 에서 name 만을 dataMap으로 사용. (name과 value 동시 사용시 오류) // todo 추후 수정 필요
for (var i in envstGrpCdNm) {
    // allEnvstGrp.push(envstGrpCdNm[i].name);
    if (envstGrpCdNm[i].nmcodeItem1 === '00') {
        cmmEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
    } else if (envstGrpCdNm[i].nmcodeItem1 === '01') {
        foodEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
    } else if (envstGrpCdNm[i].nmcodeItem1 === '03') {
        posEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
    }
}

// console.log('envstGrpCdNm', envstGrpCdNm)
// console.log('allEnvstGrp',allEnvstGrp);
// console.log('cmmEnvstGrp',cmmEnvstGrp);
// console.log('foodEnvstGrp',foodEnvstGrp);
// console.log('posEnvstGrp',posEnvstGrp);



/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', function ($scope, $http) {

    $scope._setComboData("envstFg", envstFgNm);

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('representCtrl', $scope, $http, true));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("representCtrl");
        // 그리드 DataMap 설정
        $scope.envstFgNmDataMap = new wijmo.grid.DataMap(envstFgNm, 'value', 'name');
        // $scope.envstGrpCdNmDataMap = new wijmo.grid.DataMap(envstGrpCdNm, 'value', 'name');
        $scope.envstGrpCdNmDataMap = allEnvstGrp;
        $scope.targtFgDataMap = new wijmo.grid.DataMap(targtFg, 'value', 'name');
        $scope.dirctInYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "직접"}, {
            id: "N",
            name: "선택"
        }], 'id', 'name');
        $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
            id: "N",
            name: "사용안함"
        }], 'id', 'name');
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            // 공통 안쓰고 페이지에서 헤더에 체크박스 그리는경우 사용 : 20180904 노현수
            // if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
            //   var flex = e.panel.grid;
            //   var col = s.columns[e.col];
            //   // check that this is a boolean column
            //   if (col.binding === "gChk") { // 여기에 해당하는 컬럼명 바인딩 바꿔줄 것.
            //     // prevent sorting on click
            //     col.allowSorting = false;
            //     // count true values to initialize checkbox
            //     var cnt = 0;
            //     for (var i = 0; i < flex.rows.length; i++) {
            //       if (flex.getCellData(i, col._idx) == true) cnt++;
            //     }
            //     // create and initialize checkbox
            //     e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check" />';
            //     var cb = e.cell.firstChild;
            //     cb.checked = cnt > 0;
            //     cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
            //     // apply checkbox value to cells
            //     cb.addEventListener('click', function (e) {
            //       flex.beginUpdate();
            //       for (var i = 0; i < flex.rows.length; i++) {
            //         flex.setCellData(i, col._idx, cb.checked);
            //       }
            //       flex.endUpdate();
            //     });
            //   }
            // }
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "envstCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });
        // 대표명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === "envstCd") {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    e.cancel = true;
                }
            }
        });
        // 대표명칭 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if (col.binding === "envstCd" && selectedRow.status !== "I") {
                    $scope._broadcast('detailCtrl', selectedRow.envstCd);
                }
            }
        });
    };
    // 대표명칭 그리드 조회
    $scope.$on("representCtrl", function (event, data) {

        // 파라미터
        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/cd/envConfg/envConfg/envst/list.sb", params, function () {
            // 대표명칭 그리드 버튼 show
            $("#btnAddRepresent").show();
            $("#btnDelRepresent").show();
            $("#btnSaveRepresent").show();

            $scope.flex.collectionView.commitEdit();

        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 설정그룹 변경시 환경그룹 dataMap 변경 ( //todo 추후 유통 추가)
    $scope.changeEnvstFg = function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            if (col.binding === "envstGrpCdNm") {
                var changeEnvstFg = s.rows[e.row].dataItem.envstFg;
                switch (changeEnvstFg) {
                    case '00':
                        col.dataMap = cmmEnvstGrp;
                        break;
                    case '01':
                        col.dataMap = foodEnvstGrp;
                        break;
                    case '03':
                        col.dataMap = posEnvstGrp;
                        break;
                    default:
                        col.dataMap = [''];
                }
            }
        }
    };

    // 대표명칭 그리드 행 추가
    $scope.addRow = function () {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.dirctInYn = "N";
        params.useYn = "N";
        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 대표명칭 그리드 저장
    $scope.save = function () {
        // 파라미터 설정
        var params = [];
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/cd/envConfg/envConfg/envst/save.sb", params, function () {
            $scope._broadcast('representCtrl', true);
            $scope._broadcast('detailCtrl', true);
        });
    };

    // 검색조건 설정구분 콤보박스 변경시, 환경그룹 콤보박스 셋팅
    $scope.setSrchEnvstFg = function (s) {

        var list = envstGrpCdNm;
        var comboArray = [];
        var comboData  = {};

        comboData.name  = messages["cmm.all"];
        comboData.value = "";
        comboArray.push(comboData);

        // 환경그룹 콤보박스 셋팅
        for (var i = 0; i < list.length; i++) {
            if (list[i].nmcodeItem1 == s.selectedValue) { // 설정구분이 [00]매장환경(공통), [01]외식환경, [03]포스환경 인 경우, 각각 사용중인 환경그룹코드가 셋팅된다.
                comboData = {};
                comboData.name = list[i].nmcodeNm;
                comboData.value = list[i].nmcodeCd;
                comboArray.push(comboData);
            }
        }

        $scope._setComboData("envstGrpCd", comboArray);
    };

}]);

/**
 * 세부명칭 그리드 생성
 */
app.controller('detailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('detailCtrl', $scope, $http, true));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("detailCtrl");
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
            id: "N",
            name: "사용안함"
        }], 'id', 'name');
        $scope.defltYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "기본"}, {
            id: "N",
            name: "기본아님"
        }], 'id', 'name');
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "envstValCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });
        // 세부명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === "envstValCd") {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    e.cancel = true;
                }
            }
        });
    };
    // 세부명칭 그리드 조회
    $scope.$on("detailCtrl", function (event, data) {
        // 파라미터
        var params = {};
        params.envstCd = data;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sys/cd/envConfg/envConfg/envstDtl/list.sb", params, function () {
            // 대표명칭 그리드 버튼 show
            $("#btnAddDetail").show();
            $("#btnDelDetail").show();
            $("#btnSaveDetail").show();
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    // 세부명칭 그리드 행 추가
    $scope.addRow = function () {
        var gridRepresent = agrid.getScope("representCtrl");
        var selectedRow = gridRepresent.flex.selectedRows[0]._data;
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.envstCd = selectedRow.envstCd;
        params.gChk = true;
        params.defltYn = "N";
        params.useYn = "N";
        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // 세부명칭 그리드 저장
    $scope.save = function () {

        // 초기값여부 한개 '기본'으로 설정되어 있는지 확인
        var cnt = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.defltYn === 'Y') {
                cnt++;
            }
        }
        if(cnt !== 1){
            s_alert.pop(messages["envConfg.defltYn.chk.msg"]); // 세부명칭의 초기값여부는 한개만 '기본'으로 선택해주세요.
            return false;
        }

        // 파라미터 설정
        var params = [];
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/cd/envConfg/envConfg/envstDtl/save.sb", params, function () {
            $scope._broadcast('detailCtrl', true);
        });
    };

}]);
