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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Class that represents a data point (with x and y coordinates).
         *
         * X and Y coordinates can be specified as a number or a Date object(time-based data).
         */
        var DataPoint = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:DataPoint class.
             *
             * @param x X coordinate of the new DataPoint.
             * @param y Y coordinate of the new DataPoint.
             */
            function DataPoint(x, y) {
                if (x === void 0) { x = 0; }
                if (y === void 0) { y = 0; }
                this.x = x;
                this.y = y;
            }
            return DataPoint;
        }());
        chart.DataPoint = DataPoint;
        /**
         * Provides arguments for @see:Series events.
         */
        var RenderEventArgs = /** @class */ (function (_super) {
            __extends(RenderEventArgs, _super);
            /**
             * Initializes a new instance of the @see:RenderEventArgs class.
             *
             * @param engine (@see:IRenderEngine) The rendering engine to use.
             */
            function RenderEventArgs(engine) {
                var _this = _super.call(this) || this;
                _this._engine = engine;
                return _this;
            }
            Object.defineProperty(RenderEventArgs.prototype, "engine", {
                /**
                 * Gets the @see:IRenderEngine object to use for rendering the chart elements.
                 */
                get: function () {
                    return this._engine;
                },
                enumerable: true,
                configurable: true
            });
            return RenderEventArgs;
        }(wijmo.CancelEventArgs));
        chart.RenderEventArgs = RenderEventArgs;
        /**
         * Provides arguments for @see:Series rendering event.
         */
        var SeriesRenderingEventArgs = /** @class */ (function (_super) {
            __extends(SeriesRenderingEventArgs, _super);
            /**
             * Initializes a new instance of the @see:SeriesRenderingEventArgs class.
             *
             * @param engine (@see:IRenderEngine) The rendering engine to use.
             * @param index The index of the series to render.
             * @param count Total number of the series to render.
             */
            function SeriesRenderingEventArgs(engine, index, count) {
                var _this = _super.call(this, engine) || this;
                _this._index = index;
                _this._count = count;
                return _this;
            }
            Object.defineProperty(SeriesRenderingEventArgs.prototype, "index", {
                /**
                 * Gets the index of the series to render.
                 */
                get: function () {
                    return this._index;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesRenderingEventArgs.prototype, "count", {
                /**
                 * Gets total number of series to render.
                 */
                get: function () {
                    return this._count;
                },
                enumerable: true,
                configurable: true
            });
            return SeriesRenderingEventArgs;
        }(RenderEventArgs));
        chart.SeriesRenderingEventArgs = SeriesRenderingEventArgs;
        /**
         * Specifies the format of the image with embed base64-encoded binary data.
         */
        var ImageFormat;
        (function (ImageFormat) {
            /** Gets the W3C Portable Network Graphics (PNG) image format. */
            ImageFormat[ImageFormat["Png"] = 0] = "Png";
            /** Gets the Joint Photographic Experts Group (JPEG) image format. */
            ImageFormat[ImageFormat["Jpeg"] = 1] = "Jpeg";
            /** Gets the Scalable Vector Graphics(SVG) image format. */
            ImageFormat[ImageFormat["Svg"] = 2] = "Svg";
        })(ImageFormat = chart.ImageFormat || (chart.ImageFormat = {}));
        ;
        /**
         * The @see:FlexChartBase control from which the FlexChart and FlexPie derive.
         */
        var FlexChartBase = /** @class */ (function (_super) {
            __extends(FlexChartBase, _super);
            function FlexChartBase() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this._palette = null;
                _this._selectionMode = chart.SelectionMode.None;
                _this._defPalette = chart.Palettes.standard; // ['#5DA5DA', '#FAA43A', '#60BD68', '#E5126F', '#9D722A'];
                _this._notifyCurrentChanged = true;
                _this._legendHost = null;
                _this._needBind = false;
                /**
                 * Occurs before the chart starts rendering data.
                 */
                _this.rendering = new wijmo.Event();
                /**
                 * Occurs after the chart finishes rendering.
                 */
                _this.rendered = new wijmo.Event();
                /**
                 * Occurs after the selection changes, whether programmatically
                 * or when the user clicks the chart. This is useful, for example,
                 * when you want to update details in a textbox showing the current
                 * selection.
                 */
                _this.selectionChanged = new wijmo.Event();
                return _this;
            }
            Object.defineProperty(FlexChartBase.prototype, "itemsSource", {
                //--------------------------------------------------------------------------
                // ** object model
                /**
                 * Gets or sets the array or @see:ICollectionView object that contains the data used to create the chart.
                 */
                get: function () {
                    return this._items;
                },
                set: function (value) {
                    if (this._items != value) {
                        // unbind current collection view
                        if (this._cv) {
                            this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this);
                            this._cv = null;
                        }
                        // save new data source and collection view
                        this._items = value;
                        this._cv = wijmo.asCollectionView(value);
                        // bind new collection view
                        if (this._cv != null) {
                            this._cv.currentChanged.addHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this);
                        }
                        this._clearCachedValues();
                        // bind chart
                        this._bindChart();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "collectionView", {
                /**
                 * Gets the @see:ICollectionView object that contains the chart data.
                 */
                get: function () {
                    return this._cv;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "palette", {
                /**
                 * Gets or sets an array of default colors to use for displaying each series.
                 *
                 * The array contains strings that represents CSS colors. For example:
                 * <pre>
                 * // use colors specified by name
                 * chart.palette = ['red', 'green', 'blue'];
                 * // or use colors specified as rgba-values
                 * chart.palette = [
                 *   'rgba(255,0,0,1)',
                 *   'rgba(255,0,0,0.8)',
                 *   'rgba(255,0,0,0.6)',
                 *   'rgba(255,0,0,0.4)'];
                 * </pre>
                 *
                 * There is a set of predefined palettes in the @see:Palettes class that you can use, for example:
                 * <pre>
                 * chart.palette = wijmo.chart.Palettes.coral;
                 * </pre>
                 */
                get: function () {
                    return this._palette;
                },
                set: function (value) {
                    if (value != this._palette) {
                        this._palette = wijmo.asArray(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "plotMargin", {
                /**
                 * Gets or sets the plot margin in pixels.
                 *
                 * The plot margin represents the area between the edges of the control
                 * and the plot area.
                 *
                 * By default, this value is calculated automatically based on the space
                 * required by the axis labels, but you can override it if you want
                 * to control the precise position of the plot area within the control
                 * (for example, when aligning multiple chart controls on a page).
                 *
                 * You may set this property to a numeric value or to a CSS-style
                 * margin specification. For example:
                 *
                 * <pre>
                 * // set the plot margin to 20 pixels on all sides
                 * chart.plotMargin = 20;
                 * // set the plot margin for top, right, bottom, left sides
                 * chart.plotMargin = '10 15 20 25';
                 * // set the plot margin for top/bottom (10px) and left/right (20px)
                 * chart.plotMargin = '10 20';
                 * </pre>
                 */
                get: function () {
                    return this._plotMargin;
                },
                set: function (value) {
                    if (value != this._plotMargin) {
                        this._plotMargin = value;
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "legend", {
                /**
                 * Gets or sets the chart legend.
                 */
                get: function () {
                    return this._legend;
                },
                set: function (value) {
                    if (value != this._legend) {
                        this._legend = wijmo.asType(value, chart.Legend);
                        if (this._legend != null) {
                            this._legend._chart = this;
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "header", {
                /**
                 * Gets or sets the text displayed in the chart header.
                 */
                get: function () {
                    return this._header;
                },
                set: function (value) {
                    if (value != this._header) {
                        this._header = wijmo.asString(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "footer", {
                /**
                 * Gets or sets the text displayed in the chart footer.
                 */
                get: function () {
                    return this._footer;
                },
                set: function (value) {
                    if (value != this._footer) {
                        this._footer = wijmo.asString(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "headerStyle", {
                /**
                 * Gets or sets the style of the chart header.
                 */
                get: function () {
                    return this._headerStyle;
                },
                set: function (value) {
                    if (value != this._headerStyle) {
                        this._headerStyle = value;
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "footerStyle", {
                /**
                 * Gets or sets the style of the chart footer.
                 */
                get: function () {
                    return this._footerStyle;
                },
                set: function (value) {
                    if (value != this._footerStyle) {
                        this._footerStyle = value;
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "selectionMode", {
                /**
                 * Gets or sets an enumerated value indicating whether or what is
                 * selected when the user clicks the chart.
                 */
                get: function () {
                    return this._selectionMode;
                },
                set: function (value) {
                    if (value != this._selectionMode) {
                        this._selectionMode = wijmo.asEnum(value, chart.SelectionMode);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartBase.prototype, "itemFormatter", {
                /**
                 * Gets or sets the item formatter function that allows you to customize
                 * the appearance of the chart elements.
                 *
                 * If specified, the function should take three parameters: the chart's
                 * @see:IRenderEngine responsible for rendering elements on the chart,
                 * a @see:HitTestInfo parameter that describes the element being rendered,
                 * and a function that provides the default rendering for the item.
                 *
                 * For example:
                 * <pre>
                 * itemFormatter: function (engine, hitTestInfo, defaultRenderer) {
                 *   var ht = hitTestInfo,
                 *       binding = 'downloads';
                 *
                 *   // check that this is the right series/element
                 *   if (ht.series.binding == binding && ht.pointIndex &gt; 0 &&
                 *       ht.chartElement == wijmo.chart.ChartElement.SeriesSymbol) {
                 *
                 *     // get current and previous values
                 *     var chart = ht.series.chart,
                 *         items = chart.collectionView.items,
                 *         valNow = items[ht.pointIndex][binding],
                 *         valPrev = items[ht.pointIndex - 1][binding];
                 *
                 *     // add line if value is increasing
                 *     if (valNow &gt; valPrev) {
                 *       var pt1 = chart.dataToPoint(ht.pointIndex, valNow),
                 *           pt2 = chart.dataToPoint(ht.pointIndex - 1, valPrev);
                 *       engine.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, null, {
                 *         stroke: 'gold',
                 *         strokeWidth: 6
                 *       });
                 *     }
                 *   }
                 *
                 *   // render element as usual
                 *   defaultRenderer();
                 * }
                 * </pre>
                 *
                 * @fiddle:rptz23nL
                 */
                get: function () {
                    return this._itemFormatter;
                },
                set: function (value) {
                    if (value != this._itemFormatter) {
                        this._itemFormatter = wijmo.asFunction(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:rendered event.
             *
             * @param e The @see:RenderEventArgs object used to render the chart.
             */
            FlexChartBase.prototype.onRendered = function (e) {
                this.rendered.raise(this, e);
            };
            /**
             * Raises the @see:rendering event.
             *
             * @param e The @see:RenderEventArgs object used to render the chart.
             */
            FlexChartBase.prototype.onRendering = function (e) {
                this.rendering.raise(this, e);
            };
            ///**
            // * Exports chart to an image file.
            // * 
            // * @param name The name of the exported image file.
            // * @param extension The filename extension of the exported image file.  Supported types are PNG, JPEG, SVG.
            // */
            //exportToImage(name: string, extension: string) {
            //    var n = name || 'image',
            //        ext = (extension || 'png').toLowerCase();
            //    this._exportToImage(n, ext, function (uri, n, ext) {
            //        ExportHelper.downloadImage(uri, n, ext);
            //    });
            //}
            /**
             * Saves the chart to an image file.
             *
             * NOTE: This method does not work in IE browsers. If you require IE support,
             * add the <code>wijmo.chart.render</code> module to the page.
             *
             * @param filename The filename for the exported image file including extension.
             * Supported types are PNG, JPEG and SVG.
             */
            FlexChartBase.prototype.saveImageToFile = function (filename) {
                var name, ext, format, fn;
                if (!filename || filename.length === 0 || filename.indexOf('.') === -1) {
                    filename = 'image.png';
                }
                fn = filename.split('.');
                name = fn[0].toLowerCase();
                ext = fn[1].toLowerCase();
                format = ImageFormat[(ext[0].toUpperCase() + ext.substring(1))];
                this.saveImageToDataUrl(format, function (dataURI) {
                    ExportHelper.downloadImage(dataURI, name, ext);
                });
            };
            /**
             * Saves the chart to an image data url.
             *
             * NOTE: This method does not work in IE browsers. If you require IE support,
             * add the <code>wijmo.chart.render</code> module to the page.
             *
             * @param format The @see:ImageFormat for the exported image.
             * @param done A function to be called after data url is generated. The function gets passed the data url as its argument.
             */
            FlexChartBase.prototype.saveImageToDataUrl = function (format, done) {
                var form = wijmo.asEnum(format, ImageFormat, false), f = ImageFormat[form].toLowerCase(), dataURI;
                if (f && f.length) {
                    this._exportToImage(f, function (uri) {
                        done.call(done, uri);
                    });
                }
            };
            FlexChartBase.prototype._exportToImage = function (extension, processDataURI) {
                var image = new Image(), ele = this._currentRenderEngine.element, dataUrl;
                dataUrl = ExportHelper.getDataUri(ele);
                if (extension === 'svg') {
                    processDataURI.call(null, dataUrl);
                }
                else {
                    image.onload = function () {
                        var canvas = document.createElement('canvas'), node = ele.parentNode || ele, rect = wijmo.getElementRect(node), uri;
                        canvas.width = rect.width;
                        canvas.height = rect.height;
                        var context = canvas.getContext('2d');
                        //fill background
                        context.fillStyle = '#ffffff';
                        context.fillRect(0, 0, rect.width, rect.height);
                        var left = window.getComputedStyle(node, null).getPropertyValue('padding-left').replace('px', '');
                        var top = window.getComputedStyle(node, null).getPropertyValue('padding-top').replace('px', '');
                        context.drawImage(image, +left || 0, +top || 0);
                        uri = canvas.toDataURL('image/' + extension);
                        processDataURI.call(null, uri);
                        canvas = null;
                    };
                    image.src = dataUrl;
                }
            };
            /**
             * Refreshes the chart.
             *
             * @param fullUpdate A value indicating whether to update the control layout as well as the content.
             */
            FlexChartBase.prototype.refresh = function (fullUpdate) {
                if (fullUpdate === void 0) { fullUpdate = true; }
                // call base class to suppress any pending invalidations
                _super.prototype.refresh.call(this, fullUpdate);
                // update the chart
                if (!this.isUpdating) {
                    this._refreshChart();
                }
            };
            /**
             * Raises the @see:selectionChanged event.
             */
            FlexChartBase.prototype.onSelectionChanged = function (e) {
                this.selectionChanged.raise(this, e);
            };
            FlexChartBase.prototype.onLostFocus = function (e) {
                if (this._tooltip && this._tooltip.isVisible) {
                    this._tooltip.hide();
                }
                _super.prototype.onLostFocus.call(this, e);
            };
            //--------------------------------------------------------------------------
            // implementation
            // updates chart to sync with data source
            FlexChartBase.prototype._cvCollectionChanged = function (sender, e) {
                this._clearCachedValues();
                this._bindChart();
            };
            // updates selection to sync with data source
            FlexChartBase.prototype._cvCurrentChanged = function (sender, e) {
                if (this._notifyCurrentChanged) {
                    this._bindChart();
                }
            };
            // IPalette 
            /**
            * Gets a color from the palette by index.
            *
            * @param index The index of the color in the palette.
            */
            FlexChartBase.prototype._getColor = function (index) {
                var palette = this._defPalette;
                if (this._palette != null && this._palette.length > 0) {
                    palette = this._palette;
                }
                return palette[index % palette.length];
            };
            /**
             * Gets a lighter color from the palette by index.
             *
             * @param index The index of the color in the palette.
             */
            FlexChartBase.prototype._getColorLight = function (index) {
                var color = this._getColor(index);
                return this._getLightColor(color);
            };
            /**
             * Gets a lighter color from the palette by color string.
             *
             * @param color The color in the palette.
             */
            FlexChartBase.prototype._getLightColor = function (color) {
                var c = new wijmo.Color(color);
                if (c != null && color.indexOf('-') === -1) {
                    if (c.a == 1 && color.indexOf('rgba') == -1) {
                        c.a *= 0.7;
                    }
                    color = c.toString();
                }
                return color;
            };
            // abstract
            // binds the chart to the current data source.
            FlexChartBase.prototype._bindChart = function () {
                this._needBind = true;
                this.invalidate();
            };
            FlexChartBase.prototype._clearCachedValues = function () {
            };
            FlexChartBase.prototype._render = function (engine, applyElement) {
                if (applyElement === void 0) { applyElement = true; }
                var el = this.hostElement, sz = this._getHostSize(), w = sz.width, h = sz.height;
                //if width is 0, don't draw chart.
                if (w == 0) {
                    return;
                }
                if (isNaN(w)) {
                    w = chart.FlexChart._WIDTH;
                }
                if (h == 0 || isNaN(h)) {
                    h = chart.FlexChart._HEIGHT;
                }
                engine.beginRender();
                if (w > 0 && h > 0) {
                    engine.setViewportSize(w, h);
                    this._rectChart = new wijmo.Rect(0, 0, w, h);
                    this._prepareRender();
                    var rect = new wijmo.Rect(0, 0, w, h);
                    //add chart rect to header/footer/axis/legend groups for IE.
                    this._chartRectId = 'chartRect' + (1000000 * Math.random()).toFixed();
                    engine.addClipRect(rect, this._chartRectId);
                    this._renderHeader(engine, rect);
                    this._renderFooter(engine, rect);
                    this._renderLegends(engine, rect);
                    this._renderChart(engine, rect, applyElement);
                }
                engine.endRender();
            };
            FlexChartBase.prototype._renderHeader = function (engine, rect) {
                engine.startGroup(chart.FlexChart._CSS_HEADER, this._chartRectId);
                rect = this._drawTitle(engine, rect, this.header, this.headerStyle, false);
                engine.endGroup();
            };
            FlexChartBase.prototype._renderFooter = function (engine, rect) {
                engine.startGroup(chart.FlexChart._CSS_FOOTER, this._chartRectId);
                rect = this._drawTitle(engine, rect, this.footer, this.footerStyle, true);
                engine.endGroup();
            };
            FlexChartBase.prototype._renderLegends = function (engine, rect) {
                var legend = this.legend, lsz, lpos, w = rect.width, h = rect.height, legpos = legend._getPosition(w, h);
                lsz = legend._getDesiredSize(engine, legpos, w, h);
                switch (legpos) {
                    case chart.Position.Right:
                        w -= lsz.width;
                        lpos = new wijmo.Point(w, rect.top + 0.5 * (h - lsz.height));
                        break;
                    case chart.Position.Left:
                        rect.left += lsz.width;
                        w -= lsz.width;
                        lpos = new wijmo.Point(0, rect.top + 0.5 * (h - lsz.height));
                        break;
                    case chart.Position.Top:
                        h -= lsz.height;
                        lpos = new wijmo.Point(0.5 * (w - lsz.width), rect.top);
                        rect.top += lsz.height;
                        break;
                    case chart.Position.Bottom:
                        h -= lsz.height;
                        lpos = new wijmo.Point(0.5 * (w - lsz.width), rect.top + h);
                        break;
                }
                rect.width = w;
                rect.height = h;
                if (lsz) {
                    this._legendHost = engine.startGroup(chart.FlexChart._CSS_LEGEND, this._chartRectId);
                    this._rectLegend = new wijmo.Rect(lpos.x, lpos.y, lsz.width, lsz.height);
                    this.legend._render(engine, lpos, legpos, lsz.width, lsz.height);
                    engine.endGroup();
                }
                else {
                    this._legendHost = null;
                    this._rectLegend = null;
                }
            };
            FlexChartBase.prototype._prepareRender = function () {
            };
            FlexChartBase.prototype._renderChart = function (engine, rect, applyElement) {
            };
            FlexChartBase.prototype._performBind = function () {
            };
            FlexChartBase.prototype._getDesiredLegendSize = function (engine, isVertical, width, height) {
                return null;
            };
            FlexChartBase.prototype._renderLegend = function (engine, pt, areas, isVertical, width, height) {
            };
            FlexChartBase.prototype._getHitTestItem = function (index) {
                return null;
            };
            FlexChartBase.prototype._getHitTestValue = function (index) {
                return null;
            };
            FlexChartBase.prototype._getHitTestLabel = function (index) {
                return null;
            };
            // render
            FlexChartBase.prototype._refreshChart = function () {
                if (this._needBind) {
                    this._needBind = false;
                    this._performBind();
                }
                if (this.hostElement) {
                    this._render(this._currentRenderEngine);
                }
            };
            FlexChartBase.prototype._drawTitle = function (engine, rect, title, style, isFooter) {
                var lblClass = chart.FlexChart._CSS_TITLE;
                var groupClass = isFooter ? chart.FlexChart._CSS_FOOTER : chart.FlexChart._CSS_HEADER;
                var tsz = null;
                if (isFooter) {
                    this._rectFooter = null;
                }
                else {
                    this._rectHeader = null;
                }
                if (title != null) {
                    var fontSize = null;
                    var fg = null;
                    var fontFamily = null;
                    var halign = null;
                    if (style) {
                        if (style.fontSize) {
                            fontSize = style.fontSize;
                        }
                        if (style.foreground) {
                            fg = style.foreground;
                        }
                        if (style.fill) {
                            fg = style.fill;
                        }
                        if (style.fontFamily) {
                            fontFamily = style.fontFamily;
                        }
                        if (style.halign) {
                            halign = style.halign;
                        }
                    }
                    engine.fontSize = fontSize;
                    engine.fontFamily = fontFamily;
                    tsz = engine.measureString(title, lblClass, groupClass, style);
                    rect.height -= tsz.height;
                    if (!fg) {
                        fg = chart.FlexChart._FG;
                    }
                    engine.textFill = fg;
                    if (isFooter) {
                        if (halign == 'left') {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left, rect.bottom), 0, 0, lblClass, groupClass, style);
                        }
                        else if (halign == 'right') {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left + rect.width, rect.bottom), 2, 0, lblClass, groupClass, style);
                        }
                        else {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left + 0.5 * rect.width, rect.bottom), 1, 0, lblClass, groupClass, style);
                        }
                        this._rectFooter = new wijmo.Rect(rect.left, rect.bottom, rect.width, tsz.height);
                    }
                    else {
                        this._rectHeader = new wijmo.Rect(rect.left, rect.top, rect.width, tsz.height);
                        rect.top += tsz.height;
                        if (halign == 'left') {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left, 0), 0, 0, lblClass, groupClass, style);
                        }
                        else if (halign == 'right') {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left + rect.width, 0), 2, 0, lblClass, groupClass, style);
                        }
                        else {
                            chart.FlexChart._renderText(engine, title, new wijmo.Point(rect.left + 0.5 * rect.width, 0), 1, 0, lblClass, groupClass, style);
                        }
                    }
                    engine.textFill = null;
                    engine.fontSize = null;
                    engine.fontFamily = null;
                }
                return rect;
            };
            /**
             * Converts page coordinates to control coordinates.
             *
             * @param pt The point of page coordinates or x value
                 of page coordinates.
             * @param y The y value of page coordinates. Its value
                 should be a number, if pt is a number type. However,
                 the y parameter is optional when pt is Point type.
             */
            FlexChartBase.prototype.pageToControl = function (pt, y) {
                return this._toControl(pt, y);
            };
            // convert page coordinates to control
            FlexChartBase.prototype._toControl = function (pt, y) {
                if (wijmo.isNumber(pt) && wijmo.isNumber(y)) {
                    pt = new wijmo.Point(pt, y);
                }
                else if (pt instanceof MouseEvent) {
                    pt = wijmo.mouseToPage(pt); // new Point(pt.pageX, pt.pageY);
                }
                wijmo.asType(pt, wijmo.Point);
                // control coords
                var cpt = pt.clone();
                // jQuery
                // var host = $(this.hostElement);
                // var offset = host.offset();
                // cpt.x -= offset.left + parseInt(host.css('padding-left'));
                // cpt.y -= offset.top + parseInt(host.css('padding-top'));
                var offset = this._getHostOffset();
                cpt.x -= offset.x;
                cpt.y -= offset.y;
                var cstyle = this._getHostComputedStyle();
                if (cstyle) {
                    var padLeft = parseInt(cstyle.paddingLeft.replace('px', ''));
                    if (padLeft && !isNaN(padLeft)) {
                        cpt.x -= padLeft;
                    }
                    var padTop = parseInt(cstyle.paddingTop.replace('px', ''));
                    if (padTop && !isNaN(padTop)) {
                        cpt.y -= padTop;
                    }
                }
                return cpt;
            };
            FlexChartBase.prototype._highlightItems = function (items, cls, selected) {
                if (selected) {
                    for (var i = 0; i < items.length; i++) {
                        wijmo.addClass(items[i], cls);
                    }
                }
                else {
                    for (var i = 0; i < items.length; i++) {
                        wijmo.removeClass(items[i], cls);
                    }
                }
            };
            FlexChartBase.prototype._parseMargin = function (value) {
                var margins = {};
                if (wijmo.isNumber(value) && !isNaN(value)) {
                    margins['top'] = margins['bottom'] = margins['left'] = margins['right'] = wijmo.asNumber(value);
                }
                else if (wijmo.isString(value)) {
                    var s = wijmo.asString(value);
                    var ss = s.split(' ', 4);
                    var top = NaN, bottom = NaN, left = NaN, right = NaN;
                    if (ss) {
                        if (ss.length == 4) {
                            top = parseFloat(ss[0]);
                            right = parseFloat(ss[1]);
                            bottom = parseFloat(ss[2]);
                            left = parseFloat(ss[3]);
                        }
                        else if (ss.length == 2) {
                            top = bottom = parseFloat(ss[0]);
                            left = right = parseFloat(ss[1]);
                        }
                        else if (ss.length == 1) {
                            top = bottom = left = right = parseFloat(ss[1]);
                        }
                        if (!isNaN(top)) {
                            margins['top'] = top;
                        }
                        if (!isNaN(bottom)) {
                            margins['bottom'] = bottom;
                        }
                        if (!isNaN(left)) {
                            margins['left'] = left;
                        }
                        if (!isNaN(right)) {
                            margins['right'] = right;
                        }
                    }
                }
                return margins;
            };
            // shows an automatic tooltip
            FlexChartBase.prototype._showToolTip = function (content, rect) {
                var self = this, showDelay = this._tooltip.showDelay;
                self._clearTimeouts();
                if (showDelay > 0) {
                    self._toShow = setTimeout(function () {
                        self._tooltip.show(self.hostElement, content, rect);
                        if (self._tooltip.hideDelay > 0) {
                            self._toHide = setTimeout(function () {
                                self._tooltip.hide();
                            }, self._tooltip.hideDelay);
                        }
                    }, showDelay);
                }
                else {
                    self._tooltip.show(self.hostElement, content, rect);
                    if (self._tooltip.hideDelay > 0) {
                        self._toHide = setTimeout(function () {
                            self._tooltip.hide();
                        }, self._tooltip.hideDelay);
                    }
                }
            };
            // hides an automatic tooltip
            FlexChartBase.prototype._hideToolTip = function () {
                this._clearTimeouts();
                this._tooltip.hide();
            };
            // clears the timeouts used to show and hide automatic tooltips
            FlexChartBase.prototype._clearTimeouts = function () {
                if (this._toShow) {
                    clearTimeout(this._toShow);
                    this._toShow = null;
                }
                if (this._toHide) {
                    clearTimeout(this._toHide);
                    this._toHide = null;
                }
            };
            FlexChartBase.prototype._getHostOffset = function () {
                var rect = wijmo.getElementRect(this.hostElement);
                return new wijmo.Point(rect.left, rect.top);
                /*var docElem, win,
                    offset = new Point(),
                    host = this.hostElement,
                    doc =  host && host.ownerDocument;
    
                if (!doc) {
                    return offset;
                }
    
                docElem = doc.documentElement;
    
                // Make sure it's not a disconnected DOM node
                //if (!jQuery.contains(docElem, elem)) {
                //	return box;
                //}
    
                var box = host.getBoundingClientRect();
                win = doc.defaultView;// getWindow(doc);
                offset.y = box.top + win.pageYOffset - docElem.clientTop;
                offset.x = box.left + win.pageXOffset - docElem.clientLeft;
    
                return offset;*/
            };
            FlexChartBase.prototype._getHostSize = function () {
                var sz = new wijmo.Size();
                var host = this.hostElement;
                var cstyle = this._getHostComputedStyle();
                var w = host.offsetWidth, h = host.offsetHeight;
                if (cstyle) {
                    var padLeft = parseFloat(cstyle.paddingLeft.replace('px', ''));
                    var padRight = parseFloat(cstyle.paddingRight.replace('px', ''));
                    var padTop = parseFloat(cstyle.paddingTop.replace('px', ''));
                    var padBottom = parseFloat(cstyle.paddingBottom.replace('px', ''));
                    if (!isNaN(padLeft)) {
                        w -= padLeft;
                    }
                    if (!isNaN(padRight)) {
                        w -= padRight;
                    }
                    if (!isNaN(padTop)) {
                        h -= padTop;
                    }
                    if (!isNaN(padBottom)) {
                        h -= padBottom;
                    }
                    var borderLeft = parseFloat(cstyle.borderLeftWidth.replace('px', ''));
                    var borderRight = parseFloat(cstyle.borderRightWidth.replace('px', ''));
                    var borderTop = parseFloat(cstyle.borderTopWidth.replace('px', ''));
                    var borderBottom = parseFloat(cstyle.borderBottomWidth.replace('px', ''));
                    if (!isNaN(borderLeft)) {
                        w -= borderLeft;
                    }
                    if (!isNaN(borderRight)) {
                        w -= borderRight;
                    }
                    if (!isNaN(borderTop)) {
                        h -= borderTop;
                    }
                    if (!isNaN(borderBottom)) {
                        h -= borderBottom;
                    }
                    sz.width = w;
                    sz.height = h;
                }
                return sz;
            };
            FlexChartBase.prototype._getHostComputedStyle = function () {
                var host = this.hostElement;
                if (host && host.ownerDocument && host.ownerDocument.defaultView) {
                    return host.ownerDocument.defaultView.getComputedStyle(this.hostElement);
                }
                return null;
            };
            FlexChartBase.prototype._find = function (elem, names) {
                var found = [];
                for (var i = 0; i < elem.childElementCount; i++) {
                    var child = elem.childNodes.item(i);
                    if (names.indexOf(child.nodeName) >= 0) {
                        found.push(child);
                    }
                    else {
                        var items = this._find(child, names);
                        if (items.length > 0) {
                            for (var j = 0; j < items.length; j++)
                                found.push(items[j]);
                        }
                    }
                }
                return found;
            };
            FlexChartBase._WIDTH = 300;
            FlexChartBase._HEIGHT = 200;
            FlexChartBase._SELECTION_THRESHOLD = 15;
            return FlexChartBase;
        }(wijmo.Control));
        chart.FlexChartBase = FlexChartBase;
        var _KeyWords = /** @class */ (function () {
            function _KeyWords() {
                this._keys = {};
                this._keys['seriesName'] = null;
                this._keys['pointIndex'] = null;
                this._keys['x'] = null;
                this._keys['y'] = null;
                this._keys['value'] = null;
                this._keys['name'] = null;
            }
            _KeyWords.prototype.replace = function (s, ht) {
                var kw = this;
                return wijmo.format(s, {}, function (data, name, fmt, val) {
                    return kw.getValue(name, ht, fmt);
                });
            };
            _KeyWords.prototype.getValue = function (key, ht, fmt) {
                // handle pre-defined keywords
                switch (key) {
                    case 'seriesName':
                        return ht.series ? ht.series.name : '';
                    case 'pointIndex':
                        return ht.pointIndex != null ? ht.pointIndex.toFixed() : '';
                    case 'x':
                        return fmt ? wijmo.Globalize.format(ht.x, fmt) : ht._xfmt;
                    case 'y':
                        return fmt ? wijmo.Globalize.format(ht.y, fmt) : ht._yfmt;
                    case 'value':
                        return fmt ? wijmo.Globalize.format(ht.value, fmt) : ht.value;
                    case 'name':
                        return ht.name;
                }
                // look for key in data item
                if (ht.item) {
                    if (key.indexOf('item.') == 0) {
                        key = key.substr(5);
                    }
                    if (key in ht.item) {
                        return fmt ? wijmo.Globalize.format(ht.item[key], fmt) : ht.item[key];
                    }
                }
                // no match
                return '';
            };
            return _KeyWords;
        }());
        chart._KeyWords = _KeyWords;
        var ExportHelper = /** @class */ (function () {
            function ExportHelper() {
            }
            ExportHelper.downloadImage = function (dataUrl, name, ext) {
                var a = document.createElement('a'), contentType = 'image/' + ext;
                if (navigator.msSaveOrOpenBlob) {
                    dataUrl = dataUrl.substring(dataUrl.indexOf(',') + 1);
                    var byteCharacters = atob(dataUrl), byteArrays = [], sliceSize = 512, offset, slice, blob;
                    for (offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                        slice = byteCharacters.slice(offset, offset + sliceSize);
                        var byteNumbers = new Array(slice.length);
                        for (var i = 0; i < slice.length; i++) {
                            byteNumbers[i] = slice.charCodeAt(i);
                        }
                        var byteArray = new Uint8Array(byteNumbers);
                        byteArrays.push(byteArray);
                    }
                    blob = new Blob(byteArrays, { type: contentType });
                    navigator.msSaveOrOpenBlob(blob, name + '.' + ext);
                    //navigator.msSaveOrOpenBlob(canvas.msToBlob(), n + '.' + ext);
                }
                else {
                    a.download = name + '.' + ext;
                    a.href = dataUrl;
                    document.body.appendChild(a);
                    a.addEventListener("click", function (e) {
                        wijmo.removeChild(a);
                    });
                    a.click();
                }
            };
            ExportHelper.getDataUri = function (ele) {
                var outer = document.createElement('div'), clone = ele.cloneNode(true), rect, width, height, viewBoxWidth, viewBoxHeight, box, css, parent, s, defs;
                if (ele.tagName == 'svg') {
                    rect = wijmo.getElementRect(ele.parentNode || ele);
                    width = rect.width || 0;
                    height = rect.height || 0;
                    viewBoxWidth = ele.viewBox.baseVal && ele.viewBox.baseVal.width !== 0 ? ele.viewBox.baseVal.width : width;
                    viewBoxHeight = ele.viewBox.baseVal && ele.viewBox.baseVal.height !== 0 ? ele.viewBox.baseVal.height : height;
                }
                else {
                    box = ele.getBBox();
                    width = box.x + box.width;
                    height = box.y + box.height;
                    clone.setAttribute('transform', clone.getAttribute('transform').replace(/translate\(.*?\)/, ''));
                    viewBoxWidth = width;
                    viewBoxHeight = height;
                    parent = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
                    parent.appendChild(clone);
                    clone = parent;
                }
                clone.setAttribute('version', '1.1');
                clone.setAttributeNS(ExportHelper.xmlns, 'xmlns', 'http://www.w3.org/2000/svg');
                clone.setAttributeNS(ExportHelper.xmlns, 'xmlns:xlink', 'http://www.w3.org/1999/xlink');
                clone.setAttribute('width', width);
                clone.setAttribute('height', height);
                //clone.setAttribute('width', width * options.scale);
                //clone.setAttribute('height', height * options.scale);
                clone.setAttribute('viewBox', '0 0 ' + viewBoxWidth + ' ' + viewBoxHeight);
                //add FlexChart's class to clone element.
                wijmo.addClass(clone, (ele.parentNode && ele.parentNode.getAttribute('class')) || '');
                outer.appendChild(clone);
                css = ExportHelper.getStyles(ele);
                s = document.createElement('style');
                s.setAttribute('type', 'text/css');
                s.innerHTML = "<![CDATA[\n" + css + "\n]]>";
                defs = document.createElement('defs');
                defs.appendChild(s);
                clone.insertBefore(defs, clone.firstChild);
                // encode then decode to handle `btoa` on Unicode; see MDN for `btoa`.
                return 'data:image/svg+xml;base64,' + window.btoa(window.unescape(encodeURIComponent(ExportHelper.doctype + outer.innerHTML)));
            };
            ExportHelper.getStyles = function (ele) {
                var css = '', styleSheets = document.styleSheets;
                if (styleSheets == null || styleSheets.length === 0) {
                    return null;
                }
                [].forEach.call(styleSheets, (function (sheet) {
                    //TODO: href, or other external resources
                    var cssRules;
                    try {
                        if (sheet.cssRules == null || sheet.cssRules.length === 0) {
                            return true;
                        }
                    }
                    //Note that SecurityError exception is specific to Firefox.
                    catch (e) {
                        if (e.name == 'SecurityError') {
                            console.log("SecurityError. Can't read: " + sheet.href);
                            return true;
                        }
                    }
                    cssRules = sheet.cssRules;
                    [].forEach.call(cssRules, (function (rule) {
                        var style = rule.style, match;
                        if (style == null) {
                            return true;
                        }
                        try {
                            match = ele.querySelector(rule.selectorText);
                        }
                        catch (e) {
                            console.warn('Invalid CSS selector "' + rule.selectorText + '"', e);
                        }
                        if (match) {
                            //var selector = selectorRemap ? selectorRemap(rule.selectorText) : rule.selectorText;
                            //css += selector + " { " + rule.style.cssText + " }\n";
                            css += rule.selectorText + " { " + style.cssText + " }\n";
                        }
                        else if (rule.cssText.match(/^@font-face/)) {
                            css += rule.cssText + '\n';
                        }
                    }));
                }));
                return css;
            };
            ExportHelper.doctype = '<?xml version="1.0" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">';
            ExportHelper.xmlns = 'http://www.w3.org/2000/xmlns/';
            return ExportHelper;
        }());
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * The @see:FlexPie control provides pie and doughnut charts with selectable
         * slices.
         *
         * To use the @see:FlexPie control, set the @see:FlexPie.itemsSource property
         * to an array containing the data and use the @see:FlexPie.binding and
         * @see:FlexPie.bindingName properties to set the properties that contain
         * the item values and names.
         */
        var FlexPie = /** @class */ (function (_super) {
            __extends(FlexPie, _super);
            /**
             * Initializes a new instance of the @see:FlexPie class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options A Javascript object containing initialization data for the control.
             */
            function FlexPie(element, options) {
                var _this = _super.call(this, element, null, true) || this;
                _this._areas = [];
                _this._keywords = new chart._KeyWords();
                _this._startAngle = 0;
                _this._innerRadius = 0;
                _this._offset = 0;
                _this._reversed = false;
                _this._isAnimated = false;
                _this._selectedItemPosition = chart.Position.None;
                _this._selectedItemOffset = 0;
                _this._rotationAngle = 0;
                _this._center = new wijmo.Point();
                _this._selectedOffset = new wijmo.Point();
                _this._selectedIndex = -1;
                _this._angles = [];
                _this._colRowLens = [];
                _this._values = [];
                _this._labels = [];
                _this._pels = [];
                _this._sum = 0;
                // add classes to host element
                _this.applyTemplate('wj-control wj-flexchart', null, null);
                _this._currentRenderEngine = new chart._SvgRenderEngine(_this.hostElement);
                _this._legend = new chart.Legend(_this);
                _this._tooltip = new chart.ChartTooltip();
                _this._tooltip.content = '<b>{name}</b><br/>{value}';
                _this._tooltip.showDelay = 0;
                _this._lbl = new chart.PieDataLabel();
                _this._lbl._chart = _this;
                var self = _this;
                // tooltips
                _this.hostElement.addEventListener('mousemove', function (evt) {
                    var tip = self._tooltip;
                    var tc = tip.content;
                    if (tc && !self.isTouching) {
                        var ht = self.hitTest(evt);
                        if (ht.distance <= tip.threshold) {
                            var content = self._getLabelContent(ht, self.tooltip.content);
                            self._showToolTip(content, new wijmo.Rect(evt.clientX, evt.clientY, 5, 5));
                        }
                        else {
                            self._hideToolTip();
                        }
                    }
                });
                // selection
                _this.hostElement.addEventListener('click', function (evt) {
                    var showToolTip = true;
                    if (self.selectionMode != chart.SelectionMode.None) {
                        var ht = self.hitTest(evt);
                        var thershold = chart.FlexChart._SELECTION_THRESHOLD;
                        if (self.tooltip && self.tooltip.threshold)
                            thershold = self.tooltip.threshold;
                        if (ht.distance <= thershold) {
                            if (ht.pointIndex != self._selectionIndex && self.selectedItemPosition != chart.Position.None) {
                                showToolTip = false;
                            }
                            if (ht.pointIndex != self._selectionIndex) {
                                self._select(ht.pointIndex, true);
                            }
                        }
                        else {
                            if (self._selectedIndex >= 0) {
                                self._select(null);
                            }
                        }
                    }
                    if (showToolTip && self.isTouching) {
                        var tip = self._tooltip;
                        var tc = tip.content;
                        if (tc) {
                            var ht = self.hitTest(evt);
                            if (ht.distance <= tip.threshold) {
                                var content = self._getLabelContent(ht, self.tooltip.content);
                                self._showToolTip(content, new wijmo.Rect(evt.clientX, evt.clientY, 5, 5));
                            }
                            else {
                                self._hideToolTip();
                            }
                        }
                    }
                });
                _this.hostElement.addEventListener('mouseleave', function (evt) {
                    self._hideToolTip();
                });
                // apply options only after chart is fully initialized
                _this.initialize(options);
                // refresh control to show current state
                _this.refresh();
                return _this;
            }
            Object.defineProperty(FlexPie.prototype, "binding", {
                /**
                 * Gets or sets the name of the property that contains the chart values.
                 */
                get: function () {
                    return this._binding;
                },
                set: function (value) {
                    if (value != this._binding) {
                        this._binding = wijmo.asString(value, true);
                        this._bindChart();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "bindingName", {
                /**
                 * Gets or sets the name of the property that contains the name of the data items.
                 */
                get: function () {
                    return this._bindingName;
                },
                set: function (value) {
                    if (value != this._bindingName) {
                        this._bindingName = wijmo.asString(value, true);
                        this._bindChart();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "startAngle", {
                ///**
                // * Gets or sets various chart options.
                // *
                // * The following options are supported: innerRadius, startAngle, reversed, offset.
                // * 
                // */
                //get options(): any {
                //    return this._options;
                //}
                //set options(value: any) {
                //    if (value != this._options) {
                //        this._options = value;
                //        this.invalidate();
                //    }
                //}
                /**
                 * Gets or sets the starting angle for the pie slices, in degrees.
                 *
                 * Angles are measured clockwise, starting at the 9 o'clock position.
                 */
                get: function () {
                    return this._startAngle;
                },
                set: function (value) {
                    if (value != this._startAngle) {
                        this._startAngle = wijmo.asNumber(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "offset", {
                /**
                 * Gets or sets the offset of the slices from the pie center.
                 *
                 * The offset is measured as a fraction of the pie radius.
                 */
                get: function () {
                    return this._offset;
                },
                set: function (value) {
                    if (value != this._offset) {
                        this._offset = wijmo.asNumber(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "innerRadius", {
                /**
                 * Gets or sets the size of the pie's inner radius.
                 *
                 * The inner radius is measured as a fraction of the pie radius.
                 *
                 * The default value for this property is zero, which creates
                 * a pie. Setting this property to values greater than zero
                 * creates pies with a hole in the middle, also known as
                 * doughnut charts.
                 */
                get: function () {
                    return this._innerRadius;
                },
                set: function (value) {
                    if (value != this._innerRadius) {
                        this._innerRadius = wijmo.asNumber(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "reversed", {
                /**
                 * Gets or sets a value that determines whether angles are reversed
                 * (counter-clockwise).
                 *
                 * The default value is false, which causes angles to be measured in
                 * the clockwise direction.
                 */
                get: function () {
                    return this._reversed;
                },
                set: function (value) {
                    if (value != this._reversed) {
                        this._reversed = wijmo.asBoolean(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "selectedItemPosition", {
                /**
                 * Gets or sets the position of the selected slice.
                 *
                 * Setting this property to a value other than 'None' causes
                 * the pie to rotate when an item is selected.
                 *
                 * Note that in order to select slices by clicking the chart,
                 * you must set the @see:selectionMode property to "Point".
                 */
                get: function () {
                    return this._selectedItemPosition;
                },
                set: function (value) {
                    if (value != this._selectedItemPosition) {
                        this._selectedItemPosition = wijmo.asEnum(value, wijmo.chart.Position, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "selectedItemOffset", {
                /**
                 * Gets or sets the offset of the selected slice from the pie center.
                 *
                 * Offsets are measured as a fraction of the pie radius.
                 */
                get: function () {
                    return this._selectedItemOffset;
                },
                set: function (value) {
                    if (value != this._selectedItemOffset) {
                        this._selectedItemOffset = wijmo.asNumber(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "isAnimated", {
                /**
                 * Gets or sets a value indicating whether to use animation when items are selected.
                 *
                 * See also the @see:selectedItemPosition and @see:selectionMode
                 * properties.
                 */
                get: function () {
                    return this._isAnimated;
                },
                set: function (value) {
                    if (value != this._isAnimated) {
                        this._isAnimated = value;
                        //this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "tooltip", {
                /**
                 * Gets the chart's @see:Tooltip.
                 */
                get: function () {
                    return this._tooltip;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "dataLabel", {
                /**
                 * Gets or sets the point data label.
                 */
                get: function () {
                    return this._lbl;
                },
                set: function (value) {
                    if (value != this._lbl) {
                        this._lbl = value;
                        if (this._lbl) {
                            this._lbl._chart = this;
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexPie.prototype, "selectedIndex", {
                /**
                 * Gets or sets the index of the selected slice.
                 */
                get: function () {
                    return this._selectedIndex;
                },
                set: function (value) {
                    if (value != this._selectedIndex) {
                        var index = wijmo.asNumber(value, true);
                        this._select(index, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            FlexPie.prototype._getLabelsForLegend = function () {
                return this._labels;
            };
            /**
             * Gets a @see:wijmo.chart.HitTestInfo object with information about the specified point.
             *
             * @param pt The point to investigate, in window coordinates.
             * @param y The Y coordinate of the point (if the first parameter is a number).
             * @return A @see:wijmo.chart.HitTestInfo object containing information about the point.
             */
            FlexPie.prototype.hitTest = function (pt, y) {
                // control coords
                var cpt = this._toControl(pt, y);
                var hti = new chart.HitTestInfo(this, cpt);
                var si = null;
                if (chart.FlexChart._contains(this._rectHeader, cpt)) {
                    hti._chartElement = chart.ChartElement.Header;
                }
                else if (chart.FlexChart._contains(this._rectFooter, cpt)) {
                    hti._chartElement = chart.ChartElement.Footer;
                }
                else if (chart.FlexChart._contains(this._rectLegend, cpt)) {
                    hti._chartElement = chart.ChartElement.Legend;
                    si = this.legend._hitTest(cpt);
                    if (si !== null && si >= 0 && si < this._areas.length) {
                        hti._setData(null, si);
                    }
                }
                else if (chart.FlexChart._contains(this._rectChart, cpt)) {
                    var len = this._areas.length, min_dist = NaN, min_area;
                    for (var i = 0; i < len; i++) {
                        var pt1 = cpt.clone();
                        if (this._rotationAngle != 0) {
                            var cx = this._center.x, cy = this._center.y;
                            var dx = -cx + pt1.x;
                            var dy = -cy + pt1.y;
                            var r = Math.sqrt(dx * dx + dy * dy);
                            var a = Math.atan2(dy, dx) - this._rotationAngle * Math.PI / 180;
                            pt1.x = cx + r * Math.cos(a);
                            pt1.y = cy + r * Math.sin(a);
                        }
                        if (i == this._selectedIndex) {
                            pt1.x -= this._selectedOffset.x;
                            pt1.y -= this._selectedOffset.y;
                        }
                        var area = this._areas[i];
                        if (area.contains(pt1)) {
                            hti._setData(null, area.tag);
                            hti._dist = 0;
                            if (i != this._selectedIndex) {
                                break;
                            }
                        }
                        var dist = area.distance(pt1);
                        if (dist !== undefined) {
                            if (isNaN(min_dist) || dist < min_dist) {
                                min_dist = dist;
                                min_area = area;
                            }
                        }
                    }
                    if (hti._dist !== 0 && min_area != null) {
                        hti._setData(null, min_area.tag);
                        hti._dist = min_dist;
                    }
                    hti._chartElement = chart.ChartElement.ChartArea;
                }
                else {
                    hti._chartElement = chart.ChartElement.None;
                }
                return hti;
            };
            // binds the chart to the current data source.
            FlexPie.prototype._performBind = function () {
                this._initData();
                if (this._cv) {
                    this._selectionIndex = this._cv.currentPosition;
                    var items = this._cv.items;
                    if (items) {
                        var len = items.length;
                        for (var i = 0; i < len; i++) {
                            var item = items[i];
                            this._sum += Math.abs(this._getBindData(item, this._values, this._labels, this.binding, this.bindingName));
                        }
                    }
                }
            };
            FlexPie.prototype._initData = function () {
                this._sum = 0;
                this._values = [];
                this._labels = [];
            };
            FlexPie.prototype._getBindData = function (item, values, labels, binding, bindingName) {
                var v, val = 0;
                if (binding) {
                    v = item[binding];
                }
                var val = 0;
                if (wijmo.isNumber(v)) {
                    val = wijmo.asNumber(v);
                }
                else {
                    if (v) {
                        val = parseFloat(v.toString());
                    }
                }
                if (!isNaN(val) && isFinite(val)) {
                    values.push(val);
                }
                else {
                    val = 0;
                    values.push(val);
                }
                if (bindingName && item) {
                    var name = item[bindingName];
                    if (name) {
                        name = name.toString();
                    }
                    labels.push(name);
                }
                else {
                    labels.push(val.toString());
                }
                return val;
            };
            FlexPie.prototype._render = function (engine, applyElement) {
                if (applyElement === void 0) { applyElement = true; }
                // cancelAnimationFrame(this._selectionAnimationID);
                if (this._selectionAnimationID) {
                    clearInterval(this._selectionAnimationID);
                }
                _super.prototype._render.call(this, engine, applyElement);
            };
            FlexPie.prototype._prepareRender = function () {
                this._areas = [];
            };
            FlexPie.prototype._renderChart = function (engine, rect, applyElement) {
                var r = this._rectChart.clone();
                var hostSz = new wijmo.Size(r.width, r.height);
                var tsz;
                this.onRendering(new chart.RenderEventArgs(engine));
                var w = rect.width;
                var h = rect.height;
                this._pieGroup = engine.startGroup(null, null, true); // all series
                var margins = this._parseMargin(this.plotMargin), lbl = this.dataLabel;
                var hasOutLabels = lbl.content && lbl.position == chart.PieLabelPosition.Outside;
                var outOffs = hasOutLabels ? (wijmo.isNumber(lbl.offset) ? lbl.offset : 0) + 24 : 0;
                if (isNaN(margins.left)) {
                    margins.left = hasOutLabels ? outOffs : FlexPie._MARGIN;
                }
                if (isNaN(margins.right)) {
                    margins.right = hasOutLabels ? outOffs : FlexPie._MARGIN;
                }
                if (isNaN(margins.top)) {
                    margins.top = hasOutLabels ? outOffs : FlexPie._MARGIN;
                }
                if (isNaN(margins.bottom)) {
                    margins.bottom = hasOutLabels ? outOffs : FlexPie._MARGIN;
                }
                rect.top += margins.top;
                var h = rect.height - (margins.top + margins.bottom);
                rect.height = h > 0 ? h : 24;
                rect.left += margins.left;
                var w = rect.width - (margins.left + margins.right);
                rect.width = w > 0 ? w : 24;
                this._renderData(engine, rect, this._pieGroup);
                engine.endGroup();
                this._rotationAngle = 0;
                this._highlightCurrent();
                if (this.dataLabel.content && this.dataLabel.position != chart.PieLabelPosition.None) {
                    this._renderLabels(engine);
                }
                this.onRendered(new chart.RenderEventArgs(engine));
            };
            FlexPie.prototype._getDesiredLegendSize = function (engine, isVertical, width, height) {
                var sz = new wijmo.Size();
                var pieChart = this;
                var rect = new wijmo.Size(width, height);
                var labels = pieChart._getLabelsForLegend();
                var len = labels.length;
                var cw = 0, rh = 0;
                this._colRowLens = [];
                for (var i = 0; i < len; i++) {
                    // measure the legend
                    var isz = pieChart._measureLegendItem(engine, labels[i]);
                    if (isVertical) {
                        if (rh + isz.height > height) {
                            sz.height = height;
                            this._colRowLens.push(cw);
                            cw = 0;
                            rh = 0;
                        }
                        if (cw < isz.width) {
                            cw = isz.width;
                        }
                        rh += isz.height;
                    }
                    else {
                        if (cw + isz.width > width) {
                            sz.width = width;
                            this._colRowLens.push(rh);
                            rh = 0;
                            cw = 0;
                        }
                        if (rh < isz.height) {
                            rh = isz.height;
                        }
                        cw += isz.width;
                    }
                }
                if (isVertical) {
                    if (sz.height < rh) {
                        sz.height = rh;
                    }
                    this._colRowLens.push(cw);
                    sz.width = this._colRowLens.reduce(function (a, b) { return a + b; }, 0);
                    //legend max width is width / 2
                    if (sz.width > rect.width / 2) {
                        sz.width = rect.width / 2;
                    }
                }
                else {
                    if (sz.width < cw) {
                        sz.width = cw;
                    }
                    this._colRowLens.push(rh);
                    sz.height = this._colRowLens.reduce(function (a, b) { return a + b; }, 0);
                    //legend max height is height / 2
                    if (sz.height > rect.height / 2) {
                        sz.height = rect.height / 2;
                    }
                }
                return sz;
            };
            FlexPie.prototype._renderLegend = function (engine, pos, areas, isVertical, width, height) {
                var pieChart = this;
                var rectLegend = pieChart._rectLegend;
                var labels = pieChart._getLabelsForLegend();
                var len = labels.length;
                var colRowLen = 0;
                var p = pos.clone();
                // draw legend items
                for (var i = 0; i < len; i++) {
                    var sz = pieChart._measureLegendItem(engine, labels[i]);
                    if (isVertical) {
                        if (p.y + sz.height > rectLegend.top + rectLegend.height + 1) {
                            p.x += this._colRowLens[colRowLen];
                            colRowLen++;
                            p.y = pos.y;
                        }
                    }
                    else {
                        if (p.x + sz.width > rectLegend.left + rectLegend.width + 1) {
                            p.y += this._colRowLens[colRowLen];
                            colRowLen++;
                            p.x = pos.x;
                        }
                    }
                    var rect = new wijmo.Rect(p.x, p.y, sz.width, sz.height);
                    pieChart._drawLegendItem(engine, rect, i, labels[i]);
                    areas.push(rect);
                    if (isVertical) {
                        p.y += sz.height;
                    }
                    else {
                        p.x += sz.width;
                    }
                }
            };
            FlexPie.prototype._renderData = function (engine, rect, g) {
                this._pels = [];
                this._angles = [];
                //engine.strokeWidth = 2;
                var sum = this._sum;
                var startAngle = this.startAngle + 180, // start from 9 o'clock
                innerRadius = this.innerRadius, offset = this.offset;
                if (sum > 0) {
                    var angle = startAngle * Math.PI / 180, cx0 = rect.left + 0.5 * rect.width, cy0 = rect.top + 0.5 * rect.height, r = Math.min(0.5 * rect.width, 0.5 * rect.height);
                    this._center.x = cx0;
                    this._center.y = cy0;
                    var maxoff = Math.max(offset, this.selectedItemOffset);
                    if (maxoff > 0) {
                        r = r / (1 + maxoff);
                        offset = offset * r;
                    }
                    this._radius = r;
                    var irad = innerRadius * r;
                    this._renderPie(engine, r, irad, angle, offset);
                    this._highlightCurrent();
                }
            };
            FlexPie.prototype._renderPie = function (engine, radius, innerRadius, startAngle, offset) {
                this._renderSlices(engine, this._values, this._sum, radius, innerRadius, startAngle, 2 * Math.PI, offset);
            };
            FlexPie.prototype._getCenter = function () {
                return this._center;
            };
            FlexPie.prototype._renderSlices = function (engine, values, sum, radius, innerRadius, startAngle, totalSweep, offset) {
                var len = values.length, angle = startAngle, reversed = this.reversed == true, center = this._center, sweep, pel, cx, cy, 
                //Max sweep is 359.9/360 if len > 1, to prevent data like [1, 99999].
                totalAngle = len === 1 ? 360 : 359.9 / 360;
                for (var i = 0; i < len; i++) {
                    cx = center.x;
                    cy = center.y;
                    pel = engine.startGroup();
                    engine.fill = this._getColorLight(i);
                    engine.stroke = this._getColor(i);
                    var val = Math.abs(values[i]);
                    var sweep = Math.abs(val - sum) < 1E-10 ? totalSweep : totalSweep * val / sum;
                    sweep = Math.min(sweep, totalSweep * totalAngle);
                    var currentAngle = reversed ? angle - 0.5 * sweep : angle + 0.5 * sweep;
                    if (offset > 0 && sweep < totalSweep) {
                        cx += offset * Math.cos(currentAngle);
                        cy += offset * Math.sin(currentAngle);
                    }
                    this._renderSlice(engine, cx, cy, currentAngle, i, radius, innerRadius, angle, sweep, totalSweep);
                    if (reversed) {
                        angle -= sweep;
                    }
                    else {
                        angle += sweep;
                    }
                    engine.endGroup();
                    this._pels.push(pel);
                }
            };
            FlexPie.prototype._renderSlice = function (engine, cx, cy, currentAngle, idx, radius, innerRadius, startAngle, sweep, totalSweep) {
                var _this = this;
                var reversed = !!this.reversed;
                this._angles.push(currentAngle);
                if (this.itemFormatter) {
                    var hti = new chart.HitTestInfo(this, new wijmo.Point(cx + radius * Math.cos(currentAngle), cy + radius * Math.sin(currentAngle)), chart.ChartElement.SeriesSymbol);
                    hti._setData(null, idx);
                    this.itemFormatter(engine, hti, function () {
                        _this._drawSlice(engine, idx, reversed, cx, cy, radius, innerRadius, startAngle, sweep);
                    });
                }
                else {
                    this._drawSlice(engine, idx, reversed, cx, cy, radius, innerRadius, startAngle, sweep);
                }
            };
            FlexPie.prototype._renderLabels = function (engine) {
                var len = this._areas.length, lbl = this.dataLabel, pos = lbl.position, marg = 2, gcss = 'wj-data-labels', lcss = 'wj-data-label', bcss = 'wj-data-label-border', clcss = 'wj-data-label-line', da = this._rotationAngle, line = lbl.connectingLine, lofs = lbl.offset ? lbl.offset : 0;
                engine.stroke = 'null';
                engine.fill = 'transparent';
                engine.strokeWidth = 1;
                engine.startGroup(gcss);
                for (var i = 0; i < len; i++) {
                    var seg = this._areas[i];
                    if (seg) {
                        var r = seg.radius;
                        var a = (seg.langle + da);
                        var ha = 1, va = 1;
                        if (pos == chart.PieLabelPosition.Center) {
                            r *= 0.5 * (1 + (seg.innerRadius || 0) / seg.radius);
                        }
                        else {
                            a = _Math.clampAngle(a);
                            if (a <= -170 || a >= 170) {
                                ha = 2;
                                va = 1;
                            }
                            else if (a >= -100 && a <= -80) {
                                ha = 1;
                                va = 2;
                            }
                            else if (a >= -10 && a <= 10) {
                                ha = 0;
                                va = 1;
                            }
                            else if (a >= 80 && a <= 100) {
                                ha = 1;
                                va = 0;
                            }
                            else if (-180 < a && a < -90) {
                                ha = 2;
                                va = 2;
                            }
                            else if (-90 <= a && a < 0) {
                                ha = 0;
                                va = 2;
                            }
                            else if (0 < a && a < 90) {
                                ha = 0;
                                va = 0;
                            }
                            else if (90 < a && a < 180) {
                                ha = 2;
                                va = 0;
                            }
                            if (pos == chart.PieLabelPosition.Inside) {
                                ha = 2 - ha;
                                va = 2 - va;
                            }
                        }
                        a *= Math.PI / 180;
                        var dx = 0, dy = 0, off = 0;
                        if (i == this._selectedIndex && this.selectedItemOffset > 0) {
                            off = this.selectedItemOffset;
                        }
                        if (off > 0) {
                            dx = Math.cos(a) * off * this._radius;
                            dy = Math.sin(a) * off * this._radius;
                        }
                        var r0 = r;
                        if (pos == chart.PieLabelPosition.Outside) {
                            r0 += lofs;
                        }
                        else if (pos == chart.PieLabelPosition.Inside) {
                            r0 -= lofs;
                        }
                        var centerX = seg.center.x;
                        var centerY = seg.center.y;
                        var offX = centerX - this._center.x;
                        var offY = centerY - this._center.y;
                        if (this._rotationAngle != 0) {
                            var offR = Math.sqrt(offX * offX + offY * offY);
                            var offA = Math.atan2(offY, offX) + this._rotationAngle * Math.PI / 180;
                            centerX = this._center.x + offR * Math.cos(offA);
                            centerY = this._center.y + offR * Math.sin(offA);
                        }
                        var pt = new wijmo.Point(centerX + dx + r0 * Math.cos(a), centerY + dy + r0 * Math.sin(a));
                        if (lbl.border && pos != chart.PieLabelPosition.Center) {
                            if (ha == 0)
                                pt.x += marg;
                            else if (ha == 2)
                                pt.x -= marg;
                            if (va == 0)
                                pt.y += marg;
                            else if (va == 2)
                                pt.y -= marg;
                        }
                        var hti = new chart.HitTestInfo(this, pt);
                        hti._setData(null, i);
                        var content = this._getLabelContent(hti, lbl.content);
                        var ea = new chart.DataLabelRenderEventArgs(engine, hti, pt, content);
                        if (lbl.onRendering) {
                            if (lbl.onRendering(ea)) {
                                content = ea.text;
                                pt = ea.point;
                            }
                            else {
                                content = null;
                            }
                        }
                        if (content) {
                            var lr = chart.FlexChart._renderText(engine, content, pt, ha, va, lcss);
                            if (lbl.border) {
                                engine.drawRect(lr.left - marg, lr.top - marg, lr.width + 2 * marg, lr.height + 2 * marg, bcss);
                            }
                            if (line) {
                                var pt2 = new wijmo.Point(seg.center.x + dx + (r) * Math.cos(a), seg.center.y + dy + (r) * Math.sin(a));
                                engine.drawLine(pt.x, pt.y, pt2.x, pt2.y, clcss);
                            }
                        }
                    }
                }
                engine.endGroup();
            };
            FlexPie.prototype._drawSlice = function (engine, i, reversed, cx, cy, r, irad, angle, sweep) {
                var area;
                if (reversed) {
                    if (irad > 0) {
                        if (sweep != 0) {
                            engine.drawDonutSegment(cx, cy, r, irad, angle - sweep, sweep);
                        }
                        area = new _DonutSegment(new wijmo.Point(cx, cy), r, irad, angle - sweep, sweep, this.startAngle);
                        area.tag = i;
                        this._areas.push(area);
                    }
                    else {
                        if (sweep != 0) {
                            engine.drawPieSegment(cx, cy, r, angle - sweep, sweep);
                        }
                        area = new _PieSegment(new wijmo.Point(cx, cy), r, angle - sweep, sweep, this.startAngle);
                        area.tag = i;
                        this._areas.push(area);
                    }
                }
                else {
                    if (irad > 0) {
                        if (sweep != 0) {
                            engine.drawDonutSegment(cx, cy, r, irad, angle, sweep);
                        }
                        area = new _DonutSegment(new wijmo.Point(cx, cy), r, irad, angle, sweep, this.startAngle);
                        area.tag = i;
                        this._areas.push(area);
                    }
                    else {
                        if (sweep != 0) {
                            engine.drawPieSegment(cx, cy, r, angle, sweep);
                        }
                        area = new _PieSegment(new wijmo.Point(cx, cy), r, angle, sweep, this.startAngle);
                        area.tag = i;
                        this._areas.push(area);
                    }
                    angle += sweep;
                }
            };
            FlexPie.prototype._measureLegendItem = function (engine, name) {
                var sz = new wijmo.Size();
                sz.width = chart.Series._LEGEND_ITEM_WIDTH;
                sz.height = chart.Series._LEGEND_ITEM_HEIGHT;
                if (name) {
                    var tsz = engine.measureString(name, chart.FlexChart._CSS_LABEL, chart.FlexChart._CSS_LEGEND);
                    sz.width += tsz.width;
                    if (sz.height < tsz.height) {
                        sz.height = tsz.height;
                    }
                }
                ;
                sz.width += 3 * chart.Series._LEGEND_ITEM_MARGIN;
                sz.height += 2 * chart.Series._LEGEND_ITEM_MARGIN;
                return sz;
            };
            FlexPie.prototype._drawLegendItem = function (engine, rect, i, name) {
                engine.strokeWidth = 1;
                var marg = chart.Series._LEGEND_ITEM_MARGIN;
                var fill = null;
                var stroke = null;
                if (fill === null)
                    fill = this._getColorLight(i);
                if (stroke === null)
                    stroke = this._getColor(i);
                engine.fill = fill;
                engine.stroke = stroke;
                var yc = rect.top + 0.5 * rect.height;
                var wsym = chart.Series._LEGEND_ITEM_WIDTH;
                var hsym = chart.Series._LEGEND_ITEM_HEIGHT;
                engine.drawRect(rect.left + marg, yc - 0.5 * hsym, wsym, hsym, null); //, this.style);
                if (name != null) {
                    chart.FlexChart._renderText(engine, name.toString(), new wijmo.Point(rect.left + hsym + 2 * marg, yc), 0, 1, chart.FlexChart._CSS_LABEL);
                }
            };
            //---------------------------------------------------------------------
            // tooltips
            FlexPie.prototype._getLabelContent = function (ht, content) {
                if (wijmo.isString(content)) {
                    return this._keywords.replace(content, ht);
                }
                else if (wijmo.isFunction(content)) {
                    return content(ht);
                }
                return null;
            };
            //---------------------------------------------------------------------
            // selection
            FlexPie.prototype._select = function (pointIndex, animate) {
                if (animate === void 0) { animate = false; }
                this._highlight(false, this._selectionIndex);
                this._selectionIndex = pointIndex;
                if (this.selectionMode == chart.SelectionMode.Point) {
                    var cv = this._cv;
                    if (cv) {
                        this._notifyCurrentChanged = false;
                        cv.moveCurrentToPosition(pointIndex);
                        this._notifyCurrentChanged = true;
                    }
                }
                if (!this.isAnimated && (this.selectedItemOffset > 0 || this.selectedItemPosition != chart.Position.None)) {
                    this._selectedIndex = pointIndex == null ? -1 : pointIndex;
                    this.invalidate();
                }
                else {
                    this._highlight(true, this._selectionIndex, animate);
                }
                this.onSelectionChanged();
            };
            FlexPie.prototype._highlightCurrent = function () {
                if (this.selectionMode != chart.SelectionMode.None) {
                    var pointIndex = -1;
                    var cv = this._cv;
                    if (cv) {
                        pointIndex = cv.currentPosition;
                    }
                    this._highlight(true, pointIndex);
                }
            };
            FlexPie.prototype._highlight = function (selected, pointIndex, animate) {
                if (animate === void 0) { animate = false; }
                if (this.selectionMode == chart.SelectionMode.Point && pointIndex !== undefined && pointIndex !== null && pointIndex >= 0) {
                    var gs = this._pels[pointIndex];
                    if (selected) {
                        if (gs) {
                            gs.parentNode.appendChild(gs);
                            var ells = this._find(gs, ['ellipse']);
                            this._highlightItems(this._find(gs, ['path', 'ellipse']), chart.FlexChart._CSS_SELECTION, selected);
                        }
                        var selectedAngle = this._angles[pointIndex];
                        if (this.selectedItemPosition != chart.Position.None && selectedAngle != 0) {
                            var angle = 0;
                            if (this.selectedItemPosition == chart.Position.Left) {
                                angle = 180;
                            }
                            else if (this.selectedItemPosition == chart.Position.Top) {
                                angle = -90;
                            }
                            else if (this.selectedItemPosition == chart.Position.Bottom) {
                                angle = 90;
                            }
                            var targetAngle = angle * Math.PI / 180 - selectedAngle; // - this._rotationAngle;
                            targetAngle *= 180 / Math.PI;
                            if (animate && this.isAnimated) {
                                this._animateSelectionAngle(targetAngle, 0.5);
                            }
                            else {
                                this._rotationAngle = targetAngle;
                                this._pieGroup.transform.baseVal.getItem(0).setRotate(targetAngle, this._center.x, this._center.y);
                            }
                        }
                        var off = this.selectedItemOffset;
                        if (off > 0 && ells && ells.length == 0) {
                            var x = this._selectedOffset.x = Math.cos(selectedAngle) * off * this._radius;
                            var y = this._selectedOffset.y = Math.sin(selectedAngle) * off * this._radius;
                            if (gs) {
                                gs.setAttribute('transform', 'translate(' + x.toFixed() + ',' + y.toFixed() + ')');
                            }
                        }
                        this._selectedIndex = pointIndex;
                    }
                    else {
                        if (gs) {
                            gs.parentNode.insertBefore(gs, gs.parentNode.childNodes.item(pointIndex));
                            gs.removeAttribute('transform');
                            this._highlightItems(this._find(gs, ['path', 'ellipse']), chart.FlexChart._CSS_SELECTION, selected);
                        }
                        if (this._selectedIndex == pointIndex) {
                            this._selectedIndex = -1;
                        }
                    }
                }
            };
            FlexPie.prototype._animateSelectionAngle = function (target, duration) {
                var source = _Math.clampAngle(this._rotationAngle);
                target = _Math.clampAngle(target);
                /*var delta = (target - source) / (60 * duration);
                this._selectionAnimationID = requestAnimationFrame(doAnim);
                var self = this;
    
                function doAnim() {
    
                    source += delta;
    
                    if ( Math.abs(target-source) < Math.abs(delta)) {
                       self._rotationAngle = source = target;
                    }
    
                    self._pieGroup.transform.baseVal.getItem(0).setRotate(source, self._center.x, self._center.y);
    
                    if (target == source) {
                        cancelAnimationFrame(self._selectionAnimationID);
                    } else {
                        self._selectionAnimationID = requestAnimationFrame(doAnim);
                    }
                }*/
                var delta = (target - source);
                var self = this;
                var start = source;
                var group = self._pieGroup;
                if (self._selectionAnimationID) {
                    clearInterval(this._selectionAnimationID);
                }
                this._selectionAnimationID = wijmo.animate(function (pct) {
                    if (group == self._pieGroup) {
                        self._rotationAngle = source = start + delta * pct;
                        self._pieGroup.transform.baseVal.getItem(0).setRotate(source, self._center.x, self._center.y);
                        if (pct == 1) {
                            clearInterval(self._selectionAnimationID);
                        }
                        if (pct > 0.99) {
                            if (self.selectedItemOffset > 0 || self.selectedItemPosition != chart.Position.None) {
                                self.invalidate();
                            }
                        }
                    }
                }, duration * 1000);
            };
            FlexPie.prototype._getHitTestItem = function (index) {
                var items = null, item = null;
                if (this._cv != null) {
                    items = this._cv.items;
                }
                else {
                    items = this.itemsSource;
                }
                if (items && index < items.length) {
                    item = items[index];
                }
                return item;
            };
            FlexPie.prototype._getHitTestValue = function (index) {
                return this._values[index];
            };
            FlexPie.prototype._getHitTestLabel = function (index) {
                return this._labels[index];
            };
            FlexPie._MARGIN = 4;
            return FlexPie;
        }(chart.FlexChartBase));
        chart.FlexPie = FlexPie;
        var _Math = /** @class */ (function () {
            function _Math() {
            }
            // degrees [-180, +180]  
            _Math.clampAngle = function (angle, startAngle) {
                if (startAngle === void 0) { startAngle = 0; }
                var a = (angle + 180) % 360 - 180;
                if (a < -180 + (startAngle < 0 ? startAngle + 360 : startAngle)) {
                    a += 360;
                }
                return a;
            };
            return _Math;
        }());
        var _PieSegment = /** @class */ (function () {
            function _PieSegment(center, radius, angle, sweep, startAngle) {
                if (startAngle === void 0) { startAngle = 0; }
                this._isFull = false;
                this._center = center;
                this._radius = radius;
                this._originAngle = angle;
                this._originSweep = sweep;
                if (sweep >= 2 * Math.PI) {
                    this._isFull = true;
                }
                this._sweep = 0.5 * sweep * 180 / Math.PI;
                this._angle = _Math.clampAngle(angle * 180 / Math.PI + this._sweep);
                this._radius2 = radius * radius;
                this._startAngle = startAngle;
            }
            _PieSegment.prototype.contains = function (pt) {
                var dx = pt.x - this._center.x;
                var dy = pt.y - this._center.y;
                var r2 = dx * dx + dy * dy;
                if (r2 <= this._radius2) {
                    var a = Math.atan2(dy, dx) * 180 / Math.PI;
                    var delta = _Math.clampAngle(this._angle, this._startAngle) - _Math.clampAngle(a, this._startAngle);
                    if (this._isFull || Math.abs(delta) <= this._sweep) {
                        return true;
                    }
                }
                return false;
            };
            _PieSegment.prototype.distance = function (pt) {
                if (this.contains(pt)) {
                    return 0;
                }
                var dx = pt.x - this._center.x;
                var dy = pt.y - this._center.y;
                var r2 = dx * dx + dy * dy;
                var a = Math.atan2(dy, dx) * 180 / Math.PI;
                var delta = _Math.clampAngle(this._angle, this._startAngle) - _Math.clampAngle(a, this._startAngle);
                if (this._isFull || Math.abs(delta) <= this._sweep) {
                    return Math.sqrt(r2) - this._radius;
                }
                return undefined;
            };
            Object.defineProperty(_PieSegment.prototype, "center", {
                get: function () {
                    return this._center;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_PieSegment.prototype, "radius", {
                get: function () {
                    return this._radius;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_PieSegment.prototype, "langle", {
                get: function () {
                    return this._angle;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_PieSegment.prototype, "angle", {
                get: function () {
                    return this._originAngle;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_PieSegment.prototype, "sweep", {
                get: function () {
                    return this._originSweep;
                },
                enumerable: true,
                configurable: true
            });
            return _PieSegment;
        }());
        chart._PieSegment = _PieSegment;
        var _DonutSegment = /** @class */ (function () {
            function _DonutSegment(center, radius, innerRadius, angle, sweep, startAngle) {
                if (startAngle === void 0) { startAngle = 0; }
                this._isFull = false;
                this._center = center;
                this._radius = radius;
                this._iradius = innerRadius;
                this._originAngle = angle;
                this._originSweep = sweep;
                if (sweep >= 2 * Math.PI) {
                    this._isFull = true;
                }
                this._sweep = 0.5 * sweep * 180 / Math.PI;
                this._angle = _Math.clampAngle(angle * 180 / Math.PI + this._sweep);
                this._radius2 = radius * radius;
                this._iradius2 = innerRadius * innerRadius;
                this._startAngle = startAngle;
            }
            _DonutSegment.prototype.contains = function (pt) {
                var dx = pt.x - this._center.x;
                var dy = pt.y - this._center.y;
                var r2 = dx * dx + dy * dy;
                if (r2 >= this._iradius2 && r2 <= this._radius2) {
                    var a = Math.atan2(dy, dx) * 180 / Math.PI;
                    var delta = _Math.clampAngle(this._angle, this._startAngle) - _Math.clampAngle(a, this._startAngle);
                    if (this._isFull || Math.abs(delta) <= this._sweep) {
                        return true;
                    }
                }
                return false;
            };
            _DonutSegment.prototype.distance = function (pt) {
                if (this.contains(pt)) {
                    return 0;
                }
                return undefined;
            };
            Object.defineProperty(_DonutSegment.prototype, "center", {
                get: function () {
                    return this._center;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DonutSegment.prototype, "radius", {
                get: function () {
                    return this._radius;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DonutSegment.prototype, "langle", {
                get: function () {
                    return this._angle;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DonutSegment.prototype, "angle", {
                get: function () {
                    return this._originAngle;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DonutSegment.prototype, "sweep", {
                get: function () {
                    return this._originSweep;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DonutSegment.prototype, "innerRadius", {
                get: function () {
                    return this._iradius;
                },
                enumerable: true,
                configurable: true
            });
            return _DonutSegment;
        }());
        chart._DonutSegment = _DonutSegment;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Specifies whether and how to stack the chart's data values.
         */
        var Stacking;
        (function (Stacking) {
            /** No stacking. Each series object is plotted independently. */
            Stacking[Stacking["None"] = 0] = "None";
            /** Stacked charts show how each value contributes to the total. */
            Stacking[Stacking["Stacked"] = 1] = "Stacked";
            /** 100% stacked charts show how each value contributes to the total with the relative size of
             * each series representing its contribution to the total. */
            Stacking[Stacking["Stacked100pc"] = 2] = "Stacked100pc";
        })(Stacking = chart.Stacking || (chart.Stacking = {}));
        /**
         * Specifies what is selected when the user clicks the chart.
         */
        var SelectionMode;
        (function (SelectionMode) {
            /** Select neither series nor data points when the user clicks the chart. */
            SelectionMode[SelectionMode["None"] = 0] = "None";
            /** Select the whole @see:Series when the user clicks it on the chart. */
            SelectionMode[SelectionMode["Series"] = 1] = "Series";
            /** Select the data point when the user clicks it on the chart. Since Line, Area, Spline,
             * and SplineArea charts do not render individual data points, nothing is selected with this
             * setting on those chart types. */
            SelectionMode[SelectionMode["Point"] = 2] = "Point";
        })(SelectionMode = chart.SelectionMode || (chart.SelectionMode = {}));
        ;
        /**
         * The core charting control for @see:FlexChart.
         *
         */
        var FlexChartCore = /** @class */ (function (_super) {
            __extends(FlexChartCore, _super);
            /**
             * Initializes a new instance of the @see:FlexChart class.
             *
             * @param element The DOM element that will host the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options A JavaScript object containing initialization data for the control.
             */
            function FlexChartCore(element, options) {
                var _this = _super.call(this, element, null, true) || this;
                // property storage
                _this._series = new wijmo.collections.ObservableArray();
                _this._axes = new chart.AxisCollection();
                _this._pareas = new chart.PlotAreaCollection();
                _this._interpolateNulls = false;
                _this._legendToggle = false;
                _this._symbolSize = 10;
                _this._dataInfo = new _DataInfo();
                _this.__barPlotter = null;
                _this.__linePlotter = null;
                _this.__areaPlotter = null;
                _this.__bubblePlotter = null;
                _this.__financePlotter = null;
                _this.__funnelPlotter = null;
                _this._plotters = [];
                _this._rotated = false;
                _this._stacking = Stacking.None;
                _this._xlabels = [];
                _this._xvals = [];
                _this._lblAreas = [];
                _this._colRowLens = [];
                // specify the string to be used as a binding separator.
                // the default is a comma, but in some charts (like PivotChart)
                // the bindings might contain commas, so allow them to change this.
                _this._bindingSeparator = ',';
                /**
                 * Occurs when the series visibility changes, for example when the legendToggle
                 * property is set to true and the user clicks the legend.
                */
                _this.seriesVisibilityChanged = new wijmo.Event();
                // add classes to host element
                _this.applyTemplate('wj-control wj-flexchart', null, null);
                // handle changes to chartSeries array
                var self = _this;
                self._series.collectionChanged.addHandler(function () {
                    // check that chartSeries array contains Series objects
                    var arr = self._series;
                    for (var i = 0; i < arr.length; i++) {
                        var cs = wijmo.tryCast(arr[i], wijmo.chart.SeriesBase);
                        if (!cs) {
                            throw 'chartSeries array must contain SeriesBase objects.';
                        }
                        cs._chart = self;
                        if (cs.axisX) {
                            cs.axisX._chart = self;
                        }
                        if (cs.axisY) {
                            cs.axisY._chart = self;
                        }
                    }
                    // refresh chart to show the change
                    //self.refresh();
                    self.invalidate();
                });
                _this._currentRenderEngine = new chart._SvgRenderEngine(_this.hostElement);
                _this._hitTester = new chart._HitTester(_this);
                _this._legend = new chart.Legend(_this);
                _this._tooltip = new ChartTooltip();
                _this._tooltip.showDelay = 0;
                _this._lbl = new chart.DataLabel();
                _this._lbl._chart = _this;
                _this._initAxes();
                self._axes.collectionChanged.addHandler(function () {
                    var arr = self._axes;
                    for (var i = 0; i < arr.length; i++) {
                        var axis = wijmo.tryCast(arr[i], wijmo.chart.Axis);
                        if (!axis) {
                            throw 'axes array must contain Axis objects.';
                        }
                        axis._chart = self;
                    }
                    // refresh chart to show the change
                    //self.refresh();
                    self.invalidate();
                });
                self._pareas.collectionChanged.addHandler(function () {
                    var arr = self._pareas;
                    for (var i = 0; i < arr.length; i++) {
                        var pa = wijmo.tryCast(arr[i], wijmo.chart.PlotArea);
                        if (!pa) {
                            throw 'plotAreas array must contain PlotArea objects.';
                        }
                        pa._chart = self;
                    }
                    // refresh chart to show the change
                    //self.refresh();
                    self.invalidate();
                });
                _this._keywords = new chart._KeyWords();
                //if (isTouchDevice()) {
                _this.hostElement.addEventListener('click', function (evt) {
                    var tip = self._tooltip;
                    var tc = tip.content;
                    if (tc && self.isTouching) {
                        var ht = self.hitTest(evt);
                        if (ht.distance <= tip.threshold) {
                            var content = self._getLabelContent(ht, self._tooltip.content);
                            self._showToolTip(content, new wijmo.Rect(evt.clientX, evt.clientY, 5, 5));
                        }
                        else {
                            self._hideToolTip();
                        }
                    }
                });
                //} else {
                _this.hostElement.addEventListener('mousemove', function (evt) {
                    var tip = self._tooltip;
                    var tc = tip.content;
                    if (tc && !self.isTouching) {
                        var ht = self.hitTest(evt);
                        if (ht.distance <= tip.threshold) {
                            var content = self._getLabelContent(ht, self._tooltip.content);
                            self._showToolTip(content, new wijmo.Rect(evt.clientX, evt.clientY, 5, 5));
                        }
                        else {
                            self._hideToolTip();
                        }
                    }
                });
                //}
                _this.hostElement.addEventListener('mouseleave', function (evt) {
                    self._hideToolTip();
                });
                _this.hostElement.addEventListener('click', function (evt) {
                    if (self.selectionMode != SelectionMode.None) {
                        var ht = self._hitTestData(evt);
                        var thershold = chart.FlexChart._SELECTION_THRESHOLD;
                        if (self.tooltip && self.tooltip.threshold)
                            thershold = self.tooltip.threshold;
                        if (ht.distance <= thershold && ht.series) {
                            self._select(ht.series, ht.pointIndex);
                        }
                        else {
                            if (self.selectionMode == SelectionMode.Series) {
                                ht = self.hitTest(evt);
                                if (ht.chartElement == chart.ChartElement.Legend && ht.series) {
                                    self._select(ht.series, null);
                                }
                                else {
                                    self._select(null, null);
                                }
                            }
                            else {
                                self._select(null, null);
                            }
                        }
                    }
                    if (self.legendToggle === true) {
                        ht = self.hitTest(evt);
                        if (ht.chartElement == chart.ChartElement.Legend && ht.series) {
                            if (ht.series.visibility == chart.SeriesVisibility.Legend) {
                                ht.series.visibility = chart.SeriesVisibility.Visible;
                            }
                            else if (ht.series.visibility == chart.SeriesVisibility.Visible) {
                                ht.series.visibility = chart.SeriesVisibility.Legend;
                            }
                            self.focus();
                        }
                    }
                });
                // apply options only after chart is fully initialized
                _this.initialize(options);
                return _this;
            }
            FlexChartCore.prototype._initAxes = function () {
                this._axisX = new chart.Axis(chart.Position.Bottom);
                this._axisY = new chart.Axis(chart.Position.Left);
                // default style
                this._axisX.majorGrid = false;
                this._axisX.name = 'axisX';
                this._axisY.majorGrid = true;
                this._axisY.majorTickMarks = chart.TickMark.None;
                this._axisY.name = 'axisY';
                this._axisX._chart = this;
                this._axisY._chart = this;
                this._axes.push(this._axisX);
                this._axes.push(this._axisY);
            };
            Object.defineProperty(FlexChartCore.prototype, "series", {
                //--------------------------------------------------------------------------
                // ** object model
                /**
                 * Gets the collection of @see:Series objects.
                 */
                get: function () {
                    return this._series;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "axes", {
                /**
                 * Gets the collection of @see:Axis objects.
                 */
                get: function () {
                    return this._axes;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "axisX", {
                /**
                 * Gets or sets the main X axis.
                 */
                get: function () {
                    return this._axisX;
                },
                set: function (value) {
                    if (value != this._axisX) {
                        var ax = this._axisX = wijmo.asType(value, chart.Axis);
                        // set default axis attributes
                        this.beginUpdate();
                        if (ax) {
                            if (ax.majorGrid === undefined) {
                                ax.majorGrid = false;
                            }
                            if (ax.name === undefined) {
                                ax.name = 'axisX';
                            }
                            if (ax.position === undefined) {
                                ax.position = chart.Position.Bottom;
                            }
                            ax._axisType = chart.AxisType.X;
                            ax._chart = this;
                        }
                        this.endUpdate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "axisY", {
                /**
                 * Gets or sets the main Y axis.
                 */
                get: function () {
                    return this._axisY;
                },
                set: function (value) {
                    if (value != this._axisY) {
                        var ay = this._axisY = wijmo.asType(value, chart.Axis);
                        // set default axis attributes
                        this.beginUpdate();
                        if (ay) {
                            if (ay.majorGrid === undefined) {
                                ay.majorGrid = true;
                            }
                            if (ay.name === undefined) {
                                ay.name = 'axisY';
                            }
                            ay.majorTickMarks = chart.TickMark.None;
                            if (ay.position === undefined) {
                                ay.position = chart.Position.Left;
                            }
                            ay._axisType = chart.AxisType.Y;
                            ay._chart = this;
                        }
                        this.endUpdate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "plotAreas", {
                /**
                 * Gets the collection of @see:PlotArea objects.
                 */
                get: function () {
                    return this._pareas;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "binding", {
                /**
                 * Gets or sets the name of the property that contains the Y values.
                 */
                get: function () {
                    return this._binding;
                },
                set: function (value) {
                    if (value != this._binding) {
                        this._binding = wijmo.asString(value, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "bindingX", {
                /**
                 * Gets or sets the name of the property that contains the X data values.
                 */
                get: function () {
                    return this._bindingX;
                },
                set: function (value) {
                    if (value != this._bindingX) {
                        this._bindingX = wijmo.asString(value, true);
                        this._bindChart();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "symbolSize", {
                /**
                 * Gets or sets the size of the symbols used for all Series objects in this @see:FlexChart.
                 *
                 * This property may be overridden by the symbolSize property on each @see:Series object.
                 */
                get: function () {
                    return this._symbolSize;
                },
                set: function (value) {
                    if (value != this._symbolSize) {
                        this._symbolSize = wijmo.asNumber(value, false, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "interpolateNulls", {
                /**
                 * Gets or sets a value that determines whether to interpolate
                 * null values in the data.
                 *
                 * If true, the chart interpolates the value of any missing data
                 * based on neighboring points. If false, it leaves a break in
                 * lines and areas at the points with null values.
                 */
                get: function () {
                    return this._interpolateNulls;
                },
                set: function (value) {
                    if (value != this._interpolateNulls) {
                        this._interpolateNulls = wijmo.asBoolean(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "legendToggle", {
                /**
                 * Gets or sets a value indicating whether clicking legend items toggles the
                 * series visibility in the chart.
                 */
                get: function () {
                    return this._legendToggle;
                },
                set: function (value) {
                    if (value != this._legendToggle) {
                        this._legendToggle = wijmo.asBoolean(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "tooltip", {
                /**
                 * Gets the chart @see:Tooltip object.
                 *
                 * The tooltip content is generated using a template that may contain any of the following
                 * parameters:
                 *
                 * <ul>
                 *  <li><b>propertyName</b>:    Any property of the data object represented by the point.</li>
                 *  <li><b>seriesName</b>:      Name of the series that contains the data point (FlexChart only).</li>
                 *  <li><b>pointIndex</b>:      Index of the data point.</li>
                 *  <li><b>value</b>:           <b>Value</b> of the data point (y-value for @see:FlexChart, item value for @see:FlexPie).</li>
                 *  <li><b>x</b>:               <b>x</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>y</b>:               <b>y</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>name</b>:            <b>Name</b> of the data point (x-value for @see:FlexChart or legend entry for @see:FlexPie).</li>
                 * </ul>
                 *
                 * To modify the template, assign a new value to the tooltip's content property.
                 * For example:
                 *
                 * <pre>
                 * chart.tooltip.content = '&lt;b&gt;{seriesName}&lt;/b&gt; ' +
                 *    '&lt;img src="resources/{x}.png"/&gt;&lt;br/&gt;{y}';
                 * </pre>
                 *
                 * You can disable chart tooltips by setting the template to an empty string.
                 *
                 * You can also use the @see:tooltip property to customize tooltip parameters
                 * such as @see:Tooltip.showDelay and @see:Tooltip.hideDelay:
                 *
                 * <pre>
                 * chart.tooltip.showDelay = 1000;
                 * </pre>
                 *
                 * See @see:ChartTooltip properties for more details and options.
                 */
                get: function () {
                    return this._tooltip;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "dataLabel", {
                /**
                 * Gets or sets the point data label.
                 */
                get: function () {
                    return this._lbl;
                },
                set: function (value) {
                    if (value != this._lbl) {
                        this._lbl = wijmo.asType(value, chart.DataLabel);
                        if (this._lbl) {
                            this._lbl._chart = this;
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "selection", {
                /**
                 * Gets or sets the selected chart series.
                 */
                get: function () {
                    return this._selection;
                },
                set: function (value) {
                    if (value != this._selection) {
                        this._selection = wijmo.asType(value, chart.SeriesBase, true);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:seriesVisibilityChanged event.
             *
             * @param e The @see:SeriesEventArgs object that contains the event data.
             */
            FlexChartCore.prototype.onSeriesVisibilityChanged = function (e) {
                this.seriesVisibilityChanged.raise(this, e);
            };
            /**
             * Gets a @see:wijmo.chart.HitTestInfo object with information about the specified point.
             *
             * @param pt The point to investigate, in window coordinates.
             * @param y The Y coordinate of the point (if the first parameter is a number).
             * @return A @see:wijmo.chart.HitTestInfo object with information about the point.
             */
            FlexChartCore.prototype.hitTest = function (pt, y) {
                // control coords
                var cpt = this._toControl(pt, y);
                var hti = new chart.HitTestInfo(this, cpt);
                var si = null;
                if (chart.FlexChart._contains(this._rectHeader, cpt)) {
                    hti._chartElement = chart.ChartElement.Header;
                }
                else if (chart.FlexChart._contains(this._rectFooter, cpt)) {
                    hti._chartElement = chart.ChartElement.Footer;
                }
                else if (chart.FlexChart._contains(this._rectLegend, cpt)) {
                    hti._chartElement = chart.ChartElement.Legend;
                    si = this.legend._hitTest(cpt);
                    if (si !== null && si >= 0 && si < this.series.length) {
                        if (this._getChartType() === chart.ChartType.Bar) {
                            hti._setData(this.series[this.series.length - 1 - si]);
                        }
                        else {
                            hti._setData(this.series[si]);
                        }
                    }
                }
                else if (chart.FlexChart._contains(this._rectChart, cpt)) {
                    var lblArea = this._hitTestLabels(cpt);
                    if (lblArea) {
                        hti._chartElement = chart.ChartElement.DataLabel;
                        hti._dist = 0;
                        hti._setDataPoint(lblArea.tag);
                    }
                    else {
                        var hr = this._hitTester.hitTest(cpt);
                        // custom series hit test
                        var ht = null;
                        var htsi = null;
                        for (var i = this.series.length - 1; i >= 0; i--) {
                            if (this.series[i].hitTest !== chart.Series.prototype.hitTest) {
                                var hts = this.series[i].hitTest(pt);
                                if (hts) {
                                    if (!ht || hts.distance < ht.distance) {
                                        ht = hts;
                                        htsi = i;
                                    }
                                    if (hts.distance === 0) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (hr && hr.area) {
                            if (ht && ht.distance < hr.distance) {
                                hti = ht;
                            }
                            else if (ht && ht.distance == hr.distance && htsi > hr.area.tag.seriesIndex) {
                                hti = ht;
                            }
                            else {
                                hti._setDataPoint(hr.area.tag);
                                hti._dist = hr.distance;
                            }
                        }
                        else if (ht) {
                            hti = ht;
                        }
                        if (chart.FlexChart._contains(this.axisX._axrect, cpt)) {
                            hti._chartElement = chart.ChartElement.AxisX;
                        }
                        else if (chart.FlexChart._contains(this.axisY._axrect, cpt)) {
                            hti._chartElement = chart.ChartElement.AxisY;
                        }
                        else if (chart.FlexChart._contains(this._plotRect, cpt)) {
                            hti._chartElement = chart.ChartElement.PlotArea;
                        }
                        else if (chart.FlexChart._contains(this._rectChart, cpt)) {
                            hti._chartElement = chart.ChartElement.ChartArea;
                        }
                    }
                }
                else {
                    hti._chartElement = chart.ChartElement.None;
                }
                return hti;
            };
            /**
             * Converts a @see:Point from control coordinates to chart data coordinates.
             *
             * @param pt The point to convert, in control coordinates.
             * @param y The Y coordinate of the point (if the first parameter is a number).
             * @return The point in chart data coordinates.
             */
            FlexChartCore.prototype.pointToData = function (pt, y) {
                if (wijmo.isNumber(pt) && wijmo.isNumber(y)) {
                    pt = new wijmo.Point(pt, y);
                }
                if (pt instanceof MouseEvent) {
                    pt = new wijmo.Point(pt.pageX, pt.pageY);
                    pt = this._toControl(pt);
                }
                else {
                    pt = pt.clone();
                }
                pt.x = this.axisX.convertBack(pt.x);
                pt.y = this.axisY.convertBack(pt.y);
                return pt;
            };
            /**
             * Converts a @see:Point from data coordinates to control coordinates.
             *
             * @param pt @see:Point in data coordinates, or X coordinate of a point in data coordinates.
             * @param y Y coordinate of the point (if the first parameter is a number).
             * @return The @see:Point in control coordinates.
             */
            FlexChartCore.prototype.dataToPoint = function (pt, y) {
                if (wijmo.isNumber(pt) && wijmo.isNumber(y)) {
                    pt = new wijmo.Point(pt, y);
                }
                wijmo.asType(pt, wijmo.Point);
                var cpt = pt.clone();
                cpt.x = this.axisX.convert(cpt.x);
                cpt.y = this.axisY.convert(cpt.y);
                return cpt;
            };
            //--------------------------------------------------------------------------
            // implementation
            // method used in JSON-style initialization
            FlexChartCore.prototype._copy = function (key, value) {
                if (key == 'series') {
                    this.series.clear();
                    var arr = wijmo.asArray(value);
                    for (var i = 0; i < arr.length; i++) {
                        var s = this._createSeries();
                        wijmo.copy(s, arr[i]);
                        this.series.push(s);
                    }
                    return true;
                }
                return false;
            };
            FlexChartCore.prototype._createSeries = function () {
                return new chart.Series();
            };
            FlexChartCore.prototype._clearCachedValues = function () {
                for (var i = 0; i < this._series.length; i++) {
                    var series = this._series[i];
                    if (series.itemsSource == null)
                        series._clearValues();
                }
            };
            FlexChartCore.prototype._performBind = function () {
                this._xDataType = null;
                this._xlabels.splice(0);
                this._xvals.splice(0);
                if (this._cv) {
                    var items = this._cv.items;
                    if (items) {
                        var len = items.length;
                        for (var i = 0; i < len; i++) {
                            var item = items[i];
                            if (this._bindingX) {
                                var x = item[this._bindingX];
                                if (wijmo.isNumber(x)) {
                                    this._xvals.push(wijmo.asNumber(x));
                                    this._xDataType = wijmo.DataType.Number;
                                }
                                else if (wijmo.isDate(x)) {
                                    this._xvals.push(wijmo.asDate(x).valueOf());
                                    this._xDataType = wijmo.DataType.Date;
                                }
                                this._xlabels.push(item[this._bindingX]);
                            }
                        }
                        if (this._xvals.length == len) {
                            this._xlabels.splice(0);
                        }
                        else {
                            this._xvals.splice(0);
                        }
                    }
                }
            };
            FlexChartCore.prototype._hitTestSeries = function (pt, seriesIndex) {
                // control coords
                //var cpt = pt.clone();
                //var host = this.hostElement;
                //cpt.x -= host.offsetLeft;
                //cpt.y -= host.offsetTop;
                var cpt = this._toControl(pt);
                var hti = new chart.HitTestInfo(this, cpt);
                var si = seriesIndex;
                var hr = this._hitTester.hitTestSeries(cpt, seriesIndex);
                if (hr && hr.area) {
                    hti._setDataPoint(hr.area.tag);
                    hti._chartElement = chart.ChartElement.PlotArea;
                    hti._dist = hr.distance;
                }
                return hti;
            };
            // hitTest including lines
            FlexChartCore.prototype._hitTestData = function (pt) {
                var cpt = this._toControl(pt);
                var hti = new chart.HitTestInfo(this, cpt);
                var hr = this._hitTester.hitTest(cpt, true);
                if (hr && hr.area) {
                    hti._setDataPoint(hr.area.tag);
                    hti._dist = hr.distance;
                }
                return hti;
            };
            FlexChartCore.prototype._hitTestLabels = function (pt) {
                var area = null;
                var len = this._lblAreas.length;
                for (var i = 0; i < len; i++) {
                    if (this._lblAreas[i].contains(pt)) {
                        area = this._lblAreas[i];
                        break;
                    }
                }
                return area;
            };
            /* private _hitTestLines(hti: HitTestInfo): HitTestInfo {
                if (hti.series) {
                    var pi = hti.pointIndex;
                    var p0 = hti.series._indexToPoint(pi);
    
                    // jQuery
                    //var offset = $(this.hostElement).offset();
                    var offset = this._getHostOffset();
    
                    p0 = this.dataToPoint(p0);
                    p0.x -= offset.x;
                    p0.y -= offset.y;
    
                    var d1 = null,
                        d2 = null;
                    var p1 = hti.series._indexToPoint(pi - 1);
                    var p2 = hti.series._indexToPoint(pi + 1);
                    if (p1) {
                        p1 = this.dataToPoint(p1);
                        p1.x -= offset.x;
                        p1.y -= offset.y;
                        d1 = FlexChart._dist2(p0, p1);
                    }
                    if (p2) {
                        p2 = this.dataToPoint(p2);
                        p2.x -= offset.x;
                        p2.y -= offset.y;
                        d2 = FlexChart._dist2(p0, p2);
                    }
    
                    var pt = hti.point.clone();
                    var host = this.hostElement;
                    pt.x -= host.offsetLeft;
                    pt.y -= host.offsetTop;
    
                    if (d1 && d2) {
                        if (d1 < d2) {
                            hti._dist = FlexChart._dist(pt, p0, p1);
                        }
                        else {
                            hti._dist = FlexChart._dist(pt, p0, p2);
                        }
                    } else if (d1) {
                        hti._dist = FlexChart._dist(pt, p0, p1);
                    } else if (d2) {
                        hti._dist = FlexChart._dist(pt, p0, p2);
                    }
                }
    
                return hti;
            }*/
            FlexChartCore._dist2 = function (p1, p2) {
                var dx = p1.x - p2.x;
                var dy = p1.y - p2.y;
                return dx * dx + dy * dy;
            };
            // line p1-p2 to point p0
            /*static _dist(p0: Point, p1: Point, p2: Point): number {
                var dx = p2.x - p1.x;
                var dy = p2.y - p1.y;
                return Math.sqrt(Math.abs(dy * p0.x - dx * p0.y - p1.x * p2.y + p2.x * p1.y) / Math.sqrt(dx * dx + dy * dy));
            }*/
            FlexChartCore._dist = function (p0, p1, p2) {
                return Math.sqrt(chart.FlexChart._distToSegmentSquared(p0, p1, p2));
            };
            FlexChartCore._distToSegmentSquared = function (p, v, w) {
                var l2 = chart.FlexChart._dist2(v, w);
                if (l2 == 0)
                    return chart.FlexChart._dist2(p, v);
                var t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
                if (t < 0)
                    return chart.FlexChart._dist2(p, v);
                if (t > 1)
                    return chart.FlexChart._dist2(p, w);
                return chart.FlexChart._dist2(p, new wijmo.Point(v.x + t * (w.x - v.x), v.y + t * (w.y - v.y)));
            };
            FlexChartCore.prototype._isRotated = function () {
                return this._getChartType() == chart.ChartType.Bar ? !this._rotated : this._rotated;
            };
            FlexChartCore.prototype._getChartType = function () {
                return null;
            };
            FlexChartCore.prototype._prepareRender = function () {
                this._hitTester.clear();
            };
            FlexChartCore.prototype._renderChart = function (engine, rect, applyElement) {
                var tsz;
                var r = this._rectChart.clone();
                var hostSz = new wijmo.Size(r.width, r.height);
                var w = rect.width;
                var h = rect.height;
                //
                var plotter = this._getPlotter(null);
                plotter.stacking = this._stacking;
                if (this._curPlotter != plotter) {
                    if (this._curPlotter) {
                        this._curPlotter.unload(); // clean up / restore chart settings
                    }
                    this._curPlotter = plotter;
                }
                plotter.load(); // change global chart settings
                var isRotated = this._isRotated();
                this._dataInfo.analyse(this._series, isRotated, plotter.stacking, this._xvals.length > 0 ? this._xvals : null, this.axisX.logBase > 0, this.axisY.logBase > 0);
                var rect0 = plotter.adjustLimits(this._dataInfo, rect.clone());
                if (isRotated) {
                    var ydt = this._dataInfo.getDataTypeX();
                    if (!ydt) {
                        ydt = this._xDataType;
                    }
                    this.axisX._updateActualLimits(this._dataInfo.getDataTypeY(), rect0.left, rect0.right);
                    this.axisY._updateActualLimits(ydt, rect0.top, rect0.bottom, this._xlabels, this._xvals);
                }
                else {
                    var xdt = this._dataInfo.getDataTypeX();
                    if (!xdt) {
                        xdt = this._xDataType;
                    }
                    this.axisX._updateActualLimits(xdt, rect0.left, rect0.right, this._xlabels, this._xvals);
                    this.axisY._updateActualLimits(this._dataInfo.getDataTypeY(), rect0.top, rect0.bottom);
                }
                var axes = this._getAxes();
                this._updateAuxAxes(axes, isRotated);
                //
                this._layout(rect, hostSz, engine);
                // render plot areas
                engine.startGroup(chart.FlexChart._CSS_PLOT_AREA);
                engine.fill = 'transparent';
                engine.stroke = null;
                var plen = this.plotAreas.length;
                if (plen > 0) {
                    for (var i = 0; i < this.plotAreas.length; i++) {
                        var pa = this.plotAreas[i];
                        pa._render(engine);
                    }
                }
                else {
                    var prect = this._plotRect;
                    engine.drawRect(prect.left, prect.top, prect.width, prect.height);
                }
                engine.endGroup();
                var len = this._series.length;
                this._clearPlotters();
                var groups = {};
                for (var i = 0; i < len; i++) {
                    var series = this._series[i], vis = series.visibility;
                    if ((vis == chart.SeriesVisibility.Visible || vis == chart.SeriesVisibility.Plot) && series.getValues(0)) {
                        var ay = series._getAxisY();
                        var plotter = this._getPlotter(series);
                        // auxiliary y-axis and non-bar plots has own series group
                        if (ay && ay != this.axisY && !(plotter instanceof chart._BarPlotter)) {
                            var axid = ay._uniqueId;
                            if (!groups[axid]) {
                                groups[axid] = { count: 1, index: 0 };
                            }
                            else {
                                groups[axid].count += 1;
                            }
                        }
                        else {
                            plotter.seriesCount++;
                        }
                    }
                }
                this.onRendering(new chart.RenderEventArgs(engine));
                //Don't draw axis for funnel chart.
                if (this._getChartType() !== chart.ChartType.Funnel) {
                    for (var i = 0; i < axes.length; i++) {
                        var ax = axes[i], ele;
                        if (ax.axisType == chart.AxisType.X) {
                            ele = engine.startGroup(chart.FlexChart._CSS_AXIS_X, this._chartRectId);
                        }
                        else {
                            ele = engine.startGroup(chart.FlexChart._CSS_AXIS_Y, this._chartRectId);
                        }
                        ax._hostElement = applyElement ? ele : ax._hostElement;
                        ax._render(engine);
                        engine.endGroup();
                    }
                }
                engine.startGroup('wj-series-group'); // all series
                this._plotrectId = 'plotRect' + (1000000 * Math.random()).toFixed();
                engine.addClipRect(this._plotRect, this._plotrectId);
                for (var i = 0; i < len; i++) {
                    var series_1 = this._series[i];
                    series_1._pointIndexes = [];
                    var plotter = this._getPlotter(series_1);
                    series_1._plotter = plotter;
                    var ele = engine.startGroup(series_1.cssClass, plotter.clipping ? this._plotrectId : null);
                    series_1._hostElement = applyElement ? ele : series_1._hostElement;
                    var vis = series_1.visibility;
                    var axisX = series_1.axisX;
                    var axisY = series_1.axisY;
                    if (!axisX) {
                        axisX = this.axisX;
                    }
                    if (!axisY) {
                        axisY = this.axisY;
                    }
                    if (vis == chart.SeriesVisibility.Visible || vis == chart.SeriesVisibility.Plot) {
                        var group = groups[axisY._uniqueId];
                        var index, count;
                        if (group) {
                            index = group.index;
                            count = group.count;
                            group.index++;
                            if (!series_1.onRendering(engine, index, count)) {
                                plotter.plotSeries(engine, axisX, axisY, series_1, this, index, count);
                            }
                        }
                        else {
                            index = plotter.seriesIndex;
                            count = plotter.seriesCount;
                            plotter.seriesIndex++;
                            if (!series_1.onRendering(engine, index, count)) {
                                plotter.plotSeries(engine, axisX, axisY, series_1, this, index, count);
                            }
                        }
                        series_1.onRendered(engine);
                    }
                    engine.endGroup();
                }
                engine.endGroup();
                this._lblAreas = [];
                if (this.dataLabel.content && this.dataLabel.position != chart.LabelPosition.None) {
                    this._renderLabels(engine);
                }
                this._highlightCurrent();
                this.onRendered(new chart.RenderEventArgs(engine));
            };
            FlexChartCore.prototype._getDesiredLegendSize = function (engine, isVertical, width, height) {
                // measure all series
                var sz = new wijmo.Size();
                var arr = this.series;
                var len = arr.length;
                var rh = 0;
                var cw = 0;
                this._colRowLens = [];
                for (var i = 0; i < len; i++) {
                    // get the series
                    var series = wijmo.tryCast(arr[i], wijmo.chart.SeriesBase);
                    // skip hidden series and series with no names
                    var vis = series.visibility;
                    if (!series.name || vis == chart.SeriesVisibility.Hidden || vis == chart.SeriesVisibility.Plot) {
                        continue;
                    }
                    var slen = series.legendItemLength();
                    for (var si = 0; si < slen; si++) {
                        // measure the legend
                        var isz = series.measureLegendItem(engine, si);
                        if (isVertical) {
                            if (rh + isz.height > height) {
                                sz.height = height;
                                this._colRowLens.push(cw);
                                cw = 0;
                                rh = 0;
                            }
                            if (cw < isz.width) {
                                cw = isz.width;
                            }
                            rh += isz.height;
                        }
                        else {
                            if (cw + isz.width > width) {
                                sz.width = width;
                                this._colRowLens.push(rh);
                                rh = 0;
                                cw = 0;
                            }
                            if (rh < isz.height) {
                                rh = isz.height;
                            }
                            cw += isz.width;
                        }
                    }
                }
                if (isVertical) {
                    if (sz.height < rh) {
                        sz.height = rh;
                    }
                    this._colRowLens.push(cw);
                    sz.width = this._colRowLens.reduce(function (a, b) { return a + b; }, 0);
                    //legend max width is width / 2
                    if (sz.width > width / 2) {
                        sz.width = width / 2;
                    }
                }
                else {
                    if (sz.width < cw) {
                        sz.width = cw;
                    }
                    this._colRowLens.push(rh);
                    sz.height = this._colRowLens.reduce(function (a, b) { return a + b; }, 0);
                    //legend max height is height / 2
                    if (sz.height > height / 2) {
                        sz.height = height / 2;
                    }
                }
                return sz;
            };
            FlexChartCore.prototype._renderLegend = function (engine, pos, areas, isVertical, width, height) {
                var arr = this.series;
                var len = arr.length;
                var series;
                var p = pos.clone();
                var colRowLen = 0;
                // draw legend items
                if (this._getChartType() === chart.ChartType.Bar) {
                    for (var i = len - 1; i >= 0; i--) {
                        series = wijmo.tryCast(arr[i], wijmo.chart.SeriesBase);
                        colRowLen = this._renderLegendElements(engine, series, pos, p, areas, isVertical, width, height, colRowLen);
                    }
                }
                else {
                    for (var i = 0; i < len; i++) {
                        series = wijmo.tryCast(arr[i], wijmo.chart.SeriesBase);
                        colRowLen = this._renderLegendElements(engine, series, pos, p, areas, isVertical, width, height, colRowLen);
                    }
                }
            };
            FlexChartCore.prototype._renderLegendElements = function (engine, series, pos, p, areas, isVertical, width, height, colRowLen) {
                var rectLegend = this._rectLegend;
                var colLen = colRowLen;
                var rh = 0, cw = 0;
                if (!series) {
                    return colLen;
                }
                // skip hidden series and series with no names
                var vis = series.visibility;
                if (!series.name || vis == chart.SeriesVisibility.Hidden || vis == chart.SeriesVisibility.Plot) {
                    series._legendElement = null;
                    areas.push(null);
                    return colLen;
                }
                var slen = series.legendItemLength();
                var g = engine.startGroup(series.cssClass);
                if (vis == chart.SeriesVisibility.Legend) {
                    g.setAttribute('opacity', '0.5');
                    series._legendElement = g;
                }
                else if (vis == chart.SeriesVisibility.Visible) {
                    series._legendElement = g;
                }
                else {
                    series._legendElement = null;
                }
                for (var si = 0; si < slen; si++) {
                    // create legend item
                    var sz = series.measureLegendItem(engine, si);
                    if (isVertical) {
                        if (p.y + sz.height > rectLegend.top + rectLegend.height + 1) {
                            p.x += this._colRowLens[colLen];
                            colLen++;
                            p.y = pos.y;
                        }
                    }
                    else {
                        if (p.x + sz.width > rectLegend.left + rectLegend.width + 1) {
                            p.y += this._colRowLens[colLen];
                            colLen++;
                            p.x = pos.x;
                        }
                    }
                    var rect = new wijmo.Rect(p.x, p.y, sz.width, sz.height);
                    if (vis == chart.SeriesVisibility.Legend || vis == chart.SeriesVisibility.Visible) {
                        series.drawLegendItem(engine, rect, si);
                    }
                    // done, move on to next item
                    areas.push(rect);
                    if (isVertical) {
                        p.y += sz.height;
                    }
                    else {
                        p.x += sz.width;
                    }
                }
                engine.endGroup();
                return colLen;
            };
            FlexChartCore.prototype._renderLabels = function (engine) {
                var srs = this.series;
                var slen = srs.length;
                engine.stroke = 'null';
                engine.fill = 'transparent';
                engine.strokeWidth = 1;
                var gcss = 'wj-data-labels';
                engine.startGroup(gcss);
                for (var i = 0; i < slen; i++) {
                    var ser = srs[i];
                    var smap = this._hitTester._map[i];
                    if (smap) {
                        ser._renderLabels(engine, smap, this, this._lblAreas);
                    }
                }
                engine.endGroup();
            };
            FlexChartCore.prototype._getAxes = function () {
                var axes = [this.axisX, this.axisY];
                var len = this.series.length;
                for (var i = 0; i < len; i++) {
                    var ser = this.series[i];
                    var ax = ser.axisX;
                    if (ax && axes.indexOf(ax) === -1) {
                        axes.push(ax);
                    }
                    var ay = ser.axisY;
                    if (ay && axes.indexOf(ay) === -1) {
                        axes.push(ay);
                    }
                }
                return axes;
            };
            FlexChartCore.prototype._clearPlotters = function () {
                var len = this._plotters.length;
                for (var i = 0; i < len; i++)
                    this._plotters[i].clear();
            };
            FlexChartCore.prototype._initPlotter = function (plotter) {
                plotter.chart = this;
                plotter.dataInfo = this._dataInfo;
                plotter.hitTester = this._hitTester;
                this._plotters.push(plotter);
            };
            Object.defineProperty(FlexChartCore.prototype, "_barPlotter", {
                get: function () {
                    if (this.__barPlotter === null) {
                        this.__barPlotter = new chart._BarPlotter();
                        this._initPlotter(this.__barPlotter);
                    }
                    return this.__barPlotter;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "_linePlotter", {
                get: function () {
                    if (this.__linePlotter === null) {
                        this.__linePlotter = new chart._LinePlotter();
                        this._initPlotter(this.__linePlotter);
                    }
                    return this.__linePlotter;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "_areaPlotter", {
                get: function () {
                    if (this.__areaPlotter === null) {
                        this.__areaPlotter = new chart._AreaPlotter();
                        this._initPlotter(this.__areaPlotter);
                    }
                    return this.__areaPlotter;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "_bubblePlotter", {
                get: function () {
                    if (this.__bubblePlotter === null) {
                        this.__bubblePlotter = new chart._BubblePlotter();
                        this._initPlotter(this.__bubblePlotter);
                    }
                    return this.__bubblePlotter;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "_financePlotter", {
                get: function () {
                    if (this.__financePlotter === null) {
                        this.__financePlotter = new chart._FinancePlotter();
                        this._initPlotter(this.__financePlotter);
                    }
                    return this.__financePlotter;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChartCore.prototype, "_funnelPlotter", {
                get: function () {
                    if (this.__funnelPlotter === null) {
                        this.__funnelPlotter = new chart._FunnelPlotter();
                        this._initPlotter(this.__funnelPlotter);
                    }
                    return this.__funnelPlotter;
                },
                enumerable: true,
                configurable: true
            });
            FlexChartCore.prototype._getPlotter = function (series) {
                var chartType = this._getChartType();
                var isSeries = false;
                if (series) {
                    var stype = series._getChartType();
                    if (stype !== null && stype !== undefined && stype != chartType) {
                        chartType = stype;
                        isSeries = true;
                    }
                }
                var plotter;
                switch (chartType) {
                    case chart.ChartType.Column:
                        this._barPlotter.isVolume = false;
                        this._barPlotter.width = 0.7;
                        plotter = this._barPlotter;
                        break;
                    case chart.ChartType.Bar:
                        this._barPlotter.rotated = !this._rotated;
                        this._barPlotter.isVolume = false;
                        this._barPlotter.width = 0.7;
                        plotter = this._barPlotter;
                        break;
                    case chart.ChartType.Line:
                        this._linePlotter.hasSymbols = false;
                        this._linePlotter.hasLines = true;
                        this._linePlotter.isSpline = false;
                        plotter = this._linePlotter;
                        break;
                    case chart.ChartType.Scatter:
                        this._linePlotter.hasSymbols = true;
                        this._linePlotter.hasLines = false;
                        this._linePlotter.isSpline = false;
                        plotter = this._linePlotter;
                        break;
                    case chart.ChartType.LineSymbols:
                        this._linePlotter.hasSymbols = true;
                        this._linePlotter.hasLines = true;
                        this._linePlotter.isSpline = false;
                        plotter = this._linePlotter;
                        break;
                    case chart.ChartType.Area:
                        this._areaPlotter.isSpline = false;
                        plotter = this._areaPlotter;
                        break;
                    case chart.ChartType.Bubble:
                        plotter = this._bubblePlotter;
                        break;
                    case chart.ChartType.Candlestick:
                        var fp = this._financePlotter;
                        fp.isCandle = true;
                        fp.isEqui = false;
                        fp.isArms = false;
                        fp.isVolume = false;
                        plotter = fp;
                        break;
                    case chart.ChartType.HighLowOpenClose:
                        var fp = this._financePlotter;
                        fp.isCandle = false;
                        fp.isEqui = false;
                        fp.isArms = false;
                        fp.isVolume = false;
                        plotter = fp;
                        break;
                    case chart.ChartType.Spline:
                        this._linePlotter.hasSymbols = false;
                        this._linePlotter.hasLines = true;
                        this._linePlotter.isSpline = true;
                        plotter = this._linePlotter;
                        break;
                    case chart.ChartType.SplineSymbols:
                        this._linePlotter.hasSymbols = true;
                        this._linePlotter.hasLines = true;
                        this._linePlotter.isSpline = true;
                        plotter = this._linePlotter;
                        break;
                    case chart.ChartType.SplineArea:
                        this._areaPlotter.isSpline = true;
                        plotter = this._areaPlotter;
                        break;
                    case chart.ChartType.Funnel:
                        plotter = this._funnelPlotter;
                        break;
                    default:
                        throw 'Invalid chart type.';
                }
                plotter.rotated = this._rotated;
                if (chartType == chart.ChartType.Bar)
                    plotter.rotated = !plotter.rotated;
                if (isSeries) {
                    plotter.rotated = this._isRotated();
                }
                return plotter;
            };
            FlexChartCore.prototype._layout = function (rect, size, engine) {
                if (this.plotAreas.length > 0) {
                    this._layoutMultiple(rect, size, engine);
                }
                else {
                    this._layoutSingle(rect, size, engine);
                }
            };
            FlexChartCore.prototype._layoutSingle = function (rect, size, engine) {
                var w = rect.width;
                var h = rect.height;
                var mxsz = new wijmo.Size(w, 0.75 * h);
                var mysz = new wijmo.Size(h, 0.75 * w);
                var left = 0, top = 0, right = w, bottom = h;
                var l0 = 0, t0 = 0, r0 = w, b0 = h;
                var axes = this._getAxes();
                for (var i = 0; i < axes.length; i++) {
                    var ax = axes[i];
                    var origin = ax.origin;
                    var pos = ax._getPosition();
                    if (ax.axisType == chart.AxisType.X) {
                        var ah = ax._getHeight(engine, w);
                        if (ah > mxsz.height)
                            ah = mxsz.height;
                        ax._desiredSize = new wijmo.Size(mxsz.width, ah);
                        var hasOrigin = ax._hasOrigin =
                            wijmo.isNumber(origin) && origin > this.axisY._getMinNum() && origin < this.axisY._getMaxNum();
                        var annoWidth = Math.min(0.25 * w, ax._annoSize.width);
                        if (pos == chart.Position.Bottom) {
                            left = Math.max(left, annoWidth * 0.5);
                            right = Math.min(right, w - annoWidth * 0.5);
                            if (hasOrigin) {
                                var yorigin = this._convertY(origin, t0, b0);
                                b0 = b0 - Math.max(0, (yorigin + ah) - b0);
                            }
                            else {
                                b0 = b0 - ah;
                            }
                        }
                        else if (pos == chart.Position.Top) {
                            left = Math.max(left, annoWidth * 0.5);
                            right = Math.min(right, w - annoWidth * 0.5);
                            if (hasOrigin) {
                                var yorigin = this._convertY(origin, t0, b0);
                                t0 = t0 + Math.max(0, t0 - (yorigin - ah));
                            }
                            else {
                                t0 = t0 + ah;
                            }
                        }
                    }
                    else if (ax.axisType == chart.AxisType.Y) {
                        var ah = ax._getHeight(engine, h);
                        if (ah > mysz.height) {
                            ah = mysz.height;
                        }
                        ax._desiredSize = new wijmo.Size(mysz.width, ah);
                        var hasOrigin = ax._hasOrigin =
                            wijmo.isNumber(origin) && origin > this.axisX._getMinNum() && origin < this.axisX._getMaxNum();
                        if (pos == chart.Position.Left) {
                            if (ax._actualAngle < 0) {
                                bottom = Math.min(bottom, h - ax._annoSize.height);
                            }
                            else if (ax._actualAngle > 0) {
                                top = Math.max(top, ax._annoSize.height);
                            }
                            else {
                                top = Math.max(top, ax._annoSize.height);
                                bottom = Math.min(bottom, h - ax._annoSize.height);
                            }
                            if (hasOrigin) {
                                var xorigin = this._convertX(origin, l0, r0);
                                l0 += Math.max(0, l0 - (xorigin - ah));
                            }
                            else {
                                l0 += ah;
                            }
                        }
                        else if (pos == chart.Position.Right) {
                            if (ax._actualAngle > 0) {
                                bottom = Math.min(bottom, h - ax._annoSize.height);
                            }
                            else if (ax._actualAngle < 0) {
                                top = Math.max(top, ax._annoSize.height);
                            }
                            else {
                                top = Math.max(top, ax._annoSize.height);
                                bottom = Math.min(bottom, h - ax._annoSize.height);
                            }
                            if (hasOrigin) {
                                var xorigin = this._convertX(origin, l0, r0);
                                r0 = r0 - Math.max(0, (xorigin + ah) - r0);
                            }
                            else {
                                r0 = r0 - ah;
                            }
                        }
                    }
                }
                // todo: custom margins
                var margins = this._parseMargin(this.plotMargin);
                if (!isNaN(margins.left)) {
                    left = l0 = margins.left;
                }
                else {
                    left = l0 = Math.max(left, l0) + rect.left;
                }
                if (!isNaN(margins.right)) {
                    right = r0 = size.width - margins.right;
                }
                else {
                    right = r0 = Math.min(right, r0) + rect.left;
                }
                if (!isNaN(margins.top)) {
                    top = t0 = margins.top;
                }
                else {
                    top = t0 = Math.max(top, t0) + rect.top;
                }
                if (!isNaN(margins.bottom)) {
                    bottom = b0 = size.height - margins.bottom;
                }
                else {
                    bottom = b0 = Math.min(bottom, b0) + rect.top;
                }
                w = Math.max(1, right - left);
                h = Math.max(1, bottom - top);
                this._plotRect = new wijmo.Rect(left, top, w, h);
                engine.stroke = null;
                //engine.setFill(this.plotFill);
                for (var i = 0; i < axes.length; i++) {
                    var ax = axes[i];
                    //ax._plot = _plot0;
                    var origin = ax.origin;
                    var pos = ax._getPosition();
                    if (ax.axisType == chart.AxisType.X) {
                        var axr;
                        if (!ax._hasOrigin) {
                            if (pos == chart.Position.Bottom) {
                                axr = new wijmo.Rect(left, b0, w, ax._desiredSize.height);
                                b0 += ax._desiredSize.height;
                            }
                            else if (pos == chart.Position.Top) {
                                axr = new wijmo.Rect(left, t0 - ax._desiredSize.height, w, ax._desiredSize.height);
                                t0 -= ax._desiredSize.height;
                            }
                            else {
                                axr = new wijmo.Rect(left, t0, w, 1);
                            }
                        }
                        else {
                            var yorigin = this._convertY(origin, this._plotRect.top, this._plotRect.bottom);
                            if (pos == chart.Position.Bottom) {
                                axr = new wijmo.Rect(left, yorigin, w, ax._desiredSize.height);
                                b0 += Math.max(0, axr.bottom - this._plotRect.bottom); // ax.DesiredSize.Height;
                            }
                            else if (pos == chart.Position.Top) {
                                axr = new wijmo.Rect(left, yorigin - ax._desiredSize.height, w, ax._desiredSize.height);
                                t0 -= Math.max(0, this._plotRect.top - axr.top); // ax.DesiredSize.Height;
                            }
                            else {
                                axr = new wijmo.Rect(left, yorigin, w, 1);
                            }
                        }
                        ax._layout(axr, this._plotRect);
                        //ax.render(engine, axr, this.plotRect);
                    }
                    else if (ax.axisType == chart.AxisType.Y) {
                        var ayr;
                        if (!ax._hasOrigin) {
                            if (pos == chart.Position.Left) {
                                ayr = new wijmo.Rect(l0 - ax._desiredSize.height, top, h, ax._desiredSize.height);
                                l0 -= ax._desiredSize.height;
                            }
                            else if (pos == chart.Position.Right) {
                                ayr = new wijmo.Rect(r0, top, h, ax._desiredSize.height);
                                r0 += ax._desiredSize.height;
                            }
                            else {
                                ayr = new wijmo.Rect(l0, top, h, 1);
                            }
                        }
                        else {
                            var xorigin = this._convertX(origin, this._plotRect.left, this._plotRect.right);
                            if (pos == chart.Position.Left) {
                                ayr = new wijmo.Rect(xorigin - ax._desiredSize.height, top, h, ax._desiredSize.height);
                                l0 -= ax._desiredSize.height;
                            }
                            else if (pos == chart.Position.Right) {
                                ayr = new wijmo.Rect(xorigin, top, h, ax._desiredSize.height);
                                r0 += ax._desiredSize.height;
                            }
                            else {
                                ayr = new wijmo.Rect(xorigin, top, h, 1);
                            }
                        }
                        ax._layout(ayr, this._plotRect);
                        //ax.render(engine, ayr, this.plotRect);
                    }
                }
            };
            FlexChartCore.prototype._layoutMultiple = function (rect, size, engine) {
                var w = rect.width;
                var h = rect.height;
                var cols = [], rows = [];
                var axes = this._getAxes();
                var cnt = axes.length;
                for (var i = 0; i < cnt; i++) {
                    var ax = axes[i];
                    ax._plotrect = null;
                    if (ax.axisType == chart.AxisType.X) {
                        var col = ax.plotArea ? ax.plotArea.column : 0;
                        while (cols.length <= col)
                            cols.push(new _AreaDef());
                        cols[col].axes.push(ax);
                    }
                    else if (ax.axisType == chart.AxisType.Y) {
                        var row = ax.plotArea ? ax.plotArea.row : 0;
                        while (rows.length <= row)
                            rows.push(new _AreaDef());
                        rows[row].axes.push(ax);
                    }
                }
                var ncols = cols.length, nrows = rows.length;
                var mxsz = new wijmo.Size(w, 0.3 * h), mysz = new wijmo.Size(h, 0.3 * w), left = 0, top = 0, right = w, bottom = h;
                for (var icol = 0; icol < ncols; icol++) {
                    var ad = cols[icol];
                    ad.right = w;
                    ad.bottom = h;
                    for (var i = 0; i < ad.axes.length; i++) {
                        var ax = ad.axes[i];
                        var ah = ax._getHeight(engine, ax.axisType == chart.AxisType.X ? w : h); // .GetSize(GetItems(render, ax), false);
                        if (ah > mxsz.height)
                            ah = mxsz.height;
                        var szx = new wijmo.Size(mxsz.width, ah);
                        ax._desiredSize = szx;
                        if (icol == 0)
                            ad.left = Math.max(ad.left, ax._annoSize.width * 0.5);
                        if (icol == ncols - 1)
                            ad.right = Math.min(ad.right, w - ax._annoSize.width * 0.5);
                        var pos = ax._getPosition();
                        if (pos == chart.Position.Bottom)
                            ad.bottom -= szx.height;
                        else if (pos == chart.Position.Top)
                            ad.top += szx.height;
                    }
                }
                for (var irow = 0; irow < nrows; irow++) {
                    var ad = rows[irow];
                    ad.right = w;
                    ad.bottom = h;
                    for (var i = 0; i < ad.axes.length; i++) {
                        var ax = ad.axes[i];
                        var szy = new wijmo.Size(mysz.width, ax._getHeight(engine, ax.axisType == chart.AxisType.X ? w : h));
                        if (szy.height > mysz.height)
                            szy.height = mysz.height;
                        ax._desiredSize = szy;
                        if (irow == 0)
                            ad.top = Math.max(ad.top, ax._annoSize.width * 0.5);
                        if (irow == nrows - 1)
                            ad.bottom = Math.min(ad.bottom, h - ax._annoSize.width * 0.5);
                        var pos = ax._getPosition();
                        if (pos == chart.Position.Left)
                            ad.left += szy.height;
                        else if (pos == chart.Position.Right)
                            ad.right -= szy.height;
                    }
                }
                var l0 = 0, t0 = 0, r0 = w, b0 = h;
                for (var icol = 0; icol < ncols; icol++) {
                    var ad = cols[icol];
                    l0 = Math.max(l0, ad.left);
                    t0 = Math.max(t0, ad.top);
                    r0 = Math.min(r0, ad.right);
                    b0 = Math.min(b0, ad.bottom);
                }
                for (var irow = 0; irow < nrows; irow++) {
                    var ad = rows[irow];
                    l0 = Math.max(l0, ad.left);
                    t0 = Math.max(t0, ad.top);
                    r0 = Math.min(r0, ad.right);
                    b0 = Math.min(b0, ad.bottom);
                }
                //double w = 0, h = 0;
                //AdjustMargins(arrangeSize, ref left, ref right, ref top, ref bottom, ref w, ref h, ref l0, ref r0, ref t0, ref b0);
                l0 = left = /*(margin.Left != 0) ? margin.Left :*/ Math.max(left, l0);
                r0 = right = /*(margin.Right != 0) ? arrangeSize.Width - margin.Right :*/ Math.min(right, r0);
                t0 = top = /*(margin.Top != 0) ? margin.Top :*/ Math.max(top, t0);
                b0 = bottom = /*(margin.Bottom != 0) ? arrangeSize.Height - margin.Bottom :*/ Math.min(bottom, b0);
                //_plot = _plot0 = new Rect(left, top, w, h);
                this._plotRect = new wijmo.Rect(left, top, right - left, bottom - top);
                var plot0 = this._plotRect.clone(); // new Rect(left, top, w, h);
                //var wcol = w / ncols;
                //var hrow = h / nrows;
                var x = left;
                var widths = this.plotAreas._calculateWidths(this._plotRect.width, ncols);
                for (var icol = 0; icol < ncols; icol++) {
                    b0 = bottom;
                    t0 = top;
                    var ad = cols[icol];
                    var wcol = widths[icol];
                    for (var i = 0; i < ad.axes.length; i++) {
                        var ax = ad.axes[i];
                        var pos = ax._getPosition();
                        var axplot = new wijmo.Rect(x, plot0.top, wcol, plot0.height);
                        var axr; // = new Rect();
                        if (pos == chart.Position.Bottom) {
                            axr = new wijmo.Rect(x, b0, wcol, ax._desiredSize.height);
                            b0 += ax._desiredSize.height;
                        }
                        else if (pos == chart.Position.Top) {
                            axr = new wijmo.Rect(x, t0 - ax._desiredSize.height, wcol, ax._desiredSize.height);
                            t0 -= ax._desiredSize.height;
                        }
                        ax._layout(axr, axplot);
                    }
                    for (var i = 0; i < this.plotAreas.length; i++) {
                        var pa = this.plotAreas[i];
                        if (pa.column == icol)
                            pa._setPlotX(x, wcol);
                    }
                    x += wcol;
                }
                var y = top; //bottom;
                var heights = this.plotAreas._calculateHeights(this._plotRect.height, nrows);
                for (var irow = 0; irow < nrows; irow++) {
                    l0 = left;
                    r0 = right;
                    var ad = rows[irow];
                    var hrow = heights[irow];
                    //y -= hrow;
                    for (var i = 0; i < ad.axes.length; i++) {
                        var ax = ad.axes[i];
                        var pos = ax._getPosition();
                        var axplot = new wijmo.Rect(plot0.left, y, plot0.width, hrow);
                        if (ax._plotrect) {
                            axplot.left = ax._plotrect.left;
                            axplot.width = ax._plotrect.width;
                        }
                        else if (widths && widths.length > 0) {
                            axplot.width = widths[0];
                        }
                        var ayr;
                        if (pos == chart.Position.Left) {
                            ayr = new wijmo.Rect(l0 - ax._desiredSize.height, y, hrow, ax._desiredSize.height);
                            l0 -= ax._desiredSize.height;
                        }
                        else if (pos == chart.Position.Right) {
                            ayr = new wijmo.Rect(r0, y, hrow, ax._desiredSize.height);
                            r0 += ax._desiredSize.height;
                        }
                        ax._layout(ayr, axplot);
                    }
                    for (var i = 0; i < this.plotAreas.length; i++) {
                        var pa = this.plotAreas[i];
                        if (pa.row == irow)
                            pa._setPlotY(y, hrow);
                    }
                    y += hrow;
                }
            };
            //---------------------------------------------------------------------
            FlexChartCore.prototype._convertX = function (x, left, right) {
                var ax = this.axisX;
                if (ax.reversed)
                    return right - (right - left) * (x - ax._getMinNum()) / (ax._getMaxNum() - ax._getMinNum());
                else
                    return left + (right - left) * (x - ax._getMinNum()) / (ax._getMaxNum() - ax._getMinNum());
            };
            FlexChartCore.prototype._convertY = function (y, top, bottom) {
                var ay = this.axisY;
                if (ay.reversed)
                    return top + (bottom - top) * (y - ay._getMinNum()) / (ay._getMaxNum() - ay._getMinNum());
                else
                    return bottom - (bottom - top) * (y - ay._getMinNum()) / (ay._getMaxNum() - ay._getMinNum());
            };
            // tooltips
            FlexChartCore.prototype._getLabelContent = function (ht, content) {
                //var tc = this._tooltip.content;
                if (wijmo.isString(content)) {
                    return this._keywords.replace(content, ht);
                }
                else if (wijmo.isFunction(content)) {
                    return content(ht);
                }
                return null;
            };
            //---------------------------------------------------------------------
            // selection
            FlexChartCore.prototype._select = function (newSelection, pointIndex) {
                var raiseSelectionChanged = false;
                if (newSelection != this._selection || pointIndex != this._selectionIndex) {
                    raiseSelectionChanged = true;
                }
                // un-highlight old selection
                if (this._selection) {
                    this._highlight(this._selection, false, this._selectionIndex);
                }
                // highlight new selection
                this._selection = newSelection;
                this._selectionIndex = pointIndex;
                if (this._selection) {
                    this._highlight(this._selection, true, this._selectionIndex);
                }
                // update CollectionView
                if (this.selectionMode == SelectionMode.Point) {
                    var cv = newSelection ? newSelection.collectionView : this._cv;
                    if (cv) {
                        this._notifyCurrentChanged = false;
                        cv.moveCurrentToPosition(newSelection ? pointIndex : -1);
                        this._notifyCurrentChanged = true;
                    }
                }
                // raise event
                if (raiseSelectionChanged) {
                    this.onSelectionChanged();
                }
            };
            FlexChartCore.prototype._highlightCurrent = function () {
                if (this.selectionMode != SelectionMode.None) {
                    var selection = this._selection;
                    var pointIndex = -1;
                    if (selection) {
                        var cv = selection.collectionView;
                        if (!cv) {
                            cv = this._cv;
                        }
                        if (cv) {
                            pointIndex = cv.currentPosition;
                        }
                        this._highlight(selection, true, pointIndex);
                    }
                }
            };
            FlexChartCore.prototype._highlight = function (series, selected, pointIndex) {
                // check that the selection is a Series object (or null)
                series = wijmo.asType(series, chart.SeriesBase, true);
                // select the series or the point
                if (this.selectionMode == SelectionMode.Series) {
                    var index = this.series.indexOf(series);
                    var gs = series.hostElement;
                    // jQuery
                    // var hs = $(gs);
                    // this._highlightItems(hs.find('rect'), FlexChart._CSS_SELECTION, selected);
                    // this._highlightItems(hs.find('ellipse'), FlexChart._CSS_SELECTION, selected);
                    // this._highlightItems(hs.find('polyline'), FlexChart._CSS_SELECTION, selected);
                    // this._highlightItems(hs.find('polygon'), FlexChart._CSS_SELECTION, selected);
                    // this._highlightItems(hs.find('line'), FlexChart._CSS_SELECTION, selected);
                    if (selected) {
                        gs.parentNode.appendChild(gs);
                    }
                    else {
                        gs.parentNode.insertBefore(gs, gs.parentNode.childNodes.item(index));
                    }
                    var found = this._find(gs, ['rect', 'ellipse', 'polyline', 'polygon', 'line', 'path']);
                    this._highlightItems(found, chart.FlexChart._CSS_SELECTION, selected);
                    if (series.legendElement) {
                        // jQuery
                        // var ls = $(series.legendElement);
                        // this._highlightItems(ls.find('rect'), FlexChart._CSS_SELECTION, selected);
                        // this._highlightItems(ls.find('ellipse'), FlexChart._CSS_SELECTION, selected);
                        // this._highlightItems(ls.find('line'), FlexChart._CSS_SELECTION, selected);
                        this._highlightItems(this._find(series.legendElement, ['rect', 'ellipse', 'line']), chart.FlexChart._CSS_SELECTION, selected);
                    }
                }
                else if (this.selectionMode == SelectionMode.Point) {
                    var index = this.series.indexOf(series);
                    var gs = series.hostElement;
                    /* jQuery
                    var hs = $(gs);
    
                    if (selected) {
                        gs.parentNode.appendChild(gs);
                        var pel = $(series.getPlotElement(pointIndex));
                        if (pel.length) {
                            this._highlightItems(pel, FlexChart._CSS_SELECTION, selected);
                            this._highlightItems(pel.find('line'), FlexChart._CSS_SELECTION, selected);
                            this._highlightItems(pel.find('rect'), FlexChart._CSS_SELECTION, selected);
                        }
                    } else {
                        gs.parentNode.insertBefore(gs, gs.parentNode.childNodes.item(index));
    
                        this._highlightItems(hs.find('rect'), FlexChart._CSS_SELECTION, selected);
                        this._highlightItems(hs.find('ellipse'), FlexChart._CSS_SELECTION, selected);
                        this._highlightItems(hs.find('line'), FlexChart._CSS_SELECTION, selected);
                    }
                    */
                    if (selected) {
                        gs.parentNode.appendChild(gs);
                        var pel = series.getPlotElement(pointIndex);
                        if (pel) {
                            if (pel.nodeName != 'g') {
                                this._highlightItems([pel], chart.FlexChart._CSS_SELECTION, selected);
                            }
                            var found = this._find(pel, ['line', 'rect', 'ellipse', 'path', 'polygon']);
                            this._highlightItems(found, chart.FlexChart._CSS_SELECTION, selected);
                        }
                    }
                    else {
                        gs.parentNode.insertBefore(gs, gs.parentNode.childNodes.item(index));
                        var found = this._find(gs, ['rect', 'ellipse', 'line', 'path', 'polygon']);
                        this._highlightItems(found, chart.FlexChart._CSS_SELECTION, selected);
                    }
                }
            };
            // aux axes
            FlexChartCore.prototype._updateAuxAxes = function (axes, isRotated) {
                for (var i = 2; i < axes.length; i++) {
                    var ax = axes[i];
                    ax._chart = this;
                    var slist = [];
                    for (var iser = 0; iser < this.series.length; iser++) {
                        var ser = this.series[iser];
                        if (ser.axisX == ax || ser.axisY == ax) {
                            slist.push(ser);
                        }
                    }
                    var dataMin, dataMax;
                    for (var iser = 0; iser < slist.length; iser++) {
                        var rect = slist[iser].getDataRect() || slist[iser]._getDataRect();
                        if (rect) {
                            if ((ax.axisType == chart.AxisType.X && !isRotated) || (ax.axisType == chart.AxisType.Y && isRotated)) {
                                if (dataMin === undefined || rect.left < dataMin) {
                                    dataMin = rect.left;
                                }
                                if (dataMax === undefined || rect.right > dataMax) {
                                    dataMax = rect.right;
                                }
                            }
                            else {
                                if (dataMin === undefined || rect.top < dataMin) {
                                    dataMin = rect.top;
                                }
                                if (dataMax === undefined || rect.bottom > dataMax) {
                                    dataMax = rect.bottom;
                                }
                            }
                        }
                    }
                    var dtype = slist[0].getDataType(0);
                    if (dtype == null) {
                        dtype = wijmo.DataType.Number;
                    }
                    axes[i]._updateActualLimits(dtype, dataMin, dataMax);
                }
            };
            //---------------------------------------------------------------------
            // tools
            FlexChartCore._contains = function (rect, pt) {
                if (rect && pt) {
                    return pt.x >= rect.left && pt.x <= rect.right && pt.y >= rect.top && pt.y <= rect.bottom;
                }
                return false;
            };
            FlexChartCore._intersects = function (rect1, rect2) {
                if (rect1.left > rect2.right || rect1.right < rect2.left || rect1.top > rect2.bottom || rect1.bottom < rect2.top) {
                    return false;
                }
                return true;
            };
            FlexChartCore._toOADate = function (date) {
                return date.valueOf();
                //return (date.getTime() - FlexChart._epoch) / FlexChart._msPerDay;
            };
            FlexChartCore._fromOADate = function (val) {
                return new Date(val);
                /*var dec = val - Math.floor(val);
                if (val < 0 && dec) {
                    val = Math.floor(val) - dec;
                }
                return new Date(val * FlexChart._msPerDay + FlexChart._epoch);
                */
            };
            FlexChartCore._renderText = function (engine, text, pos, halign, valign, className, groupName, style, test) {
                var sz = engine.measureString(text, className, groupName, style);
                var x = pos.x;
                var y = pos.y;
                switch (halign) {
                    // center
                    case 1:
                        x -= 0.5 * sz.width;
                        break;
                    // right
                    case 2:
                        x -= sz.width;
                        break;
                }
                switch (valign) {
                    // center
                    case 1:
                        y += 0.5 * sz.height;
                        break;
                    // top
                    case 0:
                        y += sz.height;
                        break;
                }
                var rect = new wijmo.Rect(x, y - sz.height, sz.width, sz.height);
                if (test) {
                    if (test(rect)) {
                        engine.drawString(text, new wijmo.Point(x, y), className, style);
                        return rect;
                    }
                    else
                        return null;
                }
                else {
                    engine.drawString(text, new wijmo.Point(x, y), className, style);
                    return rect;
                }
            };
            FlexChartCore._renderRotatedText = function (engine, text, pos, halign, valign, center, angle, className, groupClassName, style) {
                var sz = engine.measureString(text, className, groupClassName, style);
                var x = pos.x;
                var y = pos.y;
                switch (halign) {
                    case 1:
                        x -= 0.5 * sz.width;
                        break;
                    case 2:
                        x -= sz.width;
                        break;
                }
                switch (valign) {
                    case 1:
                        y += 0.5 * sz.height;
                        break;
                    case 0:
                        y += sz.height;
                        break;
                }
                engine.drawStringRotated(text, new wijmo.Point(x, y), center, angle, className, style);
            };
            FlexChartCore._CSS_AXIS_X = 'wj-axis-x';
            FlexChartCore._CSS_AXIS_Y = 'wj-axis-y';
            FlexChartCore._CSS_LINE = 'wj-line';
            FlexChartCore._CSS_GRIDLINE = 'wj-gridline';
            FlexChartCore._CSS_TICK = 'wj-tick';
            FlexChartCore._CSS_GRIDLINE_MINOR = 'wj-gridline-minor';
            FlexChartCore._CSS_TICK_MINOR = 'wj-tick-minor';
            FlexChartCore._CSS_LABEL = 'wj-label';
            FlexChartCore._CSS_LEGEND = 'wj-legend';
            FlexChartCore._CSS_HEADER = 'wj-header';
            FlexChartCore._CSS_FOOTER = 'wj-footer';
            FlexChartCore._CSS_TITLE = 'wj-title';
            FlexChartCore._CSS_SELECTION = 'wj-state-selected';
            FlexChartCore._CSS_PLOT_AREA = 'wj-plot-area';
            FlexChartCore._FG = '#666';
            FlexChartCore._epoch = new Date(1899, 11, 30).getTime();
            FlexChartCore._msPerDay = 86400000;
            return FlexChartCore;
        }(chart.FlexChartBase));
        chart.FlexChartCore = FlexChartCore;
        var _AreaDef = /** @class */ (function () {
            function _AreaDef() {
                this._axes = new Array();
                this.left = 0;
                this.right = 0;
                this.top = 0;
                this.bottom = 0;
            }
            Object.defineProperty(_AreaDef.prototype, "axes", {
                get: function () {
                    return this._axes;
                },
                enumerable: true,
                configurable: true
            });
            return _AreaDef;
        }());
        /**
         * Analyzes chart data.
         */
        var _DataInfo = /** @class */ (function () {
            function _DataInfo() {
                this.stackAbs = {};
                this._xvals = null;
            }
            _DataInfo.prototype.analyse = function (seriesList, isRotated, stacking, xvals, logx, logy) {
                var _this = this;
                this.minY = NaN;
                this.maxY = NaN;
                this.minX = NaN;
                this.maxX = NaN;
                this.minXp = NaN;
                this.minYp = NaN;
                this.dx = 0;
                var stackPos = {};
                var stackNeg = {};
                var stackAbs = {};
                this.dataTypeX = null;
                this.dataTypeY = null;
                this._xvals = xvals;
                if (xvals != null) {
                    var len = xvals.length;
                    for (var i = 0; i < len; i++) {
                        var xval = xvals[i];
                        if (isNaN(this.minX) || this.minX > xval) {
                            this.minX = xval;
                        }
                        if (isNaN(this.maxX) || this.maxX < xval) {
                            this.maxX = xval;
                        }
                        if (xval > 0) {
                            if (isNaN(this.minXp) || this.minXp > xval) {
                                this.minXp = xval;
                            }
                        }
                        if (i > 0) {
                            var dx = Math.abs(xval - xvals[i - 1]);
                            if (!isNaN(dx) && (dx < this.dx || this.dx == 0)) {
                                this.dx = dx;
                            }
                        }
                    }
                }
                for (var i = 0; i < seriesList.length; i++) {
                    var series = seriesList[i];
                    var ctype = series._getChartType();
                    var custom = series.chartType !== undefined;
                    var vis = series.visibility;
                    if (vis == chart.SeriesVisibility.Hidden || vis == chart.SeriesVisibility.Legend) {
                        continue;
                    }
                    var calDr = series.getDataRect();
                    var drLen;
                    if (calDr) {
                        if (!isNaN(this.minX) && this.minX < calDr.left) {
                            drLen = calDr.right;
                            calDr.left = this.minX;
                            calDr.width = drLen - this.minX;
                        }
                        if (!isNaN(this.maxX) && this.maxX > calDr.right) {
                            calDr.width = this.maxX - calDr.left;
                        }
                        if (!isNaN(this.minY) && this.minY < calDr.top) {
                            drLen = calDr.bottom;
                            calDr.top = this.minY;
                            calDr.height = drLen - this.minY;
                        }
                        if (!isNaN(this.maxY) && this.maxY > calDr.bottom) {
                            calDr.height = this.maxY - calDr.top;
                        }
                    }
                    var xvalues = null;
                    if (isRotated) {
                        if (!series._isCustomAxisY()) {
                            xvalues = series.getValues(1);
                        }
                    }
                    else {
                        if (!series._isCustomAxisX()) {
                            xvalues = series.getValues(1);
                        }
                    }
                    if (xvalues) {
                        if (!this.dataTypeX) {
                            this.dataTypeX = series.getDataType(1);
                        }
                        for (var j = 0; j < xvalues.length; j++) {
                            var val = xvalues[j];
                            if (_DataInfo.isValid(val)) {
                                if (isNaN(this.minX) || this.minX > val) {
                                    this.minX = val;
                                }
                                if (isNaN(this.maxX) || this.maxX < val) {
                                    this.maxX = val;
                                }
                                if (j > 0 && (!ctype || // only default or col/bar
                                    ctype == chart.ChartType.Column || ctype == chart.ChartType.Bar)) {
                                    var dx = Math.abs(val - xvalues[j - 1]);
                                    if (!isNaN(dx) && dx > 0 && (dx < this.dx || this.dx == 0)) {
                                        this.dx = dx;
                                    }
                                }
                            }
                        }
                    }
                    var values = null, customY = false;
                    if (isRotated) {
                        customY = series._isCustomAxisX();
                        values = series.getValues(0);
                    }
                    else {
                        customY = series._isCustomAxisY();
                        values = series.getValues(0);
                    }
                    if (values) {
                        if (!this.dataTypeY && !customY) {
                            this.dataTypeY = series.getDataType(0);
                        }
                        if (isNaN(this.minX)) {
                            this.minX = 0;
                        }
                        else if (!xvalues && !xvals) {
                            this.minX = Math.min(this.minX, 0);
                        }
                        if (isNaN(this.maxX)) {
                            this.maxX = values.length - 1;
                        }
                        else if (!xvalues && !xvals) {
                            this.maxX = Math.max(this.maxX, values.length - 1);
                        }
                        if (!customY) {
                            for (var j = 0; j < values.length; j++) {
                                var val = values[j];
                                var xval = xvalues ? wijmo.asNumber(xvalues[j], true) : (xvals ? wijmo.asNumber(xvals[j], true) : j);
                                if (wijmo.isArray(val)) {
                                    //for BarPlot.
                                    val.forEach(function (v) {
                                        _this._parseYVal(v, xval, custom, stackAbs, stackPos, stackNeg);
                                    });
                                }
                                else {
                                    this._parseYVal(val, xval, custom, stackAbs, stackPos, stackNeg);
                                }
                            }
                        }
                    }
                    //update rect based on current rect if necessary.
                    var dr = series.getDataRect(new wijmo.Rect(this.minX, this.minY, this.maxX - this.minX, this.maxY - this.minY), calDr);
                    if (dr) {
                        this.minX = dr.left;
                        this.maxX = dr.right;
                        this.minY = dr.top;
                        this.maxY = dr.bottom;
                    }
                }
                if (stacking == Stacking.Stacked) {
                    for (var key in stackPos) {
                        if (stackPos[key] > this.maxY) {
                            this.maxY = stackPos[key];
                        }
                    }
                    for (var key in stackNeg) {
                        if (stackNeg[key] < this.minY) {
                            this.minY = stackNeg[key];
                        }
                    }
                }
                else if (stacking == Stacking.Stacked100pc) {
                    this.minY = 0;
                    this.maxY = 1;
                    for (var key in stackAbs) {
                        var sum = stackAbs[key];
                        if (isFinite(sum) && sum != 0) {
                            var vpos = stackPos[key];
                            var vneg = stackNeg[key];
                            if (isFinite(vpos)) {
                                vpos = Math.min(vpos / sum, this.maxY);
                            }
                            if (isFinite(vneg)) {
                                vneg = Math.max(vneg / sum, this.minY);
                            }
                        }
                    }
                }
                this.stackAbs = stackAbs;
                if (logx) {
                    if (isRotated)
                        this.minY = isNaN(this.minYp) ? 1 : this.minYp;
                    else
                        this.minX = isNaN(this.minXp) ? 1 : this.minXp;
                }
                if (logy) {
                    if (isRotated)
                        this.minX = isNaN(this.minXp) ? 1 : this.minXp;
                    else
                        this.minY = isNaN(this.minYp) ? 1 : this.minYp;
                }
            };
            _DataInfo.prototype._parseYVal = function (val, xval, custom, stackAbs, stackPos, stackNeg) {
                if (_DataInfo.isValid(val)) {
                    if (val != null && (isNaN(this.minY) || this.minY > val)) {
                        this.minY = val;
                    }
                    if (val != null && (isNaN(this.maxY) || this.maxY < val)) {
                        this.maxY = val;
                    }
                    if (val > 0 && (isNaN(this.minYp) || this.minYp > val)) {
                        this.minYp = val;
                    }
                    if (!custom) {
                        if (val > 0) {
                            if (isNaN(stackPos[xval])) {
                                stackPos[xval] = val;
                            }
                            else {
                                stackPos[xval] += val;
                            }
                        }
                        else {
                            if (isNaN(stackNeg[xval])) {
                                stackNeg[xval] = val;
                            }
                            else {
                                stackNeg[xval] += val;
                            }
                        }
                        if (isNaN(stackAbs[xval])) {
                            stackAbs[xval] = Math.abs(val);
                        }
                        else {
                            stackAbs[xval] += Math.abs(val);
                        }
                    }
                }
            };
            _DataInfo.prototype.getMinY = function () {
                return this.minY;
            };
            _DataInfo.prototype.getMaxY = function () {
                return this.maxY;
            };
            _DataInfo.prototype.getMinX = function () {
                return this.minX;
            };
            _DataInfo.prototype.getMaxX = function () {
                return this.maxX;
            };
            _DataInfo.prototype.getMinXp = function () {
                return this.minXp;
            };
            _DataInfo.prototype.getMinYp = function () {
                return this.minYp;
            };
            _DataInfo.prototype.getDeltaX = function () {
                return this.dx;
            };
            _DataInfo.prototype.getDataTypeX = function () {
                return this.dataTypeX;
            };
            _DataInfo.prototype.getDataTypeY = function () {
                return this.dataTypeY;
            };
            _DataInfo.prototype.getStackedAbsSum = function (key) {
                var sum = this.stackAbs[key];
                return isFinite(sum) ? sum : 0;
            };
            _DataInfo.prototype.getXVals = function () {
                return this._xvals;
            };
            _DataInfo.isValid = function (value) {
                return isFinite(value); // && !isNaN(value);
            };
            return _DataInfo;
        }());
        chart._DataInfo = _DataInfo;
        /**
         * Extends the @see:Tooltip class to provide chart tooltips.
         */
        var ChartTooltip = /** @class */ (function (_super) {
            __extends(ChartTooltip, _super);
            /**
             * Initializes a new instance of the @see:ChartTooltip class.
             */
            function ChartTooltip() {
                var _this = _super.call(this) || this;
                _this._content = '<b>{seriesName}</b><br/>{x} {y}';
                _this._threshold = 15;
                return _this;
            }
            Object.defineProperty(ChartTooltip.prototype, "content", {
                /**
                 * Gets or sets the tooltip content.
                 *
                 * The tooltip content can be specified as a string or as a function that
                 * takes a @see:wijmo.chart.HitTestInfo object as a parameter.
                 *
                 * When the tooltip content is a string, it may contain any of the following
                 * parameters:
                 *
                 * <ul>
                 *  <li><b>propertyName</b>:    Any property of the data object represented by the point.</li>
                 *  <li><b>seriesName</b>:      Name of the series that contains the data point (FlexChart only).</li>
                 *  <li><b>pointIndex</b>:      Index of the data point.</li>
                 *  <li><b>value</b>:           <b>Value</b> of the data point (y-value for @see:FlexChart, item value for @see:FlexPie).</li>
                 *  <li><b>x</b>:               <b>x</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>y</b>:               <b>y</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>name</b>:            <b>Name</b> of the data point (x-value for @see:FlexChart or legend entry for @see:FlexPie).</li>
                 * </ul>
                 *
                 * Parameters must be enclosed in single curly brackets. For example:
                 *
                 * <pre>
                 *   // 'country' and 'sales' are properties of the data object.
                 *   chart.tooltip.content = '{country}, sales:{sales}';
                 * </pre>
                 *
                 * The next example shows how to set the tooltip content using a function.
                 *
                 *  <pre>
                 *   // Set the tooltip content
                 *   chart.tooltip.content = function (ht) {
                 *     return ht.name + ":" + ht.value.toFixed();
                 *   }
                 * </pre>
                 */
                get: function () {
                    return this._content;
                },
                set: function (value) {
                    if (value != this._content) {
                        this._content = value;
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ChartTooltip.prototype, "threshold", {
                /**
                 * Gets or sets the maximum distance from the element to display the tooltip.
                 */
                get: function () {
                    return this._threshold;
                },
                set: function (value) {
                    if (value != this._threshold) {
                        this._threshold = wijmo.asNumber(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            return ChartTooltip;
        }(wijmo.Tooltip));
        chart.ChartTooltip = ChartTooltip;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
/**
 * Defines the @see:FlexChart control and its associated classes.
 *
 * The example below creates a @see:FlexChart control and binds it to a data array.
 * The chart has three series, each corresponding to a property in the objects
 * contained in the source array.
 *
 * The last series in the example uses the @see:Series.chartType property to
 * override the default chart type used
 * by the other series.
 *
 * @fiddle:6GB66
 */
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Specifies the chart type.
         */
        var ChartType;
        (function (ChartType) {
            /** Shows vertical bars and allows you to compare values of items across categories. */
            ChartType[ChartType["Column"] = 0] = "Column";
            /** Shows horizontal bars. */
            ChartType[ChartType["Bar"] = 1] = "Bar";
            /** Shows patterns within the data using X and Y coordinates. */
            ChartType[ChartType["Scatter"] = 2] = "Scatter";
            /** Shows trends over a period of time or across categories. */
            ChartType[ChartType["Line"] = 3] = "Line";
            /** Shows a line chart with a symbol on each data point. */
            ChartType[ChartType["LineSymbols"] = 4] = "LineSymbols";
            /** Shows a line chart with the area below the line filled with color. */
            ChartType[ChartType["Area"] = 5] = "Area";
            /** Shows a Scatter chart with a third data value that determines the
             * size of the symbol. The data for this chart type can be defined using the
             *  @see:FlexChart or @see:Series <b>binding</b> property as a comma separated value in the
             * following format: "yProperty, bubbleSizeProperty".*/
            ChartType[ChartType["Bubble"] = 6] = "Bubble";
            /** Presents items with high, low, open, and close values.
             * The size of the wick line is determined by the High and Low values,
             * while the size of the bar is determined by the Open and Close values.
             * The bar is displayed using different colors, depending on
             * whether the close value is higher or lower than the open value.
             * The data for this chart type can be defined using the
             *  @see:FlexChart or @see:Series <b>binding</b> property as a comma separated value in the
             * following format: "highProperty, lowProperty, openProperty, closeProperty". */
            ChartType[ChartType["Candlestick"] = 7] = "Candlestick";
            /** Displays the same information as a candlestick chart, except that opening
             * values are displayed using lines to the left, while lines to the right
             * indicate closing values.  The data for this chart type can be defined using the
             *  @see:FlexChart or @see:Series <b>binding</b> property as a comma separated value in the
             * following format: "highProperty, lowProperty, openProperty, closeProperty". */
            ChartType[ChartType["HighLowOpenClose"] = 8] = "HighLowOpenClose";
            /** Displays a line chart that plots curves rather than angled lines through the
            * data points. */
            ChartType[ChartType["Spline"] = 9] = "Spline";
            /** Displays a spline chart with symbols on each data point. */
            ChartType[ChartType["SplineSymbols"] = 10] = "SplineSymbols";
            /** Displays a spline chart with the area below the line filled with color. */
            ChartType[ChartType["SplineArea"] = 11] = "SplineArea";
            /** Displays a funnel chart, usually representing stages in a process such as a sales pipeline. */
            ChartType[ChartType["Funnel"] = 12] = "Funnel";
        })(ChartType = chart.ChartType || (chart.ChartType = {}));
        /**
         * The @see:FlexChart control provides a powerful and flexible way to visualize
         * data.
         *
         * You can use the @see:FlexChart control to create charts that display data in
         * several formats, including bar, line, symbol, bubble, and others.
         *
         * To use the @see:FlexChart control, set the @see:FlexChart.itemsSource property
         * to an array containing the data objects, then add one or more @see:Series objects
         * to the @see:FlexChart.series property.
         *
         * Use the @see:FlexChart.chartType property to define the @see:ChartType used as
         * a default for all series. You may override the chart type for each series by
         * setting the @see:Series.chartType property on the members of the
         * @see:FlexChart.series array.
         *
         * @fiddle:6GB66
         */
        var FlexChart = /** @class */ (function (_super) {
            __extends(FlexChart, _super);
            /**
             * Initializes a new instance of the @see:FlexChart class.
             *
             * @param element The DOM element that will host the control,
             * or a selector for the host element (e.g. '#theCtrl').
             * @param options A JavaScript object containing initialization data
             * for the control.
             */
            function FlexChart(element, options) {
                var _this = _super.call(this, element, null) || this;
                _this._chartType = ChartType.Column;
                _this.initialize(options);
                return _this;
            }
            FlexChart.prototype._getChartType = function () {
                return this._chartType;
            };
            Object.defineProperty(FlexChart.prototype, "chartType", {
                /**
                 * Gets or sets the type of chart to create.
                 */
                get: function () {
                    return this._chartType;
                },
                set: function (value) {
                    if (value != this._chartType) {
                        this._chartType = wijmo.asEnum(value, ChartType);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChart.prototype, "rotated", {
                /**
                 * Gets or sets a value indicating whether to flip the axes so that
                 * X becomes vertical and Y becomes horizontal.
                 */
                get: function () {
                    return this._rotated;
                },
                set: function (value) {
                    if (value != this._rotated) {
                        this._rotated = wijmo.asBoolean(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChart.prototype, "stacking", {
                /**
                 * Gets or sets a value that determines whether and how the series objects are stacked.
                 */
                get: function () {
                    return this._stacking;
                },
                set: function (value) {
                    if (value != this._stacking) {
                        this._stacking = wijmo.asEnum(value, chart.Stacking);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FlexChart.prototype, "options", {
                /**
                 * Gets or sets various chart options.
                 *
                 * The following options are supported:
                 *
                 * <b>bubble.maxSize</b>: Specifies the maximum size
                 * of symbols in the Bubble chart. The default value is 30 pixels.
                 *
                 * <b>bubble.minSize</b>: Specifies the minimum size
                 * of symbols in the Bubble chart. The default value is 5 pixels.
                 *
                 * <pre>chart.options = {
                 *   bubble: { minSize: 5, maxSize: 30 }
                 * }</pre>
                 *
                 *
                 * <b>funnel.neckWidth</b>: Specifies the neck width as a percentage for the Funnel chart.
                 * The default value is 0.2.
                 *
                 * <b>funnel.neckHeight</b>: Specifies the neck height as a percentage for the Funnel chart.
                 * The default value is 0.
                 *
                 * <b>funnel.type</b>: Specifies the type of Funnel chart. It should be 'rectangle' or 'default'.
                 * neckWidth and neckHeight don't work if type is set to rectangle.
                 *
                 * <pre>chart.options = {
                 *   funnel: { neckWidth: 0.3, neckHeight: 0.3, type: 'rectangle' }
                 * }</pre>
        
                 * <b>groupWidth</b>: Specifies the group width for the Column charts,
                 * or the group height for the Bar charts. The group width can be specified
                 * in pixels or as percentage of the available space. The default value is '70%'.
                 *
                 * <pre>chart.options = {
                 *   groupWidth : 50; // 50 pixels
                 * }
                 * chart.options = {
                 *   groupWidth : '100%'; // 100% pixels
                 * }</pre>
                 */
                get: function () {
                    return this._options;
                },
                set: function (value) {
                    if (value != this._options) {
                        this._options = value;
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            return FlexChart;
        }(chart.FlexChartCore));
        chart.FlexChart = FlexChart;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Specifies the position of an axis or legend on the chart.
         */
        var Position;
        (function (Position) {
            /** The item is not visible. */
            Position[Position["None"] = 0] = "None";
            /** The item appears to the left of the chart. */
            Position[Position["Left"] = 1] = "Left";
            /** The item appears above the chart. */
            Position[Position["Top"] = 2] = "Top";
            /** The item appears to the right of the chart. */
            Position[Position["Right"] = 3] = "Right";
            /** The item appears below the chart. */
            Position[Position["Bottom"] = 4] = "Bottom";
            /** The item is positioned automatically. */
            Position[Position["Auto"] = 5] = "Auto";
        })(Position = chart_1.Position || (chart_1.Position = {}));
        ;
        /**
         * Specifies the axis type.
         */
        var AxisType;
        (function (AxisType) {
            /** Category axis (normally horizontal). */
            AxisType[AxisType["X"] = 0] = "X";
            /** Value axis (normally vertical). */
            AxisType[AxisType["Y"] = 1] = "Y";
        })(AxisType = chart_1.AxisType || (chart_1.AxisType = {}));
        /**
         * Specifies how to handle overlapping labels.
         */
        var OverlappingLabels;
        (function (OverlappingLabels) {
            /**
             * Hide overlapping labels.
             */
            OverlappingLabels[OverlappingLabels["Auto"] = 0] = "Auto";
            /**
             * Show all labels, including overlapping ones.
             */
            OverlappingLabels[OverlappingLabels["Show"] = 1] = "Show";
        })(OverlappingLabels = chart_1.OverlappingLabels || (chart_1.OverlappingLabels = {}));
        /**
         * Specifies whether and where the axis tick marks appear.
         */
        var TickMark;
        (function (TickMark) {
            /** No tick marks appear. */
            TickMark[TickMark["None"] = 0] = "None";
            /** Tick marks appear outside the plot area. */
            TickMark[TickMark["Outside"] = 1] = "Outside";
            /** Tick marks appear inside the plot area. */
            TickMark[TickMark["Inside"] = 2] = "Inside";
            /** Tick marks cross the axis. */
            TickMark[TickMark["Cross"] = 3] = "Cross";
        })(TickMark = chart_1.TickMark || (chart_1.TickMark = {}));
        /**
         * Represents an axis in the chart.
         */
        var Axis = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:Axis class.
             *
             * @param position The position of the axis on the chart.
             */
            function Axis(position) {
                //_GRIDLINE_WIDTH = 0.25;
                this._GRIDLINE_WIDTH = 1;
                this._LINE_WIDTH = 1;
                this._TICK_WIDTH = 1;
                this._TICK_HEIGHT = 4;
                this._TICK_OVERLAP = 1;
                this._TICK_LABEL_DISTANCE = 4;
                this._minorGrid = false;
                this._labels = true;
                this._axisLine = true;
                this._isTimeAxis = false;
                this._labelPadding = 5;
                /**
                 * Occurs when the axis range changes.
                 */
                this.rangeChanged = new wijmo.Event();
                // defines custom conversion functions, it allows to create axis with non-linear scale
                // convert axis coordinate to relative position on the axis.
                // The range is from 0(min) to 1(max).
                this._customConvert = null;
                // inverse function for _customConvert
                // convert relative axis position to axis coordinate
                this._customConvertBack = null;
                this.__uniqueId = Axis._id++;
                this._position = position;
                if (position == Position.Bottom || position == Position.Top) {
                    this._axisType = AxisType.X;
                }
                else {
                    this._axisType = AxisType.Y;
                    this._axisLine = false;
                }
                this._minorTickMarks = TickMark.None;
                this._overlap = OverlappingLabels.Auto;
            }
            Object.defineProperty(Axis.prototype, "hostElement", {
                //--------------------------------------------------------------------------
                //** object model
                /**
                 * Gets the axis host element.
                 */
                get: function () {
                    return this._hostElement;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "actualMin", {
                /**
                 * Gets the actual axis minimum.
                 *
                 * It returns a number or a Date object (for time-based data).
                */
                get: function () {
                    return this._isTimeAxis ? new Date(this._actualMin) : this._actualMin;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "actualMax", {
                /**
                * Gets the actual axis maximum.
                *
                * It returns a number or a Date object (for time-based data).
                */
                get: function () {
                    return this._isTimeAxis ? new Date(this._actualMax) : this._actualMax;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "min", {
                /**
                 * Gets or sets the minimum value shown on the axis.
                 *
                 * If not set, the minimum is calculated automatically.
                 * The value can be a number or a Date object (for time-based data).
                 */
                get: function () {
                    return this._min;
                },
                set: function (value) {
                    if (value != this._min) {
                        if (wijmo.isDate(value)) {
                            this._min = wijmo.asDate(value, true);
                        }
                        else {
                            this._min = wijmo.asNumber(value, true);
                        }
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "max", {
                /**
                 * Gets or sets the maximum value shown on the axis.
                 *
                 * If not set, the maximum is calculated automatically.
                 * The value can be a number or a Date object (for time-based data).
                 */
                get: function () {
                    return this._max;
                },
                set: function (value) {
                    if (value != this._max) {
                        if (wijmo.isDate(value)) {
                            this._max = wijmo.asDate(value, true);
                        }
                        else {
                            this._max = wijmo.asNumber(value, true);
                        }
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "reversed", {
                /**
                 * Gets or sets a value indicating whether the axis is
                 * reversed (top to bottom or right to left).
                 */
                get: function () {
                    return this._reversed;
                },
                set: function (value) {
                    if (this._reversed != value) {
                        this._reversed = wijmo.asBoolean(value);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "position", {
                /**
                 * Gets or sets the position of the axis with respect to the plot area.
                 */
                get: function () {
                    return this._position;
                },
                set: function (value) {
                    if (value != this._position) {
                        this._position = wijmo.asEnum(value, Position, false);
                        if (this._position == Position.Bottom || this._position == Position.Top) {
                            this._axisType = AxisType.X;
                        }
                        else if (this._position == Position.Left || this._position == Position.Right) {
                            this._axisType = AxisType.Y;
                        }
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "majorUnit", {
                /**
                 * Gets or sets the number of units between axis labels.
                 *
                 * If the axis contains date values, then the units are
                 * expressed in days.
                 */
                get: function () {
                    return this._majorUnit;
                },
                set: function (value) {
                    if (value != this._majorUnit) {
                        this._majorUnit = wijmo.asNumber(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "minorUnit", {
                /**
                  * Gets or sets the number of units between minor axis ticks.
                  *
                  * If the axis contains date values, then the units are
                  * expressed in days.
                  */
                get: function () {
                    return this._minorUnit;
                },
                set: function (value) {
                    if (value != this._minorUnit) {
                        this._minorUnit = wijmo.asNumber(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "name", {
                /**
                 * Gets or sets the axis name.
                 */
                get: function () {
                    return this._name;
                },
                set: function (value) {
                    if (value != this._name) {
                        this._name = wijmo.asString(value, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "title", {
                /**
                 * Gets or sets the title text shown next to the axis.
                 */
                get: function () {
                    return this._title;
                },
                set: function (value) {
                    if (value != this._title) {
                        this._title = wijmo.asString(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "format", {
                /**
                 * Gets or sets the format string used for the axis labels
                 * (see @see:Globalize).
                 */
                get: function () {
                    return this._format;
                },
                set: function (value) {
                    if (value != this._format) {
                        this._format = wijmo.asString(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "majorGrid", {
                //
                /**
                 * Gets or sets a value indicating whether the axis includes grid lines.
                 */
                get: function () {
                    return this._majorGrid;
                },
                set: function (value) {
                    if (value != this._majorGrid) {
                        this._majorGrid = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "majorTickMarks", {
                /**
                 * Gets or sets the location of the axis tick marks.
                 */
                get: function () {
                    return this._majorTickMarks;
                },
                set: function (value) {
                    if (value != this._majorTickMarks) {
                        this._majorTickMarks = wijmo.asEnum(value, TickMark, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "minorGrid", {
                /**
                 * Gets or sets a value indicating whether the axis includes minor grid lines.
                 */
                get: function () {
                    return this._minorGrid;
                },
                set: function (value) {
                    if (value != this._minorGrid) {
                        this._minorGrid = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "minorTickMarks", {
                /**
                 * Gets or sets the location of the minor axis tick marks.
                 */
                get: function () {
                    return this._minorTickMarks;
                },
                set: function (value) {
                    if (value != this._minorTickMarks) {
                        this._minorTickMarks = wijmo.asEnum(value, TickMark, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "axisLine", {
                /**
                 * Gets or sets a value indicating whether the axis line is visible.
                 */
                get: function () {
                    return this._axisLine;
                },
                set: function (value) {
                    if (value != this._axisLine) {
                        this._axisLine = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "labels", {
                /**
                 * Gets or sets a value indicating whether the axis labels are visible.
                 */
                get: function () {
                    return this._labels;
                },
                set: function (value) {
                    if (value != this._labels) {
                        this._labels = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "labelAlign", {
                /**
                 * Gets or sets the label alignment.
                 *
                 * By default the labels are centered. The supported values are 'left' and 'right
                 * for x-axis and 'top' and 'bottom' for y-axis.
                 */
                get: function () {
                    return this._labelAlign;
                },
                set: function (value) {
                    if (value != this._labelAlign) {
                        this._labelAlign = wijmo.asString(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "labelAngle", {
                /**
                 * Gets or sets the rotation angle of the axis labels.
                 *
                 * The angle is measured in degrees with valid values
                 * ranging from -90 to 90.
                 */
                get: function () {
                    return this._labelAngle;
                },
                set: function (value) {
                    if (value != this._labelAngle) {
                        this._labelAngle = wijmo.asNumber(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "origin", {
                /**
                 * Gets or sets the value at which an axis crosses the perpendicular axis.
                 **/
                get: function () {
                    return this._origin;
                },
                set: function (value) {
                    if (value != this._origin) {
                        this._origin = wijmo.asNumber(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "overlappingLabels", {
                /**
                 * Gets or sets a value indicating how to handle the overlapping axis labels.
                 */
                get: function () {
                    return this._overlap;
                },
                set: function (value) {
                    if (value != this._overlap) {
                        this._overlap = wijmo.asEnum(value, OverlappingLabels, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "itemsSource", {
                /**
                 * Gets or sets the items source for the axis labels.
                 *
                 * Names of the properties are specified by the @see:wijmo.chart.Axis.binding property.
                 *
                 * For example:
                 *
                 * <pre>
                 *  // default value for Axis.binding is 'value,text'
                 *  chart.axisX.itemsSource = [ { value:1, text:'one' }, { value:2, text:'two' } ];
                 * </pre>
                 */
                get: function () {
                    return this._items;
                },
                set: function (value) {
                    if (this._items != value) {
                        // unbind current collection view
                        if (this._cv) {
                            this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this);
                            this._cv = null;
                        }
                        // save new data source and collection view
                        this._items = value;
                        this._cv = wijmo.asCollectionView(value);
                        // bind new collection view
                        if (this._cv != null) {
                            this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "binding", {
                /**
                 * Gets or sets the comma-separated property names for the
                 * @see:wijmo.chart.Axis.itemsSource property to use in axis labels.
                 *
                 * The first name specifies the value on the axis, the second represents the corresponding
                 * axis label. The default value is 'value,text'.
                 */
                get: function () {
                    return this._binding;
                },
                set: function (value) {
                    if (value != this._binding) {
                        this._binding = wijmo.asString(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "itemFormatter", {
                /**
                 * Gets or sets the itemFormatter function for the axis labels.
                 *
                 * If specified, the function takes two parameters:
                 * <ul>
                 * <li><b>render engine</b>: The @see:wijmo.chart.IRenderEngine object to be used
                 * in formatting the labels.</li>
                 * <li><b>current label</b>: An object with the following properties:
                 *   <ul>
                 *     <li><b>value</b>: The value of the axis label to format.</li>
                 *     <li><b>text</b>: The text to use in the label.</li>
                 *     <li><b>pos</b>: The position in control coordinates at which
                 *     the label is to be rendered.</li>
                 *     <li><b>cls</b>: The CSS class to be applied to the label.</li>
                 *   </ul></li>
                 * </ul>
                 *
                 * The function returns the label parameters of labels for which
                 * properties are modified.
                 *
                 * For example:
                 * <pre>
                 * chart.axisY.itemFormatter = function(engine, label) {
                 *     if (label.val &gt; 5){
                 *         engine.textFill = 'red'; // red text
                 *         label.cls = null; // no default CSS
                 *      }
                 *     return label;
                 * }
                 * </pre>
                 */
                get: function () {
                    return this._ifmt;
                },
                set: function (value) {
                    if (this._ifmt != value) {
                        this._ifmt = wijmo.asFunction(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "logBase", {
                /**
                 * Gets or sets the logarithmic base of the axis.
                 *
                 * If the base is not specified the axis uses a linear scale.
                 *
                 * Use the @see:logBase property to spread data that is clustered
                 * around the origin. This is common in several financial and economic
                 * data sets.
                 */
                get: function () {
                    return this._logBase;
                },
                set: function (value) {
                    if (value != this._logBase) {
                        this._logBase = wijmo.asNumber(value, true, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "plotArea", {
                /**
                 * Gets or sets the plot area for the axis.
                 */
                get: function () {
                    return this._parea;
                },
                set: function (value) {
                    if (value != this._parea) {
                        this._parea = wijmo.asType(value, chart_1.PlotArea, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "labelPadding", {
                /**
                 * Gets or sets the label padding.
                 */
                get: function () {
                    return this._labelPadding;
                },
                set: function (value) {
                    if (value != this._labelPadding) {
                        this._labelPadding = wijmo.asNumber(value, true, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Axis.prototype, "_groupClass", {
                get: function () {
                    return this.axisType === AxisType.X ? chart_1.FlexChartCore._CSS_AXIS_X : chart_1.FlexChartCore._CSS_AXIS_Y;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:rangeChanged event.
             */
            Axis.prototype.onRangeChanged = function (e) {
                this.rangeChanged.raise(this, e);
            };
            //--------------------------------------------------------------------------
            // implementation
            Axis.prototype._getPosition = function () {
                if (this.axisType == AxisType.X) {
                    if (this.position == Position.Auto) {
                        return Position.Bottom;
                    }
                }
                else if (this.axisType == AxisType.Y) {
                    if (this.position == Position.Auto) {
                        return Position.Left;
                    }
                }
                return this.position;
            };
            Axis.prototype._isOverlapped = function (engine, w, lblClass, axisType) {
                var lbls = this._lbls;
                if (lbls != null && lbls.length > 1) {
                    var len = lbls.length;
                    var vals = this._values && this._values.length == len ? this._values : null;
                    var x0 = 0;
                    var w0 = 0;
                    for (var i = 0; i < len; i++) {
                        var val = vals ? vals[i] : i;
                        if (val >= this._actualMin && val <= this._actualMax) {
                            var x = w * (val - this._actualMin) / (this._actualMax - this._actualMin);
                            var sz = engine.measureString(lbls[i], lblClass, this._groupClass);
                            if (this.axisType == AxisType.X) {
                                if (i > 0 && Math.abs(x - x0) < Math.max(sz.width, w0) + 12) {
                                    return true;
                                }
                                x0 = x;
                                w0 = sz.width;
                            }
                            else if (this.axisType == AxisType.Y) {
                                //rotate if label width is larger than 1/3 of chart area
                                // ! need more research
                                // if (sz.width >= this._chart._rectChart.width / 3) {
                                //    return true;
                                //}
                            }
                        }
                    }
                }
                return false;
            };
            /**
             * Calculates the axis height.
             *
             * @param engine Rendering engine.
             * @param maxw Max width.
             */
            Axis.prototype._getHeight = function (engine, maxw) {
                this._actualAngle = null;
                var lblClass = chart_1.FlexChart._CSS_LABEL;
                var titleClass = chart_1.FlexChart._CSS_TITLE;
                var range = this._actualMax - this._actualMin;
                var prec = this._nicePrecision(range);
                if (prec < 0 || prec > 15)
                    prec = 0;
                var delta = 0.1 * range; // r * 0.01 * Math.E;
                var lbls = this._lbls;
                var angle = this.labelAngle;
                if (this.labels && this._chart._getChartType() !== chart_1.ChartType.Funnel) {
                    delta = this._updateAutoFormat(delta);
                    if (lbls != null && lbls.length > 0) {
                        var len = lbls.length;
                        var vals = this._values && this._values.length == len ? this._values : null;
                        this._annoSize = new wijmo.Size();
                        for (var i = 0; i < len; i++) {
                            var val = vals ? vals[i] : i;
                            if (val >= this._actualMin && val <= this._actualMax) {
                                var sz = engine.measureString(lbls[i], lblClass, this._groupClass);
                                if (this.axisType == AxisType.X) {
                                    //if ((i == 0 || i == len - 1) && sz.width > this._annoSize.width){ //&& sz.width <= 0.1 * maxw) {
                                    if (sz.width > this._annoSize.width) {
                                        this._annoSize.width = sz.width;
                                    }
                                }
                                else {
                                    if (sz.width > this._annoSize.width) {
                                        this._annoSize.width = sz.width;
                                    }
                                }
                                if (sz.height > this._annoSize.height) {
                                    this._annoSize.height = sz.height;
                                }
                            }
                        }
                        if (angle == null) {
                            if (this._isOverlapped(engine, maxw, lblClass, this.axisType)) {
                                angle = this._actualAngle = -45;
                            }
                            else {
                                this._actualAngle = 0;
                            }
                        }
                    }
                    else {
                        var text = this._formatValue(this._actualMin - delta);
                        var sz = engine.measureString(text, lblClass, this._groupClass);
                        this._annoSize = sz;
                        text = this._formatValue(this._actualMax + delta);
                        sz = engine.measureString(text, lblClass, this._groupClass);
                        if (sz.width > this._annoSize.width) {
                            this._annoSize.width = sz.width;
                        }
                        if (sz.height > this._annoSize.height)
                            this._annoSize.height = sz.height;
                    }
                    if (angle) {
                        if (angle > 90) {
                            angle = 90;
                        }
                        else if (angle < -90) {
                            angle = -90;
                        }
                        var a = angle * Math.PI / 180, w = this._annoSize.width, h = this._annoSize.height;
                        this._annoSize.width = w * Math.abs(Math.cos(a)) + h * Math.abs(Math.sin(a));
                        this._annoSize.height = w * Math.abs(Math.sin(a)) + h * Math.abs(Math.cos(a));
                    }
                }
                else {
                    this._annoSize = new wijmo.Size();
                }
                var h = 2 * (this._labelPadding || 5);
                if (this._axisType == AxisType.X) {
                    h += this._annoSize.height;
                }
                else {
                    h += this._annoSize.width + this._TICK_LABEL_DISTANCE + 2;
                }
                var th = this._TICK_HEIGHT;
                var tover = this._TICK_OVERLAP;
                if (tickMarks == TickMark.Outside) {
                    tover = 1;
                }
                else if (tickMarks == TickMark.Inside) {
                    tover = -1;
                }
                else if (tickMarks == TickMark.Cross) {
                    tover = 0;
                }
                var tickMarks = this.majorTickMarks;
                if (tickMarks == null) {
                    tickMarks = TickMark.Outside;
                }
                if (tickMarks != TickMark.None) {
                    h += 0.5 * (1 + tover) * th;
                }
                if (this._title) {
                    text = this._title;
                    this._szTitle = engine.measureString(text, titleClass, this._groupClass);
                    h += this._szTitle.height;
                }
                engine.fontSize = null;
                return h;
            };
            Axis.prototype._updateAutoFormat = function (delta) {
                if (this._isTimeAxis) {
                    var fmt = this.format;
                    var td = (0.001 * (this._actualMax - this._actualMin) / 10);
                    var trange = new _timeSpan(td * _timeSpan.TicksPerSecond);
                    var tdelta = wijmo.isNumber(this._majorUnit) // TFS 250742
                        ? _timeSpan.fromDays(this._majorUnit)
                        : _timeHelper.NiceTimeSpan(trange, fmt);
                    if (!fmt)
                        this._tfmt = _timeHelper.GetTimeDefaultFormat(1000 * tdelta.TotalSeconds, 0);
                    delta = tdelta.TotalSeconds;
                }
                return delta;
            };
            Axis.prototype._updateActualLimitsByChartType = function (labels, min, max) {
                if (labels && labels.length > 0 && !this._isTimeAxis) {
                    var ctype = this._chart._getChartType();
                    if (ctype != chart_1.ChartType.Column && ctype != chart_1.ChartType.Bar) {
                        min -= 0.5;
                        max += 0.5;
                    }
                }
                return { min: min, max: max };
            };
            /**
             * Update the actual axis limits based on a specified data range.
             *
             * @param dataType Data type.
             * @param dataMin Data minimum.
             * @param dataMax Data maximum.
             * @param labels Category labels(category axis).
             * @param values Values(value axis).
             */
            Axis.prototype._updateActualLimits = function (dataType, dataMin, dataMax, labels, values) {
                if (labels === void 0) { labels = null; }
                if (values === void 0) { values = null; }
                var oldmin = this._actualMin, oldmax = this._actualMax;
                this._isTimeAxis = (dataType == wijmo.DataType.Date);
                var minmax = this._updateActualLimitsByChartType(labels, dataMin, dataMax);
                dataMin = minmax.min;
                dataMax = minmax.max;
                var min = this._min, max = this._max;
                if (wijmo.isDate(min)) {
                    min = min.valueOf();
                }
                if (wijmo.isDate(max)) {
                    max = max.valueOf();
                }
                this._actualMin = (min != null && this._chart && this._chart._stacking !== chart_1.Stacking.Stacked100pc) ? min : dataMin;
                this._actualMax = (max != null && this._chart && this._chart._stacking !== chart_1.Stacking.Stacked100pc) ? max : dataMax;
                // todo: validate min&max
                if (this._actualMin == this._actualMax) {
                    this._actualMin -= 0.5;
                    this._actualMax += 0.5;
                }
                if (this.logBase > 0) {
                    var base = this.logBase;
                    var k = Math.log(base);
                    if (!this._max) {
                        var imax = Math.ceil(Math.log(this._actualMax) / k);
                        this._actualMax = Math.pow(base, imax);
                    }
                    if (!this._min) {
                        var imin = Math.floor(Math.log(this._actualMin) / k);
                        this._actualMin = Math.pow(base, imin);
                    }
                    if (this._actualMin <= 0 || !wijmo.isNumber(this._actualMin)) {
                        this._actualMin = 1;
                    }
                    if (this._actualMax < this._actualMin) {
                        this._actualMax = this._actualMin + 1;
                    }
                }
                //if (this._isTimeAxis) {
                //    this._tfmt = _timeHelper.GetTimeDefaultFormat(this._actualMax, this._actualMin);
                //}
                if ((oldmin != this._actualMin && (wijmo.isNumber(oldmin) || wijmo.isNumber(this._actualMin))) ||
                    (oldmax != this._actualMax && (wijmo.isNumber(oldmax) || wijmo.isNumber(this._actualMax)))) {
                    this.onRangeChanged();
                }
                if (this._items) {
                    this._values = [];
                    this._lbls = [];
                    var len = this._items.length;
                    var vbnd = 'value';
                    var nbnd = 'text';
                    if (this.binding) {
                        var bnds = this.binding.split(',');
                        if (bnds.length == 2) {
                            vbnd = bnds[0];
                            nbnd = bnds[1];
                        }
                    }
                    for (var i = 0; i < len; i++) {
                        var item = this._items[i];
                        var val = item[vbnd];
                        if (wijmo.isNumber(val)) {
                            this._values.push(val);
                            this._lbls.push(item[nbnd]);
                        }
                    }
                }
                else {
                    this._lbls = labels;
                    this._values = values;
                }
            };
            /**
             * Set the axis position.
             *
             * @param axisRect Axis rectangle.
             * @param plotRect Plot area rectangle.
             */
            Axis.prototype._layout = function (axisRect, plotRect) {
                var isVert = this.axisType == AxisType.Y;
                this._plotrect = plotRect;
                if (isVert)
                    this._axrect = new wijmo.Rect(axisRect.left, axisRect.top, axisRect.height, axisRect.width);
                else
                    this._axrect = axisRect;
            };
            Axis.prototype._hasVisibileSeries = function () {
                var series = this._chart.series, i = 0, len = series.length, vis;
                for (; i < len; i++) {
                    vis = series[i].visibility;
                    if (vis == chart_1.SeriesVisibility.Plot || vis == chart_1.SeriesVisibility.Visible) {
                        return true;
                    }
                }
                return false;
            };
            /**
             * Render the axis.
             *
             * @param engine Rendering engine.
             */
            Axis.prototype._render = function (engine) {
                if (this.position == Position.None) {
                    return;
                }
                if (!this._hasVisibileSeries()) {
                    return;
                }
                this._vals = {};
                var labelAngle = 0;
                if (this.labelAngle) {
                    labelAngle = this.labelAngle;
                    if (labelAngle > 90) {
                        labelAngle = 90;
                    }
                    else if (labelAngle < -90) {
                        labelAngle = -90;
                    }
                }
                // check for null rather than undefined (TFS 250732)
                if (this.labelAngle == null && this._actualAngle != null) {
                    labelAngle = this._actualAngle;
                }
                var fg = chart_1.FlexChart._FG;
                var fontSize = null;
                var range = this._actualMax - this._actualMin;
                if (wijmo.isNumber(range)) {
                    var delta = this._calcMajorUnit();
                    if (delta == 0)
                        delta = this._niceTickNumber(range) * 0.1;
                    var len = Math.min(Axis.MAX_MAJOR, Math.floor(range / delta) + 1);
                    var vals = [];
                    var lbls = [];
                    this._rects = [];
                    this._vals.major = vals;
                    this._vals.hasLbls = [];
                    var st = Math.floor(this._actualMin / delta) * delta;
                    if (st < this._actualMin)
                        st += delta;
                    var isCategory = false;
                    // labels
                    if (this._lbls && this._lbls.length > 0) {
                        lbls = this._lbls; // category
                        if (this._values.length == 0) {
                            isCategory = true;
                            for (var i = 0; i < lbls.length; i++) {
                                vals.push(i);
                            }
                        }
                        else {
                            vals = this._values;
                        }
                    }
                    else if (this._isTimeAxis) {
                        this._createTimeLabels(st, len, vals, lbls); // time
                    }
                    else if (!this.logBase) {
                        this._createLabels(st, len, delta, vals, lbls); // numeric
                    }
                    else {
                        this._createLogarithmicLabels(this._actualMin, this._actualMax, this.majorUnit, vals, lbls, true);
                    }
                    len = Math.min(vals.length, lbls.length);
                    engine.textFill = fg;
                    var th = this._TICK_HEIGHT;
                    var tover = this._TICK_OVERLAP;
                    var tickMarks = this.majorTickMarks;
                    if (tickMarks == null) {
                        tickMarks = TickMark.Outside;
                    }
                    if (tickMarks == TickMark.Outside) {
                        tover = 1;
                    }
                    else if (tickMarks == TickMark.Inside) {
                        tover = -1;
                    }
                    else if (tickMarks == TickMark.Cross) {
                        tover = 0;
                    }
                    var t1 = 0.5 * (tover - 1) * th;
                    var t2 = 0.5 * (1 + tover) * th;
                    for (var i = 0; i < len; i++) {
                        var hasLbl = true;
                        var val = vals[i];
                        var sval = lbls[i];
                        var showLabel = this.labels;
                        if (showLabel && (isCategory || this.itemsSource) && this.majorUnit) {
                            if (i % this.majorUnit != 0) {
                                showLabel = false;
                            }
                        }
                        if (val >= this._actualMin && val <= this._actualMax) {
                            var txtFill = engine.textFill;
                            hasLbl = this._renderLabelsAndTicks(engine, i, val, sval, labelAngle, tickMarks, showLabel, t1, t2);
                            // reset text color
                            engine.textFill = txtFill;
                        }
                        this._vals.hasLbls.push(hasLbl);
                    }
                }
                if ((this.minorGrid || this.minorTickMarks != TickMark.None)) {
                    this._renderMinor(engine, vals, isCategory);
                }
                engine.stroke = fg;
                engine.fontSize = fontSize;
                // line and title
                this._renderLineAndTitle(engine);
                engine.stroke = null;
                engine.fontSize = null;
                engine.textFill = null;
                engine.strokeWidth = null;
            };
            Axis.prototype._renderLineAndTitle = function (engine) {
                var pos = this._getPosition();
                var isVert = this.axisType == AxisType.Y, isNear = pos != Position.Top && pos != Position.Right, titleClass = chart_1.FlexChart._CSS_TITLE, lineClass = chart_1.FlexChart._CSS_LINE;
                if (isVert) {
                    if (isNear) {
                        if (this._title) {
                            var center = new wijmo.Point(this._axrect.left + this._szTitle.height * 0.5, this._axrect.top + 0.5 * this._axrect.height);
                            chart_1.FlexChart._renderRotatedText(engine, this._title, center, 1, 1, center, -90, titleClass, this._groupClass);
                        }
                        if (this.axisLine) {
                            engine.drawLine(this._axrect.right, this._axrect.top, this._axrect.right, this._axrect.bottom, lineClass);
                        }
                    }
                    else {
                        if (this._title) {
                            var center = new wijmo.Point(this._axrect.right - this._szTitle.height * 0.5, this._axrect.top + 0.5 * this._axrect.height);
                            chart_1.FlexChart._renderRotatedText(engine, this._title, center, 1, 1, center, 90, titleClass, this._groupClass);
                        }
                        if (this.axisLine) {
                            engine.drawLine(this._axrect.left, this._axrect.top, this._axrect.left, this._axrect.bottom, lineClass);
                        }
                    }
                }
                else {
                    if (isNear) {
                        if (this.axisLine) {
                            engine.drawLine(this._axrect.left, this._axrect.top, this._axrect.right, this._axrect.top, lineClass);
                        }
                        if (this._title) {
                            chart_1.FlexChart._renderText(engine, this._title, new wijmo.Point(this._axrect.left + 0.5 * this._axrect.width, this._axrect.bottom), 1, 2, titleClass);
                        }
                    }
                    else {
                        if (this.axisLine) {
                            engine.drawLine(this._axrect.left, this._axrect.bottom, this._axrect.right, this._axrect.bottom, lineClass);
                        }
                        if (this._title) {
                            chart_1.FlexChart._renderText(engine, this._title, new wijmo.Point(this._axrect.left + 0.5 * this._axrect.width, this._axrect.top), 1, 0, titleClass);
                        }
                    }
                }
            };
            Axis.prototype._renderMinor = function (engine, vals, isCategory) {
                var pos = this._getPosition();
                var isVert = this.axisType == AxisType.Y, isNear = pos != Position.Top && pos != Position.Right;
                if (!this.logBase)
                    this._createMinors(engine, vals, isVert, isNear, isCategory);
                else {
                    if (this.minorUnit > 0) {
                        var mvals = [];
                        this._createLogarithmicLabels(this._actualMin, this._actualMax, this.minorUnit, mvals, null, false);
                        var ticks = [];
                        for (var i = 0; i < mvals.length; i++) {
                            var val = mvals[i];
                            if (vals.indexOf(val) == -1 && val > this._actualMin)
                                ticks.push(val);
                        }
                        this._renderMinors(engine, ticks, isVert, isNear);
                    }
                }
            };
            Axis.prototype._renderRotatedText = function (engine, val, text, pos, halign, valign, center, angle, className, groupClassName, style) {
                if (this.itemFormatter) {
                    var lbl = this._getFormattedItem(engine, val, text, pos, className);
                    if (lbl) {
                        text = lbl.text;
                        className = lbl.cls;
                    }
                    else {
                        text = null;
                    }
                }
                chart_1.FlexChart._renderRotatedText(engine, text, pos, halign, valign, center, angle, className, groupClassName, style);
            };
            Axis.prototype._getFormattedItem = function (engine, val, text, pos, className) {
                if (this.itemFormatter) {
                    var pt = pos.clone();
                    if (this.axisType == AxisType.X) {
                        if (this.position == Position.Top)
                            pt.y = this._plotrect.top;
                        else
                            pt.y = this._plotrect.bottom;
                    }
                    else {
                        if (this.position == Position.Right)
                            pt.x = this._plotrect.right;
                        else
                            pt.x = this._plotrect.left;
                    }
                    var lbl = { val: val, text: text, pos: pt, cls: className };
                    lbl = this.itemFormatter(engine, lbl);
                    return lbl;
                }
            };
            Axis.prototype._renderLabelsAndTicks = function (engine, index, val, sval, labelAngle, tickMarks, showLabel, t1, t2) {
                var pos = this._getPosition();
                var hasLbl = true, isVert = this.axisType == AxisType.Y, isNear = pos != Position.Top && pos != Position.Right, labelPadding = this.labelPadding || 5, tth = this._TICK_WIDTH, lalign = this._getLabelAlign(isVert), lblClass = chart_1.FlexChart._CSS_LABEL, glineClass = chart_1.FlexChart._CSS_GRIDLINE, tickClass = chart_1.FlexChart._CSS_TICK, gstroke = chart_1.FlexChart._FG, tstroke = chart_1.FlexChart._FG, gth = this._GRIDLINE_WIDTH;
                var has_gline = val != this._actualMin && /*val != this._actualMax &&*/ this.majorGrid;
                if (isVert) {
                    var y = this.convert(val);
                    if (has_gline) {
                        engine.stroke = gstroke;
                        engine.strokeWidth = gth;
                        engine.drawLine(this._plotrect.left, y, this._plotrect.right, y, glineClass);
                    }
                    engine.stroke = tstroke;
                    engine.strokeWidth = tth;
                    if (isNear) {
                        if (tickMarks != TickMark.None) {
                            engine.drawLine(this._axrect.right - t1, y, this._axrect.right - t2, y, tickClass);
                        }
                        if (showLabel) {
                            var lpt = new wijmo.Point(this._axrect.right - t2 - this._TICK_LABEL_DISTANCE - labelPadding, y);
                            if (labelAngle > 0) {
                                if (labelAngle == 90) {
                                    this._renderRotatedText(engine, val, sval, lpt, 1, 0, lpt, labelAngle, lblClass, this._groupClass);
                                }
                                else {
                                    this._renderRotatedText(engine, val, sval, lpt, 2, 1, lpt, labelAngle, lblClass, this._groupClass);
                                }
                            }
                            else if (labelAngle < 0) {
                                if (labelAngle == -90) {
                                    this._renderRotatedText(engine, val, sval, lpt, 1, 2, lpt, labelAngle, lblClass, this._groupClass);
                                }
                                else {
                                    this._renderRotatedText(engine, val, sval, lpt, 2, 1, lpt, labelAngle, lblClass, this._groupClass);
                                }
                            }
                            else {
                                this._renderLabel(engine, val, sval, lpt, 2, lalign /*1*/, lblClass);
                            }
                        }
                    }
                    else {
                        if (tickMarks != TickMark.None) {
                            engine.drawLine(this._axrect.left + t1, y, this._axrect.left + t2, y, tickClass);
                        }
                        if (showLabel) {
                            var lpt = new wijmo.Point(this._axrect.left + t2 + this._TICK_LABEL_DISTANCE + labelPadding, y);
                            if (labelAngle > 0) {
                                if (labelAngle == 90) {
                                    this._renderRotatedText(engine, val, sval, lpt, 1, 2, lpt, labelAngle, lblClass, this._groupClass);
                                }
                                else {
                                    this._renderRotatedText(engine, val, sval, lpt, 0, 1, lpt, labelAngle, lblClass, this._groupClass);
                                }
                            }
                            else if (labelAngle < 0) {
                                if (labelAngle == -90) {
                                    this._renderRotatedText(engine, val, sval, lpt, 1, 0, lpt, labelAngle, lblClass, this._groupClass);
                                }
                                else {
                                    this._renderRotatedText(engine, val, sval, lpt, 0, 1, lpt, labelAngle, lblClass, this._groupClass);
                                }
                            }
                            else {
                                this._renderLabel(engine, val, sval, lpt, 0, lalign /* 1*/, lblClass);
                            }
                        }
                    }
                }
                else {
                    var x = this.convert(val);
                    if (this.overlappingLabels == OverlappingLabels.Auto && this._xCross(x))
                        showLabel = false;
                    if (has_gline) {
                        engine.stroke = gstroke;
                        engine.strokeWidth = gth;
                        engine.drawLine(x, this._plotrect.top, x, this._plotrect.bottom, glineClass);
                    }
                    engine.stroke = tstroke;
                    engine.strokeWidth = tth;
                    if (isNear) {
                        hasLbl = false;
                        if (showLabel) {
                            var lpt = new wijmo.Point(x, this._axrect.top + t2 + labelPadding);
                            if (labelAngle != 0) {
                                hasLbl = this._renderRotatedLabel(engine, val, sval, lpt, lalign, labelAngle, lblClass, isNear);
                                //var sz = engine.measureString(sval, lblClass);
                                //if (labelAngle != 90 && labelAngle != -90) {
                                //    lpt.y += 0.5 * sz.height * Math.abs(Math.sin(labelAngle * Math.PI / 180));
                                //}
                                //if (labelAngle > 0) {
                                //    FlexChart._renderRotatedText(engine, sval, lpt, 0, 1, lpt, labelAngle, lblClass);
                                //} else if (labelAngle < 0) {
                                //    FlexChart._renderRotatedText(engine, sval, lpt, 2, 1, lpt, labelAngle, lblClass);
                                //}
                                // hasLbl = true;
                            }
                            else {
                                hasLbl = this._renderLabel(engine, val, sval, lpt, lalign /* 1*/, 0, lblClass);
                            }
                        }
                        if (tickMarks != TickMark.None) {
                            /*if (isCategory && len<=10) {
                                val = val - 0.5;
                                if (val >= this._actualMin && val <= this._actualMax) {
                                    x = this.convert(val);
                                    engine.drawLine(x, this._axrect.top + t1, x, this._axrect.top + t2, tickClass);
                                }
                                if (i == len - 1) {
                                    val = val + 1;
                                    if (val >= this._actualMin && val <= this._actualMax) {
                                        x = this.convert(val);
                                        engine.drawLine(x, this._axrect.top + t1, x, this._axrect.top + t2, tickClass);
                                    }
                                }
                            } else */
                            if (hasLbl) {
                                x = this.convert(val);
                                engine.drawLine(x, this._axrect.top + t1, x, this._axrect.top + t2, tickClass);
                            }
                        }
                    }
                    else {
                        if (showLabel) {
                            var lpt = new wijmo.Point(x, this._axrect.bottom - t2 - labelPadding);
                            if (labelAngle != 0) {
                                hasLbl = this._renderRotatedLabel(engine, val, sval, lpt, lalign, labelAngle, lblClass, isNear);
                                //var sz = engine.measureString(sval, lblClass);
                                //if (labelAngle != 90 && labelAngle != -90) {
                                //    lpt.y -= 0.5 * sz.height * Math.abs(Math.sin(labelAngle * Math.PI / 180));
                                //}
                                //if (labelAngle > 0) {
                                //    FlexChart._renderRotatedText(engine, sval, lpt, 2, 1, lpt, labelAngle, lblClass);
                                //} else if (labelAngle < 0) {
                                //    FlexChart._renderRotatedText(engine, sval, lpt, 0, 1, lpt, labelAngle, lblClass);
                                //}
                            }
                            else {
                                hasLbl = this._renderLabel(engine, val, sval, lpt, lalign /*1*/, 2, lblClass);
                            }
                        }
                        if (tickMarks != TickMark.None) {
                            /*if (isCategory && len <= 10) { // offset only if number of labels is small
                                val = val - 0.5;
                                if (val >= this._actualMin && val <= this._actualMax) {
                                    x = this.convert(val);
                                    engine.drawLine(x, this._axrect.bottom - t1, x, this._axrect.bottom - t2, tickClass);
                                }
                                if (i == len - 1) {
                                    val = val + 1;
                                    if (val >= this._actualMin && val <= this._actualMax) {
                                        x = this.convert(val);
                                        engine.drawLine(x, this._axrect.bottom - t1, x, this._axrect.bottom - t2, tickClass);
                                    }
                                }
                            }
                            else */ {
                                x = this.convert(val);
                                engine.drawLine(x, this._axrect.bottom - t1, x, this._axrect.bottom - t2, tickClass);
                            }
                        }
                    }
                }
                return hasLbl;
            };
            Axis.prototype._xCross = function (x) {
                var len = this._rects.length;
                for (var i = 0; i < len; i++) {
                    var r = this._rects[i];
                    if (x >= r.left && x <= r.right) {
                        return true;
                    }
                }
                return false;
            };
            Axis.prototype._createMinors = function (engine, vals, isVert, isNear, isCategory) {
                if (vals && vals.length > 1) {
                    var delta = this.majorUnit
                        ? (this._isTimeAxis ? this.majorUnit * 24 * 3600 * 1000 : this.majorUnit)
                        : vals[1] - vals[0];
                    var minorUnit = wijmo.isNumber(this.minorUnit)
                        ? (this._isTimeAxis ? this.minorUnit * 24 * 3600 * 1000 : this.minorUnit)
                        : delta * 0.5;
                    var ticks = [];
                    for (var val = vals[0]; val > this._actualMin && ticks.length < Axis.MAX_MINOR; val -= minorUnit) {
                        if (vals.indexOf(val) == -1) {
                            ticks.push(val);
                        }
                    }
                    for (var val = vals[0] + minorUnit; val < this._actualMax && ticks.length < Axis.MAX_MINOR; val += minorUnit) {
                        if (vals.indexOf(val) == -1) {
                            ticks.push(val);
                        }
                        else if (isCategory && this.majorUnit && val % this.majorUnit != 0) {
                            ticks.push(val);
                        }
                    }
                    this._renderMinors(engine, ticks, isVert, isNear);
                }
            };
            Axis.prototype._renderMinors = function (engine, ticks, isVert, isNear) {
                var th = this._TICK_HEIGHT;
                var tth = this._TICK_WIDTH;
                var tover = this._TICK_OVERLAP;
                var tstroke = chart_1.FlexChart._FG;
                var tickMarks = this.minorTickMarks;
                var hasTicks = true;
                this._vals.minor = ticks;
                if (tickMarks == TickMark.Outside) {
                    tover = 1;
                }
                else if (tickMarks == TickMark.Inside) {
                    tover = -1;
                }
                else if (tickMarks == TickMark.Cross) {
                    tover = 0;
                }
                else {
                    hasTicks = false;
                }
                var t1 = 0.5 * (tover - 1) * th;
                var t2 = 0.5 * (1 + tover) * th;
                var cnt = ticks ? ticks.length : 0;
                var grid = this.minorGrid;
                var prect = this._plotrect;
                var gth = this._GRIDLINE_WIDTH;
                var gstroke = chart_1.FlexChart._FG;
                // CSS
                var glineClass = chart_1.FlexChart._CSS_GRIDLINE_MINOR;
                var tickClass = chart_1.FlexChart._CSS_TICK_MINOR;
                for (var i = 0; i < cnt; i++) {
                    //only render ticks between min and max.
                    if (ticks[i] >= this.actualMin && ticks[i] <= this.actualMax) {
                        if (isVert) {
                            var y = this.convert(ticks[i]);
                            if (hasTicks) {
                                engine.stroke = tstroke;
                                engine.strokeWidth = tth;
                                if (isNear) {
                                    engine.drawLine(this._axrect.right - t1, y, this._axrect.right - t2, y, tickClass);
                                }
                                else {
                                    engine.drawLine(this._axrect.left + t1, y, this._axrect.left + t2, y, tickClass);
                                }
                            }
                            if (grid) {
                                engine.stroke = gstroke;
                                engine.strokeWidth = gth;
                                engine.drawLine(prect.left, y, prect.right, y, glineClass);
                            }
                        }
                        else {
                            var x = this.convert(ticks[i]);
                            if (hasTicks) {
                                engine.stroke = tstroke;
                                engine.strokeWidth = tth;
                                if (isNear) {
                                    engine.drawLine(x, this._axrect.top + t1, x, this._axrect.top + t2, tickClass);
                                }
                                else {
                                    engine.drawLine(x, this._axrect.bottom - t1, x, this._axrect.bottom - t2, tickClass);
                                }
                            }
                            if (grid) {
                                engine.stroke = gstroke;
                                engine.strokeWidth = gth;
                                engine.drawLine(x, prect.top, x, prect.bottom, glineClass);
                            }
                        }
                    }
                }
            };
            Axis.prototype._renderLabel = function (engine, val, text, pos, ha, va, className) {
                var ok = false;
                if (this.itemFormatter) {
                    var lbl = this._getFormattedItem(engine, val, text, pos, className);
                    if (lbl) {
                        text = lbl.text;
                        className = lbl.cls;
                    }
                    else {
                        text = null;
                    }
                }
                if (text) {
                    var rects = this._rects;
                    var hide = this.overlappingLabels == OverlappingLabels.Auto && !wijmo.isNumber(this._actualAngle);
                    var rect = chart_1.FlexChart._renderText(engine, text, pos, ha, va, className, this._groupClass, null, function (rect) {
                        if (hide) {
                            var len = rects.length;
                            for (var i = 0; i < len; i++) {
                                if (chart_1.FlexChart._intersects(rects[i], rect))
                                    return false;
                            }
                        }
                        return true;
                    });
                    if (rect) {
                        // extend rect to have more intervals between labels
                        rect.left += 4;
                        rect.width += 8;
                        rects.push(rect);
                        ok = true;
                    }
                }
                return ok;
            };
            Axis.prototype._renderRotatedLabel = function (engine, val, sval, lpt, ha, labelAngle, lblClass, isNear) {
                if (this.itemFormatter) {
                    var lbl = this._getFormattedItem(engine, val, sval, lpt, lblClass);
                    if (lbl) {
                        sval = lbl.text;
                        lblClass = lbl.cls;
                    }
                    else {
                        sval = null;
                    }
                }
                if (sval) {
                    var sz = engine.measureString(sval, lblClass, this._groupClass);
                    var dy = 0.5 * sz.height;
                    var dx = 0.5 * sz.width * Math.abs(Math.sin(labelAngle * Math.PI / 180));
                    var dw = 0.5 * sz.width;
                    var offWidth = 0.5 * (sz.width * Math.abs(Math.cos(labelAngle * Math.PI / 180)) + sz.height * Math.abs(Math.sin(labelAngle * Math.PI / 180)));
                    var center = new wijmo.Point(lpt.x, lpt.y);
                    var pt = new wijmo.Point(lpt.x, lpt.y);
                    //if label align is not set, adjust its align according to angle.
                    if (!this.labelAlign) {
                        if (labelAngle == 90 || labelAngle == -90) {
                            ha = 1;
                        }
                        else {
                            if (isNear) {
                                if (labelAngle > 0) {
                                    ha = 0;
                                }
                                else {
                                    ha = 2;
                                }
                                //ha = 2;
                            }
                            else {
                                if (labelAngle > 0) {
                                    ha = 2;
                                }
                                else {
                                    ha = 0;
                                }
                                //ha = 0;
                            }
                        }
                    }
                    if (isNear) {
                        lpt.y += dy + dx;
                        center.y += dy + dx - 0.5 * sz.height;
                    }
                    else {
                        lpt.y -= dy + dx - sz.height;
                        center.y -= dy + dx - 0.5 * sz.height;
                    }
                    var rectLeft = 0;
                    if (ha === 2) {
                        //right
                        center.x -= offWidth;
                        lpt.x -= dw + offWidth;
                        rectLeft = center.x + offWidth - sz.height - 2;
                    }
                    else if (ha === 0) {
                        //left
                        center.x += offWidth;
                        lpt.x -= dw - offWidth;
                        rectLeft = center.x - offWidth;
                    }
                    else {
                        lpt.x -= dw;
                        rectLeft = center.x - sz.height / 2;
                    }
                    var rect = new wijmo.Rect(rectLeft, pt.y, sz.height + 2, sz.width);
                    var rects = this._rects;
                    var hide = this.overlappingLabels == OverlappingLabels.Auto;
                    if (hide) {
                        var len = rects.length;
                        for (var i = 0; i < len; i++) {
                            if (chart_1.FlexChart._intersects(rects[i], rect))
                                return false;
                        }
                    }
                    chart_1.FlexChart._renderRotatedText(engine, sval, lpt, 0, 2, center, labelAngle, lblClass, this._groupClass);
                    this._rects.push(rect);
                    return true;
                }
                else {
                    return false;
                }
            };
            Axis.prototype._getLabelAlign = function (isVert) {
                var lalign = 1;
                if (this.labelAlign) {
                    var la = this.labelAlign.toLowerCase();
                    if (isVert) {
                        if (la == 'top') {
                            lalign = 0;
                        }
                        else if (la == 'bottom') {
                            lalign = 2;
                        }
                    }
                    else {
                        if (la == 'left') {
                            lalign = 0;
                        }
                        else if (la == 'right') {
                            lalign = 2;
                        }
                    }
                }
                return lalign;
            };
            /**
             * Converts the specified value from data to pixel coordinates.
             *
             * @param val The data value to convert.
             * @param maxValue The max value of the data, it's optional.
             * @param minValue The min value of the data, it's optional.
             */
            Axis.prototype.convert = function (val, maxValue, minValue) {
                var max = maxValue == null ? this._actualMax : maxValue, min = minValue == null ? this._actualMin : minValue;
                if (max == min) {
                    return 0;
                }
                var x = this._axrect.left;
                var w = this._axrect.width;
                var y = this._axrect.top;
                var h = this._axrect.height;
                if (this._customConvert != null) {
                    var r = this._customConvert(val, min, max);
                    if (this.axisType == AxisType.Y) {
                        return y + r * h;
                    }
                    else {
                        return x + r * w; // x + w - r * w;
                    }
                }
                else {
                    var base = this.logBase;
                    if (!base) {
                        if (this._reversed) {
                            if (this.axisType == AxisType.Y) {
                                return y + (val - min) / (max - min) * h;
                            }
                            else {
                                return x + w - (val - min) / (max - min) * w;
                            }
                        }
                        else {
                            if (this.axisType == AxisType.Y) {
                                return y + h - (val - min) / (max - min) * h;
                            }
                            else {
                                return x + (val - min) / (max - min) * w;
                            }
                        }
                    }
                    else {
                        if (val <= 0)
                            return NaN;
                        var maxl = Math.log(max / min);
                        if (this._reversed) {
                            if (this.axisType == AxisType.Y)
                                return y + Math.log(val / min) / maxl * h;
                            else
                                return x + w - Math.log(val / min) / maxl * w;
                        }
                        else {
                            if (this.axisType == AxisType.Y)
                                return y + h - Math.log(val / min) / maxl * h;
                            else
                                return x + Math.log(val / min) / maxl * w;
                        }
                    }
                }
            };
            /**
             * Converts the specified value from pixel to data coordinates.
             *
             * @param val The pixel coordinates to convert back.
             */
            Axis.prototype.convertBack = function (val) {
                if (this._actualMax == this._actualMin) {
                    return 0;
                }
                var x = this._plotrect.left;
                var w = this._plotrect.width;
                var y = this._plotrect.top;
                var h = this._plotrect.height;
                var range = this._actualMax - this._actualMin;
                var base = this.logBase;
                if (this._customConvertBack != null) {
                    if (this.axisType == AxisType.Y) {
                        return this._customConvertBack((val - y) / h, this._actualMin, this._actualMax);
                    }
                    else {
                        return this._customConvertBack((val - x) / w, this._actualMin, this._actualMax);
                    }
                }
                else if (!base) {
                    if (this._reversed) {
                        if (this.axisType == AxisType.Y) {
                            return this._actualMin + (val - y) * range / h;
                        }
                        else {
                            return this._actualMin + (x + w - val) * range / w;
                        }
                    }
                    else {
                        if (this.axisType == AxisType.Y) {
                            return this._actualMax - (val - y) * range / h;
                        }
                        else {
                            return this._actualMin + (val - x) * range / w;
                        }
                    }
                }
                else {
                    var rval = 0;
                    if (this._reversed) {
                        if (this.axisType == AxisType.Y) {
                            rval = (val - y) / h;
                        }
                        else {
                            rval = 1 - (val - x) / w;
                        }
                    }
                    else {
                        if (this.axisType == AxisType.Y) {
                            rval = 1 - (val - y) / h;
                        }
                        else {
                            rval = (val - x) / w;
                        }
                    }
                    return Math.pow(base, (Math.log(this._actualMin) + (Math.log(this._actualMax) - Math.log(this._actualMin)) * rval) / Math.log(base));
                }
            };
            Object.defineProperty(Axis.prototype, "axisType", {
                /**
                 * Gets the axis type.
                 */
                get: function () {
                    var chart = this._chart;
                    if (chart) {
                        if (chart.axisX == this) {
                            return AxisType.X;
                        }
                        else if (chart.axisY == this) {
                            return AxisType.Y;
                        }
                    }
                    return this._axisType;
                },
                enumerable: true,
                configurable: true
            });
            Axis.prototype._getMinNum = function () {
                return this._actualMin;
            };
            Axis.prototype._getMaxNum = function () {
                return this._actualMax;
            };
            //---------------------------------------------------------------------
            // private
            Axis.prototype._invalidate = function () {
                if (this._chart) {
                    this._chart.invalidate();
                }
            };
            Axis.prototype._cvCollectionChanged = function (sender, e) {
                this._invalidate();
            };
            Axis.prototype._createLabels = function (start, len, delta, vals, lbls) {
                for (var i = 0; i < len; i++) {
                    var val0 = (start + delta * i).toFixed(14); //  Math.round(st + delta * i);//, 14); // 15
                    var val = parseFloat(val0);
                    //if (val > max)
                    //  break;
                    var sval = this._formatValue(val);
                    vals.push(val);
                    lbls.push(sval);
                }
            };
            Axis.prototype._createLogarithmicLabels = function (min, max, unit, vals, lbls, isLabels) {
                var base = this.logBase;
                var k = Math.log(base);
                var imin = Math.floor(Math.log(min) / k);
                var imax = Math.ceil(Math.log(max) / k);
                var delta = base;
                var auto = true;
                if (unit > 0) {
                    auto = false;
                    delta = unit; // islabels = false;
                }
                if (delta < base)
                    delta = base;
                //if (delta <= 0)
                //    return;
                var n = ((imax - imin + 1) * base / delta);
                // try some rational number for large values.
                // garyh 21-Apr-05 - VNCHT000250
                /* if (n > 128) {
                    if (isPowerOf(logbase, 10))
                        delta = logbase / 10;
                    else if (isPowerOf(logbase, 5))
                        delta = logbase / 5;
                    else
                        delta = logbase / 16;
    
                    n = (int)((imax - imin + 1) * logbase / delta);
    
                    if (n > 128)		// the user must handle the unitminor
                        delta = logbase;
                }*/
                var step = 1;
                if (isLabels) {
                    var pos = this._getPosition();
                    var na = this._getAnnoNumber(pos == Position.Left || pos == Position.Right);
                    if (n > na)
                        step = Math.floor(n / na + 1);
                    else if (auto) {
                        if (n <= 0.2 * na)
                            delta = 0.2 * base;
                        else if (n <= 0.1 * na)
                            delta = 0.1 * base;
                    }
                }
                for (var i = imin; i <= imax; i += step) {
                    if (auto) {
                        var baseval = Math.pow(base, i);
                        for (var j = 0; j * delta < (base - 1); j++) {
                            var val = baseval * (1 + j * delta);
                            if (val >= min && val <= max) {
                                if (j == 0) {
                                    vals.unshift(val);
                                    if (lbls)
                                        lbls.unshift(this._formatValue(val));
                                }
                                else {
                                    vals.push(val);
                                    if (lbls)
                                        lbls.push(this._formatValue(val));
                                }
                            }
                        }
                    }
                    else {
                        var val = Math.pow(delta, i);
                        if (val >= min && val <= max) {
                            vals.push(val);
                            if (lbls)
                                lbls.push(this._formatValue(val));
                        }
                    }
                }
            };
            Axis.prototype._createTimeLabels = function (start, len, vals, lbls) {
                var min = this._actualMin, max = this._actualMax, dtmin0 = new Date(min), dtmax0 = new Date(max);
                var fmt = this._format, anum = this._getAnnoNumber(this._axisType == AxisType.Y);
                if (anum > 12) {
                    anum = 12;
                }
                //if (!this._format)
                //    this._tfmt = fmt = _timeHelper.GetTimeDefaultFormat( (max - min) / anum, 0 );
                // alext 10-Jan-2010
                // better precision
                //var td = (24.0 * 3600.0 * (this._actualMax - this._actualMin) / anum);
                var td = (0.001 * (this._actualMax - this._actualMin) / anum);
                var range = new _timeSpan(td * _timeSpan.TicksPerSecond);
                var delta = wijmo.isNumber(this._majorUnit) // TFS 250742
                    ? _timeSpan.fromDays(this._majorUnit)
                    : _timeHelper.NiceTimeSpan(range, fmt);
                if (!fmt) {
                    this._tfmt = fmt = _timeHelper.GetTimeDefaultFormat(1000 * delta.TotalSeconds, 0);
                }
                var delta_ticks = delta.Ticks;
                var newmin = _timeHelper.RoundTime(min, delta.TotalDays, false);
                if (isFinite(newmin)) {
                    min = newmin;
                }
                var newmax = _timeHelper.RoundTime(max, delta.TotalDays, true);
                if (isFinite(newmax)) {
                    max = newmax;
                }
                var dtmin = new Date(min);
                var dtmax = new Date(max);
                if (delta.TotalDays >= 365 && !wijmo.isNumber(this._majorUnit)) {
                    dtmin = new Date(dtmin0.getFullYear(), 1, 1);
                    if (dtmin < dtmin0) {
                        //dtmin = dtmin.AddYears(1);
                        dtmin.setFullYear(dtmin.getFullYear() + 1);
                    }
                    var years = (delta.TotalDays / 365);
                    years = years - (years % 1);
                    for (var current = dtmin; current <= dtmax0 && years; 
                    //current = current.AddYears(nyears)
                    current.setFullYear(current.getFullYear() + years)) {
                        var val = current.valueOf();
                        vals.push(val);
                        lbls.push(this._formatValue(val));
                    }
                }
                else if (delta.TotalDays >= 30 && !wijmo.isNumber(this._majorUnit)) {
                    dtmin = new Date(dtmin0.getFullYear(), dtmin0.getMonth(), 1);
                    if (dtmin < dtmin0)
                        //dtmin = dtmin.AddMonths(1);
                        dtmin.setMonth(dtmin.getMonth() + 1);
                    var nmonths = delta.TotalDays / 30;
                    nmonths = nmonths - (nmonths % 1);
                    for (var current = dtmin; current <= dtmax0; 
                    //current = current.AddMonths(nmonths)
                    current.setMonth(current.getMonth() + nmonths)) {
                        var val = current.valueOf();
                        vals.push(val);
                        lbls.push(this._formatValue(val));
                    }
                }
                else {
                    var dt = (1000 * delta_ticks) / _timeSpan.TicksPerSecond, current = dtmin, timedif = dtmin0.getTime() - current.getTime();
                    if (timedif > dt) {
                        current = new Date(current.getTime() + Math.floor(timedif / dt) * dt);
                    }
                    for (; current <= dtmax0 && dt; 
                    //current = current.AddTicks(delta_ticks)
                    current = new Date(current.getTime() + dt)) {
                        if (current >= dtmin0) {
                            var val = current.valueOf();
                            vals.push(val);
                            lbls.push(this._formatValue(val));
                        }
                    }
                }
            };
            Axis.prototype._formatValue = function (val) {
                if (this._isTimeAxis) {
                    if (this._format) {
                        return wijmo.Globalize.format(new Date(val), this._format);
                    }
                    else {
                        return wijmo.Globalize.format(new Date(val), this._tfmt);
                    }
                }
                else {
                    if (this._format)
                        return wijmo.Globalize.format(val, this._format);
                    else {
                        var fmt = val == Math.round(val) ? 'n0' : 'n';
                        return wijmo.Globalize.format(val, fmt);
                    }
                }
            };
            Axis.prototype._calcMajorUnit = function () {
                var delta = this._majorUnit;
                if (!wijmo.isNumber(delta)) {
                    var range = this._actualMax - this._actualMin;
                    var prec = this._nicePrecision(range);
                    var dx = range / this._getAnnoNumber(this.axisType == AxisType.Y);
                    delta = this._niceNumber(2 * dx, -prec, true);
                    if (delta < dx) {
                        delta = this._niceNumber(dx, -prec + 1, false);
                    }
                    if (delta < dx) {
                        delta = this._niceTickNumber(dx);
                    }
                }
                return delta;
            };
            Axis.prototype._getAnnoNumber = function (isVert) {
                var w0 = isVert ? this._annoSize.height : this._annoSize.width;
                var w = isVert ? this._axrect.height : this._axrect.width;
                if (w0 > 0 && w > 0) {
                    var n = Math.floor(w / (w0 + 6));
                    if (n <= 0) {
                        n = 1;
                    }
                    return n;
                }
                else {
                    return 10;
                }
            };
            Axis.prototype._nicePrecision = function (range) {
                //
                //	Return a nice precision value for this range.
                //	Doesn't take into account font size, window
                //	size, etc.	Just use the log10 of the range.
                //
                if (!wijmo.isNumber(range) || range <= 0) {
                    return 0;
                }
                var log10 = Math.log(range) / Math.LN10;
                var exp;
                if (log10 >= 0) {
                    exp = Math.floor(log10); //(int)(SignedFloor(log10));
                }
                else {
                    exp = Math.ceil(log10);
                }
                var f = range / Math.pow(10.0, exp);
                /* we need the extra digit near the lower end */
                if (f < 3.0) {
                    exp = -exp + 1;
                    // more precision for more labels
                    f = range / Math.pow(10.0, exp);
                    if (f < 3.0) {
                        exp = exp + 1;
                    }
                }
                return exp;
            };
            Axis.prototype._niceTickNumber = function (x) {
                if (x == 0) {
                    return x;
                }
                else if (x < 0) {
                    x = -x;
                }
                var log10 = Math.log(x) / Math.LN10;
                var exp = Math.floor(log10); // (int) SignedFloor(log10);
                var f = x / Math.pow(10.0, exp);
                var nf = 10.0;
                if (f <= 1.0) {
                    nf = 1.0;
                }
                else if (f <= 2.0) {
                    nf = 2.0;
                }
                else if (f <= 5.0) {
                    nf = 5.0;
                }
                return (nf * Math.pow(10.0, exp));
            };
            Axis.prototype._niceNumber = function (x, exp, round) {
                if (x == 0) {
                    return x;
                }
                else if (x < 0) {
                    x = -x;
                }
                var f = x / Math.pow(10.0, exp);
                var nf = 10.0;
                if (round) {
                    if (f < 1.5) {
                        nf = 1;
                    }
                    else if (f < 3) {
                        nf = 2;
                    }
                    else if (f < 4.5) {
                        nf = 4;
                    }
                    else if (f < 7) {
                        nf = 5;
                    }
                }
                else {
                    if (f <= 1) {
                        nf = 1;
                    }
                    else if (f <= 2) {
                        nf = 2;
                    }
                    else if (f <= 5) {
                        nf = 5;
                    }
                }
                return (nf * Math.pow(10.0, exp));
            };
            Object.defineProperty(Axis.prototype, "_uniqueId", {
                get: function () {
                    return this.__uniqueId;
                },
                enumerable: true,
                configurable: true
            });
            Axis.MAX_MAJOR = 1000;
            Axis.MAX_MINOR = 2000;
            Axis._id = 0;
            return Axis;
        }());
        chart_1.Axis = Axis;
        /**
         * Represents a collection of @see:Axis objects in a @see:FlexChart control.
         */
        var AxisCollection = /** @class */ (function (_super) {
            __extends(AxisCollection, _super);
            function AxisCollection() {
                return _super !== null && _super.apply(this, arguments) || this;
            }
            /**
             * Gets an axis by name.
             *
             * @param name The name of the axis to look for.
             * @return The axis object with the specified name, or null if not found.
             */
            AxisCollection.prototype.getAxis = function (name) {
                var index = this.indexOf(name);
                return index > -1 ? this[index] : null;
            };
            /**
             * Gets the index of an axis by name.
             *
             * @param name The name of the axis to look for.
             * @return The index of the axis with the specified name, or -1 if not found.
             */
            AxisCollection.prototype.indexOf = function (name) {
                for (var i = 0; i < this.length; i++) {
                    if (this[i].name == name) {
                        return i;
                    }
                }
                return -1;
            };
            return AxisCollection;
        }(wijmo.collections.ObservableArray));
        chart_1.AxisCollection = AxisCollection;
        var _tmInc;
        (function (_tmInc) {
            _tmInc[_tmInc["tickf7"] = -7] = "tickf7";
            _tmInc[_tmInc["tickf6"] = -6] = "tickf6";
            _tmInc[_tmInc["tickf5"] = -5] = "tickf5";
            _tmInc[_tmInc["tickf4"] = -4] = "tickf4";
            _tmInc[_tmInc["tickf3"] = -3] = "tickf3";
            _tmInc[_tmInc["tickf2"] = -2] = "tickf2";
            _tmInc[_tmInc["tickf1"] = -1] = "tickf1";
            _tmInc[_tmInc["second"] = 1] = "second";
            _tmInc[_tmInc["minute"] = 60] = "minute";
            _tmInc[_tmInc["hour"] = 3600] = "hour";
            _tmInc[_tmInc["day"] = 86400] = "day";
            _tmInc[_tmInc["week"] = 604800] = "week";
            _tmInc[_tmInc["month"] = 2678400] = "month";
            _tmInc[_tmInc["year"] = 31536000] = "year";
            _tmInc[_tmInc["maxtime"] = Number.MAX_VALUE] = "maxtime";
        })(_tmInc || (_tmInc = {}));
        var _timeSpan = /** @class */ (function () {
            function _timeSpan(ticks) {
                this.ticks = ticks;
            }
            Object.defineProperty(_timeSpan.prototype, "Ticks", {
                get: function () {
                    return this.ticks;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_timeSpan.prototype, "TotalSeconds", {
                get: function () {
                    return this.ticks / 10000000;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_timeSpan.prototype, "TotalDays", {
                get: function () {
                    return this.ticks / 10000000 / (24 * 60 * 60);
                },
                enumerable: true,
                configurable: true
            });
            _timeSpan.fromSeconds = function (seconds) {
                return new _timeSpan(seconds * 10000000);
            };
            _timeSpan.fromDays = function (days) {
                return new _timeSpan(days * 10000000 * 24 * 60 * 60);
            };
            _timeSpan.TicksPerSecond = 10000000;
            return _timeSpan;
        }());
        var _timeHelper = /** @class */ (function () {
            function _timeHelper(date) {
                if (wijmo.isDate(date))
                    this.init(date);
                else if (wijmo.isNumber(date))
                    this.init(chart_1.FlexChart._fromOADate(date));
            }
            _timeHelper.prototype.init = function (dt) {
                this.year = dt.getFullYear();
                this.month = dt.getMonth();
                this.day = dt.getDate();
                this.hour = dt.getHours();
                this.minute = dt.getMinutes();
                this.second = dt.getSeconds();
            };
            _timeHelper.prototype.getTimeAsDateTime = function () {
                var smon = 0, sday = 0, ssec = 0;
                // N3CHT000043
                if (this.hour >= 24) {
                    this.hour -= 24;
                    this.day += 1;
                }
                if (this.month < 0) {
                    smon = -1 - this.day;
                    this.month = 1;
                }
                else if (this.month > 11) {
                    smon = this.month - 12;
                    this.month = 12;
                }
                if (this.day < 1) {
                    sday = -1 - this.day;
                    this.day = 1;
                }
                else if (this.day > 28 && this.month == 2) {
                    sday = this.day - 28;
                    this.day = 28;
                }
                else if (this.day > 30 && (this.month == 4 || this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11)) {
                    sday = this.day - 30;
                    this.day = 30;
                }
                else if (this.day > 31) {
                    sday = this.day - 31;
                    this.day = 31;
                }
                if (this.second > 59) {
                    ssec = this.second - 59;
                    this.second = 59;
                }
                var smin = 0;
                if (this.minute > 59) {
                    smin = this.minute - 59;
                    this.minute = 59;
                }
                return new Date(this.year, this.month, this.day, this.hour, this.minute, this.second);
                //AddDays(sday).AddMonths(smon).AddSeconds(ssec).AddMinutes(smin);
            };
            _timeHelper.prototype.getTimeAsDouble = function () {
                return this.getTimeAsDateTime().valueOf();
            };
            _timeHelper.tround = function (tval, tunit, roundup) {
                var test = ((tval / tunit) * tunit);
                test = test - (test % 1);
                if (roundup && test != tval) {
                    tunit = tunit - (tunit % 1);
                    test += tunit;
                }
                return test;
            };
            _timeHelper.RoundTime = function (timevalue, unit, roundup) {
                //TimeSpan ts = TimeSpan.FromDays(unit);
                var tunit = unit * 24 * 60 * 60; // (long) ts.TotalSeconds;
                if (tunit > 0) {
                    var th = new _timeHelper(timevalue);
                    if (tunit < _tmInc.minute) {
                        th.second = this.tround(th.second, tunit, roundup);
                        return th.getTimeAsDouble();
                    }
                    th.second = 0;
                    if (tunit < _tmInc.hour) {
                        tunit /= _tmInc.minute;
                        th.minute = this.tround(th.minute, tunit, roundup);
                        return th.getTimeAsDouble();
                    }
                    th.minute = 0;
                    if (tunit < _tmInc.day) {
                        tunit /= _tmInc.hour;
                        th.hour = this.tround(th.hour, tunit, roundup);
                        return th.getTimeAsDouble();
                    }
                    th.hour = 0;
                    if (tunit < _tmInc.month) {
                        tunit /= _tmInc.day;
                        th.day = this.tround(th.day, tunit, roundup);
                        return th.getTimeAsDouble();
                    }
                    th.day = 1;
                    if (tunit < _tmInc.year) {
                        tunit /= _tmInc.month;
                        // Jan - is good enough
                        if (th.month != 1)
                            th.month = this.tround(th.month, tunit, roundup);
                        return th.getTimeAsDouble();
                    }
                    th.month = 1;
                    tunit /= _tmInc.year;
                    th.year = this.tround(th.year, tunit, roundup);
                    return th.getTimeAsDouble();
                }
                else {
                    // alext 26-Sep-03
                    //double td = ts.TotalSeconds;
                    var td = timevalue;
                    var tx = td - tunit;
                    var tz = ((tx / unit)) * unit; // alext 12-Sep-06 int -> long VNCHT000517
                    if (roundup && tz != tx)
                        tz += unit;
                    td = tunit + tz;
                    return td;
                }
            };
            _timeHelper.TimeSpanFromTmInc = function (ti) {
                var rv = _timeSpan.fromSeconds(1);
                if (ti != _tmInc.maxtime) {
                    if (ti > _tmInc.tickf1) {
                        rv = _timeSpan.fromSeconds(ti);
                    }
                    else {
                        var rti = ti;
                        var ticks = 1;
                        rti += 7; // rti is now power of 10 of number of Ticks
                        while (rti > 0) {
                            ticks *= 10;
                            rti--;
                        }
                        rv = new _timeSpan(ticks);
                    }
                }
                return rv;
            };
            _timeHelper.manualTimeInc = function (manualformat) {
                var minSpan = _tmInc.second;
                // only interested in the lowest increment of the format,
                // so it is not necessary that the format be valid, but it
                // must exist as a string to process.
                if (manualformat == null || manualformat.length == 0)
                    return minSpan;
                var f = manualformat.indexOf('f');
                if (f >= 0) {
                    var rv = -1;
                    if (f > 0 && manualformat.substr(f - 1, 1) == '%') {
                        rv = -1;
                    }
                    else {
                        for (var i = 1; i < 6; i++) {
                            // alext 26-Sep-03
                            if ((f + i) >= manualformat.length)
                                break;
                            //
                            var ss = manualformat.substr(f + i, 1);
                            if (ss != 'f')
                                break;
                            //if (!manualformat.Substring(f + i, 1).Equals('f'))
                            //  break;
                            rv--;
                        }
                    }
                    minSpan = rv;
                }
                else if (manualformat.indexOf('s') >= 0)
                    minSpan = _tmInc.second;
                else if (manualformat.indexOf('m') >= 0)
                    minSpan = _tmInc.minute;
                else if (manualformat.indexOf('h') >= 0 || manualformat.indexOf('H'))
                    minSpan = _tmInc.hour;
                else if (manualformat.indexOf('d') >= 0)
                    minSpan = _tmInc.day;
                else if (manualformat.indexOf('M') >= 0)
                    minSpan = _tmInc.month;
                else if (manualformat.indexOf('y') >= 0)
                    minSpan = _tmInc.year;
                return minSpan;
            };
            _timeHelper.getNiceInc = function (tik, ts, mult) {
                for (var i = 0; i < tik.length; i++) {
                    var tikm = tik[i] * mult;
                    if (ts <= tikm)
                        return tikm;
                }
                return 0;
            };
            _timeHelper.NiceTimeSpan = function (ts, manualformat) {
                var minSpan = _tmInc.second;
                if (manualformat != null && manualformat.length > 0)
                    minSpan = _timeHelper.manualTimeInc(manualformat);
                var tsinc = 0;
                var tinc = 0;
                // have the minimum required by format.
                if (minSpan < _tmInc.second) {
                    // alext 10-Jan-2011
                    //if (ts.TotalSeconds < 1.0)
                    if (ts.TotalSeconds < 10.0) 
                    //
                    {
                        tsinc = ts.Ticks;
                        tinc = _timeHelper.TimeSpanFromTmInc(minSpan).Ticks;
                        while (tsinc > 10 * tinc)
                            tinc *= 10;
                        // alext 10-Jan-2011
                        var tinc1 = tinc;
                        if (tsinc > tinc1)
                            tinc1 *= 2;
                        if (tsinc > tinc1)
                            tinc1 = 5 * tinc;
                        if (tsinc > tinc1)
                            tinc1 = 10 * tinc;
                        //
                        return new _timeSpan(tinc1);
                    }
                }
                // alext 25-Jan-06
                // when tsinc < ts the annos are overlapping
                // using larger integer
                // tsinc = (long)ts.TotalSeconds;
                tsinc = Math.ceil(ts.TotalSeconds);
                if (tsinc == 0)
                    return _timeHelper.TimeSpanFromTmInc(minSpan);
                tinc = 1;
                if (minSpan < _tmInc.minute) {
                    if (tsinc < _tmInc.minute) {
                        tinc = _timeHelper.getNiceInc([1, 2, 5, 10, 15, 30], tsinc, minSpan); // alext 11-Mar-11 TimeSpanFromTmInc(minSpan).Ticks  /*(long)minSpan*/); // alext 25-Jan-06 added 2 as 'nice' number
                        if (tinc != 0)
                            return _timeSpan.fromSeconds(tinc);
                    }
                    minSpan = _tmInc.minute;
                }
                if (minSpan < _tmInc.hour) {
                    if (tsinc < _tmInc.hour) {
                        tinc = _timeHelper.getNiceInc([1, 2, 5, 10, 15, 30], tsinc, minSpan); // alext 25-Jan-06 added 2 as 'nice' number
                        if (tinc != 0)
                            return _timeSpan.fromSeconds(tinc);
                    }
                    minSpan = _tmInc.hour;
                }
                if (minSpan < _tmInc.day) {
                    if (tsinc < _tmInc.day) {
                        tinc = _timeHelper.getNiceInc([1, 3, 6, 12], tsinc, minSpan);
                        if (tinc != 0)
                            return _timeSpan.fromSeconds(tinc);
                    }
                    minSpan = _tmInc.day;
                }
                if (minSpan < _tmInc.month) {
                    if (tsinc < _tmInc.month) {
                        tinc = _timeHelper.getNiceInc([1, 2, 7, 14], tsinc, minSpan);
                        if (tinc != 0)
                            return _timeSpan.fromSeconds(tinc);
                    }
                    minSpan = _tmInc.month;
                }
                if (minSpan < _tmInc.year) {
                    if (tsinc < _tmInc.year) {
                        tinc = _timeHelper.getNiceInc([1, 2, 3, 4, 6], tsinc, minSpan);
                        if (tinc != 0)
                            return _timeSpan.fromSeconds(tinc);
                    }
                    minSpan = _tmInc.year;
                }
                // years
                tinc = 100 * _tmInc.year;
                if (tsinc < tinc) {
                    tinc = _timeHelper.getNiceInc([1, 2, 5, 10, 20, 50], tsinc, minSpan);
                    if (tinc == 0)
                        tinc = 100 * _tmInc.year;
                }
                return _timeSpan.fromSeconds(tinc);
            };
            _timeHelper.NiceTimeUnit = function (timeinc, manualformat) {
                var tsRange = _timeSpan.fromDays(timeinc);
                tsRange = _timeHelper.NiceTimeSpan(tsRange, manualformat);
                return tsRange.TotalDays;
            };
            _timeHelper.GetTimeDefaultFormat = function (maxdate, mindate) {
                if (!wijmo.isNumber(maxdate) || !wijmo.isNumber(mindate)) {
                    return '';
                }
                var format = 's';
                var tsRange = _timeSpan.fromSeconds(0.001 * (maxdate - mindate)); //amax.Subtract(amin);
                var range = tsRange.TotalSeconds;
                if (range >= _tmInc.year) {
                    format = 'yyyy';
                }
                else if (range >= _tmInc.month) {
                    format = 'MMM yyyy';
                    //} else if (range > 3 * _tmInc.month) {
                    //    format = 'MMM';
                }
                else if (range >= _tmInc.day) {
                    format = 'MMM d';
                    //} else if (range >= _tmInc.day) {
                    //    format = 'ddd d';
                }
                else if (range >= _tmInc.hour) {
                    format = 'ddd H:mm';
                }
                else if (range >= 0.5 * _tmInc.hour) {
                    format = 'H:mm';
                }
                else if (range >= 1) {
                    format = 'H:mm:ss';
                }
                else if (range > 0) {
                    var ticks = tsRange.Ticks;
                    format = 's' + '.'; //System.Globalization.NumberFormatInfo.CurrentInfo.NumberDecimalSeparator;
                    while (ticks < _timeSpan.TicksPerSecond) {
                        ticks *= 10;
                        format += 'f';
                    }
                }
                return format;
            };
            _timeHelper.secInYear = (24 * 60 * 60);
            return _timeHelper;
        }());
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Represents a plot area on the chart.
         *
         * The chart can have multiple plot areas with multiple axes.
         * To assign axis to plot area use <b>Axis.plotArea</b> property. For example:
         * <pre>
         *  // create a plot area
         *  var pa = new wijmo.chart.PlotArea();
         *  pa.row = 1;
         *  chart.plotAreas.push(pa);
         *  // create auxiliary y-axis
         *  var ay2 = new wijmo.chart.Axis(wijmo.chart.Position.Left);
         *  ay2.plotArea = pa; // attach axis to the plot area
         *  chart.axes.push(ay2);
         *  // plot first series along y-axis
         *  chart.series[0].axisY = ay2;
         * </pre>
         */
        var PlotArea = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:PlotArea class.
             *
             * @param options Initialization options for the plot area.
             */
            function PlotArea(options) {
                this._row = 0;
                this._col = 0;
                this._rect = new wijmo.Rect(0, 0, 0, 0);
                if (options) {
                    wijmo.copy(this, options);
                }
            }
            Object.defineProperty(PlotArea.prototype, "row", {
                /**
                 * Gets or sets the row index of plot area.
                 * This determines the vertical position of the plot area
                 * on the chart.
                 */
                get: function () {
                    return this._row;
                },
                set: function (value) {
                    if (value != this._row) {
                        this._row = wijmo.asInt(value, true, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(PlotArea.prototype, "column", {
                /**
                 * Gets or sets the column index of plot area.
                 * This determines the horizontal position of the plot
                 * area on the chart.
                 */
                get: function () {
                    return this._col;
                },
                set: function (value) {
                    if (value != this._col) {
                        this._col = wijmo.asInt(value, true, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(PlotArea.prototype, "name", {
                /**
                 * Gets or sets the plot area name.
                 */
                get: function () {
                    return this._name;
                },
                set: function (value) {
                    if (value != this._name) {
                        this._name = wijmo.asString(value, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(PlotArea.prototype, "width", {
                /**
                 * Gets or sets width of the plot area.
                 *
                 * The width can be specified as a number (in pixels) or
                 * as a string in the format '{number}*' (star sizing).
                 */
                get: function () {
                    return this._width;
                },
                set: function (value) {
                    if (value != this._width) {
                        this._width = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(PlotArea.prototype, "height", {
                /**
                 * Gets or sets the height of the plot area.
                 *
                 * The height can be specified as a number (in pixels) or
                 * as a string in the format '{number}*' (star sizing).
                 */
                get: function () {
                    return this._height;
                },
                set: function (value) {
                    if (value != this._height) {
                        this._height = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(PlotArea.prototype, "style", {
                /**
                 * Gets or sets the style of the plot area.
                 *
                 * Using <b>style</b> property, you can set appearance of the plot area.
                 * For example:
                 * <pre>
                 *   pa.style = { fill: 'rgba(0,255,0,0.1)' };
                 * </pre>
                 */
                get: function () {
                    return this._style;
                },
                set: function (value) {
                    if (value != this._style) {
                        this._style = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            PlotArea.prototype._invalidate = function () {
                if (this._chart) {
                    this._chart.invalidate();
                }
            };
            PlotArea.prototype._render = function (engine) {
                engine.drawRect(this._rect.left, this._rect.top, this._rect.width, this._rect.height, null, this.style);
            };
            PlotArea.prototype._setPlotX = function (x, w) {
                this._rect.left = x;
                this._rect.width = w;
            };
            PlotArea.prototype._setPlotY = function (y, h) {
                this._rect.top = y;
                this._rect.height = h;
            };
            return PlotArea;
        }());
        chart.PlotArea = PlotArea;
        /**
         * Represents a collection of @see:PlotArea objects in a @see:FlexChartCore control.
         */
        var PlotAreaCollection = /** @class */ (function (_super) {
            __extends(PlotAreaCollection, _super);
            function PlotAreaCollection() {
                return _super !== null && _super.apply(this, arguments) || this;
            }
            /**
             * Gets a plot area by name.
             *
             * @param name The name of the plot area to look for.
             * @return The axis object with the specified name, or null if not found.
             */
            PlotAreaCollection.prototype.getPlotArea = function (name) {
                var index = this.indexOf(name);
                return index > -1 ? this[index] : null;
            };
            /**
             * Gets the index of a plot area by name.
             *
             * @param name The name of the plot area to look for.
             * @return The index of the plot area with the specified name, or -1 if not found.
             */
            PlotAreaCollection.prototype.indexOf = function (name) {
                for (var i = 0; i < this.length; i++) {
                    if (this[i].name == name) {
                        return i;
                    }
                }
                return -1;
            };
            PlotAreaCollection.prototype._getWidth = function (column) {
                var w;
                for (var i = 0; i < this.length; i++) {
                    var pa = this[i];
                    if (pa.column == column && pa.row == 0 /* ? */) {
                        return pa.width;
                    }
                }
                return w;
            };
            PlotAreaCollection.prototype._getHeight = function (row) {
                var w;
                for (var i = 0; i < this.length; i++) {
                    var pa = this[i];
                    if (pa.row == row && pa.column == 0 /* ? */) {
                        return pa.height;
                    }
                }
                return w;
            };
            PlotAreaCollection.prototype._calculateWidths = function (width, ncols) {
                if (ncols <= 0)
                    throw ("ncols");
                var glens = []; // _GridLength[ncols];
                for (var i = 0; i < ncols; i++) {
                    var w = this._getWidth(i);
                    glens[i] = new _GridLength(w);
                }
                return this._calculateLengths(width, ncols, glens);
            };
            PlotAreaCollection.prototype._calculateHeights = function (height, nrows) {
                if (nrows <= 0)
                    throw ("nrows");
                var glens = [];
                for (var i = 0; i < nrows; i++) {
                    var h = this._getHeight(i);
                    glens[i] = new _GridLength(h);
                }
                return this._calculateLengths(height, nrows, glens);
            };
            PlotAreaCollection.prototype._calculateLengths = function (width, ncols, glens) {
                var ws = [ncols];
                var wabs = 0.0;
                var nstars = 0.0;
                for (var i = 0; i < ncols; i++) {
                    if (glens[i].isAbsolute) {
                        ws[i] = glens[i].value;
                        wabs += ws[i];
                    }
                    else if (glens[i].isStar)
                        nstars += glens[i].value;
                    else if (glens[i].isAuto)
                        nstars++;
                }
                var availw = width - wabs;
                var wstar = availw / nstars;
                for (var i = 0; i < ncols; i++) {
                    if (glens[i].isStar)
                        ws[i] = wstar * glens[i].value;
                    else if (glens[i].isAuto)
                        ws[i] = wstar;
                    if (ws[i] < 0)
                        ws[i] = 0;
                }
                return ws;
            };
            return PlotAreaCollection;
        }(wijmo.collections.ObservableArray));
        chart.PlotAreaCollection = PlotAreaCollection;
        var _GridUnitType;
        (function (_GridUnitType) {
            _GridUnitType[_GridUnitType["Auto"] = 0] = "Auto";
            _GridUnitType[_GridUnitType["Pixel"] = 1] = "Pixel";
            _GridUnitType[_GridUnitType["Star"] = 2] = "Star";
        })(_GridUnitType || (_GridUnitType = {}));
        var _GridLength = /** @class */ (function () {
            function _GridLength(s) {
                if (s === void 0) { s = null; }
                this._unitType = _GridUnitType.Auto;
                if (s) {
                    s = s.toString();
                    if (s.indexOf('*') >= 0) {
                        this._unitType = _GridUnitType.Star;
                        s = s.replace('*', '');
                        this._value = parseFloat(s);
                        if (isNaN(this._value)) {
                            this._value = 1;
                        }
                    }
                    else {
                        this._unitType = _GridUnitType.Pixel;
                        this._value = parseFloat(s);
                        if (isNaN(this._value)) {
                            this._unitType = _GridUnitType.Auto;
                            this._value = 1;
                        }
                    }
                }
            }
            Object.defineProperty(_GridLength.prototype, "value", {
                get: function () {
                    return this._value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_GridLength.prototype, "isStar", {
                get: function () {
                    return this._unitType == _GridUnitType.Star;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_GridLength.prototype, "isAbsolute", {
                get: function () {
                    return this._unitType == _GridUnitType.Pixel;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_GridLength.prototype, "isAuto", {
                get: function () {
                    return this._unitType == _GridUnitType.Auto;
                },
                enumerable: true,
                configurable: true
            });
            return _GridLength;
        }());
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Specifies whether and where the Series is visible.
         */
        var SeriesVisibility;
        (function (SeriesVisibility) {
            /** The series is visible on the plot and in the legend. */
            SeriesVisibility[SeriesVisibility["Visible"] = 0] = "Visible";
            /** The series is visible only on the plot. */
            SeriesVisibility[SeriesVisibility["Plot"] = 1] = "Plot";
            /** The series is visible only in the legend. */
            SeriesVisibility[SeriesVisibility["Legend"] = 2] = "Legend";
            /** The series is hidden. */
            SeriesVisibility[SeriesVisibility["Hidden"] = 3] = "Hidden";
        })(SeriesVisibility = chart_1.SeriesVisibility || (chart_1.SeriesVisibility = {}));
        /**
         * Specifies the type of marker to use for the @see:Series.symbolMarker
         * property.
         *
         * Applies to Scatter, LineSymbols, and SplineSymbols chart types.
         */
        var Marker;
        (function (Marker) {
            /**
             * Uses a circle to mark each data point.
             */
            Marker[Marker["Dot"] = 0] = "Dot";
            /**
             * Uses a square to mark each data point.
             */
            Marker[Marker["Box"] = 1] = "Box";
        })(Marker = chart_1.Marker || (chart_1.Marker = {}));
        ;
        var DataArray = /** @class */ (function () {
            function DataArray() {
            }
            return DataArray;
        }());
        chart_1.DataArray = DataArray;
        /**
         * Provides arguments for @see:Series events.
         */
        var SeriesEventArgs = /** @class */ (function (_super) {
            __extends(SeriesEventArgs, _super);
            /**
             * Initializes a new instance of the @see:SeriesEventArgs class.
             *
             * @param series Specifies the @see:Series object affected by this event.
             */
            function SeriesEventArgs(series) {
                var _this = _super.call(this) || this;
                _this._series = wijmo.asType(series, SeriesBase);
                return _this;
            }
            Object.defineProperty(SeriesEventArgs.prototype, "series", {
                /**
                 * Gets the @see:Series object affected by this event.
                 */
                get: function () {
                    return this._series;
                },
                enumerable: true,
                configurable: true
            });
            return SeriesEventArgs;
        }(wijmo.EventArgs));
        chart_1.SeriesEventArgs = SeriesEventArgs;
        /**
         * Represents a series of data points to display in the chart.
         */
        var SeriesBase = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:SeriesBase class.
             *
             * @param options JavaScript object containing initialization data for the object.
             */
            function SeriesBase(options) {
                this._altStyle = null;
                this._symbolMarker = Marker.Dot;
                this._visibility = SeriesVisibility.Visible;
                /**
                 * Occurs when series is rendering.
                 */
                this.rendering = new wijmo.Event();
                /**
                 * Occurs when series is rendered.
                 */
                this.rendered = new wijmo.Event();
                // make sure 'options' is not a host element passed in by directives
                if (options) {
                    this.initialize(options);
                }
            }
            Object.defineProperty(SeriesBase.prototype, "style", {
                //--------------------------------------------------------------------------
                // ** implementation
                /**
                 * Gets or sets the series style.
                 */
                get: function () {
                    return this._style;
                },
                set: function (value) {
                    if (value != this._style) {
                        this._style = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "altStyle", {
                /**
                 * Gets or sets the alternative style for the series. The values from
                 * this property will be used for negative values in Bar, Column,
                 * and Scatter charts; and for rising values in financial chart types
                 * like Candlestick, LineBreak, EquiVolume etc.
                 *
                 * If no value is provided, the default styles will be used.
                 */
                get: function () {
                    return this._altStyle;
                },
                set: function (value) {
                    if (value != this._altStyle) {
                        this._altStyle = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "symbolStyle", {
                /**
                 * Gets or sets the series symbol style.
                 * Applies to Scatter, LineSymbols, and SplineSymbols chart types.
                 */
                get: function () {
                    return this._symbolStyle;
                },
                set: function (value) {
                    if (value != this._symbolStyle) {
                        this._symbolStyle = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "symbolSize", {
                /**
                 * Gets or sets the size (in pixels) of the symbols used to render this @see:Series.
                 * Applies to Scatter, LineSymbols, and SplineSymbols chart types.
                 */
                get: function () {
                    return this._symbolSize;
                },
                set: function (value) {
                    if (value != this._symbolSize) {
                        this._symbolSize = wijmo.asNumber(value, true, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "symbolMarker", {
                /**
                 * Gets or sets the shape of marker to use for each data point in the series.
                 * Applies to Scatter, LineSymbols, and SplineSymbols chart types.
                 */
                get: function () {
                    return this._symbolMarker;
                },
                set: function (value) {
                    value = wijmo.asEnum(value, Marker, true);
                    if (value != this._symbolMarker) {
                        this._symbolMarker = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "binding", {
                /**
                 * Gets or sets the name of the property that contains Y values for the series.
                 */
                get: function () {
                    return this._binding ? this._binding : this._chart ? this._chart.binding : null;
                },
                set: function (value) {
                    if (value != this._binding) {
                        this._binding = wijmo.asString(value, true);
                        this._clearValues();
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "bindingX", {
                /**
                 * Gets or sets the name of the property that contains X values for the series.
                 */
                get: function () {
                    return this._bindingX ? this._bindingX : this._chart ? this._chart.bindingX : null;
                },
                set: function (value) {
                    if (value != this._bindingX) {
                        this._bindingX = wijmo.asString(value, true);
                        this._clearValues();
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "name", {
                /**
                 * Gets or sets the series name.
                 *
                 * The series name is displayed in the chart legend. Any series without a name
                 * does not appear in the legend.
                 */
                get: function () {
                    return this._name;
                },
                set: function (value) {
                    this._name = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "itemsSource", {
                /**
                 * Gets or sets the array or @see:ICollectionView object that contains the series data.
                 */
                get: function () {
                    return this._itemsSource;
                },
                set: function (value) {
                    if (value != this._itemsSource) {
                        // unbind current collection view
                        if (this._cv) {
                            this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this);
                            this._cv = null;
                        }
                        // save new data source and collection view
                        this._itemsSource = value;
                        this._cv = wijmo.asCollectionView(value);
                        // bind new collection view
                        if (this._cv != null) {
                            this._cv.currentChanged.addHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this);
                        }
                        this._clearValues();
                        this._itemsSource = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "collectionView", {
                /**
                 * Gets the @see:ICollectionView object that contains the data for this series.
                 */
                get: function () {
                    return this._cv ? this._cv : this._chart ? this._chart.collectionView : null;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "chart", {
                /**
                 * Gets the @see:FlexChart object that owns this series.
                 */
                get: function () {
                    return this._chart;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "hostElement", {
                /**
                 * Gets the series host element.
                 */
                get: function () {
                    return this._hostElement;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "legendElement", {
                /**
                 * Gets the series element in the legend.
                 */
                get: function () {
                    return this._legendElement;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "cssClass", {
                /**
                 * Gets or sets the series CSS class.
                 */
                get: function () {
                    return this._cssClass;
                },
                set: function (value) {
                    this._cssClass = wijmo.asString(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "visibility", {
                /**
                 * Gets or sets an enumerated value indicating whether and where the series appears.
                 */
                get: function () {
                    return this._visibility;
                },
                set: function (value) {
                    if (value != this._visibility) {
                        this._visibility = wijmo.asEnum(value, SeriesVisibility);
                        this._clearValues();
                        this._invalidate();
                        if (this._chart) {
                            this._chart.onSeriesVisibilityChanged(new SeriesEventArgs(this));
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets a @see:wijmo.chart.HitTestInfo object with information about the specified point.
             *
             * @param pt The point to investigate, in window coordinates.
             * @param y The Y coordinate of the point (if the first parameter is a number).
             */
            SeriesBase.prototype.hitTest = function (pt, y) {
                if (wijmo.isNumber(pt) && wijmo.isNumber(y)) {
                    pt = new wijmo.Point(pt, y);
                }
                else if (pt instanceof MouseEvent) {
                    pt = new wijmo.Point(pt.pageX, pt.pageY);
                }
                wijmo.asType(pt, wijmo.Point);
                if (this._chart) {
                    return this._chart._hitTestSeries(pt, this._chart.series.indexOf(this));
                }
                else {
                    return null;
                }
            };
            /**
             * Gets the plot element that corresponds to the specified point index.
             *
             * @param pointIndex The index of the data point.
             */
            SeriesBase.prototype.getPlotElement = function (pointIndex) {
                if (this.hostElement) {
                    if (pointIndex < this._pointIndexes.length) {
                        var elementIndex = this._pointIndexes[pointIndex];
                        if (elementIndex < this.hostElement.childNodes.length) {
                            return this.hostElement.childNodes[elementIndex];
                        }
                    }
                }
                return null;
            };
            Object.defineProperty(SeriesBase.prototype, "axisX", {
                /**
                 * Gets or sets the x-axis for the series.
                 */
                get: function () {
                    return this._axisX;
                },
                set: function (value) {
                    if (value != this._axisX) {
                        this._axisX = wijmo.asType(value, chart_1.Axis, true);
                        if (this._axisX) {
                            var chart = this._axisX._chart = this._chart;
                            if (chart && chart.axes.indexOf(this._axisX) == -1) {
                                chart.axes.push(this._axisX);
                            }
                        }
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(SeriesBase.prototype, "axisY", {
                /**
                 * Gets or sets the y-axis for the series.
                 */
                get: function () {
                    return this._axisY;
                },
                set: function (value) {
                    if (value != this._axisY) {
                        this._axisY = wijmo.asType(value, chart_1.Axis, true);
                        if (this._axisY) {
                            var chart = this._axisY._chart = this._chart;
                            if (chart && chart.axes.indexOf(this._axisY) == -1) {
                                chart.axes.push(this._axisY);
                            }
                        }
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Initializes the series by copying the properties from a given object.
             *
             * @param options JavaScript object containing initialization data for the series.
             */
            SeriesBase.prototype.initialize = function (options) {
                if (options) {
                    wijmo.copy(this, options);
                }
            };
            /**
             * Converts a @see:Point from control coordinates to series data coordinates.
             *
             * @param pt The point to convert, in control coordinates.
             * @return The point in series data coordinates.
             */
            SeriesBase.prototype.pointToData = function (pt) {
                wijmo.asType(pt, wijmo.Point);
                pt = pt.clone();
                pt.x = this._getAxisX().convertBack(pt.x);
                pt.y = this._getAxisY().convertBack(pt.y);
                return pt;
            };
            /**
             * Converts a @see:Point from series data coordinates to control coordinates.
             *
             * @param pt @see:Point in series data coordinates.
             * @return The @see:Point in control coordinates.
             */
            SeriesBase.prototype.dataToPoint = function (pt) {
                wijmo.asType(pt, wijmo.Point);
                pt = pt.clone();
                pt.x = this._getAxisX().convert(pt.x);
                pt.y = this._getAxisY().convert(pt.y);
                return pt;
            };
            /**
             * Raises the @see:rendering event.
             *
             * @param engine The @see:IRenderEngine object used to render the series.
             * @param index The index of the series to render.
             * @param count Total number of the series to render.
             */
            SeriesBase.prototype.onRendering = function (engine, index, count) {
                var args = new chart_1.SeriesRenderingEventArgs(engine, index, count);
                this.rendering.raise(this, args);
                return args.cancel;
            };
            /**
             * Raises the @see:rendered event.
             *
             * @param engine The @see:IRenderEngine object used to render the series.
             */
            SeriesBase.prototype.onRendered = function (engine) {
                this.rendered.raise(this, new chart_1.RenderEventArgs(engine));
            };
            Object.defineProperty(SeriesBase.prototype, "_chart", {
                // ** private stuff
                get: function () {
                    return this.__chart;
                },
                set: function (value) {
                    if (value !== this.__chart) {
                        this.__chart = value;
                    }
                },
                enumerable: true,
                configurable: true
            });
            SeriesBase.prototype._getSymbolSize = function () {
                return this.symbolSize != null ? this.symbolSize : this.chart.symbolSize;
            };
            Object.defineProperty(SeriesBase.prototype, "_plotter", {
                get: function () {
                    if (this.chart && !this.__plotter) {
                        this.__plotter = this.chart._getPlotter(this);
                    }
                    return this.__plotter;
                },
                set: function (value) {
                    if (value != this.__plotter) {
                        this.__plotter = value;
                    }
                },
                enumerable: true,
                configurable: true
            });
            //--------------------------------------------------------------------------
            // ** implementation
            SeriesBase.prototype.getDataType = function (dim) {
                if (dim == 0) {
                    return this._valueDataType;
                }
                else if (dim == 1) {
                    return this._xvalueDataType;
                }
                return null;
            };
            SeriesBase.prototype.getValues = function (dim) {
                if (dim == 0) {
                    if (this._values == null) {
                        this._valueDataType = null;
                        if (this._cv != null) {
                            var da = this._bindValues(this._cv.items, this._getBinding(0));
                            this._values = da.values;
                            this._valueDataType = da.dataType;
                        }
                        else if (this.binding != null) {
                            if (this._chart != null && this._chart.collectionView != null) {
                                var da = this._bindValues(this._chart.collectionView.items, this._getBinding(0));
                                this._values = da.values;
                                this._valueDataType = da.dataType;
                            }
                        }
                    }
                    return this._values;
                }
                else if (dim == 1) {
                    if (this._xvalues == null) {
                        this._xvalueDataType = null;
                        var base = this;
                        if (this.bindingX != null) {
                            if (base._cv != null) {
                                var da = this._bindValues(base._cv.items, this.bindingX, true);
                                this._xvalueDataType = da.dataType;
                                this._xvalues = da.values;
                            }
                            else {
                                if (this._bindingX == null) {
                                    return null;
                                }
                                if (base._chart != null && base._chart.collectionView != null) {
                                    var da = this._bindValues(base._chart.collectionView.items, this.bindingX, true);
                                    this._xvalueDataType = da.dataType;
                                    this._xvalues = da.values;
                                }
                            }
                        }
                    }
                    return this._xvalues;
                }
                return null;
            };
            /**
             * Draw a legend item at the specified position.
             *
             * @param engine The rendering engine to use.
             * @param rect The position of the legend item.
             * @param index Index of legend item(for series with multiple legend items).
             */
            SeriesBase.prototype.drawLegendItem = function (engine, rect, index) {
                var chartType = this._getChartType();
                if (chartType == null) {
                    chartType = this._chart._getChartType();
                }
                if (chartType === chart_1.ChartType.Funnel) {
                    this._drawFunnelLegendItem(engine, rect, index, this.style, this.symbolStyle);
                }
                else {
                    this._drawLegendItem(engine, rect, chartType, this.name, this.style, this.symbolStyle);
                }
            };
            /**
             * Measures height and width of the legend item.
             *
             * @param engine The rendering engine to use.
             * @param index Index of legend item(for series with multiple legend items).
             */
            SeriesBase.prototype.measureLegendItem = function (engine, index) {
                var chartType = this._getChartType();
                if (chartType == null) {
                    chartType = this._chart._getChartType();
                }
                if (chartType === chart_1.ChartType.Funnel) {
                    return this._measureLegendItem(engine, this._getFunnelLegendName(index));
                }
                else {
                    return this._measureLegendItem(engine, this.name);
                }
            };
            /**
             * Returns number of series items in the legend.
             */
            SeriesBase.prototype.legendItemLength = function () {
                var chartType = this._getChartType();
                if (chartType == null) {
                    chartType = this._chart._getChartType();
                }
                if (chartType === chart_1.ChartType.Funnel) {
                    if (this._chart._xlabels && this._chart._xlabels.length) {
                        return this._chart._xlabels.length;
                    }
                    return 1;
                }
                else {
                    return 1;
                }
            };
            /**
             * Returns the series bounding rectangle in data coordinates.
             *
             * If getDataRect() returns null, the limits are calculated automatically based on the data values.
             *
             * @param currentRect The current rectangle of chart. This parameter is optional.
             * @param calculatedRect The calculated rectangle of chart. This parameter is optional.
             */
            SeriesBase.prototype.getDataRect = function (currentRect, calculatedRect) {
                return null;
            };
            SeriesBase.prototype._getChartType = function () {
                return this._chartType;
            };
            /**
             * Clears any cached data values.
             */
            SeriesBase.prototype._clearValues = function () {
                this._values = null;
                this._xvalues = null;
                this.__plotter = null;
            };
            SeriesBase.prototype._getBinding = function (index) {
                var binding = this.binding;
                if (binding) {
                    var sep = this.chart ? this.chart._bindingSeparator : ',';
                    if (sep) {
                        var props = binding.split(sep);
                        if (props && props.length > index) {
                            binding = props[index].trim();
                        }
                    }
                }
                return binding;
            };
            SeriesBase.prototype._getBindingValues = function (index) {
                var items;
                if (this._cv != null) {
                    items = this._cv.items;
                }
                else if (this._chart != null && this._chart.collectionView != null) {
                    items = this._chart.collectionView.items;
                }
                var da = this._bindValues(items, this._getBinding(index));
                return da.values;
            };
            SeriesBase.prototype._getItem = function (pointIndex) {
                var item = null;
                var items = null;
                if (this.itemsSource != null) {
                    if (this._cv != null)
                        items = this._cv.items;
                    else
                        items = this.itemsSource;
                }
                else if (this._chart.itemsSource != null) {
                    if (this._chart.collectionView != null) {
                        items = this._chart.collectionView.items;
                    }
                    else {
                        items = this._chart.itemsSource;
                    }
                }
                if (items != null) {
                    item = items[pointIndex];
                }
                return item;
            };
            SeriesBase.prototype._getLength = function () {
                var len = 0;
                var items = null;
                if (this.itemsSource != null) {
                    if (this._cv != null)
                        items = this._cv.items;
                    else
                        items = this.itemsSource;
                }
                else if (this._chart.itemsSource != null) {
                    if (this._chart.collectionView != null) {
                        items = this._chart.collectionView.items;
                    }
                    else {
                        items = this._chart.itemsSource;
                    }
                }
                if (items != null) {
                    len = items.length;
                }
                return len;
            };
            SeriesBase.prototype._setPointIndex = function (pointIndex, elementIndex) {
                this._pointIndexes[pointIndex] = elementIndex;
            };
            SeriesBase.prototype._getDataRect = function () {
                var values = this.getValues(0);
                var xvalues = this.getValues(1);
                if (values) {
                    var xmin = NaN, ymin = NaN, xmax = NaN, ymax = NaN;
                    var len = values.length;
                    for (var i = 0; i < len; i++) {
                        var val = values[i];
                        if (isFinite(val)) {
                            if (isNaN(ymin)) {
                                ymin = ymax = val;
                            }
                            else {
                                if (val < ymin) {
                                    ymin = val;
                                }
                                else if (val > ymax) {
                                    ymax = val;
                                }
                            }
                        }
                        if (xvalues) {
                            var xval = xvalues[i];
                            if (isFinite(xval)) {
                                if (isNaN(xmin)) {
                                    xmin = xmax = xval;
                                }
                                else {
                                    if (xval < xmin) {
                                        xmin = xval;
                                    }
                                    else if (val > ymax) {
                                        xmax = xval;
                                    }
                                }
                            }
                        }
                    }
                    if (!xvalues) {
                        xmin = 0;
                        xmax = len - 1;
                    }
                    if (!isNaN(ymin)) {
                        return new wijmo.Rect(xmin, ymin, xmax - xmin, ymax - ymin);
                    }
                }
                return null;
            };
            SeriesBase.prototype._isCustomAxisX = function () {
                if (this._axisX) {
                    if (this._chart) {
                        return this._axisX != this.chart.axisX;
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return false;
                }
            };
            SeriesBase.prototype._isCustomAxisY = function () {
                if (this._axisY) {
                    if (this._chart) {
                        return this._axisY != this.chart.axisY;
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return false;
                }
            };
            SeriesBase.prototype._getAxisX = function () {
                var ax = null;
                if (this.axisX) {
                    ax = this.axisX;
                }
                else if (this.chart) {
                    ax = this.chart.axisX;
                }
                return ax;
            };
            SeriesBase.prototype._getAxisY = function () {
                var ay = null;
                if (this.axisY) {
                    ay = this.axisY;
                }
                else if (this.chart) {
                    ay = this.chart.axisY;
                }
                return ay;
            };
            SeriesBase.prototype._measureLegendItem = function (engine, text) {
                var sz = new wijmo.Size();
                sz.width = chart_1.Series._LEGEND_ITEM_WIDTH;
                sz.height = chart_1.Series._LEGEND_ITEM_HEIGHT;
                if (this._name) {
                    var tsz = engine.measureString(text, chart_1.FlexChart._CSS_LABEL, chart_1.FlexChart._CSS_LEGEND);
                    sz.width += tsz.width;
                    if (sz.height < tsz.height) {
                        sz.height = tsz.height;
                    }
                }
                ;
                sz.width += 3 * chart_1.Series._LEGEND_ITEM_MARGIN;
                sz.height += 2 * chart_1.Series._LEGEND_ITEM_MARGIN;
                return sz;
            };
            SeriesBase.prototype._drawFunnelLegendItem = function (engine, rect, index, style, symbolStyle) {
                engine.strokeWidth = 1;
                var marg = chart_1.Series._LEGEND_ITEM_MARGIN;
                var fill = null;
                var stroke = null;
                if (fill === null)
                    fill = this._chart._getColorLight(index);
                if (stroke === null)
                    stroke = this._chart._getColor(index);
                engine.fill = fill;
                engine.stroke = stroke;
                var yc = rect.top + 0.5 * rect.height;
                var wsym = chart_1.Series._LEGEND_ITEM_WIDTH;
                var hsym = chart_1.Series._LEGEND_ITEM_HEIGHT;
                var name = this._getFunnelLegendName(index);
                engine.drawRect(rect.left + marg, yc - 0.5 * hsym, wsym, hsym, null, symbolStyle ? symbolStyle : style);
                if (name) {
                    chart_1.FlexChart._renderText(engine, name, new wijmo.Point(rect.left + hsym + 2 * marg, yc), 0, 1, chart_1.FlexChart._CSS_LABEL);
                }
            };
            SeriesBase.prototype._getFunnelLegendName = function (index) {
                var name;
                if (this._chart._xlabels && this._chart._xlabels.length) {
                    name = this._chart._xlabels[index];
                }
                if (!name) {
                    name = this.name;
                }
                return name;
            };
            SeriesBase.prototype._drawLegendItem = function (engine, rect, chartType, text, style, symbolStyle) {
                engine.strokeWidth = 1;
                var marg = chart_1.Series._LEGEND_ITEM_MARGIN;
                var fill = null;
                var stroke = null;
                if (fill === null)
                    fill = this._chart._getColorLight(this._chart.series.indexOf(this));
                if (stroke === null)
                    stroke = this._chart._getColor(this._chart.series.indexOf(this));
                engine.fill = fill;
                engine.stroke = stroke;
                var yc = rect.top + 0.5 * rect.height;
                var wsym = chart_1.Series._LEGEND_ITEM_WIDTH;
                var hsym = chart_1.Series._LEGEND_ITEM_HEIGHT;
                switch (chartType) {
                    case chart_1.ChartType.Area:
                    case chart_1.ChartType.SplineArea:
                        {
                            engine.drawRect(rect.left + marg, yc - 0.5 * hsym, wsym, hsym, null, style);
                        }
                        break;
                    case chart_1.ChartType.Bar:
                    case chart_1.ChartType.Column:
                        {
                            engine.drawRect(rect.left + marg, yc - 0.5 * hsym, wsym, hsym, null, symbolStyle ? symbolStyle : style);
                        }
                        break;
                    case chart_1.ChartType.Scatter:
                    case chart_1.ChartType.Bubble:
                        {
                            var rx = 0.3 * wsym;
                            var ry = 0.3 * hsym;
                            if (this.symbolMarker == Marker.Box) {
                                engine.drawRect(rect.left + marg + 0.5 * wsym - rx, yc - ry, 2 * rx, 2 * ry, null, symbolStyle ? symbolStyle : style);
                            }
                            else {
                                engine.drawEllipse(rect.left + 0.5 * wsym + marg, yc, rx, ry, null, symbolStyle ? symbolStyle : style);
                            }
                        }
                        break;
                    case chart_1.ChartType.Line:
                    case chart_1.ChartType.Spline:
                        {
                            engine.drawLine(rect.left + marg, yc, rect.left + wsym + marg, yc, null, style);
                        }
                        break;
                    case chart_1.ChartType.LineSymbols:
                    case chart_1.ChartType.SplineSymbols:
                        {
                            var rx = 0.3 * wsym;
                            var ry = 0.3 * hsym;
                            if (this.symbolMarker == Marker.Box) {
                                engine.drawRect(rect.left + marg + 0.5 * wsym - rx, yc - ry, 2 * rx, 2 * ry, null, symbolStyle ? symbolStyle : style);
                            }
                            else {
                                engine.drawEllipse(rect.left + 0.5 * wsym + marg, yc, rx, ry, null, symbolStyle ? symbolStyle : style);
                            }
                            engine.drawLine(rect.left + marg, yc, rect.left + wsym + marg, yc, null, style);
                        }
                        break;
                    case chart_1.ChartType.Candlestick:
                    case chart_1.ChartType.HighLowOpenClose:
                        {
                            engine.drawLine(rect.left + marg, yc, rect.left + wsym + marg, yc, null, symbolStyle ? symbolStyle : style);
                        }
                        break;
                }
                if (this._name) {
                    chart_1.FlexChart._renderText(engine, text, new wijmo.Point(rect.left + hsym + 2 * marg, yc), 0, 1, chart_1.FlexChart._CSS_LABEL);
                }
            };
            SeriesBase.prototype._cvCollectionChanged = function (sender, e) {
                this._clearValues();
                this._invalidate();
            };
            // updates selection to sync with data source
            SeriesBase.prototype._cvCurrentChanged = function (sender, e) {
                if (this._chart && this._chart._notifyCurrentChanged) {
                    this._invalidate();
                }
            };
            SeriesBase.prototype._bindValues = function (items, binding, isX) {
                if (isX === void 0) { isX = false; }
                var values;
                var dataType;
                var arrVal;
                if (items != null) {
                    var len = items.length;
                    values = new Array(items.length);
                    for (var i = 0; i < len; i++) {
                        arrVal = null;
                        var val = items[i];
                        if (binding != null) {
                            val = val[binding];
                        }
                        if (wijmo.isArray(val) && val.length > 0) {
                            arrVal = val;
                            val = val[0];
                        }
                        if (wijmo.isNumber(val)) {
                            values[i] = val;
                            dataType = wijmo.DataType.Number;
                        }
                        else if (wijmo.isDate(val)) {
                            values[i] = val.valueOf();
                            dataType = wijmo.DataType.Date;
                        }
                        else if (isX && val) {
                            // most likely it's category axis
                            // return appropriate values
                            values[i] = i;
                            dataType = wijmo.DataType.Number;
                        }
                        if (wijmo.isArray(arrVal) && arrVal.length > 0) {
                            values[i] = arrVal;
                        }
                    }
                }
                var darr = new DataArray();
                darr.values = values;
                darr.dataType = dataType;
                return darr;
            };
            SeriesBase.prototype._invalidate = function () {
                if (this._chart) {
                    this._chart.invalidate();
                }
            };
            SeriesBase.prototype._indexToPoint = function (pointIndex) {
                if (pointIndex >= 0 && pointIndex < this._values.length) {
                    var y = this._values[pointIndex];
                    var x = this._xvalues ? this._xvalues[pointIndex] : pointIndex;
                    return new wijmo.Point(x, y);
                }
                return null;
            };
            SeriesBase.prototype._getSymbolFill = function (seriesIndex) {
                var fill = null;
                if (this.symbolStyle) {
                    fill = this.symbolStyle.fill;
                }
                if (!fill && this.style) {
                    fill = this.style.fill;
                }
                if (!fill && this.chart) {
                    fill = this.chart._getColorLight(seriesIndex);
                }
                return fill;
            };
            SeriesBase.prototype._getSymbolStroke = function (seriesIndex) {
                var stroke = null;
                if (this.symbolStyle) {
                    stroke = this.symbolStyle.stroke;
                }
                if (!stroke && this.style) {
                    stroke = this.style.stroke;
                }
                if (!stroke && this.chart) {
                    stroke = this.chart._getColor(seriesIndex);
                }
                return stroke;
            };
            // convenience method to return symbol stroke value from
            // the altStyle property
            SeriesBase.prototype._getAltSymbolStroke = function (seriesIndex) {
                var stroke = null;
                if (this.altStyle) {
                    stroke = this.altStyle.stroke;
                }
                return stroke;
            };
            // convenience method to return symbol fill value from
            // the altStyle property
            SeriesBase.prototype._getAltSymbolFill = function (seriesIndex) {
                var fill = null;
                if (this.altStyle) {
                    fill = this.altStyle.fill;
                }
                return fill;
            };
            SeriesBase.prototype._renderLabels = function (engine, smap, chart, lblAreas) {
                if (!this._plotter) {
                    return;
                }
                this._plotter._renderLabels(engine, this, smap, chart, lblAreas);
            };
            SeriesBase._LEGEND_ITEM_WIDTH = 10;
            SeriesBase._LEGEND_ITEM_HEIGHT = 10;
            SeriesBase._LEGEND_ITEM_MARGIN = 4;
            SeriesBase._DEFAULT_SYM_SIZE = 10;
            return SeriesBase;
        }());
        chart_1.SeriesBase = SeriesBase;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Represents a series of data points to display in the chart.
         *
         * The @see:Series class supports all basic chart types. You may define
         * a different chart type on each @see:Series object that you add to the
         * @see:FlexChart series collection. This overrides the @see:chartType
         * property set on the chart that is the default for all @see:Series objects
         * in its collection.
         */
        var Series = /** @class */ (function (_super) {
            __extends(Series, _super);
            function Series() {
                return _super !== null && _super.apply(this, arguments) || this;
            }
            Object.defineProperty(Series.prototype, "chartType", {
                /**
                 * Gets or sets the chart type for a specific series, overriding the chart type
                 * set on the overall chart.
                 */
                get: function () {
                    return this._chartType;
                },
                set: function (value) {
                    if (value != this._chartType) {
                        this._chartType = wijmo.asEnum(value, chart.ChartType, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            return Series;
        }(chart.SeriesBase));
        chart.Series = Series;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        'use strict';
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Render to svg.
         */
        var _SvgRenderEngine = /** @class */ (function () {
            function _SvgRenderEngine(element) {
                this._strokeWidth = 1;
                this._fontSize = null;
                this._fontFamily = null;
                this._savedGradient = {};
                this._element = element;
                this._create();
                this._element.appendChild(this._svg);
                if (_SvgRenderEngine._isff === undefined) {
                    _SvgRenderEngine._isff = navigator.userAgent.toLowerCase().indexOf('firefox') >= 0;
                }
            }
            _SvgRenderEngine.prototype.beginRender = function () {
                while (this._svg.firstChild) {
                    wijmo.removeChild(this._svg.firstChild);
                }
                this._savedGradient = {};
                this._svg.appendChild(this._defs);
                this._svg.appendChild(this._textGroup);
            };
            _SvgRenderEngine.prototype.endRender = function () {
                wijmo.removeChild(this._textGroup);
            };
            _SvgRenderEngine.prototype.setViewportSize = function (w, h) {
                this._svg.setAttribute('width', w.toString());
                this._svg.setAttribute('height', h.toString());
            };
            Object.defineProperty(_SvgRenderEngine.prototype, "element", {
                get: function () {
                    return this._svg;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "fill", {
                get: function () {
                    return this._fill;
                },
                set: function (value) {
                    this._fill = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "fontSize", {
                get: function () {
                    return this._fontSize;
                },
                set: function (value) {
                    this._fontSize = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "fontFamily", {
                get: function () {
                    return this._fontFamily;
                },
                set: function (value) {
                    this._fontFamily = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "stroke", {
                get: function () {
                    return this._stroke;
                },
                set: function (value) {
                    this._stroke = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "strokeWidth", {
                get: function () {
                    return this._strokeWidth;
                },
                set: function (value) {
                    this._strokeWidth = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_SvgRenderEngine.prototype, "textFill", {
                get: function () {
                    return this._textFill;
                },
                set: function (value) {
                    this._textFill = value;
                },
                enumerable: true,
                configurable: true
            });
            _SvgRenderEngine.prototype.addClipRect = function (clipRect, id) {
                if (clipRect && id) {
                    var clipPath = document.createElementNS(_SvgRenderEngine.svgNS, 'clipPath');
                    var rect = document.createElementNS(_SvgRenderEngine.svgNS, 'rect');
                    rect.setAttribute('x', (clipRect.left - 1).toFixed());
                    rect.setAttribute('y', (clipRect.top - 1).toFixed());
                    rect.setAttribute('width', (clipRect.width + 2).toFixed());
                    rect.setAttribute('height', (clipRect.height + 2).toFixed());
                    clipPath.appendChild(rect);
                    clipPath.setAttribute('id', id);
                    this._svg.appendChild(clipPath);
                    //this._defs.appendChild(clipPath);
                }
            };
            _SvgRenderEngine.prototype.drawEllipse = function (cx, cy, rx, ry, className, style) {
                var ell = document.createElementNS(_SvgRenderEngine.svgNS, 'ellipse');
                this._applyColor(ell, 'stroke', this._stroke);
                //ell.setAttribute('stroke', this._stroke);
                if (this._strokeWidth !== null) {
                    ell.setAttribute('stroke-width', this._strokeWidth.toString());
                }
                this._applyColor(ell, 'fill', this._fill);
                //ell.setAttribute('fill', this._fill);
                ell.setAttribute('cx', cx.toFixed(1));
                ell.setAttribute('cy', cy.toFixed(1));
                ell.setAttribute('rx', rx.toFixed(1));
                ell.setAttribute('ry', ry.toFixed(1));
                //ell.setAttribute('cx', cx.toString());
                //ell.setAttribute('cy', cy.toString());
                //ell.setAttribute('rx', rx.toString());
                //ell.setAttribute('ry', ry.toString());
                if (className) {
                    ell.setAttribute('class', className);
                }
                this._applyStyle(ell, style);
                //this._svg.appendChild(ell);
                this._appendChild(ell);
                return ell;
            };
            _SvgRenderEngine.prototype.drawRect = function (x, y, w, h, className, style, clipPath) {
                var rect = document.createElementNS(_SvgRenderEngine.svgNS, 'rect');
                this._applyColor(rect, 'fill', this._fill);
                this._applyColor(rect, 'stroke', this._stroke);
                //rect.setAttribute('fill', this._fill);
                //rect.setAttribute('stroke', this._stroke);
                if (this._strokeWidth !== null) {
                    rect.setAttribute('stroke-width', this._strokeWidth.toString());
                }
                rect.setAttribute('x', x.toFixed(1));
                rect.setAttribute('y', y.toFixed(1));
                if (w > 0 && w < 0.05) {
                    rect.setAttribute('width', '0.1');
                }
                else {
                    rect.setAttribute('width', w.toFixed(1));
                }
                if (h > 0 && h < 0.05) {
                    rect.setAttribute('height', '0.1');
                }
                else {
                    rect.setAttribute('height', h.toFixed(1));
                }
                if (clipPath) {
                    rect.setAttribute('clip-path', 'url(#' + clipPath + ')');
                }
                if (className) {
                    rect.setAttribute('class', className);
                }
                this._applyStyle(rect, style);
                this._appendChild(rect);
                return rect;
            };
            _SvgRenderEngine.prototype.drawLine = function (x1, y1, x2, y2, className, style) {
                var line = document.createElementNS(_SvgRenderEngine.svgNS, 'line');
                this._applyColor(line, 'stroke', this._stroke);
                //line.setAttribute('stroke', this._stroke);
                if (this._strokeWidth !== null) {
                    line.setAttribute('stroke-width', this._strokeWidth.toString());
                }
                line.setAttribute('x1', x1.toFixed(1));
                line.setAttribute('x2', x2.toFixed(1));
                line.setAttribute('y1', y1.toFixed(1));
                line.setAttribute('y2', y2.toFixed(1));
                //line.setAttribute('x1', x1.toString());
                //line.setAttribute('x2', x2.toString());
                //line.setAttribute('y1', y1.toString());
                //line.setAttribute('y2', y2.toString());
                if (className) {
                    line.setAttribute('class', className);
                }
                this._applyStyle(line, style);
                this._appendChild(line);
                return line;
            };
            _SvgRenderEngine.prototype.drawLines = function (xs, ys, className, style, clipPath) {
                if (xs && ys) {
                    var len = Math.min(xs.length, ys.length);
                    if (len > 0) {
                        var pline = document.createElementNS(_SvgRenderEngine.svgNS, 'polyline');
                        this._applyColor(pline, 'stroke', this._stroke);
                        //pline.setAttribute('stroke', this._stroke);
                        if (this._strokeWidth !== null) {
                            pline.setAttribute('stroke-width', this._strokeWidth.toString());
                        }
                        pline.setAttribute('fill', 'none');
                        var spts = '';
                        for (var i = 0; i < len; i++) {
                            spts += xs[i].toFixed(1) + ',' + ys[i].toFixed(1) + ' ';
                            //spts += xs[i].toString() + ',' + ys[i].toString() + ' ';
                        }
                        pline.setAttribute('points', spts);
                        if (className) {
                            pline.setAttribute('class', className);
                        }
                        if (clipPath) {
                            pline.setAttribute('clip-path', 'url(#' + clipPath + ')');
                        }
                        this._applyStyle(pline, style);
                        this._appendChild(pline);
                        return pline;
                    }
                }
                return null;
            };
            _SvgRenderEngine.prototype.drawSplines = function (xs, ys, className, style, clipPath) {
                if (xs && ys) {
                    var spline = new chart._Spline(xs, ys);
                    var s = spline.calculate();
                    var sx = s.xs;
                    var sy = s.ys;
                    var len = Math.min(sx.length, sy.length);
                    if (len > 0) {
                        var pline = document.createElementNS(_SvgRenderEngine.svgNS, 'polyline');
                        this._applyColor(pline, 'stroke', this._stroke);
                        //pline.setAttribute('stroke', this._stroke);
                        if (this._strokeWidth !== null) {
                            pline.setAttribute('stroke-width', this._strokeWidth.toString());
                        }
                        pline.setAttribute('fill', 'none');
                        var spts = '';
                        for (var i = 0; i < len; i++) {
                            spts += sx[i].toFixed(1) + ',' + sy[i].toFixed(1) + ' ';
                        }
                        pline.setAttribute('points', spts);
                        if (className) {
                            pline.setAttribute('class', className);
                        }
                        if (clipPath) {
                            pline.setAttribute('clip-path', 'url(#' + clipPath + ')');
                        }
                        this._applyStyle(pline, style);
                        this._appendChild(pline);
                        return pline;
                    }
                }
                return null;
            };
            _SvgRenderEngine.prototype.drawPolygon = function (xs, ys, className, style, clipPath) {
                if (xs && ys) {
                    var len = Math.min(xs.length, ys.length);
                    if (len > 0) {
                        var poly = document.createElementNS(_SvgRenderEngine.svgNS, 'polygon');
                        this._applyColor(poly, 'stroke', this._stroke);
                        //poly.setAttribute('stroke', this._stroke);
                        if (this._strokeWidth !== null) {
                            poly.setAttribute('stroke-width', this._strokeWidth.toString());
                        }
                        this._applyColor(poly, 'fill', this._fill);
                        //poly.setAttribute('fill', this._fill);
                        var spts = '';
                        for (var i = 0; i < len; i++) {
                            //spts += xs[i].toString() + ',' + ys[i].toString() + ' ';
                            spts += xs[i].toFixed(1) + ',' + ys[i].toFixed(1) + ' ';
                        }
                        poly.setAttribute('points', spts);
                        if (className) {
                            poly.setAttribute('class', className);
                        }
                        if (clipPath) {
                            poly.setAttribute('clip-path', 'url(#' + clipPath + ')');
                        }
                        this._applyStyle(poly, style);
                        this._appendChild(poly);
                        return poly;
                    }
                }
                return null;
            };
            _SvgRenderEngine.prototype.drawPieSegment = function (cx, cy, r, startAngle, sweepAngle, className, style, clipPath) {
                if (sweepAngle >= Math.PI * 2) {
                    return this.drawEllipse(cx, cy, r, r, className, style);
                }
                var path = document.createElementNS(_SvgRenderEngine.svgNS, 'path');
                this._applyColor(path, 'fill', this._fill);
                this._applyColor(path, 'stroke', this._stroke);
                //path.setAttribute('fill', this._fill);
                //path.setAttribute('stroke', this._stroke);
                if (this._strokeWidth !== null) {
                    path.setAttribute('stroke-width', this._strokeWidth.toString());
                }
                var p1 = new wijmo.Point(cx, cy);
                p1.x += r * Math.cos(startAngle);
                p1.y += r * Math.sin(startAngle);
                var a2 = startAngle + sweepAngle;
                var p2 = new wijmo.Point(cx, cy);
                p2.x += r * Math.cos(a2);
                p2.y += r * Math.sin(a2);
                var opt = ' 0 0,1 ';
                if (Math.abs(sweepAngle) > Math.PI) {
                    opt = ' 0 1,1 ';
                }
                //var d = 'M ' + cx.toFixed(1) + ',' + cy.toFixed(1);
                //d += ' L ' + p1.x.toFixed(1) + ',' + p1.y.toFixed(1);
                //d += ' A ' + r.toFixed(1) + ',' + r.toFixed(1) + opt;
                //d += p2.x.toFixed(1) + ',' + p2.y.toFixed(1) + ' z';
                var d = 'M ' + p1.x.toFixed(1) + ',' + p1.y.toFixed(1);
                d += ' A ' + r.toFixed(1) + ',' + r.toFixed(1) + opt;
                d += p2.x.toFixed(1) + ',' + p2.y.toFixed(1);
                d += ' L ' + cx.toFixed(1) + ',' + cy.toFixed(1) + ' z';
                path.setAttribute('d', d);
                if (clipPath) {
                    path.setAttribute('clip-path', 'url(#' + clipPath + ')');
                }
                if (className) {
                    path.setAttribute('class', className);
                }
                this._applyStyle(path, style);
                this._appendChild(path);
                return path;
            };
            _SvgRenderEngine.prototype.drawDonutSegment = function (cx, cy, radius, innerRadius, startAngle, sweepAngle, className, style, clipPath) {
                var isFull = false;
                if (sweepAngle >= Math.PI * 2) {
                    isFull = true;
                    sweepAngle -= 0.001;
                }
                var path = document.createElementNS(_SvgRenderEngine.svgNS, 'path');
                this._applyColor(path, 'fill', this._fill);
                this._applyColor(path, 'stroke', this._stroke);
                //path.setAttribute('fill', this._fill);
                //path.setAttribute('stroke', this._stroke);
                if (this._strokeWidth !== null) {
                    path.setAttribute('stroke-width', this._strokeWidth.toString());
                }
                var p1 = new wijmo.Point(cx, cy);
                p1.x += radius * Math.cos(startAngle);
                p1.y += radius * Math.sin(startAngle);
                var a2 = startAngle + sweepAngle;
                var p2 = new wijmo.Point(cx, cy);
                p2.x += radius * Math.cos(a2);
                p2.y += radius * Math.sin(a2);
                var p3 = new wijmo.Point(cx, cy);
                p3.x += innerRadius * Math.cos(a2);
                p3.y += innerRadius * Math.sin(a2);
                var p4 = new wijmo.Point(cx, cy);
                p4.x += innerRadius * Math.cos(startAngle);
                p4.y += innerRadius * Math.sin(startAngle);
                var opt1 = ' 0 0,1 ', opt2 = ' 0 0,0 ';
                if (Math.abs(sweepAngle) > Math.PI) {
                    opt1 = ' 0 1,1 ';
                    opt2 = ' 0 1,0 ';
                }
                var d = 'M ' + p1.x.toFixed(3) + ',' + p1.y.toFixed(3);
                d += ' A ' + radius.toFixed(3) + ',' + radius.toFixed(3) + opt1;
                d += p2.x.toFixed(3) + ',' + p2.y.toFixed(3);
                if (isFull) {
                    d += ' M ' + p3.x.toFixed(3) + ',' + p3.y.toFixed(3);
                }
                else {
                    d += ' L ' + p3.x.toFixed(3) + ',' + p3.y.toFixed(3);
                }
                d += ' A ' + innerRadius.toFixed(3) + ',' + innerRadius.toFixed(3) + opt2;
                d += p4.x.toFixed(3) + ',' + p4.y.toFixed(3);
                if (!isFull) {
                    d += ' z';
                }
                path.setAttribute('d', d);
                if (clipPath) {
                    path.setAttribute('clip-path', 'url(#' + clipPath + ')');
                }
                if (className) {
                    path.setAttribute('class', className);
                }
                this._applyStyle(path, style);
                this._appendChild(path);
                return path;
            };
            _SvgRenderEngine.prototype.drawString = function (s, pt, className, style) {
                var text = this._createText(pt, s);
                if (className) {
                    text.setAttribute('class', className);
                }
                this._applyStyle(text, style);
                this._appendChild(text);
                var bb = this._getBBox(text); // text.getBBox();
                text.setAttribute('y', (pt.y - (bb.y + bb.height - pt.y)).toFixed(1));
                return text;
            };
            _SvgRenderEngine.prototype.drawStringRotated = function (s, pt, center, angle, className, style) {
                var text = this._createText(pt, s);
                if (className) {
                    text.setAttribute('class', className);
                }
                this._applyStyle(text, style);
                var g = document.createElementNS(_SvgRenderEngine.svgNS, 'g');
                g.setAttribute('transform', 'rotate(' + angle.toFixed(1) + ',' + center.x.toFixed(1) + ',' + center.y.toFixed(1) + ')');
                //g.setAttribute('transform', 'rotate(' + angle.toString() + ',' + center.x.toString() + ',' + center.y.toString() + ')');
                g.appendChild(text);
                //this._svg.appendChild(g);
                this._appendChild(g);
                var bb = this._getBBox(text); // text.getBBox();
                text.setAttribute('y', (pt.y - (bb.y + bb.height - pt.y)).toFixed(1));
                return text;
            };
            _SvgRenderEngine.prototype.measureString = function (s, className, groupName, style) {
                var sz = new wijmo.Size(0, 0);
                if (this._fontSize) {
                    this._text.setAttribute('font-size', this._fontSize);
                }
                if (this._fontFamily) {
                    this._text.setAttribute('font-family', this._fontFamily);
                }
                if (className) {
                    this._text.setAttribute('class', className);
                }
                if (groupName) {
                    this._textGroup.setAttribute('class', groupName);
                }
                this._applyStyle(this._text, style);
                this._setText(this._text, s);
                var rect = this._getBBox(this._text); // this._text.getBBox();
                sz.width = rect.width;
                sz.height = rect.height;
                this._text.removeAttribute('font-size');
                this._text.removeAttribute('font-family');
                this._text.removeAttribute('class');
                if (style) {
                    for (var key in style) {
                        this._text.removeAttribute(this._deCase(key));
                    }
                }
                this._textGroup.removeAttribute('class');
                this._text.textContent = null;
                return sz;
            };
            _SvgRenderEngine.prototype.startGroup = function (className, clipPath, createTransform) {
                if (createTransform === void 0) { createTransform = false; }
                var group = document.createElementNS(_SvgRenderEngine.svgNS, 'g');
                if (className) {
                    group.setAttribute('class', className);
                }
                if (clipPath) {
                    group.setAttribute('clip-path', 'url(#' + clipPath + ')');
                }
                this._appendChild(group);
                if (createTransform) {
                    group.transform.baseVal.appendItem(this._svg.createSVGTransform());
                }
                this._group = group;
                return group;
            };
            _SvgRenderEngine.prototype.endGroup = function () {
                if (this._group) {
                    var parent = this._group.parentNode;
                    if (parent == this._svg) {
                        this._group = null;
                    }
                    else {
                        this._group = parent;
                    }
                }
            };
            _SvgRenderEngine.prototype.drawImage = function (imageHref, x, y, w, h) {
                var img = document.createElementNS(_SvgRenderEngine.svgNS, 'image');
                img.setAttributeNS(_SvgRenderEngine.xlinkNS, 'href', imageHref);
                img.setAttribute('x', x.toFixed(1));
                img.setAttribute('y', y.toFixed(1));
                img.setAttribute('width', w.toFixed(1));
                img.setAttribute('height', h.toFixed(1));
                this._appendChild(img);
                return img;
            };
            _SvgRenderEngine.prototype._appendChild = function (element) {
                var group = this._group;
                if (!group) {
                    group = this._svg;
                }
                group.appendChild(element);
            };
            _SvgRenderEngine.prototype._create = function () {
                this._svg = document.createElementNS(_SvgRenderEngine.svgNS, 'svg');
                this._defs = document.createElementNS(_SvgRenderEngine.svgNS, 'defs');
                this._svg.appendChild(this._defs);
                this._text = this._createText(new wijmo.Point(-1000, -1000), '');
                this._textGroup = document.createElementNS(_SvgRenderEngine.svgNS, 'g');
                this._textGroup.appendChild(this._text);
                this._svg.appendChild(this._textGroup);
            };
            _SvgRenderEngine.prototype._setText = function (element, s) {
                var text = s ? s.toString() : null;
                if (text && text.indexOf('tspan') >= 0) {
                    try {
                        element.textContent = null;
                        // Parse the markup into valid nodes.
                        var dXML = new DOMParser();
                        //dXML.async = false;
                        // Wrap the markup into a SVG node to ensure parsing works.
                        var sXML = '<svg xmlns="http://www.w3.org/2000/svg\">' + text + '</svg>';
                        var svgDocElement = dXML.parseFromString(sXML, 'text/xml').documentElement;
                        // Now take each node, import it and append to this element.
                        var childNode = svgDocElement.firstChild;
                        while (childNode) {
                            element.appendChild(element.ownerDocument.importNode(childNode, true));
                            childNode = childNode.nextSibling;
                        }
                    }
                    catch (e) {
                        throw new Error('Error parsing XML string.');
                    }
                    ;
                }
                else {
                    element.textContent = text;
                }
            };
            _SvgRenderEngine.prototype._createText = function (pos, text) {
                var textel = document.createElementNS(_SvgRenderEngine.svgNS, 'text');
                this._setText(textel, text);
                textel.setAttribute('fill', this._textFill);
                textel.setAttribute('x', pos.x.toFixed(1));
                textel.setAttribute('y', pos.y.toFixed(1));
                //textel.setAttribute('x', pos.x.toString());
                //textel.setAttribute('y', pos.y.toString());
                if (this._fontSize) {
                    textel.setAttribute('font-size', this._fontSize);
                }
                if (this._fontFamily) {
                    textel.setAttribute('font-family', this._fontFamily);
                }
                return textel;
            };
            _SvgRenderEngine.prototype._applyStyle = function (el, style) {
                if (style) {
                    for (var key in style) {
                        if (key === 'fill' || key === 'stroke') {
                            this._applyColor(el, key, style[key]);
                        }
                        else {
                            el.setAttribute(this._deCase(key), style[key]);
                        }
                    }
                }
            };
            _SvgRenderEngine.prototype._deCase = function (s) {
                return s.replace(/[A-Z]/g, function (a) { return '-' + a.toLowerCase(); });
            };
            _SvgRenderEngine.prototype._getBBox = function (text) {
                if (_SvgRenderEngine._isff) {
                    try {
                        return text.getBBox();
                    }
                    catch (e) {
                        return { x: 0, y: 0, width: 0, height: 0 };
                    }
                }
                else {
                    return text.getBBox();
                }
            };
            _SvgRenderEngine.prototype._applyColor = function (el, key, val) {
                var color = _GradientColorUtil.tryParse(val);
                if (color == null) {
                    return;
                }
                if (wijmo.isString(color)) {
                    el.setAttribute(key, color);
                }
                else {
                    if (this._savedGradient[val] == null) {
                        var id = 'gc' + (1000000 * Math.random()).toFixed();
                        var gradient;
                        if (color.x1 != null) {
                            gradient = document.createElementNS(_SvgRenderEngine.svgNS, 'linearGradient');
                            ['x1', 'y1', 'x2', 'y2', 'gradientUnits'].forEach(function (v) {
                                if (color[v] != null) {
                                    gradient.setAttribute(v, color[v]);
                                }
                            });
                        }
                        else if (color.r != null) {
                            gradient = document.createElementNS(_SvgRenderEngine.svgNS, 'radialGradient');
                            ['cx', 'cy', 'r', 'fx', 'fy', 'fr', 'gradientUnits'].forEach(function (v) {
                                if (color[v] != null) {
                                    gradient.setAttribute(v, color[v]);
                                }
                            });
                        }
                        if (color.colors && color.colors && color.colors.length > 0) {
                            color.colors.forEach(function (c) {
                                var stop = document.createElementNS(_SvgRenderEngine.svgNS, 'stop');
                                if (c.color != null) {
                                    stop.setAttribute('stop-color', c.color);
                                }
                                if (c.offset != null) {
                                    stop.setAttribute('offset', c.offset);
                                }
                                if (c.opacity != null) {
                                    stop.setAttribute('stop-opacity', c.opacity);
                                }
                                gradient.appendChild(stop);
                            });
                        }
                        gradient.setAttribute('id', id);
                        this._defs.appendChild(gradient);
                        this._savedGradient[val] = id;
                    }
                    el.setAttribute(key, 'url(#' + this._savedGradient[val] + ')');
                }
            };
            _SvgRenderEngine.svgNS = 'http://www.w3.org/2000/svg';
            _SvgRenderEngine.xlinkNS = 'http://www.w3.org/1999/xlink';
            return _SvgRenderEngine;
        }());
        chart._SvgRenderEngine = _SvgRenderEngine;
        //Utilities for gradient color.
        var _GradientColorUtil = /** @class */ (function () {
            function _GradientColorUtil() {
            }
            _GradientColorUtil.tryParse = function (color) {
                if (_GradientColorUtil.parsedColor[color]) {
                    return _GradientColorUtil.parsedColor[color];
                }
                if (color == null || color.indexOf('-') === -1) {
                    return color;
                }
                var arr = color.replace(/\s+/g, '').split(/\-/g);
                var type = arr[0][0];
                var relative = false;
                var gc;
                //Suppose string with '-' is gradient color.
                var coords = arr[0].match(/\(\S+\)/)[0].replace(/[\(\\)]/g, '').split(/\,/g);
                if (type === 'l' || type === 'L') {
                    gc = {
                        x1: '0',
                        y1: '0',
                        x2: '0',
                        y2: '0',
                        colors: []
                    };
                    if (type === 'l') {
                        relative = true;
                    }
                    ['x1', 'y1', 'x2', 'y2'].forEach(function (v, i) {
                        if (coords[i] != null) {
                            gc[v] = relative ? +coords[i] * 100 + '%' : coords[i] + '';
                        }
                    });
                }
                else if (type === 'r' || type === 'R') {
                    gc = {
                        cx: '0',
                        cy: '0',
                        r: '0',
                        colors: []
                    };
                    if (type === 'r') {
                        relative = true;
                    }
                    ['cx', 'cy', 'r', 'fx', 'fy', 'fr'].forEach(function (v, i) {
                        if (coords[i] != null && coords[i] !== '') {
                            gc[v] = relative ? +coords[i] * 100 + '%' : coords[i] + '';
                        }
                    });
                }
                if (!relative) {
                    gc.gradientUnits = "userSpaceOnUse";
                }
                _GradientColorUtil.parsedColor[color] = gc;
                var len = arr.length - 1;
                arr.forEach(function (v, i) {
                    if (v.indexOf(')') > -1) {
                        v = v.match(/\)\S+/)[0].replace(')', '');
                    }
                    var c = v.split(':');
                    var col = {
                        color: 'black'
                    };
                    if (c[0] != null) {
                        col.color = c[0];
                    }
                    if (c[1] != null) {
                        col.offset = relative ? +c[1] * 100 + '%' : c[1] + '';
                    }
                    else {
                        col.offset = (i / len * 100) + '%';
                    }
                    if (c[2] != null) {
                        col.opacity = c[2];
                    }
                    gc.colors.push(col);
                });
                return gc;
            };
            _GradientColorUtil.parsedColor = {};
            return _GradientColorUtil;
        }());
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Represents the chart legend.
         */
        var Legend = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:Legend class.
             *
             * @param chart @see:FlexChartBase that owns this @see:Legend.
             */
            function Legend(chart) {
                this._position = chart_1.Position.Right;
                this._title = '';
                this._titleAlign = 'left';
                this._titlePadding = 5;
                this._areas = new Array();
                this._sz = new wijmo.Size();
                this._colRowLens = [];
                this._chart = chart;
            }
            Object.defineProperty(Legend.prototype, "position", {
                //--------------------------------------------------------------------------
                //** object model
                /**
                 * Gets or sets a value that determines whether and where the legend
                 * appears in relation to the plot area.
                 */
                get: function () {
                    return this._position;
                },
                set: function (value) {
                    if (value != this._position) {
                        this._position = wijmo.asEnum(value, chart_1.Position);
                        if (this._chart) {
                            this._chart.invalidate();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Legend.prototype, "title", {
                /**
                 * Gets or sets a value that determines the title of the legend.
                 */
                get: function () {
                    return this._title;
                },
                set: function (value) {
                    if (value != this._title) {
                        this._title = wijmo.asString(value, false);
                        if (this._chart) {
                            this._chart.invalidate();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Legend.prototype, "titleAlign", {
                /**
                 * Gets or sets a value that determines the align value of the legend.
                 * The value should be 'left', 'center' or 'right'.
                 */
                get: function () {
                    return this._titleAlign;
                },
                set: function (value) {
                    if (value != this._titleAlign) {
                        var align = wijmo.asString(value, false);
                        if (align === 'right') {
                            this._titleAlign = 'right';
                        }
                        else if (align === 'center') {
                            this._titleAlign = 'center';
                        }
                        else {
                            this._titleAlign = 'left';
                        }
                        if (this._chart) {
                            this._chart.invalidate();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            //--------------------------------------------------------------------------
            //** implementation
            // measures the legend
            Legend.prototype._getDesiredSize = function (engine, pos, w, h) {
                // no legend? no size.
                //var pos = this.position;
                if (pos == chart_1.Position.None) {
                    return null;
                }
                var isVertical = pos == chart_1.Position.Right || pos == chart_1.Position.Left;
                var size = this._chart._getDesiredLegendSize(engine, isVertical, w, h);
                if (size != null) {
                    if (this.title.length > 0) {
                        var titleSize = engine.measureString(this.title, 'wj-title', 'wj-legend');
                        size.height += titleSize.height + this._titlePadding;
                        if (titleSize.width > size.width) {
                            size.width = titleSize.width;
                        }
                    }
                    this._sz = size;
                }
                return size;
            };
            Legend.prototype._getPosition = function (w, h) {
                if (this.position == chart_1.Position.Auto) {
                    return (w >= h) ? chart_1.Position.Right : chart_1.Position.Bottom;
                }
                else {
                    return this.position;
                }
            };
            // render the legend
            Legend.prototype._render = function (engine, pt, pos, w, h) {
                this._areas = [];
                var isVertical = pos == chart_1.Position.Right || pos == chart_1.Position.Left;
                // draw legend area
                engine.fill = 'transparent';
                engine.stroke = null;
                engine.drawRect(pt.x, pt.y, this._sz.width, this._sz.height);
                if (this.title.length) {
                    var text = engine.drawString(this.title, pt, 'wj-title');
                    var titleBox = text.getBBox();
                    text.setAttribute('y', Number(text.getAttribute('y')) + titleBox.height);
                    if (this.titleAlign === 'right') {
                        text.setAttribute('x', pt.x + w - titleBox.width);
                    }
                    else if (this.titleAlign === 'center') {
                        text.setAttribute('x', pt.x + w / 2 - titleBox.width / 2);
                    }
                    var len = titleBox.height + this._titlePadding;
                    pt.y += len;
                    h -= len;
                }
                this._chart._renderLegend(engine, pt, this._areas, isVertical, w, h);
            };
            // perform hit-testing on the legend
            Legend.prototype._hitTest = function (pt) {
                var areas = this._areas;
                for (var i = 0; i < areas.length; i++) {
                    if (areas[i] && chart_1.FlexChartCore._contains(areas[i], pt)) {
                        return i;
                    }
                }
                return null;
            };
            return Legend;
        }());
        chart_1.Legend = Legend;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Specifies the type of chart element found by the hitTest method.
         */
        var ChartElement;
        (function (ChartElement) {
            /** The area within the axes. */
            ChartElement[ChartElement["PlotArea"] = 0] = "PlotArea";
            /** X-axis. */
            ChartElement[ChartElement["AxisX"] = 1] = "AxisX";
            /** Y-axis. */
            ChartElement[ChartElement["AxisY"] = 2] = "AxisY";
            /** The area within the control but outside the axes. */
            ChartElement[ChartElement["ChartArea"] = 3] = "ChartArea";
            /** The chart legend. */
            ChartElement[ChartElement["Legend"] = 4] = "Legend";
            /** The chart header. */
            ChartElement[ChartElement["Header"] = 5] = "Header";
            /** The chart footer. */
            ChartElement[ChartElement["Footer"] = 6] = "Footer";
            /** A chart series. */
            ChartElement[ChartElement["Series"] = 7] = "Series";
            /** A chart series symbol. */
            ChartElement[ChartElement["SeriesSymbol"] = 8] = "SeriesSymbol";
            /** A data label. */
            ChartElement[ChartElement["DataLabel"] = 9] = "DataLabel";
            /** No chart element. */
            ChartElement[ChartElement["None"] = 10] = "None";
        })(ChartElement = chart_1.ChartElement || (chart_1.ChartElement = {}));
        ;
        /**
         * Contains information about a part of a @see:FlexChart control at
         * a specified page coordinate.
         */
        var HitTestInfo = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:wijmo.chart.HitTestInfo class.
             *
             * @param chart The chart control.
             * @param point The original point in window coordinates.
             * @param element The chart element.
             */
            function HitTestInfo(chart, point, element) {
                this._pointIndex = null;
                this._chartElement = ChartElement.None;
                this._chart = chart;
                this._pt = point;
                this._chartElement = element;
            }
            Object.defineProperty(HitTestInfo.prototype, "chart", {
                /**
                 * Gets the @see:FlexChartBase that owns this @see:wijmo.chart.HitTestInfo.
                 */
                get: function () {
                    return this._chart;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "point", {
                /**
                 * Gets the point, in control coordinates,
                 * that this @see:wijmo.chart.HitTestInfo refers to.
                 */
                get: function () {
                    return this._pt;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "series", {
                /**
                 * Gets the chart series at the specified coordinates.
                 */
                get: function () {
                    return this._series;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "pointIndex", {
                /**
                 * Gets the data point index at the specified coordinates.
                 */
                get: function () {
                    return this._pointIndex;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "chartElement", {
                /**
                 * Gets the chart element at the specified coordinates.
                 */
                get: function () {
                    return this._chartElement;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "distance", {
                /**
                 * Gets the distance to the closest data point.
                 */
                get: function () {
                    return this._dist;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "item", {
                /**
                 * Gets the data object that corresponds to the closest data point.
                 */
                get: function () {
                    if (this._item == null) {
                        //this._item = null;
                        if (this.pointIndex !== null) {
                            if (this.series != null) {
                                this._item = this.series._getItem(this.pointIndex);
                            }
                            else {
                                var item = this._chart._getHitTestItem(this.pointIndex);
                                if (item) {
                                    this._item = item;
                                }
                            }
                        }
                    }
                    return this._item;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "x", {
                /**
                 * Gets the x-value of the closest data point.
                 */
                get: function () {
                    if (this._x === undefined) {
                        this._x = this._getValue(1, false);
                    }
                    return this._x;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "y", {
                /**
                 * Gets the y-value of the closest data point.
                 */
                get: function () {
                    if (this._y === undefined) {
                        this._y = this._getValue(0, false);
                    }
                    return this._y;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "value", {
                get: function () {
                    var val = this._chart._getHitTestValue(this.pointIndex);
                    if (val != null) {
                        return val;
                    }
                    else {
                        return this.y;
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "name", {
                get: function () {
                    if (this._name === undefined) {
                        var label = this._chart._getHitTestLabel(this.pointIndex);
                        return label == null ? this.series.name : label.toString();
                    }
                    return this._name;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "_xfmt", {
                // formatted x-value
                get: function () {
                    if (this.__xfmt === undefined) {
                        this.__xfmt = this._getValue(1, true);
                    }
                    return this.__xfmt;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "_yfmt", {
                // formatted y-value
                get: function () {
                    if (this.__yfmt === undefined) {
                        this.__yfmt = this._getValue(0, true);
                    }
                    return this.__yfmt;
                },
                enumerable: true,
                configurable: true
            });
            HitTestInfo.prototype._setData = function (series, pi) {
                this._series = series;
                this._pointIndex = pi;
            };
            HitTestInfo.prototype._setDataPoint = function (dataPoint) {
                dataPoint = wijmo.asType(dataPoint, chart_1._DataPoint, true);
                if (dataPoint) {
                    this._pointIndex = dataPoint.pointIndex;
                    var fch = wijmo.asType(this._chart, wijmo.chart.FlexChartCore, true);
                    var si = dataPoint.seriesIndex;
                    if (si !== null && si >= 0 && si < fch.series.length) {
                        this._series = fch.series[si];
                    }
                    // additional properties
                    if (dataPoint['item'] != null) {
                        this._item = dataPoint['item'];
                    }
                    if (dataPoint['x'] != null) {
                        this._x = dataPoint['x'];
                    }
                    if (dataPoint['y'] != null) {
                        this._y = dataPoint['y'];
                    }
                    if (dataPoint['xfmt'] != null) {
                        this.__xfmt = dataPoint['xfmt'];
                    }
                    if (dataPoint['yfmt'] != null) {
                        this.__yfmt = dataPoint['yfmt'];
                    }
                    if (dataPoint['name'] != null) {
                        this._name = dataPoint['name'];
                    }
                }
            };
            // y: index=0
            // x: index=1
            HitTestInfo.prototype._getValue = function (index, formatted) {
                var value = this._chart._getHitTestValue(this.pointIndex);
                if (value != null) {
                    return value;
                }
                // todo: rotated charts?
                var val = null, chart = this._chart, pi = this.pointIndex, rotated = chart._isRotated();
                if (this.series !== null && pi !== null) {
                    var vals = this.series.getValues(index); // xvalues
                    var type = this.series.getDataType(index);
                    // normal values
                    if (vals && this.pointIndex < vals.length) {
                        val = vals[this.pointIndex];
                        if (type == wijmo.DataType.Date && !formatted) {
                            val = new Date(val);
                        }
                    }
                    else if (index == 1) {
                        // category axis
                        if (chart._xlabels && chart._xlabels.length > 0 && pi < chart._xlabels.length) {
                            val = chart._xlabels[pi];
                            // automatic axis values
                        }
                        else if (chart._xvals && pi < chart._xvals.length) {
                            val = chart._xvals[pi];
                            if (chart._xDataType == wijmo.DataType.Date && !formatted) {
                                val = new Date(val);
                            }
                        }
                    }
                }
                if (val !== null && formatted) {
                    if (rotated) {
                        if (index == 0) {
                            val = this.ax._formatValue(val);
                        }
                        else if (index == 1) {
                            val = this.ay._formatValue(val);
                        }
                    }
                    else {
                        if (index == 0) {
                            val = this.ay._formatValue(val);
                        }
                        else if (index == 1) {
                            val = this.ax._formatValue(val);
                        }
                    }
                }
                return val;
            };
            Object.defineProperty(HitTestInfo.prototype, "ax", {
                get: function () {
                    return this.series.axisX ? this.series.axisX : this._chart.axisX;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(HitTestInfo.prototype, "ay", {
                get: function () {
                    return this.series.axisY ? this.series.axisY : this._chart.axisY;
                },
                enumerable: true,
                configurable: true
            });
            return HitTestInfo;
        }());
        chart_1.HitTestInfo = HitTestInfo;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        'use strict';
        /**
         * These are predefined color palettes for chart @see:Series objects.
         *
         * To create custom color palettes, supply an array of strings or rgba values.
         *
         * You can specify palettes for @see:FlexChart and @see:FlexPie controls.
         * For example:
         *
         * <pre>chart.palette = Palettes.light;</pre>
         *
         * The following palettes are pre-defined:
         * <ul>
         *   <li>standard (default)</li>
         *   <li>cocoa</li>
         *   <li>coral</li>
         *   <li>dark</li>
         *   <li>highcontrast</li>
         *   <li>light</li>
         *   <li>midnight</li>
         *   <li>modern</li>
         *   <li>organic</li>
         *   <li>slate</li>
         *   <li>zen</li>
         *   <li>cyborg</li>
         *   <li>superhero</li>
         *   <li>flatly</li>
         *   <li>darkly</li>
         *   <li>cerulan</li>
         * </ul>
         */
        var Palettes = /** @class */ (function () {
            function Palettes() {
            }
            Palettes.standard = ['#88bde6', '#fbb258', '#90cd97', '#f6aac9', '#bfa554', '#bc99c7', '#eddd46', '#f07e6e', '#8c8c8c'];
            Palettes.cocoa = ['#466bb0', '#c8b422', '#14886e', '#b54836', '#6e5944', '#8b3872', '#73b22b', '#b87320', '#141414'];
            Palettes.coral = ['#84d0e0', '#f48256', '#95c78c', '#efa5d6', '#ba8452', '#ab95c2', '#ede9d0', '#e96b7d', '#888888'];
            Palettes.dark = ['#005fad', '#f06400', '#009330', '#e400b1', '#b65800', '#6a279c', '#d5a211', '#dc0127', '#000000'];
            Palettes.highcontrast = ['#ff82b0', '#0dda2c', '#0021ab', '#bcf28c', '#19c23b', '#890d3a', '#607efd', '#1b7700', '#000000'];
            Palettes.light = ['#ddca9a', '#778deb', '#cb9fbb', '#b5eae2', '#7270be', '#a6c7a7', '#9e95c7', '#95b0c7', '#9b9b9b'];
            Palettes.midnight = ['#83aaca', '#e37849', '#14a46a', '#e097da', '#a26d54', '#a584b7', '#d89c54', '#e86996', '#2c343b'];
            Palettes.modern = ['#2d9fc7', '#ec993c', '#89c235', '#e377a4', '#a68931', '#a672a6', '#d0c041', '#e35855', '#68706a'];
            Palettes.organic = ['#9c88d9', '#a3d767', '#8ec3c0', '#e9c3a9', '#91ab36', '#d4ccc0', '#61bbd8', '#e2d76f', '#80715a'];
            Palettes.slate = ['#7493cd', '#f99820', '#71b486', '#e4a491', '#cb883b', '#ae83a4', '#bacc5c', '#e5746a', '#505d65'];
            Palettes.zen = ['#7bb5ae', '#e2d287', '#92b8da', '#eac4cb', '#7b8bbd', '#c7d189', '#b9a0c8', '#dfb397', '#a9a9a9'];
            Palettes.cyborg = ['#2a9fd6', '#77b300', '#9933cc', '#ff8800', '#cc0000', '#00cca3', '#3d6dcc', '#525252', '#000000'];
            Palettes.superhero = ['#5cb85c', '#f0ad4e', '#5bc0de', '#d9534f', '#9f5bde', '#46db8c', '#b6b86e', '#4e5d6c', '#2b3e4b'];
            Palettes.flatly = ['#18bc9c', '#3498db', '#f39c12', '#6cc1be', '#99a549', '#8f54b5', '#e74c3c', '#8a9899', '#2c3e50'];
            Palettes.darkly = ['#375a7f', '#00bc8c', '#3498db', '#f39c12', '#e74c3c', '#8f61b3', '#b08725', '#4a4949', '#000000'];
            Palettes.cerulan = ['#033e76', '#87c048', '#59822c', '#53b3eb', '#fc6506', '#d42323', '#e3bb00', '#cccccc', '#222222'];
            return Palettes;
        }());
        chart.Palettes = Palettes;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Calculates Spline curves.
         */
        var _Spline = /** @class */ (function () {
            //public Point[] Points
            //{
            //    get { return _pts; }
            //}
            function _Spline(x, y) {
                // 
                this.k = 0.002;
                this._a = [];
                this._b = [];
                this._c = [];
                this._d = [];
                //  T^3     -1     +3    -3    +1     /
                //  T^2     +2     -5     4    -1    /
                //  T^1     -1      0     1     0   /  2
                //  T^0      0      2     0     0  /
                this.m = [
                    [-1 * 0.5, +3 * 0.5, -3 * 0.5, +1 * 0.5],
                    [+2 * 0.5, -5 * 0.5, +4 * 0.5, -1 * 0.5],
                    [-1 * 0.5, 0, +1 * 0.5, 0],
                    [0, +2 * 0.5, 0, 0],
                ];
                this._x = x;
                this._y = y;
                var len = this._len = Math.min(x.length, y.length);
                if (len > 3) {
                    for (var i = 0; i < len - 1; i++) {
                        var p1 = (i == 0) ? new wijmo.Point(x[i], y[i]) : new wijmo.Point(x[i - 1], y[i - 1]);
                        var p2 = new wijmo.Point(x[i], y[i]);
                        var p3 = new wijmo.Point(x[i + 1], y[i + 1]);
                        var p4 = (i == len - 2) ? new wijmo.Point(x[i + 1], y[i + 1]) : new wijmo.Point(x[i + 2], y[i + 2]);
                        var a = new wijmo.Point();
                        var b = new wijmo.Point();
                        var c = new wijmo.Point();
                        var d = new wijmo.Point();
                        a.x = p1.x * this.m[0][0] + p2.x * this.m[0][1] + p3.x * this.m[0][2] + p4.x * this.m[0][3];
                        b.x = p1.x * this.m[1][0] + p2.x * this.m[1][1] + p3.x * this.m[1][2] + p4.x * this.m[1][3];
                        c.x = p1.x * this.m[2][0] + p2.x * this.m[2][1] + p3.x * this.m[2][2] + p4.x * this.m[2][3];
                        d.x = p1.x * this.m[3][0] + p2.x * this.m[3][1] + p3.x * this.m[3][2] + p4.x * this.m[3][3];
                        a.y = p1.y * this.m[0][0] + p2.y * this.m[0][1] + p3.y * this.m[0][2] + p4.y * this.m[0][3];
                        b.y = p1.y * this.m[1][0] + p2.y * this.m[1][1] + p3.y * this.m[1][2] + p4.y * this.m[1][3];
                        c.y = p1.y * this.m[2][0] + p2.y * this.m[2][1] + p3.y * this.m[2][2] + p4.y * this.m[2][3];
                        d.y = p1.y * this.m[3][0] + p2.y * this.m[3][1] + p3.y * this.m[3][2] + p4.y * this.m[3][3];
                        this._a.push(a);
                        this._b.push(b);
                        this._c.push(c);
                        this._d.push(d);
                    }
                }
            }
            _Spline.prototype.calculatePoint = function (val) {
                var i = Math.floor(val);
                if (i < 0) {
                    i = 0;
                }
                if (i > this._len - 2) {
                    i = this._len - 2;
                }
                var d = val - i;
                var x = ((this._a[i].x * d + this._b[i].x) * d + this._c[i].x) * d + this._d[i].x;
                var y = ((this._a[i].y * d + this._b[i].y) * d + this._c[i].y) * d + this._d[i].y;
                return { x: x, y: y };
            };
            _Spline.prototype.calculate = function () {
                if (this._len <= 3) {
                    return { xs: this._x, ys: this._y };
                }
                var xs = [];
                var ys = [];
                var p0 = this.calculatePoint(0);
                xs.push(p0.x);
                ys.push(p0.y);
                var delta = this._len * this.k;
                var d = 3;
                for (var i = delta; i <= this._len - 1; i += delta) {
                    var p = this.calculatePoint(i);
                    if (Math.abs(p0.x - p.x) >= d || Math.abs(p0.y - p.y) >= d) {
                        xs.push(p.x);
                        ys.push(p.y);
                        p0 = p;
                    }
                }
                return { xs: xs, ys: ys };
            };
            return _Spline;
        }());
        chart._Spline = _Spline;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Specifies the position of data labels on the chart.
         */
        var LabelPosition;
        (function (LabelPosition) {
            /** No data labels appear. */
            LabelPosition[LabelPosition["None"] = 0] = "None";
            /** The labels appear to the left of the data points. */
            LabelPosition[LabelPosition["Left"] = 1] = "Left";
            /** The labels appear above the data points. */
            LabelPosition[LabelPosition["Top"] = 2] = "Top";
            /** The labels appear to the right of the data points. */
            LabelPosition[LabelPosition["Right"] = 3] = "Right";
            /** The labels appear below the data points. */
            LabelPosition[LabelPosition["Bottom"] = 4] = "Bottom";
            /** The labels appear centered on the data points. */
            LabelPosition[LabelPosition["Center"] = 5] = "Center";
        })(LabelPosition = chart.LabelPosition || (chart.LabelPosition = {}));
        ;
        /**
         * Specifies the position of data labels on the pie chart.
         */
        var PieLabelPosition;
        (function (PieLabelPosition) {
            /** No data labels. */
            PieLabelPosition[PieLabelPosition["None"] = 0] = "None";
            /** The label appears inside the pie slice. */
            PieLabelPosition[PieLabelPosition["Inside"] = 1] = "Inside";
            /** The item appears at the center of the pie slice. */
            PieLabelPosition[PieLabelPosition["Center"] = 2] = "Center";
            /** The item appears outside the pie slice. */
            PieLabelPosition[PieLabelPosition["Outside"] = 3] = "Outside";
        })(PieLabelPosition = chart.PieLabelPosition || (chart.PieLabelPosition = {}));
        ;
        /**
         * Provides arguments for @see:DataLabel rendering event.
         */
        var DataLabelRenderEventArgs = /** @class */ (function (_super) {
            __extends(DataLabelRenderEventArgs, _super);
            /**
             * Initializes a new instance of the @see:DataLabelRenderEventArgs class.
             *
             * @param engine (@see:IRenderEngine) The rendering engine to use.
             * @param ht The hit test information.
             * @param pt The reference point.
             * @param text The label text.
             */
            function DataLabelRenderEventArgs(engine, ht, pt, text) {
                var _this = _super.call(this, engine) || this;
                /**
                 * Gets or sets a value that indicates whether the event should be cancelled.
                 */
                _this.cancel = false;
                _this._ht = ht;
                _this._pt = pt;
                _this._text = text;
                return _this;
            }
            Object.defineProperty(DataLabelRenderEventArgs.prototype, "point", {
                /**
                 * Gets the point associated with the label in control coordinates.
                 */
                get: function () {
                    return this._pt;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DataLabelRenderEventArgs.prototype, "text", {
                /**
                 * Gets or sets the label text.
                 */
                get: function () {
                    return this._text;
                },
                set: function (value) {
                    this._text = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DataLabelRenderEventArgs.prototype, "hitTestInfo", {
                /**
                 * Gets the hit test information.
                 */
                get: function () {
                    return this._ht;
                },
                enumerable: true,
                configurable: true
            });
            return DataLabelRenderEventArgs;
        }(chart.RenderEventArgs));
        chart.DataLabelRenderEventArgs = DataLabelRenderEventArgs;
        /**
        * Represents the base abstract class for the @see:DataLabel and the @see:PieDataLabel classes.
        */
        var DataLabelBase = /** @class */ (function () {
            function DataLabelBase() {
                /**
                 * Occurs before the data label is rendered.
                 */
                this.rendering = new wijmo.Event();
            }
            Object.defineProperty(DataLabelBase.prototype, "content", {
                /**
                 * Gets or sets the content of data labels.
                 *
                 * The content can be specified as a string or as a function that
                 * takes @see:wijmo.chart.HitTestInfo object as a parameter.
                 *
                 * When the label content is a string, it can contain any of the following
                 * parameters:
                 *
                 * <ul>
                 *  <li><b>seriesName</b>: Name of the series that contains the data point (FlexChart only).</li>
                 *  <li><b>pointIndex</b>: Index of the data point.</li>
                 *  <li><b>value</b>: <b>Value</b> of the data point.</li>
                 *  <li><b>x</b>: <b>x</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>y</b>: <b>y</b>-value of the data point (FlexChart only).</li>
                 *  <li><b>name</b>: <b>Name</b> of the data point.</li>
                 *  <li><b>propertyName</b>: any property of data object.</li>
                 * </ul>
                 *
                 * The parameter must be enclosed in curly brackets, for example 'x={x}, y={y}'.
                 *
                 * In the following example, we show the y value of the data point in the labels.
                 *
                 * <pre>
                 *  // Create a chart and show y data in labels positioned above the data point.
                 *  var chart = new wijmo.chart.FlexChart('#theChart');
                 *  chart.initialize({
                 *      itemsSource: data,
                 *      bindingX: 'country',
                 *      series: [
                 *          { name: 'Sales', binding: 'sales' },
                 *          { name: 'Expenses', binding: 'expenses' },
                 *          { name: 'Downloads', binding: 'downloads' }],
                 *  });
                 *  chart.dataLabel.position = "Top";
                 *  chart.dataLabel.content = "{country} {seriesName}:{y}";
                 * </pre>
                 *
                 * The next example shows how to set data label content using a function.
                 *
                 * <pre>
                 *  // Set the data label content
                 *  chart.dataLabel.content = function (ht) {
                 *    return ht.name + ":" + ht.value.toFixed();
                 *  }
                 * </pre>
                 *
                 */
                get: function () {
                    return this._content;
                },
                set: function (value) {
                    if (value != this._content) {
                        this._content = value;
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DataLabelBase.prototype, "border", {
                /**
                 * Gets or sets a value indicating whether the data labels have borders.
                 */
                get: function () {
                    return this._bdr;
                },
                set: function (value) {
                    if (value != this._bdr) {
                        this._bdr = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DataLabelBase.prototype, "offset", {
                /**
                 * Gets or sets the offset from label to the data point.
                 */
                get: function () {
                    return this._off;
                },
                set: function (value) {
                    if (value != this._off) {
                        this._off = wijmo.asNumber(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DataLabelBase.prototype, "connectingLine", {
                /**
                 * Gets or sets a value indicating whether to draw lines that connect
                 * labels to the data points.
                 */
                get: function () {
                    return this._line;
                },
                set: function (value) {
                    if (value != this._line) {
                        this._line = wijmo.asBoolean(value, true);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:rendering event.
             *
             * @param e The @see:DataLabelRenderEventArgs object used to render the label.
             * @return True if the event was not canceled.
             */
            DataLabelBase.prototype.onRendering = function (e) {
                this.rendering.raise(this, e);
                return !e.cancel;
            };
            DataLabelBase.prototype._invalidate = function () {
                if (this._chart) {
                    this._chart.invalidate();
                }
            };
            return DataLabelBase;
        }());
        chart.DataLabelBase = DataLabelBase;
        /**
         * The point data label for FlexChart.
         */
        var DataLabel = /** @class */ (function (_super) {
            __extends(DataLabel, _super);
            function DataLabel() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this._pos = LabelPosition.Top;
                return _this;
            }
            Object.defineProperty(DataLabel.prototype, "position", {
                /**
                 * Gets or sets the position of the data labels.
                 */
                get: function () {
                    return this._pos;
                },
                set: function (value) {
                    if (value != this._pos) {
                        this._pos = wijmo.asEnum(value, LabelPosition);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            return DataLabel;
        }(DataLabelBase));
        chart.DataLabel = DataLabel;
        /**
         * The point data label for FlexPie.
         */
        var PieDataLabel = /** @class */ (function (_super) {
            __extends(PieDataLabel, _super);
            function PieDataLabel() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this._pos = PieLabelPosition.Center;
                return _this;
            }
            Object.defineProperty(PieDataLabel.prototype, "position", {
                /**
                 * Gets or sets the position of the data labels.
                 */
                get: function () {
                    return this._pos;
                },
                set: function (value) {
                    if (value != this._pos) {
                        this._pos = wijmo.asEnum(value, PieLabelPosition);
                        this._invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            return PieDataLabel;
        }(DataLabelBase));
        chart.PieDataLabel = PieDataLabel;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        'use strict';
        var LineMarkers = /** @class */ (function () {
            function LineMarkers() {
                this._moveMarker = function (e) {
                    var dom = e.currentTarget, markers = this._markers, markerIndex = dom.getAttribute('data-markerIndex'), arr;
                    if (markerIndex != null) {
                        arr = markers[markerIndex];
                        arr.forEach(function (marker) {
                            marker._moveMarker(e);
                        });
                    }
                };
                this._markers = [];
                this._bindMoveMarker = this._moveMarker.bind(this);
            }
            LineMarkers.prototype.attach = function (marker) {
                var hostEle = marker.chart.hostElement, markers = this._markers, markerIndex = hostEle.getAttribute('data-markerIndex'), len, arr;
                if (markerIndex != null) {
                    arr = markers[markerIndex];
                    if (arr && wijmo.isArray(arr)) {
                        arr.push(marker);
                    }
                    else {
                        markers[markerIndex] = [marker];
                        this._bindMoveEvent(hostEle);
                    }
                }
                else {
                    len = markers.length,
                        arr = [marker];
                    markers.push(arr);
                    hostEle.setAttribute('data-markerIndex', len);
                    this._bindMoveEvent(hostEle);
                }
            };
            LineMarkers.prototype.detach = function (marker) {
                var hostEle = marker.chart.hostElement, markers = this._markers, markerIndex = hostEle.getAttribute('data-markerIndex'), idx, arr;
                if (markerIndex != null) {
                    arr = markers[markerIndex];
                    idx = arr.indexOf(marker);
                    if (idx > -1) {
                        arr.splice(idx, 1);
                    }
                    if (arr.length === 0) {
                        idx = markers.indexOf(arr);
                        if (idx > -1) {
                            markers[idx] = undefined;
                        }
                        this._unbindMoveEvent(hostEle);
                    }
                }
            };
            LineMarkers.prototype._unbindMoveEvent = function (ele) {
                var _moveMarker = this._bindMoveMarker;
                ele.removeEventListener('mousemove', _moveMarker);
                if ('ontouchstart' in window) {
                    ele.removeEventListener('touchmove', _moveMarker);
                }
            };
            LineMarkers.prototype._bindMoveEvent = function (ele) {
                var _moveMarker = this._bindMoveMarker;
                ele.addEventListener('mousemove', _moveMarker);
                if ('ontouchstart' in window) {
                    ele.addEventListener('touchmove', _moveMarker);
                }
            };
            return LineMarkers;
        }());
        var lineMarkers = new LineMarkers();
        /**
         * Specifies the direction of the lines shown by the @see:LineMarker.
         */
        var LineMarkerLines;
        (function (LineMarkerLines) {
            /** No lines. */
            LineMarkerLines[LineMarkerLines["None"] = 0] = "None";
            /** Vertical line. */
            LineMarkerLines[LineMarkerLines["Vertical"] = 1] = "Vertical";
            /** Horizontal line. */
            LineMarkerLines[LineMarkerLines["Horizontal"] = 2] = "Horizontal";
            /** Vertical and horizontal lines. */
            LineMarkerLines[LineMarkerLines["Both"] = 3] = "Both";
        })(LineMarkerLines = chart_1.LineMarkerLines || (chart_1.LineMarkerLines = {}));
        // TODO: Implement drag interaction.
        // Drag 
        /**
         * Specifies how the @see:LineMarker interacts with the user.
         */
        var LineMarkerInteraction;
        (function (LineMarkerInteraction) {
            /** No interaction, the user specifies the position by clicking. */
            LineMarkerInteraction[LineMarkerInteraction["None"] = 0] = "None";
            /** The @see:LineMarker moves with the pointer. */
            LineMarkerInteraction[LineMarkerInteraction["Move"] = 1] = "Move";
            /** The @see:LineMarker moves when the user drags the lines. */
            LineMarkerInteraction[LineMarkerInteraction["Drag"] = 2] = "Drag";
        })(LineMarkerInteraction = chart_1.LineMarkerInteraction || (chart_1.LineMarkerInteraction = {}));
        //Binary
        //Right 0 -> 0, Left 1 -> 1, Bottom 4 -> 100, Top 6 -> 110
        /**
         * Specifies the alignment of the @see:LineMarker.
         */
        var LineMarkerAlignment;
        (function (LineMarkerAlignment) {
            /**
             * The LineMarker alignment adjusts automatically so that it stays
             * within the boundaries of the plot area. */
            LineMarkerAlignment[LineMarkerAlignment["Auto"] = 2] = "Auto";
            /** The LineMarker aligns to the right of the pointer. */
            LineMarkerAlignment[LineMarkerAlignment["Right"] = 0] = "Right";
            /** The LineMarker aligns to the left of the pointer. */
            LineMarkerAlignment[LineMarkerAlignment["Left"] = 1] = "Left";
            /** The LineMarker aligns to the bottom of the pointer. */
            LineMarkerAlignment[LineMarkerAlignment["Bottom"] = 4] = "Bottom";
            /** The LineMarker aligns to the top of the pointer. */
            LineMarkerAlignment[LineMarkerAlignment["Top"] = 6] = "Top";
        })(LineMarkerAlignment = chart_1.LineMarkerAlignment || (chart_1.LineMarkerAlignment = {}));
        /**
         * Represents an extension of the LineMarker for the FlexChart.
         *
         * The @see:LineMarker consists of a text area with content reflecting
         * data point values, and optional vertical or horizontal lines
         * (or both for a cross-hair effect) positioned over the plot area.
         *
         * It can be static (interaction = None), follow the mouse or touch
         * position (interaction = Move), or move when the user drags the
         * line (interaction = Drag).
         *
         * For example:
         * <pre>
         *   // create an interactive marker with a horizontal line and y-value
         *   var lm = new wijmo.chart.LineMarker($scope.ctx.chart, {
         *       lines: wijmo.chart.LineMarkerLines.Horizontal,
         *       interaction: wijmo.chart.LineMarkerInteraction.Move,
         *       alignment : wijmo.chart.LineMarkerAlignment.Top
         *   });
         *   lm.content = function (ht) {
         *       // show y-value
         *       return lm.y.toFixed(2);
         *   }
         * </pre>
         */
        var LineMarker = /** @class */ (function () {
            /**
             * Initializes a new instance of the @see:LineMarker class.
             *
             * @param chart The chart on which the LineMarker appears.
             * @param options A JavaScript object containing initialization data for the control.
             */
            function LineMarker(chart, options) {
                this._wrapperMousedown = null;
                this._wrapperMouseup = null;
                /**
                 * Occurs after the @see:LineMarker's position changes.
                 */
                this.positionChanged = new wijmo.Event();
                var self = this;
                self._chart = chart;
                chart.rendered.addHandler(self._initialize, self);
                self._resetDefaultValue();
                wijmo.copy(this, options);
                self._initialize();
            }
            Object.defineProperty(LineMarker.prototype, "chart", {
                //--------------------------------------------------------------------------
                //** object model
                /**
                 * Gets the @see:FlexChart object that owns the LineMarker.
                 */
                get: function () {
                    return this._chart;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "isVisible", {
                /**
                 * Gets or sets the visibility of the LineMarker.
                 */
                get: function () {
                    return this._isVisible;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._isVisible) {
                        return;
                    }
                    self._isVisible = wijmo.asBoolean(value);
                    if (!self._marker) {
                        return;
                    }
                    self._toggleVisibility();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "seriesIndex", {
                /**
                 * Gets or sets the index of the series in the chart in which the LineMarker appears.
                 * This takes effect when the @see:interaction property is set to
                 * wijmo.chart.LineMarkerInteraction.Move or wijmo.chart.LineMarkerInteraction.Drag.
                 */
                get: function () {
                    return this._seriesIndex;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._seriesIndex) {
                        return;
                    }
                    self._seriesIndex = wijmo.asNumber(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "horizontalPosition", {
                /**
                 * Gets or sets the horizontal position of the LineMarker relative to the plot area.
                 *
                 * Its value range is (0, 1).
                 * If the value is null or undefined and @see:interaction is set to
                 * wijmo.chart.LineMarkerInteraction.Move or wijmo.chart.LineMarkerInteraction.Drag,
                 * the horizontal position of the marker is calculated automatically based on the
                 * pointer's position.
                 */
                get: function () {
                    return this._horizontalPosition;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._horizontalPosition) {
                        return;
                    }
                    self._horizontalPosition = wijmo.asNumber(value, true);
                    if (self._horizontalPosition < 0 || self._horizontalPosition > 1) {
                        throw 'horizontalPosition\'s value should be in (0, 1).';
                    }
                    if (!self._marker) {
                        return;
                    }
                    self._updateMarkerPosition();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "x", {
                /**
                 * Gets the current x-value as chart data coordinates.
                 */
                get: function () {
                    var self = this, len = self._targetPoint.x - self._plotRect.left, axis = self._chart.axisX;
                    return axis.convertBack(len);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "y", {
                /**
                 * Gets the current y-value as chart data coordinates.
                 */
                get: function () {
                    var self = this, len = self._targetPoint.y - self._plotRect.top, axis = self._chart.axisY;
                    return axis.convertBack(len);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "content", {
                /**
                 * Gets or sets the content function that allows you to customize the text content of the LineMarker.
                 */
                get: function () {
                    return this._content;
                },
                set: function (value) {
                    if (value === this._content) {
                        return;
                    }
                    this._content = wijmo.asFunction(value);
                    this._updateMarkerPosition();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "verticalPosition", {
                /**
                 * Gets or sets the vertical position of the LineMarker relative to the plot area.
                 *
                 * Its value range is (0, 1).
                 * If the value is null or undefined and @see:interaction is set to wijmo.chart.LineMarkerInteraction.Move
                 * or wijmo.chart.LineMarkerInteraction.Drag, the vertical position of the LineMarker is calculated automatically based on the pointer's position.
                 */
                get: function () {
                    return this._verticalPosition;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._verticalPosition) {
                        return;
                    }
                    self._verticalPosition = wijmo.asNumber(value, true);
                    if (self._verticalPosition < 0 || self._verticalPosition > 1) {
                        throw 'verticalPosition\'s value should be in (0, 1).';
                    }
                    if (!self._marker) {
                        return;
                    }
                    self._updateMarkerPosition();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "alignment", {
                /**
                 * Gets or sets the alignment of the LineMarker content.
                 *
                 * By default, the LineMarker shows to the right, at the bottom of the target point.
                 * Use '|' to combine alignment values.
                 *
                 * <pre>
                 * // set the alignment to the left.
                 * marker.alignment = wijmo.chart.LineMarkerAlignment.Left;
                 * // set the alignment to the left top.
                 * marker.alignment = wijmo.chart.LineMarkerAlignment.Left | wijmo.chart.LineMarkerAlignment.Top;
                 * </pre>
                 */
                get: function () {
                    return this._alignment;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._alignment) {
                        return;
                    }
                    self._alignment = value;
                    if (!self._marker) {
                        return;
                    }
                    self._updatePositionByAlignment();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "lines", {
                /**
                 * Gets or sets the visibility of the LineMarker lines.
                 */
                get: function () {
                    return this._lines;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._lines) {
                        return;
                    }
                    self._lines = wijmo.asEnum(value, LineMarkerLines);
                    if (!self._marker) {
                        return;
                    }
                    self._resetLinesVisibility();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "interaction", {
                /**
                 * Gets or sets the interaction mode of the LineMarker.
                 */
                get: function () {
                    return this._interaction;
                },
                set: function (value) {
                    var self = this;
                    if (value === self._interaction) {
                        return;
                    }
                    if (self._marker) {
                        self._detach();
                    }
                    self._interaction = wijmo.asEnum(value, LineMarkerInteraction);
                    if (self._marker) {
                        self._attach();
                    }
                    self._toggleElesDraggableClass(self._interaction === LineMarkerInteraction.Drag);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "dragThreshold", {
                /**
                 * Gets or sets the maximum distance from the horizontal or vertical
                 * line that the marker can be dragged.
                 */
                get: function () {
                    return this._dragThreshold;
                },
                set: function (value) {
                    if (value != this._dragThreshold) {
                        this._dragThreshold = wijmo.asNumber(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "dragContent", {
                /**
                 * Gets or sets a value indicating whether the content of the marker
                 * is draggable when the interaction mode is "Drag."
                 */
                get: function () {
                    return this._dragContent;
                },
                set: function (value) {
                    var self = this;
                    if (value !== self._dragContent) {
                        self._dragContent = wijmo.asBoolean(value);
                    }
                    wijmo.toggleClass(self._dragEle, LineMarker._CSS_LINE_DRAGGABLE, self._interaction === LineMarkerInteraction.Drag &&
                        self._dragContent &&
                        self._lines !== LineMarkerLines.None);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(LineMarker.prototype, "dragLines", {
                /**
                 * Gets or sets a value indicating whether the lines are linked
                 * when the horizontal or vertical line is dragged when the
                 * interaction mode is "Drag."
                 */
                get: function () {
                    return this._dragLines;
                },
                set: function (value) {
                    if (value != this._dragLines) {
                        this._dragLines = wijmo.asBoolean(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:positionChanged event.
             *
             * @param point The target point at which to show the LineMarker.
             */
            LineMarker.prototype.onPositionChanged = function (point) {
                this.positionChanged.raise(this, point);
            };
            //--------------------------------------------------------------------------
            //** implementation
            /**
             * Removes the LineMarker from the chart.
             */
            LineMarker.prototype.remove = function () {
                var self = this, chart = self._chart;
                if (self._marker) {
                    chart.rendered.removeHandler(self._initialize, self);
                    self._detach();
                    self._removeMarker();
                    self._wrapperMoveMarker = null;
                    self._wrapperMousedown = null;
                    self._wrapperMouseup = null;
                }
            };
            LineMarker.prototype._attach = function () {
                var self = this, hostElement = self._chart.hostElement;
                if (this._interaction !== LineMarkerInteraction.None) {
                    wijmo.addClass(hostElement, LineMarker._CSS_TOUCH_DISABLED);
                }
                else {
                    wijmo.removeClass(hostElement, LineMarker._CSS_TOUCH_DISABLED);
                }
                lineMarkers.attach(self);
                self._attachDrag();
            };
            LineMarker.prototype._attachDrag = function () {
                var self = this;
                if (self._interaction !== LineMarkerInteraction.Drag) {
                    return;
                }
                if (!self._wrapperMousedown) {
                    self._wrapperMousedown = self._onMousedown.bind(self);
                }
                if (!self._wrapperMouseup) {
                    self._wrapperMouseup = self._onMouseup.bind(self);
                }
                // Drag mode
                self._toggleDragEventAttach(true);
            };
            LineMarker.prototype._detach = function () {
                var self = this;
                wijmo.removeClass(self._chart.hostElement, LineMarker._CSS_TOUCH_DISABLED);
                lineMarkers.detach(self);
                self._detachDrag();
            };
            LineMarker.prototype._detachDrag = function () {
                var self = this;
                if (self._interaction !== LineMarkerInteraction.Drag) {
                    return;
                }
                // Drag mode
                self._toggleDragEventAttach(false);
            };
            LineMarker.prototype._toggleDragEventAttach = function (isAttach) {
                var self = this, chartHostEle = self._chart.hostElement, eventListener = isAttach ? 'addEventListener' : 'removeEventListener';
                chartHostEle[eventListener]('mousedown', self._wrapperMousedown);
                document[eventListener]('mouseup', self._wrapperMouseup);
                if ('ontouchstart' in window) {
                    chartHostEle[eventListener]('touchstart', self._wrapperMousedown);
                }
                if ('ontouchend' in window) {
                    document[eventListener]('touchend', self._wrapperMouseup);
                }
            };
            LineMarker.prototype._onMousedown = function (e) {
                var self = this, pt = self._getEventPoint(e), hRect, vRect, contentRect, isHRectVisible, isVRectVisible;
                if (self._interaction !== LineMarkerInteraction.Drag) {
                    return;
                }
                hRect = wijmo.getElementRect(self._hLine);
                isHRectVisible = !(hRect.width === 0 || hRect.height === 0);
                vRect = wijmo.getElementRect(self._vLine);
                isVRectVisible = !(vRect.width === 0 || vRect.height === 0);
                contentRect = wijmo.getElementRect(self._markerContent);
                if (self._dragContent &&
                    self._pointInRect(pt, contentRect)) {
                    self._capturedEle = self._markerContent;
                    self._contentDragStartPoint = new wijmo.Point(pt.x, pt.y);
                    self._mouseDownCrossPoint = new wijmo.Point(self._targetPoint.x, self._targetPoint.y);
                }
                else if (isHRectVisible && ((Math.abs(hRect.top - pt.y) <= self._dragThreshold) ||
                    (Math.abs(pt.y - hRect.top - hRect.height) <= self._dragThreshold) ||
                    (pt.y >= hRect.top && pt.y <= hRect.top + hRect.height))) {
                    self._capturedEle = self._hLine;
                    self._contentDragStartPoint = undefined;
                    wijmo.addClass(self._chart.hostElement, LineMarker._CSS_LINE_DRAGGABLE);
                }
                else if (isVRectVisible && (Math.abs(vRect.left - pt.x) <= self._dragThreshold ||
                    (Math.abs(pt.x - vRect.left - vRect.width) <= self._dragThreshold) ||
                    (pt.x >= vRect.left && pt.x <= vRect.left + vRect.width))) {
                    self._capturedEle = self._vLine;
                    self._contentDragStartPoint = undefined;
                    wijmo.addClass(self._chart.hostElement, LineMarker._CSS_LINE_DRAGGABLE);
                }
                e.preventDefault();
            };
            LineMarker.prototype._onMouseup = function (e) {
                var self = this, needReAlignment = self._alignment === LineMarkerAlignment.Auto
                    && self._capturedEle === self._markerContent && self._lines !== LineMarkerLines.None;
                self._capturedEle = undefined;
                self._contentDragStartPoint = undefined;
                self._mouseDownCrossPoint = undefined;
                if (needReAlignment) {
                    // because the size of content has changed, so need to adjust the position twice.
                    self._updatePositionByAlignment();
                    self._updatePositionByAlignment();
                }
                wijmo.removeClass(self._chart.hostElement, LineMarker._CSS_LINE_DRAGGABLE);
            };
            LineMarker.prototype._moveMarker = function (e) {
                var self = this, chart = self._chart, point = self._getEventPoint(e), plotRect = self._plotRect, isDragAction = self._interaction === LineMarkerInteraction.Drag, hLineVisible = self._lines === LineMarkerLines.Horizontal, vLineVisible = self._lines === LineMarkerLines.Vertical, seriesIndex = self._seriesIndex, series, offset = wijmo.getElementRect(chart.hostElement), hitTest, xAxis, yAxis, x, y;
                if (!plotRect) {
                    return;
                }
                if (!self._isVisible || self._interaction === LineMarkerInteraction.None ||
                    (self._interaction === LineMarkerInteraction.Drag &&
                        (!self._capturedEle || self._lines === LineMarkerLines.None))) {
                    return;
                }
                if (isDragAction) {
                    if (self._contentDragStartPoint) {
                        point.x = hLineVisible ? self._targetPoint.x :
                            self._mouseDownCrossPoint.x + point.x - self._contentDragStartPoint.x;
                        point.y = vLineVisible ? self._targetPoint.y :
                            self._mouseDownCrossPoint.y + point.y - self._contentDragStartPoint.y;
                    }
                    else if (hLineVisible ||
                        (!self._dragLines && self._capturedEle === self._hLine)) {
                        // horizontal hine dragging
                        point.x = self._targetPoint.x;
                    }
                    else if (vLineVisible ||
                        (!self._dragLines && self._capturedEle === self._vLine)) {
                        // vertical hine dragging
                        point.y = self._targetPoint.y;
                    }
                }
                if ((isDragAction && self._lines === LineMarkerLines.Horizontal) ||
                    (!self._dragLines && self._capturedEle === self._hLine)) {
                    if (point.y <= plotRect.top || point.y >= plotRect.top + plotRect.height) {
                        return;
                    }
                }
                else if ((isDragAction && self._lines === LineMarkerLines.Vertical) ||
                    (!self._dragLines && self._capturedEle === self._vLine)) {
                    if (point.x <= plotRect.left || point.x >= plotRect.left + plotRect.width) {
                        return;
                    }
                }
                else {
                    if (point.x <= plotRect.left || point.y <= plotRect.top
                        || point.x >= plotRect.left + plotRect.width
                        || point.y >= plotRect.top + plotRect.height) {
                        return;
                    }
                }
                if (seriesIndex != null && seriesIndex >= 0 && seriesIndex < chart.series.length) {
                    series = chart.series[seriesIndex];
                    hitTest = series.hitTest(new wijmo.Point(point.x, NaN));
                    if (hitTest == null || hitTest.x == null || hitTest.y == null) {
                        return;
                    }
                    xAxis = series.axisX || chart.axisX;
                    yAxis = series._getAxisY();
                    x = wijmo.isDate(hitTest.x) ? chart_1.FlexChart._toOADate(hitTest.x) : hitTest.x;
                    x = wijmo.isString(x) ? hitTest.pointIndex : x;
                    y = wijmo.isDate(hitTest.y) ? chart_1.FlexChart._toOADate(hitTest.y) : hitTest.y;
                    point.x = xAxis.convert(x) + offset.left;
                    point.y = yAxis.convert(y) + offset.top;
                }
                self._updateMarkerPosition(point);
                e.preventDefault();
            };
            LineMarker.prototype._show = function (ele) {
                var e = ele ? ele : this._marker;
                e.style.display = 'block';
            };
            LineMarker.prototype._hide = function (ele) {
                var e = ele ? ele : this._marker;
                e.style.display = 'none';
            };
            LineMarker.prototype._toggleVisibility = function () {
                this._isVisible ? this._show() : this._hide();
            };
            LineMarker.prototype._resetDefaultValue = function () {
                var self = this;
                self._isVisible = true;
                self._alignment = LineMarkerAlignment.Auto;
                self._lines = LineMarkerLines.None;
                self._interaction = LineMarkerInteraction.None;
                self._horizontalPosition = null;
                self._verticalPosition = null;
                self._content = null;
                self._seriesIndex = null;
                self._dragThreshold = 15;
                self._dragContent = false;
                self._dragLines = false;
                self._targetPoint = new wijmo.Point();
            };
            LineMarker.prototype._initialize = function () {
                var self = this, plot = self._chart.hostElement.querySelector("." + chart_1.FlexChart._CSS_PLOT_AREA), box;
                self._plot = plot;
                if (!self._marker) {
                    self._createMarker();
                }
                if (plot) {
                    self._plotRect = wijmo.getElementRect(plot);
                    box = plot.getBBox();
                    self._plotRect.width = box.width;
                    self._plotRect.height = box.height;
                    self._updateMarkerSize();
                    self._updateLinesSize();
                }
                self._updateMarkerPosition();
                self._wrapperMoveMarker = self._moveMarker.bind(self);
                self._attach();
            };
            LineMarker.prototype._createMarker = function () {
                var self = this, marker, container;
                marker = document.createElement('div');
                wijmo.addClass(marker, LineMarker._CSS_MARKER);
                container = self._getContainer();
                container.appendChild(marker);
                self._markerContainer = container;
                self._marker = marker;
                self._createChildren();
            };
            LineMarker.prototype._removeMarker = function () {
                var self = this, mc = self._markerContainer;
                mc.removeChild(self._marker);
                self._content = null;
                self._hLine = null;
                self._vLine = null;
                if (!mc.hasChildNodes()) {
                    self._chart.hostElement.removeChild(self._markerContainer);
                    self._markerContainer = null;
                }
                self._marker = null;
            };
            LineMarker.prototype._getContainer = function () {
                var container = this._chart.hostElement.querySelector(LineMarker._CSS_MARKER_CONTAINER);
                if (!container) {
                    container = this._createContainer();
                }
                return container;
            };
            LineMarker.prototype._createContainer = function () {
                var markerContainer = document.createElement('div'), hostEle = this._chart.hostElement;
                wijmo.addClass(markerContainer, LineMarker._CSS_MARKER_CONTAINER);
                hostEle.insertBefore(markerContainer, hostEle.firstChild);
                return markerContainer;
            };
            LineMarker.prototype._createChildren = function () {
                var self = this, marker = self._marker, markerContent, hline, vline, dragEle;
                // work around for marker content touchmove: 
                // when the content is dynamic element, the touchmove fire only once.
                dragEle = document.createElement('div');
                dragEle.style.position = 'absolute';
                dragEle.style.height = '100%';
                dragEle.style.width = '100%';
                marker.appendChild(dragEle);
                self._dragEle = dragEle;
                //content
                markerContent = document.createElement('div');
                wijmo.addClass(markerContent, LineMarker._CSS_MARKER_CONTENT);
                marker.appendChild(markerContent);
                self._markerContent = markerContent;
                // lines
                hline = document.createElement('div');
                wijmo.addClass(hline, LineMarker._CSS_MARKER_HLINE);
                marker.appendChild(hline);
                self._hLine = hline;
                vline = document.createElement('div');
                wijmo.addClass(vline, LineMarker._CSS_MARKER_VLINE);
                marker.appendChild(vline);
                self._vLine = vline;
                self._toggleElesDraggableClass(self._interaction === LineMarkerInteraction.Drag);
                self._resetLinesVisibility();
            };
            LineMarker.prototype._toggleElesDraggableClass = function (draggable) {
                var self = this;
                wijmo.toggleClass(self._hLine, LineMarker._CSS_LINE_DRAGGABLE, draggable);
                wijmo.toggleClass(self._vLine, LineMarker._CSS_LINE_DRAGGABLE, draggable);
                wijmo.toggleClass(self._dragEle, LineMarker._CSS_LINE_DRAGGABLE, draggable &&
                    self._dragContent && self._lines !== LineMarkerLines.None);
            };
            LineMarker.prototype._updateMarkerSize = function () {
                var self = this, plotRect = self._plotRect, chartEle = self._chart.hostElement, computedStyle = window.getComputedStyle(chartEle, null), chartRect = wijmo.getElementRect(chartEle);
                if (!self._marker) {
                    return;
                }
                self._marker.style.marginTop = (plotRect.top - chartRect.top - (parseFloat(computedStyle.getPropertyValue('padding-top')) || 0)) + 'px';
                self._marker.style.marginLeft = (plotRect.left - chartRect.left - (parseFloat(computedStyle.getPropertyValue('padding-left')) || 0)) + 'px';
            };
            LineMarker.prototype._updateLinesSize = function () {
                var self = this, plotRect = self._plotRect;
                if (!self._hLine || !self._vLine) {
                    return;
                }
                self._hLine.style.width = plotRect.width + 'px';
                self._vLine.style.height = plotRect.height + 'px';
            };
            LineMarker.prototype._resetLinesVisibility = function () {
                var self = this;
                if (!self._hLine || !self._vLine) {
                    return;
                }
                self._hide(self._hLine);
                self._hide(self._vLine);
                if (self._lines === LineMarkerLines.Horizontal || self._lines === LineMarkerLines.Both) {
                    self._show(self._hLine);
                }
                if (self._lines === LineMarkerLines.Vertical || self._lines === LineMarkerLines.Both) {
                    self._show(self._vLine);
                }
            };
            LineMarker.prototype._updateMarkerPosition = function (point) {
                var self = this, plotRect = self._plotRect, targetPoint = self._targetPoint, x, y, raiseEvent = false, isDragAction = self._interaction === LineMarkerInteraction.Drag;
                if (!self._plot) {
                    return;
                }
                x = plotRect.left + plotRect.width * (self._horizontalPosition || 0);
                y = plotRect.top + plotRect.height * (self._verticalPosition || 0);
                if (self._horizontalPosition == null && point) {
                    x = point.x;
                }
                if (self._verticalPosition == null && point) {
                    y = point.y;
                }
                if (x !== targetPoint.x || y !== targetPoint.y) {
                    raiseEvent = true;
                }
                targetPoint.x = x;
                targetPoint.y = y;
                self._toggleVisibility();
                if (self._content) {
                    self._updateContent();
                }
                if (raiseEvent) {
                    self._raisePositionChanged(x, y);
                }
                // after the content changed(size changed), then update the marker's position
                self._updatePositionByAlignment(point ? true : false);
            };
            LineMarker.prototype._updateContent = function () {
                var self = this, chart = self._chart, point = self._targetPoint, hitTestInfo = chart.hitTest(point), text;
                text = self._content.call(null, hitTestInfo, point);
                self._markerContent.innerHTML = text || '';
            };
            LineMarker.prototype._raisePositionChanged = function (x, y) {
                var plotRect = this._plotRect;
                this.onPositionChanged(new wijmo.Point(x, y));
                //this.onPositionChanged(new Point(x - plotRect.left, y - plotRect.top));
            };
            LineMarker.prototype._updatePositionByAlignment = function (isMarkerMoved) {
                var self = this, align = self._alignment, tp = self._targetPoint, marker = self._marker, topBottom = 0, leftRight = 0, width = marker.clientWidth, height = marker.clientHeight, plotRect = self._plotRect, 
                //offset for right-bottom lnkemarker to avoid mouse overlapping.
                offset = 12;
                if (!self._plot) {
                    return;
                }
                if (!self._capturedEle || (self._capturedEle && self._capturedEle !== self._markerContent)) {
                    if (align === LineMarkerAlignment.Auto) {
                        if ((tp.x + width + offset > plotRect.left + plotRect.width) && (tp.x - width >= 0)) {
                            leftRight = width;
                        }
                        //set default auto to right top.
                        topBottom = height;
                        if (tp.y - height < plotRect.top) {
                            topBottom = 0;
                        }
                    }
                    else {
                        if ((1 & align) === 1) {
                            leftRight = width;
                        }
                        if ((2 & align) === 2) {
                            topBottom = height;
                        }
                    }
                    //only add offset when interaction is move and alignment is right bottom
                    if (self._interaction === LineMarkerInteraction.Move && topBottom === 0 && leftRight === 0) {
                        leftRight = -offset;
                    }
                }
                else {
                    //content dragging: when the content is on top position
                    if (parseInt(self._hLine.style.top) > 0) {
                        topBottom = height;
                    }
                    //content dragging: when the content is on left position
                    if (parseInt(self._vLine.style.left) > 0) {
                        leftRight = width;
                    }
                }
                marker.style.left = (tp.x - leftRight - plotRect.left) + 'px';
                marker.style.top = (tp.y - topBottom - plotRect.top) + 'px';
                self._hLine.style.top = topBottom + 'px';
                self._hLine.style.left = plotRect.left - tp.x + leftRight + 'px';
                self._vLine.style.top = plotRect.top - tp.y + topBottom + 'px';
                self._vLine.style.left = leftRight + 'px';
            };
            LineMarker.prototype._getEventPoint = function (e) {
                return e instanceof MouseEvent ?
                    new wijmo.Point(e.pageX, e.pageY) :
                    new wijmo.Point(e.changedTouches[0].pageX, e.changedTouches[0].pageY);
            };
            LineMarker.prototype._pointInRect = function (pt, rect) {
                if (!pt || !rect) {
                    return false;
                }
                if (pt.x >= rect.left && pt.x <= rect.left + rect.width &&
                    pt.y >= rect.top && pt.y <= rect.top + rect.height) {
                    return true;
                }
                return false;
            };
            LineMarker._CSS_MARKER = 'wj-chart-linemarker';
            LineMarker._CSS_MARKER_HLINE = 'wj-chart-linemarker-hline';
            LineMarker._CSS_MARKER_VLINE = 'wj-chart-linemarker-vline';
            LineMarker._CSS_MARKER_CONTENT = 'wj-chart-linemarker-content';
            LineMarker._CSS_MARKER_CONTAINER = 'wj-chart-linemarker-container';
            LineMarker._CSS_LINE_DRAGGABLE = 'wj-chart-linemarker-draggable';
            LineMarker._CSS_TOUCH_DISABLED = 'wj-flexchart-touch-disabled';
            return LineMarker;
        }());
        chart_1.LineMarker = LineMarker;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        'use strict';
        var _DataPoint = /** @class */ (function () {
            function _DataPoint(seriesIndex, pointIndex, dataX, dataY) {
                this._seriesIndex = seriesIndex;
                this._pointIndex = pointIndex;
                this._dataX = dataX;
                this._dataY = dataY;
            }
            Object.defineProperty(_DataPoint.prototype, "seriesIndex", {
                get: function () {
                    return this._seriesIndex;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DataPoint.prototype, "pointIndex", {
                get: function () {
                    return this._pointIndex;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DataPoint.prototype, "dataX", {
                get: function () {
                    return this._dataX;
                },
                set: function (value) {
                    if (value !== this._dataX) {
                        this._dataX = value;
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(_DataPoint.prototype, "dataY", {
                get: function () {
                    return this._dataY;
                },
                set: function (value) {
                    if (value !== this._dataY) {
                        this._dataY = value;
                    }
                },
                enumerable: true,
                configurable: true
            });
            return _DataPoint;
        }());
        chart_1._DataPoint = _DataPoint;
        var _MeasureOption;
        (function (_MeasureOption) {
            _MeasureOption[_MeasureOption["X"] = 0] = "X";
            _MeasureOption[_MeasureOption["Y"] = 1] = "Y";
            _MeasureOption[_MeasureOption["XY"] = 2] = "XY";
        })(_MeasureOption = chart_1._MeasureOption || (chart_1._MeasureOption = {}));
        var _RectArea = /** @class */ (function () {
            function _RectArea(rect) {
                this._rect = rect;
            }
            Object.defineProperty(_RectArea.prototype, "rect", {
                get: function () {
                    return this._rect;
                },
                enumerable: true,
                configurable: true
            });
            _RectArea.prototype.contains = function (pt) {
                var rect = this._rect;
                return pt.x >= rect.left && pt.x <= rect.right && pt.y >= rect.top && pt.y <= rect.bottom;
            };
            _RectArea.prototype.pointDistance = function (pt1, pt2, option) {
                var dx = pt2.x - pt1.x;
                var dy = pt2.y - pt1.y;
                if (option == _MeasureOption.X) {
                    return Math.abs(dx);
                }
                else if (option == _MeasureOption.Y) {
                    return Math.abs(dy);
                }
                return Math.sqrt(dx * dx + dy * dy);
            };
            _RectArea.prototype.distance = function (pt) {
                var option = _MeasureOption.XY;
                if (pt.x === null) {
                    option = _MeasureOption.Y;
                }
                else if (pt.y === null) {
                    option = _MeasureOption.X;
                }
                var rect = this._rect;
                if (pt.x < rect.left) {
                    if (pt.y < rect.top) {
                        return this.pointDistance(pt, new wijmo.Point(rect.left, rect.top), option);
                    }
                    else if (pt.y > rect.bottom) {
                        return this.pointDistance(pt, new wijmo.Point(rect.left, rect.bottom), option);
                    }
                    else {
                        if (option == _MeasureOption.Y) {
                            return 0;
                        }
                        return rect.left - pt.x;
                    }
                }
                else if (pt.x > rect.right) {
                    if (pt.y < rect.top) {
                        return this.pointDistance(pt, new wijmo.Point(rect.right, rect.top), option);
                    }
                    else if (pt.y > rect.bottom) {
                        return this.pointDistance(pt, new wijmo.Point(rect.right, rect.bottom), option);
                    }
                    else {
                        if (option == _MeasureOption.Y) {
                            return 0;
                        }
                        return pt.x - rect.right;
                    }
                }
                else {
                    if (option == _MeasureOption.X) {
                        return 0;
                    }
                    if (pt.y < rect.top) {
                        return rect.top - pt.y;
                    }
                    else if (pt.y > rect.bottom) {
                        return pt.y - rect.bottom;
                    }
                    else {
                        return 0;
                    }
                }
            };
            return _RectArea;
        }());
        chart_1._RectArea = _RectArea;
        var _CircleArea = /** @class */ (function () {
            function _CircleArea(center, radius) {
                this._center = center;
                this.setRadius(radius);
            }
            _CircleArea.prototype.setRadius = function (radius) {
                this._rad = radius;
                this._rad2 = radius * radius;
            };
            Object.defineProperty(_CircleArea.prototype, "center", {
                get: function () {
                    return this._center;
                },
                enumerable: true,
                configurable: true
            });
            _CircleArea.prototype.contains = function (pt) {
                var dx = this._center.x - pt.x;
                var dy = this._center.y - pt.y;
                return dx * dx + dy * dy <= this._rad2;
            };
            _CircleArea.prototype.distance = function (pt) {
                //var dx = pt.x !== null ? this._center.x - pt.x : 0;
                //var dy = pt.y !== null ? this._center.y - pt.y : 0;
                var dx = !isNaN(pt.x) ? this._center.x - pt.x : 0;
                var dy = !isNaN(pt.y) ? this._center.y - pt.y : 0;
                var d2 = dx * dx + dy * dy;
                if (d2 <= this._rad2)
                    return 0;
                else
                    return Math.sqrt(d2) - this._rad;
            };
            return _CircleArea;
        }());
        chart_1._CircleArea = _CircleArea;
        var _LinesArea = /** @class */ (function () {
            function _LinesArea(x, y) {
                this._x = [];
                this._y = [];
                this._x = x;
                this._y = y;
            }
            _LinesArea.prototype.contains = function (pt) {
                return false;
            };
            _LinesArea.prototype.distance = function (pt) {
                var dmin = NaN;
                for (var i = 0; i < this._x.length - 1; i++) {
                    var d = chart_1.FlexChart._dist(pt, new wijmo.Point(this._x[i], this._y[i]), new wijmo.Point(this._x[i + 1], this._y[i + 1]));
                    if (isNaN(dmin) || d < dmin) {
                        dmin = d;
                    }
                }
                return dmin;
            };
            return _LinesArea;
        }());
        chart_1._LinesArea = _LinesArea;
        var _HitResult = /** @class */ (function () {
            function _HitResult() {
            }
            return _HitResult;
        }());
        chart_1._HitResult = _HitResult;
        var _HitTester = /** @class */ (function () {
            //private _areas = new Array<IHitArea>();
            function _HitTester(chart) {
                this._map = {};
                this._chart = chart;
            }
            _HitTester.prototype.add = function (area, seriesIndex) {
                if (this._map[seriesIndex]) {
                    if (!area.tag) {
                        area.tag = new _DataPoint(seriesIndex, NaN, NaN, NaN);
                    }
                    this._map[seriesIndex].push(area);
                }
            };
            _HitTester.prototype.clear = function () {
                this._map = {};
                var series = this._chart.series;
                for (var i = 0; i < series.length; i++) {
                    if (series[i].hitTest === chart_1.Series.prototype.hitTest) {
                        this._map[i] = new Array();
                    }
                }
            };
            _HitTester.prototype.hitTest = function (pt, testLines) {
                if (testLines === void 0) { testLines = false; }
                var closest = null;
                var dist = Number.MAX_VALUE;
                var series = this._chart.series;
                for (var key = series.length - 1; key >= 0; key--) {
                    //for (var key in this._map) {
                    var areas = this._map[key];
                    if (areas) {
                        var len = areas.length;
                        for (var i = len - 1; i >= 0; i--) {
                            var area = areas[i];
                            if (wijmo.tryCast(area, _LinesArea) && !testLines) {
                                continue;
                            }
                            var d = area.distance(pt);
                            if (d < dist) {
                                dist = d;
                                closest = area;
                                if (dist == 0)
                                    break;
                            }
                        }
                        if (dist == 0)
                            break;
                    }
                }
                if (closest) {
                    var hr = new _HitResult();
                    hr.area = closest;
                    hr.distance = dist;
                    return hr;
                }
                return null;
            };
            _HitTester.prototype.hitTestSeries = function (pt, seriesIndex) {
                var closest = null;
                var dist = Number.MAX_VALUE;
                var areas = this._map[seriesIndex];
                if (areas) {
                    var len = areas.length;
                    for (var i = len - 1; i >= 0; i--) {
                        var area = areas[i];
                        var d = area.distance(pt);
                        if (d < dist) {
                            dist = d;
                            closest = area;
                            if (dist == 0)
                                break;
                        }
                    }
                }
                if (closest) {
                    var hr = new _HitResult();
                    hr.area = closest;
                    hr.distance = dist;
                    return hr;
                }
                return null;
            };
            return _HitTester;
        }());
        chart_1._HitTester = _HitTester;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Base class for chart plotters of all types (bar, line, area).
         */
        var _BasePlotter = /** @class */ (function () {
            function _BasePlotter() {
                this._DEFAULT_WIDTH = 2;
                this._DEFAULT_SYM_SIZE = 10;
                this.clipping = true;
            }
            _BasePlotter.prototype.clear = function () {
                this.seriesCount = 0;
                this.seriesIndex = 0;
            };
            _BasePlotter.prototype._renderLabels = function (engine, series, smap, chart, lblAreas) {
                var len = smap.length, lbl = chart.dataLabel, bdr = lbl.border, offset = lbl.offset, line = lbl.connectingLine, marg = 2;
                if (offset === undefined) {
                    offset = line ? 16 : 0;
                }
                if (bdr) {
                    offset -= marg;
                }
                for (var j = 0; j < len; j++) {
                    var map = smap[j];
                    var dp = wijmo.asType(map.tag, chart_1._DataPoint, true);
                    if (dp) {
                        this._renderLabel(engine, map, dp, chart, lbl, series, offset, lblAreas);
                    }
                }
            };
            _BasePlotter.prototype._renderLabel = function (engine, map, dp, chart, lbl, series, offset, lblAreas) {
                var pos = lbl.position == null ? chart_1.LabelPosition.Top : lbl.position, bdr = lbl.border, line = lbl.connectingLine, marg = 2;
                var ht = new chart_1.HitTestInfo(chart, pt);
                ht._setDataPoint(dp);
                var s = chart._getLabelContent(ht, lbl.content);
                var pt = this._getLabelPoint(series, dp);
                this._getPointAndPosition(pt, pos, map, chart);
                if (!chart._plotRect.contains(pt)) {
                    return;
                }
                var ea = new chart_1.DataLabelRenderEventArgs(engine, ht, pt, s);
                if (lbl.onRendering) {
                    if (lbl.onRendering(ea)) {
                        s = ea.text;
                        pt = ea.point;
                    }
                    else {
                        s = null;
                    }
                }
                if (s) {
                    var lrct = this._renderLabelAndBorder(engine, s, pos, offset, pt, line, marg, bdr);
                    if (lrct) {
                        var area = new chart_1._RectArea(lrct);
                        area.tag = dp;
                        lblAreas.push(area);
                    }
                }
            };
            _BasePlotter.prototype._getPointAndPosition = function (pt, pos, map, chart) {
                if (map instanceof chart_1._RectArea) {
                    var ra = map;
                    if (chart._isRotated()) {
                        pt.y = ra.rect.top + 0.5 * ra.rect.height;
                    }
                    else {
                        pt.x = ra.rect.left + 0.5 * ra.rect.width;
                    }
                }
            };
            _BasePlotter.prototype._getLabelPoint = function (series, dataPoint) {
                var ax = series._getAxisX(), ay = series._getAxisY();
                return new wijmo.Point(ax.convert(dataPoint.dataX), ay.convert(dataPoint.dataY));
            };
            _BasePlotter.prototype._renderLabelAndBorder = function (engine, s, pos, offset, pt, line, marg, border) {
                var lrct, lcss = 'wj-data-label', clcss = 'wj-data-label-line', bcss = 'wj-data-label-border';
                switch (pos) {
                    case chart_1.LabelPosition.Top: {
                        if (line) {
                            engine.drawLine(pt.x, pt.y, pt.x, pt.y - offset, clcss);
                        }
                        pt.y -= marg + offset;
                        lrct = chart_1.FlexChart._renderText(engine, s, pt, 1, 2, lcss);
                        break;
                    }
                    case chart_1.LabelPosition.Bottom: {
                        if (line) {
                            engine.drawLine(pt.x, pt.y, pt.x, pt.y + offset, clcss);
                        }
                        pt.y += marg + offset;
                        lrct = chart_1.FlexChart._renderText(engine, s, pt, 1, 0, lcss);
                        break;
                    }
                    case chart_1.LabelPosition.Left: {
                        if (line) {
                            engine.drawLine(pt.x, pt.y, pt.x - offset, pt.y, clcss);
                        }
                        pt.x -= marg + offset;
                        lrct = chart_1.FlexChart._renderText(engine, s, pt, 2, 1, lcss);
                        break;
                    }
                    case chart_1.LabelPosition.Right: {
                        if (line) {
                            engine.drawLine(pt.x, pt.y, pt.x + offset, pt.y, clcss);
                        }
                        pt.x += marg + offset;
                        lrct = chart_1.FlexChart._renderText(engine, s, pt, 0, 1, lcss);
                        break;
                    }
                    case chart_1.LabelPosition.Center:
                        lrct = chart_1.FlexChart._renderText(engine, s, pt, 1, 1, lcss);
                        break;
                }
                if (border && lrct) {
                    engine.drawRect(lrct.left - marg, lrct.top - marg, lrct.width + 2 * marg, lrct.height + 2 * marg, bcss);
                }
                return lrct;
            };
            _BasePlotter.prototype.getOption = function (name, parent) {
                var options = this.chart.options;
                if (parent) {
                    options = options ? options[parent] : null;
                }
                if (options && !wijmo.isUndefined(options[name]) && options[name] !== null) {
                    return options[name];
                }
                return undefined;
            };
            _BasePlotter.prototype.getNumOption = function (name, parent) {
                var options = this.chart.options;
                if (parent) {
                    options = options ? options[parent] : null;
                }
                if (options && options[name]) {
                    return wijmo.asNumber(options[name], true);
                }
                return undefined;
            };
            _BasePlotter.cloneStyle = function (style, ignore) {
                if (!style) {
                    return style;
                }
                var newStyle = {};
                for (var key in style) {
                    if (ignore && ignore.indexOf(key) >= 0) {
                        continue;
                    }
                    newStyle[key] = style[key];
                }
                return newStyle;
            };
            _BasePlotter.prototype.isValid = function (datax, datay, ax, ay) {
                return chart_1._DataInfo.isValid(datax) && chart_1._DataInfo.isValid(datay) &&
                    chart_1.FlexChart._contains(this.chart._plotRect, new wijmo.Point(datax, datay));
            };
            _BasePlotter.prototype.load = function () {
            };
            _BasePlotter.prototype.unload = function () {
            };
            return _BasePlotter;
        }());
        chart_1._BasePlotter = _BasePlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Bar/column chart plotter.
         */
        var _BarPlotter = /** @class */ (function (_super) {
            __extends(_BarPlotter, _super);
            function _BarPlotter() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this.origin = 0;
                _this.width = 0.7;
                //isColumn = false;
                _this.isVolume = false;
                _this._volHelper = null;
                _this.stackPosMap = {}; //{ [key: number]: number } = {};
                _this.stackNegMap = {}; // { [key: number]: number } = {};
                _this.stacking = chart.Stacking.None;
                return _this;
            }
            _BarPlotter.prototype.clear = function () {
                _super.prototype.clear.call(this);
                this.stackNegMap[this.chart.axisY._uniqueId] = {};
                this.stackPosMap[this.chart.axisY._uniqueId] = {};
                this._volHelper = null;
            };
            _BarPlotter.prototype.load = function () {
                _super.prototype.load.call(this);
                if (!this.isVolume) {
                    return;
                }
                var series, ax, ct, vols, dt, i, xvals, itemsSource, xmin = null, xmax = null;
                // loop through series collection
                for (i = 0; i < this.chart.series.length; i++) {
                    series = this.chart.series[i];
                    dt = series.getDataType(1) || series.chart._xDataType;
                    ax = series._getAxisX();
                    // get volume data based on chart type
                    ct = series._getChartType();
                    ct = ct === null || wijmo.isUndefined(ct) ? this.chart._getChartType() : ct;
                    if (ct === chart.ChartType.Column) {
                        var sep = this.chart ? this.chart._bindingSeparator : ',';
                        var len = series.binding.split(sep).length - 1;
                        vols = series._getBindingValues(len);
                    }
                    else if (ct === chart.ChartType.Candlestick) {
                        vols = series._getBindingValues(4);
                    }
                    else {
                        vols = null;
                    }
                    // get x values directly for dates, otherwise get from dataInfo
                    if (dt === wijmo.DataType.Date) {
                        var date;
                        xvals = [];
                        itemsSource = [];
                        for (i = 0; i < series._getLength(); i++) {
                            date = series._getItem(i)[series.bindingX].valueOf();
                            xvals.push(date);
                            itemsSource.push({
                                value: date,
                                text: wijmo.Globalize.format(new Date(date), ax.format || "d")
                            });
                        }
                    }
                    else {
                        xvals = this.dataInfo.getXVals();
                    }
                    xmin = this.dataInfo.getMinX();
                    xmax = this.dataInfo.getMaxX();
                    if (vols && vols.length > 0) {
                        this._volHelper = new chart._VolumeHelper(vols, xvals, xmin, xmax, dt);
                        ax._customConvert = this._volHelper.convert.bind(this._volHelper);
                        ax._customConvertBack = this._volHelper.convertBack.bind(this._volHelper);
                        if (itemsSource && itemsSource.length > 0) {
                            this._itemsSource = ax.itemsSource = itemsSource;
                        }
                        break; // only one set of volume data is supported per chart
                    }
                }
            };
            _BarPlotter.prototype.unload = function () {
                _super.prototype.unload.call(this);
                var series, ax;
                for (var i = 0; i < this.chart.series.length; i++) {
                    series = this.chart.series[i];
                    ax = series._getAxisX();
                    if (ax) {
                        ax._customConvert = null;
                        ax._customConvertBack = null;
                        if (ax.itemsSource && ax.itemsSource == this._itemsSource) {
                            this._itemsSource = ax.itemsSource = null;
                        }
                    }
                }
            };
            _BarPlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                this.dataInfo = dataInfo;
                var xmin = dataInfo.getMinX();
                var xmax = dataInfo.getMaxX();
                var ymin = dataInfo.getMinY();
                var ymax = dataInfo.getMaxY();
                var dx = dataInfo.getDeltaX();
                if (dx <= 0) {
                    dx = 1;
                }
                // init/cleanup volume conversions for x-axis based on ChartType/FinancialChartType mappings
                if (this.isVolume && (this.chart._getChartType() === chart.ChartType.Column || this.chart._getChartType() === chart.ChartType.Candlestick)) {
                    this.load();
                }
                else {
                    this.unload();
                }
                //for range bar, stack is not supported yet.
                for (var i = 0; i < this.chart.series.length; i++) {
                    var series = this.chart.series[i];
                    var ct = series._getChartType();
                    ct = ct === null || wijmo.isUndefined(ct) ? this.chart._getChartType() : ct;
                    if (ct === chart.ChartType.Column || ct === chart.ChartType.Bar) {
                        var isRange = this._isRange(series);
                        if (isRange) {
                            var vals = series._getBindingValues(1);
                            vals.forEach(function (v) {
                                if (v < ymin) {
                                    ymin = v;
                                }
                                else if (v > ymax) {
                                    ymax = v;
                                }
                            });
                        }
                    }
                }
                if (this.rotated) {
                    if (!this.chart.axisY.logBase && dataInfo.getDataTypeY() !== wijmo.DataType.Date) {
                        if (this.origin > ymax) {
                            ymax = this.origin;
                        }
                        else if (this.origin < ymin) {
                            ymin = this.origin;
                        }
                    }
                    return new wijmo.Rect(ymin, xmin - 0.5 * dx, ymax - ymin, xmax - xmin + dx);
                }
                else {
                    if (!this.chart.axisY.logBase && dataInfo.getDataTypeY() !== wijmo.DataType.Date) {
                        if (this.origin > ymax) {
                            ymax = this.origin;
                        }
                        else if (this.origin < ymin) {
                            ymin = this.origin;
                        }
                    }
                    return new wijmo.Rect(xmin - 0.5 * dx, ymin, xmax - xmin + dx, ymax - ymin);
                }
            };
            _BarPlotter.prototype._isRange = function (series) {
                var sep = this.chart ? this.chart._bindingSeparator : ',';
                var seps = series.binding == null ? '' : series.binding.split(sep);
                var len = seps.length - 1;
                var isRange = this.isVolume ? len === 2 : len === 1;
                return isRange;
            };
            _BarPlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser, customRender) {
                var points = [];
                var si = this.chart.series.indexOf(series);
                var ser = wijmo.asType(series, chart.SeriesBase);
                var options = this.chart.options;
                var cw = this.width;
                var wpx = 0;
                iser = iser || 0;
                nser = nser || 1;
                if (options && options.groupWidth) {
                    var gw = options.groupWidth;
                    if (wijmo.isNumber(gw)) {
                        // px
                        var gwn = wijmo.asNumber(gw);
                        if (isFinite(gwn) && gwn > 0) {
                            wpx = gwn;
                            cw = 1;
                        }
                    }
                    else if (wijmo.isString(gw)) {
                        var gws = wijmo.asString(gw);
                        // %
                        if (gws && gws.indexOf('%') >= 0) {
                            gws = gws.replace('%', '');
                            var gwn = parseFloat(gws);
                            if (isFinite(gwn)) {
                                if (gwn < 0) {
                                    gwn = 0;
                                }
                                else if (gwn > 100) {
                                    gwn = 100;
                                }
                                wpx = 0;
                                cw = gwn / 100;
                            }
                        }
                        else {
                            // px
                            var gwn = parseFloat(gws);
                            if (isFinite(gwn) && gwn > 0) {
                                wpx = gwn;
                                cw = 1;
                            }
                        }
                    }
                }
                var w = cw / nser; // this.seriesCount;
                var axid = ser._getAxisY()._uniqueId;
                var stackNeg = this.stackNegMap[axid];
                var stackPos = this.stackPosMap[axid];
                var yvals = series.getValues(0);
                var xvals = series.getValues(1);
                var isRange = this._isRange(ser);
                var rangeData = ser._bindValues(ser._cv == null ? (this.chart.collectionView == null ? null : this.chart.collectionView.items) :
                    ser._cv.items, ser._getBinding(1)).values;
                if (!yvals) {
                    return;
                }
                if (!xvals) {
                    xvals = this.dataInfo.getXVals();
                }
                if (xvals) {
                    // find minimal distance between point and use it as column width
                    var delta = this.dataInfo.getDeltaX();
                    if (delta > 0) {
                        cw *= delta;
                        w *= delta;
                    }
                }
                // set series fill and stroke from style
                var fill = ser._getSymbolFill(si), altFill = ser._getAltSymbolFill(si) || fill, stroke = ser._getSymbolStroke(si), altStroke = ser._getAltSymbolStroke(si) || stroke;
                var len = yvals.length;
                if (xvals != null) {
                    len = Math.min(len, xvals.length);
                }
                var origin = this.origin;
                //var symClass = FlexChart._CSS_SERIES_ITEM;
                var itemIndex = 0, currentFill, currentStroke;
                var stacked = this.stacking != chart.Stacking.None;
                var stacked100 = this.stacking == chart.Stacking.Stacked100pc;
                if (ser._getChartType() !== undefined) {
                    stacked = stacked100 = false;
                }
                if (!this.rotated) {
                    if (origin < ay.actualMin) {
                        origin = ay.actualMin;
                    }
                    else if (origin > ay.actualMax) {
                        origin = ay.actualMax;
                    }
                    var originScreen = ay.convert(origin), xmin = ax.actualMin, xmax = ax.actualMax;
                    if (ser._isCustomAxisY()) {
                        stacked = stacked100 = false;
                    }
                    for (var i = 0; i < len; i++) {
                        var oriScreen = originScreen;
                        var datax = xvals ? xvals[i] : i;
                        var datay = yvals[i];
                        if (this._getSymbolOrigin) {
                            oriScreen = ay.convert(this._getSymbolOrigin(origin, i, len));
                        }
                        if (isRange && rangeData && rangeData.length) {
                            var rangeVal = rangeData[i];
                            if (chart._DataInfo.isValid(rangeVal)) {
                                oriScreen = ay.convert(rangeVal);
                            }
                        }
                        if (this._getSymbolStyles) {
                            var style = this._getSymbolStyles(i, len);
                            fill = style && style.fill ? style.fill : fill;
                            altFill = style && style.fill ? style.fill : altFill;
                            stroke = style && style.stroke ? style.stroke : stroke;
                            altStroke = style && style.stroke ? style.stroke : altStroke;
                        }
                        // apply fill and stroke
                        currentFill = datay > 0 ? fill : altFill;
                        currentStroke = datay > 0 ? stroke : altStroke;
                        engine.fill = currentFill;
                        engine.stroke = currentStroke;
                        if (chart._DataInfo.isValid(datax) && chart._DataInfo.isValid(datay)) {
                            if (stacked) {
                                var x0 = datax - 0.5 * cw, x1 = datax + 0.5 * cw;
                                if ((x0 < xmin && x1 < xmin) || (x0 > xmax && x1 > xmax)) {
                                    continue;
                                }
                                x0 = ax.convert(x0);
                                x1 = ax.convert(x1);
                                if (!chart._DataInfo.isValid(x0) || !chart._DataInfo.isValid(x1)) {
                                    continue;
                                }
                                var y0, y1;
                                if (stacked100) {
                                    var sumabs = this.dataInfo.getStackedAbsSum(datax);
                                    datay = datay / sumabs;
                                }
                                var sum = 0;
                                if (datay > 0) {
                                    sum = isNaN(stackPos[datax]) ? 0 : stackPos[datax];
                                    y0 = ay.convert(sum);
                                    y1 = ay.convert(sum + datay);
                                    stackPos[datax] = sum + datay;
                                }
                                else {
                                    sum = isNaN(stackNeg[datax]) ? 0 : stackNeg[datax];
                                    y0 = ay.convert(sum);
                                    y1 = ay.convert(sum + datay);
                                    stackNeg[datax] = sum + datay;
                                }
                                if (customRender) {
                                    points.push(new wijmo.Point(ax.convert(datax), y1));
                                }
                                var rect = new wijmo.Rect(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0), Math.abs(y1 - y0));
                                if (wpx > 0) {
                                    var ratio = 1 - wpx / rect.width;
                                    if (ratio < 0) {
                                        ratio = 0;
                                    }
                                    var xc = rect.left + 0.5 * rect.width;
                                    rect.left += (xc - rect.left) * ratio;
                                    rect.width = Math.min(wpx, rect.width);
                                }
                                var area = new chart._RectArea(rect);
                                //engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle);
                                this.drawSymbol(engine, rect, series, i, new wijmo.Point(rect.left + 0.5 * rect.width, y1));
                                series._setPointIndex(i, itemIndex);
                                itemIndex++;
                                area.tag = new chart._DataPoint(si, i, datax, sum + datay);
                                this.hitTester.add(area, si);
                            }
                            else {
                                var x0 = datax - 0.5 * cw + iser * w, x1 = datax - 0.5 * cw + (iser + 1) * w;
                                if ((x0 < xmin && x1 < xmin) || (x0 > xmax && x1 > xmax)) {
                                    continue;
                                }
                                x0 = ax.convert(x0);
                                x1 = ax.convert(x1);
                                if (!chart._DataInfo.isValid(x0) || !chart._DataInfo.isValid(x1)) {
                                    continue;
                                }
                                var y = ay.convert(datay), rect = new wijmo.Rect(Math.min(x0, x1), Math.min(y, oriScreen), Math.abs(x1 - x0), Math.abs(oriScreen - y));
                                if (customRender) {
                                    points.push(new wijmo.Point((x0 + x1) / 2, y));
                                }
                                if (wpx > 0) {
                                    var sw = wpx / nser;
                                    var ratio = 1 - sw / rect.width;
                                    if (ratio < 0) {
                                        ratio = 0;
                                    }
                                    var xc = ax.convert(datax);
                                    rect.left += (xc - rect.left) * ratio;
                                    rect.width = Math.min(sw, rect.width);
                                }
                                var area = new chart._RectArea(rect);
                                //engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle);
                                this.drawSymbol(engine, rect, series, i, new wijmo.Point(rect.left + 0.5 * rect.width, y));
                                series._setPointIndex(i, itemIndex);
                                itemIndex++;
                                area.tag = new chart._DataPoint(si, i, datax, datay);
                                this.hitTester.add(area, si);
                            }
                        }
                    }
                }
                else {
                    if (origin < ax.actualMin) {
                        origin = ax.actualMin;
                    }
                    else if (origin > ax.actualMax) {
                        origin = ax.actualMax;
                    }
                    if (ser._isCustomAxisY()) {
                        stacked = stacked100 = false;
                    }
                    var originScreen = ax.convert(origin), ymin = ay.actualMin, ymax = ay.actualMax;
                    for (var i = 0; i < len; i++) {
                        var datax = xvals ? xvals[i] : i, datay = yvals[i];
                        var oriScreen = originScreen;
                        if (this._getSymbolOrigin) {
                            oriScreen = ay.convert(this._getSymbolOrigin(origin, i));
                        }
                        if (isRange && rangeData && rangeData.length) {
                            var rangeVal = rangeData[i];
                            if (chart._DataInfo.isValid(rangeVal)) {
                                oriScreen = ax.convert(rangeVal);
                            }
                        }
                        if (this._getSymbolStyles) {
                            var style = this._getSymbolStyles(i);
                            fill = style && style.fill ? style.fill : fill;
                            altFill = style && style.fill ? style.fill : altFill;
                            stroke = style && style.stroke ? style.fill : stroke;
                            altStroke = style && style.stroke ? style.fill : altStroke;
                        }
                        // apply fill and stroke
                        currentFill = datay > 0 ? fill : altFill;
                        currentStroke = datay > 0 ? stroke : altStroke;
                        engine.fill = currentFill;
                        engine.stroke = currentStroke;
                        if (chart._DataInfo.isValid(datax) && chart._DataInfo.isValid(datay)) {
                            if (stacked) {
                                var y0 = datax - 0.5 * cw, y1 = datax + 0.5 * cw;
                                if ((y0 < ymin && y1 < ymin) || (y0 > ymax && y1 > ymax)) {
                                    continue;
                                }
                                y0 = ay.convert(Math.max(y0, ymin));
                                y1 = ay.convert(Math.min(y1, ymax));
                                var x0, x1;
                                if (stacked100) {
                                    var sumabs = this.dataInfo.getStackedAbsSum(datax);
                                    datay = datay / sumabs;
                                }
                                var sum = 0;
                                if (datay > 0) {
                                    sum = isNaN(stackPos[datax]) ? 0 : stackPos[datax];
                                    x0 = ax.convert(sum);
                                    x1 = ax.convert(sum + datay);
                                    stackPos[datax] = sum + datay;
                                }
                                else {
                                    sum = isNaN(stackNeg[datax]) ? 0 : stackNeg[datax];
                                    x0 = ax.convert(sum);
                                    x1 = ax.convert(sum + datay);
                                    stackNeg[datax] = sum + datay;
                                }
                                if (customRender) {
                                    points.push(new wijmo.Point(x1, ay.convert(datax)));
                                }
                                var rect = new wijmo.Rect(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0), Math.abs(y1 - y0));
                                if (wpx > 0) {
                                    var ratio = 1 - wpx / rect.height;
                                    if (ratio < 0) {
                                        ratio = 0;
                                    }
                                    var yc = rect.top + 0.5 * rect.height;
                                    rect.top += (yc - rect.top) * ratio;
                                    rect.height = Math.min(wpx, rect.height);
                                }
                                var area = new chart._RectArea(rect);
                                //engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle);
                                this.drawSymbol(engine, rect, series, i, new wijmo.Point(x1, rect.top + 0.5 * rect.height));
                                series._setPointIndex(i, itemIndex);
                                itemIndex++;
                                area.tag = new chart._DataPoint(si, i, sum + datay, datax);
                                this.hitTester.add(area, si);
                            }
                            else {
                                var y0 = datax - 0.5 * cw + iser * w, y1 = datax - 0.5 * cw + (iser + 1) * w;
                                if ((y0 < ymin && y1 < ymin) || (y0 > ymax && y1 > ymax)) {
                                    continue;
                                }
                                y0 = ay.convert(Math.max(y0, ymin));
                                y1 = ay.convert(Math.min(y1, ymax));
                                var x = ax.convert(datay), rect = new wijmo.Rect(Math.min(x, oriScreen), Math.min(y0, y1), Math.abs(oriScreen - x), Math.abs(y1 - y0));
                                if (customRender) {
                                    points.push(new wijmo.Point(x, (y0 + y1) / 2));
                                }
                                if (wpx > 0) {
                                    var sw = wpx / nser;
                                    var ratio = 1 - sw / rect.height;
                                    if (ratio < 0) {
                                        ratio = 0;
                                    }
                                    var yc = ay.convert(datax);
                                    rect.top += (yc - rect.top) * ratio;
                                    rect.height = Math.min(sw, rect.height);
                                }
                                var area = new chart._RectArea(rect);
                                //engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle);
                                this.drawSymbol(engine, rect, series, i, new wijmo.Point(x, rect.top + 0.5 * rect.height));
                                series._setPointIndex(i, itemIndex);
                                itemIndex++;
                                area.tag = new chart._DataPoint(si, i, datay, datax);
                                this.hitTester.add(area, si);
                            }
                        }
                    }
                }
                if (customRender && points && points.length) {
                    customRender(points);
                }
            };
            _BarPlotter.prototype.drawSymbol = function (engine, rect, series, pointIndex, point) {
                var _this = this;
                if (this.chart.itemFormatter) {
                    engine.startGroup();
                    var hti = new chart.HitTestInfo(this.chart, point, chart.ChartElement.SeriesSymbol);
                    hti._setData(series, pointIndex);
                    this.chart.itemFormatter(engine, hti, function () {
                        _this.drawDefaultSymbol(engine, rect, series);
                    });
                    engine.endGroup();
                }
                else {
                    this.drawDefaultSymbol(engine, rect, series);
                }
            };
            _BarPlotter.prototype.drawDefaultSymbol = function (engine, rect, series) {
                engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle /* ,'plotRect'*/);
            };
            return _BarPlotter;
        }(chart._BasePlotter));
        chart._BarPlotter = _BarPlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Line/scatter chart plotter.
         */
        var _LinePlotter = /** @class */ (function (_super) {
            __extends(_LinePlotter, _super);
            function _LinePlotter() {
                var _this = _super.call(this) || this;
                _this.hasSymbols = false;
                _this.hasLines = true;
                _this.isSpline = false;
                _this.stacking = chart.Stacking.None;
                _this.stackPos = {};
                _this.stackNeg = {};
                _this.clipping = false;
                return _this;
            }
            _LinePlotter.prototype.clear = function () {
                _super.prototype.clear.call(this);
                this.stackNeg = {};
                this.stackPos = {};
            };
            _LinePlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                this.dataInfo = dataInfo;
                var xmin = dataInfo.getMinX();
                var ymin = dataInfo.getMinY();
                var xmax = dataInfo.getMaxX();
                var ymax = dataInfo.getMaxY();
                if (this.isSpline && !this.chart.axisY.logBase) {
                    var dy = 0.1 * (ymax - ymin);
                    ymin -= dy;
                    ymax += dy;
                }
                return this.rotated
                    ? new wijmo.Rect(ymin, xmin, ymax - ymin, xmax - xmin)
                    : new wijmo.Rect(xmin, ymin, xmax - xmin, ymax - ymin);
            };
            _LinePlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser, customRender) {
                var points = [];
                var ser = wijmo.asType(series, chart.SeriesBase);
                var si = this.chart.series.indexOf(series);
                //if (iser == 0) {
                //    this.stackNeg = {};
                //    this.stackPos = {};
                //}
                var ys = series.getValues(0);
                var xs = series.getValues(1);
                if (!ys) {
                    return;
                }
                if (!xs) {
                    xs = this.dataInfo.getXVals();
                }
                var style = chart._BasePlotter.cloneStyle(series.style, ['fill']);
                var len = ys.length;
                var hasXs = true;
                if (!xs) {
                    hasXs = false;
                    xs = new Array(len);
                }
                else {
                    len = Math.min(len, xs.length);
                }
                var swidth = this._DEFAULT_WIDTH, fill = ser._getSymbolFill(si), altFill = ser._getAltSymbolFill(si) || fill, stroke = ser._getSymbolStroke(si), altStroke = ser._getAltSymbolStroke(si) || stroke, symSize = ser._getSymbolSize();
                engine.stroke = stroke;
                engine.strokeWidth = swidth;
                engine.fill = fill;
                var xvals = new Array();
                var yvals = new Array();
                var isNullVals = new Array();
                var rotated = this.rotated;
                var stacked = this.stacking != chart.Stacking.None && !ser._isCustomAxisY();
                var stacked100 = this.stacking == chart.Stacking.Stacked100pc && !ser._isCustomAxisY();
                if (ser._getChartType() !== undefined) {
                    stacked = stacked100 = false;
                }
                var interpolateNulls = this.chart.interpolateNulls;
                var hasNulls = false;
                //var symClass = FlexChart._CSS_SERIES_ITEM;
                for (var i = 0; i < len; i++) {
                    var datax = hasXs ? xs[i] : i;
                    var datay = ys[i];
                    if (chart._DataInfo.isValid(datax) && chart._DataInfo.isValid(datay)) {
                        if (stacked) {
                            if (stacked100) {
                                var sumabs = this.dataInfo.getStackedAbsSum(datax);
                                datay = datay / sumabs;
                            }
                            if (datay >= 0) {
                                var sum = isNaN(this.stackPos[datax]) ? 0 : this.stackPos[datax];
                                datay = this.stackPos[datax] = sum + datay;
                            }
                            else {
                                var sum = isNaN(this.stackNeg[datax]) ? 0 : this.stackNeg[datax];
                                datay = this.stackNeg[datax] = sum + datay;
                            }
                        }
                        var dpt;
                        if (rotated) {
                            dpt = new chart._DataPoint(si, i, datay, datax);
                            var x = ax.convert(datay);
                            datay = ay.convert(datax);
                            datax = x;
                        }
                        else {
                            dpt = new chart._DataPoint(si, i, datax, datay);
                            datax = ax.convert(datax);
                            datay = ay.convert(datay);
                        }
                        if (!isNaN(datax) && !isNaN(datay)) {
                            xvals.push(datax);
                            yvals.push(datay);
                            isNullVals.push(false);
                            if (customRender) {
                                points.push(new wijmo.Point(datax, datay));
                            }
                            var area = new chart._CircleArea(new wijmo.Point(datax, datay), 0.5 * symSize);
                            area.tag = dpt;
                            this.hitTester.add(area, si);
                        }
                        else {
                            hasNulls = true;
                            if (interpolateNulls !== true) {
                                xvals.push(undefined);
                                yvals.push(undefined);
                            }
                            isNullVals.push(true);
                        }
                    }
                    else {
                        hasNulls = true;
                        if (interpolateNulls !== true) {
                            xvals.push(undefined);
                            yvals.push(undefined);
                        }
                        isNullVals.push(true);
                    }
                }
                var itemIndex = 0;
                if (this.hasLines) {
                    engine.fill = null;
                    if (hasNulls && interpolateNulls !== true) {
                        var dx = [];
                        var dy = [];
                        for (var i = 0; i < len; i++) {
                            if (xvals[i] === undefined) {
                                if (dx.length > 1) {
                                    this._drawLines(engine, dx, dy, null, style, this.chart._plotrectId);
                                    this.hitTester.add(new chart._LinesArea(dx, dy), si);
                                    itemIndex++;
                                }
                                dx = [];
                                dy = [];
                            }
                            else {
                                dx.push(xvals[i]);
                                dy.push(yvals[i]);
                            }
                        }
                        if (dx.length > 1) {
                            this._drawLines(engine, dx, dy, null, style, this.chart._plotrectId);
                            this.hitTester.add(new chart._LinesArea(dx, dy), si);
                            itemIndex++;
                        }
                    }
                    else {
                        this._drawLines(engine, xvals, yvals, null, style, this.chart._plotrectId);
                        this.hitTester.add(new chart._LinesArea(xvals, yvals), si);
                        itemIndex++;
                    }
                }
                if ((this.hasSymbols || this.chart.itemFormatter) && symSize > 0) {
                    engine.fill = fill;
                    var symbolIndex = 0;
                    for (var i = 0; i < len; i++) {
                        if (interpolateNulls && isNullVals[i]) {
                            continue;
                        }
                        var datax = xvals[symbolIndex];
                        var datay = yvals[symbolIndex];
                        // scatter fill/stroke
                        if (this.hasLines === false || this.chart.itemFormatter) {
                            engine.fill = ys[i] > 0 ? fill : altFill;
                            engine.stroke = ys[i] > 0 ? stroke : altStroke;
                        }
                        //if (DataInfo.isValid(datax) && DataInfo.isValid(datay)) {
                        if (this.isValid(datax, datay, ax, ay)) {
                            this._drawSymbol(engine, datax, datay, symSize, ser, i);
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                        symbolIndex++;
                    }
                }
                if (customRender && points && points.length) {
                    customRender(points);
                }
            };
            _LinePlotter.prototype._drawLines = function (engine, xs, ys, className, style, clipPath) {
                if (this.isSpline) {
                    engine.drawSplines(xs, ys, className, style, clipPath);
                }
                else {
                    engine.drawLines(xs, ys, className, style, clipPath);
                }
            };
            _LinePlotter.prototype._drawSymbol = function (engine, x, y, sz, series, pointIndex) {
                var _this = this;
                if (this.chart.itemFormatter) {
                    engine.startGroup();
                    var hti = new chart.HitTestInfo(this.chart, new wijmo.Point(x, y), chart.ChartElement.SeriesSymbol);
                    hti._setData(series, pointIndex);
                    this.chart.itemFormatter(engine, hti, function () {
                        if (_this.hasSymbols) {
                            _this._drawDefaultSymbol(engine, x, y, sz, series.symbolMarker, series.symbolStyle);
                        }
                    });
                    engine.endGroup();
                }
                else {
                    this._drawDefaultSymbol(engine, x, y, sz, series.symbolMarker, series.symbolStyle);
                }
            };
            _LinePlotter.prototype._drawDefaultSymbol = function (engine, x, y, sz, marker, style) {
                if (marker == chart.Marker.Dot) {
                    engine.drawEllipse(x, y, 0.5 * sz, 0.5 * sz, null, style);
                }
                else if (marker == chart.Marker.Box) {
                    engine.drawRect(x - 0.5 * sz, y - 0.5 * sz, sz, sz, null, style);
                }
            };
            return _LinePlotter;
        }(chart._BasePlotter));
        chart._LinePlotter = _LinePlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        /**
         * Area chart plotter.
         */
        var _AreaPlotter = /** @class */ (function (_super) {
            __extends(_AreaPlotter, _super);
            function _AreaPlotter() {
                var _this = _super.call(this) || this;
                _this.stacking = chart.Stacking.None;
                _this.isSpline = false;
                _this.stackPos = {};
                _this.stackNeg = {};
                return _this;
                //this.clipping = false;
            }
            _AreaPlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                this.dataInfo = dataInfo;
                var xmin = dataInfo.getMinX();
                var ymin = dataInfo.getMinY();
                var xmax = dataInfo.getMaxX();
                var ymax = dataInfo.getMaxY();
                if (this.isSpline) {
                    var dy = 0.1 * (ymax - ymin);
                    if (!this.chart.axisY.logBase)
                        ymin -= dy;
                    ymax += dy;
                }
                if (this.rotated) {
                    return new wijmo.Rect(ymin, xmin, ymax - ymin, xmax - xmin);
                }
                else {
                    return new wijmo.Rect(xmin, ymin, xmax - xmin, ymax - ymin);
                }
            };
            _AreaPlotter.prototype.clear = function () {
                _super.prototype.clear.call(this);
                this.stackNeg = {};
                this.stackPos = {};
            };
            _AreaPlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser, customRender) {
                var points = [];
                var si = this.chart.series.indexOf(series);
                var ser = series;
                //if (iser == 0) {
                //    this.stackNeg = {};
                //    this.stackPos = {};
                //}
                var ys = series.getValues(0);
                var xs = series.getValues(1);
                if (!ys) {
                    return;
                }
                var len = ys.length;
                if (!len) {
                    return;
                }
                if (!xs)
                    xs = this.dataInfo.getXVals();
                var hasXs = true;
                if (!xs) {
                    hasXs = false;
                    xs = new Array(len);
                }
                else if (xs.length < len) {
                    len = xs.length;
                }
                var xvals = new Array();
                var yvals = new Array();
                var xvals0 = new Array();
                var yvals0 = new Array();
                var stacked = this.stacking != chart.Stacking.None && !ser._isCustomAxisY();
                var stacked100 = this.stacking == chart.Stacking.Stacked100pc && !ser._isCustomAxisY();
                if (ser._getChartType() !== undefined) {
                    stacked = stacked100 = false;
                }
                var rotated = this.rotated;
                var hasNulls = false;
                var interpolateNulls = this.chart.interpolateNulls;
                var xmax = null;
                var xmin = null;
                var prect = this.chart._plotRect;
                for (var i = 0; i < len; i++) {
                    var datax = hasXs ? xs[i] : i;
                    var datay = ys[i];
                    if (xmax === null || datax > xmax) {
                        xmax = datax;
                    }
                    if (xmin === null || datax < xmin) {
                        xmin = datax;
                    }
                    if (chart._DataInfo.isValid(datax) && chart._DataInfo.isValid(datay)) {
                        var x = rotated ? ay.convert(datax) : ax.convert(datax);
                        if (stacked) {
                            if (stacked100) {
                                var sumabs = this.dataInfo.getStackedAbsSum(datax);
                                datay = datay / sumabs;
                            }
                            var sum = 0;
                            if (datay >= 0) {
                                sum = isNaN(this.stackPos[datax]) ? 0 : this.stackPos[datax];
                                datay = this.stackPos[datax] = sum + datay;
                            }
                            else {
                                sum = isNaN(this.stackNeg[datax]) ? 0 : this.stackNeg[datax];
                                datay = this.stackNeg[datax] = sum + datay;
                            }
                            if (rotated) {
                                if (sum < ax.actualMin) {
                                    sum = ax.actualMin;
                                }
                                xvals0.push(ax.convert(sum));
                                yvals0.push(x);
                            }
                            else {
                                xvals0.push(x);
                                if (sum < ay.actualMin) {
                                    sum = ay.actualMin;
                                }
                                yvals0.push(ay.convert(sum));
                            }
                        }
                        if (rotated) {
                            var y = ax.convert(datay);
                            if (!isNaN(x) && !isNaN(y)) {
                                xvals.push(y);
                                yvals.push(x);
                                if (chart.FlexChart._contains(prect, new wijmo.Point(y, x))) {
                                    var area = new chart._CircleArea(new wijmo.Point(y, x), this._DEFAULT_SYM_SIZE);
                                    area.tag = new chart._DataPoint(si, i, datay, datax);
                                    this.hitTester.add(area, si);
                                }
                            }
                            else {
                                hasNulls = true;
                                if (!stacked && interpolateNulls !== true) {
                                    xvals.push(undefined);
                                    yvals.push(undefined);
                                }
                            }
                        }
                        else {
                            var y = ay.convert(datay);
                            if (!isNaN(x) && !isNaN(y)) {
                                xvals.push(x);
                                yvals.push(y);
                                if (chart.FlexChart._contains(prect, new wijmo.Point(x, y))) {
                                    var area = new chart._CircleArea(new wijmo.Point(x, y), this._DEFAULT_SYM_SIZE);
                                    area.tag = new chart._DataPoint(si, i, datax, datay);
                                    this.hitTester.add(area, si);
                                }
                            }
                            else {
                                hasNulls = true;
                                if (!stacked && interpolateNulls !== true) {
                                    xvals.push(undefined);
                                    yvals.push(undefined);
                                }
                            }
                        }
                    }
                    else {
                        hasNulls = true;
                        if (!stacked && interpolateNulls !== true) {
                            xvals.push(undefined);
                            yvals.push(undefined);
                        }
                    }
                }
                if (customRender) {
                    xvals.forEach(function (v, i) {
                        if (v != null) {
                            points.push(new wijmo.Point(v, yvals[i]));
                        }
                    });
                }
                var swidth = this._DEFAULT_WIDTH;
                var fill = palette._getColorLight(si);
                var stroke = palette._getColor(si);
                var lstyle = chart._BasePlotter.cloneStyle(series.style, ['fill']);
                var pstyle = chart._BasePlotter.cloneStyle(series.style, ['stroke']);
                if (!stacked && interpolateNulls !== true && hasNulls) {
                    var dx = [];
                    var dy = [];
                    for (var i = 0; i < len; i++) {
                        if (xvals[i] === undefined) {
                            if (dx.length > 1) {
                                if (this.isSpline) {
                                    var s = this._convertToSpline(dx, dy);
                                    dx = s.xs;
                                    dy = s.ys;
                                }
                                engine.stroke = stroke;
                                engine.strokeWidth = swidth;
                                engine.fill = 'none';
                                engine.drawLines(dx, dy, null, lstyle);
                                this.hitTester.add(new chart._LinesArea(dx, dy), si);
                                if (rotated) {
                                    dx.push(ax.convert(ax.actualMin), ax.convert(ax.actualMin));
                                    dy.push(ay.convert(ay.actualMax), ay.convert(ay.actualMin));
                                }
                                else {
                                    dx.push(dx[dx.length - 1], dx[0]);
                                    dy.push(ay.convert(ay.actualMin), ay.convert(ay.actualMin));
                                }
                                engine.fill = fill;
                                engine.stroke = 'none';
                                engine.drawPolygon(dx, dy, null, pstyle);
                            }
                            dx = [];
                            dy = [];
                        }
                        else {
                            dx.push(xvals[i]);
                            dy.push(yvals[i]);
                        }
                    }
                    if (dx.length > 1) {
                        if (this.isSpline) {
                            var s = this._convertToSpline(dx, dy);
                            dx = s.xs;
                            dy = s.ys;
                        }
                        engine.stroke = stroke;
                        engine.strokeWidth = swidth;
                        engine.fill = 'none';
                        engine.drawLines(dx, dy, null, lstyle);
                        this.hitTester.add(new chart._LinesArea(dx, dy), si);
                        if (rotated) {
                            dx.push(ax.convert(ax.actualMin), ax.convert(ax.actualMin));
                            dy.push(ay.convert(ay.actualMax), ay.convert(ay.actualMin));
                        }
                        else {
                            dx.push(dx[dx.length - 1], dx[0]);
                            dy.push(ay.convert(ay.actualMin), ay.convert(ay.actualMin));
                        }
                        engine.fill = fill;
                        engine.stroke = 'none';
                        engine.drawPolygon(dx, dy, null, pstyle);
                    }
                }
                else {
                    //
                    if (this.isSpline) {
                        var s = this._convertToSpline(xvals, yvals);
                        xvals = s.xs;
                        yvals = s.ys;
                    }
                    //
                    if (stacked) {
                        if (this.isSpline) {
                            var s0 = this._convertToSpline(xvals0, yvals0);
                            xvals0 = s0.xs;
                            yvals0 = s0.ys;
                        }
                        xvals = xvals.concat(xvals0.reverse());
                        yvals = yvals.concat(yvals0.reverse());
                    }
                    else {
                        if (rotated) {
                            xvals.push(ax.convert(ax.actualMin), ax.convert(ax.actualMin));
                            if (yvals[0] > yvals[yvals.length - 1]) {
                                yvals.push(ay.convert(xmax), ay.convert(xmin));
                            }
                            else {
                                yvals.push(ay.convert(xmin), ay.convert(xmax));
                            }
                        }
                        else {
                            if (xvals[0] > xvals[xvals.length - 1]) {
                                xvals.push(ax.convert(xmin), ax.convert(xmax));
                            }
                            else {
                                xvals.push(ax.convert(xmax), ax.convert(xmin));
                            }
                            yvals.push(ay.convert(ay.actualMin), ay.convert(ay.actualMin));
                        }
                    }
                    engine.fill = fill;
                    engine.stroke = 'none';
                    engine.drawPolygon(xvals, yvals, null, pstyle);
                    if (stacked) {
                        xvals = xvals.slice(0, xvals.length - xvals0.length);
                        yvals = yvals.slice(0, yvals.length - yvals0.length);
                    }
                    else {
                        xvals = xvals.slice(0, xvals.length - 2);
                        yvals = yvals.slice(0, yvals.length - 2);
                    }
                    engine.stroke = stroke;
                    engine.strokeWidth = swidth;
                    engine.fill = 'none';
                    engine.drawLines(xvals, yvals, null, lstyle);
                    this.hitTester.add(new chart._LinesArea(xvals, yvals), si);
                }
                this._drawSymbols(engine, series, si);
                if (customRender && points && points.length) {
                    customRender(points);
                }
            };
            _AreaPlotter.prototype._convertToSpline = function (x, y) {
                if (x && y) {
                    var spline = new chart._Spline(x, y);
                    var s = spline.calculate();
                    return { xs: s.xs, ys: s.ys };
                }
                else {
                    return { xs: x, ys: y };
                }
            };
            _AreaPlotter.prototype._drawSymbols = function (engine, series, seriesIndex) {
                if (this.chart.itemFormatter != null) {
                    var areas = this.hitTester._map[seriesIndex];
                    for (var i = 0; i < areas.length; i++) {
                        var area = wijmo.tryCast(areas[i], chart._CircleArea);
                        if (area) {
                            var dpt = area.tag;
                            engine.startGroup();
                            var hti = new chart.HitTestInfo(this.chart, area.center, chart.ChartElement.SeriesSymbol);
                            hti._setDataPoint(dpt);
                            this.chart.itemFormatter(engine, hti, function () {
                            });
                            engine.endGroup();
                        }
                    }
                }
            };
            return _AreaPlotter;
        }(chart._BasePlotter));
        chart._AreaPlotter = _AreaPlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        var _BubblePlotter = /** @class */ (function (_super) {
            __extends(_BubblePlotter, _super);
            function _BubblePlotter() {
                var _this = _super.call(this) || this;
                _this._MIN_SIZE = 5;
                _this._MAX_SIZE = 30;
                _this.hasLines = false;
                _this.hasSymbols = true;
                _this.clipping = true;
                return _this;
            }
            _BubblePlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                var minSize = this.getNumOption('minSize', 'bubble');
                this._minSize = minSize ? minSize : this._MIN_SIZE;
                var maxSize = this.getNumOption('maxSize', 'bubble');
                this._maxSize = maxSize ? maxSize : this._MAX_SIZE;
                var series = this.chart.series;
                var len = series.length;
                var min = NaN;
                var max = NaN;
                for (var i = 0; i < len; i++) {
                    var ser = series[i];
                    var vals = ser._getBindingValues(1);
                    if (vals) {
                        var vlen = vals.length;
                        for (var j = 0; j < vlen; j++) {
                            if (chart._DataInfo.isValid(vals[j])) {
                                if (isNaN(min) || vals[j] < min) {
                                    min = vals[j];
                                }
                                if (isNaN(max) || vals[j] > max) {
                                    max = vals[j];
                                }
                            }
                        }
                    }
                }
                this._minValue = min;
                this._maxValue = max;
                var rect = _super.prototype.adjustLimits.call(this, dataInfo, plotRect);
                var ax = this.chart.axisX, ay = this.chart.axisY;
                // adjust only for non-log axes
                if (ax.logBase <= 0) {
                    var w = plotRect.width - this._maxSize;
                    var kw = w / rect.width;
                    rect.left -= this._maxSize * 0.5 / kw;
                    rect.width += this._maxSize / kw;
                }
                if (ay.logBase <= 0) {
                    var h = plotRect.height - this._maxSize;
                    var kh = h / rect.height;
                    rect.top -= this._maxSize * 0.5 / kh;
                    rect.height += this._maxSize / kh;
                }
                return rect;
            };
            _BubblePlotter.prototype._drawSymbol = function (engine, x, y, sz, series, pointIndex) {
                var _this = this;
                //If chart type is not bubble, adjust limits is not executed.
                if (this._minSize == null) {
                    var minSize = this.getNumOption('minSize', 'bubble');
                    this._minSize = minSize ? minSize : this._MIN_SIZE;
                }
                if (this._maxSize == null) {
                    var maxSize = this.getNumOption('maxSize', 'bubble');
                    this._maxSize = maxSize ? maxSize : this._MAX_SIZE;
                }
                var item = series._getItem(pointIndex);
                if (item) {
                    var szBinding = series._getBinding(1);
                    if (szBinding) {
                        var sz = item[szBinding];
                        if (chart._DataInfo.isValid(sz)) {
                            var k = this._minValue == this._maxValue ? 1 :
                                Math.sqrt((sz - this._minValue) / (this._maxValue - this._minValue));
                            sz = this._minSize + (this._maxSize - this._minSize) * k;
                            if (this.chart.itemFormatter) {
                                var hti = new chart.HitTestInfo(this.chart, new wijmo.Point(x, y), chart.ChartElement.SeriesSymbol);
                                hti._setData(series, pointIndex);
                                engine.startGroup();
                                this.chart.itemFormatter(engine, hti, function () {
                                    _this._drawDefaultSymbol(engine, x, y, sz, series.symbolMarker, series.symbolStyle);
                                });
                                engine.endGroup();
                            }
                            else {
                                this._drawDefaultSymbol(engine, x, y, sz, series.symbolMarker, series.symbolStyle);
                            }
                            var areas = this.hitTester._map[this.chart.series.indexOf(series)];
                            if (areas != null) {
                                var len = areas.length;
                                for (var i = len - 1; i >= 0; i--) {
                                    var area = areas[i];
                                    if (area.tag && area.tag.pointIndex == pointIndex) {
                                        var ca = wijmo.tryCast(area, chart._CircleArea);
                                        if (ca)
                                            ca.setRadius(0.5 * sz);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            return _BubblePlotter;
        }(chart._LinePlotter));
        chart._BubblePlotter = _BubblePlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart) {
        'use strict';
        var _FinancePlotter = /** @class */ (function (_super) {
            __extends(_FinancePlotter, _super);
            function _FinancePlotter() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this.isCandle = true;
                _this.isArms = false;
                _this.isEqui = false;
                _this.isVolume = false;
                _this._volHelper = null;
                _this._symWidth = 0.7;
                return _this;
            }
            _FinancePlotter.prototype.clear = function () {
                _super.prototype.clear.call(this);
                this._volHelper = null;
            };
            _FinancePlotter.prototype.load = function () {
                _super.prototype.load.call(this);
                if (!this.isVolume) {
                    return;
                }
                var series, ax, ct, vols, dt, i, xvals, itemsSource, xmin = null, xmax = null;
                // loop through series collection
                for (i = 0; i < this.chart.series.length; i++) {
                    series = this.chart.series[i];
                    dt = series.getDataType(1) || series.chart._xDataType;
                    ax = series._getAxisX();
                    // get volume data based on chart type
                    ct = series._getChartType();
                    ct = ct === null || wijmo.isUndefined(ct) ? this.chart._getChartType() : ct;
                    if (ct === chart.ChartType.Column) {
                        vols = series._getBindingValues(1);
                    }
                    else if (ct === chart.ChartType.Candlestick) {
                        vols = series._getBindingValues(4);
                    }
                    else {
                        vols = null;
                    }
                    // get x values directly for dates, otherwise get from dataInfo
                    if (dt === wijmo.DataType.Date) {
                        var date;
                        xvals = [];
                        itemsSource = [];
                        for (i = 0; i < series._getLength(); i++) {
                            date = series._getItem(i)[series.bindingX].valueOf();
                            xvals.push(date);
                            itemsSource.push({
                                value: date,
                                text: wijmo.Globalize.format(new Date(date), ax.format || "d")
                            });
                        }
                    }
                    else {
                        xvals = this.dataInfo.getXVals();
                    }
                    xmin = this.dataInfo.getMinX();
                    xmax = this.dataInfo.getMaxX();
                    if (vols && vols.length > 0) {
                        this._volHelper = new chart._VolumeHelper(vols, xvals, xmin, xmax, dt);
                        ax._customConvert = this._volHelper.convert.bind(this._volHelper);
                        ax._customConvertBack = this._volHelper.convertBack.bind(this._volHelper);
                        if (itemsSource && itemsSource.length > 0) {
                            this._itemsSource = ax.itemsSource = itemsSource;
                        }
                        break; // only one set of volume data is supported per chart
                    }
                }
            };
            _FinancePlotter.prototype.unload = function () {
                _super.prototype.unload.call(this);
                var series, ax;
                for (var i = 0; i < this.chart.series.length; i++) {
                    series = this.chart.series[i];
                    ax = series._getAxisX();
                    if (ax) {
                        ax._customConvert = null;
                        ax._customConvertBack = null;
                        if (ax.itemsSource && ax.itemsSource == this._itemsSource) {
                            this._itemsSource = ax.itemsSource = null;
                        }
                    }
                }
            };
            _FinancePlotter.prototype.parseSymbolWidth = function (val) {
                this._isPixel = undefined;
                if (val) {
                    if (wijmo.isNumber(val)) {
                        // px
                        var wpix = wijmo.asNumber(val);
                        if (isFinite(wpix) && wpix > 0) {
                            this._symWidth = wpix;
                            this._isPixel = true;
                        }
                    }
                    else if (wijmo.isString(val)) {
                        var ws = wijmo.asString(val);
                        // %
                        if (ws && ws.indexOf('%') >= 0) {
                            ws = ws.replace('%', '');
                            var wn = parseFloat(ws);
                            if (isFinite(wn)) {
                                if (wn < 0) {
                                    wn = 0;
                                }
                                else if (wn > 100) {
                                    wn = 100;
                                }
                                this._symWidth = wn / 100;
                                this._isPixel = false;
                            }
                        }
                        else {
                            // px
                            var wn = parseFloat(val);
                            if (isFinite(wn) && wn > 0) {
                                this._symWidth = wn;
                                this._isPixel = true;
                            }
                        }
                    }
                }
            };
            _FinancePlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                this.dataInfo = dataInfo;
                var xmin = dataInfo.getMinX();
                var ymin = dataInfo.getMinY();
                var xmax = dataInfo.getMaxX();
                var ymax = dataInfo.getMaxY();
                var dx = dataInfo.getDeltaX();
                var dt = this.chart._xDataType;
                if (dx <= 0) {
                    dx = 1;
                }
                var series = this.chart.series;
                var len = series.length;
                var swmax = 0;
                this.parseSymbolWidth(this.symbolWidth);
                // init/cleanup volume conversions for x-axis based on ChartType/FinancialChartType mappings
                if (this.isVolume && (this.chart._getChartType() === chart.ChartType.Column || this.chart._getChartType() === chart.ChartType.Candlestick)) {
                    this.load();
                }
                else {
                    this.unload();
                }
                for (var i = 0; i < len; i++) {
                    var ser = series[i];
                    if (ser._isCustomAxisY()) {
                        continue;
                    }
                    var bndLow = ser._getBinding(1), bndOpen = ser._getBinding(2), bndClose = ser._getBinding(3);
                    var slen = ser._getLength();
                    if (slen) {
                        var sw = ser._getSymbolSize();
                        if (sw > swmax) {
                            swmax = sw;
                        }
                        for (var j = 0; j < slen; j++) {
                            var item = ser._getItem(j);
                            if (item) {
                                var yvals = [bndLow ? item[bndLow] : null,
                                    bndOpen ? item[bndOpen] : null,
                                    bndClose ? item[bndClose] : null];
                                yvals.forEach(function (yval) {
                                    if (chart._DataInfo.isValid(yval) && yval !== null) {
                                        if (isNaN(ymin) || yval < ymin) {
                                            ymin = yval;
                                        }
                                        if (isNaN(ymax) || yval > ymax) {
                                            ymax = yval;
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                // adjust limits according to symbol size unless volume-based
                var xrng = xmax - xmin;
                var pr = this.chart._plotRect;
                if (pr && pr.width && !this.isVolume) {
                    sw += 2;
                    var xrng1 = pr.width / (pr.width - sw) * xrng;
                    xmin = xmin - 0.5 * (xrng1 - xrng);
                    xrng = xrng1;
                }
                if (dt === wijmo.DataType.Date && this.isVolume && (this.chart._getChartType() === chart.ChartType.Column || this.chart._getChartType() === chart.ChartType.Candlestick)) {
                    return new wijmo.Rect(xmin - 0.5 * dx, ymin, xmax - xmin + dx, ymax - ymin);
                }
                else {
                    return this.chart._isRotated() ? new wijmo.Rect(ymin, xmin, ymax - ymin, xrng) : new wijmo.Rect(xmin, ymin, xrng, ymax - ymin);
                }
            };
            _FinancePlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser, customRender) {
                var _this = this;
                var ser = wijmo.asType(series, chart.SeriesBase);
                var si = this.chart.series.indexOf(series);
                var highs = series.getValues(0);
                var xs = series.getValues(1);
                var sw = this._symWidth, rotated = this.chart._isRotated();
                if (!highs) {
                    return;
                }
                if (!xs) {
                    xs = this.dataInfo.getXVals();
                }
                if (xs) {
                    // find minimal distance between point and use it as column width
                    var delta = this.dataInfo.getDeltaX();
                    if (delta > 0 && this._isPixel === false) {
                        sw *= delta;
                    }
                }
                //var style = this.cloneStyle(series.style, null);// ['fill']);
                var len = highs.length;
                var hasXs = true;
                if (!xs) {
                    hasXs = false;
                    xs = new Array(len);
                }
                else {
                    len = Math.min(len, xs.length);
                }
                var swidth = this._DEFAULT_WIDTH, fill = ser._getSymbolFill(si), altFill = ser._getAltSymbolFill(si) || "transparent", stroke = ser._getSymbolStroke(si), altStroke = ser._getAltSymbolStroke(si) || stroke, symSize = this._isPixel === undefined ? ser._getSymbolSize() : sw;
                engine.stroke = stroke;
                engine.strokeWidth = swidth;
                engine.fill = fill;
                var bndLow = ser._getBinding(1);
                var bndOpen = ser._getBinding(2);
                var bndClose = ser._getBinding(3);
                var xmin = rotated ? ay.actualMin : ax.actualMin, xmax = rotated ? ay.actualMax : ax.actualMax;
                var itemIndex = 0, currentFill, currentStroke, item = null, prevItem = null;
                for (var i = 0; i < len; i++) {
                    item = ser._getItem(i);
                    if (item) {
                        var x = hasXs ? xs[i] : i;
                        if (chart._DataInfo.isValid(x) && xmin <= x && x <= xmax) {
                            var hi = highs[i];
                            var lo = bndLow ? item[bndLow] : null;
                            var open = bndOpen ? item[bndOpen] : null;
                            var close = bndClose ? item[bndClose] : null;
                            engine.startGroup();
                            if (this.isEqui && prevItem !== null) {
                                // if price is the same as previous, use previous color for now - possibly introduce a neutral color
                                if (prevItem[bndClose] !== item[bndClose]) {
                                    currentFill = prevItem[bndClose] < item[bndClose] ? altFill : fill;
                                    currentStroke = prevItem[bndClose] < item[bndClose] ? altStroke : stroke;
                                }
                            }
                            else {
                                currentFill = open < close ? altFill : fill;
                                currentStroke = open < close ? altStroke : stroke;
                            }
                            engine.fill = currentFill;
                            engine.stroke = currentStroke;
                            if (this.chart.itemFormatter) {
                                var hti = new chart.HitTestInfo(this.chart, new wijmo.Point(ax.convert(x), ay.convert(hi)), chart.ChartElement.SeriesSymbol);
                                hti._setData(ser, i);
                                this.chart.itemFormatter(engine, hti, function () {
                                    _this._drawSymbol(engine, ax, ay, si, i, currentFill, symSize, x, hi, lo, open, close);
                                });
                            }
                            else {
                                this._drawSymbol(engine, ax, ay, si, i, currentFill, symSize, x, hi, lo, open, close);
                            }
                            engine.endGroup();
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                        prevItem = item;
                    }
                }
            };
            _FinancePlotter.prototype._drawSymbol = function (engine, ax, ay, si, pi, fill, w, x, hi, lo, open, close) {
                var dpt = new chart._DataPoint(si, pi, x, hi);
                var area;
                var y0 = null, y1 = null, x1 = null, x2 = null, rotated = this.chart._isRotated();
                if (rotated) {
                    var axtmp = ay;
                    ay = ax;
                    ax = axtmp;
                }
                if (this._isPixel === false) {
                    x1 = ax.convert(x - 0.5 * w);
                    x2 = ax.convert(x + 0.5 * w);
                    if (x1 > x2) {
                        var tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }
                }
                x = ax.convert(x);
                if (this._isPixel !== false) {
                    x1 = x - 0.5 * w;
                    x2 = x + 0.5 * w;
                }
                if (this.isCandle) {
                    if (chart._DataInfo.isValid(open) && chart._DataInfo.isValid(close)) {
                        open = ay.convert(open);
                        close = ay.convert(close);
                        y0 = Math.min(open, close);
                        y1 = y0 + Math.abs(open - close);
                        if (rotated) {
                            engine.drawRect(y0, x1, y1 - y0 || 1, x2 - x1 || 1);
                            area = new chart._RectArea(new wijmo.Rect(y0, x1, y1 - y0 || 1, x2 - x1 || 1));
                        }
                        else {
                            engine.drawRect(x1, y0, x2 - x1 || 1, y1 - y0 || 1);
                            area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1 || 1, y1 - y0 || 1));
                        }
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                    if (chart._DataInfo.isValid(hi)) {
                        hi = ay.convert(hi);
                        if (y0 !== null) {
                            if (rotated) {
                                engine.drawLine(y1, x, hi, x);
                            }
                            else {
                                engine.drawLine(x, y0, x, hi);
                            }
                        }
                    }
                    if (chart._DataInfo.isValid(lo)) {
                        lo = ay.convert(lo);
                        if (y1 !== null) {
                            if (rotated) {
                                engine.drawLine(y0, x, lo, x);
                            }
                            else {
                                engine.drawLine(x, y1, x, lo);
                            }
                        }
                    }
                }
                else if (this.isEqui) {
                    if (chart._DataInfo.isValid(hi) && chart._DataInfo.isValid(lo)) {
                        hi = ay.convert(hi);
                        lo = ay.convert(lo);
                        y0 = Math.min(hi, lo);
                        y1 = y0 + Math.abs(hi - lo);
                        engine.drawRect(x1, y0, x2 - x1 || 1, y1 - y0 || 1);
                        area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1 || 1, y1 - y0 || 1));
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                }
                else if (this.isArms) {
                    // inner box
                    if (chart._DataInfo.isValid(open) && chart._DataInfo.isValid(close)) {
                        open = ay.convert(open);
                        close = ay.convert(close);
                        y0 = Math.min(open, close);
                        y1 = y0 + Math.abs(open - close);
                        engine.drawRect(x1, y0, x2 - x1 || 1, y1 - y0 || 1);
                    }
                    // high line
                    if (chart._DataInfo.isValid(hi) && y0 !== null) {
                        hi = ay.convert(hi);
                        engine.drawLine(x, y0, x, hi);
                    }
                    // low line
                    if (chart._DataInfo.isValid(lo) && y1 !== null) {
                        lo = ay.convert(lo);
                        engine.drawLine(x, y1, x, lo);
                    }
                    // outer box
                    if (chart._DataInfo.isValid(hi) && chart._DataInfo.isValid(lo)) {
                        engine.fill = "transparent";
                        y0 = Math.min(hi, lo);
                        y1 = y0 + Math.abs(hi - lo);
                        engine.drawRect(x1, y0, x2 - x1 || 1, y1 - y0 || 1);
                        area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1 || 1, y1 - y0 || 1));
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                }
                else {
                    if (chart._DataInfo.isValid(hi) && chart._DataInfo.isValid(lo)) {
                        hi = ay.convert(hi);
                        lo = ay.convert(lo);
                        y0 = Math.min(hi, lo);
                        y1 = y0 + Math.abs(hi - lo);
                        if (rotated) {
                            engine.drawLine(lo, x, hi, x);
                            area = new chart._RectArea(new wijmo.Rect(y0, x1, y1 - y0 || 1, x2 - x1 || 1));
                        }
                        else {
                            engine.drawLine(x, lo, x, hi);
                            area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1 || 1, y1 - y0 || 1));
                        }
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                    if (chart._DataInfo.isValid(open)) {
                        open = ay.convert(open);
                        if (rotated) {
                            engine.drawLine(open, x1, open, x);
                        }
                        else {
                            engine.drawLine(x1, open, x, open);
                        }
                    }
                    if (chart._DataInfo.isValid(close)) {
                        close = ay.convert(close);
                        if (rotated) {
                            engine.drawLine(close, x, close, x2);
                        }
                        else {
                            engine.drawLine(x, close, x2, close);
                        }
                    }
                }
            };
            return _FinancePlotter;
        }(chart._BasePlotter));
        chart._FinancePlotter = _FinancePlotter;
    })(chart = wijmo.chart || (wijmo.chart = {}));
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
    var chart;
    (function (chart_1) {
        'use strict';
        /**
         * Funnel chart plotter.
         */
        var _FunnelPlotter = /** @class */ (function (_super) {
            __extends(_FunnelPlotter, _super);
            function _FunnelPlotter() {
                var _this = _super !== null && _super.apply(this, arguments) || this;
                _this.stacking = chart_1.Stacking.None;
                return _this;
            }
            _FunnelPlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                this.dataInfo = dataInfo;
                var xmin = dataInfo.getMinX();
                var ymin = dataInfo.getMinY();
                var xmax = dataInfo.getMaxX();
                var ymax = dataInfo.getMaxY();
                return this.rotated
                    ? new wijmo.Rect(ymin, xmin, ymax - ymin, xmax - xmin)
                    : new wijmo.Rect(xmin, ymin, xmax - xmin, ymax - ymin);
            };
            _FunnelPlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser, customRender) {
                var si = this.chart.series.indexOf(series);
                if (si > 0) {
                    //Only support one series for Funnel in current version.
                    return;
                }
                var ser = wijmo.asType(series, chart_1.SeriesBase), options = this.chart.options, yvals = series.getValues(0), xvals = series.getValues(1), rect = this.chart._plotRect, neckWidth = (options && options.funnel && options.funnel.neckWidth != null) ? options.funnel.neckWidth : 0.2, neckHeight = (options && options.funnel && options.funnel.neckHeight != null) ? options.funnel.neckHeight : 0, neckAbsWidth = neckWidth * rect.width, i = 0, sum = 0, neckX = 0, neckY = 0, areas, angle, offsetX, offsetY, h, x = rect.left, y = rect.top, width = rect.width, height = rect.height;
                if (!yvals) {
                    return;
                }
                neckAbsWidth = neckAbsWidth ? neckAbsWidth : 1;
                if (!xvals) {
                    xvals = this.dataInfo.getXVals();
                }
                var len = yvals.length;
                if (xvals != null) {
                    len = Math.min(len, xvals.length);
                }
                for (i = 0; i < len; i++) {
                    sum += yvals[i];
                }
                var itemIndex = 0, currentFill, currentStroke;
                if (options && options.funnel && options.funnel.type === 'rectangle') {
                    neckHeight = height / len;
                    neckWidth = width;
                    var ratio;
                    for (i = 0; i < len; i++) {
                        var datax = xvals ? xvals[i] : i;
                        var datay = yvals[i];
                        var ht;
                        // set series fill and stroke from style
                        var fill = ser._getSymbolFill(i), altFill = ser._getAltSymbolFill(i) || fill, stroke = ser._getSymbolStroke(i), altStroke = ser._getAltSymbolStroke(i) || stroke;
                        if (this._getSymbolStyles) {
                            var style = this._getSymbolStyles(i, len);
                            fill = style && style.fill ? style.fill : fill;
                            altFill = style && style.fill ? style.fill : altFill;
                            stroke = style && style.stroke ? style.stroke : stroke;
                            altStroke = style && style.stroke ? style.stroke : altStroke;
                        }
                        // apply fill and stroke
                        currentFill = datay > 0 ? fill : altFill;
                        currentStroke = datay > 0 ? stroke : altStroke;
                        engine.fill = currentFill;
                        engine.stroke = currentStroke;
                        if (chart_1._DataInfo.isValid(datax) && chart_1._DataInfo.isValid(datay)) {
                            if (!ratio) {
                                ratio = width / datay;
                            }
                            var w = ratio * datay;
                            x = x + (neckWidth - w) / 2;
                            engine.drawRect(x, y, w, neckHeight);
                            ht = new _FunnelSegment(new wijmo.Point(x, y), w, neckHeight, w, neckHeight);
                            y = y + neckHeight;
                            neckWidth = w;
                            ht.tag = new chart_1._DataPoint(si, i, datax, datay);
                            this.hitTester.add(ht, si);
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                    }
                }
                else {
                    //if (!this.rotated) {
                    neckX = rect.left + rect.width * (1 - neckWidth) / 2;
                    neckY = rect.top + rect.height * (1 - neckHeight);
                    angle = (1 - neckWidth) * rect.width / 2 / (rect.height * (1 - neckHeight));
                    if (isNaN(angle) || !isFinite(angle)) {
                        width = neckAbsWidth;
                        x = neckX;
                        y = neckY;
                    }
                    areas = rect.width * neckWidth * rect.height + rect.width * (1 - neckWidth) / 2 * rect.height * (1 - neckHeight);
                    for (i = 0; i < len; i++) {
                        var datax = xvals ? xvals[i] : i;
                        var datay = yvals[i];
                        var xs = [], ys = [];
                        var ht;
                        // set series fill and stroke from style
                        var fill = ser._getSymbolFill(i), altFill = ser._getAltSymbolFill(i) || fill, stroke = ser._getSymbolStroke(i), altStroke = ser._getAltSymbolStroke(i) || stroke;
                        if (this._getSymbolStyles) {
                            var style = this._getSymbolStyles(i, len);
                            fill = style && style.fill ? style.fill : fill;
                            altFill = style && style.fill ? style.fill : altFill;
                            stroke = style && style.stroke ? style.stroke : stroke;
                            altStroke = style && style.stroke ? style.stroke : altStroke;
                        }
                        // apply fill and stroke
                        currentFill = datay > 0 ? fill : altFill;
                        currentStroke = datay > 0 ? stroke : altStroke;
                        engine.fill = currentFill;
                        engine.stroke = currentStroke;
                        if (chart_1._DataInfo.isValid(datax) && chart_1._DataInfo.isValid(datay)) {
                            var area = areas * datay / sum;
                            if (width > neckAbsWidth) {
                                offsetY = this._getTrapezoidOffsetY(width, area, angle);
                                if ((y + offsetY) < neckY) {
                                    offsetX = angle * offsetY;
                                    xs = [x, x + offsetX, x + width - offsetX, x + width];
                                    ys = [y, y + offsetY, y + offsetY, y];
                                    ht = new _FunnelSegment(new wijmo.Point(x, y), width, offsetY, width - offsetX * 2, 0);
                                    width = width - 2 * offsetX;
                                    x = x + offsetX;
                                    y = y + offsetY;
                                }
                                else {
                                    offsetY = neckY - y;
                                    offsetX = angle * offsetY;
                                    area = area - this._getTrapezoidArea(width, angle, offsetY);
                                    h = area / neckAbsWidth;
                                    xs.push(x, x + offsetX, x + offsetX, x + offsetX + neckAbsWidth, x + offsetX + neckAbsWidth, x + width);
                                    ys.push(y, y + offsetY, y + offsetY + h, y + offsetY + h, y + offsetY, y);
                                    ht = new _FunnelSegment(new wijmo.Point(x, y), width, offsetY + h, neckAbsWidth, h);
                                    width = neckAbsWidth;
                                    x = x + offsetX;
                                    y = y + offsetY + h;
                                }
                                engine.drawPolygon(xs, ys);
                            }
                            else {
                                h = area / neckAbsWidth;
                                engine.drawRect(x, y, width, h);
                                ht = new _FunnelSegment(new wijmo.Point(x, y), neckAbsWidth, h, neckAbsWidth, h);
                                y = y + h;
                            }
                            ht.tag = new chart_1._DataPoint(si, i, datax, datay);
                            this.hitTester.add(ht, si);
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                    }
                }
            };
            _FunnelPlotter.prototype._getTrapezoidArea = function (width, angle, height) {
                var offsetX = height * angle;
                return offsetX * height + (width - 2 * offsetX) * height;
            };
            _FunnelPlotter.prototype._getTrapezoidOffsetY = function (width, area, angle) {
                var val = Math.pow(width / 2 / angle, 2) - area / angle;
                var offsetY = width / 2 / angle - Math.sqrt(val >= 0 ? val : 0);
                return offsetY;
            };
            _FunnelPlotter.prototype.drawSymbol = function (engine, rect, series, pointIndex, point) {
                var _this = this;
                if (this.chart.itemFormatter) {
                    engine.startGroup();
                    var hti = new chart_1.HitTestInfo(this.chart, point, chart_1.ChartElement.SeriesSymbol);
                    hti._setData(series, pointIndex);
                    this.chart.itemFormatter(engine, hti, function () {
                        _this.drawDefaultSymbol(engine, rect, series);
                    });
                    engine.endGroup();
                }
                else {
                    this.drawDefaultSymbol(engine, rect, series);
                }
            };
            _FunnelPlotter.prototype.drawDefaultSymbol = function (engine, rect, series) {
                engine.drawRect(rect.left, rect.top, rect.width, rect.height, null, series.symbolStyle /* ,'plotRect'*/);
            };
            _FunnelPlotter.prototype._getPointAndPosition = function (pt, pos, map, chart) {
                var fs = map;
                pt.x = fs.center.x;
                pt.y = fs.center.y;
                pos = pos == null ? chart_1.LabelPosition.Center : pos;
            };
            return _FunnelPlotter;
        }(chart_1._BasePlotter));
        chart_1._FunnelPlotter = _FunnelPlotter;
        var _FunnelSegment = /** @class */ (function () {
            function _FunnelSegment(startPoint, width, height, neckWidth, neckHeight) {
                this._startPoint = startPoint;
                this._width = width;
                this._height = height;
                this._neckWidth = neckWidth;
                this._neckHeight = neckHeight;
                this._center = new wijmo.Point(this._startPoint.x + width / 2, this._startPoint.y + height / 2);
                this._offsetX = (width - neckWidth) / 2;
                this._offsetY = (height - neckHeight);
            }
            _FunnelSegment.prototype.contains = function (pt) {
                var sp = this._startPoint, ox = this._offsetX, oy = this._offsetY;
                if (pt.x >= sp.x && pt.x <= sp.x + this._width && pt.y >= sp.y && pt.y <= sp.y + this._height) {
                    if (pt.x >= sp.x + ox && pt.x <= sp.x + this._width - ox) {
                        return true;
                    }
                    else if (pt.y > sp.y + oy) {
                        return false;
                    }
                    else if (pt.x < this._center.x) {
                        return (pt.y - sp.y) / (pt.x - sp.x) < oy / ox;
                    }
                    else if (pt.x > this._center.x) {
                        return (pt.y - sp.y) / (sp.x + this._width - pt.x) < oy / ox;
                    }
                }
                return false;
            };
            _FunnelSegment.prototype.distance = function (pt) {
                if (this.contains(pt)) {
                    return 0;
                }
                var sp = this._startPoint, w = this._width, h = this._height, ox = this._offsetX, oy = this._offsetY;
                if (pt.y < sp.y) {
                    if (pt.x < sp.x) {
                        return Math.sqrt(Math.pow(sp.x - pt.x, 2) + Math.pow(sp.y - pt.y, 2));
                    }
                    else if (pt.x > sp.x + w) {
                        return Math.sqrt(Math.pow(pt.x - sp.x - w, 2) + Math.pow(sp.y - pt.y, 2));
                    }
                    else {
                        return sp.y - pt.y;
                    }
                }
                else if (pt.y > sp.y + h) {
                    if (pt.x < sp.x) {
                        return Math.sqrt(Math.pow(sp.x - pt.x, 2) + Math.pow(pt.y - sp.y - h, 2));
                    }
                    else if (pt.x > sp.x + w) {
                        return Math.sqrt(Math.pow(pt.x - sp.x - w, 2) + Math.pow(pt.y - sp.y - h, 2));
                    }
                    else {
                        return pt.y - sp.y - h;
                    }
                }
                else if (pt.y > sp.y + oy) {
                    if (pt.x < sp.x + ox) {
                        return sp.x + ox - pt.x;
                    }
                    else if (pt.x > sp.x + w - ox) {
                        return pt.x - sp.x - w + ox;
                    }
                }
                else {
                    if (pt.x < sp.x + ox) {
                        return Math.min(Math.sqrt(Math.pow(sp.x - pt.x, 2) + Math.pow(pt.y - sp.y, 2)), Math.sqrt(Math.pow(pt.x - ox / 2 - sp.x, 2) + Math.pow(pt.y - oy / 2 - sp.y, 2)), Math.sqrt(Math.pow(pt.x - ox - sp.x, 2) + Math.pow(pt.y - oy - sp.y, 2)));
                    }
                    else {
                        return Math.min(Math.sqrt(Math.pow(pt.x - w - sp.x, 2) + Math.pow(pt.y - sp.y, 2)), Math.sqrt(Math.pow(pt.x - w + ox / 2 - sp.x, 2) + Math.pow(pt.y - oy / 2 - sp.y, 2)), Math.sqrt(Math.pow(pt.x - w + ox - sp.x, 2) + Math.pow(pt.y - oy - sp.y, 2)));
                    }
                }
                return undefined;
            };
            Object.defineProperty(_FunnelSegment.prototype, "center", {
                get: function () {
                    return this._center;
                },
                enumerable: true,
                configurable: true
            });
            return _FunnelSegment;
        }());
        chart_1._FunnelSegment = _FunnelSegment;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        "use strict";
        var _VolumeHelper = /** @class */ (function () {
            function _VolumeHelper(volumes, xVals, xDataMin, xDataMax, xDataType) {
                this._volumes = wijmo.asArray(volumes);
                this._xVals = wijmo.asArray(xVals);
                this._xDataMin = wijmo.asNumber(xDataMin, true, false);
                this._xDataMax = wijmo.asNumber(xDataMax, true, false);
                this._xDataType = wijmo.asEnum(xDataType, wijmo.DataType, true);
                this._calcData = [];
                // initialize
                this._init();
            }
            // converts the specified value from data to pixel coordinates
            // for volume based x-axis (customConvert)
            _VolumeHelper.prototype.convert = function (x, min, max) {
                var retval = undefined, len = this._calcData.length, i = -1;
                if (this._hasXs && this._xDataType === wijmo.DataType.Date) {
                    // find directly
                    i = this._xVals.indexOf(x);
                    // loop through and attempt to find index
                    if (i === -1) {
                        for (var j = 0; j < this._xVals.length; j++) {
                            if (j < (this._xVals.length - 1) && this._xVals[j] <= x && x <= this._xVals[j + 1]) {
                                i = j;
                                break;
                            }
                            else if (j === 0 && x <= this._xVals[j]) {
                                i = j;
                                break;
                            }
                            else if (j === (this._xVals.length - 1) && this._xVals[j] <= x) {
                                i = j;
                                break;
                            }
                        }
                    }
                    // last resort - force
                    if (i === -1) {
                        i = this._xVals.indexOf(Math.floor(x));
                        i = wijmo.clamp(i, 0, len - 1);
                    }
                }
                else if (this._hasXs) {
                    i = this._xVals.indexOf(x);
                    if (i === -1) {
                        i = this._xVals.indexOf(Math.floor(x));
                        i = wijmo.clamp(i, 0, len - 1);
                    }
                }
                else {
                    i = wijmo.clamp(Math.round(x), 0, len - 1);
                }
                if (0 <= i && i < len) {
                    if (this._hasXs) {
                        x = _VolumeHelper.convertToRange(x, 0, (len - 1), this._xDataMin, this._xDataMax);
                    }
                    retval = this._calcData[i].value + ((x - i) * this._calcData[i].width) - (0.5 * this._calcData[i].width);
                    // tranform to the actual data range
                    min = this._getXVolume(min);
                    max = this._getXVolume(max);
                    retval = (retval - min) / (max - min);
                }
                return retval;
            };
            // converts the specified value from pixel to data coordinates
            // for volume based x-axis (customConvertBack)
            _VolumeHelper.prototype.convertBack = function (x, min, max) {
                var retval = undefined, len = this._calcData.length, idx = -1, i;
                // try to find correct index based on ranges (x1 = start & x2 = end)
                for (i = 0; i < len; i++) {
                    if ((this._calcData[i].x1 <= x && x <= this._calcData[i].x2) ||
                        (i === 0 && x <= this._calcData[i].x2) ||
                        (i === (len - 1) && this._calcData[i].x1 <= x)) {
                        idx = i;
                        break;
                    }
                }
                if (0 <= idx && idx < len) {
                    retval = (x / this._calcData[idx].width) - (this._calcData[idx].value / this._calcData[idx].width) + .5 + i;
                    if (this._hasXs) {
                        retval = _VolumeHelper.convertToRange(retval, this._xDataMin, this._xDataMax, 0, (len - 1));
                    }
                }
                return retval;
            };
            // initialize volume data
            _VolumeHelper.prototype._init = function () {
                // xVals, xDataMin, and xDataMax must all be set for _hasXs to be true
                this._hasXs = this._xVals !== null && this._xVals.length > 0;
                if (this._hasXs && !wijmo.isNumber(this._xDataMin)) {
                    this._xDataMin = Math.min.apply(null, this._xVals);
                }
                if (this._hasXs && !wijmo.isNumber(this._xDataMax)) {
                    this._xDataMax = Math.max.apply(null, this._xVals);
                }
                if (this._hasXs) {
                    this._hasXs = wijmo.isNumber(this._xDataMin) && wijmo.isNumber(this._xDataMax);
                }
                if (this._hasXs && this._xDataType === wijmo.DataType.Date) {
                    // try fill gaps for dates
                    this._fillGaps();
                }
                // calculate total volume
                var totalVolume = 0, i = 0, len = this._volumes !== null && this._volumes.length > 0 ? this._volumes.length : 0;
                for (i = 0; i < len; i++) {
                    totalVolume += this._volumes[i] || 0;
                }
                // calculate width and position (range = 0 to 1)
                var val, width, pos = 0;
                for (i = 0; i < len; i++) {
                    width = (this._volumes[i] || 0) / totalVolume;
                    val = pos + width;
                    this._calcData.push({
                        value: val,
                        width: width,
                        x1: pos,
                        x2: val
                    });
                    pos = this._calcData[i].value;
                }
            };
            // for converting min/max
            _VolumeHelper.prototype._getXVolume = function (x) {
                var len = this._calcData.length, i = -1;
                if (this._hasXs) {
                    i = this._xVals.indexOf(x);
                    // loop through and attempt to find index
                    for (var j = 0; j < this._xVals.length; j++) {
                        if (j < (this._xVals.length - 1) && this._xVals[j] <= x && x <= this._xVals[j + 1]) {
                            i = j;
                            break;
                        }
                        else if (j === 0 && x <= this._xVals[j]) {
                            i = j;
                            break;
                        }
                        else if (j === (this._xVals.length - 1) && this._xVals[j] <= x) {
                            i = j;
                            break;
                        }
                    }
                }
                // change range from something like 5-9 to 0-4
                if (this._hasXs) {
                    x = _VolumeHelper.convertToRange(x, 0, (len - 1), this._xDataMin, this._xDataMax);
                }
                if (i === -1) {
                    i = wijmo.clamp(Math.round(x), 0, len - 1);
                }
                return this._calcData[i].value + ((x - i) * this._calcData[i].width) - (0.5 * this._calcData[i].width);
            };
            // converts a value from one range to another
            // ex. converts a number within range 0-10 to a number within range 0-100 (5 becomes 50)
            _VolumeHelper.convertToRange = function (value, newMin, newMax, oldMin, oldMax) {
                if (newMin === newMax || oldMin === oldMax) {
                    return 0;
                }
                // newValue = (((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin
                return (((value - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
            };
            // fill gaps in volume and x data when x-axis is using dates
            // there could potentially be gaps for weekends and/or holidays
            _VolumeHelper.prototype._fillGaps = function () {
                if (this._xDataType !== wijmo.DataType.Date || this._xVals === null || this._xVals.length <= 0) {
                    return;
                }
                var xmin = this._xDataMin, xmax = this._xDataMax, i;
                for (i = 1; xmin < xmax; i++) {
                    xmin = new Date(xmin);
                    xmin.setDate(xmin.getDate() + 1);
                    xmin = xmin.valueOf();
                    if (xmin !== this._xVals[i]) {
                        this._xVals.splice(i, 0, xmin);
                        this._volumes.splice(i, 0, 0);
                    }
                }
            };
            return _VolumeHelper;
        }());
        chart._VolumeHelper = _VolumeHelper;
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

