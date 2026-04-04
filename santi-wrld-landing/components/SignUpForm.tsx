"use client";

import { siteContent } from "@/lib/data";
import { Instagram, Music2 } from "lucide-react";
import { FormEvent, useState } from "react";

const cfg = siteContent.signup;
const socials = siteContent.socials;

export default function SignUpForm() {
  const [email, setEmail] = useState("");
  const [status, setStatus] = useState<"idle" | "loading" | "success" | "error">("idle");
  const [message, setMessage] = useState("");

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setStatus("loading");
    setMessage("");

    const response = await fetch("/api/waitlist", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email }),
    });

    const data = await response.json();
    if (!response.ok) {
      setStatus("error");
      setMessage(data.error ?? "Unable to join waitlist right now.");
      return;
    }

    setStatus("success");
    setEmail("");
    setMessage(cfg.successMessage);
  }

  return (
    <section id="waitlist" className="mx-auto max-w-3xl px-4 py-20 text-center md:px-8">
      <p className="text-sm uppercase tracking-[0.25em] text-brand-gold">{cfg.preHeading}</p>
      <h2 className="display-font mt-2 text-5xl md:text-7xl">
        {cfg.headingStart} <span className="text-brand-red">{cfg.headingAccent}</span>
      </h2>
      <p className="mx-auto mt-4 max-w-xl text-zinc-300">{cfg.description}</p>

      <form onSubmit={onSubmit} className="mt-8 flex flex-col gap-3 sm:flex-row">
        <input
          type="email"
          required
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          placeholder="Enter your email"
          className="h-12 flex-1 rounded-full border border-white/20 bg-black/60 px-5 text-white outline-none transition focus:border-brand-red"
        />
        <button
          type="submit"
          disabled={status === "loading"}
          className="h-12 rounded-full bg-brand-red px-7 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 disabled:opacity-60"
        >
          {status === "loading" ? "Joining..." : "Join Waitlist"}
        </button>
      </form>

      {message ? (
        <p className={`mt-3 text-sm ${status === "success" ? "text-green-400" : "text-red-400"}`}>{message}</p>
      ) : null}

      <div className="mt-7 flex justify-center gap-6">
        <a
          href={socials.tiktok}
          target="_blank"
          rel="noreferrer"
          className="inline-flex items-center gap-2 text-zinc-300 transition hover:text-brand-red"
        >
          <Music2 size={18} />
          {socials.tiktokHandle}
        </a>
        <a
          href={socials.instagram}
          target="_blank"
          rel="noreferrer"
          className="inline-flex items-center gap-2 text-zinc-300 transition hover:text-brand-red"
        >
          <Instagram size={18} />
          {socials.instagramHandle}
        </a>
      </div>
    </section>
  );
}
