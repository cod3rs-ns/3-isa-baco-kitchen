angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['$routeParams', '$location', '$mdToast', '$mdDialog', 'guestService', 'reviewService'];

function GuestProfileController($routeParams, $location, $mdToast, $mdDialog, guestService, reviewService) {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.admin = false;
    guestProfileVm.showSearch = false;
    guestProfileVm.editMode = false;
    guestProfileVm.user = {};
    guestProfileVm.realUser = {};
    guestProfileVm.friendRequests = [];
    guestProfileVm.friends = [];
    guestProfileVm.isFriend = false;
    guestProfileVm.sendRequest = false;
    guestProfileVm.query = "";
    guestProfileVm.queryResult = [];
    guestProfileVm.activeReservations = [];
    guestProfileVm.visits = [];
    // Functions 
    guestProfileVm.editProfile = editProfile;
    guestProfileVm.saveChanges = saveChanges;
    guestProfileVm.cancel = cancel;
    guestProfileVm.acceptFriend = acceptFriend;
    guestProfileVm.rejectFriend = rejectFriend;
    guestProfileVm.removeFriend = removeFriend;
    guestProfileVm.addFriend = addFriend;
    guestProfileVm.search = search;
    guestProfileVm.to = to;
    guestProfileVm.cancelReservation = cancelReservation;
    guestProfileVm.showToast = showToast;
    guestProfileVm.sendReview = sendReview;
    guestProfileVm.isCommented = isCommented;

    activate();
    
    function sendReview(visit) {
        visit.review.reservation = visit.reservation.reservationId;
        return reviewService.addReview(visit.review)
            .then(function(data) { 
                visit.review.reviewId = data.reviewId;
            });
    };
    
    function cancelReservation(id) {
      
      var reservation = null;
      for (var i in guestProfileVm.activeReservations) {
          if (guestProfileVm.activeReservations[i].reservationId == id) {
              reservation = guestProfileVm.activeReservations[i];
              break;
          }
      }
      
      if (Date.now() + 30*60*1000 < reservation.reservationDateTime) {
          // FIXME Dodati brisanje rezervacije.
          for (var i in guestProfileVm.activeReservations) {
              if (guestProfileVm.activeReservations[i].reservationId == id) {
                  guestProfileVm.activeReservations.splice(i, 1);
                  break;
              }
          }
          showToast('Otkazana rezervacija.');
      }
      else {
        showToast('Rezervacija počinje za manje od pola sata. Nije moguće otkazati.');
      }
    };
    
    function to(id) {
        $location.path('profile-guest/' + id);
    };
    
    function addFriend(id) {
        guestService.addFriend(id)
          .then(function (response) {
              guestProfileVm.sendRequest = true;
          });
    };
    
    function removeFriend(id) {
      guestService.removeFriend(id)
        .then(function (response) {
            guestProfileVm.isFriend = false;
            
            if (guestProfileVm.admin) {
                for (var i = guestProfileVm.friends.length - 1; i >= 0; i--) {
                    if (guestProfileVm.friends[i].guestId === id) {
                       guestProfileVm.friends.splice(i, 1);
                       break;
                    }
                }
            }
            else {
                getFriends();
            }
        });
    };

    // Implement functions later
    function acceptFriend(id) {
        guestService.accept(id)
          .then(function (response) {
              for (var i = guestProfileVm.friendRequests.length - 1; i >= 0; i--) {
                  if (guestProfileVm.friendRequests[i].guestId === id) {
                     guestProfileVm.friends.push(guestProfileVm.friendRequests[i]);
                     guestProfileVm.friendRequests.splice(i, 1);
                     break;
                  }
              }
          });
    };
    
    function rejectFriend(id) {
      guestService.reject(id)
        .then(function (response) {
            for (var i = guestProfileVm.friendRequests.length - 1; i >= 0; i--) {
                if (guestProfileVm.friendRequests[i].guestId === id) {
                   guestProfileVm.friendRequests.splice(i, 1);
                   break;
                }
            }
        });
    };
    
    function search() {
      return guestService.getSearchResult(guestProfileVm.query)
        .then(function (response) {
            guestProfileVm.queryResult = response.data;
        });
    };
    
    function editProfile() {
        guestProfileVm.editMode = true;
        guestProfileVm.realUser = JSON.parse(JSON.stringify(guestProfileVm.user));
    }
    
    function saveChanges() {
        guestService.updateGuest(guestProfileVm.user)
          .then(function (updatedUser) {
            guestProfileVm.user = updatedUser;
          });
        guestProfileVm.editMode = false;
    }
    
    function cancel() {
        guestProfileVm.editMode = false;
        guestProfileVm.user = JSON.parse(JSON.stringify(guestProfileVm.realUser));
    }
    
    function isCommented(review) {
        return !(review.reviewId == null);
    }
    
    function activate() {
      
        guestService.isMy($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.admin = response.data;
        });
      
        getUser().then(function() { });
        
        getRequests().then(function() { });
        
        getFriends().then(function() { });
        
        isFriend().then(function() { });
        
        getReservations().then(function () { });
        
        getVisits().then(function () { });
    };

    function getUser() {
        return guestService.getGuest($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.user = response.data;
            return guestProfileVm.user;
        });
    };
    
    function getRequests() {
        return guestService.getRequests()
        .then(function(response) {
            guestProfileVm.friendRequests = response.data;
            return guestProfileVm.friendRequests;
        });
    };
    
    function getFriends() {
        return guestService.getFriends($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.friends = response.data;
            return guestProfileVm.friends;
        });
    };
    
    function isFriend() {
        return guestService.isFriend($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.isFriend = response.data;
            return guestProfileVm.isFriend;
        });
    };
    
    function getReservations() {
        return guestService.getReservations($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.activeReservations = response.data;
            return guestProfileVm.activeReservations;
        });
    };
    
    function getVisits() {
        return guestService.getVisits($routeParams.guestId)
        .then(function(response) {
            guestProfileVm.visits = response.data;
            var index;
            for (index = 0; index < guestProfileVm.visits.length; ++index) {
                if (guestProfileVm.visits[index].review == null) {
                    guestProfileVm.visits[index].review = {
                        restaurantRate: 1,
                        foodRate: 1,
                        serviceRate: 1,
                        reservation: 1,
                        comment: ''
                      };
                }
            }
            
            return guestProfileVm.visits;
        });
    }
    
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'bottom right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };
    
    guestProfileVm.changePassword = changePassword;
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
