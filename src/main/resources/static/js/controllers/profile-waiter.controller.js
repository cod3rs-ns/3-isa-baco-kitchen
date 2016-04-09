angular
    .module('isa-mrs-project')
    .controller('WaiterProfileController', WaiterProfileController);

function WaiterProfileController() {
    var waiterProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    waiterProfileVm.name = 'Sergio dr Ramos ';
    waiterProfileVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
	
	waiterProfileVm.meals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      },
	  {
        name: 'Cosmopolitan',
		img_src: 'images/meals/cosmopolitan.jpg'
      }
	];
	
	waiterProfileVm.bills = [
      {
        code: '1235AS45',
        total: '12000',
        date: '28. maj  2016',
      },
      {
        code: '8856A45B',
        total: '5000',
        date: '28. jun  2016',
      },
      {
        code: '88A7SD5A',
        total: '36987',
        date: '15. septembar  2016',
      },
    ];
	
	waiterProfileVm.calendarView = 'month';
	waiterProfileVm.calendarDate = new Date();
	waiterProfileVm.viewDate = new Date();


    waiterProfileVm.events = [
        {
            title: 'An event',
            type: 'warning',
            startsAt: moment().subtract(1, 'day').toDate(),
            endsAt: moment().subtract(1, 'day').add(5, 'hours').toDate(),
            draggable: true,
            resizable: true
        }, {
            title: '<i class="glyphicon glyphicon-asterisk"></i> <span class="text-primary">Another event</span>, with a <i>html</i> title',
            type: 'info',
            startsAt: moment().subtract(1, 'day').toDate(),
            endsAt: moment().add(5, 'days').toDate(),
            draggable: true,
            resizable: true
        }, {
            title: 'This is a really long event title that occurs on every year',
            type: 'important',
            startsAt: moment().startOf('day').add(7, 'hours').toDate(),
            endsAt: moment().startOf('day').add(19, 'hours').toDate(),
            recursOn: 'year',
            draggable: true,
            resizable: true
        }
    ];

}