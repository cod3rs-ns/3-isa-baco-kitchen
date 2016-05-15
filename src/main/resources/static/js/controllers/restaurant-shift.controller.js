angular
    .module('isa-mrs-project')
    .controller('RestaurantShiftController', RestaurantShiftController);

RestaurantShiftController.$inject = ['$timeout'];

function RestaurantShiftController($timeout) {
    var shiftVm = this;
    shiftVm.shifts = [];
    shiftVm.newShift = {};
    shiftVm.addNewShift = addNewShift;
    shiftVm.deleteShift = deleteShift;
    shiftVm.editShift = editShift;
    shiftVm.saveShift = saveShift;
    shiftVm.cancelChanges = cancelChanges;
    shiftVm.backup =  {};
    shiftVm.editState = false;
    shiftVm.inEditProcess = {};

    function addNewShift() {
        shiftVm.shifts.push(shiftVm.newShift);
        initDefaultShift();
    };

    function deleteShift(id) {
        var del_idx = -1;
        for (var i = 0; i < shiftVm.shifts.length; i++) {
            if (shiftVm.shifts[i].shiftId == id) {
                del_idx = i;
                break;
            };
        };
        shiftVm.shifts.splice(del_idx, 1);
    };

    function editShift(shift) {
        shiftVm.backup = angular.copy(shift);
        shiftVm.newShift = angular.copy(shift);
        shiftVm.inEditProcess = shift;
        shiftVm.editState = true;
    };

    function saveShift() {
        shiftVm.inEditProcess.startHour = shiftVm.newShift.startHour;
        shiftVm.inEditProcess.endHour = shiftVm.newShift.endHour;
        shiftVm.inEditProcess.startMinute = shiftVm.newShift.startMinute;
        shiftVm.inEditProcess.endMinute = shiftVm.newShift.endMinute;
        shiftVm.inEditProcess.name = shiftVm.newShift.name;

        initDefaultShift();
        shiftVm.editState = false;
    };

    function cancelChanges() {
        shiftVm.editState = false;
        initDefaultShift();
    };

    init();

    function initDefaultShift() {
        shiftVm.newShift = {
            shiftId: null,
            startHour: 0,
            endHour: 24,
            startMinute: 0,
            endMinute: 0,
            name: '',
            restaurantId: 2
        };
    };

    function init() {
        initDefaultShift();
        var shift = {
            shiftId: 1,
            startHour: 7,
            endHour: 14,
            startMinute: 30,
            endMinute: 15,
            name: 'Prva smena',
            restaurantId: 2
        };
        var shift2 = {
            shiftId: 2,
            startHour: 7,
            endHour: 14,
            startMinute: 30,
            endMinute: 15,
            name: 'Prva smena',
            restaurantId: 2
        };
        var shift3 = {
            shiftId: 3,
            startHour: 7,
            endHour: 14,
            startMinute: 30,
            endMinute: 15,
            name: 'Prva smena',
            restaurantId: 2
        };

        shiftVm.shifts.push(shift);
        shiftVm.shifts.push(shift2);
        shiftVm.shifts.push(shift3);
    };


}
