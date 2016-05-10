angular
    .module('isa-mrs-project')
    .controller('AddOrderController', AddOrderController);

AddOrderController.$inject = ['drinkService','foodService', 'orderService', '$mdDialog', '$mdToast', 'table', 'edit'];

function AddOrderController(drinkService, foodService, orderService, $mdDialog, $mdToast, table, edit) {
    var orderVm = this;
    orderVm.cancel = cancel;
    orderVm.showToast = showToast;
    orderVm.meals  = [];

    activate();
    
    function activate() {
        if(edit == null) {
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
        else{
            orderService.getOrder(edit).
                then(function (map) {
                    drinkService.getDrinks()
                        .then(function (data) {
                            for (var pos in data) {
                                if(('d' + data[pos].drinkId)  in map) {
                                    data[pos].hide = true;
                                    data[pos].count = map['d' + data[pos].drinkId];
                                    orderVm.orderMeals.push(data[pos]);
                                }
                                orderVm.meals.push(data[pos]);
                            }
                        });

                    foodService.getFood()
                        .then(function (data) {
                            for (var pos in data) {
                                if(('f' + data[pos].foodId)  in map) {
                                    data[pos].hide = true;
                                    data[pos].count = map['f' + data[pos].foodId];
                                    orderVm.orderMeals.push(data[pos]);
                                }
                                orderVm.meals.push(data[pos]);
                            }
                        });
            });
        }
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
            meal.count = 1;
            meal.hide = true;
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
            meal.hide = false;
            meal.count = 0;
            orderVm.orderMeals.splice(idx, 1);
        }
    };

    orderVm.confirm = confirm;
    function confirm(){
        if (orderVm.orderMeals.length !== 0){
            var order = createOrder();
            if(edit == null) {
                orderService.addOrder(order, table.tableId)
                    .then(function (data) {
                        if (data != null) {
                            showToast("Porudžbina je uspješno dodata.");
                            $mdDialog.cancel();
                        }
                    });
            }
            else{
                orderService.updateOrder(order, table.tableId)
                    .then(function (data) {
                        if (data != null) {
                            showToast("Porudžbina je uspješno izmijenjena.");
                            $mdDialog.cancel();
                        }
                    });
            }
        }
    };

    function createOrder(){
        var order = {
            orderId: edit,
            date : new Date(),
            deadline: null,
            items : []
        };

        orderVm.orderMeals.forEach(function (meal) {
            var f = false;
            var count = meal.count;
            delete meal.count;
            delete meal.hide;

            if (meal.type.charAt(0)=='f')
                f = true;

            for (var i=0; i<count; i++){
                var item = {
                    itemId : null,
                    state : "CREATED",
                    order : null,
                    food: null,
                    drink: null
                };
                console.log(meal);
                if (f) {
                    item.food = meal;
                }
                else {
                    item.drink = meal;
                }
                order.items.push(item);
            }
        });

        return order;
    };

}
