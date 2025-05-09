import React from "react";
import "./LoadingModal.css";

export default function LoadingModal({ show }) {
    if (!show) return null;

    return (
        <div className="loading-modal-overlay">
            <div className="loading-spinner"></div>
        </div>
    );
}
