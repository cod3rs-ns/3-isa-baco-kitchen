angular.module('isa-mrs-project')
    .directive('wcUnique', ['uniqueService', function (uniqueService) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            element.bind('blur', function (e) {
                if (!ngModel || !element.val()) return;
                var keyProperty = scope.$eval(attrs.wcUnique);
                var currentValue = element.val();
                uniqueService.checkUnique(keyProperty.key, keyProperty.property, currentValue)
                    .then(function (unique) {
                        if (currentValue == element.val()) { 
                            ngModel.$setValidity('unique', !unique);
                        }
                    }, function () {
                        //Probably want a more robust way to handle an error
                        //For this demo we'll set unique to true though
                        ngModel.$setValidity('unique', true);
                    });
            });
        }
    }
}]);