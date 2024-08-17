import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { DefaultApi } from "../client_api";
<#if httpMethod == "POST" || httpMethod == "PUT">
import ${requestSchema} from "../client_api/src/model/${requestSchema}";
</#if>
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";
import Alert from "../components/Alert";
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

const responses = {
<#list responses?keys as code>
    "${code}": {
        description: <#if responses[code].description??>"${responses[code].description}"<#else>null</#if>,
        responseSchema: {
            name: <#if responses[code].responseSchema.name??>"${responses[code].responseSchema.name}"<#else>null</#if>,
            type: <#if responses[code].responseSchema.type??>"${responses[code].responseSchema.type}"<#else>null</#if>
        },
        nextPages: [
        <#list responses[code].nextPages as nextPage>
            {name: "${nextPage.name}"}<#if nextPage_has_next>,</#if>
        </#list>
        ]
    }<#if code_has_next>,</#if>
</#list>
};

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

    const [responseData, setResponseData] = useState({});

    const [responseSchema, setResponseSchema] = useState({});
    const [nextPages, setNextPages] = useState([])
    const [alert, setAlert] = useState({
        type: '',
        statusCode: null,
        message: '',
        visible: false,
    });

    const showAlert = (type, statusCode, message) => {
        setAlert({
            type: type,
            statusCode: statusCode,
            message: message,
            visible: true,
        });
    };

    const closeAlert = () => {
        setAlert({ ...alert, visible: false });
    };

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
    const onNextPageButtonClick = (pageName) => {
        navigate("/" + pageName.charAt(0).toLowerCase() + pageName.slice(1));
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        closeAlert();
    <#if httpMethod == "POST" || httpMethod == "PUT">
        const body = ${requestSchema}.constructFromObject({
        <#list requestParams as param>
            ${param.name} : ${param.name},
        </#list>
        });
    </#if>

    <#if httpMethod == "GET">
        clientApi.${apiMethod}(<#list fields as field>${field.name}, </#list>(error, data, response) => {
    <#elseif httpMethod == "POST">
        clientApi.${apiMethod}(body, (error, data, response) => {
    <#elseif httpMethod == "DELETE">
        clientApi.${apiMethod}(${fields[0].name}, (error, data, response) => {
    <#elseif httpMethod == "PUT">
        clientApi.${apiMethod}(body, ${fields[0].name}, (error, data, response) => {
    </#if>
            if (error) {
                console.log(error);
                showAlert("error", response.statusCode, responses[response.statusCode]?.description);
            } else if (response.body == null) {
                showAlert("success", response.statusCode, responses[response.statusCode]?.description);
            }
            console.log(response)
            setResponseSchema(responses[response.statusCode]?.responseSchema);
            setNextPages(responses[response.statusCode]?.nextPages);
            setResponseData(response.body);
        });
    };

    return (
        <div className="page-container" style={getStyle(customStyles,"pageContainer")}>
        <#if pageTitle?? && pageTitle?has_content>
            <div className="title-bar" style={getStyle(customStyles,"titleBar")}>
                <div className="title" style={getStyle(customStyles,"title")}>${pageTitle}</div>
            </div>
        </#if>
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

            {alert.visible && (
                <Alert
                    type={alert.type}
                    statusCode={alert.statusCode}
                    message={alert.message}
                    onClose={closeAlert}
                />
            )}

            {responseSchema && (
                <>
                    {responseSchema.type === "null" && (
                        <div className="key-value-pairs-container" style={getStyle(customStyles,"keyValuePairsContainer")}>
                            <RecursiveKeyValuePair
                                data={responseData}
                                displayNames={displayNames}
                                styles={getStyle(customStyles,"keyValuePair")}
                            />
                        </div>
                    )}

                    {responseSchema.type === "array" && (
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
                    )}
                </>
            )}

            {nextPages.length > 0 && (
                <div className="navigation-buttons-container" style={getStyle(customStyles,"navigationButtonsContainer")}>
                    {nextPages.map((nextPage, index) => (
                        <button
                            key={index}
                            className="navigation-button"
                            style={getStyle(customStyles, "navigationButton")}
                            onClick={() => onNextPageButtonClick(nextPage.name)}
                        >
                        {nextPage.name}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}
