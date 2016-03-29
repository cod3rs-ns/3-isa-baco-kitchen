angular
    .module('isa-mrs-project', ['ngRoute'])
    .config(config);
    
function config($routeProvider) {
    $routeProvider
        // Route for homepage (login.html)
        .when('/', {
            templateUrl: 'views/login.html',
            controller: 'LoginController',
            controllerAs: 'loginVm'
        })
        // Route for login page
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'LoginController',
            controllerAs: 'loginVm'
        })
        // Route for register page
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'RegisterController',
            controllerAs: 'registerVm'
        })
        // Route for guest profile page
        .when('/profile-guest', {
            templateUrl: 'views/profile-guest.html',
            controller: 'GuestProfileController',
            controllerAs: 'guestProfileVm'
        });
}