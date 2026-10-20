# TaskFlow

A small React learning project: React form, Express API, MongoDB Atlas, saved data shown back in React.

## Requirements

- Node.js 18 or newer
- A free MongoDB Atlas cluster

## Setup

1. Create a cluster in MongoDB Atlas, add a database user, and allow your IP address under Network Access.
2. Copy the connection string from Atlas (Connect, Drivers).
3. Copy `.env.example` to `.env` in the project root and paste your connection string, with your real username and password.
4. Start the server:

```bash
cd server
npm install
npm run dev
```

5. In a second terminal, start the client:

```bash
cd client
npm install
npm run dev
```

6. Open http://localhost:5173.

## API endpoints

- `POST /api/tasks` validates and saves a task.
- `GET /api/tasks` returns saved tasks, newest first.
- `POST /api/signup` validates a signup, hashes the password with bcrypt, and saves it. The response never includes the password.

## Teaching stages

1. React forms: build the controlled inputs and log values with `console.log`.
2. Express: add the routes and test them with a tool such as Postman or curl.
3. MongoDB Atlas: add the Mongoose models and check records in the Atlas dashboard.
4. Integration: replace `console.log` with `fetch()` calls in `client/src/services/api.js`.
5. Display: fetch tasks and render them with `map()` in `TaskList.jsx`.

This version contains the finished Stage 5 code.

## Troubleshooting

- "Cannot reach the server": the Express server is not running, or `VITE_API_URL` is wrong.
- "Database connection failed": check `MONGODB_URI` and your Atlas Network Access list.
- CORS error: `CLIENT_ORIGIN` in `.env` must match the Vite address.
