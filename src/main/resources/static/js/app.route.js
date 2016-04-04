angular
    .module('isa-mrs-project', ['ngRoute', 'ngMaterial', 'ui.bootstrap', 'mwl.calendar', 'ngMessages'])
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
        })
		// Route for cook profile page
        .when('/profile-cook', {
            templateUrl: 'views/profile-cook.html',
            controller: 'CookProfileController',
            controllerAs: 'cookProfileVm'
        })
		// Route for barman profile page
        .when('/profile-barman', {
            templateUrl: 'views/profile-barman.html',
            controller: 'BarmanProfileController',
            controllerAs: 'barmanProfileVm'
        })
		// Route for waiter profile page
        .when('/profile-waiter', {
            templateUrl: 'views/profile-waiter.html',
            controller: 'WaiterProfileController',
            controllerAs: 'waiterProfileVm'
        })
        // Route for waiter profile page
        .when('/profile-provider', {
            templateUrl: 'views/profile-provider.html',
            controller: 'ProviderProfileController',
            controllerAs: 'providerProfileVm'
        })
		// Route for system manager profile page
        .when('/profile-system-manager', {
            templateUrl: 'views/profile-system-manager.html',
            controller: 'SystemManagerProfileController',
            controllerAs: 'systemManagerProfileVm'
        })
        // Route for restaurant profile page
        .when('/profile-restaurant', {
            templateUrl: 'views/profile-restaurant.html',
            controller: 'RestaurantProfileController',
            controllerAs: 'restaurantVm'
        });
}
