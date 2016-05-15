angular
    .module('isa-mrs-project')
    .controller('RestaurantScheduleController', RestaurantScheduleController);

RestaurantScheduleController.$inject = ['employeeService', '$timeout'];

function RestaurantScheduleController(employeeService, $timeout) {
    var scheduleVm = this;
    scheduleVm.employees = [];
    scheduleVm.temp = new Date();
    scheduleVm.newEvent = {
        title: null,
        type: 'danger',
        resizable: true,
        dragable: true,
        startsAt: new Date(),
        endsAt: scheduleVm.end
    };

    scheduleVm.testDate = {
        y: scheduleVm.temp.getFullYear(),
        m: scheduleVm.temp.getMonth(),
        d: scheduleVm.temp.getDate(),
        sh: null,
        eh: null,
        sm: null,
        em: null
    }

    initState();

    function initState() {
        scheduleVm.calendarView = 'month';
        scheduleVm.calendarDate = new Date();
        scheduleVm.viewDate = new Date();

        scheduleVm.events = [];
    };

    scheduleVm.changeToDay = changeToDay;
    function changeToDay(){
        scheduleVm.calendarView = 'day';
    };

    scheduleVm.changeToMonth = changeToMonth;
    function changeToMonth(){
        scheduleVm.calendarView = 'month';
    };

    scheduleVm.loadEmployees = loadEmployees;
    function loadEmployees() {
        return $timeout(function() {
            employeeService.getEmployees()
                .then(function(data){
                    scheduleVm.employees = data;
                });
       }, 500);
   };

   scheduleVm.addNewEvent = addNewEvent;
   function addNewEvent(){
       var d = scheduleVm.testDate;
       scheduleVm.newEvent.startsAt = new Date(d.y, d.m, d.d, d.sh, d.sm);
       scheduleVm.newEvent.endsAt = new Date(d.y,d.m,d.d, d.eh, d.em);
       console.log(scheduleVm.testDate);
       console.log(scheduleVm.newEvent);
       scheduleVm.events.push(scheduleVm.newEvent);
   };

}
