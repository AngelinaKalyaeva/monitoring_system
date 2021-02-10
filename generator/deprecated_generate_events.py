import time
import random
import threading

import requests

endpoints = ('200', '200', '200', '200', '200', '200', '200', '200', '200', '200', '200', '500')
bad_endpoints = ('500', '500')
good_endpoints = ('200', '200')


def run():
    timing = time.time()
    while True:
        try:
            if time.time() - timing <= 300.0:
                target = random.choice(endpoints)
            elif time.time() - timing <= 420.0:
                target = random.choice(bad_endpoints)
            else:
                target = random.choice(good_endpoints)
                requests.get("http://app:5000/payment_system_error?service_id=10&type=visa&status=%s" % target,
                             timeout=1)

            requests.get("http://app:5000/payment_system_error?service_id=10&type=visa&status=%s" % target, timeout=1)

        except:
            pass


if __name__ == '__main__':
    for _ in range(4):
        thread = threading.Thread(target=run)
        thread.setDaemon(True)
        thread.start()

    while True:
        time.sleep(1)