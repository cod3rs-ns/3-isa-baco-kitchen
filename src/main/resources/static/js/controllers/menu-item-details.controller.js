angular
    .module('isa-mrs-project')
    .controller('MenuItemDetails', MenuItemDetails);

MenuItemDetails.$inject = ['$mdDialog', '$mdToast', 'menuItem'];

function MenuItemDetails( $mdDialog, $mdToast, menuItem){
    var menuItemVm = this;
    //item that is shown
    menuItemVm.item = menuItem;

    //closing dialog
    menuItemVm.cancel = cancel;

    function cancel() {
        $mdDialog.cancel();
    };
}
