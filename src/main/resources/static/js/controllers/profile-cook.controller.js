angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

CookProfileController.$inject = ['employeeService', 'cookService','passService', 'loginService', '$mdDialog', '$mdToast', '$scope'];

function CookProfileController(employeeService, cookService, passService, loginService, $mdDialog, $mdToast, $scope) {
    var cookProfileVm = this;
    cookProfileVm.cook = {}
    cookProfileVm.notNo = -1;
    cookProfileVm.selectedTab = 0;

    cookProfileVm.waitingMeals = [];
    cookProfileVm.preparingMeals = [];

    //editing profile
    cookProfileVm.editProfile = editProfile;
    //changing password
    cookProfileVm.changePassword = changePassword;
    //show cook's schedule
    cookProfileVm.openSchedule = openSchedule;
    //accept preparing food
    cookProfileVm.prepareFood = prepareFood;
    //notify waiter that food is prepared
    cookProfileVm.finishFood = finishFood;
    //show menu item details
    cookProfileVm.showMenuItemDetails = showMenuItemDetails;
    //logout from profile
    cookProfileVm.logout = logout;

    activate();

    function activate(){
        getCook()
            .then(function() {
                getActiveFood(cookProfileVm.cook.restaurantID);
                connect(cookProfileVm.cook.restaurantID);
                getAcceptedItems(cookProfileVm.cook.userId);
        });
        
        passService.isPasswordChanged()
            .then(function (data) {
                if(!data){
                    cookProfileVm.changePassword(false);
                }
            });
    };

    function getActiveFood(r_id){
        cookService.getActiveFood(r_id)
            .then(function(data){
                for(var i in data){
                    cookProfileVm.waitingMeals.push(data[i]);
                }
        });
    }

    function getCook(){
        return cookService.getLoggedCook()
            .then(function(data) {
                cookProfileVm.cook = data;
                cookProfileVm.cook.birthday = new Date(data.birthday);
                cookProfileVm.cook.shoesSize = parseInt(cookProfileVm.cook.shoesSize);
                return cookProfileVm.cook;
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
                to_edit : cookProfileVm.cook
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

    cookProfileVm.showToast= showToast;
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            parent    : angular.element(document.querySelectorAll('#toast-box')),
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function connect(id) {
        var socket = new SockJS('/connectFood');
        cookProfileVm.stompClient = Stomp.over(socket);
        cookProfileVm.stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            cookProfileVm.stompClient.subscribe('/subscribe/ActiveFood/'+ id, function(greeting){
                showGreeting(JSON.parse(greeting.body));
            });
        });
    }

    function disconnect() {
        if (cookProfileVm.stompClient != null) {
            cookProfileVm.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function showGreeting(orders) {
        var num=0;

        for (var item in orders.new) {
            cookProfileVm.waitingMeals.push(orders.new[item]);
            num += 1;
        }

        for (var item in orders.update) {
            for(var meal in cookProfileVm.waitingMeals){
                if(orders.update[item].itemId === cookProfileVm.waitingMeals[meal].itemId){
                    cookProfileVm.waitingMeals[meal].amount = orders.update[item].amount;
                    num += 1;
                    break;
                }
            }
        }

        for (var item in orders.remove) {
            for(var meal in cookProfileVm.waitingMeals){
                if(orders.remove[item].itemId === cookProfileVm.waitingMeals[meal].itemId){
                    cookProfileVm.waitingMeals.splice(meal, 1);
                    break;
                }
            }
        }

        if (num != 0 ) {
            if(cookProfileVm.notNo == -1)
                cookProfileVm.notNo = num;
            else
                cookProfileVm.notNo += num;
        }
        $scope.$apply();
    }

    function prepareFood(itemId){
        cookProfileVm.confirmationDialog(
            "Prihvatanje porudžbine",
            "Da li ste sigurni da želite da se zauzmete za izradu navedenog jela?",
            function () {
                employeeService.prepareOrderItem(itemId, cookProfileVm.cook.userId)
                    .then(function (response){
                        if(response.statusText == "OK") {
                            cookProfileVm.preparingMeals.push(response.data);
                            cookProfileVm.showToast("Uspješno ste prihvatili jelo za izradu.");
                        }
                        else
                            cookProfileVm.showToast("Drugi kuvar je u međuvremenu prihvatio dato jelo.");
                    });
            }
        );
    }

    function getAcceptedItems(eId){
        employeeService.getAcceptedItems(eId)
            .then(function (data) {
                for(var i in data){
                    cookProfileVm.preparingMeals.push(data[i]);
                }
            });
    }

    cookProfileVm.clickOnTab = clickOnTab;
    function clickOnTab() {
        if (cookProfileVm.selectedTab == 0){
            cookProfileVm.notNo =-1;
        }
    }


    function finishFood(itemId){
        cookProfileVm.confirmationDialog(
            "Završavanje porudžbine",
            "Da li ste sigurni da je hrana spremna za serviranje?",
            function () {
                for(var meal in cookProfileVm.preparingMeals){
                    if(itemId === cookProfileVm.preparingMeals[meal].itemId){
                        cookProfileVm.preparingMeals.splice(meal, 1);
                        break;
                    }
                }

                employeeService.finishOrderItem(itemId)
                    .then(function (data) {
                        if(data != null){
                            console.log("finish");
                        }
                    });
            }
        );
    }


    cookProfileVm.confirmationDialog = confirmationDialog;
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