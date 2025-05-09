import "./Table.css";
import { Children, cloneElement, isValidElement, useState, useEffect } from "react";
import Select from "react-select";
import NoContent from "./NoContent";
import "./Table.css";

export default function Table({ title, data = [], rowKey, displayNames = {}, styles = {}, children }) {
    const [filteredData, setFilteredData] = useState(data);
    const [selectedField, setSelectedField] = useState("");
    const [searchValue, setSearchValue] = useState("");

    const childComponents = Array.isArray(children) ? children : [children];
    const hasData = Array.isArray(data) && data.length > 0 && typeof data[0] === "object";
    const columns = hasData ? Object.keys(data[0]) : [];

    useEffect(() => {
        if (selectedField && searchValue) {
            const newFilteredData = data.filter((row) => {
                const value = row[selectedField];
                if (typeof value === "string" || typeof value === "number") {
                    return value.toString().toLowerCase().includes(searchValue.toLowerCase());
                }
                return false;
            });
            setFilteredData(newFilteredData);
        } else {
            setFilteredData(data);
        }
    }, [selectedField, searchValue, data]);

    if (!hasData || columns.length === 0) {
        return (
            <div className="table-container" style={styles.tableContainer}>
                <NoContent />
            </div>
        );
    }

    const fieldOptions = columns.map((col) => ({
        value: col,
        label: displayNames?.[col] || col,
    }));

    return (
        <>
            <div className="table-header">
                <h2 className="table-title">{title}</h2>

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

            <div className="table-container" style={styles.tableContainer}>
                <table className="custom-table" style={styles.customTable}>
                    <thead>
                    <tr>
                        {columns.map((col) => (
                            <th key={col}>{displayNames?.[col] || col}</th>
                        ))}
                        {childComponents.map((_, index) => (
                            <th key={`action-${index}`}></th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {filteredData.map((row, index) => (
                        <tr key={index}>
                            {columns.map((col) => {
                                const value = row[col];
                                let displayValue;
                                if (Array.isArray(value)) {
                                    displayValue = value.map((item, i) => (
                                        <div className="table-data-list" key={i} title={item}>
                                            {item}
                                        </div>
                                    ));
                                } else if (typeof value === "object" && value !== null) {
                                    displayValue = Object.entries(value).map(([k, v], i) => (
                                        <div className="table-data-list" key={i} title={`${k} - ${v}`}>
                                            {k}: {String(v)}
                                        </div>
                                    ));
                                } else {
                                    displayValue = value !== undefined ? String(value) : "-";
                                }

                                return (
                                    <td key={col} title={Array.isArray(value) || typeof value === "object" ? undefined : String(value)}>
                                        {displayValue}
                                    </td>
                                );
                            })}

                            {childComponents.map((child, childIndex) => (
                                <td key={`child-${childIndex}`}>
                                    {isValidElement(child)
                                        ? cloneElement(child, {
                                            children: Children.map(child.props.children, (nestedChild) =>
                                                isValidElement(nestedChild)
                                                    ? cloneElement(nestedChild, { id: row[rowKey] })
                                                    : nestedChild
                                            ),
                                        })
                                        : child}
                                </td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </>
    );
}
