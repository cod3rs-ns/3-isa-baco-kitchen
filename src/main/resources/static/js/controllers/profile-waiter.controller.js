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
		title: 'My event title', // The title of the event
		type: 'info', // The type of the event (determines its color). Can be important, warning, info, inverse, success or special
		startsAt: new Date(2016,4,3,1), // A javascript date object for when the event starts
		endsAt: new Date(2016,4,4,15), // Optional - a javascript date object for when the event ends
		editable: false, // If edit-event-html is set and this field is explicitly set to false then dont make it editable.
		deletable: false, // If delete-event-html is set and this field is explicitly set to false then dont make it deleteable
		draggable: true, //Allow an event to be dragged and dropped
		resizable: true, //Allow an event to be resizable
		incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
		recursOn: 'year', // If set the event will recur on the given period. Valid values are year or month
		cssClass: 'a-css-class-name' //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
	}
	];
	

}