define("pmm/mvc", [
  "dojox/mvc"
], function (mvc) {

  return {

    initModel:function () {
      return dojox.mvc.newStatefulModel({ data:{
        "First":"John",
        "Last":"Doe",
        "Email":"jdoe@example.com"
      }});
    }
  };
});