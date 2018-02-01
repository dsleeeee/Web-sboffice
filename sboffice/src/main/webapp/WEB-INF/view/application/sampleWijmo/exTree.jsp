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
  function getData() {
    var allMenu = ${sessionScope.sessionInfo.menuData};
    //var currentMenu = "${sessionScope.sessionInfo.currentMenu.resrceCd}";
    var currentMenu = "000087"; // 임시로
    findCurrItem(allMenu, currentMenu);
    return allMenu;
  }
  function findCurrItem(items, c) {
    var keyItem = Object.keys(items);
    console.log("......keyItem : "+ keyItem);
    keyItem.forEach(function(o){
      var subMenuItems = Object.keys(items[o]);
      console.log("........ subMenuItems : "+subMenuItems);
      subMenuItems.forEach(function(i){
        var value = items[o].resrceCd;
        console.log("........ value : "+value);
      });
    });
  }
   
   /*
  function findCurrItem(items, c){
    for(var o in items) {
      
      console.log(">>> "+items[o])
      
      if(typeof items[o] == "object" ) {
        findCurrItem(items[o], c)
      } else {
        if(items[o] == c) {
          console.log('찾음 ========>  '+ items[o]);
          items[o].selectedMenu = true;
        }
      }
    }
    //console.log("resultItems : "+ JSON.stringify(items));
  }
   */
  var tree = new wijmo.nav.TreeView('#theTree', {
    itemsSource: getData(),
    displayMemberPath: 'header',
    childItemsPath: 'items',
    expandOnClick : true,
    isContentHtml: true,
    loadedItems: function(s, e) {
      console.log("loadedItems...");
      s.collapseToLevel(10);
    }
  });
}
</script>


























