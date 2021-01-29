import time
import random

from flask import Flask, request
from prometheus_flask_exporter import PrometheusMetrics


app = Flask(__name__)
metrics = PrometheusMetrics(app, group_by='endpoint')


endpoints = ('one', 'two', 'three', 'four', 'five', 'error')


@app.route('/payment_system_error')
@metrics.counter(
    'payment_error', 'Payment information', labels={
        'service_id': lambda: request.args.get('service_id'),
        'status': lambda: request.args.get('status'),
        'type': lambda: request.args.get('type')
    })
def first_route():
    time.sleep(random.random() * 0.2)
    return request.args.get('status')


@app.route('/db_timing')
@metrics.counter(
    'db_timing', 'SLA timings', labels={
        'query': lambda: request.args.get('query'),
        'time': lambda: request.args.get('time'),
        'sla_time': lambda: request.args.get('sla_time'),
    })
def the_five():
    time.sleep(random.random() * 0.4)
    return 'ok'


@app.route('/db_error')
@metrics.counter(
    'db_error', 'SLA errors', labels={
        'query': lambda: request.args.get('query'),
        'time': lambda: request.args.get('time'),
        'code': lambda: request.args.get('code'),
    })
def test_six():
    time.sleep(random.random() * 0.6)
    return 'ok'


@app.route('/drop_url')
@metrics.counter(
    'drop_url', 'RPS drop', labels={
        'url': lambda: request.args.get('url')
    })
def seven_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/cpu')
@metrics.counter(
    'cpu', 'cpu', labels={
        'value': lambda: request.args.get('cpu'),
        'available': lambda: request.args.get('available')
    })
def eight():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/ram')
@metrics.counter(
    'ram', 'ram', labels={
        'value': lambda: request.args.get('cpu'),
        'available': lambda: request.args.get('available')
    })
def nine_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/net')
@metrics.counter(
    'net', 'net', labels={
        'value': lambda: request.args.get('cpu'),
        'available': lambda: request.args.get('available')
    })
def ten_one():
    time.sleep(random.random() * 0.8)
    return 'ok'


@app.route('/error')
def oops():
    return ':(', 500


if __name__ == '__main__':
    app.run('0.0.0.0', 5000, threaded=True)