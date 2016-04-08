angular
    .module('isa-mrs-project')
    .controller('SingleDrinkController', SingleDrinkController);

function SingleDrinkController($mdDialog){
    var drinkVm = this;

    drinkVm.drink = {
        name: '',
        info: '',
        price: 0,
        id: null
    };

    drinkVm.cancel = cancel;

    function cancel(){
        $mdDialog.cancel();
    };
}
