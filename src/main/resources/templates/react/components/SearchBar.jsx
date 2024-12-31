import React, { useState } from 'react';
import './SearchBar.css';

const SearchBar = ({ placeholder = "Search...", onSearch }) => {
    const [searchQuery, setSearchQuery] = useState("");

    const handleInputChange = (event) => {
        setSearchQuery(event.target.value);
    };

    const handleSearch = (event) => {
        event.preventDefault();
        if (onSearch) {
            onSearch(searchQuery);
        }
    };

    return (
        <form className="search-bar" onSubmit={handleSearch}>
            <input
                type="text"
                className="search-input"
                value={searchQuery}
                onChange={handleInputChange}
                placeholder={placeholder}
            />
            <button type="submit" className="search-button">
                Search
            </button>
        </form>
    );
};

export default SearchBar;
