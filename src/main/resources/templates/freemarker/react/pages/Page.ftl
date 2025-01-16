<#assign responses = "/react/constants/Responses.ftl">

<#assign useState = "/react/hooks/UseState.ftl">
<#assign useEffect = "/react/hooks/UseEffect.ftl">

<#assign fetch = "/react/logic/Fetch.ftl">
<#assign navigate = "/react/logic/Navigate.ftl">
<#assign handleChange = "/react/logic/HandleChange.ftl">
<#assign handleSubmit = "/react/logic/HandleSubmit.ftl">

<#assign searchBarState = "/react/components/state/SearchBar.ftl">
<#assign formState = "/react/components/state/Form.ftl">
<#assign containerState = "/react/components/state/Container.ftl">
<#assign resultState = "/react/components/state/Result.ftl">

<#assign containerEffect = "/react/components/effect/Container.ftl">

<#assign searchBarLogic = "/react/components/logic/SearchBar.ftl">
<#assign formLogic = "/react/components/logic/Form.ftl">
<#assign containerLogic = "/react/components/logic/Container.ftl">

<#assign heroSectionCall = "/react/components/call/HeroSection.ftl">
<#assign searchBarCall = "/react/components/call/SearchBar.ftl">
<#assign buttonCall = "/react/components/call/Button.ftl">
<#assign formCall = "/react/components/call/Form.ftl">
<#assign cardSectionCall = "/react/components/call/CardSection.ftl">
<#assign alertCall = "/react/components/call/Alert.ftl">
<#assign containerCall = "/react/components/call/Container.ftl">
<#assign resultCall = "/react/components/call/Result.ftl">
<#assign nestCall = "/react/components/call/Nest.ftl">
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";
import CardSection from "../components/CardSection";
import Alert from "../components/Alert";
<#list page.components as component>
    <#switch component.type>
        <#case "Form">
import ${component.id?cap_first} from "../components/${component.id?cap_first}";
            <#break>
    </#switch>
</#list>
import "./${page.name?cap_first}.css";
import styles from "../custom_styles/${page.name?cap_first}";

<#-- Creating Constants -->
<#assign indentValue = 0>
<#list page.components as component>
    <#include responses>
</#list>

export default function ${page.name?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component UseStates -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
        <#assign component = parentComponent>
        <#switch parentComponent.type>
            <#case "SearchBar">
                <#include searchBarState>
                <#break>
            <#case "Form">
                <#include formState>
                <#break>
            <#case "Container">
                <#include containerState>
                <#break>
        </#switch>
    </#list>

    <#-- Creating Component UseEffects -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
    <#assign component = parentComponent>
    <#switch parentComponent.type>
        <#case "Container">
            <#include containerEffect>
            <#break>
    </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
        <#assign component = parentComponent>
        <#switch parentComponent.type>
            <#case "SearchBar">
                <#include searchBarLogic>
                <#break>
            <#case "Form">
                <#include formLogic>
                <#break>
            <#case "Container">
                <#include containerLogic>
                <#break>
        </#switch>
    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#assign indentValue = 3>
            <#list page.components as parentComponent>
                <#assign component = parentComponent>
                <#switch parentComponent.type>
                    <#case "HeroSection">
                        <#include heroSectionCall>
                        <#break>
                    <#case "SearchBar">
                        <#include searchBarCall>
                        <#break>
                    <#case "Button">
                        <#include buttonCall>
                        <#break>
                    <#case "Form">
                        <#include formCall>
                        <#break>
                    <#case "Container">
                        <#include containerCall>
                        <#break>
                </#switch>
            </#list>
        </div>
    )
};