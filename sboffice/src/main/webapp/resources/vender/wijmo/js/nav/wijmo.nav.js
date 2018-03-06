/*
    *
    * Wijmo Library 5.20173.405
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    *
    * Licensed under the GrapeCity Commercial License.
    * sales@wijmo.com
    * wijmo.com/products/wijmo-5/license/
    *
    */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * Defines navigation controls including the @see:TreeView and associated classes.
 */
var wijmo;
(function (wijmo) {
    var nav;
    (function (nav) {
        /**
         * The @see:TreeView control displays a hierarchical list of @see:TreeNode objects
         * which may contain text, checkboxes, images, or arbitrary HTML content.
         *
         * A @see:TreeView is typically used to display the headings in a document,
         * the entries in an index, the files and directories on a disk, or any other
         * kind of information that might usefully be displayed as a hierarchy.
         *
         * After creating a @see:TreeView, you will typically set the following properties:
         *
         * <ol>
         *  <li>
         *      @see:itemsSource: an array that contains the data to be displayed on the
         *      tree.</li>
         *  <li>
         *      @see:displayMemberPath: the name of the data item property that contains
         *      the text to display on the nodes (defaults to 'header'), and</li>
         *  <li>
         *      @see:childItemsPath: the name of the data item property that contains the
         *      node's child items (defaults to 'items').</li>
         * </ol>
         *
         *
         * The example below builds a simple tree and allows you to see the effect
         * of the TreeView's main properties:
         *
         * @fiddle:egmg93wc
         */
        var TreeView = /** @class */ (function (_super) {
            __extends(TreeView, _super);
            /**
             * Initializes a new instance of the @see:TreeView class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function TreeView(element, options) {
                var _this = _super.call(this, element) || this;
                /*private*/ _this._itmPath = new nav._BindingArray('items'); // accessible to TreeNode
                _this._dspPath = new nav._BindingArray('header');
                _this._imgPath = new nav._BindingArray();
                _this._html = false;
                _this._animated = true;
                _this._xpndOnClick = true;
                _this._autoColl = true;
                _this._showChk = false;
                _this._srch = '';
                _this._isReadOnly = true;
                // ** events
                /**
                 * Occurs when the value of the @see:itemsSource property changes.
                 */
                _this.itemsSourceChanged = new wijmo.Event();
                /**
                 * Occurs before the tree items are generated.
                 */
                _this.loadingItems = new wijmo.Event();
                /**
                 * Occurs after the tree items have been generated.
                 */
                _this.loadedItems = new wijmo.Event();
                /**
                 * Occurs when the user clicks an item or presses the Enter key and an item is selected.
                 *
                 * This event is typically used in navigation trees. Use the @see:selectedItem property
                 * to get the item that was clicked.
                 */
                _this.itemClicked = new wijmo.Event();
                /**
                 * Occurs when the value of the @see:selectedItem property changes.
                 */
                _this.selectedItemChanged = new wijmo.Event();
                /**
                 * Occurs when the value of the @see:checkedItems property changes.
                 */
                _this.checkedItemsChanged = new wijmo.Event();
                /**
                 * Occurs before the value of the @see:TreeNode.isCollapsed property changes.
                 */
                _this.isCollapsedChanging = new wijmo.Event();
                /**
                 * Occurs after the value of the @see:TreeNode.isCollapsed property changes.
                 */
                _this.isCollapsedChanged = new wijmo.Event();
                /**
                 * Occurs before the value of the @see:TreeNode.isChecked property changes.
                 */
                _this.isCheckedChanging = new wijmo.Event();
                /**
                 * Occurs after the value of the @see:TreeNode.isChecked property changes.
                 */
                _this.isCheckedChanged = new wijmo.Event();
                /**
                 * Occurs when an element representing a node has been created.
                 *
                 * This event can be used to format nodes for display.
                 *
                 * The example below uses the <b>formatItem</b> event to add a "new" badge to the
                 * right of new items on the tree.
                 *
                 * <pre>var treeViewFmtItem = new wijmo.input.TreeView('#treeViewFmtItem', {
                 *     displayMemberPath: 'header',
                 *     childItemsPath: 'items',
                 *     itemsSource: items,
                 *     formatItem: function (s, e) {
                 *         if (e.dataItem.newItem) {
                 *             e.element.innerHTML +=
                 *                 '&lt;img style="margin-left:6px" src="resources/new.png"/&gt;';
                 *         }
                 *     }
                 * });</pre>
                 */
                _this.formatItem = new wijmo.Event();
                // drag/drop events
                /**
                 * Occurs when the user starts dragging a node.
                 *
                 * This event only occurs if the @see:allowDragging property is set to true.
                 *
                 * You may prevent nodes from being dragged by setting the event's
                 * <b>cancel</b> parameter to true.
                 */
                _this.dragStart = new wijmo.Event();
                /**
                 * Occurs while the user drags a node over other nodes on the @see:TreeView.
                 *
                 * This event only occurs if the @see:allowDragging property is set to true.
                 *
                 * You may prevent drop operations over certain nodes and/or positions by
                 * setting the event's <b>cancel</b> parameter to true.
                 */
                _this.dragOver = new wijmo.Event();
                /**
                 * Occurs when the user drops a on the @see:TreeView.
                 * @return True if the event was not canceled.
                 */
                _this.drop = new wijmo.Event();
                /**
                 * Occurs when the user finishes a drag/drop operation, either by dropping
                 * a node into a new location or by canceling the operation with the mouse
                 * or keyboard.
                 */
                _this.dragEnd = new wijmo.Event();
                // editing events
                /**
                 * Occurs before a @see:TreeNode enters edit mode.
                 */
                _this.nodeEditStarting = new wijmo.Event();
                /**
                 * Occurs after a @see:TreeNode has entered edit mode.
                 */
                _this.nodeEditStarted = new wijmo.Event();
                /**
                 * Occurs before a @see:TreeNode exits edit mode.
                 */
                _this.nodeEditEnding = new wijmo.Event();
                /**
                 * Occurs after a @see:TreeNode has exited edit mode.
                 */
                _this.nodeEditEnded = new wijmo.Event();
                // instantiate and apply template
                var tpl = _this.getTemplate();
                _this.applyTemplate('wj-control wj-content wj-treeview', tpl, {
                    _root: 'root'
                });
                // accessibility: 
                // https://www.w3.org/TR/wai-aria-1.1/#tree
                // http://oaa-accessibility.org/examples/role/106/
                var host = _this.hostElement;
                wijmo.setAttribute(host, 'role', 'tree', true);
                // configure root as nodeList
                wijmo.addClass(_this._root, TreeView._CNDL);
                wijmo.setAttribute(_this._root, 'role', 'group', true);
                // handle mouse and keyboard
                _this.addEventListener(host, 'mousedown', _this._mousedown.bind(_this));
                _this.addEventListener(host, 'click', _this._click.bind(_this));
                _this.addEventListener(host, 'keydown', _this._keydown.bind(_this));
                _this.addEventListener(host, 'keypress', _this._keypress.bind(_this));
                // prevent wheel from propagating to parent elements
                _this.addEventListener(host, 'wheel', function (e) {
                    if (host.scrollHeight > host.offsetHeight) {
                        if ((e.deltaY < 0 && host.scrollTop == 0) ||
                            (e.deltaY > 0 && host.scrollTop + host.offsetHeight >= host.scrollHeight)) {
                            e.preventDefault();
                            e.stopPropagation();
                        }
                    }
                });
                // finish editing when editor loses focus
                _this.addEventListener(host, 'blur', function (e) {
                    if (_this._edtNode && !wijmo.contains(_this._edtNode.element, wijmo.getActiveElement())) {
                        _this.finishEditing();
                    }
                }, true);
                // initialize control options
                _this.initialize(options);
                // populate tree right away
                _this.refresh();
                return _this;
            }
            Object.defineProperty(TreeView.prototype, "itemsSource", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the array that contains the @see:TreeView items.
                 *
                 * @see:TreeView #see:itemsSource arrays usually have a hierarchical
                 * structure with items that contain child items. There is no fixed
                 * limit to the depth of the items.
                 *
                 * For example, the array below would generate a tree with three
                 * top-level nodes, each with two child nodes:
                 *
                 * <pre>var tree = new wijmo.input.TreeView('#treeView', {
                 *     displayMemberPath: 'header',
                 *     childItemsPath: 'items',
                 *     itemsSource: [
                 *         { header: '1 first', items: [
                 *             { header: '1.1 first child' },
                 *             { header: '1.2 second child' },
                 *         ] },
                 *         { header: '2 second', items: [
                 *             { header: '3.1 first child' },
                 *             { header: '3.2 second child' },
                 *         ] },
                 *         { header: '3 third', items: [
                 *             { header: '3.1 first child' },
                 *             { header: '3.2 second child' },
                 *         ] }
                 *     ]
                 * });</pre>
                 */
                get: function () {
                    return this._items;
                },
                set: function (value) {
                    if (this._items != value) {
                        this._items = wijmo.asArray(value);
                        this.onItemsSourceChanged();
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "childItemsPath", {
                /**
                 * Gets or sets the name of the property (or properties) that contains
                 * the child items for each node.
                 *
                 * The default value for this property is the string 'items'.
                 *
                 * In most cases, the property that contains the child items is the
                 * same for all data items on the tree. In these cases, set the
                 * @see:childItemsPath to that name.
                 *
                 * In some cases, however, items at different levels use different
                 * properties to store their child items. For example, you could have
                 * a tree with categories, products, and orders. In that case, you
                 * would set the @see:childItemsPath to an array such as this:
                 *
                 * <pre>// categories have products, products have orders:
                 * tree.childItemsPath = [ 'Products', 'Orders' ];</pre>
                 */
                get: function () {
                    return this._itmPath.path;
                },
                set: function (value) {
                    if (value != this.childItemsPath) {
                        this._itmPath.path = value;
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "displayMemberPath", {
                /**
                 * Gets or sets the name of the property (or properties) to use as
                 * the visual representation of the nodes.
                 *
                 * The default value for this property is the string 'header'.
                 *
                 * In most cases, the property that contains the node text is the
                 * same for all data items on the tree. In these cases, set the
                 * @see:displayMemberPath to that name.
                 *
                 * In some cases, however, items at different levels use different
                 * properties to represent them. For example, you could have
                 * a tree with categories, products, and orders. In that case, you
                 * might set the @see:displayMemberPath to an array such as this:
                 *
                 * <pre>// categories, products, and orders have different headers:
                 * tree.displayMemberPath = [ 'CategoryName', 'ProductName', 'OrderID' ];</pre>
                 */
                get: function () {
                    return this._dspPath.path;
                },
                set: function (value) {
                    if (value != this.displayMemberPath) {
                        this._dspPath.path = value;
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "imageMemberPath", {
                /**
                 * Gets or sets the name of the property (or properties) to use as a
                 * source of images for the nodes.
                 */
                get: function () {
                    return this._imgPath.path;
                },
                set: function (value) {
                    if (value != this.imageMemberPath) {
                        this._imgPath.path = value;
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "isContentHtml", {
                /**
                 * Gets or sets a value indicating whether items are bound to plain text or HTML.
                 */
                get: function () {
                    return this._html;
                },
                set: function (value) {
                    if (value != this._html) {
                        this._html = wijmo.asBoolean(value);
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "showCheckboxes", {
                /**
                 * Gets or sets a value that determines whether the @see:TreeView should
                 * add checkboxes to nodes and manage their state.
                 *
                 * This property can be used only on trees without lazy-loaded nodes
                 * (see the @see:lazyLoadFunction property).
                 *
                 * See also the @see:checkedItems property and @see:checkedItemsChanged
                 * event.
                 */
                get: function () {
                    return this._showChk;
                },
                set: function (value) {
                    if (value != this._showChk) {
                        this._showChk = wijmo.asBoolean(value);
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "autoCollapse", {
                /**
                 * Gets or sets a value that determines if sibling nodes should be collapsed
                 * when a node is expanded.
                 *
                 * This property is set to true by default, because in most cases collapsing
                 * nodes that are not in use helps keep the UI clearer.
                 */
                get: function () {
                    return this._autoColl;
                },
                set: function (value) {
                    this._autoColl = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "isAnimated", {
                /**
                 * Gets or sets a value that indicates whether to use animations when expanding
                 * or collapsing nodes.
                 */
                get: function () {
                    return this._animated;
                },
                set: function (value) {
                    if (value != this._animated) {
                        this._animated = wijmo.asBoolean(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "isReadOnly", {
                /**
                 * Gets or sets a value that determines whether users can edit the text in the
                 * nodes.
                 *
                 * When the @see:isReadOnly property is set to false, users may edit the content
                 * of the tree nodes by typing directly into the nodes. The F2 key can also
                 * be used to enter edit mode with the whole node content selected.
                 *
                 * You may customize the editing behavior using the following methods and events:
                 *
                 * Methods: @see:startEditing, @see:finishEditing.
                 *
                 * Events: @see:nodeEditStarting, @see:nodeEditStarted, @see:nodeEditEnding,
                 * @see:nodeEditEnded.
                 */
                get: function () {
                    return this._isReadOnly;
                },
                set: function (value) {
                    this._isReadOnly = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Starts editing a given @see:TreeNode.
             *
             * @param node @see:TreeNode to edit. If not provided, the currently
             * selected node is used.
             *
             * @return True if the edit operation started successfully.
             */
            TreeView.prototype.startEditing = function (node) {
                // not in read-only mode
                if (this.isReadOnly) {
                    return false;
                }
                // get node to edit
                if (!node) {
                    node = this.selectedNode;
                }
                if (!node || node.isDisabled) {
                    return false;
                }
                // finish pending edits
                if (!this.finishEditing()) {
                    return false;
                }
                // get editor element
                var editor = node.element.querySelector('.' + TreeView._CNDT);
                if (!editor) {
                    return false;
                }
                // starting
                var e = new nav.TreeNodeEventArgs(node);
                if (!this.onNodeEditStarting(e)) {
                    return false;
                }
                // make content editable and selected
                editor.tabIndex = 0; // important for Chrome (TFS 239219)
                editor.focus();
                editor.contentEditable = 'true';
                editor.style.cursor = 'auto';
                var rng = document.createRange();
                rng.selectNodeContents(editor);
                var sel = getSelection();
                sel.removeAllRanges();
                sel.addRange(rng);
                editor.focus(); // important for FireFox (TFS 237528)
                // turn autocomplete/correct off (TFS 238164)
                // http://stackoverflow.com/questions/21163002/disable-autocorrect-autocompletion-in-content-editable-div
                wijmo.setAttribute(editor, 'autocomplete', 'off');
                wijmo.setAttribute(editor, 'autocorrect', 'off');
                // we are in edit mode
                this._edtNode = node;
                this.onNodeEditStarted(e);
                return true;
            };
            /**
             * Commits any pending edits and exits edit mode.
             *
             * @param cancel Whether pending edits should be canceled or committed.
             * @return True if the edit operation finished successfully.
             */
            TreeView.prototype.finishEditing = function (cancel) {
                var node = this._edtNode;
                if (node) {
                    // get editor element
                    var editor = node.element.querySelector('.' + TreeView._CNDT);
                    if (!editor) {
                        return false;
                    }
                    // ending
                    var e = new nav.TreeNodeEventArgs(node);
                    if (!this.onNodeEditEnding(e)) {
                        return false;
                    }
                    // persist/restore value
                    var item = node.dataItem, level = node.level;
                    if (this.isContentHtml) {
                        if (cancel) {
                            editor.innerHTML = this._dspPath.getValue(item, level);
                        }
                        else {
                            this._dspPath.setValue(item, level, editor.innerHTML);
                        }
                    }
                    else {
                        if (cancel) {
                            editor.textContent = this._dspPath.getValue(item, level);
                        }
                        else {
                            this._dspPath.setValue(item, level, editor.textContent);
                        }
                    }
                    // remove selection
                    var rng = document.createRange();
                    rng.selectNodeContents(editor);
                    var sel = getSelection();
                    sel.removeAllRanges();
                    // done editing
                    editor.contentEditable = 'false';
                    editor.style.cursor = '';
                    this._edtNode = null;
                    // ended
                    this.onNodeEditEnded(e);
                }
                return true;
            };
            Object.defineProperty(TreeView.prototype, "allowDragging", {
                /**
                 * Gets or sets a value that determines whether users can drag and drop nodes
                 * within the @see:TreeView.
                 */
                get: function () {
                    return this._dd != null;
                },
                set: function (value) {
                    if (value != this.allowDragging) {
                        // create/dispose of the _TreeViewDragDropManager
                        if (wijmo.asBoolean(value)) {
                            this._dd = new nav._TreeDragDropManager(this);
                        }
                        else {
                            this._dd.dispose();
                            this._dd = null;
                        }
                        // add/remove draggable attribute on node elements
                        var nodes = this.hostElement.querySelectorAll('.' + TreeView._CND);
                        for (var i = 0; i < nodes.length; i++) {
                            var node = nodes[i];
                            wijmo.setAttribute(node, 'draggable', this._dd ? true : null);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "expandOnClick", {
                /**
                 * Gets or sets a value that determines whether to expand collapsed nodes when
                 * the user clicks the node header.
                 */
                get: function () {
                    return this._xpndOnClick;
                },
                set: function (value) {
                    this._xpndOnClick = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "selectedItem", {
                /**
                 * Gets or sets the data item that is currently selected.
                 */
                get: function () {
                    return this._selNode ? this._selNode.dataItem : null;
                },
                set: function (value) {
                    if (value != this.selectedItem) {
                        this.selectedNode = value ? this.getNode(value) : null;
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "selectedNode", {
                /**
                 * Gets or sets the @see:TreeNode that is currently selected.
                 */
                get: function () {
                    return this._selNode;
                },
                set: function (value) {
                    if (value != this.selectedNode) {
                        this._prevSel = this._selNode;
                        if (value) {
                            value.select();
                        }
                        else if (this._selNode) {
                            wijmo.removeClass(this._selNode.element, TreeView._CSEL);
                            this._selNode = null;
                            this.onSelectedItemChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "selectedPath", {
                /**
                 * Gets an array containing the text of all nodes from the root
                 * to the currently selected node.
                 */
                get: function () {
                    var path = [];
                    for (var nd = this.selectedNode; nd; nd = nd.parentNode) {
                        var text = this._dspPath.getValue(nd.dataItem, nd.level);
                        path.splice(0, 0, text);
                    }
                    return path;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "checkedItems", {
                /**
                 * Gets an array containing the items that are currently checked.
                 *
                 * The array returned includes only items that have no children.
                 * This is because checkboxes in parent items are used to check
                 * or uncheck the child items.
                 *
                 * See also the @see:showCheckboxes property and the
                 * @see:checkedItemsChanged property.
                 *
                 * For example:
                 *
                 * <pre>var treeViewChk = new wijmo.input.TreeView('#gsTreeViewChk', {
                 *    displayMemberPath: 'header',
                 *    childItemsPath: 'items',
                 *    showCheckboxes: true,
                 *    itemsSource: items,
                 *    checkedItemsChanged: function (s, e) {
                 *        var items = s.checkedItems,
                 *            msg = '';
                 *        if (items.length) {
                 *            msg = '&lt;p&gt;&lt;b&gt;Selected Items:&lt;/b&gt;&lt;/p&gt;&lt;ol&gt;\r\n';
                 *            for (var i = 0; i &lt; items.length; i++) {
                 *                msg += '&lt;li&gt;' + items[i].header + '&lt;/li&gt;\r\n';
                 *            }
                 *            msg += '&lt;/ol&gt;';
                 *        }
                 *        document.getElementById('gsTreeViewChkStatus').innerHTML = msg;
                 *    }
                 * });</pre>
                 */
                get: function () {
                    if (this._chkItems == null) {
                        var tv = TreeView, qry = '.' + tv._CND + '.' + tv._CEMP + ' > input:checked.' + tv._CNDC, chk = this._root.querySelectorAll(qry);
                        this._chkItems = [];
                        for (var i = 0; i < chk.length; i++) {
                            var item = chk[i].parentElement[tv._DATAITEM_KEY];
                            this._chkItems.push(item);
                        }
                    }
                    return this._chkItems;
                },
                set: function (value) {
                    if (this.showCheckboxes) {
                        var tv = TreeView, qry = '.' + tv._CND + '.' + tv._CEMP, chk = this._root.querySelectorAll(qry), changed = false;
                        for (var i = 0; i < chk.length; i++) {
                            var node = new nav.TreeNode(this, chk[i]), checked = value.indexOf(node.dataItem) > -1;
                            if (node.isChecked != checked) {
                                node.isChecked = checked;
                                changed = true;
                            }
                        }
                        if (changed) {
                            this.onCheckedItemsChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Checks or unchecks all checkboxes on the tree.
             *
             * @param check Whether to check or unckeck all checkboxes.
             */
            TreeView.prototype.checkAllItems = function (check) {
                if (this.showCheckboxes) {
                    var tv = TreeView, qry = '.' + tv._CND + '.' + tv._CEMP, chk = this._root.querySelectorAll(qry), changed = false;
                    for (var i = 0; i < chk.length; i++) {
                        var node = new nav.TreeNode(this, chk[i]);
                        if (node.isChecked != check) {
                            node.isChecked = check;
                            changed = true;
                        }
                    }
                    if (changed) {
                        this.onCheckedItemsChanged();
                    }
                }
            };
            Object.defineProperty(TreeView.prototype, "totalItemCount", {
                /**
                 * Gets the total number of items in the tree.
                 */
                get: function () {
                    var nodes = this.hostElement.querySelectorAll('.' + TreeView._CND);
                    return nodes.length;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeView.prototype, "lazyLoadFunction", {
                /**
                 * Gets or sets a function that loads child nodes on demand.
                 *
                 * The @see:lazyLoadFunction takes two parameters: the node being
                 * expanded and a callback to be invoked when the data becomes
                 * available.
                 *
                 * The callback function tells the @see:TreeView that the node
                 * loading process has been completed. It should always be called,
                 * even if there are errors when loading the data.
                 *
                 * For example:
                 *
                 *<pre>var treeViewLazyLoad = new wijmo.input.TreeView('#treeViewLazyLoad', {
                 *    displayMemberPath: 'header',
                 *    childItemsPath: 'items',
                 *    itemsSource: [ // start with three lazy-loaded nodes
                 *        { header: 'Lazy Node 1', items: []},
                 *        { header: 'Lazy Node 2', items: [] },
                 *        { header: 'Lazy Node 3', items: [] }
                 *    ],
                 *    lazyLoadFunction: function (node, callback) {
                 *        setTimeout(function () { // simulate http delay
                 *            var result = [ // simulate result
                 *                { header: 'Another lazy node...', items: [] },
                 *                { header: 'A non-lazy node without children' },
                 *                { header: 'A non-lazy node with child nodes', items: [
                 *                  { header: 'hello' },
                 *                  { header: 'world' }
                 *                ]}
                 *            ];
                 *            callback(result); // return result to control
                 *        }, 2500); // simulated 2.5 sec http delay
                 *    }
                 *});</pre>
                 *
                 * Trees with lazy-loaded nodes have some restrictions: their nodes
                 * may not have checkboxes (see the @see:showCheckboxes property) and
                 * the @see:collapseToLevel method will not expand collapsed nodes
                 * that have not been loaded yet.
                 */
                get: function () {
                    return this._lazyLoad;
                },
                set: function (value) {
                    if (value != this._lazyLoad) {
                        this._lazyLoad = wijmo.asFunction(value);
                        this._reload();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets a reference to the first @see:TreeNode in the @see:TreeView.
             *
             * @param visible Whether to return only visible nodes (whose ancestors are not collapsed).
             * @param enabled Whether to return only enabled nodes (whose ancestors are not disabled).
             */
            TreeView.prototype.getFirstNode = function (visible, enabled) {
                var first = this.hostElement.querySelector('.' + TreeView._CND), node = first ? new nav.TreeNode(this, first) : null;
                if (visible && node && !node.element.offsetHeight) {
                    node = node.next(visible, enabled);
                }
                if (enabled && node && node.isDisabled) {
                    node = node.next(visible, enabled);
                }
                return node;
            };
            /**
             * Gets a reference to the last @see:TreeNode in the @see:TreeView.
             *
             * @param visible Whether to return only visible nodes (whose ancestors are not collapsed).
             * @param enabled Whether to return only enabled nodes (whose ancestors are not disabled).
             */
            TreeView.prototype.getLastNode = function (visible, enabled) {
                var last = this.hostElement.querySelectorAll('.' + TreeView._CND + ':last-child'), node = last.length ? new nav.TreeNode(this, last[last.length - 1]) : null;
                if (visible && node && !node.element.offsetHeight) {
                    node = node.previous(visible, enabled);
                }
                if (enabled && node && node.isDisabled) {
                    node = node.previous(visible, enabled);
                }
                return node;
            };
            Object.defineProperty(TreeView.prototype, "nodes", {
                /**
                 * Gets an array of @see:TreeNode objects representing the nodes
                 * currently loaded.
                 */
                get: function () {
                    return nav.TreeNode._getChildNodes(this, this._root);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets the @see:TreeNode object representing a given data item.
             *
             * @param item The data item to look for.
             */
            TreeView.prototype.getNode = function (item) {
                // load items if necessary
                if (this._isDirty) {
                    this._loadTree();
                }
                // look for item in the tree
                var nodes = this.hostElement.querySelectorAll('.' + TreeView._CND);
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    if (node[TreeView._DATAITEM_KEY] == item) {
                        return new nav.TreeNode(this, node);
                    }
                }
                // not found...
                return null;
            };
            /**
             * Adds a child node at a specific position.
             *
             * @param index Index of the new child node.
             * @param dataItem Data item used to create the new node.
             * @return The @see:TreeNode that was added.
             */
            TreeView.prototype.addChildNode = function (index, dataItem) {
                // create a new node
                var nd = this._createNode(dataItem);
                // move the node into position
                var nodes = this.nodes;
                if (!nodes) {
                    nd.move(this, nav.DropPosition.Into);
                }
                else if (index < nodes.length) {
                    nd.move(nodes[index], nav.DropPosition.Before);
                }
                else {
                    nd.move(nodes[nodes.length - 1], nav.DropPosition.After);
                }
                // return the new node
                return nd;
            };
            /**
             * Collapses all the tree items to a given level.
             *
             * This method will typically expand or collapse multiple nodes
             * at once. But it will not perform lazy-loading on any nodes,
             * so collapsed nodes that must be lazy-loaded will not be
             * expanded.
             *
             * @param level Maximum node level to show.
             */
            TreeView.prototype.collapseToLevel = function (level) {
                // suspend animation/autoCollapse
                var animated = this._animated;
                var autoColl = this._autoColl;
                this._animated = this._autoColl = false;
                // collapse to level
                this._collapseToLevel(this.nodes, level, 0);
                // restore animation/autoCollapse
                this._animated = animated;
                this._autoColl = autoColl;
            };
            /**
             * Loads the tree using data from the current @see:itemsSource.
             * @param preserveOutlineState Whether to preserve the outline state when loading the
             * tree data. Defaults to false.
             */
            TreeView.prototype.loadTree = function (preserveOutlineState) {
                this._loadTree(preserveOutlineState);
            };
            /**
             * Raises the @see:itemsSourceChanged event.
             */
            TreeView.prototype.onItemsSourceChanged = function (e) {
                this.itemsSourceChanged.raise(this, e);
            };
            /**
             * Raises the @see:loadingItems event.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onLoadingItems = function (e) {
                this.loadingItems.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:loadedItems event.
             */
            TreeView.prototype.onLoadedItems = function (e) {
                this.loadedItems.raise(this, e);
            };
            /**
             * Raises the @see:itemClicked event.
             */
            TreeView.prototype.onItemClicked = function (e) {
                this.itemClicked.raise(this, e);
            };
            /**
             * Raises the @see:selectedItemChanged event.
             */
            TreeView.prototype.onSelectedItemChanged = function (e) {
                this.selectedItemChanged.raise(this, e);
            };
            /**
             * Raises the @see:checkedItemsChanged event.
             */
            TreeView.prototype.onCheckedItemsChanged = function (e) {
                this._chkItems = null;
                this.checkedItemsChanged.raise(this, e);
            };
            /**
             * Raises the @see:isCollapsedChanging event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onIsCollapsedChanging = function (e) {
                this.isCollapsedChanging.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:isCollapsedChanged event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             */
            TreeView.prototype.onIsCollapsedChanged = function (e) {
                this.isCollapsedChanged.raise(this, e);
            };
            /**
             * Raises the @see:isCheckedChanging event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onIsCheckedChanging = function (e) {
                this.isCheckedChanging.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:isCheckedChanged event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             */
            TreeView.prototype.onIsCheckedChanged = function (e) {
                this.isCheckedChanged.raise(this, e);
            };
            /**
             * Raises the @see:formatItem event.
             *
             * @param e @see:FormatNodeEventArgs that contains the event data.
             */
            TreeView.prototype.onFormatItem = function (e) {
                this.formatItem.raise(this, e);
            };
            /**
             * Raises the @see:dragStart event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onDragStart = function (e) {
                this.dragStart.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:dragOver event.
             *
             * @param e @see:TreeNodeDragDropEventArgs that contains the event data.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onDragOver = function (e) {
                this.dragOver.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:drop event.
             *
             * @param e @see:TreeNodeDragDropEventArgs that contains the event data.
             */
            TreeView.prototype.onDrop = function (e) {
                this.drop.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:dragEnd event.
             */
            TreeView.prototype.onDragEnd = function (e) {
                this.dragEnd.raise(this, e);
            };
            /**
             * Raises the @see:nodeEditStarting event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             * @return True if the event was not canceled.
            */
            TreeView.prototype.onNodeEditStarting = function (e) {
                this.nodeEditStarting.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:nodeEditStarted event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             */
            TreeView.prototype.onNodeEditStarted = function (e) {
                this.nodeEditStarted.raise(this, e);
            };
            /**
             * Raises the @see:nodeEditEnding event.
             *
             * @param e @see:TreeNodeEventArgs that contains the event data.
             * @return True if the event was not canceled.
             */
            TreeView.prototype.onNodeEditEnding = function (e) {
                this.nodeEditEnding.raise(this, e);
                return !e.cancel;
            };
            /**
            * Raises the @see:nodeEditEnded event.
            *
            * @param e @see:TreeNodeEventArgs that contains the event data.
            */
            TreeView.prototype.onNodeEditEnded = function (e) {
                this.nodeEditEnded.raise(this, e);
            };
            //--------------------------------------------------------------------------
            //#region ** overrides
            /**
             * Overridden to re-populate the tree.
             */
            TreeView.prototype.refresh = function () {
                _super.prototype.refresh.call(this);
                if (!this.isUpdating && this._isDirty) {
                    this._loadTree();
                }
            };
            //--------------------------------------------------------------------------
            //#region ** private stuff
            // mark as dirty to reload data on the next refresh
            TreeView.prototype._reload = function () {
                this._isDirty = true;
                this.invalidate();
            };
            // create a new node based on a data item
            TreeView.prototype._createNode = function (dataItem) {
                var t = new TreeView(document.createElement('div'), {
                    childItemsPath: this.childItemsPath,
                    displayMemberPath: this.displayMemberPath,
                    imageMemberPath: this.imageMemberPath,
                    isContentHtml: this.isContentHtml,
                    showCheckboxes: this.showCheckboxes,
                    itemsSource: [dataItem]
                });
                return t.getFirstNode();
            };
            // select on mouse down
            TreeView.prototype._mousedown = function (e) {
                if (!e.defaultPrevented) {
                    var cb = wijmo.closest(e.target, 'input.' + TreeView._CNDC), ne = wijmo.closestClass(e.target, TreeView._CND), node = ne ? new nav.TreeNode(this, ne) : null;
                    // select clicked item
                    if (node && !node.isDisabled) {
                        this.selectedNode = node;
                    }
                    // workaround for IE bug: https://github.com/jquery/jquery/issues/1698
                    this._dnIndet = cb && cb.indeterminate;
                }
            };
            // click to toggle node collapsed state
            TreeView.prototype._click = function (e) {
                var _this = this;
                if (!e.defaultPrevented) {
                    var nodeElement = wijmo.closestClass(e.target, TreeView._CND);
                    if (nodeElement) {
                        var node_1 = new nav.TreeNode(this, nodeElement), cb_1 = wijmo.closest(e.target, 'input.' + TreeView._CNDC);
                        // ignore clicks on disabled nodes
                        if (node_1.isDisabled) {
                            return;
                        }
                        // ignore clicks on nodes being edited
                        if (!cb_1 && node_1.equals(this._edtNode)) {
                            return;
                        }
                        // get the focus
                        nodeElement.focus();
                        // toggle isChecked
                        if (cb_1) {
                            // prevent checkbox from handling the click
                            e.preventDefault();
                            e.stopPropagation();
                            // make sure checkbox is updated and raise checkedItemsChanged
                            setTimeout(function () {
                                cb_1.indeterminate = false; // this is required in Chrome/FF: TFS 237264
                                node_1.isChecked = !node_1.isChecked;
                                _this.onCheckedItemsChanged();
                            });
                        }
                        // toggle isCollapsed or start editing
                        if (!cb_1) {
                            // toggle isCollapsed
                            var el = e.target, ctrlKey = e.ctrlKey || e.metaKey, collToLevel = ctrlKey && !node_1.hasPendingChildren, rc = nodeElement.getBoundingClientRect(), offset = this.rightToLeft ? rc.right - e.clientX : e.clientX - rc.left, toggled = false;
                            if (offset <= el.offsetHeight) {
                                if (collToLevel) {
                                    this.collapseToLevel(node_1.isCollapsed ? node_1.level + 1 : node_1.level);
                                }
                                else {
                                    node_1.isCollapsed = !node_1.isCollapsed;
                                }
                                toggled = true;
                            }
                            else if (this.expandOnClick && node_1.isCollapsed) {
                                if (collToLevel) {
                                    this.collapseToLevel(node_1.level);
                                }
                                else {
                                    node_1.isCollapsed = false;
                                }
                                toggled = true;
                            }
                            // make sure the selected node is visible after big expand/collapse
                            if (toggled && collToLevel && this.selectedNode) {
                                this.selectedNode.ensureVisible();
                            }
                            // start editing if we didn't toggle and the selection didn't change
                            if (!toggled && !this.isReadOnly) {
                                if (this.selectedNode && this.selectedNode.equals(this._prevSel)) {
                                    this.startEditing();
                                }
                            }
                        }
                        // raise itemClicked (for navigation)
                        if (this.selectedItem) {
                            this.onItemClicked();
                        }
                    }
                }
            };
            // handle keydown (cursor keys)
            TreeView.prototype._keydown = function (e) {
                if (!e.defaultPrevented) {
                    var node = this._selNode, newNode = void 0, key = e.keyCode, handled = true;
                    if (node && !node.isDisabled) {
                        // enter/exit editmode
                        switch (key) {
                            case wijmo.Key.F2:
                                this.startEditing();
                                e.preventDefault();
                                break;
                            case wijmo.Key.Escape:
                                this.finishEditing(true);
                                e.preventDefault();
                                break;
                            case wijmo.Key.Up:
                            case wijmo.Key.Down:
                                this.finishEditing();
                                break;
                            case wijmo.Key.Enter:
                                if (this._edtNode) {
                                    this.finishEditing();
                                    key = wijmo.Key.Down;
                                }
                                else {
                                    this.startEditing();
                                    e.preventDefault();
                                }
                                break;
                        }
                        // ignore other keys in editing mode
                        if (this._edtNode) {
                            return;
                        }
                        // switch left/right keys in RTL mode
                        if (this.rightToLeft) {
                            switch (key) {
                                case wijmo.Key.Left:
                                    key = wijmo.Key.Right;
                                    break;
                                case wijmo.Key.Right:
                                    key = wijmo.Key.Left;
                                    break;
                            }
                        }
                        // handle key
                        switch (key) {
                            // collapse expanded nodes, select parent of collapsed/empty nodes
                            case wijmo.Key.Left:
                                if (!node.isCollapsed && node.hasChildren) {
                                    node.setCollapsed(true);
                                }
                                else {
                                    node = node.parentNode;
                                    if (node) {
                                        node.select();
                                    }
                                }
                                break;
                            // expand collapsed nodes
                            case wijmo.Key.Right:
                                node.setCollapsed(false);
                                break;
                            // select previous/next
                            case wijmo.Key.Up:
                                newNode = node.previous(true, true);
                                break;
                            case wijmo.Key.Down:
                                newNode = node.next(true, true);
                                break;
                            case wijmo.Key.Home:
                                newNode = this.getFirstNode(true, true);
                                break;
                            case wijmo.Key.End:
                                newNode = this.getLastNode(true, true);
                                break;
                            // toggle checkbox on space
                            case wijmo.Key.Space:
                                if (this.selectedItem) {
                                    var cb = node.checkBox;
                                    if (cb) {
                                        node.isChecked = !cb.checked;
                                        this.onCheckedItemsChanged();
                                    }
                                }
                                break;
                            // raise itemClicked on Enter
                            case wijmo.Key.Enter:
                                if (this.selectedItem) {
                                    this.onItemClicked();
                                }
                                break;
                            // allow default handling
                            default:
                                handled = false;
                        }
                        if (handled) {
                            // ignore event
                            e.preventDefault();
                            // update selection
                            if (newNode) {
                                newNode.select();
                            }
                        }
                    }
                }
            };
            // handle keypress (select/search)
            TreeView.prototype._keypress = function (e) {
                var _this = this;
                if (!e.defaultPrevented) {
                    // don't interfere with the browser (TFS 297316)
                    if (e.ctrlKey || e.metaKey || e.altKey)
                        return;
                    // don't interfere with inner input elements (TFS 132081)
                    if (e.target instanceof HTMLInputElement)
                        return;
                    if (this._edtNode)
                        return;
                    // start editing?
                    if (e.charCode > 32 && this.startEditing(this.selectedNode)) {
                        // apply the character (needed in Firefox only: TFS 238554)
                        var edt = wijmo.getActiveElement();
                        if (wijmo.contains(this._edtNode.element, edt)) {
                            // apply new text and eat event
                            edt.textContent = String.fromCharCode(e.charCode);
                            e.preventDefault();
                            // move the cursor to the end of the new text
                            var rng = document.createRange();
                            rng.selectNodeContents(edt);
                            rng.collapse(false);
                            var sel = getSelection();
                            sel.removeAllRanges();
                            sel.addRange(rng);
                        }
                        // done here
                        return;
                    }
                    // auto search
                    if (e.charCode > 32 || (e.charCode == 32 && this._srch)) {
                        e.preventDefault();
                        // update search string
                        this._srch += String.fromCharCode(e.charCode).toLowerCase();
                        if (this._toSrch) {
                            clearTimeout(this._toSrch);
                        }
                        this._toSrch = setTimeout(function () {
                            _this._toSrch = null;
                            _this._srch = '';
                        }, TreeView._AS_DLY);
                        // perform search
                        var item = this._findNext(); // multi-char search
                        if (item == null && this._srch.length > 1) {
                            this._srch = this._srch[this._srch.length - 1];
                            item = this._findNext(); // single-char search
                        }
                        if (item != null) {
                            this.selectedItem = item;
                        }
                    }
                }
            };
            // look for the '_search' string from the current position
            TreeView.prototype._findNext = function () {
                if (this.hostElement && this.selectedItem) {
                    var start = this.getNode(this.selectedItem), node = start, wrapped = false, skip = false;
                    // start searching from current or next item
                    if (this._srch.length == 1) {
                        skip = true; // TFS 250005
                    }
                    // search through the items (with wrapping)
                    for (; node;) {
                        // check this node
                        if (!node.isDisabled && !skip) {
                            var txt = node.element.textContent.trim().toLowerCase();
                            if (txt.indexOf(this._srch) == 0) {
                                return node.dataItem;
                            }
                        }
                        // move on to next visible node
                        var next = node.next(true, true);
                        if (next == start && wrapped) {
                            break;
                        }
                        if (!next && !wrapped) {
                            next = this.getFirstNode(true, true);
                            wrapped = true;
                        }
                        node = next;
                        skip = false;
                    }
                }
                // not found
                return null;
            };
            // fill up the tree with node elements
            TreeView.prototype._loadTree = function (preserveOutlineState) {
                var root = this._root;
                if (root) {
                    // allow user to cancel
                    if (!this.onLoadingItems(new wijmo.CancelEventArgs())) {
                        return;
                    }
                    // we're not dirty anymore
                    this._isDirty = false;
                    // remember if we have focus
                    var focus_1 = this.containsFocus();
                    // remember selected item
                    var sel = this.selectedItem;
                    this.selectedItem = null;
                    // clear checkeditems array
                    this._chkItems = null;
                    // fire event so user can clean up any current items
                    this._ldLvl = -1;
                    // save collapsed state
                    var collapsedMap = void 0;
                    if (preserveOutlineState && wijmo.isFunction(window['Map'])) {
                        collapsedMap = new Map();
                        var nodes = this.hostElement.querySelectorAll('.' + TreeView._CND);
                        for (var i = 0; i < nodes.length; i++) {
                            var node = nodes[i];
                            if (wijmo.hasClass(node, TreeView._CCLD)) {
                                collapsedMap.set(node[TreeView._DATAITEM_KEY], true);
                            }
                        }
                    }
                    // populate the tree
                    root.innerHTML = '';
                    if (this._items) {
                        for (var i = 0; i < this._items.length; i++) {
                            this._addItem(root, 0, this._items[i]);
                        }
                    }
                    // restore outline state
                    if (collapsedMap) {
                        var nodes = this.hostElement.querySelectorAll('.' + TreeView._CND);
                        for (var i = 0; i < nodes.length; i++) {
                            var node = nodes[i], hasList = nav.TreeNode._isNodeList(node), collapsed = collapsedMap.get(node[TreeView._DATAITEM_KEY]);
                            wijmo.toggleClass(node, TreeView._CCLD, collapsed == true);
                            wijmo.setAttribute(node, 'aria-expanded', hasList ? (!collapsed).toString() : null);
                        }
                    }
                    // restore focus
                    if (focus_1 && !this.containsFocus()) {
                        this.focus();
                    }
                    // try restoring the selection
                    this.selectedItem = sel;
                    // fire event so user can customize items as needed
                    this.onLoadedItems();
                    this._ldLvl = -1;
                }
            };
            // adds an item to the list
            TreeView.prototype._addItem = function (host, level, item) {
                // get node data
                var text = this._dspPath.getValue(item, level), imgSrc = this._imgPath.getValue(item, level), arr = wijmo.asArray(this._itmPath.getValue(item, level), true);
                // create the node
                var node = document.createElement('div');
                wijmo.addClass(node, TreeView._CND);
                node.tabIndex = 0;
                // accessibility
                wijmo.setAttribute(node, 'role', 'treeitem', true);
                // set text
                var span = document.createElement('span');
                if (this.isContentHtml) {
                    span.innerHTML = text;
                }
                else {
                    span.textContent = text;
                }
                wijmo.addClass(span, TreeView._CNDT);
                node.appendChild(span);
                // add image
                if (imgSrc) {
                    var img = document.createElement('img');
                    img.src = imgSrc;
                    node.insertBefore(img, node.firstChild);
                }
                // add checkbox
                if (this._showChk && !this._lazyLoad) {
                    var cb = document.createElement('input');
                    cb.type = 'checkbox';
                    cb.tabIndex = -1;
                    wijmo.addClass(cb, TreeView._CNDC);
                    node.insertBefore(cb, node.firstChild);
                }
                // add draggable attribute
                if (this._dd) {
                    node.setAttribute('draggable', 'true');
                }
                // add node to host
                host.appendChild(node);
                // store reference to item in the node element
                node[TreeView._DATAITEM_KEY] = item;
                // an array with no elements is like no array
                if (arr && arr.length == 0 && !this.lazyLoadFunction) {
                    arr = null;
                }
                // load child nodes
                if (arr) {
                    // set collapsed state
                    var expanded = true;
                    if (level > this._ldLvl) {
                        this._ldLvl = level;
                        // lazy load nodes start collapsed
                        if (arr.length == 0) {
                            wijmo.addClass(node, TreeView._CCLD);
                            expanded = false;
                        }
                    }
                    else {
                        wijmo.addClass(node, TreeView._CCLD);
                        expanded = false;
                        if (level < this._ldLvl) {
                            this._ldLvl = 10000;
                        }
                    }
                    // add child nodes
                    if (arr.length > 0) {
                        // create nodeList
                        var nodeList = document.createElement('div');
                        wijmo.addClass(nodeList, TreeView._CNDL);
                        for (var i = 0; i < arr.length; i++) {
                            this._addItem(nodeList, level + 1, arr[i]);
                        }
                        host.appendChild(nodeList);
                        // accessibility
                        wijmo.setAttribute(node, 'aria-expanded', expanded.toString(), true);
                        wijmo.setAttribute(nodeList, 'role', 'group', true);
                    }
                }
                else {
                    wijmo.addClass(node, TreeView._CEMP);
                }
                // format the node
                if (this.formatItem.hasHandlers) {
                    this.onFormatItem(new nav.FormatNodeEventArgs(item, node, level));
                }
            };
            // collapse all nodes on the list above the given level
            TreeView.prototype._collapseToLevel = function (nodes, maxLevel, currentLevel) {
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    // can't lazy-load multiple nodes at once (TFS 245116)
                    if (node.hasPendingChildren) {
                        continue;
                    }
                    // collapse/expand this node and all child nodes (TFS 242904)
                    node.isCollapsed = currentLevel >= maxLevel;
                    if (node.hasChildren) {
                        this._collapseToLevel(node.nodes, maxLevel, currentLevel + 1);
                    }
                }
            };
            // internal method called by lazy-loaded nodes being expanded
            TreeView.prototype._lazyLoadNode = function (node) {
                var host = this.hostElement;
                if (!wijmo.hasClass(host, TreeView._CLDG)) {
                    wijmo.addClass(host, TreeView._CLDG);
                    wijmo.addClass(node.element, TreeView._CLDG);
                    this.lazyLoadFunction(node, this._lazyLoadCallback.bind(node));
                }
            };
            // populate node's children with lazy load results
            TreeView.prototype._lazyLoadCallback = function (result) {
                var node = this, tree = node.treeView;
                tree._lazyLoadNodeDone(node, result);
            };
            // finish lazy loading
            TreeView.prototype._lazyLoadNodeDone = function (node, result) {
                // done loading
                var tv = TreeView;
                wijmo.removeClass(node.element, tv._CLDG);
                wijmo.removeClass(this.hostElement, tv._CLDG);
                // if result is null or empty, this is an empty node
                var item = node.dataItem, level = node.level, arr = wijmo.asArray(result, true);
                if (arr == null || arr.length == 0) {
                    // no data, this is an empty node
                    this._itmPath.setValue(item, level, null);
                    wijmo.addClass(node.element, tv._CEMP);
                }
                else if (arr.length) {
                    // add dataItems to itemsSource
                    this._itmPath.setValue(item, level, arr);
                    // add elements to tree
                    var nodeList = document.createElement('div'), nodeElement = node.element;
                    wijmo.addClass(nodeList, tv._CNDL);
                    nodeElement.parentElement.insertBefore(nodeList, nodeElement.nextSibling);
                    for (var i = 0; i < arr.length; i++) {
                        this._addItem(nodeList, level + 1, arr[i]);
                    }
                    // expand the node once it's loaded
                    node.isCollapsed = false;
                }
            };
            TreeView._DATAITEM_KEY = 'wj-Data-Item'; // key used to store item reference in node elements
            TreeView._AS_DLY = 600; // auto-search delay
            TreeView._AN_DLY = 200; // animation delay (should match values in CSS)
            TreeView._CND = 'wj-node';
            TreeView._CNDL = 'wj-nodelist';
            TreeView._CEMP = 'wj-state-empty';
            TreeView._CNDT = 'wj-node-text';
            TreeView._CNDC = 'wj-node-check';
            TreeView._CSEL = 'wj-state-selected';
            TreeView._CCLD = 'wj-state-collapsed';
            TreeView._CCLG = 'wj-state-collapsing';
            TreeView._CLDG = 'wj-state-loading';
            /**
             * Gets or sets the template used to instantiate @see:TreeView controls.
             */
            TreeView.controlTemplate = '<div wj-part="root"></div>'; // node container
            return TreeView;
        }(wijmo.Control));
        nav.TreeView = TreeView;
    })(nav = wijmo.nav || (wijmo.nav = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var nav;
    (function (nav) {
        /**
         * Class that represents a node in a @see:TreeView.
         */
        var TreeNode = /** @class */ (function () {
            /**
             * Initializes a new instance of a @see:TreeNode.
             *
             * @param treeView @see:TreeView that contains the node.
             * @param nodeElement HTML element that represents the node on the @see:TreeView.
             */
            function TreeNode(treeView, nodeElement) {
                // special case: virtual node in empty tree
                if (wijmo.hasClass(nodeElement, 'wj-treeview')) {
                    treeView = wijmo.Control.getControl(nodeElement);
                    nodeElement = null;
                }
                else {
                    TreeNode._assertNode(nodeElement);
                }
                this._t = treeView;
                this._e = nodeElement;
            }
            Object.defineProperty(TreeNode.prototype, "dataItem", {
                /**
                 * Gets the data item that this node represents.
                 */
                get: function () {
                    return this._e[nav.TreeView._DATAITEM_KEY];
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "element", {
                /**
                 * Gets the HTML element that represents this node on the @see:TreeView.
                 */
                get: function () {
                    return this._e;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "treeView", {
                /**
                 * Gets a reference to the @see:TreeView that contains this node.
                 */
                get: function () {
                    return this._t;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Ensures that a node is visible by expanding any collapsed
             * ancestors and scrolling the element into view.
             */
            TreeNode.prototype.ensureVisible = function () {
                // make sure all parents are expanded
                for (var p = this.parentNode; p; p = p.parentNode) {
                    p.isCollapsed = false;
                }
                // scroll into view
                var host = this._t.hostElement, rco = this.element.getBoundingClientRect(), rcc = host.getBoundingClientRect();
                if (rco.bottom > rcc.bottom) {
                    host.scrollTop += rco.bottom - rcc.bottom;
                }
                else if (rco.top < rcc.top) {
                    host.scrollTop -= rcc.top - rco.top;
                }
            };
            /**
             * Checks whether this node refers to the same element as another node.
             *
             * @param node @TreeNode to compare with this one.
             */
            TreeNode.prototype.equals = function (node) {
                return node != null && node.element == this.element;
            };
            /**
             * Selects this node.
             */
            TreeNode.prototype.select = function () {
                var tree = this._t;
                // remove selection from previously selected node
                var selNode = tree._selNode;
                if (!this.equals(selNode)) {
                    // de-select previous node
                    if (selNode) {
                        wijmo.removeClass(selNode.element, nav.TreeView._CSEL);
                    }
                    // select this node
                    tree._selNode = this;
                    wijmo.addClass(this.element, nav.TreeView._CSEL);
                    this.ensureVisible();
                    if (tree.containsFocus()) {
                        this.element.focus();
                    }
                    // raise event
                    tree.onSelectedItemChanged();
                }
            };
            Object.defineProperty(TreeNode.prototype, "index", {
                /**
                 * Gets this node's index within the parent's node collection.
                 */
                get: function () {
                    var index = 0;
                    for (var p = this._pse(this.element); p; p = this._pse(p)) {
                        if (TreeNode._isNode(p)) {
                            index++;
                        }
                    }
                    return index;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "parentNode", {
                /**
                 * Gets this node's parent node.
                 *
                 * This property returns null for top-level nodes.
                 */
                get: function () {
                    var parent = null;
                    if (this._e) {
                        var nodeList = this._e.parentElement;
                        TreeNode._assertNodeList(nodeList);
                        parent = this._pse(nodeList);
                    }
                    return parent ? new TreeNode(this._t, parent) : null;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "level", {
                /**
                 * Gets this node's level.
                 *
                 * Top-level nodes have level zero.
                 */
                get: function () {
                    var level = -1;
                    for (var e = this; e; e = e.parentNode) {
                        level++;
                    }
                    return level;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "hasChildren", {
                /**
                 * Gets a value that indicates whether this node has child nodes.
                 */
                get: function () {
                    return TreeNode._isNode(this._e) && !TreeNode._isEmpty(this._e);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "hasPendingChildren", {
                /**
                 * Gets a value that indicates whether this node has pending child nodes
                 * that will be lazy-loaded when the node is expanded.
                 */
                get: function () {
                    return this.isCollapsed && this.hasChildren &&
                        !TreeNode._isNodeList(this.element.nextElementSibling) &&
                        wijmo.isFunction(this._t.lazyLoadFunction);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "nodes", {
                /**
                 * Gets an array containing this node's child nodes.
                 *
                 * This property returns null if the node has no children.
                 */
                get: function () {
                    return this.hasChildren
                        ? TreeNode._getChildNodes(this._t, this._e.nextSibling)
                        : null;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "checkBox", {
                /**
                 * Gets the HTMLInputElement that represents the checkbox associated
                 * with this node.
                 */
                get: function () {
                    return this._e.querySelector('input.' + nav.TreeView._CNDC);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "isCollapsed", {
                /**
                 * Gets or sets a value that determines whether this node is expanded or collapsed.
                 */
                get: function () {
                    return this.hasChildren && wijmo.hasClass(this._e, nav.TreeView._CCLD);
                },
                set: function (value) {
                    if (value != this.isCollapsed) {
                        var tree = this._t, e = new nav.TreeNodeEventArgs(this);
                        if (tree.onIsCollapsedChanging(e)) {
                            this.setCollapsed(wijmo.asBoolean(value), tree.isAnimated, tree.autoCollapse);
                            tree.onIsCollapsedChanged(e);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "isChecked", {
                /**
                 * Gets or sets a value that determines whether this node is checked.
                 *
                 * When the value of this property changes, child and ancestor nodes
                 * are automatically updated, and the parent @see:TreeView's
                 * @see:TreeView.checkedItemsChanged event is raised.
                 */
                get: function () {
                    var cb = this.checkBox;
                    return cb && !cb.indeterminate
                        ? cb.checked
                        : null;
                },
                set: function (value) {
                    if (value != this.isChecked) {
                        var tree = this._t, e = new nav.TreeNodeEventArgs(this);
                        if (tree.onIsCheckedChanging(e)) {
                            this.setChecked(wijmo.asBoolean(value), true);
                            tree.onIsCheckedChanged(e);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNode.prototype, "isDisabled", {
                /**
                 * Gets or sets a value that determines whether this node is disabled.
                 *
                 * Disabled nodes cannot get mouse or keyboard events.
                 */
                get: function () {
                    return this._e && this._e.getAttribute('disabled') != null;
                },
                set: function (value) {
                    value = wijmo.asBoolean(value, true);
                    if (value != this.isDisabled) {
                        wijmo.enable(this._e, !value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets a reference to the previous node in the view.
             *
             * @param visible Whether to return only visible nodes (whose ancestors are not collapsed).
             * @param enabled Whether to return only enabled nodes (whose ancestors are not disabled).
             */
            TreeNode.prototype.previous = function (visible, enabled) {
                // get previous sibling
                var prev = this._pse(this._e);
                // handle first on a node list
                if (!prev && TreeNode._isNodeList(this._e.parentElement)) {
                    prev = this._pse(this._e.parentElement);
                }
                // handle nodelists
                if (TreeNode._isNodeList(prev)) {
                    while (TreeNode._isNodeList(prev) && prev.childElementCount) {
                        prev = prev.lastChild;
                    }
                    if (TreeNode._isNodeList(prev)) {
                        prev = this._pse(prev);
                    }
                }
                // get previous node
                var node = TreeNode._isNode(prev) ? new TreeNode(this._t, prev) : null;
                // skip invisible and disabled nodes
                if (visible && node && !node.element.offsetHeight) {
                    node = node.previous(visible, enabled);
                }
                if (enabled && node && node.isDisabled) {
                    node = node.previous(visible, enabled);
                }
                // done
                return node;
            };
            /**
             * Gets a reference to the next node in the view.
             *
             * @param visible Whether to return only visible nodes (whose ancestors are not collapsed).
             * @param enabled Whether to return only enabled nodes (whose ancestors are not disabled).
             */
            TreeNode.prototype.next = function (visible, enabled) {
                // get next sibling
                var next = this._e.nextSibling;
                // handle nodelists
                if (TreeNode._isNodeList(next)) {
                    next = next.childElementCount
                        ? next.firstChild // first node on the expanded list
                        : next.nextSibling; // next node after the collapsed list
                }
                // handle last on a node list (TFS 246982)
                if (!next) {
                    for (var e = this._e.parentElement; !next && TreeNode._isNodeList(e); e = e.parentElement) {
                        next = e.nextSibling;
                    }
                }
                // get next node
                var node = TreeNode._isNode(next) ? new TreeNode(this._t, next) : null;
                // skip invisible and disabled nodes
                if (visible && node && !node.element.offsetHeight) {
                    node = node.next(visible, enabled);
                }
                if (enabled && node && node.isDisabled) {
                    node = node.next(visible, enabled);
                }
                // done
                return node;
            };
            /**
             * Gets a reference to the next sibling node in the view.
             */
            TreeNode.prototype.previousSibling = function () {
                var prev = this._pse(this.element);
                if (TreeNode._isNodeList(prev)) {
                    prev = this._pse(prev);
                }
                return prev ? new TreeNode(this._t, prev) : null;
            };
            /**
             * Gets a reference to the next sibling node in the view.
             */
            TreeNode.prototype.nextSibling = function () {
                var next = this.element.nextSibling;
                if (TreeNode._isNodeList(next)) {
                    next = next.nextSibling;
                }
                return next ? new TreeNode(this._t, next) : null;
            };
            /**
             * Sets the collapsed state of the node.
             *
             * @param collapsed Whether to collapse or expand the node.
             * @param animate Whether to use animation when applying the new state.
             * @param collapseSiblings Whether to collapse sibling nodes when expanding
             * this node.
             */
            TreeNode.prototype.setCollapsed = function (collapsed, animate, collapseSiblings) {
                // get node and child elements
                var tree = this._t, node = this._e, list = this._e.nextElementSibling, hasList = TreeNode._isNodeList(list);
                // accessibility: https://www.w3.org/TR/wai-aria-1.1/#tree
                wijmo.setAttribute(node, 'aria-expanded', hasList ? (!collapsed).toString() : null);
                // don't waste time...
                if (collapsed == this.isCollapsed) {
                    return;
                }
                // handle lazy-loading
                if (!collapsed && !hasList && wijmo.isFunction(tree.lazyLoadFunction)) {
                    tree._lazyLoadNode(this);
                    return;
                }
                // resolve default parameters
                if (animate == null) {
                    animate = tree.isAnimated;
                }
                if (collapseSiblings == null) {
                    collapseSiblings = tree.autoCollapse;
                }
                // update collapsed state
                if (!animate) {
                    wijmo.toggleClass(node, nav.TreeView._CCLD, collapsed);
                }
                else {
                    if (hasList) {
                        var h_1 = list.offsetHeight, s_1 = list.style;
                        if (!collapsed) {
                            wijmo.toggleClass(node, nav.TreeView._CCLD, false);
                            s_1.height = s_1.opacity = '0';
                            wijmo.animate(function (pct) {
                                if (pct >= 1) {
                                    s_1.height = s_1.opacity = '';
                                }
                                else {
                                    s_1.height = (pct * h_1).toFixed(0) + 'px';
                                    //s.opacity = pct.toFixed(2);
                                }
                            }, nav.TreeView._AN_DLY);
                        }
                        else {
                            wijmo.toggleClass(node, nav.TreeView._CCLG, true);
                            wijmo.animate(function (pct) {
                                if (pct < 1) {
                                    pct = 1 - pct;
                                    s_1.height = (pct * h_1).toFixed(0) + 'px';
                                    //s.opacity = pct.toFixed(2);
                                }
                                else {
                                    s_1.height = s_1.opacity = '';
                                    wijmo.toggleClass(node, nav.TreeView._CCLD, true);
                                    wijmo.toggleClass(node, nav.TreeView._CCLG, false);
                                }
                            }, nav.TreeView._AN_DLY);
                        }
                    }
                }
                // when expanding an item in a node list, collapse all siblings
                if (!collapsed && collapseSiblings) {
                    var list_1 = node.parentElement;
                    if (TreeNode._isNodeList(list_1)) {
                        for (var i = 0; i < list_1.children.length; i++) {
                            var sibling = list_1.children[i];
                            if (sibling != node && TreeNode._isNode(sibling)) {
                                wijmo.toggleClass(sibling, nav.TreeView._CCLD, true);
                                // accessibility: https://www.w3.org/TR/wai-aria-1.1/#tree
                                sibling.setAttribute('aria-expanded', 'false');
                            }
                        }
                    }
                }
            };
            /**
             * Sets the checked state of this node and its children.
             *
             * @param checked Whether to check or uncheck the node and its children.
             * @param updateParent Whether to update the checked state of this node's
             * ancestor nodes.
             */
            TreeNode.prototype.setChecked = function (checked, updateParent) {
                // set this node's checkbox
                var cb = this.checkBox;
                cb.checked = checked;
                cb.indeterminate = false;
                // set direct children's checkboxes
                if (this.hasChildren) {
                    var nodes = this.nodes;
                    for (var i = 0; i < nodes.length; i++) {
                        var child = nodes[i];
                        child.setChecked(checked, false);
                    }
                }
                // update parent checkboxes
                if (updateParent) {
                    var parent_1 = this.parentNode;
                    if (parent_1) {
                        parent_1._updateCheckedState();
                    }
                }
            };
            /**
             * Removes this @see:TreeNode from a @see:TreeView.
             */
            TreeNode.prototype.remove = function () {
                var tree = this._t, parent = this.parentNode, arr = this._getArray(), index = arr.indexOf(this.dataItem);
                // if this is the selected node, select the next sibling
                if (tree.selectedNode == this) {
                    var newSel = this.nextSibling() || this.previousSibling() || this.parentNode;
                    if (newSel) {
                        tree.selectedNode = newSel;
                    }
                }
                // update treeView
                wijmo.removeChild(this.element);
                // update parent state (hasChildren, checked)
                if (parent) {
                    parent._updateState();
                }
                // update itemsSource
                arr.splice(index, 1);
                // update reference to parent TreeView
                this._t = null;
            };
            /**
             * Adds a child node at a specific position.
             *
             * @param index Index of the new child node.
             * @param dataItem Data item used to create the new node.
             * @return The @see:TreeNode that was added.
             */
            TreeNode.prototype.addChildNode = function (index, dataItem) {
                // create a new node
                var nd = this._t._createNode(dataItem);
                // move the node into position
                var nodes = this.nodes;
                if (!nodes) {
                    nd.move(this, nav.DropPosition.Into);
                }
                else if (index < nodes.length) {
                    nd.move(nodes[index], nav.DropPosition.Before);
                }
                else {
                    nd.move(nodes[nodes.length - 1], nav.DropPosition.After);
                }
                // return the new node
                return nd;
            };
            /**
             * Refreshes a node to reflect data changes.
             *
             * @param dataItem New node data. This parameter is optional.
             * If not provided, the node will be refreshed based on its
             * original data item (which presumably has been updated).
             */
            TreeNode.prototype.refresh = function (dataItem) {
                // save the new data item if provided, get the updated data item
                var arr = this._getArray();
                if (dataItem) {
                    arr[this.index] = dataItem;
                }
                dataItem = arr[this.index];
                // create a new node based on the updated data
                var newNode = this._t._createNode(dataItem);
                // remove old children
                var nodeList = this.hasChildren && !this.hasPendingChildren
                    ? this.element.nextSibling
                    : null;
                if (nodeList) {
                    wijmo.removeChild(nodeList);
                }
                // add new children
                nodeList = newNode.hasChildren && !newNode.hasPendingChildren
                    ? newNode.element.nextSibling
                    : null;
                if (nodeList) {
                    this.element.parentElement.insertBefore(nodeList, this.element.nextSibling);
                }
                // replace the node content
                this.element.innerHTML = newNode.element.innerHTML;
                // update the node state
                this._updateState();
            };
            /**
             * Moves this @see:TreeNode to a new position on the @see:TreeView.
             *
             * @param refNode Reference @see:TreeNode that defines the location
             * where the node will be moved.
             * @param position Whether to move the node before, after, or into
             * the reference node.
             * @return True if the node was moved successfully.
             */
            TreeNode.prototype.move = function (refNode, position) {
                // check that the refNode is not a child of this node
                if (refNode instanceof TreeNode && this._contains(refNode)) {
                    return false;
                }
                // update treeView
                var parentOld = this.parentNode, arrOld = this._getArray();
                this._moveElements(refNode, position);
                var parentNew = this.parentNode, arrNew = this._getArray();
                // update old and new parent's state (hasChildren, checked)
                if (parentOld) {
                    parentOld._updateState();
                }
                if (parentNew) {
                    parentNew._updateState();
                }
                // update itemsSource
                var item = this.dataItem, index = arrOld.indexOf(item);
                arrOld.splice(index, 1);
                arrNew.splice(this.index, 0, item);
                // update reference to parent TreeView 
                // (in case the node was moved to another parent)
                if (refNode.treeView) {
                    this._t = refNode.treeView;
                }
                // all done
                return true;
            };
            Object.defineProperty(TreeNode.prototype, "itemsSource", {
                /**
                 * Gets the array that contains the items for this @see:TreeNode.
                 *
                 * This property is read-only. It returns an array that is a
                 * member of the parent @see:TreeView's @see:TreeView.itemsSource array.
                 */
                get: function () {
                    return this._getArray();
                },
                enumerable: true,
                configurable: true
            });
            // ** private stuff
            // gets an element's previous element sibling
            TreeNode.prototype._pse = function (e) {
                return e.previousElementSibling;
            };
            // checks whether this node contains another
            TreeNode.prototype._contains = function (node) {
                for (; node; node = node.parentNode) {
                    if (node.element == this.element) {
                        return true;
                    }
                }
                return false;
            };
            // gets the array that contains this node's data item
            TreeNode.prototype._getArray = function () {
                var tree = this._t, parent = this.parentNode, arr = tree.itemsSource;
                if (parent) {
                    var path = tree._itmPath;
                    arr = path.getValue(parent.dataItem, this.level);
                    if (!arr) {
                        arr = [];
                        path.setValue(parent.dataItem, this.level, arr);
                    }
                }
                return arr;
            };
            // move node elements to a new position in the tree
            TreeNode.prototype._moveElements = function (refNode, position) {
                // grab this node's elements into a fragment
                var frag = document.createDocumentFragment(), nodeList = this.hasChildren && !this.hasPendingChildren
                    ? this.element.nextSibling
                    : null;
                frag.appendChild(this.element);
                if (nodeList) {
                    TreeNode._assertNodeList(nodeList);
                    frag.appendChild(nodeList);
                }
                // if the refNode is a TreeView, append and be done
                if (refNode instanceof nav.TreeView) {
                    refNode._root.insertBefore(frag, null);
                    return;
                }
                // get reference node's parent so we can move this node into it
                var ref = refNode.element, parent = (ref ? ref.parentElement : refNode.treeView._root);
                TreeNode._assertNodeList(parent);
                // insert fragment at the proper position
                var dp = nav.DropPosition;
                switch (position) {
                    case dp.Before:
                        parent.insertBefore(frag, ref);
                        break;
                    case dp.After:
                        refNode = refNode.nextSibling();
                        ref = refNode ? refNode.element : null;
                        parent.insertBefore(frag, ref);
                        break;
                    case dp.Into:
                        if (!refNode.hasChildren || refNode.hasPendingChildren) {
                            nodeList = document.createElement('div');
                            wijmo.addClass(nodeList, nav.TreeView._CNDL);
                            parent.insertBefore(nodeList, ref.nextSibling);
                        }
                        parent = refNode.element.nextSibling;
                        TreeNode._assertNodeList(parent);
                        parent.insertBefore(frag, null); // append to list
                        break;
                }
            };
            // update node state after a move operation
            TreeNode.prototype._updateState = function () {
                this._updateEmptyState();
                this._updateCheckedState();
            };
            // update node empty state
            TreeNode.prototype._updateEmptyState = function () {
                // check whether we have child nodes, remove empty child lists
                var nodeList = this.element.nextSibling, hasChildren = false;
                if (TreeNode._isNodeList(nodeList)) {
                    if (nodeList.childElementCount) {
                        hasChildren = true;
                    }
                    else {
                        wijmo.removeChild(nodeList);
                    }
                }
                // update the node's empty attribute
                wijmo.toggleClass(this.element, nav.TreeView._CEMP, !hasChildren);
                // can't be expanded without children
                if (!hasChildren) {
                    this.element.removeAttribute('aria-expanded');
                }
            };
            // update node checked state
            TreeNode.prototype._updateCheckedState = function () {
                var cb = this.checkBox, nodes = this.nodes, checked = 0, unchecked = 0;
                // update this node's checked state
                if (cb && nodes) {
                    for (var i = 0; i < nodes.length; i++) {
                        var node = nodes[i];
                        switch (node.isChecked) {
                            case true:
                                checked++;
                                break;
                            case false:
                                unchecked++;
                                break;
                        }
                    }
                    if (checked == nodes.length) {
                        cb.checked = true;
                        cb.indeterminate = false;
                    }
                    else if (unchecked == nodes.length) {
                        cb.checked = false;
                        cb.indeterminate = false;
                    }
                    else {
                        cb.checked = false;
                        cb.indeterminate = true;
                    }
                }
                // move up one level
                var parent = this.parentNode;
                if (parent) {
                    parent._updateCheckedState();
                }
            };
            // gets the child nodes from a nodeList
            TreeNode._getChildNodes = function (treeView, nodeList) {
                TreeNode._assertNodeList(nodeList);
                var arr = [];
                if (TreeNode._isNodeList(nodeList)) {
                    var children = nodeList.children;
                    for (var i = 0; i < children.length; i++) {
                        var child = children[i];
                        if (TreeNode._isNode(child)) {
                            arr.push(new TreeNode(treeView, child));
                        }
                    }
                }
                return arr;
            };
            // static methods to check for node state/type based on their class
            TreeNode._isNode = function (e) {
                return e && wijmo.hasClass(e, nav.TreeView._CND);
            };
            TreeNode._isNodeList = function (e) {
                return e && wijmo.hasClass(e, nav.TreeView._CNDL);
            };
            TreeNode._isEmpty = function (node) {
                return TreeNode._isNode(node) && wijmo.hasClass(node, nav.TreeView._CEMP);
            };
            TreeNode._isCollapsed = function (node) {
                return TreeNode._isNode(node) && !TreeNode._isEmpty(node) && wijmo.hasClass(node, nav.TreeView._CCLD);
            };
            TreeNode._assertNode = function (node) {
                wijmo.assert(TreeNode._isNode(node), 'node expected');
            };
            TreeNode._assertNodeList = function (nodeList) {
                wijmo.assert(TreeNode._isNodeList(nodeList), 'nodeList expected');
            };
            return TreeNode;
        }());
        nav.TreeNode = TreeNode;
    })(nav = wijmo.nav || (wijmo.nav = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var nav;
    (function (nav) {
        /**
         * Provides arguments for the @see:TreeView.formatItem event.
         */
        var FormatNodeEventArgs = /** @class */ (function (_super) {
            __extends(FormatNodeEventArgs, _super);
            /**
             * Initializes a new instance of the @see:FormatNodeEventArgs class.
             *
             * @param dataItem Data item represented by the node.
             * @param element Element that represents the node being formatted.
             * @param level The outline level of the node being formatted.
             */
            function FormatNodeEventArgs(dataItem, element, level) {
                var _this = _super.call(this) || this;
                _this._data = dataItem;
                _this._e = wijmo.asType(element, HTMLElement);
                _this._level = level;
                return _this;
            }
            Object.defineProperty(FormatNodeEventArgs.prototype, "dataItem", {
                /**
                 * Gets the data item being formatted.
                 */
                get: function () {
                    return this._data;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FormatNodeEventArgs.prototype, "element", {
                /**
                 * Gets a reference to the element that represents the node being formatted.
                 */
                get: function () {
                    return this._e;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FormatNodeEventArgs.prototype, "level", {
                /**
                 * Gets the outline level of the node being formatted.
                 */
                get: function () {
                    return this._level;
                },
                enumerable: true,
                configurable: true
            });
            return FormatNodeEventArgs;
        }(wijmo.EventArgs));
        nav.FormatNodeEventArgs = FormatNodeEventArgs;
        /**
         * Provides arguments for @see:TreeNode-related events.
         */
        var TreeNodeEventArgs = /** @class */ (function (_super) {
            __extends(TreeNodeEventArgs, _super);
            /**
             * Initializes a new instance of the @see:TreeNodeEventArgs class.
             *
             * @param node @see:TreeNode that this event refers to.
             */
            function TreeNodeEventArgs(node) {
                var _this = _super.call(this) || this;
                _this._node = node;
                return _this;
            }
            Object.defineProperty(TreeNodeEventArgs.prototype, "node", {
                /**
                 * Gets the @see:TreeNode that this event refers to.
                 */
                get: function () {
                    return this._node;
                },
                enumerable: true,
                configurable: true
            });
            return TreeNodeEventArgs;
        }(wijmo.CancelEventArgs));
        nav.TreeNodeEventArgs = TreeNodeEventArgs;
        /**
         * Provides arguments for @see:TreeNode drag-drop events.
         */
        var TreeNodeDragDropEventArgs = /** @class */ (function (_super) {
            __extends(TreeNodeDragDropEventArgs, _super);
            /**
             * Initializes a new instance of the @see:TreeNodeEventArgs class.
             *
             * @param dragSource @see:TreeNode being dragged.
             * @param dropTarget @see:TreeNode where the source is being dropped.
             * @param position @see:DropPosition that this event refers to.
             */
            function TreeNodeDragDropEventArgs(dragSource, dropTarget, position) {
                var _this = _super.call(this) || this;
                _this._src = wijmo.asType(dragSource, nav.TreeNode);
                _this._tgt = wijmo.asType(dropTarget, nav.TreeNode);
                _this._pos = position;
                return _this;
            }
            Object.defineProperty(TreeNodeDragDropEventArgs.prototype, "dragSource", {
                /**
                 * Gets a reference to the @see:TreeNode being dragged.
                 */
                get: function () {
                    return this._src;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNodeDragDropEventArgs.prototype, "dropTarget", {
                /**
                 * Gets a reference to the current @see:TreeNode target.
                 */
                get: function () {
                    return this._tgt;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(TreeNodeDragDropEventArgs.prototype, "position", {
                /**
                 * Gets or sets the @see:DropPosition value that specifies where
                 * the @see:TreeNode will be dropped.
                 */
                get: function () {
                    return this._pos;
                },
                set: function (value) {
                    this._pos = wijmo.asEnum(value, DropPosition);
                },
                enumerable: true,
                configurable: true
            });
            return TreeNodeDragDropEventArgs;
        }(wijmo.CancelEventArgs));
        nav.TreeNodeDragDropEventArgs = TreeNodeDragDropEventArgs;
        /**
         * Specifies the position where a @see:TreeNode is being dropped during
         * a drag and drop operation.
         */
        var DropPosition;
        (function (DropPosition) {
            /** The node will become the previous sibling of the target node. */
            DropPosition[DropPosition["Before"] = 0] = "Before";
            /** The node will become the next sibling of the target node. */
            DropPosition[DropPosition["After"] = 1] = "After";
            /** The node will become the last child of the target node. */
            DropPosition[DropPosition["Into"] = 2] = "Into";
        })(DropPosition = nav.DropPosition || (nav.DropPosition = {}));
        ;
    })(nav = wijmo.nav || (wijmo.nav = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var nav;
    (function (nav) {
        /**
         * Class that handles drag/drop operations for a @see:TreeView.
         */
        var _TreeDragDropManager = /** @class */ (function () {
            /**
             * Initializes a new instance of a @see:_TreeViewDragDropManager.
             *
             * @param treeView @see:TreeView managed by this @see:_TreeViewDragDropManager.
             */
            function _TreeDragDropManager(treeView) {
                this._tree = wijmo.asType(treeView, nav.TreeView);
                this._dragstartBnd = this._dragstart.bind(this);
                this._dragoverBnd = this._dragover.bind(this);
                this._dropBnd = this._drop.bind(this);
                this._dragendBnd = this._dragend.bind(this);
                // add listeners
                var tree = this._tree, host = tree.hostElement;
                tree.addEventListener(host, 'dragstart', this._dragstartBnd);
                tree.addEventListener(host, 'dragover', this._dragoverBnd);
                tree.addEventListener(host, 'dragleave', this._dragoverBnd);
                tree.addEventListener(host, 'drop', this._dropBnd);
                tree.addEventListener(host, 'dragend', this._dragendBnd);
                tree.addEventListener(host, 'keydown', this._keydown);
            }
            /**
             * Disposes of this @see:_TreeViewDragDropManager
             */
            _TreeDragDropManager.prototype.dispose = function () {
                // remove event listeners
                var tree = this._tree, host = tree.hostElement;
                tree.removeEventListener(host, 'dragstart', this._dragstartBnd);
                tree.removeEventListener(host, 'dragover', this._dragoverBnd);
                tree.removeEventListener(host, 'dragleave', this._dragoverBnd);
                tree.removeEventListener(host, 'drop', this._dropBnd);
                tree.removeEventListener(host, 'dragend', this._dragendBnd);
                tree.removeEventListener(host, 'keydown', this._keydown);
                // dispose of marker
                this._showDragMarker();
            };
            // drag/drop event handlers
            _TreeDragDropManager.prototype._dragstart = function (e) {
                if (!e.defaultPrevented) {
                    // get the node being dragged
                    var tree = this._tree, target = wijmo.closestClass(e.target, nav.TreeView._CND), ddm = _TreeDragDropManager;
                    ddm._drgSrc = nav.TreeNode._isNode(target) ? new nav.TreeNode(tree, target) : null;
                    // raise event
                    if (ddm._drgSrc) {
                        var ee = new nav.TreeNodeEventArgs(ddm._drgSrc);
                        if (!tree.onDragStart(ee)) {
                            ddm._drgSrc = null;
                        }
                    }
                    // start dragging or prevent default
                    if (ddm._drgSrc && e.dataTransfer) {
                        wijmo._startDrag(e.dataTransfer, 'copyMove');
                        e.stopPropagation();
                    }
                    else {
                        e.preventDefault();
                    }
                }
            };
            _TreeDragDropManager.prototype._dragover = function (e) {
                this._handleDragDrop(e, false);
            };
            _TreeDragDropManager.prototype._drop = function (e) {
                this._handleDragDrop(e, true);
            };
            _TreeDragDropManager.prototype._dragend = function (e) {
                _TreeDragDropManager._drgSrc = null;
                this._showDragMarker();
                this._tree.onDragEnd();
            };
            // cancel drag/drop if user presses Escape key
            _TreeDragDropManager.prototype._keydown = function (e) {
                if (!e.defaultPrevented) {
                    if (e.keyCode == wijmo.Key.Escape) {
                        this._dragendBnd(null);
                    }
                }
            };
            // perform hit-testing to find the target node and position
            _TreeDragDropManager.prototype._handleDragDrop = function (e, drop) {
                var tree = this._tree, ddm = _TreeDragDropManager, ee, dp = nav.DropPosition, pos = dp.Into, rc;
                if (!e.defaultPrevented && ddm._drgSrc) {
                    // get target node
                    var element = document.elementFromPoint(e.clientX, e.clientY), target = wijmo.closestClass(element, nav.TreeView._CND);
                    // handle case where destination tree is empty
                    if (target == null) {
                        var tt = wijmo.Control.getControl(wijmo.closest(element, '.wj-treeview'));
                        if (tt instanceof nav.TreeView && tt.totalItemCount == 0) {
                            target = tt.hostElement;
                        }
                    }
                    // ensure target is not the source
                    if (target == ddm._drgSrc.element) {
                        target = null;
                    }
                    // calculate target details
                    if (target) {
                        // get drop position with respect to target node (before/after/into)
                        // note: can't drop into lazy-loading nodes (TFS 246954)
                        rc = target.getBoundingClientRect();
                        var targetNode = new nav.TreeNode(tree, target), szCheck = targetNode.hasPendingChildren ? rc.height / 2 : rc.height / 3;
                        if (targetNode.element == null) {
                            // dragging into an empty tree or node with lazy content
                            rc = wijmo.Rect.fromBoundingRect(rc);
                            rc.inflate(-12, -12);
                            pos = dp.Before;
                        }
                        else if (e.clientY < rc.top + szCheck) {
                            // before this node, easy
                            pos = dp.Before;
                        }
                        else if (e.clientY > rc.bottom - szCheck || targetNode.hasPendingChildren) {
                            // after this node
                            pos = dp.After;
                            // but if it has children and is not collapsed, 
                            // insert before the first child instead
                            if (targetNode.hasChildren && !targetNode.isCollapsed && !targetNode.hasPendingChildren) {
                                pos = dp.Before;
                                targetNode = targetNode.next(true, false);
                                target = targetNode.element;
                                rc = target.getBoundingClientRect();
                            }
                        }
                        // make sure target is not our child
                        if (ddm._drgSrc._contains(targetNode)) {
                            target = null;
                        }
                        else {
                            // prevent dragging to different trees by default
                            ee = new nav.TreeNodeDragDropEventArgs(ddm._drgSrc, targetNode, pos);
                            ee.cancel = ddm._drgSrc.treeView != targetNode.treeView;
                            if (!tree.onDragOver(ee)) {
                                target = null;
                            }
                        }
                    }
                    // dragging before the next or after the previous sibling has no effect
                    if (target) {
                        pos = ee.position;
                        if (pos == dp.Before) {
                            var next = ee.dragSource.next(true, false);
                            if (next && next.element == target) {
                                target = null;
                            }
                        }
                        else if (pos == dp.After) {
                            var prev = ee.dragSource.previous(true, false);
                            if (prev && prev.element == target) {
                                target = null;
                            }
                        }
                    }
                    // update the drag marker
                    if (target && !drop) {
                        e.dataTransfer.dropEffect = 'move';
                        e.preventDefault();
                        e.stopPropagation(); // prevent scrolling on Android
                        this._showDragMarker(rc, pos);
                    }
                    else {
                        this._showDragMarker();
                    }
                    // make the drop
                    if (target && drop) {
                        if (tree.onDrop(ee)) {
                            tree.hostElement.focus(); // TFS 240438
                            var src = ee.dragSource;
                            src.move(ee.dropTarget, ee.position);
                            src.select();
                        }
                    }
                }
            };
            // show the drag marker at the given position or remove it from view
            _TreeDragDropManager.prototype._showDragMarker = function (rc, pos) {
                var tree = this._tree, parent = _TreeDragDropManager._dMarker.parentElement;
                if (rc) {
                    // position the marker (accounting for RTL)
                    var rcHost = tree.hostElement.getBoundingClientRect(), top_1 = pos == nav.DropPosition.After ? rc.bottom : rc.top, css = {
                        top: Math.round(top_1 - rcHost.top + tree.hostElement.scrollTop - 2),
                        width: '75%',
                        height: pos == nav.DropPosition.Into ? rc.height : 4,
                        opacity: pos == nav.DropPosition.Into ? '0.15' : ''
                    };
                    if (tree.rightToLeft) {
                        css.right = Math.round(rcHost.right - rc.right);
                    }
                    else {
                        css.left = Math.round(rc.left - rcHost.left);
                    }
                    wijmo.setCss(_TreeDragDropManager._dMarker, css);
                    // make sure marker is in the DOM
                    if (parent != tree._root) {
                        tree._root.appendChild(_TreeDragDropManager._dMarker);
                    }
                }
                else {
                    // remove marker from the DOM
                    if (parent) {
                        parent.removeChild(_TreeDragDropManager._dMarker);
                    }
                }
            };
            // static members to allow drag/drop between trees (TFS 242905)
            _TreeDragDropManager._dMarker = wijmo.createElement('<div class="wj-marker">&nbsp;</div>');
            return _TreeDragDropManager;
        }());
        nav._TreeDragDropManager = _TreeDragDropManager;
    })(nav = wijmo.nav || (wijmo.nav = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var nav;
    (function (nav) {
        /**
         * Class that handles hierarchical (multi-level) bindings.
         */
        var _BindingArray = /** @class */ (function () {
            /**
             * Initializes a new instance of a _BindingArray.
             *
             * @param path String or array of strings to create bindings from.
             */
            function _BindingArray(path) {
                this.path = path;
            }
            Object.defineProperty(_BindingArray.prototype, "path", {
                /**
                 * Gets or sets the names of the properties used for binding.
                 */
                get: function () {
                    return this._path;
                },
                set: function (value) {
                    this._path = value;
                    if (wijmo.isString(value)) {
                        this._bindings = [
                            new wijmo.Binding(value)
                        ];
                    }
                    else if (wijmo.isArray(value)) {
                        this._bindings = [];
                        for (var i = 0; i < value.length; i++) {
                            this._bindings.push(new wijmo.Binding(value[i]));
                        }
                    }
                    else if (value != null) {
                        wijmo.assert(false, 'Path should be a string or an array of strings.');
                    }
                    this._maxLevel = this._bindings ? this._bindings.length - 1 : -1;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets the binding value for a given data item at a given level.
             *
             * @param dataItem Object that contains the data.
             * @param level Binding level to use for retrieving the data.
             */
            _BindingArray.prototype.getValue = function (dataItem, level) {
                var index = Math.min(level, this._maxLevel);
                return index > -1 ? this._bindings[index].getValue(dataItem) : null;
            };
            /**
             * Sets the binding value on a given data item at a given level.
             *
             * @param dataItem Object that contains the data.
             * @param level Binding level to use for retrieving the data.
             * @param value Value to apply to the data item.
             */
            _BindingArray.prototype.setValue = function (dataItem, level, value) {
                var index = Math.min(level, this._maxLevel);
                if (index > -1) {
                    this._bindings[index].setValue(dataItem, value);
                }
            };
            return _BindingArray;
        }());
        nav._BindingArray = _BindingArray;
    })(nav = wijmo.nav || (wijmo.nav = {}));
})(wijmo || (wijmo = {}));

