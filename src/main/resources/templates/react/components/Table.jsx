import "./Table.css";

export default function Table({ columns, data, displayNames }) {
    return (
        <div className="table-container">
            <table className="custom-table">
                <thead>
                <tr>
                    {columns.map((col) => (
                        <th key={col}>{displayNames[col]}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {data.length === 0 ? (
                    <tr>
                        <td colSpan={columns.length} className="no-data">
                            No data available
                        </td>
                    </tr>
                ) : (
                    data.map((row, index) => (
                        <tr key={index}>
                            {columns.map((col) => (
                                <td key={col}>{row[col] !== undefined ? row[col] : "-"}</td>
                            ))}
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    );
}
