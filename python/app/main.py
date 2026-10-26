import os
import time
import sys

from fastapi import FastAPI, Response

app = FastAPI()

ready = True


@app.get("/")
def root():
    return {
        "application": "python-deployment-lab",
        "version": os.getenv("APP_VERSION", "v1"),
        "hostname": os.getenv("HOSTNAME")
    }


@app.get("/health")
def health():
    return {
        "status": "healthy"
    }


@app.get("/ready")
def readiness(response: Response):

    if not ready:
        response.status_code = 503

        return {
            "status": "not-ready"
        }

    return {
        "status": "ready"
    }


@app.get("/not-ready")
def not_ready():

    global ready
    ready = False

    return {
        "message": "Application changed to not-ready"
    }


@app.get("/ready-again")
def ready_again():

    global ready
    ready = True

    return {
        "message": "Application ready again"
    }


@app.get("/slow")
def slow():

    time.sleep(10)

    return {
        "message": "Slow request completed"
    }


@app.get("/crash")
def crash():

    os._exit(1)