angular
    .module('isa-mrs-project')
    .controller('BillController', BillController);

BillController.$inject = ['waiterService', '$mdDialog', '$mdToast', 'table'];

function BillController(waiterService, $mdDialog, $mdToast, table) {
    var billVm = this;
    billVm.items = [];
    billVm.total = 0;

    activate();

    function activate() {
        waiterService.createBill(table.tableId)
            .then(function (data) {
                for (var bkey in data.items){
                    billVm.items.push(data.items[bkey]);
                    billVm.total += data.items[bkey].price;
                }
                billVm.waiter = data.waiter;
                billVm.date = data.date;
            });
    };


    billVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'bottom right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };
}
