<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #contentLayout div {
        font-family: inherit;
        font-weight: inherit;
        font-size: inherit;
        color: inherit;
    }

    #contentTblAttr div {
        font-family: inherit;
        font-weight: inherit;
        font-size: inherit;
        color: inherit;
    }

    .wj-topleft div.wj-row:first-child .wj-cell {
        background: #e8e8e8 center center no-repeat;
        background-size: 21px 20px;
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
    window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/upload';
    window.STENCIL_PATH = window.STENCIL_PATH || '/resource/graph/stencils';
    //window.CONFIG_PATH = window.CONFIG_PATH || '/resource/graph/config';

    window.TABLELAYOUT_OPEN_URL = window.TABLELAYOUT_OPEN_URL || '/base/store/tableLayout/tableNewLayout/view.sb';
    window.TABLELAYOUT_SAVE_URL = window.TABLELAYOUT_SAVE_URL || '/base/store/tableLayout/tableNewLayout/saveLayout.sb';
    window.TABLELAYOUT_UPLOADIMAGE_URL = window.TABLELAYOUT_UPLOADIMAGE_URL || '/base/store/tableLayout/tableNewLayout/uploadImageFile.sb';
    window.TABLELAYOUT_UPLOAD_PATH = window.TABLELAYOUT_UPLOAD_PATH || '/resource/images/tableLayout';

    window.TABLEATTR_OPEN_URL = window.TABLEATTR_OPEN_URL || '/base/store/tableLayout/tableNewLayout/attrView.sb';
    window.TABLEATTR_SAVE_URL = window.TABLEATTR_SAVE_URL || '/base/store/tableLayout/tableNewLayout/saveTblAttr.sb';

    window.TABLEATTR_NUM_OPEN_URL = window.TABLEATTR_NUM_OPEN_URL || '/base/store/tableLayout/tableNewLayout/attrNumView.sb';
    window.TABLEATTR_NUM_SAVE_URL = window.TABLEATTR_NUM_SAVE_URL || '/base/store/tableLayout/tableNewLayout/saveTblNumAttr.sb';

    window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
    window.mxLanguage = window.mxLanguage || urlParams['lang'];
    window.mxLanguages = window.mxLanguages || ['ko'];

    //Custom varible
    window.TABLE_ATTR_ITEMS = ${tableAttrs};
    window.TABLE_ATTR_DEFAULTS = ${defaults};
</script>

<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.js" charset="utf-8"></script>
<!--script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js"></script-->
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TableLayoutNew.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TableAttrNew.js" charset="utf-8"></script>

<%--서브컨텐츠--%>
<div class="subCon2" ng-controller="tbLayoutCtrl">

    <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
    <div class="posWrap t2">
    
    	<div class="tbLayoutWrap">
    		<div class="t-btn-wrap">
	          <div class="flr">
	              <%-- 버튼 --%>
	              <div class="updownSet oh mb5 fl">
	              	   <button class="btn_skyblue fl ml5" id="btnInitLayout"><s:message code="cmm.init" /></button>
	                  <button class="btn_skyblue fl ml5" id="btnfloorConfig"><s:message code="tableLayout.tableLayout" /></button>
				    	<button class="btn_skyblue fl ml5" id="btnTblAddLayer"><%-- <s:message code="cmm.add" /> --%>테이블추가</button>
	                  <button class="btn_skyblue fl ml5" id="btnSaveLayout"><s:message code="cmm.save" /></button>
	              </div>
	          </div>
	         </div>
	          <div class="clearfix"></div>        
        <%-- left --%>
        <div class="table-l">
    
	      <%--그룹--%>
	      <div class="posBtnWrap">


	          <div class="frGroupWrap" id="divLayers">
	              <%--<span><a href="#" class="on">2층</a></span> --%>
	          </div>
	      </div>
	      <%--//그룹--%>     
	      
			<%--구성요소--%>
			<div class="fl w110px" style="display: none;">
				<h2 class="h2_tit2"><s:message code="tableLayout.component" /></h2>
				<div class="cfgWrap" style="height:495px">
					<ul class="tableImg" id="divCompos">
			    	<%--<li><a href="#"><img src="img/img_table2.png" alt="" /></a></li>--%>
			    </ul>
			  </div>
			</div>
			<%--//구성요소--%>
	
			<%-- 미리보기 --%>
			<div style="width:850px;">
				<div class="prev1 patternBg t2" id="contentLayout" tabindex="-1" style="width:886px; height:636px">
					<%--점 패턴 background classname : "patternBg"--%>
					<%--510 * 510 pixel 입니다.--%>
			  </div>
			</div>
			<%-- //미리보기 --%>
        </div>
        <%-- //left --%>

			<%--right--%>
			<div class="table-r">					
			
				<%--구성요소--%>
				<div class="cfgWrap pd10" style="height:716px">
					<h2 class="h2_tit2 mt10 mb20" style="padding-bottom:10px; border-bottom:1px solid #ddd"><s:message code="tableLayout.component" /></h2>
					<%-- 테이블명 --%>
					<div class="touch-box">
					    <div class="bk lh30 s12"><s:message code="todayDtl.dtl.tblNm" /></div>
							<div class="mb5">
					    	<input type="text" class="sb-input" style="width:100%" id="tableName">
	
					    </div>
					</div>						
					<%-- 테이블자리수/유형 --%>
					<div class="touch-box mt10">
				    <%-- 테이블자리수 --%>
						<div class="fl w45">
				    	<span class="bk lh30 s12"><s:message code="tableAttr.tableSeatCnt" /></span>
							<%-- 셀렉트 --%>
							<div class="sb-select">
				    		<span class="txtIn">
									<wj-combo-box
										id="tblSeatCntComboBox"
										ng-hide="true"
										text="_tblSeatCntComboBox"
										items-source="combobox2"
										display-member-path="name"
										selected-value-path="value"
										is-editable="false">
									</wj-combo-box>
								</span>
				    	</div>
						</div>

						<%-- 테이블유형 --%>
						<div class="fr w45">
				    	<span class="bk lh30 s12"><s:message code="tableAttr.tableType" /></span>
							<%-- 셀렉트 --%>
							<div class="sb-select">
							  <span class="txtIn">
									<wj-combo-box
										id="tblTypeFgComboBox"
										ng-hide="true"
										text="_tblTypeFg"
										items-source="_getComboData('tblTypeFgComboData')"
										display-member-path="name"
										selected-value-path="value"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</div>
					</div>
					<%-- //테이블자리수/유형 --%>
					
					<%-- 위치 --%>
					<div class="touch-box mt10">
					  <span class="bk lh30 s12"><s:message code="posFunc.pos"/></span>
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
						
						<div>
					    <div class="oh mt10">
					      <span class="s12 fl lh30 bk mr10" style="display: none;"><s:message code="tableLayout.bgColor" /></span>
								<div class="sb-select fl txtIn w100 mb10" style="display: none;">
					    		<div id="bgColor"></div>
					    	</div>
								<span class="s12 fl lh30 bk mr10"><s:message code="tableLayout.bgImg" /></span>
								<div class="txtIn fl bgfile w100" style="position:relative">
									<input type="file" id="btnBgFile" style="width:100% !important" accept="image/x-png, image/gif, image/jpeg" />
									<a href="#" class="btn_grayS" id="btnDelBgImg" style="position:absolute; top:-24px; right:0; padding:0 10px; line-height:21px; font-size:12px">
										<s:message code="cmm.delete" />
									</a>
				    		</div>
							</div>
							
							<div class="oh mt10" id="cellImage" style="display: none;">
								<span class="s12 fl lh30 bk mr10"><s:message code="tableLayout.cellImg" /></span>
								<div class="txtIn fl bgfile w100" style="position:relative">
									<input type="file" id="btnCellBgFile" style="width:100% !important" accept="image/x-png, image/gif, image/jpeg" />
									<a href="#" class="btn_grayS" id="btnDelCellImg" style="position:absolute; top:-24px; right:0; padding:0 10px; line-height:21px; font-size:12px">
										<s:message code="cmm.delete" />
									</a>
							  </div>
							</div>
					
							<div class="mt10" id="tableAlign" style="display:none; position:relative; height:70px">
								<span class="s12 lh30 bk"><s:message code="tableLayout.align" /></span>
								<span class="align" style="position:absolute; top:30px; left:0">
									<a href="#" class="btn_objleft" id="btnLeft"></a>
									<a href="#" class="btn_objcenter" id="btnCenter"></a>
									<a href="#" class="btn_objright" id="btnRight"></a>
								</span>
								<span class="align" style="position:absolute; top:30px; right:0">
									<a href="#" class="btn_objtop" id="btnTop"></a>
									<a href="#" class="btn_objmiddle" id="btnMiddle"></a>
									<a href="#" class="btn_objbottom" id="btnBottom"></a>
								</span>
							</div>
					
					  </div>
					  <div class="clearfix"></div>
					</div>
					<button class="btn_skyblue w100 mt20" id="btnTblAttConfig">테이블속성 설정</button>
					<input type="hidden" id="tableSelectStoreCd" value="${sessionInfo.storeCd}"/>
				</div>
				<%--//구성요소--%>
			</div>
			<%--//right--%>
	  	</div>
	  	
    </div>
    <%--// posWrap end --%>

</div>
<%--//서브컨텐츠--%>

<%--layer:For Center screen--%>
<div class="fullDimmed floorLayer" id="floorMask" style="display: none;"></div>
<div class="layer floorLayer" id="floorLayer" style="display: none;">
    <div class="layer_inner">
        <%--layerContent--%>
        <div class="title w400px">
            <p class="tit">
                <s:message code="tableLayout.tableLayout" />
            </p>
            <a href="#" class="btn_close _btnClose"></a>
            <div class="con sc" style="height:300px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30"><s:message code="tableLayout.tableLayout" /></span>
                    <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add" /></button>
                </div>
                <%--위즈모 테이블--%>
                <div>
                    <div id="floorGrid" class="mt10"></div>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <div class="btnSet">
                <span>
                	<a href="#" class="btn_blue _btnClose" id="btnFloorApply"><s:message code="cmm.apply" /></a>
                </span>
                <span>
                	<a href="#" class="btn_gray _btnClose"><s:message code="cmm.close" /></a>
                </span>
            </div>
        </div>
        <%--//layerContent--%>
    </div>
</div>
<%--//layer:For Center screen--%>

<%--layer: 테이블기능 start --%>
<div class="fullDimmed tableAddLayer" id="tableAddMask"  style="display: none;"></div>
<div class="layer tableAddLayer" id="tableAddLayer" style="display: none;">
	<div class="layer_inner">
	<%--layerContent--%>
		<div class="title" style="width:450px; padding-bottom:20px">
			<p class="tit"><s:message code="tableLayout.tableAdd" /><span class="s12 gr"> (<s:message code="tableLayout.tableAddCaution" />.)</span></p>
			<a href="#" class="btn_close _btnClose"></a>
			<div class="con sc">
				<div class="tblBr mb20" >
					<table class="tblType01" style="border-top:1px solid #c5c5c5" >
						<colgroup>
					  		<col class="w30" />
					  		<col class="w70" />
					  	</colgroup>
					  	<tbody>
					  		<tr>
					  			<th class="tc"><s:message code="tableLayout.amount" /></th>
					  			<td>
					  				<span class="bk lh30 s12 w50px"></span>
					  				<input type="text" class="sb-input tc" id="tblAddAmount" maxlength="4" style="width:80px">
					  			</td>
					  			<!-- <td>
					  				<div class="sb-select fl w130px">
					  					<div id="tbAmount"></div>
					  				</div>
					  			</td> -->
					  		</tr>
					  		<tr style="display: none;">
					  			<th class="tc"><s:message code="tableLayout.size" /></th>
					  			<td>
					  				<span class="bk lh30 s12 w50px"><s:message code="func.width" /></span>
					  				<input type="text" class="sb-input tc mr10" id="tblAddWidth" maxlength="4" style="width:80px">
					  				<span class="bk lh30 s12 w50px"><s:message code="func.height" /></span>
					  				<input type="text" class="sb-input tc" id="tblAddHeight" maxlength="4" style="width:80px">
					  			</td>
					  		</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btnSet" style="padding-top:20px">
				<span><a href="#" class="btn_blue _btnClose" id="btnAddTbl">추가</a></span>
				<span><a href="#" class="btn_gray _btnClose"><s:message code="cmm.close"/></a></span>
			</div>
		</div>
		<%--//layerContent--%>
	</div>
</div>
<%--//layer: 테이블기능 end --%>

<%--layer:For Center screen--%>
<div class="fullDimmed tblAttrLayer" id="tblAttrMask" style="display: none;"></div>
<div class="layer tblAttrLayer" id="tblAttrLayer" style="display: none;">
    <div class="layer_inner">

        <%--layerContent--%>
        <div class="title" style="width:1010px">
            <p class="tit" id="tblAttrTitle" style="padding-left:20px">
                <s:message code="tableAttr.tableAttr" />
            </p>
            <a href="#" class="btn_close _btnClose"></a>

            <div class="pd20">
                <%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>

                <%--left--%>
                <div class="fl" style="width:218px">

                    <%--구성요소--%>
                    <h2 class="h2_tit2">
                        <s:message code="tableAttr.component" />
                    </h2>
                    <div class="cfgWrap">
                        <%--위즈모 테이블--%>
                        <div id="tblAttrGrid"></div>
                        <%--//위즈모 테이블--%>
                    </div>
                    <%--//구성요소--%>
                </div>
                <%--//left--%>

                <%--center--%>
                <div class="fl" style="width:512px">

                    <%--미리보기--%>
                    <div class="ml10" style="margin-top:26px">
                        <%-- <h2 class="h2_tit2"><s:message code="tableAttr.preview" /></h2> --%>
                        <div class="prev1 patternBg" id="contentTblAttr" tabindex="-1">
                            <%--점 패턴 background classname : "patternBg"--%>
                            <%--510 * 510 pixel 입니다.--%>
                        </div>
                    </div>
                    <%--//미리보기--%>

                    <%--설정--%>
                    <div class="ml10" style="width:500px; padding:38px 30px 35px; background-color:#f4f4f4">
                        <div class="btn_int tc" id="format">
                            <button class="btn_blue" style="display: none;" id="btnChangeAttr"><s:message code="cmm.init" /></button>
                            <button class="btn_gray" id="btnInitAttr"><s:message code="cmm.init" /></button>
                            <button class="btn_blue"  id="btnSaveAttr" ><s:message code="cmm.save" /></button>
                            <button class="btn_blue" id="btnSaveTypeAttr"><s:message code="cmm.typeSave" /></button>
                        </div>
                    </div>
                    <%--//설정--%>
                </div>
                <%--//center--%>

                <%-- right --%>
                <div class="fl tch-inner">

                	<span class="bk lh30 s12"><s:message code="tableAttr.preview" /></span>
                	<%-- 미리보기 start --%>
                	<div class="tablethum mb20">
                		<div class="thumarea">

                			<%-- 미리보기 영역 --%>
                			<div id="previewTblAttr"></div>

                		</div>
                	</div>

                	<%-- 미리보기 end --%>

                    <%-- 상세 폰트 설정 Start --%>
                    <div id="divProdNavWrap" class="prodNavWrap t2">
                        <div class="keyStyleWrap">
                            <div id="fontStyle" class="w100">
                                <span class="s12 lh30 bk">
                                    <s:message code="tableAttr.font" />
                                </span>

                                <%-- 폰트종류 --%>
                                <div class="sb-select w100 mb5">
                                    <div id="fontFamily"></div>
                                </div>

                                <%-- 폰트컬러 --%>
                                <div class="sb-select w100 mb5">
                                    <div id="fontColor"></div>
                                </div>

                                <%-- 폰트사이즈 --%>
                                <div class="sb-select w100 mb5">
                                    <div id="fontSize"></div>
                                </div>

                                <div class="sb-select w100 mb5">
                                    <span class="align">
                                        <a href="#" class="btn_bold" id="btnBold" title="굵게"></a>
                                        <a href="#" class="btn_italic" id="btnItalic" title="기울임꼴"></a>
                                        <a href="#" class="btn_underline" id="btnUnderline" title="밑줄"></a>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="touch-box mt10" id="textAlign" style="display:none; position:relative">
                        <span class="bk lh30 s12">
                            <s:message code="tableAttr.align" /></span>
                        <div class="align" style="position:absolute; top:30px; left:0;">
                            <a href="#" class="btn_left" id="btnLeft" title="왼쪽 맞춤"></a>
                            <a href="#" class="btn_center" id="btnCenter" title="가운데 맞춤"></a>
                            <a href="#" class="btn_right" id="btnRight" title="오른쪽 맞춤"></a>
                        </div>
                        <div class="align" style="position:absolute; top:30px; right:0;">
                            <a href="#" class="btn_top" id="btnTop" title="위쪽 맞춤"></a>
                            <a href="#" class="btn_middle" id="btnMiddle" title="가운데 맞춤"></a>
                            <a href="#" class="btn_bottom" id="btnBottom" title="아래쪽 맞춤"></a>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <%-- //상세 폰트 설정 end --%>

                </div>
                <div class="clearfix"></div>
                <%--//right --%>
            </div>
        </div>

    </div>
    <%--//layerContent--%>
</div>
<%--//layer:For Center screen--%>

<script>
    $(document).ready(function() {

        $("#btnfloorConfig").click(function(e) {
            $("div.floorLayer").show();
            //wijmo.grid.FlexGrid.refreshAll();
        });

        $("#btnTblAddLayer").click(function(e) {
            $("div.tableAddLayer").show();
            //wijmo.grid.FlexGrid.refreshAll();
        });

        $("#btnTblAttConfig").click(function(e) {
        	$("#btnChangeAttr").trigger("click");
        	$("div.tblAttrLayer").show();
        	//wijmo.grid.FlexGrid.refreshAll();
      	});

        $("._btnClose").click(function(e) {
            $("div.floorLayer").hide();
            $("div.tableAddLayer").hide();
            $("div.tblAttrLayer").hide();
        });

    	var tblTypeFgComboBox = wijmo.Control.getControl("#tblTypeFgComboBox");
        tblTypeFgComboBox.isDroppedDownChanged.addHandler(function(s, e) {

        	var cells = currentGraph.getSelectionCells();
        	var model = currentGraph.getModel();

        	if (currentCell != null) {
        		for (var i = 0; i < cells.length; i++) {
                    if (model.isVertex(cells[i])) {
                    	var cellStyle = cells[i].style;
                    	var styleStr = setCellStyle(cellStyle, "tblTypeFg" , tblTypeFgComboBox.selectedValue);
                    	cells[i].setStyle(styleStr);
                    }
                }
        	}
        });

        var tblSeatCntComboBox = wijmo.Control.getControl("#tblSeatCntComboBox");
        tblSeatCntComboBox.isDroppedDownChanged.addHandler(function(s, e) {

        	var cells = currentGraph.getSelectionCells();
        	var model = currentGraph.getModel();

        	if (currentCell != null) {
        		for (var i = 0; i < cells.length; i++) {
                    if (model.isVertex(cells[i])) {
                    	var cellStyle = cells[i].style;
                    	var styleStr = setCellStyle(cellStyle, "tblSeatCnt" , tblSeatCntComboBox.selectedValue);
                    	cells[i].setStyle(styleStr);
                    }
                }
        	}
        });
    });
</script>

<script>
    (function() {

        var tableLayoutInit = TableLayout.prototype.init;
        TableLayout.prototype.init = function() {
            tableLayoutInit.apply(this, arguments);
        };

        var tableAttrInit = TableAttr.prototype.init;
        TableAttr.prototype.init = function() {
            tableAttrInit.apply(this, arguments);
        };

        if (!mxClient.isBrowserSupported()) {
            // Displays an error message if the browser is not supported.
            mxUtils.error('Browser is not supported!', 200, false);
        } else {
            // Adds required resources (disables loading of fallback properties, this can only
            // be used if we know that all keys are defined in the language specific file)
            mxResources.loadDefaultBundle = false;
            var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage) || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

            // Fixes possible asynchronous requests
            mxUtils.getAll(
                [bundle, STYLE_PATH + '/tablelayout.xml'],
                function(xhr) {
                    // Adds bundle text to resources
                    mxResources.parse(xhr[0].getText());
                    // Configures the default graph theme
                    var themes = {};
                    themes[GraphLayout.prototype.defaultThemeName] = xhr[1].getDocumentElement();

                    // Main
                    var tablelayout = new TableLayout(themes);
                },
                function() {
                    document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
                });
        }

        <%--그래픽 영역에 삭제(del키) 이벤트를 활성화 화기 위해 blur에 focus 처리--%>
        /* $("#contentLayout").blur(function() {
        	$(this).focus();
        }); */

        <%--그래픽 영역에 삭제(del키) 이벤트를 활성화 화기 위해 blur에 focus 처리--%>
        /* $("#contentTblAttr").blur(function() {
        	$(this).focus();
        }); */

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
                    themes[GraphAttr.prototype.defaultThemeName] = xhr[1].getDocumentElement();

                    // Main
                    var tableattr = new TableAttr(themes);
                },
                function() {
                    document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
                });
        }

    })();
    <%--기존 페이지 스크립트 및 폰트 / 채우기등 추가--%>

    var app = agrid.getApp();

    /* 테스트 페이지 */
    app.controller('tbLayoutCtrl', ['$scope', '$http', function($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('tbLayoutCtrl', $scope, $http, true));

    	// 테이블형태 콤보박스
        $scope._getComboDataQuery('035', 'tblTypeFgComboData');

        $scope.combobox1 = ['1층', '2층', '테라스'];
        $scope.combobox2 = [{"name":"없음","value":"0"},{"name":"2","value":"2"},{"name":"4","value":"4"}];
        $scope.combobox4 = ['테이블명', '구분1', '구분2'];

    }]);

</script>