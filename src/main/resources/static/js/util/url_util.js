var urlModule = {
  getParem: function () {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
      temp = params[i].split("=");
      if ([temp[0]] == sname) { sval = temp[1]; }

    }
    return sval;
  }
}
export default urlModule;