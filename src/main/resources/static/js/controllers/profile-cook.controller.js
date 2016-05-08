angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

CookProfileController.$inject = ['cookService','passService', '$mdDialog'];

function CookProfileController(cookService, passService, $mdDialog) {
    var cookProfileVm = this;
    cookProfileVm.cook = {}

    activate();

    function activate(){
        getCook()
            .then(function() {

        });
        
        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    cookProfileVm.changePassword(false);
                }
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
                to_edit : cookProfileVm.cook
            }
        });
    };
    
    cookProfileVm.changePassword = changePassword;
    function changePassword(modal) {
        $mdDialog.show(
            {
                controller: 'ChangePasswordController',
                controllerAs: 'userVm',
                templateUrl: '/views/dialogs/change-password.html',
                parent: angular.element(document.body),
                clickOutsideToClose: modal,
                escapeToClose: modal,
                fullscreen: false,
                openFrom : angular.element(document.querySelector('#pass-option')),
                closeTo : angular.element(document.querySelector('#pass-option')),
                locals: {
                    modal : modal
                }
            }
        );
    };


    cookProfileVm.openSchedule = openSchedule;
    function openSchedule() {
        $mdDialog.show(
            {
                controller: 'EmployeeScheduleController',
                controllerAs: 'employeeScheduleVm',
                templateUrl: '/views/dialogs/calendar-view.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: false
            }
        );
    };
}