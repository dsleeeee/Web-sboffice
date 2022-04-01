/****************************************************************
 *
 * 파일명 : alimtalkSendType.js
 * 설  명 : 알림톡 전송유형 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 전송유형 상세 등록에 추가버튼, '전송유형코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";
// 전송유형 템플릿 등록에 추가버튼, '전송유형상세코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected2 = "N";

// 전송주기구분
var sendPeriodFgComboData = [
    {"name":"대기인원수","value":"01"}
];

/**
 *  알림톡 전송유형 조회 그리드 생성
 */
app.controller('alimtalkSendTypeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendTypeCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 전송유형코드
                if (col.binding === "sendTypeCd") {
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

                // 전송유형코드 클릭시 상세정보 조회
                if ( col.binding === "sendTypeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    // var storeScope = agrid.getScope('alimtalkSendTypeDetailCtrl');
                    // storeScope._broadcast('alimtalkSendTypeDetailCtrl', selectedRow);
                    // event.preventDefault();

                    // 알림톡 계정등록 체크 없으면 아무동작 못하게
                    $scope.alimtalkIdRegisterChkStop(selectedRow);
                }
            }
        });

        // 페이지 로드시 호출
        $scope.initPageAlimtalkSendType();
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendTypeCtrl", function(event, data) {
        $scope.searchAlimtalkSendType();
        event.preventDefault();
    });

    $scope.searchAlimtalkSendType = function() {
        var params = {};

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkSendTypeList.sb", params, function() {
            addSelected = "N";
            addSelected2 = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('alimtalkSendTypeDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('alimtalkSendTypeDetailCtrl', null);

                var storeScope2 = agrid.getScope('alimtalkSendTypeTemplateCtrl');
                storeScope2._broadcast('alimtalkSendTypeTemplateCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 페이지 로드시 호출
    $scope.initPageAlimtalkSendType = function() {
        // 조회
        $scope.searchAlimtalkSendType();

        // 알림톡 계정등록 체크
        $scope.alimtalkIdRegisterChk("N");
    };

    // 알림톡 계정등록
    $scope.alimtalkIdRegister = function(){
        // 알림톡 계정등록 체크
        $scope.alimtalkIdRegisterChk("Y");
    };

    // 알림톡 계정등록 체크
    $scope.alimtalkIdRegisterChk = function(alertPopupYn){
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkIdRegisterChk.sb", params, function(response){
            var alimtalkIdInfo = response.data.data.result;
            $scope.alimtalkIdInfo = alimtalkIdInfo;

            if(response.data.data.result != null) {
                if (alertPopupYn == "Y") {
                    $scope._popMsg(messages["alimtalkSendType.alimtalkIdRegisterAlert"]); // 계정이 등록되어 있습니다.
                    return false;
                }
            } else {
                $scope.wjAlimtalkIdRegisterLayer.show(true);
                event.preventDefault();
            }
        });
    };

    // 알림톡 계정등록 체크 없으면 아무동작 못하게
    $scope.alimtalkIdRegisterChkStop = function(selectedRow){
        var params = {};

        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkIdRegisterChk.sb", params, function(response){
            var alimtalkIdInfo = response.data.data.result;
            $scope.alimtalkIdInfo = alimtalkIdInfo;

            if(response.data.data.result != null) {
                var storeScope = agrid.getScope('alimtalkSendTypeDetailCtrl');
                storeScope._broadcast('alimtalkSendTypeDetailCtrl', selectedRow);
                event.preventDefault();
            } else {
                $scope._popMsg(messages["alimtalkSendType.alimtalkIdRegisterStopAlert"]); // 계정등록 후 클릭해주세요.
                return false;
            }
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 알림톡 계정등록 팝업 핸들러 추가
        $scope.wjAlimtalkIdRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkIdRegisterCtrl', null);
            }, 50)
        });
    });
}]);


/**
 *  알림톡 전송유형 상세 조회 그리드 생성
 */
