import { Pool } from "pg";
import dotenv from 'dotenv';
dotenv.config();

const db_schema = process.env.DB_SCHEMA;

export class Team {
    team_id: string;
    team_name: string;
    sport: string;
    logo: string;

    constructor(team_id: string, team_name: string, sport: string, logo: string){
        this.team_id = team_id;
        this.team_name = team_name;
        this.sport = sport;
        this.logo = logo;
    }
}

export class TeamDAO {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    private rowToTeam(row: any): Team {
        return new Team(
            "team_id" in row ? String(row["team_id"]) : "",
            "team_name" in row ? String(row["team_name"]) : "",
            "sport" in row ? String(row["sport"]) : "",
            "logo" in row ? String(row["logo"]) : ""
        );
    }

    async searchByName(team_name: string) : Promise<Team[]>{
        const result = await this.pool.query(`SELECT * FROM ${db_schema}.fp_teams WHERE team_name ILIKE '%${team_name}%'`);
        return result.rows.map((row: any) => this.rowToTeam(row));
    }

    async findById(team_id: string) : Promise<Team | null>{
        let row: string[];
        const result = await this.pool.query(`SELECT * FROM ${db_schema}.fp_teams WHERE team_id='${team_id}'`);
        if(result.rowCount === null || result.rowCount > 0){
            row = result.rows[0];
            return this.rowToTeam(result.rows[0]);
        }
        else return null;
    }

    async getAllTeams() : Promise<Team | null>{
        let row: string[];
        const result = await this.pool.query(`SELECT * FROM ${db_schema}.fp_teams`);
        if(result.rowCount === null || result.rowCount > 0){
            row = result.rows[0];
            return this.rowToTeam(result.rows[0]);
        }
        else return null;
    }
}