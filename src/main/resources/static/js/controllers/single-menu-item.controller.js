angular
    .module('isa-mrs-project')
    .controller('SingleMenuItemController', SingleMenuItemController);

SingleMenuItemController.$inject = ['menuItemService', '$mdDialog', '$mdToast', 'restaurant_id', 'drinks_menu_ref', 'food_menu_ref', 'tabs'];

function SingleMenuItemController(menuItemService, $mdDialog, $mdToast, restaurant_id, drinks_menu_ref, food_menu_ref, tabs) {
    var menuItemVm = this;
    menuItemVm.restaurant_id = restaurant_id;
    menuItemVm.drinks_menu_ref = drinks_menu_ref;
    menuItemVm.food_menu_ref = food_menu_ref;
    menuItemVm.tabs = tabs;
    menuItemVm.cancel = cancel;
    menuItemVm.createMenuItem = createMenuItem;
    menuItemVm.showToast = showToast;

    menuItemVm.menuItem = {
        name: '',
        info: '',
        price: 1,
        type: '',
        image: 'images/no_image.gif',
        specType: '',
        menuItemId: null
    };

    menuItemVm.allowedDrinks = [
        {
            db_value: 'pivo',
            name: 'Pivo'
        },
        {
            db_value: 'topli napitak',
            name: 'Topli napitak'
        },
        {
            db_value: 'voda',
            name: 'Voda'
        },
        {
            db_value: 'koktel',
            name: 'Koktel'
        },
        {
            db_value: 'žestoka pića',
            name: 'Žestoka pića'
        },
        {
            db_value: 'sokovi',
            name: 'Sokovi'
        },
        {
            db_value: 'ostalo',
            name: 'Ostalo'
        }
    ];

    menuItemVm.allowedFood = [
        {
            db_value: 'dezert',
            name: 'Dezert'
        },
        {
            db_value: 'sendvič',
            name: 'Sendvič'
        },
        {
            db_value: 'glavno jelo',
            name: 'Glavno jelo'
        },
        {
            db_value: 'salata',
            name: 'salata'
        },
        {
            db_value: 'supa',
            name: 'Supa'
        },
        {
            db_value: 'drugo',
            name: 'Drugo'
        }
    ];

    menuItemVm.mainTypes = [
        {
            db_value: 'food',
            name: 'Hrana'
        },
        {
            db_value: 'drink',
            name: 'Piće'
        }
    ];

    function createMenuItem() {
        menuItemService.create(menuItemVm.menuItem, restaurant_id)
            .then(function(response){
                var data = response.data;
                if (menuItemVm.menuItem.type == 'drink') {
                    menuItemVm.drinks_menu_ref.push(data);
                    menuItemVm.showToast('Piće je uspešno kreirano i dodato u kartu pića.');
                    menuItemVm.tabs.selected = 1; // switch to Drinks menu
                } else {
                    menuItemVm.food_menu_ref.push(data);
                    menuItemVm.showToast('Jelo je uspešno kreirano i dodato u jelovnik.');
                    menuItemVm.tabs.selected = 0; // switch to Food menu
                };
                menuItemVm.cancel();
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
