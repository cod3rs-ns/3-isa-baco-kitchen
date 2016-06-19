angular
    .module('isa-mrs-project')
    .controller('WaiterReportController', WaiterReportController);

WaiterReportController.$inject = ['$mdDialog', 'reportService', 'waiter_id', 'waiter_name'];

function WaiterReportController($mdDialog, reportService, waiter_id, waiter_name) {
    var reportVm = this;
    reportVm.dialogName = 'Prihodi konobara';
    reportVm.startDate = new Date();
    reportVm.endDate = new Date();
    reportVm.waiterId = waiter_id;
    reportVm.waiterName = waiter_name;
    reportVm.total = 0;
    reportVm.message = '';
    reportVm.activated = false;
    reportVm.invalidReport = false;
    reportVm.partialReport = false;

    reportVm.showReport = showReport;
    reportVm.cancel = cancel;

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
        var dates = [reportVm.startDate, reportVm.endDate];
        reportService.findBillsByWaiter(reportVm.waiterId, dates)
            .then(function(response) {
                var data = response.data;
                var reportData = new Array();
                for (var i = 0; i < data.length; i++) {
                    var d = new Date(data[i].publishDate);
                    var just_date = new Date(d.getFullYear(), d.getMonth(), d.getDate());
                    var timestamp_key = just_date.getTime();
                    if (reportData[timestamp_key] == null) {
                        reportData[timestamp_key] = data[i].totalAmount;
                    }else {
                        var newVal = reportData[timestamp_key] + data[i].totalAmount;
                        reportData[timestamp_key] = newVal;
                    }
                }
                reportVm.report = reportData;
                reportVm.activated = true;
                drawChart();
            });
    }

    function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Datum');
        data.addColumn('number', 'Ostvareni prihodi');

        var rows = [];
        reportVm.total = 0;
        for (var key in reportVm.report) {
            rows.push([{v:new Date(parseInt(key))}, reportVm.report[key]]);
            reportVm.total = reportVm.total + reportVm.report[key];
        }

        reportVm.message = 'Ukupan prihod za izabrani vremenski period: ' + reportVm.total + ' RSD';

        data.addRows(rows);
        var options = {
            title: 'Konobar ' + reportVm.waiterName + ' - prihodi po datumu',
            width: 800,
            height: 400,
            hAxis: {
                title: 'Datum',
                format: 'dd.MM.yyyy'
            },
            viewWindow: {
                min: reportVm.startDate,
                max: reportVm.endDate
            },
            vAxis: {
                title: 'Ostvareni prihodi'
            }
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    };

    function cancel() {
        $mdDialog.cancel();
    };
}
