import "./Table.css";
import { Children, cloneElement, isValidElement } from "react";
import NoContent from "./NoContent";

export default function Table({ data = [], rowKey, displayNames = {}, styles = {}, children }) {
    const childComponents = Array.isArray(children) ? children : [children];
    const hasData = Array.isArray(data) && data.length > 0 && typeof data[0] === "object";
    const columns = hasData ? Object.keys(data[0]) : [];

    if (!hasData || columns.length === 0) {
        return (
            <div className="table-container" style={styles.tableContainer}>
                <NoContent />
            </div>
        );
    }

    return (
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
                {data.map((row, index) => (
                    <tr key={index}>
                        {columns.map((col) => {
                            const value = row[col];

                            let displayValue;
                            if (Array.isArray(value)) {
                                displayValue = value.map((item, i) => <div key={i}>{item}</div>);
                            } else if (typeof value === "object" && value !== null) {
                                displayValue = Object.entries(value).map(([k, v], i) => (
                                    <div key={i}>
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
    );
}
