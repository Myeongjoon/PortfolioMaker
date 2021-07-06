var urlModule = {
  getParam: function (target) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
      var temp = params[i].split("=");
      if ([temp[0]] == target) { sval = temp[1]; }

    }
    return sval;
  }
}
export default urlModule;