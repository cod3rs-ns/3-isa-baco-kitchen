angular
    .module('isa-mrs-project')
    .controller('FinancesController', FinancesController);

FinancesController.$inject = ['$mdDialog', 'reportService', 'restaurant'];

function FinancesController($mdDialog, reportService, restaurant) {
    var reportVm = this;
    reportVm.dialogName = 'Finansije restorana';
    reportVm.startDate = new Date();
    reportVm.endDate = new Date();
    reportVm.total = 0;
    reportVm.message = '';
    reportVm.activated = false;
    reportVm.invalidReport = false;
    reportVm.restaurant = restaurant;
    reportVm.partialReport = false;

    reportVm.showReportInit = showReportInit;
    reportVm.showReport = showReport;
    reportVm.cancel = cancel;

    init();

    function init() {
        if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
            // Load for first time if needed
            google.charts.load('current', {'packages':["calendar", 'corechart', 'bar']});
            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(reportVm.showReportInit);
        }else {
            // redraw on every call
            angular.element(document).ready(function () {
                reportVm.showReportInit();
            });
        };
    }

    function showReportInit() {
        var start_ts =  reportVm.startDate.getTime();
        var end_ts = reportVm.endDate.getTime();
        reportService.findBillsByRestaurant(reportVm.restaurant.restaurantId)
            .success(function(data) {
                if (data.length == 0) {
                    reportVm.invalidReport = true;
                }
                var reportData = new Array();
                for (var i = 0; i < data.length; i++) {
                    var d = new Date(data[i].publishDate);
                    var just_date = new Date(d.getFullYear(), d.getMonth(), d.getDate());
                    var timestamp_key = just_date.getTime();
                    if (reportVm.partialReport) {
                        if (timestamp_key >= start_ts && timestamp_key <= end_ts) {
                            if (reportData[timestamp_key] == null) {
                                reportData[timestamp_key] = data[i].totalAmount;
                            }else {
                                var newVal = reportData[timestamp_key] + data[i].totalAmount;
                                reportData[timestamp_key] = newVal;
                            }
                        }
                    }else {
                        if (reportData[timestamp_key] == null) {
                            reportData[timestamp_key] = data[i].totalAmount;
                        }else {
                            var newVal = reportData[timestamp_key] + data[i].totalAmount;
                            reportData[timestamp_key] = newVal;
                        }
                    }
                }
                reportVm.report = reportData;
                reportVm.activated = true;
                drawChart();
            });
    }

    function showReport() {
        reportVm.partialReport = true;
        reportVm.showReportInit();
    }

    function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Datum');
        data.addColumn('number', 'Prihodi');

        var rows = [];
        reportVm.total = 0;
        for (var key in reportVm.report) {
            rows.push([{v:new Date(parseInt(key))}, reportVm.report[key]]);
            reportVm.total = reportVm.total + reportVm.report[key];
        }

        reportVm.message = 'Ukupni prihodi: ' + reportVm.total + ' RSD';

        data.addRows(rows);
        var options = {
            title: 'Restoran ' + reportVm.restaurant.name + ' - pregled prihoda',
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
