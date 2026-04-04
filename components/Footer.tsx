import { siteContent } from "@/lib/data";

const brand = siteContent.brand;

export default function Footer() {
  return (
    <footer className="border-t border-white/10 py-8">
      <div className="mx-auto flex max-w-6xl flex-col items-center justify-between gap-2 px-4 text-sm text-zinc-400 md:flex-row md:px-8">
        <p>{brand.name} {brand.year}</p>
        <p className="display-font text-lg tracking-widest text-zinc-200">{brand.tagline}</p>
      </div>
    </footer>
  );
}
