angular
    .module('isa-mrs-project')
    .controller('AddRestaurantController', AddRestaurantController);

function AddRestaurantController($mdDialog) {
    // Var vm stands for ViewModel
    var addRestaurantVm = this;

    addRestaurantVm.addingRestaurant = {
        name: '',
        info: '',
        type: '',
        start: 1,
        end: 1,
        id: null
    };

    addRestaurantVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

    addRestaurantVm.preview = preview;
    function preview() {
        alert(angular.toJson(addRestaurantVm.addingRestaurant, true));
    };
}