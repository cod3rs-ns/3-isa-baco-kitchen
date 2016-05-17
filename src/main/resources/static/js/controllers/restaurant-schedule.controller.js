angular
    .module('isa-mrs-project')
    .controller('RestaurantScheduleController', RestaurantScheduleController);

RestaurantScheduleController.$inject = ['employeeService', 'scheduleService', 'regionService', '$timeout', '$mdToast'];

function RestaurantScheduleController(employeeService, scheduleService, regionService, $timeout, $mdToast) {
    var scheduleVm = this;
    scheduleVm.employees = [];
    scheduleVm.regions = [];
    scheduleVm.temp = new Date();
    scheduleVm.newEvent = {
        title: null,
        type: 'danger',
        resizable: true,
        dragable: true,
        startsAt: new Date(),
        endsAt: scheduleVm.end
    };

    scheduleVm.dateClicked = null;
    scheduleVm.selectedDate = false;
    scheduleVm.testDate = {
        y: scheduleVm.temp.getFullYear(),
        m: scheduleVm.temp.getMonth(),
        d: scheduleVm.temp.getDate(),
        sh: null,
        eh: null,
        sm: null,
        em: null,
        employee: null,
        region: null
    };

    scheduleVm.selectedEmployee = false;
    scheduleVm.isWaiterSelected = false;
    initState();

    function initState() {
        scheduleVm.calendarView = 'month';
        scheduleVm.calendarDate = new Date();
        scheduleVm.viewDate = new Date();
        scheduleVm.events = [];
        scheduleService.getSchedules()
            .then(function(data) {
                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var test_event = {};
                    test_event.title = data[i].employee.firstName;
                    test_event.type = 'success';
                    test_event.startsAt = new Date(data[i].day);
                    test_event.endsAt = new Date(data[i].day);
                    console.log(test_event);
                    test_event.startsAt.setHours(data[i].startHours);
                    test_event.startsAt.setMinutes(data[i].startMinutes);
                    test_event.endsAt.setHours(data[i].endHours);
                    test_event.endsAt.setMinutes(data[i].endMinutes);
                    test_event.type = convertTypeToClass(data[i].employee.type);
                    console.log(test_event);
                    scheduleVm.events.push(test_event);
                };
            });

    };

    scheduleVm.changeToDay = changeToDay;
    function changeToDay(){
        scheduleVm.calendarView = 'day';
    };

    scheduleVm.changeToWeek = changeToWeek;
    function changeToWeek(){
        scheduleVm.calendarView = 'week';
    };

    scheduleVm.changeToMonth = changeToMonth;
    function changeToMonth(){
        scheduleVm.calendarView = 'month';
    };

    scheduleVm.timespanClicked = timespanClicked;
    function timespanClicked(date) {
      scheduleVm.dateClicked = date;
      console.log(scheduleVm.dateClicked);
      scheduleVm.selectedDate = true;
    };

    scheduleVm.onSelectedEmployee = onSelectedEmployee;
    function onSelectedEmployee(employee) {
        console.log(employee);
        if (employee.type == 'waiter') {
            scheduleVm.isWaiterSelected = true;
        } else {
            scheduleVm.isWaiterSelected = false;
        };
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

   scheduleVm.loadRegions = loadRegions;
   function loadRegions() {
       return $timeout(function() {
           regionService.getRegionsByRestaurant(2)
               .then(function(data){
                   scheduleVm.regions = data;
               });
      }, 500);
  };

   scheduleVm.addNewEvent = addNewEvent;
   function addNewEvent(){
       var d = scheduleVm.testDate;
       //scheduleVm.newEvent.startsAt = new Date(d.y, d.m, d.d, d.sh, d.sm);
       //scheduleVm.newEvent.endsAt = new Date(d.y,d.m,d.d, d.eh, d.em);
       scheduleVm.newEvent.startsAt = scheduleVm.dateClicked;
       scheduleVm.newEvent.startsAt.setHours(d.sh);
       scheduleVm.newEvent.startsAt.setMinutes(d.sm);
       scheduleVm.newEvent.endsAt = scheduleVm.dateClicked;
       scheduleVm.newEvent.endsAt.setHours(d.eh);
       scheduleVm.newEvent.endsAt.setMinutes(d.em);
       scheduleVm.newEvent.title = d.employee.firstName + ' ' + d.employee.lastName;
       scheduleVm.newEvent.type = convertTypeToClass(d.employee.type);
       console.log(scheduleVm.testDate);
       console.log(scheduleVm.newEvent);
       scheduleVm.events.push(scheduleVm.newEvent);
       var schedule = {
           dailyScheduleId: null,
           day: scheduleVm.newEvent.startsAt,
           region: null,
           employee: null,
           restaurantId: 2,
           startHours: d.sh,
           startMinutes: d.sm,
           endHours: d.eh,
           endMinutes: d.em
       };
       console.log(schedule);
       var regid = -1;
       if (d.employee.type == 'waiter') {
           regid = d.region.regionId;
       }
       scheduleService.createSchedule(schedule, 2, d.employee.userId, regid)
       .then(function(data) {
           console.log(data);
           showToast('Uspešno ste sačuvali raspored radnika.')
       });
   };

   function showToast(toast_message) {
       $mdToast.show({
           hideDelay : 3000,
           position  : 'top right',
           template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
       });
   };

   function convertTypeToClass(type) {
       if (type == 'cook') {
           return 'important';
       };
       if (type == 'waiter') {
           return 'success';
       }
       if (type == 'bartender') {
           return 'info';
       }
   };

}
