import express from 'express';
import { Pool } from 'pg';
import dotenv from 'dotenv';
import bcrypt from 'bcrypt';
import session from 'express-session';
import { get_games, get_teams, get_team_games, get_favorites, login} from './views.ts';
import { Team, TeamDAO } from './Team.ts';
import { Game, GameDAO } from './Game.ts';
import { User, UserDAO } from './User.ts';
import { Fav, FavDAO } from './Favs.ts';

dotenv.config();
const app = express();
app.use(express.json());
app.use(express.static('public'));
app.use(session({
    secret: process.env.SESSION_SECRET || 'dev-secret-change-me',
    resave: false,
    saveUninitialized: false
}));
declare module 'express-session' {
    interface SessionData { username: string }
}


const port = 3000; // use this port, e.g. localhost:3000

let pool= new Pool({
    user: 'rmaury', //replace with your username
    host: 'db-pgsql-nyc3-53905-do-user-41991816-0.h.db.ondigitalocean.com',
    database: 'defaultdb',
    password: process.env.PASSWORD,
    port: 25060,
    ssl: { rejectUnauthorized: false }
});

const userDAO = new UserDAO(pool);
const teamDAO = new TeamDAO(pool);
const gameDAO = new GameDAO(pool);
const favsDAO = new FavDAO(pool);


// Validation patterns
const USERNAME = /^[A-Za-z0-9_]{3,30}$/;
const TEAM_ID = /^[0-9]{1,10}$/;

type Credentials = { username?: string; password?: string };
type AuthRequest = express.Request<unknown, unknown, Credentials>;

type GamesQuery = { favorites?: string };
type GamesRequest = express.Request<{}, unknown, unknown, GamesQuery>;

type FavBody = { team_id?: string };
type FavRequest = express.Request<{}, unknown, FavBody>;

type FavPageQuery = { team_name?: string };
type FavPageRequest = express.Request<{}, unknown, unknown, FavPageQuery>;

type TeamParams = { team_id: string };
type TeamPageRequest = express.Request<TeamParams, unknown, unknown>;

function requireLogin(req: express.Request, res: express.Response, next: express.NextFunction) {
    if (!req.session.username) {
        res.redirect('/login');
        return;
    }
    next();
}

app.get('/login', (req, res) => {
    res.send(login());
});

// handle user login by POST
app.post('/login', async (req: AuthRequest, res: express.Response) => {
    const { username, password } = req.body;
    if (!username || !password) {
        console.log("line 80")
        res.json({ success: false, message: 'Invalid Username or Password' });
        return;
    }
    const user = await userDAO.findByUsername(username);
    console.log(password)
    console.log(user)
    if (!user || !(await bcrypt.compare(password, user.pword))){
        console.log("line 85")
        res.json({ success: false, message: 'Invalid Username or Password' });
        return;
    }

    req.session.username = user.username;
    res.json({ success: true, message: 'User authentication successful.' });
});

// handle user registration by POST
app.post('/register', async (req: AuthRequest, res: express.Response) => {
    const { username, password } = req.body;
    if(!username || !USERNAME.test(username) || !password || password.length < 6){ 
        res.json({ success: false, message: 'Invalid username or password format' });
        return;
    }
    // add new user to the credentials object
    const hash = await bcrypt.hash(password, 10);
    const created = await userDAO.createUser(new User(username, hash));

    if (!created) {
        res.json({ success: false, message: 'Username is already taken.' });
        return;
    }
    res.json({ success: true, message: 'Registration successful.' });
})

// GET the current user
app.get('/user', (req, res) => {
    if (!req.session.username) {
        res.status(401).json({ success: false, message: 'Not logged in' });
        return;
    }
    res.json({ username: req.session.username });
});

app.get('/games', requireLogin, async (req: GamesRequest, res) => {
    const onlyFavs = req.query.favorites === 'true';
    const favs_list = await favsDAO.getFavsByUsername(req.session.username!)
    const games = onlyFavs
        ? await gameDAO.getGamesForFavs(favs_list)
        : await gameDAO.getRecentGames();
    res.status(200).send(get_games(games, onlyFavs));
});

app.get('/favorites', requireLogin, async (req: FavPageRequest, res) => {
    const username = req.session.username!;
        const favs = await favsDAO.getFavsByUsername(username);
        const results = req.query.team_name
            ? await teamDAO.searchByName(req.query.team_name)
            : [];
        res.send(get_favorites(favs, results, req.query.team_name ?? ''));
});

app.post('/favorites/add', requireLogin, async (req: FavRequest, res) => {
    const { team_id } = req.body;
    if (!team_id) {
        res.status(400).send('Invalid team');
        return;
    }
    await favsDAO.addFav(new Fav(req.session.username!, team_id));
    res.redirect('/favorites');
});

app.post('/favorites/remove', requireLogin, async (req, res) => {
    const { team_id } = req.body;
    if (!team_id) {
        res.status(400).send('Invalid team');
        return;
    }
    await favsDAO.removeFav(new Fav(req.session.username!, team_id));
    res.redirect('/favorites');
});

app.get('/teams', requireLogin, async (req, res) => {
    const teams = await teamDAO.getAllTeams();
    if (!teams) {
        res.status(404).send('Teams missing');
        return;
    }
    res.status(200).send(get_teams(teams));
});

app.get('/teams:team_id', requireLogin, async (req: TeamPageRequest, res: express.Response) => {
    const { team_id } = req.params;

    if (!team_id) {
        res.status(400).send('Invalid team');
        return;
    }

    const team = await teamDAO.findById(team_id);
    if (!team) {
        res.status(404).send('Team not found');
        return;
    }
    const games = await gameDAO.getGamesByTeamId(team_id);
    res.status(200).send(get_team_games(team, games));
});

app.listen(port, () => {
    console.log("Listening on port " + port);
});
