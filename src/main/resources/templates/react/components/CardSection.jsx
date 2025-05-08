import "./CardSection.css";
import NoContent from "./NoContent";
import { useEffect, useState } from "react";
import Select from "react-select";

const CardSection = ({ data, responseSchema, onClick, mapModel, title, displayNames = {}, styles = {} }) => {
    const [filteredData, setFilteredData] = useState(data);
    const [selectedField, setSelectedField] = useState("");
    const [searchValue, setSearchValue] = useState("");

    const hasData = Array.isArray(data) && data.length > 0 && typeof data[0] === "object";
    const columns = hasData ? Object.keys(data[0]) : [];

    useEffect(() => {
        let newFilteredData = data;

        if (selectedField && searchValue) {
            newFilteredData = newFilteredData.filter((row) => {
                const value = row[selectedField];
                if (typeof value === "string" || typeof value === "number") {
                    return value.toString().toLowerCase().includes(searchValue.toLowerCase());
                }
                return false;
            });
        }

        setFilteredData(newFilteredData);
    }, [selectedField, searchValue, data]);

    const fieldOptions = columns.map((col) => ({
        value: col,
        label: displayNames?.[col] || col,
    }));

    if (!Array.isArray(filteredData) || filteredData.length === 0) {
        return (
            <div className="card-array-container" style={styles.cardArrayContainer}>
                <NoContent />
            </div>
        );
    }

    const mappedData = mapModel(filteredData, responseSchema);

    return (
        <>
            <div className="card-section-header">
                <h2 className="card-section-title">{title}</h2>

                <div className="filter-section">
                    <Select
                        options={fieldOptions}
                        value={selectedField ? fieldOptions.find((option) => option.value === selectedField) : null}
                        onChange={(option) => setSelectedField(option?.value || "")}
                        placeholder="Select a field"
                        className="react-select"
                    />
                    <input
                        type="text"
                        placeholder="Search value..."
                        value={searchValue}
                        onChange={(e) => setSearchValue(e.target.value)}
                        className="filter-input"
                    />
                </div>
            </div>
            <div className="card-array-container" style={styles.cardArrayContainer}>
                {mappedData.map((item) => (
                    <div
                        key={item.key}
                        className="card"
                        onClick={() => onClick(item.key)}
                        style={styles.card}
                    >
                        {item.image && (
                            <div
                                className="card-img"
                                style={{
                                    backgroundImage: `url(${item.image})`,
                                    ...styles.cardImg,
                                }}
                            ></div>
                        )}
                        <div className="card-info" style={styles.cardInfo}>
                            <p className="text-title" style={styles.textTitle}>{item.title}</p>
                            {item.description && (
                                <p className="text-body" style={styles.textBody}>{item.description}</p>
                            )}
                            {item.highlight && (
                                <div className="highlight-text" style={styles.highlightText}>
                                    {item.highlight}
                                </div>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
};

export default CardSection;
