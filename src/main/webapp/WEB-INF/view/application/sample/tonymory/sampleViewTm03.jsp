<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>

.tonyAdd {position:absolute; top:0; left:-220px; bottom:0; right:220px; width:220px; height:100%; z-index:99; background-color:#2e333f}

.tonyMainlogo {position:fixed; top:0; width:200px; height:49px; z-index:100; background-color:#2e333f}
.tonyMainlogo span {display:block; text-align:center}

.tonySnb {position:fixed; top:101px; width:220px; z-index:100; background-color:#2e333f}
.tonySnb ul {padding:20px} 
.tonySnb ul li {}
.tonySnb ul li a {display:block; width:100%; height:45px; font-size:14px; }
.tonySnb ul li a:hover,.tonySnb ul li a:active {background:url(/resource/solbipos/css/img/menu_bullet.png) 170px 12px no-repeat; background-size:6px; color:#fff}
.tonySnb ul li a .ico {display:inline-block; position:relative; top:6px; width:20px; height:22px; margin-right:15px; background:url(/resource/solbipos/css/img/menu.png) 0 0 no-repeat}
.tonySnb ul li a.menu1 .ico {background-position:0 0}
.tonySnb ul li a.menu2 .ico {background-position:-60px 0}
.tonySnb ul li a.menu3 .ico {background-position:-120px 0}
.tonySnb ul li a.menu4 .ico {background-position:-160px 0}
.tonySnb ul li a.menu5 .ico {background-position:-100px 0}
.tonySnb ul li a.menu6 .ico {background-position:-80px 0}
.tonySnb ul li a.menu7 .ico {background-position:-140px 0}
.tonySnb ul li a.menu8 .ico {background-position:-200px 0}

.tonytab {position:fixed; top:50px; width:220px; border-top:1px solid #3e4452; border-bottom:1px solid #3e4452}
.tonytab span {display:block; float:left; width:110px; height:49px; line-height:49px; text-align:center; font-size:14px; color:#888fa4}
.tonytab span:first-child {border-right:1px solid #3e4452; background-color:#212633}
.tonytab span em {display:inline-block; width:40px; padding-bottom:16px; border-bottom:2px solid #fff; font-size:14px; color:#fff}

.menuControl {display:none}
.userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}
.type_Green .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#00ba8b}
.type_Green .userInfo.t2 .userId {border-left-color:#00a279}
.type_Orange .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}
.type_Orange .userInfo.t2 .userId {border-left-color:#ee8418}
.type_Blue .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#1e88e5}
.type_Blue .userInfo.t2 .userId {border-left-color:#127bd8}
.type_Purple .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ae63ff}
.type_Purple .userInfo.t2 .userId {border-left-color:#8c38e6}

.fixedMenu.t2 {position:fixed; top:50px; left:220px; right:0; z-index:97; box-shadow:0 0 0 0 #ff9d39}
.fixedMenu.t2 ul li a {padding:0 35px 0 10px !important}
.fixedMenu.t2 ul li a.btn_close {padding-right:0 !important; top:16px !important; right:10px !important}

.center-box {position:relative; height:500px}
.c-arrow-right {position:absolute; bottom:130px; left:30px; width:30px; height:30px; background-color:#eee; cursor:pointer}
.c-arrow-left {position:absolute; bottom:80px; left:30px;width:30px; height:30px; background-color:#eee; cursor:pointer}
.c-arrow-left span {display:inline-block;  margin:4px 0 0 5px}
.c-arrow-right span {display:inline-block; margin:4px 0 0 7px}
.c-arrow-right:hover,.c-arrow-left:hover {background-color:#666}
.c-arrow-right:hover span,.c-arrow-left:hover span {color:#fff}

#gLeft .wj-cells,#gLeft .wj-rowheaders,#gRight .wj-cells,#gRight .wj-rowheaders {display:none}

</style>

		<div class="subCon" ng-controller="spTonyCtrl">

			<div class="tonyAdd">
				<a href="#" class="tonyMainlogo">
					<span><img src="/resource/solbipos/css/img/test/tony_main_logo.png" alt="" /></span>
				</a>

				<div class="tonytab">
					<span><em>전체</em></span>
					<span>즐겨찾기</span>
				</div>

				<div class="tonySnb">
					<ul>
						<li><a href="#" class="menu1"><i class="ico"></i>운영관리</a></li>
						<li><a href="#" class="menu2"><i class="ico"></i>마스터관리</a></li>
						<li><a href="#" class="menu3"><i class="ico"></i>매출분석</a></li>
						<li><a href="#" class="menu4"><i class="ico"></i>수주관리</a></li>
						<li><a href="#" class="menu5"><i class="ico"></i>매입관리</a></li>
						<li><a href="#" class="menu6"><i class="ico"></i>재고관리</a></li>
						<li><a href="#" class="menu7"><i class="ico"></i>도우미관리</a></li>
						<li><a href="#" class="menu8"><i class="ico"></i>커뮤니케이션</a></li>
					</ul>
				</div>
			</div>

			<div class="userInfo t2">
				<a href="#" class="userId">
					<span>토니모리 관리자</span>
				</a>

				<div class="userLayer userLayer2" style="display:none">
					<p>
						<span>토니모리 관리자</span>
						<span> 
							<em>토니모리</em> 
						</span> 
						<span>개발중</span>
					</p>
					<ul>
						<li><a href="#">내 정보 변경</a></li>
						<li><a href="#" id="pwchg">비밀번호 변경</a></li>
						<li><a href="#">로그아웃</a></li>
					</ul>
				</div>
			</div>
			
			<!-- 고정메뉴 -->
			<div class="fixedMenu t2">
				<nav>
					<ul>
						<li>			
							<a href="/sample/tonymory/sample41.sb">메인화면</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample51.sb">제품등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample42.sb" class="on">제품별수주현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample43.sb">주문등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample44.sb">판촉마일리지 관리</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample45.sb">판촉마일리지 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample46.sb">판촉마일리지 내역조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample47.sb">당일 매출 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample48.sb">매출 업로드 등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample49.sb">매출 업로드 확정</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample50.sb">일별-제품판매실적조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
					</ul>
				</nav>
				<!-- 고정메뉴 있는경우 -->
			</div>
			<!-- //고정메뉴 -->


			<div class="searchBar flddUnfld">
				<a href="#" class="open fl">제품별 수주현황</a>
				<!-- 조회 -->
				<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
					<button class="btn_blue" id="" ng-click="">조회</button>
					<button class="btn_gray" id="" ng-click="">엑셀</button>
					<button class="btn_gray" id="" ng-click="">닫기</button>
				</div>
			</div>

			<table class="searchTbl">
				<colgroup>
				<col class="w15" />
				<col class="w35" />
				<col class="w15" />
				<col class="w35" />
				</colgroup>
				<tbody>
					<tr>
						<!-- 주문일자 -->
						<th>
							<div class="sb-select">
								<span class="txtIn w110px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>					
						</th>
						<td>
							<div class="sb-select fl">
								<span class="txtIn w110px">
									<wj-input-date
										value="startDate"
										ng-model="startDate"
										control="startDateCombo"
										min="2000-01-01"
										max="2099-12-31"
										initialized="_initDateBox(s)">
									</wj-input-date>
								</span>
								<span class="rg">~</span>
								<span class="txtIn w110px">
									<wj-input-date
										value="startDate"
										ng-model="startDate"
										control="startDateCombo"
										min="2000-01-01"
										max="2099-12-31"
										initialized="_initDateBox(s)">
									</wj-input-date>
								</span>
							</div>
						</td>
						<!-- 매장선택 -->
						<th>매장선택</th>
						<td>
							<span class="txtIn fl">
								<input type="text" id="" name="" class="sb-input w100px"/>
								<input type="text" id="stVal" name="" value=""class="sb-input w50px tc"/> 개
							</span>
							<span class="fl">
								<a href="#" class="btn_grayS ml5" id="btnLy1">추가</a>
								<a href="#" class="btn_grayS" id="btnDel1">지움</a>
							</span>
						</td>						
					</tr>
					<tr>
						<!-- 상품분류 -->
						<th>상품분류</th>
						<td>
							<div class="sb-select">
								<span class="txtIn w90px">
									<wj-combo-box
										items-source="combobox2"
										is-editable="false">
									</wj-combo-box>
								</span>
								<span class="txtIn w90px">
									<wj-combo-box
										items-source="combobox2"
										is-editable="false">
									</wj-combo-box>
								</span>
								<span class="txtIn w90px">
									<wj-combo-box
										items-source="combobox2"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</td>
						<!-- 상품코드/명 -->
						<th>상품코드/명</th>
						<td>
							<span class="txtIn fl">
								<input type="text" id="" name="" ng-model="" class="sb-input w100px"/>
								<input type="text" id="pdVal" name="" ng-model="" class="sb-input w50px tc"/> 개
							</span>
							<span class="fl">
								<a href="#" class="btn_grayS ml5" id="btnLy2">추가</a>
								<a href="#" class="btn_grayS" id="btnDel2">지움</a>
							</span>
						</td>
					</tr>
					<tr>
						<!-- 주문구분 -->
						<th>주문구분</th>
						<td>
							<div class="sb-select">
								<span class="txtIn w90px">
									<wj-combo-box
										items-source="combobox2"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</td>
						<th></th>
						<td></td>
					</tr>
				</tbody>
			</table>


			<div id="grid" class="w100">
				<div class="wj-gridWrap mt10" style="height:420px; overflow-y: hidden;">
				  <div class="row">
					<wj-flex-grid 
						id="flexGrid"
						sticky-headers="true"
						items-source="data">

						<wj-flex-grid-column header="id" binding="id"></wj-flex-grid-column>
						<wj-flex-grid-column header="str1" binding="str1"></wj-flex-grid-column>
						<wj-flex-grid-column header="str2" binding="str2"></wj-flex-grid-column>
						<wj-flex-grid-column header="num1" binding="num1"></wj-flex-grid-column>
						<wj-flex-grid-column header="num2" binding="num2"></wj-flex-grid-column>
						<wj-flex-grid-column header="num3" binding="num3"></wj-flex-grid-column>
						<wj-flex-grid-column header="num4" binding="num4"></wj-flex-grid-column>
						<wj-flex-grid-column header="num5" binding="num5"></wj-flex-grid-column>
						<wj-flex-grid-column header="num6" binding="num6"></wj-flex-grid-column>
						<wj-flex-grid-column header="num7" binding="num7"></wj-flex-grid-column>
						<wj-flex-grid-column header="num8" binding="num8"></wj-flex-grid-column>
					</wj-flex-grid>

				  </div>
				</div>
			  </div>


		</div>
		<!-- //subCon end-->

	</div>
	<!-- -->

</div>


<!--layer:매장조회 -->
<div class="fullDimmed storelayer" id="floorMask" style="display: none;"></div>
<div class="layer storelayer" id="storelayer" style="display: none;">
	<div class="layer_inner" ng-controller="spTonyCtrl2">
		
		<!--layerContent-->
		<div class="title" style="width:1000px; padding-bottom:20px">
			<p class="tit">매장조회</p>
			<a href="#" class="btn_close btnClose"></a>
			
			<!-- layer content inner -->
			<div class="con sc">
				
				<div class="mb10">
					<span class="lh30 bk">매장조회</span>
					<!-- 버튼 -->
					<div class="fr">
						<button class="btn_blue" id="lySch1">조회</button>
					</div>
					<div class="clearfix"></div>
				</div>

				<!-- left -->
				<div class="fl w45 mb30">

					<table class="searchTbl" style="width:100%; min-width:auto; border:1px solid #ddd">
						<colgroup>
						<col class="w20" />
						<col class="w30" />
						<col class="w20" />
						<col class="w30" />
						</colgroup>
						<tbody>
							<tr>
								<th>매장코드</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>
								<th>매장명</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>						
							</tr>
							<tr>
								<th>SAP코드</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>
								<th>상권</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>						
							</tr>
							<tr>
								<th>오픈구분</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>담당자</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>					
							</tr>
							<tr>
								<th>부서/팀</th>
								<td colspan="3">
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>										
									</div>
								</td>				
							</tr>
							<tr>
								<th>매장형태</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w100px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>재경매장형태</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>									
									</div>
								</td>									
							</tr>
						</tbody>
					</table>

					<div class="w100">
						<div class="wj-gridWrap mt10" style="display:block; height:350px">
							<wj-flex-grid
								id="gLeft"
								sticky-headers="true"
								items-source="data">
		
								<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
								<wj-flex-grid-column header="No" binding="id" width="50" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="매장번호" binding="str7" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="SAP코드" binding="str8" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="매장명" binding="str9" width="*"></wj-flex-grid-column>
							</wj-flex-grid>
						</div>
					</div>

				</div>				
				<!-- //left -->
				
				<!-- center -->
				<div class="center-box fl w10">
					<div class="c-arrow-right"><span>▶</span></div>
					<div class="c-arrow-left"><span>◀</span></div>
				</div>
				<!--//center-->
				
				<!-- right -->
				<div class="fl w45">				
					<p class="s16 bk mt10">선택매장</p>

					<div class="w100">
						<div class="wj-gridWrap mt10" style="display:block; height:565px">
							<wj-flex-grid
								id="gRight"
								sticky-headers="true"
								items-source="data1">
		
								<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
								<wj-flex-grid-column header="매장번호" binding="str7" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="SAP코드" binding="str8" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="매장명" binding="str9" width="*" align="center"></wj-flex-grid-column>
							</wj-flex-grid>
						</div>
					</div>

				</div>
				<div class="clearfix"></div>
				<!--//right-->
			
			</div>
			<!-- //layer content inner -->
			
			<div class="btnSet" style="padding-top:20px">
				<span><a href="#" class="btn_blue btnClose" id="">업로드</a></span>
				<span><a href="#" class="btn_gray btnClose">닫기</a></span>
			</div>
		</div>
		<!--//layerContent-->
			
	</div>
</div>
<!--//layer:매장조회 -->	

<!--layer:상품조회 -->
<div class="fullDimmed pdlayer" id="floorMask" style="display: none;"></div>
<div class="layer pdlayer" id="pdlayer" style="display: none;">
	<div class="layer_inner" ng-controller="spTonyCtrl2">
		
		<!--layerContent-->
		<div class="title" style="width:1000px; padding-bottom:20px">
			<p class="tit">상품조회</p>
			<a href="#" class="btn_close btnClose"></a>
			
			<!-- layer content inner -->
			<div class="con sc">
				
				<div class="mb10">
					<span class="lh30 bk">상품조회</span>
					<!-- 버튼 -->
					<div class="fr">
						<button class="btn_blue" id="lySch2">조회</button>
					</div>
					<div class="clearfix"></div>
				</div>

				<!-- left -->
				<div class="fl w45 mb30">

					<table class="searchTbl" style="width:100%; min-width:auto; border:1px solid #ddd">
						<colgroup>
						<col class="w20" />
						<col class="w30" />
						<col class="w20" />
						<col class="w30" />
						</colgroup>
						<tbody>
							<tr>
								<th>상품코드</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>
								<th>상품명</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>						
							</tr>
							<tr>
								<th>상품유형</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>라인유형</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>					
							</tr>
							<tr>
								<th>특정분류</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>바코드</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>					
							</tr>
							<tr>
								<th>상품분류</th>
								<td colspan="3">
									<div class="sb-select">
										<span class="txtIn w100px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
										<span class="txtIn w100px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
										<span class="txtIn w100px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
							</tr>
						</tbody>
					</table>

					<div class="w100">
						<div class="wj-gridWrap mt10" style="display:block; height:350px">
							<wj-flex-grid
								id="gLeft"
								sticky-headers="true"
								items-source="data">
		
								<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
								<wj-flex-grid-column header="No" binding="id" width="50" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="분류" binding="str4" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품코드" binding="str5" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품명" binding="str6" width="*" align="center"></wj-flex-grid-column>
							</wj-flex-grid>
						</div>
					</div>

				</div>				
				<!-- //left -->
				
				<!-- center -->
				<div class="center-box fl w10">
					<div class="c-arrow-right"><span>▶</span></div>
					<div class="c-arrow-left"><span>◀</span></div>
				</div>
				<!--//center-->
				
				<!-- right -->
				<div class="fl w45">				
					<table class="searchTbl" style="width:100%; min-width:auto; border:1px solid #ddd">
						<colgroup>
						<col class="w20" />
						<col class="w30" />
						<col class="w20" />
						<col class="w30" />
						</colgroup>
						<tbody>
							<tr>
								<th>상품기획 담당자</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>
								<th>디자인 담당자</th>
								<td>
									<input type="text" id="" name="" class="sb-input w100"/>
								</td>						
							</tr>
							<tr>
								<th>판매가능여부</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>잡화구분</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>					
							</tr>
							<tr>
								<th>판매가</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>
								<th>과세구분</th>
								<td>
									<div class="sb-select">
										<span class="txtIn w110px">
											<wj-combo-box
												items-source="combobox2"
												is-editable="false">
											</wj-combo-box>
										</span>
									</div>
								</td>					
							</tr>

						</tbody>
					</table>

					<p class="s16 bk mt10">선택상품</p>

					<div class="w100">
						<div class="wj-gridWrap mt10" style="display:block; height:350px">
							<wj-flex-grid
								id="gRight"
								sticky-headers="true"
								items-source="data1">
		
								<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품코드" binding="str5" width="*" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품명" binding="str6" width="*" align="center"></wj-flex-grid-column>
							</wj-flex-grid>
						</div>
					</div>


				</div>
				<div class="clearfix"></div>
				<!--//right-->
			
			</div>
			<!-- //layer content inner -->
			
			<div class="btnSet" style="padding-top:20px">
				<span><a href="#" class="btn_blue btnClose" id="">업로드</a></span>
				<span><a href="#" class="btn_gray btnClose">닫기</a></span>
			</div>
		</div>
		<!--//layerContent-->
			
	</div>
</div>
<!--//layer:상품조회 -->	


</body>

<script>

$(document).ready(function() {

	//layer1 open
	$("#btnLy1").click(function(e) {
		$("div.storelayer").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//layer1 close
	$(document).on('click', '.storelayer .btnClose', function() {
		$("div.storelayer").hide();
		$(".storelayer #gRight .wj-cells, .storelayer #gRight .wj-rowheaders, .storelayer #gLeft .wj-cells, .storelayer #gLeft .wj-rowheaders").hide();
		$('#stVal').val('3');
	});
	//layer1 search
	$("#lySch1").click(function() {
		$(".storelayer #gLeft .wj-cells,.storelayer #gLeft .wj-rowheaders").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//layer1 value delete
	$("#btnDel1").click(function() {
		$('#stVal').val('');
	});
	
	//right arrow btn
	$(".storelayer .c-arrow-right").click(function() {
		$(".storelayer #gRight .wj-cells,.storelayer #gRight .wj-rowheaders").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//left arrow btn
	$(".storelayer .c-arrow-left").click(function() {
		$(".storelayer #gRight .wj-cells,.storelayer #gRight .wj-rowheaders").hide();
		wijmo.grid.FlexGrid.refreshAll();
	});

	//layer2 open
	$("#btnLy2").click(function(e) {
		$("div.pdlayer").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//layer2 close
	$(document).on('click', '.pdlayer .btnClose', function() {
		$("div.pdlayer").hide();
		$(".pdlayer #gRight .wj-cells,.pdlayer #gRight .wj-rowheaders,.pdlayer #gLeft .wj-cells,.pdlayer #gLeft .wj-rowheaders").hide();
		$('#pdVal').val('3');
	});
	//layer2 search
	$("#lySch2").click(function() {
		$(".pdlayer #gLeft .wj-cells,.pdlayer #gLeft .wj-rowheaders").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//layer2 value delete
	$("#btnDel2").click(function() {
		$('#pdVal').val('');
	});

	//right arrow btn
	$(".pdlayer .c-arrow-right").click(function() {
		$(".pdlayer #gRight .wj-cells,.pdlayer #gRight .wj-rowheaders").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	//left arrow btn
	$(".pdlayer .c-arrow-left").click(function() {
		$(".pdlayer #gRight .wj-cells,.pdlayer #gRight .wj-rowheaders").hide();
		wijmo.grid.FlexGrid.refreshAll();
	});

	$(document).on('click', '.userId', function() {
		$("div.userLayer2").toggle();
	});

});


  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl', $scope, $http, true));

	$scope.combobox1 = ['주문일자', '주문자', '주문번호'];
	$scope.combobox2 = ['전체', '분류1', '분류2'];


	//1. 랜덤 데이터 생성
    var txt1 = 'B001009400,B00101022,B001105200,B001002200,B001007700,B002109000,B003002400'.split(','),
        txt2 = '버블트리오렌지바이탈바디클렌저,퍼퓸드뮤즈바디클렌저스파클링,바디위드여성청결제,펴퓸드바디그레이스크림샤워3,퍼퓸드바디클래식샤워젤3'.split(',')
    
	var data = [];
	for (var i = 0; i < 50; i++) {
		data.push({
			id: i,
			str1: txt1[i % txt1.length],
			str2: txt2[i % txt2.length],
			num1: Math.random() * 1000,
			num2: Math.random() * 100,
			num3: Math.random() * 1000,
			num4: Math.random() * 10000,
			num5: Math.random() * 100,
			num6: Math.random() * 1000,
			num7: Math.random() * 1000,
			num8: Math.random() * 100
		});
	}

	//2. 그리드 초기화 및 데이터 바인딩
	var grid = new wijmo.grid.FlexGrid('#flexGrid');
		grid.itemsSource = data;
		grid.columns[0].width = 50;
		grid.columns[1].width = 170;
		grid.columns[2].width = 170;
		grid.columns[3].width = 170;
		grid.columns[4].width = 170;
		grid.columns[5].width = 170;
		grid.columns[6].width = 170;
		grid.columns[7].width = 170;
		grid.columns[8].width = 170;
		grid.columns[9].width = 170;
		grid.columns[10].width = 170;

	//3. 다중 헤더 추가
	for (var i = 0; i < 1; i++) {
		var hr = new wijmo.grid.Row();
		grid.columnHeaders.rows.push(hr);
	}

	//Grid 전체에 대해서 병합(Merge) 허용
	grid.allowMerging = wijmo.grid.AllowMerging.All;

	// 컬럼 헤더에 데이터 추가 및 컬럼 헤더 병합
	for (var r = 0; r < grid.columnHeaders.rows.length; r++) {
		if (r != 1) {
			grid.columnHeaders.rows[r].allowMerging = true;
		}
		for (var c = 0; c < grid.columns.length; c++) {
			if (r == 0 && c == 0 || r == 1 && c == 0) {
				grid.columnHeaders.setCellData(r, c, "No");
			}
			else if ((r == 0 && c == 1) || r == 1 && c == 1) {
				grid.columnHeaders.setCellData(r, c, "상품코드");
			}
			else if ((r == 0 && c == 2) || r == 1 && c == 2) {
				grid.columnHeaders.setCellData(r, c, "상품명");
			}
			else if ((r == 0 && c == 3) || r == 1 && c == 3) {
				grid.columnHeaders.setCellData(r, c, "현재고");
			}
			else if ((r == 0 && c == 4) || r == 1 && c == 4) {
				grid.columnHeaders.setCellData(r, c, "입수");
			}
			else if ((r == 0 && c == 5) || r == 0 && c == 6) {
				grid.columnHeaders.setCellData(r, c, "출고예정수량/금액");
			}
			else if ((r == 1 && c == 5)) {
				grid.columnHeaders.setCellData(r, c, "수량");
			}
			else if ((r == 1 && c == 6)) {
				grid.columnHeaders.setCellData(r, c, "금액");
			}
			else if ((r == 0 && c == 7) || r == 0 && c == 8) {
				grid.columnHeaders.setCellData(r, c, "출고수량/금액");
			}
			else if ((r == 1 && c == 7)) {
				grid.columnHeaders.setCellData(r, c, "수량");
			}
			else if ((r == 1 && c == 8)) {
				grid.columnHeaders.setCellData(r, c, "금액");
			}
			else if ((r == 0 && c == 9) || r == 0 && c == 10) {
				grid.columnHeaders.setCellData(r, c, "반품출고수량/금액");
			}
			else if ((r == 1 && c == 9)) {
				grid.columnHeaders.setCellData(r, c, "수량");
			}
			else if ((r == 1 && c == 10)) {
				grid.columnHeaders.setCellData(r, c, "금액");
			}
			else {
				grid.columnHeaders.setCellData(r, c, "1 Header");
			}
			grid.columns[c].allowMerging = true;
			grid.columns[c].align = "center";
		}
	}

	// 해더의 vertical align 설정
	grid.formatItem.addHandler(function (s, e) {
		if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
			e.cell.innerHTML = '<div>' + e.cell.innerHTML + '</div>';
			wijmo.setCss(e.cell.children[0], {
				position: 'relative',
				top: '50%',
				transform: 'translateY(-50%)',
				fontSize:'12px',
				color:'#222',
				fontWeight:'bold'
			});
		}
	});

    // add data array to scope
	$scope.data = data;

}

]);

  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl2', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl2', $scope, $http, true));

	$scope.combobox1 = ['주문일자', '주문자', '주문번호'];
	$scope.combobox2 = ['전체', '분류1', '분류2'];


	// generate some random data
    var txt3 = '468,469,470,471,472,473,474,475,476,477,478,479,480'.split(','),
    	txt4 = '판매보조용품'.split(','),
		txt5 = 'TM00000871,TM00000880,TM00001131,ET15055400,ET15055700,ET15055590,TM00000871,TM00000268,TM00001354'.split(','),
		txt6 = 'VMD_POP크롬도금집게5개2,VMD_S/S 색조쇼핑백,VMD_S/S 해외 색조쇼핑팩,VMD_UV마스터3종진열렉,VMD_감사의달프라이스카드,VMD_겟잇틴트HD연출_로드'.split(',')
		txt7 = 'C05289,C07446,C05289,C07139,C05643,C05566,C05692,C07869,C07836,C07498'.split(',')
		txt8 = '1100039,1101727,1101579,1100041,1100043,1100033,1100037,1100070,1101749,1102072'.split(',')
		txt9 = '(광주충장로점),(구리역점),(꼬끼오점),(대구동성로점),(동대문점),(명동3호점),(명동3호점),(명동유네스코점점),(미)(남대문점),(미)(명동3호점)'.split(',')
    	data = [];
    for (var i = 0; i < 100; i++) {
        data.push({
            id: i,
            str3: txt3[i % txt3.length],
            str4: txt4[i % txt4.length],
            str5: txt5[i % txt5.length],
            str6: txt6[i % txt6.length],
            str7: txt7[i % txt7.length],
            str8: txt8[i % txt8.length],
            str9: txt9[i % txt9.length],
            chk: i % 0 == 0
        });
    }
	$scope.data = data;

    	data1 = [];
    for (var i = 0; i < 3; i++) {
        data1.push({
            id: i,
            str3: txt3[i % txt3.length],
            str4: txt4[i % txt4.length],
            str5: txt5[i % txt5.length],
			str6: txt6[i % txt6.length],
            str7: txt7[i % txt7.length],
            str8: txt8[i % txt8.length],
            str9: txt9[i % txt9.length],			
            chk: i % 0 == 0
        });
    }
	$scope.data1 = data1;

  }

  ]);
</script>   



