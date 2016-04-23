angular
    .module('isa-mrs-project')
    .controller('WaiterProfileController', WaiterProfileController);

WaiterProfileController.$inject = ['tableService', 'waiterService', '$mdDialog', 'passService'];

function WaiterProfileController(tableService, waiterService, $mdDialog, passService) {
    var waiterProfileVm = this;
    
    waiterProfileVm.waiter = {};

    activate();

    function activate(){
        getWaiter().then(function() {
        });


        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    waiterProfileVm.changePassword(false);
                }
            });

        getTablesByRestaurant();
    };


    function getWaiter(){
        return waiterService.getLoggedWaiter()
            .then(function(data) {
                waiterProfileVm.waiter = data;
                waiterProfileVm.waiter.birthday = new Date(data.birthday);
                return waiterProfileVm.cook;
            });
    };


    waiterProfileVm.meals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      },
	  {
        name: 'Cosmopolitan',
		img_src: 'images/meals/cosmopolitan.jpg'
      }
	];
	
	waiterProfileVm.bills = [
      {
        code: '1235AS45',
        total: '12000',
        date: '28. maj  2016',
      },
      {
        code: '8856A45B',
        total: '5000',
        date: '28. jun  2016',
      },
      {
        code: '88A7SD5A',
        total: '36987',
        date: '15. septembar  2016',
      },
    ];


    //Part for seting calendar  -----------------------------------
	
	waiterProfileVm.calendarView = 'month';
	waiterProfileVm.calendarDate = new Date();
	waiterProfileVm.viewDate = new Date();


    waiterProfileVm.events = [
        {
            title: 'An event',
            type: 'warning',
            startsAt: moment().subtract(1, 'day').toDate(),
            endsAt: moment().subtract(1, 'day').add(5, 'hours').toDate(),
            draggable: true,
            resizable: true
        }, {
            title: '<i class="glyphicon glyphicon-asterisk"></i> <span class="text-primary">Another event</span>, with a <i>html</i> title',
            type: 'info',
            startsAt: moment().subtract(1, 'day').toDate(),
            endsAt: moment().add(5, 'days').toDate(),
            draggable: true,
            resizable: true
        }, {
            title: 'This is a really long event title that occurs on every year',
            type: 'important',
            startsAt: moment().startOf('day').add(7, 'hours').toDate(),
            endsAt: moment().startOf('day').add(19, 'hours').toDate(),
            recursOn: 'year',
            draggable: true,
            resizable: true
        }
    ];

    //editing profile

    waiterProfileVm.editProfile = editProfile;
    function editProfile() {
        $mdDialog.show({
            controller: 'SingleEmployeeController',
            controllerAs: 'employeeVm',
            templateUrl: '/views/dialogs/single-employee-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : waiterProfileVm.waiter,
            }
        });
    };

    waiterProfileVm.changePassword = changePassword;
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


    //part for tables

    waiterProfileVm.allTables = [];
    function getTablesByRestaurant() {
        //TODO SET RESTAURANT ID
        return tableService.getTablesByRestaurant(2)
            .then(function(data) {
                changeWaitersTables(data);
                waiterProfileVm.allTables = data;
                return waiterProfileVm.tables;
            });
    };

    function changeWaitersTables(tables){
        for (pos in tables){
            if(pos<3) {
                tables[pos].color = '#CDDC39';
            }
            else{
                tables[pos].color = '#1B5E20';
            }
        }
    }

}