/****************************************************************
 *
 * 파일명 : alimtalkTemplateDtl.js
 * 설  명 : 알림톡 템플릿상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 계정/그룹
var dtlTemplateGrpFgComboData = [
    {"name":"그룹","value":"1"}
];
// 메세지유형
var dtlTemplateMsgTypeComboData = [
    {"name":"기본형","value":"BA"},
    {"name":"부가정보형","value":"EX"},
    {"name":"채널추가형","value":"AD"},
    {"name":"복합형","value":"MI"}
];
// 템플릿 강조 유형
var dtlTemplateEmpsizeTypeComboData = [
    {"name":"선택안함","value":"NONE"},
    {"name":"TEXT","value":"TEXT"},
    {"name":"이미지형","value":"IMAGE"}
];
// 보안 템플릿 여부
var dtlSecurityFgComboData = [
    {"name":"미설정","value":"false"},
    {"name":"설정","value":"true"}
];
// 버튼 타입
var dtlButtonsTypeData = [
    {"name":"배송조회","value":"DS"},
    {"name":"웹링크","value":"WL"},
    {"name":"앱링크","value":"AL"},
    {"name":"봇키워드","value":"BK"},
    {"name":"메세지전달","value":"MD"},
    {"name":"상담톡전환","value":"BC"},
    {"name":"봇전환","value":"BT"},
    {"name":"채널 추가","value":"AC"}
];

/**
 *  템플릿상세 조회 그리드 생성
 */
