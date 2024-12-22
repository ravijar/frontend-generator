import "./Alert.css";

export default function Alert({ type, statusCode, message, onClose }) {
    const getAlertClassName = () => {
        switch (type) {
            case 'success':
                return 'alert alert-success';
            case 'error':
                return 'alert alert-error';
            default:
                return 'alert';
        }
    };

    return (
        <div className={getAlertClassName()}>
            <div className="alert-content">
                {statusCode && (
                    <div className="alert-status-code">
                        {statusCode}
                    </div>
                )}
                <div className="alert-message">
                    {message}
                </div>
                {onClose && (
                    <button className="alert-close-button" onClick={onClose}>
                        &times;
                    </button>
                )}
            </div>
        </div>
    );
}
