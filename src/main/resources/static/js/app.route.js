angular
    .module('isa-mrs-project', ['ngRoute', 'ngMaterial', 'ui.bootstrap', 'mwl.calendar', 'ngMessages', 'mgo-angular-wizard', 'flow'])
    .config(['$routeProvider', '$httpProvider', routeConfig])
    .config(['flowFactoryProvider', uploadConfig]);

function routeConfig($routeProvider, $httpProvider) {
    $routeProvider
        // Route for homepage
        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeController',
            controllerAs: 'homeVm'
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
          templateUrl: 'views/messages/registered.html',
          controller: 'TokenController',
          controllerAs: 'tokenVm'
        })
        // Route for guest profile page
        .when('/profile-guest/:guestId', {
            templateUrl: 'views/profile-guest.html',
            controller: 'GuestProfileController',
            controllerAs: 'guestVm'
        })
		    // Route for cook profile page
        .when('/profile-cook/', {
            templateUrl: 'views/profile-cook.html',
            controller: 'CookProfileController',
            controllerAs: 'cookProfileVm'
        })
        // Route for barman profile page
        .when('/profile-barman/', {
            templateUrl: 'views/profile-barman.html',
            controller: 'BarmanProfileController',
            controllerAs: 'barmanProfileVm'
        })
        // Route for waiter profile page
        .when('/profile-waiter/', {
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
        // Route for reservation invite
        .when('/invite/:reservationId', {
            templateUrl: 'views/reservation-invite.html',
            controller: 'ReservationInviteController',
            controllerAs: 'inviteVm'
        })
        // Route for verification messages
        .when('/registration-confirm-wrong-link', {
            templateUrl: 'views/messages/verified-wrong.html',
            controller: 'TokenController',
            controllerAs: 'tokenVm'
        })
        .when('/registration-confirm-expired-link', {
            templateUrl: 'views/messages/verified-expired.html',
            controller: 'TokenController',
            controllerAs: 'tokenVm'
        })
        .when('/registration-confirm-success', {
            templateUrl: 'views/messages/verified-ok.html'
        })
        // Route for wrong URL address
        .otherwise({
            redirectTo: '/'
        });

        $httpProvider.interceptors.push(['$q', '$window', '$location', function($q, $window, $location) {
            return {
              // Set Header to Request if user is logged
              'request': function (config) {
                    var token = $window.localStorage.getItem('AUTH_TOKEN');
                        if (token !== null && token!=="null") {
                            config.headers.Authorization = 'Bearer ' + token;
                        }
                        return config;
                },
              // When try to get Unauthorized page
              'responseError': function(response) {
                    if(response.status === 401 || response.status === 403) {
                        $location.path('/');
                    }
                    return $q.reject(response);
                }
              };
            }]);
}

function uploadConfig(flowFactoryProvider) {
    flowFactoryProvider.defaults = {
        target: 'http://localhost:8091/api/upload',
        permanentErrors: [404, 500, 501],
        maxChunkRetries: 1,
        uploadMethod: 'POST',
        chunkRetryInterval: 5000,
        simultaneousUploads: 4,
        singleFile: true
    };

    flowFactoryProvider.on('catchAll', function (event) {
        console.log('catchAll', arguments);
    });
}
