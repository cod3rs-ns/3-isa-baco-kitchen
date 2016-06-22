angular
    .module('isa-mrs-project')
    .controller('SingleMenuItemController', SingleMenuItemController);

SingleMenuItemController.$inject = ['menuItemService', '$mdDialog', '$mdToast', 'restaurant_id', 'drinks_menu_ref', 'food_menu_ref', 'tabs', 'to_edit'];

function SingleMenuItemController(menuItemService, $mdDialog, $mdToast, restaurant_id, drinks_menu_ref, food_menu_ref, tabs, to_edit) {
    var menuItemVm = this;
    menuItemVm.restaurant_id = restaurant_id;
    menuItemVm.drinks_menu_ref = drinks_menu_ref;
    menuItemVm.food_menu_ref = food_menu_ref;
    menuItemVm.tabs = tabs;
    menuItemVm.cancel = cancel;
    menuItemVm.createMenuItem = createMenuItem;
    menuItemVm.showToast = showToast;
    menuItemVm.editState = to_edit == null ? false : true;
    menuItemVm.menuItem = {};
    menuItemVm.backup = {};

    activate();

    function activate() {
        if(menuItemVm.editState) {
            menuItemVm.menuItem = to_edit;
            menuItemVm.backup = angular.copy(to_edit);
        } else {
            menuItemVm.menuItem = {
                name: '',
                info: '',
                price: 1,
                type: '',
                image: 'images/no_image.gif',
                specType: '',
                menuItemId: null
            };
        }
    }

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
        var progress = "<md-progress-linear md-mode='indeterminate'></md-progress-linear>";
        var toast = menuItemVm.showToast(progress, 0);
        if (menuItemVm.editState) {
            menuItemService.update(menuItemVm.menuItem, restaurant_id)
                .then(function() {
                    menuItemVm.editState = false;
                    menuItemVm.cancel();
                    $mdToast.hide(toast);
                    menuItemVm.showToast("<strong>Sve promene su uspešno sačuvane.</strong>", 3000);
                });
        } else {
            menuItemService.create(menuItemVm.menuItem, restaurant_id)
                .then(function(response){
                    var data = response.data;
                    if (menuItemVm.menuItem.type == 'drink') {
                        menuItemVm.drinks_menu_ref.push(data);
                        menuItemVm.showToast('Piće je uspešno kreirano i dodato u kartu pića.', 3000);
                        menuItemVm.tabs.selected = 1; // switch to Drinks menu
                    } else {
                        menuItemVm.food_menu_ref.push(data);
                        menuItemVm.showToast('Jelo je uspešno kreirano i dodato u jelovnik.', 3000);
                        menuItemVm.tabs.selected = 0; // switch to Food menu
                    };
                    menuItemVm.cancel();
                });
        }
    };

    function showToast(text, delay) {
        var toast = $mdToast.show({
          hideDelay : delay,
          position  : 'top right',
          parent    : angular.element(document.querySelectorAll('#toast-box')),
          template  : '<md-toast>' + text  + '</md-toast>'
        });
        return toast;
    };

    function cancel() {
        if (menuItemVm.editState) {
            menuItemVm.menuItem.name = menuItemVm.backup.name;
            menuItemVm.menuItem.info = menuItemVm.backup.info;
            menuItemVm.menuItem.price = menuItemVm.backup.price;
            menuItemVm.menuItem.type = menuItemVm.backup.type;
            menuItemVm.menuItem.specType = menuItemVm.backup.specType;
            menuItemVm.backup = null;
            menuItemVm.editState = false;
        };
        $mdDialog.cancel();
    };
}
