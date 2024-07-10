import InputField from "../components/InputField";
import {useState} from "react";

export default function ${pageName?cap_first}() {
    const [${fieldName}, set${fieldName?cap_first}] = useState("");
    const [${fieldName}Error, set${fieldName?cap_first}Error] = useState("")

    const handle${fieldName?cap_first}Change = (value) => {
        set${fieldName?cap_first}(value);
        if (value.trim() === '') {
            set${fieldName?cap_first}Error('This field is required');
        } else {
            set${fieldName?cap_first}Error('');
        }
    };

    return (
        <div>
            <InputField
                label={"${fieldName?cap_first}"}
                value={${fieldName}}
                onChange={handle${fieldName?cap_first}Change}
                placeholder={"Enter ${fieldName?cap_first}"}
                required={true}
                error={${fieldName}Error}
            />
        </div>
    );
}