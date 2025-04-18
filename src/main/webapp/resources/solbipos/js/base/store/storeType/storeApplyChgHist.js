/****************************************************************
 *
 * 파일명 : storeApplyChgHist.js
 * 설  명 : 매장타입관리 > 매장적용이력 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 처리구분
var applyProcFgComboData = [
    {"name":"전체","value":""},
    {"name":"매장적용요청(예약)","value":"0"},
    {"name":"적용완료","value":"1"},
    {"name":"적용오류","value":"2"},
    {"name":"적용취소","value":"3"},
    {"name":"매장적용요청(즉시)","value":"5"}
];

/**
 *  매장적용이력 그리드 생성
 */
app.controller('storeApplyChgHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeApplyChgHistCtrl', $scope, $http, false));

    // 변경일자 셋팅
    var startDate = wcombo.genDateVal("#srchSachStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchSachEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchSachStoreType", storeTypeList);
    $scope._setComboData("srchSachStoreGroup", storeGroupList);
    $scope._setComboData("srchSachStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.applyProcFgDataMap = new wijmo.grid.DataMap(applyProcFgComboData, 'value', 'name'); // 처리구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                var item = s.rows[e.row].dataItem;

                // 매장타입코드, 매장타입명
                if (col.binding === "storeTypeCd" || col.binding === "storeTypeNm") {
                    // 값이 있으면 링크 효과
                    if (item[("applyProcFg")] === "0" && item[("applyProcFgYn")] === "Y") {
                        wijmo.addClass(e.cell, 'wijLink-blue');
                    }
                }

                // 체크박스
                if (col.binding === "gChk") {
                    // 값이 있으면 링크 효과
                    if (item[("applyProcFg")] === "0" && item[("applyProcFgYn")] === "Y") {
                    } else {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        item[("gChk")] = false; // 전체 체크시 오류

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                var selectedRow = s.rows[ht.row].dataItem;

                // 매장타입코드, 매장타입명 클릭시 상세정보 조회
                if (col.binding === "storeTypeCd" || col.binding === "storeTypeNm") {
                    if (selectedRow[("applyProcFg")] === "0" && selectedRow[("applyProcFgYn")] === "Y") {
                        $scope.setSelectedStoreApplyChgHist(selectedRow);
                        $scope.wjStoreApplyChgHistDtlLayer.show(true);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("storeApplyChgHistCtrl", function(event, data) {
        $scope.searchStoreApplyChgHist();
        event.preventDefault();
    });

    $scope.searchStoreApplyChgHist = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#storeApplyChgHistStoreCd").val();
        params.storeTypeCd = $scope.srchSachStoreTypeCombo.selectedValue;
        params.storeGroupCd = $scope.srchSachStoreGroupCombo.selectedValue;

        if(brandUseFg === "1" && orgnFg === "HQ"){

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchSachStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquiryMain("/base/store/storeType/storeApplyChgHist/getStoreApplyChgHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 취소
    $scope.cancel = function () {
        $scope._popConfirm(messages["cmm.choo.cancel"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                s_alert.pop(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/storeType/storeApplyChgHist/getStoreApplyChgHistSaveUpdate.sb", params, function(){
                $scope.searchStoreApplyChgHist();
            });
        });
    };

    // 선택
    $scope.selectedStoreApplyChgHist;
    $scope.setSelectedStoreApplyChgHist = function(store) {
        $scope.selectedStoreApplyChgHist = store;
    };
    $scope.getSelectedStoreApplyChgHist = function() {
        return $scope.selectedStoreApplyChgHist;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 매장적용이력 상세 팝업 핸들러 추가
        $scope.wjStoreApplyChgHistDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('storeApplyChgHistDtlCtrl', $scope.getSelectedStoreApplyChgHist());
            }, 50)
        });
    });

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
                    return column.visible;
                }
            }, '매장적용이력_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);