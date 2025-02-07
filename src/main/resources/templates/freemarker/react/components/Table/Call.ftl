<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}<div className="${component.resultComponent.styleId}-container">
${indent}    {${component.id}Fetched && (
${indent}       <Table
${indent}           styles={styles.${component.resultComponent.id}}
${indent}           columns={Object.keys(${component.id}FetchResponse.data[0])}
${indent}           data={${component.id}FetchResponse.data}
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}       />
${indent}    )}
${indent}</div>
        <#break>
</#switch>
