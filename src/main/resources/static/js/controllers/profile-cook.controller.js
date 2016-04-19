angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

CookProfileController.$inject = ['cookService', '$mdDialog', '$routeParams'];

function CookProfileController(cookService, $mdDialog, $routeParams) {
    var cookProfileVm = this;
    cookProfileVm.cook = {}

    activate();

    function activate(){
        return getCook($routeParams.cookId).then(function() {

        });
    };


    function getCook(){
        return cookService.getLoggedCook()
            .then(function(data) {
                cookProfileVm.cook = data;
                cookProfileVm.cook.birthday = new Date(data.birthday);
                return cookProfileVm.cook;
            });
    };


	cookProfileVm.waitingMeals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      }
    ];


	cookProfileVm.preparingMeals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      }
    ];

    cookProfileVm.editProfile = editProfile;
    function editProfile() {
        $mdDialog.show({
            controller: 'SingleEmployeeController',
            controllerAs: 'employeeVm',
            templateUrl: '/views/dialogs/single-employee-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : cookProfileVm.cook,
            }
        });
    };
}