<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "root">
${indent}<div className="${component.styleId}-container">
${indent}   <HeroSection
${indent}       backgroundImage="${component.image}"
${indent}       text="${component.text}"
${indent}       subText="${component.subtext}"
${indent}       styles={styles.${component.id}}
${indent}   >
            <#include nestCall>
${indent}   </HeroSection>
${indent}</div>
        <#break>
</#switch>
