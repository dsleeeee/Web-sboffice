/****************************************************************
 *
 * 파일명 : acComCode.js
 * 설  명 : 벤슨 > 회계관리 > 회계관리 > 공통코드관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.14     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 DropBoxDataMap (그리드 콤보 매핑용)
var useYnFgDataMap = new wijmo.grid.DataMap([
    {id: "Y", name: "사용"},
    {id: "N", name: "사용안함"}
], 'id', 'name');

/**
 * 공통코드 (좌측) 그리드 생성
 */
app.controller('acComCodeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acComCodeCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("acComCodeCtrl");

        // 그리드 내 콤보박스 설정
        $scope.useYnFgDataMap = useYnFgDataMap;

        // 신규(status=='I')행만 코드 입력 가능하도록 편집 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "acNmcodeCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        // 기존행(status!='I')의 코드는 링크 효과 + readonly 배경, 신규행은 편집 가능 표시
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "acNmcodeCd") {
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

        // 공통코드 그리드 선택 시 상세코드 그리드 조회
        s.hostElement.addEventListener('mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if (col.binding === "acNmcodeCd" && selectedRow.status !== "I") {
                    $scope._broadcast('acComCodeDtlCtrl', selectedRow);
                }
            }
        });

        // 수정 시 체크박스 자동 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var item = s.rows[e.row].dataItem;
                $scope.checked(item);
            }
            s.collectionView.commitEdit();
        });
    };

    // 수정 시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    // 공통코드 그리드 조회 (조회조건 없음 : 조회 버튼 클릭 시에만 조회)
    $scope.$on("acComCodeCtrl", function (event, data) {
        var params = {};

        $scope._inquiryMain("/accounting/accountingMain/acComCode/getAcComCodeList.sb", params, function () {});

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 공통코드 그리드 행 추가
    $scope.addRow = function () {
        var params = {};
        params.useYn = "Y";
        params.gChk = false;

        $scope._addRow(params, 1);
    };

    // 공통코드 그리드 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                var item = $scope.flex.collectionView.itemsAdded[i];
                if (item.acNmcodeCd === undefined || item.acNmcodeCd.length === 0) {
                    $scope._popMsg(messages["acComCode.require.nmcodeCd"]); // 코드를 입력해주세요.
                    return false;
                }
                if (item.acNmcodeNm === undefined || item.acNmcodeNm.length === 0) {
                    $scope._popMsg(messages["acComCode.require.nmcodeNm"]); // 코드명을 입력해주세요.
                    return false;
                }
                if (item.acNmcodeCd.length > 4) {
                    $scope._popMsg(messages["acComCode.require.nmcodeCdLengthChk"]); // 코드는 4자 이내로 입력 가능합니다.
                    return false;
                }

                // 코드중복 확인 (그리드에 로드된 항목 기준)
                var dupCnt = 0;
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    if ($scope.flex.collectionView.items[j].acNmcodeCd === item.acNmcodeCd) {
                        dupCnt++;
                    }
                }
                if (dupCnt > 1) {
                    $scope._popMsg(messages["acComCode.require.nmcodeCdChk"] + ' (' + item.acNmcodeCd + ')'); // 코드중복 확인
                    return false;
                }

                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            $scope._save("/accounting/accountingMain/acComCode/saveAcComCode.sb", params, function () {
                $scope._broadcast('acComCodeCtrl');
            });
        });
    };

    // 공통코드 그리드 행 삭제 (체크된 row만, 확인 후 즉시 반영)
    $scope.deleteRow = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                params.push(item);
            }
        }

        if (params.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
            return false;
        }

        $scope._popConfirm(messages["acComCode.require.delConfirm"], function () {
            for (var i = 0; i < params.length; i++) {
                params[i].status = "D";
            }

            $scope._save("/accounting/accountingMain/acComCode/saveAcComCode.sb", params, function () {
                $scope._broadcast('acComCodeCtrl');
            });
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.binding != 'gChk'; // 선택 컬럼 제외
                }
            }, messages["acComCode.grpGridNm"] + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);

/**
 * 상세코드 (우측) 그리드 생성
 */
