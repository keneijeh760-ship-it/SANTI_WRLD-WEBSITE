import { siteContent } from "@/lib/data";
import Image from "next/image";
import Link from "next/link";

type Props = {
  images: string[];
};

export default function Lookbook({ images }: Props) {

  return (
    <section className="mx-auto max-w-6xl px-4 py-14 md:px-8" id="lookbook">
      <div className="mb-6 flex items-end justify-between gap-4">
        <h2 className="display-font text-5xl md:text-6xl">
          Campaign <span className="text-brand-gold">Archives</span>
        </h2>
        <Link href="/archives" className="text-sm uppercase tracking-wider text-zinc-300 hover:text-brand-red">
          Open full archives
        </Link>
      </div>
      <div className="grid gap-4 md:grid-cols-3">
        {images.map((image, index) => {
          const isWide = index === 0;
          return (
            <div
              key={image}
              className={`relative overflow-hidden rounded-2xl border border-white/10 ${
                isWide ? "md:col-span-2 h-[360px] md:h-[420px]" : "h-[300px] md:h-[320px]"
              }`}
            >
              <Image
                src={image}
                alt="SANTI WRLD campaign photo"
                fill
                sizes={isWide ? "(max-width: 768px) 100vw, 66vw" : "(max-width: 768px) 100vw, 33vw"}
                className="object-cover"
              />
            </div>
          );
        })}
      </div>
      <div className="mt-6 overflow-hidden rounded-2xl border border-white/10">
        <video
          src={siteContent.campaignVideo}
          autoPlay
          muted
          loop
          playsInline
          className="h-[280px] w-full object-cover md:h-[380px]"
        />
      </div>
    </section>
  );
}
