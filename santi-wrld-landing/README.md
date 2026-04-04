# SANTI WRLD Landing Page

Pre-launch landing site for SANTI WRLD with:
- Bold animated hero and marquee
- Available pieces + lookbook archives
- Coming soon countdown teaser
- Waitlist form backed by Supabase
- Route transitions and loading screen between pages

## Run Locally

```bash
npm install
npm run dev
```

App runs at [http://localhost:3000](http://localhost:3000).

## Environment Setup

Copy `.env.example` to `.env.local` and set:

```bash
SUPABASE_URL=...
SUPABASE_ANON_KEY=...
SUPABASE_SERVICE_ROLE_KEY=...
```

## Supabase Table

Run SQL from `supabase-schema.sql` in your Supabase SQL editor:

```sql
create table if not exists waitlist (
  id uuid primary key default gen_random_uuid(),
  email text unique not null,
  created_at timestamptz not null default now()
);
```
