angular
    .module('isa-mrs-project')
    .controller('MenuItemReportController', MenuItemReportController);

MenuItemReportController.$inject = ['$mdDialog'];

function MenuItemReportController($mdDialog) {
    var reportVm = this;
    reportVm.dialogName = 'Ic something';

    init();

    function init() {
        if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
            // Load for first time if needed
            google.charts.load('current', {'packages':['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart);
        }else {
            // redraw on every call
            angular.element(document).ready(function () {
                drawChart();
            });
        };
    }

    function drawChart() {
        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Ocena');
        data.addColumn('number', 'Broj recenzija');
        data.addRows([
          ['Ocena 5', 4],
          ['Ocena 4', 1],
          ['Ocena 3', 2],
          ['Ocena 2', 1],
          ['Ocena 1', 2]
        ]);

        // Set chart options
        var options = {'title':'Ocena toga i toga',
                       'width':400,
                       'height':400};

        // Instantiate and draw chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    };

    reportVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

}
