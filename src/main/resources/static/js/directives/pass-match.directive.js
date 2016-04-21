angular.module('isa-mrs-project')
    .directive('passMatch', ['passService', function (passService) {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attrs,  ngModel) {
                element.bind('blur', function (e) {
                    if (!ngModel || !element.val()) return;
                    var keyProperty = scope.$eval(attrs.passMatch);
                    var currentValue = element.val();
                    passService.checkPass(currentValue)
                        .then(function (match) {
                            if (currentValue == element.val()) {
                                ngModel.$setValidity('match', match);
                            }
                        }, function () {
                            //Probably want a more robust way to handle an error
                            //For this demo we'll set unique to true though
                            ngModel.$setValidity('match', true);
                        });
                });
            }
        }
    }]);