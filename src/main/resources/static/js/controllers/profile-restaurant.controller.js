angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);

RestaurantProfileController.$inject = ['restaurantService', 'userService', 'reviewService', 'tableService', 'guestService', '$mdDialog', '$routeParams'];

function RestaurantProfileController(restaurantService, userService, reviewService, tableService, guestService, $mdDialog, $routeParams, SingleDrinkController){
    var restaurantVm = this;
    
    restaurantVm.restaurant = {};
    restaurantVm.reviews = [];
    restaurantVm.allTables = [];
    restaurantVm.addManagerOption = false;
    restaurantVm.review = {
      // reviewId: 1,
      restaurantRate: 1,
      foodRate: 1,
      serviceRate: 1,
      reservation: 1,
      comment: ''
    };
    
    restaurantVm.reservationTables = [];
    restaurantVm.currentUserFriends = [];
    
    restaurantVm.sendReview = sendReview;
    restaurantVm.inviteFriend = inviteFriend;
    restaurantVm.getTablesByRestaurant = getTablesByRestaurant;
    restaurantVm.selectTable = selectTable;
    restaurantVm.saveReservation = saveReservation;

    activate();

    function activate() {
        getRestaurant($routeParams.restaurantId).then(function() {
            //alert('Restaurant retreived from database.')
            restaurantVm.worktime = restaurantVm.restaurant.startTime + ' h : '
                                    + restaurantVm.restaurant.endTime + ' h'
        });


        setPriorities().then(function(){
        });
        
        getReviews($routeParams.restaurantId).then(function() {
          
        });
        
        getTablesByRestaurant().then(function() {

        });
        
        getFriends().then(function() {
          
        });
    };
    
    function sendReview() {
        return reviewService.addReview(restaurantVm.review)
            .then(function(data) {
                restaurantVm.reviews.push(data);
                
                restaurantVm.review = {
                  // reviewId: 1,
                  restaurantRate: 1,
                  foodRate: 1,
                  serviceRate: 1,
                  reservation: 1,
                  comment: ''
                };
            });
    };

    function setPriorities() {
        return userService.getRegisteredUser()
            .then(function(data) {
                restaurantVm.addManagerOption = (data.type == "system_manager");
            });
    };

    function getRestaurant(id) {
        return restaurantService.getRestaurant(id)
            .then(function(data) {
                restaurantVm.restaurant = data;
                return restaurantVm.restaurant;
            });
    };
    
    function getReviews(id) {
        return reviewService.getReviews(id)
            .then(function(data) {
                restaurantVm.reviews = data;
                return restaurantVm.reviews;
            });
    };

    restaurantVm.createDrink = createDrink;

    function createDrink(){
        $mdDialog.show({
            controller: 'SingleDrinkController',
            controllerAs: 'drinkVm',
            templateUrl: '/views/dialogs/drink-form-tmpl.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: false,
        });
    };

    restaurantVm.test = test;
    function test(){
        $mdDialog.show({
            templateUrl: '/views/dialogs/single-emloyee-tmpl.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: false,
        });
    };

    restaurantVm.addRestaurantManager = addRestaurantManager;
    function addRestaurantManager() {
        $mdDialog.show({
            controller: 'SingleRManagerController',
            controllerAs: 'rmanagerVm',
            templateUrl: '/views/dialogs/single-rmanager-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : null,
                restaurant_id :  restaurantVm.restaurant.restaurantId
            }
        });
    }
    
    function getTablesByRestaurant() {
        //TODO SET RESTAURANT ID
        return tableService.getTablesByRestaurant($routeParams.restaurantId)
            .then(function(data) {
                restaurantVm.allTables = data;
                
                for (var table in restaurantVm.allTables) {
                    restaurantVm.allTables[table].color = '#00ff00';
                }
                return restaurantVm.allTables;
            });
    };
    
    function selectTable(tableId) {
        /*for (var table in restaurantVm.allTables) {
            if (restaurantVm.allTables[table].tableId == tableId){
                restaurantVm.selectedTable = restaurantVm.allTables[table];
                if(restaurantVm.selectedTable.color !== '#CDDC39'){
                    restaurantVm.selectedTable = -1;
                    restaurantVm.selectedTableOrders.length = 0;
                }
                else{
                    getOrders();
                }
                break;
            }
        }*/
        for (var table in restaurantVm.allTables) {
            if (restaurantVm.allTables[table].tableId == tableId) {
                if (restaurantVm.allTables[table].color == '#00ff00') {
                    restaurantVm.reservationTables.push(tableId);
                    restaurantVm.allTables[table].color = '#ff0000';
                }
                else {
                    for (var rt in restaurantVm.reservationTables) {
                        if (restaurantVm.reservationTables[rt] == tableId) {
                            restaurantVm.reservationTables.splice(rt, 1);
                            break;
                        }
                    }
                    restaurantVm.allTables[table].color = '#00ff00';
                }
            }
        }
    };
    
    function getFriends() {
        return guestService.getFriends(-1).then(function(data) {
            restaurantVm.currentUserFriends = data;
            return restaurantVm.friends;
        });
    };
    
    function inviteFriend(friendId) {
        for (var friend in restaurantVm.currentUserFriends) {
            if (restaurantVm.currentUserFriends[friend].userId == friendId) {
                restaurantVm.currentUserFriends[friend].invited = true;
            }
        }
    };
    
    function saveReservation() {
        alert('Make reservation');
    };
}
