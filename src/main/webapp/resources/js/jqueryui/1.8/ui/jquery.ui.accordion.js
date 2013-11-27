/*
 * jQuery UI Accordion 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Accordion
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function (a, b) {
    a.widget("ui.accordion", {options:{active:0, animated:"slide", autoHeight:!0, clearStyle:!1, collapsible:!1, event:"click", fillSpace:!1, header:"> li > :first-child,> :not(li):even", icons:{header:"ui-icon-triangle-1-e", headerSelected:"ui-icon-triangle-1-s"}, navigation:!1, navigationFilter:function () {
        return this.href.toLowerCase() === location.href.toLowerCase()
    }}, _create:function () {
        var b = this, c = b.options;
        b.running = 0, b.element.addClass("ui-accordion ui-widget ui-helper-reset").children("li").addClass("ui-accordion-li-fix"), b.headers = b.element.find(c.header).addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-all").bind("mouseenter.accordion",
            function () {
                c.disabled || a(this).addClass("ui-state-hover")
            }).bind("mouseleave.accordion",
            function () {
                c.disabled || a(this).removeClass("ui-state-hover")
            }).bind("focus.accordion",
            function () {
                c.disabled || a(this).addClass("ui-state-focus")
            }).bind("blur.accordion", function () {
                c.disabled || a(this).removeClass("ui-state-focus")
            }), b.headers.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom");
        if (c.navigation) {
            var d = b.element.find("a").filter(c.navigationFilter).eq(0);
            if (d.length) {
                var e = d.closest(".ui-accordion-header");
                e.length ? b.active = e : b.active = d.closest(".ui-accordion-content").prev()
            }
        }
        b.active = b._findActive(b.active || c.active).addClass("ui-state-default ui-state-active").toggleClass("ui-corner-all").toggleClass("ui-corner-top"), b.active.next().addClass("ui-accordion-content-active"), b._createIcons(), b.resize(), b.element.attr("role", "tablist"), b.headers.attr("role", "tab").bind("keydown.accordion",
            function (a) {
                return b._keydown(a)
            }).next().attr("role", "tabpanel"), b.headers.not(b.active || "").attr({"aria-expanded":"false", "aria-selected":"false", tabIndex:-1}).next().hide(), b.active.length ? b.active.attr({"aria-expanded":"true", "aria-selected":"true", tabIndex:0}) : b.headers.eq(0).attr("tabIndex", 0), a.browser.safari || b.headers.find("a").attr("tabIndex", -1), c.event && b.headers.bind(c.event.split(" ").join(".accordion ") + ".accordion", function (a) {
            b._clickHandler.call(b, a, this), a.preventDefault()
        })
    }, _createIcons:function () {
        var b = this.options;
        b.icons && (a("<span></span>").addClass("ui-icon " + b.icons.header).prependTo(this.headers), this.active.children(".ui-icon").toggleClass(b.icons.header).toggleClass(b.icons.headerSelected), this.element.addClass("ui-accordion-icons"))
    }, _destroyIcons:function () {
        this.headers.children(".ui-icon").remove(), this.element.removeClass("ui-accordion-icons")
    }, destroy:function () {
        var b = this.options;
        this.element.removeClass("ui-accordion ui-widget ui-helper-reset").removeAttr("role"), this.headers.unbind(".accordion").removeClass("ui-accordion-header ui-accordion-disabled ui-helper-reset ui-state-default ui-corner-all ui-state-active ui-state-disabled ui-corner-top").removeAttr("role").removeAttr("aria-expanded").removeAttr("aria-selected").removeAttr("tabIndex"), this.headers.find("a").removeAttr("tabIndex"), this._destroyIcons();
        var c = this.headers.next().css("display", "").removeAttr("role").removeClass("ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content ui-accordion-content-active ui-accordion-disabled ui-state-disabled");
        (b.autoHeight || b.fillHeight) && c.css("height", "");
        return a.Widget.prototype.destroy.call(this)
    }, _setOption:function (b, c) {
        a.Widget.prototype._setOption.apply(this, arguments), b == "active" && this.activate(c), b == "icons" && (this._destroyIcons(), c && this._createIcons()), b == "disabled" && this.headers.add(this.headers.next())[c ? "addClass" : "removeClass"]("ui-accordion-disabled ui-state-disabled")
    }, _keydown:function (b) {
        if (!(this.options.disabled || b.altKey || b.ctrlKey)) {
            var c = a.ui.keyCode, d = this.headers.length, e = this.headers.index(b.target), f = !1;
            switch (b.keyCode) {
                case c.RIGHT:
                case c.DOWN:
                    f = this.headers[(e + 1) % d];
                    break;
                case c.LEFT:
                case c.UP:
                    f = this.headers[(e - 1 + d) % d];
                    break;
                case c.SPACE:
                case c.ENTER:
                    this._clickHandler({target:b.target}, b.target), b.preventDefault()
            }
            if (f) {
                a(b.target).attr("tabIndex", -1), a(f).attr("tabIndex", 0), f.focus();
                return!1
            }
            return!0
        }
    }, resize:function () {
        var b = this.options, c;
        if (b.fillSpace) {
            if (a.browser.msie) {
                var d = this.element.parent().css("overflow");
                this.element.parent().css("overflow", "hidden")
            }
            c = this.element.parent().height(), a.browser.msie && this.element.parent().css("overflow", d), this.headers.each(function () {
                c -= a(this).outerHeight(!0)
            }), this.headers.next().each(
                function () {
                    a(this).height(Math.max(0, c - a(this).innerHeight() + a(this).height()))
                }).css("overflow", "auto")
        } else b.autoHeight && (c = 0, this.headers.next().each(
            function () {
                c = Math.max(c, a(this).height("").height())
            }).height(c));
        return this
    }, activate:function (a) {
        this.options.active = a;
        var b = this._findActive(a)[0];
        this._clickHandler({target:b}, b);
        return this
    }, _findActive:function (b) {
        return b ? typeof b == "number" ? this.headers.filter(":eq(" + b + ")") : this.headers.not(this.headers.not(b)) : b === !1 ? a([]) : this.headers.filter(":eq(0)")
    }, _clickHandler:function (b, c) {
        var d = this.options;
        if (!d.disabled) {
            if (!b.target) {
                if (!d.collapsible)return;
                this.active.removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all").children(".ui-icon").removeClass(d.icons.headerSelected).addClass(d.icons.header), this.active.next().addClass("ui-accordion-content-active");
                var e = this.active.next(), f = {options:d, newHeader:a([]), oldHeader:d.active, newContent:a([]), oldContent:e}, g = this.active = a([]);
                this._toggle(g, e, f);
                return
            }
            var h = a(b.currentTarget || c), i = h[0] === this.active[0];
            d.active = d.collapsible && i ? !1 : this.headers.index(h);
            if (this.running || !d.collapsible && i)return;
            var j = this.active, g = h.next(), e = this.active.next(), f = {options:d, newHeader:i && d.collapsible ? a([]) : h, oldHeader:this.active, newContent:i && d.collapsible ? a([]) : g, oldContent:e}, k = this.headers.index(this.active[0]) > this.headers.index(h[0]);
            this.active = i ? a([]) : h, this._toggle(g, e, f, i, k), j.removeClass("ui-state-active ui-corner-top").addClass("ui-state-default ui-corner-all").children(".ui-icon").removeClass(d.icons.headerSelected).addClass(d.icons.header), i || (h.removeClass("ui-state-default ui-corner-all").addClass("ui-state-active ui-corner-top").children(".ui-icon").removeClass(d.icons.header).addClass(d.icons.headerSelected), h.next().addClass("ui-accordion-content-active"));
            return
        }
    }, _toggle:function (b, c, d, e, f) {
        var g = this, h = g.options;
        g.toShow = b, g.toHide = c, g.data = d;
        var i = function () {
            if (!!g)return g._completed.apply(g, arguments)
        };
        g._trigger("changestart", null, g.data), g.running = c.size() === 0 ? b.size() : c.size();
        if (h.animated) {
            var j = {};
            h.collapsible && e ? j = {toShow:a([]), toHide:c, complete:i, down:f, autoHeight:h.autoHeight || h.fillSpace} : j = {toShow:b, toHide:c, complete:i, down:f, autoHeight:h.autoHeight || h.fillSpace}, h.proxied || (h.proxied = h.animated), h.proxiedDuration || (h.proxiedDuration = h.duration), h.animated = a.isFunction(h.proxied) ? h.proxied(j) : h.proxied, h.duration = a.isFunction(h.proxiedDuration) ? h.proxiedDuration(j) : h.proxiedDuration;
            var k = a.ui.accordion.animations, l = h.duration, m = h.animated;
            m && !k[m] && !a.easing[m] && (m = "slide"), k[m] || (k[m] = function (a) {
                this.slide(a, {easing:m, duration:l || 700})
            }), k[m](j)
        } else h.collapsible && e ? b.toggle() : (c.hide(), b.show()), i(!0);
        c.prev().attr({"aria-expanded":"false", "aria-selected":"false", tabIndex:-1}).blur(), b.prev().attr({"aria-expanded":"true", "aria-selected":"true", tabIndex:0}).focus()
    }, _completed:function (a) {
        this.running = a ? 0 : --this.running;
        this.running || (this.options.clearStyle && this.toShow.add(this.toHide).css({height:"", overflow:""}), this.toHide.removeClass("ui-accordion-content-active"), this.toHide.length && (this.toHide.parent()[0].className = this.toHide.parent()[0].className), this._trigger("change", null, this.data))
    }}), a.extend(a.ui.accordion, {version:"1.8.17", animations:{slide:function (b, c) {
        b = a.extend({easing:"swing", duration:300}, b, c);
        if (!b.toHide.size())b.toShow.animate({height:"show", paddingTop:"show", paddingBottom:"show"}, b); else {
            if (!b.toShow.size()) {
                b.toHide.animate({height:"hide", paddingTop:"hide", paddingBottom:"hide"}, b);
                return
            }
            var d = b.toShow.css("overflow"), e = 0, f = {}, g = {}, h = ["height", "paddingTop", "paddingBottom"], i, j = b.toShow;
            i = j[0].style.width, j.width(j.parent().width() - parseFloat(j.css("paddingLeft")) - parseFloat(j.css("paddingRight")) - (parseFloat(j.css("borderLeftWidth")) || 0) - (parseFloat(j.css("borderRightWidth")) || 0)), a.each(h, function (c, d) {
                g[d] = "hide";
                var e = ("" + a.css(b.toShow[0], d)).match(/^([\d+-.]+)(.*)$/);
                f[d] = {value:e[1], unit:e[2] || "px"}
            }), b.toShow.css({height:0, overflow:"hidden"}).show(), b.toHide.filter(":hidden").each(b.complete).end().filter(":visible").animate(g, {step:function (a, c) {
                c.prop == "height" && (e = c.end - c.start === 0 ? 0 : (c.now - c.start) / (c.end - c.start)), b.toShow[0].style[c.prop] = e * f[c.prop].value + f[c.prop].unit
            }, duration:b.duration, easing:b.easing, complete:function () {
                b.autoHeight || b.toShow.css("height", ""), b.toShow.css({width:i, overflow:d}), b.complete()
            }})
        }
    }, bounceslide:function (a) {
        this.slide(a, {easing:a.down ? "easeOutBounce" : "swing", duration:a.down ? 1e3 : 200})
    }}})
})(jQuery)