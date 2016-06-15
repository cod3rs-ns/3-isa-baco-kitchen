angular
    .module('isa-mrs-project')
    .controller('TokenController', TokenController);

TokenController.$inject = ['guestService'];

function TokenController(guestService) {
    var tokenVm = this;
    
    // Enter email for token resend
    tokenVm.email = "";
    
    // Click on 'Posalji ponovo'
    tokenVm.sendToken = sendToken;
    
    // Send email on entered email with new verification token
    function sendToken() {
        guestService.resendToken(tokenVm.email)
            .then(function(response) {

            });
    };
}