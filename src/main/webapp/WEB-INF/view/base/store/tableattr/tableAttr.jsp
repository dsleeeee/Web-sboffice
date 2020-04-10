<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #content div {
        font-family: inherit;
        font-weight: inherit;
        font-size: inherit;
        color: inherit;
    }
</style>
<script>
    var urlParams = (function(url) {
        var result = {};
        var idx = url.lastIndexOf('?');

        if (idx > 0) {
            var params = url.substring(idx + 1).split('&');

            for (var i = 0; i < params.length; i++) {
                idx = params[i].indexOf('=');

                if (idx > 0) {
                    result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
                }
            }
        }

        return result;
    })(window.location.href);

    // Default resources are included in grapheditor resources
    mxLoadResources = false;

    // urlParams is null when used for embedding
    window.urlParams = window.urlParams || {};

    window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE || 10485760;

    window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
    window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
    window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
    window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
    window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';
    window.STENCIL_PATH = window.STENCIL_PATH || '/resource/graph/stencils';
    //window.CONFIG_PATH = window.CONFIG_PATH || '/resource/graph/config';

    window.TABLEATTR_OPEN_URL = window.TABLEATTR_OPEN_URL || '/base/store/tableAttr/tableAttr/view.sb';
    window.TABLEATTR_SAVE_URL = window.TABLEATTR_SAVE_URL || '/base/store/tableAttr/tableAttr/save.sb';

    window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
    window.mxLanguage = window.mxLanguage || urlParams['lang'];
    window.mxLanguages = window.mxLanguages || ['ko'];

    //Custom varible
    window.TABLE_ATTR_ITEMS = ${tableAttrs};
    window.TABLE_ATTR_DEFAULTS = ${defaults};

    <%--
    //local test
    var tableAttrs = [];
    tableAttrs.push({
        nmcodeNm: "테이블명",
        nmcodeItem1: "1번"
    });
    tableAttrs.push({
        nmcodeNm: "주문메뉴",
        nmcodeItem1: "아메리카노<br>아이스라떼"
    });

    var defaults = [];
    defaults.push({
        attrCd: '01',
        x: 0,
        y: 0,
        width: 100,
        height: 150
    });
    defaults.push({
        attrCd: '02',
        x: 150,
        y: 150,
        width: 200,
        height: 250
    });

    window.TABLE_ATTR_ITEMS = tableAttrs;
    window.TABLE_ATTR_DEFAULTS = defaults;
    --%>

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.js" charset="utf-8"></script>
<!--script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js"></script-->
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TableAttr.js" charset="utf-8"></script>

