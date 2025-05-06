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
                        {columns.map((col) => (
                            <td key={col}>
                                {Array.isArray(row[col]) ? (
                                    <div>
                                        {row[col].map((item, i) => (
                                            <div key={i}>{item}</div>
                                        ))}
                                    </div>
                                ) : typeof row[col] === "object" && row[col] !== null ? (
                                    <div>
                                        {Object.entries(row[col]).map(([k, v], i) => (
                                            <div key={i}>{k}: {String(v)}</div>
                                        ))}
                                    </div>
                                ) : row[col] !== undefined ? (
                                    row[col]
                                ) : (
                                    "-"
                                )}
                            </td>
                        ))}
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
