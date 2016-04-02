angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

function BarmanProfileController() {
    var barmanProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    barmanProfileVm.name = 'Sergio dr Ramos ';
    barmanProfileVm.foo = foo;
	
	
	barmanProfileVm.waitingDrinks = [
      {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      },
	  {
        name: 'Cosmopolitan',
		img_src: 'images/meals/cosmopolitan.jpg'
      }
    ];
	
	
	barmanProfileVm.preparingDrinks = [
      {
        name: 'Martini',
		img_src: 'images/meals/martini.jpg'
      },
	  {
        name: 'Sour',
		img_src: 'images/meals/sour.jpg'
      }
    ];

    
    // Implement functions later
    function foo() {
        
    }
}