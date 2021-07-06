var graphModule = {
  checkPriceRate: function (priceRate) {
    //3퍼 하락
    if (priceRate <= -3) {
      return "<span class = 'bold_blue'>" + priceRate + "</span>";
    } else if (priceRate >= 3) {
      return "<span class = 'bold_red'>" + priceRate + "</span>";
    } else if (priceRate > 0) {
      return "<span class = 'red'>" + priceRate + "</span>";
    } else if (priceRate < 0) {
      return "<span class = 'blue'>" + priceRate + "</span>";
    } else {
      return priceRate;
    }
  }
}
export default graphModule;