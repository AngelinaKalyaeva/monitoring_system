FROM python:3-alpine

ADD requirements.txt /tmp/requirements.txt

RUN pip install -r /tmp/requirements.txt

ADD deprecated_generate_events.py /var/app/generator.py

CMD python /var/app/generator.py