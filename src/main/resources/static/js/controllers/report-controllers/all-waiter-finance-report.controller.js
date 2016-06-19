angular
    .module('isa-mrs-project')
    .controller('WaitersFinanceReportController', WaitersFinanceReportController);

WaitersFinanceReportController.$inject = ['$mdDialog', 'reportService', 'restaurant'];

function WaitersFinanceReportController($mdDialog, reportService, restaurant) {
    var reportVm = this;
    reportVm.dialogName = 'Prihodi svih konobara konobara';
    reportVm.startDate = new Date();
    reportVm.endDate = new Date();
    reportVm.restaurant = restaurant;
    reportVm.total = 0;
    reportVm.message = '';
    reportVm.activated = false;
    reportVm.invalidReport = false;
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
            .then(function(response) {
                var data = response.data;
                if (data.length == 0) {
                    reportVm.invalidReport = true;
                }
                var reportData = new Array();
                for (var i = 0; i < data.length; i++) {
                    var waiter_key = data[i].waiter.firstName + ' ' + data[i].waiter.lastName;
                    var d = new Date(data[i].publishDate);
                    var just_date = new Date(d.getFullYear(), d.getMonth(), d.getDate());
                    var timestamp_key = just_date.getTime();
                    if (reportVm.partialReport) {
                        if (timestamp_key >= start_ts && timestamp_key <= end_ts) {
                            if (reportData[waiter_key] == null) {
                                reportData[waiter_key] = data[i].totalAmount;
                            }else {
                                var newVal = reportData[waiter_key] + data[i].totalAmount;
                                reportData[waiter_key] = newVal;
                            }
                        }
                    }else {
                        if (reportData[waiter_key] == null) {
                            reportData[waiter_key] = data[i].totalAmount;
                        }else {
                            var newVal = reportData[waiter_key] + data[i].totalAmount;
                            reportData[waiter_key] = newVal;
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
        data.addColumn('string', 'Konobar');
        data.addColumn('number', 'Ostvareni prihodi');

        var rows = [];
        reportVm.total = 0;
        for (var key in reportVm.report) {
            rows.push([{v:key}, reportVm.report[key]]);
            reportVm.total = reportVm.total + reportVm.report[key];
        }

        reportVm.message = 'Ukupan prihod za izabrani vremenski period: ' + reportVm.total + ' RSD';

        data.addRows(rows);
        var options = {
            title: 'Restoran ' + reportVm.restaurant.name + ' - prihodi konobara',
            width: 800,
            height: 400,
            hAxis: {
                title: 'Ostvareni prihodi',
            },
            vAxis: {
                title: 'Konobar'
            }
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    };

    function cancel() {
        $mdDialog.cancel();
    };
}
