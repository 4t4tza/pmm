define("pmm/tssDGFX", ["pmm/tssDGFXTools", "dojo/dom", "dojo/_base/array", "dojo/on", "dojo/domReady!"],
    function (tdg, dom, arr, on) {

  var overviewSurface;
  var detailSurface;
  var rooms = [];
  var healthyAnimations = [];
  var warningAnimations = [];
  var severeAnimations = [];
  var stopAnimations = false;

  return {

    initSurfaces:function (overviewParentId, detailParentId) {

      //overviewSurface = tdg.createArtboard(10, 10, 450, 450, overviewParentId, "overviewArtboard");
      overviewSurface = tdg.createDefaultArtboard(overviewParentId, "overviewArtboard");
      //detailSurface = tdg.createArtboard(10, 10, 450, 450, detailParentId, "detailArtboard");
      detailSurface = tdg.createDefaultArtboard(detailParentId, "detailArtboard");
    },

    initRoom11:function () {

      var pathStr = "M0 0 150 0 150 150 0 150z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom21:function () {

      var pathStr = "M150 0 300 0 300 150 150 150z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom31:function () {

      var pathStr = "M300 0 450 0 450 150 300 150z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom12:function () {

      var pathStr = "M0 150 150 150 150 300 0 300z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom22:function () {

      var pathStr = "M150 150 300 150 300 300 150 300z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom32:function () {

      var pathStr = "M300 150 450 150 450 300 300 300z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom13:function () {

      var pathStr = "M0 300 150 300C175 375 175 400 150 450L0 450z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom23:function () {

      var pathStr = "M150 300 300 300 C325 375 325 400 300 450L150 450C175 400 175 375 150 300z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRoom33:function () {

      var pathStr = "M300 300 450 300 450 450 300 450C325 400 325 375 300 300z";
      rooms.push(tdg.path(pathStr, overviewSurface));
    },

    initRooms:function () {

      this.initRoom11();
      this.initRoom21();
      this.initRoom31();
      this.initRoom12();
      this.initRoom22();
      this.initRoom32();
      this.initRoom13();
      this.initRoom23();
      this.initRoom33();
      arr.forEach(rooms, function(item, index) {
         item.connect("onclick", function(evt) {
           arr.forEach(rooms, function(otherItem, otherIndex) {
              if(otherIndex != index)
                rooms[otherIndex].setStroke("#333")
           });
           rooms[index].setStroke({color:"white", style:"dot",width:3});
           detailSurface.clear();
           detailSurface.createText({
             x: 50, y: 200, text: "room: " + index, align: "start"
           }).setFont({ family: "Arial", size: "20pt", weight: "bold" })
               .setFill("black");
         });
      });
    },

    initAnimations:function () {

      arr.forEach(rooms, function (item, index) {

        healthyAnimations.push(tdg.fillAnimation(rooms[index], 3000, "#444", "#090"));
        warningAnimations.push(tdg.fillAnimation(rooms[index], 1000, "#444", "#990"));
        severeAnimations.push(tdg.fillAnimation(rooms[index], 500, "#444", "#900"));
      });
    },

    drawRooms:function () {

      var stroke = "#333";
      var fill = "#ddd";

      arr.forEach(rooms, function (item, index) {

        item.setFill(fill).setStroke(stroke);
      });
    },

    animateRoomStatus:function (roomStatus) {

      var animations;
      var health = roomStatus.health;

      if (health == "healthy")
        animations = healthyAnimations;
      else if (health == "warning")
        animations = warningAnimations;
      else if (health == "severe")
        animations = severeAnimations;

      var anim = animations[(roomStatus.y * 3) + roomStatus.x];

      on(anim, "End", function () {

        anim.gotoPercent(0.0);
        if (!stopAnimations)
          anim.play();
      });

      anim.play();
    },

    stopOn:function (button) {
      on(button, "click", function (evt) {
        stopAnimations = true;
      });
    }
  }
});
