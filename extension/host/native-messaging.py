import struct
import sys
import threading
import ctypes
import psutil
import time

def read_thread_func():
    while True:
        text_length_bytes = sys.stdin.buffer.read(4)

        if len(text_length_bytes) == 0:
            sys.exit(0)

        text_length = struct.unpack('@I', text_length_bytes)[0]

        text = sys.stdin.buffer.read(text_length).decode('utf-8')

def send_message(prevAppName, nowAppName):
    text = '{"prevAppName": "' + str(prevAppName) + '", "nowAppName": "' + str(nowAppName) + '"}'
    sys.stdout.buffer.write(struct.pack('I', len(text)))
    sys.stdout.buffer.write(text.encode('utf-8'))
    sys.stdout.flush()

def get_active_window_process_info():
    user32 = ctypes.windll.user32
    w_handle = user32.GetForegroundWindow()
    pid = ctypes.c_ulong()
    user32.GetWindowThreadProcessId(w_handle, ctypes.byref(pid))
    process_id = pid.value
    process = psutil.Process(process_id)
    process_name = process.name()
    return process_name

def main():
    thread = threading.Thread(target=read_thread_func)
    thread.daemon = True
    thread.start()

    last_process_name = None

    while True:
        current_process_name = get_active_window_process_info()
        if current_process_name != last_process_name:
            send_message(last_process_name, current_process_name)
            last_process_name = current_process_name
        time.sleep(0.5)

if __name__ == '__main__':
    main()
