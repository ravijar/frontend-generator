<#assign indent = ""?left_pad(indent * 4)>
${indent}const ${value}HandleChange = (value) => {
${indent}    set${value?cap_first}(value);
${indent}    if (value.trim() === '') {
${indent}        set${value?cap_first}Error('This value is required');
${indent}    } else {
${indent}        set${value?cap_first}Error('');
${indent}    }
${indent}};

