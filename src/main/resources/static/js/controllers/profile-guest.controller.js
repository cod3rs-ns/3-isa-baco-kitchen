angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['$routeParams', 'guestService'];

function GuestProfileController($routeParams, guestService) {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.admin = false;
    guestProfileVm.showSearch = false;
    guestProfileVm.editMode = false;
    guestProfileVm.user = {};
    guestProfileVm.friendRequests = [];
    guestProfileVm.friends = [];
    guestProfileVm.isFriend = false;
    guestProfileVm.sendRequest = false;
    // Functions 
    guestProfileVm.editProfile = editProfile;
    guestProfileVm.saveChanges = saveChanges;
    guestProfileVm.cancel = cancel;
    guestProfileVm.acceptFriend = acceptFriend;
    guestProfileVm.rejectFriend = rejectFriend;
    guestProfileVm.removeFriend = removeFriend;
    guestProfileVm.addFriend = addFriend;

    activate();
    
    function addFriend(id) {
        guestService.addFriend(id)
          .then(function (response) {
              console.log('Added friend ' + id);
              guestProfileVm.sendRequest = true;
          });
    };
    
    function removeFriend(id) {
      guestService.removeFriend(id)
        .then(function (response) {
            console.log('Removed friend ' + id);
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
              console.log('Accepted friend ' + id);
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
            console.log('Rejected friend ' + id);
            for (var i = guestProfileVm.friendRequests.length - 1; i >= 0; i--) {
                if (guestProfileVm.friendRequests[i].guestId === id) {
                   guestProfileVm.friendRequests.splice(i, 1);
                   break;
                }
            }
        });
    };
    
    function editProfile() {
        guestProfileVm.editMode = true;
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
    }
    
    function activate() {
      
        guestService.isMy($routeParams.guestId).then(function(data) {
            guestProfileVm.admin = data;
        });
      
        getUser().then(function() {
            console.log('User loaded.');
        });
        
        getRequests().then(function() {
            console.log('Requests loaded.');
        });
        
        getFriends().then(function() {
            console.log('Friends loaded.');
        });
        
        isFriend().then(function() {
            console.log('Is friend loaded.')
        });
    };

    function getUser() {
        return guestService.getGuest($routeParams.guestId).then(function(data) {
            guestProfileVm.user = data;
            return guestProfileVm.user;
        });
    };
    
    function getRequests() {
        return guestService.getRequests().then(function(data) {
            guestProfileVm.friendRequests = data;
            return guestProfileVm.friendRequests;
        });
    };
    
    function getFriends() {
        return guestService.getFriends($routeParams.guestId).then(function(data) {
            guestProfileVm.friends = data;
            return guestProfileVm.friends;
        });
    };
    
    function isFriend() {
        return guestService.isFriend($routeParams.guestId).then(function(data) {
            guestProfileVm.isFriend = data;
            return guestProfileVm.isFriend;
        });
    };
}
