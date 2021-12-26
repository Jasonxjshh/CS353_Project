import random
import re
import time
import uuid

import board
import adafruit_scd30
import pika
import json
from datetime import datetime
import requests


def getPosition(address):
    # AK = 'XpCot88EYUQGBgR8yGY4RPXuyaLfXw1M'
    # address = address
    # url = 'http://api.map.b/geocoding/v3/?address={}&output=json&ak={}&callback=showLocation'.format(address,AK)
    # res = requests.get(url)
    # print(res.text)
    # results = json.loads(re.findall(r'\((.*?)\)', res.text)[0])
    # lng = format(float(results['result']['location']['lng']), '.4f')
    # lat = format(float(results['result']['location']['lat']), '.4f')
    position = [119.1959, 26.0560]
    return position


def process_data(data):
    # split the data string of sensor data received to fit in the json message format
    serial, date_time, co2, temperature, humidity, sensor_name, address = data.split(",")
    lng, lat = getPosition(address)
    # https://www.cleancss.com/python-beautify/ Python code formatter
    data_json = {
        # make a random UUID using uuid4() function
        # .node is to get the time component of uuid simply for uuid is too long for this column in SQL database
        # read more: https://www.geeksforgeeks.org/generating-random-ids-using-uuid-python/
        "json_message_ID": str(uuid.uuid4().node),
        "processor_code": serial,
        "processor_shortname": serial[8:16],
        "processor_model": " ",
        "sensor_name": sensor_name,
        "capture_type": "AMBIENT_AIR",
        "data_capture_event": "xxxx_xxxxxxxx_xx_x",
        "capture_date_time": date_time,
        "type": "FILTER_READING",
        "rules-version": "1.00",
        "event_index": random.randint(1, 3),
        "sensate_name": "CO2",
        "ppm": co2,
        "celcius": 1,
        "temperature": temperature,
        "humidity": humidity,
        "filtername": "CRI_Filter",
        "longitude": lng,
        "latitude": lat,
        "hs_model": "",
        "hs_manufacturer": "",
        "hs_description": "",
        "fuel_name": "",
        "hst_description": "",
        "fu_filter_name": "",
        "fu_filter_model": "",
        "c_RFID": "",
        "c_installed": date_time,
        "ct_description": "",
        "status": "200",
        "ssma_timestamp": date_time
    }
    print()
    print("Save the following JSON object to a file")
    print(data_json)
    return data_json

    # # #append to file with valid json objects, but cannot parse directly (no "," separated or "[]" wrapped)
    # # with open (os.path.expanduser('~/Desktop/CRI-CS_Summer_Internship/prototype-program/sensor_json_notformatted.txt'), 'a', encoding='utf-8') as f:
    # #     #use json.dump() not json.dumps() here, json.dump() writes to a file or file-like object, whereas json.dumps() returns a string
    # #     json.dump(data_json, f, ensure_ascii=False, indent=4, sort_keys=True, separators=(',', ': '))
    # #     #f.write(",")
    #
    # #append to file with json arrays, reformat json file later in odbconnect.py
    # with open (os.path.expanduser('~/Desktop/CRI-CS_Summer_Internship/prototype-program/sensor_json_formatted.txt'), 'a', encoding='utf-8') as outfile:
    #     output = []
    #     output.append(data_json)
    #     json.dump(output, outfile)


def getserial():
    # Extract serial from cpuinfo file
    cpuserial = "0000000000000000"
    try:
        f = open('/proc/cpuinfo', 'r')
        for line in f:
            if line[0:6] == 'Serial':
                cpuserial = line[10:26]
        f.close()
    except:
        cpuserial = "ERROR000000000"
    return cpuserial


def sensor_data():
    global sensor
    i2c = board.I2C()  # uses board.SCL and board.SDA
    scd = adafruit_scd30.SCD30(i2c)
    if scd.data_available:
        print("Data Available")
        eco2 = format(scd.CO2, '.2f')
        temperature = format(scd.temperature, '.2f')
        humidity = format(scd.relative_humidity, '.2f')
        now = datetime.now()
        date_time = now.strftime("%Y-%m-%d %H:%M:%S")
        serial = getserial()
        sensor_name = "SCD30_Jason"
        address = "福州大学"
        sensor = '{},{},{},{},{},{},{}'.format(serial, date_time, eco2, temperature, humidity, sensor_name, address)
        print(sensor)
    return sensor


credentials = pika.PlainCredentials("Jason", "123456")
connection = pika.BlockingConnection(pika.ConnectionParameters("121.199.73.186", 5672, '/Jason', credentials))
channel = connection.channel()
channel.queue_declare("sensorData", durable=True)


def my_client():
    while True:
        receive = "Data"
        if receive == "Data":
            print("Send Data")
            data = sensor_data()
            msg = str(process_data(data))
            channel.basic_publish(exchange="",
                                  routing_key="sensorData",
                                  body=msg)
            print("=====" + msg + "=====")
            time.sleep(2)
        elif receive == "Kill":
            print("Shutting down server...")
            break
        elif receive == "Exit":
            print("Client side has disconnected.")
            channel.close()
            break
        else:
            continue


if __name__ == '__main__':
    while 1:
        my_client()