app.controller('acComCodeDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acComCodeDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("acComCodeDtlCtrl");

        // 그리드 내 콤보박스 설정
        $scope.useYnFgDataMap = useYnFgDataMap;

        // 신규(status=='I')행만 코드 입력 가능하도록 편집 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "acNmcodeDtlCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });

        // 기존행(status!='I')의 코드는 readonly 배경 표시, 신규행은 편집 가능 표시
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "acNmcodeDtlCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        // 수정 시 체크박스 자동 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var item = s.rows[e.row].dataItem;
                $scope.checked(item);
            }
            s.collectionView.commitEdit();
        });
    };

    // 수정 시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    // 좌측(공통코드) 그리드가 재조회될 때만 상세코드 그리드 비움 ('init'은 우리 클릭→조회 흐름에도 같이 뜨므로 사용 안 함)
    $scope.$on("acComCodeCtrl", function () {
        $scope._gridDataInit();
        $("#s_acNmcodeCd").val("");
    });

    // 좌측(공통코드) 그리드에서 선택된 코드로 상세코드 그리드 조회
    $scope.$on("acComCodeDtlCtrl", function (event, data) {
        var params = {};
        params.acNmcodeCd = data.acNmcodeCd;

        $("#s_acNmcodeCd").val(data.acNmcodeCd);

        $scope._inquirySub("/accounting/accountingMain/acComCode/getAcComCodeDtlList.sb", params, function () {});

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 재조회 (저장/삭제 후 캐시된 부모코드로 다시 조회)
    $scope.allSearch = function () {
        var params = {};
        params.acNmcodeCd = $("#s_acNmcodeCd").val();

        $scope._inquirySub("/accounting/accountingMain/acComCode/getAcComCodeDtlList.sb", params, function () {});
    };

    // 상세코드 그리드 행 추가
    $scope.addRow = function () {
        var gridGrp = agrid.getScope('acComCodeCtrl');
        if (isEmpty(gridGrp.flex.selectedRows[0])) {
            $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
            return false;
        }
        var selectedRow = gridGrp.flex.selectedRows[0]._data;

        var params = {};
        params.acNmcodeCd = selectedRow.acNmcodeCd;
        params.useYn = "Y";
        params.gChk = false;

        $scope._addRow(params, 2);
    };

    // 상세코드 그리드 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                var item = $scope.flex.collectionView.itemsAdded[i];
                if (item.acNmcodeDtlCd === undefined || item.acNmcodeDtlCd.length === 0) {
                    $scope._popMsg(messages["acComCode.detail.require.nmcodeCd"]); // 상세코드를 입력해주세요.
                    return false;
                }
                if (item.acNmcodeDtlNm === undefined || item.acNmcodeDtlNm.length === 0) {
                    $scope._popMsg(messages["acComCode.detail.require.nmcodeNm"]); // 상세코드명을 입력해주세요.
                    return false;
                }
                if (item.acNmcodeDtlCd.length > 4) {
                    $scope._popMsg(messages["acComCode.detail.require.nmcodeCdLengthChk"]); // 상세코드는 4자 이내로 입력 가능합니다.
                    return false;
                }

                // 코드중복 확인 (그리드에 로드된 항목 기준)
                var dupCnt = 0;
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    if ($scope.flex.collectionView.items[j].acNmcodeDtlCd === item.acNmcodeDtlCd) {
                        dupCnt++;
                    }
                }
                if (dupCnt > 1) {
                    $scope._popMsg(messages["acComCode.detail.require.nmcodeCdChk"] + ' (' + item.acNmcodeDtlCd + ')'); // 코드중복 확인
                    return false;
                }

                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            $scope._save("/accounting/accountingMain/acComCode/saveAcComCodeDtl.sb", params, function () {
                $scope.allSearch();
            });
        });
    };

    // 상세코드 그리드 행 삭제 (체크된 row만)
    $scope.deleteRow = function () {
        $scope._popConfirm(messages["acComCode.detail.require.delConfirm"], function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    item.status = "D";
                    params.push(item);
                }
            }

            if (params.length <= 0) {
                $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
                return false;
            }

            $scope._save("/accounting/accountingMain/acComCode/saveAcComCodeDtl.sb", params, function () {
                $scope.allSearch();
            });
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.binding != 'gChk'; // 선택 컬럼 제외
                }
            }, messages["acComCode.gridNm"] + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
