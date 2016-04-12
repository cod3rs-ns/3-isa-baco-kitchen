angular
    .module('isa-mrs-project')
    .controller('EditRestaurantController', EditRestaurantController);

EditRestaurantController.$inject = ['restaurantService', '$mdDialog','$mdToast', 'to_edit'];

function EditRestaurantController(restaurantService, $mdDialog, $mdToast, to_edit) {
    // Var vm stands for ViewModel
    var restaurantVm = this;

    restaurantVm.restaurant = to_edit;
    console.log(to_edit);
    console.log(restaurantVm.restaurant);
    restaurantVm.showToast = showToast;
    restaurantVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

    function showToast() {
        $mdToast.show({
          hideDelay : 3000,
          position  : 'top right',
          template  : '<md-toast><strong>Restoran je uspešno izmenjen.<strong> </md-toast>'
        });
    };

    restaurantVm.update = update;
    function update() {
        //Trenutno je podeseno da se restoran doda menadzeru ciji je id 3
        restaurantService.updateRestaurant(restaurantVm.restaurant, 6)
            .then(function(data){
                restaurantVm.showToast();
                restaurantVm.cancel();
            });
    };

}
