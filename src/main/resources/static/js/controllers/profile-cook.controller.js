angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

function CookProfileController() {
    var cookProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    cookProfileVm.name = 'Sergio dr Ramos ';
    cookProfileVm.foo = foo;

	cookProfileVm.waitingMeals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      }
    ];
	
	
	cookProfileVm.preparingMeals = [
      {
        name: 'Kolač sa borovnicama',
		img_src: 'images/meals/borovnica.jpg'
      },
	  {
        name: 'Štrudla sa makom',
		img_src: 'images/meals/mak.jpg'
      },
	  {
        name: 'Čokoladni kolač',
		img_src: 'images/meals/cokolada.jpg'
      }
    ];
	
    // Implement functions later
    function foo() {
        
    }
}