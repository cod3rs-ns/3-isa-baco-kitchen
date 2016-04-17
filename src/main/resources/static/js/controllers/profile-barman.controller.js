angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

BarmanProfileController.$inject = ['bartenderService', '$mdDialog', '$routeParams'];

function BarmanProfileController(bartenderService, $mdDialog, $routeParams) {
    var barmanProfileVm = this;

    barmanProfileVm.barman = {};

    activate();

    function activate(){
        return getBarman($routeParams.barmanId).then(function() {

        });
    };


    function getBarman(id){
        return bartenderService.getBartender(id)
            .then(function(data) {
                barmanProfileVm.barman = data;
                barmanProfileVm.barman.birthday = new Date(data.birthday);
                return barmanProfileVm.barman;
            });
    };



	
	barmanProfileVm.waitingDrinks = [
      {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      },
	  {
        name: 'Cosmopolitan',
		img_src: 'images/meals/cosmopolitan.jpg'
      }
    ];
	
	
	barmanProfileVm.preparingDrinks = [
      {
        name: 'Martini',
		img_src: 'images/meals/martini.jpg'
      },
	  {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      }
    ];

    barmanProfileVm.editProfile = editProfile;
    function editProfile() {
        $mdDialog.show({
            controller: 'SingleEmployeeController',
            controllerAs: 'employeeVm',
            templateUrl: '/views/dialogs/single-employee-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : barmanProfileVm.barman,
            }
        });
    };

}