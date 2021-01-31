import time
import random

from flask import Flask, request
from prometheus_client import Gauge
from prometheus_client import Counter
from werkzeug.middleware.dispatcher import DispatcherMiddleware
from prometheus_client import make_wsgi_app


app = Flask(__name__)
# metrics = PrometheusMetrics(app, group_by='endpoint')
url_time_g = Gauge('url_time', 'Description of gauge', ['service_id', 'url'])
sla_time_g = Gauge('sla_time', 'Description of gauge', ['service_id', 'url'])
payment_system_error_g = Counter('payment_error', 'Payment information', ['service_id', 'status', 'type'])
url_error_g = Counter('url_error', 'SLA error', ['service_id', 'url', 'code'])


@app.route('/payment_system_error')
def a():
    time.sleep(random.random() * 0.2)
    payment_system_error_g.labels(
        request.args.get('service_id'),
        request.args.get('status'),
        request.args.get('type')
    ).inc()
    return request.args.get('status')


@app.route('/url_error')
def b():
    time.sleep(random.random() * 0.4)
    url_error_g.labels(
        request.args.get('service_id'),
        request.args.get('url'),
        request.args.get('code')
    ).inc()
    return request.args.get('code')


@app.route('/sla_timing')
def c():
    time.sleep(random.random() * 0.4)
    sla_time_g.labels(
        request.args.get('service_id'),
        request.args.get('url')
    ).set(request.args.get('sla_time'))
    return request.args.get('sla_time')


@app.route('/url_timing')
def d():
    time.sleep(random.random() * 0.4)
    url_time_g.labels(
        request.args.get('service_id'),
        request.args.get('url')
    ).set(request.args.get('url_time'))
    return request.args.get('url_time')


@app.route('/db_timing')
def the_five():
    time.sleep(random.random() * 0.4)
    return 'ok'


@app.route('/db_error')
def test_six():
    time.sleep(random.random() * 0.6)
    return 'ok'


@app.route('/drop_url')
def seven_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/cpu')
def eight():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/ram')
def nine_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/net')
def ten_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/error')
def oops():
    return ':(', 500


if __name__ == '__main__':
    app.wsgi_app = DispatcherMiddleware(app.wsgi_app, {
        '/metrics': make_wsgi_app()
    })

    app.run('0.0.0.0', 5000, threaded=True)