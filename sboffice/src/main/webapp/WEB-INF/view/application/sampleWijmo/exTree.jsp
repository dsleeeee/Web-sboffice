<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://cdn.wijmo.com/5.latest/controls/wijmo.nav.min.js"></script>

<style>
.wj-treeview {
    display:block;
    height: 50%;
    font-size: 120%;
    margin-bottom: 8px;
    padding: 6px;
    background: #f0f0f0;
    box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);
}
body {
  margin-bottom: 24pt;
}
</style>

exTree

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls : ${task}</h3>
  </div>

  <br>
  <div id="theTree"></div>
  
  <div id="selection"></div>
  <div id="click"></div>
</div>

<script>
onload = function() {
  
    function getDataTest() {
      	
//       	var test = "[{\"header\":\"샘플\", \"items\":[ { \"header\": \"테스트1\" }, { \"header\": \"테스트2\" } ] }]";
      
      	var test = ${treeData};
      
		return test;
    }
	/* 
	function getData() {
		return [
			{ header: "샘플",
				items: [
					{ header: "<a href='/exRedis.sb'>레디스이동</a>" },
					{ header: "<a href='/sample3.sb'>이동</a>" },
					{ header: "<a href='/sample2.sb'>샘플2 이동</a>"}
					{ header: "샘플2",
					  items:[
					    { header: "<a href='http://www.daum.net'>샘플1</a>" },
					    { header: "<a href='http://www.naver.com'>샘플2</a>" }
					  ]
					}
					}
					}
					]
			},
			{ header: "그리드", 
				items: [
					{ header: "<a href='/sampleGrid.sb'>그리드 샘플 이동(json)</a>" },
					{ header: "<a href='/sampleGrid2.sb'>그리드 샘플 이동(test)</a>" },
					{ header: "<a href='/exGridPage.sb'>그리드 페이징 샘플 이동</a>" },
					{ header: "<a href='/exGridHeader.sb?rnum=1000'>그리드 헤더 번역 샘플 이동</a>" },
					{ header: "<a href='/exInput.sb'>INPUT 테스트</a>" },
					{ header: "<a href='/exTree.sb'>Tree 테스트</a>" }
					]
			}
			];
	}
 */
	
	var tree = new wijmo.nav.TreeView('#theTree', {
	  	itemsSource: getDataTest(),	  
		displayMemberPath: 'header',
	    childItemsPath: 'items',
// 	    showCheckboxes : true,
	    expandOnClick : true,
	    isContentHtml: true,
	    loadedItems: function(s, e) {
			console.log("loadedItems...");
			s.collapseToLevel(10);
	    }
	    /*
	    selectedItemChanged: function(s, e) {
	    	var msg = wijmo.format('You selected item <b>{header}</b>.', s.selectedItem);
	    	document.getElementById('selection').innerHTML = msg;
	    },
	    itemClicked: function(s, e) {
	    	var msg = wijmo.format('You clicked item <b>{header}</b>.', s.selectedItem);
	    	document.getElementById('click').innerHTML = msg;
	    } */
	});

	
}
</script>


























