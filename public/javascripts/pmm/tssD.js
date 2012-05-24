define("pmm/tssD",
    ["dijit/registry", "dijit/TitlePane", "dojo/_base/xhr", "dojo/domReady!"],
    function (reg, dTitlePane, xhr) {

      return {

        initHandlers:function () {
          var detailViewTitlePane = reg.byId('detailedView');
          detailViewTitlePane.toggle();
        },
        fetchRoom:function(roomNo) {
          // Execute a HTTP GET request
          xhr.get({
            // The URL to request
            url: "room/"+roomNo,
            // The method that handles the request's successful result
            // Handle the response any way you'd like!
            load: function(result) {
              console.log("The message is: " + result);
            }
          });

        }}}
    );