angular
    .module('isa-mrs-project')
    .controller('AddOrderController', AddOrderController);

AddOrderController.$inject = ['menuItemService', 'orderService', '$mdDialog', '$mdToast', 'table', 'restaurantId', 'edit'];

function AddOrderController(menuItemService, orderService, $mdDialog, $mdToast, table, restaurantId, edit) {
    var orderVm = this;
    orderVm.cancel = cancel;
    orderVm.showToast = showToast;
    orderVm.meals  = [];

    activate();
    
    function activate() {
        console.log(table);

        if(edit == null) {
            menuItemService.getMenuItemsByRestaurant(restaurantId)
                .then(function (data) {
                    for (var pos in data) {
                        orderVm.meals.push(data[pos]);
                    }
                });
        }
        else{
            orderService.getOrder(edit).
                then(function (orderItems) {
                    menuItemService.getMenuItemsByRestaurant(restaurantId)
                        .then(function (data) {
                            console.log(data);
                            console.log(orderItems);

                            for (var pos in data) {
                                //dodaj sve menuItem-e u listu za dodavanje
                                orderVm.meals.push(data[pos]);
                            }

                            for (var item in orderItems) {
                                //dodaj sve poručene stavke i obriši ih iz liste svih jela i pića
                                var mitem  = orderItems[item].menuItem

                                console.log(mitem);
                                for(var orderMeal in orderVm.meals){
                                    if (mitem.menuItemId === orderVm.meals[orderMeal].menuItemId){
                                        orderVm.meals[orderMeal].hide = true;
                                        orderVm.meals[orderMeal].count = orderItems[item].amount;
                                        orderVm.orderMeals.push(orderVm.meals[orderMeal]);
                                        break;
                                    }
                                }
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

}
