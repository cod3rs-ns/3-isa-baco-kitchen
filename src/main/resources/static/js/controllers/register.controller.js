angular
    .module('isa-mrs-project')
    .controller('RegisterController', RegisterController);

RegisterController.$inject = ['$location', 'userService'];
    
function RegisterController($location, userService) {
    // Var vm stands for ViewModel
    var registerVm = this;
    
    // Set bindable memebers at the top of the controller
    registerVm.user = {};
    registerVm.registerGuest = registerGuest;
    
    initState();
    
    function initState() {
        registerVm.user = {
            userId: 0,
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            image: 'http://www.cubadebate.cu/wp-content/uploads/2013/02/333.jpg',
            type: 'guest',
            verified: 'not_verified'
        };
    };
    
    // Implement functions later
    function registerGuest() {
        userService.registerUser(registerVm.user)
          .then(function (registeredUser) {
            $location.path('verify');  
          });
    }
}