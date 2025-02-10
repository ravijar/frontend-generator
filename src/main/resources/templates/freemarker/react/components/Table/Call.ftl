<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}<div className="${component.resultComponent.styleId}-container">
        <#switch component.fetch>
            <#case "resource">
${indent}    {${component.id}Fetched && (
${indent}       <Table
${indent}           styles={styles.${component.resultComponent.id}}
${indent}           columns={Object.keys(${component.id}FetchResponse.data[0])}
${indent}           data={${component.id}FetchResponse.data}
${indent}           rowKey="${component.resultComponent.rowKey}"
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}       >
                <#assign component = component.resultComponent>
                <#include nestCall>
${indent}       </Table>
${indent}    )}
                <#break>
            <#case "load">
${indent}       <Table
${indent}           styles={styles.${component.resultComponent.id}}
${indent}           columns={Object.keys(load${component.loadKey?cap_first}()[0])}
${indent}           data={load${component.loadKey?cap_first}()}
${indent}           rowKey="${component.resultComponent.rowKey}"
${indent}       >
                <#assign component = component.resultComponent>
                <#include nestCall>
${indent}       </Table>
                <#break>
        </#switch>
${indent}</div>
        <#break>
</#switch>
