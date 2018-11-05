/* global CryptoJS */
var aesCrypto = {};

(function (obj) {
  "use strict";

  obj.encrypt = function (text, password) {
    try {
      return CryptoJS.AES.encrypt(text, password).toString();
    } catch (err) { return ''; }
  };

  obj.decrypt = function (text, password) {
    try {
      var decrypted = CryptoJS.AES.decrypt(text, password);
      return decrypted.toString(CryptoJS.enc.Utf8);
    } catch (err) { return ''; }
  };
}(aesCrypto));


