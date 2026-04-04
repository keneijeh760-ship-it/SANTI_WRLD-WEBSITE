import Image from "next/image";

type Props = {
  title: string;
  accent: "red" | "gold";
  images: string[];
};

export default function PhotoStrip({ title, accent, images }: Props) {
  const accentClass = accent === "red" ? "text-brand-red" : "text-brand-gold";
  const words = title.split(" ");
  const lastWord = words[words.length - 1];
  const firstWords = words.slice(0, -1).join(" ");

  return (
    <section className="mx-auto max-w-6xl px-4 py-14 md:px-8">
      <h2 className="display-font mb-8 text-5xl md:text-6xl">
        {firstWords} <span className={accentClass}>{lastWord}</span>
      </h2>
      <div className="grid grid-cols-2 gap-4 md:gap-5">
        {images.map((src) => (
          <Image
            key={src}
            src={src}
            alt="Love Never Dies campaign"
            width={600}
            height={800}
            sizes="(max-width: 768px) 50vw, 50vw"
            className="w-full rounded-xl"
          />
        ))}
      </div>
    </section>
  );
}
