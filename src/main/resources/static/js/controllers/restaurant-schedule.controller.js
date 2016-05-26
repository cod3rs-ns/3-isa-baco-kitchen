angular
    .module('isa-mrs-project')
    .controller('RestaurantScheduleController', RestaurantScheduleController);

RestaurantScheduleController.$inject = ['employeeService', 'scheduleService', 'regionService', '$timeout', '$mdToast', 'moment', 'workingTimeService'];

function RestaurantScheduleController(employeeService, scheduleService, regionService, $timeout, $mdToast, moment, workingTimeService) {
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
        region: null,
        reversed: false
    };

    scheduleVm.selectedEmployee = false;
    scheduleVm.isWaiterSelected = false;
    initState();

    function initState() {
        workingTimeService.findWorkingTimeByRestaurant(2).
            then(function(data) {
                scheduleVm.restaurantWorkingTime = data;
            });
        scheduleVm.calendarView = 'month';
        scheduleVm.calendarDate = new Date();
        scheduleVm.viewDate =  moment().startOf('month').toDate();
        scheduleVm.events = [];
        scheduleVm.viewChangeEnabled = true;
        scheduleService.getSchedules()
            .then(function(data) {
                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var test_event = data[i];
                    test_event.title = data[i].employee.firstName;
                    test_event.startsAt = new Date(data[i].mergedStart);
                    test_event.endsAt = new Date(data[i].mergedEnd);
                    test_event.type = convertTypeToClass(data[i].employee.type);
                    test_event.draggable = true;
                    test_event.resizable = true;
                    console.log(test_event);
                    scheduleVm.events.push(test_event);
                };
            });

    };


    scheduleVm.viewChangeClicked = viewChangeClicked;
    function viewChangeClicked(date, nextView) {
        console.log(date, nextView);
        return scheduleVm.viewChangeEnabled;
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

    scheduleVm.validateForm = validateForm;
    function validateForm() {
        var wt = scheduleVm.restaurantWorkingTime;
        var t = scheduleVm.testDate;
        var reversed = scheduleVm.testDate.reversed;
        var work_day  = 'regular';
        var a = moment('2016-7-28', 'YYYY MM DD');
        var wt_a = moment('2016-7-28', 'YYYY MM DD');
        var b = moment('2016-7-28', 'YYYY MM DD');
        var wt_b = moment('2016-7-28', 'YYYY MM DD');
        if (reversed) {
            b = moment('2016-7-29', 'YYYY MM DD');
            wt_b = moment('2016-7-29', 'YYYY MM DD');
        };


        a.hour(t.sh);
        a.minute(t.sm);
        b.hour(t.eh);
        b.minute(t.em);

        // check if start is before end
        if(a.isAfter(b)){
            scheduleVm.invalidForm = true;
            scheduleVm.invalidMessage = 'Početak smene mora biti pre kraja.'
            return;
        };

        if (work_day == 'regular'){
            wt_a.hour(wt.regStartHours);
            wt_a.minute(wt.regStartMinutes);
            wt_b.hour(wt.regEndHours);
            wt_b.minute(wt.regEndMinutes);
        } else if (work_day == 'saturday'){
            wt_a.hour(wt.satStartHours);
            wt_a.minute(wt.satStartMinutes);
            wt_b.hour(wt.satEndHours);
            wt_b.minute(wt.satEndMinutes);
        } else if (work_day == 'sunday'){
            wt_a.hour(wt.sunStartHours);
            wt_a.minute(wt.sunStartMinutes);
            wt_b.hour(wt.sunEndHours);
            wt_b.minute(wt.sunEndMinutes);
        } else {
            return;
        };
        console.log(a);
        console.log(b);
        console.log(wt_a);
        console.log(wt_b);
        // Check start
        if (wt_a.isBefore(a) || wt_a.isAfter(b)) {
            scheduleVm.invalidForm = true;
            scheduleVm.invalidMessage = 'Početak smene se ne uklapa u radno vreme restorana.'
            return;
        };

        // Check end
        if(wt_b.isBefore(a) || wt_b.isAfter(b)) {
            scheduleVm.invalidForm = true;
            scheduleVm.invalidMessage = 'Kraj smene se ne uklapa u radno vreme restorana.'
            return;
        };
        scheduleVm.invalidForm = false;
    };

   scheduleVm.addNewEvent = addNewEvent;
   function addNewEvent(){
       scheduleVm.validateForm();
       if (scheduleVm.invalidForm){
           return;
       }
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
       scheduleVm.newEvent.mergedStart = scheduleVm.newEvent.startsAt;
       scheduleVm.newEvent.mergedEnd = scheduleVm.newEvent.endsAt;
       scheduleVm.draggable = true;
       scheduleVm.resizable = true;
       scheduleVm.events.push(scheduleVm.newEvent);

       var schedule = {
           dailyScheduleId: null,
           day: scheduleVm.newEvent.startsAt.getDate(),
           region: null,
           employee: null,
           restaurantId: 2,
           startHours: d.sh,
           startMinutes: d.sm,
           endHours: d.eh,
           endMinutes: d.em,
           mergedStart: scheduleVm.newEvent.startsAt,
           mergedEnd: scheduleVm.newEvent.endsAt
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

   scheduleVm.eventTimesChanged = eventTimesChanged;
   function eventTimesChanged(calendarEvent) {
       console.log(calendarEvent);
       var t = splitDate(calendarEvent);
       var regid = -1;
       if (t.employee.type == 'waiter') {
           regid = t.region.regionId;
       }
       scheduleService.updateSchedule(t, 2, t.employee.userId, regid)
       showToast('Uspešno ste ažurirali radno vreme radnika.');
   };

   function showToast(toast_message) {
       $mdToast.show({
           hideDelay : 3000,
           position  : 'bottom right',
           template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
       });
   };

   function splitDate(d_event) {
        d_event.mergedEnd = d_event.endsAt;
        d_event.mergedStart = d_event.startsAt;
        // TODO event on two dates
        d_event.startHours = d_event.startsAt.getHours();
        d_event.endHours = d_event.endsAt.getHours();
        d_event.startHours = d_event.startsAt.getMinutes();
        d_event.endHours = d_event.endsAt.getMinutes();
        return d_event;
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
