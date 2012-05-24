define("pmm/tssDGFXTools",
    ["dojo/fx",  "dojo/fx/easing", "dojox/gfx", "dojox/gfx/fx", "dojo/dom","dojo/dom-geometry", "dojo/domReady!"],
    function (fx, fxe, gfx, gfxfx, dom, geom) {

    return {

        min: function(val1, val2) {
          var min;
          if(val1 <= val2)
            min = val1;
          else
            min = val2;

          return min;
        },

        clamp: function(min, max, val) {

          var result;

          if(val < min)
            result = min;
          else if(val > max)
            result = max;
          else
            result = val;

          return result;
        },

        createDefaultArtboard: function(parentNodeId, artboardId) {

          var parentNodeInDom = dom.byId(parentNodeId);

          var position = geom.position(parentNodeInDom, false)

          var width = position.w;
          var height = position.h;

          if(width == 0 && height != 0)
            width = height;
          else if(height == 0 && width != 0)
            height = width;

          var side = this.min(height, width);


          return this.createArtboard(0,0,height,width,parentNodeId, artboardId);
        },

        createArtboard:function (xOrigin, yOrigin, width, height, parentNodeId, artBoardId) {

            var artBoardStyle = "position:relative;margin:0;padding:0;border:0p;background-color:#fff;";
            artBoardStyle += "width:" + width + "px;";
            artBoardStyle += "height:" + height + "px;";
            artBoardStyle += "top:" + yOrigin + "px;";
            artBoardStyle += "left:" + xOrigin + "px;";

            var parentNode = dom.byId(parentNodeId);

            parentNode.innerHTML += "<div id=\'" + artBoardId + "\' style=\'" + artBoardStyle + "\'></div>";

            return gfx.createSurface(artBoardId, width, height);
        },

        rect:function (xOrigin, yOrigin, width, height, stroke, fill, surface) {

            return surface.createRect({x:xOrigin, y:yOrigin, width:width, height:height})
                .setStroke(stroke)
                .setFill(fill);
        },

        polyLine:function (points, stroke, surface) {

            return surface.createPolyline(points).setStroke(stroke);
        },

        path:function (pathString, surface) {

            return surface.createPath(pathString);
        },

        fillAnimation:function (shape, duration, firstColor, secondColor) {

            var animation1 = gfxfx.animateFill({shape:shape, duration:duration, color:{start:firstColor, end:secondColor}});
            var animation2 = gfxfx.animateFill({shape:shape, duration:duration, color:{start:secondColor, end:firstColor}});

            animation1.easing = fxe.quadIn;
            animation2.easing = fxe.quadOut;

            return fx.chain([
                animation1,
                animation2
            ]);
        }
    }
});
