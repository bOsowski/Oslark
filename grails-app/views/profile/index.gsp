<%@ page import="oslarkserver.User; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Profile</title>
    <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}" />
</head>
<body>
    <div id="edit-profile" class="content scaffold-edit" role="main">
        <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${this.role}">
            <ul class="errors" role="alert">
                <g:eachError bean="${user}" var="error">
                    <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form resource="${user}" method="PUT">
            <g:hiddenField name="version" value="${user.version}" />
            <fieldset class="form">
                <f:field bean="${user}" property="username"/>
                <f:field bean="${user}" property="password"/>
                <f:field bean="${user}" property="firstName"/>
                <f:field bean="${user}" property="lastName"/>
            </fieldset>
            <fieldset class="buttons">
                <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>