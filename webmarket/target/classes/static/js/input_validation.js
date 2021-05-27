ymaps.ready(init);

function init() {
    var suggestView = new ymaps.SuggestView('suggest'),
        map,
        placemark;

    $('#button').bind('click', function (e) {
        geocode();
    });

    function geocode() {
        var request = $('#suggest').val();
        ymaps.geocode(request).then(function (res) {
            var obj = res.geoObjects.get(0),
                error, hint;
        }, function (e) {
            console.log(e)
        })

    }
    function showResult(obj) {
        $('#suggest').removeClass('input_error');
        $('#notice').css('display', 'none');

        var mapContainer = $('#map'),
            bounds = obj.properties.get('boundedBy'),
            mapState = ymaps.util.bounds.getCenterAndZoom(
                bounds,
                [mapContainer.width(), mapContainer.height()]
            ),
            address = [obj.getCountry(), obj.getAddressLine()].join(', '),
            shortAddress = [obj.getThoroughfare(), obj.getPremiseNumber(), obj.getPremise()].join(' ');
        mapState.controls = [];
        createMap(mapState, shortAddress);
        showMessage(address);
    }

    function showError(message) {
        $('#notice').text(message);
        $('#suggest').addClass('input_error');
        $('#notice').css('display', 'block');
        if (map) {
            map.destroy();
            map = null;
        }
    }

    function createMap(state, caption) {
        if (!map) {
            map = new ymaps.Map('map', state);
            placemark = new ymaps.Placemark(
                map.getCenter(), {
                    iconCaption: caption,
                    balloonContent: caption
                }, {
                    preset: 'islands#redDotIconWithCaption'
                });
            map.geoObjects.add(placemark);
        } else {
            map.setCenter(state.center, state.zoom);
            placemark.geometry.setCoordinates(state.center);
            placemark.properties.set({iconCaption: caption, balloonContent: caption});
        }
    }

    function showMessage(message) {
        $('#message').text(message);
    }
}