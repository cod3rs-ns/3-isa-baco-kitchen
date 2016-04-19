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
        // Route for after register message
        .when('/verify', {
          templateUrl: 'views/messages/registered.html'
        })
        // Route for guest profile page
        .when('/profile-guest', {
            templateUrl: 'views/profile-guest.html',
            controller: 'GuestProfileController',
            controllerAs: 'guestProfileVm'
        })
		// Route for cook profile page
        .when('/profile-cook/:cookId', {
            templateUrl: 'views/profile-cook.html',
            controller: 'CookProfileController',
            controllerAs: 'cookProfileVm'
        })
        // Route for barman profile page
        .when('/profile-barman/:barmanId', {
            templateUrl: 'views/profile-barman.html',
            controller: 'BarmanProfileController',
            controllerAs: 'barmanProfileVm'
        })
        // Route for waiter profile page
        .when('/profile-waiter/:waiterId', {
            templateUrl: 'views/profile-waiter.html',
            controller: 'WaiterProfileController',
            controllerAs: 'waiterProfileVm'
        })
        // Route for waiter profile page
        .when('/profile-provider', {
            templateUrl: 'views/profile-provider.html',
            controller: 'ProviderProfileController',
            controllerAs: 'providerVm'
        })
		    // Route for system manager profile page
        .when('/profile-system-manager', {
            templateUrl: 'views/profile-system-manager.html',
            controller: 'SystemManagerProfileController',
            controllerAs: 'systemManagerProfileVm'
        })
        // Route for restaurant profile page
        .when('/profile-restaurant/:restaurantId', {
            templateUrl: 'views/profile-restaurant.html',
            controller: 'RestaurantProfileController',
            controllerAs: 'restaurantVm'
        })
        // Route for restaurant manager profile page
        .when('/profile-restaurant-manager', {
            templateUrl: 'views/profile-restaurant-manager.html',
            controller: 'RestaurantManagerController',
            controllerAs: 'rmanagerVm'
        })
        // Route for verification messages
        .when('/registration-confirm-wrong-link', {
            templateUrl: 'views/messages/verified-wrong.html'
        })
        .when('/registration-confirm-expired-link', {
            templateUrl: 'views/messages/verified-expired.html'
        })
        .when('/registration-confirm-success', {
            templateUrl: 'views/messages/verified-ok.html'
        });
}
