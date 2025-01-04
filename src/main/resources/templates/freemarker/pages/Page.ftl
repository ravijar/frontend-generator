<#assign heroSectionTemplatePath = "/components/HeroSection.ftl">
<#assign searchBarTemplatePath = "/components/SearchBar.ftl">
<#assign buttonTemplatePath = "/components/Button.ftl">
<#assign formTemplatePath = "/components/Form.ftl">
<#assign cardTemplatePath = "/components/Card.ftl">

<#assign fetchTemplatePath = "/logic/Fetch.ftl">
<#assign navigateTemplatePath = "/logic/Navigate.ftl">
<#assign handleChangeTemplatePath = "/logic/HandleChange.ftl">
<#assign handleSubmitTemplatePath = "/logic/HandleSubmit.ftl">

<#assign useStateTemplatePath = "/hooks/UseState.ftl">
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";

export default function ${data.name?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component Hooks -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign state = "${component.id}${resource.urlParameters[0]?cap_first}">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}${resource.urlParameters[0]?cap_first}Error">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign state = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${parameter?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#list resource.requestParameters as parameter>
                    <#assign state = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${parameter?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
        </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign value = "${component.id}${resource.urlParameters[0]?cap_first}">
                <#assign indent = 1>
                <#include handleChangeTemplatePath>
                <#assign indent = 1>
                <#include fetchTemplatePath>
                <#assign indent = 1>
                <#include handleSubmitTemplatePath>
            <#break>
            <#case "Button">
                <#assign indent = 1>
                <#include navigateTemplatePath>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign value = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include handleChangeTemplatePath>
                </#list>
                <#list resource.requestParameters as parameter>
                    <#assign value = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include handleChangeTemplatePath>
                </#list>
                <#assign indent = 1>
                <#include fetchTemplatePath>
                <#assign indent = 1>
                <#include handleSubmitTemplatePath>
            <#break>
        </#switch>
    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#list data.components as component>
                <#assign body = component.body>
                <#assign resource = (component.resource)!>
                <#switch body.type>
                    <#case "HeroSection">
                        <#assign indent = 3>
                        <#include heroSectionTemplatePath>
                    <#break>
                    <#case "SearchBar">
                        <#assign indent = 3>
                        <#include searchBarTemplatePath>
                        <#if body.result.component.type == "Card">
                            <#assign data = "${component.id}FetchResponse">
                            <#assign indent = 4>
                            <#include cardTemplatePath>
                        </#if>
                    <#break>
                    <#case "Button">
                        <#assign indent = 3>
                        <#include buttonTemplatePath>
                     <#break>
                    <#case "Form">
                        <#assign indent = 3>
                        <#include formTemplatePath>
                    <#break>
                </#switch>
            </#list>
        </div>
    )
};