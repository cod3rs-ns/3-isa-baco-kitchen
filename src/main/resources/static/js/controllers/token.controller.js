angular
    .module('isa-mrs-project')
    .controller('TokenController', TokenController);

TokenController.$inject = ['guestService'];

function TokenController(guestService) {
  
    var tokenVm = this;
    
    tokenVm.email = "";
    tokenVm.sendToken = sendToken;
    
    function sendToken() {
        alert(tokenVm.email);
        guestService.resendToken(tokenVm.email)
            .then(function(response) {

            });
    };
}