angular
    .module('isa-mrs-project')
    .controller('AddRestaurantController', AddRestaurantController);

AddRestaurantController.$inject = ['restaurantService', '$mdDialog'];

function AddRestaurantController(restaurantService, $mdDialog) {
    // Var vm stands for ViewModel
    var addRestaurantVm = this;

    addRestaurantVm.addingRestaurant = {
        restaurantId: null,
        name: '',
        info: '',
        type: '',
        startTime: 1,
        endTime: 1
    };

    addRestaurantVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

    addRestaurantVm.create = create;
    function create() {
        //Trenutno je podeseno da se restoran doda menadzeru ciji je id 6
        restaurantService.createRestaurant(addRestaurantVm.addingRestaurant, 6)
            .then(function(addedRestaurant){
                console.log(addedRestaurant);
                addRestaurantVm.cancel();
            });
    };

}