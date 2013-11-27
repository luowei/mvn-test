/*
 * jQuery UI Sortable 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Sortables
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 */
(function (a, b) {
    a.widget("ui.sortable", a.ui.mouse, {widgetEventPrefix:"sort", options:{appendTo:"parent", axis:!1, connectWith:!1, containment:!1, cursor:"auto", cursorAt:!1, dropOnEmpty:!0, forcePlaceholderSize:!1, forceHelperSize:!1, grid:!1, handle:!1, helper:"original", items:"> *", opacity:!1, placeholder:!1, revert:!1, scroll:!0, scrollSensitivity:20, scrollSpeed:20, scope:"default", tolerance:"intersect", zIndex:1e3}, _create:function () {
        var a = this.options;
        this.containerCache = {}, this.element.addClass("ui-sortable"), this.refresh(), this.floating = this.items.length ? a.axis === "x" || /left|right/.test(this.items[0].item.css("float")) || /inline|table-cell/.test(this.items[0].item.css("display")) : !1, this.offset = this.element.offset(), this._mouseInit()
    }, destroy:function () {
        this.element.removeClass("ui-sortable ui-sortable-disabled"), this._mouseDestroy();
        for (var a = this.items.length - 1; a >= 0; a--)this.items[a].item.removeData(this.widgetName + "-item");
        return this
    }, _setOption:function (b, c) {
        b === "disabled" ? (this.options[b] = c, this.widget()[c ? "addClass" : "removeClass"]("ui-sortable-disabled")) : a.Widget.prototype._setOption.apply(this, arguments)
    }, _mouseCapture:function (b, c) {
        var d = this;
        if (this.reverting)return!1;
        if (this.options.disabled || this.options.type == "static")return!1;
        this._refreshItems(b);
        var e = null, f = this, g = a(b.target).parents().each(function () {
            if (a.data(this, d.widgetName + "-item") == f) {
                e = a(this);
                return!1
            }
        });
        a.data(b.target, d.widgetName + "-item") == f && (e = a(b.target));
        if (!e)return!1;
        if (this.options.handle && !c) {
            var h = !1;
            a(this.options.handle, e).find("*").andSelf().each(function () {
                this == b.target && (h = !0)
            });
            if (!h)return!1
        }
        this.currentItem = e, this._removeCurrentsFromItems();
        return!0
    }, _mouseStart:function (b, c, d) {
        var e = this.options, f = this;
        this.currentContainer = this, this.refreshPositions(), this.helper = this._createHelper(b), this._cacheHelperProportions(), this._cacheMargins(), this.scrollParent = this.helper.scrollParent(), this.offset = this.currentItem.offset(), this.offset = {top:this.offset.top - this.margins.top, left:this.offset.left - this.margins.left}, this.helper.css("position", "absolute"), this.cssPosition = this.helper.css("position"), a.extend(this.offset, {click:{left:b.pageX - this.offset.left, top:b.pageY - this.offset.top}, parent:this._getParentOffset(), relative:this._getRelativeOffset()}), this.originalPosition = this._generatePosition(b), this.originalPageX = b.pageX, this.originalPageY = b.pageY, e.cursorAt && this._adjustOffsetFromHelper(e.cursorAt), this.domPosition = {prev:this.currentItem.prev()[0], parent:this.currentItem.parent()[0]}, this.helper[0] != this.currentItem[0] && this.currentItem.hide(), this._createPlaceholder(), e.containment && this._setContainment(), e.cursor && (a("body").css("cursor") && (this._storedCursor = a("body").css("cursor")), a("body").css("cursor", e.cursor)), e.opacity && (this.helper.css("opacity") && (this._storedOpacity = this.helper.css("opacity")), this.helper.css("opacity", e.opacity)), e.zIndex && (this.helper.css("zIndex") && (this._storedZIndex = this.helper.css("zIndex")), this.helper.css("zIndex", e.zIndex)), this.scrollParent[0] != document && this.scrollParent[0].tagName != "HTML" && (this.overflowOffset = this.scrollParent.offset()), this._trigger("start", b, this._uiHash()), this._preserveHelperProportions || this._cacheHelperProportions();
        if (!d)for (var g = this.containers.length - 1; g >= 0; g--)this.containers[g]._trigger("activate", b, f._uiHash(this));
        a.ui.ddmanager && (a.ui.ddmanager.current = this), a.ui.ddmanager && !e.dropBehaviour && a.ui.ddmanager.prepareOffsets(this, b), this.dragging = !0, this.helper.addClass("ui-sortable-helper"), this._mouseDrag(b);
        return!0
    }, _mouseDrag:function (b) {
        this.position = this._generatePosition(b), this.positionAbs = this._convertPositionTo("absolute"), this.lastPositionAbs || (this.lastPositionAbs = this.positionAbs);
        if (this.options.scroll) {
            var c = this.options, d = !1;
            this.scrollParent[0] != document && this.scrollParent[0].tagName != "HTML" ? (this.overflowOffset.top + this.scrollParent[0].offsetHeight - b.pageY < c.scrollSensitivity ? this.scrollParent[0].scrollTop = d = this.scrollParent[0].scrollTop + c.scrollSpeed : b.pageY - this.overflowOffset.top < c.scrollSensitivity && (this.scrollParent[0].scrollTop = d = this.scrollParent[0].scrollTop - c.scrollSpeed), this.overflowOffset.left + this.scrollParent[0].offsetWidth - b.pageX < c.scrollSensitivity ? this.scrollParent[0].scrollLeft = d = this.scrollParent[0].scrollLeft + c.scrollSpeed : b.pageX - this.overflowOffset.left < c.scrollSensitivity && (this.scrollParent[0].scrollLeft = d = this.scrollParent[0].scrollLeft - c.scrollSpeed)) : (b.pageY - a(document).scrollTop() < c.scrollSensitivity ? d = a(document).scrollTop(a(document).scrollTop() - c.scrollSpeed) : a(window).height() - (b.pageY - a(document).scrollTop()) < c.scrollSensitivity && (d = a(document).scrollTop(a(document).scrollTop() + c.scrollSpeed)), b.pageX - a(document).scrollLeft() < c.scrollSensitivity ? d = a(document).scrollLeft(a(document).scrollLeft() - c.scrollSpeed) : a(window).width() - (b.pageX - a(document).scrollLeft()) < c.scrollSensitivity && (d = a(document).scrollLeft(a(document).scrollLeft() + c.scrollSpeed))), d !== !1 && a.ui.ddmanager && !c.dropBehaviour && a.ui.ddmanager.prepareOffsets(this, b)
        }
        this.positionAbs = this._convertPositionTo("absolute");
        if (!this.options.axis || this.options.axis != "y")this.helper[0].style.left = this.position.left + "px";
        if (!this.options.axis || this.options.axis != "x")this.helper[0].style.top = this.position.top + "px";
        for (var e = this.items.length - 1; e >= 0; e--) {
            var f = this.items[e], g = f.item[0], h = this._intersectsWithPointer(f);
            if (!h)continue;
            if (g != this.currentItem[0] && this.placeholder[h == 1 ? "next" : "prev"]()[0] != g && !a.ui.contains(this.placeholder[0], g) && (this.options.type == "semi-dynamic" ? !a.ui.contains(this.element[0], g) : !0)) {
                this.direction = h == 1 ? "down" : "up";
                if (this.options.tolerance == "pointer" || this._intersectsWithSides(f))this._rearrange(b, f); else break;
                this._trigger("change", b, this._uiHash());
                break
            }
        }
        this._contactContainers(b), a.ui.ddmanager && a.ui.ddmanager.drag(this, b), this._trigger("sort", b, this._uiHash()), this.lastPositionAbs = this.positionAbs;
        return!1
    }, _mouseStop:function (b, c) {
        if (!!b) {
            a.ui.ddmanager && !this.options.dropBehaviour && a.ui.ddmanager.drop(this, b);
            if (this.options.revert) {
                var d = this, e = d.placeholder.offset();
                d.reverting = !0, a(this.helper).animate({left:e.left - this.offset.parent.left - d.margins.left + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft), top:e.top - this.offset.parent.top - d.margins.top + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop)}, parseInt(this.options.revert, 10) || 500, function () {
                    d._clear(b)
                })
            } else this._clear(b, c);
            return!1
        }
    }, cancel:function () {
        var b = this;
        if (this.dragging) {
            this._mouseUp({target:null}), this.options.helper == "original" ? this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper") : this.currentItem.show();
            for (var c = this.containers.length - 1; c >= 0; c--)this.containers[c]._trigger("deactivate", null, b._uiHash(this)), this.containers[c].containerCache.over && (this.containers[c]._trigger("out", null, b._uiHash(this)), this.containers[c].containerCache.over = 0)
        }
        this.placeholder && (this.placeholder[0].parentNode && this.placeholder[0].parentNode.removeChild(this.placeholder[0]), this.options.helper != "original" && this.helper && this.helper[0].parentNode && this.helper.remove(), a.extend(this, {helper:null, dragging:!1, reverting:!1, _noFinalSort:null}), this.domPosition.prev ? a(this.domPosition.prev).after(this.currentItem) : a(this.domPosition.parent).prepend(this.currentItem));
        return this
    }, serialize:function (b) {
        var c = this._getItemsAsjQuery(b && b.connected), d = [];
        b = b || {}, a(c).each(function () {
            var c = (a(b.item || this).attr(b.attribute || "id") || "").match(b.expression || /(.+)[-=_](.+)/);
            c && d.push((b.key || c[1] + "[]") + "=" + (b.key && b.expression ? c[1] : c[2]))
        }), !d.length && b.key && d.push(b.key + "=");
        return d.join("&")
    }, toArray:function (b) {
        var c = this._getItemsAsjQuery(b && b.connected), d = [];
        b = b || {}, c.each(function () {
            d.push(a(b.item || this).attr(b.attribute || "id") || "")
        });
        return d
    }, _intersectsWith:function (a) {
        var b = this.positionAbs.left, c = b + this.helperProportions.width, d = this.positionAbs.top, e = d + this.helperProportions.height, f = a.left, g = f + a.width, h = a.top, i = h + a.height, j = this.offset.click.top, k = this.offset.click.left, l = d + j > h && d + j < i && b + k > f && b + k < g;
        return this.options.tolerance == "pointer" || this.options.forcePointerForContainers || this.options.tolerance != "pointer" && this.helperProportions[this.floating ? "width" : "height"] > a[this.floating ? "width" : "height"] ? l : f < b + this.helperProportions.width / 2 && c - this.helperProportions.width / 2 < g && h < d + this.helperProportions.height / 2 && e - this.helperProportions.height / 2 < i
    }, _intersectsWithPointer:function (b) {
        var c = a.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, b.top, b.height), d = a.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, b.left, b.width), e = c && d, f = this._getDragVerticalDirection(), g = this._getDragHorizontalDirection();
        if (!e)return!1;
        return this.floating ? g && g == "right" || f == "down" ? 2 : 1 : f && (f == "down" ? 2 : 1)
    }, _intersectsWithSides:function (b) {
        var c = a.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, b.top + b.height / 2, b.height), d = a.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, b.left + b.width / 2, b.width), e = this._getDragVerticalDirection(), f = this._getDragHorizontalDirection();
        return this.floating && f ? f == "right" && d || f == "left" && !d : e && (e == "down" && c || e == "up" && !c)
    }, _getDragVerticalDirection:function () {
        var a = this.positionAbs.top - this.lastPositionAbs.top;
        return a != 0 && (a > 0 ? "down" : "up")
    }, _getDragHorizontalDirection:function () {
        var a = this.positionAbs.left - this.lastPositionAbs.left;
        return a != 0 && (a > 0 ? "right" : "left")
    }, refresh:function (a) {
        this._refreshItems(a), this.refreshPositions();
        return this
    }, _connectWith:function () {
        var a = this.options;
        return a.connectWith.constructor == String ? [a.connectWith] : a.connectWith
    }, _getItemsAsjQuery:function (b) {
        var c = this, d = [], e = [], f = this._connectWith();
        if (f && b)for (var g = f.length - 1; g >= 0; g--) {
            var h = a(f[g]);
            for (var i = h.length - 1; i >= 0; i--) {
                var j = a.data(h[i], this.widgetName);
                j && j != this && !j.options.disabled && e.push([a.isFunction(j.options.items) ? j.options.items.call(j.element) : a(j.options.items, j.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), j])
            }
        }
        e.push([a.isFunction(this.options.items) ? this.options.items.call(this.element, null, {options:this.options, item:this.currentItem}) : a(this.options.items, this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), this]);
        for (var g = e.length - 1; g >= 0; g--)e[g][0].each(function () {
            d.push(this)
        });
        return a(d)
    }, _removeCurrentsFromItems:function () {
        var a = this.currentItem.find(":data(" + this.widgetName + "-item)");
        for (var b = 0; b < this.items.length; b++)for (var c = 0; c < a.length; c++)a[c] == this.items[b].item[0] && this.items.splice(b, 1)
    }, _refreshItems:function (b) {
        this.items = [], this.containers = [this];
        var c = this.items, d = this, e = [
            [a.isFunction(this.options.items) ? this.options.items.call(this.element[0], b, {item:this.currentItem}) : a(this.options.items, this.element), this]
        ], f = this._connectWith();
        if (f)for (var g = f.length - 1; g >= 0; g--) {
            var h = a(f[g]);
            for (var i = h.length - 1; i >= 0; i--) {
                var j = a.data(h[i], this.widgetName);
                j && j != this && !j.options.disabled && (e.push([a.isFunction(j.options.items) ? j.options.items.call(j.element[0], b, {item:this.currentItem}) : a(j.options.items, j.element), j]), this.containers.push(j))
            }
        }
        for (var g = e.length - 1; g >= 0; g--) {
            var k = e[g][1], l = e[g][0];
            for (var i = 0, m = l.length; i < m; i++) {
                var n = a(l[i]);
                n.data(this.widgetName + "-item", k), c.push({item:n, instance:k, width:0, height:0, left:0, top:0})
            }
        }
    }, refreshPositions:function (b) {
        this.offsetParent && this.helper && (this.offset.parent = this._getParentOffset());
        for (var c = this.items.length - 1; c >= 0; c--) {
            var d = this.items[c];
            if (d.instance != this.currentContainer && this.currentContainer && d.item[0] != this.currentItem[0])continue;
            var e = this.options.toleranceElement ? a(this.options.toleranceElement, d.item) : d.item;
            b || (d.width = e.outerWidth(), d.height = e.outerHeight());
            var f = e.offset();
            d.left = f.left, d.top = f.top
        }
        if (this.options.custom && this.options.custom.refreshContainers)this.options.custom.refreshContainers.call(this); else for (var c = this.containers.length - 1; c >= 0; c--) {
            var f = this.containers[c].element.offset();
            this.containers[c].containerCache.left = f.left, this.containers[c].containerCache.top = f.top, this.containers[c].containerCache.width = this.containers[c].element.outerWidth(), this.containers[c].containerCache.height = this.containers[c].element.outerHeight()
        }
        return this
    }, _createPlaceholder:function (b) {
        var c = b || this, d = c.options;
        if (!d.placeholder || d.placeholder.constructor == String) {
            var e = d.placeholder;
            d.placeholder = {element:function () {
                var b = a(document.createElement(c.currentItem[0].nodeName)).addClass(e || c.currentItem[0].className + " ui-sortable-placeholder").removeClass("ui-sortable-helper")[0];
                e || (b.style.visibility = "hidden");
                return b
            }, update:function (a, b) {
                if (!e || !!d.forcePlaceholderSize)b.height() || b.height(c.currentItem.innerHeight() - parseInt(c.currentItem.css("paddingTop") || 0, 10) - parseInt(c.currentItem.css("paddingBottom") || 0, 10)), b.width() || b.width(c.currentItem.innerWidth() - parseInt(c.currentItem.css("paddingLeft") || 0, 10) - parseInt(c.currentItem.css("paddingRight") || 0, 10))
            }}
        }
        c.placeholder = a(d.placeholder.element.call(c.element, c.currentItem)), c.currentItem.after(c.placeholder), d.placeholder.update(c, c.placeholder)
    }, _contactContainers:function (b) {
        var c = null, d = null;
        for (var e = this.containers.length - 1; e >= 0; e--) {
            if (a.ui.contains(this.currentItem[0], this.containers[e].element[0]))continue;
            if (this._intersectsWith(this.containers[e].containerCache)) {
                if (c && a.ui.contains(this.containers[e].element[0], c.element[0]))continue;
                c = this.containers[e], d = e
            } else this.containers[e].containerCache.over && (this.containers[e]._trigger("out", b, this._uiHash(this)), this.containers[e].containerCache.over = 0)
        }
        if (!!c)if (this.containers.length === 1)this.containers[d]._trigger("over", b, this._uiHash(this)), this.containers[d].containerCache.over = 1; else if (this.currentContainer != this.containers[d]) {
            var f = 1e4, g = null, h = this.positionAbs[this.containers[d].floating ? "left" : "top"];
            for (var i = this.items.length - 1; i >= 0; i--) {
                if (!a.ui.contains(this.containers[d].element[0], this.items[i].item[0]))continue;
                var j = this.items[i][this.containers[d].floating ? "left" : "top"];
                Math.abs(j - h) < f && (f = Math.abs(j - h), g = this.items[i])
            }
            if (!g && !this.options.dropOnEmpty)return;
            this.currentContainer = this.containers[d], g ? this._rearrange(b, g, null, !0) : this._rearrange(b, null, this.containers[d].element, !0), this._trigger("change", b, this._uiHash()), this.containers[d]._trigger("change", b, this._uiHash(this)), this.options.placeholder.update(this.currentContainer, this.placeholder), this.containers[d]._trigger("over", b, this._uiHash(this)), this.containers[d].containerCache.over = 1
        }
    }, _createHelper:function (b) {
        var c = this.options, d = a.isFunction(c.helper) ? a(c.helper.apply(this.element[0], [b, this.currentItem])) : c.helper == "clone" ? this.currentItem.clone() : this.currentItem;
        d.parents("body").length || a(c.appendTo != "parent" ? c.appendTo : this.currentItem[0].parentNode)[0].appendChild(d[0]), d[0] == this.currentItem[0] && (this._storedCSS = {width:this.currentItem[0].style.width, height:this.currentItem[0].style.height, position:this.currentItem.css("position"), top:this.currentItem.css("top"), left:this.currentItem.css("left")}), (d[0].style.width == "" || c.forceHelperSize) && d.width(this.currentItem.width()), (d[0].style.height == "" || c.forceHelperSize) && d.height(this.currentItem.height());
        return d
    }, _adjustOffsetFromHelper:function (b) {
        typeof b == "string" && (b = b.split(" ")), a.isArray(b) && (b = {left:+b[0], top:+b[1] || 0}), "left"in b && (this.offset.click.left = b.left + this.margins.left), "right"in b && (this.offset.click.left = this.helperProportions.width - b.right + this.margins.left), "top"in b && (this.offset.click.top = b.top + this.margins.top), "bottom"in b && (this.offset.click.top = this.helperProportions.height - b.bottom + this.margins.top)
    }, _getParentOffset:function () {
        this.offsetParent = this.helper.offsetParent();
        var b = this.offsetParent.offset();
        this.cssPosition == "absolute" && this.scrollParent[0] != document && a.ui.contains(this.scrollParent[0], this.offsetParent[0]) && (b.left += this.scrollParent.scrollLeft(), b.top += this.scrollParent.scrollTop());
        if (this.offsetParent[0] == document.body || this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == "html" && a.browser.msie)b = {top:0, left:0};
        return{top:b.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0), left:b.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)}
    }, _getRelativeOffset:function () {
        if (this.cssPosition == "relative") {
            var a = this.currentItem.position();
            return{top:a.top - (parseInt(this.helper.css("top"), 10) || 0) + this.scrollParent.scrollTop(), left:a.left - (parseInt(this.helper.css("left"), 10) || 0) + this.scrollParent.scrollLeft()}
        }
        return{top:0, left:0}
    }, _cacheMargins:function () {
        this.margins = {left:parseInt(this.currentItem.css("marginLeft"), 10) || 0, top:parseInt(this.currentItem.css("marginTop"), 10) || 0}
    }, _cacheHelperProportions:function () {
        this.helperProportions = {width:this.helper.outerWidth(), height:this.helper.outerHeight()}
    }, _setContainment:function () {
        var b = this.options;
        b.containment == "parent" && (b.containment = this.helper[0].parentNode);
        if (b.containment == "document" || b.containment == "window")this.containment = [0 - this.offset.relative.left - this.offset.parent.left, 0 - this.offset.relative.top - this.offset.parent.top, a(b.containment == "document" ? document : window).width() - this.helperProportions.width - this.margins.left, (a(b.containment == "document" ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top];
        if (!/^(document|window|parent)$/.test(b.containment)) {
            var c = a(b.containment)[0], d = a(b.containment).offset(), e = a(c).css("overflow") != "hidden";
            this.containment = [d.left + (parseInt(a(c).css("borderLeftWidth"), 10) || 0) + (parseInt(a(c).css("paddingLeft"), 10) || 0) - this.margins.left, d.top + (parseInt(a(c).css("borderTopWidth"), 10) || 0) + (parseInt(a(c).css("paddingTop"), 10) || 0) - this.margins.top, d.left + (e ? Math.max(c.scrollWidth, c.offsetWidth) : c.offsetWidth) - (parseInt(a(c).css("borderLeftWidth"), 10) || 0) - (parseInt(a(c).css("paddingRight"), 10) || 0) - this.helperProportions.width - this.margins.left, d.top + (e ? Math.max(c.scrollHeight, c.offsetHeight) : c.offsetHeight) - (parseInt(a(c).css("borderTopWidth"), 10) || 0) - (parseInt(a(c).css("paddingBottom"), 10) || 0) - this.helperProportions.height - this.margins.top]
        }
    }, _convertPositionTo:function (b, c) {
        c || (c = this.position);
        var d = b == "absolute" ? 1 : -1, e = this.options, f = this.cssPosition == "absolute" && (this.scrollParent[0] == document || !a.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, g = /(html|body)/i.test(f[0].tagName);
        return{top:c.top + this.offset.relative.top * d + this.offset.parent.top * d - (a.browser.safari && this.cssPosition == "fixed" ? 0 : (this.cssPosition == "fixed" ? -this.scrollParent.scrollTop() : g ? 0 : f.scrollTop()) * d), left:c.left + this.offset.relative.left * d + this.offset.parent.left * d - (a.browser.safari && this.cssPosition == "fixed" ? 0 : (this.cssPosition == "fixed" ? -this.scrollParent.scrollLeft() : g ? 0 : f.scrollLeft()) * d)}
    }, _generatePosition:function (b) {
        var c = this.options, d = this.cssPosition == "absolute" && (this.scrollParent[0] == document || !a.ui.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, e = /(html|body)/i.test(d[0].tagName);
        this.cssPosition == "relative" && (this.scrollParent[0] == document || this.scrollParent[0] == this.offsetParent[0]) && (this.offset.relative = this._getRelativeOffset());
        var f = b.pageX, g = b.pageY;
        if (this.originalPosition) {
            this.containment && (b.pageX - this.offset.click.left < this.containment[0] && (f = this.containment[0] + this.offset.click.left), b.pageY - this.offset.click.top < this.containment[1] && (g = this.containment[1] + this.offset.click.top), b.pageX - this.offset.click.left > this.containment[2] && (f = this.containment[2] + this.offset.click.left), b.pageY - this.offset.click.top > this.containment[3] && (g = this.containment[3] + this.offset.click.top));
            if (c.grid) {
                var h = this.originalPageY + Math.round((g - this.originalPageY) / c.grid[1]) * c.grid[1];
                g = this.containment ? h - this.offset.click.top < this.containment[1] || h - this.offset.click.top > this.containment[3] ? h - this.offset.click.top < this.containment[1] ? h + c.grid[1] : h - c.grid[1] : h : h;
                var i = this.originalPageX + Math.round((f - this.originalPageX) / c.grid[0]) * c.grid[0];
                f = this.containment ? i - this.offset.click.left < this.containment[0] || i - this.offset.click.left > this.containment[2] ? i - this.offset.click.left < this.containment[0] ? i + c.grid[0] : i - c.grid[0] : i : i
            }
        }
        return{top:g - this.offset.click.top - this.offset.relative.top - this.offset.parent.top + (a.browser.safari && this.cssPosition == "fixed" ? 0 : this.cssPosition == "fixed" ? -this.scrollParent.scrollTop() : e ? 0 : d.scrollTop()), left:f - this.offset.click.left - this.offset.relative.left - this.offset.parent.left + (a.browser.safari && this.cssPosition == "fixed" ? 0 : this.cssPosition == "fixed" ? -this.scrollParent.scrollLeft() : e ? 0 : d.scrollLeft())}
    }, _rearrange:function (a, b, c, d) {
        c ? c[0].appendChild(this.placeholder[0]) : b.item[0].parentNode.insertBefore(this.placeholder[0], this.direction == "down" ? b.item[0] : b.item[0].nextSibling), this.counter = this.counter ? ++this.counter : 1;
        var e = this, f = this.counter;
        window.setTimeout(function () {
            f == e.counter && e.refreshPositions(!d)
        }, 0)
    }, _clear:function (b, c) {
        this.reverting = !1;
        var d = [], e = this;
        !this._noFinalSort && this.currentItem.parent().length && this.placeholder.before(this.currentItem), this._noFinalSort = null;
        if (this.helper[0] == this.currentItem[0]) {
            for (var f in this._storedCSS)if (this._storedCSS[f] == "auto" || this._storedCSS[f] == "static")this._storedCSS[f] = "";
            this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper")
        } else this.currentItem.show();
        this.fromOutside && !c && d.push(function (a) {
            this._trigger("receive", a, this._uiHash(this.fromOutside))
        }), (this.fromOutside || this.domPosition.prev != this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent != this.currentItem.parent()[0]) && !c && d.push(function (a) {
            this._trigger("update", a, this._uiHash())
        });
        if (!a.ui.contains(this.element[0], this.currentItem[0])) {
            c || d.push(function (a) {
                this._trigger("remove", a, this._uiHash())
            });
            for (var f = this.containers.length - 1; f >= 0; f--)a.ui.contains(this.containers[f].element[0], this.currentItem[0]) && !c && (d.push(function (a) {
                return function (b) {
                    a._trigger("receive", b, this._uiHash(this))
                }
            }.call(this, this.containers[f])), d.push(function (a) {
                return function (b) {
                    a._trigger("update", b, this._uiHash(this))
                }
            }.call(this, this.containers[f])))
        }
        for (var f = this.containers.length - 1; f >= 0; f--)c || d.push(function (a) {
            return function (b) {
                a._trigger("deactivate", b, this._uiHash(this))
            }
        }.call(this, this.containers[f])), this.containers[f].containerCache.over && (d.push(function (a) {
            return function (b) {
                a._trigger("out", b, this._uiHash(this))
            }
        }.call(this, this.containers[f])), this.containers[f].containerCache.over = 0);
        this._storedCursor && a("body").css("cursor", this._storedCursor), this._storedOpacity && this.helper.css("opacity", this._storedOpacity), this._storedZIndex && this.helper.css("zIndex", this._storedZIndex == "auto" ? "" : this._storedZIndex), this.dragging = !1;
        if (this.cancelHelperRemoval) {
            if (!c) {
                this._trigger("beforeStop", b, this._uiHash());
                for (var f = 0; f < d.length; f++)d[f].call(this, b);
                this._trigger("stop", b, this._uiHash())
            }
            return!1
        }
        c || this._trigger("beforeStop", b, this._uiHash()), this.placeholder[0].parentNode.removeChild(this.placeholder[0]), this.helper[0] != this.currentItem[0] && this.helper.remove(), this.helper = null;
        if (!c) {
            for (var f = 0; f < d.length; f++)d[f].call(this, b);
            this._trigger("stop", b, this._uiHash())
        }
        this.fromOutside = !1;
        return!0
    }, _trigger:function () {
        a.Widget.prototype._trigger.apply(this, arguments) === !1 && this.cancel()
    }, _uiHash:function (b) {
        var c = b || this;
        return{helper:c.helper, placeholder:c.placeholder || a([]), position:c.position, originalPosition:c.originalPosition, offset:c.positionAbs, item:c.currentItem, sender:b ? b.element : null}
    }}), a.extend(a.ui.sortable, {version:"1.8.17"})
})(jQuery)