angular
    .module('isa-mrs-project')
    .controller('SingleFoodController', SingleFoodController);

SingleFoodController.$inject = ['foodService', '$mdDialog', '$mdToast', 'restaurant_id', 'food_menu_ref', 'tabs'];

function SingleFoodController(foodService, $mdDialog, $mdToast, restaurant_id, food_menu_ref, tabs) {
    var foodVm = this;
    foodVm.restaurant_id = restaurant_id;
    foodVm.food_menu_ref = food_menu_ref;
    foodVm.tabs = tabs;
    foodVm.cancel = cancel;
    foodVm.createFood = createFood;
    foodVm.showToast = showToast;

    foodVm.food = {
        name: '',
        info: '',
        price: 1,
        type: '',
        image: 'images/meals/borovnica.jpg',
        foodId: null
    };
    foodVm.allowedTypes = [
        {
            db_value: 'dessert',
            name: 'Dezert'
        },
        {
            db_value: 'appetizer',
            name: 'Predjelo'
        },
        {
            db_value: 'main_course',
            name: 'Glavno jelo'
        }
    ];

    function createFood() {
        foodService.createFood(foodVm.food, restaurant_id)
            .then(function(addedFood){
                foodVm.food_menu_ref.push(addedFood);
                foodVm.showToast('Jelo je uspešno kreirano i dodato u jelovnik.');
                foodVm.tabs.selected = 1; // switch to Food menu
                foodVm.cancel();
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
}
