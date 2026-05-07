"use client";

import { siteContent } from "@/lib/data";
import { useEffect, useState } from "react";

const cfg = siteContent.comingSoon;

function calcTimeLeft() {
  const now = Date.now();
  const target = new Date(cfg.dropDate).getTime();
  const diff = Math.max(target - now, 0);

  const days = Math.floor(diff / (1000 * 60 * 60 * 24));
  const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
  const minutes = Math.floor((diff / (1000 * 60)) % 60);
  return { days, hours, minutes };
}

export default function ComingSoon() {
  const [timeLeft, setTimeLeft] = useState<ReturnType<typeof calcTimeLeft> | null>(null);

  useEffect(() => {
    setTimeLeft(calcTimeLeft());
    const timer = setInterval(() => setTimeLeft(calcTimeLeft()), 60000);
    return () => clearInterval(timer);
  }, []);

  const label = timeLeft
    ? `${timeLeft.days}d ${timeLeft.hours}h ${timeLeft.minutes}m`
    : "";

  return (
    <section className="relative overflow-hidden border-y border-white/10 bg-zinc-950 py-20">
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_20%_20%,rgba(227,27,35,0.2),transparent_40%)]" />
      <div className="relative mx-auto max-w-6xl px-4 text-center md:px-8">
        <p className="text-sm uppercase tracking-[0.25em] text-brand-gold">{cfg.preHeading}</p>
        <h2 className="display-font mt-2 text-6xl text-brand-red md:text-8xl">{cfg.heading}</h2>
        <p className="mx-auto mt-4 max-w-xl text-zinc-300">{cfg.description}</p>
        <div className="pulse-glow mx-auto mt-8 inline-flex rounded-full border border-brand-red/40 bg-black/70 px-8 py-4">
          <p className="display-font text-4xl text-white md:text-5xl">{label}</p>
        </div>
      </div>
    </section>
  );
}
