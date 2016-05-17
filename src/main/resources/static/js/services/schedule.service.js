angular
    .module('isa-mrs-project')
    .factory('scheduleService', scheduleService);

scheduleService.$inject = ['$http'];

function scheduleService($http) {
    var service = {
        getSchedules: getSchedules,
        getSchedule: getSchedule,
        updateSchedule: updateSchedule,
        deleteSchedule: deleteSchedule,
        createSchedule: createSchedule,
        getSchedulesByRestaurant: getSchedulesByRestaurant,
        getSchedulesByEmployee: getSchedulesByEmployee,
        getSchedulesByRegion: getSchedulesByRegion,
        getSchedulesByDay: getSchedulesByDay
    };
    return service;

    function getSchedules() {
        return $http.get('api/daily_schedules')
        .then(function(response) {
            return response.data;
        });
    };

    function getSchedule(id) {
        return $http.get('api/daily_schedules/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateSchedule(schedule, restaurant_id, employee_id, region_id) {
        return $http.put('/api/daily_schedules/r='+ restaurant_id + '+e='+ employee_id + '+r=' + region_id, schedule)
        .then(function (response) {
            return response.data;
        });
    };

    function deleteSchedule(id) {
        return $http.delete('/api/daily_schedules/' +id)
        .then(function (response) {
            return response.data;
        });
    };

    function createSchedule(schedule, restaurant_id, employee_id, region_id){
        return $http.post('/api/daily_schedules/r='+ restaurant_id + '+e='+ employee_id + '+r=' + region_id, schedule)
        .then(function (response) {
            return response.data;
        });
    };

    function getSchedulesByRestaurant(restaurant_id){
        return $http.get('api/daily_schedules/rst=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

    function getSchedulesByEmployee(employee_id){
        return $http.get('api/daily_schedules/emp=' + employee_id)
        .then(function(response) {
            return response.data;
        });
    };

    function getSchedulesByRegion(region_id){
        return $http.get('api/daily_schedules/reg=' + region_id)
        .then(function(response) {
            return response.data;
        });
    };

    function getSchedulesByDay(day){
        return $http.get('api/daily_schedules/day', day)
        .then(function(response) {
            return response.data;
        });
    };
}
