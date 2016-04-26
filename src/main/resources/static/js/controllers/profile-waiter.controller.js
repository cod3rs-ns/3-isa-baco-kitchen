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
        waiterService.getTables()
            .then(function (data) {
                for (var pos in tables){
                    for(var mypos in data){
                        if(tables[pos].tableId == data[mypos].tableId)
                            tables[pos].color = '#CDDC39';
                    }
                }
            });
    };

    waiterProfileVm.selectedTable = null;
    waiterProfileVm.selectTable = selectTable;
    function selectTable(tableId){
        for(var table in waiterProfileVm.allTables){
            if (waiterProfileVm.allTables[table].tableId == tableId){
                waiterProfileVm.selectedTable = waiterProfileVm.allTables[table];
                if(waiterProfileVm.selectedTable.color !== '#CDDC39'){
                    waiterProfileVm.selectedTable = -1;
                }
                break;
            }
        }
    };

    waiterProfileVm.finishedOrders = [
        {
            title: "Sto broj 5",
            desc: "Spremljen je gulaš."
        },
        {
            title: "Sto broj 9",
            desc: "Spremljen je pasulj."
        },
        {
            title: "Sto broj 2",
            desc: "Spremljen je koktel."
        }
    ];

    waiterProfileVm.deleteFinishedOrder = deleteFinishedOrder
    function deleteFinishedOrder(order){
        var index = waiterProfileVm.finishedOrders.indexOf(order);
        waiterProfileVm.finishedOrders[index] = true;
        waiterProfileVm.finishedOrders.splice(index,1);
    };


    waiterProfileVm.addOrder = addOrder;
    function addOrder() {
        $mdDialog.show({
            controller: 'AddOrderController',
            controllerAs: 'orderVm',
            templateUrl: '/views/dialogs/add-order.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: true,
            locals: {
                table: waiterProfileVm.selectedTable
            }
        });
    };


    waiterProfileVm.openSchedule = openSchedule;
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