app.controller('alimtalkSendTypeDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendTypeDetailCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 전송유형상세코드
                if (col.binding === "sendTypeDtlCd") {
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

                // 전송유형상세코드 클릭시 상세정보 조회
                if ( col.binding === "sendTypeDtlCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    selectedRow.sendTypeCd = $scope.selectedSendType.sendTypeCd;

                    var storeScope = agrid.getScope('alimtalkSendTypeTemplateCtrl');
                    storeScope._broadcast('alimtalkSendTypeTemplateCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendTypeDetailCtrl", function(event, data) {
        $scope.setSelectedSendType(data);

        if(!$.isEmptyObject($scope.selectedSendType) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblSendTypeCd").text(" ( [ " + $scope.selectedSendType.sendTypeCd + " ]");
            $("#lblSendTypeNm").text($scope.selectedSendType.sendTypeNm + " )");
            $scope.searchAlimtalkSendTypeDetail();

        } else if(addSelected === "N") {
            $("#lblSendTypeCd").text("");
            $("#lblSendTypeNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchAlimtalkSendTypeDetail = function() {
        var params = {};
        params.sendTypeCd = $scope.selectedSendType.sendTypeCd;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkSendTypeDetailList.sb", params, function() {
            addSelected2 = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('alimtalkSendTypeTemplateCtrl');
                storeScope._broadcast('alimtalkSendTypeTemplateCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedSendType;
    $scope.setSelectedSendType = function(store) {
        $scope.selectedSendType = store;
    };
    $scope.getSelectedSendType = function(){
        return $scope.selectedSendType;
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if(addSelected === "N" ) {
            $scope._popMsg(messages["alimtalkSendType.sendTypeCdBlank"]); // 전송유형 코드를 선택해주세요.
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            $scope.flex.collectionView.itemsEdited[i].sendTypeCd = $scope.selectedSendType.sendTypeCd;
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkSendTypeDetailSave.sb", params, function(){
            $scope.searchAlimtalkSendTypeDetail();
        });
    };
    // <-- //그리드 저장 -->
}]);


/**
 *  알림톡 전송유형 템플릿 조회 그리드 생성
 */
app.controller('alimtalkSendTypeTemplateCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendTypeTemplateCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sendPeriodFgCombo", sendPeriodFgComboData); // 전송주기구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendTypeTemplateCtrl", function(event, data) {
        $("#divTemplateComment").html("");

        $scope.setSelectedSendTypeTemplate(data);

        if(!$.isEmptyObject($scope.selectedSendTypeTemplate) ) {
            addSelected2 = "Y";
        }
        if(addSelected2 === "Y") {
            $("#lblSendTypeDtlCd").text(" ( [ " + $scope.selectedSendTypeTemplate.sendTypeDtlCd + " ]");
            $("#lblSendTypeDtlNm").text($scope.selectedSendTypeTemplate.sendTypeDtlNm + " )");
            // 대기일때만
            if($scope.selectedSendTypeTemplate.sendTypeCd == "001") {
                $("#tabSendTypeTemplate").css("display", "");
            } else {
                $("#tabSendTypeTemplate").css("display", "none");
            }
            $("#divTemplateGrpPage").css("display", "");

            $scope.searchAlimtalkSendTypeTemplate();

        } else if(addSelected2 === "N") {
            $("#lblSendTypeDtlCd").text("");
            $("#lblSendTypeDtlNm").text("");
            $("#tabSendTypeTemplate").css("display", "none");
            $("#divTemplateGrpPage").css("display", "none");
        }
        event.preventDefault();
    });

    $scope.searchAlimtalkSendTypeTemplate = function() {
        var params = {};
        params.sendTypeCd = $scope.selectedSendTypeTemplate.sendTypeCd;
        params.sendTypeDtlCd = $scope.selectedSendTypeTemplate.sendTypeDtlCd;

        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkSendTypeDetailTemplate.sb", params, function(response){
            var templateInfo = response.data.data.result;
            $scope.templateInfo = templateInfo;

            if(response.data.data.result != null) {
                $scope.sendPeriodFg = $scope.templateInfo.sendPeriodFg;
                $scope.sendPeriod = $scope.templateInfo.sendPeriod;
                $("#lblTemplateGrpFg").text($scope.templateInfo.templateGrpFg);
                $("#lblTemplateCd").text($scope.templateInfo.templateCd);

                // 템플릿 양식 그리기
                $scope.searchTemplateFormMake("Y", $scope.templateInfo);

            } else {
                $scope.sendPeriodFg = "";
                $scope.sendPeriod = "";
                $("#lblTemplateGrpFg").text("");
                $("#lblTemplateCd").text("");

                // 템플릿 양식 그리기
                $scope.searchTemplateFormMake("N", null);
            }
        });
    };

    $scope.searchTemplateFormMake = function(dataYn, data) {
        var innerHtml = "";
        if(dataYn == "Y") {
            innerHtml += "<div style=\"float:left; text-align:center; width:225px; height:230px; padding-top:10px; padding-right:10px;\">";
            innerHtml += "<table>";
            innerHtml += "<colgroup>";
            innerHtml += "<col class=\"w100\" />";
            innerHtml += "</colgroup>";
            innerHtml += "<tbody>";
            innerHtml += "<table>";
            innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-msg w100\" value=\""+ data.templateNm +"\" readonly/></td></tr>";
            innerHtml += "<tr style=\"height: 10px\"></tr>";
            innerHtml += "<tr><td><textarea style=\"width:100%; height:180px; overflow-x:hidden; background-color: #EAF7FF\" readonly>" + data.templateContent + "</textarea></td></tr>";
            innerHtml += "</table>";
            innerHtml += "</div>";
            $("#divTemplateComment").html(innerHtml);

        } else {
            innerHtml += "<div style=\"float:left; text-align:center; width:225px; height:230px; padding-top:10px; padding-right:10px;\">";
            innerHtml += "<table>";
            innerHtml += "<colgroup>";
            innerHtml += "<col class=\"w100\" />";
            innerHtml += "</colgroup>";
            innerHtml += "<tbody>";
            innerHtml += "<table>";
            // innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-msg w100\" value=\""+ '' +"\" readonly/></td></tr>";
            // innerHtml += "<tr style=\"height: 10px\"></tr>";
            // innerHtml += "<tr><td><textarea style=\"width:100%; height:180px; overflow-x:hidden; background-color: #EAF7FF\" readonly>" + '' + "</textarea></td></tr>";
            innerHtml += "<tr><td>"+''+"</td></tr>";
            innerHtml += "</table>";
            innerHtml += "</div>";
            $("#divTemplateComment").html(innerHtml);
        }
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedSendTypeTemplate;
    $scope.setSelectedSendTypeTemplate = function(store) {
        $scope.selectedSendTypeTemplate = store;
    };
    $scope.getSelectedSendTypeTemplate = function(){
        return $scope.selectedSendTypeTemplate;
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if(addSelected2 === "N" ) {
            $scope._popMsg(messages["alimtalkSendType.sendTypeDtlCdBlank"]); // 전송유형상세 코드를 선택해주세요.
            return false;
        }

        // 전송주기
        if($scope.sendPeriod === "" || $scope.sendPeriod === null) {
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test($scope.sendPeriod)) {
                $scope._popMsg(messages["alimtalkSendType.sendPeriodNumberChk"]); // 전송주기는 숫자만 입력해주세요.
                return false;
            }
        }

        var params = {};
        params.sendTypeCd = $scope.selectedSendTypeTemplate.sendTypeCd;
        params.sendTypeDtlCd = $scope.selectedSendTypeTemplate.sendTypeDtlCd;
        params.sendPeriodFg = $scope.sendPeriodFg;
        params.sendPeriod = $scope.sendPeriod;
        params.templateGrpFg = $("#lblTemplateGrpFg").text();
        params.templateCd = $("#lblTemplateCd").text();

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/alimtalk/alimtalkSendType/alimtalkSendType/getAlimtalkSendTypeDetailTemplateSave.sb", params, function(){
            $scope.searchAlimtalkSendTypeTemplate();
        });
    };
    // <-- //그리드 저장 -->

    // 템플릿 선택
    $scope.templatePopup = function() {
        $scope.wjTemplatePopupLayer.show(true);
        event.preventDefault();
    };

    // 템플릿 선택 팝업 - 템플릿 목록 선택
    $scope.templateChoiceChange = function(data) {
        $("#lblTemplateGrpFg").text(data.templateGrpFg);
        $("#lblTemplateCd").text(data.templateCd);

        // 템플릿 양식 그리기
        $scope.searchTemplateFormMake("Y", data);
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 템플릿 선택 팝업 핸들러 추가
        $scope.wjTemplatePopupLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('templatePopupCtrl', $scope.getSelectedSendTypeTemplate());
            }, 50)
        });
    });
}]);