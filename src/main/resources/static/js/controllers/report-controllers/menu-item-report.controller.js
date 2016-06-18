angular
    .module('isa-mrs-project')
    .controller('MenuItemReportController', MenuItemReportController);

MenuItemReportController.$inject = ['reportService', '$mdDialog', 'item_id', 'item_name'];

function MenuItemReportController(reportService, $mdDialog, item_id, item_name) {
    var reportVm = this;
    reportVm.dialogName = 'Ocene stavke menija';
    reportVm.menuItemId = item_id;
    reportVm.menuItemName = item_name;
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
            google.charts.load('current', {'packages':["calendar", 'corechart']});

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
        reportService.findReviewsByMenuItem(reportVm.menuItemId)
            .then(function(response) {
                var data = response.data;
                if (data.length == 0){
                    reportVm.invalidReport = true;
                }
                var reportData = [0, 0, 0, 0, 0];
                reportVm.message = 'Ukupan broj recenzija: ' + data.length;
                var total = 0.0;
                for (var i = 0; i < data.length; i++) {
                    var val = reportData[data[i].foodRate - 1] + 1;
                    reportData[data[i].foodRate - 1] = val;
                    total = total + data[i].foodRate;
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
            'title': reportVm.menuItemName + ' - pregled ocena',
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
