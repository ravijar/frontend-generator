<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-result-container">
${indent}    {${component.id}ShowAlert && (
${indent}        <Alert
${indent}           statusCode={${component.id}AlertData.code}
${indent}           message={${component.id}AlertData.message}
${indent}           duration={4000}
${indent}           onClose={() => set${component.id?cap_first}ShowAlert(false)}
${indent}           styles={styles.${component.id}.alert}
${indent}        />
${indent}    )}
${indent}</div>
