<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${body.text.body}"
${indent}       onClick={${component.id}Navigate}
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>

