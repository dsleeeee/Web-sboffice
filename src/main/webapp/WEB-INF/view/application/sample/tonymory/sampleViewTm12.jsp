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

.searchTbl {width:100%; border-top:1px solid #ccc; min-width:auto}
.searchTbl th {border-right:1px solid #ddd}
.tblImg {}
.imgArea {text-align:center; margin:0 auto 20px}
.imgArea:after {display:block; clear:both; content:''}
.imgArea .left,.imgArea .right {display:inline-block; position:relative; top:110px; width:30px; height:30px; background-color:#666; cursor:pointer}
.imgArea .left span,.imgArea .right span {display:inline-block; margin-top:4px; color:#fff}
.imgArea .left:hover,.imgArea .right:hover {background-color:#222}
.imgArea .img {display:inline-block; width:50%; height:250px; border:1px solid #999}
.imgArea .img span {display:inline-block; position:relative; top:110px;}
.btnA .btn_gray {font-size:14px; padding:0 20px; line-height:24px}
.wj-TblWrapBr {min-height:400px}

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
							<a href="/sample/tonymory/sample51.sb" class="on">제품등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample42.sb">제품별수주현황</a>
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
				<a href="#" class="open fl">제품등록 <span class="s12">※ 거래선 혹은 이미지 변경시, 저장을 해야만 데이터가 반영됩니다.</span></a>
				<!-- 조회 -->
				<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
					<button class="btn_blue" id="" ng-click="">조회</button>
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
						<th>상품분류</th>
						<td>
							<div class="sb-select">
								<span class="txtIn w100px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
								<span class="txtIn w100px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
								<span class="txtIn w100px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</td>						
						<th>라인유형</th>
						<td>
							<div class="sb-select">
								<span class="txtIn w100px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</td>
					</tr>
					<tr>
						<th>바코드</th>
						<td>
							<input type="text" id="" name="" ng-model="" class="sb-input w100"/>
						</td>
						<th>상품명/코드</th>
						<td>
							<input type="text" id="" name="" ng-model="" class="sb-input w100"/>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="wj-TblWrap mt20">
				<!-- left -->
				<div class="w35 fl">

					<div class="wj-gridWrap mt10" style="height:400px; overflow-y: hidden;">
						<div class="row">
							<wj-flex-grid
								sticky-headers="true"
								items-source="data">

								<wj-flex-grid-column header="No" binding="id" width="50" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품코드" binding="str1" width="100" align="center"></wj-flex-grid-column>
								<wj-flex-grid-column header="상품명" binding="str2"  width="*"></wj-flex-grid-column>
								<wj-flex-grid-column header="판매가" binding="num1" width="*" align="right"></wj-flex-grid-column>
							</wj-flex-grid>
						</div>
					</div>

				</div>
			
				<!-- right -->
				<div class="w65 fr">

					<div class="wj-TblWrapBr mt10 ml10 pd20">			
						<div class="fl">
							<span class="bk lh30">상품상세</span>
						</div>

						<div class="fr mb10">
							<button id="" class="btn_skyblue mr5">수정</button>	
							<button id="" class="btn_skyblue">닫기</button>	
						</div>

						<div class="rightDtl">
							<table class="searchTbl">
								<colgroup>
								<col class="w15" />
								<col class="w35" />
								<col class="w15" />
								<col class="w35" />
								</colgroup>
								<tbody>
									<tr>
										<th colspan="2" rowspan="8">
											<div class="tblImg">
												<div class="imgArea">
													<div class="left"><span>◀</span></div>
													<div class="img"><span>이미지없음</span></div>
													<div class="right"><span>▶</span></div>
												</div>
											</div>
											<div class="btnA tc">
												<button class="btn_gray mr10">찾아보기</button>	
												<button class="btn_gray">삭제</button>												
											</div>
										</th>
										<th>상품대분류</th>
										<td>판매보조용품</td>
									</tr>
									<tr>
										<th>상품중분류</th>
										<td>판매보조용품</td>
									</tr>
									<tr>
										<th>상품소분류</th>
										<td>판매보조용품</td>
									</tr>
									<tr>
										<th>상품소분류</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox2"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>										
										</td>
									</tr>
									<tr>
										<th>잡화구분</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox1"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>상품코드</th>
										<td>TM00000115</td>
									</tr>
									<tr>
										<th>상품명</th>
										<td>(금지)[부자재]닥터로지시트</td>
									</tr>
									<tr>
										<th>영문명</th>
										<td><input type="text" id="" name="" ng-model="" class="sb-input w100"/></td>
									</tr>
									<tr>
										<th>FULL 상품명</th>
										<td>(금지)[부자재]닥터로지시트</td>
										<th>위생허가</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox3"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>										
										</td>
									</tr>
									<tr>
										<th>판매가</th>
										<td><input type="text" value="0" class="sb-input w100 tr"/></td>
										<th>상품유형</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox4"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>최소발주량</th>
										<td><input type="text" value="1" class="sb-input w100 tr"/></td>
										<th class="red">최대주문수량</th>
										<td><input type="text" value="1" class="sb-input w100 tr"/></td>
									</tr>
								</tbody>
							</table>

							<span class="txtIn s16 mt20 bk lh30">매입/재고관련 정보</span>

							<table class="searchTbl">
								<colgroup>
								<col class="w15" />
								<col class="w35" />
								<col class="w15" />
								<col class="w35" />
								</colgroup>
								<tbody>
									<tr>
										<th>재고단위</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox5"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>											
										</td>
										<th>재고입수</th>
										<td><input type="text" value="1" class="sb-input w100 tr"/></td>
									</tr>
									<tr>
										<th>발주단위</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox6"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>											
										</td>
										<th>일반입수</th>
										<td><input type="text" value="10" class="sb-input w100 tr"/></td>
									</tr>
									<tr>
										<td colspan="4" style="border-left:1px solid #c5c5c5">입고 : 1EA => 1EA</td>
									</tr>
								</tbody>
							</table>

							<span class="txtIn s16 mt20 bk lh30">기타정보</span>

							<table class="searchTbl">
								<colgroup>
								<col class="w15" />
								<col class="w35" />
								<col class="w10" />
								<col class="w15" />
								<col class="w10" />
								<col class="w15" />
								</colgroup>
								<tbody>
									<tr>
										<th>소스코드</th>
										<td>8806358591144</td>
										<th>용량</th>
										<td><input type="text" value="0" class="sb-input w100 tr"/></td>
										<th>사용여부</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox7"
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
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox8"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>
										</td>
										<th>세트구분</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox1"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>										
										</td>
										<th>제품허용그룹</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox9"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>기존자재번호</th>
										<td><input type="text" value="" class="sb-input w100"/></td>
										<th>영업조직</th>
										<td colspan="3">
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox10"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>
										</td>
									</tr>
									<tr>
										<th>자재 내역</th>
										<td colspan="5"><input type="text" value="" class="sb-input w100"/></td>
									</tr>
									<tr>
										<th>용량단위</th>
										<td><input type="text" value="" class="sb-input w100"/></td>
										<th>상품기획자</th>
										<td colspan="3"><input type="text" value="" class="sb-input w100"/></td>
									</tr>
									<tr>
										<th>판매가능여부</th>
										<td>
											<div class="sb-select">
												<span class="txtIn w100">
													<wj-combo-box
														items-source="combobox11"
														is-editable="false">
													</wj-combo-box>
												</span>
											</div>										
										</td>
										<th>디자인담당자</th>
										<td colspan="3"><input type="text" value="" class="sb-input w100"/></td>
									</tr>
								</tbody>
							</table>
						</div>

					</div>
				</div>	
				
				<div class="clearfix"></div>
			</div>


		</div>
		<!-- //subCon end-->

	</div>
	<!-- -->

</div>

<script>
$(document).ready(function() {

	$('.rightDtl').hide();

	$(document).on('click', '.wj-cells', function() {
		$("div.rightDtl").show();
		wijmo.grid.FlexGrid.refreshAll();
	});
	
	$(document).on('click', '.userId', function() {
		$("div.userLayer2").toggle();
	});
	
});

</script>


<script>
  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl', $scope, $http, true));

	$scope.combobox1 = ['전체', '분류1', '분류2'];
	$scope.combobox2 = ['러닝_보통', '분류1', '분류2'];
	$scope.combobox3 = ['유','무'];
	$scope.combobox4 = ['재고미관리(판촉)','재고관리'];
	$scope.combobox5 = ['EA','분류1'];
	$scope.combobox6 = ['BOX','분류1'];
	$scope.combobox7 = ['사용','미사용'];
	$scope.combobox8 = ['미분류','분류'];
	$scope.combobox9 = ['범용','분류'];
	$scope.combobox10 = ['(주)토니모리','분류'];
	$scope.combobox11 = ['예','아니오'];


	//1. 랜덤 데이터 생성
    var txt1 = 'TM00001003,TM00000115,TM00003631,ET09044800,ET09043300,ET07010800,B001013600,TM0000600,TM00003490,TM00004300'.split(','),
        txt2 = '[라비오뜨]트러플에센스마스크,(금지)[부자재]닥터로지시트,(중국)플로리아뉴트라에너지토너4,(증정용)캐비어볼륨아이필러,(증정용)플라센타크림리플,(특가)아이스토탈솔루션'.split(',')
    
	var data = [];
	for (var i = 0; i < 50; i++) {
		data.push({
			id: i,
			str1: txt1[i % txt1.length],
			str2: txt2[i % txt2.length],
			num1: Math.random() * 1000
		});
	}

	$scope.data = data;
	
  }

  ]);
</script>



