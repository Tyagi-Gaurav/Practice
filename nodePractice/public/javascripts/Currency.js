
var Currency = function(canadianDoller) {
    this.canadianDoller = canadianDoller;
};

Currency.prototype.roundTwoDecimals = function(amount) {
  return Math.round(amount * 100) / 100;
};

Currency.prototype.CanadianToUS = function(canadian) {
  return this.roundTwoDecimals(canadian * this.canadianDoller);
};

Currency.prototype.USToCanadian = function(us) {
  return this.roundTwoDecimals(us / this.canadianDoller);
};

module.exports = Currency;