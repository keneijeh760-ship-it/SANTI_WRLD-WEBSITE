import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";
import { lookbookImages } from "@/lib/data";
import Image from "next/image";

export default function ArchivesPage() {
  return (
    <div className="bg-brand-black text-white">
      <NavBar />
      <main className="mx-auto max-w-6xl px-4 pb-16 pt-28 md:px-8">
        <p className="text-sm uppercase tracking-[0.25em] text-brand-gold">SANTI WRLD</p>
        <h1 className="display-font mt-2 text-6xl text-brand-red md:text-8xl">ARCHIVES</h1>
        <p className="mt-4 max-w-2xl text-zinc-300">
          Full visual board from our campaign moments: park scenes, night shots, and raw streetwear edits.
        </p>
        <div className="mt-8 grid gap-4 sm:grid-cols-2 md:grid-cols-3">
          {lookbookImages.map((image) => (
            <div key={image} className="relative h-80 overflow-hidden rounded-2xl border border-white/10">
              <Image src={image} alt="SANTI WRLD archive image" fill sizes="(max-width: 640px) 100vw, (max-width: 768px) 50vw, 33vw" className="object-cover transition duration-500 hover:scale-105" />
            </div>
          ))}
        </div>
      </main>
      <Footer />
    </div>
  );
}
