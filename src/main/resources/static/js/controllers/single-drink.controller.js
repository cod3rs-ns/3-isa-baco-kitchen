angular
    .module('isa-mrs-project')
    .controller('SingleDrinkController', SingleDrinkController);

SingleDrinkController.$inject = ['drinkService', '$mdDialog', '$mdToast', 'restaurant_id', 'drinks_menu_ref', 'tabs'];

function SingleDrinkController(drinkService, $mdDialog, $mdToast, restaurant_id, drinks_menu_ref, tabs){
    var drinkVm = this;
    drinkVm.restaurant_id = restaurant_id;
    drinkVm.drinks_menu_ref = drinks_menu_ref;
    drinkVm.tabs = tabs;
    drinkVm.cancel = cancel;
    drinkVm.createDrink = createDrink;
    drinkVm.showToast = showToast;
    drinkVm.drink = {
        name: '',
        info: '',
        price: 0,
        type: '',
        image: 'images/meals/cosmopolitan.jpg',
        drinkId: null
    };
    drinkVm.allowedTypes = [
        {
            db_value: 'alcoholic',
            name: 'Alkoholno piće'
        },
        {
            db_value: 'non-alcoholic',
            name: 'Bezalkoholno piće'
        },
        {
            db_value: 'juice',
            name: 'Sok'
        }
    ];

    function createDrink() {
        drinkService.createDrink(drinkVm.drink, restaurant_id)
            .then(function(addedDrink){
                drinkVm.drinks_menu_ref.push(addedDrink);
                drinkVm.showToast('Piće je uspešno kreirano i dodato u kartu pića.');
                drinkVm.tabs.selected = 2; // switch to Drinks menu
                drinkVm.cancel();
            });
    };


    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    drinkVm.cancel = cancel;

    function cancel(){
        $mdDialog.cancel();
    };
}
