angular
    .module('isa-mrs-project')
    .controller('AddOrderController', AddOrderController);

AddOrderController.$inject = ['drinkService','foodService','$mdDialog', '$mdToast', 'table'];

function AddOrderController(drinkService, foodService, $mdDialog, $mdToast, table) {
    var orderVm = this;
    orderVm.cancel = cancel;
    orderVm.showToast = showToast;
    orderVm.meals  = [];

    activate();
    
    function activate() {
        drinkService.getDrinks()
            .then(function (data) {
                for (var pos in data) {
                    orderVm.meals.push(data[pos]);
                }
            });

        foodService.getFood()
            .then(function (data) {
                for (var pos in data) {
                    orderVm.meals.push(data[pos]);
                }
            });

    }

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function cancel() {
        console.log(table);
        $mdDialog.cancel();
    };

    orderVm.orderMeals = [];

    orderVm.addMeal = addMeal;
    function addMeal(meal) {
        var idx = orderVm.orderMeals.indexOf(meal);
        if (idx === -1) {
            orderVm.orderMeals.push(meal);
        }
        else {
            orderVm.orderMeals.splice(idx, 1);
            meal.count = 0;
        }
    };


    orderVm.removeMeal = removeMeal;
    function removeMeal(meal) {
        var idx = orderVm.orderMeals.indexOf(meal);
        if (idx !== -1) {
            meal.check = false;
            meal.count = 0;
            orderVm.orderMeals.splice(idx, 1);
        }
    };
}
