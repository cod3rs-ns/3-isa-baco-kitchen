angular
    .module('isa-mrs-project')
    .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['loginService', '$rootScope'];


function ToolbarController(loginService) {
    this.isOpen = false;
    this.selectedMode = 'md-scale';
    this.selectedDirection = 'left';

    this.logout = logout;
    function logout() {
        loginService.logout();
    };

    this.profile = profile;
    function profile() {
        loginService.redirectProfile();
    };

}