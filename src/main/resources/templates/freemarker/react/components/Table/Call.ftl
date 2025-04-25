<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
        <#switch component.parent.action>
            <#case "resource">
${indent}    {${component.parent.id}Fetched && ${component.parent.id}FetchResponse.data[0] && (
${indent}       <Table
${indent}           styles={styles.${component.id}}
${indent}           columns={Object.keys(${component.parent.id}FetchResponse.data[0])}
${indent}           data={${component.parent.id}FetchResponse.data}
${indent}           rowKey="${component.rowKey}"
${indent}           displayNames={${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}       >
                <#include nestCall>
${indent}       </Table>
${indent}    )}
                <#break>
            <#case "load">
${indent}    {load${component.parent.localStorageKey?cap_first}()[0] && (
${indent}       <Table
${indent}           styles={styles.${component.id}}
${indent}           columns={Object.keys(load${component.parent.localStorageKey?cap_first}()[0])}
${indent}           data={${component.parent.id}LoadResponse}
${indent}           rowKey="${component.rowKey}"
${indent}       >
                <#include nestCall>
${indent}       </Table>
${indent}    )}
                <#break>
        </#switch>
${indent}</div>
        <#break>
</#switch>
