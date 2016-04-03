angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);


function RestaurantProfileController(){
    var restaurantVm = this;

    restaurantVm.name = 'Caffe Macchiato';
    restaurantVm.type = 'Italijanska hrana';
    restaurantVm.info = 'Takva i takva usluga, takva i takva ponuda, atmosfera, zašto baš da dodjete kod nas i to...'
    restaurantVm.time = '06:00 - 23:00'

    restaurantVm.meals = [
          {
            name: 'Kolač sa borovnicama',
    		img_src: 'images/meals/borovnica.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
          },
    	  {
            name: 'Štrudla sa makom',
    		img_src: 'images/meals/mak.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
          },
    	  {
            name: 'Štrudla sa makom',
    		img_src: 'images/meals/mak.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
          name: 'Kolač sa borovnicama',
          img_src: 'images/meals/borovnica.jpg',
          price: '150 RSD',
          info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        }
    ];

    restaurantVm.drinks = [
        {
            name: 'Sour',
            img_src: 'images/meals/sour.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Cosmopolitan',
            img_src: 'images/meals/cosmopolitan.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Sour',
            img_src: 'images/meals/sour.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Cosmopolitan',
            img_src: 'images/meals/cosmopolitan.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        }
    ];
}
