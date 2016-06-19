angular
    .module('isa-mrs-project')
    .controller('RegisterController', RegisterController);

RegisterController.$inject = ['$location', 'userService'];
    
function RegisterController($location, userService) {
    var registerVm = this;
    
    // User parameters
    registerVm.user = {};
    
    // Click on button 'Registruj se'
    registerVm.registerGuest = registerGuest;
    
    initState();
    
    // Create DEFAULT user
    function initState() {
        registerVm.user = {
            userId: 0,
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            image: 'https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png',
            type: 'guest',
            verified: 'not_verified'
        };
    };
    
    // Guest registration
    function registerGuest() {
        userService.registerUser(registerVm.user)
          .then(function (registeredUser) {
              // Redirect to Verification page
              $location.path('verify');  
          });
    };
}