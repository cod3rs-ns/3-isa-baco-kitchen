angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

CookProfileController.$inject = ['employeeService', 'cookService','passService', '$mdDialog', '$mdToast', '$scope'];

function CookProfileController(employeeService, cookService, passService, $mdDialog, $mdToast, $scope) {
    var cookProfileVm = this;
    cookProfileVm.cook = {}
    cookProfileVm.notNo = -1;
    cookProfileVm.selectedTab = 0;

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
                return cookProfileVm.cook;
            });

    };

	cookProfileVm.waitingMeals = [];
	cookProfileVm.preparingMeals = [];


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



    cookProfileVm.showToast= showToast;
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
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

        console.log(orders.new);
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
            console.log(orders.remove);
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

    cookProfileVm.prepareFood = prepareFood;
    function prepareFood(itemId){
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

    function getAcceptedItems(eId){
        console.log(eId);
        employeeService.getAcceptedItems(eId)
            .then(function (data) {
                console.log(data);
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


    cookProfileVm.finishFood = finishFood;
    function finishFood(itemId){

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
}