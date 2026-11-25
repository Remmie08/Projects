import { Pool } from "pg";
import dotenv from 'dotenv';
dotenv.config();

const db_schema = process.env.DB_SCHEMA;

export class User {
    username: string;
    pword: string;

    constructor(username: string, pword: string){
        this.username = username;
        this.pword = pword;
    }
}

export class UserDAO {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async findByUsername(username: string) : Promise<User | null>{
        let retval: User;
        let row: string[];
        const result = await this.pool.query(`SELECT * FROM ${db_schema}.fp_accounts WHERE username='${username}'`);
        if(result.rowCount === null || result.rowCount > 0){
            row = result.rows[0];
            retval = new User(
                "username" in row ? String(row["username"]) : "",
                "pword" in row ? String(row["pword"]) : ""
            );
            return retval;
        }
        else return null;
    }

    async createUser(user: User): Promise<boolean> {
        const result = await this.pool.query(
            `INSERT INTO ${db_schema}.fp_accounts (username, pword)
             VALUES ('${user.username}', '${user.pword}')
             ON CONFLICT (username) DO NOTHING`
        );
        return result.rowCount === 1;
    }
}