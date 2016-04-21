angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

BarmanProfileController.$inject = ['bartenderService', 'passService', '$mdDialog'];

function BarmanProfileController(bartenderService, passService, $mdDialog) {
    var barmanProfileVm = this;

    barmanProfileVm.barman = {};

    activate();

    function activate(){
        getBarman().then(function() {

        });


        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    barmanProfileVm.changePassword(false);
                }
            });

    };


    function getBarman(){
        return bartenderService.getLoggedBartender()
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


    barmanProfileVm.changePassword = changePassword;
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
}