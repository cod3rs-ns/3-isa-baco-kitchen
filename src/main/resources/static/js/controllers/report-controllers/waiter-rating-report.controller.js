angular
    .module('isa-mrs-project')
    .controller('WaiterRatingReportController', WaiterRatingReportController);

WaiterRatingReportController.$inject = ['reportService', '$mdDialog', 'waiter_id', 'waiter_name'];

function WaiterRatingReportController(reportService, $mdDialog, waiter_id, waiter_name) {
    var reportVm = this;
    reportVm.dialogName = 'Ocene konobara';
    reportVm.waiterId = waiter_id;
    reportVm.waiterName = waiter_name;
    reportVm.reviews = [];
    reportVm.activated = false;
    reportVm.message = '';
    reportVm.message_2 = '';
    reportVm.invalidReport = false;

    reportVm.cancel = cancel;
    reportVm.showReport = showReport;
    init();

    function init() {
        if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
            // Load for first time if needed
            google.charts.load('current', {'packages':["calendar", 'corechart', 'bar']});

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
        reportService.findReviewsByWaiter(reportVm.waiterId)
            .then(function(response) {
                var data = response.data;
                if (data.length == 0){
                    reportVm.invalidReport = true;
                }
                var reportData = [0, 0, 0, 0, 0];
                reportVm.message = 'Ukupan broj recenzija: ' + data.length;
                var total = 0.0;
                for (var i = 0; i < data.length; i++) {
                    var val = reportData[data[i].serviceRate - 1] + 1;
                    reportData[data[i].serviceRate - 1] = val;
                    total = total + data[i].serviceRate;
                }
                var mean = total / data.length;
                reportVm.message_2 = 'Prosečna ocena: ' + mean.toFixed(2);
                reportVm.report = reportData;
                reportVm.activated = true;
                drawChart();
            });
    }

    function drawChart() {
        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Ocena');
        data.addColumn('number', 'Broj recenzija');
        data.addRows([
          ['Ocena 1', reportVm.report[0]],
          ['Ocena 2', reportVm.report[1]],
          ['Ocena 3', reportVm.report[2]],
          ['Ocena 4', reportVm.report[3]],
          ['Ocena 5', reportVm.report[4]]
        ]);

        // Set chart options
        var options = {
            'title': 'Konobar ' + reportVm.waiterName + ' - pregled ocena',
            'width': 550,
            'height': 450
        };

        // Instantiate and draw chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    };

    function cancel() {
        $mdDialog.cancel();
    };

}
