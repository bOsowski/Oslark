<%@ page import="oslarkserver.gameObjects.HighscoreController; oslarkserver.gameObjects.SeedController; oslarkserver.gameObjects.GameCharacterController; oslarkserver.CreateAccountController" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Oslark</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

    <div class="svg" role="presentation">
        <div class="oslark_logo_container">
            <asset:image src="homepage_logo.png" class="oslark_logo"/>
        </div>
    </div>

    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Oslark</h1>

            <p>
                Welcome to the world of adventure.

                <br><br> <g:link controller="login">${"Login"}</g:link><br>
                <g:link controller="createAccount">${"Create Account"}</g:link><br>
                <g:link controller="gameCharacter">${"Game Characters"}</g:link><br>
                <g:link controller="seed">${"Seeds"}</g:link><br>
                <g:link controller="highscore">${"High Scores"}</g:link>
            </p>
        </section>
    </div>
</body>
</html>
