angular
    .module('isa-mrs-project')
    .controller('DemoCtrl', DemoCtrl);

DemoCtrl.$inject = ['loginService', '$rootScope'];


function DemoCtrl(loginService) {
    this.topDirections = ['left', 'up'];
    this.bottomDirections = ['down', 'right'];
    this.isOpen = false;
    this.availableModes = ['md-fling', 'md-scale'];
    this.selectedMode = 'md-fling';
    this.availableDirections = ['up', 'down', 'left', 'right'];
    this.selectedDirection = 'left';


    this.logout = logout;
    function logout() {
        console.log("logout");
    };

    this.profile = profile;
    function profile() {
        loginService.redirect();
    };



}