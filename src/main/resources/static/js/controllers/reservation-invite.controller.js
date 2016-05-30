angular
    .module('isa-mrs-project')
    .controller('ReservationInviteController', ReservationInviteController);

ReservationInviteController.$inject = ['menuItemService', 'orderService', '$mdToast'];

function ReservationInviteController(menuItemService, orderService, $mdToast) {
    var inviteVm = this;
    
    inviteVm.meals  = [];
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
            if(edit == null) {
                orderService.addOrder(order, table.tableId, restaurantId)
                  .then(function (data) {
                    if (data != null) {
                      showToast("Naručili ste hranu i piće uz Vašu rezervaciju. Čekamo Vas!");
                    }
                  });
              }
          }
      };

    function activate() {
      // Ovaj 1 je restaurantId
      menuItemService.getMenuItemsByRestaurant(1)
        .then(function (data) {
            inviteVm.meals = data;
      });
    };
    
    function createOrder() {
        var order = {
            orderId: edit,
            date : new Date(),
            deadline: null,
            items : []
        };

        orderVm.orderMeals.forEach(function (meal) {
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

        console.log(order);
        return order;
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };
}