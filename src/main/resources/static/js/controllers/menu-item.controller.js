angular
    .module('isa-mrs-project')
    .controller('MenuItemController', MenuItemController);

MenuItemController.$inject = ['menuItemService', '$mdDialog', '$mdToast', 'restaurant_id', 'menuItem_menu_ref', 'drinks_menu_ref', 'tabs'];

function MenuItemController(menuItemService, $mdDialog, $mdToast, restaurant_id, menuItem_menu_ref, drinks_menu_ref, tabs) {
    var menuItemVm = this;
    menuItemVm.restaurant_id = restaurant_id;
    menuItemVm.food_menu_ref = menuItem_menu_ref;
    menuItemVm.drinks_menu_ref = drinks_menu_ref;
    
    menuItemVm.tabs = tabs;
    menuItemVm.cancel = cancel;
    menuItemVm.createMenuItem = createMenuItem;
    menuItemVm.showToast = showToast;

    menuItemVm.menuItem = {
        menuItemId: null,
        info: null,
        name: null,
        price: null,
        type: null,
        image: null,
        specType: null
    };
    
    menuItemVm.allowedTypes = [
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

    function createMenuItem() {
        menuItemService.createMenuItem(menuItemVm.menuItem, restaurant_id)
            .then(function(addedmenuItem){
                menuItemVm.menuItem_menu_ref.push(addedmenuItem);
                menuItemVm.showToast('Jelo je uspešno kreirano i dodato u jelovnik.');
                menuItemVm.tabs.selected = 1; // switch to menuItem menu
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