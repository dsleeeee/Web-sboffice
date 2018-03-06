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
///<wijmo-soft-import from="wijmo.grid.multirow"/>
///<wijmo-soft-import from="wijmo.grid.sheet"/>
///<wijmo-soft-import from="wijmo.olap"/>
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
* Defines the @see:FlexGridPdfConverter class used to export the @see:FlexGrid to PDF.
*/
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid_1) {
        var pdf;
        (function (pdf) {
            'use strict';
            /**
            * Specifies how the grid content should be scaled to fit the page.
            */
            var ScaleMode;
            (function (ScaleMode) {
                /**
                * Render the grid in actual size, breaking into pages as needed.
                */
                ScaleMode[ScaleMode["ActualSize"] = 0] = "ActualSize";
                /**
                * Scale the grid, so that it fits the page width.
                */
                ScaleMode[ScaleMode["PageWidth"] = 1] = "PageWidth";
                /**
                * Scale the grid, so that it fits on a single page.
                */
                ScaleMode[ScaleMode["SinglePage"] = 2] = "SinglePage";
            })(ScaleMode = pdf.ScaleMode || (pdf.ScaleMode = {}));
            /**
            * Specifies whether the whole grid or just a section should be rendered.
            */
            var ExportMode;
            (function (ExportMode) {
                /**
                * Exports all the data from grid.
                */
                ExportMode[ExportMode["All"] = 0] = "All";
                /**
                * Exports the current selection only.
                */
                ExportMode[ExportMode["Selection"] = 1] = "Selection";
            })(ExportMode = pdf.ExportMode || (pdf.ExportMode = {}));
            /**
            * Represents arguments of the IFlexGridDrawSettings.formatItem callback.
            */
            var PdfFormatItemEventArgs = /** @class */ (function (_super) {
                __extends(PdfFormatItemEventArgs, _super);
                /**
                * Initializes a new instance of the @see:PdfFormatItemEventArgs class.
                *
                * @param p @see:GridPanel that contains the range.
                * @param rng Range of cells affected by the event.
                * @param cell Element that represents the grid cell to be rendered.
                * @param canvas Canvas to perform the custom painting on.
                * @param clientRect	Object that represents the client rectangle of the grid cell to be rendered in canvas coordinates.
                * @param contentRect Object that represents the content rectangle of the grid cell to be rendered in canvas coordinates.
                * @param textTop Object that represents the top position of the text in canvas coordinates.
                * @param style Object that represents the style of the grid cell to be rendered.
                * @param getFormattedCell Callback function that should return the grid cell when the getFormattedCell method is called.
                */
                function PdfFormatItemEventArgs(p, rng, cell, canvas, clientRect, contentRect, textTop, style, getFormattedCell) {
                    var _this = _super.call(this, p, rng) || this;
                    /**
                    * Gets or sets a value that indicates that default cell borders drawing should be canceled.
                    */
                    _this.cancelBorders = false;
                    _this._cell = wijmo.asType(cell, HTMLElement, true);
                    _this._canvas = canvas;
                    _this._clientRect = clientRect;
                    _this._contentRect = contentRect;
                    _this._textTop = textTop;
                    _this._style = style;
                    _this._getFormattedCell = getFormattedCell;
                    return _this;
                }
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "canvas", {
                    /**
                    * Gets the canvas to perform the custom painting on.
                    */
                    get: function () {
                        return this._canvas;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "cell", {
                    /**
                    * Gets a reference to the element that represents the grid cell being rendered.
                    * If IFlexGridDrawSettings.customCellContent is set to true then contains
                    * reference to the element that represents the formatted grid cell; otherwise, a null value.
                    */
                    get: function () {
                        return this._cell;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "clientRect", {
                    /**
                    * Gets the client rectangle of the cell being rendered in canvas coordinates.
                    */
                    get: function () {
                        return this._clientRect;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "contentRect", {
                    /**
                    * Gets the content rectangle of the cell being rendered in canvas coordinates.
                    */
                    get: function () {
                        return this._contentRect;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                * Returns a reference to the element that represents the grid cell being rendered.
                * This method is useful when export of custom formatting is disabled, but you need
                * to export custom content for certain cells.
                */
                PdfFormatItemEventArgs.prototype.getFormattedCell = function () {
                    return wijmo.asFunction(this._getFormattedCell)();
                };
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "style", {
                    /**
                    * Gets an object that represents the style of the cell being rendered.
                    * If IFlexGridDrawSettings.customCellContent is set to true then the style is inferred
                    * from the cell style; othwerwise it contains a combination of the IFlexGridDrawSettings.styles export
                    * setting, according to the row type of exported cell.
                    */
                    get: function () {
                        return this._style;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfFormatItemEventArgs.prototype, "textTop", {
                    /**
                    * Gets the value that represents the top position of the text of the cell being rendered in canvas coordinates.
                    */
                    get: function () {
                        return this._textTop;
                    },
                    enumerable: true,
                    configurable: true
                });
                return PdfFormatItemEventArgs;
            }(wijmo.grid.CellRangeEventArgs));
            pdf.PdfFormatItemEventArgs = PdfFormatItemEventArgs;
            /*
            * Merges the content of the source object with the destination object.
            *
            * @param dst The destination object.
            * @param src The source object.
            * @param overwrite Indicates whether the existing properties should be overwritten.
            * @return The modified destination object.
            */
            function _merge(dst, src, overwrite) {
                if (overwrite === void 0) { overwrite = false; }
                if (src && dst) {
                    for (var key in src) {
                        var srcProp = src[key], dstProp = dst[key];
                        if (!wijmo.isObject(srcProp)) {
                            if (dstProp === undefined || (overwrite && srcProp !== undefined)) {
                                dst[key] = srcProp;
                            }
                        }
                        else {
                            if (dstProp === undefined || !wijmo.isObject(dstProp) && overwrite) {
                                if (wijmo.isFunction(srcProp.clone)) {
                                    dst[key] = dstProp = srcProp.clone();
                                    continue;
                                }
                                else {
                                    dst[key] = dstProp = {};
                                }
                            }
                            if (wijmo.isObject(dstProp)) {
                                _merge(dst[key], srcProp, overwrite);
                            }
                        }
                    }
                }
                return dst;
            }
            var globCell;
            /**
            * Provides a functionality to export the @see:FlexGrid to PDF.
            */
            var FlexGridPdfConverter = /** @class */ (function () {
                function FlexGridPdfConverter() {
                }
                /**
                * Draws the @see:FlexGrid to an existing @see:PdfDocument at the
                * (0, @wijmo.pdf.PdfDocument.y) coordinates.
                *
                * If width is not specified, then grid will be rendered in actual size,
                * breaking into pages as needed. If height is not specified, then grid will be
                * scaled to fit the width, breaking into pages vertically as needed.
                * If both, width and height are determined, then grid will be scaled to fit
                * the specified rectangle without any page breaks.
                *
                * <pre>
                * var doc = new wijmo.pdf.PdfDocument({
                *    ended: function (sender, args) {
                *       wijmo.pdf.saveBlob(args.blob, 'FlexGrid.pdf');
                *    }
                * });
                *
                * wijmo.grid.pdf.FlexGridPdfConverter.draw(grid, doc, null, null, {
                *    maxPages: 10,
                *    styles: {
                *       cellStyle: {
                *          backgroundColor: '#ffffff',
                *          borderColor: '#c6c6c6'
                *       },
                *       headerCellStyle: {
                *          backgroundColor: '#eaeaea'
                *       }
                *    }
                * });
                * </pre>
                *
                * @param flex The @see:FlexGrid instance to export.
                * @param doc The @see:PdfDocument instance to draw in.
                * @param width The width of the drawing area in points.
                * @param height The height of the drawing area in points.
                * @param settings The draw settings.
                */
                FlexGridPdfConverter.draw = function (flex, doc, width, height, settings) {
                    wijmo.assert(!!flex, 'The flex argument cannot be null.');
                    wijmo.assert(!!doc, 'The doc argument cannot be null.');
                    var options = _merge({}, settings); // clone
                    _merge(options, this.DefaultDrawSettings);
                    if (width == null) {
                        options.scaleMode = ScaleMode.ActualSize;
                    }
                    else {
                        options.scaleMode = height == null ? ScaleMode.PageWidth : ScaleMode.SinglePage;
                    }
                    try {
                        if (options.recalculateStarWidths) {
                            flex.columns._updateStarSizes(wijmo.pdf.ptToPx(doc.width));
                        }
                        this._draw(flex, doc, null, width, height, options);
                    }
                    finally {
                        if (options.recalculateStarWidths) {
                            flex.invalidate(true); // Rollback changes.
                        }
                    }
                };
                /**
                * Draws the @see:FlexGrid to an existing @see:PdfDocument instance at the
                * specified coordinates.
                *
                * If width is not specified, then grid will be rendered in actual size
                * without any page breaks.
                * If height is not specified, then grid will be scaled to fit the width
                * without any page breaks.
                * If both, width and height are determined, then grid will be scaled to fit
                * the specified rectangle without any page breaks.
                *
                * <pre>
                * var doc = new wijmo.pdf.PdfDocument({
                *    ended: function (sender, args) {
                *       wijmo.pdf.saveBlob(args.blob, 'FlexGrid.pdf');
                *    }
                * });
                *
                * wijmo.grid.pdf.FlexGridPdfConverter.drawToPosition(grid, doc, new wijmo.Point(0, 0), null, null, {
                *    maxPages: 10,
                *    styles: {
                *       cellStyle: {
                *          backgroundColor: '#ffffff',
                *          borderColor: '#c6c6c6'
                *       },
                *       headerCellStyle: {
                *          backgroundColor: '#eaeaea'
                *       }
                *    }
                * });
                * </pre>
                *
                * @param flex The @see:FlexGrid instance to export.
                * @param doc The @see:PdfDocument instance to draw in.
                * @param point The position to draw at, in points.
                * @param width The width of the drawing area in points.
                * @param height The height of the drawing area in points.
                * @param settings The draw settings.
                */
                FlexGridPdfConverter.drawToPosition = function (flex, doc, point, width, height, settings) {
                    wijmo.assert(!!flex, 'The flex argument cannot be null.');
                    wijmo.assert(!!doc, 'The doc argument cannot be null.');
                    wijmo.assert(!!point, 'The point argument cannot be null.');
                    var options = _merge({}, settings); // clone
                    _merge(options, this.DefaultDrawSettings);
                    if (width == null) {
                        options.scaleMode = ScaleMode.ActualSize;
                    }
                    else {
                        options.scaleMode = height == null ? ScaleMode.PageWidth : ScaleMode.SinglePage;
                    }
                    try {
                        if (options.recalculateStarWidths) {
                            flex.columns._updateStarSizes(wijmo.pdf.ptToPx(doc.width));
                        }
                        this._draw(flex, doc, point, width, height, options);
                    }
                    finally {
                        if (options.recalculateStarWidths) {
                            flex.invalidate(true); // Rollback changes.
                        }
                    }
                };
                /**
                * Exports the @see:FlexGrid to PDF.
                *
                * <pre>
                * wijmo.grid.pdf.FlexGridPdfConverter.export(grid, 'FlexGrid.pdf', {
                *    scaleMode: wijmo.grid.pdf.ScaleMode.PageWidth,
                *    maxPages: 10,
                *    styles: {
                *       cellStyle: {
                *          backgroundColor: '#ffffff',
                *          borderColor: '#c6c6c6'
                *       },
                *       headerCellStyle: {
                *          backgroundColor: '#eaeaea'
                *       }
                *    },
                *    documentOptions: {
                *       info: {
                *          title: 'Sample'
                *       }
                *    }
                * });
                * </pre>
                *
                * @param flex The @see:FlexGrid instance to export.
                * @param fileName Name of the file to export.
                * @param settings The export settings.
                */
                FlexGridPdfConverter.export = function (flex, fileName, settings) {
                    wijmo.assert(!!flex, 'The flex argument cannot be null.');
                    wijmo.assert(!!fileName, 'The fileName argument cannot be empty.');
                    settings = _merge({}, settings); // clone
                    _merge(settings, this.DefaultExportSettings);
                    var originalEnded = settings.documentOptions.ended;
                    settings.documentOptions.ended = function (sender, args) {
                        if (!originalEnded || (originalEnded.apply(doc, [sender, args]) !== false)) {
                            wijmo.pdf.saveBlob(args.blob, fileName);
                        }
                    };
                    var doc = new wijmo.pdf.PdfDocument(settings.documentOptions);
                    try {
                        if (settings.recalculateStarWidths) {
                            flex.columns._updateStarSizes(wijmo.pdf.ptToPx(doc.width));
                        }
                        this._draw(flex, doc, null, null, null, settings);
                        doc.end();
                    }
                    finally {
                        if (settings.recalculateStarWidths) {
                            flex.invalidate(true); // Rollback changes.
                        }
                    }
                };
                FlexGridPdfConverter._draw = function (flex, doc, point, width, height, settings) {
                    var isPositionedMode = point != null, clSize = new wijmo.Size(doc.width, doc.height);
                    if (!point) {
                        point = new wijmo.Point(0, doc.y);
                        //point = new Point(doc.x, doc.y); // use current position
                    }
                    if (wijmo.isArray(settings.embeddedFonts)) {
                        settings.embeddedFonts.forEach(function (font) {
                            doc.registerFont(font);
                        });
                    }
                    // ** initialize
                    var range = RowRange.getSelection(flex, settings.exportMode), gr = this._getGridRenderer(flex, settings, range, this.BorderWidth, true), rect = new wijmo.Rect(point.x || 0, point.y || 0, width || clSize.width, height || clSize.height), scaleFactor = this._getScaleFactor(gr, settings.scaleMode, rect), pages = this._getPages(gr, range, rect, settings, isPositionedMode, scaleFactor);
                    // create an element to calculate FlexGrid's cell style.
                    globCell = document.createElement('div');
                    globCell.setAttribute(grid_1.FlexGrid._WJS_MEASURE, 'true');
                    globCell.style.visibility = 'hidden';
                    flex.hostElement.appendChild(globCell);
                    try {
                        // ** render
                        for (var i = 0; i < pages.length; i++) {
                            if (i > 0) {
                                doc.addPage();
                            }
                            var page = pages[i], x = page.pageCol === 0 ? rect.left : 0, y = page.pageRow === 0 ? rect.top : 0;
                            doc.saveState();
                            doc.paths.rect(0, 0, clSize.width, clSize.height).clip();
                            doc.scale(scaleFactor, scaleFactor, new wijmo.Point(x, y));
                            doc.translate(x, y);
                            var gridPage = this._getGridRenderer(flex, settings, page.range, this.BorderWidth, i === pages.length - 1);
                            gridPage.render(doc);
                            doc.restoreState();
                            // move document cursor to the grid's left bottom corner.
                            doc.x = x;
                            doc.y = y + gridPage.renderSize.height * scaleFactor;
                        }
                    }
                    finally {
                        if (globCell) {
                            wijmo.removeChild(globCell);
                            globCell = null;
                        }
                    }
                };
                FlexGridPdfConverter._getScaleFactor = function (gr, scaleMode, rect) {
                    var factor = 1;
                    if (scaleMode === ScaleMode.ActualSize) {
                        return factor;
                    }
                    var size = gr.renderSize;
                    if (scaleMode === ScaleMode.SinglePage) {
                        var f = Math.min(rect.width / size.width, rect.height / size.height);
                        if (f < 1) {
                            factor = f;
                        }
                    }
                    else {
                        var f = rect.width / size.width;
                        if (f < 1) {
                            factor = f;
                        }
                    }
                    return factor;
                };
                FlexGridPdfConverter._getPages = function (gr, ranges, rect, settings, isPositionedMode, scaleFactor) {
                    var _this = this;
                    var rowBreaks = [], colBreaks = [], p2u = wijmo.pdf.pxToPt, flex = gr.flex, showColumnHeader = gr.showColumnHeader, showRowHeader = gr.showRowHeader, colHeaderHeight = showColumnHeader ? p2u(flex.columnHeaders.height) : 0, rowHeaderWidth = showRowHeader ? p2u(flex.rowHeaders.width) : 0, breakRows = settings.scaleMode === ScaleMode.ActualSize || settings.scaleMode === ScaleMode.PageWidth, breakColumns = settings.scaleMode === ScaleMode.ActualSize, zeroColWidth = (rect.width - rect.left) * (1 / scaleFactor), // the width of the leftmost grids
                    zeroRowHeight = (rect.height - rect.top) * (1 / scaleFactor), // the height of the topmost grids
                    rectWidth = rect.width * (1 / scaleFactor), rectHeight = rect.height * (1 / scaleFactor), totalHeight = colHeaderHeight, totalWidth = rowHeaderWidth, 
                    // Normally in ActualSize mode we are inserting page breaks before partially visible columns\ rows to display them completely.
                    // But there is no page breaks in positioned mode, so we need to omit this to fit the maximum amount of content in a drawing area.
                    dontBreakBeforePartiallyVisibleElements = isPositionedMode && (settings.scaleMode == ScaleMode.ActualSize);
                    if (breakRows) {
                        var visibleRowsCnt = 0;
                        ranges.forEach(flex.cells, function (row, rng, rIdx, sIdx) {
                            var renderAreaHeight = rowBreaks.length ? rectHeight : zeroRowHeight;
                            if (PanelSection.isRenderableRow(row)) {
                                var rowHeight = p2u(row.renderHeight);
                                visibleRowsCnt++;
                                totalHeight += rowHeight;
                                if (showColumnHeader || visibleRowsCnt > 1) {
                                    totalHeight -= _this.BorderWidth; // border collapse
                                }
                                if (totalHeight > renderAreaHeight) {
                                    if (colHeaderHeight + rowHeight > renderAreaHeight || dontBreakBeforePartiallyVisibleElements) {
                                        rowBreaks.push(sIdx);
                                        totalHeight = colHeaderHeight;
                                    }
                                    else {
                                        rowBreaks.push(sIdx - 1);
                                        totalHeight = colHeaderHeight + rowHeight;
                                    }
                                    if (showColumnHeader) {
                                        totalHeight -= _this.BorderWidth; // border collapse
                                    }
                                }
                            }
                        });
                    }
                    var len = ranges.length() - 1;
                    if (len < 0) {
                        len = 0;
                    }
                    if (!rowBreaks.length || (rowBreaks[rowBreaks.length - 1] !== len)) {
                        rowBreaks.push(len);
                    }
                    if (breakColumns) {
                        var visibleColumnsCnt = 0;
                        for (var i = ranges.leftCol; i <= ranges.rightCol; i++) {
                            var col = flex.columns[i];
                            if (col.isVisible) {
                                var colWidth = p2u(col.renderWidth), renderAreaWidth = colBreaks.length ? rectWidth : zeroColWidth;
                                visibleColumnsCnt++;
                                totalWidth += colWidth;
                                if (showRowHeader || visibleColumnsCnt > 1) {
                                    totalWidth -= this.BorderWidth; // border collapse
                                }
                                if (totalWidth > renderAreaWidth) {
                                    if (rowHeaderWidth + colWidth > renderAreaWidth || dontBreakBeforePartiallyVisibleElements) {
                                        colBreaks.push(i);
                                        totalWidth = rowHeaderWidth;
                                    }
                                    else {
                                        colBreaks.push(i - 1);
                                        totalWidth = rowHeaderWidth + colWidth;
                                    }
                                    if (showRowHeader) {
                                        totalWidth -= this.BorderWidth; // border collapse
                                    }
                                }
                            }
                        }
                    }
                    if (!colBreaks.length || (colBreaks[colBreaks.length - 1] !== ranges.rightCol)) {
                        colBreaks.push(ranges.rightCol);
                    }
                    var pages = [], flag = false, pageCount = 1, maxPages = isPositionedMode && (settings.maxPages > 0) ? 1 : settings.maxPages;
                    for (var i = 0; i < rowBreaks.length && !flag; i++) {
                        for (var j = 0; j < colBreaks.length && !flag; j++, pageCount++) {
                            if (!(flag = pageCount > maxPages)) {
                                var r = i == 0 ? 0 : rowBreaks[i - 1] + 1, c = j == 0 ? ranges.leftCol : colBreaks[j - 1] + 1;
                                pages.push(new PdfPageRowRange(ranges.subrange(r, rowBreaks[i] - r + 1, c, colBreaks[j]), j, i));
                            }
                        }
                    }
                    return pages;
                };
                FlexGridPdfConverter._getGridRenderer = function (flex, settings, range, borderWidth, lastPage) {
                    var t = ((grid_1.multirow && (flex instanceof grid_1.multirow.MultiRow) && MultiRowRenderer) || // MultiRow
                        (grid_1.sheet && (flex instanceof grid_1.sheet.FlexSheet) && FlexSheetRenderer) || // FlexSheet
                        (wijmo.olap && (flex instanceof wijmo.olap.PivotGrid) && PivotGridRenderer) || // PivotGrid
                        FlexGridRenderer); // Finally a regular FlexGrid
                    return new t(flex, settings, range, borderWidth, lastPage);
                };
                FlexGridPdfConverter.BorderWidth = 1; // pt, hardcoded because of border collapsing.
                FlexGridPdfConverter.DefaultDrawSettings = {
                    maxPages: Number.MAX_VALUE,
                    exportMode: ExportMode.All,
                    repeatMergedValuesAcrossPages: true,
                    recalculateStarWidths: true,
                    styles: {
                        cellStyle: {
                            font: new wijmo.pdf.PdfFont(),
                            padding: 1.5,
                            verticalAlign: 'middle'
                        },
                        headerCellStyle: {
                            font: { weight: 'bold' } // Don't use PdfFont. It's necessary to specify exclusive properties only, no default values (in order to merge cell styles properly).
                        } /*,
                        errorCellStyle: {
                            backgroundColor: 'rgba(255, 0, 0, 0.3)'
                        }*/
                    }
                };
                FlexGridPdfConverter.DefaultExportSettings = _merge({
                    scaleMode: ScaleMode.PageWidth,
                    documentOptions: {
                        compress: false,
                        pageSettings: {
                            margins: {
                                left: 36,
                                right: 36,
                                top: 18,
                                bottom: 18
                            }
                        }
                    }
                }, FlexGridPdfConverter.DefaultDrawSettings);
                return FlexGridPdfConverter;
            }());
            pdf.FlexGridPdfConverter = FlexGridPdfConverter;
            var FlexGridRenderer = /** @class */ (function () {
                function FlexGridRenderer(flex, settings, range, borderWidth, lastPage) {
                    this._flex = flex;
                    this._borderWidth = borderWidth;
                    this._lastPage = lastPage;
                    this._settings = settings || {};
                    this._topLeft = new PanelSectionRenderer(this, flex.topLeftCells, this.showRowHeader && this.showColumnHeader
                        ? new RowRange(flex, [new grid_1.CellRange(0, 0, flex.topLeftCells.rows.length - 1, flex.topLeftCells.columns.length - 1)])
                        : new RowRange(flex, []), borderWidth);
                    this._rowHeader = new PanelSectionRenderer(this, flex.rowHeaders, this.showRowHeader
                        ? range.clone(0, flex.rowHeaders.columns.length - 1)
                        : new RowRange(flex, []), borderWidth);
                    this._columnHeader = new PanelSectionRenderer(this, flex.columnHeaders, this.showColumnHeader
                        ? new RowRange(flex, [new grid_1.CellRange(0, range.leftCol, flex.columnHeaders.rows.length - 1, range.rightCol)])
                        : new RowRange(flex, []), borderWidth);
                    this._cells = new PanelSectionRenderer(this, flex.cells, range, borderWidth);
                    this._bottomLeft = new PanelSectionRenderer(this, flex.bottomLeftCells, this.showRowHeader && this.showColumnFooter
                        ? new RowRange(flex, [new grid_1.CellRange(0, 0, flex.bottomLeftCells.rows.length - 1, flex.bottomLeftCells.columns.length - 1)])
                        : new RowRange(flex, []), borderWidth);
                    this._columnFooter = new PanelSectionRenderer(this, flex.columnFooters, this.showColumnFooter
                        ? new RowRange(flex, [new grid_1.CellRange(0, range.leftCol, flex.columnFooters.rows.length - 1, range.rightCol)])
                        : new RowRange(flex, []), borderWidth);
                }
                Object.defineProperty(FlexGridRenderer.prototype, "settings", {
                    get: function () {
                        return this._settings;
                    },
                    enumerable: true,
                    configurable: true
                });
                FlexGridRenderer.prototype.render = function (doc) {
                    var offsetX = Math.max(0, Math.max(this._topLeft.renderSize.width, this._rowHeader.renderSize.width) - this._borderWidth), // left section width
                    offsetY = Math.max(0, Math.max(this._topLeft.renderSize.height, this._columnHeader.renderSize.height) - this._borderWidth); // top section height
                    this._topLeft.render(doc, 0, 0);
                    this._rowHeader.render(doc, 0, offsetY);
                    this._columnHeader.render(doc, offsetX, 0);
                    this._cells.render(doc, offsetX, offsetY);
                    // bottomLeft + columnFooter
                    offsetY = Math.max(0, offsetY + this._cells.renderSize.height - this._borderWidth);
                    this._bottomLeft.render(doc, 0, offsetY);
                    this._columnFooter.render(doc, offsetX, offsetY);
                };
                Object.defineProperty(FlexGridRenderer.prototype, "flex", {
                    get: function () {
                        return this._flex;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridRenderer.prototype, "renderSize", {
                    get: function () {
                        var height = Math.max(this._topLeft.renderSize.height, this._columnHeader.renderSize.height) // top section height
                            + Math.max(this._rowHeader.renderSize.height, this._cells.renderSize.height) // middle section height
                            + Math.max(this._bottomLeft.renderSize.height, this._columnFooter.renderSize.height), // bottom section height
                        width = Math.max(this._topLeft.renderSize.width, this._rowHeader.renderSize.width) // left section width
                            + Math.max(this._columnHeader.renderSize.width, this._cells.renderSize.width); // right section width
                        if (this._columnHeader.visibleRows > 0) {
                            height -= this._borderWidth;
                        }
                        if (this._columnFooter.visibleRows > 0) {
                            height -= this._borderWidth;
                        }
                        if (this._rowHeader.visibleColumns > 0) {
                            width -= this._borderWidth;
                        }
                        return new wijmo.Size(width, height);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridRenderer.prototype, "showColumnHeader", {
                    get: function () {
                        return !!(this._flex.headersVisibility & grid_1.HeadersVisibility.Column);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridRenderer.prototype, "showRowHeader", {
                    get: function () {
                        return !!(this._flex.headersVisibility & grid_1.HeadersVisibility.Row);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridRenderer.prototype, "showColumnFooter", {
                    get: function () {
                        return this._lastPage && this._flex.columnFooters.rows.length > 0;
                    },
                    enumerable: true,
                    configurable: true
                });
                FlexGridRenderer.prototype.alignMergedTextToTheTopRow = function (panel) {
                    return false;
                };
                FlexGridRenderer.prototype.getCellValue = function (panel, col, row) {
                    return panel.getCellData(row, col, true);
                };
                FlexGridRenderer.prototype.getColumn = function (panel, col, row) {
                    return panel.columns[col];
                };
                FlexGridRenderer.prototype.isAlternatingRow = function (row) {
                    return row.index % 2 != 0;
                };
                FlexGridRenderer.prototype.isGroupRow = function (row) {
                    return row instanceof grid_1.GroupRow && row.hasChildren; // Group row with no children should be treated as a data row (hierarchical grid)
                };
                FlexGridRenderer.prototype.isMergeableCell = function (col, row) {
                    return true; // To simplify the support of a new FlexGrid-derived controls (#268929.2)
                    //return row.allowMerging || col.allowMerging || row instanceof GroupRow;
                };
                FlexGridRenderer.prototype.getCellStyle = function (panel, row, column) {
                    var styles = this.settings.styles, result = _merge({}, styles.cellStyle), // merge cell styles
                    grid = this._flex;
                    switch (panel.cellType) {
                        case grid_1.CellType.Cell:
                            if (this.isGroupRow(row)) {
                                _merge(result, styles.groupCellStyle, true);
                            }
                            else {
                                if (this.isAlternatingRow(row)) {
                                    _merge(result, styles.altCellStyle, true);
                                }
                            }
                            break;
                        case grid_1.CellType.ColumnHeader:
                        case grid_1.CellType.RowHeader:
                        case grid_1.CellType.TopLeft:
                        case grid_1.CellType.BottomLeft:
                            _merge(result, styles.headerCellStyle, true);
                            break;
                        case grid_1.CellType.ColumnFooter:
                            _merge(result, styles.headerCellStyle, true);
                            _merge(result, styles.footerCellStyle, true);
                            break;
                    }
                    if (!this.settings.customCellContent && grid._getShowErrors() && grid._getError(panel, row.index, column.index)) {
                        _merge(result, styles.errorCellStyle, true);
                    }
                    return result;
                };
                return FlexGridRenderer;
            }());
            var FlexSheetRenderer = /** @class */ (function (_super) {
                __extends(FlexSheetRenderer, _super);
                function FlexSheetRenderer(flex, settings, range, borderWidth, lastPage) {
                    return _super.call(this, flex, settings, range, borderWidth, lastPage) || this;
                }
                //#region override
                FlexSheetRenderer.prototype.getCellValue = function (panel, col, row) {
                    if (panel.cellType === grid_1.CellType.Cell) {
                        // Treat the data row as a column header row 
                        if (panel.rows[row] instanceof grid_1.sheet.HeaderRow) {
                            return this.flex.columnHeaders.getCellData(row, col, true);
                        }
                        return this.flex.getCellValue(row, col, true); // formula evaluation
                    }
                    return _super.prototype.getCellValue.call(this, panel, col, row);
                };
                FlexSheetRenderer.prototype.getCellStyle = function (panel, row, column) {
                    var result = _super.prototype.getCellStyle.call(this, panel, row, column), table = this.flex.selectedSheet.findTable(row.index, column.index);
                    if (table) {
                        var ri = row.index - table.range.topRow, ci = column.index - table.range.leftCol, style = table._getTableCellAppliedStyles(ri, ci);
                        if (style) {
                            Object.keys(style).forEach(function (k) {
                                if (k.toLowerCase().indexOf('color') >= 0) {
                                    style[k] = table._getStrColor(style[k]);
                                }
                            });
                            style.font = new wijmo.pdf.PdfFont(style.fontFamily, wijmo.pdf._asPt(style.fontSize, true, undefined), style.fontStyle, style.fontWeight);
                        }
                        _merge(result, style, true);
                    }
                    return result;
                };
                Object.defineProperty(FlexSheetRenderer.prototype, "showColumnHeader", {
                    // hide all headers\ footers
                    get: function () {
                        return false;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexSheetRenderer.prototype, "showRowHeader", {
                    get: function () {
                        return false;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexSheetRenderer.prototype, "showColumnFooter", {
                    get: function () {
                        return false;
                    },
                    enumerable: true,
                    configurable: true
                });
                return FlexSheetRenderer;
            }(FlexGridRenderer));
            var MultiRowRenderer = /** @class */ (function (_super) {
                __extends(MultiRowRenderer, _super);
                function MultiRowRenderer(multirow, settings, range, borderWidth, lastPage) {
                    return _super.call(this, multirow, settings, range, borderWidth, lastPage) || this;
                }
                //#region override
                MultiRowRenderer.prototype.getColumn = function (panel, col, row) {
                    return this.flex.getBindingColumn(panel, row, col);
                };
                MultiRowRenderer.prototype.isAlternatingRow = function (row) {
                    if (row instanceof grid_1.multirow._MultiRow) {
                        return row.dataIndex % 2 != 0;
                    }
                    return _super.prototype.isAlternatingRow.call(this, row);
                };
                MultiRowRenderer.prototype.isMergeableCell = function (col, row) {
                    return true;
                };
                return MultiRowRenderer;
            }(FlexGridRenderer));
            var PivotGridRenderer = /** @class */ (function (_super) {
                __extends(PivotGridRenderer, _super);
                function PivotGridRenderer(pivot, settings, range, borderWidth, lastPage) {
                    return _super.call(this, pivot, settings, range, borderWidth, lastPage) || this;
                }
                //#region override
                PivotGridRenderer.prototype.alignMergedTextToTheTopRow = function (panel) {
                    return !this.flex.centerHeadersVertically && (panel.cellType === grid_1.CellType.ColumnHeader || panel.cellType === grid_1.CellType.RowHeader);
                };
                return PivotGridRenderer;
            }(FlexGridRenderer));
            var PanelSection = /** @class */ (function () {
                function PanelSection(panel, range) {
                    this._panel = panel;
                    this._range = range.clone();
                }
                PanelSection.isRenderableRow = function (row) {
                    return row.isVisible && !(row instanceof grid_1._NewRowTemplate);
                };
                Object.defineProperty(PanelSection.prototype, "visibleRows", {
                    get: function () {
                        var _this = this;
                        if (this._visibleRows == null) {
                            this._visibleRows = 0;
                            this._range.forEach(this._panel, function (row) {
                                if (_this.isRenderableRow(row)) {
                                    _this._visibleRows++;
                                }
                            });
                        }
                        return this._visibleRows;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PanelSection.prototype, "visibleColumns", {
                    get: function () {
                        if (this._visibleColumns == null) {
                            this._visibleColumns = 0;
                            if (this._range.isValid) {
                                for (var i = this._range.leftCol; i <= this._range.rightCol; i++) {
                                    if (this._panel.columns[i].isVisible) {
                                        this._visibleColumns++;
                                    }
                                }
                            }
                        }
                        return this._visibleColumns;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PanelSection.prototype, "size", {
                    // pt units
                    get: function () {
                        if (this._size == null) {
                            var sz = this._range.getRenderSize(this._panel);
                            this._size = new wijmo.Size(wijmo.pdf.pxToPt(sz.width), wijmo.pdf.pxToPt(sz.height));
                        }
                        return this._size;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PanelSection.prototype, "range", {
                    get: function () {
                        return this._range;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PanelSection.prototype, "panel", {
                    get: function () {
                        return this._panel;
                    },
                    enumerable: true,
                    configurable: true
                });
                PanelSection.prototype.isRenderableRow = function (row) {
                    return PanelSection.isRenderableRow(row);
                };
                return PanelSection;
            }());
            var PanelSectionRenderer = /** @class */ (function (_super) {
                __extends(PanelSectionRenderer, _super);
                function PanelSectionRenderer(gr, panel, range, borderWidth) {
                    var _this = _super.call(this, panel, range) || this;
                    _this._gr = gr;
                    _this._borderWidth = borderWidth;
                    return _this;
                }
                Object.defineProperty(PanelSectionRenderer.prototype, "gr", {
                    get: function () {
                        return this._gr;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PanelSectionRenderer.prototype, "renderSize", {
                    // pt units
                    get: function () {
                        if (this._renderSize == null) {
                            this._renderSize = this.size.clone();
                            if (this.visibleColumns > 1) {
                                this._renderSize.width -= this._borderWidth * (this.visibleColumns - 1);
                            }
                            if (this.visibleRows > 1) {
                                this._renderSize.height -= this._borderWidth * (this.visibleRows - 1);
                            }
                        }
                        return this._renderSize;
                    },
                    enumerable: true,
                    configurable: true
                });
                PanelSectionRenderer.prototype.getRangeWidth = function (leftCol, rightCol) {
                    var width = 0, visibleColumns = 0, pnl = this.panel;
                    for (var i = leftCol; i <= rightCol; i++) {
                        var col = pnl.columns[i];
                        if (col.isVisible) {
                            visibleColumns++;
                            width += col.renderWidth;
                        }
                    }
                    width = wijmo.pdf.pxToPt(width);
                    if (visibleColumns > 1) {
                        width -= this._borderWidth * (visibleColumns - 1);
                    }
                    return width;
                };
                PanelSectionRenderer.prototype.getRangeHeight = function (topRow, bottomRow) {
                    var height = 0, visibleRows = 0, pnl = this.panel;
                    for (var i = topRow; i <= bottomRow; i++) {
                        var row = pnl.rows[i];
                        if (this.isRenderableRow(row)) {
                            visibleRows++;
                            height += row.renderHeight;
                        }
                    }
                    height = wijmo.pdf.pxToPt(height);
                    if (visibleRows > 1) {
                        height = height - this._borderWidth * (visibleRows - 1);
                    }
                    return height;
                };
                PanelSectionRenderer.prototype.render = function (doc, x, y) {
                    var _this = this;
                    var ranges = this.range, pnl = this.panel, mngr = new GetMergedRangeProxy(this._gr.flex), curCellRange = new CellRangeExt(pnl, 0, 0, 0, 0), cellRenderer = new CellRenderer(this, doc, this._borderWidth);
                    if (!ranges.isValid) {
                        return;
                    }
                    var pY = {}; // tracks the current Y position for each column
                    for (var c = ranges.leftCol; c <= ranges.rightCol; c++) {
                        pY[c] = y;
                    }
                    ranges.forEach(pnl, function (row, rng, r) {
                        if (!_this.isRenderableRow(row)) {
                            return;
                        }
                        var pX = x;
                        for (var c = ranges.leftCol; c <= ranges.rightCol; c++) {
                            var col = _this.gr.getColumn(pnl, c, r), height = undefined, width = undefined, value, needRender = false, skipC = undefined;
                            if (!col.isVisible) {
                                continue;
                            }
                            var cellValue = _this._getCellValue(c, r), mergedRng = null;
                            if (_this.gr.isMergeableCell(col, row) && (mergedRng = mngr.getMergedRange(pnl, r, c))) {
                                curCellRange.copyFrom(mergedRng);
                                if (mergedRng.topRow !== mergedRng.bottomRow) {
                                    if (mergedRng.firstVisibleRow /*.topRow*/ === r || r === rng.topRow) {
                                        needRender = true;
                                        value = _this.gr.settings.repeatMergedValuesAcrossPages
                                            ? cellValue
                                            : (mergedRng.firstVisibleRow /*.topRow*/ === r ? cellValue : ''); // set value to the very fist cell of the merged range only
                                        height = _this.getRangeHeight(r, Math.min(mergedRng.bottomRow, rng.bottomRow));
                                        width = _this.getRangeWidth(c, c);
                                    }
                                    else {
                                        width = _this.getRangeWidth(c, c); // an absorbed cell
                                    }
                                }
                                else {
                                    // c === mrg.leftCol means the very first cell of the range, otherwise it is the remains of the range spreaded between multiple pages
                                    needRender = true;
                                    value = _this.gr.settings.repeatMergedValuesAcrossPages
                                        ? cellValue
                                        : (c === mergedRng.leftCol ? cellValue : ''); // set value to the very fist cell of the merged range only
                                    height = _this.getRangeHeight(r, r);
                                    width = _this.getRangeWidth(Math.max(ranges.leftCol, mergedRng.leftCol), Math.min(ranges.rightCol, mergedRng.rightCol));
                                    // skip absorbed cells until the end of the merged range or page (which comes first)
                                    skipC = Math.min(ranges.rightCol, mergedRng.rightCol); // to update loop variable later
                                    for (var t = c + 1; t <= skipC; t++) {
                                        pY[t] += height - _this._borderWidth; // collapse borders
                                    }
                                }
                            }
                            else {
                                curCellRange.setRange(r, c, r, c);
                                needRender = true;
                                value = cellValue;
                                height = _this.getRangeHeight(r, r);
                                width = _this.getRangeWidth(c, c);
                            }
                            if (needRender) {
                                cellRenderer.renderCell(value, row, col, curCellRange, new wijmo.Rect(pX, pY[c], width, height));
                            }
                            if (height) {
                                pY[c] += height - _this._borderWidth; // collapse borders
                            }
                            if (width) {
                                pX += width - _this._borderWidth; // collapse borders
                            }
                            if (skipC) {
                                c = skipC;
                            }
                        }
                    });
                };
                PanelSectionRenderer.prototype._getCellValue = function (col, row) {
                    var pnl = this.panel, value = this.gr.getCellValue(pnl, col, row);
                    if (!value && pnl.cellType === grid_1.CellType.Cell) {
                        var flexRow = pnl.rows[row];
                        // seems that FlexGrid doesn't provide an API for getting group header text, so build it manually
                        if (flexRow instanceof grid_1.GroupRow && flexRow.dataItem && flexRow.dataItem.groupDescription && (col === pnl.columns.firstVisibleIndex)) {
                            var propName = flexRow.dataItem.groupDescription.propertyName, column = pnl.columns.getColumn(propName);
                            if (column && column.header) {
                                propName = column.header;
                            }
                            value = propName + ': ' + flexRow.dataItem.name + ' (' + flexRow.dataItem.items.length + ' items)';
                        }
                    }
                    return value;
                };
                return PanelSectionRenderer;
            }(PanelSection));
            var CellRenderer = /** @class */ (function () {
                function CellRenderer(panelRenderer, area, borderWidth) {
                    this._pr = panelRenderer;
                    this._area = area;
                    this._borderWidth = borderWidth;
                }
                CellRenderer.prototype.renderCell = function (value, row, column, rng, r) {
                    var formatEventArgs, grid = row.grid, panel = this._pr.panel, getGridCell = function () {
                        var ri = rng.topRow, ci = rng.leftCol, cell = panel.getCellElement(ri, ci);
                        if (!cell) {
                            globCell.innerHTML = globCell.className = globCell.style.cssText = '';
                            grid.cellFactory.updateCell(panel, ri, ci, globCell);
                        }
                        return cell || globCell;
                    }, getComputedCellStyle = function (cell) {
                        cell.className = cell.className.replace('wj-state-selected', '');
                        cell.className = cell.className.replace('wj-state-multi-selected', '');
                        return window.getComputedStyle(cell); // live object
                    }, customContent = !!this._pr.gr.settings.customCellContent, gridCell = null, style = this._pr.gr.getCellStyle(panel, row, column);
                    if (customContent) {
                        gridCell = getGridCell();
                    }
                    if (customContent) {
                        value = gridCell.textContent.trim(); // remove control characters
                        if (!value && this._isBooleanCell(column, row, panel)) {
                            value = this._extractCheckboxValue(gridCell) + '';
                        }
                    }
                    if (customContent) {
                        var css = getComputedCellStyle(gridCell);
                        // change the exported (public) properties only
                        style.color = css.color;
                        style.backgroundColor = css.backgroundColor;
                        style.borderColor = css.borderColor || css.borderRightColor || css.borderBottomColor || css.borderLeftColor || css.borderTopColor;
                        style.font = new wijmo.pdf.PdfFont(css.fontFamily, wijmo.pdf._asPt(css.fontSize, true, undefined), css.fontStyle, css.fontWeight);
                        style.textAlign = css.textAlign;
                    }
                    // harcoded border styles
                    style.boxSizing = 'border-box';
                    style.borderWidth = this._borderWidth;
                    style.borderStyle = 'solid';
                    // horizontal alignment
                    if (!style.textAlign && !(row instanceof grid_1.GroupRow && !column.aggregate)) {
                        style.textAlign = column.getAlignment();
                    }
                    // add indent
                    if (panel.cellType === grid_1.CellType.Cell && grid.rows.maxGroupLevel >= 0 && rng.leftCol === grid.columns.firstVisibleIndex) {
                        var level = (row instanceof grid_1.GroupRow)
                            ? Math.max(row.level, 0) // group row cell
                            : grid.rows.maxGroupLevel + 1; // data cell
                        var basePadding = wijmo.pdf._asPt(style.paddingLeft || style.padding), levelPadding = wijmo.pdf.pxToPt(level * grid.treeIndent);
                        style.paddingLeft = basePadding + levelPadding;
                    }
                    var m = this._measureCell(value, column, row, panel, style, r), alignToTopRow = (rng.rowSpan > 1) && (rng.visibleRowsCount > 1) && this._pr.gr.alignMergedTextToTheTopRow(panel), topRowContentRect;
                    if (alignToTopRow) {
                        topRowContentRect = new wijmo.Rect(m.contentRect.left, m.contentRect.top, m.contentRect.width, m.contentRect.height / (rng.visibleRowsCount || 1));
                        m.textRect = this._calculateTextRect(value, column, row, panel, topRowContentRect, style);
                    }
                    if (wijmo.isFunction(this._pr.gr.settings.formatItem)) {
                        formatEventArgs = new PdfFormatItemEventArgs(panel, rng, gridCell, this._area, m.rect, m.contentRect, m.textRect.top, style, function () { return gridCell || getGridCell(); });
                        formatEventArgs.data = value;
                        this._pr.gr.settings.formatItem(formatEventArgs);
                        if (formatEventArgs.data !== value) {
                            value = wijmo.asString(formatEventArgs.data);
                            // recalculate text rect
                            m.textRect = this._calculateTextRect(value, column, row, panel, alignToTopRow ? topRowContentRect : m.contentRect, style);
                        }
                    }
                    this._renderCell(value, row, column, rng, m, style, formatEventArgs ? !formatEventArgs.cancel : true, formatEventArgs ? !formatEventArgs.cancelBorders : true);
                };
                CellRenderer.prototype._renderCell = function (value, row, column, rng, m, style, renderContent, renderBorders) {
                    if (!renderContent && !renderBorders) {
                        return;
                    }
                    if (this._isBooleanCellAndValue(value, column, row, this._pr.panel)) {
                        this._renderBooleanCell(value, m, style, renderContent, renderBorders);
                    }
                    else {
                        this._renderTextCell(value, m, style, renderContent, renderBorders);
                    }
                };
                CellRenderer.prototype._isBooleanCellAndValue = function (value, column, row, panel) {
                    return this._isBooleanCell(column, row, panel) && this._isBoolean(value);
                };
                CellRenderer.prototype._isBooleanCell = function (column, row, panel) {
                    return column.dataType === wijmo.DataType.Boolean && panel.cellType === grid_1.CellType.Cell && !this._pr.gr.isGroupRow(row);
                };
                CellRenderer.prototype._isBoolean = function (value) {
                    var lowerCase = wijmo.isString(value) && value.toLowerCase();
                    return lowerCase === 'true' || lowerCase === 'false' || value === true || value === false;
                };
                CellRenderer.prototype._measureCell = function (value, column, row, panel, style, rect) {
                    this._decompositeStyle(style);
                    var x = rect.left, //  wijmo.pdf._asPt(style.left),
                    y = rect.top, // wijmo.pdf._asPt(style.top),
                    height = rect.height, // wijmo.pdf._asPt(style.height),
                    width = rect.width, // wijmo.pdf._asPt(style.width),
                    brd = this._parseBorder(style), blw = brd.left.width, btw = brd.top.width, bbw = brd.bottom.width, brw = brd.right.width, pad = this._parsePadding(style), 
                    // content + padding
                    clientHeight = 0, clientWidth = 0, 
                    // content
                    contentHeight = 0, contentWidth = 0;
                    // setup client and content dimensions depending on boxing model.
                    if (style.boxSizing === 'content-box' || style.boxSizing === undefined) {
                        clientHeight = pad.top + height + pad.bottom;
                        clientWidth = pad.left + width + pad.right;
                        contentHeight = height;
                        contentWidth = width;
                    }
                    else {
                        if (style.boxSizing === 'border-box') {
                            // Browsers are using different approaches to calculate style.width and style.heigth properties. While Chrome and Firefox returns the total size, IE returns the content size only.
                            if (wijmo.pdf._IE && (style instanceof CSSStyleDeclaration)) {
                                clientHeight = pad.top + pad.bottom + height;
                                clientWidth = pad.left + pad.right + width;
                            }
                            else {
                                clientHeight = height - btw - bbw;
                                clientWidth = width - blw - brw;
                            }
                        }
                        else {
                            // padding-box? It is supported by Mozilla only.
                            throw 'Invalid value: ' + style.boxSizing;
                        }
                        contentHeight = clientHeight - pad.top - pad.bottom;
                        contentWidth = clientWidth - pad.left - pad.right;
                    }
                    var rect = new wijmo.Rect(x, y, width, height), clientRect = new wijmo.Rect(x + blw, y + btw, clientWidth, clientHeight), // rect - borders
                    contentRect = new wijmo.Rect(x + blw + pad.left, y + btw + pad.top, contentWidth, contentHeight), // rect - borders - padding
                    textRect = this._calculateTextRect(value, column, row, panel, contentRect, style);
                    return {
                        rect: rect,
                        clientRect: clientRect,
                        contentRect: contentRect,
                        textRect: textRect
                    };
                };
                //	Decomposites some properties to handle the situation when the style was created manually.
                CellRenderer.prototype._decompositeStyle = function (style) {
                    if (style) {
                        var val;
                        if (val = style.borderColor) {
                            // honor single properties
                            if (!style.borderLeftColor) {
                                style.borderLeftColor = val;
                            }
                            if (!style.borderRightColor) {
                                style.borderRightColor = val;
                            }
                            if (!style.borderTopColor) {
                                style.borderTopColor = val;
                            }
                            if (!style.borderBottomColor) {
                                style.borderBottomColor = val;
                            }
                        }
                        if (val = style.borderWidth) {
                            // honor single properties
                            if (!style.borderLeftWidth) {
                                style.borderLeftWidth = val;
                            }
                            if (!style.borderRightWidth) {
                                style.borderRightWidth = val;
                            }
                            if (!style.borderTopWidth) {
                                style.borderTopWidth = val;
                            }
                            if (!style.borderBottomWidth) {
                                style.borderBottomWidth = val;
                            }
                        }
                        if (val = style.borderStyle) {
                            // honor single properties
                            if (!style.borderLeftStyle) {
                                style.borderLeftStyle = val;
                            }
                            if (!style.borderRightStyle) {
                                style.borderRightStyle = val;
                            }
                            if (!style.borderTopStyle) {
                                style.borderTopStyle = val;
                            }
                            if (!style.borderBottomStyle) {
                                style.borderBottomStyle = val;
                            }
                        }
                        if (val = style.padding) {
                            // honor single properties
                            if (!style.paddingLeft) {
                                style.paddingLeft = val;
                            }
                            if (!style.paddingRight) {
                                style.paddingRight = val;
                            }
                            if (!style.paddingTop) {
                                style.paddingTop = val;
                            }
                            if (!style.paddingBottom) {
                                style.paddingBottom = val;
                            }
                        }
                    }
                };
                /*
                * Extracts the border values from the CSSStyleDeclaration object.
                *
                * @param style A value to extract from.
                * @return A @see:_IBorder object.
                */
                CellRenderer.prototype._parseBorder = function (style) {
                    var borders = {
                        left: { width: 0 },
                        top: { width: 0 },
                        bottom: { width: 0 },
                        right: { width: 0 }
                    };
                    if (style.borderLeftStyle !== 'none') {
                        borders.left = {
                            width: wijmo.pdf._asPt(style.borderLeftWidth),
                            style: style.borderLeftStyle,
                            color: style.borderLeftColor
                        };
                    }
                    if (style.borderTopStyle !== 'none') {
                        borders.top = {
                            width: wijmo.pdf._asPt(style.borderTopWidth),
                            style: style.borderTopStyle,
                            color: style.borderTopColor
                        };
                    }
                    if (style.borderBottomStyle !== 'none') {
                        borders.bottom = {
                            width: wijmo.pdf._asPt(style.borderBottomWidth),
                            style: style.borderBottomStyle,
                            color: style.borderBottomColor
                        };
                    }
                    if (style.borderRightStyle !== 'none') {
                        borders.right = {
                            width: wijmo.pdf._asPt(style.borderRightWidth),
                            style: style.borderRightStyle,
                            color: style.borderRightColor
                        };
                    }
                    return borders;
                };
                /*
                * Extracts the padding values from the CSSStyleDeclaration object.
                *
                * @param style Value to extract from.
                * @return The @see:IPadding object.
                */
                CellRenderer.prototype._parsePadding = function (style) {
                    return {
                        left: wijmo.pdf._asPt(style.paddingLeft),
                        top: wijmo.pdf._asPt(style.paddingTop),
                        bottom: wijmo.pdf._asPt(style.paddingBottom),
                        right: wijmo.pdf._asPt(style.paddingRight)
                    };
                };
                /*
                * Renders an empty cell.
                *
                * The following CSSStyleDeclaration properties are supported for now:
                *   left, top
                *   width, height
                *   border<Left \ Right\ Top\ Bottom>Style (if 'none' then no border, otherwise a solid border)
                *   border<Left\ Right\ Top\ Bottom>Width,
                *   border<Left\ Right\ Top\ Bottom>Color
                *   backgroundColor
                *   boxSizing (content-box + border-box)
                *   padding<Left\ Top\ Right\ Bottom>
                *   textAlign
                *   fontFamily, fontStyle, fontWeight, fontSize
                *
                * @param style A CSSStyleDeclaration object that represents the cell style and positioning.
                * @return A ICellInfo object that represents information about the cell's content.
                */
                CellRenderer.prototype._renderEmptyCell = function (m, style, renderContent, renderBorders) {
                    var x = m.rect.left, y = m.rect.top, clientWidth = m.clientRect.width, clientHeight = m.clientRect.height, blw = m.clientRect.left - m.rect.left, btw = m.clientRect.top - m.rect.top, bbw = (m.rect.top + m.rect.height) - (m.clientRect.top + m.clientRect.height), brw = (m.rect.left + m.rect.width) - (m.clientRect.left + m.clientRect.width);
                    if (renderBorders && (blw || brw || bbw || btw)) {
                        var blc = style.borderLeftColor || style.borderColor, brc = style.borderRightColor || style.borderColor, btc = style.borderTopColor || style.borderColor, bbc = style.borderBottomColor || style.borderColor;
                        // all borders has the same width and color, draw a rectangle
                        if ((blw && btw && bbw && brw) && (blw === brw && blw === bbw && blw === btw) && (blc === brc && blc === bbc && blc === btc)) {
                            var border = blw, half = border / 2; // use an adjustment because of center border alignment used by PDFKit.
                            this._area.paths
                                .rect(x + half, y + half, clientWidth + border, clientHeight + border)
                                .stroke(new wijmo.pdf.PdfPen(blc, border));
                        }
                        else {
                            // use a trapeze for each border
                            if (blw) {
                                this._area.paths
                                    .polygon([[x, y], [x + blw, y + btw], [x + blw, y + btw + clientHeight], [x, y + btw + clientHeight + bbw]])
                                    .fill(blc);
                            }
                            if (btw) {
                                this._area.paths
                                    .polygon([[x, y], [x + blw, y + btw], [x + blw + clientWidth, y + btw], [x + blw + clientWidth + brw, y]])
                                    .fill(btc);
                            }
                            if (brw) {
                                this._area.paths
                                    .polygon([[x + blw + clientWidth + brw, y], [x + blw + clientWidth, y + btw], [x + blw + clientWidth, y + btw + clientHeight], [x + blw + clientWidth + brw, y + btw + clientHeight + bbw]])
                                    .fill(brc);
                            }
                            if (bbw) {
                                this._area.paths
                                    .polygon([[x, y + btw + clientHeight + bbw], [x + blw, y + btw + clientHeight], [x + blw + clientWidth, y + btw + clientHeight], [x + blw + clientWidth + brw, y + btw + clientHeight + bbw]])
                                    .fill(bbc);
                            }
                        }
                    }
                    // draw background
                    if (renderContent && style.backgroundColor && clientWidth > 0 && clientHeight > 0) {
                        this._area.paths
                            .rect(x + blw, y + btw, clientWidth, clientHeight)
                            .fill(style.backgroundColor);
                    }
                };
                /*
                * Renders a cell with a checkbox inside.
                *
                * @param value Boolean value.
                * @param style A CSSStyleDeclaration object that represents the cell style and
                * positioning.
                *
                * @return A reference to the document.
                */
                CellRenderer.prototype._renderBooleanCell = function (value, m, style, renderContent, renderBorders) {
                    this._renderEmptyCell(m, style, renderContent, renderBorders);
                    if (!renderContent) {
                        return;
                    }
                    var border = 0.5, x = m.textRect.left, y = m.textRect.top, sz = m.textRect.height;
                    // border and content area
                    this._area.paths
                        .rect(x, y, sz, sz)
                        .fillAndStroke(wijmo.Color.fromRgba(255, 255, 255), new wijmo.pdf.PdfPen(undefined, border));
                    // checkmark
                    if (wijmo.changeType(value, wijmo.DataType.Boolean, '') === true) {
                        var space = sz / 20, cmRectSize = sz - border - space * 2, cmLineWidth = sz / 8;
                        this._area._pdfdoc.saveState();
                        this._area.translate(x + border / 2 + space, y + border / 2 + space)
                            .paths
                            .moveTo(cmLineWidth / 2, cmRectSize * 0.6)
                            .lineTo(cmRectSize - cmRectSize * 0.6, cmRectSize - cmLineWidth)
                            .lineTo(cmRectSize - cmLineWidth / 2, cmLineWidth / 2)
                            .stroke(new wijmo.pdf.PdfPen(undefined, cmLineWidth));
                        this._area._pdfdoc.restoreState();
                    }
                };
                /*
                * Renders a cell with a text inside.
                *
                * @param text Text inside the cell.
                * @param style A CSSStyleDeclaration object that represents the cell style and positioning.
                *
                * @return A reference to the document.
                */
                CellRenderer.prototype._renderTextCell = function (text, m, style, renderContent, renderBorders) {
                    this._renderEmptyCell(m, style, renderContent, renderBorders);
                    if (!renderContent) {
                        return;
                    }
                    if (text) {
                        this._area.drawText(text, m.textRect.left, m.textRect.top, {
                            brush: style.color,
                            font: style.font,
                            height: m.textRect.height,
                            width: m.textRect.width,
                            align: style.textAlign === 'center'
                                ? wijmo.pdf.PdfTextHorizontalAlign.Center
                                : (style.textAlign === 'right'
                                    ? wijmo.pdf.PdfTextHorizontalAlign.Right
                                    : (style.textAlign === 'justify'
                                        ? wijmo.pdf.PdfTextHorizontalAlign.Justify
                                        : wijmo.pdf.PdfTextHorizontalAlign.Left)) // default
                        });
                    }
                };
                CellRenderer.prototype._calculateTextRect = function (value, column, row, panel, content, style) {
                    var res = content.clone();
                    if (this._isBooleanCellAndValue(value, column, row, panel)) {
                        var szh = this._getTextLineHeight(style.font);
                        switch (style.verticalAlign) {
                            case 'middle':
                                res.top = content.top + content.height / 2 - szh / 2;
                                break;
                            case 'bottom':
                                res.top = content.top + content.height - szh;
                                break;
                        }
                        switch (style.textAlign) {
                            case 'justify':
                            case 'center':
                                res.left = content.left + content.width / 2 - szh / 2;
                                break;
                            case 'right':
                                res.left = content.left + content.width - szh;
                                break;
                        }
                        res.height = res.width = szh;
                    }
                    else {
                        if (res.height > 0 && res.width > 0) {
                            switch (style.verticalAlign) {
                                case 'bottom':
                                    var sz = this._area.measureText(value, style.font, { height: res.height, width: res.width });
                                    if (sz.size.height < res.height) {
                                        res.top += res.height - sz.size.height;
                                        res.height = sz.size.height;
                                    }
                                    break;
                                case 'middle':
                                    var sz = this._area.measureText(value, style.font, { height: res.height, width: res.width });
                                    if (sz.size.height < res.height) {
                                        res.top += res.height / 2 - sz.size.height / 2;
                                        res.height = sz.size.height;
                                    }
                                    break;
                                default:// 'top'
                                    break;
                            }
                            if (!column.wordWrap) {
                                res.height = this._getTextLineHeight(style.font);
                            }
                        }
                    }
                    return res;
                };
                CellRenderer.prototype._extractCheckboxValue = function (cell) {
                    var cb = cell.querySelector('input.wj-cell-check[type=checkbox]');
                    if (cb) {
                        var style = window.getComputedStyle(cb);
                        if (style.display !== 'none' && style.visibility !== 'hidden') {
                            return cb.checked;
                        }
                    }
                    return undefined;
                };
                CellRenderer.prototype._getTextLineHeight = function (font) {
                    //return this._area.measureText('A', font, { width: Infinity }).size.height;
                    return this._area.lineHeight(font);
                };
                return CellRenderer;
            }());
            // A caching proxy for the flex.getMergedRange method, caches last vertical range for each column.
            var GetMergedRangeProxy = /** @class */ (function () {
                function GetMergedRangeProxy(flex) {
                    this._columns = {};
                    this._flex = flex;
                }
                GetMergedRangeProxy.prototype.getMergedRange = function (panel, r, c) {
                    var rng = this._columns[c];
                    if (rng && r >= rng.topRow && r <= rng.bottomRow) {
                        return rng;
                    }
                    else {
                        var mergedRange = this._flex.getMergedRange(panel, r, c, false);
                        return this._columns[c] = mergedRange ? new CellRangeExt(panel, mergedRange) : null;
                    }
                };
                return GetMergedRangeProxy;
            }());
            var CellRangeExt = /** @class */ (function (_super) {
                __extends(CellRangeExt, _super);
                function CellRangeExt(panel, cr, col, row2, col2) {
                    var _this = this;
                    if (cr instanceof grid_1.CellRange) {
                        _this = _super.call(this, cr.row, cr.col, cr.row2, cr.col2) || this;
                    }
                    else {
                        _this = _super.call(this, cr, col, row2, col2) || this;
                    }
                    _this.firstVisibleRow = -1;
                    _this.visibleRowsCount = 0;
                    var tr = _this.topRow, br = _this.bottomRow, len = panel.rows.length;
                    // find first visible row
                    if (len > 0) {
                        for (var i = tr; i <= br && i < len; i++) {
                            if (panel.rows[i].isVisible) {
                                if (_this.firstVisibleRow < 0) {
                                    _this.firstVisibleRow = i;
                                }
                                _this.visibleRowsCount++;
                            }
                        }
                    }
                    return _this;
                }
                CellRangeExt.prototype.copyFrom = function (cr) {
                    this.setRange(cr.row, cr.col, cr.row2, cr.col2);
                    this.firstVisibleRow = cr.firstVisibleRow;
                    this.visibleRowsCount = cr.visibleRowsCount;
                };
                return CellRangeExt;
            }(grid_1.CellRange));
            var RowRange = /** @class */ (function () {
                function RowRange(flex, ranges) {
                    this._flex = flex;
                    this._ranges = ranges || [];
                }
                RowRange.getSelection = function (flex, exportMode) {
                    var ranges = [];
                    if (exportMode === ExportMode.All) {
                        ranges.push(new grid_1.CellRange(0, 0, flex.rows.length - 1, flex.columns.length - 1));
                    }
                    else {
                        var selection = flex.selection;
                        switch (flex.selectionMode) {
                            case grid_1.SelectionMode.None:
                                break;
                            case grid_1.SelectionMode.Cell:
                            case grid_1.SelectionMode.CellRange:
                                ranges.push(selection);
                                break;
                            case grid_1.SelectionMode.Row:
                                ranges.push(new grid_1.CellRange(selection.topRow, 0, selection.topRow, flex.cells.columns.length - 1));
                                break;
                            case grid_1.SelectionMode.RowRange:
                                ranges.push(new grid_1.CellRange(selection.topRow, 0, selection.bottomRow, flex.cells.columns.length - 1));
                                break;
                            case grid_1.SelectionMode.ListBox:
                                var top = -1;
                                for (var r = 0; r < flex.rows.length; r++) {
                                    var row = flex.rows[r];
                                    if (row.isSelected) {
                                        if (top < 0) {
                                            top = r;
                                        }
                                        if (r === flex.rows.length - 1) {
                                            ranges.push(new grid_1.CellRange(top, 0, r, flex.cells.columns.length - 1));
                                        }
                                    }
                                    else {
                                        if (top >= 0) {
                                            ranges.push(new grid_1.CellRange(top, 0, r - 1, flex.cells.columns.length - 1));
                                        }
                                        top = -1;
                                    }
                                }
                                break;
                        }
                    }
                    return new RowRange(flex, ranges);
                };
                RowRange.prototype.length = function () {
                    var res = 0;
                    for (var i = 0; i < this._ranges.length; i++) {
                        var r = this._ranges[i];
                        if (r.isValid) {
                            res += r.bottomRow - r.topRow + 1;
                        }
                    }
                    return res;
                };
                Object.defineProperty(RowRange.prototype, "isValid", {
                    get: function () {
                        return this._ranges.length && this._ranges[0].isValid;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(RowRange.prototype, "leftCol", {
                    get: function () {
                        if (this._ranges.length) {
                            return this._ranges[0].leftCol;
                        }
                        return -1;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(RowRange.prototype, "rightCol", {
                    get: function () {
                        if (this._ranges.length) {
                            return this._ranges[0].rightCol;
                        }
                        return -1;
                    },
                    enumerable: true,
                    configurable: true
                });
                RowRange.prototype.clone = function (leftCol, rightCol) {
                    var ranges = [];
                    for (var i = 0; i < this._ranges.length; i++) {
                        var range = this._ranges[i].clone();
                        if (arguments.length > 0) {
                            range.col = leftCol;
                        }
                        if (arguments.length > 1) {
                            range.col2 = rightCol;
                        }
                        ranges.push(range);
                    }
                    return new RowRange(this._flex, ranges);
                };
                RowRange.prototype.getRenderSize = function (panel) {
                    var res = new wijmo.Size(0, 0);
                    for (var i = 0; i < this._ranges.length; i++) {
                        var size = this._ranges[i].getRenderSize(panel);
                        res.width = Math.max(res.width, size.width);
                        res.height += size.height;
                    }
                    return res;
                };
                RowRange.prototype.forEach = function (panel, fn) {
                    var idx = 0;
                    for (var i = 0; i < this._ranges.length; i++) {
                        var range = this._ranges[i];
                        if (range.isValid) {
                            for (var j = range.topRow; j <= range.bottomRow; j++) {
                                fn(panel.rows[j], range, j, idx++);
                            }
                        }
                    }
                };
                RowRange.prototype.subrange = function (from, count, leftCol, rightCol) {
                    var ranges = [];
                    if (from >= 0 && count > 0) {
                        var start = 0, end = 0;
                        for (var i = 0; i < this._ranges.length && count > 0; i++, start = end + 1) {
                            var r = this._ranges[i];
                            end = start + (r.bottomRow - r.topRow);
                            if (from > end) {
                                continue;
                            }
                            var r1 = (from > start) ? r.topRow + (from - start) : r.topRow, r2 = Math.min(r.bottomRow, r1 + count - 1), lCol = arguments.length > 2 ? leftCol : r.leftCol, rCol = arguments.length > 2 ? rightCol : r.rightCol;
                            ranges.push(new grid_1.CellRange(r1, lCol, r2, rCol));
                            count -= r2 - r1 + 1;
                        }
                    }
                    return new RowRange(this._flex, ranges);
                };
                return RowRange;
            }());
            var PdfPageRowRange = /** @class */ (function () {
                function PdfPageRowRange(range, col, row) {
                    this._col = col;
                    this._row = row;
                    this._range = range;
                }
                Object.defineProperty(PdfPageRowRange.prototype, "range", {
                    get: function () {
                        return this._range;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfPageRowRange.prototype, "pageCol", {
                    get: function () {
                        return this._col;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(PdfPageRowRange.prototype, "pageRow", {
                    get: function () {
                        return this._row;
                    },
                    enumerable: true,
                    configurable: true
                });
                return PdfPageRowRange;
            }());
        })(pdf = grid_1.pdf || (grid_1.pdf = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

