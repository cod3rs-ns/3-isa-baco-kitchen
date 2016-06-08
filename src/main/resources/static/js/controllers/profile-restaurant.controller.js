angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);

RestaurantProfileController.$inject = ['restaurantService', 'userService', 'reviewService', 'tableService', 'guestService', 'reservationService', '$mdDialog', '$mdToast', '$routeParams'];

function RestaurantProfileController(restaurantService, userService, reviewService, tableService, guestService, reservationService, $mdDialog, $mdToast, $routeParams, SingleDrinkController){
    var restaurantVm = this;
    
    restaurantVm.restaurant = {};
    restaurantVm.reviews = [];
    restaurantVm.allTables = [];
    restaurantVm.freeTables = [];
    restaurantVm.addManagerOption = false;
    restaurantVm.review = {
      // reviewId: 1,
      restaurantRate: 1,
      foodRate: 1,
      serviceRate: 1,
      reservation: 1,
      comment: ''
    };
    
    restaurantVm.DateTime = {};
    restaurantVm.reservation = {
      restaurant: null,
      reservationDateTime: null,
      length: 0
    };
    restaurantVm.reservationTables = [];
    restaurantVm.currentUserFriends = [];
    
    restaurantVm.sendReview = sendReview;
    restaurantVm.inviteFriend = inviteFriend;
    restaurantVm.getTablesByRestaurant = getTablesByRestaurant;
    restaurantVm.selectTable = selectTable;
    restaurantVm.saveReservation = saveReservation;
    restaurantVm.showToast = showToast;
    restaurantVm.cancel = cancel;
    restaurantVm.showReservationDialog = showReservationDialog;
    restaurantVm.finishReservation = finishReservation;
    restaurantVm.continueReservation = continueReservation;
    restaurantVm.ok = false;
    restaurantVm.reservationOkay = reservationOkay;
    
    function reservationOkay() {
      return restaurantVm.ok;
    }

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
                return restaurantVm.allTables;
            });
    };
    
    function getFreeTables() {
        return tableService.getFreeTables(
          // restaurantVm.reservation.reservationId, 
          $routeParams.restaurantId,
          restaurantVm.reservation.reservationDateTime,
          restaurantVm.reservation.length)
          .then(function(data) {
              restaurantVm.freeTables = data;
              
              for (var table in restaurantVm.allTables) {
                  var p = true;
                  for (var free in restaurantVm.freeTables) {
                      if (restaurantVm.freeTables[free].tableId == restaurantVm.allTables[table].tableId) {
                        restaurantVm.allTables[table].color = '#00ff00';
                        p = false;
                        break;
                      }
                  }
                  if (p) {
                    restaurantVm.allTables[table].color = '#ff0000';
                  }
              }
          }); 
    }
    
    function selectTable(tableId) {
        var p = true;
        for (var table in restaurantVm.allTables) {
            if (restaurantVm.allTables[table].tableId == tableId) {
                for (var free in restaurantVm.freeTables) {
                  if (restaurantVm.freeTables[free].tableId == tableId) {
                    // -------------------
                    p = false;
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
                    // -------------------
                }
                
              }
            }
        }
        
        if (p) {
          showToast('Odabrani sto nije dostupan u ovom terminu!');
        }
    };
    
    function getFriends() {
        return guestService.getFriends(-1).then(function(data) {
            restaurantVm.currentUserFriends = data;
            return restaurantVm.friends;
        });
    };
    
    function inviteFriend(friendId) {
        var invitedFriend = null;
      
        for (var friend in restaurantVm.currentUserFriends) {
            if (restaurantVm.currentUserFriends[friend].userId == friendId) {
                invitedFriend = restaurantVm.currentUserFriends[friend];
                restaurantVm.currentUserFriends[friend].invited = true;
                restaurantVm.currentUserFriends.splice(friend, 1);
            }
        }
        
        return reservationService.inviteFriend(restaurantVm.reservation.reservationId, invitedFriend.email)
          .then(function(data) { 
            showToast('Poziv je poslat za ' + invitedFriend.firstName + ' ' + invitedFriend.lastName + '.');
          });
    };
    
    function saveReservation() {
      reservationService.saveTables(restaurantVm.reservation.reservationId, restaurantVm.reservationTables)
          .then(function(data) {
              if (data.answer == "WRONG_TABLES") {
                showToast('Neki od stolova su zauzeti u međuvremenu. Odaberite Vaše stolove ponovo.');
                getFreeTables();
                restaurantVm.reservationTables = [];
                restaurantVm.ok = false;
              }
              else if (data.answer == "NO_TABLES"){
                showToast('Morate odabrati bar jedan sto!');
                restaurantVm.ok = false;
              }
              else {
                showToast('Rezervacija uspješno kreirana. Možete pozvati prijatelje da Vam se pridruže!');
                restaurantVm.ok = true;
              }
          });
    };
    
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };
    
    function cancel() {
        $mdDialog.cancel();
    };
    
    function showReservationDialog() {
      $mdDialog.show({
          controller: 'RestaurantProfileController',
          controllerAs: 'restaurantVm',
          templateUrl: '/views/dialogs/reservation-form-tmpl.html',
          parent: angular.element(document.body),
          clickOutsideToClose: true,
          fullscreen: false
      });
    };
    
    function finishReservation() {
        if (restaurantVm.ok) {
          $mdDialog.cancel();
        }
        else {
          showToast('Vratite se na prethodni korak.');
        }
    };
    
    function continueReservation() {
      restaurantVm.reservation.restaurant = restaurantVm.restaurant;
      restaurantVm.reservation.reservationDateTime = new Date(
        restaurantVm.DateTime.date.getFullYear(),
        restaurantVm.DateTime.date.getMonth(),
        restaurantVm.DateTime.date.getDate(),
        restaurantVm.DateTime.hours,
        restaurantVm.DateTime.mins,
        0,
        0
      );

      return reservationService.addReservation(restaurantVm.reservation)
          .then(function(data) {
              restaurantVm.reservation = data;
              
              getFreeTables().then(function() {
                
              });
          });
    };
}
