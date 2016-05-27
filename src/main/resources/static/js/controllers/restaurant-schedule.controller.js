angular
    .module('isa-mrs-project')
    .controller('RestaurantScheduleController', RestaurantScheduleController);

RestaurantScheduleController.$inject = ['employeeService', 'scheduleService', 'regionService', '$timeout', '$mdToast', 'moment', 'workingTimeService', 'shiftTemplateService'];

function RestaurantScheduleController(employeeService, scheduleService, regionService, $timeout, $mdToast, moment, workingTimeService, shiftTemplateService) {
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
    scheduleVm.shifts = [];
    scheduleVm.loadShifts = loadShifts;
    scheduleVm.setShift = setShift;
    scheduleVm.invalidForm = false;
    scheduleVm.invalidMessage = '';

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

    function loadShifts() {
        return $timeout(function() {
            // TODO 2 change to actual restaurant
            shiftTemplateService.findShiftTemplatesByRestaurant(2)
                .then(function(data) {
                    scheduleVm.shifts = data;
                });
       }, 500);
   };

   function setShift(shift) {
       scheduleVm.testDate.sh = shift.startHours;
       scheduleVm.testDate.sm = shift.startMinutes;
       scheduleVm.testDate.eh = shift.endHours;
       scheduleVm.testDate.em = shift.endMinutes;
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
   function eventTimesChanged(calendarEvent, newStart, newEnd) {
        var start = moment(newStart);
        var end = moment(newEnd);
        var start_day = start.day(); // Number 1-7 representing day of the week
        var end_day = end.day();
        var t = scheduleVm.restaurantWorkingTime;

        if (start_day == 6 && t.workingOnSat == false) {
            showToast('Restoran ne radi subotom.');
            return;
        };
        if (start_day == 0 && t.workingOnSun == false) {
            showToast('Restoran ne radi nedeljom.')
            return;
        };

        var restaurantStart = angular.copy(start);
        var restaurantEnd = {};

        if (start_day > 0 && start_day < 6) {
            restaurantStart.hour(t.regStartHours);
            restaurantStart.minute(t.regStartMinutes);
            if (t.regReversed) {
                restaurantEnd = restaurantStart.clone().add(1, 'd');
            } else{
                restaurantEnd = moment(newStart);
            }
            restaurantEnd.hour(t.regEndHours);
            restaurantEnd.minute(t.regEndMinutes);
        } else if (start_day == 6) {
            restaurantStart.hour(t.satStartHours);
            restaurantStart.minute(t.satStartMinutes);
            if (t.satReversed) {
                restaurantEnd = restaurantStart.clone().add(1, 'd');
            } else{
                restaurantEnd = moment(newStart);
            }
            restaurantEnd.hour(t.satEndHours);
            restaurantEnd.minute(t.satEndMinutes);
        } else if ( start_day == 0) {
            restaurantStart.hour(t.sunStartHours);
            restaurantStart.minute(t.sunStartMinutes);
            if (t.sunReversed) {
                restaurantEnd = restaurantStart.clone().add(1, 'd');
            } else{
                restaurantEnd = moment(newStart);
            }
            restaurantEnd.hour(t.sunEndHours);
            restaurantEnd.minute(t.sunEndMinutes);
        } else {
           // error
           return;
        };
        console.log('------');
        console.log(restaurantStart.format("dddd, MMMM Do YYYY, h:mm:ss a"));
        console.log(restaurantEnd.format("dddd, MMMM Do YYYY, h:mm:ss a"));

        if(start.isBefore(restaurantStart) || start.isAfter(restaurantEnd)) {
            showToast('Početak rasporeda rada zaposlenog se ne uklapa u radno vreme.');
            return;
        };
        if(end.isBefore(restaurantStart) || end.isAfter(restaurantEnd)) {
            showToast('Kraj rasporeda rada zaposlenog se ne uklapa u radno vreme.');
            return;
        }

       calendarEvent.startsAt = newStart;
       calendarEvent.endsAt = newEnd;
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
           return 'teal';
       };
       if (type == 'waiter') {
           return 'pink';
       }
       if (type == 'bartender') {
           return 'indigo';
       }
   };

   scheduleVm.validateForm = validateForm;
   function validateForm() {
       var start = moment(scheduleVm.dateClicked);
       var end = moment(scheduleVm.dateClicked);
       start.hours(scheduleVm.testDate.sh);
       start.minutes(scheduleVm.testDate.sm);
       end.hours(scheduleVm.testDate.eh);
       end.minutes(scheduleVm.testDate.em);

       var start_day = start.day(); // Number 1-7 representing day of the week
       var end_day = end.day();
       var t = scheduleVm.restaurantWorkingTime;

       if (start_day == 6 && t.workingOnSat == false) {
           scheduleVm.invalidForm = true;
           scheduleVm.invalidMessage = 'Restoran ne radi subotom.';
           return;
       };
       if (start_day == 0 && t.workingOnSun == false) {
           scheduleVm.invalidForm = true;
           scheduleVm.invalidMessage = 'Restoran ne radi nedeljom.';
           return;
       };

       var restaurantStart = angular.copy(start);
       var restaurantEnd = {};

       if (start_day > 0 && start_day < 6) {
           restaurantStart.hour(t.regStartHours);
           restaurantStart.minute(t.regStartMinutes);
           if (t.regReversed) {
               restaurantEnd = restaurantStart.clone().add(1, 'd');
           } else{
               restaurantEnd = moment(newStart);
           }
           restaurantEnd.hour(t.regEndHours);
           restaurantEnd.minute(t.regEndMinutes);
       } else if (start_day == 6) {
           restaurantStart.hour(t.satStartHours);
           restaurantStart.minute(t.satStartMinutes);
           if (t.satReversed) {
               restaurantEnd = restaurantStart.clone().add(1, 'd');
           } else{
               restaurantEnd = moment(newStart);
           }
           restaurantEnd.hour(t.satEndHours);
           restaurantEnd.minute(t.satEndMinutes);
       } else if ( start_day == 0) {
           restaurantStart.hour(t.sunStartHours);
           restaurantStart.minute(t.sunStartMinutes);
           if (t.sunReversed) {
               restaurantEnd = restaurantStart.clone().add(1, 'd');
           } else{
               restaurantEnd = moment(newStart);
           }
           restaurantEnd.hour(t.sunEndHours);
           restaurantEnd.minute(t.sunEndMinutes);
       } else {
          // error
          return;
       };
       console.log('------');
       console.log(restaurantStart.format("dddd, MMMM Do YYYY, h:mm:ss a"));
       console.log(restaurantEnd.format("dddd, MMMM Do YYYY, h:mm:ss a"));

       if(start.isBefore(restaurantStart) || start.isAfter(restaurantEnd)) {
           scheduleVm.invalidForm = true;
           scheduleVm.invalidMessage = 'Početak rasporeda rada zaposlenog se ne uklapa u radno vreme.';
           return;
       };
       if(end.isBefore(restaurantStart) || end.isAfter(restaurantEnd)) {
           scheduleVm.invalidForm = true;
           scheduleVm.invalidMessage = 'Kraj rasporeda rada zaposlenog se ne uklapa u radno vreme.';
           return;
       };

       scheduleVm.invalidForm = false;
   };

}
