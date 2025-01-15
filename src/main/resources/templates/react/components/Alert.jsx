import React, { useEffect, useState } from "react";
import "./Alert.css";

export default function Alert({ statusCode, message, onClose, duration = 5000, styles = {} }) {
    const [progress, setProgress] = useState(100);

    const getAlertType = () => {
        if (statusCode >= 200 && statusCode < 300) {
            return "success";
        }
        return "error";
    };

    const type = getAlertType();

    useEffect(() => {
        const interval = duration / 100;
        const timer = setInterval(() => {
            setProgress((prev) => Math.max(prev - 1, 0));
        }, interval);

        const timeout = setTimeout(() => {
            if (onClose) {
                onClose();
            }
        }, duration);

        return () => {
            clearInterval(timer);
            clearTimeout(timeout);
        };
    }, [duration, onClose]);

    const getAlertClassName = () => {
        switch (type) {
            case "success":
                return "alert alert-success";
            case "error":
                return "alert alert-error";
            default:
                return "alert";
        }
    };

    const getCustomAlertClassName = () => {
        switch (type) {
            case "success":
                return { ...styles.alert, ...styles.alertSuccess };
            case "error":
                return { ...styles.alert, ...styles.alertError };
            default:
                return { ...styles.alert }
        }
    }

    return (
        <div className={getAlertClassName()} style={getCustomAlertClassName()}>
            <div className="alert-content" style={styles.alertContent}>
                {statusCode && (
                    <div className="alert-status-code" style={styles.alertStatusCode}>
                        {statusCode}
                    </div>
                )}
                <div className="alert-message" style={styles.alertMessage}>
                    {message}
                </div>
                {onClose && (
                    <button className="alert-close-button" onClick={onClose} style={styles.alertCloseButton}>
                        &times;
                    </button>
                )}
            </div>
            <div className="alert-progress-bar" style={{ width: `${progress}%`, ...styles.alertProgressBar }}></div>
        </div>
    );
}
