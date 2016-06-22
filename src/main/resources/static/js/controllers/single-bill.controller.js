angular
    .module('isa-mrs-project')
    .controller('BillController', BillController);

BillController.$inject = ['waiterService', '$mdDialog', '$mdToast', 'table', 'billId'];

function BillController(waiterService, $mdDialog, $mdToast, table, billId) {
    var billVm = this;
    billVm.items = [];
    billVm.total = 0;
    billVm.circular = true;

    activate();

    function activate() {
        if(table == null){
            waiterService.billDetails(billId)
                .then(function (data) {
                    for (var bkey in data.items) {
                        billVm.items.push(data.items[bkey]);
                        billVm.total += data.items[bkey].price;
                    }
                    billVm.waiter = data.waiter;
                    billVm.date = data.date;
                    billVm.circular = false;
                });
        }
        else {
            waiterService.createBill(table.tableId)
                .then(function (data) {
                    for (var bkey in data.items) {
                        billVm.items.push(data.items[bkey]);
                        billVm.total += data.items[bkey].price;
                    }
                    billVm.waiter = data.waiter;
                    billVm.date = data.date;
                    billVm.circular = false;
                });
        }
    };


    billVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };
}
