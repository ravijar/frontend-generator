<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const ${component.id}Fetch = async (requestBody) => {
${indent}    setLoading(true);
${indent}    try {
