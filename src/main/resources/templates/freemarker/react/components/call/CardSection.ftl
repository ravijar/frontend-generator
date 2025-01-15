<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-result-container">
${indent}    {${component.id}Fetched && (
${indent}       <CardSection
${indent}           responseData={${component.id}FetchResponse?.data}
${indent}           responseSchema={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.responseSchema}
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}           styles={styles.${component.id}.cardSection}
${indent}       >
    <#if component.resultComponent.subComponents??>
        <#assign indentValue = indentValue + 3>
        <#list component.resultComponent.subComponents as component>
            <#switch component.type>
                <#case "Button">
                    <#include buttonCall>
                    <#break>
            </#switch>
        </#list>
        <#assign indentValue = indentValue - 3>
        <#assign indent = ""?left_pad(indentValue * 4)>
    </#if>
${indent}       </CardSection>
${indent}    )}
${indent}</div>