app.controller('alimtalkTemplateDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkTemplateDtlCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("dtlTemplateGrpFgCombo", dtlTemplateGrpFgComboData); // 계정/그룹
    $scope._setComboData("dtlTemplateMsgTypeCombo", dtlTemplateMsgTypeComboData); // 메세지유형
    $scope._setComboData("dtlTemplateEmpsizeTypeCombo", dtlTemplateEmpsizeTypeComboData); // 템플릿 강조 유형
    $scope._setComboData("dtlSecurityFgCombo", dtlSecurityFgComboData); // 보안 템플릿 여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.buttonsTypeDataMap = new wijmo.grid.DataMap(dtlButtonsTypeData, 'value', 'name'); // 버튼 타입

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.buttonsType = messages["alimtalkTemplateDtl.buttonsType"];
        dataItem.buttonsName = messages["alimtalkTemplateDtl.buttonsName"];
        dataItem.buttonsLinkMo = messages["alimtalkTemplateDtl.buttonsLink"];
        dataItem.buttonsLinkPc = messages["alimtalkTemplateDtl.buttonsLink"];
        dataItem.buttonsLinkLos = messages["alimtalkTemplateDtl.buttonsLink"];
        dataItem.buttonsLinkAndroid = messages["alimtalkTemplateDtl.buttonsLink"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkTemplateDtlCtrl", function(event, data) {
        $scope.searchAlimtalkTemplateDtl(data);
        event.preventDefault();
    });

    $scope.searchAlimtalkTemplateDtl = function(data) {
        var params = {};
        params.sendTypeCd = data.sendTypeCd;
        params.sendTypeDtlCd = data.sendTypeDtlCd;
        params.templateCd = data.templateCd;

        $scope._postJSONQuery.withOutPopUp( "/adi/alimtalk/alimtalkTemplate/alimtalkTemplateDtl/getAlimtalkTemplateDtlList.sb", params, function(response){
            var alimtalkTemplateDtl = response.data.data.result;
            $scope.alimtalkTemplateDtl = alimtalkTemplateDtl;

            $scope.alimtalkTemplateDtl.sendTypeNm = $scope.alimtalkTemplateDtl.sendTypeNm;
            $scope.alimtalkTemplateDtl.sendTypeDtlNm = $scope.alimtalkTemplateDtl.sendTypeDtlNm;
            $scope.alimtalkTemplateDtl.templateGrpFg = $scope.alimtalkTemplateDtl.templateGrpFg;
            $scope.alimtalkTemplateDtl.groupKeyNm = $scope.alimtalkTemplateDtl.groupKeyNm;
            $scope.alimtalkTemplateDtl.templateCd = $scope.alimtalkTemplateDtl.templateCd;
            $scope.alimtalkTemplateDtl.templateNm = $scope.alimtalkTemplateDtl.templateNm;
            $("#dtlTemplateContent").val($scope.alimtalkTemplateDtl.templateContent);
            $scope.alimtalkTemplateDtl.templateMsgType = $scope.alimtalkTemplateDtl.templateMsgType;
            $scope.alimtalkTemplateDtl.templateExtra = $scope.alimtalkTemplateDtl.templateExtra;
            $scope.alimtalkTemplateDtl.templateAd = $scope.alimtalkTemplateDtl.templateAd;
            // 기본형
            if($scope.alimtalkTemplateDtl.templateMsgType == "BA") {
                $("#trDtlTemplateExtra").css("display", "none");
                $("#trDtlTemplateAd").css("display", "none");
            // 부가정보형
            } else if($scope.alimtalkTemplateDtl.templateMsgType == "EX") {
                $("#trDtlTemplateExtra").css("display", "");
                $("#trDtlTemplateAd").css("display", "none");
            // 채널추가형
            } else if(s$scope.alimtalkTemplateDtl.templateMsgType == "AD") {
                $("#trDtlTemplateExtra").css("display", "none");
                $("#trDtlTemplateAd").css("display", "");
            // 복합형
            } else if($scope.alimtalkTemplateDtl.templateMsgType == "MI") {
                $("#trDtlTemplateExtra").css("display", "");
                $("#trDtlTemplateAd").css("display", "");
            }
            $scope.alimtalkTemplateDtl.templateEmpsizeType = $scope.alimtalkTemplateDtl.templateEmpsizeType;
            $scope.alimtalkTemplateDtl.templateTitle = $scope.alimtalkTemplateDtl.templateTitle;
            $scope.alimtalkTemplateDtl.templateSubtitle = $scope.alimtalkTemplateDtl.templateSubtitle;
            $("#dtlImgAlimtalkImage").attr("src", $scope.alimtalkTemplateDtl.templateImgUrl ||'/'|| templateImgNm ||'.png');
            // 선택안함
            if($scope.alimtalkTemplateDtl.templateEmpsizeType == "NONE") {
                $("#trDtlTemplateTitle").css("display", "none");
                $("#trDtlTemplateSubtitle").css("display", "none");
                $("#trDtlTemplateImgNm").css("display", "none");
            // TEXT
            } else if($scope.alimtalkTemplateDtl.templateEmpsizeType == "TEXT") {
                $("#trDtlTemplateTitle").css("display", "");
                $("#trTemplateSubtitle").css("display", "");
                $("#trDtlTemplateImgNm").css("display", "none");
            // 이미지형
            } else if($scope.alimtalkTemplateDtl.templateEmpsizeType == "IMAGE") {
                $("#trDtlTemplateTitle").css("display", "none");
                $("#trDtlTemplateSubtitle").css("display", "none");
                $("#trDtlTemplateImgNm").css("display", "");
            }
            $scope.alimtalkTemplateDtl.securityFg = $scope.alimtalkTemplateDtl.securityFg;
            $scope.alimtalkTemplateDtl.templateClsNmL = $scope.alimtalkTemplateDtl.templateClsNmL;
            $scope.alimtalkTemplateDtl.templateClsNmM = $scope.alimtalkTemplateDtl.templateClsNmM;

            // 버튼 리스트 조회
            $scope.searchAlimtalkTemplateDtlButtons(params);
        });
    };

    // 버튼 리스트 조회
    $scope.searchAlimtalkTemplateDtlButtons = function(params) {

        $scope._inquirySub("/adi/alimtalk/alimtalkTemplate/alimtalkTemplateDtl/getAlimtalkTemplateDtlButtonsList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);