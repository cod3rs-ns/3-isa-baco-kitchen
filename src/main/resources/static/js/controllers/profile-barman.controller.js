angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

BarmanProfileController.$inject = ['employeeService', 'bartenderService', 'loginService', 'passService', '$mdDialog', '$scope', '$mdToast'];

function BarmanProfileController(employeeService, bartenderService, loginService, passService, $mdDialog, $scope, $mdToast) {
    var barmanProfileVm = this;

    barmanProfileVm.barman = {};
    barmanProfileVm.notNo = -1;
    barmanProfileVm.selectedTab = 0;

    barmanProfileVm.waitingDrinks = [];
    barmanProfileVm.preparingDrinks = [];

    //editing profiles
    barmanProfileVm.editProfile = editProfile;
    //changing password
    barmanProfileVm.changePassword = changePassword;
    //showing barman's schedule
    barmanProfileVm.openSchedule = openSchedule;
    //accept drink to prepare
    barmanProfileVm.prepareDrink = prepareDrink;
    //notify waiter that drink is prepared
    barmanProfileVm.finishDrink = finishDrink;
    //show menu item details
    barmanProfileVm.showMenuItemDetails = showMenuItemDetails;
    //logout from your profile
    barmanProfileVm.logout = logout;
    //image uplaod
    barmanProfileVm.upload = upload;


    activate();

    function activate(){
        getBarman().then(function() {
            getActiveDrinks(barmanProfileVm.barman.restaurantID);
            getAcceptedItems(barmanProfileVm.barman.userId);
            connect(barmanProfileVm.barman.restaurantID);
        });

        passService.isPasswordChanged()
            .then(function (data) {
                if(!data){
                    barmanProfileVm.changePassword(false);
                }
            });

    };

    function upload($flow){
        $flow.opts.target = 'api/upload/users/' + barmanProfileVm.barman.userId ;
        $flow.upload();
        barmanProfileVm.barman.image = '/images/users/users_' + barmanProfileVm.barman.userId + '.png';
        employeeService.updateEmployee(barmanProfileVm.barman)
            .then(function(data) {
                barmanProfileVm.barman = data;
                barmanProfileVm.showToast('Fotografija uspešno promenjena.', 3000);
            })
    }

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
                barmanProfileVm.barman.shoesSize = parseInt(barmanProfileVm.barman.shoesSize);
                return barmanProfileVm.barman;
            });
    };

    function editProfile() {
        $mdDialog.show({
            controller: 'SingleEmployeeController',
            controllerAs: 'employeeVm',
            templateUrl: '/views/dialogs/single-employee-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:false,
            fullscreen: false,
            locals: {
                to_edit : barmanProfileVm.barman,
            }
        });
    };

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

    function prepareDrink(itemId){
        barmanProfileVm.confirmationDialog(
            "Prihvatanje porudžbine",
            "Da li ste sigurni da želite da se zauzmete za izradu navedenog pića?",
            function () {
                employeeService.prepareOrderItem(itemId, barmanProfileVm.barman.userId)
                    .then(function (response) {
                        if(response.statusText == "OK") {
                            barmanProfileVm.preparingDrinks.push(response.data);
                            barmanProfileVm.showToast("Uspješno ste prihvatili piće za izradu.");
                        }
                        else
                            barmanProfileVm.showToast("Drugi šanker je u međuvremenu prihvatio dato piće.");
                    });
            });
    }

    barmanProfileVm.showToast= showToast;
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            parent    : angular.element(document.querySelectorAll('#toast-box')),
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };


    function getAcceptedItems(eId) {
        employeeService.getAcceptedItems(eId)
            .then(function (data) {
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

    function finishDrink(itemId){
        barmanProfileVm.confirmationDialog(
            "Završavanje porudžbine",
            "Da li ste sigurni da je piće spremno za serviranje?",
            function () {
                for(var meal in barmanProfileVm.preparingDrinks){
                    if(itemId === barmanProfileVm.preparingDrinks[meal].itemId){
                        barmanProfileVm.preparingDrinks.splice(meal, 1);
                        break;
                    }
                }

                employeeService.finishOrderItem(itemId)
                    .then(function (data) {
                        if(data != null){
                            console.log("finish");
                        }
                    });
            });
    }

    barmanProfileVm.confirmationDialog = confirmationDialog;
    function confirmationDialog(title, text, yesFunc) {
        var confirm = $mdDialog.confirm()
            .title(title)
            .textContent(text)
            .ariaLabel('Confirmation')
            .ok('DA')
            .cancel('NE');

        $mdDialog.show(confirm).then(
            yesFunc,
            function(){
                $mdDialog.hide();
            });
    };

    function showMenuItemDetails(menuItem) {
        $mdDialog.show({
            controller: 'MenuItemDetails',
            controllerAs: 'menuItemVm',
            templateUrl: '/views/dialogs/menu-item-details.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                menuItem : menuItem
            }
        });
    };

    function logout() {
        loginService.logout();
    };
}
