angular
    .module('isa-mrs-project')
    .controller('WaiterProfileController', WaiterProfileController);

WaiterProfileController.$inject = ['tableService', 'waiterService', 'passService', 'orderService', '$mdDialog', '$mdToast', '$scope'];

function WaiterProfileController(tableService, waiterService, passService, orderService, $mdDialog, $mdToast, $scope) {
    var waiterProfileVm = this;
    
    waiterProfileVm.waiter = {};
    waiterProfileVm.stompClient = null;
    waiterProfileVm.workingRegion = null;
    waiterProfileVm.notNo = -1;
    waiterProfileVm.selectedTab = 0;
    waiterProfileVm.reservation = false;
    waiterProfileVm.reservationOrders = [];

    activate();

    function activate(){
        getWaiter().then(function() {
            connect(waiterProfileVm.waiter.userId);
            getRegion(waiterProfileVm.waiter.userId);
            getBills();
        });

        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    waiterProfileVm.changePassword(false);
                }
            });

    };


    function getWaiter(){
        return waiterService.getLoggedWaiter()
            .then(function(data) {
                waiterProfileVm.waiter = data;
                waiterProfileVm.waiter.birthday = new Date(data.birthday);
                return waiterProfileVm.waiter;
            });
    };

    
    function getBills(){
        return waiterService.findBills()
            .then(function (data) {
                for(var bill in data){
                    data[bill].publishDate = new Date(data[bill].publishDate);
                }
                waiterProfileVm.bills = data;
            })
    }

    function getRegion(){
        return waiterService.getWorkingRegion(waiterProfileVm.waiter.userId)
            .then(function(data) {
                waiterProfileVm.workingRegion = data;
                waiterService.getFinishedOrders(waiterProfileVm.workingRegion.regionId)
                    .then(function (data) {
                        for(var pos in data){
                            waiterProfileVm.meals.push(data[pos]);
                        }
                    });
                getTablesByRestaurant(waiterProfileVm.waiter.restaurantID);
                return data;
            });
    };

    waiterProfileVm.meals = [];

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
    function getTablesByRestaurant(restaurantId) {
        return tableService.getTablesByRestaurant(restaurantId)
            .then(function(data) {
                waiterProfileVm.allTables = data;
                changeWaitersTables(data);
                return waiterProfileVm.tables;
            });
    };

    function changeWaitersTables(tables){
        waiterService.getTables(waiterProfileVm.workingRegion.regionId)
            .then(function (data) {
                for (var pos in tables){
                    for(var mypos in data){
                        if(tables[pos].tableId == data[mypos].tableId)
                            tables[pos].color = '#FF3F80';
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
                if(waiterProfileVm.selectedTable.color !== '#FF3F80'){
                    waiterProfileVm.selectedTable = -1;
                    waiterProfileVm.selectedTableOrders.length = 0;
                }
                else{
                    getOrders();
                    getReservationOrders();
                }
                break;
            }
        }
    };


    function getOrders(){
        if(waiterProfileVm.selectedTable != null && waiterProfileVm.selectedTable != -1)
        orderService.getOrders(waiterProfileVm.selectedTable.tableId)
            .then(function (data) {
                waiterProfileVm.selectedTableOrders = data;
                waiterProfileVm.selectedTableOrders.forEach(function (order) {
                    order.date = new Date(order.date);
                });
            });
    };

    function getReservationOrders(){
        if(waiterProfileVm.selectedTable != null && waiterProfileVm.selectedTable != -1)
            orderService.getOrdersFromReservation(waiterProfileVm.selectedTable.tableId)
                .then(function (data) {
                    console.log(data);
                    waiterProfileVm.reservationOrders = data;
                    waiterProfileVm.reservationOrders.forEach(function (order) {
                        order.date = new Date(order.date);
                    });
                });
    }

    waiterProfileVm.selectedTableOrders = [];

    waiterProfileVm.addOrder = addOrder;
    function addOrder() {
        $mdDialog.show({
            controller: 'AddOrderController',
            controllerAs: 'orderVm',
            templateUrl: '/views/dialogs/add-order.html',
            parent: angular.element(document.body),
            clickOutsideToClose:false,
            fullscreen: true,
            locals: {
                table: waiterProfileVm.selectedTable,
                restaurantId : waiterProfileVm.waiter.restaurantID,
                edit : null,
                reservationId : null,
                guestId : null
            },
            onRemoving : function() {getOrders();}
        });
    };


    waiterProfileVm.createBill = createBill;
    function createBill() {
        waiterProfileVm.selectedTableOrders.length = 0;
        $mdDialog.show({
            controller: 'BillController',
            controllerAs: 'billVm',
            templateUrl: '/views/dialogs/bill-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:false,
            fullscreen: true,
            locals: {
                table: waiterProfileVm.selectedTable,
                billId: null
            },
            onRemoving: function() {
                getBills();
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

    waiterProfileVm.editOrder = editOrder;
    function editOrder(order) {
        var difference = (new Date()).getTime() - order.date.getTime();
        var minDiff = Math.floor(difference / 60000);
        if(minDiff > 30){
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title('Upozorenje!')
                    .textContent('Ne možete izmijeniti porudžbinu!\n Isteklo je više od 30 min od kreiranja iste.')
                    .ok('POTVRDA!')
            );
            return;
        }

        orderService.canEdit(order.orderId)
            .then(function (data) {
                if(!data){
                    $mdDialog.show(
                        $mdDialog.alert()
                            .clickOutsideToClose(false)
                            .title('Upozorenje!')
                            .textContent('Ne možete izmijeniti porudžbinu!\n Neke od njenih stavki su već u izradi.')
                            .ok('POTVRDA!')
                    );
                }
                else {
                    $mdDialog.show({
                        controller: 'AddOrderController',
                        controllerAs: 'orderVm',
                        templateUrl: '/views/dialogs/add-order.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: true,
                        locals: {
                            table: waiterProfileVm.selectedTable,
                            restaurantId: waiterProfileVm.waiter.restaurantID,
                            edit: order.orderId,
                            reservationId : null,
                            guestId : null
                        },
                        onRemoving: function () {
                            getOrders();
                        }
                    });
                }
            });

    };


    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function connect(id) {
        var socket = new SockJS('/finishOrder');
        waiterProfileVm.stompClient = Stomp.over(socket);
        waiterProfileVm.stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            waiterProfileVm.stompClient.subscribe('/subscribe/finishOrder/'+ id, function(greeting){
                showGreeting(JSON.parse(greeting.body));
            });
        });
    }

    function disconnect() {
        if (waiterProfileVm.stompClient != null) {
            waiterProfileVm.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function showGreeting(meal) {
        waiterProfileVm.meals.push(meal);

        if(waiterProfileVm.notNo == -1)
            waiterProfileVm.notNo = 1;
        else
            waiterProfileVm.notNo += 1;

        $scope.$apply();
    }

    waiterProfileVm.clickOnTab = clickOnTab;
    function clickOnTab() {
        if (waiterProfileVm.selectedTab == 0){
            waiterProfileVm.notNo =-1;
        }
    }

    waiterProfileVm.deliverOrder = deliverOrder;
    function deliverOrder(orderId) {
        waiterService.deliverOrder(orderId)
            .then(function (data) {
                for(var pos in waiterProfileVm.meals){
                    if(waiterProfileVm.meals[pos].itemId  === data.itemId){
                        waiterProfileVm.meals.splice(pos,1);
                        break;
                    }
                }
            });
    };

    waiterProfileVm.showBill = showBill;
    function showBill(billId) {
        $mdDialog.show({
            controller: 'BillController',
            controllerAs: 'billVm',
            templateUrl: '/views/dialogs/bill-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:false,
            fullscreen: true,
            locals: {
                table: null,
                billId: billId
            }
        });
    };

    waiterProfileVm.reservationButtonToggle = reservationButtonToggle;
    function reservationButtonToggle() {
        waiterProfileVm.reservation = !waiterProfileVm.reservation;
        if(waiterProfileVm.reservation){
            getReservationOrders();
        }
    }

    waiterProfileVm.changeOrderStatus = changeOrderStatus;
    function changeOrderStatus(order) {
        waiterProfileVm.selectedTableOrders.push(order);
        waiterProfileVm.reservationOrders.splice(waiterProfileVm.reservationOrders.indexOf(order),1);
        orderService.changeStatus(order.orderId);
    }
}