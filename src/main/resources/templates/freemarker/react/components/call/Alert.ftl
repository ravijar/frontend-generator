<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
${indent}<div className="${component.resultComponent.styleId}-container">
${indent}    {${component.id}ShowAlert && (
${indent}        <Alert
${indent}           statusCode={${component.id}FetchResponse?.httpStatusCode}
${indent}           message={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.description}
${indent}           duration={4000}
${indent}           onClose={() => set${component.id?cap_first}ShowAlert(false)}
${indent}           styles={styles.${component.id}.alert}
${indent}        />
${indent}    )}
${indent}</div>
        <#break>
</#switch>