angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['$routeParams', '$location', '$mdToast', '$mdDialog', 'guestService', 'reviewService'];

function GuestProfileController($routeParams, $location, $mdToast, $mdDialog, guestService, reviewService) {
    var guestVm = this;

    // Check if logged user's profile
    guestVm.admin = false;
    // Edit button clicked
    guestVm.editMode = false;
    // User informations
    guestVm.user = {};
    // User informations used for cancel editing
    guestVm.realUser = {};
    // List of friend requests
    guestVm.friendRequests = [];
    // List of friends
    guestVm.friends = [];
    // Check if current user is logged user's friend
    guestVm.isFriend = false;
    // If request is send but friendship is not accepted and not rejected
    guestVm.sendRequest = false;
    
    // Search parameters - query and result
    guestVm.query = "";
    guestVm.queryResult = [];
    
    // List of active reservations, already visited restaurants and invitations
    guestVm.activeReservations = [];
    guestVm.visits = [];
    guestVm.invitations = [];
    
    
    // Edit profile functions
    guestVm.editProfile = editProfile;
    guestVm.saveChanges = saveChanges;
    guestVm.cancel = cancel;
    guestVm.changePassword = changePassword;
    
    // Friend and user functions
    guestVm.acceptFriend = acceptFriend;
    guestVm.rejectFriend = rejectFriend;
    guestVm.removeFriend = removeFriend;
    guestVm.addFriend = addFriend;
    guestVm.to = to;
    
    // Invitations, visits and active reservations functions
    guestVm.cancelReservation = cancelReservation;
    guestVm.acceptInvite = acceptInvite;
    guestVm.declineInvite = declineInvite;
    guestVm.mealOrder = mealOrder;
    guestVm.cancelMealOrder = cancelMealOrder;
    
    // Check if user commented on visit
    guestVm.isCommented = isCommented;
    guestVm.search = search;
    guestVm.sendReview = sendReview;
    
    guestVm.showToast = showToast;

    activate();
    
    function sendReview(visit) {
        // Set review's reservation id to current reservation id
        visit.review.reservation = visit.reservation.reservationId;
        // Send review 
        return reviewService.addReview(visit.review)
            .then(function(response) { 
                // Change review id from null to real id - function 'isCommented' use this information
                visit.review.reviewId = response.data.reviewId;
            });
    };
    
    function cancelReservation(reservation) {
      // Check if reservation time is in next 30 minutes
      if (Date.now() + 30*60*1000 < reservation.reservationDateTime) {
          // Remove reservation from list of active reservations
          for (var i in guestVm.activeReservations) {
              if (guestVm.activeReservations[i].reservationId == reservation.reservationIdid) {
                  guestVm.activeReservations.splice(i, 1);
                  break;
              }
          }
          
          showToast('Otkazana rezervacija.');
      }
      else {
        showToast('Rezervacija počinje za manje od pola sata. Nije moguće otkazati.');
      }
    };
    
    // Redirect to user profile with this ID
    function to(id) {
        $location.path('profile-guest/' + id);
    };
    
    // Send friend request
    function addFriend(id) {
        guestService.addFriend(id)
          .then(function (response) {
              guestVm.sendRequest = true;
          });
    };
    
    // Remove friend from list
    function removeFriend(id) {
        guestService.removeFriend(id)
          .then(function (response) {
              // If on my profile then remove user from list
              if (guestVm.admin) {
                  for (var i = guestVm.friends.length - 1; i >= 0; i--) {
                      if (guestVm.friends[i].guestId === id) {
                         guestVm.friends.splice(i, 1);
                         break;
                      }
                  }
              }
              // If not on my profile - user is not friend anymore and reload his friends
              else {
                  guestVm.isFriend = false;
                  getFriends();
              }
          });
    };

    // Accept friend request
    function acceptFriend(id) {
        guestService.accept(id)
          .then(function (response) {
              // Add accepted friend to list of friends and remove request
              for (var i = guestVm.friendRequests.length - 1; i >= 0; i--) {
                  if (guestVm.friendRequests[i].guestId === id) {
                     guestVm.friends.push(guestVm.friendRequests[i]);
                     guestVm.friendRequests.splice(i, 1);
                     break;
                  }
              }
          });
    };
    
    // Reject friend request
    function rejectFriend(id) {
        guestService.reject(id)
          .then(function (response) {
              // Remove request
              for (var i = guestVm.friendRequests.length - 1; i >= 0; i--) {
                  if (guestVm.friendRequests[i].guestId === id) {
                     guestVm.friendRequests.splice(i, 1);
                     break;
                  }
              }
          });
    };
    
    function search() {
        if (guestVm.query != '') {
            return guestService.getSearchResult(guestVm.query)
              .then(function (response) {
                  guestVm.queryResult = response.data;
              });
        }
        else {
            guestVm.queryResult = [];
        }
    };
    
    // Edit button is clicked and copy user in 'realUser' for edit cancel
    function editProfile() {
        guestVm.editMode = true;
        guestVm.realUser = JSON.parse(JSON.stringify(guestVm.user));
    }
    
    function saveChanges() {
        guestService.updateGuest(guestVm.user)
          .then(function (updatedUser) {
            guestVm.user = updatedUser;
          });
        guestVm.editMode = false;
    }
    
    // Accept reservation invite
    function acceptInvite(reservationId) {
        guestService.acceptInvite(reservationId)
          .then(function (response) {
              // Update visits and intvitations
              getVisits();
              getInvitations();
          });
    };
    
    // Reject reservation invite
    function declineInvite(reservationId) {
      guestService.declineInvite(reservationId)
        .then(function (response) {
            // Update visits and intvitations
            getVisits();  
            getInvitations();
        });
    };
    
    // Order meal for ACCEPTED or CREATED reservation
    function mealOrder(reservation) {
        // Show dialog for Orders
        $mdDialog.show({
            controller: 'AddOrderController',
            controllerAs: 'orderVm',
            templateUrl: '/views/dialogs/add-order.html',
            parent: angular.element(document.body),
            clickOutsideToClose:false,
            fullscreen: true,
            locals: {
                // User doesn't know table
                table: null,
                // Get 'restaurantId' by reservation
                restaurantId : reservation.restaurantId,
                // FIXME @Baco Not my param
                edit : null,
                // Get 'reservationId' for order
                reservationId : reservation.reservation.reservationId,
                // Which guest creates order - it's current user
                guestId : $routeParams.guestId
            },
            onRemoving: function () {
                // Update reservations list (returns if order is created or not)
                getReservations();
            }
        });
    };
    
    // Cancel created meal order
    function cancelMealOrder(reservation) {
        // Check if reservation time (and order time) is in next 30 minutes
        if (Date.now() + 30*60*1000 < reservation.reservation.reservationDateTime) {
          guestService.cancelMealOrder(reservation.order)
            .then(function() {
                showToast('Otkazali ste porudžbinu. Sada možete napraviti novu!');
                reservation.order = null;
            });
        }
        else {
            showToast('Vaša porudžbina će biti spremna za manje od pola sata. Nije moguće otkazati.');
        }
    };
    
    // Cancel edit mode
    function cancel() {
        guestVm.editMode = false;
        // Return current user changes to old values
        guestVm.user = JSON.parse(JSON.stringify(guestVm.realUser));
    }
    
    // Check if review is commented
    function isCommented(review) {
        return !(review.reviewId == null);
    }
    
    function activate() {
        getUser();
        getRequests();
        getFriends();
        getReservations();
        getVisits();
        getInvitations();
        
        isFriend();
        isMyProfile();
    };

    function getUser() {
        return guestService.getGuest($routeParams.guestId)
          .then(function(response) {
              guestVm.user = response.data;
              return guestVm.user;
          });
    };
    
    function getRequests() {
        return guestService.getRequests()
          .then(function(response) {
              guestVm.friendRequests = response.data;
              return guestVm.friendRequests;
          });
    };
    
    function getFriends() {
        return guestService.getFriends($routeParams.guestId)
          .then(function(response) {
              guestVm.friends = response.data;
              return guestVm.friends;
          });
    };
    
    function isFriend() {
        return guestService.isFriend($routeParams.guestId)
          .then(function(response) {
              guestVm.isFriend = response.data;
              return guestVm.isFriend;
          });
    };
    
    function getReservations() {
        return guestService.getReservations($routeParams.guestId)
          .then(function(response) {
              guestVm.activeReservations = response.data;
              return guestVm.activeReservations;
          });
    };
    
    function getInvitations() {
        return guestService.getInvitations($routeParams.guestId)
          .then(function(response) {
              guestVm.invitations = response.data;
              return guestVm.invitations;
          });
    };
    
    function getVisits() {
        return guestService.getVisits($routeParams.guestId)
          .then(function(response) {
              guestVm.visits = response.data;
              
              // If visit is not commented create default review
              var index;
              for (index = 0; index < guestVm.visits.length; ++index) {
                  if (guestVm.visits[index].review == null) {
                      guestVm.visits[index].review = {
                          restaurantRate: 1,
                          foodRate: 1,
                          serviceRate: 1,
                          reservation: 1,
                          comment: ''
                        };
                  }
              }
              
              return guestVm.visits;
          });
    };
    
    function isMyProfile() {
      return guestService.isMy($routeParams.guestId)
        .then(function(response) {
            guestVm.admin = response.data;
            return guestVm.admin;
        });
    };
    
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'bottom right',
            template  : '<md-toast><strong>' + toast_message + '<strong></md-toast>'
        });
    };
    
    function changePassword(modal) {
        $mdDialog.show(
            {
                controller: 'ChangePasswordController',
                controllerAs: 'userVm',
                templateUrl: '/views/dialogs/change-password.html',
                parent: angular.element(document.body),
                clickOutsideToClose: modal,
                escapeToClose: modal,
                fullscreen: false,
                openFrom : angular.element(document.querySelector('#pass-option')),
                closeTo : angular.element(document.querySelector('#pass-option')),
                locals: {
                    modal : modal
                }
            }
        );
    };
}
