angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);

RestaurantProfileController.$inject = ['WizardHandler', 'restaurantService', 'menuItemService', 'userService', 'reviewService', 'tableService', 'guestService', 'reservationService', '$mdDialog', '$mdToast', '$routeParams'];

function RestaurantProfileController(WizardHandler, restaurantService, menuItemService, userService, reviewService, tableService, guestService, reservationService, $mdDialog, $mdToast, $routeParams, SingleDrinkController){
    var restaurantVm = this;

    restaurantVm.restaurant = {};
    restaurantVm.foodMenu = [];
    restaurantVm.drinkMenu = [];
    restaurantVm.reviews = [];
    // All restaurant's tables
    restaurantVm.allTables = [];
    // Free tables in restaurant
    restaurantVm.freeTables = [];
    restaurantVm.addManagerOption = false;
    restaurantVm.DateTime = {};
    // Inital reservation's state
    restaurantVm.reservation = {
      restaurant: null,
      reservationDateTime: null,
      length: 0
    };
    // Check if guest logged - then show 'Rezervisi' button
    restaurantVm.isGuestLogged = false;
    // Checked tables for reservation
    restaurantVm.reservationTables = [];
    // Get user's friends for invitation
    restaurantVm.currentUserFriends = [];
    // Number of places in reservation
    restaurantVm.guestsNumber = 0;
    restaurantVm.guestsInvited = 0;

    restaurantVm.getTablesByRestaurant = getTablesByRestaurant;
    restaurantVm.saveReservation = saveReservation;
    restaurantVm.showToast = showToast;
    restaurantVm.cancel = cancel;

    // Call friend function
    restaurantVm.inviteFriend = inviteFriend;
    // Click on table
    restaurantVm.selectTable = selectTable;
    // Open reservation dialog
    restaurantVm.showReservationDialog = showReservationDialog;
    // Continue to next step of reservation
    restaurantVm.continueReservation = continueReservation;
    // Try to finish reservation
    restaurantVm.finishReservation = finishReservation;

    activate();

    function activate() {
        getRestaurant($routeParams.restaurantId).then(function(data) {
            restaurantVm.restaurant = data;
            //alert('Restaurant retreived from database.')
            restaurantVm.worktime = restaurantVm.restaurant.startTime + ' h : '
                                    + restaurantVm.restaurant.endTime + ' h';
            initMap();
            menuItemService.getAllActiveByType('food', $routeParams.restaurantId).success(function(data) {
                restaurantVm.foodMenu = data;
            });

            menuItemService.getAllActiveByType('drink', $routeParams.restaurantId).success(function(data) {
                restaurantVm.drinksMenu = data;
            });
        });

        setPriorities();
        getReviews($routeParams.restaurantId);
        getTablesByRestaurant();


        // If guest is logged then get it's friends
        isLoggedIn().then(function() {
            if (restaurantVm.isGuestLogged) {
                getFriends();
            }
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
            .then(function(response) {
                restaurantVm.reviews = response.data;
                return restaurantVm.reviews;
            });
    };

    function isLoggedIn() {
        return guestService.isLoggedIn()
            .then(function(response) {
                restaurantVm.isGuestLogged = response.data;
                return restaurantVm.isGuestLogged;
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
        return tableService.getTablesByRestaurant($routeParams.restaurantId)
            .then(function(data) {
                restaurantVm.allTables = data;
                return restaurantVm.allTables;
            });
    };

    // Function for get all tables and coloring free
    function getFreeTables() {
        return tableService.getFreeTables(
            $routeParams.restaurantId,
            restaurantVm.reservation.reservationDateTime.getTime(),
            restaurantVm.reservation.length)
          .then(function(data) {
              restaurantVm.freeTables = data;
              // Change colors of free and busy tables
              for (var table in restaurantVm.allTables) {
                  var p = true;
                  for (var free in restaurantVm.freeTables) {
                      // This is free table
                      if (restaurantVm.freeTables[free].tableId == restaurantVm.allTables[table].tableId) {
                        restaurantVm.allTables[table].color = '#4CAF50';
                        p = false;
                        break;
                      }
                  }

                  // If table is not found in free tables
                  if (p) {
                    restaurantVm.allTables[table].color = '#F44336';
                  }
              }
          });
    }

    // Click on table
    function selectTable(tableId) {
        var index;
        for (index = 0; index < restaurantVm.allTables.length; ++index) {
            if (restaurantVm.allTables[index].tableId == tableId) {
                p = false;
                // If current color is FREE
                if (restaurantVm.allTables[index].color == '#4CAF50') {
                    // Add table to reervation tables
                    restaurantVm.reservationTables.push(tableId);
                    // Change color to MY_RESERVED
                    restaurantVm.allTables[index].color = '#FF5722';
                }
                // If current color is MY_RESERVED
                else if (restaurantVm.allTables[index].color == '#FF5722') {
                    for (var rt in restaurantVm.reservationTables) {
                        if (restaurantVm.reservationTables[rt] == tableId) {
                            restaurantVm.reservationTables.splice(rt, 1);
                            break;
                        }
                    }
                    // Change color to FREE
                    restaurantVm.allTables[index].color = '#4CAF50';
                }
                // If current color is ALREADY_RESERVED
                else if (restaurantVm.allTables[index].color == '#F44336') {
                    showToast('Odabrani sto je već rezervisan');
                }
            }
        }
    };

    function getFriends() {
        return guestService.getFriends(-1).then(function(response) {
            restaurantVm.currentUserFriends = response.data;
            return restaurantVm.currentUserFriends;
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

        restaurantVm.guestsInvited += 1;
        return reservationService.inviteFriend(restaurantVm.reservation.reservationId, invitedFriend.email)
          .then(function(response) {
              showToast('Poziv je poslat za ' + invitedFriend.firstName + ' ' + invitedFriend.lastName + '.');
          });
    };

    restaurantVm.enterValidation = function() {
      return false;
    };

    function saveReservation() {
      var requestBody = {
          reservation: restaurantVm.reservation,
          tables: restaurantVm.reservationTables
      }
      reservationService.saveReservationWithTables(requestBody)
          .then(function(response) {
              if (response.data.answer == "WRONG_TABLES") {
                showToast('Neki od stolova su zauzeti u međuvremenu. Odaberite Vaše stolove ponovo.');
                getFreeTables();
                restaurantVm.reservationTables = [];
              }
              else if (response.data.answer == "NO_TABLES") {
                showToast('Morate odabrati bar jedan sto!');
              }
              else if (response.data.answer == "WRONG_RESERVATION") {
                showToast('Pogrešni parametri rezervacije!');
              }
              else {
                restaurantVm.reservation = response.data;
                showToast('Rezervacija uspješno kreirana. Možete pozvati prijatelje da Vam se pridruže!');
                var index;
                for (index = 0; index < restaurantVm.reservationTables.length; ++index) {
                    var i;
                    for (i = 0; i < restaurantVm.allTables.length; ++i) {
                        if (restaurantVm.allTables[i].tableId == restaurantVm.reservationTables[index]) {
                            restaurantVm.guestsNumber += restaurantVm.allTables[i].positions;
                            break;
                        }
                    }
                }
                WizardHandler.wizard().goTo(2);
              }
          });
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            parent    : angular.element(document.querySelectorAll('#toast-box')),
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong></md-toast>'
        });
    };

    // Cancel reservation dialog
    function cancel() {
        $mdDialog.cancel();
    };

    // Open reservation dialog
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
        $mdDialog.cancel();
    };

    // Set reservation date and time and go to the next step
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

      getFreeTables();
    };

    function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 16,
            center: {lat: -34.397, lng: 150.644}
        });

        var geocoder = new google.maps.Geocoder();
        geocodeAddress(geocoder, map);
    }

    function geocodeAddress(geocoder, resultsMap) {
        var address = restaurantVm.restaurant.address;

        geocoder.geocode({'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: resultsMap,
                position: results[0].geometry.location
            });
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }

}
