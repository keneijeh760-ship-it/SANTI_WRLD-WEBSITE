import { siteContent } from "@/lib/data";
import Image from "next/image";

const hero = siteContent.hero;

export default function Hero() {
  return (
    <section className="relative min-h-screen overflow-hidden pt-16">
      <Image
        src={hero.image}
        alt="SANTI WRLD campaign hero"
        fill
        priority
        className="object-cover opacity-60"
      />
      <div className="grain-overlay absolute inset-0 bg-black/55" />
      <div className="relative mx-auto flex min-h-[calc(100vh-4rem)] max-w-6xl flex-col justify-end px-4 pb-16 md:px-8 md:pb-24">
        {hero.preHeading ? (
          <p className="hero-fade-in mb-2 text-sm uppercase tracking-[0.25em] text-brand-gold">{hero.preHeading}</p>
        ) : null}
        <h1 className="display-font overflow-hidden text-6xl leading-[0.85] text-white md:text-8xl lg:text-9xl">
          <span className="hero-slide-up inline-block">{hero.titleLine1}</span>{" "}
          <span className="hero-slide-up inline-block text-brand-red" style={{ animationDelay: "0.1s" }}>{hero.titleLine2}</span>
        </h1>
        <p className="hero-fade-in mt-4 max-w-lg text-base text-zinc-200 md:text-lg" style={{ animationDelay: "0.35s" }}>
          {hero.subtitle}
        </p>
        <div className="hero-fade-in mt-8 flex flex-wrap gap-4" style={{ animationDelay: "0.5s" }}>
          <a
            href="#waitlist"
            className="btn-slide group relative overflow-hidden rounded-full bg-brand-red px-7 py-3 text-sm font-semibold uppercase tracking-wider text-white"
          >
            <span className="btn-slide-fill absolute inset-0 bg-red-500" />
            <span className="relative">{hero.ctaPrimary}</span>
          </a>
          <a
            href="#available"
            className="btn-slide group relative overflow-hidden rounded-full border border-white/30 px-7 py-3 text-sm font-semibold uppercase tracking-wider text-white"
          >
            <span className="btn-slide-fill absolute inset-0 bg-brand-gold/15" />
            <span className="relative transition-colors duration-300 group-hover:text-brand-gold">{hero.ctaSecondary}</span>
          </a>
        </div>
      </div>
    </section>
  );
}
