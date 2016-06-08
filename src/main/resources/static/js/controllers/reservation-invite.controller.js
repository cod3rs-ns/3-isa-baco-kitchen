angular
    .module('isa-mrs-project')
    .controller('ReservationInviteController', ReservationInviteController);

ReservationInviteController.$inject = ['$routeParams', 'menuItemService', 'orderService', 'reservationService','$mdToast'];

function ReservationInviteController($routeParams, menuItemService, orderService, reservationService, $mdToast) {
    var inviteVm = this;
    
    inviteVm.reservation = null;
    inviteVm.tableId = null,
    inviteVm.restaurantId = null;
    inviteVm.meals = [];
    inviteVm.orderedMeals = [];
    
    // Functions
    inviteVm.addMeal = addMeal;
    inviteVm.removeMeal = removeMeal;
    inviteVm.confirm = confirm;
    inviteVm.showToast = showToast;

    activate();
    
    function addMeal(meal) {
        var idx = inviteVm.orderedMeals.indexOf(meal);
        if (idx === -1) {
            meal.count = 1;
            meal.hide = true;
            inviteVm.orderedMeals.push(meal);
        }
        else {
            inviteVm.orderedMeals.splice(idx, 1);
            meal.count = 0;
        }
    };

    function removeMeal(meal) {
        var idx = inviteVm.orderedMeals.indexOf(meal);
        if (idx !== -1) {
            meal.hide = false;
            meal.count = 0;
            inviteVm.orderedMeals.splice(idx, 1);
        }
    };
    
    function confirm() {
        if (inviteVm.orderedMeals.length !== 0) {
            var order = createOrder();
            orderService.addOrder(order, inviteVm.tableId, inviteVm.restaurantId)
              .then(function (data) {
                  if (data != null) {
                    showToast("Naručili ste hranu i piće uz Vašu rezervaciju. Čekamo Vas!");
                  }
                });
        }
    };

    function activate() {
      // Ovaj 1 je restaurantId
      reservationService.getInvite($routeParams.reservationId)
        .then(function (data) {
          
          inviteVm.reservation = data.reservation;
          inviteVm.tableId = data.tableId;
          inviteVm.restaurantId = data.restaurantId;
          
          menuItemService.getMenuItemsByRestaurant(inviteVm.restaurantId)
            .then(function (data) {
                inviteVm.meals = data;
          });
        });
    };
    
    function createOrder() {
        var order = {
            orderId: null, 
            date : new Date(),
            deadline: inviteVm.reservation.reservationDateTime,
            reservation: inviteVm.reservation,
            items : []
        };

        inviteVm.orderedMeals.forEach(function (meal) {
            var count = meal.count;
            delete meal.count;
            delete meal.hide;

            var item = {
                itemId : null,
                state : "CREATED",
                order : null,
                menuItem : meal,
                amount : count
            };
            order.items.push(item);
        });

        return order;
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong></md-toast>'
        });
    };
}