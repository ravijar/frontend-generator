import InputField from "../components/InputField";
import {useState} from "react";

export default function ${pageName?cap_first}() {
<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

<#list fields as field>
    const handle${field.name?cap_first}Change = (value) => {
        set${field.name?cap_first}(value);
        if (value.trim() === '') {
            set${field.name?cap_first}Error('This field is required');
        } else {
            set${field.name?cap_first}Error('');
        }
    };
</#list>

    return (
        <div>
        <#list fields as field>
            <InputField
                label={"${field.name?cap_first}"}
                value={${field.name}}
                onChange={handle${field.name?cap_first}Change}
                placeholder={"Enter ${field.name?cap_first}"}
                required={true}
                error={${field.name}Error}
            />
        </#list>
        </div>
    );
}
