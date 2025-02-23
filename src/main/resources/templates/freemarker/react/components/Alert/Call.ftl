<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}    {${component.parent.id}ShowAlert && (
${indent}        <Alert
${indent}           statusCode={${component.parent.id}FetchResponse?.httpStatusCode}
${indent}           message={${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.description}
${indent}           duration={4000}
${indent}           onClose={() => set${component.parent.id?cap_first}ShowAlert(false)}
${indent}           styles={styles.${component.id}}
${indent}        />
${indent}    )}
${indent}</div>
        <#break>
</#switch>