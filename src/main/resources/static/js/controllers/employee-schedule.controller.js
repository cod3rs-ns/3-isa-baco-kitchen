angular
    .module('isa-mrs-project')
    .controller('EmployeeScheduleController', EmployeeScheduleController);

EmployeeScheduleController.$inject = ['employeeService', '$mdDialog'];

function EmployeeScheduleController(employeeService, $mdDialog) {
    var employeeScheduleVm = this;
    //closing dialog
    employeeScheduleVm.cancel = cancel;
    //change to day view
    employeeScheduleVm.changeToDay = changeToDay;
    //change to week view
    employeeScheduleVm.changeToWeek = changeToWeek;
    //change to month view
    employeeScheduleVm.changeToMonth = changeToMonth;

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

    function changeToDay(){
        employeeScheduleVm.calendarView = 'day';
    }

    function changeToMonth(){
        employeeScheduleVm.calendarView = 'month';
    }

    function changeToWeek(){
        employeeScheduleVm.calendarView = 'week';
    }

    function cancel() {
        $mdDialog.cancel();
    };
}