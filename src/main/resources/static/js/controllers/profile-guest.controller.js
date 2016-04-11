angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);

function GuestProfileController() {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.showSearch = false;
    guestProfileVm.name = 'Sergio Ramos';
    guestProfileVm.visits =
        [
          {
            img_src: 'http://medias.otbor.vm.aiprod.com/360x360/RESAQU033FS000JE/0-Bouliac-Restaurant-Le-St-James-2.jpg',
            name: 'Restoran Nolo',
            rating_food: 5,
            rating_service: 4,
            rating_restaurant: 3
          },
          {
            img_src: 'http://s3-media3.fl.yelpcdn.com/bphoto/eeDy9fmvYnUdnXXdVzQnYQ/348s.jpg',
            name: 'Restoran Baćo',
            rating_food: 2,
            rating_service: 5,
            rating_restaurant: 3
          },
          {
            img_src: 'https://www.wien.info/media/images/restaurant-silvio-nickol-palais-coburg.jpg/image_leading_article_teaser',
            name: 'Restoran AveMoa',
            rating_food: 1,
            rating_service: 4,
            rating_restaurant: 3
          }
        ];
        guestProfileVm.reservations =
            [
              {
                img_src: 'http://medias.otbor.vm.aiprod.com/360x360/RESAQU033FS000JE/0-Bouliac-Restaurant-Le-St-James-2.jpg',
                name: 'Restoran Nolo',
                type: 'Gost',
                time: '15:15',
                date: '30. mart 2016.',
                guests: 5
              },
              {
                img_src: 'http://s3-media3.fl.yelpcdn.com/bphoto/eeDy9fmvYnUdnXXdVzQnYQ/348s.jpg',
                name: 'Restoran Baćo',
                type: 'Vlasnik',
                time: '20:15',
                date: '21. april 2016.',
                guests: 10
              },
              {
                img_src: 'https://www.wien.info/media/images/restaurant-silvio-nickol-palais-coburg.jpg/image_leading_article_teaser',
                type: 'Gost',
                time: '20:00',
                date: '30. oktobar 2016.',
                guests: 2
              }
            ];
    guestProfileVm.friends = 
        [
            {
                avatar: 'images/friend-01-small.png', 
                name: 'Stefan Ristanović',
                colspan: 1, 
                rowspan: 1
            }, 
            {
                avatar: 'images/friend-02-small.png',
                name: 'Baćo',
                colspan: 1,
                rowspan: 1
            }, 
            {
                avatar: 'images/friend-04-small.png',
                name: 'Mika',
                colspan: 1, 
                rowspan: 1
            }, 
            {
                avatar: 'images/friend-01-small.png',
                name: 'Keky',
                colspan: 1,
                rowspan: 1
            }
        ]
    guestProfileVm.foo = foo;

    // Implement functions later
    function foo() {

    }
}
