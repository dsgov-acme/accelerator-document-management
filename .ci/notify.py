import requests
import os
import sys

def usage():
    print(f"Example: {sys.argv[0]} \"<message>\"")

def send_slack_notification(webhook_url, message):
    payload = {
        "text": message,
    }
    requests.post(webhook_url, json=payload)

if __name__ == "__main__":
    webhook_url = os.environ.get("SLACK_WEBHOOK_URL")

    if len(sys.argv) > 1 and len(sys.argv) < 3 and webhook_url :
        message = sys.argv[1]
        send_slack_notification(webhook_url, message)
    elif not webhook_url :
        print("Missing slack webhook environment variable.")
        usage()
        sys.exit(1)
    elif len(sys.argv) < 2 :
        print("Missing argument for slack message.")
        usage()
        sys.exit(1)
    else:
        usage()
