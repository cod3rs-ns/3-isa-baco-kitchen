angular
    .module('isa-mrs-project')
    .controller('TablesController', TablesController);

TablesController.$inject = ['tableService'];

function TablesController(tableService) {
    var tablesVm = this;
    tablesVm.tables = [];

    activate();

    function activate() {
        return getTablesByRestaurant().then(function() {
            // after success action
        });
    };

    function getTablesByRestaurant() {
        // TODO currently locked on 2
        return tableService.getTablesByRestaurant(2)
            .then(function(data) {
                tablesVm.tables = data;
                return tablesVm.tables;
            });
    };

    tablesVm.calculatePeople = calculatePeople;
    function calculatePeople(x, y){
        var area = x*y;
        if(area < 100){
            return 1;
        }else if (100 <= area < 200) {
            return 2;
        }else if (200 <= area < 300) {
            return 3;
        }else if (300 <= area < 400) {
            return 4;
        }else if (400 <= area < 500) {
            return 5;
        }else if (500 <= area < 600) {
            return 6;
        }else {
            return 'puno ljudi';
        }

    };

    // Define how should tables behave
    interact('.restaurant-table')
        .draggable({
            // enable inertial throwing
            inertia: false,
            // keep the element within the area of it's parent
            restrict: {
                restriction: 'parent',
                endOnly: true,
                elementRect: { top: 1, left: 1, bottom: 1, right: 1 }
            },
            // enable autoScroll
            autoScroll: false,

            // call this function on every dragmove event
            onmove: dragMoveListener,
            // call this function on every dragend event
            onend: null
        })
        .resizable({
            preserveAspectRatio: false,
            // define on which edges should table be resizable
            edges: { left: false, right: true, bottom: true, top: false }
        })
        .on('resizemove', resizeListener);

    // Function that saves current positon of the element after dragging
    function dragMoveListener (event) {
        var target = event.target;
        // keep the dragged position in the data-x/data-y attributes
        var x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx;
        var y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

        // translate the element
        target.style.webkitTransform =
        target.style.transform = 'translate(' + x + 'px, ' + y + 'px)';

        // update the posiion attributes
        target.setAttribute('data-x', x);
        target.setAttribute('data-y', y);
    };

    // Function that saves current size of element after resizing
    function resizeListener(event) {
        var target = event.target;
        var x = (parseFloat(target.getAttribute('data-x')) || 0);
        var y = (parseFloat(target.getAttribute('data-y')) || 0);

        // update the element's style
        target.style.width  = event.rect.width + 'px';
        target.style.height = event.rect.height + 'px';

        // translate when resizing from top or left edges
        x += event.deltaRect.left;
        y += event.deltaRect.top;

        target.style.webkitTransform =
        target.style.transform = 'translate(' + x + 'px,' + y + 'px)';

        target.setAttribute('data-x', x);
        target.setAttribute('data-y', y);

        // TODO
        target.textContent = Math.round(event.rect.width) + '×' + Math.round(event.rect.height);
    };

    // this is used on whole window when draggin or resizing
    window.dragMoveListener = dragMoveListener;
    window.resizeListener = resizeListener;

    //interact.maxInteractions(Infinity);
}
