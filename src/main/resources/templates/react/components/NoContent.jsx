import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBoxOpen } from '@fortawesome/free-solid-svg-icons';
import './NoContent.css';

export default function NoContent({ message = "Nothing to display here." }) {
    return (
        <div className="no-content">
            <FontAwesomeIcon icon={faBoxOpen} className="no-content-icon" />
            <p className="no-content-message">{message}</p>
        </div>
    );
}