<%--서브컨텐츠--%>
<div class="subCon2" ng-controller="tbAttrCtrl">
    <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
    <div class="posWrap oh" style="width:1010px">
        <%--left--%>
        <div class="fl" style="width:228px">
            <%--구성요소--%>
            <h2 class="h2_tit2">
                <s:message code="tableAttr.component" />
            </h2>
            <div class="cfgWrap">
                <%--위즈모 테이블--%>
                <div id="theGrid"></div><%--tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요.--%>
                <%--//위즈모 테이블--%>
            </div>
            <%--//구성요소--%>

        </div>
        <%--//left--%>

        <%--center--%>
        <div class="fl" style="width:532px">
            <%--미리보기--%>
            <div class="ml20">
                <h2 class="h2_tit2">
                    <s:message code="tableAttr.preview" />
                </h2>
                <div class="prev1 patternBg" id="content" tabindex="-1">
                    <%--점 패턴 background classname : "patternBg"--%>
                    <%--510 * 510 pixel 입니다.--%>
                </div>
            </div>
            <%--//미리보기--%>

            <%--설정--%>
            <div class="shopSetting ml20" id="format">
                <div class="btn_int">
                    <button class="btn_skyblue" id="btnInit">
                        <s:message code="cmm.init" />
                    </button>
                    <button class="btn_skyblue" id="btnSave">
                        <s:message code="cmm.save" />
                    </button>
                </div>
            </div>
            <%--//설정--%>
        </div>
        <%--//center--%>

        <%-- right --%>
        <div class="fl tch-inner">
            <%-- 위치 --%>
            <div class="touch-box mt10">
                <span class="bk lh30 s12">위치</span>
                <div class="touch-box mb10 s12">
                    <div class="fl w45 mb5">
                        <span class="txtIn bk lh30 s14 w30px">X</span>
                        <input type="text" class="fr sb-input w50px tc" id="cellX" maxlength="4">
                    </div>
                    <div class="fr w45 mb5">
                        <span class="txtIn bk lh30 s14 w30px">Y</span>
                        <input type="text" class="fr sb-input w50px tc" id="cellY" maxlength="4">
                    </div>
                    <div class="clearfix"></div>
                    <div class="fl w45">
                        <span class="bk lh30 s12 w30px"><s:message code="func.width"/></span>
                        <input type="text" class="fr sb-input w50px tc" id="cellW" maxlength="4">
                    </div>
                    <div class="fr w45">
                        <span class="bk lh30 s12 w30px"><s:message code="func.height"/></span>
                        <input type="text" class="fr sb-input w50px tc" id="cellH" maxlength="4">
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <%-- //위치 --%>

            <%-- 상세 폰트 설정 Start --%>
            <div id="divProdNavWrap" class="prodNavWrap t2">
                <div class="keyStyleWrap">
                    <div id="fontStyle" class="w100">
                        <span class="s12 lh30 bk">
                            <s:message code="tableAttr.font" />
                        </span>
                        <div class="sb-select w100 mb5">
                        	<%-- 폰트종류 --%>
                        	<div id="fontFamily"></div>
                        </div>
                        <div class="sb-select w100 mb5">
                            <%-- 폰트컬러 --%>
                            <div id="fontColor"></div>
                        </div>
                        <div class="sb-select w100 mb5">
                            <%-- 폰트사이즈 --%>
                            <div id="fontSize"></div>
                        </div>
                        <div class="sb-select w100 mb5">
                        	<span class="align">
								<a href="#" class="btn_bold" id="btnBold"></a>
								<a href="#" class="btn_italic" id="btnItalic"></a>
								<a href="#" class="btn_underline" id="btnUnderline"></a>
							</span>
						</div>
                    </div>
                </div>
            </div>
            <div class="touch-box mt10" id="textAlign" style="display:none;">
                <span class="bk lh30 s12"><s:message code="tableAttr.align"/></span>
                <div class="align">
					<a href="#" class="btn_left" id="btnLeft"></a>
					<a href="#" class="btn_center" id="btnCenter"></a>
					<a href="#" class="btn_right" id="btnRight"></a>
				</div>
				<div class="align">
					<a href="#" class="btn_top" id="btnTop"></a>
					<a href="#" class="btn_middle" id="btnMiddle"></a>
					<a href="#" class="btn_bottom" id="btnBottom"></a>
				</div>
                <div class="clearfix"></div>
            </div>
            <%-- //상세 폰트 설정 end --%>
        </div>
        <div class="clearfix"></div>
        <%--//right--%>
    </div>

</div>
<%--//서브컨텐츠--%>

<script>
    $(document).ready(function() {

    });
</script>

<script>
    (function() {
        var tableAttrInit = TableAttr.prototype.init;
        TableAttr.prototype.init = function() {
            tableAttrInit.apply(this, arguments);
        };

        <%--그래픽 영역에 삭제(del키) 이벤트를 활성화 화기 위해 blur에 focus 처리--%>
           $("#content").blur(function() {

               $(this).focus();
           });

        if (!mxClient.isBrowserSupported()) {
            // Displays an error message if the browser is not supported.
            mxUtils.error('Browser is not supported!', 200, false);
        } else {
            // Adds required resources (disables loading of fallback properties, this can only
            // be used if we know that all keys are defined in the language specific file)
            mxResources.loadDefaultBundle = false;
            var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage) ||
                mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

            // Fixes possible asynchronous requests
            mxUtils.getAll(
                [bundle, STYLE_PATH + '/tableAttr.xml'],
                function(xhr) {
                    // Adds bundle text to resources
                    mxResources.parse(xhr[0].getText());

                    // Configures the default graph theme
                    var themes = {};
                    themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

                    // Main
                    var tableattr = new TableAttr(themes);
                },
                function() {
                    document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
                });
        }

        var app = agrid.getApp();

        /* 테스트 페이지 */
        app.controller('tbAttrCtrl', ['$scope', '$http', function($scope, $http) {
            // 상위 객체 상속 : T/F 는 picker
            angular.extend(this, new RootController('tbLayoutCtrl', $scope, $http, true));

        	// 테이블형태 콤보박스
            $scope._getComboDataQuery('035', 'tblTypeFgComboData');

            $scope.combobox1 = ['1층', '2층', '테라스'];
            $scope.combobox2 = [{"name":"없음","value":"0"},{"name":"2","value":"2"},{"name":"4","value":"4"}];
            $scope.combobox4 = ['테이블명', '구분1', '구분2'];

        }]);
    })();

</script>