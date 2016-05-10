angular
    .module('isa-mrs-project')
    .controller('TablesController', TablesController);

TablesController.$inject = ['tableService', 'regionService', '$mdDialog', '$mdToast'];

function TablesController(tableService, regionService, $mdDialog, $mdToast) {
    var tablesVm = this;
    // switch of edit or no-edit state
    tablesVm.enabledEdit = false;
    // table containers
    tablesVm.tables = [];
    tablesVm.deletedTables = [];
    tablesVm.backup = [];

    // objects of interaction (canvas and all its object separated)
    tablesVm.interactObject = null;
    tablesVm.interactCanvas = null;

    // processing state changes
    tablesVm.saveChanges = saveChanges;
    tablesVm.cancelChanges = cancelChanges;

    // saves current layout, used for backup
    tablesVm.saveCurrentState = saveCurrentState;

    // calculates number of people for table
    tablesVm.tableSizeHeuristic = tableSizeHeuristic;

    // enable interaction with elements
    tablesVm.startInteraction = startInteraction;

    // toogle editState attribute true/false and do appropriate actions
    tablesVm.switchCanvasState = switchCanvasState;

    // returns table with provided ID
    tablesVm.findTable = findTable;

    // feedback to user
    tablesVm.showInfo = showInfo;
    tablesVm.showToast = showToast

    tablesVm.tableColors = ['default', 'first-color', 'second-color' , 'third-color',
                            'fourth-color', 'fifth-color', 'sixth-color', 'seventh-color',
                            'eighth-color', 'ninth-color'];
    tablesVm.hexColors = [  '#283593', '#00695C', '#CDDC39', '#AD1457', '#0277BD',
                            '#5D4037', '#607D8B', '#E91E63', '#43A047', '#76FF03'];
    tablesVm.regionCount = 1;
    tablesVm.regions = [];
    tablesVm.regionEditFields = [];
    tablesVm.addNewRegion = addNewRegion;
    tablesVm.backupRegion = null;
    tablesVm.saveRegion = saveRegion;
    tablesVm.deleteRegion = deleteRegion;
    tablesVm.getNextRestaurantTableNo = getNextRestaurantTableNo;
    tablesVm.tablesInRestaurantNumbers = [];
    activate();

    function activate() {
        getTablesByRestaurant().then(function() {
            // after success action
        });


        getRegionsByRestaurant();
        tablesVm.regionCount = tablesVm.regions.length;


        for (var i = 0; i < tablesVm.regions.length; i++) {
            tablesVm.regionEditFields[i] = false;
        }
    };

    function getTablesByRestaurant() {
        // TODO currently locked on 2
        // find way to access to logged user
        return tableService.getTablesByRestaurant(2)
            .then(function(data) {
                tablesVm.tables = data;

                for (var i = 0; i < tablesVm.tables.length; i++) {
                    tablesVm.tablesInRestaurantNumbers.push(tablesVm.tables[i].tableInRestaurantNo);
                };
                tablesVm.tablesInRestaurantNumbers.sort();
                console.log(tablesVm.tablesInRestaurantNumbers);
                return tablesVm.tables;
            });
    };

    function getRegionsByRestaurant() {
        // TODO currently locked on 2
        // find way to access to logged user
        return regionService.getRegionsByRestaurant(2)
            .then(function(data) {
                tablesVm.regions = data;
                tablesVm.regionCount = tablesVm.regions.length;
            })
    }

    function showInfo(ev){
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('#upper')))
                .clickOutsideToClose(true)
                .title('Uputstvo za korišćenje')
                .textContent(
                    'Za dodavanje novog stola potrebno je zadržati klik na praznoj površin dok se ne pojavi sto '
                +   'Za uklanjanje postojećeg stola potrebno je uraditi dupli klik na njegovoj površini. '
                +   'Za promenu regiona stola potrebno je kliknuti na sto i boja će mu se promeniti. '
                +   'Promenom veličine stola menja se i broj ljudi koji mogu da sednu za njega. '
                +   'Promena veličine omogućena je u levoj i donjoj ivici stola, prevlačenjem miša do željene pozicije. '
                +   'Promena pozicije stola moguća je prostim prevlačenjem stola na drugu poziciju. '
                +   'Sve promene se čuvaju tek nakon klika na dugme Sačuvaj. Ukoliko se klikne na dugme Poništi, sve '
                +   'promene će biti poništene i raspored će biti vraćen u pređašnje stanje.')
                .ariaLabel('Uputstvo za korišćenje')
                .ok('OK')
                .targetEvent(ev)
            );
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'bottom left',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function saveChanges() {
        // TODO Region currently locked onto 1
        for (var i = 0; i < tablesVm.tables.length; i++) {
            // create new tables
            if (tablesVm.tables[i].hasOwnProperty('isNew')) {
                tableService.createTable(angular.toJson(tablesVm.tables[i]), tablesVm.tables[i].region.regionId);
            }else{ // update existing tables
                tableService.updateTable(angular.toJson(tablesVm.tables[i]), tablesVm.tables[i].region.regionId);
            };
        };

        // remove deleted tables from database
        for (var i = 0; i < tablesVm.deletedTables.length; i++) {
            tableService.deleteTable(tablesVm.deletedTables[i].tableId);
        };
        // reset list for next time
        tablesVm.deletedTables = [];
        tablesVm.switchCanvasState();
        tablesVm.showToast('Raspored stolova je sačuvan.')
    };

    function cancelChanges() {
        tablesVm.tables = tablesVm.backup;
        tablesVm.backup = [];
        tablesVm.deletedTables = [];
        tablesVm.switchCanvasState();
        tablesVm.showToast('Sve promene su poništene.')
    };

    function saveCurrentState(){
        tablesVm.backup = angular.copy(tablesVm.tables);
        tablesVm.deletedTables = [];
    };

    function tableSizeHeuristic(x, y){
        // TODO This is example of function, should be way smarter
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

    function startInteraction(){
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
            .on('tap', colorSwitchListener)
            .on('doubletap', removeListener);

            tablesVm.interactCanvas = interact('.restaurant-canvas')
                .on('hold', addTableListener);
    };

    function getNextRestaurantTableNo(){
        var nextTableNo = -1;
        for (var i = 0; i < tablesVm.tablesInRestaurantNumbers.length; i++) {
            if(tablesVm.tablesInRestaurantNumbers[i] != (i+1)){
                nextTableNo = i+1;
                break;
            };
        }
        if(nextTableNo == -1){
            nextTableNo = tablesVm.tablesInRestaurantNumbers.length + 1;
        };


        return nextTableNo;
    }

    function addTableListener(event) {
        tablesVm.newTable = {
            tableId: null,
            datax: event.layerX - 90,
            datay: event.layerY - 140,
            width: 40,
            height: 40,
            positions: 2,
            tempId: 10000 + tablesVm.tables.length,
            isNew: true,
            region_color: 'green',
            region: tablesVm.regions[0],
            tableInRestaurantNo: tablesVm.getNextRestaurantTableNo()
        };

        tablesVm.tables.push(tablesVm.newTable);
        tablesVm.tablesInRestaurantNumbers.push(tablesVm.newTable.tableInRestaurantNo);
        tablesVm.tablesInRestaurantNumbers.sort();
        console.log(tablesVm.tablesInRestaurantNumbers);
        var parent = document.getElementById('canvas');
        var test =  document.createElement("p");
        parent.appendChild(test);
        parent.removeChild(test);
    };

    function colorSwitchListener(event) {
        var to_disable = null;
        var index = -1;
        var current = null;
        var stylename = null;
        for (var j = 0; j < tablesVm.tableColors.length; j++) {
            stylename = tablesVm.tableColors[j];
            index = index + 1;
            for (var i = 0; i < event.currentTarget.classList.length; i++) {
                current = event.currentTarget.classList[i];
                if (stylename == current){
                    to_disable = current;
                    break;
                }
            }
            if(to_disable != null){
                break;
            }
        }
        event.currentTarget.classList.toggle(to_disable);
        var next = (index+1)%(tablesVm.regionCount);
        console.log(next);
        event.currentTarget.classList.toggle(tablesVm.tableColors[next]);
        var currentTable = tablesVm.findTable(event.currentTarget.getAttribute('id'));
        console.log(currentTable);
        currentTable.region = tablesVm.regions[next];
        event.preventDefault();
    };

    // Function that saves current positon of the element after dragging
    function dragMoveListener(event) {
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

        // TODO tableSizeHeuristic
        //target.textContent = 'n:' + 'br'
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
    // TODO maybe this should be restricted only to canvas
    window.dragMoveListener = dragMoveListener;
    window.resizeListener = resizeListener;
    window.colorSwitchListener = colorSwitchListener;
    window.removeListener = removeListener;
    window.addTableListener = addTableListener;
    //interact.maxInteractions(Infinity);

    function switchCanvasState() {
        tablesVm.enabledEdit = !tablesVm.enabledEdit;
        if(tablesVm.enabledEdit) {
            tablesVm.startInteraction();
            tablesVm.saveCurrentState();
            document.getElementById('canvas').classList.toggle('edit-bg');
        }else {
            tablesVm.interactObject.unset();
            tablesVm.interactCanvas.unset();
            document.getElementById('canvas').classList.toggle('edit-bg');
        }

    }

    function findTable(table_id) {
        for (var i = 0; i < tablesVm.tables.length; i++) {
            if ( tablesVm.tables[i].hasOwnProperty('isNew')) {
                // use .tempId
                if(tablesVm.tables[i].tempId == table_id) {
                    return tablesVm.tables[i];
                };
            }else {
                // use standard .tableId
                if(tablesVm.tables[i].tableId == table_id) {
                    return tablesVm.tables[i];
                };
            };
        };
        return null;
    };

    function addNewRegion() {
        var nextFreeNo = -1;
        for (var i = 0; i < tablesVm.regions.length; i++) {
            if(tablesVm.regions[i].regionNo != (i+1) ){
                nextFreeNo = i+1;
                break;
            };
        };

        if(nextFreeNo == -1) {
            if(tablesVm.regions.length < 10){
                nextFreeNo = tablesVm.regions.length + 1;
            };
        };

        if(nextFreeNo == -1){
            // TODO Show alert
            alert("No free region slots");
            return;
        }
        var newRegion = {
            regionId: null,
            name: "novi_region",
            regionNo: nextFreeNo,
            color: tablesVm.hexColors[nextFreeNo-1]
        };
        tablesVm.regions.push(newRegion);
        tablesVm.regionCount = tablesVm.regionCount + 1;
        regionService.createRegion(newRegion, 2).then(
            function(data){
                alert("Saved region.");
                console.log(data);
            }
        );
    };

    function saveRegion(idx) {
        // TODO get real restaurant NO
        regionService.updateRegion(tablesVm.regions[idx], 2)
            .then(function(data){
                console.log(data);

            });
        tablesVm.regionEditFields[idx] = false;
    };

    function deleteRegion() {
        tablesVm.regionEditFields[idx] = false;
        //tablesVm.regionEditFields.splice(idx, 1);

    }
}
