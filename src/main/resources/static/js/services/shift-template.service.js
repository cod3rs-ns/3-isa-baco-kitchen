angular
    .module('isa-mrs-project')
    .factory('shiftTemplateService', shiftTemplateService);

shiftTemplateService.$inject = ['$http'];

function shiftTemplateService($http) {
    var service = {
        getShiftTemplates: getShiftTemplates,
        getShiftTemplate: getShiftTemplate,
        updateShiftTemplate: updateShiftTemplate,
        deleteShiftTemplate: deleteShiftTemplate,
        createShiftTemplate: createShiftTemplate,
        findShiftTemplatesByRestaurant: findShiftTemplatesByRestaurant
    };
    return service;

    function getShiftTemplates() {
        return $http.get('api/shifts')
        .then(function(response) {
            return response.data;
        });
    };

    function getShiftTemplate(id) {
        return $http.get('api/shifts/' + id)
            .then(function(response){
                return response.data;
            });
    };

    function updateShiftTemplate(shiftTemplate) {
        return $http.put('/api/shifts', shiftTemplate)
            .then(function (response) {
                return response.data;
            });
    };

    function deleteShiftTemplate(id) {
        return $http.delete('/api/shifts/' + id)
            .then(function (response) {
                return response.data;
            });
    };

    function createShiftTemplate(shiftTemplate) {
        return $http.post('/api/shifts', shiftTemplate)
            .then(function (response) {
                return response.data;
            });
    };

    function findShiftTemplatesByRestaurant(restaurant_id){
        return $http.get('api/shifts/r=' + restaurant_id)
            .then(function(response) {
                return response.data;
            });
    };
}
