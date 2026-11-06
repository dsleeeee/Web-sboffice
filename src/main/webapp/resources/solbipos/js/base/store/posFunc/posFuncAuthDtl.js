/****************************************************************
 *
 * 파일명 : posFuncAuthDtl.js
 * 설  명 : 포스기능키 인증관리 인증허용등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.08    이다솜      1.0
 * 2026.11.04    김유승      1.1            대상선택 팝업 제거, 사원별 인증허용 열 추가 및 일괄 저장
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// angular 그리드 생성
app.controller('posFuncAuthDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posFuncAuthDtlCtrl', $scope, $http, false));

    // 사원 열 binding 접두어
    var EMP_PREFIX = "emp_";

    // 재직 사원 목록 (사원 열 생성용)
    $scope.empList = [];

    // 조회 당시 값 (변경 건수·저장 대상 비교용) : { fnkeyNo : { authYn, emp_사원번호 ... } }
    $scope.orgData = {};

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 사원 열 머리글(사원명 + 체크박스) 높이
        s.columnHeaders.rows.defaultSize = 50;

        s.formatItem.addHandler(function (s, e) {
            var col = s.columns[e.col];

            // 사원 열 머리글 : 사원명 + 일괄 허용/해제 체크박스 (전부 허용 체크, 일부 허용 ▬)
            if (e.panel === s.columnHeaders && isEmpCol(col)) {
                var cnt = 0;
                for (var i = 0; i < s.rows.length; i++) {
                    if (s.rows[i].dataItem[col.binding] === true) {
                        cnt++;
                    }
                }

                var nmDiv = document.createElement("div");
                nmDiv.textContent = col.header;
                var cb = document.createElement("input");
                cb.type = "checkbox";
                cb.className = "wj-cell-check";
                cb.checked = s.rows.length > 0 && cnt === s.rows.length;
                cb.indeterminate = cnt > 0 && cnt < s.rows.length;
                e.cell.innerHTML = "";
                e.cell.style.textAlign = "center";
                e.cell.appendChild(nmDiv);
                e.cell.appendChild(cb);

                cb.addEventListener("click", function (ev) {
                    ev.stopPropagation();
                    // 해당 사원을 기능구분 내 모든 기능키에 일괄 허용/해제 (허용 시 인증여부 자동 체크)
                    for (var i = 0; i < s.rows.length; i++) {
                        var item = s.rows[i].dataItem;
                        item[col.binding] = cb.checked;
                        if (cb.checked) {
                            item.authYn = true;
                        }
                    }
                    s.invalidate();
                    $scope.setChangeCnt();
                });
                return;
            }

            if (e.panel === s.cells) {
                var item = s.rows[e.row].dataItem;
                var noEmp = item.authYn === true && getAuthEmpCnt(item) === 0;

                // 인증여부 체크 + 허용 사원 0명 : 행 강조
                e.cell.style.backgroundColor = noEmp ? "#fff8e1" : "";

                // 기능키명 옆 "인증 사원 없음" 경고
                if (col.binding === "fnkeyNm" && noEmp) {
                    var warn = document.createElement("span");
                    warn.textContent = " " + messages["posFunc.auth.noEmp"];
                    warn.style.color = "#c0392b";
                    warn.style.fontWeight = "bold";
                    e.cell.appendChild(warn);
                }

                // 인증여부 미체크 행의 사원 칸 비활성 (값은 유지)
                if (isEmpCol(col) && item.authYn !== true) {
                    wijmo.addClass(e.cell, "wj-custom-readonly");
                    var input = e.cell.querySelector("input");
                    if (input) {
                        input.disabled = true;
                    }
                }
            }
        });

        // 인증여부 미체크 행의 사원 칸은 수정 불가
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (isEmpCol(col) && item.authYn !== true) {
                e.cancel = true;
            }
        });

        // 사원 체크 시 해당 기능키 인증여부 자동 체크
        s.cellEditEnded.addHandler(function (s, e) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (isEmpCol(col) && item[col.binding] === true) {
                item.authYn = true;
            }
            s.invalidate();
            $scope.setChangeCnt();
        });
    };

    $scope.$on("posFuncAuthDtlCtrl", function (event, data) {

        $scope.posFuncAuthDtlLayer.show(true);

        // 선택한 fnkey 이름 셋팅 및 hidden 에 값 가지고 있기
        $("#authDtlTitle").text(data.nmcodeNm);
        $("#hdStoreCd").val(data.storeCd);
        $("#hdFnkeyFg").val(data.nmcodeCd);

        // 포스기능키목록 그리드 조회
        $scope.getPosFuncAuthDetail(data);

        event.preventDefault();

    });

    // 포스기능인증 목록 상세 조회
    $scope.getPosFuncAuthDetail = function(data) {

        var params = {};

        params.storeCd = data.storeCd;
        params.fnkeyFg = data.nmcodeCd;

        $scope.empList = [];
        $scope.orgData = {};
        $scope.setChangeCnt();

        // 재직 사원별 인증허용 기능키 조회 후 기능키 목록 조회
        $scope._postJSONQuery.withOutPopUp("/base/store/posfunc/auth/getAuthEmpFnkeyList.sb", params, function (response) {

            // 사원별 허용 기능키 : { 사원번호 : { 기능키번호 : true } }
            var empFnkey = {};
            var list = response.data.data.list;
            for (var i = 0; i < list.length; i++) {
                if (empFnkey[list[i].empNo] === undefined) {
                    empFnkey[list[i].empNo] = {};
                    $scope.empList.push({empNo: list[i].empNo, empNm: list[i].empNm});
                }
                if (list[i].fnkeyNo) {
                    empFnkey[list[i].empNo][list[i].fnkeyNo] = true;
                }
            }

            $scope._inquirySub("/base/store/posfunc/auth/getPosConfAuthDetail.sb", params, function() {

                // 사원 열 다시 생성
                var grid = $scope.flex;
                for (var c = grid.columns.length - 1; c >= 0; c--) {
                    if (isEmpCol(grid.columns[c])) {
                        grid.columns.removeAt(c);
                    }
                }
                for (var j = 0; j < $scope.empList.length; j++) {
                    var empCol = new wijmo.grid.Column();
                    empCol.binding = EMP_PREFIX + $scope.empList[j].empNo;
                    empCol.header = $scope.empList[j].empNm;
                    empCol.dataType = wijmo.DataType.Boolean;
                    empCol.width = 80;
                    empCol.allowSorting = false;
                    grid.columns.push(empCol);
                }

                // 기능키별 사원 허용값 세팅, 조회 당시 값 보관
                var items = $scope.data.items;
                for (var r = 0; r < items.length; r++) {
                    var org = {authYn: items[r].authYn === true};
                    items[r].authYn = org.authYn;
                    for (var k = 0; k < $scope.empList.length; k++) {
                        var binding = EMP_PREFIX + $scope.empList[k].empNo;
                        items[r][binding] = empFnkey[$scope.empList[k].empNo][items[r].fnkeyNo] === true;
                        org[binding] = items[r][binding];
                    }
                    $scope.orgData[items[r].fnkeyNo] = org;
                }

                // 기능키번호·기능키명·인증여부 열 고정
                grid.frozenColumns = grid.columns.indexOf("authYn") + 1;
                grid.invalidate();
            }, false);
        });
    };

    // 변경 건수 표시 (조회 당시 값과 다른 기능키 행 수)
    $scope.setChangeCnt = function () {
        var cnt = 0;
        var items = $scope.flex && $scope.flex.collectionView ? $scope.flex.collectionView.items : [];
        for (var i = 0; i < items.length; i++) {
            if (getChangeParams(items[i]).length > 0) {
                cnt++;
            }
        }
        if (cnt > 0) {
            $("#authDtlChangeCnt").text(messages["posFunc.auth.changeCnt"].replace("{0}", cnt)).show();
        } else {
            $("#authDtlChangeCnt").text("").hide();
        }
    };

    // 저장
    $scope.save = function() {

        $scope.flex.collectionView.commitEdit();

        // 바뀐 것만 저장 (인증여부 : 사원번호 없이, 사원 허용 : 사원번호 포함)
        var params = [];
        var items = $scope.flex.collectionView.items;
        for (var i = 0; i < items.length; i++) {
            params = params.concat(getChangeParams(items[i]));
        }

        if (params.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]); // 변경 사항이 없습니다.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 저장기능 수행 : 저장 성공 시에만 팝업 닫기 (서버 검증 오류 시 수정 내용 유지)
            $scope._postJSONSave.withPopUp('/base/store/posfunc/auth/savePosAuthEmpConf.sb', params, function(){
                $scope.posFuncAuthDtlLayer.hide(true);
            });
        });
    };

    // 사원 열 여부
    function isEmpCol(col) {
        return col !== undefined && col !== null && typeof col.binding === "string" && col.binding.indexOf(EMP_PREFIX) === 0;
    }

    // 기능키별 허용 사원 수
    function getAuthEmpCnt(item) {
        var cnt = 0;
        for (var i = 0; i < $scope.empList.length; i++) {
            if (item[EMP_PREFIX + $scope.empList[i].empNo] === true) {
                cnt++;
            }
        }
        return cnt;
    }

    // 기능키 행의 변경분 저장 파라미터
    function getChangeParams(item) {
        var result = [];
        var org = $scope.orgData[item.fnkeyNo];
        if (org === undefined) {
            return result;
        }

        var storeCd = $("#hdStoreCd").val();
        var fnkeyFg = $("#hdFnkeyFg").val();

        if (item.authYn !== org.authYn) {
            result.push({storeCd: storeCd, fnkeyFg: fnkeyFg, fnkeyNo: item.fnkeyNo, authYn: item.authYn});
        }
        for (var i = 0; i < $scope.empList.length; i++) {
            var binding = EMP_PREFIX + $scope.empList[i].empNo;
            if (item[binding] !== org[binding]) {
                result.push({storeCd: storeCd, fnkeyFg: fnkeyFg, fnkeyNo: item.fnkeyNo, empNo: $scope.empList[i].empNo, useYn: item[binding]});
            }
        }
        return result;
    }

}]);
