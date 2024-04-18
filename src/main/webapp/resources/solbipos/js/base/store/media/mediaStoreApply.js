/****************************************************************
 *
 * 파일명 : mediaStoreApply.js
 * 설  명 : 매장별적용파일 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.17     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var langFg = [
    {"name":"전체","value":""},
    {"name":"국문","value":"0"},
    {"name":"영문","value":"1"},
    {"name":"중문","value":"2"},
    {"name":"일문","value":"3"}
];

/**********************************************************************
 *  미디어관리 그리드
 **********************************************************************/
app.controller('mediaStoreApplyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mediaStoreApplyCtrl', $scope, $http, true));

    // 전체기간 체크박스
    $scope.isChecked = true;
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYn);
    $scope._setComboData("fileType", fileTypeComboListAll);
    $scope._setComboData("langFg", langFg);

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate2", gvStartDate);

    // 선택 버전
    $scope.selectVersion;
    $scope.setSelectVersion = function(ver){
        $scope.selectVersion = ver;
    };
    $scope.getSelectVersion = function(){
        return $scope.selectVersion;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
        $scope.fileTypeDataMap = new wijmo.grid.DataMap(fileTypeComboList, 'value', 'name');
        $scope.langFgDataMap = new wijmo.grid.DataMap(langFg, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "preview" || col.binding === "downLoad") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 미리보기
                if(col.binding === "preview"){
                    if(item.fileExt === "png" || item.fileExt === "PNG" ||
                        item.fileExt === "jpg" || item.fileExt === "JPG" ||
                        item.fileExt === "jpeg" || item.fileExt === "JPEG" ||
                        item.fileExt === "gif" || item.fileExt === "GIF") {

                        e.cell.innerHTML = "미리보기";
                    }
                }

                // 다운로드
                if(col.binding === "downLoad"){
                    e.cell.innerHTML = "<td><a href=\"/base/store/media/media/download.sb?fileNm=" + item.fileNmExt + "&orginlFileNm=" + item.realFileOrgNm + "&fileExt=" + item.fileExt + "\">다운로드</a></td>";
                }
            }
        });

        // 버전 선택
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedData = s.rows[ht.row].dataItem;

                if ( col.binding === "preview") {
                    if(selectedData.fileExt === "png" || selectedData.fileExt === "PNG" ||
                        selectedData.fileExt === "jpg" || selectedData.fileExt === "JPG" ||
                        selectedData.fileExt === "jpeg" || selectedData.fileExt === "JPEG" ||
                        selectedData.fileExt === "gif" || selectedData.fileExt === "GIF") {

                        $scope._broadcast('mediaPreviewCtrl', selectedData.verSerNo);
                        $scope.mediaPreviewLayer.show(true);
                        event.preventDefault();
                    }
                }
            }
        });

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 파일사이즈
                if (col.binding === "fileSize") {
                    if(e.cell.innerText !== null && e.cell.innerText !== undefined && e.cell.innerText !== ""){
                        e.cell.innerHTML = getfileSize(e.cell.innerText.replaceAll(',',''));
                    }
                }
            }
        });

        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
    };

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mediaStoreShow = function () {
        $scope._broadcast('mediaStoreCtrl');
    };

    // 조회 버튼 클릭
    $scope.$on("mediaStoreApplyCtrl", function(event, data) {
        $scope.getMediaStoreApplyList();
        event.preventDefault();
    });

    // 매장별적용파일 목록 조회
    $scope.getMediaStoreApplyList = function(){
        var params = {};
        params.listScale = 5000;
        params.curr = $scope._getPagingInfo('curr');
        params.hqOfficeCd = hqOfficeCd;
        params.useYn = $scope.useYn;
        params.fileType = $scope.fileType;
        params.fileOrgNm = $("#fileOrgNm").text();
        params.langFg = $scope.langFg;
        params.storeCds   = $("#mediaStoreCd").val();

        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        }

        $scope._inquiryMain("/base/store/media/media/getMediaStoreApplyList.sb", params, function() {
        });
    };

    // 엑셀 다운로드
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
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, '듀얼모니터영상관리_매장별적용파일_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };


}]);