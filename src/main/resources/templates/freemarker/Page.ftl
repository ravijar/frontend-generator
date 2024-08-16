import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { DefaultApi } from "../client_api";
<#if httpMethod == "POST" || httpMethod == "PUT">
import ${requestSchema} from "../client_api/src/model/${requestSchema}";
</#if>
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";
import { getStyle, getDisplayName } from "../common/Utils"
<#if customStyled>
import styles from "../customStyles/${pageName?cap_first}Styles";
</#if>
import "./Page.css";

const displayNames = {
<#list displayNames as displayNameMap>
<#list displayNameMap?keys as key>
    ${key} : "${displayNameMap[key]}",
</#list>
</#list>
}

export default function ${pageName?cap_first}() {
    const navigate = useNavigate();
    const clientApi = new DefaultApi();
    let customStyles = {};
<#if customStyled>
    customStyles = styles;
</#if>

<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

<#if responseSchema.type == "null">
    const [responseData, setResponseData] = useState({});
<#elseif responseSchema.type == "array">
    const [responseData, setResponseData] = useState([]);
</#if>

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
<#list nextPages as nextPage>
    const onClick${nextPage.name} = () => {
        navigate("/${nextPage.name?uncap_first}");
    }

</#list>
    const handleSubmit = (event) => {
        event.preventDefault();
    <#if httpMethod == "POST" || httpMethod == "PUT">
        const body = ${requestSchema}.constructFromObject({
        <#list requestParams as param>
            ${param.name} : ${param.name},
        </#list>
        });
    </#if>

    <#if httpMethod == "GET">
        clientApi.${apiMethod}(<#list fields as field>${field.name}, </#list>(error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "POST">
        clientApi.${apiMethod}(body, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "DELETE">
        clientApi.${apiMethod}(${fields[0].name}, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "PUT">
        clientApi.${apiMethod}(body, ${fields[0].name}, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    </#if>

    };

    return (
        <div className="page-container" style={getStyle(customStyles,"pageContainer")}>
            <div className="title-bar" style={getStyle(customStyles,"titleBar")}>
                <div className="title" style={getStyle(customStyles,"title")}>${pageTitle}</div>
            </div>

            <form onSubmit={handleSubmit} className="form-container" style={getStyle(customStyles,"formContainer")}>
                <div className="form-inputs" style={getStyle(customStyles,"formInputs")}>
                <#list fields as field>
                    <InputField
                        label={getDisplayName(displayNames, "${field.name}")}
                        value={${field.name}}
                        onChange={handle${field.name?cap_first}Change}
                        placeholder={"Enter " + getDisplayName(displayNames, "${field.name}")}
                        error={${field.name}Error}
                        styles={getStyle(customStyles,"inputField")}
                    />
                </#list>
                </div>
                <button type="submit" className="form-submit" style={getStyle(customStyles,"formSubmit")}>Submit</button>
            </form>

        <#if responseSchema.type == "null">
            <div className="key-value-pairs-container" style={getStyle(customStyles,"keyValuePairsContainer")}>
                <RecursiveKeyValuePair
                    data={responseData}
                    displayNames={displayNames}
                    styles={getStyle(customStyles,"keyValuePair")}
                />
            </div>
        <#elseif responseSchema.type == "array">
            <div className="array-container" style={getStyle(customStyles,"arrayContainer")}>
                {responseData.map((item, index) => (
                    <div key={index} className="array-item" style={getStyle(customStyles,"arrayItem")}>
                        <RecursiveKeyValuePair
                            data={item}
                            displayNames={displayNames}
                            styles={getStyle(customStyles,"keyValuePair")}
                        />
                    </div>
                ))}
            </div>
        </#if>

            <div className="navigation-buttons-container" style={getStyle(customStyles,"navigationButtonsContainer")}>
            <#list nextPages as nextPage>
                <button
                    className="navigation-button"
                    style={getStyle(customStyles,"navigationButton")}
                    onClick={onClick${nextPage.name}}
                >
                    ${nextPage.name}
                </button>
            </#list>
            </div>
        </div>
    );
}
