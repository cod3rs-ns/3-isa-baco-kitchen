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
                console.log(data);

                for(var pos in data){
                    var schedule = data[pos];
                    var event =
                    {
                        title: 'Početak smjene',
                        type: 'indigo',
                        startsAt: new Date(schedule.mergedStart),
                        endsAt: new Date(schedule.mergedEnd)
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

    employeeScheduleVm.changeToWeek = changeToWeek;
    function changeToWeek(){
        employeeScheduleVm.calendarView = 'week';
    }

    function cancel() {
        $mdDialog.cancel();
    };
}