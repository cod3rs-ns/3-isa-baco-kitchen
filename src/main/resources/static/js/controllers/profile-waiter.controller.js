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

}