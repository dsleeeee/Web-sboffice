﻿/*
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
/**
 * Extension that provides detail rows for @see:FlexGrid controls.
 */
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid_1) {
        var detail;
        (function (detail) {
            'use strict';
            /**
             * Specifies when and how the row details are displayed.
             */
            var DetailVisibilityMode;
            (function (DetailVisibilityMode) {
                /**
                 * Details are shown or hidden in code, using the
                 * @see:FlexGridDetailProvider.showDetail and
                 * @see:FlexGridDetailProvider.hideDetail methods.
                 */
                DetailVisibilityMode[DetailVisibilityMode["Code"] = 0] = "Code";
                /**
                 * Details are shown for the row that is currently selected.
                 */
                DetailVisibilityMode[DetailVisibilityMode["Selection"] = 1] = "Selection";
                /**
                 * Details are shown or hidden using buttons added to the row headers.
                 * Only one row may be expanded at a time.
                 */
                DetailVisibilityMode[DetailVisibilityMode["ExpandSingle"] = 2] = "ExpandSingle";
                /**
                 * Details are shown or hidden using buttons added to the row headers.
                 * Multiple rows may be expanded at a time.
                 */
                DetailVisibilityMode[DetailVisibilityMode["ExpandMulti"] = 3] = "ExpandMulti";
            })(DetailVisibilityMode = detail.DetailVisibilityMode || (detail.DetailVisibilityMode = {}));
            /**
             * Implements detail rows for @see:FlexGrid controls.
             *
             * To add detail rows to a @see:FlexGrid control, create an instance of a
             * @see:FlexGridDetailProvider and set the @see:createDetailCell property
             * to a function that creates elements to be displayed in the detail cells.
             *
             * For example:
             *
             * <pre>// create FlexGrid to show categories
             * var gridCat = new wijmo.grid.FlexGrid('#gridCat');
             * gridCat.itemsSource = getCategories();
             * // add detail rows showing products in each category
             * var detailProvider = new wijmo.grid.detail.FlexGridDetailProvider(gridCat);
             * detailProvider.createDetailCell = function (row) {
             *   var cell = document.createElement('div');
             *   var gridProducts = new wijmo.grid.FlexGrid(cell);
             *   gridProducts.itemsSource = getProducts(row.dataItem.CategoryID);
             *   return cell;
             * }</pre>
             *
             * The @see:FlexGridDetailProvider provides a @see:detailVisibilityMode property
             * that determines when the detail rows should be displayed. The default value for
             * this property is <b>ExpandSingle</b>, which adds collapse/expand icons to the
             * row headers.
             */
            var FlexGridDetailProvider = /** @class */ (function () {
                /**
                 * Initializes a new instance of the @see:FlexGridDetailProvider class.
                 *
                 * @param grid @see:FlexGrid that will receive detail rows.
                 * @param options Initialization options for the new @see:FlexGridDetailProvider.
                 */
                function FlexGridDetailProvider(grid, options) {
                    var _this = this;
                    this._mode = DetailVisibilityMode.ExpandSingle;
                    this._animated = false;
                    this._g = grid;
                    // custom merging for cells and row headers
                    grid.mergeManager = new detail.DetailMergeManager(grid);
                    // expand/collapse detail
                    grid.rowHeaders.hostElement.addEventListener('click', this._hdrClick.bind(this));
                    // show details, collapse/expand icons
                    grid.formatItem.addHandler(this._formatItem, this);
                    // show details for selected cell
                    grid.selectionChanged.addHandler(this._selectionChanged, this);
                    // refresh controls to update layout when detail rows are resized
                    grid.resizedRow.addHandler(this._resizedRow, this);
                    // hide all details when grid is refreshed
                    grid.loadingRows.addHandler(function () {
                        _this.hideDetail();
                    });
                    // hide detail when dragging row (TFS 241962)
                    grid.draggingRow.addHandler(function (s, e) {
                        if (e.row < s.rows.length - 1 && s.rows[e.row + 1] instanceof detail.DetailRow) {
                            e.cancel = true;
                            _this.hideDetail(e.row);
                        }
                    });
                    // keep detail row at the start even with frozen columns (TFS 131863)
                    grid.formatItem.addHandler(function (s, e) {
                        if (e.panel == s.cells) {
                            var row = s.rows[e.row];
                            if (row instanceof detail.DetailRow) {
                                e.cell.style.left = '0';
                            }
                        }
                    });
                    // apply initialization options if any
                    if (options) {
                        wijmo.copy(this, options);
                    }
                }
                Object.defineProperty(FlexGridDetailProvider.prototype, "grid", {
                    // ** object model
                    /**
                     * Gets the @see:FlexGrid that owns this @see:FlexGridDetailProvider.
                     */
                    get: function () {
                        return this._g;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridDetailProvider.prototype, "detailVisibilityMode", {
                    /**
                     * Gets or sets a value that determines when row details are displayed.
                     */
                    get: function () {
                        return this._mode;
                    },
                    set: function (value) {
                        if (value != this._mode) {
                            this._mode = wijmo.asEnum(value, DetailVisibilityMode);
                            this.hideDetail();
                            this._g.invalidate();
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridDetailProvider.prototype, "maxHeight", {
                    /**
                     * Gets or sets the maximum height of the detail rows, in pixels.
                     */
                    get: function () {
                        return this._maxHeight;
                    },
                    set: function (value) {
                        this._maxHeight = wijmo.asNumber(value, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridDetailProvider.prototype, "isAnimated", {
                    /**
                     * Gets or sets a value that indicates whether to use animation when
                     * showing row details.
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
                Object.defineProperty(FlexGridDetailProvider.prototype, "createDetailCell", {
                    /**
                     * Gets or sets the callback function that creates detail cells.
                     *
                     * The callback function takes a @see:Row as a parameter and
                     * returns an HTML element representing the row details.
                     * For example:
                     *
                     * <pre>// create detail cells for a given row
                     * dp.createDetailCell = function (row) {
                     *   var cell = document.createElement('div');
                     *   var detailGrid = new wijmo.grid.FlexGrid(cell, {
                     *     itemsSource: getProducts(row.dataItem.CategoryID),
                     *     headersVisibility: wijmo.grid.HeadersVisibility.Column
                     *   });
                     *   return cell;
                     * };</pre>
                     */
                    get: function () {
                        return this._createDetailCellFn;
                    },
                    set: function (value) {
                        this._createDetailCellFn = wijmo.asFunction(value, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridDetailProvider.prototype, "disposeDetailCell", {
                    /**
                     * Gets or sets the callback function that disposes of detail cells.
                     *
                     * The callback function takes a @see:Row as a parameter and
                     * disposes of any resources associated with the detail cell.
                     *
                     * This function is optional. Use it in cases where the
                     * @see:createDetailCell function allocates resources that are not
                     * automatically garbage-collected.
                     */
                    get: function () {
                        return this._disposeDetailCellFn;
                    },
                    set: function (value) {
                        this._disposeDetailCellFn = wijmo.asFunction(value, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridDetailProvider.prototype, "rowHasDetail", {
                    /**
                     * Gets or sets the callback function that determines whether a row
                     * has details.
                     *
                     * The callback function takes a @see:Row as a parameter and
                     * returns a boolean value that indicates whether the row has
                     * details. For example:
                     *
                     * <pre>// remove details from items with odd CategoryID
                     * dp.rowHasDetail = function (row) {
                     *   return row.dataItem.CategoryID % 2 == 0;
                     * };</pre>
                     *
                     * Setting this property to null indicates all rows have details.
                     */
                    get: function () {
                        return this._rowHasDetailFn;
                    },
                    set: function (value) {
                        this._rowHasDetailFn = wijmo.asFunction(value, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Gets the detail row associated with a given grid row.
                 *
                 * @param row Row or index of the row to investigate.
                 */
                FlexGridDetailProvider.prototype.getDetailRow = function (row) {
                    var rows = this._g.rows;
                    row = this._toIndex(row);
                    if (rows[row] instanceof detail.DetailRow) {
                        return rows[row];
                    }
                    if (row < rows.length - 1 && rows[row + 1] instanceof detail.DetailRow) {
                        return rows[row + 1];
                    }
                    return null;
                };
                /**
                 * Gets a value that determines if a row's details are visible.
                 *
                 * @param row Row or index of the row to investigate.
                 */
                FlexGridDetailProvider.prototype.isDetailVisible = function (row) {
                    return this.getDetailRow(row) != null;
                };
                /**
                 * Gets a value that determines if a row has details to show.
                 *
                 * @param row Row or index of the row to investigate.
                 */
                FlexGridDetailProvider.prototype.isDetailAvailable = function (row) {
                    var rows = this._g.rows;
                    row = this._toIndex(row);
                    return this._hasDetail(row);
                };
                /**
                 * Hides the detail row for a given row.
                 *
                 * @param row Row or index of the row that will have its details hidden.
                 * This parameter is optional. If not provided, all detail rows are hidden.
                 */
                FlexGridDetailProvider.prototype.hideDetail = function (row) {
                    var rows = this._g.rows;
                    // if 'row' is not provided, hide all details
                    if (row == null) {
                        for (var r = 0; r < rows.length; r++) {
                            if (rows[r] instanceof detail.DetailRow) {
                                this.hideDetail(r);
                            }
                        }
                        return;
                    }
                    // remove detail for a given row
                    row = this._toIndex(row);
                    // skip to next row if this is the main row
                    if (!(rows[row] instanceof detail.DetailRow) &&
                        row < rows.length - 1 &&
                        rows[row + 1] instanceof detail.DetailRow) {
                        row++;
                    }
                    // if we have a detail row, dispose of any child controls 
                    // (to avoid memory leaks) and remove the row
                    var detailRow = rows[row];
                    if (detailRow instanceof detail.DetailRow) {
                        if (this.disposeDetailCell) {
                            this.disposeDetailCell(detailRow);
                        }
                        wijmo.Control.disposeAll(detailRow.detail);
                        rows.removeAt(row);
                    }
                };
                /**
                 * Shows the detail row for a given row.
                 *
                 * @param row Row or index of the row that will have its details shown.
                 * @param hideOthers Whether to hide details for all other rows.
                 */
                FlexGridDetailProvider.prototype.showDetail = function (row, hideOthers) {
                    if (hideOthers === void 0) { hideOthers = false; }
                    var g = this._g, rows = g.rows;
                    // convert rows into indices
                    row = this._toIndex(row);
                    // get main row if given row was a detail
                    if (row > 0 && rows[row] instanceof detail.DetailRow) {
                        row--;
                    }
                    // hide others before showing this
                    if (hideOthers) {
                        var sel = g.selection, updateSelection = false;
                        for (var r = 0; r < rows.length - 1; r++) {
                            if (r != row && rows[r + 1] instanceof detail.DetailRow) {
                                this.hideDetail(r);
                                if (r < row) {
                                    row--;
                                }
                                if (r < sel.row) {
                                    sel.row--;
                                    sel.row2--;
                                    updateSelection = true;
                                }
                            }
                        }
                        if (updateSelection) {
                            g.select(sel, false);
                        }
                    }
                    // show this after hiding the others (TFS 203017)
                    if (!this.isDetailVisible(row) && this._hasDetail(row)) {
                        // create detail row and cell element
                        var detailRow = new detail.DetailRow(rows[row]);
                        detailRow.detail = this._createDetailCell(rows[row]);
                        // insert new detail row below the current row and show it
                        if (detailRow.detail) {
                            if (!this._animated) {
                                rows.insert(row + 1, detailRow);
                                g.scrollIntoView(row, -1);
                            }
                            else {
                                var style = detailRow.detail.style;
                                style.transform = 'translateY(-100%)';
                                style.opacity = '0';
                                rows.insert(row + 1, detailRow);
                                wijmo.animate(function (pct) {
                                    if (pct < 1) {
                                        style.transform = 'translateY(' + (-(1 - pct) * 100).toFixed(0) + '%)';
                                        style.opacity = (pct * pct).toString();
                                    }
                                    else {
                                        style.transform = '';
                                        style.opacity = '';
                                        wijmo.Control.invalidateAll(detailRow.detail);
                                        g.scrollIntoView(row, -1);
                                    }
                                });
                            }
                        }
                    }
                };
                // ** implementation
                // convert Row objects into row indices
                FlexGridDetailProvider.prototype._toIndex = function (row) {
                    if (row instanceof grid_1.Row) {
                        row = row.index;
                    }
                    return wijmo.asNumber(row, false, true);
                };
                // expand/collapse detail row
                FlexGridDetailProvider.prototype._hdrClick = function (e) {
                    if (!e.defaultPrevented) {
                        switch (this._mode) {
                            case DetailVisibilityMode.ExpandMulti:
                            case DetailVisibilityMode.ExpandSingle:
                                var g = this._g, ht = g.hitTest(e);
                                if (ht.row > -1) {
                                    var row = g.rows[ht.row];
                                    if (this.isDetailVisible(ht.row)) {
                                        this.hideDetail(ht.row);
                                    }
                                    else {
                                        g.select(new grid_1.CellRange(ht.row, 0, ht.row, g.columns.length - 1));
                                        this.showDetail(ht.row, this._mode == DetailVisibilityMode.ExpandSingle);
                                    }
                                    e.preventDefault();
                                }
                                break;
                        }
                    }
                };
                // expand selected row (but not too often)
                FlexGridDetailProvider.prototype._selectionChanged = function (s, e) {
                    var _this = this;
                    if (this._mode == DetailVisibilityMode.Selection) {
                        if (this._toSel) {
                            clearTimeout(this._toSel);
                        }
                        this._toSel = setTimeout(function () {
                            if (s.selection.row > -1) {
                                _this.showDetail(s.selection.row, true);
                            }
                            else {
                                _this.hideDetail();
                            }
                        }, 300);
                    }
                };
                // show details, collapse/expand icons
                FlexGridDetailProvider.prototype._formatItem = function (s, e) {
                    var g = this._g, row = e.panel.rows[e.row];
                    // show detail in detail row
                    if (e.panel == g.cells && row instanceof detail.DetailRow && row.detail != null) {
                        // add detail to cell
                        wijmo.addClass(e.cell, 'wj-detail');
                        e.cell.textContent = '';
                        e.cell.style.textAlign = ''; // TFS 130035
                        e.cell.appendChild(row.detail);
                        // set row height (once)
                        if (row.height == null) {
                            // make sure controls in detail cell are properly sized
                            wijmo.Control.refreshAll(e.cell);
                            // calculate height needed for the detail plus padding
                            var cs = getComputedStyle(e.cell), h = row.detail.scrollHeight + parseInt(cs.paddingTop) + parseInt(cs.paddingBottom);
                            // honor max height
                            if (this._maxHeight > 0 && h > this._maxHeight) {
                                h = this._maxHeight;
                            }
                            // apply height
                            row.height = h;
                            // make the cell element fill the row
                            if (!row.detail.style.height) {
                                row.detail.style.height = '100%';
                            }
                            // make inner FlexGrid controls fill the row
                            var gridHost = row.detail.querySelector('.wj-flexgrid');
                            if (gridHost && !gridHost.style.height) {
                                gridHost.style.height = '100%';
                            }
                        }
                        else {
                            setTimeout(function () {
                                wijmo.Control.refreshAll(row.detail);
                            });
                        }
                    }
                    // show collapse/expand icon
                    if (this._mode == DetailVisibilityMode.ExpandMulti ||
                        this._mode == DetailVisibilityMode.ExpandSingle) {
                        // if this row has details, add collapse/expand icons
                        if (e.panel == g.rowHeaders && e.col == 0 && this._hasDetail(e.row)) {
                            // if the next row is, the icon is a 'minus' (collapse)
                            var minus = e.row < g.rows.length - 1 && g.rows[e.row + 1] instanceof detail.DetailRow;
                            // show icon
                            e.cell.innerHTML = minus
                                ? '<span class="wj-glyph-minus"></span>'
                                : '<span class="wj-glyph-plus"></span>';
                        }
                    }
                };
                // refresh controls to update layout when detail rows are resized
                FlexGridDetailProvider.prototype._resizedRow = function (s, e) {
                    var row = e.panel.rows[e.row];
                    if (row instanceof detail.DetailRow && row.detail) {
                        wijmo.Control.refreshAll(row.detail);
                    }
                };
                // check if a row has details currently visible
                FlexGridDetailProvider.prototype._hasVisibleDetail = function (row) {
                    return row instanceof detail.DetailRow || row instanceof grid_1.GroupRow || row instanceof grid_1._NewRowTemplate
                        ? false
                        : true;
                };
                // check if a row has details to show
                FlexGridDetailProvider.prototype._hasDetail = function (row) {
                    return wijmo.isFunction(this._rowHasDetailFn)
                        ? this._rowHasDetailFn(this._g.rows[row])
                        : true;
                };
                // creates the cell element that will show details for a given row
                FlexGridDetailProvider.prototype._createDetailCell = function (row, col) {
                    return this.createDetailCell
                        ? this.createDetailCell(row, col)
                        : null;
                };
                return FlexGridDetailProvider;
            }());
            detail.FlexGridDetailProvider = FlexGridDetailProvider;
        })(detail = grid_1.detail || (grid_1.detail = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
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
    var grid;
    (function (grid_1) {
        var detail;
        (function (detail) {
            'use strict';
            /**
             * Merge manager class used by the @see:FlexGridDetailProvider class.
             *
             * The @see:DetailMergeManager merges detail cells (cells in a @see:DetailRow)
             * into a single detail cell that spans all grid columns.
             */
            var DetailMergeManager = /** @class */ (function (_super) {
                __extends(DetailMergeManager, _super);
                /**
                 * Initializes a new instance of the @see:DetailMergeManager class.
                 *
                 * @param grid The @see:FlexGrid object that owns this @see:DetailMergeManager.
                 */
                function DetailMergeManager(grid) {
                    return _super.call(this, grid) || this;
                }
                /**
                 * Gets a @see:CellRange that specifies the merged extent of a cell
                 * in a @see:GridPanel.
                 *
                 * @param p The @see:GridPanel that contains the range.
                 * @param r The index of the row that contains the cell.
                 * @param c The index of the column that contains the cell.
                 * @param clip Whether to clip the merged range to the grid's current view range.
                 * @return A @see:CellRange that specifies the merged range, or null if the cell is not merged.
                 */
                DetailMergeManager.prototype.getMergedRange = function (p, r, c, clip) {
                    if (clip === void 0) { clip = true; }
                    switch (p.cellType) {
                        // merge detail cells all the way across
                        case grid_1.CellType.Cell:
                            if (p.rows[r] instanceof detail.DetailRow) {
                                return new grid_1.CellRange(r, 0, r, p.columns.length - 1);
                            }
                            break;
                        // merge row headers for main and detail rows
                        case grid_1.CellType.RowHeader:
                            if (p.rows[r] instanceof detail.DetailRow) {
                                return new grid_1.CellRange(r - 1, c, r, c);
                            }
                            else if (r < p.rows.length - 1 && p.rows[r + 1] instanceof detail.DetailRow) {
                                return new grid_1.CellRange(r, c, r + 1, c);
                            }
                            break;
                    }
                    // allow base class
                    return _super.prototype.getMergedRange.call(this, p, r, c, clip);
                };
                return DetailMergeManager;
            }(grid_1.MergeManager));
            detail.DetailMergeManager = DetailMergeManager;
        })(detail = grid_1.detail || (grid_1.detail = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
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
    var grid;
    (function (grid) {
        var detail;
        (function (detail) {
            'use strict';
            /**
             * Row that contains a single detail cell spanning all grid columns.
             */
            var DetailRow = /** @class */ (function (_super) {
                __extends(DetailRow, _super);
                /**
                 * Initializes a new instance of the @see:DetailRow class.
                 *
                 * @param parentRow @see:Row that this @see:DetailRow provides details for.
                 */
                function DetailRow(parentRow) {
                    var _this = _super.call(this) || this;
                    _this.isReadOnly = true;
                    return _this;
                }
                Object.defineProperty(DetailRow.prototype, "detail", {
                    /**
                     * Gets or sets the HTML element that represents the detail cell in this @see:DetailRow.
                     */
                    get: function () {
                        return this._detail;
                    },
                    set: function (value) {
                        this._detail = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                return DetailRow;
            }(grid.Row));
            detail.DetailRow = DetailRow;
        })(detail = grid.detail || (grid.detail = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

