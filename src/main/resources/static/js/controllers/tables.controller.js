angular
    .module('isa-mrs-project')
    .controller('TablesController', TablesController);

TablesController.$inject = ['tableService'];

function TablesController(tableService) {
    var tablesVm = this;
    tablesVm.tables = [];
    tablesVm.createTable = createTable;
    tablesVm.enabledEdit = true;
    tablesVm.deletedTables = [];
    tablesVm.backup = [];

    tablesVm.saveState = saveState;
    tablesVm.cancelChanges = cancelChanges;

    function saveState() {
        for (var i = 0; i < tablesVm.tables.length; i++) {
            if ( tablesVm.tables[i].hasOwnProperty('isNew')) {
                console.log('create');
                tableService.createTable(angular.toJson(tablesVm.tables[i]), 1);
            }else{
                console.log('update');
                tableService.updateTable(angular.toJson(tablesVm.tables[i]), 1);
            };
        };

        for (var i = 0; i < tablesVm.deletedTables.length; i++) {
            tableService.deleteTable(tablesVm.deletedTables[i].tableId);
        };
        tablesVm.deletedTables = [];
    };

    function cancelChanges() {

    };

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
        console.log(area);
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

    tablesVm.initInteractable = initInteractable;

    function initInteractable(){
        // Define how should tables behave
        tablesVm.interactObject = interact('.restaurant-table')
            .draggable({
                // enable inertial throwing
                inertia: false,
                // keep the element within the area of it's parent
                restrict: {
                    restriction: 'parent',
                    endOnly: true,
                    elementRect: { top: 0, left: 0, bottom: 1, right: 1 }
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
            .on('resizemove', resizeListener)
            .on('tap', function (event) {
                event.currentTarget.classList.toggle('switch-bg');
                event.preventDefault();
            })
            .on('doubletap', removeListener);

            tablesVm.canvas = interact('.restaurant-canvas')
                .on('hold', function(event){

                    tablesVm.newTable = {
                        tableId: null,
                        datax: event.layerX - 90,
                        datay: event.layerY - 140,
                        width: 40,
                        height: 40,
                        positions: 2,
                        tempId: 10000 + tablesVm.tables.length,
                        isNew: true
                    };

                    tablesVm.tables.push(tablesVm.newTable);
                    var parent = document.getElementById('canvas');
                    var test =  document.createElement("p");
                    parent.appendChild(test);
                    parent.removeChild(test);

                } );
    };
    tablesVm.initInteractable();
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

        var currentTable = tablesVm.findTable(target.getAttribute('id'));
        currentTable.datax = x;
        currentTable.datay = y;

    };

    // Function that saves current size of element after resizing
    function resizeListener(event) {
        var target = event.target;
        var x = (parseFloat(target.getAttribute('data-x')) || 0);
        var y = (parseFloat(target.getAttribute('data-y')) || 0);
        var currentTable = tablesVm.findTable(target.getAttribute('id'));
        console.log(currentTable);
        // update the element's style
        target.style.width  = event.rect.width + 'px';
        target.style.height = event.rect.height + 'px';

        // update model
        currentTable.height = event.rect.height;
        currentTable.width =  event.rect.width;

        // translate when resizing from top or left edges
        x += event.deltaRect.left;
        y += event.deltaRect.top;

        target.style.webkitTransform =
        target.style.transform = 'translate(' + x + 'px,' + y + 'px)';

        target.setAttribute('data-x', x);
        target.setAttribute('data-y', y);

        currentTable.datax = x;
        currentTable.datay = y;

        // TODO calculatePeople
        target.textContent = 'n:' + 'br'
    };

    function removeListener(event) {
        var target = event.target;
        console.log(target);
        var id = parseInt(target.getAttribute('id'));
        var toRemove = -1;
        for (var i = 0; i < tablesVm.tables.length; i++) {
            if(tablesVm.tables[i].hasOwnProperty('isNew')){
                console.log('new');
                if(tablesVm.tables[i].tempId == id){
                    toRemove = i;
                    break;
                }
            }else{
                if(tablesVm.tables[i].tableId == id){
                    toRemove = i;
                    break;
                };
            };

        };
        console.log('toRemove' + toRemove);
        // add to deleted tables only if table already exists in database
        if(tablesVm.tables[toRemove].hasOwnProperty('isNew') === false){
            tablesVm.deletedTables.push(angular.copy(tablesVm.tables[toRemove]));
        };

        var parent = document.getElementById("canvas");
        var child = document.getElementById(id);
        parent.removeChild(child);

        tablesVm.tables.splice(toRemove, 1);
    };

    // this is used on whole window when draggin or resizing
    window.dragMoveListener = dragMoveListener;
    window.resizeListener = resizeListener;

    //interact.maxInteractions(Infinity);

    function createTable(){
        tablesVm.newTable = {
            tableId: null,
            datax: 10,
            datay: -50,
            width: 40,
            height: 40,
            positions: 2
        };
        tablesVm.tables.push(tablesVm.newTable);
    };

    tablesVm.switchEdit = switchEdit;
    function switchEdit(){
        tablesVm.enabledEdit = !tablesVm.enabledEdit;
        if(tablesVm.enabledEdit){
            tablesVm.initInteractable();
        }else{
            tablesVm.interactObject.unset();
            tablesVm.canvas.unset();
        }

    }

    tablesVm.findTable = findTable;
    function findTable(table_id){
        for (var i = 0; i < tablesVm.tables.length; i++) {
            if ( tablesVm.tables[i].hasOwnProperty('isNew')) {
                if(tablesVm.tables[i].tempId == table_id){
                    return tablesVm.tables[i];
                };
            }else{
                if(tablesVm.tables[i].tableId == table_id){
                    return tablesVm.tables[i];
                }
            };
        }
        return null;
    };


}
