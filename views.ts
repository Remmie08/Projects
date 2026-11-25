import { User } from './User.ts';
import { Game } from './Game.ts';
import { Team } from './Team.ts';
import { Fav } from './Favs.ts'
 
 export function login(){
    return `
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Login page for ESPN10: The Diez"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>

        <link href="/CSS/login.css" rel="stylesheet" />

        <script type="module">
            import App from "./JS/login.js";
            const app = new App();
        </script>
    </head>
    <body>
        <img src="vecteezy_high-dynamic-range-soccer-stadium-with-empty-stands-background_48632608.jpg" alt="Stadium background" class="background">
        <div class="login-page-wrapper">
            <div class="form-box login">
                <h2>Login</h2>
                <form action="#" id="login">
                    <div class="form-error hidden" id="login-error-message">
                        <p>Invalid Username or Password</p>
                    </div>
                    <div class="input-box">
                        <input type="text" id="login-username" required>
                        <label>Username</label>
                    </div>
                    <div class="input-box">
                        <input type="password" id="login-password" required>
                        <label>Password</label>
                    </div>
                    <button type="submit" class="btn">Login</button>
                    <div class="login-register">
                        <p>Don't have an account? <a href="#" class="register-link">Register</a></p>
                    </div>
                </form>
            </div>

            <div class="form-box register">
                <h2>Registration</h2>
                <form action="#" id="register">
                    <div class="form-error hidden" id="register-error-message">
                        <p>Account already exits</p>
                    </div>
                    <div class="input-box">
                        <input type="text" id="register-username" required>
                        <label>Username</label>
                    </div>
                    <div class="input-box">
                        <input type="password" id="register-password" required>
                        <label>Password</label>
                    </div>
                    <button type="submit" class="btn">Register</button>
                    <div class="login-register">
                        <p>Already have an account? <a href="#" class="login-link">Login</a></p>
                    </div>
                </form>
            </div>
        </div>
    </body>
    </html>
    `
}

export function get_games(games: Game[], onlyFavorites: boolean){
    return `
    <html>
      <body>
        <h1>Welcome to the games Page</h1>
      </body>
    </html>
    `
}

export function get_favorites(favs: Fav[], team_search: Team[], search_box: string){
    return `
    <html>
      <body>
        <h1>Welcome to the favorites Page</h1>
      </body>
    </html>
    `
}

export function get_teams(team: Team){
    return `
    <html>
      <body>
        <h1>Welcome to the teams Page</h1>
      </body>
    </html>
    `
}

export function get_team_games(team: Team, games: Game[]){
    return `
    <html>
      <body>
        <h1>Welcome to the team games Page</h1>
      </body>
    </html>
    `
}