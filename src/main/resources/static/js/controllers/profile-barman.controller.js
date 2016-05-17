angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

BarmanProfileController.$inject = ['employeeService', 'bartenderService', 'passService', '$mdDialog', '$scope'];

function BarmanProfileController(employeeService, bartenderService, passService, $mdDialog, $scope) {
    var barmanProfileVm = this;

    barmanProfileVm.barman = {};
    barmanProfileVm.notNo = -1;
    barmanProfileVm.selectedTab = 0;

    activate();

    function activate(){
        getBarman().then(function() {
            getActiveDrinks(barmanProfileVm.barman.restaurantID);
            getAcceptedItems(barmanProfileVm.barman.userId);
            connect(barmanProfileVm.barman.restaurantID);
        });

        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    barmanProfileVm.changePassword(false);
                }
            });

    };

    function getActiveDrinks(r_id){
        bartenderService.getActiveDrinks(r_id)
            .then(function(data){
                for(var i in data){
                    barmanProfileVm.waitingDrinks.push(data[i]);
                }
            });
    }

    function getBarman(){
        return bartenderService.getLoggedBartender()
            .then(function(data) {
                barmanProfileVm.barman = data;
                barmanProfileVm.barman.birthday = new Date(data.birthday);
                return barmanProfileVm.barman;
            });
    };

	barmanProfileVm.waitingDrinks = [];
	barmanProfileVm.preparingDrinks = [];

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


    barmanProfileVm.openSchedule = openSchedule;
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

    function connect(id) {
        var socket = new SockJS('/connectDrink');
        barmanProfileVm.stompClient = Stomp.over(socket);
        barmanProfileVm.stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            barmanProfileVm.stompClient.subscribe('/subscribe/ActiveDrink/'+ id, function(greeting){
                showGreeting(JSON.parse(greeting.body));
            });
        });
    }

    function disconnect() {
        if (barmanProfileVm.stompClient != null) {
            barmanProfileVm.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function showGreeting(orders) {
        var num=0;

        console.log(orders.new);

        for (var item in orders.new) {
            barmanProfileVm.waitingDrinks.push(orders.new[item]);
            num += 1;
        }

        for (var item in orders.update) {
            for(var meal in barmanProfileVm.waitingDrinks){
                if(orders.update[item].itemId === barmanProfileVm.waitingDrinks[meal].itemId){
                    barmanProfileVm.waitingDrinks[meal].amount = orders.update[item].amount;
                    num += 1;
                    break;
                }
            }
        }

        for (var item in orders.remove) {
            console.log(orders.remove);
            for(var meal in barmanProfileVm.waitingDrinks){
                if(orders.remove[item].itemId === barmanProfileVm.waitingDrinks[meal].itemId){
                    barmanProfileVm.waitingDrinks.splice(meal, 1);
                    break;
                }
            }
        }

        if (num != 0 ) {
            if(barmanProfileVm.notNo == -1)
                barmanProfileVm.notNo = num;
            else
                barmanProfileVm.notNo += num;
        }
        $scope.$apply();
    }

    barmanProfileVm.prepareDrink = prepareDrink;
    function prepareDrink(itemId){
        console.log(itemId);
        employeeService.prepareOrderItem(itemId, barmanProfileVm.barman.userId)
            .then(function (data) {
                if(data != null){
                    barmanProfileVm.preparingDrinks.push(data);
                }
            });
    }

    function getAcceptedItems(eId) {
        employeeService.getAcceptedItems(eId)
            .then(function (data) {
                console.log(data);
                for (var i in data) {
                    barmanProfileVm.preparingDrinks.push(data[i]);
                }
            });
    }


    barmanProfileVm.clickOnTab = clickOnTab;

    function clickOnTab() {
        if (barmanProfileVm.selectedTab == 0){
            barmanProfileVm.notNo =-1;
        }
    }
}