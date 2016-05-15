angular
    .module('isa-mrs-project')
    .factory('workingTimeService', workingTimeService);

workingTimeService.$inject = ['$http'];

function workingTimeService($http) {
    var service = {
        getWorkingTimes: getWorkingTimes,
        getWorkingTime: getWorkingTime,
        updateWorkingTime: updateWorkingTime,
        createWorkingTime: createWorkingTime,
        findWorkingTimeByRestaurant: findWorkingTimeByRestaurant
    };
    return service;

    function getWorkingTimes() {
        return $http.get('api/working_times')
        .then(function(response) {
            return response.data;
        });
    };

    function getWorkingTime(id) {
        return $http.get('api/working_times/' + id)
            .then(function(response){
                return response.data;
            });
    };

    function updateWorkingTime(workingTime) {
        return $http.put('/api/working_times', workingTime)
            .then(function (response) {
                return response.data;
            });
    };

    function createWorkingTime(workingTime) {
        return $http.post('/api/working_times', workingTime)
            .then(function (response) {
                return response.data;
            });
    };

    function findWorkingTimeByRestaurant(restaurant_id){
        return $http.get('api/working_times/r=' + restaurant_id)
            .then(function(response) {
                return response.data;
            });
    };
}
