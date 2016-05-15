angular
    .module('isa-mrs-project')
    .controller('WorkingTimeController', WorkingTimeController);

WorkingTimeController.$inject = ['workingTimeService', '$mdToast'];

function WorkingTimeController(workingTimeService, $mdToast) {
    var workingTimeVm = this;
    workingTimeVm.wtime = {};
    workingTimeVm.editMode = false;
    workingTimeVm.backup = {};

    workingTimeVm.initEditMode = initEditMode;
    workingTimeVm.cancelChanges = cancelChanges;
    workingTimeVm.updateWorkingTime = updateWorkingTime;

    activate();

    function activate() {
        findWorkingTimeByRestaurant();
    };

    function findWorkingTimeByRestaurant() {
        // TODO get actual restaurant
        workingTimeService.findWorkingTimeByRestaurant(2)
            .then(function(data) {
                console.log(data);
                workingTimeVm.wtime = data;
            });
    };

    function initEditMode() {
        workingTimeVm.backup = angular.copy(workingTimeVm.wtime);
        workingTimeVm.editMode = true;
    };

    function cancelChanges() {
        workingTimeVm.wtime = workingTimeVm.backup;
        workingTimeVm.editMode = false;
        showToast('Sve promene su poništene.');
    };

    function updateWorkingTime() {
        // erase old working data on Saturday
        if (workingTimeVm.wtime.workingOnSat == false) {
            workingTimeVm.wtime.satStartHours = null;
            workingTimeVm.wtime.satStartMinutes = null;
            workingTimeVm.wtime.satEndHours = null;
            workingTimeVm.wtime.satEndMinutes = null;
        };
        // erase old working data on Sunday
        if (workingTimeVm.wtime.workingOnSun == false) {
            workingTimeVm.wtime.sunStartHours = null;
            workingTimeVm.wtime.sunStartMinutes = null;
            workingTimeVm.wtime.sunEndHours = null;
            workingTimeVm.wtime.sunEndMinutes = null;
        };
        workingTimeService.updateWorkingTime(workingTimeVm.wtime)
            .then(function(data) {
                workingTimeVm.wtime = data;
                showToast('Radno vreme restorana je uspešno sačuvano.');
            });
        workingTimeVm.editMode = false;
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

}
