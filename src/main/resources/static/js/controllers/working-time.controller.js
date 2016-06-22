angular
    .module('isa-mrs-project')
    .controller('WorkingTimeController', WorkingTimeController);

WorkingTimeController.$inject = ['workingTimeService', 'restaurantManagerService', '$mdToast'];

function WorkingTimeController(workingTimeService, restaurantManagerService, $mdToast) {
    var workingTimeVm = this;
    workingTimeVm.wtime = {};
    workingTimeVm.editMode = false;
    workingTimeVm.backup = {};

    workingTimeVm.initEditMode = initEditMode;
    workingTimeVm.cancelChanges = cancelChanges;
    workingTimeVm.updateWorkingTime = updateWorkingTime;
    workingTimeVm.validateForm = validateForm;
    workingTimeVm.regInvalid = false;
    workingTimeVm.satInvalid = false;
    workingTimeVm.sunInvalid = false;
    workingTimeVm.invalidMessage = 'Početak radnog vremena mora biti pre kraja. :)';

    activate();

    function activate() {
        restaurantManagerService.getLoggedRestaurantManager()
            .then(function(data) {
                workingTimeVm.rmanager = data;
                findWorkingTimeByRestaurant();
            });

    };

    function findWorkingTimeByRestaurant() {
        // TODO get actual restaurant
        // TODO init empty working time if
        workingTimeService.findWorkingTimeByRestaurant(workingTimeVm.rmanager.restaurant.restaurantId)
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
            workingTimeVm.wtime.satReversed = false;
        };
        // erase old working data on Sunday
        if (workingTimeVm.wtime.workingOnSun == false) {
            workingTimeVm.wtime.sunStartHours = null;
            workingTimeVm.wtime.sunStartMinutes = null;
            workingTimeVm.wtime.sunEndHours = null;
            workingTimeVm.wtime.sunEndMinutes = null;
            workingTimeVm.wtime.sunReversed = false;
        };
        // Validation
        validateForm();
        if(workingTimeVm.regInvalid || workingTimeVm.satInvalid ||workingTimeVm.sunInvalid){
            return;
        };
        workingTimeService.updateWorkingTime(workingTimeVm.wtime)
            .then(function(data) {
                workingTimeVm.wtime = data;
                showToast('Radno vreme restorana je uspešno sačuvano.');
            });
        workingTimeVm.editMode = false;
    };

    function validateForm() {

        var t = workingTimeVm.wtime;
        console.log(t);
        var a = moment('2016-7-28', 'YYYY MM DD');
        var b = moment('2016-7-28', 'YYYY MM DD');

        // ---------- REGULAR DAYS CHECK ---------- //
        if (workingTimeVm.wtime.regReversed) {
            b = moment('2016-7-29', 'YYYY MM DD');
        };

        a.hour(t.regStartHours);
        a.minute(t.regStartMinutes);
        b.hour(t.regEndHours);
        b.minute(t.regEndMinutes);

        if (t.regReversed && t.regStartHours < t.regEndHours) {
            workingTimeVm.invalidMessage = 'Dvodnevno radno vreme mora da obuhvata 00:00.';
            workingTimeVm.regInvalid = true;
            return;
        };
        // check if start is before end for regular days
        if (a.isAfter(b)) {
            workingTimeVm.invalidMessage = 'Početak radnog vremena mora biti pre kraja. :)';
            workingTimeVm.regInvalid = true;
            return;
        };
        workingTimeVm.regInvalid = false;
        // ---------- SATURDAYS CHECK ---------- //
        if (workingTimeVm.wtime.workingOnSat) {
            var a = moment('2016-7-28', 'YYYY MM DD');
            var b = moment('2016-7-28', 'YYYY MM DD');
            if (workingTimeVm.wtime.satReversed) {
                b = moment('2016-7-29', 'YYYY MM DD');
            };

            a.hour(t.satStartHours);
            a.minute(t.satStartMinutes);
            b.hour(t.satEndHours);
            b.minute(t.satEndMinutes);

            if (t.satReversed && t.satStartHours < t.satEndHours) {
                workingTimeVm.invalidMessage = 'Dvodnevno radno vreme mora da obuhvata 00:00.';
                workingTimeVm.satInvalid = true;
                return;
            };

            // check if start is before end for regular days
            if (a.isAfter(b)) {
                workingTimeVm.invalidMessage = 'Početak radnog vremena mora biti pre kraja. :)';
                workingTimeVm.satInvalid = true;
                return;
            };
            workingTimeVm.satInvalid = false;
        };

        // ---------- SUNDAYS CHECK ---------- //
        if (workingTimeVm.wtime.workingOnSun) {
            var a = moment('2016-7-28', 'YYYY MM DD');
            var b = moment('2016-7-28', 'YYYY MM DD');
            if (workingTimeVm.wtime.sunReversed) {
                b = moment('2016-7-29', 'YYYY MM DD');
            };

            a.hour(t.sunStartHours);
            a.minute(t.sunStartMinutes);
            b.hour(t.sunEndHours);
            b.minute(t.sunEndMinutes);

            console.log(t.sunReversed);
            console.log(t.sunStartHours);
            console.log(t.sunEndHours);
            if (t.sunReversed && t.sunStartHours < t.sunEndHours) {
                workingTimeVm.invalidMessage = 'Dvodnevno radno vreme mora da obuhvata 00:00.';
                workingTimeVm.sunInvalid = true;
                return;
            };

            // check if start is before end for regular days
            if (a.isAfter(b)) {
                workingTimeVm.invalidMessage = 'Početak radnog vremena mora biti pre kraja. :)';
                workingTimeVm.sunInvalid = true;
                return;
            };
            workingTimeVm.sunInvalid = false;
        };

        // ---------- WorkTime CONTINUOUS CHECK  ---------- //
        if (t.workingOnSat && t.regReversed) {
            if (t.regEndHours > t.satStartHours) {
                workingTimeVm.invalidMessage = 'Radno vreme radnim danima i subotom se preklapa.';
                workingTimeVm.satInvalid = true;
                return;
            };
        };

        if (t.workingOnSun && t.satReversed) {
            if (t.satEndHours > t.sunStartHours) {
                workingTimeVm.invalidMessage = 'Radno vreme subotom i nedeljom se preklapa.';
                workingTimeVm.sunInvalid = true;
                return;
            };
        };

        if (t.workingOnSun & t.sunReversed) {
            if (t.sunEndHours > t.regEndHours) {
                workingTimeVm.invalidMessage = 'Radno vreme nedeljom i radnim danima se preklapa.';
                workingTimeVm.regInvalid = true;
                return;
            };
        };
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

}
