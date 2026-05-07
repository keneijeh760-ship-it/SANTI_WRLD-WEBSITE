import { siteContent } from "@/lib/data";

const items = siteContent.marquee;

export default function MarqueeTicker() {
  return (
    <section className="overflow-hidden border-y border-white/10 bg-black py-4">
      <div className="marquee-track flex w-max gap-10 whitespace-nowrap">
        {[...items, ...items, ...items].map((item, index) => (
          <p key={`${item}-${index}`} className="display-font text-4xl text-brand-red md:text-5xl">
            {item}
          </p>
        ))}
      </div>
    </section>
  );
}
