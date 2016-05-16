angular
    .module('isa-mrs-project')
    .controller('ShiftTemplateController', ShiftTemplateController);

ShiftTemplateController.$inject = ['shiftTemplateService', '$mdToast'];

function ShiftTemplateController(shiftTemplateService, $mdToast) {
    var shiftVm = this;
    shiftVm.shiftTemplates = [];
    shiftVm.activeShiftTemplate = {};
    shiftVm.backup =  {};
    shiftVm.editState = false;
    shiftVm.inEditProcess = {};

    // methods
    shiftVm.createShiftTemplate = createShiftTemplate;
    shiftVm.deleteShiftTemplate = deleteShiftTemplate;
    shiftVm.initEditMode = initEditMode;
    shiftVm.updateShiftTemplate = updateShiftTemplate;
    shiftVm.cancelChanges = cancelChanges;

    activate();

    function activate() {
        initDefaultShiftTemplate();
        findShiftTemplatesByRestaurant();
    };

    function findShiftTemplatesByRestaurant() {
        // TODO get actual restaurant no
        shiftTemplateService.findShiftTemplatesByRestaurant(2)
            .then(function(data) {
                shiftVm.shiftTemplates = data;
            });
    };

    function initDefaultShiftTemplate() {
        shiftVm.activeShiftTemplate = {
            shiftId: null,
            startHours: 0,
            endHours: 24,
            startMinutes: 0,
            endMinutes: 0,
            name: '',
            restaurantId: 2
        };
    };

    function createShiftTemplate() {
        shiftTemplateService.createShiftTemplate(shiftVm.activeShiftTemplate)
            .then(function(data) {
                console.log(data);
                shiftVm.shiftTemplates.push(data);
                initDefaultShiftTemplate();
                showToast('Templejt smene je uspešno kreiran.')
            });
    };

    function deleteShiftTemplate(id) {
        var del_idx = -1;
        for (var i = 0; i < shiftVm.shiftTemplates.length; i++) {
            if (shiftVm.shiftTemplates[i].shiftId == id) {
                del_idx = i;
                break;
            };
        };
        shiftVm.shiftTemplates.splice(del_idx, 1);
        shiftTemplateService.deleteShiftTemplate(id)
            .then(function(data){
                console.log('Deleted shift template.');
                showToast('Templejt smene je uspešno obrisan.');
            });
    };

    function initEditMode(shift) {
        shiftVm.backup = angular.copy(shift);
        shiftVm.activeShiftTemplate = angular.copy(shift);
        shiftVm.inEditProcess = shift;
        shiftVm.editState = true;
    };

    function updateShiftTemplate() {
        shiftVm.inEditProcess.startHours = shiftVm.activeShiftTemplate.startHours;
        shiftVm.inEditProcess.endHours = shiftVm.activeShiftTemplate.endHours;
        shiftVm.inEditProcess.startMinutes = shiftVm.activeShiftTemplate.startMinutes;
        shiftVm.inEditProcess.endMinutes = shiftVm.activeShiftTemplate.endMinutes;
        shiftVm.inEditProcess.name = shiftVm.activeShiftTemplate.name;
        shiftTemplateService.updateShiftTemplate(shiftVm.inEditProcess)
            .then(function(data) {
                console.log(data);
                showToast('Templejt smene je uspešno sačuvan.');
            });
        initDefaultShiftTemplate();
        shiftVm.editState = false;
    };

    function cancelChanges() {
        shiftVm.editState = false;
        shiftVm.backup = {};
        shiftVm.inEditProcess = {};
        initDefaultShiftTemplate();
        showToast('Sve promene su uspešno poništene.');
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };
}
