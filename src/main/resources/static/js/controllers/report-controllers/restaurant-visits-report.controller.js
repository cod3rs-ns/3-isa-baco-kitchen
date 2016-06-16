angular
    .module('isa-mrs-project')
    .controller('RestaurantVisitsController', RestaurantVisitsController);

RestaurantVisitsController.$inject = ['$mdDialog', 'reportService'];

function RestaurantVisitsController($mdDialog, reportService) {
    var reportVm = this;
    reportVm.dialogName = 'Posećenost restorana';
    reportVm.total = 0;
    reportVm.message = '';

    reportVm.showReport = showReport;
    reportVm.cancel = cancel;

    init();

    function init() {
        if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
            // Load for first time if needed
            google.charts.load("current", {packages:["calendar", 'corechart']});
            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(reportVm.showReport);
        }else {
            // redraw on every call
            angular.element(document).ready(function () {
                reportVm.showReport();
            });
        };
    }

    function showReport() {
        reportService.findReservationsByRestaurant(2)
            .then(function(data) {
                var reportData = new Array();
                for (var i = 0; i < data.length; i++) {
                    var d = new Date(data[i].reservationDateTime);
                    var just_date = new Date(d.getFullYear(), d.getMonth(), d.getDate());
                    var timestamp_key = just_date.getTime();
                    if (reportData[timestamp_key] == null) {
                        reportData[timestamp_key] = 1;
                    }else {
                        var newVal = reportData[timestamp_key] + 1;
                        reportData[timestamp_key] = newVal;
                    }
                }
                reportVm.report = reportData;
                drawChart();
            });
    }

    function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Datum');
        data.addColumn('number', 'Broj rezervacija');

        var rows = [];
        reportVm.total = 0;
        for (var key in reportVm.report) {
            rows.push([{v:new Date(parseInt(key))}, reportVm.report[key]]);
            reportVm.total = reportVm.total + reportVm.report[key];
        }

        reportVm.message = 'Ukupan broj rezervacija:' + reportVm.total;

        data.addRows(rows);
        var options = {
            title: 'Pregled posećenosti',
            height: 500,
            width: 1000
        };

        var chart = new google.visualization.Calendar(document.getElementById('chart_div'));
        chart.draw(data, options);
    };

    function cancel() {
        $mdDialog.cancel();
    };
}
