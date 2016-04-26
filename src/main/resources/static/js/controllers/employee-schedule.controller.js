angular
    .module('isa-mrs-project')
    .controller('EmployeeScheduleController', EmployeeScheduleController);

EmployeeScheduleController.$inject = ['employeeService', '$mdDialog'];

function EmployeeScheduleController(employeeService, $mdDialog) {
    var employeeScheduleVm = this;
    employeeScheduleVm.cancel = cancel;

    initState();

    function initState() {
        employeeScheduleVm.calendarView = 'month';
        employeeScheduleVm.calendarDate = new Date();
        employeeScheduleVm.viewDate = new Date();

        employeeScheduleVm.events = [];
        getSchedule();
    };


    function getSchedule() {
        employeeService.getSchedule()
            .then(function (data) {
                for(var pos in data){
                    var period = data[pos];
                    var event =
                    {
                        title: 'Pocetak smjene',
                        type: 'warning',
                        startsAt: new Date(period.start),
                        endsAt: new Date(period.end)
                    };
                    employeeScheduleVm.events.push(event);
                }
            });
    };

    employeeScheduleVm.changeToDay = changeToDay;
    function changeToDay(){
        employeeScheduleVm.calendarView = 'day';
    }

    employeeScheduleVm.changeToMonth = changeToMonth;
    function changeToMonth(){
        employeeScheduleVm.calendarView = 'month';
    }

    function cancel() {
        $mdDialog.cancel();
    };
}